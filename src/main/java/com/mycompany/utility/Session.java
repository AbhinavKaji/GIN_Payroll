/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.utility;

import com.mycompany.model.User;
import java.sql.SQLException;

/**
 *
 * @author aavin
 */
public class Session {

    private User currentUser;

    /**
     * login function
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public boolean login(String username, String password) throws SQLException {
        currentUser = CredentialManager.getUser(username, password);
        return currentUser != null;
    }

    /**
     * logout method
     */
    public void logout() {
        currentUser = null;
        System.out.println("Logged out.");
    }

    /**
     * getter for logged in user
     * @return
     */
    public User getCurrentUser() {
        return currentUser;
    }
}
