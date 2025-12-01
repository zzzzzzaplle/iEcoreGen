import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // Create a Company object named "FastDeliveryInc"
        Company company = new Company("FastDeliveryInc");
        
        // Add three owned vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("ABC123", false)); // owned vehicle
        vehicles.add(new Vehicle("XYZ789", false)); // owned vehicle
        vehicles.add(new Vehicle("LMN456", false)); // owned vehicle
        
        company.setVehicles(vehicles);
        
        // Count rented vehicles and verify result
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 0 when all vehicles are owned", 0, rentedCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // Create a Company object named "QuickEats"
        Company company = new Company("QuickEats");
        
        // Add four vehicles to the company (two owned, two rented)
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("GHI101", false)); // owned vehicle
        vehicles.add(new Vehicle("JKL202", true));  // rented vehicle
        vehicles.add(new Vehicle("MNO303", false)); // owned vehicle
        vehicles.add(new Vehicle("PQR404", true));  // rented vehicle
        
        company.setVehicles(vehicles);
        
        // Count rented vehicles and verify result
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles", 2, rentedCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Create a Company object named "FoodExpress"
        Company company = new Company("FoodExpress");
        
        // Add five rented vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("STU505", true)); // rented vehicle
        vehicles.add(new Vehicle("VWX606", true)); // rented vehicle
        vehicles.add(new Vehicle("YZA707", true)); // rented vehicle
        vehicles.add(new Vehicle("BCD808", true)); // rented vehicle
        vehicles.add(new Vehicle("EFG909", true)); // rented vehicle
        
        company.setVehicles(vehicles);
        
        // Count rented vehicles and verify result
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, rentedCount);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // Create a Company object named "DailyDeliveries"
        Company company = new Company("DailyDeliveries");
        
        // Add six vehicles to the company (four owned, two rented)
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(new Vehicle("HIJ010", false)); // owned vehicle
        vehicles.add(new Vehicle("KLM111", true));  // rented vehicle
        vehicles.add(new Vehicle("NOP222", false)); // owned vehicle
        vehicles.add(new Vehicle("QRS333", true));  // rented vehicle
        vehicles.add(new Vehicle("TUV444", false)); // owned vehicle
        vehicles.add(new Vehicle("WXY555", false)); // owned vehicle
        
        company.setVehicles(vehicles);
        
        // Count rented vehicles and verify result
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles among mixed types", 2, rentedCount);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // Create a Company object named "DeliveriesRUs"
        Company company = new Company("DeliveriesRUs");
        
        // Do not add any vehicles to the company (vehicles list remains empty)
        
        // Count rented vehicles and verify result
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 0 when there are no vehicles", 0, rentedCount);
    }
}