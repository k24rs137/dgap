package com.example.dgap.model;

import java.util.List;
import java.util.Map;

public class Category {
    private final Map<String, String> titles;
    private final Map<String, String> descriptions;
    private final String url;
    private final Map<String, List<String>> steps;
    private final List<Lesson> lessons;

    public Category(Map<String, String> titles, Map<String, String> descriptions,
            String url, Map<String, List<String>> steps, List<Lesson> lessons) {
        this.titles = Map.copyOf(titles);
        this.descriptions = Map.copyOf(descriptions);
        this.url = url;
        this.steps = Map.copyOf(steps);
        this.lessons = List.copyOf(lessons);
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

    public List<String> getStepsJa() {
        return getStepsByLang("ja");
    }

    public List<String> getStepsKo() {
        return getStepsByLang("ko");
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public String getTitleByLang(String lang) {
        return localized(titles, lang);
    }

    public String getDescriptionByLang(String lang) {
        return localized(descriptions, lang);
    }

    public List<String> getStepsByLang(String lang) {
        return steps.getOrDefault(normalizeLanguage(lang), steps.getOrDefault("ja", List.of()));
    }

    private String localized(Map<String, String> values, String lang) {
        return values.getOrDefault(normalizeLanguage(lang), values.getOrDefault("ja", ""));
    }

    private String normalizeLanguage(String lang) {
        return lang == null || lang.isBlank() ? "ja" : lang;
    }
}
