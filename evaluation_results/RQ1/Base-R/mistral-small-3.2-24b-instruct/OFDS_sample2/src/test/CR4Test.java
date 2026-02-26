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
    public void testCase1_singleEmployeeWithVehicle() {
        // Test Case 1: Single Employee with a Vehicle
        // SetUp: Create Company "Food Express" with one employee assigned to one vehicle
        company.setName("Food Express");
        
        Employee john = new Employee();
        john.setName("John Doe");
        john.setType("Full-Time");
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setDriver(john);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(john);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("John Doe");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_multipleEmployeesMultipleVehicles() {
        // Test Case 2: Multiple Employees, Multiple Vehicles
        // SetUp: Create Company "Quick Delivery" with multiple employees assigned to vehicles
        company.setName("Quick Delivery");
        
        Employee alice = new Employee();
        alice.setName("Alice Smith");
        alice.setType("Full-Time");
        
        Employee bob = new Employee();
        bob.setName("Bob Johnson");
        bob.setType("Part-Time");
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setDriver(alice);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setDriver(bob);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("Alice Smith");
        expected.add("Bob Johnson");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_noCurrentDrivers() {
        // Test Case 3: No Current Drivers
        // SetUp: Create Company "Gourmet Delivery" with vehicles but no drivers assigned
        company.setName("Gourmet Delivery");
        
        Employee charlie = new Employee();
        charlie.setName("Charlie Brown");
        charlie.setType("Part-Time");
        
        Employee david = new Employee();
        david.setName("David Warner");
        david.setType("Full-Time");
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF321");
        vehicle1.setDriver(null); // No driver assigned
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL654");
        vehicle2.setDriver(null); // No driver assigned
        
        List<Employee> employees = new ArrayList<>();
        employees.add(charlie);
        employees.add(david);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_mixedVehiclesState() {
        // Test Case 4: Mixed Vehicles State
        // SetUp: Create Company "Fast Meals Co." with some vehicles assigned and some not
        company.setName("Fast Meals Co.");
        
        Employee eva = new Employee();
        eva.setName("Eva Green");
        eva.setType("Full-Time");
        
        Employee frank = new Employee();
        frank.setName("Frank Wright");
        frank.setType("Part-Time");
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setDriver(eva);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("UVW567");
        vehicle2.setDriver(null); // No driver assigned
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setDriver(frank);
        
        List<Employee> employees = new ArrayList<>();
        employees.add(eva);
        employees.add(frank);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("Eva Green");
        expected.add("Frank Wright");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_employeesWithoutVehicles() {
        // Test Case 5: Employees Without Vehicles
        // SetUp: Create Company "Delicious Delivery" with employees but no vehicles
        company.setName("Delicious Delivery");
        
        Employee grace = new Employee();
        grace.setName("Grace Title");
        grace.setType("Full-Time");
        
        Employee henry = new Employee();
        henry.setName("Henry Field");
        henry.setType("Part-Time");
        
        List<Employee> employees = new ArrayList<>();
        employees.add(grace);
        employees.add(henry);
        company.setEmployees(employees);
        
        // No vehicles added to company
        List<Vehicle> vehicles = new ArrayList<>();
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.findEmployeesDrivingVehicles();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        assertEquals(expected, result);
    }
}