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
        // SetUp: Create a Company object named "Food Express"
        company.setName("Food Express");
        
        // Create two part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType(EmployeeType.PART_TIME);
        
        // Create two full-time employees
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setType(EmployeeType.FULL_TIME);
        
        Employee emp4 = new Employee();
        emp4.setName("Diana");
        emp4.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles
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
        
        // Expected Output: Registration numbers = ["ABC123", "XYZ789"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("XYZ789"));
        assertFalse(result.contains("LMN456"));
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // SetUp: Create a Company object named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create three full-time employees
        Employee emp1 = new Employee();
        emp1.setName("Ethan");
        emp1.setType(EmployeeType.FULL_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Fiona");
        emp2.setType(EmployeeType.FULL_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("George");
        emp3.setType(EmployeeType.FULL_TIME);
        
        // Create two vehicles
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
        
        // Expected Output: Registration numbers = []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // SetUp: Create a Company object named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType(EmployeeType.PART_TIME);
        
        // Create three full-time employees
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setType(EmployeeType.FULL_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setType(EmployeeType.FULL_TIME);
        
        Employee emp4 = new Employee();
        emp4.setName("Kara");
        emp4.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles (note: specification says four but only three are described)
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
        
        // Expected Output: Registration numbers = ["DEF333"]
        assertEquals(1, result.size());
        assertEquals("DEF333", result.get(0));
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // SetUp: Create a Company object named "City Foods"
        company.setName("City Foods");
        
        // Create two part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Lily");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Mike");
        emp2.setType(EmployeeType.PART_TIME);
        
        // Create one full-time employee
        Employee emp3 = new Employee();
        emp3.setName("Nina");
        emp3.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles
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
        
        // Expected Output: Registration numbers = ["PQR777", "STU888"]
        assertEquals(2, result.size());
        assertTrue(result.contains("PQR777"));
        assertTrue(result.contains("STU888"));
        assertFalse(result.contains("VWX999"));
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // SetUp: Create a Company object named "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create two part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Olivia");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Paul");
        emp2.setType(EmployeeType.PART_TIME);
        
        // Create two vehicles with no current drivers
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
        
        // Expected Output: Registration numbers = []
        assertTrue(result.isEmpty());
    }
}