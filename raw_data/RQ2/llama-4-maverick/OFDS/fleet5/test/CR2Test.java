package edu.fleet.fleet5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.fleet.Company;
import edu.fleet.Employee;
import edu.fleet.EmployeeType;
import edu.fleet.FleetFactory;
import edu.fleet.Vehicle;

public class CR2Test {
    
    private FleetFactory factory;
    
    @Before
    public void setUp() {
        factory = FleetFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Create a Company object named "Food Express"
        Company company = factory.createCompany();
        company.setName("Food Express");
        
        // Create two part-time employees
        Employee employee1 = factory.createEmployee();
        employee1.setName("Alice");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = factory.createEmployee();
        employee2.setName("Bob");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create two full-time employees
        Employee employee3 = factory.createEmployee();
        employee3.setName("Charlie");
        employee3.setType(EmployeeType.FULL_TIME);
        
        Employee employee4 = factory.createEmployee();
        employee4.setName("Diana");
        employee4.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles
        Vehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(employee1); // Employee 1 (part-time)
        
        Vehicle vehicle2 = factory.createOwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setCurrentDriver(employee2); // Employee 2 (part-time)
        
        Vehicle vehicle3 = factory.createOwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setCurrentDriver(employee3); // Employee 3 (full-time)
        
        // Assign employees and vehicles to the Company object
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        company.getEmployees().add(employee4);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Execute the method under test
        var result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify the result
        assertEquals(2, result.size());
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("XYZ789"));
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Create a Company object named "Quick Delivery"
        Company company = factory.createCompany();
        company.setName("Quick Delivery");
        
        // Create three full-time employees
        Employee employee1 = factory.createEmployee();
        employee1.setName("Ethan");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = factory.createEmployee();
        employee2.setName("Fiona");
        employee2.setType(EmployeeType.FULL_TIME);
        
        Employee employee3 = factory.createEmployee();
        employee3.setName("George");
        employee3.setType(EmployeeType.FULL_TIME);
        
        // Create two vehicles
        Vehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setCurrentDriver(employee1);
        
        Vehicle vehicle2 = factory.createOwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setCurrentDriver(employee2);
        
        // Assign employees and vehicles to the Company object
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Execute the method under test
        var result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify the result
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Create a Company object named "Gourmet Delivery"
        Company company = factory.createCompany();
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        Employee employee1 = factory.createEmployee();
        employee1.setName("Henry");
        employee1.setType(EmployeeType.PART_TIME);
        
        // Create three full-time employees
        Employee employee2 = factory.createEmployee();
        employee2.setName("Isla");
        employee2.setType(EmployeeType.FULL_TIME);
        
        Employee employee3 = factory.createEmployee();
        employee3.setName("Jack");
        employee3.setType(EmployeeType.FULL_TIME);
        
        Employee employee4 = factory.createEmployee();
        employee4.setName("Kara");
        employee4.setType(EmployeeType.FULL_TIME);
        
        // Create four vehicles
        Vehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setCurrentDriver(employee1); // Employee 1 (part-time)
        
        Vehicle vehicle2 = factory.createOwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setCurrentDriver(employee2); // Employee 2 (full-time)
        
        Vehicle vehicle3 = factory.createOwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setCurrentDriver(employee3); // Employee 3 (full-time)
        
        // Note: The test specification mentions 4 vehicles but only describes 3.
        // I'll assume it's a typo and proceed with 3 vehicles as described.
        
        // Assign employees and vehicles to the Company object
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        company.getEmployees().add(employee4);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Execute the method under test
        var result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify the result
        assertEquals(1, result.size());
        assertTrue(result.contains("DEF333"));
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Create a Company object named "City Foods"
        Company company = factory.createCompany();
        company.setName("City Foods");
        
        // Create two part-time employees
        Employee employee1 = factory.createEmployee();
        employee1.setName("Lily");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = factory.createEmployee();
        employee2.setName("Mike");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create one full-time employee
        Employee employee3 = factory.createEmployee();
        employee3.setName("Nina");
        employee3.setType(EmployeeType.FULL_TIME);
        
        // Create two vehicles driven by part-time employees and one by full-time
        Vehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setCurrentDriver(employee1); // Employee 1 (part-time)
        
        Vehicle vehicle2 = factory.createOwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setCurrentDriver(employee2); // Employee 2 (part-time)
        
        Vehicle vehicle3 = factory.createOwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setCurrentDriver(employee3); // Employee 3 (full-time)
        
        // Assign employees and vehicles to the Company object
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Execute the method under test
        var result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify the result
        assertEquals(2, result.size());
        assertTrue(result.contains("PQR777"));
        assertTrue(result.contains("STU888"));
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Create a Company object named "Rapid Deliveries"
        Company company = factory.createCompany();
        company.setName("Rapid Deliveries");
        
        // Create two part-time employees
        Employee employee1 = factory.createEmployee();
        employee1.setName("Olivia");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = factory.createEmployee();
        employee2.setName("Paul");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create two vehicles with no current drivers
        Vehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        // No driver assigned
        
        Vehicle vehicle2 = factory.createOwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        // No driver assigned
        
        // Assign employees and vehicles to the Company object
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Execute the method under test
        var result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify the result
        assertEquals(0, result.size());
    }
}