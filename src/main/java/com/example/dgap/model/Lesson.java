package com.example.dgap.model;

import java.util.Arrays;
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

    public String getTitleByLang(String lang) {
        if ("ko".equals(lang)) {
            return titleKo;
        }

        if ("en".equals(lang)) {
            return switch (url) {
                case "/lesson/smartphone-power" -> "Turn on a smartphone";
                case "/lesson/smartphone-input" -> "Type text";
                case "/lesson/phone-call" -> "Make a phone call";
                case "/lesson/camera-photo" -> "Take a photo with the camera";
                case "/lesson/gallery-view" -> "View photos in the gallery";
                case "/lesson/line-message" -> "Send a message on LINE";
                case "/lesson/kakao-message" -> "Send a message on KakaoTalk";
                case "/lesson/sns-privacy" -> "Check SNS privacy settings";
                case "/lesson/google-search" -> "Use Google Search";
                case "/lesson/gmail" -> "Send an email with Gmail";
                case "/lesson/google-map" -> "Find a destination with Google Maps";
                case "/lesson/youtube-watch" -> "Watch videos on YouTube";
                case "/lesson/google-translate" -> "Use Google Translate";
                case "/lesson/payment-app" -> "Pay with a payment app";
                case "/lesson/smbc-app" -> "Basic use of the SMBC app";
                case "/lesson/yucho-app" -> "Basic use of the Japan Post Bankbook app";
                case "/lesson/money-safety" -> "Identify suspicious notifications";
                default -> titleJa;
            };
        }

        if ("hira".equals(lang)) {
            return switch (url) {
                case "/lesson/smartphone-power" -> "すまーとふぉんのでんげんをいれる";
                case "/lesson/smartphone-input" -> "もじをにゅうりょくする";
                case "/lesson/phone-call" -> "でんわをかける";
                case "/lesson/camera-photo" -> "かめらでしゃしんをとる";
                case "/lesson/gallery-view" -> "ぎゃらりーでしゃしんをみる";
                case "/lesson/line-message" -> "LINEでめっせーじをおくる";
                case "/lesson/kakao-message" -> "かかおとーくでめっせーじをおくる";
                case "/lesson/sns-privacy" -> "SNSのこうかいはんいをかくにんする";
                case "/lesson/google-search" -> "Googleけんさくをつかう";
                case "/lesson/gmail" -> "Gmailでめーるをおくる";
                case "/lesson/google-map" -> "Googleまっぷでもくてきちをしらべる";
                case "/lesson/youtube-watch" -> "YouTubeでどうがをみる";
                case "/lesson/google-translate" -> "Googleほんやくをつかう";
                case "/lesson/payment-app" -> "けっさいあぷりでしはらいをする";
                case "/lesson/smbc-app" -> "SMBCあぷりのきほんそうさ";
                case "/lesson/yucho-app" -> "ゆうちょつうちょうあぷりのきほんそうさ";
                case "/lesson/money-safety" -> "あやしいつうちをみわける";
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
                case "/lesson/smartphone-power" -> "Learn how to turn on a smartphone and unlock the screen.";
                case "/lesson/smartphone-input" -> "Learn how to type text and create a sentence on a smartphone.";
                case "/lesson/phone-call" -> "Learn how to use the phone app to call a contact.";
                case "/lesson/camera-photo" -> "Learn how to use the camera app to take photos.";
                case "/lesson/gallery-view" -> "Learn how to check saved photos in the gallery.";
                case "/lesson/line-message" -> "Learn how to send a message to a friend using LINE.";
                case "/lesson/kakao-message" -> "Learn how to send a message using KakaoTalk.";
                case "/lesson/sns-privacy" -> "Learn how to check the visibility of posts and profiles to protect personal information.";
                case "/lesson/google-search" -> "Learn how to use Google Search to find information.";
                case "/lesson/gmail" -> "Learn how to create and send an email using Gmail.";
                case "/lesson/google-map" -> "Learn how to use Google Maps to search for destinations and routes.";
                case "/lesson/youtube-watch" -> "Learn how to search for and play videos on YouTube.";
                case "/lesson/google-translate" -> "Learn how to translate text with Google Translate.";
                case "/lesson/payment-app" -> "Learn how to pay safely using a payment app.";
                case "/lesson/smbc-app" -> "Learn basic operations such as checking your balance in the SMBC app.";
                case "/lesson/yucho-app" -> "Learn basic operations for checking information in the Japan Post Bankbook app.";
                case "/lesson/money-safety" -> "Learn how to identify suspicious notifications when using finance apps safely.";
                default -> descriptionJa;
            };
        }

        if ("hira".equals(lang)) {
            return switch (url) {
                case "/lesson/smartphone-power" -> "すまーとふぉんのでんげんをいれて、ろっくをかいじょするほうほうをまなびます。";
                case "/lesson/smartphone-input" -> "すまーとふぉんでもじをにゅうりょくして、ぶんしょうをつくるほうほうをまなびます。";
                case "/lesson/phone-call" -> "でんわあぷりをつかって、れんらくさきにでんわをかけるほうほうをまなびます。";
                case "/lesson/camera-photo" -> "かめらあぷりをつかって、しゃしんをとるほうほうをまなびます。";
                case "/lesson/gallery-view" -> "ほぞんされたしゃしんを、ぎゃらりーでかくにんするほうほうをまなびます。";
                case "/lesson/line-message" -> "LINEをつかって、ともだちにめっせーじをおくるほうほうをまなびます。";
                case "/lesson/kakao-message" -> "かかおとーくをつかって、めっせーじをおくるほうほうをまなびます。";
                case "/lesson/sns-privacy" -> "とうこうやぷろふぃーるのこうかいはんいをかくにんして、こじんじょうほうをまもるほうほうをまなびます。";
                case "/lesson/google-search" -> "Googleけんさくをつかって、しりたいじょうほうをしらべるほうほうをまなびます。";
                case "/lesson/gmail" -> "Gmailをつかって、めーるをつくっておくるほうほうをまなびます。";
                case "/lesson/google-map" -> "Googleまっぷをつかって、もくてきちやけいろをしらべるほうほうをまなびます。";
                case "/lesson/youtube-watch" -> "YouTubeでみたいどうがをけんさくして、さいせいするほうほうをまなびます。";
                case "/lesson/google-translate" -> "Googleほんやくをつかって、ぶんしょうをほんやくするほうほうをまなびます。";
                case "/lesson/payment-app" -> "けっさいあぷりをつかって、あんぜんにしはらいをするほうほうをまなびます。";
                case "/lesson/smbc-app" -> "SMBCあぷりで、ざんだかかくにんなどのきほんそうさをまなびます。";
                case "/lesson/yucho-app" -> "ゆうちょつうちょうあぷりで、じょうほうをかくにんするきほんそうさをまなびます。";
                case "/lesson/money-safety" -> "おかねのあぷりをあんぜんにつかうため、あやしいつうちをみわけるほうほうをまなびます。";
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
            return getStepsEn();
        }

        if ("hira".equals(lang)) {
            return getStepsHira();
        }

        return stepsJa;
    }

    private List<String> getStepsEn() {
        return switch (url) {
            case "/lesson/smartphone-power" -> Arrays.asList(
                "Check where the power button is",
                "Press and hold the power button",
                "Unlock the screen when it appears"
            );
            case "/lesson/smartphone-input" -> Arrays.asList(
                "Tap the input field",
                "Check that the keyboard appears",
                "Type the text",
                "Check what you typed"
            );
            case "/lesson/phone-call" -> Arrays.asList(
                "Open the phone app",
                "Choose a contact or enter a number",
                "Tap the call button",
                "Tap the end button when the call is finished"
            );
            case "/lesson/camera-photo" -> Arrays.asList(
                "Open the camera app",
                "Point the camera at what you want to take",
                "Tap the shutter button",
                "Check the photo you took"
            );
            case "/lesson/gallery-view" -> Arrays.asList(
                "Open the gallery app",
                "Choose the photo you want to see",
                "Zoom in or out to check the photo",
                "Tap the back button if needed"
            );
            case "/lesson/line-message" -> Arrays.asList(
                "Open the LINE app",
                "Choose the person you want to chat with",
                "Type your message",
                "Tap the send button"
            );
            case "/lesson/kakao-message" -> Arrays.asList(
                "Open KakaoTalk",
                "Choose the person you want to chat with",
                "Type your message",
                "Tap the send button"
            );
            case "/lesson/sns-privacy" -> Arrays.asList(
                "Open the SNS app you use",
                "Open the settings screen",
                "Check the privacy settings",
                "Change the visibility of posts if needed"
            );
            case "/lesson/google-search" -> Arrays.asList(
                "Open Google",
                "Type the words you want to search in the search box",
                "Choose the page you want to see from the results",
                "Check the information you need"
            );
            case "/lesson/gmail" -> Arrays.asList(
                "Open Gmail",
                "Tap the Compose button",
                "Enter the recipient, subject, and message",
                "Tap the Send button"
            );
            case "/lesson/google-map" -> Arrays.asList(
                "Open Google Maps",
                "Enter the destination in the search box",
                "Tap the Directions button",
                "Choose how you want to travel"
            );
            case "/lesson/youtube-watch" -> Arrays.asList(
                "Open YouTube",
                "Type words for the video you want to watch in the search box",
                "Choose a video and play it",
                "Adjust the volume or pause the video"
            );
            case "/lesson/google-translate" -> Arrays.asList(
                "Open Google Translate",
                "Enter the text you want to translate",
                "Choose the language to translate into",
                "Check the translation result"
            );
            case "/lesson/payment-app" -> Arrays.asList(
                "Open the payment app",
                "Show the payment screen",
                "Check the amount and store name",
                "Confirm the details and complete the payment"
            );
            case "/lesson/smbc-app" -> Arrays.asList(
                "Open the SMBC app",
                "Check the login screen",
                "Choose the function you want from the menu",
                "Check the displayed information"
            );
            case "/lesson/yucho-app" -> Arrays.asList(
                "Open the Japan Post Bankbook app",
                "Check the login screen",
                "Choose the item you want to check",
                "Check the displayed information"
            );
            case "/lesson/money-safety" -> Arrays.asList(
                "Check who sent the notification",
                "Check whether the message sounds unnatural",
                "Do not open the URL immediately",
                "If you feel unsure, check through the official app or official website"
            );
            default -> stepsJa;
        };
    }

    private List<String> getStepsHira() {
        return switch (url) {
            case "/lesson/smartphone-power" -> Arrays.asList(
                "でんげんぼたんのばしょをかくにんする",
                "でんげんぼたんをながくおす",
                "がめんがひょうじされたら、ろっくをかいじょする"
            );
            case "/lesson/smartphone-input" -> Arrays.asList(
                "にゅうりょくらんをたっぷする",
                "きーぼーどがひょうじされることをかくにんする",
                "もじをにゅうりょくする",
                "にゅうりょくしたないようをかくにんする"
            );
            case "/lesson/phone-call" -> Arrays.asList(
                "でんわあぷりをひらく",
                "れんらくさき、またはばんごうをえらぶ",
                "はっしんぼたんをおす",
                "つうわがおわったら、しゅうりょうぼたんをおす"
            );
            case "/lesson/camera-photo" -> Arrays.asList(
                "かめらあぷりをひらく",
                "とりたいものにかめらをむける",
                "さつえいぼたんをおす",
                "とったしゃしんをかくにんする"
            );
            case "/lesson/gallery-view" -> Arrays.asList(
                "ぎゃらりーあぷりをひらく",
                "みたいしゃしんをえらぶ",
                "しゃしんをおおきくしたり、ちいさくしたりしてかくにんする",
                "ひつようなら、もどるぼたんをおす"
            );
            case "/lesson/line-message" -> Arrays.asList(
                "LINEあぷりをひらく",
                "とーくしたいあいてをえらぶ",
                "めっせーじをにゅうりょくする",
                "そうしんぼたんをおす"
            );
            case "/lesson/kakao-message" -> Arrays.asList(
                "かかおとーくをひらく",
                "ちゃっとしたいあいてをえらぶ",
                "めっせーじをにゅうりょくする",
                "そうしんぼたんをおす"
            );
            case "/lesson/sns-privacy" -> Arrays.asList(
                "つかっているSNSあぷりをひらく",
                "せっていがめんをひらく",
                "ぷらいばしーせっていをかくにんする",
                "ひつようにおうじて、とうこうのこうかいはんいをかえる"
            );
            case "/lesson/google-search" -> Arrays.asList(
                "Googleをひらく",
                "けんさくらんにしりたいことばをにゅうりょくする",
                "けんさくけっかから、みたいぺーじをえらぶ",
                "ひつようなじょうほうをかくにんする"
            );
            case "/lesson/gmail" -> Arrays.asList(
                "Gmailをひらく",
                "さくせいぼたんをおす",
                "あてさき・けんめい・ほんぶんをにゅうりょくする",
                "そうしんぼたんをおす"
            );
            case "/lesson/google-map" -> Arrays.asList(
                "Googleまっぷをひらく",
                "けんさくらんにもくてきちをにゅうりょくする",
                "けいろぼたんをおす",
                "いどうしゅだんをえらぶ"
            );
            case "/lesson/youtube-watch" -> Arrays.asList(
                "YouTubeをひらく",
                "けんさくらんにみたいどうがのことばをにゅうりょくする",
                "どうがをえらんでさいせいする",
                "おんりょうやいちじていしをそうさする"
            );
            case "/lesson/google-translate" -> Arrays.asList(
                "Googleほんやくをひらく",
                "ほんやくしたいぶんしょうをにゅうりょくする",
                "ほんやくするげんごをえらぶ",
                "ほんやくけっかをかくにんする"
            );
            case "/lesson/payment-app" -> Arrays.asList(
                "けっさいあぷりをひらく",
                "しはらいがめんをひょうじする",
                "きんがくやおみせのなまえをかくにんする",
                "ないようをかくにんして、しはらいをかんりょうする"
            );
            case "/lesson/smbc-app" -> Arrays.asList(
                "SMBCあぷりをひらく",
                "ろぐいんがめんをかくにんする",
                "めにゅーからつかいたいきのうをえらぶ",
                "ひょうじないようをかくにんする"
            );
            case "/lesson/yucho-app" -> Arrays.asList(
                "ゆうちょつうちょうあぷりをひらく",
                "ろぐいんがめんをかくにんする",
                "かくにんしたいこうもくをえらぶ",
                "ひょうじされたないようをかくにんする"
            );
            case "/lesson/money-safety" -> Arrays.asList(
                "つうちのそうしんもとをかくにんする",
                "ふしぜんなひょうげんがないかかくにんする",
                "URLをすぐにひらかない",
                "ふあんなときは、こうしきあぷりやこうしきさいとからかくにんする"
            );
            default -> stepsJa;
        };
    }

    public String getLevelByLang(String lang) {
        if ("ko".equals(lang)) {
            return "초급";
        }
        if ("en".equals(lang)) {
            return "Beginner";
        }
        if ("hira".equals(lang)) {
            return "しょきゅう";
        }
        return level;
    }

    public String getTimeByLang(String lang) {
        String minutes = time.replace("分", "");

        if ("ko".equals(lang)) {
            return minutes + "분";
        }
        if ("en".equals(lang)) {
            return minutes + " min";
        }
        if ("hira".equals(lang)) {
            return minutes + "ふん";
        }
        return time;
    }

    public String getQuizQuestionByLang(String lang) {
        if ("ko".equals(lang)) {
            return quiz.getQuestionKo();
        }
        if ("en".equals(lang)) {
            return getQuizQuestionEn();
        }
        if ("hira".equals(lang)) {
            return getQuizQuestionHira();
        }
        return quiz.getQuestionJa();
    }

    private String getQuizQuestionEn() {
        return switch (url) {
            case "/lesson/smartphone-power" -> "What do you press to turn on a smartphone?";
            case "/lesson/smartphone-input" -> "What should you do first before typing text?";
            case "/lesson/phone-call" -> "Which button do you press to make a call?";
            case "/lesson/camera-photo" -> "Which app do you use to take photos?";
            case "/lesson/gallery-view" -> "Which app do you use to view saved photos?";
            case "/lesson/line-message" -> "What do you choose before sending a message on LINE?";
            case "/lesson/kakao-message" -> "Which screen do you use to send a message on KakaoTalk?";
            case "/lesson/sns-privacy" -> "Which setting should you check to protect personal information on SNS?";
            case "/lesson/google-search" -> "Where do you first type when using Google Search?";
            case "/lesson/gmail" -> "Which button do you press first when writing an email in Gmail?";
            case "/lesson/google-map" -> "Which button do you press to check directions in Google Maps?";
            case "/lesson/youtube-watch" -> "Where do you search for a video you want to watch on YouTube?";
            case "/lesson/google-translate" -> "Where do you enter the text you want to translate in Google Translate?";
            case "/lesson/payment-app" -> "What is important to check before paying?";
            case "/lesson/smbc-app" -> "What do you use to choose a function in a banking app?";
            case "/lesson/yucho-app" -> "Which screen do you check before viewing information in the Japan Post Bankbook app?";
            case "/lesson/money-safety" -> "What should you not do immediately when you receive a suspicious notification?";
            default -> quiz.getQuestionJa();
        };
    }

    private String getQuizQuestionHira() {
        return switch (url) {
            case "/lesson/smartphone-power" -> "すまーとふぉんのでんげんをいれるときにおすものはどれですか？";
            case "/lesson/smartphone-input" -> "もじをにゅうりょくするまえに、さいしょにすることはどれですか？";
            case "/lesson/phone-call" -> "でんわをかけるときにおすぼたんはどれですか？";
            case "/lesson/camera-photo" -> "しゃしんをとるときにつかうあぷりはどれですか？";
            case "/lesson/gallery-view" -> "ほぞんされたしゃしんをみるときにつかうあぷりはどれですか？";
            case "/lesson/line-message" -> "LINEでめっせーじをおくるまえにえらぶものはどれですか？";
            case "/lesson/kakao-message" -> "かかおとーくでめっせーじをおくるときにつかうがめんはどれですか？";
            case "/lesson/sns-privacy" -> "SNSでこじんじょうほうをまもるためにかくにんするせっていはどれですか？";
            case "/lesson/google-search" -> "Googleけんさくでさいしょににゅうりょくするばしょはどこですか？";
            case "/lesson/gmail" -> "Gmailでめーるをかくとき、さいしょにおすぼたんはどれですか？";
            case "/lesson/google-map" -> "Googleまっぷでみちじゅんをしらべるときにおすぼたんはどれですか？";
            case "/lesson/youtube-watch" -> "YouTubeでみたいどうがをさがすときにつかうばしょはどこですか？";
            case "/lesson/google-translate" -> "Googleほんやくでほんやくしたいぶんしょうはどこににゅうりょくしますか？";
            case "/lesson/payment-app" -> "しはらいのまえにかくにんすることでたいせつなのはどれですか？";
            case "/lesson/smbc-app" -> "ぎんこうあぷりできのうをえらぶときにつかうものはどれですか？";
            case "/lesson/yucho-app" -> "ゆうちょつうちょうあぷりでじょうほうをみるまえにかくにんするがめんはどれですか？";
            case "/lesson/money-safety" -> "あやしいつうちがとどいたとき、すぐにしてはいけないことはどれですか？";
            default -> quiz.getQuestionJa();
        };
    }

    public List<String> getQuizChoicesByLang(String lang) {
        if ("ko".equals(lang)) {
            return quiz.getChoicesKo();
        }
        if ("en".equals(lang)) {
            return getQuizChoicesEn();
        }
        if ("hira".equals(lang)) {
            return getQuizChoicesHira();
        }
        return quiz.getChoicesJa();
    }

    private List<String> getQuizChoicesEn() {
        return switch (url) {
            case "/lesson/smartphone-power" -> Arrays.asList("Power button", "Volume button", "Back button");
            case "/lesson/smartphone-input" -> Arrays.asList("Tap the input field", "Turn off the power", "Delete a photo");
            case "/lesson/phone-call" -> Arrays.asList("Call button", "Delete button", "Translate button");
            case "/lesson/camera-photo" -> Arrays.asList("Camera app", "Phone app", "Banking app");
            case "/lesson/gallery-view" -> Arrays.asList("Gallery app", "Translate app", "Settings app");
            case "/lesson/line-message" -> Arrays.asList("The person you want to chat with", "Screen brightness", "Camera quality");
            case "/lesson/kakao-message" -> Arrays.asList("Chat screen", "Map screen", "Payment screen");
            case "/lesson/sns-privacy" -> Arrays.asList("Privacy settings", "Volume settings", "Charging settings");
            case "/lesson/google-search" -> Arrays.asList("Search box", "Delete button", "Volume button");
            case "/lesson/gmail" -> Arrays.asList("Compose button", "Delete button", "Settings button");
            case "/lesson/google-map" -> Arrays.asList("Directions button", "Delete button", "Volume button");
            case "/lesson/youtube-watch" -> Arrays.asList("Search box", "Call button", "Payment screen");
            case "/lesson/google-translate" -> Arrays.asList("Input field", "Call history", "Camera settings");
            case "/lesson/payment-app" -> Arrays.asList("Amount and store name", "Wallpaper color", "Volume level");
            case "/lesson/smbc-app" -> Arrays.asList("Menu", "Camera zoom", "Ringtone");
            case "/lesson/yucho-app" -> Arrays.asList("Login screen", "Video playback screen", "Map screen");
            case "/lesson/money-safety" -> Arrays.asList("Open the URL immediately", "Check the sender", "Check through the official app");
            default -> quiz.getChoicesJa();
        };
    }

    private List<String> getQuizChoicesHira() {
        return switch (url) {
            case "/lesson/smartphone-power" -> Arrays.asList("でんげんぼたん", "おんりょうぼたん", "もどるぼたん");
            case "/lesson/smartphone-input" -> Arrays.asList("にゅうりょくらんをたっぷする", "でんげんをきる", "しゃしんをけす");
            case "/lesson/phone-call" -> Arrays.asList("はっしんぼたん", "さくじょぼたん", "ほんやくぼたん");
            case "/lesson/camera-photo" -> Arrays.asList("かめらあぷり", "でんわあぷり", "ぎんこうあぷり");
            case "/lesson/gallery-view" -> Arrays.asList("ぎゃらりーあぷり", "ほんやくあぷり", "せっていあぷり");
            case "/lesson/line-message" -> Arrays.asList("とーくしたいあいて", "がめんのあかるさ", "かめらのがしつ");
            case "/lesson/kakao-message" -> Arrays.asList("ちゃっとがめん", "ちずがめん", "しはらいがめん");
            case "/lesson/sns-privacy" -> Arrays.asList("ぷらいばしーせってい", "おんりょうせってい", "じゅうでんせってい");
            case "/lesson/google-search" -> Arrays.asList("けんさくらん", "さくじょぼたん", "おんりょうぼたん");
            case "/lesson/gmail" -> Arrays.asList("さくせいぼたん", "さくじょぼたん", "せっていぼたん");
            case "/lesson/google-map" -> Arrays.asList("けいろぼたん", "さくじょぼたん", "おんりょうぼたん");
            case "/lesson/youtube-watch" -> Arrays.asList("けんさくらん", "つうわぼたん", "しはらいがめん");
            case "/lesson/google-translate" -> Arrays.asList("にゅうりょくらん", "つうわりれき", "かめらせってい");
            case "/lesson/payment-app" -> Arrays.asList("きんがくやおみせのなまえ", "かべがみのいろ", "おんりょうのおおきさ");
            case "/lesson/smbc-app" -> Arrays.asList("めにゅー", "かめらずーむ", "ちゃくしんおん");
            case "/lesson/yucho-app" -> Arrays.asList("ろぐいんがめん", "どうがさいせいがめん", "ちずがめん");
            case "/lesson/money-safety" -> Arrays.asList("URLをすぐにひらく", "そうしんもとをかくにんする", "こうしきあぷりからかくにんする");
            default -> quiz.getChoicesJa();
        };
    }
}
