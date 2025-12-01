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
    public void testCase1_singleEmployeeWithVehicle() {
        // SetUp: Create a Company named "Food Express"
        company.setName("Food Express");
        
        // SetUp: Create an Employee named "John Doe" of type Full-Time
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setType("full-time");
        
        // SetUp: Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setDriver(employee);
        employee.setVehicle(vehicle);
        
        // Add employee and vehicle to company
        company.addEmployee(employee);
        company.addVehicle(vehicle);
        
        // Execute: Retrieve the current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingVehicles();
        
        // Verify: Expected Output: ["John Doe"]
        List<String> expected = Arrays.asList("John Doe");
        assertEquals("Should return John Doe as the only driver", expected, result);
    }
    
    @Test
    public void testCase2_multipleEmployeesMultipleVehicles() {
        // SetUp: Create a Company named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // SetUp: Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee employee1 = new Employee();
        employee1.setName("Alice Smith");
        employee1.setType("full-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Bob Johnson");
        employee2.setType("part-time");
        
        // SetUp: Create Vehicles with drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setDriver(employee1);
        employee1.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setDriver(employee2);
        employee2.setVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute: Retrieve the current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingVehicles();
        
        // Verify: Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        assertEquals("Should return both Alice Smith and Bob Johnson as drivers", expected, result);
    }
    
    @Test
    public void testCase3_noCurrentDrivers() {
        // SetUp: Create a Company named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // SetUp: Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee employee1 = new Employee();
        employee1.setName("Charlie Brown");
        employee1.setType("part-time");
        
        Employee employee2 = new Employee();
        employee2.setName("David Warner");
        employee2.setType("full-time");
        
        // SetUp: Create Vehicles not assigned to any driver
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Execute: Retrieve the current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingVehicles();
        
        // Verify: Expected Output: []
        List<String> expected = Arrays.asList();
        assertTrue("Should return empty list when no drivers are assigned", result.isEmpty());
        assertEquals("Should return empty list when no drivers are assigned", expected, result);
    }
    
    @Test
    public void testCase4_mixedVehiclesState() {
        // SetUp: Create a Company named "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // SetUp: Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee employee1 = new Employee();
        employee1.setName("Eva Green");
        employee1.setType("full-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Frank Wright");
        employee2.setType("part-time");
        
        // SetUp: Create Vehicles with mixed assignment state
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setDriver(employee1);
        employee1.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // Not assigned to any driver
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setDriver(employee2);
        employee2.setVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute: Retrieve the current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingVehicles();
        
        // Verify: Expected Output: ["Eva Green", "Frank Wright"]
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        assertEquals("Should return only Eva Green and Frank Wright as drivers", expected, result);
    }
    
    @Test
    public void testCase5_employeesWithoutVehicles() {
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // SetUp: Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles
        Employee employee1 = new Employee();
        employee1.setName("Grace Title");
        employee1.setType("full-time");
        
        Employee employee2 = new Employee();
        employee2.setName("Henry Field");
        employee2.setType("part-time");
        
        // Add employees to company (no vehicles added)
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        
        // Execute: Retrieve the current drivers' names
        List<String> result = company.getNamesOfEmployeesDrivingVehicles();
        
        // Verify: Expected Output: []
        List<String> expected = Arrays.asList();
        assertTrue("Should return empty list when employees have no vehicles", result.isEmpty());
        assertEquals("Should return empty list when employees have no vehicles", expected, result);
    }
}