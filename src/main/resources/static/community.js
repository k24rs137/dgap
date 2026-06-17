const communityLang = document.documentElement.lang;

const notLoggedInArea = document.getElementById("notLoggedInArea");
const loggedInArea = document.getElementById("loggedInArea");
const userWelcome = document.getElementById("userWelcome");

const postInput = document.getElementById("postInput");
const postButton = document.getElementById("postButton");
const postList = document.getElementById("postList");

const logoutButton = document.getElementById("logoutButton");

const isLoggedIn = localStorage.getItem("loggedIn");
const userId = localStorage.getItem("userId");

if (isLoggedIn === "true") {
    notLoggedInArea.style.display = "none";
    loggedInArea.style.display = "block";

    userWelcome.textContent =
        communityLang === "ko"
            ? userId + "님, 환영합니다."
            : userId + "さん、ようこそ。";
} else {
    notLoggedInArea.style.display = "block";
    loggedInArea.style.display = "none";
}

function showPosts() {
    postList.innerHTML = "";

    const posts =
        JSON.parse(localStorage.getItem("communityPosts")) || [];

    posts.forEach(function (post, index) {
        const div = document.createElement("div");
        div.className = "post-card";

        let deleteButtonHtml = "";

        if (post.user === userId) {
            deleteButtonHtml =
                "<button class='delete-post-button' data-index='" + index + "'>" +
                (communityLang === "ko" ? "삭제" : "削除") +
                "</button>";
        }

        if (!post.likedUsers) {
            post.likedUsers = [];
        }

        const liked =
            post.likedUsers.includes(userId);

        const heart =
            liked ? "❤️" : "♡";

        div.innerHTML =
            "<strong>" + post.user + "</strong><br>" +
            "<small>" + (post.date || "") + "</small>" +
            "<p>" + post.text + "</p>" +
            "<button class='like-button' data-index='" + index + "'>" +
            heart + " " +
            (post.likes || 0) +
            "</button>" +
            deleteButtonHtml;

        postList.appendChild(div);
    });

    const deleteButtons =
        document.querySelectorAll(".delete-post-button");

    deleteButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            const index = button.dataset.index;

            const posts =
                JSON.parse(localStorage.getItem("communityPosts")) || [];

            posts.splice(index, 1);

            localStorage.setItem(
                "communityPosts",
                JSON.stringify(posts)
            );

            showPosts();
        });
    });

    const likeButtons =
        document.querySelectorAll(".like-button");

    likeButtons.forEach(function (button) {
        button.addEventListener("click", function () {
            const index = button.dataset.index;

            const posts =
                JSON.parse(localStorage.getItem("communityPosts")) || [];

            if (!posts[index].likedUsers) {
                posts[index].likedUsers = [];
            }

            if (!posts[index].likes) {
                posts[index].likes = 0;
            }

            const alreadyLiked =
                posts[index].likedUsers.includes(userId);

            if (alreadyLiked) {
                posts[index].likes--;

                posts[index].likedUsers =
                    posts[index].likedUsers.filter(
                        function (user) {
                            return user !== userId;
                        }
                    );
            } else {
                posts[index].likes++;
                posts[index].likedUsers.push(userId);
            }

            localStorage.setItem(
                "communityPosts",
                JSON.stringify(posts)
            );

            showPosts();
        });
    });
}

if (postButton) {
    postButton.addEventListener("click", function () {
        const text = postInput.value;

        if (!text) {
            return;
        }

        const posts =
            JSON.parse(localStorage.getItem("communityPosts")) || [];

        posts.unshift({
            user: userId,
            text: text,
            date: new Date().toLocaleString(),
            likes: 0,
            likedUsers: []
        });

        localStorage.setItem(
            "communityPosts",
            JSON.stringify(posts)
        );

        postInput.value = "";

        showPosts();
    });
}

if (postList) {
    showPosts();
}

if (logoutButton) {
    logoutButton.addEventListener("click", function () {
        localStorage.removeItem("loggedIn");
        location.reload();
    });
}