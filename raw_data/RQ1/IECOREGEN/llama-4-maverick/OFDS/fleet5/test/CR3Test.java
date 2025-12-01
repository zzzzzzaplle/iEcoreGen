package edu.fleet.fleet5.test;

import org.junit.Test;
import static org.junit.Assert.*;

import edu.fleet.Company;
import edu.fleet.FleetFactory;
import edu.fleet.OwnedVehicle;
import edu.fleet.RentedVehicle;

public class CR3Test {
    
    private FleetFactory factory;
    
    @Test
    public void testCase1_CountRentedVehiclesWithNoRentedVehicles() {
        // Create a Company object named "FastDeliveryInc"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("FastDeliveryInc");
        
        // Add three owned vehicles to the company
        OwnedVehicle vehicle1 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle1.setRegistrationNumber("ABC123");
        company.getVehicles().add(vehicle1);
        
        OwnedVehicle vehicle2 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle2.setRegistrationNumber("XYZ789");
        company.getVehicles().add(vehicle2);
        
        OwnedVehicle vehicle3 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle3.setRegistrationNumber("LMN456");
        company.getVehicles().add(vehicle3);
        
        // Count rented vehicles
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Assert that the rented vehicle count is 0
        assertEquals(0, rentedVehicleCount);
    }
    
    @Test
    public void testCase2_CountRentedVehiclesWithOneRentedVehicle() {
        // Create a Company object named "QuickEats"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("QuickEats");
        
        // Add four vehicles to the company (2 owned, 2 rented)
        OwnedVehicle vehicle1 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle1.setRegistrationNumber("GHI101");
        company.getVehicles().add(vehicle1);
        
        RentedVehicle vehicle2 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle2.setRegistrationNumber("JKL202");
        company.getVehicles().add(vehicle2);
        
        OwnedVehicle vehicle3 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle3.setRegistrationNumber("MNO303");
        company.getVehicles().add(vehicle3);
        
        RentedVehicle vehicle4 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle4.setRegistrationNumber("PQR404");
        company.getVehicles().add(vehicle4);
        
        // Count rented vehicles
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Assert that the rented vehicle count is 2
        assertEquals(2, rentedVehicleCount);
    }
    
    @Test
    public void testCase3_CountRentedVehiclesAllRented() {
        // Create a Company object named "FoodExpress"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("FoodExpress");
        
        // Add five rented vehicles to the company
        RentedVehicle vehicle1 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle1.setRegistrationNumber("STU505");
        company.getVehicles().add(vehicle1);
        
        RentedVehicle vehicle2 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle2.setRegistrationNumber("VWX606");
        company.getVehicles().add(vehicle2);
        
        RentedVehicle vehicle3 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle3.setRegistrationNumber("YZA707");
        company.getVehicles().add(vehicle3);
        
        RentedVehicle vehicle4 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle4.setRegistrationNumber("BCD808");
        company.getVehicles().add(vehicle4);
        
        RentedVehicle vehicle5 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle5.setRegistrationNumber("EFG909");
        company.getVehicles().add(vehicle5);
        
        // Count rented vehicles
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Assert that the rented vehicle count is 5
        assertEquals(5, rentedVehicleCount);
    }
    
    @Test
    public void testCase4_CountRentedVehiclesWithMixedVehicleTypes() {
        // Create a Company object named "DailyDeliveries"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("DailyDeliveries");
        
        // Add six vehicles to the company (4 owned, 2 rented)
        OwnedVehicle vehicle1 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle1.setRegistrationNumber("HIJ010");
        company.getVehicles().add(vehicle1);
        
        RentedVehicle vehicle2 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle2.setRegistrationNumber("KLM111");
        company.getVehicles().add(vehicle2);
        
        OwnedVehicle vehicle3 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle3.setRegistrationNumber("NOP222");
        company.getVehicles().add(vehicle3);
        
        RentedVehicle vehicle4 = FleetFactory.eINSTANCE.createRentedVehicle();
        vehicle4.setRegistrationNumber("QRS333");
        company.getVehicles().add(vehicle4);
        
        OwnedVehicle vehicle5 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle5.setRegistrationNumber("TUV444");
        company.getVehicles().add(vehicle5);
        
        OwnedVehicle vehicle6 = FleetFactory.eINSTANCE.createOwnedVehicle();
        vehicle6.setRegistrationNumber("WXY555");
        company.getVehicles().add(vehicle6);
        
        // Count rented vehicles
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Assert that the rented vehicle count is 2
        assertEquals(2, rentedVehicleCount);
    }
    
    @Test
    public void testCase5_CountRentedVehiclesWithNoVehicles() {
        // Create a Company object named "DeliveriesRUs"
        Company company = FleetFactory.eINSTANCE.createCompany();
        company.setName("DeliveriesRUs");
        
        // Do not add any vehicles to the company
        
        // Count rented vehicles
        int rentedVehicleCount = company.getRentedVehicleCount();
        
        // Assert that the rented vehicle count is 0
        assertEquals(0, rentedVehicleCount);
    }
}