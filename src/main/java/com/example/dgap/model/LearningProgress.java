package com.example.dgap.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "learning_progress", uniqueConstraints =
        @UniqueConstraint(columnNames = {"user_id", "lesson_key"}))
public class LearningProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "lesson_key", nullable = false, length = 160)
    private String lessonKey;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private Instant completedAt;

    protected LearningProgress() {}

    public LearningProgress(User user, String lessonKey, String title, Instant completedAt) {
        this.user = user;
        this.lessonKey = lessonKey;
        this.title = title;
        this.completedAt = completedAt;
    }

    public Long getId() { return id; }
    public String getLessonKey() { return lessonKey; }
    public String getTitle() { return title; }
    public Instant getCompletedAt() { return completedAt; }
    public void setTitle(String title) { this.title = title; }
    public void setCompletedAt(Instant completedAt) { this.completedAt = completedAt; }
}
