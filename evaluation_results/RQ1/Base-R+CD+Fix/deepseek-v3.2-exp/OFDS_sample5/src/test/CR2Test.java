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
        // SetUp: Create Company "Food Express"
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
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(alice);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setCurrentDriver(bob);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setCurrentDriver(charlie);
        
        // Set employees list
        List<Employee> employees = Arrays.asList(alice, bob, charlie, diana);
        company.setEmployees(employees);
        
        // Set vehicles list
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2, vehicle3);
        company.setVehicles(vehicles);
        
        // Test: Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: ["ABC123", "XYZ789"]
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // SetUp: Create Company "Quick Delivery"
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
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setCurrentDriver(ethan);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setCurrentDriver(fiona);
        
        // Set employees list
        List<Employee> employees = Arrays.asList(ethan, fiona, george);
        company.setEmployees(employees);
        
        // Set vehicles list
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        company.setVehicles(vehicles);
        
        // Test: Retrieve registration numbers for vehicles with no part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: []
        List<String> expected = Arrays.asList();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // SetUp: Create Company "Gourmet Delivery"
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
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setCurrentDriver(henry);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setCurrentDriver(isla);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setCurrentDriver(jack);
        
        // Set employees list
        List<Employee> employees = Arrays.asList(henry, isla, jack, kara);
        company.setEmployees(employees);
        
        // Set vehicles list
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2, vehicle3);
        company.setVehicles(vehicles);
        
        // Test: Retrieve registration numbers for vehicles driven by part-time employees amidst mixed employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: ["DEF333"]
        List<String> expected = Arrays.asList("DEF333");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // SetUp: Create Company "City Foods"
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
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setCurrentDriver(lily);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setCurrentDriver(mike);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setCurrentDriver(nina);
        
        // Set employees list
        List<Employee> employees = Arrays.asList(lily, mike, nina);
        company.setEmployees(employees);
        
        // Set vehicles list
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2, vehicle3);
        company.setVehicles(vehicles);
        
        // Test: Retrieve registration numbers for vehicles with multiple part-time drivers
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: ["PQR777", "STU888"]
        List<String> expected = Arrays.asList("PQR777", "STU888");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // SetUp: Create Company "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create part-time employees
        Employee olivia = new Employee();
        olivia.setName("Olivia");
        olivia.setType(EmployeeType.PART_TIME);
        
        Employee paul = new Employee();
        paul.setName("Paul");
        paul.setType(EmployeeType.PART_TIME);
        
        // Create vehicles with no current drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setCurrentDriver(null);
        
        // Set employees list
        List<Employee> employees = Arrays.asList(olivia, paul);
        company.setEmployees(employees);
        
        // Set vehicles list
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        company.setVehicles(vehicles);
        
        // Test: Retrieve registration numbers for vehicles that currently have no drivers assigned
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: []
        List<String> expected = Arrays.asList();
        assertEquals(expected, result);
    }
}