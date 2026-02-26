package edu.fleet.fleet2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.fleet.FleetFactory;
import edu.fleet.FleetPackage;
import edu.fleet.Company;
import edu.fleet.Employee;
import edu.fleet.Vehicle;
import edu.fleet.OwnedVehicle;
import edu.fleet.RentedVehicle;
import edu.fleet.EmployeeType;
import org.eclipse.emf.common.util.EList;

public class CR2Test {
    
    private FleetFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
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
        vehicle1.setCurrentDriver(employee1);
        
        Vehicle vehicle2 = factory.createOwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setCurrentDriver(employee2);
        
        Vehicle vehicle3 = factory.createOwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setCurrentDriver(employee3);
        
        // Add employees to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        company.getEmployees().add(employee4);
        
        // Add vehicles to company
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        EList<String> registrationNumbers = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output
        assertEquals(2, registrationNumbers.size());
        assertTrue(registrationNumbers.contains("ABC123"));
        assertTrue(registrationNumbers.contains("XYZ789"));
        assertFalse(registrationNumbers.contains("LMN456"));
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
        
        // Add employees to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        
        // Add vehicles to company
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        EList<String> registrationNumbers = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output - empty list since no part-time employees are driving
        assertEquals(0, registrationNumbers.size());
        assertTrue(registrationNumbers.isEmpty());
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
        vehicle1.setCurrentDriver(employee1);
        
        Vehicle vehicle2 = factory.createOwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setCurrentDriver(employee2);
        
        Vehicle vehicle3 = factory.createOwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setCurrentDriver(employee3);
        
        // Add employees to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        company.getEmployees().add(employee4);
        
        // Add vehicles to company
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        EList<String> registrationNumbers = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output - only one vehicle driven by part-time employee
        assertEquals(1, registrationNumbers.size());
        assertEquals("DEF333", registrationNumbers.get(0));
        assertFalse(registrationNumbers.contains("GHI444"));
        assertFalse(registrationNumbers.contains("JKL555"));
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
        
        // Create three vehicles
        Vehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setCurrentDriver(employee1);
        
        Vehicle vehicle2 = factory.createOwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setCurrentDriver(employee2);
        
        Vehicle vehicle3 = factory.createOwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setCurrentDriver(employee3);
        
        // Add employees to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getEmployees().add(employee3);
        
        // Add vehicles to company
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        EList<String> registrationNumbers = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output - two vehicles driven by part-time employees
        assertEquals(2, registrationNumbers.size());
        assertTrue(registrationNumbers.contains("PQR777"));
        assertTrue(registrationNumbers.contains("STU888"));
        assertFalse(registrationNumbers.contains("VWX999"));
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
        // currentDriver is null by default
        
        Vehicle vehicle2 = factory.createOwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        // currentDriver is null by default
        
        // Add employees to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        
        // Add vehicles to company
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        EList<String> registrationNumbers = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output - empty list since no vehicles have drivers assigned
        assertEquals(0, registrationNumbers.size());
        assertTrue(registrationNumbers.isEmpty());
    }
}