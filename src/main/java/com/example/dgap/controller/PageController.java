package com.example.dgap.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.dgap.model.Category;
import com.example.dgap.model.Lesson;
import com.example.dgap.model.Quiz;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    private List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        categories.add(new Category(
            "スマートフォン操作",
            "스마트폰 조작",
            "スマートフォンの基本操作を学べます。",
            "스마트폰의 기본 조작을 배울 수 있습니다.",
            "/category/smartphone",
            Arrays.asList(
                "電源の入れ方・切り方を確認する",
                "文字入力や音量調整を練習する",
                "カメラや電話など基本アプリを使う"
            ),
            Arrays.asList(
                "전원을 켜고 끄는 방법을 확인한다",
                "문자 입력과 음량 조절을 연습한다",
                "카메라와 전화 같은 기본 앱을 사용한다"
            ),
            Arrays.asList(
                new Lesson(
                    "スマートフォンの電源を入れる",
                    "스마트폰 전원 켜기",
                    "スマートフォンの電源を入れ、ロックを解除する方法を学びます。",
                    "스마트폰의 전원을 켜고 잠금을 해제하는 방법을 배웁니다.",
                    "/lesson/smartphone-power",
                    "初級",
                    "3分",
                    Arrays.asList(
                        "電源ボタンの位置を確認する",
                        "電源ボタンを長押しする",
                        "画面が表示されたらロックを解除する"
                    ),
                    Arrays.asList(
                        "전원 버튼의 위치를 확인한다",
                        "전원 버튼을 길게 누른다",
                        "화면이 표시되면 잠금을 해제한다"
                    ),
                    new Quiz(
                        "スマートフォンの電源を入れるときに押すものはどれですか？",
                        "스마트폰 전원을 켤 때 누르는 것은 무엇인가요?",
                        Arrays.asList("電源ボタン", "音量ボタン", "戻るボタン"),
                        Arrays.asList("전원 버튼", "음량 버튼", "뒤로 가기 버튼"),
                        0
                    )
                ),
                new Lesson(
                    "文字を入力する",
                    "문자 입력하기",
                    "スマートフォンで文字を入力し、文章を作成する方法を学びます。",
                    "스마트폰에서 문자를 입력하고 문장을 작성하는 방법을 배웁니다.",
                    "/lesson/smartphone-input",
                    "初級",
                    "5分",
                    Arrays.asList(
                        "入力欄をタップする",
                        "キーボードが表示されることを確認する",
                        "文字を入力する",
                        "入力した内容を確認する"
                    ),
                    Arrays.asList(
                        "입력 칸을 누른다",
                        "키보드가 표시되는지 확인한다",
                        "문자를 입력한다",
                        "입력한 내용을 확인한다"
                    ),
                    new Quiz(
                        "文字を入力する前に最初にすることはどれですか？",
                        "문자를 입력하기 전에 먼저 해야 하는 것은 무엇인가요?",
                        Arrays.asList("入力欄をタップする", "電源を切る", "写真を削除する"),
                        Arrays.asList("입력 칸을 누른다", "전원을 끈다", "사진을 삭제한다"),
                        0
                    )
                ),
                new Lesson(
                    "電話をかける",
                    "전화 걸기",
                    "電話アプリを使って連絡先に電話をかける方法を学びます。",
                    "전화 앱을 사용해 연락처에 전화하는 방법을 배웁니다.",
                    "/lesson/phone-call",
                    "初級",
                    "4分",
                    Arrays.asList(
                        "電話アプリを開く",
                        "連絡先または番号を選ぶ",
                        "発信ボタンを押す",
                        "通話が終わったら終了ボタンを押す"
                    ),
                    Arrays.asList(
                        "전화 앱을 연다",
                        "연락처 또는 번호를 선택한다",
                        "통화 버튼을 누른다",
                        "통화가 끝나면 종료 버튼을 누른다"
                    ),
                    new Quiz(
                        "電話をかけるときに押すボタンはどれですか？",
                        "전화를 걸 때 누르는 버튼은 무엇인가요?",
                        Arrays.asList("発信ボタン", "削除ボタン", "翻訳ボタン"),
                        Arrays.asList("통화 버튼", "삭제 버튼", "번역 버튼"),
                        0
                    )
                ),
                new Lesson(
                    "カメラで写真を撮る",
                    "카메라로 사진 찍기",
                    "カメラアプリを使って写真を撮影する方法を学びます。",
                    "카메라 앱을 사용해 사진을 찍는 방법을 배웁니다.",
                    "/lesson/camera-photo",
                    "初級",
                    "4分",
                    Arrays.asList(
                        "カメラアプリを開く",
                        "撮りたいものにカメラを向ける",
                        "撮影ボタンを押す",
                        "撮った写真を確認する"
                    ),
                    Arrays.asList(
                        "카메라 앱을 연다",
                        "찍고 싶은 대상을 향해 카메라를 맞춘다",
                        "촬영 버튼을 누른다",
                        "찍은 사진을 확인한다"
                    ),
                    new Quiz(
                        "写真を撮るときに使うアプリはどれですか？",
                        "사진을 찍을 때 사용하는 앱은 무엇인가요?",
                        Arrays.asList("カメラアプリ", "電話アプリ", "銀行アプリ"),
                        Arrays.asList("카메라 앱", "전화 앱", "은행 앱"),
                        0
                    )
                ),
                new Lesson(
                    "ギャラリーで写真を見る",
                    "갤러리에서 사진 보기",
                    "保存された写真をギャラリーで確認する方法を学びます。",
                    "저장된 사진을 갤러리에서 확인하는 방법을 배웁니다.",
                    "/lesson/gallery-view",
                    "初級",
                    "3分",
                    Arrays.asList(
                        "ギャラリーアプリを開く",
                        "見たい写真を選ぶ",
                        "写真を拡大・縮小して確認する",
                        "必要に応じて戻るボタンを押す"
                    ),
                    Arrays.asList(
                        "갤러리 앱을 연다",
                        "보고 싶은 사진을 선택한다",
                        "사진을 확대하거나 축소해 확인한다",
                        "필요하면 뒤로 가기 버튼을 누른다"
                    ),
                    new Quiz(
                        "保存された写真を見るときに使うアプリはどれですか？",
                        "저장된 사진을 볼 때 사용하는 앱은 무엇인가요?",
                        Arrays.asList("ギャラリーアプリ", "翻訳アプリ", "設定アプリ"),
                        Arrays.asList("갤러리 앱", "번역 앱", "설정 앱"),
                        0
                    )
                )
            )
        ));

        categories.add(new Category(
            "SNS",
            "SNS",
            "LINEやカカオトークなどの使い方を学べます。",
            "LINE이나 카카오톡 사용법을 배울 수 있습니다.",
            "/category/sns",
            Arrays.asList(
                "アカウントの基本設定を確認する",
                "メッセージや投稿の使い方を学ぶ",
                "個人情報を守る設定を理解する"
            ),
            Arrays.asList(
                "계정의 기본 설정을 확인한다",
                "메시지와 게시물 사용법을 배운다",
                "개인정보를 보호하는 설정을 이해한다"
            ),
            Arrays.asList(
                new Lesson(
                    "LINEでメッセージを送る",
                    "LINE으로 메시지 보내기",
                    "LINEを使って友だちにメッセージを送る方法を学びます。",
                    "LINE을 사용해 친구에게 메시지를 보내는 방법을 배웁니다.",
                    "/lesson/line-message",
                    "初級",
                    "5分",
                    Arrays.asList(
                        "LINEアプリを開く",
                        "トークしたい相手を選ぶ",
                        "メッセージを入力する",
                        "送信ボタンを押す"
                    ),
                    Arrays.asList(
                        "LINE 앱을 연다",
                        "대화하고 싶은 상대를 선택한다",
                        "메시지를 입력한다",
                        "보내기 버튼을 누른다"
                    ),
                    new Quiz(
                        "LINEでメッセージを送る前に選ぶものはどれですか？",
                        "LINE에서 메시지를 보내기 전에 선택하는 것은 무엇인가요?",
                        Arrays.asList("トークしたい相手", "画面の明るさ", "カメラの画質"),
                        Arrays.asList("대화하고 싶은 상대", "화면 밝기", "카메라 화질"),
                        0
                    )
                ),
                new Lesson(
                    "カカオトークでメッセージを送る",
                    "카카오톡으로 메시지 보내기",
                    "カカオトークを使ってメッセージを送る方法を学びます。",
                    "카카오톡을 사용해 메시지를 보내는 방법을 배웁니다.",
                    "/lesson/kakao-message",
                    "初級",
                    "5分",
                    Arrays.asList(
                        "カカオトークを開く",
                        "チャットしたい相手を選ぶ",
                        "メッセージを入力する",
                        "送信ボタンを押す"
                    ),
                    Arrays.asList(
                        "카카오톡을 연다",
                        "채팅하고 싶은 상대를 선택한다",
                        "메시지를 입력한다",
                        "보내기 버튼을 누른다"
                    ),
                    new Quiz(
                        "カカオトークでメッセージを送るときに使う画面はどれですか？",
                        "카카오톡에서 메시지를 보낼 때 사용하는 화면은 무엇인가요?",
                        Arrays.asList("チャット画面", "地図画面", "支払い画面"),
                        Arrays.asList("채팅 화면", "지도 화면", "결제 화면"),
                        0
                    )
                ),
                new Lesson(
                    "SNSの公開範囲を確認する",
                    "SNS 공개 범위 확인하기",
                    "投稿やプロフィールの公開範囲を確認し、個人情報を守る方法を学びます。",
                    "게시물과 프로필의 공개 범위를 확인하고 개인정보를 보호하는 방법을 배웁니다.",
                    "/lesson/sns-privacy",
                    "初級",
                    "7分",
                    Arrays.asList(
                        "利用しているSNSアプリを開く",
                        "設定画面を開く",
                        "プライバシー設定を確認する",
                        "投稿の公開範囲を必要に応じて変更する"
                    ),
                    Arrays.asList(
                        "사용 중인 SNS 앱을 연다",
                        "설정 화면을 연다",
                        "개인정보 보호 설정을 확인한다",
                        "필요에 따라 게시물 공개 범위를 변경한다"
                    ),
                    new Quiz(
                        "SNSで個人情報を守るために確認する設定はどれですか？",
                        "SNS에서 개인정보를 보호하기 위해 확인해야 하는 설정은 무엇인가요?",
                        Arrays.asList("プライバシー設定", "音量設定", "充電設定"),
                        Arrays.asList("개인정보 보호 설정", "음량 설정", "충전 설정"),
                        0
                    )
                )
            )
        ));

        categories.add(new Category(
            "Googleサービス",
            "Google 서비스",
            "Google検索、Gmail、Googleマップなどを学べます。",
            "Google 검색, Gmail, Google 지도 등을 배울 수 있습니다.",
            "/category/google",
            Arrays.asList(
                "Google検索の使い方を学ぶ",
                "Gmailでメールを送受信する",
                "Googleマップや翻訳を使う"
            ),
            Arrays.asList(
                "Google 검색 사용법을 배운다",
                "Gmail로 메일을 보내고 받는다",
                "Google 지도와 번역을 사용한다"
            ),
            Arrays.asList(
                new Lesson(
                    "Google検索を使う",
                    "Google 검색 사용하기",
                    "Google検索を使って知りたい情報を調べる方法を学びます。",
                    "Google 검색을 사용해 필요한 정보를 찾는 방법을 배웁니다.",
                    "/lesson/google-search",
                    "初級",
                    "5分",
                    Arrays.asList(
                        "Googleを開く",
                        "検索欄に知りたい言葉を入力する",
                        "検索結果から見たいページを選ぶ",
                        "必要な情報を確認する"
                    ),
                    Arrays.asList(
                        "Google을 연다",
                        "검색창에 알고 싶은 단어를 입력한다",
                        "검색 결과에서 보고 싶은 페이지를 선택한다",
                        "필요한 정보를 확인한다"
                    ),
                    new Quiz(
                        "Google検索で最初に入力する場所はどこですか？",
                        "Google 검색에서 먼저 입력하는 곳은 어디인가요?",
                        Arrays.asList("検索欄", "削除ボタン", "音量ボタン"),
                        Arrays.asList("검색창", "삭제 버튼", "음량 버튼"),
                        0
                    )
                ),
                new Lesson(
                    "Gmailでメールを送る",
                    "Gmail로 메일 보내기",
                    "Gmailを使ってメールを作成し、送信する方法を学びます。",
                    "Gmail을 사용해 메일을 작성하고 보내는 방법을 배웁니다.",
                    "/lesson/gmail",
                    "初級",
                    "5分",
                    Arrays.asList(
                        "Gmailを開く",
                        "作成ボタンを押す",
                        "宛先・件名・本文を入力する",
                        "送信ボタンを押す"
                    ),
                    Arrays.asList(
                        "Gmail을 연다",
                        "작성 버튼을 누른다",
                        "받는 사람, 제목, 내용을 입력한다",
                        "보내기 버튼을 누른다"
                    ),
                    new Quiz(
                        "Gmailでメールを書くとき、最初に押すボタンはどれですか？",
                        "Gmail에서 메일을 작성할 때 먼저 누르는 버튼은 무엇인가요?",
                        Arrays.asList("作成ボタン", "削除ボタン", "設定ボタン"),
                        Arrays.asList("작성 버튼", "삭제 버튼", "설정 버튼"),
                        0
                    )
                ),
                new Lesson(
                    "Googleマップで目的地を調べる",
                    "Google 지도로 목적지 찾기",
                    "Googleマップを使って目的地や経路を調べる方法を学びます。",
                    "Google 지도를 사용해 목적지와 경로를 찾는 방법을 배웁니다.",
                    "/lesson/google-map",
                    "初級",
                    "5分",
                    Arrays.asList(
                        "Googleマップを開く",
                        "検索欄に目的地を入力する",
                        "経路ボタンを押す",
                        "移動手段を選ぶ"
                    ),
                    Arrays.asList(
                        "Google 지도를 연다",
                        "검색창에 목적지를 입력한다",
                        "경로 버튼을 누른다",
                        "이동 수단을 선택한다"
                    ),
                    new Quiz(
                        "Googleマップで道順を調べるときに押すボタンはどれですか？",
                        "Google 지도에서 길찾기를 할 때 누르는 버튼은 무엇인가요?",
                        Arrays.asList("経路ボタン", "削除ボタン", "音量ボタン"),
                        Arrays.asList("경로 버튼", "삭제 버튼", "음량 버튼"),
                        0
                    )
                ),
                new Lesson(
                    "YouTubeで動画を見る",
                    "YouTube에서 동영상 보기",
                    "YouTubeで見たい動画を検索して再生する方法を学びます。",
                    "YouTube에서 보고 싶은 동영상을 검색하고 재생하는 방법을 배웁니다.",
                    "/lesson/youtube-watch",
                    "初級",
                    "5分",
                    Arrays.asList(
                        "YouTubeを開く",
                        "検索欄に見たい動画の言葉を入力する",
                        "動画を選んで再生する",
                        "音量や一時停止を操作する"
                    ),
                    Arrays.asList(
                        "YouTube를 연다",
                        "검색창에 보고 싶은 영상의 단어를 입력한다",
                        "동영상을 선택해 재생한다",
                        "음량과 일시정지를 조작한다"
                    ),
                    new Quiz(
                        "YouTubeで見たい動画を探すときに使う場所はどこですか？",
                        "YouTube에서 보고 싶은 영상을 찾을 때 사용하는 곳은 어디인가요?",
                        Arrays.asList("検索欄", "通話ボタン", "支払い画面"),
                        Arrays.asList("검색창", "통화 버튼", "결제 화면"),
                        0
                    )
                ),
                new Lesson(
                    "Google翻訳を使う",
                    "Google 번역 사용하기",
                    "Google翻訳を使って文章を翻訳する方法を学びます。",
                    "Google 번역을 사용해 문장을 번역하는 방법을 배웁니다.",
                    "/lesson/google-translate",
                    "初級",
                    "5分",
                    Arrays.asList(
                        "Google翻訳を開く",
                        "翻訳したい文章を入力する",
                        "翻訳する言語を選ぶ",
                        "翻訳結果を確認する"
                    ),
                    Arrays.asList(
                        "Google 번역을 연다",
                        "번역하고 싶은 문장을 입력한다",
                        "번역할 언어를 선택한다",
                        "번역 결과를 확인한다"
                    ),
                    new Quiz(
                        "Google翻訳で翻訳したい文章はどこに入力しますか？",
                        "Google 번역에서 번역하고 싶은 문장은 어디에 입력하나요?",
                        Arrays.asList("入力欄", "通話履歴", "カメラ設定"),
                        Arrays.asList("입력 칸", "통화 기록", "카메라 설정"),
                        0
                    )
                )
            )
        ));

        categories.add(new Category(
            "金融アプリ",
            "금융 앱",
            "決済アプリや銀行アプリを学べます。",
            "결제 앱이나 은행 앱 사용법을 배울 수 있습니다.",
            "/category/money",
            Arrays.asList(
                "安全なログイン方法を確認する",
                "銀行アプリの基本操作を学ぶ",
                "不審な通知や詐欺に注意する"
            ),
            Arrays.asList(
                "안전한 로그인 방법을 확인한다",
                "은행 앱의 기본 조작을 배운다",
                "수상한 알림이나 사기에 주의한다"
            ),
            Arrays.asList(
                new Lesson(
                    "決済アプリで支払いをする",
                    "결제 앱으로 결제하기",
                    "決済アプリを使って安全に支払いを行う方法を学びます。",
                    "결제 앱을 사용해 안전하게 결제하는 방법을 배웁니다.",
                    "/lesson/payment-app",
                    "初級",
                    "5分",
                    Arrays.asList(
                        "決済アプリを開く",
                        "支払い画面を表示する",
                        "金額や店舗名を確認する",
                        "内容を確認して支払いを完了する"
                    ),
                    Arrays.asList(
                        "결제 앱을 연다",
                        "결제 화면을 표시한다",
                        "금액과 가게 이름을 확인한다",
                        "내용을 확인하고 결제를 완료한다"
                    ),
                    new Quiz(
                        "決済前に確認することとして大切なのはどれですか？",
                        "결제 전에 확인해야 할 중요한 것은 무엇인가요?",
                        Arrays.asList("金額や店舗名", "壁紙の色", "音量の大きさ"),
                        Arrays.asList("금액과 가게 이름", "배경화면 색", "음량 크기"),
                        0
                    )
                ),
                new Lesson(
                    "SMBCアプリの基本操作",
                    "SMBC 앱 기본 조작",
                    "SMBCアプリで残高確認など基本的な操作を学びます。",
                    "SMBC 앱에서 잔액 확인 등 기본 조작을 배웁니다.",
                    "/lesson/smbc-app",
                    "初級",
                    "6分",
                    Arrays.asList(
                        "SMBCアプリを開く",
                        "ログイン画面を確認する",
                        "メニューから利用したい機能を選ぶ",
                        "表示内容を確認する"
                    ),
                    Arrays.asList(
                        "SMBC 앱을 연다",
                        "로그인 화면을 확인한다",
                        "메뉴에서 사용할 기능을 선택한다",
                        "표시된 내용을 확인한다"
                    ),
                    new Quiz(
                        "銀行アプリで機能を選ぶときに使うものはどれですか？",
                        "은행 앱에서 기능을 선택할 때 사용하는 것은 무엇인가요?",
                        Arrays.asList("メニュー", "カメラズーム", "着信音"),
                        Arrays.asList("메뉴", "카메라 줌", "벨소리"),
                        0
                    )
                ),
                new Lesson(
                    "ゆうちょ通帳アプリの基本操作",
                    "유초 통장 앱 기본 조작",
                    "ゆうちょ通帳アプリで情報を確認する基本操作を学びます。",
                    "유초 통장 앱에서 정보를 확인하는 기본 조작을 배웁니다.",
                    "/lesson/yucho-app",
                    "初級",
                    "6分",
                    Arrays.asList(
                        "ゆうちょ通帳アプリを開く",
                        "ログイン画面を確認する",
                        "確認したい項目を選ぶ",
                        "表示された内容を確認する"
                    ),
                    Arrays.asList(
                        "유초 통장 앱을 연다",
                        "로그인 화면을 확인한다",
                        "확인하고 싶은 항목을 선택한다",
                        "표시된 내용을 확인한다"
                    ),
                    new Quiz(
                        "ゆうちょ通帳アプリで情報を見る前に確認する画面はどれですか？",
                        "유초 통장 앱에서 정보를 보기 전에 확인하는 화면은 무엇인가요?",
                        Arrays.asList("ログイン画面", "動画再生画面", "地図画面"),
                        Arrays.asList("로그인 화면", "동영상 재생 화면", "지도 화면"),
                        0
                    )
                ),
                new Lesson(
                    "不審な通知を見分ける",
                    "수상한 알림 구별하기",
                    "金融アプリを安全に使うため、不審な通知を見分ける方法を学びます。",
                    "금융 앱을 안전하게 사용하기 위해 수상한 알림을 구별하는 방법을 배웁니다.",
                    "/lesson/money-safety",
                    "初級",
                    "7分",
                    Arrays.asList(
                        "通知の送信元を確認する",
                        "不自然な表現がないか確認する",
                        "URLをすぐに開かない",
                        "不安な場合は公式アプリや公式サイトから確認する"
                    ),
                    Arrays.asList(
                        "알림을 보낸 곳을 확인한다",
                        "어색한 표현이 있는지 확인한다",
                        "URL을 바로 열지 않는다",
                        "불안할 때는 공식 앱이나 공식 사이트에서 확인한다"
                    ),
                    new Quiz(
                        "不審な通知が届いたとき、すぐにしてはいけないことはどれですか？",
                        "수상한 알림이 왔을 때 바로 하면 안 되는 것은 무엇인가요?",
                        Arrays.asList("URLをすぐに開く", "送信元を確認する", "公式アプリから確認する"),
                        Arrays.asList("URL을 바로 연다", "보낸 곳을 확인한다", "공식 앱에서 확인한다"),
                        0
                    )
                )
            )
        ));

        return categories;
    }

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "ja") String lang, Model model) {
        model.addAttribute("categories", getCategories());
        model.addAttribute("lang", lang);
        return "index";
    }

    @GetMapping("/category/{id}")
    public String category(
            @PathVariable String id,
            @RequestParam(defaultValue = "ja") String lang,
            Model model) {

        for (Category category : getCategories()) {
            if (category.getUrl().equals("/category/" + id)) {
                model.addAttribute("category", category);
                model.addAttribute("lang", lang);

                List<Category> recommendations = new ArrayList<>();

                for (Category other : getCategories()) {
                    if (!other.getUrl().equals(category.getUrl())) {
                        recommendations.add(other);
                    }
                }

                model.addAttribute("recommendations", recommendations);
                return "category";
            }
        }

        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "ja") String lang,
            Model model) {

        List<Category> results = new ArrayList<>();

        for (Category category : getCategories()) {
            if (category.getTitleJa().contains(keyword)
                    || category.getTitleKo().contains(keyword)
                    || category.getDescriptionJa().contains(keyword)
                    || category.getDescriptionKo().contains(keyword)) {
                results.add(category);
            }
        }

        model.addAttribute("keyword", keyword);
        model.addAttribute("results", results);
        model.addAttribute("lang", lang);

        return "search";
    }

    @GetMapping("/lesson/{id}")
    public String lesson(
            @PathVariable String id,
            @RequestParam(defaultValue = "ja") String lang,
            Model model) {

        for (Category category : getCategories()) {
            for (Lesson lesson : category.getLessons()) {
                if (lesson.getUrl().equals("/lesson/" + id)) {
                    model.addAttribute("lesson", lesson);
                    model.addAttribute("category", category);
                    model.addAttribute("lang", lang);
                    return "lesson";
                }
            }
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(@RequestParam(defaultValue = "ja") String lang, Model model) {
        model.addAttribute("lang", lang);
        return "login";
    }

    @GetMapping("/register")
    public String register(@RequestParam(defaultValue = "ja") String lang, Model model) {
        model.addAttribute("lang", lang);
        return "register";
    }

    @GetMapping("/community")
    public String community(@RequestParam(defaultValue = "ja") String lang, Model model) {
        model.addAttribute("lang", lang);
        return "community";
    }

    @GetMapping("/diagnosis")
    public String diagnosis(@RequestParam(defaultValue = "ja") String lang, Model model) {
        model.addAttribute("lang", lang);
        return "diagnosis";
    }
    @GetMapping("/ai")
public String ai(@RequestParam(defaultValue = "ja") String lang, Model model) {
    model.addAttribute("lang", lang);
    return "ai";
}
@GetMapping("/fraud")
public String fraud(@RequestParam(defaultValue = "ja") String lang, Model model) {
    model.addAttribute("lang", lang);
    return "fraud";
}

}