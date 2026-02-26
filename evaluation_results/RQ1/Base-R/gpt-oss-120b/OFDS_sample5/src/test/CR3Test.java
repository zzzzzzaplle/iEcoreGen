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
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("ABC123");
        vehicle1.setType(VehicleType.OWNED);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        vehicle2.setType(VehicleType.OWNED);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("LMN456");
        vehicle3.setType(VehicleType.OWNED);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        company.setVehicles(vehicles);
        
        // Expected Output: Rented vehicle count = 0
        int result = company.getRentedVehicleCount();
        assertEquals("Should return 0 when no rented vehicles exist", 0, result);
    }

    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // SetUp: Create a Company object named "QuickEats"
        company.setName("QuickEats");
        
        // Add four vehicles to the company (2 owned, 2 rented)
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("GHI101");
        vehicle1.setType(VehicleType.OWNED);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("JKL202");
        vehicle2.setType(VehicleType.RENTED);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("MNO303");
        vehicle3.setType(VehicleType.OWNED);
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("PQR404");
        vehicle4.setType(VehicleType.RENTED);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        vehicles.add(vehicle4);
        company.setVehicles(vehicles);
        
        // Expected Output: Rented vehicle count = 2
        int result = company.getRentedVehicleCount();
        assertEquals("Should return 2 when there are 2 rented vehicles", 2, result);
    }

    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // SetUp: Create a Company object named "FoodExpress"
        company.setName("FoodExpress");
        
        // Add five rented vehicles to the company
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("STU505");
        vehicle1.setType(VehicleType.RENTED);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("VWX606");
        vehicle2.setType(VehicleType.RENTED);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("YZA707");
        vehicle3.setType(VehicleType.RENTED);
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("BCD808");
        vehicle4.setType(VehicleType.RENTED);
        
        Vehicle vehicle5 = new Vehicle();
        vehicle5.setRegistrationNumber("EFG909");
        vehicle5.setType(VehicleType.RENTED);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        vehicles.add(vehicle4);
        vehicles.add(vehicle5);
        company.setVehicles(vehicles);
        
        // Expected Output: Rented vehicle count = 5
        int result = company.getRentedVehicleCount();
        assertEquals("Should return 5 when all vehicles are rented", 5, result);
    }

    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // SetUp: Create a Company object named "DailyDeliveries"
        company.setName("DailyDeliveries");
        
        // Add six vehicles to the company (4 owned, 2 rented)
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setRegistrationNumber("HIJ010");
        vehicle1.setType(VehicleType.OWNED);
        
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setRegistrationNumber("KLM111");
        vehicle2.setType(VehicleType.RENTED);
        
        Vehicle vehicle3 = new Vehicle();
        vehicle3.setRegistrationNumber("NOP222");
        vehicle3.setType(VehicleType.OWNED);
        
        Vehicle vehicle4 = new Vehicle();
        vehicle4.setRegistrationNumber("QRS333");
        vehicle4.setType(VehicleType.RENTED);
        
        Vehicle vehicle5 = new Vehicle();
        vehicle5.setRegistrationNumber("TUV444");
        vehicle5.setType(VehicleType.OWNED);
        
        Vehicle vehicle6 = new Vehicle();
        vehicle6.setRegistrationNumber("WXY555");
        vehicle6.setType(VehicleType.OWNED);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(vehicle1);
        vehicles.add(vehicle2);
        vehicles.add(vehicle3);
        vehicles.add(vehicle4);
        vehicles.add(vehicle5);
        vehicles.add(vehicle6);
        company.setVehicles(vehicles);
        
        // Expected Output: Rented vehicle count = 2
        int result = company.getRentedVehicleCount();
        assertEquals("Should return 2 when there are 2 rented vehicles among mixed types", 2, result);
    }

    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // SetUp: Create a Company object named "DeliveriesRUs"
        company.setName("DeliveriesRUs");
        
        // Do not add any vehicles to the company
        company.setVehicles(new ArrayList<Vehicle>());
        
        // Expected Output: Rented vehicle count = 0
        int result = company.getRentedVehicleCount();
        assertEquals("Should return 0 when no vehicles exist", 0, result);
    }
}