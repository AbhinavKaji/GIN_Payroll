/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gin_payroll;

import com.mycompany.model.Employee;
import com.mycompany.utility.AlertUtils;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import com.mycompany.utility.SQLHelper;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author aavin
 */
public class EmployeeListController extends GenericController implements Initializable {

    @FXML
    private TextField filterField;

    @FXML
    private Pane employeeListPane;

    @FXML
    private VBox staffHeader;

    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private Pagination pagination;
    @FXML
    private TableColumn<Employee, String> eidColumn;
    @FXML
    private TableColumn<Employee, String> firstNameColumn;
    @FXML
    private TableColumn<Employee, Void> actionColumn;
    @FXML
    private TableColumn<Employee, String> lastNameColumn;
    @FXML
    private TableColumn<Employee, String> phoneColumn;
    private final int rowsPerPage = 6;
    private ObservableList<Employee> masterData;
    private FilteredList<Employee> filteredData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Initialize your data source (masterData) with employee records
            // You can use a method like getAllEmployees() to fetch data from a database or other source.
            masterData = FXCollections.observableArrayList(getAllEmployee());

            // Set up the table columns
            eidColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmployeeId()));
            firstNameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getFirstName()));
            lastNameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLastName()));
            phoneColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPhone()));
            actionColumn.setCellFactory(param -> new TableCell<>() {
                private final Button editButton = new Button("Edit");

                {
                    // Handle the Edit button click event
                    editButton.setOnAction(event -> {
                        Employee employee = getTableView().getItems().get(getIndex());
                        var id = employee.getUserId();
                        gotoEdit(id);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox buttons = new HBox(editButton);
                        setGraphic(buttons);
                    }
                }
            });
            // Create a filtered list based on the master data
            filteredData = new FilteredList<>(masterData, p -> true);

            // Bind the filter field to the filtered data
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(employee -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return employee.getFirstName().toLowerCase().contains(lowerCaseFilter);
                });
                updateTable();
            });

            // Set the table items to the filtered data
            employeeTable.setItems(filteredData);

            // Configure pagination
            int totalPage = (int) Math.ceil(filteredData.size() / (double) rowsPerPage);
            pagination.setPageCount(totalPage);
            pagination.setCurrentPageIndex(0);
            pagination.currentPageIndexProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                createPage(newValue.intValue());
            });
            createPage(0);
            //pagination.setPageFactory(this::createPage);
        } catch (SQLException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

    @FXML

    private void addEmployeeHandler(ActionEvent event) {
        try {
            changeView(employeeListPane, "addEditViewEmployee");
        } catch (IOException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

    @Override
    public void customInitialize(Object... obj) {
        super.customInitialize(obj);
        setDataForHeader(staffHeader, "staffHeader");
    }

    public void gotoEdit(int id) {
        try {
            Stage stage = (Stage) employeeListPane.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("addEditViewEmployee.fxml"));
            Parent root = fxmlLoader.load();
            GenericController fxmlController = fxmlLoader.getController();
            fxmlController.customInitialize(getCurrentUser(), id);
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

    private List<Employee> getAllEmployee() throws SQLException {

        String selectQuery = "Select * from user as u inner join employee as e on u.userId = e.userId";
        List<Employee> emps = SQLHelper.executeQuery(selectQuery, resultSet -> {
            Employee emp = new Employee();
            emp.setUserId(resultSet.getInt("userId"));
            emp.setUserName(resultSet.getString("userName"));
            emp.setFirstName(resultSet.getString("firstName"));
            emp.setLastName(resultSet.getString("lastName"));
            emp.setEmployeeId(resultSet.getString("employeeId"));
            emp.setPhone(resultSet.getString("phone"));
            return emp;
        });
        return emps;

    }

    private void updateTable() {
        SortedList<Employee> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());
        employeeTable.setItems(sortedData);
    }

    private TableView<Employee> createPage(int pageIndex) {
        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, filteredData.size());
        List<Employee> subListObs = masterData.subList(fromIndex, toIndex);
        ObservableList<Employee> tmpObsToSetTableVal = FXCollections.observableArrayList();

        employeeTable.setItems(null);

        for (Employee t : subListObs) {
            tmpObsToSetTableVal.add(t);
        }

        employeeTable.setItems(tmpObsToSetTableVal);

        return employeeTable;
    }
}
