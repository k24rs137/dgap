/* ==============================
   Search page filter
================================ */

document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("keyword");
    const searchCards = document.querySelectorAll("[data-search-card]");
    const noResultBox = document.getElementById("searchNoResult");
    const searchWordText = document.getElementById("searchWordText");
    const searchForm = document.querySelector(".search-form");
    const historySection = document.getElementById("searchHistory");
    const historyList = document.getElementById("searchHistoryList");
    const clearHistoryButton = document.getElementById("clearSearchHistory");
    const historyStorageKey = "dgapSearchHistory";

    if (!searchInput || searchCards.length === 0) {
        return;
    }

    const urlParams = new URLSearchParams(window.location.search);
    const keywordFromUrl = urlParams.get("keyword") || "";

    searchInput.value = keywordFromUrl;

    filterSearchCards(keywordFromUrl);
    if (keywordFromUrl.trim()) addSearchHistory(keywordFromUrl);
    renderSearchHistory();

    searchInput.addEventListener("input", function () {
        filterSearchCards(searchInput.value);
    });

    if (searchForm) {
        searchForm.addEventListener("submit", function () {
            addSearchHistory(searchInput.value);
        });
    }

    if (clearHistoryButton) {
        clearHistoryButton.addEventListener("click", function () {
            localStorage.removeItem(historyStorageKey);
            renderSearchHistory();
            searchInput.focus();
        });
    }

    initializeVoiceSearch(searchInput);

    function getSearchHistory() {
        try {
            const stored = JSON.parse(localStorage.getItem(historyStorageKey));
            return Array.isArray(stored)
                ? stored.filter(item => typeof item === "string" && item.trim()).slice(0, 5)
                : [];
        } catch (error) {
            return [];
        }
    }

    function addSearchHistory(value) {
        const keyword = String(value || "").trim().slice(0, 100);
        if (!keyword) return;
        const history = getSearchHistory().filter(
            item => item.toLocaleLowerCase() !== keyword.toLocaleLowerCase()
        );
        localStorage.setItem(historyStorageKey, JSON.stringify([keyword, ...history].slice(0, 5)));
        renderSearchHistory();
    }

    function renderSearchHistory() {
        if (!historySection || !historyList) return;
        const history = getSearchHistory();
        historySection.hidden = history.length === 0;
        historyList.replaceChildren();

        history.forEach(function (keyword) {
            const button = document.createElement("button");
            button.type = "button";
            button.className = "search-history-item";
            button.textContent = keyword;
            button.addEventListener("click", function () {
                searchInput.value = keyword;
                filterSearchCards(keyword);
                const url = new URL(window.location.href);
                url.searchParams.set("keyword", keyword);
                window.history.replaceState({}, "", url);
                addSearchHistory(keyword);
                searchInput.focus();
            });
            historyList.appendChild(button);
        });
    }

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

    function initializeVoiceSearch(input) {
        const SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;
        const voiceButton = document.getElementById("voiceSearchButton");
        const voiceStatus = document.getElementById("voiceSearchStatus");
        const voiceNote = document.getElementById("voiceSearchNote");
        if (!SpeechRecognition || !voiceButton || !voiceStatus || !window.isSecureContext) {
            return;
        }

        const selectedLanguage = new URLSearchParams(window.location.search).get("lang") || "ja";
        const recognitionLanguages = {
            ja: "ja-JP",
            hira: "ja-JP",
            ko: "ko-KR",
            en: "en-US"
        };
        const messages = {
            ja: {
                listening: "音声を聞いています。検索したい言葉を話してください。",
                recognized: "認識しました：",
                stopped: "音声入力を終了しました。",
                noSpeech: "音声を認識できませんでした。もう一度お試しください。",
                denied: "マイクの使用が許可されていません。ブラウザの設定を確認してください。",
                microphone: "マイクを利用できません。接続や設定を確認してください。",
                network: "音声認識サービスに接続できませんでした。通常の文字検索をご利用ください。",
                failed: "音声を認識できませんでした。通常の文字検索をご利用ください。"
            },
            ko: {
                listening: "음성을 듣고 있습니다. 검색할 단어를 말해 주세요.",
                recognized: "인식했습니다: ",
                stopped: "음성 입력을 종료했습니다.",
                noSpeech: "음성을 인식하지 못했습니다. 다시 시도해 주세요.",
                denied: "마이크 사용이 허용되지 않았습니다. 브라우저 설정을 확인해 주세요.",
                microphone: "마이크를 사용할 수 없습니다. 연결과 설정을 확인해 주세요.",
                network: "음성 인식 서비스에 연결할 수 없습니다. 문자 검색을 이용해 주세요.",
                failed: "음성을 인식하지 못했습니다. 문자 검색을 이용해 주세요."
            },
            en: {
                listening: "Listening. Say what you want to search for.",
                recognized: "Recognized: ",
                stopped: "Voice input stopped.",
                noSpeech: "No speech was recognized. Please try again.",
                denied: "Microphone access was denied. Check your browser settings.",
                microphone: "The microphone is unavailable. Check its connection and settings.",
                network: "The speech service could not be reached. Please use text search.",
                failed: "Speech could not be recognized. Please use text search."
            },
            hira: {
                listening: "こえを きいています。けんさくしたい ことばを はなしてください。",
                recognized: "ききとりました：",
                stopped: "こえの にゅうりょくを おわりました。",
                noSpeech: "こえを ききとれませんでした。もういちど おためしください。",
                denied: "まいくを つかえません。ぶらうざの せっていを かくにんしてください。",
                microphone: "まいくを つかえません。せつぞくと せっていを かくにんしてください。",
                network: "おんせいにんしきに せつぞくできません。もじで けんさくしてください。",
                failed: "こえを ききとれませんでした。もじで けんさくしてください。"
            }
        };
        const text = messages[selectedLanguage] || messages.ja;
        const recognition = new SpeechRecognition();
        recognition.lang = recognitionLanguages[selectedLanguage] || recognitionLanguages.ja;
        recognition.continuous = false;
        recognition.interimResults = false;
        recognition.maxAlternatives = 1;

        let isListening = false;
        let hasOutcome = false;
        voiceButton.hidden = false;
        if (voiceNote) voiceNote.hidden = false;

        voiceButton.addEventListener("click", function () {
            if (isListening) {
                recognition.stop();
                return;
            }
            hasOutcome = false;
            try {
                recognition.start();
            } catch (error) {
                showVoiceStatus(text.failed, true);
            }
        });

        recognition.addEventListener("start", function () {
            isListening = true;
            voiceButton.classList.add("is-listening");
            voiceButton.setAttribute("aria-pressed", "true");
            showVoiceStatus(text.listening, false);
        });

        recognition.addEventListener("result", function (event) {
            const transcript = event.results[0][0].transcript.trim();
            hasOutcome = true;
            input.value = transcript;
            input.dispatchEvent(new Event("input", { bubbles: true }));
            addSearchHistory(transcript);
            showVoiceStatus(text.recognized + transcript, false);
            input.focus();
        });

        recognition.addEventListener("error", function (event) {
            hasOutcome = true;
            const errorMessages = {
                "no-speech": text.noSpeech,
                "not-allowed": text.denied,
                "service-not-allowed": text.denied,
                "audio-capture": text.microphone,
                "network": text.network
            };
            showVoiceStatus(errorMessages[event.error] || text.failed, true);
        });

        recognition.addEventListener("end", function () {
            isListening = false;
            voiceButton.classList.remove("is-listening");
            voiceButton.setAttribute("aria-pressed", "false");
            if (!hasOutcome) showVoiceStatus(text.stopped, false);
        });

        function showVoiceStatus(message, isError) {
            voiceStatus.hidden = false;
            voiceStatus.textContent = message;
            voiceStatus.dataset.state = isError ? "error" : "info";
        }
    }
});

