package com.example.dgap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class CommunityDto {
    private CommunityDto() {}

    public record CreatePostRequest(
            @NotBlank(message = "投稿を入力してください。")
            @Size(max = 1000, message = "投稿は1〜1000文字で入力してください。")
            String text) {}

    public record Post(Long id, String user, String text, String createdAt,
            long likes, boolean liked, boolean owned) {}
    public record Created(boolean success, Long id) {}
    public record Action(boolean success) {}
    public record Like(boolean success, boolean liked) {}
}
