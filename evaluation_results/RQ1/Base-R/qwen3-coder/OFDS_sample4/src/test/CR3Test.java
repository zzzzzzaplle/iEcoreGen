import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Company company;
    
    /**
     * Set up method to initialize common test objects
     */
    @Before
    public void setUp() {
        company = new Company();
    }
    
    /**
     * Test Case 1: Count Rented Vehicles with No Rented Vehicles
     * Expected Output: Rented vehicle count = 0
     */
    @Test
    public void testCase1_countRentedVehiclesWithNoRentedVehicles() {
        // SetUp: Create a Company object named "FastDeliveryInc"
        company.setName("FastDeliveryInc");
        
        // SetUp: Add three owned vehicles to the company
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setType("owned");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setType("owned");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setType("owned");
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        
        // Execute: Count the number of rented vehicles
        int result = company.countRentedVehicles();
        
        // Verify: Rented vehicle count should be 0
        assertEquals("Rented vehicle count should be 0 when there are no rented vehicles", 0, result);
    }
    
    /**
     * Test Case 2: Count Rented Vehicles with Two Rented Vehicles
     * Expected Output: Rented vehicle count = 2
     */
    @Test
    public void testCase2_countRentedVehiclesWithTwoRentedVehicles() {
        // SetUp: Create a Company object named "QuickEats"
        company.setName("QuickEats");
        
        // SetUp: Add four vehicles to the company (two owned, two rented)
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("GHI101");
        vehicle1.setType("owned");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL202");
        vehicle2.setType("rented");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("MNO303");
        vehicle3.setType("owned");
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("PQR404");
        vehicle4.setType("rented");
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        company.addVehicle(vehicle4);
        
        // Execute: Count the number of rented vehicles
        int result = company.countRentedVehicles();
        
        // Verify: Rented vehicle count should be 2
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles", 2, result);
    }
    
    /**
     * Test Case 3: Count Rented Vehicles All Rented
     * Expected Output: Rented vehicle count = 5
     */
    @Test
    public void testCase3_countRentedVehiclesAllRented() {
        // SetUp: Create a Company object named "FoodExpress"
        company.setName("FoodExpress");
        
        // SetUp: Add five rented vehicles to the company
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("STU505");
        vehicle1.setType("rented");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("VWX606");
        vehicle2.setType("rented");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("YZA707");
        vehicle3.setType("rented");
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("BCD808");
        vehicle4.setType("rented");
        
        Vehicle vehicle5 = new Vehicle();
        vehicle5.setRegistrationNumber("EFG909");
        vehicle5.setType("rented");
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        company.addVehicle(vehicle4);
        company.addVehicle(vehicle5);
        
        // Execute: Count the number of rented vehicles
        int result = company.countRentedVehicles();
        
        // Verify: Rented vehicle count should be 5
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, result);
    }
    
    /**
     * Test Case 4: Count Rented Vehicles with Mixed Vehicle Types
     * Expected Output: Rented vehicle count = 2
     */
    @Test
    public void testCase4_countRentedVehiclesWithMixedVehicleTypes() {
        // SetUp: Create a Company object named "DailyDeliveries"
        company.setName("DailyDeliveries");
        
        // SetUp: Add six vehicles to the company (four owned, two rented)
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("HIJ010");
        vehicle1.setType("owned");
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("KLM111");
        vehicle2.setType("rented");
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("NOP222");
        vehicle3.setType("owned");
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("QRS333");
        vehicle4.setType("rented");
        
        Vehicle vehicle5 = new Vehicle();
        vehicle5.setRegistrationNumber("TUV444");
        vehicle5.setType("owned");
        
        Vehicle vehicle6 = new Vehicle();
        vehicle6.setRegistrationNumber("WXY555");
        vehicle6.setType("owned");
        
        company.addVehicle(vehicle1);
        company.addVehicle(vehicle2);
        company.addVehicle(vehicle3);
        company.addVehicle(vehicle4);
        company.addVehicle(vehicle5);
        company.addVehicle(vehicle6);
        
        // Execute: Count the number of rented vehicles
        int result = company.countRentedVehicles();
        
        // Verify: Rented vehicle count should be 2
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles among mixed types", 2, result);
    }
    
    /**
     * Test Case 5: Count Rented Vehicles with No Vehicles
     * Expected Output: Rented vehicle count = 0
     */
    @Test
    public void testCase5_countRentedVehiclesWithNoVehicles() {
        // SetUp: Create a Company object named "DeliveriesRUs"
        company.setName("DeliveriesRUs");
        
        // SetUp: Do not add any vehicles to the company
        
        // Execute: Count the number of rented vehicles
        int result = company.countRentedVehicles();
        
        // Verify: Rented vehicle count should be 0
        assertEquals("Rented vehicle count should be 0 when there are no vehicles", 0, result);
    }
}