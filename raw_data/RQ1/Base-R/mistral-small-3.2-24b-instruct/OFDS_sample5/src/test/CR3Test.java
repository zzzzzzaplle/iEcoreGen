import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    
    private Company company;
    
    @Before
    public void setUp() {
        company = new Company();
    }
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // SetUp: Create Company object named "FastDeliveryInc"
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
        
        // Count rented vehicles and verify result
        int result = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 0 when no rented vehicles exist", 0, result);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // SetUp: Create Company object named "QuickEats"
        company.setName("QuickEats");
        
        // Add four vehicles with mixed types (2 rented, 2 owned)
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
        
        // Count rented vehicles and verify result
        int result = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 2 when there are 2 rented vehicles", 2, result);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // SetUp: Create Company object named "FoodExpress"
        company.setName("FoodExpress");
        
        // Add five rented vehicles
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
        
        // Count rented vehicles and verify result
        int result = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 5 when all vehicles are rented", 5, result);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // SetUp: Create Company object named "DailyDeliveries"
        company.setName("DailyDeliveries");
        
        // Add six vehicles with mixed types (2 rented, 4 owned)
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
        
        // Count rented vehicles and verify result
        int result = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 2 when there are 2 rented vehicles among mixed types", 2, result);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // SetUp: Create Company object named "DeliveriesRUs"
        company.setName("DeliveriesRUs");
        
        // Do not add any vehicles to the company (vehicles list remains empty)
        
        // Count rented vehicles and verify result
        int result = company.countRentedVehicles();
        assertEquals("Rented vehicle count should be 0 when no vehicles exist", 0, result);
    }
}