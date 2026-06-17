const checkbox = document.getElementById("completeCheck");
const message = document.getElementById("completeMessage");
const lang = document.documentElement.lang;

if (checkbox) {
    const key = checkbox.dataset.key;

    if (localStorage.getItem(key) === "done") {
        checkbox.checked = true;

        if (message) {
            message.textContent =
                lang === "ko"
                    ? "학습이 완료되었습니다. 수고하셨습니다."
                    : "学習完了です。お疲れさまでした。";
        }
    }
    const history =
    JSON.parse(localStorage.getItem("learningHistory")) || [];

history.unshift({
    title: document.querySelector("h1").textContent,
    date: new Date().toLocaleString()
});

localStorage.setItem(
    "learningHistory",
    JSON.stringify(history)
);

    checkbox.addEventListener("change", function () {
        if (checkbox.checked) {
            localStorage.setItem(key, "done");

            if (message) {
                message.textContent =
                    lang === "ko"
                        ? "학습이 완료되었습니다. 수고하셨습니다."
                        : "学習完了です。お疲れさまでした。";
            }
        } else {
            localStorage.removeItem(key);

            if (message) {
                message.textContent = "";
            }
        }
    });
}

const progressText = document.getElementById("progressText");
const progressFill = document.getElementById("progressFill");
if (progressText) {
    const lessonKeys = [
    "/lesson/smartphone-power",
    "/lesson/smartphone-input",
    "/lesson/phone-call",
    "/lesson/camera-photo",
    "/lesson/gallery-view",
    "/lesson/line-message",
    "/lesson/kakao-message",
    "/lesson/sns-privacy",
    "/lesson/google-search",
    "/lesson/gmail",
    "/lesson/google-map",
    "/lesson/youtube-watch",
    "/lesson/google-translate",
    "/lesson/payment-app",
    "/lesson/smbc-app",
    "/lesson/yucho-app",
    "/lesson/money-safety"
];

    let completed = 0;

    for (const key of lessonKeys) {
        if (localStorage.getItem(key) === "done") {
            completed++;
        }
    }

progressText.textContent =
    lang === "ko"
        ? completed + " / " + lessonKeys.length + " 레슨 완료"
        : completed + " / " + lessonKeys.length + " レッスン完了";

const percent =
    (completed / lessonKeys.length) * 100;

if (progressFill) {
    progressFill.style.width = percent + "%";
}
}
const lessonStatuses = document.querySelectorAll(".lesson-status");

