package com.example.dgap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class AccountDto {
    private AccountDto() {}

    public record ChangeDisplayNameRequest(
            @NotBlank(message = "ユーザー名を入力してください。")
            @Size(max = 40, message = "ユーザー名は40文字以内で入力してください。")
            @jakarta.validation.constraints.Pattern(
                    regexp = "^[^\\p{Cc}\\p{Cf}]+$",
                    message = "ユーザー名に使用できない文字が含まれています。")
            String displayName) {}

    public record ChangeDisplayNameResponse(boolean success, String displayName, String message) {}
}
