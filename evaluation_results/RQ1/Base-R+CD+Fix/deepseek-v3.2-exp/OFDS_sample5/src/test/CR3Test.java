import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // Test Case 1: Count Rented Vehicles with No Rented Vehicles
        // SetUp:
        // 1. Create a Company object named "FastDeliveryInc"
        Company company = new Company();
        company.setName("FastDeliveryInc");
        
        // 2. Add three vehicles to the company:
        List<Vehicle> vehicles = new ArrayList<>();
        
        // - A owned vehicle with registration number "ABC123"
        OwnedVehicle owned1 = new OwnedVehicle();
        owned1.setRegistrationNumber("ABC123");
        vehicles.add(owned1);
        
        // - A owned vehicle with registration number "XYZ789"
        OwnedVehicle owned2 = new OwnedVehicle();
        owned2.setRegistrationNumber("XYZ789");
        vehicles.add(owned2);
        
        // - A owned vehicle with registration number "LMN456"
        OwnedVehicle owned3 = new OwnedVehicle();
        owned3.setRegistrationNumber("LMN456");
        vehicles.add(owned3);
        
        company.setVehicles(vehicles);
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when no rented vehicles", 0, company.getRentedVehicleCount());
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // Test Case 2: Count Rented Vehicles with Two Rented Vehicles
        // SetUp:
        // 1. Create a Company object named "QuickEats"
        Company company = new Company();
        company.setName("QuickEats");
        
        // 2. Add four vehicles to the company:
        List<Vehicle> vehicles = new ArrayList<>();
        
        // - A owned vehicle with registration number "GHI101"
        OwnedVehicle owned1 = new OwnedVehicle();
        owned1.setRegistrationNumber("GHI101");
        vehicles.add(owned1);
        
        // - A rented vehicle with registration number "JKL202"
        RentedVehicle rented1 = new RentedVehicle();
        rented1.setRegistrationNumber("JKL202");
        vehicles.add(rented1);
        
        // - A owned vehicle with registration number "MNO303"
        OwnedVehicle owned2 = new OwnedVehicle();
        owned2.setRegistrationNumber("MNO303");
        vehicles.add(owned2);
        
        // - A rented vehicle with registration number "PQR404"
        RentedVehicle rented2 = new RentedVehicle();
        rented2.setRegistrationNumber("PQR404");
        vehicles.add(rented2);
        
        company.setVehicles(vehicles);
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist", 2, company.getRentedVehicleCount());
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Test Case 3: Count Rented Vehicles All Rented
        // SetUp:
        // 1. Create a Company object named "FoodExpress"
        Company company = new Company();
        company.setName("FoodExpress");
        
        // 2. Add five vehicles to the company:
        List<Vehicle> vehicles = new ArrayList<>();
        
        // - A rented vehicle with registration number "STU505"
        RentedVehicle rented1 = new RentedVehicle();
        rented1.setRegistrationNumber("STU505");
        vehicles.add(rented1);
        
        // - A rented vehicle with registration number "VWX606"
        RentedVehicle rented2 = new RentedVehicle();
        rented2.setRegistrationNumber("VWX606");
        vehicles.add(rented2);
        
        // - A rented vehicle with registration number "YZA707"
        RentedVehicle rented3 = new RentedVehicle();
        rented3.setRegistrationNumber("YZA707");
        vehicles.add(rented3);
        
        // - A rented vehicle with registration number "BCD808"
        RentedVehicle rented4 = new RentedVehicle();
        rented4.setRegistrationNumber("BCD808");
        vehicles.add(rented4);
        
        // - A rented vehicle with registration number "EFG909"
        RentedVehicle rented5 = new RentedVehicle();
        rented5.setRegistrationNumber("EFG909");
        vehicles.add(rented5);
        
        company.setVehicles(vehicles);
        
        // Expected Output: Rented vehicle count = 5
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, company.getRentedVehicleCount());
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // Test Case 4: Count Rented Vehicles with Mixed Vehicle Types
        // SetUp:
        // 1. Create a Company object named "DailyDeliveries"
        Company company = new Company();
        company.setName("DailyDeliveries");
        
        // 2. Add six vehicles to the company:
        List<Vehicle> vehicles = new ArrayList<>();
        
        // - A owned vehicle with registration number "HIJ010"
        OwnedVehicle owned1 = new OwnedVehicle();
        owned1.setRegistrationNumber("HIJ010");
        vehicles.add(owned1);
        
        // - A rented vehicle with registration number "KLM111"
        RentedVehicle rented1 = new RentedVehicle();
        rented1.setRegistrationNumber("KLM111");
        vehicles.add(rented1);
        
        // - A owned vehicle with registration number "NOP222"
        OwnedVehicle owned2 = new OwnedVehicle();
        owned2.setRegistrationNumber("NOP222");
        vehicles.add(owned2);
        
        // - A rented vehicle with registration number "QRS333"
        RentedVehicle rented2 = new RentedVehicle();
        rented2.setRegistrationNumber("QRS333");
        vehicles.add(rented2);
        
        // - A owned vehicle with registration number "TUV444"
        OwnedVehicle owned3 = new OwnedVehicle();
        owned3.setRegistrationNumber("TUV444");
        vehicles.add(owned3);
        
        // - A owned vehicle with registration number "WXY555"
        OwnedVehicle owned4 = new OwnedVehicle();
        owned4.setRegistrationNumber("WXY555");
        vehicles.add(owned4);
        
        company.setVehicles(vehicles);
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when two rented vehicles exist among mixed types", 2, company.getRentedVehicleCount());
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // Test Case 5: Count Rented Vehicles with No Vehicles
        // SetUp:
        // 1. Create a Company object named "DeliveriesRUs"
        Company company = new Company();
        company.setName("DeliveriesRUs");
        
        // 2. Do not add any vehicles to the company
        // (Company already has empty vehicles list by default)
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when no vehicles exist", 0, company.getRentedVehicleCount());
    }
}