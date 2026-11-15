(function () {
    const lang = new URLSearchParams(location.search).get("lang") || "ja";
    const messages = {
        ja: {
            success: "ユーザー名を変更しました。",
            failed: "ユーザー名を変更できませんでした。もう一度お試しください。"
        },
        ko: {
            success: "사용자 이름을 변경했습니다.",
            failed: "사용자 이름을 변경할 수 없습니다. 다시 시도해 주세요."
        },
        en: {
            success: "Your display name has been changed.",
            failed: "We could not change your display name. Please try again."
        },
        hira: {
            success: "ひょうじする なまえを かえました。",
            failed: "なまえを かえられませんでした。もういちど おためしください。"
        }
    };
    const text = messages[lang] || messages.ja;
    const profileMessage = document.getElementById("profileMessage");
    const savedMessage = sessionStorage.getItem("dgapProfileMessage");
    if (profileMessage && savedMessage) {
        profileMessage.textContent = savedMessage;
        profileMessage.dataset.state = "success";
        sessionStorage.removeItem("dgapProfileMessage");
    }

    const profileForm = document.getElementById("changeDisplayNameForm");
    const changeButton = document.getElementById("changeDisplayNameButton");
    if (profileForm) {
        profileForm.addEventListener("submit", async function (event) {
            event.preventDefault();
            if (!profileForm.reportValidity()) return;

            const displayName = document.getElementById("newDisplayName").value.trim();
            if (changeButton) changeButton.disabled = true;
            if (profileMessage) {
                profileMessage.textContent = "";
                delete profileMessage.dataset.state;
            }

            try {
                const response = await window.dgapFetch("/api/me/display-name", {
                    method: "PATCH",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ displayName })
                });
                const result = await response.json().catch(function () { return {}; });
                if (!response.ok) {
                    throw new Error(result.message || text.failed);
                }

                sessionStorage.setItem("dgapProfileMessage", text.success);
                location.reload();
            } catch (error) {
                if (profileMessage) {
                    profileMessage.textContent = error.message || text.failed;
                    profileMessage.dataset.state = "error";
                }
                if (changeButton) changeButton.disabled = false;
            }
        });
    }

    const logoutButton = document.getElementById("mypageLogoutButton");
    if (!logoutButton) return;

    logoutButton.addEventListener("click", async function () {
        logoutButton.disabled = true;
        try {
            if (window.dgapLogout) {
                await window.dgapLogout();
                return;
            }
            await window.dgapFetch("/api/logout", { method: "POST" });
            location.href = "/login";
        } finally {
            logoutButton.disabled = false;
        }
    });
})();
