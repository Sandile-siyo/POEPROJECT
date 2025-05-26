package com.mycompany.poeproject;

import org.junit.Test;
import static org.junit.Assert.*;

public class LoginNGTest {

    Login login = new Login();

    @Test
    public void testValidUsername() {
        assertTrue("Expected valid username", login.checkUserName("abc_1"));
    }

    @Test
    public void testInvalidUsername() {
        assertFalse("Expected invalid username", login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    public void testValidPassword() {
        assertTrue("Expected valid password", login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    public void testInvalidPassword() {
        assertFalse("Expected invalid password", login.checkPasswordComplexity("password"));
    }

    @Test
    public void testValidCellNumber() {
        assertTrue("Expected valid cell number", login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    public void testInvalidCellNumber() {
        assertFalse("Expected invalid cell number", login.checkCellPhoneNumber("08966553"));
    }

    @Test
    public void testLoginSuccess() {
        assertTrue("Login should succeed", login.loginUser("abc_1", "Ch&&sec@ke99!", "abc_1", "Ch&&sec@ke99!"));
    }

    @Test
    public void testLoginFailure() {
        assertFalse("Login should fail", login.loginUser("abc_1", "wrongpass", "abc_1", "Ch&&sec@ke99!"));
    }
}
