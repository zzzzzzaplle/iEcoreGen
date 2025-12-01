import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CR3Test {
    
    private OnlineRideshareSystem system;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Create driver D3 and trips A1, A2 with shared stop "CityY"
        Trip tripA1 = new Trip();
        tripA1.setDriverId("D3");
        tripA1.setId("A1");
        
        Stop stop1 = new Stop();
        stop1.setStopStation("CityX");
        Stop stop2 = new Stop();
        stop2.setStopStation("CityY");
        tripA1.setStops(Arrays.asList(stop1, stop2));
        
        Trip tripA2 = new Trip();
        tripA2.setDriverId("D3");
        tripA2.setId("A2");
        
        Stop stop3 = new Stop();
        stop3.setStopStation("CityY");
        Stop stop4 = new Stop();
        stop4.setStopStation("CityZ");
        tripA2.setStops(Arrays.asList(stop3, stop4));
        
        // Test: Check stop overlap between Trip A1 and A2
        boolean result = system.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        
        // Verify: Expected true, common stop: "CityY"
        assertTrue("Trips should have common stop CityY", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Create driver D4 and trips B1, B2 with no common stops
        Trip tripB1 = new Trip();
        tripB1.setDriverId("D4");
        tripB1.setId("B1");
        
        Stop stop1 = new Stop();
        stop1.setStopStation("CityM");
        Stop stop2 = new Stop();
        stop2.setStopStation("CityN");
        tripB1.setStops(Arrays.asList(stop1, stop2));
        
        Trip tripB2 = new Trip();
        tripB2.setDriverId("D4");
        tripB2.setId("B2");
        
        Stop stop3 = new Stop();
        stop3.setStopStation("CityP");
        Stop stop4 = new Stop();
        stop4.setStopStation("CityQ");
        tripB2.setStops(Arrays.asList(stop3, stop4));
        
        // Test: Check stop overlap between Trip B1 and B2
        boolean result = system.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        
        // Verify: Expected false, no common stop
        assertFalse("Trips should not have any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Create trips C1 and C2 with empty stop lists
        Trip tripC1 = new Trip();
        tripC1.setDriverId("D5");
        tripC1.setId("C1");
        // No stops added (empty list by default)
        
        Trip tripC2 = new Trip();
        tripC2.setDriverId("D5");
        tripC2.setId("C2");
        // No stops added (empty list by default)
        
        // Test: Check stop overlap between Trip C1 and C2
        boolean result = system.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        
        // Verify: Expected false, tests empty stop handling
        assertFalse("Trips with empty stop lists should return false", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Create trips D1 and D2 with multiple shared stops
        Trip tripD1 = new Trip();
        tripD1.setDriverId("D6");
        tripD1.setId("D1");
        
        Stop stop1 = new Stop();
        stop1.setStopStation("A");
        Stop stop2 = new Stop();
        stop2.setStopStation("B");
        Stop stop3 = new Stop();
        stop3.setStopStation("C");
        tripD1.setStops(Arrays.asList(stop1, stop2, stop3));
        
        Trip tripD2 = new Trip();
        tripD2.setDriverId("D6");
        tripD2.setId("D2");
        
        Stop stop4 = new Stop();
        stop4.setStopStation("X");
        Stop stop5 = new Stop();
        stop5.setStopStation("B");
        Stop stop6 = new Stop();
        stop6.setStopStation("C");
        tripD2.setStops(Arrays.asList(stop4, stop5, stop6));
        
        // Test: Check stop overlap between Trip D1 and D2
        boolean result = system.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        
        // Verify: Expected true, common stops: "B" and "C"
        assertTrue("Trips should have common stops B and C", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Create trips E1 and E2 with case-sensitive stop names
        Trip tripE1 = new Trip();
        tripE1.setDriverId("D7");
        tripE1.setId("E1");
        
        Stop stop1 = new Stop();
        stop1.setStopStation("Boston");
        tripE1.setStops(Arrays.asList(stop1));
        
        Trip tripE2 = new Trip();
        tripE2.setDriverId("D7");
        tripE2.setId("E2");
        
        Stop stop2 = new Stop();
        stop2.setStopStation("boston");
        tripE2.setStops(Arrays.asList(stop2));
        
        // Test: Check stop overlap between Trip E1 and E2
        boolean result = system.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        
        // Verify: Expected false, no common stop (case-sensitive comparison)
        assertFalse("Case-sensitive comparison should return false for 'Boston' vs 'boston'", result);
    }
}