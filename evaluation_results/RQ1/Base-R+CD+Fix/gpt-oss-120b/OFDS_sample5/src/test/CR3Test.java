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
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // SetUp: Create a Company object named "FastDeliveryInc"
        company.setName("FastDeliveryInc");
        
        // Add three owned vehicles to the company
        company.addVehicle(new OwnedVehicle("ABC123"));
        company.addVehicle(new OwnedVehicle("XYZ789"));
        company.addVehicle(new OwnedVehicle("LMN456"));
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when there are no rented vehicles", 
                         0, company.getRentedVehicleCount());
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // SetUp: Create a Company object named "QuickEats"
        company.setName("QuickEats");
        
        // Add four vehicles to the company: 2 owned and 2 rented
        company.addVehicle(new OwnedVehicle("GHI101"));
        company.addVehicle(new RentedVehicle("JKL202"));
        company.addVehicle(new OwnedVehicle("MNO303"));
        company.addVehicle(new RentedVehicle("PQR404"));
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles", 
                         2, company.getRentedVehicleCount());
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // SetUp: Create a Company object named "FoodExpress"
        company.setName("FoodExpress");
        
        // Add five rented vehicles to the company
        company.addVehicle(new RentedVehicle("STU505"));
        company.addVehicle(new RentedVehicle("VWX606"));
        company.addVehicle(new RentedVehicle("YZA707"));
        company.addVehicle(new RentedVehicle("BCD808"));
        company.addVehicle(new RentedVehicle("EFG909"));
        
        // Expected Output: Rented vehicle count = 5
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 
                         5, company.getRentedVehicleCount());
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // SetUp: Create a Company object named "DailyDeliveries"
        company.setName("DailyDeliveries");
        
        // Add six vehicles to the company: 4 owned and 2 rented
        company.addVehicle(new OwnedVehicle("HIJ010"));
        company.addVehicle(new RentedVehicle("KLM111"));
        company.addVehicle(new OwnedVehicle("NOP222"));
        company.addVehicle(new RentedVehicle("QRS333"));
        company.addVehicle(new OwnedVehicle("TUV444"));
        company.addVehicle(new OwnedVehicle("WXY555"));
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Rented vehicle count should be 2 when there are two rented vehicles among mixed types", 
                         2, company.getRentedVehicleCount());
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // SetUp: Create a Company object named "DeliveriesRUs"
        company.setName("DeliveriesRUs");
        
        // Do not add any vehicles to the company
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Rented vehicle count should be 0 when there are no vehicles", 
                         0, company.getRentedVehicleCount());
    }
}