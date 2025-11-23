package com.buspass.utils;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class GuiUtils {
    public static final Font GOOGLE_SANS;
    public static final Font GOOGLE_SANS_13;
    public static final Font GOOGLE_SANS_14;
    public static final Font GOOGLE_SANS_16;
    public static final Font GOOGLE_SANS_18;

    public static final Font LEXEND_MEDIUM;
    public static final Font LEXEND_MEDIUM_18;

    static {
        try (InputStream is = GuiUtils.class.getResourceAsStream("../res/fonts/GoogleSans-Regular.ttf")) {
            if (is == null) {
                throw new IOException("Resource not found: ../res/fonts/GoogleSans-Regular.ttf");
            }
            GOOGLE_SANS = Font.createFont(Font.TRUETYPE_FONT, is);

            GOOGLE_SANS_13 = GOOGLE_SANS.deriveFont(13f);
            GOOGLE_SANS_14 = GOOGLE_SANS.deriveFont(14f);
            GOOGLE_SANS_16 = GOOGLE_SANS.deriveFont(16f);
            GOOGLE_SANS_18 = GOOGLE_SANS.deriveFont(18f);


        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        } 

        try (InputStream is = GuiUtils.class.getResourceAsStream("../res/fonts/Lexend-Medium.ttf")) {
            if (is == null) {
                throw new IOException("Resource not found: ../res/fonts/Lexend-Medium.ttf");
            }
            LEXEND_MEDIUM = Font.createFont(Font.TRUETYPE_FONT, is);

            LEXEND_MEDIUM_18 = LEXEND_MEDIUM.deriveFont(18f);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        } 
    }
}
