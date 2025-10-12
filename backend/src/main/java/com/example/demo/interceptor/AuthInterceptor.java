package com.example.demo.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        HttpSession session = request.getSession(false); // false - не создавать новую сессию

        String username = null;
        if (session != null) {
            username = (String) session.getAttribute("currentUser");
        }

        // Устанавливаем атрибуты для Thymeleaf
        request.setAttribute("isAuthenticated", username != null);
        if (username != null) {
            request.setAttribute("currentUsername", username);
        }

        String requestURI = request.getRequestURI();

        // Публичные URL которые не требуют авторизации
        if (requestURI.equals("/") ||
                requestURI.startsWith("/auth/") ||
                requestURI.equals("/login") ||
                requestURI.startsWith("/static/") ||
                requestURI.startsWith("/css/") ||
                requestURI.startsWith("/js/") ||
                requestURI.startsWith("/images/") ||
                requestURI.equals("/error")) {
            return true;
        }

        // Проверяем авторизацию для защищенных URL
        if (username == null) {
            response.sendRedirect("/auth/login");
            return false;
        }

        return true;
    }
}