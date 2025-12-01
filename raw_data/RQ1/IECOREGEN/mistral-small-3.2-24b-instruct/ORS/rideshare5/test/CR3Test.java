package edu.rideshare.rideshare5.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.rideshare.Award;
import edu.rideshare.Booking;
import edu.rideshare.Customer;
import edu.rideshare.Driver;
import edu.rideshare.MembershipPackage;
import edu.rideshare.RideshareFactory;
import edu.rideshare.RidesharePackage;
import edu.rideshare.Stop;
import edu.rideshare.Trip;
import edu.rideshare.User;

import org.eclipse.emf.common.util.EList;

public class CR3Test {
    
    private Driver driverD3;
    private Driver driverD4;
    private RideshareFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory for creating Ecore objects
        factory = RideshareFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 and A2 with shared stop "CityY"
        driverD3 = factory.createDriver();
        driverD3.setID("D3");
        driverD3.setEmail("d3@example.com");
        driverD3.setPhoneNumber("1234567890");
        
        Trip tripA1 = factory.createTrip();
        tripA1.setDepartureStation("StartA");
        tripA1.setArrivalStation("EndA");
        tripA1.setDriver(driverD3);
        
        // Add stops to Trip A1
        Stop stopA1_1 = factory.createStop();
        stopA1_1.setStopStation("CityX");
        tripA1.getStops().add(stopA1_1);
        
        Stop stopA1_2 = factory.createStop();
        stopA1_2.setStopStation("CityY");
        tripA1.getStops().add(stopA1_2);
        
        Trip tripA2 = factory.createTrip();
        tripA2.setDepartureStation("StartB");
        tripA2.setArrivalStation("EndB");
        tripA2.setDriver(driverD3);
        
        // Add stops to Trip A2
        Stop stopA2_1 = factory.createStop();
        stopA2_1.setStopStation("CityY");
        tripA2.getStops().add(stopA2_1);
        
        Stop stopA2_2 = factory.createStop();
        stopA2_2.setStopStation("CityZ");
        tripA2.getStops().add(stopA2_2);
        
        // Test: Check stop overlap between Trip A1 and A2
        boolean result = driverD3.checkStopOverlap(tripA1, tripA2);
        
        // Verify: Should return true due to common stop "CityY"
        assertTrue("Trips should have common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 and B2 with no common stops
        driverD4 = factory.createDriver();
        driverD4.setID("D4");
        driverD4.setEmail("d4@example.com");
        driverD4.setPhoneNumber("0987654321");
        
        Trip tripB1 = factory.createTrip();
        tripB1.setDepartureStation("StartM");
        tripB1.setArrivalStation("EndM");
        tripB1.setDriver(driverD4);
        
        // Add stops to Trip B1
        Stop stopB1_1 = factory.createStop();
        stopB1_1.setStopStation("CityM");
        tripB1.getStops().add(stopB1_1);
        
        Stop stopB1_2 = factory.createStop();
        stopB1_2.setStopStation("CityN");
        tripB1.getStops().add(stopB1_2);
        
        Trip tripB2 = factory.createTrip();
        tripB2.setDepartureStation("StartP");
        tripB2.setArrivalStation("EndQ");
        tripB2.setDriver(driverD4);
        
        // Add stops to Trip B2
        Stop stopB2_1 = factory.createStop();
        stopB2_1.setStopStation("CityP");
        tripB2.getStops().add(stopB2_1);
        
        Stop stopB2_2 = factory.createStop();
        stopB2_2.setStopStation("CityQ");
        tripB2.getStops().add(stopB2_2);
        
        // Test: Check stop overlap between Trip B1 and B2
        boolean result = driverD4.checkStopOverlap(tripB1, tripB2);
        
        // Verify: Should return false as no common stops
        assertFalse("Trips should not have any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Two trips with no stops
        Driver driver = factory.createDriver();
        driver.setID("D5");
        driver.setEmail("d5@example.com");
        driver.setPhoneNumber("1111111111");
        
        Trip tripC1 = factory.createTrip();
        tripC1.setDepartureStation("StartC1");
        tripC1.setArrivalStation("EndC1");
        tripC1.setDriver(driver);
        // No stops added - empty stop list
        
        Trip tripC2 = factory.createTrip();
        tripC2.setDepartureStation("StartC2");
        tripC2.setArrivalStation("EndC2");
        tripC2.setDriver(driver);
        // No stops added - empty stop list
        
        // Test: Check stop overlap between Trip C1 and C2
        boolean result = driver.checkStopOverlap(tripC1, tripC2);
        
        // Verify: Should return false for empty stop lists
        assertFalse("Empty stop lists should not have overlap", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Two trips with multiple shared stops
        Driver driver = factory.createDriver();
        driver.setID("D6");
        driver.setEmail("d6@example.com");
        driver.setPhoneNumber("2222222222");
        
        Trip tripD1 = factory.createTrip();
        tripD1.setDepartureStation("StartD1");
        tripD1.setArrivalStation("EndD1");
        tripD1.setDriver(driver);
        
        // Add stops to Trip D1
        Stop stopD1_1 = factory.createStop();
        stopD1_1.setStopStation("A");
        tripD1.getStops().add(stopD1_1);
        
        Stop stopD1_2 = factory.createStop();
        stopD1_2.setStopStation("B");
        tripD1.getStops().add(stopD1_2);
        
        Stop stopD1_3 = factory.createStop();
        stopD1_3.setStopStation("C");
        tripD1.getStops().add(stopD1_3);
        
        Trip tripD2 = factory.createTrip();
        tripD2.setDepartureStation("StartD2");
        tripD2.setArrivalStation("EndD2");
        tripD2.setDriver(driver);
        
        // Add stops to Trip D2
        Stop stopD2_1 = factory.createStop();
        stopD2_1.setStopStation("X");
        tripD2.getStops().add(stopD2_1);
        
        Stop stopD2_2 = factory.createStop();
        stopD2_2.setStopStation("B");
        tripD2.getStops().add(stopD2_2);
        
        Stop stopD2_3 = factory.createStop();
        stopD2_3.setStopStation("C");
        tripD2.getStops().add(stopD2_3);
        
        // Test: Check stop overlap between Trip D1 and D2
        boolean result = driver.checkStopOverlap(tripD1, tripD2);
        
        // Verify: Should return true due to multiple common stops "B" and "C"
        assertTrue("Trips should have multiple common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Two trips with stops that differ only in case
        Driver driver = factory.createDriver();
        driver.setID("D7");
        driver.setEmail("d7@example.com");
        driver.setPhoneNumber("3333333333");
        
        Trip tripE1 = factory.createTrip();
        tripE1.setDepartureStation("StartE1");
        tripE1.setArrivalStation("EndE1");
        tripE1.setDriver(driver);
        
        // Add stop to Trip E1
        Stop stopE1 = factory.createStop();
        stopE1.setStopStation("Boston");
        tripE1.getStops().add(stopE1);
        
        Trip tripE2 = factory.createTrip();
        tripE2.setDepartureStation("StartE2");
        tripE2.setArrivalStation("EndE2");
        tripE2.setDriver(driver);
        
        // Add stop to Trip E2
        Stop stopE2 = factory.createStop();
        stopE2.setStopStation("boston");
        tripE2.getStops().add(stopE2);
        
        // Test: Check stop overlap between Trip E1 and E2
        boolean result = driver.checkStopOverlap(tripE1, tripE2);
        
        // Verify: Should return false due to case sensitivity
        assertFalse("Case-sensitive comparison should return false for 'Boston' vs 'boston'", result);
    }
}