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
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        
        // Assign drivers to vehicles
        vehicle1.setDriver(alice); // Alice (part-time) drives ABC123
        vehicle2.setDriver(bob);   // Bob (part-time) drives XYZ789
        vehicle3.setDriver(charlie); // Charlie (full-time) drives LMN456
        
        // Add employees to company
        company.setEmployees(Arrays.asList(alice, bob, charlie, diana));
        
        // Add vehicles to company
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method under test
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        assertEquals("Should return registration numbers of vehicles driven by part-time employees", 
                     expected, result);
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
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("QWE111");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("RTY222");
        
        // Assign drivers to vehicles
        vehicle1.setDriver(ethan);  // Ethan (full-time) drives QWE111
        vehicle2.setDriver(fiona);  // Fiona (full-time) drives RTY222
        
        // Add employees to company
        company.setEmployees(Arrays.asList(ethan, fiona, george));
        
        // Add vehicles to company
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method under test
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList();
        assertEquals("Should return empty list when no part-time employees exist", 
                     expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // SetUp: Create Company "Gourmet Delivery"
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
        
        // Assign drivers to vehicles
        vehicle1.setDriver(henry); // Henry (part-time) drives DEF333
        vehicle2.setDriver(isla);  // Isla (full-time) drives GHI444
        vehicle3.setDriver(jack);  // Jack (full-time) drives JKL555
        
        // Add employees to company
        company.setEmployees(Arrays.asList(henry, isla, jack, kara));
        
        // Add vehicles to company
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method under test
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList("DEF333");
        assertEquals("Should return registration number of vehicle driven by the only part-time employee", 
                     expected, result);
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
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("PQR777");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("STU888");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("VWX999");
        
        // Assign drivers to vehicles
        vehicle1.setDriver(lily);  // Lily (part-time) drives PQR777
        vehicle2.setDriver(mike);  // Mike (part-time) drives STU888
        vehicle3.setDriver(nina);  // Nina (full-time) drives VWX999
        
        // Add employees to company
        company.setEmployees(Arrays.asList(lily, mike, nina));
        
        // Add vehicles to company
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method under test
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList("PQR777", "STU888");
        assertEquals("Should return registration numbers of vehicles driven by multiple part-time employees", 
                     expected, result);
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
        
        // Create vehicles with no drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("AAA000");
        // No driver assigned - vehicle1.setDriver(null) is implicit
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("BBB111");
        // No driver assigned - vehicle2.setDriver(null) is implicit
        
        // Add employees to company
        company.setEmployees(Arrays.asList(olivia, paul));
        
        // Add vehicles to company
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method under test
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList();
        assertEquals("Should return empty list when no vehicles have drivers assigned", 
                     expected, result);
    }
}