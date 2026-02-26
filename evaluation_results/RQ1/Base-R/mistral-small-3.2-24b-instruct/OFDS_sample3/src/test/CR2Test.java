import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Set up test data as specified
        company.setName("Food Express");
        
        // Create part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType(EmployeeType.PART_TIME);
        
        // Create full-time employees
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setType(EmployeeType.FULL_TIME);
        
        Employee emp4 = new Employee();
        emp4.setName("Diana");
        emp4.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        
        // Assign vehicles to employees
        emp1.setVehicle(vehicle1);
        emp2.setVehicle(vehicle2);
        emp3.setVehicle(vehicle3);
        emp4.setVehicle(null);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute the method under test
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("ABC123");
        expected.add("XYZ789");
        
        assertEquals("Registration numbers should match expected output", expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Set up test data as specified
        company.setName("Quick Delivery");
        
        // Create full-time employees only
        Employee emp1 = new Employee();
        emp1.setName("Ethan");
        emp1.setType(EmployeeType.FULL_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Fiona");
        emp2.setType(EmployeeType.FULL_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("George");
        emp3.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("QWE111");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("RTY222");
        
        // Assign vehicles to employees
        emp1.setVehicle(vehicle1);
        emp2.setVehicle(vehicle2);
        emp3.setVehicle(null);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute the method under test
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        
        assertEquals("Registration numbers should be empty when no part-time employees", expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Set up test data as specified
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType(EmployeeType.PART_TIME);
        
        // Create full-time employees
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setType(EmployeeType.FULL_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setType(EmployeeType.FULL_TIME);
        
        Employee emp4 = new Employee();
        emp4.setName("Kara");
        emp4.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF333");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("GHI444");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("JKL555");
        
        // Assign vehicles to employees
        emp1.setVehicle(vehicle1);
        emp2.setVehicle(vehicle2);
        emp3.setVehicle(vehicle3);
        emp4.setVehicle(null);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute the method under test
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("DEF333");
        
        assertEquals("Registration numbers should match expected output for mixed employees", expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Set up test data as specified
        company.setName("City Foods");
        
        // Create part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Lily");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Mike");
        emp2.setType(EmployeeType.PART_TIME);
        
        // Create full-time employee
        Employee emp3 = new Employee();
        emp3.setName("Nina");
        emp3.setType(EmployeeType.FULL_TIME);
        
        // Create vehicles
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("PQR777");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("STU888");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("VWX999");
        
        // Assign vehicles to employees
        emp1.setVehicle(vehicle1);
        emp2.setVehicle(vehicle2);
        emp3.setVehicle(vehicle3);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute the method under test
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("PQR777");
        expected.add("STU888");
        
        assertEquals("Registration numbers should include all part-time employee vehicles", expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Set up test data as specified
        company.setName("Rapid Deliveries");
        
        // Create part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Olivia");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Paul");
        emp2.setType(EmployeeType.PART_TIME);
        
        // Create vehicles with no drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("AAA000");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("BBB111");
        
        // No vehicles assigned to employees
        emp1.setVehicle(null);
        emp2.setVehicle(null);
        
        // Add employees to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        company.setEmployees(employees);
        
        // Add vehicles to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute the method under test
        List<String> result = company.findVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        
        assertEquals("Registration numbers should be empty when no vehicles are assigned to part-time employees", expected, result);
    }
}