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
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setType(VehicleType.OWNED);
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setType(VehicleType.OWNED);
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setType(VehicleType.OWNED);
        vehicles.add(vehicle3);
        
        company.setVehicles(vehicles);
        
        // Count rented vehicles and verify expected output
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 0 when no rented vehicles exist", 0, rentedCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // Create a Company object named "QuickEats"
        Company company = new Company();
        company.setName("QuickEats");
        
        // Add four vehicles to the company (two owned, two rented)
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("GHI101");
        vehicle1.setType(VehicleType.OWNED);
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL202");
        vehicle2.setType(VehicleType.RENTED);
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("MNO303");
        vehicle3.setType(VehicleType.OWNED);
        vehicles.add(vehicle3);
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("PQR404");
        vehicle4.setType(VehicleType.RENTED);
        vehicles.add(vehicle4);
        
        company.setVehicles(vehicles);
        
        // Count rented vehicles and verify expected output
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist", 2, rentedCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Create a Company object named "FoodExpress"
        Company company = new Company();
        company.setName("FoodExpress");
        
        // Add five rented vehicles to the company
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("STU505");
        vehicle1.setType(VehicleType.RENTED);
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("VWX606");
        vehicle2.setType(VehicleType.RENTED);
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("YZA707");
        vehicle3.setType(VehicleType.RENTED);
        vehicles.add(vehicle3);
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("BCD808");
        vehicle4.setType(VehicleType.RENTED);
        vehicles.add(vehicle4);
        
        Vehicle vehicle5 = new Vehicle();
        vehicle5.setRegistrationNumber("EFG909");
        vehicle5.setType(VehicleType.RENTED);
        vehicles.add(vehicle5);
        
        company.setVehicles(vehicles);
        
        // Count rented vehicles and verify expected output
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, rentedCount);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // Create a Company object named "DailyDeliveries"
        Company company = new Company();
        company.setName("DailyDeliveries");
        
        // Add six vehicles to the company (four owned, two rented)
        List<Vehicle> vehicles = new ArrayList<>();
        
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("HIJ010");
        vehicle1.setType(VehicleType.OWNED);
        vehicles.add(vehicle1);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("KLM111");
        vehicle2.setType(VehicleType.RENTED);
        vehicles.add(vehicle2);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("NOP222");
        vehicle3.setType(VehicleType.OWNED);
        vehicles.add(vehicle3);
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("QRS333");
        vehicle4.setType(VehicleType.RENTED);
        vehicles.add(vehicle4);
        
        Vehicle vehicle5 = new Vehicle();
        vehicle5.setRegistrationNumber("TUV444");
        vehicle5.setType(VehicleType.OWNED);
        vehicles.add(vehicle5);
        
        Vehicle vehicle6 = new Vehicle();
        vehicle6.setRegistrationNumber("WXY555");
        vehicle6.setType(VehicleType.OWNED);
        vehicles.add(vehicle6);
        
        company.setVehicles(vehicles);
        
        // Count rented vehicles and verify expected output
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist among mixed types", 2, rentedCount);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // Create a Company object named "DeliveriesRUs"
        Company company = new Company();
        company.setName("DeliveriesRUs");
        
        // Do not add any vehicles to the company
        company.setVehicles(new ArrayList<Vehicle>());
        
        // Count rented vehicles and verify expected output
        int rentedCount = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 0 when no vehicles exist", 0, rentedCount);
    }
}