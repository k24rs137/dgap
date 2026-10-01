const communityLang = document.documentElement.lang;
const notLoggedInArea = document.getElementById("notLoggedInArea");
const loggedInArea = document.getElementById("loggedInArea");
const userWelcome = document.getElementById("userWelcome");
const postInput = document.getElementById("postInput");
const postButton = document.getElementById("postButton");
const postList = document.getElementById("postList");
const logoutButton = document.getElementById("logoutButton");

async function communityRequest(url, options) {
    const response = await fetch(url, Object.assign({ credentials: "same-origin" }, options));
    if (!response.ok) {
        let message = "操作に失敗しました。";
        try { message = (await response.json()).message || message; } catch (error) { /* noop */ }
        throw new Error(message);
    }
    if (response.status === 204) return null;
    return response.json();
}

function createPostCard(post) {
    const card = document.createElement("article");
    card.className = "post-card";

    const author = document.createElement("strong");
    author.textContent = post.user;
    const date = document.createElement("small");
    date.textContent = new Date(post.createdAt).toLocaleString();
    const content = document.createElement("p");
    content.textContent = post.text;
    const likeButton = document.createElement("button");
    likeButton.type = "button";
    likeButton.className = "like-button";
    likeButton.textContent = (post.liked ? "♥ " : "♡ ") + post.likes;
    likeButton.addEventListener("click", async function () {
        try {
            await communityRequest("/api/community/posts/" + post.id + "/like", { method: "POST" });
            await showPosts();
        } catch (error) {
            alert(error.message);
        }
    });

    card.append(author, document.createElement("br"), date, content, likeButton);
    if (post.owned) {
        const deleteButton = document.createElement("button");
        deleteButton.type = "button";
        deleteButton.className = "delete-post-button";
        deleteButton.textContent = communityLang === "en" ? "Delete" : "削除";
        deleteButton.addEventListener("click", async function () {
            try {
                await communityRequest("/api/community/posts/" + post.id, { method: "DELETE" });
                await showPosts();
            } catch (error) {
                alert(error.message);
            }
        });
        card.append(deleteButton);
    }
    return card;
}

async function showPosts() {
    const posts = await communityRequest("/api/community/posts");
    postList.replaceChildren();
    if (posts.length === 0) {
        const empty = document.createElement("p");
        empty.textContent = communityLang === "en" ? "No posts yet." : "まだ投稿はありません。";
        postList.appendChild(empty);
        return;
    }
    posts.forEach(post => postList.appendChild(createPostCard(post)));
}

async function initializeCommunity() {
    try {
        const session = await communityRequest("/api/session");
        notLoggedInArea.style.display = "none";
        loggedInArea.style.display = "block";
        userWelcome.textContent = communityLang === "en"
            ? "Welcome, " + session.userId + "."
            : session.userId + "さん、ようこそ。";
        await showPosts();
    } catch (error) {
        notLoggedInArea.style.display = "block";
        loggedInArea.style.display = "none";
    }
}

if (postButton) {
    postButton.addEventListener("click", async function () {
        const text = postInput.value.trim();
        if (!text) return;
        postButton.disabled = true;
        try {
            await communityRequest("/api/community/posts", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ text })
            });
            postInput.value = "";
            await showPosts();
        } catch (error) {
            alert(error.message);
        } finally {
            postButton.disabled = false;
        }
    });
}

if (logoutButton) {
    logoutButton.addEventListener("click", function () {
        if (window.dgapLogout) window.dgapLogout();
    });
}

initializeCommunity();
