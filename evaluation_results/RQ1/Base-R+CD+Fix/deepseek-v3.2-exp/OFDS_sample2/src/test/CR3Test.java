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
        assertEquals("Rented vehicle count should be 0 when no rented vehicles exist", 
                     0, rentedVehicleCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // SetUp: Create a Company object named "QuickEats"
        company.setName("QuickEats");
        
        // Add four vehicles to the company (2 owned, 2 rented)
        List<Vehicle> vehicles = new ArrayList<>();
        
        OwnedVehicle owned1 = new OwnedVehicle();
        owned1.setRegistrationNumber("GHI101");
        vehicles.add(owned1);
        
        RentedVehicle rented1 = new RentedVehicle();
        rented1.setRegistrationNumber("JKL202");
        vehicles.add(rented1);
        
        OwnedVehicle owned2 = new OwnedVehicle();
        owned2.setRegistrationNumber("MNO303");
        vehicles.add(owned2);
        
        RentedVehicle rented2 = new RentedVehicle();
        rented2.setRegistrationNumber("PQR404");
        vehicles.add(rented2);
        
        company.setVehicles(vehicles);
        
        // Execute: Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist", 
                     2, rentedVehicleCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // SetUp: Create a Company object named "FoodExpress"
        company.setName("FoodExpress");
        
        // Add five rented vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        
        RentedVehicle rented1 = new RentedVehicle();
        rented1.setRegistrationNumber("STU505");
        vehicles.add(rented1);
        
        RentedVehicle rented2 = new RentedVehicle();
        rented2.setRegistrationNumber("VWX606");
        vehicles.add(rented2);
        
        RentedVehicle rented3 = new RentedVehicle();
        rented3.setRegistrationNumber("YZA707");
        vehicles.add(rented3);
        
        RentedVehicle rented4 = new RentedVehicle();
        rented4.setRegistrationNumber("BCD808");
        vehicles.add(rented4);
        
        RentedVehicle rented5 = new RentedVehicle();
        rented5.setRegistrationNumber("EFG909");
        vehicles.add(rented5);
        
        company.setVehicles(vehicles);
        
        // Execute: Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 5
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 
                     5, rentedVehicleCount);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // SetUp: Create a Company object named "DailyDeliveries"
        company.setName("DailyDeliveries");
        
        // Add six vehicles to the company (4 owned, 2 rented)
        List<Vehicle> vehicles = new ArrayList<>();
        
        OwnedVehicle owned1 = new OwnedVehicle();
        owned1.setRegistrationNumber("HIJ010");
        vehicles.add(owned1);
        
        RentedVehicle rented1 = new RentedVehicle();
        rented1.setRegistrationNumber("KLM111");
        vehicles.add(rented1);
        
        OwnedVehicle owned2 = new OwnedVehicle();
        owned2.setRegistrationNumber("NOP222");
        vehicles.add(owned2);
        
        RentedVehicle rented2 = new RentedVehicle();
        rented2.setRegistrationNumber("QRS333");
        vehicles.add(rented2);
        
        OwnedVehicle owned3 = new OwnedVehicle();
        owned3.setRegistrationNumber("TUV444");
        vehicles.add(owned3);
        
        OwnedVehicle owned4 = new OwnedVehicle();
        owned4.setRegistrationNumber("WXY555");
        vehicles.add(owned4);
        
        company.setVehicles(vehicles);
        
        // Execute: Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist among mixed types", 
                     2, rentedVehicleCount);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // SetUp: Create a Company object named "DeliveriesRUs"
        company.setName("DeliveriesRUs");
        
        // Do not add any vehicles to the company (vehicles list remains empty)
        
        // Execute: Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify: Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when no vehicles exist", 
                     0, rentedVehicleCount);
    }
}