import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Reset company before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // Test Case 1: Count Rented Vehicles with No Rented Vehicles
        // SetUp: Create Company "FastDeliveryInc" with 3 owned vehicles
        company.setName("FastDeliveryInc");
        
        List<Vehicle> vehicles = new ArrayList<>();
        
        // Add owned vehicle with registration number "ABC123"
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicles.add(vehicle1);
        
        // Add owned vehicle with registration number "XYZ789"
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicles.add(vehicle2);
        
        // Add owned vehicle with registration number "LMN456"
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicles.add(vehicle3);
        
        company.setVehicles(vehicles);
        
        // Execute: Count rented vehicles
        int rentedCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when no rented vehicles exist", 0, rentedCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // Test Case 2: Count Rented Vehicles with Two Rented Vehicles
        // SetUp: Create Company "QuickEats" with 2 owned and 2 rented vehicles
        company.setName("QuickEats");
        
        List<Vehicle> vehicles = new ArrayList<>();
        
        // Add owned vehicle with registration number "GHI101"
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("GHI101");
        vehicles.add(vehicle1);
        
        // Add rented vehicle with registration number "JKL202"
        RentedVehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("JKL202");
        vehicles.add(vehicle2);
        
        // Add owned vehicle with registration number "MNO303"
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("MNO303");
        vehicles.add(vehicle3);
        
        // Add rented vehicle with registration number "PQR404"
        RentedVehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("PQR404");
        vehicles.add(vehicle4);
        
        company.setVehicles(vehicles);
        
        // Execute: Count rented vehicles
        int rentedCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when there are 2 rented vehicles", 2, rentedCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Test Case 3: Count Rented Vehicles All Rented
        // SetUp: Create Company "FoodExpress" with 5 rented vehicles
        company.setName("FoodExpress");
        
        List<Vehicle> vehicles = new ArrayList<>();
        
        // Add 5 rented vehicles with sequential registration numbers
        RentedVehicle vehicle1 = new RentedVehicle();
        vehicle1.setRegistrationNumber("STU505");
        vehicles.add(vehicle1);
        
        RentedVehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("VWX606");
        vehicles.add(vehicle2);
        
        RentedVehicle vehicle3 = new RentedVehicle();
        vehicle3.setRegistrationNumber("YZA707");
        vehicles.add(vehicle3);
        
        RentedVehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("BCD808");
        vehicles.add(vehicle4);
        
        RentedVehicle vehicle5 = new RentedVehicle();
        vehicle5.setRegistrationNumber("EFG909");
        vehicles.add(vehicle5);
        
        company.setVehicles(vehicles);
        
        // Execute: Count rented vehicles
        int rentedCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 5
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, rentedCount);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // Test Case 4: Count Rented Vehicles with Mixed Vehicle Types
        // SetUp: Create Company "DailyDeliveries" with 4 owned and 2 rented vehicles
        company.setName("DailyDeliveries");
        
        List<Vehicle> vehicles = new ArrayList<>();
        
        // Add owned vehicle with registration number "HIJ010"
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("HIJ010");
        vehicles.add(vehicle1);
        
        // Add rented vehicle with registration number "KLM111"
        RentedVehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("KLM111");
        vehicles.add(vehicle2);
        
        // Add owned vehicle with registration number "NOP222"
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("NOP222");
        vehicles.add(vehicle3);
        
        // Add rented vehicle with registration number "QRS333"
        RentedVehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("QRS333");
        vehicles.add(vehicle4);
        
        // Add owned vehicle with registration number "TUV444"
        OwnedVehicle vehicle5 = new OwnedVehicle();
        vehicle5.setRegistrationNumber("TUV444");
        vehicles.add(vehicle5);
        
        // Add owned vehicle with registration number "WXY555"
        OwnedVehicle vehicle6 = new OwnedVehicle();
        vehicle6.setRegistrationNumber("WXY555");
        vehicles.add(vehicle6);
        
        company.setVehicles(vehicles);
        
        // Execute: Count rented vehicles
        int rentedCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when there are 2 rented vehicles mixed with owned vehicles", 2, rentedCount);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // Test Case 5: Count Rented Vehicles with No Vehicles
        // SetUp: Create Company "DeliveriesRUs" with no vehicles
        company.setName("DeliveriesRUs");
        // No vehicles added to the company
        
        // Execute: Count rented vehicles
        int rentedCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when no vehicles exist in the company", 0, rentedCount);
    }
}