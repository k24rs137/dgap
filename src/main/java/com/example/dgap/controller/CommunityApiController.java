package com.example.dgap.controller;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dgap.model.CommunityLike;
import com.example.dgap.model.CommunityPost;
import com.example.dgap.model.User;
import com.example.dgap.repository.CommunityLikeRepository;
import com.example.dgap.repository.CommunityPostRepository;
import com.example.dgap.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/community/posts")
public class CommunityApiController {
    private final UserService userService;
    private final CommunityPostRepository postRepository;
    private final CommunityLikeRepository likeRepository;

    public CommunityApiController(UserService userService,
            CommunityPostRepository postRepository,
            CommunityLikeRepository likeRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<Map<String, Object>> posts(HttpSession session) {
        User currentUser = currentUser(session);
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .limit(100)
                .map(post -> Map.<String, Object>of(
                        "id", post.getId(),
                        "user", post.getUser().getUserId(),
                        "text", post.getText(),
                        "createdAt", post.getCreatedAt().toString(),
                        "likes", likeRepository.countByPost(post),
                        "liked", currentUser != null && likeRepository.existsByPostAndUser(post, currentUser),
                        "owned", currentUser != null && post.getUser().getId().equals(currentUser.getId())))
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Map<String, String> body, HttpSession session) {
        User user = currentUser(session);
        if (user == null) return unauthorized();
        String text = body.getOrDefault("text", "").trim();
        if (text.isEmpty() || text.length() > 1000) {
            return ResponseEntity.badRequest().body(Map.of(
                    "success", false,
                    "message", "投稿は1〜1000文字で入力してください。"));
        }
        CommunityPost post = postRepository.save(new CommunityPost(user, text, Instant.now()));
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("success", true, "id", post.getId()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id, HttpSession session) {
        User user = currentUser(session);
        if (user == null) return unauthorized();
        CommunityPost post = postRepository.findById(id).orElse(null);
        if (post == null) return ResponseEntity.notFound().build();
        if (!post.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("success", false));
        }
        likeRepository.deleteByPost(post);
        postRepository.delete(post);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/{id}/like")
    @Transactional
    public ResponseEntity<?> toggleLike(@PathVariable Long id, HttpSession session) {
        User user = currentUser(session);
        if (user == null) return unauthorized();
        CommunityPost post = postRepository.findById(id).orElse(null);
        if (post == null) return ResponseEntity.notFound().build();
        boolean liked = likeRepository.existsByPostAndUser(post, user);
        if (liked) {
            likeRepository.deleteByPostAndUser(post, user);
        } else {
            likeRepository.save(new CommunityLike(post, user));
        }
        return ResponseEntity.ok(Map.of("success", true, "liked", !liked));
    }

    private User currentUser(HttpSession session) {
        return userService.findByUserId((String) session.getAttribute(AuthController.SESSION_USER_ID))
                .orElse(null);
    }

    private ResponseEntity<Map<String, Object>> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("success", false));
    }
}