if (lessonStatuses) {
    lessonStatuses.forEach(function (status) {
        const key = status.dataset.key;

        if (localStorage.getItem(key) === "done") {
            status.textContent =
                lang === "ko"
                    ? "완료됨"
                    : "完了済み";
        }
    });
    const quizArea = document.getElementById("quizArea");
const quizMessage = document.getElementById("quizMessage");

if (quizArea) {
    const answer = quizArea.dataset.answer;
    const choices = document.querySelectorAll(".quiz-choice");

    choices.forEach(function (choice) {
        choice.addEventListener("click", function () {
            const selected = choice.dataset.index;

            if (selected === answer) {
    quizMessage.textContent =
        lang === "ko"
            ? "정답입니다! 학습 완료로 저장했습니다."
            : "正解です！学習完了として保存しました。";

    const completeCheck =
        document.getElementById("completeCheck");

    if (completeCheck) {
        completeCheck.checked = true;

        const key = completeCheck.dataset.key;
        localStorage.setItem(key, "done");
    }
} else {
                quizMessage.textContent =
                    lang === "ko"
                        ? "もう一度考えてみましょう。"
                        : "もう一度考えてみましょう。";
            }
        });
    });
}
const favoriteButton = document.getElementById("favoriteButton");

if (favoriteButton) {
    const favoriteKey = favoriteButton.dataset.key;

    const favorites =
        JSON.parse(localStorage.getItem("favoriteLessons")) || [];

    if (favorites.includes(favoriteKey)) {
        favoriteButton.textContent =
            lang === "ko"
                ? "즐겨찾기 해제"
                : "お気に入り解除";
    }

    favoriteButton.addEventListener("click", function () {
        const favorites =
            JSON.parse(localStorage.getItem("favoriteLessons")) || [];

        if (favorites.includes(favoriteKey)) {
            const newFavorites =
                favorites.filter(function (item) {
                    return item !== favoriteKey;
                });

            localStorage.setItem(
                "favoriteLessons",
                JSON.stringify(newFavorites)
            );

            favoriteButton.textContent =
                lang === "ko"
                    ? "즐겨찾기에 추가"
                    : "お気に入りに追加";

        } else {
            favorites.push(favoriteKey);

            localStorage.setItem(
                "favoriteLessons",
                JSON.stringify(favorites)
            );

            favoriteButton.textContent =
                lang === "ko"
                    ? "즐겨찾기 해제"
                    : "お気に入り解除";
        }
    });
}
const favoriteList = document.getElementById("favoriteList");

if (favoriteList) {
    const favorites =
        JSON.parse(localStorage.getItem("favoriteLessons")) || [];

    const lessonNames = {
        "/lesson/smartphone-power": {
            ja: "スマートフォンの電源を入れる",
            ko: "스마트폰 전원 켜기"
        },
        "/lesson/smartphone-input": {
            ja: "文字を入力する",
            ko: "문자 입력하기"
        },
        "/lesson/phone-call": {
            ja: "電話をかける",
            ko: "전화 걸기"
        },
        "/lesson/camera-photo": {
            ja: "カメラで写真を撮る",
            ko: "카메라로 사진 찍기"
        },
        "/lesson/gallery-view": {
            ja: "ギャラリーで写真を見る",
            ko: "갤러리에서 사진 보기"
        },
        "/lesson/line-message": {
            ja: "LINEでメッセージを送る",
            ko: "LINE으로 메시지 보내기"
        },
        "/lesson/kakao-message": {
            ja: "カカオトークでメッセージを送る",
            ko: "카카오톡으로 메시지 보내기"
        },
        "/lesson/sns-privacy": {
            ja: "SNSの公開範囲を確認する",
            ko: "SNS 공개 범위 확인하기"
        },
        "/lesson/google-search": {
            ja: "Google検索を使う",
            ko: "Google 검색 사용하기"
        },
        "/lesson/gmail": {
            ja: "Gmailでメールを送る",
            ko: "Gmail로 메일 보내기"
        },
        "/lesson/google-map": {
            ja: "Googleマップで目的地を調べる",
            ko: "Google 지도로 목적지 찾기"
        },
        "/lesson/youtube-watch": {
            ja: "YouTubeで動画を見る",
            ko: "YouTube에서 동영상 보기"
        },
        "/lesson/google-translate": {
            ja: "Google翻訳を使う",
            ko: "Google 번역 사용하기"
        },
        "/lesson/payment-app": {
            ja: "決済アプリで支払いをする",
            ko: "결제 앱으로 결제하기"
        },
        "/lesson/smbc-app": {
            ja: "SMBCアプリの基本操作",
            ko: "SMBC 앱 기본 조작"
        },
        "/lesson/yucho-app": {
            ja: "ゆうちょ通帳アプリの基本操作",
            ko: "유초 통장 앱 기본 조작"
        },
        "/lesson/money-safety": {
            ja: "不審な通知を見分ける",
            ko: "수상한 알림 구별하기"
        }
    };

    if (favorites.length > 0) {
        favoriteList.innerHTML = "";

        favorites.forEach(function (key) {
            const lesson = lessonNames[key];

            if (lesson) {
                const a = document.createElement("a");
                a.className = "card";
                a.href = key + "?lang=" + lang;
                a.textContent =
                    lang === "ko"
                        ? lesson.ko
                        : lesson.ja;

                favoriteList.appendChild(a);
            }
        });
    }}
    const historyList =
    document.getElementById("historyList");

if (historyList) {

    const history =
        JSON.parse(
            localStorage.getItem("learningHistory")
        ) || [];

    if (history.length > 0) {

        historyList.innerHTML = "";

        history.forEach(function (item) {

            const div =
                document.createElement("div");

            div.className = "card";

            div.innerHTML =
                "<strong>" +
                item.title +
                "</strong><br>" +
                item.date;

            historyList.appendChild(div);

        });

    }

}
const speakButton = document.getElementById("speakButton");
const stopSpeakButton = document.getElementById("stopSpeakButton");

if (speakButton) {
    speakButton.addEventListener("click", function () {
        const title = document.querySelector("h1").textContent;

        const steps = Array.from(document.querySelectorAll("ol li"))
            .map(function (li) {
                return li.textContent;
            })
            .join("。");

        const text = title + "。" + steps;

        const speech = new SpeechSynthesisUtterance(text);

        speech.lang =
            lang === "ko"
                ? "ko-KR"
                : "ja-JP";

        speech.rate = 0.9;

        speechSynthesis.cancel();
        speechSynthesis.speak(speech);
    });
}

if (stopSpeakButton) {
    stopSpeakButton.addEventListener("click", function () {
        speechSynthesis.cancel();
    });
}
}
