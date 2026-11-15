const navigatorLang = document.documentElement.lang;
const navigatorInput = document.getElementById("aiInput");
const navigatorButton = document.getElementById("aiButton");
const navigatorResult = document.getElementById("aiResult");
const navigatorHistory = document.getElementById("aiHistory");
const voiceButton = document.getElementById("voiceButton");

const lessonCatalog = [
    ["/lesson/smartphone-power", "スマートフォンの電源を入れる", "Turn on a smartphone", ["電源", "起動", "スマホ", "power", "turn on"]],
    ["/lesson/smartphone-input", "文字を入力する", "Type text", ["文字", "入力", "キーボード", "type", "keyboard"]],
    ["/lesson/phone-call", "電話をかける", "Make a phone call", ["電話", "通話", "call", "phone"]],
    ["/lesson/camera-photo", "カメラで写真を撮る", "Take a photo", ["写真", "カメラ", "撮影", "photo", "camera"]],
    ["/lesson/gallery-view", "ギャラリーで写真を見る", "View photos", ["ギャラリー", "写真を見る", "gallery", "view photo"]],
    ["/lesson/line-message", "LINEでメッセージを送る", "Send a LINE message", ["line", "ライン", "メッセージ"]],
    ["/lesson/kakao-message", "KakaoTalkでメッセージを送る", "Send a KakaoTalk message", ["kakao", "カカオ", "카카오"]],
    ["/lesson/sns-privacy", "SNSの公開範囲を確認する", "Check SNS privacy", ["sns", "公開", "個人情報", "privacy"]],
    ["/lesson/google-search", "Google検索を使う", "Use Google Search", ["検索", "google", "調べる", "search"]],
    ["/lesson/gmail", "Gmailでメールを送る", "Send an email", ["メール", "mail", "gmail", "email"]],
    ["/lesson/google-map", "Googleマップで目的地を調べる", "Use Google Maps", ["道", "地図", "場所", "マップ", "map", "route"]],
    ["/lesson/youtube-watch", "YouTubeで動画を見る", "Watch YouTube", ["動画", "youtube", "ユーチューブ", "video"]],
    ["/lesson/google-translate", "Google翻訳を使う", "Use Google Translate", ["翻訳", "translate", "translation"]],
    ["/lesson/payment-app", "決済アプリで支払う", "Use a payment app", ["決済", "支払い", "payment", "pay"]],
    ["/lesson/smbc-app", "SMBCアプリの基本操作", "Use the SMBC app", ["smbc", "三井住友", "残高"]],
    ["/lesson/yucho-app", "ゆうちょ通帳アプリの基本操作", "Use the Japan Post Bank app", ["ゆうちょ", "通帳", "郵便局"]],
    ["/lesson/money-safety", "不審な通知を見分ける", "Identify suspicious messages", ["詐欺", "危険", "通知", "銀行", "怪しい", "scam", "fraud"]]
].map(item => ({ url: item[0], ja: item[1], en: item[2], keywords: item[3] }));

function normalizeNavigatorText(value) {
    return value.normalize("NFKC").toLocaleLowerCase().trim();
}

function saveNavigatorHistory(text) {
    let histories = [];
    try { histories = JSON.parse(localStorage.getItem("navigatorHistories")) || []; } catch (error) { histories = []; }
    histories.unshift({ text, date: new Date().toISOString() });
    localStorage.setItem("navigatorHistories", JSON.stringify(histories.slice(0, 30)));
}

function showNavigatorHistory() {
    if (!navigatorHistory) return;
    navigatorHistory.replaceChildren();
    let histories = [];
    try { histories = JSON.parse(localStorage.getItem("navigatorHistories")) || []; } catch (error) { histories = []; }
    if (histories.length === 0) {
        navigatorHistory.textContent = navigatorLang === "en" ? "No consultation history yet." : "相談履歴はまだありません。";
        return;
    }
    histories.forEach(function (history) {
        const item = document.createElement("div");
        item.className = "post-card";
        const strong = document.createElement("strong");
        strong.textContent = history.text;
        const date = document.createElement("small");
        date.textContent = new Date(history.date).toLocaleString();
        item.append(strong, document.createElement("br"), date);
        navigatorHistory.appendChild(item);
    });
}

function runNavigator() {
    const originalText = navigatorInput.value.trim();
    const text = normalizeNavigatorText(originalText);
    navigatorResult.replaceChildren();
    if (!text) {
        navigatorResult.textContent = navigatorLang === "en" ? "Describe what you want to do." : "困っていることを入力してください。";
        return;
    }
    saveNavigatorHistory(originalText);
    const matches = lessonCatalog.filter(lesson =>
        lesson.keywords.some(keyword => text.includes(normalizeNavigatorText(keyword))));
    if (matches.length === 0) {
        navigatorResult.textContent = navigatorLang === "en"
            ? "No matching lesson was found. Try a shorter phrase such as email, map, photo, or payment."
            : "一致するレッスンがありません。メール、地図、写真、支払いなど短い言葉でお試しください。";
    } else {
        matches.forEach(function (lesson) {
            const link = document.createElement("a");
            link.className = "card";
            link.href = lesson.url + "?lang=" + encodeURIComponent(navigatorLang);
            link.textContent = navigatorLang === "en" ? lesson.en : lesson.ja;
            navigatorResult.appendChild(link);
        });
    }
    showNavigatorHistory();
}

if (navigatorButton) navigatorButton.addEventListener("click", runNavigator);
if (voiceButton) voiceButton.addEventListener("click", function () {
    const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
    if (!SpeechRecognition) {
        alert(navigatorLang === "en" ? "Voice input is not supported by this browser." : "このブラウザは音声入力に対応していません。");
        return;
    }
    const recognition = new SpeechRecognition();
    recognition.lang = navigatorLang === "en" ? "en-US" : navigatorLang === "ko" ? "ko-KR" : "ja-JP";
    recognition.onresult = event => {
        navigatorInput.value = event.results[0][0].transcript;
        runNavigator();
    };
    recognition.onerror = () => alert(navigatorLang === "en" ? "Voice recognition failed." : "音声認識に失敗しました。");
    recognition.start();
});

showNavigatorHistory();
