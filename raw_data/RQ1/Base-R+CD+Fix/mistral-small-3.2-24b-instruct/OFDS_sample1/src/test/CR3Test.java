import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // Create a Company object named "FastDeliveryInc"
        Company company = new Company();
        company.setName("FastDeliveryInc");
        
        // Add three owned vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        
        OwnedVehicle ownedVehicle1 = new OwnedVehicle();
        ownedVehicle1.setRegistrationNumber("ABC123");
        vehicles.add(ownedVehicle1);
        
        OwnedVehicle ownedVehicle2 = new OwnedVehicle();
        ownedVehicle2.setRegistrationNumber("XYZ789");
        vehicles.add(ownedVehicle2);
        
        OwnedVehicle ownedVehicle3 = new OwnedVehicle();
        ownedVehicle3.setRegistrationNumber("LMN456");
        vehicles.add(ownedVehicle3);
        
        company.setVehicles(vehicles);
        
        // Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when there are no rented vehicles", 0, rentedVehicleCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // Create a Company object named "QuickEats"
        Company company = new Company();
        company.setName("QuickEats");
        
        // Add four vehicles to the company with two rented vehicles
        List<Vehicle> vehicles = new ArrayList<>();
        
        OwnedVehicle ownedVehicle1 = new OwnedVehicle();
        ownedVehicle1.setRegistrationNumber("GHI101");
        vehicles.add(ownedVehicle1);
        
        RentedVehicle rentedVehicle1 = new RentedVehicle();
        rentedVehicle1.setRegistrationNumber("JKL202");
        vehicles.add(rentedVehicle1);
        
        OwnedVehicle ownedVehicle2 = new OwnedVehicle();
        ownedVehicle2.setRegistrationNumber("MNO303");
        vehicles.add(ownedVehicle2);
        
        RentedVehicle rentedVehicle2 = new RentedVehicle();
        rentedVehicle2.setRegistrationNumber("PQR404");
        vehicles.add(rentedVehicle2);
        
        company.setVehicles(vehicles);
        
        // Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles", 2, rentedVehicleCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Create a Company object named "FoodExpress"
        Company company = new Company();
        company.setName("FoodExpress");
        
        // Add five rented vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        
        RentedVehicle rentedVehicle1 = new RentedVehicle();
        rentedVehicle1.setRegistrationNumber("STU505");
        vehicles.add(rentedVehicle1);
        
        RentedVehicle rentedVehicle2 = new RentedVehicle();
        rentedVehicle2.setRegistrationNumber("VWX606");
        vehicles.add(rentedVehicle2);
        
        RentedVehicle rentedVehicle3 = new RentedVehicle();
        rentedVehicle3.setRegistrationNumber("YZA707");
        vehicles.add(rentedVehicle3);
        
        RentedVehicle rentedVehicle4 = new RentedVehicle();
        rentedVehicle4.setRegistrationNumber("BCD808");
        vehicles.add(rentedVehicle4);
        
        RentedVehicle rentedVehicle5 = new RentedVehicle();
        rentedVehicle5.setRegistrationNumber("EFG909");
        vehicles.add(rentedVehicle5);
        
        company.setVehicles(vehicles);
        
        // Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 5
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, rentedVehicleCount);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // Create a Company object named "DailyDeliveries"
        Company company = new Company();
        company.setName("DailyDeliveries");
        
        // Add six vehicles to the company with mixed types
        List<Vehicle> vehicles = new ArrayList<>();
        
        OwnedVehicle ownedVehicle1 = new OwnedVehicle();
        ownedVehicle1.setRegistrationNumber("HIJ010");
        vehicles.add(ownedVehicle1);
        
        RentedVehicle rentedVehicle1 = new RentedVehicle();
        rentedVehicle1.setRegistrationNumber("KLM111");
        vehicles.add(rentedVehicle1);
        
        OwnedVehicle ownedVehicle2 = new OwnedVehicle();
        ownedVehicle2.setRegistrationNumber("NOP222");
        vehicles.add(ownedVehicle2);
        
        RentedVehicle rentedVehicle2 = new RentedVehicle();
        rentedVehicle2.setRegistrationNumber("QRS333");
        vehicles.add(rentedVehicle2);
        
        OwnedVehicle ownedVehicle3 = new OwnedVehicle();
        ownedVehicle3.setRegistrationNumber("TUV444");
        vehicles.add(ownedVehicle3);
        
        OwnedVehicle ownedVehicle4 = new OwnedVehicle();
        ownedVehicle4.setRegistrationNumber("WXY555");
        vehicles.add(ownedVehicle4);
        
        company.setVehicles(vehicles);
        
        // Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles mixed with owned vehicles", 2, rentedVehicleCount);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // Create a Company object named "DeliveriesRUs"
        Company company = new Company();
        company.setName("DeliveriesRUs");
        
        // Do not add any vehicles to the company
        // The company already has an empty vehicles list by default
        
        // Count the number of rented vehicles in the company
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when there are no vehicles", 0, rentedVehicleCount);
    }
}