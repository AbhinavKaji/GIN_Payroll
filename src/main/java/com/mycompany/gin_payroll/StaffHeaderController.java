/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gin_payroll;

import com.mycompany.utility.AlertUtils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author aavin
 */
public class StaffHeaderController extends GenericController implements Initializable {

    @FXML
    private Label loginUserName;
    @FXML
    private Pane staffHeaderPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void customInitialize(Object... obj) {
        super.customInitialize(obj);
        loginUserName.setText(getCurrentUser().getUserName());
    }

    @FXML
    private void logoutHandler(ActionEvent event) {
        try {
            changeView(staffHeaderPane, "login");
        } catch (IOException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

    @FXML
    private void employeeHandler(ActionEvent event) {
        try {
            changeView(staffHeaderPane, "employeeList");
        } catch (IOException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

    @FXML
    private void payslipHandler(ActionEvent event) {
        try {
            changeView(staffHeaderPane, "payrollReport");
        } catch (IOException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

    @FXML
    private void dashboardHandler(ActionEvent event) {
        try {
            changeView(staffHeaderPane, "staffDashboard");
        } catch (IOException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }
}
