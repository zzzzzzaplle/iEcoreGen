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
    public void testCase1_countRentedVehiclesWithNoRentedVehicles() {
        // Test Case 1: Count Rented Vehicles with No Rented Vehicles
        // SetUp: Create a Company object named "FastDeliveryInc"
        company.setName("FastDeliveryInc");
        
        // SetUp: Add three owned vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle ownedVehicle1 = new OwnedVehicle();
        ownedVehicle1.setRegistrationNumber("ABC123");
        vehicles.add(ownedVehicle1);
        
        Vehicle ownedVehicle2 = new OwnedVehicle();
        ownedVehicle2.setRegistrationNumber("XYZ789");
        vehicles.add(ownedVehicle2);
        
        Vehicle ownedVehicle3 = new OwnedVehicle();
        ownedVehicle3.setRegistrationNumber("LMN456");
        vehicles.add(ownedVehicle3);
        
        company.setVehicles(vehicles);
        
        // Execute the method under test
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify the expected output
        assertEquals("Rented vehicle count should be 0 when there are no rented vehicles", 
                     0, rentedVehicleCount);
    }
    
    @Test
    public void testCase2_countRentedVehiclesWithTwoRentedVehicles() {
        // Test Case 2: Count Rented Vehicles with Two Rented Vehicles
        // SetUp: Create a Company object named "QuickEats"
        company.setName("QuickEats");
        
        // SetUp: Add four vehicles to the company (2 owned, 2 rented)
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle ownedVehicle1 = new OwnedVehicle();
        ownedVehicle1.setRegistrationNumber("GHI101");
        vehicles.add(ownedVehicle1);
        
        Vehicle rentedVehicle1 = new RentedVehicle();
        rentedVehicle1.setRegistrationNumber("JKL202");
        vehicles.add(rentedVehicle1);
        
        Vehicle ownedVehicle2 = new OwnedVehicle();
        ownedVehicle2.setRegistrationNumber("MNO303");
        vehicles.add(ownedVehicle2);
        
        Vehicle rentedVehicle2 = new RentedVehicle();
        rentedVehicle2.setRegistrationNumber("PQR404");
        vehicles.add(rentedVehicle2);
        
        company.setVehicles(vehicles);
        
        // Execute the method under test
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify the expected output
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles", 
                     2, rentedVehicleCount);
    }
    
    @Test
    public void testCase3_countRentedVehiclesAllRented() {
        // Test Case 3: Count Rented Vehicles All Rented
        // SetUp: Create a Company object named "FoodExpress"
        company.setName("FoodExpress");
        
        // SetUp: Add five rented vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle rentedVehicle1 = new RentedVehicle();
        rentedVehicle1.setRegistrationNumber("STU505");
        vehicles.add(rentedVehicle1);
        
        Vehicle rentedVehicle2 = new RentedVehicle();
        rentedVehicle2.setRegistrationNumber("VWX606");
        vehicles.add(rentedVehicle2);
        
        Vehicle rentedVehicle3 = new RentedVehicle();
        rentedVehicle3.setRegistrationNumber("YZA707");
        vehicles.add(rentedVehicle3);
        
        Vehicle rentedVehicle4 = new RentedVehicle();
        rentedVehicle4.setRegistrationNumber("BCD808");
        vehicles.add(rentedVehicle4);
        
        Vehicle rentedVehicle5 = new RentedVehicle();
        rentedVehicle5.setRegistrationNumber("EFG909");
        vehicles.add(rentedVehicle5);
        
        company.setVehicles(vehicles);
        
        // Execute the method under test
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify the expected output
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 
                     5, rentedVehicleCount);
    }
    
    @Test
    public void testCase4_countRentedVehiclesWithMixedVehicleTypes() {
        // Test Case 4: Count Rented Vehicles with Mixed Vehicle Types
        // SetUp: Create a Company object named "DailyDeliveries"
        company.setName("DailyDeliveries");
        
        // SetUp: Add six vehicles to the company (4 owned, 2 rented)
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle ownedVehicle1 = new OwnedVehicle();
        ownedVehicle1.setRegistrationNumber("HIJ010");
        vehicles.add(ownedVehicle1);
        
        Vehicle rentedVehicle1 = new RentedVehicle();
        rentedVehicle1.setRegistrationNumber("KLM111");
        vehicles.add(rentedVehicle1);
        
        Vehicle ownedVehicle2 = new OwnedVehicle();
        ownedVehicle2.setRegistrationNumber("NOP222");
        vehicles.add(ownedVehicle2);
        
        Vehicle rentedVehicle2 = new RentedVehicle();
        rentedVehicle2.setRegistrationNumber("QRS333");
        vehicles.add(rentedVehicle2);
        
        Vehicle ownedVehicle3 = new OwnedVehicle();
        ownedVehicle3.setRegistrationNumber("TUV444");
        vehicles.add(ownedVehicle3);
        
        Vehicle ownedVehicle4 = new OwnedVehicle();
        ownedVehicle4.setRegistrationNumber("WXY555");
        vehicles.add(ownedVehicle4);
        
        company.setVehicles(vehicles);
        
        // Execute the method under test
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify the expected output
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles among mixed types", 
                     2, rentedVehicleCount);
    }
    
    @Test
    public void testCase5_countRentedVehiclesWithNoVehicles() {
        // Test Case 5: Count Rented Vehicles with No Vehicles
        // SetUp: Create a Company object named "DeliveriesRUs"
        company.setName("DeliveriesRUs");
        
        // SetUp: Do not add any vehicles to the company
        // The company already has an empty vehicles list from the setUp method
        
        // Execute the method under test
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Verify the expected output
        assertEquals("Rented vehicle count should be 0 when there are no vehicles", 
                     0, rentedVehicleCount);
    }
}