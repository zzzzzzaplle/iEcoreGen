import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR4Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SingleEmployeeWithVehicle() {
        // Test Case 1: Single Employee with a Vehicle
        // SetUp: Create Company "Food Express"
        company.setName("Food Express");
        
        // Create Employee "John Doe" of type Full-Time
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setType(EmployeeType.FULL_TIME);
        
        // Create Vehicle with registration "ABC123" assigned to "John Doe" as driver
        Vehicle vehicle = new OwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setCurrentDriver(employee);
        employee.setDrivenVehicle(vehicle);
        
        // Add employee and vehicle to company
        company.getEmployees().add(employee);
        company.getVehicles().add(vehicle);
        
        // Execute method and verify expected output
        List<String> result = company.getCurrentDriversNames();
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0));
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create Company "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee employee1 = new Employee();
        employee1.setName("Alice Smith");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob Johnson");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles with drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setCurrentDriver(employee1);
        employee1.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(employee2);
        employee2.setDrivenVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Execute method and verify expected output
        List<String> result = company.getCurrentDriversNames();
        assertEquals(2, result.size());
        assertTrue(result.contains("Alice Smith"));
        assertTrue(result.contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create Company "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee employee1 = new Employee();
        employee1.setName("Charlie Brown");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("David Warner");
        employee2.setType(EmployeeType.FULL_TIME);
        
        // Create Vehicles not assigned to any driver
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        vehicle2.setCurrentDriver(null);
        
        // Add employees and vehicles to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Execute method and verify expected output (empty list)
        List<String> result = company.getCurrentDriversNames();
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create Company "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee employee1 = new Employee();
        employee1.setName("Eva Green");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Frank Wright");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles with mixed assignment
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(employee1);
        employee1.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        vehicle2.setCurrentDriver(null);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(employee2);
        employee2.setDrivenVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Execute method and verify expected output
        List<String> result = company.getCurrentDriversNames();
        assertEquals(2, result.size());
        assertTrue(result.contains("Eva Green"));
        assertTrue(result.contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create Company "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles
        Employee employee1 = new Employee();
        employee1.setName("Grace Title");
        employee1.setType(EmployeeType.FULL_TIME);
        employee1.setDrivenVehicle(null);
        
        Employee employee2 = new Employee();
        employee2.setName("Henry Field");
        employee2.setType(EmployeeType.PART_TIME);
        employee2.setDrivenVehicle(null);
        
        // Add employees to company (no vehicles added)
        company.getEmployees().add(employee1);
        company.getEmployees().add(employee2);
        
        // Execute method and verify expected output (empty list)
        List<String> result = company.getCurrentDriversNames();
        assertTrue(result.isEmpty());
    }
}