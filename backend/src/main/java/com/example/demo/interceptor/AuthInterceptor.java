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

        // Публичные URL которые не требуют авторизации
        if (requestURI.equals("/") ||  // Главная страница доступна всем
                requestURI.startsWith("/auth/") ||
                requestURI.equals("/login") ||
                requestURI.startsWith("/static/") ||
                requestURI.startsWith("/css/") ||
                requestURI.equals("/error")) {
            return true;
        }

        // Проверяем авторизацию для защищенных URL
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("currentUser");

        if (username == null) {
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }
}