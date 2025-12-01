import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleEmployeeWithVehicle() {
        // SetUp: Create a Company named "Food Express"
        company.setName("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setType(EmployeeType.FULL_TIME);
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = new OwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setCurrentDriver(employee);
        
        // Add employee and vehicle to company
        company.setEmployees(Arrays.asList(employee));
        company.setVehicles(Arrays.asList(vehicle));
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output: ["John Doe"]
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0));
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // SetUp: Create a Company named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee employee1 = new Employee();
        employee1.setName("Alice Smith");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob Johnson");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles: Registration "XYZ789" with driver "Alice Smith"
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setCurrentDriver(employee1);
        
        // Registration "LMN456" with driver "Bob Johnson"
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(employee2);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(employee1, employee2));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output: ["Alice Smith", "Bob Johnson"]
        assertEquals(2, result.size());
        assertTrue(result.contains("Alice Smith"));
        assertTrue(result.contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // SetUp: Create a Company named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee employee1 = new Employee();
        employee1.setName("Charlie Brown");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("David Warner");
        employee2.setType(EmployeeType.FULL_TIME);
        
        // Create Vehicles: Registration "DEF321" not assigned to any driver
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        // Registration "JKL654" not assigned to any driver
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(employee1, employee2));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // SetUp: Create a Company named "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee employee1 = new Employee();
        employee1.setName("Eva Green");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Frank Wright");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles: Registration "RST234" with driver "Eva Green"
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(employee1);
        
        // Registration "UVW567" not assigned to any driver
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        
        // Registration "OPQ890" with driver "Frank Wright"
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(employee2);
        
        // Add employees and vehicles to company
        company.setEmployees(Arrays.asList(employee1, employee2));
        company.setVehicles(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output: ["Eva Green", "Frank Wright"]
        assertEquals(2, result.size());
        assertTrue(result.contains("Eva Green"));
        assertTrue(result.contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles
        Employee employee1 = new Employee();
        employee1.setName("Grace Title");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Henry Field");
        employee2.setType(EmployeeType.PART_TIME);
        
        // No vehicles created for this test case
        company.setEmployees(Arrays.asList(employee1, employee2));
        company.setVehicles(Arrays.asList()); // Empty vehicles list
        
        // Execute method under test
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output: []
        assertTrue(result.isEmpty());
    }
}