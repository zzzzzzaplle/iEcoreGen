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
        // Test Case 1: "Single Part-Time Employee Vehicle Retrieval"
        // Setup: Create Company "Food Express"
        company.setName("Food Express");
        
        // Create part-time employees
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setType(EmployeeType.PART_TIME);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setType(EmployeeType.PART_TIME);
        
        // Create full-time employees
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setType(EmployeeType.FULL_TIME);
        
        Employee diana = new Employee();
        diana.setName("Diana");
        diana.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        
        // Assign vehicles to employees
        alice.setAssignedVehicle(vehicle1);
        bob.setAssignedVehicle(vehicle2);
        charlie.setAssignedVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(alice, bob, charlie, diana));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method and verify result
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        
        assertEquals("Registration numbers should match expected output", expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Test Case 2: "No Part-Time Employees"
        // Setup: Create Company "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create full-time employees only
        Employee ethan = new Employee();
        ethan.setName("Ethan");
        ethan.setType(EmployeeType.FULL_TIME);
        
        Employee fiona = new Employee();
        fiona.setName("Fiona");
        fiona.setType(EmployeeType.FULL_TIME);
        
        Employee george = new Employee();
        george.setName("George");
        george.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("QWE111");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("RTY222");
        
        // Assign vehicles to employees
        ethan.setAssignedVehicle(vehicle1);
        fiona.setAssignedVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(ethan, fiona, george));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method and verify result
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        List<String> expected = Arrays.asList();
        
        assertEquals("Registration numbers should be empty when no part-time employees", expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: "Mixed Employees and Vehicles"
        // Setup: Create Company "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setType(EmployeeType.PART_TIME);
        
        // Create full-time employees
        Employee isla = new Employee();
        isla.setName("Isla");
        isla.setType(EmployeeType.FULL_TIME);
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setType(EmployeeType.FULL_TIME);
        
        Employee kara = new Employee();
        kara.setName("Kara");
        kara.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF333");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("GHI444");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("JKL555");
        
        // Assign vehicles to employees - only part-time employee gets a vehicle
        henry.setAssignedVehicle(vehicle1);
        isla.setAssignedVehicle(vehicle2);
        jack.setAssignedVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(henry, isla, jack, kara));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method and verify result
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        List<String> expected = Arrays.asList("DEF333");
        
        assertEquals("Registration numbers should contain only part-time employee's vehicle", expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: "Multiple Drivers for One Vehicle"
        // Note: Test specification says "multiple part-time drivers" but setup shows separate vehicles for each
        // Setup: Create Company "City Foods"
        company.setName("City Foods");
        
        // Create part-time employees
        Employee lily = new Employee();
        lily.setName("Lily");
        lily.setType(EmployeeType.PART_TIME);
        
        Employee mike = new Employee();
        mike.setName("Mike");
        mike.setType(EmployeeType.PART_TIME);
        
        // Create full-time employee
        Employee nina = new Employee();
        nina.setName("Nina");
        nina.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("PQR777");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("STU888");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("VWX999");
        
        // Assign vehicles to employees (each part-time employee gets their own vehicle)
        lily.setAssignedVehicle(vehicle1);
        mike.setAssignedVehicle(vehicle2);
        nina.setAssignedVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(lily, mike, nina));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method and verify result
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        List<String> expected = Arrays.asList("PQR777", "STU888");
        
        assertEquals("Registration numbers should contain vehicles from both part-time employees", expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: "All Vehicles without Drivers"
        // Setup: Create Company "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create part-time employees (but no vehicles assigned to them)
        Employee olivia = new Employee();
        olivia.setName("Olivia");
        olivia.setType(EmployeeType.PART_TIME);
        
        Employee paul = new Employee();
        paul.setName("Paul");
        paul.setType(EmployeeType.PART_TIME);
        
        // Create vehicles with no drivers assigned
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("AAA000");
        // currentDriver remains null
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("BBB111");
        // currentDriver remains null
        
        // Add employees and vehicles to company (no vehicle assignments)
        company.setEmployees(Arrays.asList(olivia, paul));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method and verify result
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        List<String> expected = Arrays.asList();
        
        assertEquals("Registration numbers should be empty when no vehicles are assigned to part-time employees", expected, result);
    }
}