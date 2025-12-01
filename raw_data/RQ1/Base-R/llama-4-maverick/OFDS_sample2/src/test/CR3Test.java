import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // Test Case 1: Count Rented Vehicles with No Rented Vehicles
        // Create a Company object named "FastDeliveryInc"
        Company company = new Company();
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
        
        // Count rented vehicles and verify expected output
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 0 when no rented vehicles exist", 0, rentedCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // Test Case 2: Count Rented Vehicles with Two Rented Vehicles
        // Create a Company object named "QuickEats"
        Company company = new Company();
        company.setName("QuickEats");
        
        // Add four vehicles to the company (2 owned, 2 rented)
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
        
        // Count rented vehicles and verify expected output
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist", 2, rentedCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Test Case 3: Count Rented Vehicles All Rented
        // Create a Company object named "FoodExpress"
        Company company = new Company();
        company.setName("FoodExpress");
        
        // Add five rented vehicles to the company
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
        
        // Count rented vehicles and verify expected output
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, rentedCount);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // Test Case 4: Count Rented Vehicles with Mixed Vehicle Types
        // Create a Company object named "DailyDeliveries"
        Company company = new Company();
        company.setName("DailyDeliveries");
        
        // Add six vehicles to the company (4 owned, 2 rented)
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
        
        // Count rented vehicles and verify expected output
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 2 when mixed vehicle types exist", 2, rentedCount);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // Test Case 5: Count Rented Vehicles with No Vehicles
        // Create a Company object named "DeliveriesRUs"
        Company company = new Company();
        company.setName("DeliveriesRUs");
        
        // Do not add any vehicles to the company (vehicles list remains empty)
        
        // Count rented vehicles and verify expected output
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 0 when no vehicles exist", 0, rentedCount);
    }
}