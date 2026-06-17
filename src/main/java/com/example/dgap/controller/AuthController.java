package com.example.dgap.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dgap.service.UserService;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/register")
    public Map<String, Object> register(@RequestBody Map<String, String> body) {

        String userId = body.get("userId");
        String password = body.get("password");

        if (userId == null || password == null || userId.isEmpty() || password.isEmpty()) {
            return Map.of(
                "success", false,
                "message", "入力が不足しています。"
            );
        }

        if (userService.register(userId, password) == null) {
            return Map.of(
                "success", false,
                "message", "このIDはすでに使われています。"
            );
        }

        return Map.of(
            "success", true,
            "message", "会員登録が完了しました。"
        );
    }

    @PostMapping("/api/login")
    public Map<String, Object> login(@RequestBody Map<String, String> body) {

        String userId = body.get("userId");
        String password = body.get("password");

        boolean result = userService.login(userId, password);

        if (result) {
            return Map.of(
                "success", true,
                "message", "ログインしました。"
            );
        }

        return Map.of(
            "success", false,
            "message", "IDまたはパスワードが正しくありません。"
        );
    }
}