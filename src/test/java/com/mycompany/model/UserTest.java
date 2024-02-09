/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.model;

import com.mycompany.utility.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(1, "testUser", "kailash", "Niraula", "password123", Role.ADMIN);
    }

    @Test
    public void testGetUserId() {
        assertEquals(1, user.getUserId());
    }

    @Test
    public void testSetUserId() {
        user.setUserId(2);
        assertEquals(2, user.getUserId());
    }

    @Test
    public void testGetUserName() {
        assertEquals("testUser", user.getUserName());
    }

    @Test
    public void testSetUserName() {
        user.setUserName("abhinav");
        assertEquals("abhinav", user.getUserName());
    }

    @Test
    public void testGetFirstName() {
        assertEquals("kailash", user.getFirstName());
    }

    @Test
    public void testSetFirstName() {
        user.setFirstName("aavinav");
        assertEquals("aavinav", user.getFirstName());
    }

    @Test
    public void testGetLastName() {
        assertEquals("Niraula", user.getLastName());
    }

    @Test
    public void testSetLastName() {
        user.setLastName("paudel");
        assertEquals("paudel", user.getLastName());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newPassword123");
        assertEquals("newPassword123", user.getPassword());
    }

    @Test
    public void testGetRole() {
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    public void testSetRole() {
        user.setRole(Role.ADMIN);
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    public void testToString() {
        String expected = "User{userId=1, userName=testUser, firstName=kailash, lastName=Niraula, password=password123, role=ADMIN}";
        assertEquals(expected, user.toString());
    }
}
