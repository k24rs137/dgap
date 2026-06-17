const aiLang = document.documentElement.lang;

const aiInput = document.getElementById("aiInput");
const aiButton = document.getElementById("aiButton");
const aiResult = document.getElementById("aiResult");
const aiHistory = document.getElementById("aiHistory");
const voiceButton =
    document.getElementById("voiceButton");
const lessons = [
    {
        keywords: ["メール", "mail", "gmail", "メッセージ", "메일"],
        titleJa: "Gmailでメールを送る",
        titleKo: "Gmail로 메일 보내기",
        url: "/lesson/gmail"
    },
    {
        keywords: ["道", "地図", "場所", "マップ", "길", "지도", "장소"],
        titleJa: "Googleマップで目的地を調べる",
        titleKo: "Google 지도로 목적지 찾기",
        url: "/lesson/google-map"
    },
    {
        keywords: ["写真", "カメラ", "撮る", "사진", "카메라"],
        titleJa: "カメラで写真を撮る",
        titleKo: "카메라로 사진 찍기",
        url: "/lesson/camera-photo"
    },
    {
        keywords: ["電話", "通話", "전화", "통화"],
        titleJa: "電話をかける",
        titleKo: "전화 걸기",
        url: "/lesson/phone-call"
    },
    {
        keywords: ["line", "ライン", "LINE"],
        titleJa: "LINEでメッセージを送る",
        titleKo: "LINE으로 메시지 보내기",
        url: "/lesson/line-message"
    },
    {
        keywords: ["詐欺", "危険", "通知", "銀行", "사기", "위험", "알림", "은행"],
        titleJa: "不審な通知を見分ける",
        titleKo: "수상한 알림 구별하기",
        url: "/lesson/money-safety"
    },
    {
        keywords: ["翻訳", "translate", "번역"],
        titleJa: "Google翻訳を使う",
        titleKo: "Google 번역 사용하기",
        url: "/lesson/google-translate"
    },
    {
        keywords: ["動画", "youtube", "ユーチューブ", "동영상"],
        titleJa: "YouTubeで動画を見る",
        titleKo: "YouTube에서 동영상 보기",
        url: "/lesson/youtube-watch"
    }
];

if (aiButton) {
    aiButton.addEventListener("click", function () {
        const text = aiInput.value.toLowerCase();
        const histories =
    JSON.parse(localStorage.getItem("aiHistories")) || [];

histories.unshift({
    text: aiInput.value,
    date: new Date().toLocaleString()
});

localStorage.setItem(
    "aiHistories",
    JSON.stringify(histories)
);

        aiResult.innerHTML = "";

        if (!text) {
            aiResult.textContent =
                aiLang === "ko"
                    ? "상담 내용을 입력해주세요."
                    : "相談内容を入力してください。";
            return;
        }

        const matches = lessons.filter(function (lesson) {
            return lesson.keywords.some(function (keyword) {
                return text.includes(keyword.toLowerCase());
            });
        });

        if (matches.length === 0) {
            aiResult.textContent =
                aiLang === "ko"
                    ? "관련 레슨을 찾지 못했습니다. 다른 말로 입력해보세요."
                    : "関連するレッスンが見つかりませんでした。別の言葉で入力してみてください。";
            return;
        }

        matches.forEach(function (lesson) {
            const a = document.createElement("a");
            a.className = "card";
            a.href = lesson.url + "?lang=" + aiLang;
            a.textContent =
                aiLang === "ko"
                    ? lesson.titleKo
                    : lesson.titleJa;

            aiResult.appendChild(a);
        });
    });
    function showAiHistory() {
    if (!aiHistory) {
        return;
    }

    const histories =
        JSON.parse(localStorage.getItem("aiHistories")) || [];

    aiHistory.innerHTML = "";

    if (histories.length === 0) {
        aiHistory.textContent =
            aiLang === "ko"
                ? "아직 상담 기록이 없습니다."
                : "まだ相談履歴はありません。";
        return;
    }

    histories.forEach(function (history) {
        const div = document.createElement("div");
        div.className = "post-card";

        div.innerHTML =
            "<strong>" + history.text + "</strong><br>" +
            "<small>" + history.date + "</small>";

        aiHistory.appendChild(div);
    });
}

showAiHistory();
if (voiceButton) {

    voiceButton.addEventListener(
        "click",
        function () {

            const SpeechRecognition =
                window.SpeechRecognition
                || window.webkitSpeechRecognition;

            if (!SpeechRecognition) {

                alert(
                    aiLang === "ko"
                        ? "음성 입력을 지원하지 않는 브라우저입니다."
                        : "このブラウザは音声入力に対応していません。"
                );

                return;
            }

            const recognition =
                new SpeechRecognition();

            recognition.lang =
                aiLang === "ko"
                    ? "ko-KR"
                    : "ja-JP";

            recognition.start();

            recognition.onresult =
    function(event) {

        const text =
            event.results[0][0].transcript;

        aiInput.value = text;

        aiButton.click();
    };

            recognition.onerror =
                function() {

                    alert(
                        aiLang === "ko"
                            ? "음성 인식에 실패했습니다."
                            : "音声認識に失敗しました。"
                    );
                };
        }
    );
}
}
