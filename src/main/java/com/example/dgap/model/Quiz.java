package com.example.dgap.model;

import java.util.List;

public class Quiz {

    private String questionJa;
    private String questionKo;

    private List<String> choicesJa;
    private List<String> choicesKo;

    private int answerIndex;

    public Quiz(
            String questionJa,
            String questionKo,
            List<String> choicesJa,
            List<String> choicesKo,
            int answerIndex) {

        this.questionJa = questionJa;
        this.questionKo = questionKo;
        this.choicesJa = choicesJa;
        this.choicesKo = choicesKo;
        this.answerIndex = answerIndex;
    }

    public String getQuestionJa() {
        return questionJa;
    }

    public String getQuestionKo() {
        return questionKo;
    }

    public List<String> getChoicesJa() {
        return choicesJa;
    }

    public List<String> getChoicesKo() {
        return choicesKo;
    }

    public int getAnswerIndex() {
        return answerIndex;
    }
}