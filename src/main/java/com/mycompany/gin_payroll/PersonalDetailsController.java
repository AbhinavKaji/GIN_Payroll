/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gin_payroll;

import com.mycompany.model.Bank;
import com.mycompany.model.Employee;
import com.mycompany.model.Superannuation;
import com.mycompany.utility.AlertUtils;
import com.mycompany.utility.SQLHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author aavin
 */
public class PersonalDetailsController extends GenericController implements Initializable {

    @FXML
    private Pane employeeDetailsPane;
    @FXML
    private VBox employeeHeader;
    @FXML
    private TextField nameField;
    @FXML
    private TextField empIdField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField hourlyRateField;
    @FXML
    private TextField bankNameField;
    @FXML
    private TextField bsbField;
    @FXML
    private TextField accNumField;
    @FXML
    private TextField payIdField;
    @FXML
    private TextField tfnField;
    @FXML
    private TextField supperField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField memberNumField;
    @FXML
    private TextField usiNumField;

    private int userId = 0;
    private int empId = 0;
    private int supperId = 0;
    private int bankId = 0;

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
        setDataForHeader(employeeHeader, "employeeHeader");
        getPersonalDetails(getCurrentUser().getUserId());
    }

    @FXML
    private void updateHandler(ActionEvent event) {
        try {
            if (updateEmployee()) {

                changeView(employeeDetailsPane, "dashboard");

            }
        } catch (IOException | SQLException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

    @FXML
    private void cancelHandler(ActionEvent event) {
        try {
            changeView(employeeDetailsPane, "dashboard");
        } catch (IOException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

    private boolean updateEmployee() throws SQLException {

        String createEmployee = "UPDATE employee SET address = ?,phone = ?, email = ? WHERE userId = ?";
        var empsId = SQLHelper.executeUpdate(createEmployee, addressField.getText(), phoneField.getText(), emailField.getText(), userId);
        String createBank = "UPDATE bank SET bankName = ?, bsbNumber = ?, accNumber = ?, payId = ?, tfnNumber = ? WHERE employeeId = ? ";
        var banksId = SQLHelper.executeUpdate(createBank, bankNameField.getText(), bsbField.getText(), accNumField.getText(), payIdField.getText(), tfnField.getText(), empId);
        String createSuper = "UPDATE superannuation SET superName = ?, memberNumber = ?, usiNumber = ? WHERE employeeId = ?";
        var supersId = SQLHelper.executeUpdate(createSuper, supperField.getText(), memberNumField.getText(), usiNumField.getText(), empId);
        return empsId != 0 && banksId != 0 && supersId != 0;

    }

    private void getPersonalDetails(int id) {
        try {
            userId = id;
            Employee emp = SQLHelper.getUser(id);
            empId = emp.getId();
            Bank bank = SQLHelper.getBank(emp.getId());
            bankId = bank.getId();
            Superannuation supper = SQLHelper.getSuper(emp.getId());
            supperId = supper.getId();
            nameField.setText(emp.getFirstName() + " " + emp.getLastName());
            empIdField.setText(emp.getEmployeeId());
            phoneField.setText(emp.getPhone());
            emailField.setText(emp.getEmail());
            addressField.setText(emp.getAddress());
            hourlyRateField.setText(Double.toString(emp.getHourlyRate()));
            bankNameField.setText(bank.getBankName());
            bsbField.setText(Integer.toString(bank.getBsbNumber()));
            accNumField.setText(Integer.toString(bank.getAccNumber()));
            payIdField.setText(Integer.toString(bank.getPayId()));
            tfnField.setText(Integer.toString(bank.getTfnNumber()));
            supperField.setText(supper.getSuperName());
            memberNumField.setText(Integer.toString(supper.getMemberNumber()));
            usiNumField.setText(Integer.toString(supper.getUsiNumber()));
        } catch (SQLException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }
}
