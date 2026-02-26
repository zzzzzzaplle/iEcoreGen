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
        
        // Count rented vehicles and verify expected output
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist among mixed types", 2, rentedCount);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // Test Case 5: Count Rented Vehicles with No Vehicles
        // Create a Company object named "DeliveriesRUs"
        Company company = new Company();
        company.setName("DeliveriesRUs");
        
        // Do not add any vehicles to the company
        // Count rented vehicles and verify expected output
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 0 when no vehicles exist", 0, rentedCount);
    }
}