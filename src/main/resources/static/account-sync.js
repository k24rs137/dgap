(function () {
    const lessonKeys = [
        "/lesson/smartphone-power", "/lesson/smartphone-input", "/lesson/phone-call",
        "/lesson/camera-photo", "/lesson/gallery-view", "/lesson/line-message",
        "/lesson/kakao-message", "/lesson/sns-privacy", "/lesson/google-search",
        "/lesson/gmail", "/lesson/google-map", "/lesson/youtube-watch",
        "/lesson/google-translate", "/lesson/payment-app", "/lesson/smbc-app",
        "/lesson/yucho-app", "/lesson/money-safety"
    ];

    window.dgapAccount = { authenticated: false, userId: null };

    async function jsonRequest(url, options) {
        const response = await fetch(url, Object.assign({ credentials: "same-origin" }, options));
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

    window.dgapLogout = async function () {
        await fetch("/api/logout", { method: "POST", credentials: "same-origin" });
        sessionStorage.removeItem("dgapAccountHydrated");
        sessionStorage.removeItem("dgapAccountSignature");
        localStorage.removeItem("loggedIn");
        localStorage.removeItem("userId");
        location.reload();
    };

    async function initializeAccount() {
        const session = await jsonRequest("/api/session");
        if (!session || !session.authenticated) return;

        window.dgapAccount = { authenticated: true, userId: session.userId };
        const data = await jsonRequest("/api/me/learning");
        if (!data) return;

        lessonKeys.forEach(key => localStorage.removeItem(key));
        (data.completed || []).forEach(key => localStorage.setItem(key, "done"));
        localStorage.setItem("favoriteLessons", JSON.stringify(data.favorites || []));
        localStorage.setItem("learningHistory", JSON.stringify(data.history || []));

        const signature = JSON.stringify(data);
        const hydrationKey = session.userId;
        const previousHydration = sessionStorage.getItem("dgapAccountHydrated");
        const previousSignature = sessionStorage.getItem("dgapAccountSignature");
        sessionStorage.setItem("dgapAccountHydrated", hydrationKey);
        sessionStorage.setItem("dgapAccountSignature", signature);

        updateAccountDisplay(session.userId);
        if (previousHydration !== hydrationKey || (previousSignature && previousSignature !== signature)) {
            location.reload();
        }
    }

    function updateAccountDisplay(userId) {
        const status = document.getElementById("loginStatus");
        if (status) status.textContent = userId + "さんでログイン中";
        const loginLink = document.getElementById("loginLink");
        const registerLink = document.getElementById("registerLink");
        const logoutButton = document.getElementById("topLogoutButton");
        if (loginLink) loginLink.style.display = "none";
        if (registerLink) registerLink.style.display = "none";
        if (logoutButton) {
            logoutButton.style.display = "inline-block";
            logoutButton.addEventListener("click", window.dgapLogout, { once: true });
        }
    }

    window.dgapAccountReady = initializeAccount().catch(function () {
        // The app remains usable in guest mode when the server is temporarily unavailable.
    });
})();
