package com.buspass.utils;

import javax.swing.JOptionPane;

public class DialogUtils {
    public static void showDialogUserAlreadyExists(String username) {
        JOptionPane.showMessageDialog(null, "User with Username: " + username + " already exists.", "Registration Failed", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showDialogRegistrationSuccess(String username) {
        JOptionPane.showMessageDialog(null, "Registration Successful with Username: " + username, "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showDialogInvalidUsername() {
        JOptionPane.showMessageDialog(null, "Invalid Username!", "Error", JOptionPane.WARNING_MESSAGE);
    }
}
