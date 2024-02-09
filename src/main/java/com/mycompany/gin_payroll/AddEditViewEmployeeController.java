/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gin_payroll;

import com.mycompany.model.Bank;
import com.mycompany.model.Employee;
import com.mycompany.model.Superannuation;
import com.mycompany.utility.AlertUtils;
import com.mycompany.utility.CredentialManager;
import com.mycompany.utility.Role;
import com.mycompany.utility.SQLHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author aavin
 */
public class AddEditViewEmployeeController extends GenericController implements Initializable {

    @FXML
    private VBox staffHeader;
    @FXML
    private Pane addEditViewEmployeePane;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
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
    private TextField payidField;
    @FXML
    private TextField tfnField;
    @FXML
    private TextField superNameField;
    @FXML
    private TextField memberNumField;
    @FXML
    private TextField usiNumField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField employeeIdField;
    @FXML
    private Button updateButton;
    @FXML
    private Button createButton;

    private int userId = 0;
    private int empId = 0;
    private int supperId = 0;
    private int bankId = 0;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void customInitialize(Object... obj) {
        super.customInitialize(obj);
        setDataForHeader(staffHeader, "staffHeader");
        if (obj.length > 1) {
            if ((int) obj[1] != 0) {
                viewEmployeeInit((int) obj[1]);
            }
        } else {
            createEmployeeInit();
        }
    }

    @FXML
    private void updateEmployeeHandler(ActionEvent event) {
        try {
            if (updateEmployee()) {
                changeView(addEditViewEmployeePane, "employeeList");

            }
        } catch (SQLException | IOException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

    @FXML
    private void createEmployeeHandler(ActionEvent event) {
        try {
            if (!checkEmployeeIdDuplicate(employeeIdField.getText())) {
                AlertUtils.showErrorAlert("duplicate employeeId");
            } else if (!checkUserNameDuplicate(userNameField.getText())) {
                AlertUtils.showErrorAlert("duplicate userName");
            } else {
                if (createEmployee()) {
                    changeView(addEditViewEmployeePane, "employeeList");
                }
            }

        } catch (SQLException | IOException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

    private void createEmployeeInit() {
        updateButton.setDisable(true);
        createButton.setDisable(false);
    }

    private void viewEmployeeInit(int id) {
        try {
            passwordField.setDisable(true);
            updateButton.setDisable(false);
            createButton.setDisable(true);
            userId = id;
            Employee emp = SQLHelper.getUser(id);
            empId = emp.getId();
            Bank bank = SQLHelper.getBank(emp.getId());
            bankId = bank.getId();
            Superannuation supper = SQLHelper.getSuper(emp.getId());
            supperId = supper.getId();
            userNameField.setText(emp.getUserName());
            firstNameField.setText(emp.getFirstName());
            lastNameField.setText(emp.getLastName());
            employeeIdField.setText(emp.getEmployeeId());
            phoneField.setText(emp.getPhone());
            hourlyRateField.setText(Double.toString(emp.getHourlyRate()));
            bankNameField.setText(bank.getBankName());
            bsbField.setText(Integer.toString(bank.getBsbNumber()));
            accNumField.setText(Integer.toString(bank.getAccNumber()));
            payidField.setText(Integer.toString(bank.getPayId()));
            tfnField.setText(Integer.toString(bank.getTfnNumber()));
            superNameField.setText(supper.getSuperName());
            memberNumField.setText(Integer.toString(supper.getMemberNumber()));
            usiNumField.setText(Integer.toString(supper.getUsiNumber()));
        } catch (SQLException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

    private boolean checkEmployeeIdDuplicate(String empId) throws SQLException {
        String selectQuery = "SELECT * FROM employee WHERE employeeId = ?";
        var result = SQLHelper.getQuery(selectQuery, resultSet -> {
            return resultSet != null;
        }, empId);
        return result == null;
    }

    private boolean checkUserNameDuplicate(String userName) throws SQLException {
        String selectQuery = "SELECT * FROM user WHERE userName = ?";
        var result = SQLHelper.getQuery(selectQuery, resultSet -> {
            return resultSet.getInt("userId");
        }, userName);
        return result == null;
    }

    private boolean createEmployee() throws SQLException {

        String createUser = "INSERT INTO user VALUES (?,?,?,?,?,?)";
        var insertedId = SQLHelper.executeInsert(createUser, 0, userNameField.getText(), firstNameField.getText(), lastNameField.getText(), CredentialManager.getHashedPassword(passwordField.getText()), Role.EMPLOYEE.getDbValue());
        String createEmployee = "INSERT INTO employee VALUES (?,?,?,?,?,?,?)";
        var empsId = SQLHelper.executeInsert(createEmployee, 0, insertedId, employeeIdField.getText(), "", phoneField.getText(), hourlyRateField.getText(), "");
        String createBank = "INSERT INTO bank VALUES (?,?,?,?,?,?,?)";
        var banksId = SQLHelper.executeInsert(createBank, 0, bankNameField.getText(), bsbField.getText(), accNumField.getText(), payidField.getText(), tfnField.getText(), empsId);
        String createSuper = "INSERT INTO superannuation VALUES (?,?,?,?,?)";
        var supersId = SQLHelper.executeInsert(createSuper, 0, superNameField.getText(), memberNumField.getText(), usiNumField.getText(), empsId);
        return insertedId != 0 && empsId != 0 && banksId != 0 && supersId != 0;

    }

    private boolean updateEmployee() throws SQLException {

        String createUser = "UPDATE user SET userName = ?,firstName = ?,lastName = ? WHERE userId = ?";
        var insertedId = SQLHelper.executeUpdate(createUser, userNameField.getText(), firstNameField.getText(), lastNameField.getText(), userId);
        String createEmployee = "UPDATE employee SET employeeId = ?,address = ?,phone = ?,hourlyRate = ?, email = ? WHERE userId = ?";
        var empsId = SQLHelper.executeUpdate(createEmployee, employeeIdField.getText(), "", phoneField.getText(), hourlyRateField.getText(), "", userId);
        String createBank = "UPDATE bank SET bankName = ?, bsbNumber = ?, accNumber = ?, payId = ?, tfnNumber = ? WHERE employeeId = ? ";
        var banksId = SQLHelper.executeUpdate(createBank, bankNameField.getText(), bsbField.getText(), accNumField.getText(), payidField.getText(), tfnField.getText(), empId);
        String createSuper = "UPDATE superannuation SET superName = ?, memberNumber = ?, usiNumber = ? WHERE employeeId = ?";
        var supersId = SQLHelper.executeUpdate(createSuper, superNameField.getText(), memberNumField.getText(), usiNumField.getText(), empId);
        return insertedId != 0 && empsId != 0 && banksId != 0 && supersId != 0;

    }

    @FXML
    private void clearHandler(ActionEvent event) {
        userId = 0;
        empId = 0;
        bankId = 0;
        supperId = 0;
        createEmployeeInit();
        userNameField.setText(null);
        firstNameField.setText(null);
        lastNameField.setText(null);
        employeeIdField.setText(null);
        phoneField.setText(null);
        hourlyRateField.setText(null);
        bankNameField.setText(null);
        bsbField.setText(null);
        accNumField.setText(null);
        payidField.setText(null);
        tfnField.setText(null);
        superNameField.setText(null);
        memberNumField.setText(null);
        usiNumField.setText(null);
        passwordField.setDisable(false);
    }

    @FXML
    private void cancelHandler(ActionEvent event) {
        try {
            changeView(addEditViewEmployeePane, "employeeList");
        } catch (IOException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

}
