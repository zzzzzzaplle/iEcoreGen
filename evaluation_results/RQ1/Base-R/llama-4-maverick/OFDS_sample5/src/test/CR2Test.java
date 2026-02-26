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
        // SetUp: Create company and employees
        company.setName("Food Express");
        
        // Create part-time employees
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Alice");
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Bob");
        
        // Create full-time employees
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Charlie");
        
        FullTimeEmployee emp4 = new FullTimeEmployee();
        emp4.setName("Diana");
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setDriver(emp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setDriver(emp2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setDriver(emp3);
        
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
        
        // Execute: Retrieve registration numbers
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Check expected output
        List<String> expected = new ArrayList<>();
        expected.add("ABC123");
        expected.add("XYZ789");
        
        assertEquals("Registration numbers should match expected output", expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // SetUp: Create company and employees
        company.setName("Quick Delivery");
        
        // Create full-time employees
        FullTimeEmployee emp1 = new FullTimeEmployee();
        emp1.setName("Ethan");
        
        FullTimeEmployee emp2 = new FullTimeEmployee();
        emp2.setName("Fiona");
        
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("George");
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setDriver(emp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setDriver(emp2);
        
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
        
        // Execute: Retrieve registration numbers
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Check expected output (empty list)
        List<String> expected = new ArrayList<>();
        
        assertEquals("Registration numbers should be empty when no part-time employees drive vehicles", expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // SetUp: Create company and employees
        company.setName("Gourmet Delivery");
        
        // Create part-time employee
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Henry");
        
        // Create full-time employees
        FullTimeEmployee emp2 = new FullTimeEmployee();
        emp2.setName("Isla");
        
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Jack");
        
        FullTimeEmployee emp4 = new FullTimeEmployee();
        emp4.setName("Kara");
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setDriver(emp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setDriver(emp2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setDriver(emp3);
        
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
        
        // Execute: Retrieve registration numbers
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Check expected output
        List<String> expected = new ArrayList<>();
        expected.add("DEF333");
        
        assertEquals("Registration numbers should match expected output for mixed employees", expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // SetUp: Create company and employees
        company.setName("City Foods");
        
        // Create part-time employees
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Lily");
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Mike");
        
        // Create full-time employee
        FullTimeEmployee emp3 = new FullTimeEmployee();
        emp3.setName("Nina");
        
        // Create vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setDriver(emp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setDriver(emp2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setDriver(emp3);
        
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
        
        // Execute: Retrieve registration numbers
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Check expected output
        List<String> expected = new ArrayList<>();
        expected.add("PQR777");
        expected.add("STU888");
        
        assertEquals("Registration numbers should include all vehicles driven by part-time employees", expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // SetUp: Create company and employees
        company.setName("Rapid Deliveries");
        
        // Create part-time employees (but they won't be assigned to vehicles)
        PartTimeEmployee emp1 = new PartTimeEmployee();
        emp1.setName("Olivia");
        
        PartTimeEmployee emp2 = new PartTimeEmployee();
        emp2.setName("Paul");
        
        // Create vehicles with no drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setDriver(null);
        
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
        
        // Execute: Retrieve registration numbers
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify: Check expected output (empty list)
        List<String> expected = new ArrayList<>();
        
        assertEquals("Registration numbers should be empty when vehicles have no drivers", expected, result);
    }
}