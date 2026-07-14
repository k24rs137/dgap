const checkbox = document.getElementById("completeCheck");
const message = document.getElementById("completeMessage");
const lang = getDgapCurrentLang();

function getDgapCurrentLang() {
    const params = new URLSearchParams(window.location.search);
    const urlLang = params.get("lang");

    if (["ja", "ko", "en", "hira"].includes(urlLang)) {
        return urlLang;
    }

    const savedLang = localStorage.getItem("dgapLanguage");

    if (["ja", "ko", "en", "hira"].includes(savedLang)) {
        return savedLang;
    }

    return document.documentElement.lang || "ja";
}

function getDgapCompleteMessage() {
    if (lang === "ko") {
        return "학습이 완료되었습니다. 수고하셨습니다.";
    }

    if (lang === "en") {
        return "Learning completed. Great job!";
    }

    if (lang === "hira") {
        return "がくしゅうかんりょうです。おつかれさまでした。";
    }

    return "学習完了です。お疲れさまでした。";
}

function getDgapProgressText(completed, total) {
    if (lang === "ko") {
        return completed + " / " + total + " 레슨 완료";
    }

    if (lang === "en") {
        return completed + " / " + total + " lessons completed";
    }

    if (lang === "hira") {
        return completed + " / " + total + " れっすん かんりょう";
    }

    return completed + " / " + total + " レッスン完了";
}

function getDgapDoneStatusText() {
    if (lang === "ko") {
        return "완료됨";
    }

    if (lang === "en") {
        return "Completed";
    }

    if (lang === "hira") {
        return "かんりょうずみ";
    }

    return "完了済み";
}

function getDgapFavoriteAddText() {
    if (lang === "ko") {
        return "즐겨찾기에 추가";
    }

    if (lang === "en") {
        return "Add to favorites";
    }

    if (lang === "hira") {
        return "おきにいりについか";
    }

    return "お気に入りに追加";
}

function getDgapFavoriteRemoveText() {
    if (lang === "ko") {
        return "즐겨찾기 해제";
    }

    if (lang === "en") {
        return "Remove from favorites";
    }

    if (lang === "hira") {
        return "おきにいりをはずす";
    }

    return "お気に入り解除";
}

if (checkbox) {
    const key = checkbox.dataset.key;

    if (localStorage.getItem(key) === "done") {
        checkbox.checked = true;

        if (message) {
            message.textContent = getDgapCompleteMessage();
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
                message.textContent = getDgapCompleteMessage();
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

progressText.textContent = getDgapProgressText(completed, lessonKeys.length);

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
            status.textContent = getDgapDoneStatusText();
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
            : lang === "en"
            ? "Correct! Saved as learning completed."
            : lang === "ja"
            ? "正解です！学習完了として保存しました。"
            : "せいかいです！がくしゅうかんりょうとしてほぞんしました。";

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
                        ? "다시 한번 생각해 보세요."
                        : lang === "en"
                        ? "Please think again."
                        : lang === "ja"
                        ? "もう一度考えてみましょう。"
                        : "もういちどかんがえてみましょう。";
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
        favoriteButton.textContent = getDgapFavoriteRemoveText();
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

            favoriteButton.textContent = getDgapFavoriteAddText();

        } else {
            favorites.push(favoriteKey);

            localStorage.setItem(
                "favoriteLessons",
                JSON.stringify(favorites)
            );

            favoriteButton.textContent = getDgapFavoriteRemoveText();
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
document.addEventListener("DOMContentLoaded", () => {
    initializeDgapScrollReveal();
    initializeDgapSmoothScroll();
    initializeDgapRippleButtons();
    initializeDgapMobileMenu();
});


function initializeDgapScrollReveal() {
    const revealElements =
        document.querySelectorAll(".reveal");

    if (revealElements.length === 0) {
        return;
    }

    const reducedMotion =
        window.matchMedia(
            "(prefers-reduced-motion: reduce)"
        ).matches;

    if (reducedMotion) {
        revealElements.forEach((element) => {
            element.classList.add("is-visible");
        });

        return;
    }

    const observer =
        new IntersectionObserver(
            (entries, currentObserver) => {
                entries.forEach((entry) => {
                    if (!entry.isIntersecting) {
                        return;
                    }

                    entry.target.classList.add(
                        "is-visible"
                    );

                    currentObserver.unobserve(
                        entry.target
                    );
                });
            },
            {
                threshold: 0.15,
                rootMargin:
                    "0px 0px -50px 0px"
            }
        );

    revealElements.forEach((element) => {
        observer.observe(element);
    });
}


function initializeDgapSmoothScroll() {
    const scrollButtons =
        document.querySelectorAll(
            "[data-scroll-target]"
        );

    scrollButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const selector =
                button.dataset.scrollTarget;

            if (!selector) {
                return;
            }

            const target =
                document.querySelector(selector);

            if (!target) {
                return;
            }

            target.scrollIntoView({
                behavior: "smooth",
                block: "start"
            });
        });
    });
}


function initializeDgapRippleButtons() {
    const rippleButtons =
        document.querySelectorAll(
            "[data-ripple]"
        );

    rippleButtons.forEach((button) => {
        button.addEventListener(
            "pointerdown",
            (event) => {
                const oldRipple =
                    button.querySelector(
                        ".button-ripple"
                    );

                if (oldRipple) {
                    oldRipple.remove();
                }

                const rectangle =
                    button.getBoundingClientRect();

                const ripple =
                    document.createElement("span");

                ripple.className =
                    "button-ripple";

                ripple.style.left =
                    `${event.clientX - rectangle.left}px`;

                ripple.style.top =
                    `${event.clientY - rectangle.top}px`;

                button.appendChild(ripple);

                window.setTimeout(() => {
                    ripple.remove();
                }, 700);
            }
        );
    });
}


let currentDgapLanguage = "ja";

