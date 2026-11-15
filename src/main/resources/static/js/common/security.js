(function () {
    function csrfToken() {
        const cookie = document.cookie.split("; ")
            .find(item => item.startsWith("XSRF-TOKEN="));
        return cookie ? decodeURIComponent(cookie.substring("XSRF-TOKEN=".length)) : null;
    }

    window.dgapFetch = function (url, options) {
        const requestOptions = Object.assign({ credentials: "same-origin" }, options || {});
        const method = (requestOptions.method || "GET").toUpperCase();
        if (!["GET", "HEAD", "OPTIONS", "TRACE"].includes(method)) {
            const headers = new Headers(requestOptions.headers || {});
            const token = csrfToken();
            if (token) headers.set("X-XSRF-TOKEN", token);
            requestOptions.headers = headers;
        }
        return fetch(url, requestOptions);
    };
})();
