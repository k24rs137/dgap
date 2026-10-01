package com.example.dgap.controller;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dgap.model.FavoriteLesson;
import com.example.dgap.model.LearningProgress;
import com.example.dgap.model.User;
import com.example.dgap.repository.FavoriteLessonRepository;
import com.example.dgap.repository.LearningProgressRepository;
import com.example.dgap.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/me")
public class UserDataController {
    private final UserService userService;
    private final LearningProgressRepository progressRepository;
    private final FavoriteLessonRepository favoriteRepository;

    public UserDataController(UserService userService,
            LearningProgressRepository progressRepository,
            FavoriteLessonRepository favoriteRepository) {
        this.userService = userService;
        this.progressRepository = progressRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @GetMapping("/learning")
    public ResponseEntity<?> learning(HttpSession session) {
        User user = currentUser(session);
        if (user == null) return unauthorized();

        List<LearningProgress> progress = progressRepository.findByUserOrderByCompletedAtDesc(user);
        return ResponseEntity.ok(Map.of(
                "completed", progress.stream().map(LearningProgress::getLessonKey).toList(),
                "favorites", favoriteRepository.findByUser(user).stream()
                        .map(FavoriteLesson::getLessonKey).toList(),
                "history", progress.stream().limit(30).map(item -> Map.of(
                        "title", item.getTitle(),
                        "date", item.getCompletedAt().toString())).toList()));
    }

    @PostMapping("/progress")
    @Transactional
    public ResponseEntity<?> progress(@RequestBody Map<String, Object> body, HttpSession session) {
        User user = currentUser(session);
        if (user == null) return unauthorized();
        String key = cleanKey(body.get("key"));
        String title = cleanTitle(body.get("title"));
        boolean completed = Boolean.TRUE.equals(body.get("completed"));
        if (key == null) return badRequest("レッスン情報が正しくありません。");

        if (!completed) {
            progressRepository.deleteByUserAndLessonKey(user, key);
        } else {
            LearningProgress item = progressRepository.findByUserAndLessonKey(user, key)
                    .orElseGet(() -> new LearningProgress(user, key, title, Instant.now()));
            item.setTitle(title);
            item.setCompletedAt(Instant.now());
            progressRepository.save(item);
        }
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/favorite")
    @Transactional
    public ResponseEntity<?> favorite(@RequestBody Map<String, Object> body, HttpSession session) {
        User user = currentUser(session);
        if (user == null) return unauthorized();
        String key = cleanKey(body.get("key"));
        boolean favorite = Boolean.TRUE.equals(body.get("favorite"));
        if (key == null) return badRequest("レッスン情報が正しくありません。");

        if (favorite && favoriteRepository.findByUserAndLessonKey(user, key).isEmpty()) {
            favoriteRepository.save(new FavoriteLesson(user, key));
        } else if (!favorite) {
            favoriteRepository.deleteByUserAndLessonKey(user, key);
        }
        return ResponseEntity.ok(Map.of("success", true));
    }

    private User currentUser(HttpSession session) {
        return userService.findByUserId((String) session.getAttribute(AuthController.SESSION_USER_ID))
                .orElse(null);
    }

    private String cleanKey(Object value) {
        String key = value instanceof String text ? text.trim() : "";
        return key.matches("/lesson/[a-z0-9-]{1,100}") ? key : null;
    }

    private String cleanTitle(Object value) {
        String title = value instanceof String text ? text.trim() : "レッスン";
        return title.substring(0, Math.min(title.length(), 200));
    }

    private ResponseEntity<Map<String, Object>> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false));
    }

    private ResponseEntity<Map<String, Object>> badRequest(String message) {
        return ResponseEntity.badRequest().body(Map.of("success", false, "message", message));
    }
}
