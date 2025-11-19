package com.buspass.utils;

import org.mindrot.jbcrypt.BCrypt;

public class LoginUtils {
    private LoginUtils() {}

    public static boolean isValidUsername(String username) {
        return !username.contains(" ");
    }

    public static String hashPassword(String plain) {
        if (plain.isEmpty()) return "";
        
        String hash = BCrypt.hashpw(plain, BCrypt.gensalt());
        
        return hash;
    }

    public static boolean verify(String plain, String hash) {
        return BCrypt.checkpw(plain, hash);
    }
}

