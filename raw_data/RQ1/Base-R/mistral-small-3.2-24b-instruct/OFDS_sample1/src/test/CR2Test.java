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
        company = null;
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Test Case 1: Single Part-Time Employee Vehicle Retrieval
        // Setup
        company = new Company("Food Express");
        
        // Create part-time employees
        Employee alice = new Employee("Alice", false);
        Employee bob = new Employee("Bob", false);
        
        // Create full-time employees
        Employee charlie = new Employee("Charlie", true);
        Employee diana = new Employee("Diana", true);
        
        // Create vehicles with drivers
        Vehicle vehicle1 = new Vehicle("ABC123", false);
        Vehicle vehicle2 = new Vehicle("XYZ789", false);
        Vehicle vehicle3 = new Vehicle("LMN456", false);
        
        // Set up company data
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        employees.add(charlie);
        employees.add(diana);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        List<Employee> drivers = new ArrayList<>();
        drivers.add(alice); // part-time driver for vehicle1
        drivers.add(bob);   // part-time driver for vehicle2
        drivers.add(charlie); // full-time driver for vehicle3
        company.setDrivers(drivers);
        
        // Execute method under test
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("ABC123");
        expected.add("XYZ789");
        
        assertEquals("Should return registration numbers of vehicles driven by part-time employees", 
                     expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Test Case 2: No Part-Time Employees
        // Setup
        company = new Company("Quick Delivery");
        
        // Create full-time employees only
        Employee ethan = new Employee("Ethan", true);
        Employee fiona = new Employee("Fiona", true);
        Employee george = new Employee("George", true);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle("QWE111", false);
        Vehicle vehicle2 = new Vehicle("RTY222", false);
        
        // Set up company data
        List<Employee> employees = new ArrayList<>();
        employees.add(ethan);
        employees.add(fiona);
        employees.add(george);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        List<Employee> drivers = new ArrayList<>();
        drivers.add(ethan);  // full-time driver for vehicle1
        drivers.add(fiona);  // full-time driver for vehicle2
        company.setDrivers(drivers);
        
        // Execute method under test
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list when no part-time employees drive vehicles", 
                     expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: Mixed Employees and Vehicles
        // Setup
        company = new Company("Gourmet Delivery");
        
        // Create one part-time employee
        Employee henry = new Employee("Henry", false);
        
        // Create full-time employees
        Employee isla = new Employee("Isla", true);
        Employee jack = new Employee("Jack", true);
        Employee kara = new Employee("Kara", true);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle("DEF333", false);
        Vehicle vehicle2 = new Vehicle("GHI444", false);
        Vehicle vehicle3 = new Vehicle("JKL555", false);
        
        // Set up company data
        List<Employee> employees = new ArrayList<>();
        employees.add(henry);
        employees.add(isla);
        employees.add(jack);
        employees.add(kara);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        List<Employee> drivers = new ArrayList<>();
        drivers.add(henry);  // part-time driver for vehicle1
        drivers.add(isla);   // full-time driver for vehicle2
        drivers.add(jack);   // full-time driver for vehicle3
        company.setDrivers(drivers);
        
        // Execute method under test
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("DEF333");
        
        assertEquals("Should return registration number of vehicle driven by single part-time employee", 
                     expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: Multiple Drivers for One Vehicle
        // Setup
        company = new Company("City Foods");
        
        // Create part-time employees
        Employee lily = new Employee("Lily", false);
        Employee mike = new Employee("Mike", false);
        
        // Create full-time employee
        Employee nina = new Employee("Nina", true);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle("PQR777", false);
        Vehicle vehicle2 = new Vehicle("STU888", false);
        Vehicle vehicle3 = new Vehicle("VWX999", false);
        
        // Set up company data
        List<Employee> employees = new ArrayList<>();
        employees.add(lily);
        employees.add(mike);
        employees.add(nina);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        List<Employee> drivers = new ArrayList<>();
        drivers.add(lily);  // part-time driver for vehicle1
        drivers.add(mike);  // part-time driver for vehicle2
        drivers.add(nina);  // full-time driver for vehicle3
        company.setDrivers(drivers);
        
        // Execute method under test
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("PQR777");
        expected.add("STU888");
        
        assertEquals("Should return registration numbers of vehicles driven by multiple part-time employees", 
                     expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: All Vehicles without Drivers
        // Setup
        company = new Company("Rapid Deliveries");
        
        // Create part-time employees (but no drivers assigned)
        Employee olivia = new Employee("Olivia", false);
        Employee paul = new Employee("Paul", false);
        
        // Create vehicles with no drivers assigned
        Vehicle vehicle1 = new Vehicle("AAA000", false);
        Vehicle vehicle2 = new Vehicle("BBB111", false);
        
        // Set up company data
        List<Employee> employees = new ArrayList<>();
        employees.add(olivia);
        employees.add(paul);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Drivers list is empty (no drivers assigned to vehicles)
        List<Employee> drivers = new ArrayList<>();
        company.setDrivers(drivers);
        
        // Execute method under test
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list when no drivers are assigned to vehicles", 
                     expected, result);
    }
}