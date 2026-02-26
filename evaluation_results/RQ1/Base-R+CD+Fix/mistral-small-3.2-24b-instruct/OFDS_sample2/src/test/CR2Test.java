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
        // SetUp: Create Company object "Food Express"
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
        
        // Create vehicles and assign drivers
        Vehicle vehicle1 = new OwnedVehicle("ABC123");
        vehicle1.setCurrentDriver(alice);
        alice.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle("XYZ789");
        vehicle2.setCurrentDriver(bob);
        bob.setDrivenVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle("LMN456");
        vehicle3.setCurrentDriver(charlie);
        charlie.setDrivenVehicle(vehicle3);
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(alice, bob, charlie, diana));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected Output: Registration numbers = ["ABC123", "XYZ789"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("XYZ789"));
        assertEquals(Arrays.asList("ABC123", "XYZ789"), result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // SetUp: Create Company object "Quick Delivery"
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
        
        // Create vehicles and assign drivers
        Vehicle vehicle1 = new OwnedVehicle("QWE111");
        vehicle1.setCurrentDriver(ethan);
        ethan.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle("RTY222");
        vehicle2.setCurrentDriver(fiona);
        fiona.setDrivenVehicle(vehicle2);
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(ethan, fiona, george));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute: Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected Output: Registration numbers = []
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // SetUp: Create Company object "Gourmet Delivery"
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
        
        // Create vehicles and assign drivers
        Vehicle vehicle1 = new OwnedVehicle("DEF333");
        vehicle1.setCurrentDriver(henry);
        henry.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle("GHI444");
        vehicle2.setCurrentDriver(isla);
        isla.setDrivenVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle("JKL555");
        vehicle3.setCurrentDriver(jack);
        jack.setDrivenVehicle(vehicle3);
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(henry, isla, jack, kara));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected Output: Registration numbers = ["DEF333"]
        assertEquals(1, result.size());
        assertEquals("DEF333", result.get(0));
        assertEquals(Arrays.asList("DEF333"), result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // SetUp: Create Company object "City Foods"
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
        
        // Create vehicles and assign drivers
        Vehicle vehicle1 = new OwnedVehicle("PQR777");
        vehicle1.setCurrentDriver(lily);
        lily.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle("STU888");
        vehicle2.setCurrentDriver(mike);
        mike.setDrivenVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle("VWX999");
        vehicle3.setCurrentDriver(nina);
        nina.setDrivenVehicle(vehicle3);
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(lily, mike, nina));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected Output: Registration numbers = ["PQR777", "STU888"]
        assertEquals(2, result.size());
        assertTrue(result.contains("PQR777"));
        assertTrue(result.contains("STU888"));
        assertEquals(Arrays.asList("PQR777", "STU888"), result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // SetUp: Create Company object "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create part-time employees
        Employee olivia = new Employee();
        olivia.setName("Olivia");
        olivia.setType(EmployeeType.PART_TIME);
        
        Employee paul = new Employee();
        paul.setName("Paul");
        paul.setType(EmployeeType.PART_TIME);
        
        // Create vehicles with no drivers
        Vehicle vehicle1 = new OwnedVehicle("AAA000");
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle("BBB111");
        vehicle2.setCurrentDriver(null);
        
        // Assign employees and vehicles to company
        company.setEmployees(Arrays.asList(olivia, paul));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute: Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected Output: Registration numbers = []
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }
}