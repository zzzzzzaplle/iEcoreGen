import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Test Case 1: "Single Part-Time Employee Vehicle Retrieval"
        // Setup
        company.setName("Food Express");
        
        // Create part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType(EmployeeType.PART_TIME);
        
        // Create full-time employees
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setType(EmployeeType.FULL_TIME);
        
        Employee emp4 = new Employee();
        emp4.setName("Diana");
        emp4.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(emp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setCurrentDriver(emp2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setCurrentDriver(emp3);
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
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
        // Test Case 2: "No Part-Time Employees"
        // Setup
        company.setName("Quick Delivery");
        
        // Create full-time employees
        Employee emp1 = new Employee();
        emp1.setName("Ethan");
        emp1.setType(EmployeeType.FULL_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Fiona");
        emp2.setType(EmployeeType.FULL_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("George");
        emp3.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setCurrentDriver(emp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setCurrentDriver(emp2);
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        assertEquals("Registration numbers should be empty when no part-time employees drive vehicles", expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: "Mixed Employees and Vehicles"
        // Setup
        company.setName("Gourmet Delivery");
        
        // Create part-time employee
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType(EmployeeType.PART_TIME);
        
        // Create full-time employees
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setType(EmployeeType.FULL_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setType(EmployeeType.FULL_TIME);
        
        Employee emp4 = new Employee();
        emp4.setName("Kara");
        emp4.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setCurrentDriver(emp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setCurrentDriver(emp2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setCurrentDriver(emp3);
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("DEF333");
        assertEquals("Registration numbers should match expected list with mixed employees", expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: "Multiple Drivers for One Vehicle"
        // Setup
        company.setName("City Foods");
        
        // Create part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Lily");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Mike");
        emp2.setType(EmployeeType.PART_TIME);
        
        // Create full-time employee
        Employee emp3 = new Employee();
        emp3.setName("Nina");
        emp3.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setCurrentDriver(emp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setCurrentDriver(emp2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setCurrentDriver(emp3);
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("PQR777");
        expected.add("STU888");
        assertEquals("Registration numbers should match expected list with multiple part-time drivers", expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: "All Vehicles without Drivers"
        // Setup
        company.setName("Rapid Deliveries");
        
        // Create part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Olivia");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Paul");
        emp2.setType(EmployeeType.PART_TIME);
        
        // Create vehicles with no drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        // currentDriver remains null
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        // currentDriver remains null
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        assertEquals("Registration numbers should be empty when no vehicles have drivers", expected, result);
    }
}