function initializeDgapMobileMenu() {
    const menuButton =
        document.getElementById(
            "dgapMenuButton"
        );

    const mobileMenu =
        document.getElementById(
            "dgapMobileMenu"
        );

    if (!menuButton || !mobileMenu) {
        return;
    }

    menuButton.addEventListener("click", () => {
        const isOpen =
            mobileMenu.classList.toggle(
                "is-open"
            );

        menuButton.setAttribute(
            "aria-expanded",
            String(isOpen)
        );

        const translation =
            dgapHomeTranslations[
                currentDgapLanguage
            ] || dgapHomeTranslations.ja;

        menuButton.textContent =
            isOpen
                ? translation.close
                : translation.menu;
    });

    mobileMenu
        .querySelectorAll("a")
        .forEach((link) => {
            link.addEventListener(
                "click",
                () => {
                    mobileMenu.classList.remove(
                        "is-open"
                    );

                    menuButton.setAttribute(
                        "aria-expanded",
                        "false"
                    );

                    const translation =
                        dgapHomeTranslations[
                            currentDgapLanguage
                        ] || dgapHomeTranslations.ja;

                    menuButton.textContent =
                        translation.menu;
                }
            );
        });
}
document.addEventListener("DOMContentLoaded", () => {
    initializeDgapLanguageSwitch();
    initializeDgapFontSizeSwitch();
});


