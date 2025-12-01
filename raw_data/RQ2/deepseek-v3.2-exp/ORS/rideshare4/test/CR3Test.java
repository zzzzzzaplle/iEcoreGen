package edu.rideshare.rideshare4.test;

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
    
    private RideshareFactory factory;
    private Driver driverD3;
    private Driver driverD4;
    
    @Before
    public void setUp() {
        // Initialize the factory using Ecore factory pattern
        factory = RideshareFactory.eINSTANCE;
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 and A2 with shared stop "CityY"
        driverD3 = factory.createDriver();
        driverD3.setID("D3");
        
        Trip tripA1 = factory.createTrip();
        tripA1.setDepartureStation("StartA");
        tripA1.setArrivalStation("EndA");
        
        Trip tripA2 = factory.createTrip();
        tripA2.setDepartureStation("StartB");
        tripA2.setArrivalStation("EndB");
        
        // Add stops to Trip A1: [Stop("CityX"), Stop("CityY")]
        Stop stopA1_1 = factory.createStop();
        stopA1_1.setStopStation("CityX");
        tripA1.getStops().add(stopA1_1);
        
        Stop stopA1_2 = factory.createStop();
        stopA1_2.setStopStation("CityY");
        tripA1.getStops().add(stopA1_2);
        
        // Add stops to Trip A2: [Stop("CityY"), Stop("CityZ")]
        Stop stopA2_1 = factory.createStop();
        stopA2_1.setStopStation("CityY");
        tripA2.getStops().add(stopA2_1);
        
        Stop stopA2_2 = factory.createStop();
        stopA2_2.setStopStation("CityZ");
        tripA2.getStops().add(stopA2_2);
        
        // Add trips to driver
        driverD3.getTrips().add(tripA1);
        driverD3.getTrips().add(tripA2);
        
        // Test: Check stop overlap between Trip A1 and A2
        boolean result = driverD3.checkStopOverlap(tripA1, tripA2);
        
        // Expected Output: true, common stop: "CityY"
        assertTrue("Trips should share common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 and B2 with no common stops
        driverD4 = factory.createDriver();
        driverD4.setID("D4");
        
        Trip tripB1 = factory.createTrip();
        tripB1.setDepartureStation("StartM");
        tripB1.setArrivalStation("EndM");
        
        Trip tripB2 = factory.createTrip();
        tripB2.setDepartureStation("StartP");
        tripB2.setArrivalStation("EndQ");
        
        // Add stops to Trip B1: [Stop("CityM"), Stop("CityN")]
        Stop stopB1_1 = factory.createStop();
        stopB1_1.setStopStation("CityM");
        tripB1.getStops().add(stopB1_1);
        
        Stop stopB1_2 = factory.createStop();
        stopB1_2.setStopStation("CityN");
        tripB1.getStops().add(stopB1_2);
        
        // Add stops to Trip B2: [Stop("CityP"), Stop("CityQ")]
        Stop stopB2_1 = factory.createStop();
        stopB2_1.setStopStation("CityP");
        tripB2.getStops().add(stopB2_1);
        
        Stop stopB2_2 = factory.createStop();
        stopB2_2.setStopStation("CityQ");
        tripB2.getStops().add(stopB2_2);
        
        // Add trips to driver
        driverD4.getTrips().add(tripB1);
        driverD4.getTrips().add(tripB2);
        
        // Test: Check stop overlap between Trip B1 and B2
        boolean result = driverD4.checkStopOverlap(tripB1, tripB2);
        
        // Expected Output: false, no common stop
        assertFalse("Trips should not share any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 and C2 with no stops
        Driver driver = factory.createDriver();
        
        Trip tripC1 = factory.createTrip();
        tripC1.setDepartureStation("StartC1");
        tripC1.setArrivalStation("EndC1");
        // No stops added - empty stop list
        
        Trip tripC2 = factory.createTrip();
        tripC2.setDepartureStation("StartC2");
        tripC2.setArrivalStation("EndC2");
        // No stops added - empty stop list
        
        // Add trips to driver
        driver.getTrips().add(tripC1);
        driver.getTrips().add(tripC2);
        
        // Test: Check stop overlap between Trip C1 and C2
        boolean result = driver.checkStopOverlap(tripC1, tripC2);
        
        // Expected Output: false, tests empty stop handling
        assertFalse("Empty stop lists should return false", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 and D2 with multiple shared stops
        Driver driver = factory.createDriver();
        
        Trip tripD1 = factory.createTrip();
        tripD1.setDepartureStation("StartD1");
        tripD1.setArrivalStation("EndD1");
        
        Trip tripD2 = factory.createTrip();
        tripD2.setDepartureStation("StartD2");
        tripD2.setArrivalStation("EndD2");
        
        // Add stops to Trip D1: [Stop("A"), Stop("B"), Stop("C")]
        Stop stopD1_1 = factory.createStop();
        stopD1_1.setStopStation("A");
        tripD1.getStops().add(stopD1_1);
        
        Stop stopD1_2 = factory.createStop();
        stopD1_2.setStopStation("B");
        tripD1.getStops().add(stopD1_2);
        
        Stop stopD1_3 = factory.createStop();
        stopD1_3.setStopStation("C");
        tripD1.getStops().add(stopD1_3);
        
        // Add stops to Trip D2: [Stop("X"), Stop("B"), Stop("C")]
        Stop stopD2_1 = factory.createStop();
        stopD2_1.setStopStation("X");
        tripD2.getStops().add(stopD2_1);
        
        Stop stopD2_2 = factory.createStop();
        stopD2_2.setStopStation("B");
        tripD2.getStops().add(stopD2_2);
        
        Stop stopD2_3 = factory.createStop();
        stopD2_3.setStopStation("C");
        tripD2.getStops().add(stopD2_3);
        
        // Add trips to driver
        driver.getTrips().add(tripD1);
        driver.getTrips().add(tripD2);
        
        // Test: Check stop overlap between Trip D1 and D2
        boolean result = driver.checkStopOverlap(tripD1, tripD2);
        
        // Expected Output: true, common stops: Stop("B"), Stop("C")
        assertTrue("Trips should share multiple common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 and E2 with case-sensitive stop names
        Driver driver = factory.createDriver();
        
        Trip tripE1 = factory.createTrip();
        tripE1.setDepartureStation("StartE1");
        tripE1.setArrivalStation("EndE1");
        
        Trip tripE2 = factory.createTrip();
        tripE2.setDepartureStation("StartE2");
        tripE2.setArrivalStation("EndE2");
        
        // Add stops to Trip E1: [Stop("Boston")]
        Stop stopE1 = factory.createStop();
        stopE1.setStopStation("Boston");
        tripE1.getStops().add(stopE1);
        
        // Add stops to Trip E2: [Stop("boston")]
        Stop stopE2 = factory.createStop();
        stopE2.setStopStation("boston");
        tripE2.getStops().add(stopE2);
        
        // Add trips to driver
        driver.getTrips().add(tripE1);
        driver.getTrips().add(tripE2);
        
        // Test: Check stop overlap between Trip E1 and E2
        boolean result = driver.checkStopOverlap(tripE1, tripE2);
        
        // Expected Output: false, no common stop (case-sensitive comparison)
        assertFalse("Case-sensitive comparison should return false for 'Boston' vs 'boston'", result);
    }
}