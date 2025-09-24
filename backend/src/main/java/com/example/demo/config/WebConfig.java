package com.example.demo.config;

import com.example.demo.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/auth/**",           // регистрация и верификация
                        "/login",             // страница входа
                        "/logout",            // выход
                        "/static/**",         // статические файлы
                        "/css/**",            // CSS файлы
                        "/error",             // страницы ошибок
                        "/favicon.ico"        // иконка сайта
                );
    }
}