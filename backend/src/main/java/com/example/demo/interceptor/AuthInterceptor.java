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

        // Список публичных URL (должен совпадать с excludePathPatterns)
        if (requestURI.startsWith("/auth/") ||
                requestURI.equals("/login") ||
                requestURI.equals("/logout") ||
                requestURI.startsWith("/static/") ||
                requestURI.startsWith("/css/") ||
                requestURI.equals("/error") ||
                requestURI.equals("/favicon.ico")) {
            return true; // пропускаем публичные URL
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