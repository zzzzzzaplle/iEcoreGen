import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Test Case 1: Single Part-Time Employee Vehicle Retrieval
        // SetUp: Create company and employees
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
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(emp1, emp2, emp3, emp4));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Test: Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        assertEquals("Should return registration numbers of vehicles driven by part-time employees", 
                     expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Test Case 2: No Part-Time Employees
        // SetUp: Create company with only full-time employees
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
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(emp1, emp2, emp3));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Test: Retrieve registration numbers for vehicles with no part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output (empty list)
        List<String> expected = Arrays.asList();
        assertEquals("Should return empty list when no part-time employees exist", 
                     expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: Mixed Employees and Vehicles
        // SetUp: Create company with mixed employees
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
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(emp1, emp2, emp3, emp4));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Test: Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output
        List<String> expected = Arrays.asList("DEF333");
        assertEquals("Should return registration number of vehicle driven by the only part-time employee", 
                     expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: Multiple Drivers for One Vehicle
        // SetUp: Create company with multiple part-time drivers
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
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(emp1, emp2, emp3));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Test: Retrieve registration numbers for vehicles with multiple part-time drivers
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output
        List<String> expected = Arrays.asList("PQR777", "STU888");
        assertEquals("Should return registration numbers of vehicles driven by all part-time employees", 
                     expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: All Vehicles without Drivers
        // SetUp: Create company with vehicles having no drivers
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
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setCurrentDriver(null);
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(emp1, emp2));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Test: Retrieve registration numbers for vehicles that currently have no drivers assigned
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify expected output (empty list)
        List<String> expected = Arrays.asList();
        assertEquals("Should return empty list when no vehicles have drivers assigned", 
                     expected, result);
    }
}