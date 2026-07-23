document.addEventListener('DOMContentLoaded', () => {
    const menuBtn = document.getElementById('userMenuBtn');
    const userMenu = document.getElementById('userMenu');

    if (menuBtn && userMenu) {
        menuBtn.addEventListener('click', (e) => {
            e.stopPropagation();
            userMenu.classList.toggle('show');
        });

        document.addEventListener('click', (e) => {
            if (!userMenu.contains(e.target) && !menuBtn.contains(e.target)) {
                userMenu.classList.remove('show');
            }
        });
    }
});