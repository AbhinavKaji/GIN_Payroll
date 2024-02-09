/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SuperannuationTest {

    private Superannuation superannuation;

    @BeforeEach
    public void setUp() {
        superannuation = new Superannuation(1, "AustraliaSuper", 12345, 6789, 1001);
    }

    @Test
    public void testGetId() {
        assertEquals(1, superannuation.getId());
    }

    @Test
    public void testSetId() {
        superannuation.setId(2);
        assertEquals(2, superannuation.getId());
    }

    @Test
    public void testGetSuperName() {
        assertEquals("AustraliaSuper", superannuation.getSuperName());
    }

    @Test
    public void testSetSuperName() {
        superannuation.setSuperName("NewSuper");
        assertEquals("NewSuper", superannuation.getSuperName());
    }

    @Test
    public void testGetMemberNumber() {
        assertEquals(12345, superannuation.getMemberNumber());
    }

    @Test
    public void testSetMemberNumber() {
        superannuation.setMemberNumber(54321);
        assertEquals(54321, superannuation.getMemberNumber());
    }

    @Test
    public void testGetUsiNumber() {
        assertEquals(6789, superannuation.getUsiNumber());
    }

    @Test
    public void testSetUsiNumber() {
        superannuation.setUsiNumber(9876);
        assertEquals(9876, superannuation.getUsiNumber());
    }

    @Test
    public void testGetEmployeeId() {
        assertEquals(1001, superannuation.getEmployeeId());
    }

    @Test
    public void testSetEmployeeId() {
        superannuation.setEmployeeId(2002);
        assertEquals(2002, superannuation.getEmployeeId());
    }
}
