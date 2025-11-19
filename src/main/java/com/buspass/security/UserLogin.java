package com.buspass.security;

import java.util.Map;
import java.util.List;

import com.buspass.db.QueryExecutionModule;
import com.buspass.queries.UserService;
import com.buspass.utils.LoginUtils;

public class UserLogin {
    private int userId;
    private int userRoleId;
    private int age;
    private String username;
    private String fullName;
    private String address;
    private String phoneNumber;

    UserService userService = new UserService();

    public int getUserId()         { return userId; }
    public int getUserRoleId()     { return userRoleId; }
    public int getAge()            { return age; }
    public String getUsername()    { return username; }
    public String getFullName()    { return fullName; }
    public String getAddress()     { return address; }
    public String getPhoneNumber() { return phoneNumber;}


    private void setUserId(int userId)              { this.userId = userId; }
    private void setUserRoleId(int userRoleId)      { this.userRoleId = userRoleId; }
    private void setAge(int age)                    { this.age = age; }
    private void setUsername(String username)       { this.username = username; }
    private void setFullName(String fullName)       { this.fullName = fullName; }
    private void setAddress(String address)         { this.address = address; }
    private void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber;}
    
    
    /**
     * Return the result of the User login
     * @param userId - The UserID of the User
     * @param plainPW - Password in plaintext
     * @return -1 if the User doesn't exist, 0 if the password was incorrect, and 1 if the login was successful 
     */
    public int attemptLogin(String username, String plainPW) {
        // Fetch stored hash and role id for the user
        List<Map<String, Object>> results;
        results = QueryExecutionModule.executeQuery(
            "SELECT UserID, UserPassword, FullName, Age, Phone, UserAddress, UserRoleID FROM `User` WHERE Username = ?", 
            username
        );

        if (results == null || results.isEmpty()) {
            // user not found
            return -1;
        }

        Map<String, Object> user = results.get(0);
        Object pwObj = user.get("UserPassword");
        if (pwObj == null) {
            return 0; // no password set
        }

        String hashedPW = pwObj.toString();

        // IMPORTANT: BCrypt generates a different hash each time because it uses a random salt.
        // To verify a password, use BCrypt.checkpw(plainPassword, storedHash) instead of
        // hashing the plaintext again.
        boolean matches = false;
        try {
            matches = LoginUtils.verify(plainPW, hashedPW);
        } 
        catch (Exception e) {
            // any parsing/format issue with the stored hash
            matches = false;
        }

        if (matches) {
            Object userIdObj   = user.get("UserID");
            Object roleObj     = user.get("UserRoleID");
            Object ageObj      = user.get("Age");
            Object fullNameObj = user.get("FullName");
            Object addressObj  = user.get("UserAddress");
            Object phoneObj    = user.get("Phone");
            
            setUsername(username);
            
            if (userIdObj != null)
                setUserId(Integer.parseInt(roleObj.toString()));
            if (roleObj != null)
                setUserRoleId(Integer.parseInt(roleObj.toString()));
            if (ageObj != null)
                setAge(Integer.parseInt(ageObj.toString()));
            if (fullNameObj != null)
                setFullName(fullNameObj.toString());
            if (addressObj != null)
                setAddress(addressObj.toString());
            if (phoneObj != null)
                setPhoneNumber(phoneObj.toString());
            
            return 1;
        }

        return 0;
    }
}
