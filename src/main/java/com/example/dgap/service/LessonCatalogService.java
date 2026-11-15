package com.example.dgap.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.dgap.model.Category;
import com.example.dgap.model.Lesson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

@Service
public class LessonCatalogService {
    private static final String CATALOG_RESOURCE = "/content/lesson-catalog.yaml";
    private final List<Category> categories;

    public LessonCatalogService() {
        this.categories = loadCatalog();
    }

    public List<Category> getCategories() {
        return categories;
    }

    private List<Category> loadCatalog() {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try (InputStream input = LessonCatalogService.class.getResourceAsStream(CATALOG_RESOURCE)) {
            if (input == null) {
                throw new IllegalStateException("Lesson catalog was not found: " + CATALOG_RESOURCE);
            }
            CatalogFile catalog = mapper.readValue(input, CatalogFile.class);
            if (catalog.categories() == null || catalog.categories().isEmpty()) {
                throw new IllegalStateException("Lesson catalog must contain at least one category.");
            }
            return catalog.categories().stream().map(this::toCategory).toList();
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load lesson catalog: " + CATALOG_RESOURCE, exception);
        }
    }

    private Category toCategory(CategoryData data) {
        List<Lesson> lessons = data.lessons() == null
                ? List.of()
                : data.lessons().stream().map(this::toLesson).toList();
        return new Category(
                required(data.title(), "category title"),
                required(data.description(), "category description"),
                "/category/" + required(data.id(), "category id"),
                required(data.steps(), "category steps"),
                lessons);
    }

    private Lesson toLesson(LessonData data) {
        QuizData quiz = required(data.quiz(), "lesson quiz");
        return new Lesson(
                required(data.title(), "lesson title"),
                required(data.description(), "lesson description"),
                "/lesson/" + required(data.id(), "lesson id"),
                required(data.level(), "lesson level"),
                required(data.time(), "lesson time"),
                required(data.steps(), "lesson steps"),
                required(data.outcome(), "lesson outcome"),
                required(data.practiceTasks(), "lesson practice tasks"),
                required(data.safetyTip(), "lesson safety tip"),
                required(quiz.question(), "quiz question"),
                required(quiz.choices(), "quiz choices"),
                quiz.answerIndex());
    }

    private <T> T required(T value, String field) {
        if (value == null) throw new IllegalStateException("Missing " + field + " in lesson catalog.");
        return value;
    }

    private record CatalogFile(List<CategoryData> categories) {}

    private record CategoryData(
            String id,
            Map<String, String> title,
            Map<String, String> description,
            Map<String, List<String>> steps,
            List<LessonData> lessons) {}

    private record LessonData(
            String id,
            Map<String, String> title,
            Map<String, String> description,
            Map<String, String> level,
            Map<String, String> time,
            Map<String, List<String>> steps,
            Map<String, String> outcome,
            Map<String, List<String>> practiceTasks,
            Map<String, String> safetyTip,
            QuizData quiz) {}

    private record QuizData(
            Map<String, String> question,
            Map<String, List<String>> choices,
            int answerIndex) {}
}
