package com.mycompany.poeproject;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginNGTest {

    @Test
    public void testValidUsername() {
        Login login = new Login();
        assertTrue(login.checkUserName("abc_1"));
    }

    @Test
    public void testInvalidUsername() {
        Login login = new Login();
        assertFalse(login.checkUserName("abcdef"));
    }

    @Test
    public void testValidPassword() {
        Login login = new Login();
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testInvalidPassword() {
        Login login = new Login();
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testValidCellPhone() {
        Login login = new Login();
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testInvalidCellPhone() {
        Login login = new Login();
        assertFalse(login.checkCellPhoneNumber("083"));
    }

    @Test
    public void testLogin_Success() {
        Login login = new Login();
        assertTrue(login.loginUser("user_1", "Pass@123", "user_1", "Pass@123"));
    }

    @Test
    public void testLogin_Fail() {
        Login login = new Login();
        assertFalse(login.loginUser("user_1", "wrongpass", "user_1", "Pass@123"));
    }
}