const dgapHomeTranslations = {
    ja: {
        login: "ログイン",
        navLearning: "学習カテゴリ",
        navDiagnosis: "困りごと診断",
        navAi: "AI相談",
        navCommunity: "コミュニティ",
        search: "検索",
        desktopNavAriaLabel: "メインメニュー",
        searchPageHeroLabel: "SEARCH",
        searchPageHeroTitle: "学びたい内容を検索する",
        searchPageHeroDescription: "スマートフォン、SNS、Googleサービス、金融アプリなど、困っていることや学びたい内容を入力してください。",
        searchInputLabel: "検索ワード",
        searchInputPlaceholder: "例：スマホ、LINE、地図、メール、決済",
        searchButton: "検索する",
        searchRecommendedLabel: "おすすめ：",
        searchKeyword1: "スマホ",
        searchKeyword2: "LINE",
        searchKeyword3: "地図",
        searchKeyword4: "メール",
        searchKeyword5: "決済",
        searchKeyword6: "詐欺",
        searchResultHeadingLabel: "RESULT",
        searchResultHeading: "検索結果",
        searchCardType: "カテゴリー",
        searchCategory1Title: "スマートフォン操作",
        searchCategory1Text: "電話、カメラ、文字入力、画面操作など、スマートフォンの基本操作を学べます。",
        searchCategory1Tags: "スマホ / 電話 / カメラ / 文字入力",
        searchCategory2Title: "SNS・メッセージ",
        searchCategory2Text: "LINEやカカオトークなど、家族や友人と連絡を取るための使い方を学べます。",
        searchCategory2Tags: "LINE / カカオトーク / SNS / メッセージ",
        searchCategory3Title: "Googleサービス",
        searchCategory3Text: "Google検索、Gmail、Googleマップ、翻訳など、日常で役立つサービスを学べます。",
        searchCategory3Tags: "Google / Gmail / 地図 / 検索 / 翻訳",
        searchCategory4Title: "金融・決済アプリ",
        searchCategory4Text: "決済アプリや銀行アプリを安全に使うための基本を学べます。",
        searchCategory4Tags: "決済 / 銀行 / 金融 / 安全",
        searchNoResultTitle: "該当するカテゴリが見つかりませんでした",
        searchNoResultDescription: "別の言葉で検索してみてください。例：スマホ、LINE、地図、メール、決済、詐欺",
        searchSupportTitle: "何を検索すればよいか分からない場合",
        searchSupportText: "困りごと診断を使うと、今の状況に合った学習内容を探しやすくなります。",
        searchSupportButton: "困りごと診断を使う",
        backToTop: "トップへ戻る",
        searchWordPrefix: "検索ワード：",
        searchWordEmpty: "キーワードを入力すると、関連するカテゴリを探せます。",
        menu: "メニュー",
        close: "閉じる",
        fontSizeLabel: "文字サイズ",
        fontSizeNormal: "標準",
        fontSizeLarge: "大",
        fontSizeXlarge: "特大",

        heroLabel: "DIGITAL LEARNING SERVICE",
        heroTitle: "デジタルの「分からない」を、<br>一つずつ「できる」に。",
        heroDescription:
            "D-Gapは、スマートフォン、SNS、メール、地図、金融アプリなどを、やさしい手順で学べるデジタル学習サービスです。老若男女が安心して使えるように、見やすさ・分かりやすさ・操作しやすさを大切にしています。",
        startLearning: "学習を始める",
        diagnosisButton: "困りごとを診断する",

        support1: "大きく読みやすい文字",
        support2: "音声読み上げ対応",
        support3: "日本語・韓国語対応",
        support4: "スマホでも使いやすい",

        demoTitle: "今日のおすすめ学習",
        demoCard1Title: "スマートフォンの基本",
        demoCard1Text: "画面操作、電話、カメラ、文字入力を学びます。",
        demoCard2Title: "地図で経路を検索",
        demoCard2Text: "目的地までの行き方を調べます。",
        demoCard3Title: "詐欺メール対策",
        demoCard3Text: "危険なメールの特徴を確認できます。",
        progressLabel: "学習の進み具合",
        scrollDown: "下へスクロール",

        learningLabel: "LEARNING",
        learningTitle: "学びたい内容を選ぶ",
        learningText:
            "目的に合ったカテゴリから、すぐに学習を始められます。はじめて使う人でも迷いにくいように、身近な操作から順番に学べます。",

        category1Title: "スマートフォン操作",
        category1Text:
            "電話、カメラ、文字入力、画面操作など、スマートフォンの基本を学びます。",
        category2Title: "SNS・メッセージ",
        category2Text:
            "LINEやカカオトークなど、家族や友人とつながるための使い方を学びます。",
        category3Title: "Googleサービス",
        category3Text:
            "Gmail、Googleマップ、検索、翻訳など、日常で役立つサービスを学びます。",
        category4Title: "金融・決済アプリ",
        category4Text:
            "決済アプリや金融アプリを安全に使うための基本を学びます。",

        featuresLabel: "FEATURES",
        featuresTitle: "誰でも安心して使える工夫",
        featuresText:
            "D-Gapは、若い人だけでなく、高齢者やデジタル機器に不慣れな人にも使いやすい画面を目指しています。",
        feature1Title: "見やすい画面設計",
        feature1Text:
            "大きめの文字、十分な余白、分かりやすいボタン配置により、初めて使う人でも迷いにくい画面にしています。",
        feature2Title: "音声でサポート",
        feature2Text:
            "文章の読み上げや音声入力に対応し、文字を読むことや入力することが苦手な人も学習できます。",
        feature3Title: "進捗を確認できる",
        feature3Text:
            "学習完了やお気に入り機能によって、自分がどこまで学んだかを確認しながら進められます。",

        supportLabel: "SUPPORT",
        supportTitle: "困ったときも、すぐに次の行動が分かる",
        supportText:
            "何から学べばよいか分からない場合は、困りごと診断やAI相談から始められます。入力した内容に合わせて、必要な学習につながるように設計しています。",
        useDiagnosis: "困りごと診断を使う",
        useAi: "AI相談を使う",
        useFraud: "詐欺メール診断",

        step1Title: "困っていることを選ぶ",
        step1Text: "スマホ操作、SNS、地図、メールなどから選択できます。",
        step2Title: "おすすめの学習を見る",
        step2Text: "必要なレッスンへすぐに移動できます。",
        step3Title: "自分のペースで練習する",
        step3Text: "何度でも確認しながら、少しずつ学習できます。",

        ctaLabel: "START",
        ctaTitle: "今日からデジタル学習を始めよう",
        ctaText:
            "分からないことを、一つずつ一緒に解決します。まずは無料登録して、学習履歴を残しながら進めてみましょう。",
        registerFree: "無料で会員登録する",
        footerText: "デジタルの不安を、学びと安心に変えるサービス。"
    },

    ko: {
        login: "로그인",
        navLearning: "학습 카테고리",
        navDiagnosis: "문제 진단",
        navAi: "AI 상담",
        navCommunity: "커뮤니티",
        search: "검색",
        desktopNavAriaLabel: "메인 메뉴",
        searchPageHeroLabel: "SEARCH",
        searchPageHeroTitle: "배우고 싶은 내용을 검색하세요",
        searchPageHeroDescription: "스마트폰, SNS, Google 서비스, 금융 앱 등 궁금한 점이나 배우고 싶은 내용을 입력하세요.",
        searchInputLabel: "검색어",
        searchInputPlaceholder: "예: 스마트폰, LINE, 지도, 메일, 결제",
        searchButton: "검색하기",
        searchRecommendedLabel: "추천:",
        searchKeyword1: "스마트폰",
        searchKeyword2: "LINE",
        searchKeyword3: "지도",
        searchKeyword4: "메일",
        searchKeyword5: "결제",
        searchKeyword6: "사기",
        searchResultHeadingLabel: "RESULT",
        searchResultHeading: "검색 결과",
        searchCardType: "카테고리",
        searchCategory1Text: "전화, 카메라, 문자 입력, 화면 조작 등 스마트폰 기본 조작을 배울 수 있습니다.",
        searchCategory1Tags: "스마트폰 / 전화 / 카메라 / 문자 입력",
        searchCategory2Title: "SNS・메시지",
        searchCategory2Text: "LINE이나 카카오톡 등 가족과 친구와 연결되는 방법을 배웁니다.",
        searchCategory2Tags: "LINE / 카카오톡 / SNS / 메시지",
        searchCategory3Title: "Google 서비스",
        searchCategory3Text: "Google 검색, Gmail, Google 지도, 번역 등 일상에 도움이 되는 서비스를 배웁니다.",
        searchCategory3Tags: "Google / Gmail / 지도 / 검색 / 번역",
        searchCategory4Title: "금융・결제 앱",
        searchCategory4Text: "결제 앱과 은행 앱을 안전하게 사용하는 기본을 배웁니다.",
        searchCategory4Tags: "결제 / 은행 / 금융 / 안전",
        searchNoResultTitle: "일치하는 카테고리가 없습니다",
        searchNoResultDescription: "다른 단어로 검색해 보세요. 예: 스마트폰, LINE, 지도, 메일, 결제, 사기",
        searchSupportTitle: "무엇을 검색해야 할지 모르겠다면",
        searchSupportText: "문제 진단을 사용하면 현재 상황에 맞는 학습 내용을 찾기 쉬워집니다.",
        searchSupportButton: "문제 진단 사용하기",
        backToTop: "메인으로 돌아가기",
        searchPageTitle: "검색 | D-Gap",
        searchWordPrefix: "검색어：",
        searchWordCountSuffix: " {count}건",
        searchWordEmpty: "키워드를 입력하면 관련 카테고리를 찾을 수 있습니다.",
        searchKeywordsAriaLabel: "추천 검색어",
        menu: "메뉴",
        close: "닫기",
        fontSizeLabel: "글자 크기",
        fontSizeNormal: "기본",
        fontSizeLarge: "크게",
        fontSizeXlarge: "매우 크게",

        heroLabel: "DIGITAL LEARNING SERVICE",
        heroTitle: "디지털의 ‘모르겠어요’를<br>하나씩 ‘할 수 있어요’로.",
        heroDescription:
            "D-Gap은 스마트폰, SNS, 메일, 지도, 금융 앱 등을 쉬운 단계로 배울 수 있는 디지털 학습 서비스입니다. 누구나 안심하고 사용할 수 있도록 보기 쉬움, 이해하기 쉬움, 조작하기 쉬움을 중요하게 생각합니다.",
        startLearning: "학습 시작하기",
        diagnosisButton: "문제 진단하기",

        support1: "크고 읽기 쉬운 글자",
        support2: "음성 읽기 지원",
        support3: "일본어・한국어 지원",
        support4: "스마트폰에서도 사용하기 쉬움",

        demoTitle: "오늘의 추천 학습",
        demoCard1Title: "스마트폰 기본",
        demoCard1Text: "화면 조작, 전화, 카메라, 문자 입력을 배웁니다.",
        demoCard2Title: "지도에서 경로 검색",
        demoCard2Text: "목적지까지 가는 방법을 알아봅니다.",
        demoCard3Title: "사기 메일 대책",
        demoCard3Text: "위험한 메일의 특징을 확인할 수 있습니다.",
        progressLabel: "학습 진행률",
        scrollDown: "아래로 스크롤",

        learningLabel: "LEARNING",
        learningTitle: "배우고 싶은 내용을 선택하세요",
        learningText:
            "목적에 맞는 카테고리에서 바로 학습을 시작할 수 있습니다. 처음 사용하는 사람도 헤매지 않도록 익숙한 조작부터 차례대로 배울 수 있습니다.",

        category1Title: "스마트폰 조작",
        category1Text:
            "전화, 카메라, 문자 입력, 화면 조작 등 스마트폰의 기본을 배웁니다.",
        category2Title: "SNS・메시지",
        category2Text:
            "LINE이나 카카오톡 등 가족과 친구와 연결되는 방법을 배웁니다.",
        category3Title: "Google 서비스",
        category3Text:
            "Gmail, Google 지도, 검색, 번역 등 일상에 도움이 되는 서비스를 배웁니다.",
        category4Title: "금융・결제 앱",
        category4Text:
            "결제 앱과 금융 앱을 안전하게 사용하는 기본을 배웁니다.",

        featuresLabel: "FEATURES",
        featuresTitle: "누구나 안심하고 사용할 수 있는 기능",
        featuresText:
            "D-Gap은 젊은 사람뿐 아니라 고령자와 디지털 기기에 익숙하지 않은 사람도 사용하기 쉬운 화면을 목표로 합니다.",
        feature1Title: "보기 쉬운 화면 설계",
        feature1Text:
            "큰 글자, 충분한 여백, 알기 쉬운 버튼 배치로 처음 사용하는 사람도 헤매지 않도록 했습니다.",
        feature2Title: "음성 지원",
        feature2Text:
            "문장 읽기와 음성 입력을 지원하여 글자를 읽거나 입력하기 어려운 사람도 학습할 수 있습니다.",
        feature3Title: "진행 상황 확인",
        feature3Text:
            "학습 완료와 즐겨찾기 기능으로 어디까지 배웠는지 확인하며 진행할 수 있습니다.",

        supportLabel: "SUPPORT",
        supportTitle: "곤란할 때도 다음 행동을 바로 알 수 있습니다",
        supportText:
            "무엇부터 배워야 할지 모를 때는 문제 진단이나 AI 상담부터 시작할 수 있습니다. 입력한 내용에 맞춰 필요한 학습으로 이어지도록 설계했습니다.",
        useDiagnosis: "문제 진단 사용하기",
        useAi: "AI 상담 사용하기",
        useFraud: "사기 메일 진단",

        step1Title: "곤란한 내용을 선택",
        step1Text: "스마트폰 조작, SNS, 지도, 메일 등에서 선택할 수 있습니다.",
        step2Title: "추천 학습 보기",
        step2Text: "필요한 레슨으로 바로 이동할 수 있습니다.",
        step3Title: "자신의 속도로 연습",
        step3Text: "여러 번 확인하면서 조금씩 배울 수 있습니다.",

        ctaLabel: "START",
        ctaTitle: "오늘부터 디지털 학습을 시작하세요",
        ctaText:
            "모르는 것을 하나씩 함께 해결합니다. 먼저 무료로 가입하고 학습 기록을 남기며 진행해 보세요.",
        registerFree: "무료로 회원가입",
        footerText: "디지털의 불안을 배움과 안심으로 바꾸는 서비스."
    },

    en: {
        login: "Log in",
        navLearning: "Learning",
        navDiagnosis: "Trouble check",
        navAi: "AI support",
        navCommunity: "Community",
        search: "Search",
        desktopNavAriaLabel: "Main menu",
        searchPageHeroLabel: "SEARCH",
        searchPageHeroTitle: "Search what you want to learn",
        searchPageHeroDescription: "Enter what you want to learn about smartphones, social media, Google services, or finance apps.",
        searchInputLabel: "Keyword",
        searchInputPlaceholder: "Ex: smartphone, LINE, maps, email, payment",
        searchButton: "Search",
        searchRecommendedLabel: "Recommended:",
        searchKeyword1: "Smartphone",
        searchKeyword2: "LINE",
        searchKeyword3: "Maps",
        searchKeyword4: "Email",
        searchKeyword5: "Payment",
        searchKeyword6: "Scam",
        searchResultHeadingLabel: "RESULT",
        searchResultHeading: "Search results",
        searchCardType: "CATEGORY",
        searchCategory1Title: "Smartphone operation",
        searchCategory1Text: "Learn basic smartphone operation such as calls, camera, typing, and screen control.",
        searchCategory1Tags: "Smartphone / Calls / Camera / Typing",
        searchCategory2Title: "SNS and messages",
        searchCategory2Text: "Learn how to use LINE, KakaoTalk, and messaging tools to stay connected.",
        searchCategory2Tags: "LINE / KakaoTalk / SNS / Messages",
        searchCategory3Title: "Google services",
        searchCategory3Text: "Learn useful daily services such as Google Search, Gmail, Maps, and Translate.",
        searchCategory3Tags: "Google / Gmail / Maps / Search / Translate",
        searchCategory4Title: "Finance and payment apps",
        searchCategory4Text: "Learn the basics of using payment and finance apps safely.",
        searchCategory4Tags: "Payment / Bank / Finance / Safety",
        searchNoResultTitle: "No matching categories found",
        searchNoResultDescription: "Try searching with different words. Eg: smartphone, LINE, maps, email, payment, scam",
        searchSupportTitle: "Not sure what to search for?",
        searchSupportText: "Use the trouble check to find learning content that fits your situation.",
        searchSupportButton: "Use trouble check",
        backToTop: "Back to top",
        searchPageTitle: "Search | D-Gap",
        searchWordPrefix: "Search keyword: ",
        searchWordCountSuffix: " ({count} items)",
        searchWordEmpty: "Enter a keyword to find related categories.",
        searchKeywordsAriaLabel: "Recommended search keywords",
        menu: "Menu",
        close: "Close",
        fontSizeLabel: "Font size",
        fontSizeNormal: "Normal",
        fontSizeLarge: "Large",
        fontSizeXlarge: "Extra large",

        heroLabel: "DIGITAL LEARNING SERVICE",
        heroTitle: "Turn digital confusion<br>into confidence, step by step.",
        heroDescription:
            "D-Gap helps people learn smartphones, social media, email, maps, and finance apps through simple step-by-step lessons. It is designed to be easy to read, easy to understand, and easy to use for everyone.",
        startLearning: "Start learning",
        diagnosisButton: "Check your trouble",

        support1: "Large readable text",
        support2: "Voice reading support",
        support3: "Japanese and Korean support",
        support4: "Easy to use on smartphones",

        demoTitle: "Recommended lesson today",
        demoCard1Title: "Smartphone basics",
        demoCard1Text: "Learn screen operation, calls, camera, and typing.",
        demoCard2Title: "Search routes on maps",
        demoCard2Text: "Find how to get to your destination.",
        demoCard3Title: "Scam email safety",
        demoCard3Text: "Check common signs of dangerous emails.",
        progressLabel: "Learning progress",
        scrollDown: "Scroll down",

        learningLabel: "LEARNING",
        learningTitle: "Choose what you want to learn",
        learningText:
            "You can start learning from the category that matches your goal. Lessons begin with familiar everyday actions so first-time users can follow easily.",

        category1Title: "Smartphone use",
        category1Text:
            "Learn basic smartphone skills such as calls, camera, typing, and screen operation.",
        category2Title: "SNS and messages",
        category2Text:
            "Learn how to use LINE, KakaoTalk, and messaging tools to connect with family and friends.",
        category3Title: "Google services",
        category3Text:
            "Learn useful daily services such as Gmail, Google Maps, Search, and Translate.",
        category4Title: "Finance and payment apps",
        category4Text:
            "Learn the basics of using payment and finance apps safely.",

        featuresLabel: "FEATURES",
        featuresTitle: "Designed for safe and easy use",
        featuresText:
            "D-Gap aims to be easy to use not only for young people, but also for older adults and people unfamiliar with digital devices.",
        feature1Title: "Readable screen design",
        feature1Text:
            "Large text, enough spacing, and clear button placement help first-time users navigate without confusion.",
        feature2Title: "Voice support",
        feature2Text:
            "Text-to-speech and voice input help users who find reading or typing difficult.",
        feature3Title: "Progress tracking",
        feature3Text:
            "Lesson completion and favorite features help users check what they have learned.",

        supportLabel: "SUPPORT",
        supportTitle: "Know what to do next when you need help",
        supportText:
            "If you do not know where to start, you can use the trouble check or AI support. D-Gap guides you to the lesson you need.",
        useDiagnosis: "Use trouble check",
        useAi: "Use AI support",
        useFraud: "Scam email check",

        step1Title: "Choose your trouble",
        step1Text: "Choose from smartphone use, SNS, maps, email, and more.",
        step2Title: "View recommended lessons",
        step2Text: "Move directly to the lesson you need.",
        step3Title: "Practice at your own pace",
        step3Text: "Review as many times as you need and learn little by little.",

        ctaLabel: "START",
        ctaTitle: "Start digital learning today",
        ctaText:
            "Solve digital confusion step by step. Register for free and learn while saving your progress.",
        registerFree: "Register for free",
        footerText: "A service that turns digital anxiety into learning and confidence."
    },

    hira: {
        login: "ろぐいん",
        navLearning: "がくしゅう",
        navDiagnosis: "こまりごとしんだん",
        navAi: "AIそうだん",
        navCommunity: "こみゅにてぃ",
        search: "けんさく",
        desktopNavAriaLabel: "メインメニュー",
        searchPageHeroLabel: "SEARCH",
        searchPageHeroTitle: "まなびたいないようをけんさくする",
        searchPageHeroDescription: "すまーとふぉん、SNS、Googleさーびす、おかねのあぷりなど、しりたいことやまなびたいことをにゅうりょくしてください。",
        searchInputLabel: "けんさくワード",
        searchInputPlaceholder: "れい：すまほ、LINE、ちず、メール、けっさい",
        searchButton: "けんさくする",
        searchRecommendedLabel: "おすすめ：",
        searchKeyword1: "すまほ",
        searchKeyword2: "LINE",
        searchKeyword3: "ちず",
        searchKeyword4: "メール",
        searchKeyword5: "けっさい",
        searchKeyword6: "さぎ",
        searchResultHeadingLabel: "RESULT",
        searchResultHeading: "けんさくけっか",
        searchCardType: "カテゴリー",
        searchCategory1Text: "でんわ、かめら、もじにゅうりょく、がめんそうさなど、すまーとふぉんのきほんそうさをまなべます。",
        searchCategory1Tags: "すまほ / でんわ / かめら / もじにゅうりょく",
        searchCategory2Title: "SNS・めっせーじ",
        searchCategory2Text: "LINEやかかおとーくなど、かぞくやともだちとつながるつかいかたをまなべます。",
        searchCategory2Tags: "LINE / かかおとーく / SNS / めっせーじ",
        searchCategory3Title: "Googleさーびす",
        searchCategory3Text: "Googleけんさく、Gmail、Googleまっぷ、ほんやくなど、にちじょうにやくだつさーびすをまなべます。",
        searchCategory3Tags: "Google / Gmail / ちず / けんさく / ほんやく",
        searchCategory4Title: "おかね・けっさいあぷり",
        searchCategory4Text: "けっさいあぷりやぎんこうあぷりをあんぜんにつかうきほんをまなべます。",
        searchCategory4Tags: "けっさい / ぎんこう / おかね / あんぜん",
        searchNoResultTitle: "いっちするカテゴリーがみつかりませんでした",
        searchNoResultDescription: "ほかのことばでけんさくしてみてください。れい：すまほ、LINE、ちず、メール、けっさい、さぎ",
        searchSupportTitle: "なにをけんさくすればよいかわからないばあい",
        searchSupportText: "こまりごとしんだんをつかうと、いまのじょうきょうにあったがくしゅうないようをさがしやすくなります。",
        searchSupportButton: "こまりごとしんだんをつかう",
        backToTop: "トップへもどる",
        searchPageTitle: "けんさく | D-Gap",
        searchWordPrefix: "けんさくワード：",
        searchWordCountSuffix: "　{count}けん",
        searchWordEmpty: "キーワードをにゅうりょくすると、かんれんするカテゴリーをさがせます。",
        searchKeywordsAriaLabel: "おすすめけんさくワード",
        menu: "めにゅー",
        close: "とじる",
        fontSizeLabel: "もじのだいきさ",
        fontSizeNormal: "ひょうじゅん",
        fontSizeLarge: "おおきい",
        fontSizeXlarge: "とてもおおきい",

        heroLabel: "でじたる がくしゅう さーびす",
        heroTitle: "でじたるの「わからない」を、<br>ひとつずつ「できる」に。",
        heroDescription:
            "D-Gapは、すまーとふぉん、SNS、めーる、ちず、おかねのあぷりなどを、やさしいてじゅんでまなべるさーびすです。だれでもあんしんしてつかえるように、みやすさ、わかりやすさ、つかいやすさをたいせつにしています。",
        startLearning: "がくしゅうをはじめる",
        diagnosisButton: "こまりごとをしんだんする",

        support1: "おおきくよみやすいもじ",
        support2: "おんせいよみあげ",
        support3: "にほんご・かんこくご",
        support4: "すまほでもつかいやすい",

        demoTitle: "きょうのおすすめ",
        demoCard1Title: "すまーとふぉんのきほん",
        demoCard1Text: "がめんそうさ、でんわ、かめら、もじにゅうりょくをまなびます。",
        demoCard2Title: "ちずでみちをしらべる",
        demoCard2Text: "いきたいばしょまでのいきかたをしらべます。",
        demoCard3Title: "あぶないめーるたいさく",
        demoCard3Text: "あぶないめーるのとくちょうをかくにんできます。",
        progressLabel: "がくしゅうのすすみぐあい",
        scrollDown: "したへすくろーる",

        learningLabel: "がくしゅう",
        learningTitle: "まなびたいものをえらぶ",
        learningText:
            "もくてきにあったものから、すぐにがくしゅうをはじめられます。はじめてのひとでもまよわないように、みぢかなそうさからじゅんばんにまなべます。",

        category1Title: "すまーとふぉんそうさ",
        category1Text:
            "でんわ、かめら、もじにゅうりょく、がめんそうさなどをまなびます。",
        category2Title: "SNS・めっせーじ",
        category2Text:
            "LINEやかかおとーくなど、かぞくやともだちとつながるつかいかたをまなびます。",
        category3Title: "Googleさーびす",
        category3Text:
            "Gmail、Googleまっぷ、けんさく、ほんやくなどをまなびます。",
        category4Title: "おかね・けっさいあぷり",
        category4Text:
            "けっさいあぷりやおかねのあぷりを、あんぜんにつかうきほんをまなびます。",

        featuresLabel: "とくちょう",
        featuresTitle: "だれでもあんしんしてつかえるくふう",
        featuresText:
            "D-Gapは、わかいひとだけでなく、おとしよりやでじたるききになれていないひとにもつかいやすいがめんをめざしています。",
        feature1Title: "みやすいがめん",
        feature1Text:
            "おおきめのもじ、じゅうぶんなよはく、わかりやすいぼたんで、はじめてのひとでもまよいにくくしています。",
        feature2Title: "おんせいでさぽーと",
        feature2Text:
            "ぶんしょうのよみあげやおんせいにゅうりょくで、よむことやにゅうりょくがにがてなひともまなべます。",
        feature3Title: "すすみぐあいをかくにん",
        feature3Text:
            "がくしゅうかんりょうやお気に入りで、どこまでまなんだかかくにんできます。",

        supportLabel: "さぽーと",
        supportTitle: "こまったときも、つぎにすることがわかる",
        supportText:
            "なにからまなべばよいかわからないときは、こまりごとしんだんやAIそうだんからはじめられます。",
        useDiagnosis: "こまりごとしんだんをつかう",
        useAi: "AIそうだんをつかう",
        useFraud: "あぶないめーるしんだん",

        step1Title: "こまっていることをえらぶ",
        step1Text: "すまほそうさ、SNS、ちず、めーるなどからえらべます。",
        step2Title: "おすすめのがくしゅうをみる",
        step2Text: "ひつようなれっすんへすぐにいどうできます。",
        step3Title: "じぶんのぺーすでれんしゅう",
        step3Text: "なんどでもかくにんしながら、すこしずつまなべます。",

        ctaLabel: "はじめる",
        ctaTitle: "きょうからでじたるがくしゅうをはじめよう",
        ctaText:
            "わからないことを、ひとつずついっしょにかいけつします。まずはむりょうとうろくして、がくしゅうりれきをのこしながらすすめてみましょう。",
        registerFree: "むりょうでとうろくする",
        footerText: "でじたるのふあんを、まなびとあんしんにかえるさーびす。"
    }
};


