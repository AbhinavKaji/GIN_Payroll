package com.mycompany.utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {

    private Session session;

    @BeforeEach
    public void setUp() {
        session = new Session();
    }

    @Test
    public void testLoginWithValidCredentials() throws SQLException {
        // Assuming you have a valid username and password for testing
        String username = "testUser";
        String password = "testPassword";

        assertTrue(session.login(username, password));
        assertNotNull(session.getCurrentUser());
    }

    @Test
    public void testLoginWithInvalidCredentials() throws SQLException {
        // Assuming you have invalid credentials for testing
        String username = "invalidUser";
        String password = "invalidPassword";

        assertFalse(session.login(username, password));
        assertNull(session.getCurrentUser());
    }

    @Test
    public void testLogout() throws SQLException{
        // Login a user before testing logout
        session.login("testUser", "testPassword");

        assertNotNull(session.getCurrentUser());

        session.logout();
        
        assertNull(session.getCurrentUser());
    }
}
