(() => {
    "use strict";

    const precheck = document.getElementById("lessonPrecheck");
    const postcheck = document.getElementById("postLessonCheck");
    if (!precheck || !postcheck) return;

    const lessonKey = precheck.dataset.lessonKey;
    const answerIndex = String(precheck.dataset.answer);
    const lang = typeof getDgapCurrentLang === "function" ? getDgapCurrentLang() : "ja";
    const storageKey = "dgapLessonActivities";
    const isReview = new URLSearchParams(window.location.search).get("review") === "1";
    const copy = {
        ja: {
            preSaved: "回答を記録しました。正解はまだ表示せず、学習後に結果を比べます。",
            missionDone: "実生活ミッションを完了しました。安全を確認しながら実践できました。",
            correct: "正解です。",
            wrong: "今回は不正解です。正しい答えは",
            safety: "覚えておくポイント：",
            improved: "学習前は迷っていた内容を、学習後は正しく答えられました。",
            maintained: "学習前から理解できていた内容を、学習後も確認できました。",
            review: "復習を完了しました。次の復習は14日後です。",
            due: "この内容は復習対象として保存しました。",
            complete: "学習完了として保存しました。次のレッスンへ進めます。"
        },
        en: {
            preSaved: "Answer saved. The correct answer stays hidden until after the lesson.",
            missionDone: "Real-life mission completed safely.",
            correct: "Correct.", wrong: "Not quite. The correct answer is",
            safety: "Remember: ", improved: "You improved from the before-check to the after-check.",
            maintained: "You confirmed what you already understood.", review: "Review complete. The next review is in 14 days.",
            due: "This lesson was added to your review list.", complete: "Lesson completed. You can continue to the next lesson."
        },
        ko: {
            preSaved: "답변을 저장했습니다. 정답은 학습 후에 확인합니다.", missionDone: "실생활 미션을 안전하게 완료했습니다.",
            correct: "정답입니다.", wrong: "정답은", safety: "기억할 점: ", improved: "학습 전보다 이해도가 향상되었습니다.",
            maintained: "알고 있던 내용을 다시 확인했습니다.", review: "복습 완료. 다음 복습은 14일 후입니다.",
            due: "복습 목록에 저장했습니다.", complete: "학습 완료로 저장했습니다. 다음 레슨으로 이동할 수 있습니다."
        },
        hira: {
            preSaved: "こたえを ほぞんしました。せいかいは あとで かくにんします。", missionDone: "じっせいかつの みっしょんが おわりました。",
            correct: "せいかいです。", wrong: "せいかいは", safety: "おぼえておくこと：", improved: "まなぶまえより できるように なりました。",
            maintained: "わかっていたことを もういちど かくにんできました。", review: "ふくしゅうが おわりました。つぎは 14にちごです。",
            due: "ふくしゅうする ないように ほぞんしました。", complete: "がくしゅうかんりょうです。つぎへ すすめます。"
        }
    }[lang] || null;

    function allActivities() {
        try { return JSON.parse(localStorage.getItem(storageKey)) || {}; }
        catch (error) { return {}; }
    }

    function currentActivity() {
        return allActivities()[lessonKey] || { missions: [] };
    }

    function saveActivity(changes, apiChanges) {
        const activities = allActivities();
        activities[lessonKey] = { ...currentActivity(), ...changes, updatedAt: new Date().toISOString() };
        localStorage.setItem(storageKey, JSON.stringify(activities));
        if (window.dgapSaveActivity) window.dgapSaveActivity(lessonKey, apiChanges || changes);
    }

    const preButtons = [...precheck.querySelectorAll(".precheck-choice")];
    const preMessage = document.getElementById("precheckMessage");
    const saved = currentActivity();
    if (saved.preSelected != null) restorePrecheck(saved.preSelected);

    preButtons.forEach(button => button.addEventListener("click", () => {
        const selected = button.dataset.index;
        saveActivity({ preSelected: selected, preCorrect: selected === answerIndex },
                { preCorrect: selected === answerIndex });
        restorePrecheck(selected);
        enablePostcheck();
    }));

    function restorePrecheck(selected) {
        preButtons.forEach(button => {
            button.classList.toggle("is-selected", button.dataset.index === String(selected));
            button.disabled = true;
        });
        preMessage.textContent = copy.preSaved;
        precheck.classList.add("is-complete");
    }

    const mission = document.getElementById("lessonMission");
    const missionChecks = [...mission.querySelectorAll(".mission-check")];
    const missionCount = document.getElementById("missionCount");
    const missionMessage = document.getElementById("missionMessage");
    const savedMissions = Array.isArray(saved.missions) ? saved.missions : [];
    missionChecks.forEach(check => {
        check.checked = Boolean(savedMissions[Number(check.dataset.index)]);
        check.addEventListener("change", updateMissions);
    });
    renderMissionState();

    function updateMissions() {
        const missions = missionChecks.map(check => check.checked);
        const missionCompleted = missions.length > 0 && missions.every(Boolean);
        saveActivity({ missions, missionCompleted }, { missionCompleted });
        renderMissionState();
    }

    function renderMissionState() {
        const done = missionChecks.filter(check => check.checked).length;
        missionCount.textContent = `${done} / ${missionChecks.length}`;
        const complete = missionChecks.length > 0 && done === missionChecks.length;
        mission.classList.toggle("is-complete", complete);
        missionMessage.textContent = complete ? copy.missionDone : "";
    }

    const quizArea = document.getElementById("quizArea");
    const quizMessage = document.getElementById("quizMessage");
    const resultSummary = document.getElementById("lessonResultSummary");
    const nextButton = document.getElementById("lessonNextButton");
    const quizChoices = [...quizArea.querySelectorAll(".quiz-choice")];
    const correctChoice = quizChoices.find(choice => choice.dataset.index === answerIndex);
    const gateMessage = document.getElementById("postcheckGateMessage");
    if (saved.preSelected == null) {
        quizChoices.forEach(choice => { choice.disabled = true; });
        postcheck.classList.add("is-locked");
    } else {
        enablePostcheck();
    }

    function enablePostcheck() {
        postcheck.querySelectorAll(".quiz-choice").forEach(choice => { choice.disabled = false; });
        postcheck.classList.remove("is-locked");
        if (gateMessage) gateMessage.hidden = true;
    }

    quizChoices.forEach(choice => choice.addEventListener("click", () => {
        const correct = choice.dataset.index === answerIndex;
        quizChoices.forEach(item => {
            item.classList.toggle("is-correct", item.dataset.index === answerIndex);
            item.classList.toggle("is-wrong", item === choice && !correct);
        });

        const safetyTip = postcheck.dataset.safetyTip || "";
        quizMessage.replaceChildren();
        const heading = document.createElement("strong");
        heading.textContent = correct ? copy.correct : `${copy.wrong}「${correctChoice.textContent.trim()}」です。`;
        const detail = document.createElement("p");
        detail.textContent = `${copy.safety}${safetyTip}`;
        quizMessage.append(heading, detail);

        const activity = currentActivity();
        if (isReview) {
            if (correct) {
                const nextDue = new Date(Date.now() + 14 * 86400000).toISOString();
                saveActivity({ reviewedAt: new Date().toISOString(), reviewDueAt: nextDue }, { reviewCompleted: true });
                showSummary(copy.review);
            } else {
                showSummary(copy.due);
            }
            return;
        }

        const reviewDueAt = new Date(Date.now() + (correct ? 7 * 86400000 : 0)).toISOString();
        saveActivity({ postCorrect: correct, reviewDueAt }, { postCorrect: correct });
        if (correct) {
            completeLesson();
            const comparison = activity.preCorrect === false ? copy.improved
                    : activity.preCorrect === true ? copy.maintained : copy.complete;
            showSummary(`${comparison} ${copy.complete}`);
            if (nextButton) nextButton.hidden = false;
        } else {
            showSummary(copy.due);
        }
    }));

    function completeLesson() {
        const checkbox = document.getElementById("completeCheck");
        if (checkbox) checkbox.checked = true;
        localStorage.setItem(lessonKey, "done");
        localStorage.setItem(`dgapCompletedAt:${lessonKey}`, new Date().toISOString());
        if (window.dgapSaveProgress) {
            window.dgapSaveProgress(lessonKey, true, document.querySelector("h1").textContent);
        }
    }

    function showSummary(message) {
        resultSummary.textContent = message;
        resultSummary.hidden = false;
    }
})();
