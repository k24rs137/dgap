package com.example.dgap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dgap.model.LessonActivity;
import com.example.dgap.model.User;

public interface LessonActivityRepository extends JpaRepository<LessonActivity, Long> {
    List<LessonActivity> findByUser(User user);
    Optional<LessonActivity> findByUserAndLessonKey(User user, String lessonKey);
}
