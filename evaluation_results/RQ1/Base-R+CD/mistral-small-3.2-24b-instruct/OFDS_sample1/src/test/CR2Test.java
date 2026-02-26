import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Setup: Create Company "Food Express"
        company.setName("Food Express");
        
        // Create two part-time employees
        Employee employee1 = new Employee();
        employee1.setName("Alice");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create two full-time employees
        Employee employee3 = new Employee();
        employee3.setName("Charlie");
        employee3.setType(EmployeeType.FULL_TIME);
        
        Employee employee4 = new Employee();
        employee4.setName("Diana");
        employee4.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles with assigned drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(employee1); // Part-time driver
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setCurrentDriver(employee2); // Part-time driver
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setCurrentDriver(employee3); // Full-time driver
        
        // Add employees to company
        company.setEmployees(Arrays.asList(employee1, employee2, employee3, employee4));
        
        // Add vehicles to company
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected registration numbers = ["ABC123", "XYZ789"]
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Setup: Create Company "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create three full-time employees
        Employee employee1 = new Employee();
        employee1.setName("Ethan");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Fiona");
        employee2.setType(EmployeeType.FULL_TIME);
        
        Employee employee3 = new Employee();
        employee3.setName("George");
        employee3.setType(EmployeeType.FULL_TIME);
        
        // Create two vehicles with full-time drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setCurrentDriver(employee1); // Full-time driver
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setCurrentDriver(employee2); // Full-time driver
        
        // Add employees to company
        company.setEmployees(Arrays.asList(employee1, employee2, employee3));
        
        // Add vehicles to company
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute: Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected registration numbers = [] (empty list)
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Setup: Create Company "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        Employee employee1 = new Employee();
        employee1.setName("Henry");
        employee1.setType(EmployeeType.PART_TIME);
        
        // Create three full-time employees
        Employee employee2 = new Employee();
        employee2.setName("Isla");
        employee2.setType(EmployeeType.FULL_TIME);
        
        Employee employee3 = new Employee();
        employee3.setName("Jack");
        employee3.setType(EmployeeType.FULL_TIME);
        
        Employee employee4 = new Employee();
        employee4.setName("Kara");
        employee4.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles (only 3 vehicles as specified in setup)
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setCurrentDriver(employee1); // Part-time driver
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setCurrentDriver(employee2); // Full-time driver
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setCurrentDriver(employee3); // Full-time driver
        
        // Add employees to company
        company.setEmployees(Arrays.asList(employee1, employee2, employee3, employee4));
        
        // Add vehicles to company
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected registration numbers = ["DEF333"]
        List<String> expected = Arrays.asList("DEF333");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Setup: Create Company "City Foods"
        company.setName("City Foods");
        
        // Create two part-time employees
        Employee employee1 = new Employee();
        employee1.setName("Lily");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Mike");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create one full-time employee
        Employee employee3 = new Employee();
        employee3.setName("Nina");
        employee3.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles as specified in setup
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setCurrentDriver(employee1); // Part-time driver
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setCurrentDriver(employee2); // Part-time driver
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setCurrentDriver(employee3); // Full-time driver
        
        // Add employees to company
        company.setEmployees(Arrays.asList(employee1, employee2, employee3));
        
        // Add vehicles to company
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected registration numbers = ["PQR777", "STU888"]
        List<String> expected = Arrays.asList("PQR777", "STU888");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Setup: Create Company "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create two part-time employees (though they won't be assigned as drivers)
        Employee employee1 = new Employee();
        employee1.setName("Olivia");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Paul");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create two vehicles with no current drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setCurrentDriver(null); // No driver
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setCurrentDriver(null); // No driver
        
        // Add employees to company
        company.setEmployees(Arrays.asList(employee1, employee2));
        
        // Add vehicles to company
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute: Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected registration numbers = [] (empty list)
        assertTrue(result.isEmpty());
    }
}