import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Test Case 1: Single Part-Time Employee Vehicle Retrieval
        // Setup as specified
        company.setName("Food Express");
        
        // Create part-time employees
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Alice");
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Bob");
        
        // Create full-time employees
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Charlie");
        
        FullTimeEmployee emp4 = new FullTimeEmployee();
        emp4.setName("Diana");
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        emp1.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        emp2.setVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        emp3.setVehicle(vehicle3);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute the method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("ABC123");
        expected.add("XYZ789");
        
        assertEquals("Registration numbers should match expected output", expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Test Case 2: No Part-Time Employees
        // Setup as specified
        company.setName("Quick Delivery");
        
        // Create full-time employees only
        FullTimeEmployee emp1 = new FullTimeEmployee();
        emp1.setName("Ethan");
        
        FullTimeEmployee emp2 = new FullTimeEmployee();
        emp2.setName("Fiona");
        
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("George");
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        emp1.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        emp2.setVehicle(vehicle2);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute the method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output - empty list since no part-time employees
        List<String> expected = new ArrayList<>();
        
        assertEquals("Registration numbers should be empty when no part-time employees", expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: Mixed Employees and Vehicles
        // Setup as specified
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Henry");
        
        // Create full-time employees
        FullTimeEmployee emp2 = new FullTimeEmployee();
        emp2.setName("Isla");
        
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Jack");
        
        FullTimeEmployee emp4 = new FullTimeEmployee();
        emp4.setName("Kara");
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        emp1.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        emp2.setVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        emp3.setVehicle(vehicle3);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute the method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output - only the part-time employee's vehicle
        List<String> expected = new ArrayList<>();
        expected.add("DEF333");
        
        assertEquals("Registration numbers should only include part-time employee vehicles", expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: Multiple Drivers for One Vehicle
        // Note: The test specification mentions "multiple part-time drivers" but setup shows separate vehicles
        // Following the exact setup as specified
        company.setName("City Foods");
        
        // Create part-time employees
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Lily");
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Mike");
        
        // Create full-time employee
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Nina");
        
        // Create vehicles (note: spec says 3 vehicles but lists 3 registration numbers)
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        emp1.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        emp2.setVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        emp3.setVehicle(vehicle3);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute the method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output - both part-time employees' vehicles
        List<String> expected = new ArrayList<>();
        expected.add("PQR777");
        expected.add("STU888");
        
        assertEquals("Registration numbers should include all part-time employee vehicles", expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: All Vehicles without Drivers
        // Setup as specified
        company.setName("Rapid Deliveries");
        
        // Create part-time employees
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Olivia");
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Paul");
        
        // Create vehicles with no drivers assigned
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        // No driver assigned (vehicle1.setDriver() not called)
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        // No driver assigned (vehicle2.setDriver() not called)
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute the method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output - empty list since no vehicles are assigned to part-time employees
        List<String> expected = new ArrayList<>();
        
        assertEquals("Registration numbers should be empty when no vehicles are assigned to part-time employees", expected, result);
    }
}