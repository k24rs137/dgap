const fraudLang = document.documentElement.lang;

const fraudButton = document.getElementById("fraudButton");
const fraudResult = document.getElementById("fraudResult");

if (fraudButton) {
    fraudButton.addEventListener("click", function () {
        const text = document.getElementById("mailText").value;

        let score = 0;
        const reasons = [];

        if (text.includes("http") || text.includes("www")) {
            score += 3;
            reasons.push(
                fraudLang === "ko"
                    ? "URL이 포함되어 있습니다."
                    : "URLが含まれています。"
            );
        }

        if (
            text.includes("至急") ||
            text.includes("緊急") ||
            text.includes("停止") ||
            text.includes("今すぐ") ||
            text.includes("긴급") ||
            text.includes("정지") ||
            text.includes("즉시")
        ) {
            score += 2;
            reasons.push(
                fraudLang === "ko"
                    ? "긴급하게 행동하도록 유도하는 표현이 있습니다."
                    : "緊急性をあおる表現があります。"
            );
        }

        if (
            text.includes("パスワード") ||
            text.includes("暗証番号") ||
            text.includes("口座") ||
            text.includes("ログイン") ||
            text.includes("비밀번호") ||
            text.includes("계좌") ||
            text.includes("로그인")
        ) {
            score += 3;
            reasons.push(
                fraudLang === "ko"
                    ? "개인정보나 로그인 정보를 요구하는表現があります."
                    : "個人情報やログイン情報を求める表現があります。"
            );
        }

        let levelText = "";
        let adviceText = "";

        if (score >= 6) {
            levelText =
                fraudLang === "ko" ? "위험도: 높음" : "危険度：高";
            adviceText =
                fraudLang === "ko"
                    ? "URL을 열지 말고、공식 앱이나公式サイトから確認してください。"
                    : "URLを開かず、公式アプリや公式サイトから確認してください。";
        } else if (score >= 3) {
            levelText =
                fraudLang === "ko" ? "위험도: 주의" : "危険度：注意";
            adviceText =
                fraudLang === "ko"
                    ? "すぐに操作せず、送信元を確認してください。"
                    : "すぐに操作せず、送信元を確認してください。";
        } else {
            levelText =
                fraudLang === "ko" ? "위험도: 낮음" : "危険度：低";
            adviceText =
                fraudLang === "ko"
                    ? "큰 위험 신호는 적지만、重要な操作は公式サイトから確認してください。"
                    : "大きな危険信号は少ないですが、重要な操作は公式サイトから確認してください。";
        }

        fraudResult.innerHTML =
            "<h3>" + levelText + "</h3>" +
            "<p>" + adviceText + "</p>" +
            "<ul>" +
            reasons.map(function (reason) {
                return "<li>" + reason + "</li>";
            }).join("") +
            "</ul>";
    });
}