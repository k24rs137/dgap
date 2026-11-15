/* ==============================
   D-Gap redesigned pages effects
================================ */

document.addEventListener("DOMContentLoaded", () => {
    initializeDgapTiltCards();
    initializeDgapPageEntryClass();
});

function initializeDgapPageEntryClass() {
    document.body.classList.add("dgap-page-ready");
}

function initializeDgapTiltCards() {
    const cards = document.querySelectorAll("[data-tilt-card]");

    if (cards.length === 0) {
        return;
    }

    const reducedMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;

    if (reducedMotion) {
        return;
    }

    cards.forEach((card) => {
        card.addEventListener("pointermove", (event) => {
            const rect = card.getBoundingClientRect();
            const x = event.clientX - rect.left;
            const y = event.clientY - rect.top;
            const centerX = rect.width / 2;
            const centerY = rect.height / 2;
            const rotateX = ((y - centerY) / centerY) * -4;
            const rotateY = ((x - centerX) / centerX) * 4;

            card.style.transform = `perspective(900px) rotateX(${rotateX}deg) rotateY(${rotateY}deg) translateY(-4px)`;
        });

        card.addEventListener("pointerleave", () => {
            card.style.transform = "";
        });
    });
}

