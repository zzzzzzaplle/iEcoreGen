package edu.fleet.fleet5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.fleet.FleetFactory;
import edu.fleet.FleetPackage;
import edu.fleet.Company;
import edu.fleet.Vehicle;
import edu.fleet.OwnedVehicle;
import edu.fleet.RentedVehicle;
import org.eclipse.emf.common.util.EList;

public class CR3Test {
    
    private FleetFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = FleetFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // Test Case 1: Count Rented Vehicles with No Rented Vehicles
        // Input: Count the number of rented vehicles in the company.
        
        // SetUp:
        // 1. Create a Company object named "FastDeliveryInc"
        Company company = factory.createCompany();
        company.setName("FastDeliveryInc");
        
        // 2. Add three owned vehicles to the company
        OwnedVehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        company.getVehicles().add(vehicle1);
        
        OwnedVehicle vehicle2 = factory.createOwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        company.getVehicles().add(vehicle2);
        
        OwnedVehicle vehicle3 = factory.createOwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        company.getVehicles().add(vehicle3);
        
        // Expected Output: Rented vehicle count = 0
        int result = company.getRentedVehicleCount();
        assertEquals("Should return 0 when no rented vehicles present", 0, result);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // Test Case 2: Count Rented Vehicles with Two Rented Vehicles
        // Input: Count the number of rented vehicles in the company.
        
        // SetUp:
        // 1. Create a Company object named "QuickEats"
        Company company = factory.createCompany();
        company.setName("QuickEats");
        
        // 2. Add four vehicles to the company (2 owned, 2 rented)
        OwnedVehicle owned1 = factory.createOwnedVehicle();
        owned1.setRegistrationNumber("GHI101");
        company.getVehicles().add(owned1);
        
        RentedVehicle rented1 = factory.createRentedVehicle();
        rented1.setRegistrationNumber("JKL202");
        company.getVehicles().add(rented1);
        
        OwnedVehicle owned2 = factory.createOwnedVehicle();
        owned2.setRegistrationNumber("MNO303");
        company.getVehicles().add(owned2);
        
        RentedVehicle rented2 = factory.createRentedVehicle();
        rented2.setRegistrationNumber("PQR404");
        company.getVehicles().add(rented2);
        
        // Expected Output: Rented vehicle count = 2
        int result = company.getRentedVehicleCount();
        assertEquals("Should return 2 when two rented vehicles present", 2, result);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Test Case 3: Count Rented Vehicles All Rented
        // Input: Count the number of rented vehicles in the company.
        
        // SetUp:
        // 1. Create a Company object named "FoodExpress"
        Company company = factory.createCompany();
        company.setName("FoodExpress");
        
        // 2. Add five rented vehicles to the company
        RentedVehicle vehicle1 = factory.createRentedVehicle();
        vehicle1.setRegistrationNumber("STU505");
        company.getVehicles().add(vehicle1);
        
        RentedVehicle vehicle2 = factory.createRentedVehicle();
        vehicle2.setRegistrationNumber("VWX606");
        company.getVehicles().add(vehicle2);
        
        RentedVehicle vehicle3 = factory.createRentedVehicle();
        vehicle3.setRegistrationNumber("YZA707");
        company.getVehicles().add(vehicle3);
        
        RentedVehicle vehicle4 = factory.createRentedVehicle();
        vehicle4.setRegistrationNumber("BCD808");
        company.getVehicles().add(vehicle4);
        
        RentedVehicle vehicle5 = factory.createRentedVehicle();
        vehicle5.setRegistrationNumber("EFG909");
        company.getVehicles().add(vehicle5);
        
        // Expected Output: Rented vehicle count = 5
        int result = company.getRentedVehicleCount();
        assertEquals("Should return 5 when all vehicles are rented", 5, result);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // Test Case 4: Count Rented Vehicles with Mixed Vehicle Types
        // Input: Count the number of rented vehicles in the company.
        
        // SetUp:
        // 1. Create a Company object named "DailyDeliveries"
        Company company = factory.createCompany();
        company.setName("DailyDeliveries");
        
        // 2. Add six vehicles to the company (4 owned, 2 rented)
        OwnedVehicle owned1 = factory.createOwnedVehicle();
        owned1.setRegistrationNumber("HIJ010");
        company.getVehicles().add(owned1);
        
        RentedVehicle rented1 = factory.createRentedVehicle();
        rented1.setRegistrationNumber("KLM111");
        company.getVehicles().add(rented1);
        
        OwnedVehicle owned2 = factory.createOwnedVehicle();
        owned2.setRegistrationNumber("NOP222");
        company.getVehicles().add(owned2);
        
        RentedVehicle rented2 = factory.createRentedVehicle();
        rented2.setRegistrationNumber("QRS333");
        company.getVehicles().add(rented2);
        
        OwnedVehicle owned3 = factory.createOwnedVehicle();
        owned3.setRegistrationNumber("TUV444");
        company.getVehicles().add(owned3);
        
        OwnedVehicle owned4 = factory.createOwnedVehicle();
        owned4.setRegistrationNumber("WXY555");
        company.getVehicles().add(owned4);
        
        // Expected Output: Rented vehicle count = 2
        int result = company.getRentedVehicleCount();
        assertEquals("Should return 2 when two rented vehicles among mixed types", 2, result);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // Test Case 5: Count Rented Vehicles with No Vehicles
        // Input: Count the number of rented vehicles in the company.
        
        // SetUp:
        // 1. Create a Company object named "DeliveriesRUs"
        Company company = factory.createCompany();
        company.setName("DeliveriesRUs");
        
        // 2. Do not add any vehicles to the company
        
        // Expected Output: Rented vehicle count = 0
        int result = company.getRentedVehicleCount();
        assertEquals("Should return 0 when no vehicles present", 0, result);
        
        // Verify that vehicles list is empty
        EList<Vehicle> vehicles = company.getVehicles();
        assertTrue("Vehicles list should be empty", vehicles.isEmpty());
    }
}