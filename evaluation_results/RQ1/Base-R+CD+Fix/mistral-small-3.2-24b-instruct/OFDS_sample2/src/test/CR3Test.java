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
        company = null;
    }
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // Test Case 1: Count Rented Vehicles with No Rented Vehicles
        // SetUp: Create Company "FastDeliveryInc" with 3 owned vehicles
        company = new Company();
        company.setName("FastDeliveryInc");
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new OwnedVehicle("ABC123"));
        vehicles.add(new OwnedVehicle("XYZ789"));
        vehicles.add(new OwnedVehicle("LMN456"));
        company.setVehicles(vehicles);
        
        // Execute: Count rented vehicles
        int rentedCount = company.getRentedVehicleCount();
        
        // Verify: Expected rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when no rented vehicles exist", 0, rentedCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // Test Case 2: Count Rented Vehicles with Two Rented Vehicles
        // SetUp: Create Company "QuickEats" with 2 owned and 2 rented vehicles
        company = new Company();
        company.setName("QuickEats");
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new OwnedVehicle("GHI101"));
        vehicles.add(new RentedVehicle("JKL202"));
        vehicles.add(new OwnedVehicle("MNO303"));
        vehicles.add(new RentedVehicle("PQR404"));
        company.setVehicles(vehicles);
        
        // Execute: Count rented vehicles
        int rentedCount = company.getRentedVehicleCount();
        
        // Verify: Expected rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist", 2, rentedCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Test Case 3: Count Rented Vehicles All Rented
        // SetUp: Create Company "FoodExpress" with 5 rented vehicles
        company = new Company();
        company.setName("FoodExpress");
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new RentedVehicle("STU505"));
        vehicles.add(new RentedVehicle("VWX606"));
        vehicles.add(new RentedVehicle("YZA707"));
        vehicles.add(new RentedVehicle("BCD808"));
        vehicles.add(new RentedVehicle("EFG909"));
        company.setVehicles(vehicles);
        
        // Execute: Count rented vehicles
        int rentedCount = company.getRentedVehicleCount();
        
        // Verify: Expected rented vehicle count = 5
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, rentedCount);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // Test Case 4: Count Rented Vehicles with Mixed Vehicle Types
        // SetUp: Create Company "DailyDeliveries" with 4 owned and 2 rented vehicles
        company = new Company();
        company.setName("DailyDeliveries");
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new OwnedVehicle("HIJ010"));
        vehicles.add(new RentedVehicle("KLM111"));
        vehicles.add(new OwnedVehicle("NOP222"));
        vehicles.add(new RentedVehicle("QRS333"));
        vehicles.add(new OwnedVehicle("TUV444"));
        vehicles.add(new OwnedVehicle("WXY555"));
        company.setVehicles(vehicles);
        
        // Execute: Count rented vehicles
        int rentedCount = company.getRentedVehicleCount();
        
        // Verify: Expected rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when mixed vehicle types exist", 2, rentedCount);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // Test Case 5: Count Rented Vehicles with No Vehicles
        // SetUp: Create Company "DeliveriesRUs" with no vehicles
        company = new Company();
        company.setName("DeliveriesRUs");
        
        // No vehicles added to company
        
        // Execute: Count rented vehicles
        int rentedCount = company.getRentedVehicleCount();
        
        // Verify: Expected rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when no vehicles exist", 0, rentedCount);
    }
}