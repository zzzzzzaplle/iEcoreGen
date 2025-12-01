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
        // Create a Company object named "Food Express"
        company.setName("Food Express");
        
        // Create two part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType("part-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType("part-time");
        
        // Create two full-time employees
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setType("full-time");
        
        Employee emp4 = new Employee();
        emp4.setName("Diana");
        emp4.setType("full-time");
        
        // Create three vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setDriver(emp1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setDriver(emp2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setDriver(emp3);
        
        // Assign vehicles to employees
        emp1.setAssignedVehicle(vehicle1);
        emp2.setAssignedVehicle(vehicle2);
        emp3.setAssignedVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getVehiclesDrivenByPartTimeEmployees();
        
        // Expected Output: Registration numbers = ["ABC123", "XYZ789"]
        List<String> expected = Arrays.asList("ABC123", "XYZ789");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Create a Company object named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create three full-time employees
        Employee emp1 = new Employee();
        emp1.setName("Ethan");
        emp1.setType("full-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Fiona");
        emp2.setType("full-time");
        
        Employee emp3 = new Employee();
        emp3.setName("George");
        emp3.setType("full-time");
        
        // Create two vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setDriver(emp1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setDriver(emp2);
        
        // Assign vehicles to employees
        emp1.setAssignedVehicle(vehicle1);
        emp2.setAssignedVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getVehiclesDrivenByPartTimeEmployees();
        
        // Expected Output: Registration numbers = []
        List<String> expected = Arrays.asList();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Create a Company object named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType("part-time");
        
        // Create three full-time employees
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setType("full-time");
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setType("full-time");
        
        Employee emp4 = new Employee();
        emp4.setName("Kara");
        emp4.setType("full-time");
        
        // Create four vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setDriver(emp1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setDriver(emp2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setDriver(emp3);
        
        // Assign vehicles to employees
        emp1.setAssignedVehicle(vehicle1);
        emp2.setAssignedVehicle(vehicle2);
        emp3.setAssignedVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        company.addEmployee(emp4);
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getVehiclesDrivenByPartTimeEmployees();
        
        // Expected Output: Registration numbers = ["DEF333"]
        List<String> expected = Arrays.asList("DEF333");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Create a Company object named "City Foods"
        company.setName("City Foods");
        
        // Create two part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Lily");
        emp1.setType("part-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Mike");
        emp2.setType("part-time");
        
        // Create one full-time employee
        Employee emp3 = new Employee();
        emp3.setName("Nina");
        emp3.setType("full-time");
        
        // Create three vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setDriver(emp1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setDriver(emp2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setDriver(emp3);
        
        // Assign vehicles to employees
        emp1.setAssignedVehicle(vehicle1);
        emp2.setAssignedVehicle(vehicle2);
        emp3.setAssignedVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        company.addEmployee(emp3);
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getVehiclesDrivenByPartTimeEmployees();
        
        // Expected Output: Registration numbers = ["PQR777", "STU888"]
        List<String> expected = Arrays.asList("PQR777", "STU888");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Create a Company object named "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create two part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Olivia");
        emp1.setType("part-time");
        
        Employee emp2 = new Employee();
        emp2.setName("Paul");
        emp2.setType("part-time");
        
        // Create two vehicles with no current drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setDriver(null);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setDriver(null);
        
        // Add employees and vehicles to company
        company.addEmployee(emp1);
        company.addEmployee(emp2);
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getVehiclesDrivenByPartTimeEmployees();
        
        // Expected Output: Registration numbers = []
        List<String> expected = Arrays.asList();
        assertEquals(expected, result);
    }
}