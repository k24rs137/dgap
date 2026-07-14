package com.example.dgap.model;

import java.util.Arrays;
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

    public String getTitleByLang(String lang) {
        if ("ko".equals(lang)) {
            return titleKo;
        }

        if ("en".equals(lang)) {
            return switch (url) {
                case "/category/smartphone" -> "Smartphone Use";
                case "/category/sns" -> "SNS and Messages";
                case "/category/google" -> "Google Services";
                case "/category/money" -> "Finance Apps";
                default -> titleJa;
            };
        }

        if ("hira".equals(lang)) {
            return switch (url) {
                case "/category/smartphone" -> "すまーとふぉんそうさ";
                case "/category/sns" -> "SNS・めっせーじ";
                case "/category/google" -> "Googleさーびす";
                case "/category/money" -> "おかねのあぷり";
                default -> titleJa;
            };
        }

        return titleJa;
    }

    public String getDescriptionByLang(String lang) {
        if ("ko".equals(lang)) {
            return descriptionKo;
        }

        if ("en".equals(lang)) {
            return switch (url) {
                case "/category/smartphone" -> "Learn basic smartphone operations.";
                case "/category/sns" -> "Learn how to use LINE, KakaoTalk, and other messaging tools.";
                case "/category/google" -> "Learn Google Search, Gmail, Google Maps, and other useful services.";
                case "/category/money" -> "Learn how to use payment apps and banking apps safely.";
                default -> descriptionJa;
            };
        }

        if ("hira".equals(lang)) {
            return switch (url) {
                case "/category/smartphone" -> "すまーとふぉんのきほんそうさをまなべます。";
                case "/category/sns" -> "LINEやかかおとーくなどのつかいかたをまなべます。";
                case "/category/google" -> "Googleけんさく、Gmail、Googleまっぷなどをまなべます。";
                case "/category/money" -> "けっさいあぷりやぎんこうあぷりをまなべます。";
                default -> descriptionJa;
            };
        }

        return descriptionJa;
    }

    public List<String> getStepsByLang(String lang) {
        if ("ko".equals(lang)) {
            return stepsKo;
        }

        if ("en".equals(lang)) {
            return switch (url) {
                case "/category/smartphone" -> Arrays.asList(
                    "Check how to turn the phone on and off",
                    "Practice typing and adjusting the volume",
                    "Use basic apps such as camera and phone"
                );
                case "/category/sns" -> Arrays.asList(
                    "Check basic account settings",
                    "Learn how to use messages and posts",
                    "Understand settings that protect personal information"
                );
                case "/category/google" -> Arrays.asList(
                    "Learn how to use Google Search",
                    "Send and receive emails with Gmail",
                    "Use Google Maps and Google Translate"
                );
                case "/category/money" -> Arrays.asList(
                    "Check safe login methods",
                    "Learn basic banking app operations",
                    "Be careful of suspicious notifications and scams"
                );
                default -> stepsJa;
            };
        }

        if ("hira".equals(lang)) {
            return switch (url) {
                case "/category/smartphone" -> Arrays.asList(
                    "でんげんのいれかた・きりかたをかくにんする",
                    "もじにゅうりょくやおんりょうちょうせいをれんしゅうする",
                    "かめらやでんわなどのきほんあぷりをつかう"
                );
                case "/category/sns" -> Arrays.asList(
                    "あかうんとのきほんせっていをかくにんする",
                    "めっせーじやとうこうのつかいかたをまなぶ",
                    "こじんじょうほうをまもるせっていをりかいする"
                );
                case "/category/google" -> Arrays.asList(
                    "Googleけんさくのつかいかたをまなぶ",
                    "Gmailでめーるをおくったりうけとったりする",
                    "Googleまっぷやほんやくをつかう"
                );
                case "/category/money" -> Arrays.asList(
                    "あんぜんなろぐいんほうほうをかくにんする",
                    "ぎんこうあぷりのきほんそうさをまなぶ",
                    "あやしいつうちやさぎにちゅういする"
                );
                default -> stepsJa;
            };
        }

        return stepsJa;
    }
}
