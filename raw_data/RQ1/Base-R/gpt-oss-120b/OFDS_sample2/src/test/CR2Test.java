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
        company = new Company();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Create a Company object named "Food Express"
        company.setName("Food Express");
        
        // Create two part-time employees
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Alice");
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Bob");
        
        // Create two full-time employees
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Charlie");
        FullTimeEmployee emp4 = new FullTimeEmployee();
        emp4.setName("Diana");
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        
        // Assign drivers to vehicles
        vehicle1.setDriver(emp1); // Part-time employee
        vehicle2.setDriver(emp2); // Part-time employee
        vehicle3.setDriver(emp3); // Full-time employee
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Expected output: Registration numbers = ["ABC123", "XYZ789"]
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        assertEquals("Should return registration numbers of vehicles driven by part-time employees", 
                     expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Create a Company object named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create three full-time employees
        FullTimeEmployee emp1 = new FullTimeEmployee();
        emp1.setName("Ethan");
        FullTimeEmployee emp2 = new FullTimeEmployee();
        emp2.setName("Fiona");
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("George");
        
        // Create two vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        
        // Assign drivers to vehicles
        vehicle1.setDriver(emp1); // Full-time employee
        vehicle2.setDriver(emp2); // Full-time employee
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Expected output: Registration numbers = []
        assertTrue("Should return empty list when no part-time employees drive vehicles", 
                   result.isEmpty());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Create a Company object named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Henry");
        
        // Create three full-time employees
        FullTimeEmployee emp2 = new FullTimeEmployee();
        emp2.setName("Isla");
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Jack");
        FullTimeEmployee emp4 = new FullTimeEmployee();
        emp4.setName("Kara");
        
        // Create four vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        
        // Assign drivers to vehicles
        vehicle1.setDriver(emp1); // Part-time employee
        vehicle2.setDriver(emp2); // Full-time employee
        vehicle3.setDriver(emp3); // Full-time employee
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Expected output: Registration numbers = ["DEF333"]
        List<String> expected = Arrays.asList("DEF333");
        assertEquals("Should return registration number of vehicle driven by the only part-time employee", 
                     expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Create a Company object named "City Foods"
        company.setName("City Foods");
        
        // Create two part-time employees
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Lily");
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Mike");
        
        // Create one full-time employee
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Nina");
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        
        // Assign drivers to vehicles
        vehicle1.setDriver(emp1); // Part-time employee
        vehicle2.setDriver(emp2); // Part-time employee
        vehicle3.setDriver(emp3); // Full-time employee
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Expected output: Registration numbers = ["PQR777", "STU888"]
        List<String> expected = Arrays.asList("PQR777", "STU888");
        assertEquals("Should return registration numbers of vehicles driven by multiple part-time employees", 
                     expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Create a Company object named "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create two part-time employees
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Olivia");
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Paul");
        
        // Create two vehicles with no current drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        // No driver assigned (null)
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        // No driver assigned (null)
        
        // Add employees to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        
        // Add vehicles to company
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Expected output: Registration numbers = []
        assertTrue("Should return empty list when no vehicles have drivers assigned", 
                   result.isEmpty());
    }
}