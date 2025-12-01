import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR2Test {
    
    private Company company;
    private List<Employee> employees;
    private List<Vehicle> vehicles;
    
    @Before
    public void setUp() {
        company = new Company();
        employees = new ArrayList<>();
        vehicles = new ArrayList<>();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Create a Company object named "Food Express"
        company.setName("Food Express");
        
        // Create two part-time employees
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setType(EmployeeType.PART_TIME);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setType(EmployeeType.PART_TIME);
        
        // Create two full-time employees
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setType(EmployeeType.FULL_TIME);
        
        Employee diana = new Employee();
        diana.setName("Diana");
        diana.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(alice);
        alice.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setCurrentDriver(bob);
        bob.setDrivenVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setCurrentDriver(charlie);
        charlie.setDrivenVehicle(vehicle3);
        
        // Assign employees and vehicles to the Company object
        List<Employee> employees = Arrays.asList(alice, bob, charlie, diana);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2, vehicle3);
        
        company.setEmployees(employees);
        company.setVehicles(vehicles);
        
        // Expected Output: Registration numbers = ["ABC123", "XYZ789"]
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Create a Company object named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create three full-time employees
        Employee ethan = new Employee();
        ethan.setName("Ethan");
        ethan.setType(EmployeeType.FULL_TIME);
        
        Employee fiona = new Employee();
        fiona.setName("Fiona");
        fiona.setType(EmployeeType.FULL_TIME);
        
        Employee george = new Employee();
        george.setName("George");
        george.setType(EmployeeType.FULL_TIME);
        
        // Create two vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setCurrentDriver(ethan);
        ethan.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setCurrentDriver(fiona);
        fiona.setDrivenVehicle(vehicle2);
        
        // Assign employees and vehicles to the Company object
        List<Employee> employees = Arrays.asList(ethan, fiona, george);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        
        company.setEmployees(employees);
        company.setVehicles(vehicles);
        
        // Expected Output: Registration numbers = []
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        List<String> expected = new ArrayList<>();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Create a Company object named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setType(EmployeeType.PART_TIME);
        
        // Create three full-time employees
        Employee isla = new Employee();
        isla.setName("Isla");
        isla.setType(EmployeeType.FULL_TIME);
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setType(EmployeeType.FULL_TIME);
        
        Employee kara = new Employee();
        kara.setName("Kara");
        kara.setType(EmployeeType.FULL_TIME);
        
        // Create four vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setCurrentDriver(henry);
        henry.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setCurrentDriver(isla);
        isla.setDrivenVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setCurrentDriver(jack);
        jack.setDrivenVehicle(vehicle3);
        
        // Assign employees and vehicles to the Company object
        List<Employee> employees = Arrays.asList(henry, isla, jack, kara);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2, vehicle3);
        
        company.setEmployees(employees);
        company.setVehicles(vehicles);
        
        // Expected Output: Registration numbers = ["DEF333"]
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        List<String> expected = Arrays.asList("DEF333");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Create a Company object named "City Foods"
        company.setName("City Foods");
        
        // Create two part-time employees
        Employee lily = new Employee();
        lily.setName("Lily");
        lily.setType(EmployeeType.PART_TIME);
        
        Employee mike = new Employee();
        mike.setName("Mike");
        mike.setType(EmployeeType.PART_TIME);
        
        // Create one full-time employee
        Employee nina = new Employee();
        nina.setName("Nina");
        nina.setType(EmployeeType.FULL_TIME);
        
        // Create two vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setCurrentDriver(lily);
        lily.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setCurrentDriver(mike);
        mike.setDrivenVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setCurrentDriver(nina);
        nina.setDrivenVehicle(vehicle3);
        
        // Assign employees and vehicles to the Company object
        List<Employee> employees = Arrays.asList(lily, mike, nina);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2, vehicle3);
        
        company.setEmployees(employees);
        company.setVehicles(vehicles);
        
        // Expected Output: Registration numbers = ["PQR777", "STU888"]
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        List<String> expected = Arrays.asList("PQR777", "STU888");
        
        assertEquals(expected.size(), result.size());
        assertTrue(result.containsAll(expected));
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Create a Company object named "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create two part-time employees
        Employee olivia = new Employee();
        olivia.setName("Olivia");
        olivia.setType(EmployeeType.PART_TIME);
        
        Employee paul = new Employee();
        paul.setName("Paul");
        paul.setType(EmployeeType.PART_TIME);
        
        // Create two vehicles with no current drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setCurrentDriver(null);
        
        // Assign employees and vehicles to the Company object
        List<Employee> employees = Arrays.asList(olivia, paul);
        List<Vehicle> vehicles = Arrays.asList(vehicle1, vehicle2);
        
        company.setEmployees(employees);
        company.setVehicles(vehicles);
        
        // Expected Output: Registration numbers = []
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        List<String> expected = new ArrayList<>();
        
        assertEquals(expected, result);
    }
}