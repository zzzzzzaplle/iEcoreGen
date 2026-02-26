import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Arrays;

public class CR4Test {
    private Company company;
    
    @Test
    public void testCase1_SingleEmployeeWithVehicle() {
        // Create a Company named "Food Express"
        company = new Company();
        company.setName("Food Express");
        
        // Create an Employee named "John Doe" of type Full-Time
        Employee john = new Employee();
        john.setName("John Doe");
        john.setType(EmployeeType.FULL_TIME);
        
        // Create a Vehicle with registration number "ABC123" assigned to "John Doe" as the driver
        Vehicle vehicle = new OwnedVehicle();
        vehicle.setRegistrationNumber("ABC123");
        vehicle.setCurrentDriver(john);
        john.setDrivenVehicle(vehicle);
        
        // Add employee and vehicle to company
        company.getEmployees().add(john);
        company.getVehicles().add(vehicle);
        
        // Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output
        assertEquals(Arrays.asList("John Doe"), result);
    }
    
    @Test
    public void testCase2_MultipleEmployeesMultipleVehicles() {
        // Create a Company named "Quick Delivery"
        company = new Company();
        company.setName("Quick Delivery");
        
        // Create Employees: "Alice Smith" (Full-Time), "Bob Johnson" (Part-Time)
        Employee alice = new Employee();
        alice.setName("Alice Smith");
        alice.setType(EmployeeType.FULL_TIME);
        
        Employee bob = new Employee();
        bob.setName("Bob Johnson");
        bob.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles with drivers assigned
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("XYZ789");
        vehicle1.setCurrentDriver(alice);
        alice.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("LMN456");
        vehicle2.setCurrentDriver(bob);
        bob.setDrivenVehicle(vehicle2);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(alice, bob));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output
        List<String> expected = Arrays.asList("Alice Smith", "Bob Johnson");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase3_NoCurrentDrivers() {
        // Create a Company named "Gourmet Delivery"
        company = new Company();
        company.setName("Gourmet Delivery");
        
        // Create Employees: "Charlie Brown" (Part-Time), "David Warner" (Full-Time)
        Employee charlie = new Employee();
        charlie.setName("Charlie Brown");
        charlie.setType(EmployeeType.PART_TIME);
        
        Employee david = new Employee();
        david.setName("David Warner");
        david.setType(EmployeeType.FULL_TIME);
        
        // Create Vehicles not assigned to any driver
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF321");
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("JKL654");
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(charlie, david));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2));
        
        // Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_MixedVehiclesState() {
        // Create a Company named "Fast Meals Co."
        company = new Company();
        company.setName("Fast Meals Co.");
        
        // Create Employees: "Eva Green" (Full-Time), "Frank Wright" (Part-Time)
        Employee eva = new Employee();
        eva.setName("Eva Green");
        eva.setType(EmployeeType.FULL_TIME);
        
        Employee frank = new Employee();
        frank.setName("Frank Wright");
        frank.setType(EmployeeType.PART_TIME);
        
        // Create Vehicles with mixed assignment state
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("RST234");
        vehicle1.setCurrentDriver(eva);
        eva.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("UVW567");
        // No driver assigned
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("OPQ890");
        vehicle3.setCurrentDriver(frank);
        frank.setDrivenVehicle(vehicle3);
        
        // Add employees and vehicles to company
        company.getEmployees().addAll(Arrays.asList(eva, frank));
        company.getVehicles().addAll(Arrays.asList(vehicle1, vehicle2, vehicle3));
        
        // Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output
        List<String> expected = Arrays.asList("Eva Green", "Frank Wright");
        assertEquals(expected, result);
    }
    
    @Test
    public void testCase5_EmployeesWithoutVehicles() {
        // Create a Company named "Delicious Delivery"
        company = new Company();
        company.setName("Delicious Delivery");
        
        // Create Employees without vehicles
        Employee grace = new Employee();
        grace.setName("Grace Title");
        grace.setType(EmployeeType.FULL_TIME);
        
        Employee henry = new Employee();
        henry.setName("Henry Field");
        henry.setType(EmployeeType.PART_TIME);
        
        // Add employees to company (no vehicles added)
        company.getEmployees().addAll(Arrays.asList(grace, henry));
        
        // Retrieve current drivers' names
        List<String> result = company.getCurrentDriversNames();
        
        // Verify expected output
        assertTrue(result.isEmpty());
    }
}