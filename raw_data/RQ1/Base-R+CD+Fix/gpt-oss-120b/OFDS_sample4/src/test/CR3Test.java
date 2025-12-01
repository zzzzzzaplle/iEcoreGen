import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // SetUp: Create Company object named "FastDeliveryInc"
        Company company = new Company();
        company.setName("FastDeliveryInc");
        
        // SetUp: Add three owned vehicles
        List<Vehicle> vehicles = new ArrayList<>();
        
        OwnedVehicle owned1 = new OwnedVehicle();
        owned1.setRegistrationNumber("ABC123");
        vehicles.add(owned1);
        
        OwnedVehicle owned2 = new OwnedVehicle();
        owned2.setRegistrationNumber("XYZ789");
        vehicles.add(owned2);
        
        OwnedVehicle owned3 = new OwnedVehicle();
        owned3.setRegistrationNumber("LMN456");
        vehicles.add(owned3);
        
        company.setVehicles(vehicles);
        
        // Input: Count rented vehicles and verify expected output
        int result = company.getRentedVehicleCount();
        assertEquals("Rented vehicle count should be 0 when no rented vehicles exist", 0, result);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // SetUp: Create Company object named "QuickEats"
        Company company = new Company();
        company.setName("QuickEats");
        
        // SetUp: Add four vehicles (two owned, two rented)
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
        
        // Input: Count rented vehicles and verify expected output
        int result = company.getRentedVehicleCount();
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist", 2, result);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // SetUp: Create Company object named "FoodExpress"
        Company company = new Company();
        company.setName("FoodExpress");
        
        // SetUp: Add five rented vehicles
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
        
        // Input: Count rented vehicles and verify expected output
        int result = company.getRentedVehicleCount();
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, result);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // SetUp: Create Company object named "DailyDeliveries"
        Company company = new Company();
        company.setName("DailyDeliveries");
        
        // SetUp: Add six vehicles (four owned, two rented)
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
        
        // Input: Count rented vehicles and verify expected output
        int result = company.getRentedVehicleCount();
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist among mixed types", 2, result);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // SetUp: Create Company object named "DeliveriesRUs"
        Company company = new Company();
        company.setName("DeliveriesRUs");
        
        // SetUp: Do not add any vehicles (default empty list)
        
        // Input: Count rented vehicles and verify expected output
        int result = company.getRentedVehicleCount();
        assertEquals("Rented vehicle count should be 0 when no vehicles exist", 0, result);
    }
}