/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.gin_payroll;

import com.mycompany.model.Employee;
import com.mycompany.model.Payroll;
import com.mycompany.utility.AlertUtils;
import com.mycompany.utility.Role;
import com.mycompany.utility.SQLHelper;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author aavin
 */
public class PayrollReportController extends GenericController implements Initializable {
private static final DecimalFormat df = new DecimalFormat("0.00");
    @FXML
    private DatePicker datePickerField;
    @FXML
    private Label unitLabel;
    @FXML
    private Label rateLabel;
    @FXML
    private Label totalSalaryLabel;
    @FXML
    private Label ytdTotalSalaryLabel;
    @FXML
    private Label taxLabel;
    @FXML
    private Label ytdTaxLabel;
    @FXML
    private Label supperLabel;
    @FXML
    private Label ytdSupperLabel;
    @FXML
    private Label netPayLabel;
    @FXML
    private Label ytdNetPayLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label payDateLabel;
    @FXML
    private Pane payrollReportPane;
    @FXML
    private VBox employeeHeader;
    @FXML
    private Label nameLbl;
    @FXML
    private Label addressLbl;
    @FXML
    private Label rateLbl;

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
        if(getCurrentUser().getRole() == Role.ADMIN){
                    setDataForHeader(employeeHeader, "staffHeader");
                    nameLabel.setVisible(false);
                    addressLabel.setVisible(false);
                    rateLabel.setVisible(false);
                    rateLbl.setVisible(false);
                    addressLbl.setVisible(false);
                    nameLbl.setText("Weekly Report");
        }else{
            setDataForHeader(employeeHeader, "employeeHeader");
        }
    }

    @FXML
    private void viewPayslipHandler(ActionEvent event){
        if(getCurrentUser().getRole() == Role.ADMIN){
            viewPayslipForAdmin();
        }else{
            viewPayslipForEmp();
        }
    }
    private void viewPayslipForEmp() {
        try{
        var date = datePickerField.getValue();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        int year = date.getYear();
        if (weekNumber == 0) {
            year = (year - 1);
            weekNumber = 52;
        }
        Employee emp = SQLHelper.getUser(getCurrentUser().getUserId());
        Payroll payrolls = SQLHelper.getEachPayroll(emp.getId(), weekNumber, year);
            System.out.println("aa"+payrolls+","+emp.getId()+","+weekNumber+","+year);
        if(payrolls != null){
            
            nameLabel.setText(emp.getFirstName()+" "+emp.getLastName());
            addressLabel.setText(emp.getAddress());
            unitLabel.setText(df.format(payrolls.getHoursWorked()));
            rateLabel.setText(df.format(payrolls.getCurrentHourlyRate()));
            totalSalaryLabel.setText(df.format(payrolls.getTotalSalary()));
            supperLabel.setText(df.format(payrolls.getSuperAnnuation()));
            taxLabel.setText(df.format(payrolls.getTax()));
            netPayLabel.setText(df.format(payrolls.getNetPay()));
            ytdTotalSalaryLabel.setText(df.format(payrolls.getYtdTotalSalary()));
            ytdSupperLabel.setText(df.format(payrolls.getYtdSuperAnnuation()));
            ytdTaxLabel.setText(df.format(payrolls.getYtdTax()));
            ytdNetPayLabel.setText(df.format(payrolls.getYtdNetPay()));
            payDateLabel.setText(payrolls.getPayDate().toString());
        }
        }catch(Exception e){
                            AlertUtils.showErrorAlert("View Payroll :" + e.getMessage());

        }
    }
    
    private void viewPayslipForAdmin() {
        try{
        var date = datePickerField.getValue();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        int year = date.getYear();
        if (weekNumber == 0) {
            year = (year - 1);
            weekNumber = 52;
        }
        List<Payroll> payrolls = SQLHelper.getPayroll(weekNumber, year);
        if(payrolls != null){
            Payroll tempPayroll = new Payroll();
            for (Payroll payroll : payrolls) {
                tempPayroll.setHoursWorked(payroll.getHoursWorked()+ tempPayroll.getHoursWorked());
                tempPayroll.setTotalSalary(payroll.getTotalSalary()+ tempPayroll.getTotalSalary());
                tempPayroll.setSuperAnnuation(payroll.getSuperAnnuation()+ tempPayroll.getSuperAnnuation());
                tempPayroll.setTax(payroll.getTax()+ tempPayroll.getTax());
                tempPayroll.setNetPay(payroll.getNetPay()+ tempPayroll.getNetPay());
                tempPayroll.setYtdTotalSalary(payroll.getYtdTotalSalary()+ tempPayroll.getYtdTotalSalary());
                tempPayroll.setYtdSuperAnnuation(payroll.getYtdSuperAnnuation()+ tempPayroll.getYtdSuperAnnuation());
                tempPayroll.setYtdTax(payroll.getYtdTax()+ tempPayroll.getYtdTax());
                tempPayroll.setYtdNetPay(payroll.getYtdNetPay()+ tempPayroll.getYtdNetPay());
            }
            tempPayroll.setPayDate(payrolls.get(0).getPayDate());
            unitLabel.setText(df.format(tempPayroll.getHoursWorked()));
            totalSalaryLabel.setText(df.format(tempPayroll.getTotalSalary()));
            supperLabel.setText(df.format(tempPayroll.getSuperAnnuation()));
            taxLabel.setText(df.format(tempPayroll.getTax()));
            netPayLabel.setText(df.format(tempPayroll.getNetPay()));
            ytdTotalSalaryLabel.setText(df.format(tempPayroll.getYtdTotalSalary()));
            ytdSupperLabel.setText(df.format(tempPayroll.getYtdSuperAnnuation()));
            ytdTaxLabel.setText(df.format(tempPayroll.getYtdTax()));
            ytdNetPayLabel.setText(df.format(tempPayroll.getYtdNetPay()));
            payDateLabel.setText(tempPayroll.getPayDate().toString());
        }
        }catch(Exception e){
                            AlertUtils.showErrorAlert("View Payroll :" + e.getMessage());

        }
    }
}
