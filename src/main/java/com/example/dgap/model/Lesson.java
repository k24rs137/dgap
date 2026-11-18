package com.example.dgap.model;

import java.util.List;
import java.util.Map;

public class Lesson {
    private final Map<String, String> titles;
    private final Map<String, String> descriptions;
    private final String url;
    private final Map<String, String> levels;
    private final Map<String, String> times;
    private final Map<String, List<String>> steps;
    private final Map<String, String> outcomes;
    private final Map<String, List<String>> practiceTasks;
    private final Map<String, String> safetyTips;
    private final Map<String, List<String>> prerequisites;
    private final Map<String, List<String>> deviceNotes;
    private final Map<String, List<String>> troubleshooting;
    private final Map<String, String> quizQuestions;
    private final Map<String, List<String>> quizChoices;
    private final Quiz quiz;

    public Lesson(Map<String, String> titles, Map<String, String> descriptions,
            String url, Map<String, String> levels, Map<String, String> times,
            Map<String, List<String>> steps, Map<String, String> outcomes,
            Map<String, List<String>> practiceTasks, Map<String, String> safetyTips,
            Map<String, List<String>> prerequisites, Map<String, List<String>> deviceNotes,
            Map<String, List<String>> troubleshooting,
            Map<String, String> quizQuestions, Map<String, List<String>> quizChoices,
            int answerIndex) {
        this.titles = Map.copyOf(titles);
        this.descriptions = Map.copyOf(descriptions);
        this.url = url;
        this.levels = Map.copyOf(levels);
        this.times = Map.copyOf(times);
        this.steps = Map.copyOf(steps);
        this.outcomes = Map.copyOf(outcomes);
        this.practiceTasks = Map.copyOf(practiceTasks);
        this.safetyTips = Map.copyOf(safetyTips);
        this.prerequisites = Map.copyOf(prerequisites);
        this.deviceNotes = Map.copyOf(deviceNotes);
        this.troubleshooting = Map.copyOf(troubleshooting);
        this.quizQuestions = Map.copyOf(quizQuestions);
        this.quizChoices = Map.copyOf(quizChoices);
        this.quiz = new Quiz(
                localized(this.quizQuestions, "ja"),
                localized(this.quizQuestions, "ko"),
                localizedList(this.quizChoices, "ja"),
                localizedList(this.quizChoices, "ko"),
                answerIndex);
    }

    public String getTitleJa() {
        return getTitleByLang("ja");
    }

    public String getTitleKo() {
        return getTitleByLang("ko");
    }

    public String getDescriptionJa() {
        return getDescriptionByLang("ja");
    }

    public String getDescriptionKo() {
        return getDescriptionByLang("ko");
    }

    public String getUrl() {
        return url;
    }

    public String getLevel() {
        return getLevelByLang("ja");
    }

    public String getTime() {
        return getTimeByLang("ja");
    }

    public List<String> getStepsJa() {
        return getStepsByLang("ja");
    }

    public List<String> getStepsKo() {
        return getStepsByLang("ko");
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public String getTitleByLang(String lang) {
        return localized(titles, lang);
    }

    public String getDescriptionByLang(String lang) {
        return localized(descriptions, lang);
    }

    public List<String> getStepsByLang(String lang) {
        return localizedList(steps, lang);
    }

    public String getLevelByLang(String lang) {
        return localized(levels, lang);
    }

    public String getTimeByLang(String lang) {
        return localized(times, lang);
    }

    public String getOutcomeByLang(String lang) {
        return localized(outcomes, lang);
    }

    public List<String> getPracticeTasksByLang(String lang) {
        return localizedList(practiceTasks, lang);
    }

    public String getSafetyTipByLang(String lang) {
        return localized(safetyTips, lang);
    }

    public List<String> getPrerequisitesByLang(String lang) {
        return localizedList(prerequisites, lang);
    }

    public List<String> getDeviceNotesByLang(String lang) {
        return localizedList(deviceNotes, lang);
    }

    public List<String> getTroubleshootingByLang(String lang) {
        return localizedList(troubleshooting, lang);
    }

    public String getQuizQuestionByLang(String lang) {
        return localized(quizQuestions, lang);
    }

    public List<String> getQuizChoicesByLang(String lang) {
        return localizedList(quizChoices, lang);
    }

    private static String localized(Map<String, String> values, String lang) {
        return values.getOrDefault(normalizeLanguage(lang), values.getOrDefault("ja", ""));
    }

    private static List<String> localizedList(Map<String, List<String>> values, String lang) {
        return values.getOrDefault(normalizeLanguage(lang), values.getOrDefault("ja", List.of()));
    }

    private static String normalizeLanguage(String lang) {
        return lang == null || lang.isBlank() ? "ja" : lang;
    }
}
