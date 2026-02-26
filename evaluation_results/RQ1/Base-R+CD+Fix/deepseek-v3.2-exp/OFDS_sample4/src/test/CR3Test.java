import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // SetUp: Create a Company object named "FastDeliveryInc"
        company.setName("FastDeliveryInc");
        
        // Add three owned vehicles to the company
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Test: Count the number of rented vehicles in the company
        int result = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Should return 0 when there are no rented vehicles", 0, result);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // SetUp: Create a Company object named "QuickEats"
        company.setName("QuickEats");
        
        // Add four vehicles to the company with two rented vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("GHI101");
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("JKL202");
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("MNO303");
        
        Vehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("PQR404");
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        vehicles.add(vehicle4);
        company.setVehicles(vehicles);
        
        // Test: Count the number of rented vehicles in the company
        int result = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Should return 2 when there are two rented vehicles", 2, result);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // SetUp: Create a Company object named "FoodExpress"
        company.setName("FoodExpress");
        
        // Add five rented vehicles to the company
        Vehicle vehicle1 = new RentedVehicle();
        vehicle1.setRegistrationNumber("STU505");
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("VWX606");
        
        Vehicle vehicle3 = new RentedVehicle();
        vehicle3.setRegistrationNumber("YZA707");
        
        Vehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("BCD808");
        
        Vehicle vehicle5 = new RentedVehicle();
        vehicle5.setRegistrationNumber("EFG909");
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        vehicles.add(vehicle4);
        vehicles.add(vehicle5);
        company.setVehicles(vehicles);
        
        // Test: Count the number of rented vehicles in the company
        int result = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 5
        assertEquals("Should return 5 when all vehicles are rented", 5, result);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // SetUp: Create a Company object named "DailyDeliveries"
        company.setName("DailyDeliveries");
        
        // Add six vehicles to the company with two rented vehicles
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("HIJ010");
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("KLM111");
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("NOP222");
        
        Vehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("QRS333");
        
        Vehicle vehicle5 = new OwnedVehicle();
        vehicle5.setRegistrationNumber("TUV444");
        
        Vehicle vehicle6 = new OwnedVehicle();
        vehicle6.setRegistrationNumber("WXY555");
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        vehicles.add(vehicle4);
        vehicles.add(vehicle5);
        vehicles.add(vehicle6);
        company.setVehicles(vehicles);
        
        // Test: Count the number of rented vehicles in the company
        int result = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Should return 2 when there are two rented vehicles among mixed types", 2, result);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // SetUp: Create a Company object named "DeliveriesRUs"
        company.setName("DeliveriesRUs");
        
        // Do not add any vehicles to the company (vehicles list remains empty)
        
        // Test: Count the number of rented vehicles in the company
        int result = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Should return 0 when there are no vehicles", 0, result);
    }
}