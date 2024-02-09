/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gin_payroll;

import com.mycompany.model.Employee;
import com.mycompany.model.Payroll;
import com.mycompany.utility.AlertUtils;
import com.mycompany.utility.SQLHelper;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author aavin
 */
public class AttendenceController extends GenericController implements Initializable {

    @FXML
    private Pane attendencePane;
    @FXML
    private DatePicker insertDatePicker;
    @FXML
    private TextField weekWorkHourField;
    @FXML
    private VBox employeeHeader;

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
    }

    @FXML
    private void saveHandler(ActionEvent event) {
        try {
            var date = insertDatePicker.getValue();
            var workedHour = Double.parseDouble(weekWorkHourField.getText());
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int weekNumber = date.get(weekFields.weekOfWeekBasedYear()) - 1;
            int year = date.getYear();
            if (weekNumber == 0) {
                year = (year - 1);
                weekNumber = 52;
            }
            Payroll pay = calculatePayroll(workedHour, year, weekNumber, date);
            if (pay.getEmployeeId() > 0) {
                if (createPayroll(pay)) {
                    try {
                        changeView(attendencePane, "dashboard");
                    } catch (IOException ex) {
                        AlertUtils.showErrorAlert(ex.getMessage());
                    }
                }
            }else{
                AlertUtils.showErrorAlert("Attendence already done for this week");
            }

        } catch (SQLException ex) {
            AlertUtils.showErrorAlert(ex.getMessage());
        }
    }

    private Payroll calculatePayroll(double workedHour, int year, int weekNumber, LocalDate date) throws SQLException {
        Payroll payroll = new Payroll();
        Employee emp = SQLHelper.getUser(getCurrentUser().getUserId());
        List<Payroll> payrolls = SQLHelper.getPayrollById(emp.getId());
        if (!checkPay(payrolls, weekNumber)) {
            payroll.setHoursWorked(workedHour);
            payroll.setYear(year);
            payroll.setPayWeekNum(weekNumber);
            payroll.setPayDate(date);
            payroll.setEmployeeId(emp.getId());
            // Calculate total salary based on hourly rate and hours worked
            payroll.setCurrentHourlyRate(emp.getHourlyRate());
            double totalSalary = workedHour * payroll.getCurrentHourlyRate();
            payroll.setTotalSalary(totalSalary);
            payroll.calculateIncomeTax();
            // Calculate superannuation
            payroll.calculateSuperannuation();
            // Calculate net pay
            payroll.setNetPay(totalSalary - payroll.getTax() - payroll.getSuperAnnuation());
            var lastPayroll = getlastPay(payrolls, weekNumber);
            payroll.ytdCalculation(lastPayroll);
        }
        return payroll;
    }

    public boolean checkPay(List<Payroll> payrollList, int weekNumber) {
        for (Payroll payroll : payrollList) {
            if (payroll.getPayWeekNum() == weekNumber) {
                return true;
            }
        }
        return false;
    }

    public Payroll getlastPay(List<Payroll> payrollList, int weekNumber) {
        for (Payroll payroll : payrollList) {
            if (payroll.getPayWeekNum() == (weekNumber - 1)) {
                return payroll;
            }
        }
        return null;
    }

    private boolean createPayroll(Payroll pay) throws SQLException {
        String createPayroll = "INSERT INTO payroll VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        var insertedId = SQLHelper.executeInsert(
                createPayroll, 0,
                pay.getEmployeeId(),
                pay.getHoursWorked(),
                pay.getCurrentHourlyRate(),
                pay.getTotalSalary(),
                pay.getPayDate(),
                pay.getPayWeekNum(),
                pay.getYear(),
                pay.getTax(),
                pay.getSuperAnnuation(),
                pay.getNetPay(),
                pay.getYtdTotalSalary(),
                pay.getYtdTax(),
                pay.getYtdSuperAnnuation(),
                pay.getYtdNetPay());
        return insertedId != 0;
    }
}