const dgapHomeTextTargets = [
    [".desktop-nav", "desktopNavAriaLabel", "attribute", "aria-label"],
    [".desktop-nav a:nth-child(1)", "navLearning"],
    [".desktop-nav a:nth-child(2)", "navDiagnosis"],
    [".desktop-nav a:nth-child(3)", "navAi"],
    [".desktop-nav a:nth-child(4)", "navCommunity"],
    [".header-search", "search"],
    [".mobile-nav a:nth-child(1)", "navLearning"],
    [".mobile-nav a:nth-child(2)", "navDiagnosis"],
    [".mobile-nav a:nth-child(3)", "navAi"],
    [".mobile-nav a:nth-child(4)", "navCommunity"],
    [".mobile-nav a:nth-child(5)", "search"],
    [".mobile-nav a:nth-child(6)", "login"],
    [".mobile-menu-button", "menu"],
    [".accessibility-toolbar .accessibility-group:nth-child(2) .accessibility-label", "fontSizeLabel"],
    [".font-size-button[data-font-size='normal']", "fontSizeNormal"],
    [".font-size-button[data-font-size='large']", "fontSizeLarge"],
    [".font-size-button[data-font-size='xlarge']", "fontSizeXlarge"],
    ["[data-dgap-text='login']", "login"],

    [".hero-label", "heroLabel"],
    [".hero-content h1", "heroTitle", "html"],
    [".hero-description", "heroDescription"],
    [".hero-buttons .button-primary", "startLearning"],
    [".hero-buttons .button-secondary", "diagnosisButton"],

    [".hero-support span:nth-child(1)", "support1"],
    [".hero-support span:nth-child(2)", "support2"],
    [".hero-support span:nth-child(3)", "support3"],
    [".hero-support span:nth-child(4)", "support4"],

    [".demo-small-title", "demoTitle"],
    [".demo-card:nth-of-type(1) strong", "demoCard1Title"],
    [".demo-card:nth-of-type(1) p", "demoCard1Text"],
    [".demo-card:nth-of-type(2) strong", "demoCard2Title"],
    [".demo-card:nth-of-type(2) p", "demoCard2Text"],
    [".demo-card:nth-of-type(3) strong", "demoCard3Title"],
    [".demo-card:nth-of-type(3) p", "demoCard3Text"],
    [".demo-progress-info span", "progressLabel"],
    [".scroll-guide span:first-child", "scrollDown"],

    [".learning-section .section-label", "learningLabel"],
    [".learning-section h2", "learningTitle"],
    [".learning-section .section-heading p:last-child", "learningText"],

    [".category-card:nth-child(1) h3", "category1Title"],
    [".category-card:nth-child(1) p", "category1Text"],
    [".category-card:nth-child(2) h3", "category2Title"],
    [".category-card:nth-child(2) p", "category2Text"],
    [".category-card:nth-child(3) h3", "category3Title"],
    [".category-card:nth-child(3) p", "category3Text"],
    [".category-card:nth-child(4) h3", "category4Title"],
    [".category-card:nth-child(4) p", "category4Text"],

    [".feature-section .section-label", "featuresLabel"],
    [".feature-section h2", "featuresTitle"],
    [".feature-section .section-heading p:last-child", "featuresText"],

    [".feature-card:nth-child(1) h3", "feature1Title"],
    [".feature-card:nth-child(1) p", "feature1Text"],
    [".feature-card:nth-child(2) h3", "feature2Title"],
    [".feature-card:nth-child(2) p", "feature2Text"],
    [".feature-card:nth-child(3) h3", "feature3Title"],
    [".feature-card:nth-child(3) p", "feature3Text"],

    [".support-section .section-label", "supportLabel"],
    [".support-content h2", "supportTitle"],
    [".support-content > p:not(.section-label)", "supportText"],
    [".support-buttons a:nth-child(1)", "useDiagnosis"],
    [".support-buttons a:nth-child(2)", "useAi"],
    [".support-buttons a:nth-child(3)", "useFraud"],

    [".support-panel-item:nth-child(1) strong", "step1Title"],
    [".support-panel-item:nth-child(1) p", "step1Text"],
    [".support-panel-item:nth-child(2) strong", "step2Title"],
    [".support-panel-item:nth-child(2) p", "step2Text"],
    [".support-panel-item:nth-child(3) strong", "step3Title"],
    [".support-panel-item:nth-child(3) p", "step3Text"],

    [".header-actions .button-primary", "login"],
    [".search-hero-section .section-label", "searchPageHeroLabel"],
    [".search-hero-section h1", "searchPageHeroTitle", "html"],
    [".search-hero-text", "searchPageHeroDescription"],
    [".search-input-label", "searchInputLabel"],
    [".search-input", "searchInputPlaceholder", "attribute", "placeholder"],
    [".search-submit-button", "searchButton"],
    [".search-keywords", "searchKeywordsAriaLabel", "attribute", "aria-label"],
    [".search-keywords span", "searchRecommendedLabel"],
    [".search-keywords a:nth-of-type(1)", "searchKeyword1"],
    [".search-keywords a:nth-of-type(2)", "searchKeyword2"],
    [".search-keywords a:nth-of-type(3)", "searchKeyword3"],
    [".search-keywords a:nth-of-type(4)", "searchKeyword4"],
    [".search-keywords a:nth-of-type(5)", "searchKeyword5"],
    [".search-keywords a:nth-of-type(6)", "searchKeyword6"],
    [".search-result-heading .section-label", "searchResultHeadingLabel"],
    [".search-result-heading h2", "searchResultHeading"],
    [".search-result-card .search-result-type", "searchCardType", "all"],
    [".search-result-card:nth-child(1) h3", "searchCategory1Title"],
    [".search-result-card:nth-child(1) .search-result-content p:not(.search-result-type)", "searchCategory1Text"],
    [".search-result-card:nth-child(1) .search-result-tags", "searchCategory1Tags"],
    [".search-result-card:nth-child(2) h3", "searchCategory2Title"],
    [".search-result-card:nth-child(2) .search-result-content p:not(.search-result-type)", "searchCategory2Text"],
    [".search-result-card:nth-child(2) .search-result-tags", "searchCategory2Tags"],
    [".search-result-card:nth-child(3) h3", "searchCategory3Title"],
    [".search-result-card:nth-child(3) .search-result-content p:not(.search-result-type)", "searchCategory3Text"],
    [".search-result-card:nth-child(3) .search-result-tags", "searchCategory3Tags"],
    [".search-result-card:nth-child(4) h3", "searchCategory4Title"],
    [".search-result-card:nth-child(4) .search-result-content p:not(.search-result-type)", "searchCategory4Text"],
    [".search-result-card:nth-child(4) .search-result-tags", "searchCategory4Tags"],
    [".search-no-result h3", "searchNoResultTitle"],
    [".search-no-result p", "searchNoResultDescription"],
    [".search-support-box h2", "searchSupportTitle"],
    [".search-support-box p", "searchSupportText"],
    [".search-support-box .button-secondary", "searchSupportButton"],
    [".search-back-area .button-primary", "backToTop"],

    [".cta-section .section-label", "ctaLabel"],
    [".cta-section h2", "ctaTitle"],
    [".cta-section p:not(.section-label)", "ctaText"],
    [".cta-section .button-light", "registerFree"],
    [".site-footer p:not(.copyright)", "footerText"]
];


