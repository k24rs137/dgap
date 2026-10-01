package com.example.dgap.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dgap.model.FavoriteLesson;
import com.example.dgap.model.User;

public interface FavoriteLessonRepository extends JpaRepository<FavoriteLesson, Long> {
    List<FavoriteLesson> findByUser(User user);
    Optional<FavoriteLesson> findByUserAndLessonKey(User user, String lessonKey);
    void deleteByUserAndLessonKey(User user, String lessonKey);
}
