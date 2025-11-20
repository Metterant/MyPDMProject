package com.buspass.auth;

import com.buspass.queries.UserService;
import com.buspass.utils.AuthUtils;

public class UserRegister {
    
    /**
     * Attempt to create a new user with a Username and UserPassword
     * @param username
     * @param plainPassword
     * @param plainConfirmPassword
     * @return -1 If the username is invalid, -2 if both passwords are not identical, -3 if passwords are invalid, 0 if the
     * User creation fails, and 1 if the operation is successful
     */
    public int attemptRegister(String username, String plainPassword, String plainConfirmPassword) {
        if (!AuthUtils.isValidUsername(username))
            return -1;
        
        if (!AuthUtils.isValidPassword(plainPassword))
            return -3;

        if (plainPassword.compareTo(plainConfirmPassword) != 0)
            return -2;

        UserService userService = new UserService();

        boolean hasRegistered = userService.registerUser(username, plainPassword);

        return (hasRegistered)? 1 : 0;
    }
}
