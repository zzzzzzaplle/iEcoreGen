package edu.rideshare.rideshare1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.rideshare.RideshareFactory;
import edu.rideshare.Driver;
import edu.rideshare.Trip;
import edu.rideshare.Stop;
import org.eclipse.emf.common.util.EList;

public class CR3Test {
    
    private RideshareFactory factory;
    private Driver driverD3;
    private Driver driverD4;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory for object creation
        factory = RideshareFactory.eINSTANCE;
        
        // Create drivers for testing
        driverD3 = factory.createDriver();
        driverD4 = factory.createDriver();
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        Trip tripA1 = factory.createTrip();
        Stop stopA1_1 = factory.createStop();
        stopA1_1.setStopStation("CityX");
        Stop stopA1_2 = factory.createStop();
        stopA1_2.setStopStation("CityY");
        
        tripA1.getStops().add(stopA1_1);
        tripA1.getStops().add(stopA1_2);
        driverD3.getTrips().add(tripA1);
        
        // Setup: Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        Trip tripA2 = factory.createTrip();
        Stop stopA2_1 = factory.createStop();
        stopA2_1.setStopStation("CityY");
        Stop stopA2_2 = factory.createStop();
        stopA2_2.setStopStation("CityZ");
        
        tripA2.getStops().add(stopA2_1);
        tripA2.getStops().add(stopA2_2);
        driverD3.getTrips().add(tripA2);
        
        // Test: Check stop overlap between Trip A1 and A2
        boolean result = driverD3.checkStopOverlap(tripA1, tripA2);
        
        // Expected Output: true, common stop: "CityY"
        assertTrue("Trips should share common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        Trip tripB1 = factory.createTrip();
        Stop stopB1_1 = factory.createStop();
        stopB1_1.setStopStation("CityM");
        Stop stopB1_2 = factory.createStop();
        stopB1_2.setStopStation("CityN");
        
        tripB1.getStops().add(stopB1_1);
        tripB1.getStops().add(stopB1_2);
        driverD4.getTrips().add(tripB1);
        
        // Setup: Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        Trip tripB2 = factory.createTrip();
        Stop stopB2_1 = factory.createStop();
        stopB2_1.setStopStation("CityP");
        Stop stopB2_2 = factory.createStop();
        stopB2_2.setStopStation("CityQ");
        
        tripB2.getStops().add(stopB2_1);
        tripB2.getStops().add(stopB2_2);
        driverD4.getTrips().add(tripB2);
        
        // Test: Check stop overlap between Trip B1 and B2
        boolean result = driverD4.checkStopOverlap(tripB1, tripB2);
        
        // Expected Output: false, no common stop
        assertFalse("Trips should not share any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 has no stops
        Trip tripC1 = factory.createTrip();
        // No stops added intentionally
        
        // Setup: Trip C2 has no stops  
        Trip tripC2 = factory.createTrip();
        // No stops added intentionally
        
        // Test: Check stop overlap between Trip C1 and C2
        boolean result = driverD3.checkStopOverlap(tripC1, tripC2);
        
        // Expected Output: false. Tests empty stop handling
        assertFalse("Empty stop lists should return false", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        Trip tripD1 = factory.createTrip();
        Stop stopD1_1 = factory.createStop();
        stopD1_1.setStopStation("A");
        Stop stopD1_2 = factory.createStop();
        stopD1_2.setStopStation("B");
        Stop stopD1_3 = factory.createStop();
        stopD1_3.setStopStation("C");
        
        tripD1.getStops().add(stopD1_1);
        tripD1.getStops().add(stopD1_2);
        tripD1.getStops().add(stopD1_3);
        driverD3.getTrips().add(tripD1);
        
        // Setup: Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        Trip tripD2 = factory.createTrip();
        Stop stopD2_1 = factory.createStop();
        stopD2_1.setStopStation("X");
        Stop stopD2_2 = factory.createStop();
        stopD2_2.setStopStation("B");
        Stop stopD2_3 = factory.createStop();
        stopD2_3.setStopStation("C");
        
        tripD2.getStops().add(stopD2_1);
        tripD2.getStops().add(stopD2_2);
        tripD2.getStops().add(stopD2_3);
        driverD3.getTrips().add(tripD2);
        
        // Test: Check stop overlap between Trip D1 and D2
        boolean result = driverD3.checkStopOverlap(tripD1, tripD2);
        
        // Expected Output: true, common stop: Stop("B"), Stop("C")
        assertTrue("Trips should share multiple common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 stops: [Stop("Boston")]
        Trip tripE1 = factory.createTrip();
        Stop stopE1 = factory.createStop();
        stopE1.setStopStation("Boston");
        
        tripE1.getStops().add(stopE1);
        driverD3.getTrips().add(tripE1);
        
        // Setup: Trip E2 stops: [Stop("boston")]
        Trip tripE2 = factory.createTrip();
        Stop stopE2 = factory.createStop();
        stopE2.setStopStation("boston");
        
        tripE2.getStops().add(stopE2);
        driverD3.getTrips().add(tripE2);
        
        // Test: Check stop overlap between Trip E1 and E2
        boolean result = driverD3.checkStopOverlap(tripE1, tripE2);
        
        // Expected Output: false, no common stop (case-sensitive comparison)
        assertFalse("Case-sensitive comparison should return false for 'Boston' vs 'boston'", result);
    }
}