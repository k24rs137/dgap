package com.example.dgap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.dgap.model.Lesson;
import com.example.dgap.service.LessonCatalogService;

class LessonCatalogTests {
    private final LessonCatalogService catalog = new LessonCatalogService();

    @Test
    void loadsAllLocalizedLessonsFromYaml() {
        assertThat(catalog.getCategories()).hasSize(4);
        assertThat(catalog.getCategories())
                .flatExtracting(category -> category.getLessons())
                .hasSize(24)
                .allSatisfy(lesson -> {
                    assertThat(lesson.getTitleByLang("ja")).isNotBlank();
                    assertThat(lesson.getTitleByLang("ko")).isNotBlank();
                    assertThat(lesson.getTitleByLang("en")).isNotBlank();
                    assertThat(lesson.getTitleByLang("hira")).isNotBlank();
                    assertThat(lesson.getStepsByLang("ja")).isNotEmpty();
                    assertThat(lesson.getQuizChoicesByLang("en")).isNotEmpty();
                });
    }

    @Test
    void providesADeepSmartphoneCourseWithPracticalGuidance() {
        var smartphone = catalog.getCategories().stream()
                .filter(category -> category.getUrl().equals("/category/smartphone"))
                .findFirst().orElseThrow();
        assertThat(smartphone.getLessons()).hasSize(12);

        Lesson troubleshooting = smartphone.getLessons().stream()
                .filter(lesson -> lesson.getUrl().equals("/lesson/qr-troubleshooting"))
                .findFirst().orElseThrow();

        assertThat(troubleshooting.getLevelByLang("ja")).isEqualTo("実践");
        assertThat(troubleshooting.getPrerequisitesByLang("ja")).isNotEmpty();
        assertThat(troubleshooting.getDeviceNotesByLang("ja")).isNotEmpty();
        assertThat(troubleshooting.getTroubleshootingByLang("ja")).isNotEmpty();
        assertThat(troubleshooting.getPracticeTasksByLang("ja")).hasSizeGreaterThanOrEqualTo(3);
    }

    @Test
    void keepsExpectedGmailContent() {
        Lesson gmail = catalog.getCategories().stream()
                .flatMap(category -> category.getLessons().stream())
                .filter(lesson -> lesson.getUrl().equals("/lesson/gmail"))
                .findFirst().orElseThrow();

        assertThat(gmail.getTitleByLang("ja")).isEqualTo("Gmailでメールを送る");
        assertThat(gmail.getTitleByLang("en")).isEqualTo("Send an email with Gmail");
        assertThat(gmail.getQuiz().getAnswerIndex()).isBetween(0, 2);
    }
}
