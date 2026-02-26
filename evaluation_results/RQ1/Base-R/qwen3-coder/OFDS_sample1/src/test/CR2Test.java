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
        // Set up company
        company.setName("Food Express");
        
        // Create part-time employees
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setType("part-time");
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setType("part-time");
        
        // Create full-time employees
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setType("full-time");
        
        Employee diana = new Employee();
        diana.setName("Diana");
        diana.setType("full-time");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setDriver(alice);
        alice.setAssignedVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setDriver(bob);
        bob.setAssignedVehicle(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setDriver(charlie);
        charlie.setAssignedVehicle(vehicle3);
        
        // Add employees to company
        company.addEmployee(alice);
        company.addEmployee(bob);
        company.addEmployee(charlie);
        company.addEmployee(diana);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Test the method
        List<String> result = company.getPartTimeEmployeeVehicleRegistrations();
        
        // Verify expected output
        assertEquals(2, result.size());
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("XYZ789"));
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Set up company
        company.setName("Quick Delivery");
        
        // Create full-time employees
        Employee ethan = new Employee();
        ethan.setName("Ethan");
        ethan.setType("full-time");
        
        Employee fiona = new Employee();
        fiona.setName("Fiona");
        fiona.setType("full-time");
        
        Employee george = new Employee();
        george.setName("George");
        george.setType("full-time");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setDriver(ethan);
        ethan.setAssignedVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setDriver(fiona);
        fiona.setAssignedVehicle(vehicle2);
        
        // Add employees to company
        company.addEmployee(ethan);
        company.addEmployee(fiona);
        company.addEmployee(george);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Test the method
        List<String> result = company.getPartTimeEmployeeVehicleRegistrations();
        
        // Verify expected output
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Set up company
        company.setName("Gourmet Delivery");
        
        // Create part-time employee
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setType("part-time");
        
        // Create full-time employees
        Employee isla = new Employee();
        isla.setName("Isla");
        isla.setType("full-time");
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setType("full-time");
        
        Employee kara = new Employee();
        kara.setName("Kara");
        kara.setType("full-time");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setDriver(henry);
        henry.setAssignedVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setDriver(isla);
        isla.setAssignedVehicle(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setDriver(jack);
        jack.setAssignedVehicle(vehicle3);
        
        // Add employees to company
        company.addEmployee(henry);
        company.addEmployee(isla);
        company.addEmployee(jack);
        company.addEmployee(kara);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Test the method
        List<String> result = company.getPartTimeEmployeeVehicleRegistrations();
        
        // Verify expected output
        assertEquals(1, result.size());
        assertEquals("DEF333", result.get(0));
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Set up company
        company.setName("City Foods");
        
        // Create part-time employees
        Employee lily = new Employee();
        lily.setName("Lily");
        lily.setType("part-time");
        
        Employee mike = new Employee();
        mike.setName("Mike");
        mike.setType("part-time");
        
        // Create full-time employee
        Employee nina = new Employee();
        nina.setName("Nina");
        nina.setType("full-time");
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setDriver(lily);
        lily.setAssignedVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setDriver(mike);
        mike.setAssignedVehicle(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setDriver(nina);
        nina.setAssignedVehicle(vehicle3);
        
        // Add employees to company
        company.addEmployee(lily);
        company.addEmployee(mike);
        company.addEmployee(nina);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Test the method
        List<String> result = company.getPartTimeEmployeeVehicleRegistrations();
        
        // Verify expected output
        assertEquals(2, result.size());
        assertTrue(result.contains("PQR777"));
        assertTrue(result.contains("STU888"));
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Set up company
        company.setName("Rapid Deliveries");
        
        // Create part-time employees
        Employee olivia = new Employee();
        olivia.setName("Olivia");
        olivia.setType("part-time");
        
        Employee paul = new Employee();
        paul.setName("Paul");
        paul.setType("part-time");
        
        // Create vehicles with no drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setDriver(null);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setDriver(null);
        
        // Add employees to company
        company.addEmployee(olivia);
        company.addEmployee(paul);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Test the method
        List<String> result = company.getPartTimeEmployeeVehicleRegistrations();
        
        // Verify expected output
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }
}