package edu.fleet.fleet5.test;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.fleet.Company;
import edu.fleet.Employee;
import edu.fleet.EmployeeType;
import edu.fleet.FleetFactory;
import edu.fleet.OwnedVehicle;
import edu.fleet.RentedVehicle;
import edu.fleet.Vehicle;

import org.eclipse.emf.common.util.EList;

public class CR4Test {

    private Company company;
    private Employee employee1, employee2;
    private Vehicle vehicle1, vehicle2, vehicle3;

    /**
     * Test Case 1: Single Employee with a Vehicle
     * Input: Retrieve the current drivers' names for a company with a single employee assigned to a vehicle.
     * SetUp:
     * 1. Create a Company named "Food Express".
     * 2. Create an Employee named "John Doe" of type Full-Time.
     * 3. Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver.
     * Expected Output: ["John Doe"]
     */
    @Test
    public void testCase1_singleEmployeeWithVehicle() {
        // Create a Company named "Food Express"
        company = FleetFactory.eINSTANCE.createCompany();
        company.setName("Food Express");

        // Create an Employee named "John Doe" of type Full-Time
        employee1 = FleetFactory.eINSTANCE.createEmployee();
        employee1.setName("John Doe");
        employee1.setType(EmployeeType.FULL_TIME);
        company.getEmployees().add(employee1);

        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        vehicle1 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(employee1);
        company.getVehicles().add(vehicle1);

        // Execute the method under test
        EList<String> result = company.getCurrentDriversNames();

        // Verify the result
        assertEquals(1, result.size());
        assertTrue(result.contains("John Doe"));
    }

    /**
     * Test Case 2: Multiple Employees, Multiple Vehicles
     * Input: Retrieve the current drivers' names for a company with multiple employees and vehicles.
     * SetUp:
     * 1. Create a Company named "Quick Delivery".
     * 2. Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time).
     * 3. Create Vehicles:
     * - Registration "XYZ789" with driver "Alice Smith".
     * - Registration "LMN456" with driver "Bob Johnson".
     * Expected Output: ["Alice Smith", "Bob Johnson"]
     */
    @Test
    public void testCase2_multipleEmployeesMultipleVehicles() {
        // Create a Company named "Quick Delivery"
        company = FleetFactory.eINSTANCE.createCompany();
        company.setName("Quick Delivery");

        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        employee1 = FleetFactory.eINSTANCE.createEmployee();
        employee1.setName("Alice Smith");
        employee1.setType(EmployeeType.FULL_TIME);
        company.getEmployees().add(employee1);

        employee2 = FleetFactory.eINSTANCE.createEmployee();
        employee2.setName("Bob Johnson");
        employee2.setType(EmployeeType.PART_TIME);
        company.getEmployees().add(employee2);

        // Create Vehicles
        vehicle1 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setCurrentDriver(employee1);
        company.getVehicles().add(vehicle1);

        vehicle2 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(employee2);
        company.getVehicles().add(vehicle2);

        // Execute the method under test
        EList<String> result = company.getCurrentDriversNames();

        // Verify the result
        assertEquals(2, result.size());
        assertTrue(result.contains("Alice Smith"));
        assertTrue(result.contains("Bob Johnson"));
    }

    /**
     * Test Case 3: No Current Drivers
     * Input: Retrieve the current drivers' names for a company with no current drivers assigned to any vehicles.
     * SetUp:
     * 1. Create a Company named "Gourmet Delivery".
     * 2. Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time).
     * 3. Create Vehicles:
     * - Registration "DEF321" not assigned to any driver.
     * - Registration "JKL654" not assigned to any driver.
     * Expected Output: []
     */
    @Test
    public void testCase3_noCurrentDrivers() {
        // Create a Company named "Gourmet Delivery"
        company = FleetFactory.eINSTANCE.createCompany();
        company.setName("Gourmet Delivery");

        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        employee1 = FleetFactory.eINSTANCE.createEmployee();
        employee1.setName("Charlie Brown");
        employee1.setType(EmployeeType.PART_TIME);
        company.getEmployees().add(employee1);

        employee2 = FleetFactory.eINSTANCE.createEmployee();
        employee2.setName("David Warner");
        employee2.setType(EmployeeType.FULL_TIME);
        company.getEmployees().add(employee2);

        // Create Vehicles not assigned to any driver
        vehicle1 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        company.getVehicles().add(vehicle1);

        vehicle2 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        company.getVehicles().add(vehicle2);

        // Execute the method under test
        EList<String> result = company.getCurrentDriversNames();

        // Verify the result
        assertEquals(0, result.size());
    }

    /**
     * Test Case 4: Mixed Vehicles State
     * Input: Retrieve the current drivers' names for a company with some vehicles assigned to drivers and some not.
     * SetUp:
     * 1. Create a Company named "Fast Meals Co.".
     * 2. Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time).
     * 3. Create Vehicles:
     * - Registration "RST234" with driver "Eva Green".
     * - Registration "UVW567" not assigned to any driver.
     * - Registration "OPQ890" with driver "Frank Wright".
     * Expected Output: ["Eva Green", "Frank Wright"]
     */
    @Test
    public void testCase4_mixedVehiclesState() {
        // Create a Company named "Fast Meals Co."
        company = FleetFactory.eINSTANCE.createCompany();
        company.setName("Fast Meals Co.");

        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        employee1 = FleetFactory.eINSTANCE.createEmployee();
        employee1.setName("Eva Green");
        employee1.setType(EmployeeType.FULL_TIME);
        company.getEmployees().add(employee1);

        employee2 = FleetFactory.eINSTANCE.createEmployee();
        employee2.setName("Frank Wright");
        employee2.setType(EmployeeType.PART_TIME);
        company.getEmployees().add(employee2);

        // Create Vehicles
        vehicle1 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(employee1);
        company.getVehicles().add(vehicle1);

        vehicle2 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // Not assigned to any driver
        company.getVehicles().add(vehicle2);

        vehicle3 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(employee2);
        company.getVehicles().add(vehicle3);

        // Execute the method under test
        EList<String> result = company.getCurrentDriversNames();

        // Verify the result
        assertEquals(2, result.size());
        assertTrue(result.contains("Eva Green"));
        assertTrue(result.contains("Frank Wright"));
    }

    /**
     * Test Case 5: Employees Without Vehicles
     * Input: Retrieve the current drivers' names for a company where employees are not assigned any vehicles.
     * SetUp:
     * 1. Create a Company named "Delicious Delivery".
     * 2. Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles.
     * Expected Output: []
     */
    @Test
    public void testCase5_employeesWithoutVehicles() {
        // Create a Company named "Delicious Delivery"
        company = FleetFactory.eINSTANCE.createCompany();
        company.setName("Delicious Delivery");

        // Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles
        employee1 = FleetFactory.eINSTANCE.createEmployee();
        employee1.setName("Grace Title");
        employee1.setType(EmployeeType.FULL_TIME);
        company.getEmployees().add(employee1);

        employee2 = FleetFactory.eINSTANCE.createEmployee();
        employee2.setName("Henry Field");
        employee2.setType(EmployeeType.PART_TIME);
        company.getEmployees().add(employee2);

        // Execute the method under test
        EList<String> result = company.getCurrentDriversNames();

        // Verify the result
        assertEquals(0, result.size());
    }
}