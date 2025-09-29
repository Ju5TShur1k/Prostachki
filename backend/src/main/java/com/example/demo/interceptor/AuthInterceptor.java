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

        String requestURI = request.getRequestURI();

        // Разрешить доступ к главной странице без авторизации
        if (requestURI.equals("/") ||
                requestURI.startsWith("/auth/") ||
                requestURI.equals("/login") ||
                requestURI.startsWith("/static/") ||
                requestURI.startsWith("/css/")) {
            return true; // пропускаем без проверки авторизации
        }

        // Проверяем авторизацию только для защищенных URL
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("currentUser");

        if (username == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}