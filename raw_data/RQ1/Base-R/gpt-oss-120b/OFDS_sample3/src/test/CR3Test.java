import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = null;
    }
    
    @Test
    public void testCase1_countRentedVehiclesWithNoRentedVehicles() {
        // Test Case 1: Count Rented Vehicles with No Rented Vehicles
        // SetUp: Create a Company object named "FastDeliveryInc"
        company = new Company("FastDeliveryInc");
        
        // Add three owned vehicles to the company
        Vehicle vehicle1 = new Vehicle("ABC123", VehicleType.OWNED);
        Vehicle vehicle2 = new Vehicle("XYZ789", VehicleType.OWNED);
        Vehicle vehicle3 = new Vehicle("LMN456", VehicleType.OWNED);
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Count the number of rented vehicles in the company
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when there are no rented vehicles", 0, rentedCount);
    }
    
    @Test
    public void testCase2_countRentedVehiclesWithTwoRentedVehicles() {
        // Test Case 2: Count Rented Vehicles with Two Rented Vehicles
        // SetUp: Create a Company object named "QuickEats"
        company = new Company("QuickEats");
        
        // Add four vehicles to the company (two owned, two rented)
        Vehicle vehicle1 = new Vehicle("GHI101", VehicleType.OWNED);
        Vehicle vehicle2 = new Vehicle("JKL202", VehicleType.RENTED);
        Vehicle vehicle3 = new Vehicle("MNO303", VehicleType.OWNED);
        Vehicle vehicle4 = new Vehicle("PQR404", VehicleType.RENTED);
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        company.addVehicle(vehicle4);
        
        // Count the number of rented vehicles in the company
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles", 2, rentedCount);
    }
    
    @Test
    public void testCase3_countRentedVehiclesAllRented() {
        // Test Case 3: Count Rented Vehicles All Rented
        // SetUp: Create a Company object named "FoodExpress"
        company = new Company("FoodExpress");
        
        // Add five rented vehicles to the company
        Vehicle vehicle1 = new Vehicle("STU505", VehicleType.RENTED);
        Vehicle vehicle2 = new Vehicle("VWX606", VehicleType.RENTED);
        Vehicle vehicle3 = new Vehicle("YZA707", VehicleType.RENTED);
        Vehicle vehicle4 = new Vehicle("BCD808", VehicleType.RENTED);
        Vehicle vehicle5 = new Vehicle("EFG909", VehicleType.RENTED);
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        company.addVehicle(vehicle4);
        company.addVehicle(vehicle5);
        
        // Count the number of rented vehicles in the company
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 5
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, rentedCount);
    }
    
    @Test
    public void testCase4_countRentedVehiclesWithMixedVehicleTypes() {
        // Test Case 4: Count Rented Vehicles with Mixed Vehicle Types
        // SetUp: Create a Company object named "DailyDeliveries"
        company = new Company("DailyDeliveries");
        
        // Add six vehicles to the company (four owned, two rented)
        Vehicle vehicle1 = new Vehicle("HIJ010", VehicleType.OWNED);
        Vehicle vehicle2 = new Vehicle("KLM111", VehicleType.RENTED);
        Vehicle vehicle3 = new Vehicle("NOP222", VehicleType.OWNED);
        Vehicle vehicle4 = new Vehicle("QRS333", VehicleType.RENTED);
        Vehicle vehicle5 = new Vehicle("TUV444", VehicleType.OWNED);
        Vehicle vehicle6 = new Vehicle("WXY555", VehicleType.OWNED);
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        company.addVehicle(vehicle4);
        company.addVehicle(vehicle5);
        company.addVehicle(vehicle6);
        
        // Count the number of rented vehicles in the company
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles among mixed types", 2, rentedCount);
    }
    
    @Test
    public void testCase5_countRentedVehiclesWithNoVehicles() {
        // Test Case 5: Count Rented Vehicles with No Vehicles
        // SetUp: Create a Company object named "DeliveriesRUs"
        company = new Company("DeliveriesRUs");
        
        // Do not add any vehicles to the company (vehicles list remains empty)
        
        // Count the number of rented vehicles in the company
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when there are no vehicles", 0, rentedCount);
    }
}