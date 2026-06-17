package com.example.dgap.model;

import java.util.List;

public class Lesson {

    private String titleJa;
    private String titleKo;

    private String descriptionJa;
    private String descriptionKo;

    private String url;

    private String level;
    private String time;

    private List<String> stepsJa;
    private List<String> stepsKo;

    private Quiz quiz;

    public Lesson(
            String titleJa,
            String titleKo,
            String descriptionJa,
            String descriptionKo,
            String url,
            String level,
            String time,
            List<String> stepsJa,
            List<String> stepsKo,
            Quiz quiz) {

        this.titleJa = titleJa;
        this.titleKo = titleKo;
        this.descriptionJa = descriptionJa;
        this.descriptionKo = descriptionKo;
        this.url = url;
        this.level = level;
        this.time = time;
        this.stepsJa = stepsJa;
        this.stepsKo = stepsKo;
        this.quiz = quiz;
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

    public String getLevel() {
        return level;
    }

    public String getTime() {
        return time;
    }

    public List<String> getStepsJa() {
        return stepsJa;
    }

    public List<String> getStepsKo() {
        return stepsKo;
    }

    public Quiz getQuiz() {
        return quiz;
    }
}