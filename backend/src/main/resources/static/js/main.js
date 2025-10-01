// Основной JavaScript для главной страницы
document.addEventListener('DOMContentLoaded', function() {
    // Инициализация страницы
    initPage();
});

function initPage() {
    // Можно добавить динамическое поведение
    console.log('ЖД Мониторинг система загружена');

    // Пример: плавная прокрутка для якорей
    const links = document.querySelectorAll('a[href^="#"]');
    links.forEach(link => {
        link.addEventListener('click', smoothScroll);
    });
}

function smoothScroll(e) {
    e.preventDefault();
    const targetId = this.getAttribute('href');
    const targetElement = document.querySelector(targetId);

    if (targetElement) {
        targetElement.scrollIntoView({
            behavior: 'smooth',
            block: 'start'
        });
    }
}