/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import java.time.LocalDate;

/**
 *
 * @author aavin
 */
public class Payroll {

    private int payrollId;
    private int employeeId;
    private double hoursWorked;
    private double CurrentHourlyRate;
    private double totalSalary;
    private LocalDate payDate;
    private int payWeekNum;
    private int year;
    private double tax;
    private double superAnnuation;
    private double netPay;
    private double ytdTotalSalary;
    private double ytdTax;
    private double ytdSuperAnnuation;
    private double ytdNetPay;

    public Payroll() {
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Payroll(int payrollId, int employeeId, double hoursWorked, double CurrentHourlyRate, double totalSalary, LocalDate payDate, int payWeekNum, int year, double tax, double superAnnuation, double netPay, double ytdTotalSalary, double ytdTax, double ytdSuperAnnuation, double ytdNetPay) {
        this.payrollId = payrollId;
        this.employeeId = employeeId;
        this.hoursWorked = hoursWorked;
        this.CurrentHourlyRate = CurrentHourlyRate;
        this.totalSalary = totalSalary;
        this.payDate = payDate;
        this.payWeekNum = payWeekNum;
        this.year = year;
        this.tax = tax;
        this.superAnnuation = superAnnuation;
        this.netPay = netPay;
        this.ytdTotalSalary = ytdTotalSalary;
        this.ytdTax = ytdTax;
        this.ytdSuperAnnuation = ytdSuperAnnuation;
        this.ytdNetPay = ytdNetPay;
    }

    public void calculateIncomeTax() {
        // Define tax brackets and rates for the 2021-2022 financial year in NSW
        double[] incomeThresholds = {0, 350, 700, 2200, Double.MAX_VALUE};
        double[] taxRates = {0, 0.19, 0.325, 0.37, 0.45};

        double tempTax = 0.0;

        for (int i = 1; i < incomeThresholds.length; i++) {
            if (totalSalary <= incomeThresholds[i]) {
                tempTax += (totalSalary - incomeThresholds[i - 1]) * taxRates[i];
                break;
            } else {
                tempTax += (incomeThresholds[i] - incomeThresholds[i - 1]) * taxRates[i];
            }
        }

        this.tax = tempTax;
    }

    public void ytdCalculation(Payroll lastPayroll) {
        if (lastPayroll == null) {
            setYtdTotalSalary(totalSalary);
            setYtdTax(tax);
            setYtdSuperAnnuation(superAnnuation);
            setYtdNetPay(netPay);
        } else {
            setYtdTotalSalary(lastPayroll.getYtdTotalSalary() + totalSalary);
            setYtdTax(lastPayroll.getYtdTax() + tax);
            setYtdSuperAnnuation(lastPayroll.getYtdSuperAnnuation() + superAnnuation);
            setYtdNetPay(lastPayroll.getYtdNetPay() + netPay);
        }
    }


    public int getPayWeekNum() {
        return payWeekNum;
    }

    public void setPayWeekNum(int payWeekNum) {
        this.payWeekNum = payWeekNum;
    }

    public int getPayrollId() {
        return payrollId;
    }

    public void setPayrollId(int payrollId) {
        this.payrollId = payrollId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getCurrentHourlyRate() {
        return CurrentHourlyRate;
    }

    public void setCurrentHourlyRate(double CurrentHourlyRate) {
        this.CurrentHourlyRate = CurrentHourlyRate;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(double totalSalary) {
        this.totalSalary = totalSalary;
    }

    public LocalDate getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDate payDate) {
        this.payDate = payDate;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getSuperAnnuation() {
        return superAnnuation;
    }

    public void setSuperAnnuation(double superAnnuation) {
        this.superAnnuation = superAnnuation;
    }

    public double getNetPay() {
        return netPay;
    }

    public void setNetPay(double netPay) {
        this.netPay = netPay;
    }

    public double getYtdTotalSalary() {
        return ytdTotalSalary;
    }

    public void setYtdTotalSalary(double ytdTotalSalary) {
        this.ytdTotalSalary = ytdTotalSalary;
    }

    public double getYtdTax() {
        return ytdTax;
    }

    public void setYtdTax(double ytdTax) {
        this.ytdTax = ytdTax;
    }

    public double getYtdSuperAnnuation() {
        return ytdSuperAnnuation;
    }

    public void setYtdSuperAnnuation(double ytdSuperAnnuation) {
        this.ytdSuperAnnuation = ytdSuperAnnuation;
    }

    public double getYtdNetPay() {
        return ytdNetPay;
    }

    public void setYtdNetPay(double ytdNetPay) {
        this.ytdNetPay = ytdNetPay;
    }

    @Override
    public String toString() {
        return "Payroll{" + "payrollId=" + payrollId + ", employeeId=" + employeeId + ", hoursWorked=" + hoursWorked + ", CurrentHourlyRate=" + CurrentHourlyRate + ", totalSalary=" + totalSalary + ", payDate=" + payDate + ", payWeekNum=" + payWeekNum + ", year=" + year + ", tax=" + tax + ", superAnnuation=" + superAnnuation + ", netPay=" + netPay + ", ytdTotalSalary=" + ytdTotalSalary + ", ytdTax=" + ytdTax + ", ytdSuperAnnuation=" + ytdSuperAnnuation + ", ytdNetPay=" + ytdNetPay + '}';
    }

    /**
     * calculate super annuation in base rate 9.5
     */
    public void calculateSuperannuation() {
        this.superAnnuation = this.totalSalary * 0.095; //9.5% supprannuation rate of australia - assumption
    }

}
