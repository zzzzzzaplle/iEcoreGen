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
        
        // Create a Vehicle with registration number "ABC123" 
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        
        // Assign vehicle to employee as the driver
        company.addEmployee(employee);
        company.addVehicle(vehicle);
        company.assignDriver(employee, vehicle);
        
        // Expected Output: ["John Doe"]
        List<String> result = company.getNamesOfEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList("John Doe");
        
        assertEquals("Single employee with vehicle should return correct name", 
                     expected, result);
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
        
        // Create Vehicles with registrations and assign drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("LMN456");
        
        // Add employees and vehicles to company and assign drivers
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        company.assignDriver(employee1, vehicle1);
        company.assignDriver(employee2, vehicle2);
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> result = company.getNamesOfEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        
        assertEquals("Multiple employees with vehicles should return all driver names", 
                     expected, result);
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
        
        // Create Vehicles not assigned to any driver
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees and vehicles to company (no driver assignments)
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        
        // Expected Output: []
        List<String> result = company.getNamesOfEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList();
        
        assertEquals("No current drivers should return empty list", 
                     expected, result);
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
        
        // Create Vehicles: one assigned, one not assigned, one assigned
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("RST234");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("UVW567");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        
        // Add employees and vehicles to company
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Assign only vehicle1 and vehicle3 to drivers
        company.assignDriver(employee1, vehicle1);
        company.assignDriver(employee2, vehicle3);
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> result = company.getNamesOfEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        
        assertEquals("Mixed vehicles state should return only drivers with assigned vehicles", 
                     expected, result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles
        Employee employee1 = new Employee();
        employee1.setName("Grace Title");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Henry Field");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Add employees to company (no vehicles added)
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        
        // Expected Output: []
        List<String> result = company.getNamesOfEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList();
        
        assertEquals("Employees without vehicles should return empty list", 
                     expected, result);
    }
}