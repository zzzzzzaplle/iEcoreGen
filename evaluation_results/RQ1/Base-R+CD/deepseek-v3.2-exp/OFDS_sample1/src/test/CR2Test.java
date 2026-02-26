import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

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
        
        // Create employees
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setType(EmployeeType.PART_TIME);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setType(EmployeeType.PART_TIME);
        
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setType(EmployeeType.FULL_TIME);
        
        Employee diana = new Employee();
        diana.setName("Diana");
        diana.setType(EmployeeType.FULL_TIME);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        employees.add(charlie);
        employees.add(diana);
        company.setEmployees(employees);
        
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
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Test: Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: ["ABC123", "XYZ789"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("XYZ789"));
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // SetUp: Create Company "Quick Delivery"
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
        
        List<Employee> employees = new ArrayList<>();
        employees.add(ethan);
        employees.add(fiona);
        employees.add(george);
        company.setEmployees(employees);
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setCurrentDriver(ethan);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setCurrentDriver(fiona);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Test: Retrieve registration numbers for vehicles with no part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // SetUp: Create Company "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create mixed employees
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setType(EmployeeType.PART_TIME);
        
        Employee isla = new Employee();
        isla.setName("Isla");
        isla.setType(EmployeeType.FULL_TIME);
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setType(EmployeeType.FULL_TIME);
        
        Employee kara = new Employee();
        kara.setName("Kara");
        kara.setType(EmployeeType.FULL_TIME);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(henry);
        employees.add(isla);
        employees.add(jack);
        employees.add(kara);
        company.setEmployees(employees);
        
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
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Test: Retrieve registration numbers for vehicles driven by part-time employees amidst mixed employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: ["DEF333"]
        assertEquals(1, result.size());
        assertEquals("DEF333", result.get(0));
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // SetUp: Create Company "City Foods"
        company.setName("City Foods");
        
        // Create employees
        Employee lily = new Employee();
        lily.setName("Lily");
        lily.setType(EmployeeType.PART_TIME);
        
        Employee mike = new Employee();
        mike.setName("Mike");
        mike.setType(EmployeeType.PART_TIME);
        
        Employee nina = new Employee();
        nina.setName("Nina");
        nina.setType(EmployeeType.FULL_TIME);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(lily);
        employees.add(mike);
        employees.add(nina);
        company.setEmployees(employees);
        
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
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Test: Retrieve registration numbers for vehicles with multiple part-time drivers
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: ["PQR777", "STU888"]
        assertEquals(2, result.size());
        assertTrue(result.contains("PQR777"));
        assertTrue(result.contains("STU888"));
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
        
        List<Employee> employees = new ArrayList<>();
        employees.add(olivia);
        employees.add(paul);
        company.setEmployees(employees);
        
        // Create vehicles with no current drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setCurrentDriver(null);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Test: Retrieve registration numbers for vehicles that currently have no drivers assigned
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
}