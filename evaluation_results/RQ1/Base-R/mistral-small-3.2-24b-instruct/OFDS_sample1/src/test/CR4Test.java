import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        // SetUp: Create Company "Food Express", Employee "John Doe" (Full-Time), Vehicle "ABC123" assigned to John Doe
        company.setName("Food Express");
        
        Employee john = new Employee("John Doe", true);
        Vehicle vehicle1 = new Vehicle("ABC123", false);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(john);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        company.setVehicles(vehicles);
        
        List<Employee> drivers = new ArrayList<>();
        drivers.add(john);
        company.setDrivers(drivers);
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("John Doe");
        assertEquals("Should return single driver name", expected, result);
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create Company "Quick Delivery", Employees "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time),
        // Vehicles "XYZ789" assigned to Alice, "LMN456" assigned to Bob
        company.setName("Quick Delivery");
        
        Employee alice = new Employee("Alice Smith", true);
        Employee bob = new Employee("Bob Johnson", false);
        
        Vehicle vehicle1 = new Vehicle("XYZ789", false);
        Vehicle vehicle2 = new Vehicle("LMN456", true);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        List<Employee> drivers = new ArrayList<>();
        drivers.add(alice);
        drivers.add(bob);
        company.setDrivers(drivers);
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("Alice Smith");
        expected.add("Bob Johnson");
        assertEquals("Should return all driver names", expected, result);
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create Company "Gourmet Delivery", Employees "Charlie Brown" (Part-Time), "David Warner" (Full-Time),
        // Vehicles "DEF321" and "JKL654" not assigned to any driver
        company.setName("Gourmet Delivery");
        
        Employee charlie = new Employee("Charlie Brown", false);
        Employee david = new Employee("David Warner", true);
        
        Vehicle vehicle1 = new Vehicle("DEF321", false);
        Vehicle vehicle2 = new Vehicle("JKL654", true);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(charlie);
        employees.add(david);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // No drivers assigned - drivers list is empty
        List<Employee> drivers = new ArrayList<>();
        company.setDrivers(drivers);
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        assertEquals("Should return empty list when no drivers", expected, result);
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create Company "Fast Meals Co.", Employees "Eva Green" (Full-Time), "Frank Wright" (Part-Time),
        // Vehicles "RST234" assigned to Eva, "UVW567" not assigned, "OPQ890" assigned to Frank
        company.setName("Fast Meals Co.");
        
        Employee eva = new Employee("Eva Green", true);
        Employee frank = new Employee("Frank Wright", false);
        
        Vehicle vehicle1 = new Vehicle("RST234", false);
        Vehicle vehicle2 = new Vehicle("UVW567", true);
        Vehicle vehicle3 = new Vehicle("OPQ890", false);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(eva);
        employees.add(frank);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        List<Employee> drivers = new ArrayList<>();
        drivers.add(eva);
        drivers.add(frank);
        company.setDrivers(drivers);
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("Eva Green");
        expected.add("Frank Wright");
        assertEquals("Should return only assigned driver names", expected, result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create Company "Delicious Delivery", Employees "Grace Title" (Full-Time), "Henry Field" (Part-Time),
        // both without vehicles (no vehicles in company)
        company.setName("Delicious Delivery");
        
        Employee grace = new Employee("Grace Title", true);
        Employee henry = new Employee("Henry Field", false);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(grace);
        employees.add(henry);
        company.setEmployees(employees);
        
        // No vehicles in company
        List<Vehicle> vehicles = new ArrayList<>();
        company.setVehicles(vehicles);
        
        // No drivers assigned
        List<Employee> drivers = new ArrayList<>();
        company.setDrivers(drivers);
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        assertEquals("Should return empty list when no drivers assigned", expected, result);
    }
}