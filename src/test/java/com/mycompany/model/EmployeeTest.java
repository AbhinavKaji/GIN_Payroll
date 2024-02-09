package com.mycompany.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {
    private Employee employee;

    @BeforeEach
    public void setUp() {
        // Create a sample Employee object before each test
        employee = new Employee(1, "EMP456", "57 Bay Street", "0404223033", 20.0, "testemail@gmail.com");
    }

    @Test
    public void testGetId() {
        assertEquals(1, employee.getId());
    }

    @Test
    public void testGetEmployeeId() {
        assertEquals("EMP456", employee.getEmployeeId());
    }

    @Test
    public void testGetEmail() {
        assertEquals("testemail@gmail.com", employee.getEmail());
    }

    @Test
    public void testSetEmail() {
        employee.setEmail("newemail@example.com");
        assertEquals("newemail@example.com", employee.getEmail());
    }

    @Test
    public void testGetAddress() {
        assertEquals("57 Bay Street", employee.getAddress());
    }

    @Test
    public void testSetAddress() {
        employee.setAddress("12 Robertson street");
        assertEquals("12 Robertson street", employee.getAddress());
    }

    @Test
    public void testGetPhone() {
        assertEquals("0404223033", employee.getPhone());
    }

    @Test
    public void testSetPhone() {
        employee.setPhone("044440404");
        assertEquals("044440404", employee.getPhone());
    }

    @Test
    public void testGetHourlyRate() {
        assertEquals(20.0, employee.getHourlyRate(), 0.01);
    }

    @Test
    public void testSetHourlyRate() {
        employee.setHourlyRate(25.0);
        assertEquals(25.0, employee.getHourlyRate(), 0.01);
    }

    @Test
    public void testToString() {
        String expected = "Employee{id=1, employeeId=EMP456, address=57 Bay Street, phone=0404223033, hourlyRate=20.0, email=testemail@gmail.com}";
        assertEquals(expected, employee.toString());
    }
}
