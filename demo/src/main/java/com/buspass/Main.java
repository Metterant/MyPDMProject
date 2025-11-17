package com.buspass;

import com.buspass.db.*;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();

        System.out.println(userService.getUserById(1));
    }
}
