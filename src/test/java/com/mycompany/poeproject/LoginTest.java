package com.mycompany.poeproject;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTest {

    Login login = new Login();

    @Test
    public void testValidUsername() {
        // Username must contain an underscore and be 5 characters or fewer
        String username = "abc_1";
        assertTrue("Expected valid username", login.checkUserName(username));
    }

    @Test
    public void testInvalidUsername() {
        // Invalid because it doesn't have underscore or is too long
        String username = "kyle!!!!!!!";
        assertFalse("Expected invalid username", login.checkUserName(username));
    }

    @Test
    public void testValidPassword() {
        // Meets all password complexity requirements
        String password = "Ch&&sec@ke99!";
        assertTrue("Expected valid password", login.checkPasswordComplexity(password));
    }

    @Test
    public void testInvalidPassword() {
        // Fails complexity rules (no uppercase, no number, no special char, too short)
        String password = "password";
        assertFalse("Expected invalid password", login.checkPasswordComplexity(password));
    }

    @Test
    public void testValidCellNumber() {
        // Valid SA cell number with +27 and 9 digits
        String cell = "+27838968976";
        assertTrue("Expected valid cell number", login.checkCellPhoneNumber(cell));
    }

    @Test
    public void testInvalidCellNumber() {
        // Invalid format (missing +27)
        String cell = "08966553";
        assertFalse("Expected invalid cell number", login.checkCellPhoneNumber(cell));
    }

    @Test
    public void testLoginSuccess() {
        // Login should succeed when username and password match
        String username = "abc_1";
        String password = "Ch&&sec@ke99!";
        assertTrue("Login should succeed", login.loginUser(username, password, username, password));
    }

    @Test
    public void testLoginFailure() {
        // Login should fail when passwords don't match
        String username = "abc_1";
        String password = "wrongpass";
        assertFalse("Login should fail", login.loginUser(username, password, username, "Ch&&sec@ke99!"));
    }

    @Test
    public void testReturnLoginStatusSuccess() {
        String result = login.returnLoginStatus("abc_1", "Ch&&sec@ke99!", "abc_1", "Ch&&sec@ke99!");
        assertEquals("Welcome abc_1, it is great to see you again.", result);
    }

    @Test
    public void testReturnLoginStatusFailure() {
        String result = login.returnLoginStatus("abc_1", "wrongpass", "abc_1", "Ch&&sec@ke99!");
        assertEquals("Username or password incorrect, please try again.", result);
    }
}