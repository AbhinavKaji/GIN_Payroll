/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.model;

import com.mycompany.utility.Role;

/**
 *
 * @author aavin
 */
public class Employee extends User{
    private int id;
    private String employeeId;
    private String address;
    private String phone;
    private double hourlyRate;
    private String email;
    

    public Employee() {
    }

    public Employee(int id, String employeeId, String address, String phone, double hourlyRate, String email) {
        this.id = id;
        this.employeeId = employeeId;
        this.address = address;
        this.phone = phone;
        this.hourlyRate = hourlyRate;
        this.email = email;
    }

    public Employee(int id, String employeeId, String address, String phone, double hourlyRate, String email, int userId, String userName, String firstName, String lastName, String password, Role role) {
        super(userId, userName, firstName, lastName, password, role);
        this.id = id;
        this.employeeId = employeeId;
        this.address = address;
        this.phone = phone;
        this.hourlyRate = hourlyRate;
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    

    public int getId() {
        return id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", employeeId=" + employeeId + ", address=" + address + ", phone=" + phone + ", hourlyRate=" + hourlyRate + ", email=" + email + '}';
    }
}
