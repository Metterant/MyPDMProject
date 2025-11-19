package com.buspass.utils;

public class StringUtils {
    public static String normalizeStr(String input) {
        String normalized = input.trim().replaceAll("\\s+", " ");

        return normalized;
    }
}
