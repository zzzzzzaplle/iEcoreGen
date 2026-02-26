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
        // Setup: Create company and populate with employees and vehicles
        company.setName("Food Express");
        
        // Create part-time employees
        Employee employee1 = new Employee();
        employee1.setName("Alice");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create full-time employees
        Employee employee3 = new Employee();
        employee3.setName("Charlie");
        employee3.setType(EmployeeType.FULL_TIME);
        
        Employee employee4 = new Employee();
        employee4.setName("Diana");
        employee4.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(employee1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setCurrentDriver(employee2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setCurrentDriver(employee3);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("ABC123");
        expected.add("XYZ789");
        
        assertEquals("Registration numbers should match expected list", expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Test Case 2: No Part-Time Employees
        // Setup: Create company with only full-time employees
        company.setName("Quick Delivery");
        
        // Create full-time employees
        Employee employee1 = new Employee();
        employee1.setName("Ethan");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Fiona");
        employee2.setType(EmployeeType.FULL_TIME);
        
        Employee employee3 = new Employee();
        employee3.setName("George");
        employee3.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setCurrentDriver(employee1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setCurrentDriver(employee2);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output (empty list)
        List<String> expected = new ArrayList<>();
        
        assertEquals("Registration numbers should be empty when no part-time employees drive vehicles", expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: Mixed Employees and Vehicles
        // Setup: Create company with mixed employee types and vehicles
        company.setName("Gourmet Delivery");
        
        // Create part-time employee
        Employee employee1 = new Employee();
        employee1.setName("Henry");
        employee1.setType(EmployeeType.PART_TIME);
        
        // Create full-time employees
        Employee employee2 = new Employee();
        employee2.setName("Isla");
        employee2.setType(EmployeeType.FULL_TIME);
        
        Employee employee3 = new Employee();
        employee3.setName("Jack");
        employee3.setType(EmployeeType.FULL_TIME);
        
        Employee employee4 = new Employee();
        employee4.setName("Kara");
        employee4.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setCurrentDriver(employee1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setCurrentDriver(employee2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setCurrentDriver(employee3);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("DEF333");
        
        assertEquals("Registration numbers should contain only vehicles driven by part-time employees", expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: Multiple Drivers for One Vehicle
        // Setup: Create company with multiple part-time drivers
        company.setName("City Foods");
        
        // Create part-time employees
        Employee employee1 = new Employee();
        employee1.setName("Lily");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Mike");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create full-time employee
        Employee employee3 = new Employee();
        employee3.setName("Nina");
        employee3.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setCurrentDriver(employee1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setCurrentDriver(employee2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setCurrentDriver(employee3);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("PQR777");
        expected.add("STU888");
        
        assertEquals("Registration numbers should contain all vehicles driven by part-time employees", expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: All Vehicles without Drivers
        // Setup: Create company with vehicles that have no drivers
        company.setName("Rapid Deliveries");
        
        // Create part-time employees
        Employee employee1 = new Employee();
        employee1.setName("Olivia");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Paul");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create vehicles with no drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setCurrentDriver(null);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output (empty list)
        List<String> expected = new ArrayList<>();
        
        assertEquals("Registration numbers should be empty when no vehicles have drivers", expected, result);
    }
}