document.addEventListener("DOMContentLoaded", () => {
    initializeDgapScrollReveal();
    initializeDgapSmoothScroll();
    initializeDgapRippleButtons();
    initializeDgapMobileMenu();
    initializeCurrentNavigation();
    initializeDesktopMoreMenu();
});

function initializeCurrentNavigation() {
    const currentPath = window.location.pathname.replace(/\/$/, "") || "/";
    document.querySelectorAll(".desktop-nav a, .mobile-nav a, .header-search").forEach((link) => {
        const url = new URL(link.href, window.location.origin);
        const linkPath = url.pathname.replace(/\/$/, "") || "/";
        const isLearningAnchor = url.hash === "#learning";
        const isCurrent = linkPath === currentPath &&
            (currentPath !== "/" || (isLearningAnchor ? window.location.hash === "#learning" : !window.location.hash));
        if (isCurrent) link.setAttribute("aria-current", "page");
    });
}

function initializeDesktopMoreMenu() {
    const menu = document.querySelector(".desktop-more-menu");
    if (!menu) return;

    document.addEventListener("pointerdown", (event) => {
        if (!menu.contains(event.target)) menu.removeAttribute("open");
    });

    menu.addEventListener("keydown", (event) => {
        if (event.key !== "Escape") return;
        menu.removeAttribute("open");
        menu.querySelector("summary").focus();
    });
}


function initializeDgapScrollReveal() {
    const revealElements =
        document.querySelectorAll(".reveal");

    if (revealElements.length === 0) {
        return;
    }

    const reducedMotion =
        window.matchMedia(
            "(prefers-reduced-motion: reduce)"
        ).matches;

    if (reducedMotion) {
        revealElements.forEach((element) => {
            element.classList.add("is-visible");
        });

        return;
    }

    const observer =
        new IntersectionObserver(
            (entries, currentObserver) => {
                entries.forEach((entry) => {
                    if (!entry.isIntersecting) {
                        return;
                    }

                    entry.target.classList.add(
                        "is-visible"
                    );

                    currentObserver.unobserve(
                        entry.target
                    );
                });
            },
            {
                threshold: 0.15,
                rootMargin:
                    "0px 0px -50px 0px"
            }
        );

    revealElements.forEach((element) => {
        observer.observe(element);
    });
}


function initializeDgapSmoothScroll() {
    const scrollButtons =
        document.querySelectorAll(
            "[data-scroll-target]"
        );

    scrollButtons.forEach((button) => {
        button.addEventListener("click", () => {
            const selector =
                button.dataset.scrollTarget;

            if (!selector) {
                return;
            }

            const target =
                document.querySelector(selector);

            if (!target) {
                return;
            }

            target.scrollIntoView({
                behavior: "smooth",
                block: "start"
            });
        });
    });
}


function initializeDgapRippleButtons() {
    const rippleButtons =
        document.querySelectorAll(
            "[data-ripple]"
        );

    rippleButtons.forEach((button) => {
        button.addEventListener(
            "pointerdown",
            (event) => {
                const oldRipple =
                    button.querySelector(
                        ".button-ripple"
                    );

                if (oldRipple) {
                    oldRipple.remove();
                }

                const rectangle =
                    button.getBoundingClientRect();

                const ripple =
                    document.createElement("span");

                ripple.className =
                    "button-ripple";

                ripple.style.left =
                    `${event.clientX - rectangle.left}px`;

                ripple.style.top =
                    `${event.clientY - rectangle.top}px`;

                button.appendChild(ripple);

                window.setTimeout(() => {
                    ripple.remove();
                }, 700);
            }
        );
    });
}


let currentDgapLanguage = "ja";

function initializeDgapMobileMenu() {
    const menuButton =
        document.getElementById(
            "dgapMenuButton"
        );

    const mobileMenu =
        document.getElementById(
            "dgapMobileMenu"
        );

    if (!menuButton || !mobileMenu) {
        return;
    }

    menuButton.addEventListener("click", () => {
        const isOpen =
            mobileMenu.classList.toggle(
                "is-open"
            );

        menuButton.setAttribute(
            "aria-expanded",
            String(isOpen)
        );

        const translation =
            dgapHomeTranslations[
                currentDgapLanguage
            ] || dgapHomeTranslations.ja;

        menuButton.textContent =
            isOpen
                ? translation.close
                : translation.menu;
    });

    mobileMenu
        .querySelectorAll("a")
        .forEach((link) => {
            link.addEventListener(
                "click",
                () => {
                    mobileMenu.classList.remove(
                        "is-open"
                    );

                    menuButton.setAttribute(
                        "aria-expanded",
                        "false"
                    );

                    const translation =
                        dgapHomeTranslations[
                            currentDgapLanguage
                        ] || dgapHomeTranslations.ja;

                    menuButton.textContent =
                        translation.menu;
                }
            );
        });
}
