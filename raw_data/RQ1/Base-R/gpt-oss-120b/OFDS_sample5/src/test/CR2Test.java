import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR2Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_SinglePartTimeEmployeeVehicleRetrieval() {
        // Create company
        company.setName("Food Express");
        
        // Create employees
        Employee emp1 = new Employee();
        emp1.setName("Alice");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Bob");
        emp2.setType(EmployeeType.PART_TIME);
        
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
        emp2.setAssignedVehicle(vehicle2);
        emp3.setAssignedVehicle(vehicle3);
        
        // Add employees to company
        company.getEmployees().add(emp1);
        company.getEmployees().add(emp2);
        company.getEmployees().add(emp3);
        company.getEmployees().add(emp4);
        
        // Add vehicles to company
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Execute method under test
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify expected output
        assertEquals(2, result.size());
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("XYZ789"));
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Create company
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
        emp1.setAssignedVehicle(vehicle1);
        emp2.setAssignedVehicle(vehicle2);
        
        // Add employees to company
        company.getEmployees().add(emp1);
        company.getEmployees().add(emp2);
        company.getEmployees().add(emp3);
        
        // Add vehicles to company
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Execute method under test
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify expected output - empty list
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Create company
        company.setName("Gourmet Delivery");
        
        // Create mixed employees
        Employee emp1 = new Employee();
        emp1.setName("Henry");
        emp1.setType(EmployeeType.PART_TIME);
        
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
        emp2.setAssignedVehicle(vehicle2);
        emp3.setAssignedVehicle(vehicle3);
        
        // Add employees to company
        company.getEmployees().add(emp1);
        company.getEmployees().add(emp2);
        company.getEmployees().add(emp3);
        company.getEmployees().add(emp4);
        
        // Add vehicles to company
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Execute method under test
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify expected output - only vehicle driven by part-time employee
        assertEquals(1, result.size());
        assertEquals("DEF333", result.get(0));
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Create company
        company.setName("City Foods");
        
        // Create part-time and full-time employees
        Employee emp1 = new Employee();
        emp1.setName("Lily");
        emp1.setType(EmployeeType.PART_TIME);
        
        Employee emp2 = new Employee();
        emp2.setName("Mike");
        emp2.setType(EmployeeType.PART_TIME);
        
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
        emp1.setAssignedVehicle(vehicle1);
        emp2.setAssignedVehicle(vehicle2);
        emp3.setAssignedVehicle(vehicle3);
        
        // Add employees to company
        company.getEmployees().add(emp1);
        company.getEmployees().add(emp2);
        company.getEmployees().add(emp3);
        
        // Add vehicles to company
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        company.getVehicles().add(vehicle3);
        
        // Execute method under test
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify expected output - vehicles driven by part-time employees
        assertEquals(2, result.size());
        assertTrue(result.contains("PQR777"));
        assertTrue(result.contains("STU888"));
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Create company
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
        
        // Add employees to company (no vehicles assigned)
        company.getEmployees().add(emp1);
        company.getEmployees().add(emp2);
        
        // Add vehicles to company
        company.getVehicles().add(vehicle1);
        company.getVehicles().add(vehicle2);
        
        // Execute method under test
        List<String> result = company.getVehicleRegistrationsDrivenByPartTimeEmployees();
        
        // Verify expected output - empty list since no vehicles are assigned to employees
        assertTrue(result.isEmpty());
    }
}