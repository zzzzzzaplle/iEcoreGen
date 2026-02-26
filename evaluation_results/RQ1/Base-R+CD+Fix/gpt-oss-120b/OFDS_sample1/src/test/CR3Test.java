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
        
        // SetUp: Add three owned vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicles.add(vehicle1);
        
        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicles.add(vehicle2);
        
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicles.add(vehicle3);
        
        company.setVehicles(vehicles);
        
        // Execute: Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when there are no rented vehicles", 0, rentedVehicleCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // SetUp: Create a Company object named "QuickEats"
        company.setName("QuickEats");
        
        // SetUp: Add four vehicles to the company (2 owned, 2 rented)
        List<Vehicle> vehicles = new ArrayList<>();
        
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("GHI101");
        vehicles.add(vehicle1);
        
        RentedVehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("JKL202");
        vehicles.add(vehicle2);
        
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("MNO303");
        vehicles.add(vehicle3);
        
        RentedVehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("PQR404");
        vehicles.add(vehicle4);
        
        company.setVehicles(vehicles);
        
        // Execute: Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when there are 2 rented vehicles", 2, rentedVehicleCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // SetUp: Create a Company object named "FoodExpress"
        company.setName("FoodExpress");
        
        // SetUp: Add five rented vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        
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
        
        // Execute: Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 5
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, rentedVehicleCount);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // SetUp: Create a Company object named "DailyDeliveries"
        company.setName("DailyDeliveries");
        
        // SetUp: Add six vehicles to the company (4 owned, 2 rented)
        List<Vehicle> vehicles = new ArrayList<>();
        
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("HIJ010");
        vehicles.add(vehicle1);
        
        RentedVehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("KLM111");
        vehicles.add(vehicle2);
        
        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("NOP222");
        vehicles.add(vehicle3);
        
        RentedVehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("QRS333");
        vehicles.add(vehicle4);
        
        OwnedVehicle vehicle5 = new OwnedVehicle();
        vehicle5.setRegistrationNumber("TUV444");
        vehicles.add(vehicle5);
        
        OwnedVehicle vehicle6 = new OwnedVehicle();
        vehicle6.setRegistrationNumber("WXY555");
        vehicles.add(vehicle6);
        
        company.setVehicles(vehicles);
        
        // Execute: Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when there are 2 rented vehicles mixed with owned vehicles", 2, rentedVehicleCount);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // SetUp: Create a Company object named "DeliveriesRUs"
        company.setName("DeliveriesRUs");
        
        // SetUp: Do not add any vehicles to the company (vehicles list remains empty)
        company.setVehicles(new ArrayList<Vehicle>());
        
        // Execute: Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when there are no vehicles", 0, rentedVehicleCount);
    }
}