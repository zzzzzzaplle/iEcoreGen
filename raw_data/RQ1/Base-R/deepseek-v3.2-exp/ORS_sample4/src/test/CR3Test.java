import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private OnlineRideshareSystem system;
    private User driverD3;
    private User driverD4;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        
        // Create drivers for testing
        driverD3 = new User();
        driverD3.setUserId("D3");
        
        driverD4 = new User();
        driverD4.setUserId("D4");
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        Trip tripA1 = new Trip();
        tripA1.setDriver(driverD3);
        tripA1.setTripId("A1");
        
        List<Stop> stopsA1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("CityX");
        stopsA1.add(stop1);
        Stop stop2 = new Stop();
        stop2.setStopStation("CityY");
        stopsA1.add(stop2);
        tripA1.setStops(stopsA1);
        
        // Setup: Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        Trip tripA2 = new Trip();
        tripA2.setDriver(driverD3);
        tripA2.setTripId("A2");
        
        List<Stop> stopsA2 = new ArrayList<>();
        Stop stop3 = new Stop();
        stop3.setStopStation("CityY");
        stopsA2.add(stop3);
        Stop stop4 = new Stop();
        stop4.setStopStation("CityZ");
        stopsA2.add(stop4);
        tripA2.setStops(stopsA2);
        
        // Execute: Check stop overlap between Trip A1 and A2
        boolean result = system.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        
        // Verify: Expected Output: true, common stop: "CityY"
        assertTrue("Should return true when trips share common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        Trip tripB1 = new Trip();
        tripB1.setDriver(driverD4);
        tripB1.setTripId("B1");
        
        List<Stop> stopsB1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("CityM");
        stopsB1.add(stop1);
        Stop stop2 = new Stop();
        stop2.setStopStation("CityN");
        stopsB1.add(stop2);
        tripB1.setStops(stopsB1);
        
        // Setup: Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        Trip tripB2 = new Trip();
        tripB2.setDriver(driverD4);
        tripB2.setTripId("B2");
        
        List<Stop> stopsB2 = new ArrayList<>();
        Stop stop3 = new Stop();
        stop3.setStopStation("CityP");
        stopsB2.add(stop3);
        Stop stop4 = new Stop();
        stop4.setStopStation("CityQ");
        stopsB2.add(stop4);
        tripB2.setStops(stopsB2);
        
        // Execute: Check stop overlap between Trip B1 and B2
        boolean result = system.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        
        // Verify: Expected Output: false, no common stop
        assertFalse("Should return false when trips have no common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 has no stops
        Trip tripC1 = new Trip();
        tripC1.setDriver(driverD3);
        tripC1.setTripId("C1");
        tripC1.setStops(new ArrayList<>());
        
        // Setup: Trip C2 has no stops
        Trip tripC2 = new Trip();
        tripC2.setDriver(driverD3);
        tripC2.setTripId("C2");
        tripC2.setStops(new ArrayList<>());
        
        // Execute: Check stop overlap between Trip C1 and C2
        boolean result = system.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        
        // Verify: Expected Output: false. Tests empty stop handling
        assertFalse("Should return false when both trips have empty stop lists", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        Trip tripD1 = new Trip();
        tripD1.setDriver(driverD3);
        tripD1.setTripId("D1");
        
        List<Stop> stopsD1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("A");
        stopsD1.add(stop1);
        Stop stop2 = new Stop();
        stop2.setStopStation("B");
        stopsD1.add(stop2);
        Stop stop3 = new Stop();
        stop3.setStopStation("C");
        stopsD1.add(stop3);
        tripD1.setStops(stopsD1);
        
        // Setup: Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        Trip tripD2 = new Trip();
        tripD2.setDriver(driverD3);
        tripD2.setTripId("D2");
        
        List<Stop> stopsD2 = new ArrayList<>();
        Stop stop4 = new Stop();
        stop4.setStopStation("X");
        stopsD2.add(stop4);
        Stop stop5 = new Stop();
        stop5.setStopStation("B");
        stopsD2.add(stop5);
        Stop stop6 = new Stop();
        stop6.setStopStation("C");
        stopsD2.add(stop6);
        tripD2.setStops(stopsD2);
        
        // Execute: Check stop overlap between Trip D1 and D2
        boolean result = system.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        
        // Verify: Expected Output: true, common stop: Stop("B"), Stop("C")
        assertTrue("Should return true when trips share multiple common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 stops: [Stop("Boston")]
        Trip tripE1 = new Trip();
        tripE1.setDriver(driverD3);
        tripE1.setTripId("E1");
        
        List<Stop> stopsE1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("Boston");
        stopsE1.add(stop1);
        tripE1.setStops(stopsE1);
        
        // Setup: Trip E2 stops: [Stop("boston")]
        Trip tripE2 = new Trip();
        tripE2.setDriver(driverD3);
        tripE2.setTripId("E2");
        
        List<Stop> stopsE2 = new ArrayList<>();
        Stop stop2 = new Stop();
        stop2.setStopStation("boston");
        stopsE2.add(stop2);
        tripE2.setStops(stopsE2);
        
        // Execute: Check stop overlap between Trip E1 and E2
        boolean result = system.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        
        // Verify: Expected Output: false, no common stop
        assertFalse("Should return false when stop names differ in case sensitivity", result);
    }
}