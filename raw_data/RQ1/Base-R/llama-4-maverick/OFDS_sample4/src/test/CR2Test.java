import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Test Case 1: Single Part-Time Employee Vehicle Retrieval
        // SetUp:
        company.setName("Food Express");
        
        // Create two part-time employees
        PartTimeEmployee partTimeEmp1 = new PartTimeEmployee();
        partTimeEmp1.setName("Alice");
        PartTimeEmployee partTimeEmp2 = new PartTimeEmployee();
        partTimeEmp2.setName("Bob");
        
        // Create two full-time employees
        FullTimeEmployee fullTimeEmp1 = new FullTimeEmployee();
        fullTimeEmp1.setName("Charlie");
        FullTimeEmployee fullTimeEmp2 = new FullTimeEmployee();
        fullTimeEmp2.setName("Diana");
        
        // Create employee list and add to company
        List<Employee> employees = new ArrayList<>();
        employees.add(partTimeEmp1);
        employees.add(partTimeEmp2);
        employees.add(fullTimeEmp1);
        employees.add(fullTimeEmp2);
        company.setEmployees(employees);
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setDriver(partTimeEmp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setDriver(partTimeEmp2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setDriver(fullTimeEmp1);
        
        // Create vehicle list and add to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("ABC123");
        expected.add("XYZ789");
        assertEquals("Registration numbers should match expected list", expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Test Case 2: No Part-Time Employees
        // SetUp:
        company.setName("Quick Delivery");
        
        // Create three full-time employees
        FullTimeEmployee fullTimeEmp1 = new FullTimeEmployee();
        fullTimeEmp1.setName("Ethan");
        FullTimeEmployee fullTimeEmp2 = new FullTimeEmployee();
        fullTimeEmp2.setName("Fiona");
        FullTimeEmployee fullTimeEmp3 = new FullTimeEmployee();
        fullTimeEmp3.setName("George");
        
        // Create employee list and add to company
        List<Employee> employees = new ArrayList<>();
        employees.add(fullTimeEmp1);
        employees.add(fullTimeEmp2);
        employees.add(fullTimeEmp3);
        company.setEmployees(employees);
        
        // Create two vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setDriver(fullTimeEmp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setDriver(fullTimeEmp2);
        
        // Create vehicle list and add to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        assertEquals("Registration numbers should be empty when no part-time employees drive vehicles", expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: Mixed Employees and Vehicles
        // SetUp:
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        PartTimeEmployee partTimeEmp1 = new PartTimeEmployee();
        partTimeEmp1.setName("Henry");
        
        // Create three full-time employees
        FullTimeEmployee fullTimeEmp1 = new FullTimeEmployee();
        fullTimeEmp1.setName("Isla");
        FullTimeEmployee fullTimeEmp2 = new FullTimeEmployee();
        fullTimeEmp2.setName("Jack");
        FullTimeEmployee fullTimeEmp3 = new FullTimeEmployee();
        fullTimeEmp3.setName("Kara");
        
        // Create employee list and add to company
        List<Employee> employees = new ArrayList<>();
        employees.add(partTimeEmp1);
        employees.add(fullTimeEmp1);
        employees.add(fullTimeEmp2);
        employees.add(fullTimeEmp3);
        company.setEmployees(employees);
        
        // Create four vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setDriver(partTimeEmp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setDriver(fullTimeEmp1);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setDriver(fullTimeEmp2);
        
        // Create vehicle list and add to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("DEF333");
        assertEquals("Registration numbers should match expected list with mixed employees", expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: Multiple Drivers for One Vehicle
        // SetUp:
        company.setName("City Foods");
        
        // Create two part-time employees
        PartTimeEmployee partTimeEmp1 = new PartTimeEmployee();
        partTimeEmp1.setName("Lily");
        PartTimeEmployee partTimeEmp2 = new PartTimeEmployee();
        partTimeEmp2.setName("Mike");
        
        // Create one full-time employee
        FullTimeEmployee fullTimeEmp1 = new FullTimeEmployee();
        fullTimeEmp1.setName("Nina");
        
        // Create employee list and add to company
        List<Employee> employees = new ArrayList<>();
        employees.add(partTimeEmp1);
        employees.add(partTimeEmp2);
        employees.add(fullTimeEmp1);
        company.setEmployees(employees);
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setDriver(partTimeEmp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setDriver(partTimeEmp2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setDriver(fullTimeEmp1);
        
        // Create vehicle list and add to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        expected.add("PQR777");
        expected.add("STU888");
        assertEquals("Registration numbers should match expected list with multiple part-time drivers", expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: All Vehicles without Drivers
        // SetUp:
        company.setName("Rapid Deliveries");
        
        // Create two part-time employees
        PartTimeEmployee partTimeEmp1 = new PartTimeEmployee();
        partTimeEmp1.setName("Olivia");
        PartTimeEmployee partTimeEmp2 = new PartTimeEmployee();
        partTimeEmp2.setName("Paul");
        
        // Create employee list and add to company
        List<Employee> employees = new ArrayList<>();
        employees.add(partTimeEmp1);
        employees.add(partTimeEmp2);
        company.setEmployees(employees);
        
        // Create two vehicles with no current drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setDriver(null);
        
        // Create vehicle list and add to company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute method under test
        List<String> result = company.getRegistrationNumbersOfVehiclesDrivenByPartTimeEmployees();
        
        // Verify expected output
        List<String> expected = new ArrayList<>();
        assertEquals("Registration numbers should be empty when vehicles have no drivers", expected, result);
    }
}