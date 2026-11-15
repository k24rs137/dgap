package com.example.dgap.controller;

import java.time.Instant;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dgap.dto.ApiError;
import com.example.dgap.dto.AuthDto;
import com.example.dgap.model.User;
import com.example.dgap.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
    public static final String SESSION_USER_ID = "authenticatedUserId";
    private static final String LOGIN_FAILURES = "loginFailures";
    private static final String LOGIN_BLOCKED_UNTIL = "loginBlockedUntil";

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthDto.Registration body,
            HttpSession session, HttpServletRequest request) {
        String userId = UserService.normalizeUserId(body.userId());
        try {
            User user = userService.register(userId, body.displayName(), body.password());
            if (user == null) {
                return error(HttpStatus.CONFLICT, "このユーザーIDはすでに使用されています。");
            }
            request.changeSessionId();
            session.setAttribute(SESSION_USER_ID, user.getUserId());
            session.setMaxInactiveInterval(60 * 60 * 8);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    new AuthDto.Action(true, user.getUserId(), user.getDisplayName(),
                            "会員登録が完了しました。"));
        } catch (DataIntegrityViolationException exception) {
            return error(HttpStatus.CONFLICT, "このユーザーIDはすでに使用されています。");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthDto.Credentials body,
            HttpSession session, HttpServletRequest request) {
        Long blockedUntil = (Long) session.getAttribute(LOGIN_BLOCKED_UNTIL);
        if (blockedUntil != null && blockedUntil > Instant.now().toEpochMilli()) {
            return error(HttpStatus.TOO_MANY_REQUESTS, "試行回数が多すぎます。1分後に再度お試しください。");
        }

        String userId = UserService.normalizeUserId(body.userId());
        if (!userService.login(userId, body.password())) {
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

        request.changeSessionId();
        session.setAttribute(SESSION_USER_ID, userId);
        session.setAttribute(LOGIN_FAILURES, 0);
        session.removeAttribute(LOGIN_BLOCKED_UNTIL);
        session.setMaxInactiveInterval(60 * 60 * 8);
        String displayName = userService.findByUserId(userId)
                .map(User::getDisplayName).orElse(userId);
        return ResponseEntity.ok(new AuthDto.Action(
                true, userId, displayName, "ログインしました。"));
    }

    @GetMapping("/session")
    public ResponseEntity<AuthDto.Session> session(HttpSession session) {
        String userId = (String) session.getAttribute(SESSION_USER_ID);
        if (userId == null) {
            return ResponseEntity.ok(new AuthDto.Session(false, null, null));
        }
        return userService.findByUserId(userId)
                .map(user -> ResponseEntity.ok(new AuthDto.Session(
                        true, user.getUserId(), user.getDisplayName())))
                .orElseGet(() -> ResponseEntity.ok(new AuthDto.Session(false, null, null)));
    }

    @PostMapping("/logout")
    public AuthDto.Logout logout(HttpSession session) {
        session.invalidate();
        return new AuthDto.Logout(true);
    }

    private ResponseEntity<ApiError> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ApiError.of(message));
    }
}
