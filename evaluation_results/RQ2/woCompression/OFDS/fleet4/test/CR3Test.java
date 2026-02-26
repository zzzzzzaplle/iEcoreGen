package edu.fleet.fleet4.test;

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
        
        // 2. Add three vehicles to the company (all owned vehicles)
        Vehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        company.getVehicles().add(vehicle1);
        
        Vehicle vehicle2 = factory.createOwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        company.getVehicles().add(vehicle2);
        
        Vehicle vehicle3 = factory.createOwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        company.getVehicles().add(vehicle3);
        
        // Execute the operation and verify expected output
        int rentedCount = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Company with only owned vehicles should have 0 rented vehicles", 0, rentedCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithTwoRentedVehicles() {
        // Test Case 2: Count Rented Vehicles with Two Rented Vehicles
        // Input: Count the number of rented vehicles in the company.
        
        // SetUp:
        // 1. Create a Company object named "QuickEats"
        Company company = factory.createCompany();
        company.setName("QuickEats");
        
        // 2. Add four vehicles to the company (mix of owned and rented)
        Vehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("GHI101");
        company.getVehicles().add(vehicle1);
        
        Vehicle vehicle2 = factory.createRentedVehicle();
        vehicle2.setRegistrationNumber("JKL202");
        company.getVehicles().add(vehicle2);
        
        Vehicle vehicle3 = factory.createOwnedVehicle();
        vehicle3.setRegistrationNumber("MNO303");
        company.getVehicles().add(vehicle3);
        
        Vehicle vehicle4 = factory.createRentedVehicle();
        vehicle4.setRegistrationNumber("PQR404");
        company.getVehicles().add(vehicle4);
        
        // Execute the operation and verify expected output
        int rentedCount = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Company with 2 rented vehicles should return count of 2", 2, rentedCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Test Case 3: Count Rented Vehicles All Rented
        // Input: Count the number of rented vehicles in the company.
        
        // SetUp:
        // 1. Create a Company object named "FoodExpress"
        Company company = factory.createCompany();
        company.setName("FoodExpress");
        
        // 2. Add five vehicles to the company (all rented vehicles)
        Vehicle vehicle1 = factory.createRentedVehicle();
        vehicle1.setRegistrationNumber("STU505");
        company.getVehicles().add(vehicle1);
        
        Vehicle vehicle2 = factory.createRentedVehicle();
        vehicle2.setRegistrationNumber("VWX606");
        company.getVehicles().add(vehicle2);
        
        Vehicle vehicle3 = factory.createRentedVehicle();
        vehicle3.setRegistrationNumber("YZA707");
        company.getVehicles().add(vehicle3);
        
        Vehicle vehicle4 = factory.createRentedVehicle();
        vehicle4.setRegistrationNumber("BCD808");
        company.getVehicles().add(vehicle4);
        
        Vehicle vehicle5 = factory.createRentedVehicle();
        vehicle5.setRegistrationNumber("EFG909");
        company.getVehicles().add(vehicle5);
        
        // Execute the operation and verify expected output
        int rentedCount = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 5
        assertEquals("Company with all rented vehicles should return count of 5", 5, rentedCount);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // Test Case 4: Count Rented Vehicles with Mixed Vehicle Types
        // Input: Count the number of rented vehicles in the company.
        
        // SetUp:
        // 1. Create a Company object named "DailyDeliveries"
        Company company = factory.createCompany();
        company.setName("DailyDeliveries");
        
        // 2. Add six vehicles to the company (mix of owned and rented)
        Vehicle vehicle1 = factory.createOwnedVehicle();
        vehicle1.setRegistrationNumber("HIJ010");
        company.getVehicles().add(vehicle1);
        
        Vehicle vehicle2 = factory.createRentedVehicle();
        vehicle2.setRegistrationNumber("KLM111");
        company.getVehicles().add(vehicle2);
        
        Vehicle vehicle3 = factory.createOwnedVehicle();
        vehicle3.setRegistrationNumber("NOP222");
        company.getVehicles().add(vehicle3);
        
        Vehicle vehicle4 = factory.createRentedVehicle();
        vehicle4.setRegistrationNumber("QRS333");
        company.getVehicles().add(vehicle4);
        
        Vehicle vehicle5 = factory.createOwnedVehicle();
        vehicle5.setRegistrationNumber("TUV444");
        company.getVehicles().add(vehicle5);
        
        Vehicle vehicle6 = factory.createOwnedVehicle();
        vehicle6.setRegistrationNumber("WXY555");
        company.getVehicles().add(vehicle6);
        
        // Execute the operation and verify expected output
        int rentedCount = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 2
        assertEquals("Company with 2 rented vehicles out of 6 total should return count of 2", 2, rentedCount);
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
        // (vehicles list remains empty)
        
        // Execute the operation and verify expected output
        int rentedCount = company.getRentedVehicleCount();
        
        // Expected Output: Rented vehicle count = 0
        assertEquals("Company with no vehicles should return rented count of 0", 0, rentedCount);
        
        // Additional verification that vehicles list is indeed empty
        assertTrue("Vehicles list should be empty", company.getVehicles().isEmpty());
    }
}