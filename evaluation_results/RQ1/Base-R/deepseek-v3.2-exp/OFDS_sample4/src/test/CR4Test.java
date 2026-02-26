import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

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
        company.addEmployee(employee);
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setDriver(employee);
        employee.setAssignedVehicle(vehicle);
        company.addVehicle(vehicle);
        
        // Retrieve the current drivers' names
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Expected Output: ["John Doe"]
        assertEquals(Arrays.asList("John Doe"), result);
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // SetUp: Create a Company named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee employee1 = new Employee();
        employee1.setName("Alice Smith");
        employee1.setType(EmployeeType.FULL_TIME);
        company.addEmployee(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob Johnson");
        employee2.setType(EmployeeType.PART_TIME);
        company.addEmployee(employee2);
        
        // Create Vehicles: 
        // Registration "XYZ789" with driver "Alice Smith"
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setDriver(employee1);
        employee1.setAssignedVehicle(vehicle1);
        company.addVehicle(vehicle1);
        
        // Registration "LMN456" with driver "Bob Johnson"
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setDriver(employee2);
        employee2.setAssignedVehicle(vehicle2);
        company.addVehicle(vehicle2);
        
        // Retrieve the current drivers' names
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // SetUp: Create a Company named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee employee1 = new Employee();
        employee1.setName("Charlie Brown");
        employee1.setType(EmployeeType.PART_TIME);
        company.addEmployee(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("David Warner");
        employee2.setType(EmployeeType.FULL_TIME);
        company.addEmployee(employee2);
        
        // Create Vehicles: 
        // Registration "DEF321" not assigned to any driver
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF321");
        company.addVehicle(vehicle1);
        
        // Registration "JKL654" not assigned to any driver
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL654");
        company.addVehicle(vehicle2);
        
        // Retrieve the current drivers' names
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Expected Output: []
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
        company.addEmployee(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("Frank Wright");
        employee2.setType(EmployeeType.PART_TIME);
        company.addEmployee(employee2);
        
        // Create Vehicles: 
        // Registration "RST234" with driver "Eva Green"
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setDriver(employee1);
        employee1.setAssignedVehicle(vehicle1);
        company.addVehicle(vehicle1);
        
        // Registration "UVW567" not assigned to any driver
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("UVW567");
        company.addVehicle(vehicle2);
        
        // Registration "OPQ890" with driver "Frank Wright"
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setDriver(employee2);
        employee2.setAssignedVehicle(vehicle3);
        company.addVehicle(vehicle3);
        
        // Retrieve the current drivers' names
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles
        Employee employee1 = new Employee();
        employee1.setName("Grace Title");
        employee1.setType(EmployeeType.FULL_TIME);
        company.addEmployee(employee1);
        
        Employee employee2 = new Employee();
        employee2.setName("Henry Field");
        employee2.setType(EmployeeType.PART_TIME);
        company.addEmployee(employee2);
        
        // Retrieve the current drivers' names
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
}