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
        // SetUp: Create company "Food Express"
        company.setName("Food Express");
        
        // Create two part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType(EmployeeType.PART_TIME);
        
        // Create two full-time employees
        Employee emp3 = new Employee();
        emp3.setName("Charlie");
        emp3.setType(EmployeeType.FULL_TIME);
        
        Employee emp4 = new Employee();
        emp4.setName("Diana");
        emp4.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(emp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setCurrentDriver(emp2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setCurrentDriver(emp3);
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute: Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected output = ["ABC123", "XYZ789"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("XYZ789"));
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // SetUp: Create company "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create three full-time employees
        Employee emp1 = new Employee();
        emp1.setName("Ethan");
        emp1.setType(EmployeeType.FULL_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Fiona");
        emp2.setType(EmployeeType.FULL_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("George");
        emp3.setType(EmployeeType.FULL_TIME);
        
        // Create two vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setCurrentDriver(emp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setCurrentDriver(emp2);
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute: Retrieve registration numbers for vehicles with no part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected output = []
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // SetUp: Create company "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType(EmployeeType.PART_TIME);
        
        // Create three full-time employees
        Employee emp2 = new Employee();
        emp2.setName("Isla");
        emp2.setType(EmployeeType.FULL_TIME);
        
        Employee emp3 = new Employee();
        emp3.setName("Jack");
        emp3.setType(EmployeeType.FULL_TIME);
        
        Employee emp4 = new Employee();
        emp4.setName("Kara");
        emp4.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles (note: setup says 4 vehicles but only 3 are specified)
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setCurrentDriver(emp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setCurrentDriver(emp2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setCurrentDriver(emp3);
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute: Retrieve registration numbers for vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected output = ["DEF333"]
        assertEquals(1, result.size());
        assertEquals("DEF333", result.get(0));
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // SetUp: Create company "City Foods"
        company.setName("City Foods");
        
        // Create two part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Lily");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Mike");
        emp2.setType(EmployeeType.PART_TIME);
        
        // Create one full-time employee
        Employee emp3 = new Employee();
        emp3.setName("Nina");
        emp3.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setCurrentDriver(emp1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setCurrentDriver(emp2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setCurrentDriver(emp3);
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Execute: Retrieve registration numbers for vehicles with multiple part-time drivers
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected output = ["PQR777", "STU888"]
        assertEquals(2, result.size());
        assertTrue(result.contains("PQR777"));
        assertTrue(result.contains("STU888"));
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // SetUp: Create company "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create two part-time employees
        Employee emp1 = new Employee();
        emp1.setName("Olivia");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Paul");
        emp2.setType(EmployeeType.PART_TIME);
        
        // Create two vehicles with no current drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setCurrentDriver(null);
        
        // Add employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Execute: Retrieve registration numbers for vehicles that currently have no drivers assigned
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify: Expected output = []
        assertEquals(0, result.size());
        assertTrue(result.isEmpty());
    }
}