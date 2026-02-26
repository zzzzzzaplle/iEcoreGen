import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = null;
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Test Case 1: "Single Part-Time Employee Vehicle Retrieval"
        // Setup
        company = new Company("Food Express");
        
        // Create employees
        Employee alice = new Employee("Alice", EmployeeType.PART_TIME);
        Employee bob = new Employee("Bob", EmployeeType.PART_TIME);
        Employee charlie = new Employee("Charlie", EmployeeType.FULL_TIME);
        Employee diana = new Employee("Diana", EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle("ABC123", VehicleType.OWNED);
        vehicle1.setDriver(alice); // part-time driver
        
        Vehicle vehicle2 = new Vehicle("XYZ789", VehicleType.OWNED);
        vehicle2.setDriver(bob); // part-time driver
        
        Vehicle vehicle3 = new Vehicle("LMN456", VehicleType.OWNED);
        vehicle3.setDriver(charlie); // full-time driver
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        assertEquals("Should return registration numbers of vehicles driven by part-time employees", expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Test Case 2: "No Part-Time Employees"
        // Setup
        company = new Company("Quick Delivery");
        
        // Create employees (all full-time)
        Employee ethan = new Employee("Ethan", EmployeeType.FULL_TIME);
        Employee fiona = new Employee("Fiona", EmployeeType.FULL_TIME);
        Employee george = new Employee("George", EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle("QWE111", VehicleType.OWNED);
        vehicle1.setDriver(ethan); // full-time driver
        
        Vehicle vehicle2 = new Vehicle("RTY222", VehicleType.OWNED);
        vehicle2.setDriver(fiona); // full-time driver
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify
        List<String> expected = Arrays.asList();
        assertTrue("Should return empty list when no part-time employees drive vehicles", result.isEmpty());
        assertEquals("Should return empty list when no part-time employees drive vehicles", expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: "Mixed Employees and Vehicles"
        // Setup
        company = new Company("Gourmet Delivery");
        
        // Create employees
        Employee henry = new Employee("Henry", EmployeeType.PART_TIME);
        Employee isla = new Employee("Isla", EmployeeType.FULL_TIME);
        Employee jack = new Employee("Jack", EmployeeType.FULL_TIME);
        Employee kara = new Employee("Kara", EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle("DEF333", VehicleType.OWNED);
        vehicle1.setDriver(henry); // part-time driver
        
        Vehicle vehicle2 = new Vehicle("GHI444", VehicleType.OWNED);
        vehicle2.setDriver(isla); // full-time driver
        
        Vehicle vehicle3 = new Vehicle("JKL555", VehicleType.OWNED);
        vehicle3.setDriver(jack); // full-time driver
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify
        List<String> expected = Arrays.asList("DEF333");
        assertEquals("Should return registration number of vehicle driven by part-time employee", expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: "Multiple Drivers for One Vehicle"
        // Setup
        company = new Company("City Foods");
        
        // Create employees
        Employee lily = new Employee("Lily", EmployeeType.PART_TIME);
        Employee mike = new Employee("Mike", EmployeeType.PART_TIME);
        Employee nina = new Employee("Nina", EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle("PQR777", VehicleType.OWNED);
        vehicle1.setDriver(lily); // part-time driver
        
        Vehicle vehicle2 = new Vehicle("STU888", VehicleType.OWNED);
        vehicle2.setDriver(mike); // part-time driver
        
        Vehicle vehicle3 = new Vehicle("VWX999", VehicleType.OWNED);
        vehicle3.setDriver(nina); // full-time driver
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify
        List<String> expected = Arrays.asList("PQR777", "STU888");
        assertEquals("Should return registration numbers of vehicles driven by part-time employees", expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: "All Vehicles without Drivers"
        // Setup
        company = new Company("Rapid Deliveries");
        
        // Create employees (not assigned to any vehicle)
        Employee olivia = new Employee("Olivia", EmployeeType.PART_TIME);
        Employee paul = new Employee("Paul", EmployeeType.PART_TIME);
        
        // Create vehicles with no drivers
        Vehicle vehicle1 = new Vehicle("AAA000", VehicleType.OWNED);
        vehicle1.setDriver(null); // no driver
        
        Vehicle vehicle2 = new Vehicle("BBB111", VehicleType.OWNED);
        vehicle2.setDriver(null); // no driver
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify
        List<String> expected = Arrays.asList();
        assertTrue("Should return empty list when no vehicles have drivers", result.isEmpty());
        assertEquals("Should return empty list when no vehicles have drivers", expected, result);
    }
}