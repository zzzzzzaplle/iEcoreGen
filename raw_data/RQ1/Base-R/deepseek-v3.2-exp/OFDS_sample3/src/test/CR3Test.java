import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    private Vehicle vehicle;
    private Employee employee;
    
    @Before
    public void setUp() {
        // Initialize common objects before each test
        company = new Company();
        vehicle = new Vehicle();
        employee = new Employee();
    }
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // SetUp: Create a Company object named "FastDeliveryInc"
        company.setName("FastDeliveryInc");
        
        // Create and add three owned vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setRented(false); // owned vehicle
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setRented(false); // owned vehicle
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setRented(false); // owned vehicle
        vehicles.add(vehicle3);
        
        company.setVehicles(vehicles);
        
        // Count rented vehicles and verify result
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when there are no rented vehicles", 0, rentedCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // SetUp: Create a Company object named "QuickEats"
        company.setName("QuickEats");
        
        // Create and add four vehicles to the company (2 rented, 2 owned)
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("GHI101");
        vehicle1.setRented(false); // owned vehicle
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL202");
        vehicle2.setRented(true); // rented vehicle
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("MNO303");
        vehicle3.setRented(false); // owned vehicle
        vehicles.add(vehicle3);
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("PQR404");
        vehicle4.setRented(true); // rented vehicle
        vehicles.add(vehicle4);
        
        company.setVehicles(vehicles);
        
        // Count rented vehicles and verify result
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles", 2, rentedCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // SetUp: Create a Company object named "FoodExpress"
        company.setName("FoodExpress");
        
        // Create and add five rented vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("STU505");
        vehicle1.setRented(true); // rented vehicle
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("VWX606");
        vehicle2.setRented(true); // rented vehicle
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("YZA707");
        vehicle3.setRented(true); // rented vehicle
        vehicles.add(vehicle3);
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("BCD808");
        vehicle4.setRented(true); // rented vehicle
        vehicles.add(vehicle4);
        
        Vehicle vehicle5 = new Vehicle();
        vehicle5.setRegistrationNumber("EFG909");
        vehicle5.setRented(true); // rented vehicle
        vehicles.add(vehicle5);
        
        company.setVehicles(vehicles);
        
        // Count rented vehicles and verify result
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 5
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, rentedCount);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // SetUp: Create a Company object named "DailyDeliveries"
        company.setName("DailyDeliveries");
        
        // Create and add six vehicles to the company (2 rented, 4 owned)
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("HIJ010");
        vehicle1.setRented(false); // owned vehicle
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("KLM111");
        vehicle2.setRented(true); // rented vehicle
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("NOP222");
        vehicle3.setRented(false); // owned vehicle
        vehicles.add(vehicle3);
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("QRS333");
        vehicle4.setRented(true); // rented vehicle
        vehicles.add(vehicle4);
        
        Vehicle vehicle5 = new Vehicle();
        vehicle5.setRegistrationNumber("TUV444");
        vehicle5.setRented(false); // owned vehicle
        vehicles.add(vehicle5);
        
        Vehicle vehicle6 = new Vehicle();
        vehicle6.setRegistrationNumber("WXY555");
        vehicle6.setRented(false); // owned vehicle
        vehicles.add(vehicle6);
        
        company.setVehicles(vehicles);
        
        // Count rented vehicles and verify result
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles mixed with owned vehicles", 2, rentedCount);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // SetUp: Create a Company object named "DeliveriesRUs"
        company.setName("DeliveriesRUs");
        
        // Do not add any vehicles to the company (use default empty list)
        
        // Count rented vehicles and verify result
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when there are no vehicles", 0, rentedCount);
    }
}