package com.example.dgap.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dgap.dto.ApiError;
import com.example.dgap.dto.AccountDto;
import com.example.dgap.dto.LearningDto;
import com.example.dgap.model.FavoriteLesson;
import com.example.dgap.model.LearningProgress;
import com.example.dgap.model.User;
import com.example.dgap.repository.FavoriteLessonRepository;
import com.example.dgap.repository.LearningProgressRepository;
import com.example.dgap.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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
        return ResponseEntity.ok(new LearningDto.LearningResponse(
                progress.stream().map(LearningProgress::getLessonKey).toList(),
                favoriteRepository.findByUser(user).stream().map(FavoriteLesson::getLessonKey).toList(),
                progress.stream().limit(30)
                        .map(item -> new LearningDto.HistoryItem(
                                item.getTitle(), item.getCompletedAt().toString()))
                        .toList()));
    }

    @PostMapping("/progress")
    @Transactional
    public ResponseEntity<?> progress(@Valid @RequestBody LearningDto.ProgressRequest body,
            HttpSession session) {
        User user = currentUser(session);
        if (user == null) return unauthorized();
        String title = cleanTitle(body.title());

        if (!body.completed()) {
            progressRepository.deleteByUserAndLessonKey(user, body.key());
        } else {
            LearningProgress item = progressRepository.findByUserAndLessonKey(user, body.key())
                    .orElseGet(() -> new LearningProgress(user, body.key(), title, Instant.now()));
            item.setTitle(title);
            item.setCompletedAt(Instant.now());
            progressRepository.save(item);
        }
        return ResponseEntity.ok(new LearningDto.Saved(true));
    }

    @PostMapping("/favorite")
    @Transactional
    public ResponseEntity<?> favorite(@Valid @RequestBody LearningDto.FavoriteRequest body,
            HttpSession session) {
        User user = currentUser(session);
        if (user == null) return unauthorized();

        if (body.favorite() && favoriteRepository.findByUserAndLessonKey(user, body.key()).isEmpty()) {
            favoriteRepository.save(new FavoriteLesson(user, body.key()));
        } else if (!body.favorite()) {
            favoriteRepository.deleteByUserAndLessonKey(user, body.key());
        }
        return ResponseEntity.ok(new LearningDto.Saved(true));
    }

    @PatchMapping("/display-name")
    public ResponseEntity<?> changeDisplayName(
            @Valid @RequestBody AccountDto.ChangeDisplayNameRequest body,
            HttpSession session) {
        String userId = (String) session.getAttribute(AuthController.SESSION_USER_ID);
        return userService.changeDisplayName(userId, body.displayName())
                .<ResponseEntity<?>>map(user -> ResponseEntity.ok(
                        new AccountDto.ChangeDisplayNameResponse(
                                true, user.getDisplayName(), "ユーザー名を変更しました。")))
                .orElseGet(this::unauthorized);
    }

    private User currentUser(HttpSession session) {
        return userService.findByUserId((String) session.getAttribute(AuthController.SESSION_USER_ID))
                .orElse(null);
    }

    private String cleanTitle(String value) {
        String title = value == null || value.isBlank() ? "レッスン" : value.trim();
        return title.substring(0, Math.min(title.length(), 200));
    }

    private ResponseEntity<ApiError> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiError.of("ログインが必要です。"));
    }
}
