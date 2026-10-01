const authLang = new URLSearchParams(location.search).get("lang") || "ja";

async function sendAuthRequest(url, payload) {
    const response = await fetch(url, {
        method: "POST",
        credentials: "same-origin",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });
    let result = {};
    try {
        result = await response.json();
    } catch (error) {
        result = { message: "サーバーから正しい応答を受け取れませんでした。" };
    }
    if (!response.ok) {
        throw new Error(result.message || "処理に失敗しました。");
    }
    return result;
}

function showAuthMessage(element, text, isError) {
    if (!element) return;
    element.textContent = text;
    element.dataset.state = isError ? "error" : "success";
}

const registerButton = document.getElementById("registerButton");
if (registerButton) {
    registerButton.addEventListener("click", async function () {
        const id = document.getElementById("registerId").value.trim();
        const password = document.getElementById("registerPassword").value;
        const confirm = document.getElementById("registerPasswordConfirm").value;
        const message = document.getElementById("registerMessage");

        if (password !== confirm) {
            showAuthMessage(message, "パスワードが一致しません。", true);
            return;
        }
        registerButton.disabled = true;
        try {
            const result = await sendAuthRequest("/api/register", { userId: id, password });
            localStorage.removeItem("loggedIn");
            localStorage.removeItem("userId");
            sessionStorage.removeItem("dgapAccountHydrated");
            showAuthMessage(message, result.message, false);
            setTimeout(() => { location.href = "/?lang=" + encodeURIComponent(authLang); }, 400);
        } catch (error) {
            showAuthMessage(message, error.message, true);
        } finally {
            registerButton.disabled = false;
        }
    });
}

const loginButton = document.getElementById("loginButton");
if (loginButton) {
    loginButton.addEventListener("click", async function () {
        const id = document.getElementById("loginId").value.trim();
        const password = document.getElementById("loginPassword").value;
        const message = document.getElementById("loginMessage");
        loginButton.disabled = true;
        try {
            const result = await sendAuthRequest("/api/login", { userId: id, password });
            localStorage.removeItem("loggedIn");
            localStorage.removeItem("userId");
            sessionStorage.removeItem("dgapAccountHydrated");
            showAuthMessage(message, result.message, false);
            setTimeout(() => { location.href = "/?lang=" + encodeURIComponent(authLang); }, 400);
        } catch (error) {
            showAuthMessage(message, error.message, true);
        } finally {
            loginButton.disabled = false;
        }
    });
}

["loginPassword", "registerPasswordConfirm"].forEach(function (id) {
    const input = document.getElementById(id);
    if (input) input.addEventListener("keydown", function (event) {
        if (event.key === "Enter") {
            const button = id === "loginPassword" ? loginButton : registerButton;
            if (button) button.click();
        }
    });
});
