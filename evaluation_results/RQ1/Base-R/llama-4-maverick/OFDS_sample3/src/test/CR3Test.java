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
        
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicles.add(vehicle3);
        
        company.setVehicles(vehicles);
        
        // Count the number of rented vehicles in the company
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when no rented vehicles exist", 0, rentedCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // Create a Company object named "QuickEats"
        Company company = new Company();
        company.setName("QuickEats");
        
        // Add four vehicles to the company (two owned, two rented)
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("GHI101");
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("JKL202");
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("MNO303");
        vehicles.add(vehicle3);
        
        Vehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("PQR404");
        vehicles.add(vehicle4);
        
        company.setVehicles(vehicles);
        
        // Count the number of rented vehicles in the company
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist", 2, rentedCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Create a Company object named "FoodExpress"
        Company company = new Company();
        company.setName("FoodExpress");
        
        // Add five rented vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new RentedVehicle();
        vehicle1.setRegistrationNumber("STU505");
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("VWX606");
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new RentedVehicle();
        vehicle3.setRegistrationNumber("YZA707");
        vehicles.add(vehicle3);
        
        Vehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("BCD808");
        vehicles.add(vehicle4);
        
        Vehicle vehicle5 = new RentedVehicle();
        vehicle5.setRegistrationNumber("EFG909");
        vehicles.add(vehicle5);
        
        company.setVehicles(vehicles);
        
        // Count the number of rented vehicles in the company
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 5
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, rentedCount);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // Create a Company object named "DailyDeliveries"
        Company company = new Company();
        company.setName("DailyDeliveries");
        
        // Add six vehicles to the company (four owned, two rented)
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("HIJ010");
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("KLM111");
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("NOP222");
        vehicles.add(vehicle3);
        
        Vehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("QRS333");
        vehicles.add(vehicle4);
        
        Vehicle vehicle5 = new OwnedVehicle();
        vehicle5.setRegistrationNumber("TUV444");
        vehicles.add(vehicle5);
        
        Vehicle vehicle6 = new OwnedVehicle();
        vehicle6.setRegistrationNumber("WXY555");
        vehicles.add(vehicle6);
        
        company.setVehicles(vehicles);
        
        // Count the number of rented vehicles in the company
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist among mixed types", 2, rentedCount);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // Create a Company object named "DeliveriesRUs"
        Company company = new Company();
        company.setName("DeliveriesRUs");
        
        // Do not add any vehicles to the company
        // The company already has an empty vehicles list by default
        
        // Count the number of rented vehicles in the company
        int rentedCount = company.countRentedVehicles();
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when no vehicles exist", 0, rentedCount);
    }
}