/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 *
 * @author aavin
 */
public class Bank {
    private int bankId;
    private String bankName;
    private int bsbNumber;
    private int accNumber;
    private int payId;
    private int tfnNumber;
    private int employeeId;

    public Bank() {
    }

    public Bank(int bankId, String bankName, int bsbNumber, int accNumber, int payId, int tfnNumber, int employeeId) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.bsbNumber = bsbNumber;
        this.accNumber = accNumber;
        this.payId = payId;
        this.tfnNumber = tfnNumber;
        this.employeeId = employeeId;
    }

    public int getId() {
        return bankId;
    }

    public void setId(int bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public int getBsbNumber() {
        return bsbNumber;
    }

    @Override
    public String toString() {
        return "Bank{" + "bankId=" + bankId + ", bankName=" + bankName + ", bsbNumber=" + bsbNumber + ", accNumber=" + accNumber + ", payId=" + payId + ", tfnNumber=" + tfnNumber + ", employeeId=" + employeeId + '}';
    }

    public void setBsbNumber(int bsbNumber) {
        this.bsbNumber = bsbNumber;
    }

    public int getAccNumber() {
        return accNumber;
    }

    public void setAccNumber(int accNumber) {
        this.accNumber = accNumber;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public int getTfnNumber() {
        return tfnNumber;
    }

    public void setTfnNumber(int tfnNumber) {
        this.tfnNumber = tfnNumber;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    
}