function getInitialDgapLanguage() {
    const urlParams =
        new URLSearchParams(window.location.search);

    const urlLanguage =
        urlParams.get("lang");

    const savedLanguage =
        localStorage.getItem("dgapLanguage");

    return dgapHomeTranslations[urlLanguage]
        ? urlLanguage
        : dgapHomeTranslations[savedLanguage]
            ? savedLanguage
            : "ja";
}

function initializeDgapLanguageSwitch() {
    const languageButtons =
        document.querySelectorAll("[data-dgap-lang]");

    const applyInitialLanguage = () => {
        const initialLanguage = getInitialDgapLanguage();
        applyDgapLanguage(initialLanguage);
    };

    applyInitialLanguage();

    window.addEventListener("pageshow", () => {
        applyInitialLanguage();
    });

    if (languageButtons.length === 0) {
        return;
    }

    applyInitialLanguage();

    window.addEventListener("pageshow", () => {
        applyInitialLanguage();
    });

    languageButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const selectedLanguage =
                button.dataset.dgapLang;

            applyDgapLanguage(selectedLanguage);

            const currentUrl =
                new URL(window.location.href);

            currentUrl.searchParams.set(
                "lang",
                selectedLanguage
            );

            window.history.replaceState(
                {},
                "",
                currentUrl
            );

            localStorage.setItem(
                "dgapLanguage",
                selectedLanguage
            );
        });
    });
}


