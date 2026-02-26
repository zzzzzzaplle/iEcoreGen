import org.junit.Test;
import static org.junit.Assert.*;

public class CR3Test {

    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // Create a Company object named "FastDeliveryInc"
        Company company = new Company("FastDeliveryInc");
        
        // Add three owned vehicles to the company
        company.addVehicle(new OwnedVehicle("ABC123"));
        company.addVehicle(new OwnedVehicle("XYZ789"));
        company.addVehicle(new OwnedVehicle("LMN456"));
        
        // Count the number of rented vehicles
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Assert that the rented vehicle count is 0
        assertEquals(0, rentedVehicleCount);
    }

    @Test
    public void testCase2_CountRentedVehiclesWithOneRentedVehicle() {
        // Create a Company object named "QuickEats"
        Company company = new Company("QuickEats");
        
        // Add four vehicles to the company: 2 owned and 2 rented
        company.addVehicle(new OwnedVehicle("GHI101"));
        company.addVehicle(new RentedVehicle("JKL202"));
        company.addVehicle(new OwnedVehicle("MNO303"));
        company.addVehicle(new RentedVehicle("PQR404"));
        
        // Count the number of rented vehicles
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Assert that the rented vehicle count is 2
        assertEquals(2, rentedVehicleCount);
    }

    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Create a Company object named "FoodExpress"
        Company company = new Company("FoodExpress");
        
        // Add five rented vehicles to the company
        company.addVehicle(new RentedVehicle("STU505"));
        company.addVehicle(new RentedVehicle("VWX606"));
        company.addVehicle(new RentedVehicle("YZA707"));
        company.addVehicle(new RentedVehicle("BCD808"));
        company.addVehicle(new RentedVehicle("EFG909"));
        
        // Count the number of rented vehicles
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Assert that the rented vehicle count is 5
        assertEquals(5, rentedVehicleCount);
    }

    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // Create a Company object named "DailyDeliveries"
        Company company = new Company("DailyDeliveries");
        
        // Add six vehicles to the company: 4 owned and 2 rented
        company.addVehicle(new OwnedVehicle("HIJ010"));
        company.addVehicle(new RentedVehicle("KLM111"));
        company.addVehicle(new OwnedVehicle("NOP222"));
        company.addVehicle(new RentedVehicle("QRS333"));
        company.addVehicle(new OwnedVehicle("TUV444"));
        company.addVehicle(new OwnedVehicle("WXY555"));
        
        // Count the number of rented vehicles
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Assert that the rented vehicle count is 2
        assertEquals(2, rentedVehicleCount);
    }

    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // Create a Company object named "DeliveriesRUs"
        Company company = new Company("DeliveriesRUs");
        
        // Do not add any vehicles to the company
        
        // Count the number of rented vehicles
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Assert that the rented vehicle count is 0
        assertEquals(0, rentedVehicleCount);
    }
}