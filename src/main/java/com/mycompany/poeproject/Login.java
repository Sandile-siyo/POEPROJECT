// Fixed Login.java
package com.mycompany.poeproject;

public class Login {

    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellphone;

    // Checks if the username contains an underscore and is no more than 5 characters
    public boolean checkUserName(String username) {
        return username.contains("_") && username.length() <= 5;
    }

    // Checks if the password meets complexity requirements
    public boolean checkPasswordComplexity(String password) {
        return password.length() >= 8 &&
               password.matches(".*[A-Z].*") &&
               password.matches(".*[a-z].*") &&
               password.matches(".*[0-9].*") &&
               password.matches(".*[!@#$%^&*()_+<>:{}\\[\\]\\\\\\-].*");
    }

    // Checks if the cellphone number starts with +27 and contains 9 digits after it
    public boolean checkCellPhoneNumber(String cellphoneNumber) {
        return cellphoneNumber.matches("^\\+27\\d{9}$");
    }

    // Optionally store user details if needed later
    public void registerUser(String username, String password, String cellphone) {
        this.registeredUsername = username;
        this.registeredPassword = password;
        this.registeredCellphone = cellphone;
    }

    // Validates login against stored values
    public boolean loginUser(String username, String password, String registeredUser, String registeredPass) {
        return username.equals(registeredUser) && password.equals(registeredPass);
    }

    // Message for successful or failed login
    public String returnLoginStatus(String username, String password, String registeredUser, String registeredPass) {
        if (loginUser(username, password, registeredUser, registeredPass)) {
            return "Welcome " + username + ", it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
