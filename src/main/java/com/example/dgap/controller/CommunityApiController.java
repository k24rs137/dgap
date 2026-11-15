package com.example.dgap.controller;

import java.time.Instant;
import java.util.List;

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

import com.example.dgap.dto.ApiError;
import com.example.dgap.dto.CommunityDto;
import com.example.dgap.model.CommunityLike;
import com.example.dgap.model.CommunityPost;
import com.example.dgap.model.User;
import com.example.dgap.repository.CommunityLikeRepository;
import com.example.dgap.repository.CommunityPostRepository;
import com.example.dgap.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

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
    public List<CommunityDto.Post> posts(HttpSession session) {
        User currentUser = currentUser(session);
        return postRepository.findAllByOrderByCreatedAtDesc().stream()
                .limit(100)
                .map(post -> new CommunityDto.Post(
                        post.getId(), post.getUser().getDisplayName(), post.getText(),
                        post.getCreatedAt().toString(), likeRepository.countByPost(post),
                        currentUser != null && likeRepository.existsByPostAndUser(post, currentUser),
                        currentUser != null && post.getUser().getId().equals(currentUser.getId())))
                .toList();
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CommunityDto.CreatePostRequest body,
            HttpSession session) {
        User user = currentUser(session);
        if (user == null) return unauthorized();
        CommunityPost post = postRepository.save(
                new CommunityPost(user, body.text().trim(), Instant.now()));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommunityDto.Created(true, post.getId()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id, HttpSession session) {
        User user = currentUser(session);
        if (user == null) return unauthorized();
        CommunityPost post = postRepository.findById(id).orElse(null);
        if (post == null) return ResponseEntity.notFound().build();
        if (!post.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiError.of("この投稿は削除できません。"));
        }
        likeRepository.deleteByPost(post);
        postRepository.delete(post);
        return ResponseEntity.ok(new CommunityDto.Action(true));
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
        return ResponseEntity.ok(new CommunityDto.Like(true, !liked));
    }

    private User currentUser(HttpSession session) {
        return userService.findByUserId((String) session.getAttribute(AuthController.SESSION_USER_ID))
                .orElse(null);
    }

    private ResponseEntity<ApiError> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiError.of("ログインが必要です。"));
    }
}
