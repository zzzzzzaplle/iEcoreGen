import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // SetUp: Create company and add three owned vehicles
        company.setName("FastDeliveryInc");
        
        List<Vehicle> vehicles = new ArrayList<>();
        
        // Add owned vehicle ABC123
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicles.add(vehicle1);
        
        // Add owned vehicle XYZ789
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicles.add(vehicle2);
        
        // Add owned vehicle LMN456
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicles.add(vehicle3);
        
        company.setVehicles(vehicles);
        
        // Test: Count rented vehicles
        int result = company.getRentedVehicleCount();
        
        // Verify: Expected count is 0
        assertEquals("Rented vehicle count should be 0 when no rented vehicles exist", 0, result);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // SetUp: Create company and add mixed vehicles (2 owned, 2 rented)
        company.setName("QuickEats");
        
        List<Vehicle> vehicles = new ArrayList<>();
        
        // Add owned vehicle GHI101
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("GHI101");
        vehicles.add(vehicle1);
        
        // Add rented vehicle JKL202
        RentedVehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("JKL202");
        vehicles.add(vehicle2);
        
        // Add owned vehicle MNO303
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("MNO303");
        vehicles.add(vehicle3);
        
        // Add rented vehicle PQR404
        RentedVehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("PQR404");
        vehicles.add(vehicle4);
        
        company.setVehicles(vehicles);
        
        // Test: Count rented vehicles
        int result = company.getRentedVehicleCount();
        
        // Verify: Expected count is 2
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist", 2, result);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // SetUp: Create company and add five rented vehicles
        company.setName("FoodExpress");
        
        List<Vehicle> vehicles = new ArrayList<>();
        
        // Add five rented vehicles
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
        
        // Test: Count rented vehicles
        int result = company.getRentedVehicleCount();
        
        // Verify: Expected count is 5
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, result);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // SetUp: Create company and add mixed vehicles (4 owned, 2 rented)
        company.setName("DailyDeliveries");
        
        List<Vehicle> vehicles = new ArrayList<>();
        
        // Add owned vehicle HIJ010
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("HIJ010");
        vehicles.add(vehicle1);
        
        // Add rented vehicle KLM111
        RentedVehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("KLM111");
        vehicles.add(vehicle2);
        
        // Add owned vehicle NOP222
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("NOP222");
        vehicles.add(vehicle3);
        
        // Add rented vehicle QRS333
        RentedVehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("QRS333");
        vehicles.add(vehicle4);
        
        // Add owned vehicle TUV444
        OwnedVehicle vehicle5 = new OwnedVehicle();
        vehicle5.setRegistrationNumber("TUV444");
        vehicles.add(vehicle5);
        
        // Add owned vehicle WXY555
        OwnedVehicle vehicle6 = new OwnedVehicle();
        vehicle6.setRegistrationNumber("WXY555");
        vehicles.add(vehicle6);
        
        company.setVehicles(vehicles);
        
        // Test: Count rented vehicles
        int result = company.getRentedVehicleCount();
        
        // Verify: Expected count is 2
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist among mixed types", 2, result);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // SetUp: Create company with no vehicles
        company.setName("DeliveriesRUs");
        
        // vehicles list remains empty (default empty list)
        
        // Test: Count rented vehicles
        int result = company.getRentedVehicleCount();
        
        // Verify: Expected count is 0
        assertEquals("Rented vehicle count should be 0 when no vehicles exist", 0, result);
    }
}