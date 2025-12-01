import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
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
        // Test Case 1: "Single Part-Time Employee Vehicle Retrieval"
        // Create a Company object named "Food Express"
        company = new Company("Food Express");
        
        // Create two part-time employees
        Employee employee1 = new Employee("Alice", EmployeeType.PART_TIME);
        Employee employee2 = new Employee("Bob", EmployeeType.PART_TIME);
        
        // Create two full-time employees
        Employee employee3 = new Employee("Charlie", EmployeeType.FULL_TIME);
        Employee employee4 = new Employee("Diana", EmployeeType.FULL_TIME);
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle("ABC123");
        Vehicle vehicle2 = new OwnedVehicle("XYZ789");
        Vehicle vehicle3 = new OwnedVehicle("LMN456");
        
        // Assign drivers to vehicles
        vehicle1.setCurrentDriver(employee1); // part-time
        vehicle2.setCurrentDriver(employee2); // part-time
        vehicle3.setCurrentDriver(employee3); // full-time
        
        // Add employees to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addEmployee(employee4);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: Registration numbers = ["ABC123", "XYZ789"]
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        assertEquals("Should return registration numbers of vehicles driven by part-time employees", 
                     expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Test Case 2: "No Part-Time Employees"
        // Create a Company object named "Quick Delivery"
        company = new Company("Quick Delivery");
        
        // Create three full-time employees
        Employee employee1 = new Employee("Ethan", EmployeeType.FULL_TIME);
        Employee employee2 = new Employee("Fiona", EmployeeType.FULL_TIME);
        Employee employee3 = new Employee("George", EmployeeType.FULL_TIME);
        
        // Create two vehicles
        Vehicle vehicle1 = new OwnedVehicle("QWE111");
        Vehicle vehicle2 = new OwnedVehicle("RTY222");
        
        // Assign drivers to vehicles
        vehicle1.setCurrentDriver(employee1);
        vehicle2.setCurrentDriver(employee2);
        
        // Add employees to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: Registration numbers = []
        assertTrue("Should return empty list when there are no part-time employees driving vehicles", 
                   result.isEmpty());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: "Mixed Employees and Vehicles"
        // Create a Company object named "Gourmet Delivery"
        company = new Company("Gourmet Delivery");
        
        // Create one part-time employee
        Employee employee1 = new Employee("Henry", EmployeeType.PART_TIME);
        
        // Create three full-time employees
        Employee employee2 = new Employee("Isla", EmployeeType.FULL_TIME);
        Employee employee3 = new Employee("Jack", EmployeeType.FULL_TIME);
        Employee employee4 = new Employee("Kara", EmployeeType.FULL_TIME);
        
        // Create four vehicles (spec says 4, but only 3 are described - using 3 as per description)
        Vehicle vehicle1 = new OwnedVehicle("DEF333");
        Vehicle vehicle2 = new OwnedVehicle("GHI444");
        Vehicle vehicle3 = new OwnedVehicle("JKL555");
        
        // Assign drivers to vehicles
        vehicle1.setCurrentDriver(employee1); // part-time
        vehicle2.setCurrentDriver(employee2); // full-time
        vehicle3.setCurrentDriver(employee3); // full-time
        
        // Add employees to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addEmployee(employee4);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: Registration numbers = ["DEF333"]
        List<String> expected = Arrays.asList("DEF333");
        assertEquals("Should return registration number of vehicle driven by the only part-time employee", 
                     expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: "Multiple Drivers for One Vehicle"
        // Create a Company object named "City Foods"
        company = new Company("City Foods");
        
        // Create two part-time employees
        Employee employee1 = new Employee("Lily", EmployeeType.PART_TIME);
        Employee employee2 = new Employee("Mike", EmployeeType.PART_TIME);
        
        // Create one full-time employee
        Employee employee3 = new Employee("Nina", EmployeeType.FULL_TIME);
        
        // Create three vehicles (spec says 2 but description lists 3 - using 3 as per description)
        Vehicle vehicle1 = new OwnedVehicle("PQR777");
        Vehicle vehicle2 = new OwnedVehicle("STU888");
        Vehicle vehicle3 = new OwnedVehicle("VWX999");
        
        // Assign drivers to vehicles
        vehicle1.setCurrentDriver(employee1); // part-time
        vehicle2.setCurrentDriver(employee2); // part-time
        vehicle3.setCurrentDriver(employee3); // full-time
        
        // Add employees to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: Registration numbers = ["PQR777", "STU888"]
        List<String> expected = Arrays.asList("PQR777", "STU888");
        assertEquals("Should return registration numbers of vehicles driven by both part-time employees", 
                     expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: "All Vehicles without Drivers"
        // Create a Company object named "Rapid Deliveries"
        company = new Company("Rapid Deliveries");
        
        // Create two part-time employees
        Employee employee1 = new Employee("Olivia", EmployeeType.PART_TIME);
        Employee employee2 = new Employee("Paul", EmployeeType.PART_TIME);
        
        // Create two vehicles with no current drivers
        Vehicle vehicle1 = new OwnedVehicle("AAA000");
        Vehicle vehicle2 = new OwnedVehicle("BBB111");
        
        // Vehicles have null drivers by default, no need to set
        
        // Add employees to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: Registration numbers = []
        assertTrue("Should return empty list when no vehicles have drivers assigned", 
                   result.isEmpty());
    }
}