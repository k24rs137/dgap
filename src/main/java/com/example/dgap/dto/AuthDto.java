package com.example.dgap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public final class AuthDto {
    private AuthDto() {}

    public record Registration(
            @NotBlank(message = "ユーザーIDを入力してください。")
            @Pattern(regexp = "[A-Za-z0-9._-]{3,40}",
                    message = "ユーザーIDは3〜40文字の半角英数字・ピリオド・ハイフン・アンダースコアで入力してください。")
            String userId,
            @NotBlank(message = "ユーザー名を入力してください。")
            @Size(max = 40, message = "ユーザー名は40文字以内で入力してください。")
            @Pattern(regexp = "^[^\\p{Cc}\\p{Cf}]+$", message = "ユーザー名に使用できない文字が含まれています。")
            String displayName,
            @NotBlank(message = "パスワードを入力してください。")
            @Size(min = 8, max = 72, message = "パスワードは8〜72文字で入力してください。")
            String password) {}

    public record Credentials(
            @NotBlank(message = "ユーザーIDを入力してください。")
            @Pattern(regexp = "[A-Za-z0-9._-]{3,40}",
                    message = "ユーザーIDは3〜40文字の半角英数字・ピリオド・ハイフン・アンダースコアで入力してください。")
            String userId,
            @NotBlank(message = "パスワードを入力してください。")
            @Size(min = 8, max = 72, message = "パスワードは8〜72文字で入力してください。")
            String password) {}

    public record Action(boolean success, String userId, String displayName, String message) {}
    public record Session(boolean authenticated, String userId, String displayName) {}
    public record Logout(boolean success) {}
}
