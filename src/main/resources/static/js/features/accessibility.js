document.addEventListener("DOMContentLoaded", () => {
    initializeDgapLanguageSwitch();
    initializeDgapFontSizeSwitch();
});

function getInitialDgapLanguage() {
    const urlParams =
        new URLSearchParams(window.location.search);

    const urlLanguage =
        urlParams.get("lang");

    const savedLanguage =
        localStorage.getItem("dgapLanguage");

    return dgapHomeTranslations[urlLanguage]
        ? urlLanguage
        : dgapHomeTranslations[savedLanguage]
            ? savedLanguage
            : "ja";
}

function initializeDgapLanguageSwitch() {
    const languageButtons =
        document.querySelectorAll("[data-dgap-lang]");

    const applyInitialLanguage = () => {
        const initialLanguage = getInitialDgapLanguage();
        applyDgapLanguage(initialLanguage);
    };

    applyInitialLanguage();

    window.addEventListener("pageshow", () => {
        applyInitialLanguage();
    });

    if (languageButtons.length === 0) {
        return;
    }

    applyInitialLanguage();

    window.addEventListener("pageshow", () => {
        applyInitialLanguage();
    });

    languageButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const selectedLanguage =
                button.dataset.dgapLang;

            applyDgapLanguage(selectedLanguage);

            const currentUrl =
                new URL(window.location.href);

            currentUrl.searchParams.set(
                "lang",
                selectedLanguage
            );

            window.history.replaceState(
                {},
                "",
                currentUrl
            );

            localStorage.setItem(
                "dgapLanguage",
                selectedLanguage
            );
        });
    });
}


function updateDgapLinksLanguage(language) {
    document.querySelectorAll("a[href]").forEach((link) => {
        const href = link.getAttribute("href");

        if (!href) {
            return;
        }

        if (
            href.startsWith("#") ||
            href.startsWith("mailto:") ||
            href.startsWith("tel:") ||
            href.startsWith("http://") ||
            href.startsWith("https://")
        ) {
            return;
        }

        if (/\.[a-zA-Z0-9]{1,5}(\?|#|$)/.test(href)) {
            return;
        }

        try {
            const url = new URL(href, window.location.origin);
            url.searchParams.set("lang", language);
            link.setAttribute(
                "href",
                url.pathname + url.search + url.hash
            );
        } catch (error) {
            // ignore malformed hrefs
        }
    });
}

function applyDgapLanguage(language) {
    const translation =
        dgapHomeTranslations[language] ||
        dgapHomeTranslations.ja;

    currentDgapLanguage = language;

    document.documentElement.lang =
        language === "ko"
            ? "ko"
            : language === "en"
                ? "en"
                : "ja";

    dgapHomeTextTargets.forEach((target) => {
        const selector = target[0];
        const key = target[1];
        const mode = target[2];

        const element =
            document.querySelector(selector);

        if (!element || !translation[key]) {
            return;
        }

        if (mode === "html") {
            element.innerHTML = translation[key];
            return;
        }

        if (mode === "attribute" && target[3]) {
            element.setAttribute(target[3], translation[key]);
            return;
        }

        if (mode === "all") {
            const elements = document.querySelectorAll(selector);
            elements.forEach((el) => {
                el.textContent = translation[key];
            });
            return;
        }

        element.textContent = translation[key];
    });

    updateDgapLinksLanguage(language);

    if (translation.searchPageTitle) {
        document.title = translation.searchPageTitle;
    }

    const menuButton =
        document.getElementById("dgapMenuButton");

    if (menuButton) {
        const isOpen =
            menuButton.getAttribute(
                "aria-expanded"
            ) === "true";

        menuButton.textContent =
            isOpen
                ? translation.close
                : translation.menu;
    }

    document
        .querySelectorAll("[data-dgap-lang]")
        .forEach((button) => {
            const isSelected =
                button.dataset.dgapLang === language;

            button.classList.toggle(
                "is-active",
                isSelected
            );

            button.setAttribute(
                "aria-pressed",
                String(isSelected)
            );
        });
}


function initializeDgapFontSizeSwitch() {
    const fontButtons =
        document.querySelectorAll("[data-font-size]");

    if (fontButtons.length === 0) {
        return;
    }

    const savedFontSize =
        localStorage.getItem("dgapFontSize") ||
        "normal";

    applyDgapFontSize(savedFontSize);

    fontButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const selectedSize =
                button.dataset.fontSize;

            applyDgapFontSize(selectedSize);

            localStorage.setItem(
                "dgapFontSize",
                selectedSize
            );
        });
    });
}


function applyDgapFontSize(size) {
    const allowedSizes =
        ["normal", "large", "xlarge"];

    const safeSize =
        allowedSizes.includes(size)
            ? size
            : "normal";

    document.documentElement.dataset.fontSize =
        safeSize;

    document
        .querySelectorAll("[data-font-size]")
        .forEach((button) => {
            const isSelected =
                button.dataset.fontSize === safeSize;

            button.classList.toggle(
                "is-active",
                isSelected
            );

            button.setAttribute(
                "aria-pressed",
                String(isSelected)
            );
        });
}
