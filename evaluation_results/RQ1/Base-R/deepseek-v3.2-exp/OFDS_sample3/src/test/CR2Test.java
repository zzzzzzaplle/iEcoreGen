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
        // Expected Output: Registration numbers = ["ABC123", "XYZ789"]
        
        // Set up company
        company.setName("Food Express");
        
        // Create employees
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setFullTime(false); // Part-time
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setFullTime(false); // Part-time
        
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setFullTime(true); // Full-time
        
        Employee emp4 = new Employee();
        emp4.setName("Diana");
        emp4.setFullTime(true); // Full-time
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setDriver(emp1); // Driven by part-time employee
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setDriver(emp2); // Driven by part-time employee
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setDriver(emp3); // Driven by full-time employee
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Test the method
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify the result
        assertEquals(2, result.size());
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("XYZ789"));
        assertFalse(result.contains("LMN456")); // Should not contain full-time driver vehicle
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Test Case 2: No Part-Time Employees
        // Expected Output: Registration numbers = []
        
        // Set up company
        company.setName("Quick Delivery");
        
        // Create employees (all full-time)
        Employee emp1 = new Employee();
        emp1.setName("Ethan");
        emp1.setFullTime(true); // Full-time
        
        Employee emp2 = new Employee();
        emp2.setName("Fiona");
        emp2.setFullTime(true); // Full-time
        
        Employee emp3 = new Employee();
        emp3.setName("George");
        emp3.setFullTime(true); // Full-time
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setDriver(emp1); // Driven by full-time employee
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setDriver(emp2); // Driven by full-time employee
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Test the method
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify the result should be empty
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: Mixed Employees and Vehicles
        // Expected Output: Registration numbers = ["DEF333"]
        
        // Set up company
        company.setName("Gourmet Delivery");
        
        // Create employees
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setFullTime(false); // Part-time
        
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setFullTime(true); // Full-time
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setFullTime(true); // Full-time
        
        Employee emp4 = new Employee();
        emp4.setName("Kara");
        emp4.setFullTime(true); // Full-time
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setDriver(emp1); // Driven by part-time employee
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setDriver(emp2); // Driven by full-time employee
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setDriver(emp3); // Driven by full-time employee
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Test the method
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify the result
        assertEquals(1, result.size());
        assertEquals("DEF333", result.get(0));
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: Multiple Drivers for One Vehicle
        // Expected Output: Registration numbers = ["PQR777", "STU888"]
        
        // Set up company
        company.setName("City Foods");
        
        // Create employees
        Employee emp1 = new Employee();
        emp1.setName("Lily");
        emp1.setFullTime(false); // Part-time
        
        Employee emp2 = new Employee();
        emp2.setName("Mike");
        emp2.setFullTime(false); // Part-time
        
        Employee emp3 = new Employee();
        emp3.setName("Nina");
        emp3.setFullTime(true); // Full-time
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setDriver(emp1); // Driven by part-time employee
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setDriver(emp2); // Driven by part-time employee
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setDriver(emp3); // Driven by full-time employee
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Test the method
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify the result
        assertEquals(2, result.size());
        assertTrue(result.contains("PQR777"));
        assertTrue(result.contains("STU888"));
        assertFalse(result.contains("VWX999")); // Should not contain full-time driver vehicle
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: All Vehicles without Drivers
        // Expected Output: Registration numbers = []
        
        // Set up company
        company.setName("Rapid Deliveries");
        
        // Create employees
        Employee emp1 = new Employee();
        emp1.setName("Olivia");
        emp1.setFullTime(false); // Part-time
        
        Employee emp2 = new Employee();
        emp2.setName("Paul");
        emp2.setFullTime(false); // Part-time
        
        // Create vehicles (no drivers assigned)
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setDriver(null); // No driver assigned
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setDriver(null); // No driver assigned
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Test the method
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify the result should be empty since no vehicles have drivers
        assertTrue(result.isEmpty());
    }
}