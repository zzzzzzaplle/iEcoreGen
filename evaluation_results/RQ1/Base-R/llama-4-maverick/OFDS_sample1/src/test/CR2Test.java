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
        // Create a Company object named "Food Express"
        company.setName("Food Express");
        
        // Create part-time employees
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Alice");
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Bob");
        
        // Create full-time employees
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Charlie");
        
        FullTimeEmployee emp4 = new FullTimeEmployee();
        emp4.setName("Diana");
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        
        // Assign vehicles to employees
        emp1.setVehicle(vehicle1);
        emp2.setVehicle(vehicle2);
        emp3.setVehicle(vehicle3);
        emp4.setVehicle(null); // No vehicle assigned
        
        // Add employees to company
        company.setEmployees(Arrays.asList(emp1, emp2, emp3, emp4));
        
        // Execute the method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Create a Company object named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create full-time employees
        FullTimeEmployee emp1 = new FullTimeEmployee();
        emp1.setName("Ethan");
        
        FullTimeEmployee emp2 = new FullTimeEmployee();
        emp2.setName("Fiona");
        
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("George");
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        
        // Assign vehicles to employees
        emp1.setVehicle(vehicle1);
        emp2.setVehicle(vehicle2);
        emp3.setVehicle(null); // No vehicle assigned
        
        // Add employees to company
        company.setEmployees(Arrays.asList(emp1, emp2, emp3));
        
        // Execute the method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Create a Company object named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create part-time employee
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Henry");
        
        // Create full-time employees
        FullTimeEmployee emp2 = new FullTimeEmployee();
        emp2.setName("Isla");
        
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Jack");
        
        FullTimeEmployee emp4 = new FullTimeEmployee();
        emp4.setName("Kara");
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        
        // Assign vehicles to employees
        emp1.setVehicle(vehicle1);
        emp2.setVehicle(vehicle2);
        emp3.setVehicle(vehicle3);
        emp4.setVehicle(null); // No vehicle assigned
        
        // Add employees to company
        company.setEmployees(Arrays.asList(emp1, emp2, emp3, emp4));
        
        // Execute the method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList("DEF333");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Create a Company object named "City Foods"
        company.setName("City Foods");
        
        // Create part-time employees
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Lily");
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Mike");
        
        // Create full-time employee
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Nina");
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        
        // Assign vehicles to employees
        emp1.setVehicle(vehicle1);
        emp2.setVehicle(vehicle2);
        emp3.setVehicle(vehicle3);
        
        // Add employees to company
        company.setEmployees(Arrays.asList(emp1, emp2, emp3));
        
        // Execute the method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList("PQR777", "STU888");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Create a Company object named "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create part-time employees
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Olivia");
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Paul");
        
        // Create vehicles with no drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        
        // Don't assign vehicles to employees (leave vehicle as null)
        emp1.setVehicle(null);
        emp2.setVehicle(null);
        
        // Add employees to company
        company.setEmployees(Arrays.asList(emp1, emp2));
        
        // Execute the method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = Arrays.asList();
        assertEquals(expected, result);
    }
}