/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankTest {

    private Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank(1, "MyBank", 123456, 7890, 1001, 987654, 2001);
    }

    @Test
    public void testGetId() {
        assertEquals(1, bank.getId());
        
    }

    @Test
    public void testSetId() {
        bank.setId(2);
        assertEquals(2, bank.getId());
    }

    @Test
    public void testGetBankName() {
        assertEquals("MyBank", bank.getBankName());
    }

    @Test
    public void testSetBankName() {
        bank.setBankName("NewBank");
        assertEquals("NewBank", bank.getBankName());
    }

    @Test
    public void testGetBsbNumber() {
        assertEquals(123456, bank.getBsbNumber());
    }

    @Test
    public void testSetBsbNumber() {
        bank.setBsbNumber(654321);
        assertEquals(654321, bank.getBsbNumber());
    }

    @Test
    public void testGetAccNumber() {
        assertEquals(7890, bank.getAccNumber());
    }

    @Test
    public void testSetAccNumber() {
        bank.setAccNumber(9876);
        assertEquals(9876, bank.getAccNumber());
    }

    @Test
    public void testGetPayId() {
        assertEquals(1001, bank.getPayId());
    }

    @Test
    public void testSetPayId() {
        bank.setPayId(2002);
        assertEquals(2002, bank.getPayId());
    }

    @Test
    public void testGetTfnNumber() {
        assertEquals(987654, bank.getTfnNumber());
    }

    @Test
    public void testSetTfnNumber() {
        bank.setTfnNumber(123456);
        assertEquals(123456, bank.getTfnNumber());
    }

    @Test
    public void testGetEmployeeId() {
        assertEquals(2001, bank.getEmployeeId());
    }

    @Test
    public void testSetEmployeeId() {
        bank.setEmployeeId(3001);
        assertEquals(3001, bank.getEmployeeId());
    }

    @Test
    public void testToString() {
        String expected = "Bank{bankId=1, bankName=MyBank, bsbNumber=123456, accNumber=7890, payId=1001, tfnNumber=987654, employeeId=2001}";
        assertEquals(expected, bank.toString());
    }
}
