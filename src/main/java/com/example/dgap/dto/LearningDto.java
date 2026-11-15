package com.example.dgap.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public final class LearningDto {
    private LearningDto() {}

    public record ProgressRequest(
            @NotBlank @Pattern(regexp = "/lesson/[a-z0-9-]{1,100}") String key,
            boolean completed,
            @Size(max = 200) String title) {}

    public record FavoriteRequest(
            @NotBlank @Pattern(regexp = "/lesson/[a-z0-9-]{1,100}") String key,
            boolean favorite) {}

    public record HistoryItem(String title, String date) {}
    public record LearningResponse(List<String> completed, List<String> favorites,
            List<HistoryItem> history) {}
    public record Saved(boolean success) {}
}
