import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        // Initialize a new Company object before each test
        company = new Company();
    }
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // Test Case 1: Count Rented Vehicles with No Rented Vehicles
        // SetUp: Create Company "FastDeliveryInc" with three owned vehicles
        company.setName("FastDeliveryInc");
        
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setType(VehicleType.OWNED);
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setType(VehicleType.OWNED);
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setType(VehicleType.OWNED);
        vehicles.add(vehicle3);
        
        company.setVehicles(vehicles);
        
        // Execute: Count rented vehicles
        int rentedCount = company.countRentedVehicles();
        
        // Verify: Expected rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when company has only owned vehicles", 
                     0, rentedCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // Test Case 2: Count Rented Vehicles with One Rented Vehicle
        // Note: Specification says "One Rented Vehicle" but setup shows two rented vehicles
        // SetUp: Create Company "QuickEats" with mixed vehicles (2 rented, 2 owned)
        company.setName("QuickEats");
        
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("GHI101");
        vehicle1.setType(VehicleType.OWNED);
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL202");
        vehicle2.setType(VehicleType.RENTED);
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("MNO303");
        vehicle3.setType(VehicleType.OWNED);
        vehicles.add(vehicle3);
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("PQR404");
        vehicle4.setType(VehicleType.RENTED);
        vehicles.add(vehicle4);
        
        company.setVehicles(vehicles);
        
        // Execute: Count rented vehicles
        int rentedCount = company.countRentedVehicles();
        
        // Verify: Expected rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when company has 2 rented vehicles", 
                     2, rentedCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Test Case 3: Count Rented Vehicles All Rented
        // SetUp: Create Company "FoodExpress" with 5 rented vehicles
        company.setName("FoodExpress");
        
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("STU505");
        vehicle1.setType(VehicleType.RENTED);
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("VWX606");
        vehicle2.setType(VehicleType.RENTED);
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("YZA707");
        vehicle3.setType(VehicleType.RENTED);
        vehicles.add(vehicle3);
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("BCD808");
        vehicle4.setType(VehicleType.RENTED);
        vehicles.add(vehicle4);
        
        Vehicle vehicle5 = new Vehicle();
        vehicle5.setRegistrationNumber("EFG909");
        vehicle5.setType(VehicleType.RENTED);
        vehicles.add(vehicle5);
        
        company.setVe