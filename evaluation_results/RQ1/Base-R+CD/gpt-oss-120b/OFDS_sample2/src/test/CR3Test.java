import org.junit.Test;
import static org.junit.Assert.*;

public class CR3Test {

    // Test Case 1: Count Rented Vehicles with No Rented Vehicles
    @Test
    public void testCase1_countRentedVehiclesWithNoRentedVehicles() {
        // Create a Company object named "FastDeliveryInc"
        Company company = new Company();
        company.setName("FastDeliveryInc");

        // Add three owned vehicles to the company
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        company.addVehicle(vehicle1);

        OwnedVehicle vehicle2 = new OwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        company.addVehicle(vehicle2);

        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        company.addVehicle(vehicle3);

        // Expected Output: Rented vehicle count = 0
        assertEquals(0, company.getRentedVehicleCount());
    }

    // Test Case 2: Count Rented Vehicles with Two Rented Vehicles
    @Test
    public void testCase2_countRentedVehiclesWithTwoRentedVehicles() {
        // Create a Company object named "QuickEats"
        Company company = new Company();
        company.setName("QuickEats");

        // Add four vehicles to the company: 2 owned and 2 rented
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("GHI101");
        company.addVehicle(vehicle1);

        RentedVehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("JKL202");
        company.addVehicle(vehicle2);

        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("MNO303");
        company.addVehicle(vehicle3);

        RentedVehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("PQR404");
        company.addVehicle(vehicle4);

        // Expected Output: Rented vehicle count = 2
        assertEquals(2, company.getRentedVehicleCount());
    }

    // Test Case 3: Count Rented Vehicles All Rented
    @Test
    public void testCase3_countRentedVehiclesAllRented() {
        // Create a Company object named "FoodExpress"
        Company company = new Company();
        company.setName("FoodExpress");

        // Add five rented vehicles to the company
        RentedVehicle vehicle1 = new RentedVehicle();
        vehicle1.setRegistrationNumber("STU505");
        company.addVehicle(vehicle1);

        RentedVehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("VWX606");
        company.addVehicle(vehicle2);

        RentedVehicle vehicle3 = new RentedVehicle();
        vehicle3.setRegistrationNumber("YZA707");
        company.addVehicle(vehicle3);

        RentedVehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("BCD808");
        company.addVehicle(vehicle4);

        RentedVehicle vehicle5 = new RentedVehicle();
        vehicle5.setRegistrationNumber("EFG909");
        company.addVehicle(vehicle5);

        // Expected Output: Rented vehicle count = 5
        assertEquals(5, company.getRentedVehicleCount());
    }

    // Test Case 4: Count Rented Vehicles with Mixed Vehicle Types
    @Test
    public void testCase4_countRentedVehiclesWithMixedVehicleTypes() {
        // Create a Company object named "DailyDeliveries"
        Company company = new Company();
        company.setName("DailyDeliveries");

        // Add six vehicles to the company: 4 owned and 2 rented
        OwnedVehicle vehicle1 = new OwnedVehicle();
        vehicle1.setRegistrationNumber("HIJ010");
        company.addVehicle(vehicle1);

        RentedVehicle vehicle2 = new RentedVehicle();
        vehicle2.setRegistrationNumber("KLM111");
        company.addVehicle(vehicle2);

        OwnedVehicle vehicle3 = new OwnedVehicle();
        vehicle3.setRegistrationNumber("NOP222");
        company.addVehicle(vehicle3);

        RentedVehicle vehicle4 = new RentedVehicle();
        vehicle4.setRegistrationNumber("QRS333");
        company.addVehicle(vehicle4);

        OwnedVehicle vehicle5 = new OwnedVehicle();
        vehicle5.setRegistrationNumber("TUV444");
        company.addVehicle(vehicle5);

        OwnedVehicle vehicle6 = new OwnedVehicle();
        vehicle6.setRegistrationNumber("WXY555");
        company.addVehicle(vehicle6);

        // Expected Output: Rented vehicle count = 2
        assertEquals(2, company.getRentedVehicleCount());
    }

    // Test Case 5: Count Rented Vehicles with No Vehicles
    @Test
    public void testCase5_countRentedVehiclesWithNoVehicles() {
        // Create a Company object named "DeliveriesRUs"
        Company company = new Company();
        company.setName("DeliveriesRUs");

        // Do not add any vehicles to the company

        // Expected Output: Rented vehicle count = 0
        assertEquals(0, company.getRentedVehicleCount());
    }
}