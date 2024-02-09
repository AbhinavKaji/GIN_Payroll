/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.utility;

/**
 *
 * @author aavin
 */
public enum Role {
    ADMIN("admin"),
    EMPLOYEE("employee"),
    LINEMANAGER("linemanager"),
    PAYROLLMANAGER("payrollmanager");
    
    private final String dbValue;

    Role(String dbValue) {
        this.dbValue = dbValue;
    }

    /**
     *
     * @return
     */
    public String getDbValue() {
        return dbValue;
    }

    public static Role fromDbValue(String dbValue) {
        for (Role role : values()) {
            if (role.dbValue.equals(dbValue)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No matching color for dbValue: " + dbValue);
    }
}
