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
        int baseMinutes = Integer.parseInt(time.replace("分", ""));
        String minutes = String.valueOf(baseMinutes + 7);

        if ("ko".equals(lang)) {
            return minutes + "분";
        }
        if ("en".equals(lang)) {
            return minutes + " min";
        }
        if ("hira".equals(lang)) {
            return minutes + "ふん";
        }
        return minutes + "分";
    }

    public String getOutcomeByLang(String lang) {
        if ("ko".equals(lang)) {
            return "안전한 연습용 정보로 직접 조작하고, 결과를 스스로 확인할 수 있습니다.";
        }
        if ("en".equals(lang)) {
            return "You will be able to complete the task with safe practice data and verify the result yourself.";
        }
        if ("hira".equals(lang)) {
            return "あんぜんなれんしゅうようのないようで、じぶんでそうさして、けっかをかくにんできるようになります。";
        }
        return switch (url) {
            case "/lesson/smartphone-power" -> "電源オン・ロック解除・再起動を自分で行い、反応しないときの確認もできます。";
            case "/lesson/smartphone-input" -> "文字種を切り替え、入力ミスを直しながら短い文章を完成できます。";
            case "/lesson/phone-call" -> "連絡先を選んで発信し、スピーカーやミュートを使って安全に通話できます。";
            case "/lesson/camera-photo" -> "ピントと明るさを確認して撮影し、写真が保存されたことまで確認できます。";
            case "/lesson/gallery-view" -> "目的の写真を探し、拡大・整理・共有前の確認ができます。";
            case "/lesson/line-message" -> "相手を間違えずに文章や写真を送り、既読や送信結果を確認できます。";
            case "/lesson/kakao-message" -> "チャット相手を確認してメッセージを送り、送信結果を確認できます。";
            case "/lesson/sns-privacy" -> "投稿・プロフィール・位置情報の公開範囲を自分で点検できます。";
            case "/lesson/google-search" -> "具体的な検索語で調べ、複数の情報源を比べて信頼性を判断できます。";
            case "/lesson/gmail" -> "宛先・件名・本文・添付を確認してメールを送り、返信もできます。";
            case "/lesson/google-map" -> "目的地までの経路を交通手段別に調べ、出発前に所要時間を確認できます。";
            case "/lesson/youtube-watch" -> "必要な動画を探し、字幕・速度・音量を調整して視聴できます。";
            case "/lesson/google-translate" -> "言語を正しく選び、文字・音声・カメラ翻訳を場面に応じて使えます。";
            case "/lesson/payment-app" -> "店名と金額を確認し、支払い結果と利用履歴まで安全に確認できます。";
            case "/lesson/smbc-app" -> "公式アプリで残高と明細を確認し、安全にログアウトできます。";
            case "/lesson/yucho-app" -> "公式アプリで残高と入出金明細を確認し、安全に終了できます。";
            case "/lesson/money-safety" -> "不審な通知を開かず、公式窓口で確認して報告・削除できます。";
            default -> "手順を自分で実行し、結果を確認できるようになります。";
        };
    }

    public List<String> getPracticeTasksByLang(String lang) {
        if ("ko".equals(lang)) {
            return Arrays.asList("연습용 정보로 처음부터 끝까지 조작한다", "표시된 결과가 맞는지 확인한다", "화면이 다르면 뒤로 돌아가 메뉴 이름을 찾는다");
        }
        if ("en".equals(lang)) {
            return Arrays.asList("Complete the steps using safe practice information", "Check that the expected result appears", "If your screen differs, go back and look for a similarly named menu");
        }
        if ("hira".equals(lang)) {
            return Arrays.asList("れんしゅうようのないようで、さいしょからさいごまでそうさする", "おもったとおりのけっかになったか、かくにんする", "がめんがちがうときは、ひとつもどって、にたなまえをさがす");
        }
        return switch (url) {
            case "/lesson/smartphone-power" -> Arrays.asList("充電が20％以上あることを確認し、電源を入れてロックを解除する", "電源ボタンと音量ボタンの違いを指で確認する", "動作が重い想定で、電源メニューから再起動する場所を確認する");
            case "/lesson/smartphone-input" -> Arrays.asList("メモアプリに『明日10時に駅で会います』と入力する", "かな・英字・数字を切り替えて『KSU 2026』と入力する", "わざと1文字間違え、カーソル移動と削除で修正する");
            case "/lesson/phone-call" -> Arrays.asList("家族など了承を得た相手を連絡先から選び、発信前に名前を再確認する", "通話中にスピーカーとミュートを一度ずつ切り替える", "通話履歴から相手を確認し、誤発信せず折り返す手順を確認する");
            case "/lesson/camera-photo" -> Arrays.asList("明るい場所で書類を1枚、全体が入るように撮影する", "画面の被写体をタップしてピントを合わせ、撮り直して比べる", "撮影直後の小さい写真を開き、文字が読めるか拡大して確認する");
            case "/lesson/gallery-view" -> Arrays.asList("今日撮影した写真を日付から探す", "2本指で拡大し、必要な部分が鮮明か確認する", "共有ボタンを開くところまで進み、送信先を選ばず閉じる");
            case "/lesson/line-message" -> Arrays.asList("自分用メモまたは了承を得た相手を開き、短い予定を送る", "送信前に相手の名前と文章を声に出して確認する", "写真選択画面を開き、個人情報が写っていない写真だけを選ぶ");
            case "/lesson/kakao-message" -> Arrays.asList("自分用チャットまたは了承を得た相手に短い予定を送る", "送信前にプロフィール名とチャット履歴を確認する", "通知・写真・連絡先の権限設定を開き、許可範囲を確認する");
            case "/lesson/sns-privacy" -> Arrays.asList("現在のアカウントが公開か非公開か確認する", "過去の投稿を1件開き、公開相手と位置情報の有無を確認する", "タグ付けの承認と、連絡先から検索される設定を確認する");
            case "/lesson/google-search" -> Arrays.asList("『福岡市 粗大ごみ 申込 公式』のように地域・目的・公式を入れて検索する", "広告表示と通常の検索結果を見分ける", "自治体など公式サイトと別のサイトを開き、日付と内容を比較する");
            case "/lesson/gmail" -> Arrays.asList("自分宛てに件名『送信練習』、本文2行のメールを作る", "送信前に宛先・件名・添付ファイルを指差し確認する", "受信したメールに返信し、引用部分と自分の文章を見分ける");
            case "/lesson/google-map" -> Arrays.asList("近くの市役所や駅を検索し、住所が正しいか確認する", "徒歩と公共交通の経路を切り替え、時間と乗換回数を比べる", "出発時刻を変更し、一本後の経路も確認する");
            case "/lesson/youtube-watch" -> Arrays.asList("公式チャンネルの操作説明動画を検索する", "字幕をオンにし、再生速度を0.75倍へ変更する", "自動再生をオフにし、視聴履歴から同じ動画を開く");
            case "/lesson/google-translate" -> Arrays.asList("短い案内文を入力し、翻訳元と翻訳先の言語を入れ替える", "スピーカーボタンで発音を聞き、音量を調整する", "カメラ翻訳を試し、重要な文章は原文と結果を両方保存する");
            case "/lesson/payment-app" -> Arrays.asList("支払い前画面で残高・店名・金額を確認する順番を練習する", "店員に提示するコードと、自分が読み取るコードの違いを確認する", "支払い後に完了画面と利用履歴を開き、同じ金額か確認する");
            case "/lesson/smbc-app" -> Arrays.asList("公式ストアから入れたアプリか、提供元名を確認する", "ログイン後に残高と直近の入出金明細を確認する", "振込は実行せず、振込前に必要な確認項目を読み、ログアウトする");
            case "/lesson/yucho-app" -> Arrays.asList("公式ストアから入れたアプリか、提供元名を確認する", "ログイン後に残高と直近の入出金明細を確認する", "端末認証と通知設定を確認し、アプリを安全に終了する");
            case "/lesson/money-safety" -> Arrays.asList("不審な通知の送信元・URL・期限を紙に書き出して怪しい点を探す", "通知内のリンクを使わず、公式アプリのお知らせ欄から同じ情報を探す", "詐欺メール診断を使い、家族や公式窓口へ相談する手順を確認する");
            default -> Arrays.asList("手順を最初から最後まで実行する", "結果を確認する", "失敗した場合は一つ前の画面へ戻る");
        };
    }

    public String getSafetyTipByLang(String lang) {
        if ("ko".equals(lang)) return "비밀번호·인증번호·결제 정보는 다른 사람에게 보내지 마세요. 화면 이름은 기종과 앱 버전에 따라 다를 수 있습니다.";
        if ("en".equals(lang)) return "Never share passwords, verification codes, or payment details. Screen names may vary by device and app version.";
        if ("hira".equals(lang)) return "ぱすわーど・にんしょうばんごう・おかねのじょうほうは、ほかのひとにおくらないでください。がめんのなまえは、きしゅによってちがうことがあります。";
        if (url.contains("payment") || url.contains("smbc") || url.contains("yucho") || url.contains("money")) {
            return "暗証番号・パスワード・SMS認証コードは誰にも伝えません。送金や支払いは、店名・相手・金額を確認できた場合だけ確定してください。";
        }
        if (url.contains("line") || url.contains("kakao") || url.contains("sns")) {
            return "送信・投稿の前に相手と公開範囲を再確認します。住所、電話番号、身分証、現在地が写る画像は送らないでください。";
        }
        return "画面名やボタンの位置は機種・OS・アプリの版によって異なります。分からないまま削除・購入・送信を確定せず、一つ前へ戻って確認してください。";
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
