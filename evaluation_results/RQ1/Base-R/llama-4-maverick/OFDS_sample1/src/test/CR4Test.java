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
        // SetUp: Create a Company named "Food Express"
        company.setName("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        FullTimeEmployee employee = new FullTimeEmployee();
        employee.setName("John Doe");
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        OwnedVehicle vehicle = new OwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        employee.setVehicle(vehicle);
        
        // Add employee to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee);
        company.setEmployees(employees);
        
        // Add vehicle to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle);
        company.setVehicles(vehicles);
        
        // Execute method and verify expected output
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        List<String> expected = new ArrayList<>();
        expected.add("John Doe");
        
        assertEquals("Should return single employee driving a vehicle", expected, result);
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create a Company named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        FullTimeEmployee employee1 = new FullTimeEmployee();
        employee1.setName("Alice Smith");
        
        PartTimeEmployee employee2 = new PartTimeEmployee();
        employee2.setName("Bob Johnson");
        
        // Create Vehicles with drivers
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        employee1.setVehicle(vehicle1);
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        employee2.setVehicle(vehicle2);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute method and verify expected output
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        List<String> expected = new ArrayList<>();
        expected.add("Alice Smith");
        expected.add("Bob Johnson");
        
        assertEquals("Should return all employees driving vehicles", expected, result);
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create a Company named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        PartTimeEmployee employee1 = new PartTimeEmployee();
        employee1.setName("Charlie Brown");
        
        FullTimeEmployee employee2 = new FullTimeEmployee();
        employee2.setName("David Warner");
        
        // Create Vehicles not assigned to any driver
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees to company (without vehicles assigned)
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute method and verify expected output
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list when no employees are driving vehicles", expected, result);
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create a Company named "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        FullTimeEmployee employee1 = new FullTimeEmployee();
        employee1.setName("Eva Green");
        
        PartTimeEmployee employee2 = new PartTimeEmployee();
        employee2.setName("Frank Wright");
        
        // Create Vehicles with mixed assignment state
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        employee1.setVehicle(vehicle1);
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // vehicle2 not assigned to any driver
        
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        employee2.setVehicle(vehicle3);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute method and verify expected output
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        List<String> expected = new ArrayList<>();
        expected.add("Eva Green");
        expected.add("Frank Wright");
        
        assertEquals("Should return only employees who are driving vehicles", expected, result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles: "Grace Title" (Full-Time), "Henry Field" (Part-Time)
        FullTimeEmployee employee1 = new FullTimeEmployee();
        employee1.setName("Grace Title");
        
        PartTimeEmployee employee2 = new PartTimeEmployee();
        employee2.setName("Henry Field");
        
        // Add employees to company (no vehicles assigned)
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        company.setEmployees(employees);
        
        // Execute method and verify expected output
        List<String> result = company.getNamesOfEmployeesDrivingAVehicle();
        List<String> expected = new ArrayList<>();
        
        assertEquals("Should return empty list when employees have no vehicles", expected, result);
    }
}