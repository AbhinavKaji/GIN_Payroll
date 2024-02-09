/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.utility;

import com.mycompany.model.User;
import static com.mycompany.utility.SQLHelper.executeQuery;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author aavin
 */
public class CredentialManager {

    public static boolean checkPassword(String password, String hashPassword) {
        return BCrypt.checkpw(password, hashPassword);
    }

    public static String getHashedPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static User getUser(String username, String password) throws SQLException {
        String selectQuery = "SELECT * FROM user WHERE username = ? LIMIT 1";
        List<User> users = new ArrayList<>();
        User user = null;
            users = executeQuery(selectQuery, resultSet -> {
                int id = resultSet.getInt("userId");
                String userName = resultSet.getString("userName");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String hashPassword = resultSet.getString("password");
                Role role = Role.fromDbValue(resultSet.getString("role"));
                return new User(id, userName, firstName, lastName, hashPassword, role);
            }, username);
            
        if (!users.isEmpty()) {
            if (checkPassword(password, users.get(0).getPassword())) {
                user = users.get(0);
            }
        }
        return user;

    }
}
