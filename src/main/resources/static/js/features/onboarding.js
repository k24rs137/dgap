(function () {
    const STORAGE_KEY = "dgapOnboardingVersion";
    const VERSION = "1";

    const copy = {
        ja: {
            welcomeTitle: "D-Gapの使い方を一緒に確認しますか？",
            welcomeDescription: "約30秒で、基本的な使い方を実際に操作しながら確認できます。",
            welcomePoint1: "文字サイズと言語の変更",
            welcomePoint2: "メニューから学習を探す方法",
            welcomePoint3: "学習カテゴリの選び方",
            welcomeNote: "案内はいつでも「その他 → 使い方」からもう一度確認できます。",
            start: "案内を始める",
            skip: "スキップ",
            back: "戻る",
            progress: "{current} / {total}",
            actionHint: "強調された場所をタップしてください",
            steps: [
                ["表示設定を開いてみましょう", "文字サイズや表示言語を変更できます。「表示設定」をタップしてください。"],
                ["メニューを開いてみましょう", "相談ナビや詐欺メール診断、使い方などを確認できます。強調されたメニューをタップしてください。"],
                ["学習内容を見てみましょう", "「学習」をタップすると、学習カテゴリまで移動できます。"],
                ["カテゴリを選びましょう", "学びたいカテゴリをタップするとレッスン一覧へ進みます。最初のカテゴリを試してみましょう。"]
            ]
        },
        en: {
            welcomeTitle: "Would you like a quick tour of D-Gap?",
            welcomeDescription: "Learn the basics by trying them yourself in about 30 seconds.",
            welcomePoint1: "Change text size and language",
            welcomePoint2: "Find learning from the menu",
            welcomePoint3: "Choose a learning category",
            welcomeNote: "You can run this guide again from More → How to use D-Gap.",
            start: "Start the tour",
            skip: "Skip",
            back: "Back",
            progress: "{current} / {total}",
            actionHint: "Tap the highlighted control",
            steps: [
                ["Open display settings", "Change the text size or display language. Tap Display settings."],
                ["Open the menu", "Find the lesson navigator, scam check, and this guide. Tap the highlighted menu."],
                ["Browse learning", "Tap Learn to move to the learning categories."],
                ["Choose a category", "Tap a category to see its lessons. Try the first category now."]
            ]
        },
        ko: {
            welcomeTitle: "D-Gap 사용 방법을 함께 확인할까요?",
            welcomeDescription: "약 30초 동안 직접 조작하면서 기본 사용법을 확인할 수 있습니다.",
            welcomePoint1: "글자 크기와 언어 변경",
            welcomePoint2: "메뉴에서 학습 찾기",
            welcomePoint3: "학습 카테고리 선택",
            welcomeNote: "더보기 → 사용 방법에서 언제든지 다시 볼 수 있습니다.",
            start: "안내 시작",
            skip: "건너뛰기",
            back: "뒤로",
            progress: "{current} / {total}",
            actionHint: "강조된 곳을 눌러 주세요",
            steps: [
                ["화면 설정을 열어 보세요", "글자 크기와 표시 언어를 변경할 수 있습니다. 화면 설정을 눌러 주세요."],
                ["메뉴를 열어 보세요", "학습 상담, 사기 메일 진단, 사용 방법을 찾을 수 있습니다. 강조된 메뉴를 눌러 주세요."],
                ["학습 내용을 확인해 보세요", "학습을 누르면 학습 카테고리로 이동합니다."],
                ["카테고리를 선택하세요", "카테고리를 누르면 레슨 목록으로 이동합니다. 첫 번째 카테고리를 선택해 보세요."]
            ]
        },
        hira: {
            welcomeTitle: "D-Gapの つかいかたを いっしょに みますか？",
            welcomeDescription: "30びょうくらいで、じっさいに おしながら つかいかたを かくにんできます。",
            welcomePoint1: "もじの おおきさと ことばを かえる",
            welcomePoint2: "めにゅーから がくしゅうを さがす",
            welcomePoint3: "がくしゅうの かてごりーを えらぶ",
            welcomeNote: "「そのほか → つかいかた」から いつでも もういちど みられます。",
            start: "あんないを はじめる",
            skip: "すきっぷ",
            back: "もどる",
            progress: "{current} / {total}",
            actionHint: "きいろで かこまれた ところを おしてください",
            steps: [
                ["ひょうじせっていを ひらきましょう", "もじの おおきさや ことばを かえられます。ひょうじせっていを おしてください。"],
                ["めにゅーを ひらきましょう", "そうだんなびや あぶないめーるしんだんを みられます。めにゅーを おしてください。"],
                ["がくしゅうを みてみましょう", "がくしゅうを おすと、かてごりーまで いどうします。"],
                ["かてごりーを えらびましょう", "かてごりーを おすと れっすんの いちらんへ すすみます。さいしょの かてごりーを おしてみましょう。"]
            ]
        }
    };

    document.addEventListener("DOMContentLoaded", initializeOnboarding);

    function initializeOnboarding() {
        const welcome = document.getElementById("onboardingWelcome");
        const tour = document.getElementById("onboardingTour");
        if (!welcome || !tour) return;

        const language = getLanguage();
        const text = copy[language] || copy.ja;
        const startButton = document.getElementById("onboardingStart");
        const welcomeSkip = document.getElementById("onboardingWelcomeSkip");
        const tourSkip = document.getElementById("onboardingSkip");
        const backButton = document.getElementById("onboardingBack");
        const popover = document.getElementById("onboardingPopover");
        const spotlight = document.getElementById("onboardingSpotlight");
        const progress = document.getElementById("onboardingProgress");
        const stepTitle = document.getElementById("onboardingStepTitle");
        const stepDescription = document.getElementById("onboardingStepDescription");
        const actionHint = document.getElementById("onboardingActionHint");
        const liveRegion = document.getElementById("onboardingLiveRegion");
        let stepIndex = 0;
        let activeTarget = null;
        let activeClickHandler = null;
        let returnFocus = null;

        applyCopy();

        startButton.addEventListener("click", startTour);
        welcomeSkip.addEventListener("click", skipTour);
        tourSkip.addEventListener("click", skipTour);
        backButton.addEventListener("click", function () {
            if (stepIndex === 0) return;
            stepIndex -= 1;
            showStep();
        });
        window.addEventListener("resize", positionCurrentStep);
        window.addEventListener("scroll", positionCurrentStep, { passive: true });
        document.addEventListener("keydown", handleKeyboard);

        const parameters = new URLSearchParams(window.location.search);
        const forced = parameters.get("guide") === "1";
        if (forced) {
            parameters.delete("guide");
            const cleanUrl = window.location.pathname
                + (parameters.toString() ? "?" + parameters.toString() : "")
                + window.location.hash;
            window.history.replaceState({}, "", cleanUrl);
        }

        if (forced || readStoredVersion() !== VERSION) showWelcome();

        function applyCopy() {
            document.querySelectorAll("[data-onboarding-text]").forEach(function (element) {
                const value = text[element.dataset.onboardingText];
                if (value) element.textContent = value;
            });
            backButton.textContent = text.back;
        }

        function showWelcome() {
            returnFocus = document.activeElement;
            welcome.hidden = false;
            document.body.classList.add("onboarding-lock");
            window.setTimeout(function () { startButton.focus(); }, 0);
        }

        function startTour() {
            welcome.hidden = true;
            document.body.classList.remove("onboarding-lock");
            stepIndex = 0;
            tour.hidden = false;
            showStep();
        }

        function showStep() {
            removeTargetListener();
            activeTarget = getTarget(stepIndex);
            if (!activeTarget) {
                finishTour();
                return;
            }

            const step = text.steps[stepIndex];
            progress.textContent = text.progress
                .replace("{current}", String(stepIndex + 1))
                .replace("{total}", String(text.steps.length));
            stepTitle.textContent = step[0];
            stepDescription.textContent = step[1];
            actionHint.textContent = text.actionHint;
            backButton.disabled = stepIndex === 0;
            liveRegion.textContent = progress.textContent + "。" + step[0] + "。" + step[1];

            activeTarget.classList.add("onboarding-active-target");
            activeClickHandler = function () {
                if (stepIndex === text.steps.length - 1) {
                    storeVersion();
                    closeTour(false);
                    return;
                }
                window.setTimeout(function () {
                    stepIndex += 1;
                    closeCompletedControl(stepIndex);
                    showStep();
                }, stepIndex < 2 ? 450 : 650);
            };
            activeTarget.addEventListener("click", activeClickHandler, { once: true });

            if (stepIndex === 3) {
                activeTarget.scrollIntoView({ behavior: reducedMotion() ? "auto" : "smooth", block: "center" });
                window.setTimeout(positionCurrentStep, reducedMotion() ? 0 : 450);
            } else {
                positionCurrentStep();
            }
            window.setTimeout(function () { activeTarget.focus({ preventScroll: true }); }, 30);
        }

        function getTarget(index) {
            if (index === 0) return document.querySelector('[data-onboarding="display-settings"]');
            if (index === 1) {
                return findVisible('[data-onboarding="more-menu"]')
                    || findVisible('[data-onboarding="mobile-menu"]');
            }
            if (index === 2) return findVisible('[data-onboarding="learning-link"]');
            return document.querySelector('[data-onboarding="first-category"]');
        }

        function closeCompletedControl(nextStepIndex) {
            if (nextStepIndex === 1) {
                const settingsPanel = document.getElementById("accessibilityPanel");
                const settingsToggle = document.getElementById("accessibilityToggle");
                if (settingsPanel) settingsPanel.hidden = true;
                if (settingsToggle) settingsToggle.setAttribute("aria-expanded", "false");
            }
            if (nextStepIndex === 2) {
                const desktopMore = document.querySelector(".desktop-more-menu");
                if (desktopMore) desktopMore.removeAttribute("open");
            }
        }

        function findVisible(selector) {
            return Array.from(document.querySelectorAll(selector)).find(function (element) {
                const style = window.getComputedStyle(element);
                return style.display !== "none" && style.visibility !== "hidden"
                    && element.getClientRects().length > 0;
            }) || null;
        }

        function positionCurrentStep() {
            if (tour.hidden || !activeTarget) return;
            const rectangle = activeTarget.getBoundingClientRect();
            const padding = 8;
            spotlight.style.left = Math.max(4, rectangle.left - padding) + "px";
            spotlight.style.top = Math.max(4, rectangle.top - padding) + "px";
            spotlight.style.width = Math.min(window.innerWidth - 8, rectangle.width + padding * 2) + "px";
            spotlight.style.height = Math.min(window.innerHeight - 8, rectangle.height + padding * 2) + "px";

            const margin = 14;
            const popoverWidth = popover.offsetWidth;
            const popoverHeight = popover.offsetHeight;
            const left = Math.min(
                window.innerWidth - popoverWidth - margin,
                Math.max(margin, rectangle.left + rectangle.width / 2 - popoverWidth / 2)
            );
            const below = rectangle.bottom + margin;
            const top = below + popoverHeight <= window.innerHeight - margin
                ? below
                : Math.max(margin, rectangle.top - popoverHeight - margin);
            popover.style.left = left + "px";
            popover.style.top = top + "px";
        }

        function skipTour() {
            storeVersion();
            closeTour(true);
        }

        function finishTour() {
            storeVersion();
            closeTour(true);
        }

        function closeTour(restoreFocus) {
            welcome.hidden = true;
            tour.hidden = true;
            document.body.classList.remove("onboarding-lock");
            removeTargetListener();
            if (restoreFocus && returnFocus && typeof returnFocus.focus === "function") {
                returnFocus.focus();
            }
        }

        function removeTargetListener() {
            if (!activeTarget) return;
            activeTarget.classList.remove("onboarding-active-target");
            if (activeClickHandler) activeTarget.removeEventListener("click", activeClickHandler);
            activeTarget = null;
            activeClickHandler = null;
        }

        function handleKeyboard(event) {
            if (!welcome.hidden) {
                if (event.key === "Escape") {
                    event.preventDefault();
                    skipTour();
                } else if (event.key === "Tab") {
                    trapFocus(event, welcome.querySelectorAll("button"));
                }
                return;
            }
            if (tour.hidden) return;
            if (event.key === "Escape") {
                event.preventDefault();
                skipTour();
            } else if (event.key === "Tab") {
                const controls = [activeTarget, tourSkip, backButton]
                    .filter(function (element) { return element && !element.disabled; });
                trapFocus(event, controls);
            }
        }

        function trapFocus(event, elements) {
            const focusable = Array.from(elements);
            if (!focusable.length) return;
            const current = focusable.indexOf(document.activeElement);
            let next = event.shiftKey ? current - 1 : current + 1;
            if (current === -1) next = event.shiftKey ? focusable.length - 1 : 0;
            if (next < 0) next = focusable.length - 1;
            if (next >= focusable.length) next = 0;
            event.preventDefault();
            focusable[next].focus({ preventScroll: true });
        }

        function readStoredVersion() {
            try { return localStorage.getItem(STORAGE_KEY); } catch (error) { return null; }
        }

        function storeVersion() {
            try { localStorage.setItem(STORAGE_KEY, VERSION); } catch (error) { /* continue without persistence */ }
        }
    }

    function getLanguage() {
        const requested = new URLSearchParams(window.location.search).get("lang");
        if (copy[requested]) return requested;
        try {
            const saved = localStorage.getItem("dgapLanguage");
            if (copy[saved]) return saved;
        } catch (error) { /* use Japanese */ }
        return "ja";
    }

    function reducedMotion() {
        return window.matchMedia("(prefers-reduced-motion: reduce)").matches;
    }
})();
