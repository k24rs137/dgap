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
@Table(name = "lesson_activities", uniqueConstraints =
        @UniqueConstraint(columnNames = {"user_id", "lesson_key"}))
public class LessonActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "lesson_key", nullable = false, length = 160)
    private String lessonKey;

    @Column(name = "pre_correct")
    private Boolean preCorrect;

    @Column(name = "post_correct")
    private Boolean postCorrect;

    @Column(name = "mission_completed", nullable = false)
    private boolean missionCompleted;

    @Column(name = "review_due_at")
    private Instant reviewDueAt;

    @Column(name = "reviewed_at")
    private Instant reviewedAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected LessonActivity() {}

    public LessonActivity(User user, String lessonKey) {
        this.user = user;
        this.lessonKey = lessonKey;
        this.updatedAt = Instant.now();
    }

    public String getLessonKey() { return lessonKey; }
    public Boolean getPreCorrect() { return preCorrect; }
    public Boolean getPostCorrect() { return postCorrect; }
    public boolean isMissionCompleted() { return missionCompleted; }
    public Instant getReviewDueAt() { return reviewDueAt; }
    public Instant getReviewedAt() { return reviewedAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setPreCorrect(Boolean preCorrect) { this.preCorrect = preCorrect; }
    public void setPostCorrect(Boolean postCorrect) { this.postCorrect = postCorrect; }
    public void setMissionCompleted(boolean missionCompleted) { this.missionCompleted = missionCompleted; }
    public void setReviewDueAt(Instant reviewDueAt) { this.reviewDueAt = reviewDueAt; }
    public void setReviewedAt(Instant reviewedAt) { this.reviewedAt = reviewedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
