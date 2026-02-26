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
        // Create a Company object named "Food Express"
        company.setName("Food Express");
        
        // Create two part-time employees
        Employee alice = new Employee();
        alice.setName("Alice");
        alice.setType(EmployeeType.PART_TIME);
        
        Employee bob = new Employee();
        bob.setName("Bob");
        bob.setType(EmployeeType.PART_TIME);
        
        // Create two full-time employees
        Employee charlie = new Employee();
        charlie.setName("Charlie");
        charlie.setType(EmployeeType.FULL_TIME);
        
        Employee diana = new Employee();
        diana.setName("Diana");
        diana.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setCurrentDriver(alice);
        alice.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setCurrentDriver(bob);
        bob.setDrivenVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setCurrentDriver(charlie);
        charlie.setDrivenVehicle(vehicle3);
        
        // Assign vehicles to the Company object
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Assign employees to the Company object
        List<Employee> employees = new ArrayList<>();
        employees.add(alice);
        employees.add(bob);
        employees.add(charlie);
        employees.add(diana);
        company.setEmployees(employees);
        
        // Execute the method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify the result
        assertEquals(2, result.size());
        assertTrue(result.contains("ABC123"));
        assertTrue(result.contains("XYZ789"));
    }
    
    @Test
    public void testCase2_NoPartTimeEmployees() {
        // Create a Company object named "Quick Delivery"
        company.setName("Quick Delivery");
        
        // Create three full-time employees
        Employee ethan = new Employee();
        ethan.setName("Ethan");
        ethan.setType(EmployeeType.FULL_TIME);
        
        Employee fiona = new Employee();
        fiona.setName("Fiona");
        fiona.setType(EmployeeType.FULL_TIME);
        
        Employee george = new Employee();
        george.setName("George");
        george.setType(EmployeeType.FULL_TIME);
        
        // Create two vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("QWE111");
        vehicle1.setCurrentDriver(ethan);
        ethan.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("RTY222");
        vehicle2.setCurrentDriver(fiona);
        fiona.setDrivenVehicle(vehicle2);
        
        // Assign vehicles to the Company object
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Assign employees to the Company object
        List<Employee> employees = new ArrayList<>();
        employees.add(ethan);
        employees.add(fiona);
        employees.add(george);
        company.setEmployees(employees);
        
        // Execute the method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify the result
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase3_MixedEmployeesAndVehicles() {
        // Create a Company object named "Gourmet Delivery"
        company.setName("Gourmet Delivery");
        
        // Create one part-time employee
        Employee henry = new Employee();
        henry.setName("Henry");
        henry.setType(EmployeeType.PART_TIME);
        
        // Create three full-time employees
        Employee isla = new Employee();
        isla.setName("Isla");
        isla.setType(EmployeeType.FULL_TIME);
        
        Employee jack = new Employee();
        jack.setName("Jack");
        jack.setType(EmployeeType.FULL_TIME);
        
        Employee kara = new Employee();
        kara.setName("Kara");
        kara.setType(EmployeeType.FULL_TIME);
        
        // Create four vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("DEF333");
        vehicle1.setCurrentDriver(henry);
        henry.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("GHI444");
        vehicle2.setCurrentDriver(isla);
        isla.setDrivenVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("JKL555");
        vehicle3.setCurrentDriver(jack);
        jack.setDrivenVehicle(vehicle3);
        
        // Note: The test specification mentions 4 vehicles but only describes 3.
        // I'll assume it's a typo and proceed with 3 vehicles as described.
        
        // Assign vehicles to the Company object
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Assign employees to the Company object
        List<Employee> employees = new ArrayList<>();
        employees.add(henry);
        employees.add(isla);
        employees.add(jack);
        employees.add(kara);
        company.setEmployees(employees);
        
        // Execute the method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify the result
        assertEquals(1, result.size());
        assertTrue(result.contains("DEF333"));
    }
    
    @Test
    public void testCase4_MultipleDriversForOneVehicle() {
        // Create a Company object named "City Foods"
        company.setName("City Foods");
        
        // Create two part-time employees
        Employee lily = new Employee();
        lily.setName("Lily");
        lily.setType(EmployeeType.PART_TIME);
        
        Employee mike = new Employee();
        mike.setName("Mike");
        mike.setType(EmployeeType.PART_TIME);
        
        // Create one full-time employee
        Employee nina = new Employee();
        nina.setName("Nina");
        nina.setType(EmployeeType.FULL_TIME);
        
        // Create three vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("PQR777");
        vehicle1.setCurrentDriver(lily);
        lily.setDrivenVehicle(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("STU888");
        vehicle2.setCurrentDriver(mike);
        mike.setDrivenVehicle(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("VWX999");
        vehicle3.setCurrentDriver(nina);
        nina.setDrivenVehicle(vehicle3);
        
        // Assign vehicles to the Company object
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Assign employees to the Company object
        List<Employee> employees = new ArrayList<>();
        employees.add(lily);
        employees.add(mike);
        employees.add(nina);
        company.setEmployees(employees);
        
        // Execute the method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify the result
        assertEquals(2, result.size());
        assertTrue(result.contains("PQR777"));
        assertTrue(result.contains("STU888"));
    }
    
    @Test
    public void testCase5_AllVehiclesWithoutDrivers() {
        // Create a Company object named "Rapid Deliveries"
        company.setName("Rapid Deliveries");
        
        // Create two part-time employees
        Employee olivia = new Employee();
        olivia.setName("Olivia");
        olivia.setType(EmployeeType.PART_TIME);
        
        Employee paul = new Employee();
        paul.setName("Paul");
        paul.setType(EmployeeType.PART_TIME);
        
        // Create two vehicles with no current drivers
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("AAA000");
        vehicle1.setCurrentDriver(null);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("BBB111");
        vehicle2.setCurrentDriver(null);
        
        // Assign vehicles to the Company object
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        company.setVehicles(vehicles);
        
        // Assign employees to the Company object
        List<Employee> employees = new ArrayList<>();
        employees.add(olivia);
        employees.add(paul);
        company.setEmployees(employees);
        
        // Execute the method under test
        List<String> result = company.getPartTimeDriverVehicleRegistrationNumbers();
        
        // Verify the result
        assertEquals(0, result.size());
    }
}