function updateDgapLinksLanguage(language) {
    document.querySelectorAll("a[href]").forEach((link) => {
        const href = link.getAttribute("href");

        if (!href) {
            return;
        }

        if (
            href.startsWith("#") ||
            href.startsWith("mailto:") ||
            href.startsWith("tel:") ||
            href.startsWith("http://") ||
            href.startsWith("https://")
        ) {
            return;
        }

        if (/\.[a-zA-Z0-9]{1,5}(\?|#|$)/.test(href)) {
            return;
        }

        try {
            const url = new URL(href, window.location.origin);
            url.searchParams.set("lang", language);
            link.setAttribute(
                "href",
                url.pathname + url.search + url.hash
            );
        } catch (error) {
            // ignore malformed hrefs
        }
    });
}

function applyDgapLanguage(language) {
    const translation =
        dgapHomeTranslations[language] ||
        dgapHomeTranslations.ja;

    currentDgapLanguage = language;

    document.documentElement.lang =
        language === "ko"
            ? "ko"
            : language === "en"
                ? "en"
                : "ja";

    dgapHomeTextTargets.forEach((target) => {
        const selector = target[0];
        const key = target[1];
        const mode = target[2];

        const element =
            document.querySelector(selector);

        if (!element || !translation[key]) {
            return;
        }

        if (mode === "html") {
            element.innerHTML = translation[key];
            return;
        }

        if (mode === "attribute" && target[3]) {
            element.setAttribute(target[3], translation[key]);
            return;
        }

        if (mode === "all") {
            const elements = document.querySelectorAll(selector);
            elements.forEach((el) => {
                el.textContent = translation[key];
            });
            return;
        }

        element.textContent = translation[key];
    });

    updateDgapLinksLanguage(language);

    if (translation.searchPageTitle) {
        document.title = translation.searchPageTitle;
    }

    const menuButton =
        document.getElementById("dgapMenuButton");

    if (menuButton) {
        const isOpen =
            menuButton.getAttribute(
                "aria-expanded"
            ) === "true";

        menuButton.textContent =
            isOpen
                ? translation.close
                : translation.menu;
    }

    document
        .querySelectorAll("[data-dgap-lang]")
        .forEach((button) => {
            const isSelected =
                button.dataset.dgapLang === language;

            button.classList.toggle(
                "is-active",
                isSelected
            );

            button.setAttribute(
                "aria-pressed",
                String(isSelected)
            );
        });
}


function initializeDgapFontSizeSwitch() {
    const fontButtons =
        document.querySelectorAll("[data-font-size]");

    if (fontButtons.length === 0) {
        return;
    }

    const savedFontSize =
        localStorage.getItem("dgapFontSize") ||
        "normal";

    applyDgapFontSize(savedFontSize);

    fontButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const selectedSize =
                button.dataset.fontSize;

            applyDgapFontSize(selectedSize);

            localStorage.setItem(
                "dgapFontSize",
                selectedSize
            );
        });
    });
}


