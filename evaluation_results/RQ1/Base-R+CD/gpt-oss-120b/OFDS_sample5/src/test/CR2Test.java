import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Create a Company object named "Food Express"
        company.setName("Food Express");
        
        // Create two part-time employees
        Employee alice = new Employee("Alice", EmployeeType.PART_TIME);
        Employee bob = new Employee("Bob", EmployeeType.PART_TIME);
        
        // Create two full-time employees
        Employee charlie = new Employee("Charlie", EmployeeType.FULL_TIME);
        Employee diana = new Employee("Diana", EmployeeType.FULL_TIME);
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle("ABC123");
        Vehicle vehicle2 = new OwnedVehicle("XYZ789");
        Vehicle vehicle3 = new OwnedVehicle("LMN456");
        
        // Assign drivers to vehicles
        vehicle1.setCurrentDriver(alice);  // Alice (part-time) drives vehicle1
        vehicle2.setCurrentDriver(bob);    // Bob (part-time) drives vehicle2
        vehicle3.setCurrentDriver(charlie); // Charlie (full-time) drives vehicle3
        
        // Add employees to company
        company.addEmployee(alice);
        company.addEmployee(bob);
        company.addEmployee(charlie);
        company.addEmployee(diana);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: Registration numbers = ["ABC123", "XYZ789"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("XYZ789"));
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Create a Company object named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create three full-time employees
        Employee ethan = new Employee("Ethan", EmployeeType.FULL_TIME);
        Employee fiona = new Employee("Fiona", EmployeeType.FULL_TIME);
        Employee george = new Employee("George", EmployeeType.FULL_TIME);
        
        // Create two vehicles
        Vehicle vehicle1 = new OwnedVehicle("QWE111");
        Vehicle vehicle2 = new OwnedVehicle("RTY222");
        
        // Assign drivers to vehicles
        vehicle1.setCurrentDriver(ethan);  // Ethan (full-time) drives vehicle1
        vehicle2.setCurrentDriver(fiona);  // Fiona (full-time) drives vehicle2
        
        // Add employees to company
        company.addEmployee(ethan);
        company.addEmployee(fiona);
        company.addEmployee(george);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Retrieve registration numbers for vehicles with no part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: Registration numbers = []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Create a Company object named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        Employee henry = new Employee("Henry", EmployeeType.PART_TIME);
        
        // Create three full-time employees
        Employee isla = new Employee("Isla", EmployeeType.FULL_TIME);
        Employee jack = new Employee("Jack", EmployeeType.FULL_TIME);
        Employee kara = new Employee("Kara", EmployeeType.FULL_TIME);
        
        // Create four vehicles
        Vehicle vehicle1 = new OwnedVehicle("DEF333");
        Vehicle vehicle2 = new OwnedVehicle("GHI444");
        Vehicle vehicle3 = new OwnedVehicle("JKL555");
        
        // Assign drivers to vehicles
        vehicle1.setCurrentDriver(henry);  // Henry (part-time) drives vehicle1
        vehicle2.setCurrentDriver(isla);   // Isla (full-time) drives vehicle2
        vehicle3.setCurrentDriver(jack);   // Jack (full-time) drives vehicle3
        
        // Add employees to company
        company.addEmployee(henry);
        company.addEmployee(isla);
        company.addEmployee(jack);
        company.addEmployee(kara);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees amidst mixed employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: Registration numbers = ["DEF333"]
        assertEquals(1, result.size());
        assertTrue(result.contains("DEF333"));
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Create a Company object named "City Foods"
        company.setName("City Foods");
        
        // Create two part-time employees
        Employee lily = new Employee("Lily", EmployeeType.PART_TIME);
        Employee mike = new Employee("Mike", EmployeeType.PART_TIME);
        
        // Create one full-time employee
        Employee nina = new Employee("Nina", EmployeeType.FULL_TIME);
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle("PQR777");
        Vehicle vehicle2 = new OwnedVehicle("STU888");
        Vehicle vehicle3 = new OwnedVehicle("VWX999");
        
        // Assign drivers to vehicles
        vehicle1.setCurrentDriver(lily);   // Lily (part-time) drives vehicle1
        vehicle2.setCurrentDriver(mike);   // Mike (part-time) drives vehicle2
        vehicle3.setCurrentDriver(nina);   // Nina (full-time) drives vehicle3
        
        // Add employees to company
        company.addEmployee(lily);
        company.addEmployee(mike);
        company.addEmployee(nina);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve registration numbers for vehicles with multiple part-time drivers
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: Registration numbers = ["PQR777", "STU888"]
        assertEquals(2, result.size());
        assertTrue(result.contains("PQR777"));
        assertTrue(result.contains("STU888"));
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Create a Company object named "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create two part-time employees
        Employee olivia = new Employee("Olivia", EmployeeType.PART_TIME);
        Employee paul = new Employee("Paul", EmployeeType.PART_TIME);
        
        // Create two vehicles with no current drivers
        Vehicle vehicle1 = new OwnedVehicle("AAA000");
        Vehicle vehicle2 = new OwnedVehicle("BBB111");
        
        // Note: No drivers assigned to vehicles (currentDriver remains null)
        
        // Add employees to company
        company.addEmployee(olivia);
        company.addEmployee(paul);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Retrieve registration numbers for vehicles that currently have no drivers assigned
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected Output: Registration numbers = []
        assertTrue(result.isEmpty());
    }
}