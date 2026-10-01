package com.example.dgap.model;

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
@Table(name = "favorite_lessons", uniqueConstraints =
        @UniqueConstraint(columnNames = {"user_id", "lesson_key"}))
public class FavoriteLesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "lesson_key", nullable = false, length = 160)
    private String lessonKey;

    protected FavoriteLesson() {}

    public FavoriteLesson(User user, String lessonKey) {
        this.user = user;
        this.lessonKey = lessonKey;
    }

    public String getLessonKey() { return lessonKey; }
}
