/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayrollTest {

    private Payroll payroll;

    @BeforeEach
    public void setUp() {
        payroll = new Payroll(1, 1001, 40.0, 25.0, 1000.0, LocalDate.of(2023, 9, 1), 36, 2023, 100.0, 50.0, 850.0, 12000.0, 6000.0, 5100.0, 11400.0);
    }

    @Test
    public void testGetPayrollId() {
        assertEquals(1, payroll.getPayrollId());
    }

    @Test
    public void testSetPayrollId() {
        payroll.setPayrollId(2);
        assertEquals(2, payroll.getPayrollId());
    }

    @Test
    public void testGetEmployeeId() {
        assertEquals(1001, payroll.getEmployeeId());
    }

    @Test
    public void testSetEmployeeId() {
        payroll.setEmployeeId(2002);
        assertEquals(2002, payroll.getEmployeeId());
    }

    @Test
    public void testGetHoursWorked() {
        assertEquals(40.0, payroll.getHoursWorked(), 0.001);
    }

    @Test
    public void testSetHoursWorked() {
        payroll.setHoursWorked(35.0);
        assertEquals(35.0, payroll.getHoursWorked(), 0.001);
    }

    @Test
    public void testGetCurrentHourlyRate() {
        assertEquals(25.0, payroll.getCurrentHourlyRate(), 0.001);
    }

    @Test
    public void testSetCurrentHourlyRate() {
        payroll.setCurrentHourlyRate(30.0);
        assertEquals(30.0, payroll.getCurrentHourlyRate(), 0.001);
    }

    @Test
    public void testGetTotalSalary() {
        assertEquals(1000.0, payroll.getTotalSalary(), 0.001);
    }

    @Test
    public void testSetTotalSalary() {
        payroll.setTotalSalary(1200.0);
        assertEquals(1200.0, payroll.getTotalSalary(), 0.001);
    }

    @Test
    public void testGetPayDate() {
        assertEquals(LocalDate.of(2023, 9, 1), payroll.getPayDate());
    }

    @Test
    public void testSetPayDate() {
        payroll.setPayDate(LocalDate.of(2023, 10, 1));
        assertEquals(LocalDate.of(2023, 10, 1), payroll.getPayDate());
    }

    @Test
    public void testGetTax() {
        assertEquals(100.0, payroll.getTax(), 0.001);
    }

    @Test
    public void testSetTax() {
        payroll.setTax(120.0);
        assertEquals(120.0, payroll.getTax(), 0.001);
    }

    @Test
    public void testGetSuperAnnuation() {
        assertEquals(50.0, payroll.getSuperAnnuation(), 0.001);
    }

    @Test
    public void testSetSuperAnnuation() {
        payroll.setSuperAnnuation(60.0);
        assertEquals(60.0, payroll.getSuperAnnuation(), 0.001);
    }

    @Test
    public void testGetNetPay() {
        assertEquals(850.0, payroll.getNetPay(), 0.001);
    }

    @Test
    public void testSetNetPay() {
        payroll.setNetPay(900.0);
        assertEquals(900.0, payroll.getNetPay(), 0.001);
    }

    @Test
    public void testGetYtdTotalSalary() {
        assertEquals(12000.0, payroll.getYtdTotalSalary(), 0.001);
    }

    @Test
    public void testSetYtdTotalSalary() {
        payroll.setYtdTotalSalary(13000.0);
        assertEquals(13000.0, payroll.getYtdTotalSalary(), 0.001);
    }

    @Test
    public void testGetYtdTax() {
        assertEquals(6000.0, payroll.getYtdTax(), 0.001);
    }

    @Test
    public void testSetYtdTax() {
        payroll.setYtdTax(7000.0);
        assertEquals(7000.0, payroll.getYtdTax(), 0.001);
    }

    @Test
    public void testGetYtdSuperAnnuation() {
        assertEquals(5100.0, payroll.getYtdSuperAnnuation(), 0.001);
    }

    @Test
    public void testSetYtdSuperAnnuation() {
        payroll.setYtdSuperAnnuation(5500.0);
        assertEquals(5500.0, payroll.getYtdSuperAnnuation(), 0.001);
    }

    @Test
    public void testGetYtdNetPay() {
        assertEquals(11400.0, payroll.getYtdNetPay(), 0.001);
    }

    @Test
    public void testSetYtdNetPay() {
        payroll.setYtdNetPay(12000.0);
        assertEquals(12000.0, payroll.getYtdNetPay(), 0.001);
    }

    @Test
    public void testGetYear() {
        assertEquals(2023, payroll.getYear());
    }

    @Test
    public void testSetYear() {
        payroll.setYear(2024);
        assertEquals(2024, payroll.getYear());
    }

    @Test
    public void testGetPayWeekNum() {
        assertEquals(36, payroll.getPayWeekNum());
    }

    @Test
    public void testSetPayWeekNum() {
        payroll.setPayWeekNum(37);
        assertEquals(37, payroll.getPayWeekNum());
    }

    @Test
    public void testCalculateIncomeTax() {
        // You may need to modify this test depending on your specific tax calculation logic.
        // Test that the income tax is calculated correctly.
        payroll.calculateIncomeTax();
        assertEquals(291.25, payroll.getTax());
    }

    @Test
    public void testCalculateSuperannuation() {
        // Test that superannuation is calculated correctly.
        payroll.calculateSuperannuation();
        assertEquals(95.0, payroll.getSuperAnnuation(), 0.01);
    }

    @Test
    public void testYtdCalculationWithNullLastPayroll() {
        // Test YTD calculations when lastPayroll is null.
        payroll.ytdCalculation(null);
        assertEquals(1000.0, payroll.getYtdTotalSalary(), 0.01);
        assertEquals(100.0, payroll.getYtdTax(), 0.01);
        assertEquals(50.0, payroll.getYtdSuperAnnuation(), 0.01);
        assertEquals(850.0, payroll.getYtdNetPay(), 0.01);
    }

    @Test
    public void testYtdCalculationWithLastPayroll() {
        // Test YTD calculations when lastPayroll is not null.
        Payroll lastPayroll = new Payroll(2, 1001, 45.0, 25.0, 1125.0, LocalDate.of(2023, 8, 25), 35, 2023, 112.5, 56.25, 956.25, 11900.0, 5950.0, 5050.0, 11350.0);
        payroll.ytdCalculation(lastPayroll);
        assertEquals(12900.0, payroll.getYtdTotalSalary(), 0.01);
        assertEquals(6050.0, payroll.getYtdTax(), 0.01);
        assertEquals(5100.0, payroll.getYtdSuperAnnuation(), 0.01);
        assertEquals(12200.0, payroll.getYtdNetPay(), 0.01);
    }

    @Test
    public void testToString() {
        String expectedString = "Payroll{payrollId=1, employeeId=1001, hoursWorked=40.0, CurrentHourlyRate=25.0, totalSalary=1000.0, payDate=2023-09-01, payWeekNum=36, year=2023, tax=100.0, superAnnuation=50.0, netPay=850.0, ytdTotalSalary=12000.0, ytdTax=6000.0, ytdSuperAnnuation=5100.0, ytdNetPay=11400.0}";
        assertEquals(expectedString, payroll.toString());
    }
}
