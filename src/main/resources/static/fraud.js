const fraudLang = document.documentElement.lang;
const fraudButton = document.getElementById("fraudButton");
const fraudResult = document.getElementById("fraudResult");

const urgentWords = ["至急", "緊急", "停止", "今すぐ", "本日中", "期限", "urgent", "immediately", "긴급", "정지", "즉시"];
const credentialWords = ["パスワード", "暗証番号", "認証コード", "口座", "ログイン", "本人確認", "password", "pin", "verify account", "비밀번호", "계좌", "로그인"];
const moneyWords = ["振込", "送金", "未払い", "当選", "返金", "ギフトカード", "手数料", "payment", "prize", "refund", "gift card"];
const shorteners = ["bit.ly", "tinyurl.com", "t.co", "is.gd", "goo.gl"];

function includesAny(text, words) {
    return words.some(word => text.includes(word.toLocaleLowerCase()));
}

function addReason(reasons, points, message) {
    reasons.push({ points, message });
    return points;
}

function analyzeMessage(rawText) {
    const text = rawText.normalize("NFKC").toLocaleLowerCase();
    const reasons = [];
    let score = 0;
    const urls = text.match(/https?:\/\/[^\s<>"']+|www\.[^\s<>"']+/g) || [];

    if (urls.length > 0) score += addReason(reasons, 2, "本文にリンクが含まれています。");
    if (urls.some(url => shorteners.some(domain => url.includes(domain)))) {
        score += addReason(reasons, 3, "短縮URLが使われ、移動先を確認しにくくなっています。");
    }
    if (urls.some(url => url.includes("xn--") || /https?:\/\/(?:\d{1,3}\.){3}\d{1,3}/.test(url))) {
        score += addReason(reasons, 4, "見慣れない形式のリンクが含まれています。");
    }
    if (includesAny(text, urgentWords)) score += addReason(reasons, 2, "急いで操作させる表現があります。");
    if (includesAny(text, credentialWords)) score += addReason(reasons, 3, "認証情報や個人情報を求める表現があります。");
    if (includesAny(text, moneyWords)) score += addReason(reasons, 2, "支払い・送金・当選など金銭に関する表現があります。");
    if (/添付|attachment|zip|exe|apk/.test(text)) score += addReason(reasons, 2, "添付ファイルを開かせる表現があります。");
    if (/お客様|利用者|dear customer/.test(text) && !/氏名|様/.test(text)) {
        score += addReason(reasons, 1, "宛名が一般的で、受信者を特定していない可能性があります。");
    }
    return { score, reasons };
}

function renderFraudResult(analysis) {
    fraudResult.replaceChildren();
    const heading = document.createElement("h3");
    const advice = document.createElement("p");
    const disclaimer = document.createElement("p");
    disclaimer.className = "fraud-disclaimer";

    if (analysis.score >= 7) {
        heading.textContent = fraudLang === "en" ? "High risk" : "危険度：高";
        advice.textContent = fraudLang === "en" ? "Do not open links or attachments. Verify through the official app or phone number." : "リンクや添付ファイルを開かず、公式アプリや公式窓口から確認してください。";
    } else if (analysis.score >= 3) {
        heading.textContent = fraudLang === "en" ? "Use caution" : "危険度：注意";
        advice.textContent = fraudLang === "en" ? "Do not act immediately. Check the sender and verify independently." : "すぐに操作せず、送信元を確認して公式窓口へ問い合わせてください。";
    } else {
        heading.textContent = fraudLang === "en" ? "No strong warning signs found" : "強い危険信号は見つかりませんでした";
        advice.textContent = fraudLang === "en" ? "This does not prove the message is safe. Verify important requests independently." : "安全を保証する結果ではありません。重要な依頼は必ず公式アプリや公式窓口で確認してください。";
    }

    fraudResult.append(heading, advice);
    if (analysis.reasons.length > 0) {
        const list = document.createElement("ul");
        analysis.reasons.forEach(reason => {
            const item = document.createElement("li");
            item.textContent = reason.message;
            list.appendChild(item);
        });
        fraudResult.appendChild(list);
    }
    disclaimer.textContent = fraudLang === "en"
        ? "This check is educational and cannot guarantee whether a message is safe or fraudulent."
        : "この診断は学習用の目安であり、メールの安全性や詐欺であることを保証・断定するものではありません。";
    fraudResult.appendChild(disclaimer);
}

if (fraudButton) fraudButton.addEventListener("click", function () {
    const input = document.getElementById("mailText");
    const text = input.value.trim();
    if (!text) {
        fraudResult.textContent = fraudLang === "en" ? "Enter the email text first." : "メール本文を入力してください。";
        return;
    }
    renderFraudResult(analyzeMessage(text));
});
