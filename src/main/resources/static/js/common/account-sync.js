(function () {
    const lessonKeys = [
        "/lesson/smartphone-power", "/lesson/smartphone-input", "/lesson/phone-call",
        "/lesson/camera-photo", "/lesson/gallery-view", "/lesson/smartphone-home",
        "/lesson/wifi-connect", "/lesson/app-install", "/lesson/notification-control",
        "/lesson/storage-cleanup", "/lesson/accessibility-settings", "/lesson/qr-troubleshooting",
        "/lesson/line-message",
        "/lesson/kakao-message", "/lesson/sns-privacy", "/lesson/google-search",
        "/lesson/gmail", "/lesson/google-map", "/lesson/youtube-watch",
        "/lesson/google-translate", "/lesson/payment-app", "/lesson/smbc-app",
        "/lesson/yucho-app", "/lesson/money-safety"
    ];

    window.dgapAccount = { authenticated: false, userId: null, displayName: null };

    async function jsonRequest(url, options) {
        const response = await window.dgapFetch(url, options);
        if (!response.ok) return null;
        return response.json();
    }

    window.dgapSaveProgress = async function (key, completed, title) {
        if (window.dgapAccountReady) await window.dgapAccountReady;
        if (!window.dgapAccount.authenticated) return;
        await jsonRequest("/api/me/progress", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ key, completed, title: title || "レッスン" })
        });
    };

    window.dgapSaveFavorite = async function (key, favorite) {
        if (window.dgapAccountReady) await window.dgapAccountReady;
        if (!window.dgapAccount.authenticated) return;
        await jsonRequest("/api/me/favorite", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ key, favorite })
        });
    };

    window.dgapSaveActivity = async function (key, activity) {
        if (window.dgapAccountReady) await window.dgapAccountReady;
        if (!window.dgapAccount.authenticated) return;
        await jsonRequest("/api/me/activity", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ key, ...activity })
        });
    };

    window.dgapLogout = async function () {
        await window.dgapFetch("/api/logout", { method: "POST" });
        sessionStorage.removeItem("dgapAccountHydrated");
        sessionStorage.removeItem("dgapAccountSignature");
        localStorage.removeItem("loggedIn");
        localStorage.removeItem("userId");
        location.reload();
    };

    async function initializeAccount() {
        const session = await jsonRequest("/api/session");
        if (!session || !session.authenticated) return;

        window.dgapAccount = {
            authenticated: true,
            userId: session.userId,
            displayName: session.displayName
        };
        const data = await jsonRequest("/api/me/learning");
        if (!data) return;

        lessonKeys.forEach(key => localStorage.removeItem(key));
        (data.completed || []).forEach(key => localStorage.setItem(key, "done"));
        localStorage.setItem("favoriteLessons", JSON.stringify(data.favorites || []));
        localStorage.setItem("learningHistory", JSON.stringify(data.history || []));
        const activities = {};
        (data.activities || []).forEach(item => {
            activities[item.key] = {
                preCorrect: item.preCorrect,
                preSelected: item.preCorrect == null ? null : "saved",
                postCorrect: item.postCorrect,
                missionCompleted: item.missionCompleted,
                reviewDueAt: item.reviewDueAt,
                reviewedAt: item.reviewedAt,
                missions: item.missionCompleted ? [true, true, true] : []
            };
        });
        localStorage.setItem("dgapLessonActivities", JSON.stringify(activities));

        const signature = JSON.stringify(data);
        const hydrationKey = session.userId;
        const previousHydration = sessionStorage.getItem("dgapAccountHydrated");
        const previousSignature = sessionStorage.getItem("dgapAccountSignature");
        sessionStorage.setItem("dgapAccountHydrated", hydrationKey);
        sessionStorage.setItem("dgapAccountSignature", signature);

        updateAccountDisplay(session.displayName || session.userId);
        if (previousHydration !== hydrationKey || (previousSignature && previousSignature !== signature)) {
            location.reload();
        }
    }

    function updateAccountDisplay(displayName) {
        const status = document.getElementById("loginStatus");
        if (status) status.textContent = displayName + "さんでログイン中";
        const loginLink = document.getElementById("loginLink");
        const registerLink = document.getElementById("registerLink");
        const myPageLink = document.getElementById("myPageLink");
        const mobileMyPageLink = document.getElementById("mobileMyPageLink");
        const logoutButton = document.getElementById("topLogoutButton");
        if (loginLink) loginLink.style.display = "none";
        if (registerLink) registerLink.style.display = "none";
        if (myPageLink) myPageLink.style.display = "inline-flex";
        if (mobileMyPageLink) mobileMyPageLink.style.display = "block";
        if (logoutButton) {
            logoutButton.style.display = "inline-block";
            logoutButton.addEventListener("click", window.dgapLogout, { once: true });
        }
    }

    window.dgapAccountReady = initializeAccount().catch(function () {
        // The app remains usable in guest mode when the server is temporarily unavailable.
    });
})();
