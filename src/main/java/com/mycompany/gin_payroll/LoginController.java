package com.mycompany.gin_payroll;

import com.mycompany.utility.AlertUtils;
import com.mycompany.utility.Role;
import com.mycompany.utility.Session;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class LoginController extends GenericController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordFields;
    @FXML
    private Label messageLabel;

    @FXML private Pane loginPane;
    
    private Session session;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        session = new Session();
        messageLabel.setText("");
    }

    @FXML
    private void LoginHandler(ActionEvent event) {
        
        String username = usernameField.getText();
        String password = passwordFields.getText();
        try{
        if (session.login(username, password)) {
            messageLabel.setText("Login Success");
            customInitialize(session.getCurrentUser());
            if (getCurrentUser().getRole() == Role.EMPLOYEE) {
                changeView(loginPane, "dashboard");
            } else if (getCurrentUser().getRole() == Role.ADMIN) {
                changeView(loginPane,"staffDashboard");
            }
        } else {
                            AlertUtils.showErrorAlert("Invalid username or password.");

        }
        }catch(IOException | SQLException ex){
                            AlertUtils.showErrorAlert(ex.getMessage());

        }
    }
}

