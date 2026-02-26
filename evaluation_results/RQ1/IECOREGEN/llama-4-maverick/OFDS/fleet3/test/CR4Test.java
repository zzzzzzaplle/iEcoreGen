package edu.fleet.fleet3.test;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.fleet.Company;
import edu.fleet.Employee;
import edu.fleet.Vehicle;
import edu.fleet.FleetFactory;
import edu.fleet.EmployeeType;

public class CR4Test {
    
    private FleetFactory factory;
    
    @Test
    public void testCase1_singleEmployeeWithVehicle() {
        // Create a Company named "Food Express"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        Employee employee = FleetFactory.eINSTANCE.createEmployee();
        employee.setName("John Doe");
        employee.setType(EmployeeType.FULL_TIME);
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setCurrentDriver(employee);
        employee.setDrivenVehicle(vehicle);
        
        // Add employee and vehicle to company
        company.getEmployees().add(employee);
        company.getVehicles().add(vehicle);
        
        // Verify the result
        assertEquals(1, company.getCurrentDriversNames().size());
        assertTrue(company.getCurrentDriversNames().contains("John Doe"));
    }
    
    @Test
    public void testCase2_multipleEmployeesMultipleVehicles() {
        // Create a Company named "Quick Delivery"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee alice = FleetFactory.eINSTANCE.createEmployee();
        alice.setName("Alice Smith");
        alice.setType(EmployeeType.FULL_TIME);
        
        Employee bob = FleetFactory.eINSTANCE.createEmployee();
        bob.setName("Bob Johnson");
        bob.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles with drivers
        Vehicle vehicle1 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setCurrentDriver(alice);
        alice.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(bob);
        bob.setDrivenVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.getEmployees().add(alice);
        company.getEmployees().add(bob);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Verify the result
        assertEquals(2, company.getCurrentDriversNames().size());
        assertTrue(company.getCurrentDriversNames().contains("Alice Smith"));
        assertTrue(company.getCurrentDriversNames().contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_noCurrentDrivers() {
        // Create a Company named "Gourmet Delivery"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee charlie = FleetFactory.eINSTANCE.createEmployee();
        charlie.setName("Charlie Brown");
        charlie.setType(EmployeeType.PART_TIME);
        
        Employee david = FleetFactory.eINSTANCE.createEmployee();
        david.setName("David Warner");
        david.setType(EmployeeType.FULL_TIME);
        
        // Create Vehicles not assigned to any driver
        Vehicle vehicle1 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        Vehicle vehicle2 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees and vehicles to company
        company.getEmployees().add(charlie);
        company.getEmployees().add(david);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Verify the result
        assertEquals(0, company.getCurrentDriversNames().size());
    }
    
    @Test
    public void testCase4_mixedVehiclesState() {
        // Create a Company named "Fast Meals Co."
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee eva = FleetFactory.eINSTANCE.createEmployee();
        eva.setName("Eva Green");
        eva.setType(EmployeeType.FULL_TIME);
        
        Employee frank = FleetFactory.eINSTANCE.createEmployee();
        frank.setName("Frank Wright");
        frank.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles: some with drivers, some without
        Vehicle vehicle1 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(eva);
        eva.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // No driver assigned
        
        Vehicle vehicle3 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(frank);
        frank.setDrivenVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.getEmployees().add(eva);
        company.getEmployees().add(frank);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Verify the result
        assertEquals(2, company.getCurrentDriversNames().size());
        assertTrue(company.getCurrentDriversNames().contains("Eva Green"));
        assertTrue(company.getCurrentDriversNames().contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_employeesWithoutVehicles() {
        // Create a Company named "Delicious Delivery"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("Delicious Delivery");
        
        // Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles
        Employee grace = FleetFactory.eINSTANCE.createEmployee();
        grace.setName("Grace Title");
        grace.setType(EmployeeType.FULL_TIME);
        // No vehicle assigned
        
        Employee henry = FleetFactory.eINSTANCE.createEmployee();
        henry.setName("Henry Field");
        henry.setType(EmployeeType.PART_TIME);
        // No vehicle assigned
        
        // Add employees to company
        company.getEmployees().add(grace);
        company.getEmployees().add(henry);
        
        // Verify the result
        assertEquals(0, company.getCurrentDriversNames().size());
    }
}