package com.example.dgap.dto;

import java.util.List;

public final class MyPageDto {
    private MyPageDto() {}

    public record LessonItem(
            String title,
            String description,
            String url,
            String level,
            String time) {}

    public record HistoryItem(String title, String url, String completedDate) {}

    public record ReviewItem(String title, String description, String url, String dueDate) {}

    public record View(
            String userId,
            String displayName,
            int completedCount,
            int totalCount,
            int progressPercent,
            LessonItem nextLesson,
            List<ReviewItem> reviewLessons,
            String nextReviewDate,
            int assessedCount,
            int improvedCount,
            int missionCompletedCount,
            List<LessonItem> favorites,
            List<HistoryItem> recentHistory) {}
}
