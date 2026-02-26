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
        // Test Case 1: Single Part-Time Employee Vehicle Retrieval
        // SetUp: Create company "Food Express" with mixed employees and vehicles
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
        
        // Create vehicles with drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(alice);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setCurrentDriver(bob);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setCurrentDriver(charlie);
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(alice, bob, charlie, diana));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method and verify expected output
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        
        assertEquals("Registration numbers should match expected list", expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Test Case 2: No Part-Time Employees
        // SetUp: Create company "Quick Delivery" with only full-time employees
        company.setName("Quick Delivery");
        
        // Create full-time employees
        Employee ethan = new Employee();
        ethan.setName("Ethan");
        ethan.setType(EmployeeType.FULL_TIME);
        
        Employee fiona = new Employee();
        fiona.setName("Fiona");
        fiona.setType(EmployeeType.FULL_TIME);
        
        Employee george = new Employee();
        george.setName("George");
        george.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles with drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setCurrentDriver(ethan);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setCurrentDriver(fiona);
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(ethan, fiona, george));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method and verify expected output
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        List<String> expected = Arrays.asList();
        
        assertEquals("Registration numbers should be empty when no part-time employees drive vehicles", expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: Mixed Employees and Vehicles
        // SetUp: Create company "Gourmet Delivery" with mixed employees and vehicles
        company.setName("Gourmet Delivery");
        
        // Create part-time employee
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
        
        // Create vehicles with drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setCurrentDriver(henry);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setCurrentDriver(isla);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setCurrentDriver(jack);
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(henry, isla, jack, kara));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method and verify expected output
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        List<String> expected = Arrays.asList("DEF333");
        
        assertEquals("Registration numbers should match expected list with single part-time driver", expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: Multiple Drivers for One Vehicle
        // SetUp: Create company "City Foods" with multiple part-time drivers
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
        
        // Create vehicles with drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setCurrentDriver(lily);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setCurrentDriver(mike);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setCurrentDriver(nina);
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(lily, mike, nina));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method and verify expected output
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        List<String> expected = Arrays.asList("PQR777", "STU888");
        
        assertEquals("Registration numbers should match expected list with multiple part-time drivers", expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: All Vehicles without Drivers
        // SetUp: Create company "Rapid Deliveries" with vehicles having no drivers
        company.setName("Rapid Deliveries");
        
        // Create part-time employees (but vehicles have no drivers)
        Employee olivia = new Employee();
        olivia.setName("Olivia");
        olivia.setType(EmployeeType.PART_TIME);
        
        Employee paul = new Employee();
        paul.setName("Paul");
        paul.setType(EmployeeType.PART_TIME);
        
        // Create vehicles without drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setCurrentDriver(null);
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(olivia, paul));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method and verify expected output
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        List<String> expected = Arrays.asList();
        
        assertEquals("Registration numbers should be empty when vehicles have no drivers", expected, result);
    }
}