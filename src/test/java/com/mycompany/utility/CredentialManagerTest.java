/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.utility;

import com.mycompany.model.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

/**
 *
 * @author aavin
 */
public class CredentialManagerTest {

    @Test
    public void testCheckPasswordWithMatchingPasswords() {
        String password = "password";
        String hashedPassword = CredentialManager.getHashedPassword(password);

        assertTrue(CredentialManager.checkPassword(password, hashedPassword));
    }

    @Test
    public void testCheckPasswordWithNonMatchingPasswords() {
        String password = "password";
        String hashedPassword = CredentialManager.getHashedPassword("differentPassword");

        assertFalse(CredentialManager.checkPassword(password, hashedPassword));
    }

    @Test
    public void testGetHashedPassword() {
        String password = "password";
        String hashedPassword = CredentialManager.getHashedPassword(password);

        assertNotNull(hashedPassword);
        assertNotEquals(password, hashedPassword); // Ensure that the hashed password is not the same as the original password
    }

    // You can add more tests for the `getUser` method if needed
    @Test
    public void testGetUserWithValidCredentials() throws SQLException {
        // Assuming you have valid credentials for testing
        String username = "testUser";
        String password = "testPassword";

        User user = CredentialManager.getUser(username, password);

        assertNotNull(user);
    }

    @Test
    public void testGetUserWithInvalidCredentials() throws SQLException {
        // Assuming you have invalid credentials for testing
        String username = "invalidUser";
        String password = "invalidPassword";

        User user = CredentialManager.getUser(username, password);

        assertNull(user);
    }
}
