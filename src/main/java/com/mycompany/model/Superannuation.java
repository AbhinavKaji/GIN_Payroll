/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

/**
 *
 * @author aavin
 */
public class Superannuation {
    private int superId;
    private String superName;
    private int memberNumber;
    private int usiNumber;
    private int employeeId;

    public Superannuation() {
    }

    public Superannuation(int superId, String superName, int memberNumber, int usiNumber, int employeeId) {
        this.superId = superId;
        this.superName = superName;
        this.memberNumber = memberNumber;
        this.usiNumber = usiNumber;
        this.employeeId = employeeId;
    }

    public int getId() {
        return superId;
    }

    public void setId(int superId) {
        this.superId = superId;
    }

    public String getSuperName() {
        return superName;
    }

    public void setSuperName(String superName) {
        this.superName = superName;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(int memberNumber) {
        this.memberNumber = memberNumber;
    }

    public int getUsiNumber() {
        return usiNumber;
    }

    public void setUsiNumber(int usiNumber) {
        this.usiNumber = usiNumber;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    

}
