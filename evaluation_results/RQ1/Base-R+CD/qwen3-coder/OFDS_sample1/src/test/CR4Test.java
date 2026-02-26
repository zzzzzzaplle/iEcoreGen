import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    
    private Company company;
    private Employee employee1;
    private Employee employee2;
    private Vehicle vehicle1;
    private Vehicle vehicle2;
    private Vehicle vehicle3;
    
    @Before
    public void setUp() {
        company = new Company();
        employee1 = new Employee();
        employee2 = new Employee();
        vehicle1 = new OwnedVehicle();
        vehicle2 = new OwnedVehicle();
        vehicle3 = new OwnedVehicle();
    }
    
    @Test
    public void testCase1_singleEmployeeWithVehicle() {
        // SetUp: Create a Company named "Food Express"
        company.setName("Food Express");
        
        // SetUp: Create an Employee named "John Doe" of type Full-Time
        employee1.setName("John Doe");
        employee1.setType(EmployeeType.FULL_TIME);
        
        // SetUp: Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(employee1);
        employee1.setDrivenVehicle(vehicle1);
        
        // Add employee and vehicle to company
        company.getEmployees().add(employee1);
        company.getVehicles().add(vehicle1);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Expected Output: ["John Doe"]
        List<String> expected = Arrays.asList("John Doe");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_multipleEmployeesMultipleVehicles() {
        // SetUp: Create a Company named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // SetUp: Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        employee1.setName("Alice Smith");
        employee1.setType(EmployeeType.FULL_TIME);
        
        employee2.setName("Bob Johnson");
        employee2.setType(EmployeeType.PART_TIME);
        
        // SetUp: Create Vehicles with drivers
        // Registration "XYZ789" with driver "Alice Smith"
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setCurrentDriver(employee1);
        employee1.setDrivenVehicle(vehicle1);
        
        // Registration "LMN456" with driver "Bob Johnson"
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(employee2);
        employee2.setDrivenVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_noCurrentDrivers() {
        // SetUp: Create a Company named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // SetUp: Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        employee1.setName("Charlie Brown");
        employee1.setType(EmployeeType.PART_TIME);
        
        employee2.setName("David Warner");
        employee2.setType(EmployeeType.FULL_TIME);
        
        // SetUp: Create Vehicles not assigned to any driver
        // Registration "DEF321" not assigned to any driver
        vehicle1.setRegistrationNumber("DEF321");
        vehicle1.setCurrentDriver(null);
        
        // Registration "JKL654" not assigned to any driver
        vehicle2.setRegistrationNumber("JKL654");
        vehicle2.setCurrentDriver(null);
        
        // Add employees and vehicles to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_mixedVehiclesState() {
        // SetUp: Create a Company named "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // SetUp: Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        employee1.setName("Eva Green");
        employee1.setType(EmployeeType.FULL_TIME);
        
        employee2.setName("Frank Wright");
        employee2.setType(EmployeeType.PART_TIME);
        
        // SetUp: Create Vehicles
        // Registration "RST234" with driver "Eva Green"
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(employee1);
        employee1.setDrivenVehicle(vehicle1);
        
        // Registration "UVW567" not assigned to any driver
        vehicle2.setRegistrationNumber("UVW567");
        vehicle2.setCurrentDriver(null);
        
        // Registration "OPQ890" with driver "Frank Wright"
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(employee2);
        employee2.setDrivenVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_employeesWithoutVehicles() {
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // SetUp: Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles
        employee1.setName("Grace Title");
        employee1.setType(EmployeeType.FULL_TIME);
        employee1.setDrivenVehicle(null);
        
        employee2.setName("Henry Field");
        employee2.setType(EmployeeType.PART_TIME);
        employee2.setDrivenVehicle(null);
        
        // Add employees to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Expected Output: []
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
}