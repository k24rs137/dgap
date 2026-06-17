const authLang = document.documentElement.lang;

// =====================
// 会員登録
// =====================

const registerButton = document.getElementById("registerButton");

if (registerButton) {
    registerButton.addEventListener("click", async function () {
        const id = document.getElementById("registerId").value;
        const password = document.getElementById("registerPassword").value;
        const confirm = document.getElementById("registerPasswordConfirm").value;
        const message = document.getElementById("registerMessage");

        if (!id || !password || !confirm) {
            message.textContent =
                authLang === "ko"
                    ? "모든 항목을 입력해주세요."
                    : "すべての項目を入力してください。";
            return;
        }

        if (password !== confirm) {
            message.textContent =
                authLang === "ko"
                    ? "비밀번호가 일치하지 않습니다."
                    : "パスワードが一致しません。";
            return;
        }

        const response = await fetch("/api/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                userId: id,
                password: password
            })
        });

        const result = await response.json();

        if (result.success) {
            localStorage.setItem("userId", id);
            localStorage.setItem("loggedIn", "true");

            message.textContent =
                authLang === "ko"
                    ? "회원가입이 완료되었습니다."
                    : "会員登録が完了しました。";

            setTimeout(function () {
                location.href = "/?lang=" + authLang;
            }, 500);
        } else {
            message.textContent = result.message;
        }
    });
}

// =====================
// ログイン
// =====================

const loginButton = document.getElementById("loginButton");

if (loginButton) {
    loginButton.addEventListener("click", async function () {
        const id = document.getElementById("loginId").value;
        const password = document.getElementById("loginPassword").value;
        const message = document.getElementById("loginMessage");

        const response = await fetch("/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                userId: id,
                password: password
            })
        });

        const result = await response.json();

        if (result.success) {
            localStorage.setItem("userId", id);
            localStorage.setItem("loggedIn", "true");

            message.textContent =
                authLang === "ko"
                    ? "로그인되었습니다."
                    : "ログインしました。";

            setTimeout(function () {
                location.href = "/?lang=" + authLang;
            }, 500);
        } else {
            message.textContent = result.message;
        }
    });
}

// =====================
// トップページ表示
// =====================

const loginStatus = document.getElementById("loginStatus");
const topLogoutButton = document.getElementById("topLogoutButton");
const loginLink = document.getElementById("loginLink");
const registerLink = document.getElementById("registerLink");

if (loginStatus) {
    const loggedIn = localStorage.getItem("loggedIn");
    const userId = localStorage.getItem("userId");

    if (loggedIn === "true") {
        loginStatus.textContent =
            authLang === "ko"
                ? userId + "님 로그인 중"
                : userId + "さんでログイン中";

        if (topLogoutButton) {
            topLogoutButton.style.display = "inline-block";
        }

        if (loginLink) {
            loginLink.style.display = "none";
        }

        if (registerLink) {
            registerLink.style.display = "none";
        }
    } else {
        loginStatus.textContent =
            authLang === "ko"
                ? "로그인하지 않았습니다"
                : "ログインしていません";
    }
}

// =====================
// ログアウト
// =====================

if (topLogoutButton) {
    topLogoutButton.addEventListener("click", function () {
        localStorage.removeItem("loggedIn");
        localStorage.removeItem("userId");
        location.reload();
    });
}