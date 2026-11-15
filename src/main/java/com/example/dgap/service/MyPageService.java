package com.example.dgap.service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dgap.dto.MyPageDto;
import com.example.dgap.model.FavoriteLesson;
import com.example.dgap.model.LearningProgress;
import com.example.dgap.model.Lesson;
import com.example.dgap.model.User;
import com.example.dgap.repository.FavoriteLessonRepository;
import com.example.dgap.repository.LearningProgressRepository;

@Service
public class MyPageService {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd")
            .withZone(ZoneId.of("Asia/Tokyo"));

    private final UserService userService;
    private final LessonCatalogService catalogService;
    private final LearningProgressRepository progressRepository;
    private final FavoriteLessonRepository favoriteRepository;

    public MyPageService(UserService userService,
            LessonCatalogService catalogService,
            LearningProgressRepository progressRepository,
            FavoriteLessonRepository favoriteRepository) {
        this.userService = userService;
        this.catalogService = catalogService;
        this.progressRepository = progressRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Transactional(readOnly = true)
    public Optional<MyPageDto.View> build(String userId, String lang) {
        return userService.findByUserId(userId).map(user -> buildForUser(user, lang));
    }

    private MyPageDto.View buildForUser(User user, String lang) {
        List<Lesson> lessons = catalogService.getCategories().stream()
                .flatMap(category -> category.getLessons().stream())
                .toList();
        Map<String, Lesson> lessonsByUrl = lessons.stream().collect(Collectors.toMap(
                Lesson::getUrl,
                lesson -> lesson,
                (first, ignored) -> first,
                LinkedHashMap::new));

        List<LearningProgress> progress = progressRepository.findByUserOrderByCompletedAtDesc(user);
        Set<String> completedKeys = progress.stream()
                .map(LearningProgress::getLessonKey)
                .filter(lessonsByUrl::containsKey)
                .collect(Collectors.toSet());
        Set<String> favoriteKeys = favoriteRepository.findByUser(user).stream()
                .map(FavoriteLesson::getLessonKey)
                .collect(Collectors.toSet());

        int totalCount = lessons.size();
        int completedCount = completedKeys.size();
        int progressPercent = totalCount == 0
                ? 0
                : (int) Math.round(completedCount * 100.0 / totalCount);

        MyPageDto.LessonItem nextLesson = lessons.stream()
                .filter(lesson -> !completedKeys.contains(lesson.getUrl()))
                .findFirst()
                .map(lesson -> lessonItem(lesson, lang))
                .orElse(null);
        List<MyPageDto.LessonItem> favorites = lessons.stream()
                .filter(lesson -> favoriteKeys.contains(lesson.getUrl()))
                .map(lesson -> lessonItem(lesson, lang))
                .toList();
        List<MyPageDto.HistoryItem> recentHistory = progress.stream()
                .filter(item -> lessonsByUrl.containsKey(item.getLessonKey()))
                .limit(5)
                .map(item -> new MyPageDto.HistoryItem(
                        lessonsByUrl.get(item.getLessonKey()).getTitleByLang(lang),
                        item.getLessonKey(),
                        DATE_FORMAT.format(item.getCompletedAt())))
                .toList();

        return new MyPageDto.View(user.getUserId(), user.getDisplayName(), completedCount, totalCount,
                progressPercent, nextLesson, favorites, recentHistory);
    }

    private MyPageDto.LessonItem lessonItem(Lesson lesson, String lang) {
        return new MyPageDto.LessonItem(
                lesson.getTitleByLang(lang),
                lesson.getDescriptionByLang(lang),
                lesson.getUrl(),
                lesson.getLevelByLang(lang),
                lesson.getTimeByLang(lang));
    }
}
