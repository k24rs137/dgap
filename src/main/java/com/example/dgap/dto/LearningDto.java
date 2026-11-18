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

    public record ActivityRequest(
            @NotBlank @Pattern(regexp = "/lesson/[a-z0-9-]{1,100}") String key,
            Boolean preCorrect,
            Boolean postCorrect,
            Boolean missionCompleted,
            Boolean reviewCompleted) {}

    public record HistoryItem(String title, String date) {}
    public record ActivityItem(String key, Boolean preCorrect, Boolean postCorrect,
            boolean missionCompleted, String reviewDueAt, String reviewedAt) {}
    public record LearningResponse(List<String> completed, List<String> favorites,
            List<HistoryItem> history, List<ActivityItem> activities) {}
    public record Saved(boolean success) {}
}
