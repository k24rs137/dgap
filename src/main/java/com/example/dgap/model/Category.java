package com.example.dgap.model;

import java.util.List;

public class Category {

    private String titleJa;
    private String titleKo;

    private String descriptionJa;
    private String descriptionKo;

    private String url;

    private List<String> stepsJa;
    private List<String> stepsKo;

    private List<Lesson> lessons;

    public Category(
            String titleJa,
            String titleKo,
            String descriptionJa,
            String descriptionKo,
            String url,
            List<String> stepsJa,
            List<String> stepsKo,
            List<Lesson> lessons) {

        this.titleJa = titleJa;
        this.titleKo = titleKo;

        this.descriptionJa = descriptionJa;
        this.descriptionKo = descriptionKo;

        this.url = url;

        this.stepsJa = stepsJa;
        this.stepsKo = stepsKo;

        this.lessons = lessons;
    }

    public String getTitleJa() {
        return titleJa;
    }

    public String getTitleKo() {
        return titleKo;
    }

    public String getDescriptionJa() {
        return descriptionJa;
    }

    public String getDescriptionKo() {
        return descriptionKo;
    }

    public String getUrl() {
        return url;
    }

    public List<String> getStepsJa() {
        return stepsJa;
    }

    public List<String> getStepsKo() {
        return stepsKo;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }
}