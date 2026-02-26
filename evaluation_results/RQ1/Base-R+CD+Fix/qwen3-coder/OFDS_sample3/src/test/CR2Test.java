import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Test Case 1: Single Part-Time Employee Vehicle Retrieval
        // Create a Company object named "Food Express"
        Company company = new Company();
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
        vehicle1.setCurrentDriver(employee1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setCurrentDriver(employee2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setCurrentDriver(employee3);
        
        // Assign employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected: Registration numbers = ["ABC123", "XYZ789"]
        List<String> expected = new ArrayList<>();
        expected.add("ABC123");
        expected.add("XYZ789");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Test Case 2: No Part-Time Employees
        // Create a Company object named "Quick Delivery"
        Company company = new Company();
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
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setCurrentDriver(employee2);
        
        // Assign employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected: Registration numbers = []
        List<String> expected = new ArrayList<>();
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Test Case 3: Mixed Employees and Vehicles
        // Create a Company object named "Gourmet Delivery"
        Company company = new Company();
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
        
        // Create three vehicles (note: test case says 4 but only shows 3)
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setCurrentDriver(employee1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setCurrentDriver(employee2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setCurrentDriver(employee3);
        
        // Assign employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        employees.add(employee4);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected: Registration numbers = ["DEF333"]
        List<String> expected = new ArrayList<>();
        expected.add("DEF333");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Test Case 4: Multiple Drivers for One Vehicle
        // Create a Company object named "City Foods"
        Company company = new Company();
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
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setCurrentDriver(employee1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setCurrentDriver(employee2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setCurrentDriver(employee3);
        
        // Assign employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected: Registration numbers = ["PQR777", "STU888"]
        List<String> expected = new ArrayList<>();
        expected.add("PQR777");
        expected.add("STU888");
        
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Test Case 5: All Vehicles without Drivers
        // Create a Company object named "Rapid Deliveries"
        Company company = new Company();
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
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setCurrentDriver(null);
        
        // Assign employees and vehicles to company
        List<Employee> employees = new ArrayList<>();
        employees.add(employee1);
        employees.add(employee2);
        company.setEmployees(employees);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Get registration numbers of vehicles driven by part-time employees
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Expected: Registration numbers = []
        List<String> expected = new ArrayList<>();
        
        assertEquals(expected, result);
    }
}