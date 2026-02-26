import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

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
        // Create a Company named "Food Express"
        company.setName("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        employee1.setName("John Doe");
        employee1.setType(EmployeeType.FULL_TIME);
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(employee1);
        employee1.setDrivenVehicle(vehicle1);
        
        // Add employee and vehicle to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        company.setVehicles(vehicles);
        
        // Expected Output: ["John Doe"]
        List<String> result = company.getCurrentDriversNames();
        assertEquals(1, result.size());
        assertTrue(result.contains("John Doe"));
    }
    
    @Test
    public void testCase2_multipleEmployeesMultipleVehicles() {
        // Create a Company named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        employee1.setName("Alice Smith");
        employee1.setType(EmployeeType.FULL_TIME);
        
        employee2.setName("Bob Johnson");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles with drivers
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setCurrentDriver(employee1);
        employee1.setDrivenVehicle(vehicle1);
        
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(employee2);
        employee2.setDrivenVehicle(vehicle2);
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Expected Output: ["Alice Smith", "Bob Johnson"]
        List<String> result = company.getCurrentDriversNames();
        assertEquals(2, result.size());
        assertTrue(result.contains("Alice Smith"));
        assertTrue(result.contains("Bob Johnson"));
    }
    
    @Test
    public void testCase3_noCurrentDrivers() {
        // Create a Company named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        employee1.setName("Charlie Brown");
        employee1.setType(EmployeeType.PART_TIME);
        
        employee2.setName("David Warner");
        employee2.setType(EmployeeType.FULL_TIME);
        
        // Create Vehicles not assigned to any driver
        vehicle1.setRegistrationNumber("DEF321");
        vehicle1.setCurrentDriver(null);
        
        vehicle2.setRegistrationNumber("JKL654");
        vehicle2.setCurrentDriver(null);
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Expected Output: []
        List<String> result = company.getCurrentDriversNames();
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_mixedVehiclesState() {
        // Create a Company named "Fast Meals Co."
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        employee1.setName("Eva Green");
        employee1.setType(EmployeeType.FULL_TIME);
        
        employee2.setName("Frank Wright");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles: some with drivers, some without
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(employee1);
        employee1.setDrivenVehicle(vehicle1);
        
        vehicle2.setRegistrationNumber("UVW567");
        vehicle2.setCurrentDriver(null);
        
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(employee2);
        employee2.setDrivenVehicle(vehicle3);
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Expected Output: ["Eva Green", "Frank Wright"]
        List<String> result = company.getCurrentDriversNames();
        assertEquals(2, result.size());
        assertTrue(result.contains("Eva Green"));
        assertTrue(result.contains("Frank Wright"));
    }
    
    @Test
    public void testCase5_employeesWithoutVehicles() {
        // Create a Company named "Delicious Delivery"
        company.setName("Delicious Delivery");
        
        // Create Employees: "Grace Title" (Full-Time), "Henry Field" (Part-Time), both without vehicles
        employee1.setName("Grace Title");
        employee1.setType(EmployeeType.FULL_TIME);
        employee1.setDrivenVehicle(null);
        
        employee2.setName("Henry Field");
        employee2.setType(EmployeeType.PART_TIME);
        employee2.setDrivenVehicle(null);
        
        // Add employees to company (no vehicles needed as employees aren't driving)
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        company.setEmployees(employees);
        
        // Add empty vehicle list
        List<Vehicle> vehicles = new ArrayList<>();
        company.setVehicles(vehicles);
        
        // Expected Output: []
        List<String> result = company.getCurrentDriversNames();
        assertEquals(0, result.size());
    }
}