import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    
    @Test
    public void testCase1_SingleEmployeeWithVehicle() {
        // Create a Company named "Food Express"
        Company company = new Company();
        company.setName("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        Employee employee = new Employee();
        employee.setName("John Doe");
        employee.setType(EmployeeType.FULL_TIME);
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber("ABC123");
        employee.setVehicle(vehicle);
        
        // Add employee to company
        company.setEmployees(Arrays.asList(employee));
        
        // Test the method and verify expected output
        List<String> result = company.findEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList("John Doe");
        
        assertEquals("Single employee with vehicle should return their name", expected, result);
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Create a Company named "Quick Delivery"
        Company company = new Company();
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee employee1 = new Employee();
        employee1.setName("Alice Smith");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Bob Johnson");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles with drivers
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        employee1.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("LMN456");
        employee2.setVehicle(vehicle2);
        
        // Add employees to company
        company.setEmployees(Arrays.asList(employee1, employee2));
        
        // Test the method and verify expected output
        List<String> result = company.findEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        
        assertEquals("Multiple employees with vehicles should return all their names", expected, result);
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Create a Company named "Gourmet Delivery"
        Company company = new Company();
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee employee1 = new Employee();
        employee1.setName("Charlie Brown");
        employee1.setType(EmployeeType.PART_TIME);
        // No vehicle assigned
        
        Employee employee2 = new Employee();
        employee2.setName("David Warner");
        employee2.setType(EmployeeType.FULL_TIME);
        // No vehicle assigned
        
        // Create Vehicles not assigned to any driver
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees to company
        company.setEmployees(Arrays.asList(employee1, employee2));
        // Add vehicles to company (though not relevant for this test)
        company.setVehicles(Arrays.asList(vehicle1, vehicle2));
        
        // Test the method and verify expected output
        List<String> result = company.findEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList();
        
        assertEquals("No drivers assigned to vehicles should return empty list", expected, result);
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Create a Company named "Fast Meals Co."
        Company company = new Company();
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee employee1 = new Employee();
        employee1.setName("Eva Green");
        employee1.setType(EmployeeType.FULL_TIME);
        
        Employee employee2 = new Employee();
        employee2.setName("Frank Wright");
        employee2.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles: one assigned, one not assigned, one assigned
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("RST234");
        employee1.setVehicle(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // Not assigned to any employee
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        employee2.setVehicle(vehicle3);
        
        // Add employees to company
        company.setEmployees(Arrays.asList(employee1, employee2));
        
        // Test the method and verify expected output
        List<String> result = company.findEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        
        assertEquals("Mixed vehicle assignments should return only employees with vehicles", expected, result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Create a Company named "Delicious Delivery"
        Company company = new Company();
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles: "Grace Title" (Full-Time), "Henry Field" (Part-Time)
        Employee employee1 = new Employee();
        employee1.setName("Grace Title");
        employee1.setType(EmployeeType.FULL_TIME);
        // No vehicle assigned
        
        Employee employee2 = new Employee();
        employee2.setName("Henry Field");
        employee2.setType(EmployeeType.PART_TIME);
        // No vehicle assigned
        
        // Add employees to company
        company.setEmployees(Arrays.asList(employee1, employee2));
        
        // Test the method and verify expected output
        List<String> result = company.findEmployeesDrivingVehicles();
        List<String> expected = Arrays.asList();
        
        assertEquals("Employees without vehicles should return empty list", expected, result);
    }
}