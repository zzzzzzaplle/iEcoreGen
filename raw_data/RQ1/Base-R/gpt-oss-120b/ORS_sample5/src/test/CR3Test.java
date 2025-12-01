import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;

public class CR3Test {
    
    private RideShareSystem system;
    private Driver driverD3;
    private Driver driverD4;
    
    @Before
    public void setUp() {
        system = new RideShareSystem();
        
        // Create drivers for the test cases
        driverD3 = new Driver();
        driverD3.setId("D3");
        system.addUser(driverD3);
        
        driverD4 = new Driver();
        driverD4.setId("D4");
        system.addUser(driverD4);
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        Trip tripA1 = new Trip();
        tripA1.setDriver(driverD3);
        Stop stop1A1 = new Stop();
        stop1A1.setStationName("CityX");
        Stop stop2A1 = new Stop();
        stop2A1.setStationName("CityY");
        tripA1.setStops(Arrays.asList(stop1A1, stop2A1));
        
        // Setup: Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        Trip tripA2 = new Trip();
        tripA2.setDriver(driverD3);
        Stop stop1A2 = new Stop();
        stop1A2.setStationName("CityY");
        Stop stop2A2 = new Stop();
        stop2A2.setStationName("CityZ");
        tripA2.setStops(Arrays.asList(stop1A2, stop2A2));
        
        // Execute: Check stop overlap between Trip A1 and A2
        boolean result = system.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        
        // Verify: Expected Output: true, common stop: "CityY"
        assertTrue("Trips should have common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        Trip tripB1 = new Trip();
        tripB1.setDriver(driverD4);
        Stop stop1B1 = new Stop();
        stop1B1.setStationName("CityM");
        Stop stop2B1 = new Stop();
        stop2B1.setStationName("CityN");
        tripB1.setStops(Arrays.asList(stop1B1, stop2B1));
        
        // Setup: Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        Trip tripB2 = new Trip();
        tripB2.setDriver(driverD4);
        Stop stop1B2 = new Stop();
        stop1B2.setStationName("CityP");
        Stop stop2B2 = new Stop();
        stop2B2.setStationName("CityQ");
        tripB2.setStops(Arrays.asList(stop1B2, stop2B2));
        
        // Execute: Check stop overlap between Trip B1 and B2
        boolean result = system.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        
        // Verify: Expected Output: false, no common stop
        assertFalse("Trips should not have any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 has no stops
        Trip tripC1 = new Trip();
        tripC1.setDriver(driverD3);
        tripC1.setStops(Arrays.asList()); // empty stops list
        
        // Setup: Trip C2 has no stops
        Trip tripC2 = new Trip();
        tripC2.setDriver(driverD3);
        tripC2.setStops(Arrays.asList()); // empty stops list
        
        // Execute: Check stop overlap between Trip C1 and C2
        boolean result = system.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        
        // Verify: Expected Output: false. Tests empty stop handling
        assertFalse("Trips with empty stop lists should return false", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        Trip tripD1 = new Trip();
        tripD1.setDriver(driverD3);
        Stop stop1D1 = new Stop();
        stop1D1.setStationName("A");
        Stop stop2D1 = new Stop();
        stop2D1.setStationName("B");
        Stop stop3D1 = new Stop();
        stop3D1.setStationName("C");
        tripD1.setStops(Arrays.asList(stop1D1, stop2D1, stop3D1));
        
        // Setup: Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        Trip tripD2 = new Trip();
        tripD2.setDriver(driverD3);
        Stop stop1D2 = new Stop();
        stop1D2.setStationName("X");
        Stop stop2D2 = new Stop();
        stop2D2.setStationName("B");
        Stop stop3D2 = new Stop();
        stop3D2.setStationName("C");
        tripD2.setStops(Arrays.asList(stop1D2, stop2D2, stop3D2));
        
        // Execute: Check stop overlap between Trip D1 and D2
        boolean result = system.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        
        // Verify: Expected Output: true, common stop: Stop("B"), Stop("C")
        assertTrue("Trips should have common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 stops: [Stop("Boston")]
        Trip tripE1 = new Trip();
        tripE1.setDriver(driverD3);
        Stop stopE1 = new Stop();
        stopE1.setStationName("Boston");
        tripE1.setStops(Arrays.asList(stopE1));
        
        // Setup: Trip E2 stops: [Stop("boston")]
        Trip tripE2 = new Trip();
        tripE2.setDriver(driverD3);
        Stop stopE2 = new Stop();
        stopE2.setStationName("boston");
        tripE2.setStops(Arrays.asList(stopE2));
        
        // Execute: Check stop overlap between Trip E1 and E2
        boolean result = system.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        
        // Verify: Expected Output: false, no common stop (case-sensitive comparison)
        assertFalse("Case-sensitive comparison should return false for 'Boston' vs 'boston'", result);
    }
}