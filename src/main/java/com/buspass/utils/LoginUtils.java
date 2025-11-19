package com.buspass.utils;

import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

public class LoginUtils {
    private LoginUtils() {}

    public static boolean isValidUsername(String username) {
        Pattern p = Pattern.compile("^(?![_.-])(?!.*[_.-]{2})(?!.*\\.$)[A-Za-z0-9._-]{3,30}$");
        boolean ok = p.matcher(username).matches();

        // Password restrictions:
        // - "Username must be 3–30 characters."
        // - "Only letters, numbers, . _ and - are allowed."
        // - "Username cannot start or end with ., _ or -."
        // - "Please avoid consecutive punctuation like .. or -_."
        // - "Duplicate Usernames are not allowed"

        return ok;
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

