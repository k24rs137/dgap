(() => {
    "use strict";

    const scenarios = {
        "/lesson/smartphone-power": [
            ["本体を確認", "画面が消えています。電源を入れる操作は？", ["側面の電源ボタンを長押し", "音量ボタンを1回押す", "画面を強く押す"], 0],
            ["ロック画面", "ホーム画面を表示するには？", ["画面を上へスワイプして認証", "緊急通報を押す", "カメラを開く"], 0],
            ["電源メニュー", "動作が不安定なときに選ぶ項目は？", ["再起動", "初期化", "緊急通報"], 0]
        ],
        "/lesson/smartphone-input": [
            ["メモ", "文字を入力するため、最初に押す場所は？", ["文章の入力欄", "削除ボタン", "共有ボタン"], 0],
            ["キーボード", "数字を入力したいときに使うキーは？", ["文字種・数字切替", "改行", "音声を消す"], 0],
            ["入力内容", "1文字間違えました。安全な直し方は？", ["カーソルを合わせて削除", "メモ全体を削除", "電源を切る"], 0]
        ],
        "/lesson/phone-call": [
            ["連絡先", "発信前に最初に確認することは？", ["相手の名前と電話番号", "画面の明るさ", "電池の色"], 0],
            ["連絡先詳細", "電話を開始するボタンは？", ["電話マークの発信ボタン", "ごみ箱", "共有"], 0],
            ["通話中", "通話を終える操作は？", ["赤い終了ボタン", "音量を最大にする", "ホームへ戻るだけ"], 0]
        ],
        "/lesson/camera-photo": [
            ["カメラ", "写真を鮮明にするため、被写体に行う操作は？", ["画面上の被写体をタップ", "電源を切る", "共有を押す"], 0],
            ["撮影画面", "写真を撮るボタンは？", ["丸いシャッターボタン", "戻るボタン", "音量設定"], 0],
            ["撮影後", "保存結果を確認する場所は？", ["画面端の小さい写真", "設定アプリ", "電話履歴"], 0]
        ],
        "/lesson/gallery-view": [
            ["写真一覧", "今日撮った写真を探すには？", ["日付が新しい写真を見る", "連絡先を開く", "機内モードにする"], 0],
            ["写真表示", "細部を大きく見る操作は？", ["2本指を広げる", "長押しして削除", "端末を振る"], 0],
            ["共有画面", "送信前に必ず確認することは？", ["写真と送信相手", "壁紙の色", "着信音"], 0]
        ],
        "/lesson/line-message": [
            ["トーク一覧", "最初に選ぶものは？", ["送信したい相手", "ニュース広告", "設定の削除"], 0],
            ["トーク", "文章を書いたあと、送信前に確認することは？", ["相手と文章の内容", "電池残量だけ", "文字の色だけ"], 0],
            ["送信確認", "個人情報が写った写真を選んだ場合は？", ["選択を外して送らない", "そのまま送る", "全員へ転送する"], 0]
        ],
        "/lesson/kakao-message": [
            ["チャット一覧", "メッセージを送る前に選ぶものは？", ["正しい相手のチャット", "地図画面", "決済画面"], 0],
            ["チャット", "送信直前に確認する項目は？", ["プロフィール名と文章", "画面の明るさ", "着信音"], 0],
            ["写真の権限", "必要以上の写真を見せたくない場合は？", ["選択した写真だけ許可", "すべて常に許可", "暗証番号を送る"], 0]
        ],
        "/lesson/sns-privacy": [
            ["プロフィール", "個人情報を守るために開く項目は？", ["プライバシー設定", "音量設定", "充電設定"], 0],
            ["公開範囲", "知人だけに投稿を見せる設定は？", ["友達・フォロワーのみ", "全員に公開", "検索サイトへ公開"], 0],
            ["投稿確認", "現在地が含まれていた場合は？", ["位置情報を削除してから投稿", "そのまま投稿", "住所も追加する"], 0]
        ],
        "/lesson/google-search": [
            ["検索", "自治体の正しい情報を探しやすい検索語は？", ["福岡市 粗大ごみ 申込 公式", "ごみ", "今すぐ無料"], 0],
            ["検索結果", "公式ページを判断する手がかりは？", ["運営者・URL・更新日", "一番派手な広告", "文字の大きさ"], 0],
            ["内容確認", "情報が正しいか確かめる方法は？", ["複数の信頼できる情報源を比べる", "最初の1件だけ信じる", "SNSの投稿だけを見る"], 0]
        ],
        "/lesson/gmail": [
            ["受信トレイ", "新しいメールを書くボタンは？", ["作成", "削除", "迷惑メール"], 0],
            ["メール作成", "送信前に確認する組み合わせは？", ["宛先・件名・本文・添付", "文字の色だけ", "受信件数だけ"], 0],
            ["添付確認", "違う写真を添付した場合は？", ["添付を削除して選び直す", "そのまま送る", "宛先を全員にする"], 0]
        ],
        "/lesson/google-map": [
            ["地図", "目的地を探す場所は？", ["検索欄", "音量ボタン", "メール作成"], 0],
            ["場所の詳細", "道順を見るボタンは？", ["経路", "削除", "投稿"], 0],
            ["経路一覧", "出発前に確認することは？", ["交通手段・所要時間・到着地", "画面の色", "動画の字幕"], 0]
        ],
        "/lesson/youtube-watch": [
            ["YouTube", "見たい動画を探す場所は？", ["検索欄", "通話履歴", "支払い画面"], 0],
            ["検索結果", "信頼できる操作説明を選ぶ手がかりは？", ["公式チャンネルと公開日", "刺激的な題名だけ", "広告の多さ"], 0],
            ["再生設定", "ゆっくり確認したいときは？", ["再生速度を0.75倍にする", "音量を最大にする", "自動再生をオンにする"], 0]
        ],
        "/lesson/google-translate": [
            ["言語選択", "日本語を英語にしたいときの設定は？", ["日本語 → 英語", "英語 → 日本語", "言語を選ばない"], 0],
            ["翻訳", "短い文章を翻訳する場所は？", ["入力欄", "通話履歴", "地図"], 0],
            ["結果確認", "契約など重要な文章の場合は？", ["原文と訳を保存し、人にも確認する", "翻訳だけで即決する", "原文を削除する"], 0]
        ],
        "/lesson/payment-app": [
            ["支払い前", "最初に確認する項目は？", ["残高・店名・金額", "壁紙", "着信音"], 0],
            ["決済確認", "表示金額が違う場合は？", ["確定せず店員に確認", "そのまま支払う", "認証コードを伝える"], 0],
            ["支払い後", "完了を確認する場所は？", ["完了画面と利用履歴", "カメラ", "連絡先"], 0]
        ],
        "/lesson/smbc-app": [
            ["アプリ確認", "安全に起動する方法は？", ["公式アプリを直接開く", "SMSのリンクから開く", "他人の端末で開く"], 0],
            ["口座画面", "身に覚えのない出金がある場合は？", ["公式窓口へ連絡", "通知を削除して終える", "暗証番号を返信する"], 0],
            ["終了", "利用後に行う安全な操作は？", ["ログアウトして画面を閉じる", "開いたまま渡す", "パスワードをメモ表示する"], 0]
        ],
        "/lesson/yucho-app": [
            ["アプリ確認", "安全に残高を確認する入口は？", ["公式アプリ", "メール内の不明なURL", "SNS広告"], 0],
            ["明細", "取引内容を確かめる項目は？", ["日付・金額・摘要", "背景色", "文字サイズだけ"], 0],
            ["端末認証", "認証コードを聞かれた場合は？", ["誰にも教えない", "電話相手へ伝える", "SNSに投稿する"], 0]
        ],
        "/lesson/money-safety": [
            ["不審な通知", "『今すぐ確認』というURLが届きました。最初にすることは？", ["リンクを開かず送信元を確認", "すぐリンクを開く", "認証コードを返信する"], 0],
            ["公式確認", "本物のお知らせか確認する方法は？", ["公式アプリを直接開く", "通知の電話番号へ即連絡", "友人へ転送する"], 0],
            ["相談", "判断できない場合は？", ["家族や公式窓口へ相談", "一人で急いで支払う", "通知をSNSへ公開する"], 0]
        ]
    };

    const labels = {
        ja: { app: "操作練習", start: "正しい操作を選んでください。", correct: "正解です。次の画面へ進みます。", wrong: "その操作ではありません。落ち着いて、もう一度選びましょう。", done: "操作練習を完了しました。実際の端末でも、安全を確認しながら試してみましょう。", next: "次の操作へ" },
        ko: { app: "조작 연습", start: "올바른 조작을 선택하세요.", correct: "정답입니다. 다음 화면으로 이동합니다.", wrong: "다른 조작을 다시 선택해 보세요.", done: "조작 연습을 완료했습니다.", next: "다음 조작" },
        en: { app: "Practice", start: "Choose the correct action.", correct: "Correct. Continue to the next screen.", wrong: "That is not the expected action. Try again.", done: "Practice completed. Try it on your device while checking each step safely.", next: "Next action" },
        hira: { app: "そうされんしゅう", start: "ただしいそうさをえらんでください。", correct: "せいかいです。つぎのがめんへすすみます。", wrong: "ちがうそうさです。もういちどえらびましょう。", done: "そうされんしゅうがおわりました。", next: "つぎのそうさ" }
    };

    document.addEventListener("DOMContentLoaded", () => {
        const root = document.getElementById("practiceSimulator");
        if (!root) return;

        const language = labels[root.dataset.lang] ? root.dataset.lang : "ja";
        const text = labels[language];
        let steps = scenarios[root.dataset.lessonKey];
        if (!steps) {
            root.hidden = true;
            return;
        }

        if (language !== "ja") {
            const localizedTasks = [...document.querySelectorAll(".lesson-practice-list p")]
                .map(element => element.textContent.trim())
                .filter(Boolean);
            const localizedOptions = {
                ko: ["안내를 확인하고 이 조작을 한다", "확인하지 않고 다음으로 간다", "앱을 바로 종료한다"],
                en: ["Check the screen and perform this action", "Continue without checking", "Close the app immediately"],
                hira: ["がめんをかくにんして、このそうさをする", "かくにんしないでつぎへすすむ", "すぐにあぷりをとじる"]
            };
            steps = localizedTasks.map((task, index) => [
                `${text.app} ${index + 1}`,
                task,
                localizedOptions[language],
                0
            ]);
        }
        const appName = document.getElementById("practiceAppName");
        const count = document.getElementById("practiceStepCount");
        const screenLabel = document.getElementById("practiceScreenLabel");
        const instruction = document.getElementById("practiceInstruction");
        const actions = document.getElementById("practiceActions");
        const feedback = document.getElementById("practiceFeedback");
        const startButton = document.getElementById("practiceStartButton");
        const resetButton = document.getElementById("practiceResetButton");
        let currentStep = 0;

        appName.textContent = text.app;

        function renderStep() {
            const step = steps[currentStep];
            count.textContent = `${currentStep + 1} / ${steps.length}`;
            screenLabel.textContent = step[0];
            instruction.textContent = step[1];
            feedback.textContent = text.start;
            feedback.className = "practice-feedback";
            actions.replaceChildren();

            step[2].forEach((option, index) => {
                const button = document.createElement("button");
                button.type = "button";
                button.className = "practice-screen-button";
                button.textContent = option;
                button.addEventListener("click", () => chooseAction(index, step[3]));
                actions.appendChild(button);
            });
        }

        function chooseAction(selected, correct) {
            if (selected !== correct) {
                feedback.textContent = text.wrong;
                feedback.className = "practice-feedback is-error";
                return;
            }

            feedback.textContent = text.correct;
            feedback.className = "practice-feedback is-success";
            actions.querySelectorAll("button").forEach(button => { button.disabled = true; });

            const nextButton = document.createElement("button");
            nextButton.type = "button";
            nextButton.className = "practice-screen-button is-primary";
            nextButton.textContent = text.next;
            nextButton.addEventListener("click", () => {
                currentStep++;
                if (currentStep < steps.length) renderStep();
                else completePractice();
            });
            actions.appendChild(nextButton);
        }

        function completePractice() {
            count.textContent = `${steps.length} / ${steps.length}`;
            screenLabel.textContent = "COMPLETE";
            instruction.textContent = text.done;
            feedback.textContent = "✓";
            feedback.className = "practice-feedback is-success is-complete";
            actions.replaceChildren();
            resetButton.hidden = false;
            const completeCheck = document.getElementById("completeCheck");
            if (completeCheck && !completeCheck.checked) {
                completeCheck.checked = true;
                completeCheck.dispatchEvent(new Event("change", { bubbles: true }));
            }
        }

        function startPractice() {
            currentStep = 0;
            startButton.hidden = true;
            resetButton.hidden = false;
            renderStep();
        }

        startButton.addEventListener("click", startPractice);
        resetButton.addEventListener("click", startPractice);
    });
})();
