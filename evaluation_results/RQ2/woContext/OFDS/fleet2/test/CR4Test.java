package edu.fleet.fleet2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.fleet.FleetFactory;
import edu.fleet.FleetPackage;
import edu.fleet.Company;
import edu.fleet.Employee;
import edu.fleet.Vehicle;
import edu.fleet.EmployeeType;
import org.eclipse.emf.common.util.EList;

public class CR4Test {
    
    private FleetFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = FleetFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_SingleEmployeeWithVehicle() {
        // Test Case 1: Single Employee with a Vehicle
        // Input: Retrieve the current drivers' names for a company with a single employee assigned to a vehicle.
        
        // SetUp:
        // 1. Create a Company named "Food Express"
        Company company = factory.createCompany();
        company.setName("Food Express");
        
        // 2. Create an Employee named "John Doe" of type Full-Time
        Employee employee = factory.createEmployee();
        employee.setName("John Doe");
        employee.setType(EmployeeType.FULL_TIME);
        
        // 3. Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = factory.createOwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setCurrentDriver(employee);
        
        // Add employee and vehicle to company
        company.getEmployees().add(employee);
        company.getVehicles().add(vehicle);
        
        // Execute: Get current drivers' names
        EList<String> result = company.getCurrentDriversNames();
        
        // Verify: Expected Output: ["John Doe"]
        assertEquals("Should contain exactly one driver name", 1, result.size());
        assertTrue("Should contain 'John Doe'", result.contains("John Doe"));
        assertEquals("First element should be 'John Doe'", "John Doe", result.get(0));
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // Input: Retrieve the current drivers' names for a company with multiple employees and vehicles.
        
        // SetUp:
        // 1. Create a Company named "Quick Delivery"
        Company company = factory.createCompany();
        company.setName("Quick Delivery");
        
        // 2. Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee employee1 = factory.createEmployee();
        employee1.setName("Alice Smith");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = factory.createEmployee();
        employee2.setName("Bob Johnson");
        employee2.setType(EmployeeType.PART_TIME);
        
        // 3. Create Vehicles with drivers
        Vehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setCurrentDriver(employee1);
        
        Vehicle vehicle2 = factory.createRentedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(employee2);
        
        // Add employees and vehicles to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Execute: Get current drivers' names
        EList<String> result = company.getCurrentDriversNames();
        
        // Verify: Expected Output: ["Alice Smith", "Bob Johnson"]
        assertEquals("Should contain exactly two driver names", 2, result.size());
        assertTrue("Should contain 'Alice Smith'", result.contains("Alice Smith"));
        assertTrue("Should contain 'Bob Johnson'", result.contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // Input: Retrieve the current drivers' names for a company with no current drivers assigned to any vehicles.
        
        // SetUp:
        // 1. Create a Company named "Gourmet Delivery"
        Company company = factory.createCompany();
        company.setName("Gourmet Delivery");
        
        // 2. Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee employee1 = factory.createEmployee();
        employee1.setName("Charlie Brown");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = factory.createEmployee();
        employee2.setName("David Warner");
        employee2.setType(EmployeeType.FULL_TIME);
        
        // 3. Create Vehicles not assigned to any driver
        Vehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        // No driver assigned
        
        Vehicle vehicle2 = factory.createRentedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        // No driver assigned
        
        // Add employees and vehicles to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Execute: Get current drivers' names
        EList<String> result = company.getCurrentDriversNames();
        
        // Verify: Expected Output: []
        assertTrue("Should return empty list when no drivers assigned", result.isEmpty());
        assertEquals("Empty list size should be 0", 0, result.size());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // Input: Retrieve the current drivers' names for a company with some vehicles assigned to drivers and some not.
        
        // SetUp:
        // 1. Create a Company named "Fast Meals Co."
        Company company = factory.createCompany();
        company.setName("Fast Meals Co.");
        
        // 2. Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee employee1 = factory.createEmployee();
        employee1.setName("Eva Green");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = factory.createEmployee();
        employee2.setName("Frank Wright");
        employee2.setType(EmployeeType.PART_TIME);
        
        // 3. Create Vehicles with mixed driver assignments
        Vehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(employee1); // Has driver
        
        Vehicle vehicle2 = factory.createRentedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // No driver assigned
        
        Vehicle vehicle3 = factory.createOwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(employee2); // Has driver
        
        // Add employees and vehicles to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Execute: Get current drivers' names
        EList<String> result = company.getCurrentDriversNames();
        
        // Verify: Expected Output: ["Eva Green", "Frank Wright"]
        assertEquals("Should contain exactly two driver names", 2, result.size());
        assertTrue("Should contain 'Eva Green'", result.contains("Eva Green"));
        assertTrue("Should contain 'Frank Wright'", result.contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // Input: Retrieve the current drivers' names for a company where employees are not assigned any vehicles.
        
        // SetUp:
        // 1. Create a Company named "Delicious Delivery"
        Company company = factory.createCompany();
        company.setName("Delicious Delivery");
        
        // 2. Create Employees without vehicles: "Grace Title" (Full-Time), "Henry Field" (Part-Time)
        Employee employee1 = factory.createEmployee();
        employee1.setName("Grace Title");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = factory.createEmployee();
        employee2.setName("Henry Field");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Add only employees to company (no vehicles)
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        
        // Execute: Get current drivers' names
        EList<String> result = company.getCurrentDriversNames();
        
        // Verify: Expected Output: []
        assertTrue("Should return empty list when no vehicles exist", result.isEmpty());
        assertEquals("Empty list size should be 0", 0, result.size());
    }
}