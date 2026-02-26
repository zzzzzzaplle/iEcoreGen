import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Company company;
    private List<Employee> employees;
    private List<Vehicle> vehicles;
    
    @Before
    public void setUp() {
        company = new Company();
        employees = new ArrayList<>();
        vehicles = new ArrayList<>();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Create a Company object named "Food Express"
        company.setName("Food Express");
        
        // Create two part-time employees
        Employee employee1 = new Employee();
        employee1.setName("Alice");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create two full-time employees
        Employee employee3 = new Employee();
        employee3.setName("Charlie");
        employee3.setType(EmployeeType.FULL_TIME);
        
        Employee employee4 = new Employee();
        employee4.setName("Diana");
        employee4.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(employee1); // Employee 1 (part-time)
        employee1.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setCurrentDriver(employee2); // Employee 2 (part-time)
        employee2.setDrivenVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setCurrentDriver(employee3); // Employee 3 (full-time)
        employee3.setDrivenVehicle(vehicle3);
        
        // Assign vehicles to the Company object
        List<Employee> companyEmployees = new ArrayList<>();
        companyEmployees.add(employee1);
        companyEmployees.add(employee2);
        companyEmployees.add(employee3);
        companyEmployees.add(employee4);
        company.setEmployees(companyEmployees);
        
        List<Vehicle> companyVehicles = new ArrayList<>();
        companyVehicles.add(vehicle1);
        companyVehicles.add(vehicle2);
        companyVehicles.add(vehicle3);
        company.setVehicles(companyVehicles);
        
        // Execute the method and verify results
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        assertEquals(2, result.size());
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("XYZ789"));
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Create a Company object named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create three full-time employees
        Employee employee1 = new Employee();
        employee1.setName("Ethan");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Fiona");
        employee2.setType(EmployeeType.FULL_TIME);
        
        Employee employee3 = new Employee();
        employee3.setName("George");
        employee3.setType(EmployeeType.FULL_TIME);
        
        // Create two vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setCurrentDriver(employee1);
        employee1.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setCurrentDriver(employee2);
        employee2.setDrivenVehicle(vehicle2);
        
        // Assign vehicles to the Company object
        List<Employee> companyEmployees = new ArrayList<>();
        companyEmployees.add(employee1);
        companyEmployees.add(employee2);
        companyEmployees.add(employee3);
        company.setEmployees(companyEmployees);
        
        List<Vehicle> companyVehicles = new ArrayList<>();
        companyVehicles.add(vehicle1);
        companyVehicles.add(vehicle2);
        company.setVehicles(companyVehicles);
        
        // Execute the method and verify results
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Create a Company object named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        Employee employee1 = new Employee();
        employee1.setName("Henry");
        employee1.setType(EmployeeType.PART_TIME);
        
        // Create three full-time employees
        Employee employee2 = new Employee();
        employee2.setName("Isla");
        employee2.setType(EmployeeType.FULL_TIME);
        
        Employee employee3 = new Employee();
        employee3.setName("Jack");
        employee3.setType(EmployeeType.FULL_TIME);
        
        Employee employee4 = new Employee();
        employee4.setName("Kara");
        employee4.setType(EmployeeType.FULL_TIME);
        
        // Create four vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setCurrentDriver(employee1); // Employee 1 (part-time)
        employee1.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setCurrentDriver(employee2); // Employee 2 (full-time)
        employee2.setDrivenVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setCurrentDriver(employee3); // Employee 3 (full-time)
        employee3.setDrivenVehicle(vehicle3);
        
        // Note: The test specification mentions 4 vehicles but only describes 3.
        // Assign vehicles to the Company object
        List<Employee> companyEmployees = new ArrayList<>();
        companyEmployees.add(employee1);
        companyEmployees.add(employee2);
        companyEmployees.add(employee3);
        companyEmployees.add(employee4);
        company.setEmployees(companyEmployees);
        
        List<Vehicle> companyVehicles = new ArrayList<>();
        companyVehicles.add(vehicle1);
        companyVehicles.add(vehicle2);
        companyVehicles.add(vehicle3);
        company.setVehicles(companyVehicles);
        
        // Execute the method and verify results
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        assertEquals(1, result.size());
        assertTrue(result.contains("DEF333"));
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Create a Company object named "City Foods"
        company.setName("City Foods");
        
        // Create two part-time employees
        Employee employee1 = new Employee();
        employee1.setName("Lily");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Mike");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create one full-time employee
        Employee employee3 = new Employee();
        employee3.setName("Nina");
        employee3.setType(EmployeeType.FULL_TIME);
        
        // Create two vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setCurrentDriver(employee1); // Employee 1 (part-time)
        employee1.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setCurrentDriver(employee2); // Employee 2 (part-time)
        employee2.setDrivenVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setCurrentDriver(employee3); // Employee 3 (full-time)
        employee3.setDrivenVehicle(vehicle3);
        
        // Assign vehicles to the Company object
        List<Employee> companyEmployees = new ArrayList<>();
        companyEmployees.add(employee1);
        companyEmployees.add(employee2);
        companyEmployees.add(employee3);
        company.setEmployees(companyEmployees);
        
        List<Vehicle> companyVehicles = new ArrayList<>();
        companyVehicles.add(vehicle1);
        companyVehicles.add(vehicle2);
        companyVehicles.add(vehicle3);
        company.setVehicles(companyVehicles);
        
        // Execute the method and verify results
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        assertEquals(2, result.size());
        assertTrue(result.contains("PQR777"));
        assertTrue(result.contains("STU888"));
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Create a Company object named "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create two part-time employees
        Employee employee1 = new Employee();
        employee1.setName("Olivia");
        employee1.setType(EmployeeType.PART_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Paul");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create two vehicles with no current drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setCurrentDriver(null); // No driver
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setCurrentDriver(null); // No driver
        
        // Assign vehicles to the Company object
        List<Employee> companyEmployees = new ArrayList<>();
        companyEmployees.add(employee1);
        companyEmployees.add(employee2);
        company.setEmployees(companyEmployees);
        
        List<Vehicle> companyVehicles = new ArrayList<>();
        companyVehicles.add(vehicle1);
        companyVehicles.add(vehicle2);
        company.setVehicles(companyVehicles);
        
        // Execute the method and verify results
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        assertEquals(0, result.size());
    }
}