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
        // Setup: Create company "Food Express"
        company.setName("Food Express");
        
        // Create part-time employees
        PartTimeEmployee alice = new PartTimeEmployee();
        alice.setName("Alice");
        
        PartTimeEmployee bob = new PartTimeEmployee();
        bob.setName("Bob");
        
        // Create full-time employees
        FullTimeEmployee charlie = new FullTimeEmployee();
        charlie.setName("Charlie");
        
        FullTimeEmployee diana = new FullTimeEmployee();
        diana.setName("Diana");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle() {};
        vehicle1.setRegistrationNumber("ABC123");
        
        Vehicle vehicle2 = new Vehicle() {};
        vehicle2.setRegistrationNumber("XYZ789");
        
        Vehicle vehicle3 = new Vehicle() {};
        vehicle3.setRegistrationNumber("LMN456");
        
        // Assign vehicles to employees
        alice.setVehicle(vehicle1);
        bob.setVehicle(vehicle2);
        charlie.setVehicle(vehicle3);
        
        // Add employees to company
        company.setEmployees(Arrays.asList(alice, bob, charlie, diana));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Expected output = ["ABC123", "XYZ789"]
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Setup: Create company "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create full-time employees only
        FullTimeEmployee ethan = new FullTimeEmployee();
        ethan.setName("Ethan");
        
        FullTimeEmployee fiona = new FullTimeEmployee();
        fiona.setName("Fiona");
        
        FullTimeEmployee george = new FullTimeEmployee();
        george.setName("George");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle() {};
        vehicle1.setRegistrationNumber("QWE111");
        
        Vehicle vehicle2 = new Vehicle() {};
        vehicle2.setRegistrationNumber("RTY222");
        
        // Assign vehicles to employees
        ethan.setVehicle(vehicle1);
        fiona.setVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(ethan, fiona, george));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute: Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Expected output = [] (empty list)
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Setup: Create company "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        PartTimeEmployee henry = new PartTimeEmployee();
        henry.setName("Henry");
        
        // Create full-time employees
        FullTimeEmployee isla = new FullTimeEmployee();
        isla.setName("Isla");
        
        FullTimeEmployee jack = new FullTimeEmployee();
        jack.setName("Jack");
        
        FullTimeEmployee kara = new FullTimeEmployee();
        kara.setName("Kara");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle() {};
        vehicle1.setRegistrationNumber("DEF333");
        
        Vehicle vehicle2 = new Vehicle() {};
        vehicle2.setRegistrationNumber("GHI444");
        
        Vehicle vehicle3 = new Vehicle() {};
        vehicle3.setRegistrationNumber("JKL555");
        
        // Assign vehicles to employees
        henry.setVehicle(vehicle1);
        isla.setVehicle(vehicle2);
        jack.setVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(henry, isla, jack, kara));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Expected output = ["DEF333"]
        List<String> expected = Arrays.asList("DEF333");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Setup: Create company "City Foods"
        company.setName("City Foods");
        
        // Create part-time employees
        PartTimeEmployee lily = new PartTimeEmployee();
        lily.setName("Lily");
        
        PartTimeEmployee mike = new PartTimeEmployee();
        mike.setName("Mike");
        
        // Create full-time employee
        FullTimeEmployee nina = new FullTimeEmployee();
        nina.setName("Nina");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle() {};
        vehicle1.setRegistrationNumber("PQR777");
        
        Vehicle vehicle2 = new Vehicle() {};
        vehicle2.setRegistrationNumber("STU888");
        
        Vehicle vehicle3 = new Vehicle() {};
        vehicle3.setRegistrationNumber("VWX999");
        
        // Assign vehicles to employees
        lily.setVehicle(vehicle1);
        mike.setVehicle(vehicle2);
        nina.setVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(lily, mike, nina));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Expected output = ["PQR777", "STU888"]
        List<String> expected = Arrays.asList("PQR777", "STU888");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Setup: Create company "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create part-time employees
        PartTimeEmployee olivia = new PartTimeEmployee();
        olivia.setName("Olivia");
        
        PartTimeEmployee paul = new PartTimeEmployee();
        paul.setName("Paul");
        
        // Create vehicles with no drivers
        Vehicle vehicle1 = new Vehicle() {};
        vehicle1.setRegistrationNumber("AAA000");
        
        Vehicle vehicle2 = new Vehicle() {};
        vehicle2.setRegistrationNumber("BBB111");
        
        // Add employees and vehicles to company (vehicles not assigned to any employee)
        company.setEmployees(Arrays.asList(olivia, paul));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute: Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Expected output = [] (empty list)
        assertTrue(result.isEmpty());
    }
}