package com.example.dgap.controller;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dgap.model.User;
import com.example.dgap.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class AuthController {
    public static final String SESSION_USER_ID = "authenticatedUserId";
    private static final String LOGIN_FAILURES = "loginFailures";
    private static final String LOGIN_BLOCKED_UNTIL = "loginBlockedUntil";
    private static final Pattern USER_ID_PATTERN = Pattern.compile("[a-z0-9._-]{3,40}");

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(
            @RequestBody Map<String, String> body, HttpSession session) {
        String userId = UserService.normalizeUserId(body.get("userId"));
        String password = body.get("password");
        String validationError = validateCredentials(userId, password);
        if (validationError != null) {
            return error(HttpStatus.BAD_REQUEST, validationError);
        }

        try {
            User user = userService.register(userId, password);
            if (user == null) {
                return error(HttpStatus.CONFLICT, "このユーザーIDはすでに使用されています。");
            }
            session.setAttribute(SESSION_USER_ID, user.getUserId());
            session.setMaxInactiveInterval(60 * 60 * 8);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "success", true,
                    "userId", user.getUserId(),
                    "message", "会員登録が完了しました。"));
        } catch (DataIntegrityViolationException exception) {
            return error(HttpStatus.CONFLICT, "このユーザーIDはすでに使用されています。");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody Map<String, String> body, HttpSession session) {
        Long blockedUntil = (Long) session.getAttribute(LOGIN_BLOCKED_UNTIL);
        if (blockedUntil != null && blockedUntil > Instant.now().toEpochMilli()) {
            return error(HttpStatus.TOO_MANY_REQUESTS, "試行回数が多すぎます。1分後に再度お試しください。");
        }

        String userId = UserService.normalizeUserId(body.get("userId"));
        String password = body.get("password");
        if (!userService.login(userId, password)) {
            int failures = ((Integer) Optional.ofNullable(
                    session.getAttribute(LOGIN_FAILURES)).orElse(0)) + 1;
            session.setAttribute(LOGIN_FAILURES, failures);
            if (failures >= 5) {
                session.setAttribute(LOGIN_BLOCKED_UNTIL,
                        Instant.now().plusSeconds(60).toEpochMilli());
                session.setAttribute(LOGIN_FAILURES, 0);
            }
            return error(HttpStatus.UNAUTHORIZED, "ユーザーIDまたはパスワードが正しくありません。");
        }

        session.setAttribute(SESSION_USER_ID, userId);
        session.setAttribute(LOGIN_FAILURES, 0);
        session.removeAttribute(LOGIN_BLOCKED_UNTIL);
        session.setMaxInactiveInterval(60 * 60 * 8);
        return ResponseEntity.ok(Map.of(
                "success", true,
                "userId", userId,
                "message", "ログインしました。"));
    }

    @GetMapping("/session")
    public ResponseEntity<Map<String, Object>> session(HttpSession session) {
        String userId = (String) session.getAttribute(SESSION_USER_ID);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("authenticated", false));
        }
        return ResponseEntity.ok(Map.of("authenticated", true, "userId", userId));
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(HttpSession session) {
        session.invalidate();
        return Map.of("success", true);
    }

    private String validateCredentials(String userId, String password) {
        if (!USER_ID_PATTERN.matcher(userId).matches()) {
            return "ユーザーIDは3〜40文字の半角英数字・ピリオド・ハイフン・アンダースコアで入力してください。";
        }
        if (password == null || password.length() < 8 || password.length() > 72) {
            return "パスワードは8〜72文字で入力してください。";
        }
        return null;
    }

    private ResponseEntity<Map<String, Object>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of("success", false, "message", message));
    }
}