function applyDgapFontSize(size) {
    const allowedSizes =
        ["normal", "large", "xlarge"];

    const safeSize =
        allowedSizes.includes(size)
            ? size
            : "normal";

    document.documentElement.dataset.fontSize =
        safeSize;

    document
        .querySelectorAll("[data-font-size]")
        .forEach((button) => {
            const isSelected =
                button.dataset.fontSize === safeSize;

            button.classList.toggle(
                "is-active",
                isSelected
            );

            button.setAttribute(
                "aria-pressed",
                String(isSelected)
            );
        });
}
/* ==============================
   Search page filter
================================ */

document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("keyword");
    const searchCards = document.querySelectorAll("[data-search-card]");
    const noResultBox = document.getElementById("searchNoResult");
    const searchWordText = document.getElementById("searchWordText");

    if (!searchInput || searchCards.length === 0) {
        return;
    }

    const urlParams = new URLSearchParams(window.location.search);
    const keywordFromUrl = urlParams.get("keyword") || "";

    searchInput.value = keywordFromUrl;

    filterSearchCards(keywordFromUrl);

    searchInput.addEventListener("input", function () {
        filterSearchCards(searchInput.value);
    });

    function filterSearchCards(keyword) {
        const normalizedKeyword = normalizeText(keyword);

        let visibleCount = 0;

        searchCards.forEach(function (card) {
            const title = card.querySelector("h3")
                ? card.querySelector("h3").textContent
                : "";

            const description = card.querySelector(".search-result-content p:not(.search-result-type)")
                ? card.querySelector(".search-result-content p:not(.search-result-type)").textContent
                : "";

            const tags = card.querySelector(".search-result-tags")
                ? card.querySelector(".search-result-tags").textContent
                : "";

            const keywords = card.dataset.keywords || "";

            const targetText = normalizeText(
                title + " " + description + " " + tags + " " + keywords
            );

            const isMatch =
                normalizedKeyword === "" ||
                targetText.includes(normalizedKeyword);

            if (isMatch) {
                card.style.display = "grid";
                visibleCount++;
            } else {
                card.style.display = "none";
            }
        });

        if (noResultBox) {
            if (visibleCount === 0) {
                noResultBox.classList.add("is-visible");
            } else {
                noResultBox.classList.remove("is-visible");
            }
        }

        if (searchWordText) {
            const translation =
                dgapHomeTranslations[currentDgapLanguage] ||
                dgapHomeTranslations.ja;

            if (normalizedKeyword === "") {
                searchWordText.textContent =
                    translation.searchWordEmpty;
            } else {
                const countSuffix =
                    translation.searchWordCountSuffix ||
                    "　" + visibleCount + "件";

                searchWordText.innerHTML =
                    translation.searchWordPrefix +
                    "<strong>" +
                    escapeHtml(keyword) +
                    "</strong>" +
                    countSuffix.replace("{count}", visibleCount);
            }
        }
    }

    function normalizeText(text) {
        return String(text)
            .toLowerCase()
            .replace(/\s+/g, "")
            .replace(/　+/g, "")
            .trim();
    }

    function escapeHtml(text) {
        return String(text)
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll('"', "&quot;")
            .replaceAll("'", "&#039;");
    }
});
}

/* ==============================
   D-Gap redesigned pages effects
================================ */

document.addEventListener("DOMContentLoaded", () => {
    initializeDgapTiltCards();
    initializeDgapPageEntryClass();
});

function initializeDgapPageEntryClass() {
    document.body.classList.add("dgap-page-ready");
}

function initializeDgapTiltCards() {
    const cards = document.querySelectorAll("[data-tilt-card]");

    if (cards.length === 0) {
        return;
    }

    const reducedMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;

    if (reducedMotion) {
        return;
    }

    cards.forEach((card) => {
        card.addEventListener("pointermove", (event) => {
            const rect = card.getBoundingClientRect();
            const x = event.clientX - rect.left;
            const y = event.clientY - rect.top;
            const centerX = rect.width / 2;
            const centerY = rect.height / 2;
            const rotateX = ((y - centerY) / centerY) * -4;
            const rotateY = ((x - centerX) / centerX) * 4;

            card.style.transform = `perspective(900px) rotateX(${rotateX}deg) rotateY(${rotateY}deg) translateY(-4px)`;
        });

        card.addEventListener("pointerleave", () => {
            card.style.transform = "";
        });
    });
}
