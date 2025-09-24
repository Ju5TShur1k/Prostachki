package com.example.demo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SimplePasswordHasher {

    private static final int SALT_LENGTH = 16;
    private static final String ALGORITHM = "SHA-256";

    public static String hashPassword(String password) {
        try {
            // Генерируем случайную соль
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            // Хешируем пароль с солью
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Комбинируем соль и хеш для хранения
            byte[] combined = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashedPassword, 0, combined, salt.length, hashedPassword.length);

            return Base64.getEncoder().encodeToString(combined);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка при хешировании пароля", e);
        }
    }

    public static boolean verifyPassword(String password, String storedHash) {
        try {
            // Декодируем stored hash из Base64
            byte[] combined = Base64.getDecoder().decode(storedHash);

            // Извлекаем соль (первые 16 байт)
            byte[] salt = new byte[SALT_LENGTH];
            System.arraycopy(combined, 0, salt, 0, salt.length);

            // Хешируем введенный пароль с той же солью
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Сравниваем с stored hash (последние 32 байта)
            for (int i = 0; i < hashedPassword.length; i++) {
                if (hashedPassword[i] != combined[salt.length + i]) {
                    return false;
                }
            }
            return true;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка при проверке пароля", e);
        }
    }

    // Вспомогательный метод для тестирования
    public static void main(String[] args) {
        String password = "myPassword123";
        String hashed = hashPassword(password);
        System.out.println("Original: " + password);
        System.out.println("Hashed: " + hashed);
        System.out.println("Verify: " + verifyPassword(password, hashed));
        System.out.println("Verify wrong: " + verifyPassword("wrongPassword", hashed));
    }
}