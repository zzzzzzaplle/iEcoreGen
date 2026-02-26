import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

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
        emp1.setAssignedVehicle(vehicle1);
        vehicle1.setDriver(emp1);
        
        emp2.setAssignedVehicle(vehicle2);
        vehicle2.setDriver(emp2);
        
        emp3.setAssignedVehicle(vehicle3);
        vehicle3.setDriver(emp3);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(emp1, emp2, emp3, emp4));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Get registration numbers driven by part-time employees
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify: Expected registration numbers = ["ABC123", "XYZ789"]
        assertEquals(2, result.size());
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("XYZ789"));
        assertEquals(Arrays.asList("ABC123", "XYZ789"), result);
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // SetUp: Create company with only full-time employees
        company.setName("Quick Delivery");
        
        // Create full-time employees
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
        emp1.setAssignedVehicle(vehicle1);
        vehicle1.setDriver(emp1);
        
        emp2.setAssignedVehicle(vehicle2);
        vehicle2.setDriver(emp2);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(emp1, emp2, emp3));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Execute: Get registration numbers driven by part-time employees
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify: Expected registration numbers = []
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // SetUp: Create company with mixed employees and vehicles
        company.setName("Gourmet Delivery");
        
        // Create part-time employee
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
        emp1.setAssignedVehicle(vehicle1);
        vehicle1.setDriver(emp1);
        
        emp2.setAssignedVehicle(vehicle2);
        vehicle2.setDriver(emp2);
        
        emp3.setAssignedVehicle(vehicle3);
        vehicle3.setDriver(emp3);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(emp1, emp2, emp3, emp4));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Get registration numbers driven by part-time employees
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify: Expected registration numbers = ["DEF333"]
        assertEquals(1, result.size());
        assertEquals("DEF333", result.get(0));
        assertEquals(Arrays.asList("DEF333"), result);
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // SetUp: Create company with multiple part-time drivers
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
        
        // Assign vehicles to employees (each vehicle has one driver)
        emp1.setAssignedVehicle(vehicle1);
        vehicle1.setDriver(emp1);
        
        emp2.setAssignedVehicle(vehicle2);
        vehicle2.setDriver(emp2);
        
        emp3.setAssignedVehicle(vehicle3);
        vehicle3.setDriver(emp3);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(emp1, emp2, emp3));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Execute: Get registration numbers driven by part-time employees
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify: Expected registration numbers = ["PQR777", "STU888"]
        assertEquals(2, result.size());
        assertTrue(result.contains("PQR777"));
        assertTrue(result.contains("STU888"));
        assertEquals(Arrays.asList("PQR777", "STU888"), result);
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // SetUp: Create company with vehicles having no drivers
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
        vehicle1.setDriver(null);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setDriver(null);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(emp1, emp2));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Execute: Get registration numbers driven by part-time employees
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify: Expected registration numbers = []
        assertTrue(result.isEmpty());
        assertEquals(0, result.size());
    }
}