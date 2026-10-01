package com.example.dgap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dgap.model.LearningProgress;
import com.example.dgap.model.User;

public interface LearningProgressRepository extends JpaRepository<LearningProgress, Long> {
    List<LearningProgress> findByUserOrderByCompletedAtDesc(User user);
    Optional<LearningProgress> findByUserAndLessonKey(User user, String lessonKey);
    void deleteByUserAndLessonKey(User user, String lessonKey);
}
