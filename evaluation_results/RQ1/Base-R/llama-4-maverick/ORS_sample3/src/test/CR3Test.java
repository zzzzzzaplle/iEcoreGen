import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    private ORS ors;
    private Trip tripA1;
    private Trip tripA2;
    private Trip tripB1;
    private Trip tripB2;
    private Trip tripC1;
    private Trip tripC2;
    private Trip tripD1;
    private Trip tripD2;
    private Trip tripE1;
    private Trip tripE2;

    @Before
    public void setUp() {
        ors = new ORS();
        
        // Setup for Test Case 1: Shared stop in indirect trips
        tripA1 = new Trip();
        tripA1.setDriverId("D3");
        tripA1.setStops(java.util.Arrays.asList(createStop("CityX"), createStop("CityY")));
        
        tripA2 = new Trip();
        tripA2.setDriverId("D3");
        tripA2.setStops(java.util.Arrays.asList(createStop("CityY"), createStop("CityZ")));
        
        // Setup for Test Case 2: No common stops in indirect trips
        tripB1 = new Trip();
        tripB1.setDriverId("D4");
        tripB1.setStops(java.util.Arrays.asList(createStop("CityM"), createStop("CityN")));
        
        tripB2 = new Trip();
        tripB2.setDriverId("D4");
        tripB2.setStops(java.util.Arrays.asList(createStop("CityP"), createStop("CityQ")));
        
        // Setup for Test Case 3: Empty stop lists comparison
        tripC1 = new Trip();
        tripC1.setDriverId("D5");
        tripC1.setStops(new java.util.ArrayList<>());
        
        tripC2 = new Trip();
        tripC2.setDriverId("D5");
        tripC2.setStops(new java.util.ArrayList<>());
        
        // Setup for Test Case 4: Multiple shared stops detection
        tripD1 = new Trip();
        tripD1.setDriverId("D6");
        tripD1.setStops(java.util.Arrays.asList(createStop("A"), createStop("B"), createStop("C")));
        
        tripD2 = new Trip();
        tripD2.setDriverId("D6");
        tripD2.setStops(java.util.Arrays.asList(createStop("X"), createStop("B"), createStop("C")));
        
        // Setup for Test Case 5: Case-sensitive stop comparison
        tripE1 = new Trip();
        tripE1.setDriverId("D7");
        tripE1.setStops(java.util.Arrays.asList(createStop("Boston"))));
        
        tripE2 = new Trip();
        tripE2.setDriverId("D7");
        tripE2.setStops(java.util.Arrays.asList(createStop("boston"))));
    }

    private Stop createStop(String station) {
        Stop stop = new Stop();
        stop.setStopStation(station);
        return stop;
    }

    @Test
    public void testCase1_sharedStopInIndirectTrips() {
        // Test shared stop in indirect trips
        boolean result = ors.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        assertTrue("Should return true when trips share common stop 'CityY'", result);
    }

    @Test
    public void testCase2_noCommonStopsInIndirectTrips() {
        // Test no common stops in indirect trips
        boolean result = ors.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        assertFalse("Should return false when trips have no common stops", result);
    }

    @Test
    public void testCase3_emptyStopListsComparison() {
        // Test empty stop lists comparison
        boolean result = ors.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        assertFalse("Should return false when both trips have empty stop lists", result);
    }

    @Test
    public void testCase4_multipleSharedStopsDetection() {
        // Test multiple shared stops detection
        boolean result = ors.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        assertTrue("Should return true when trips share multiple common stops 'B' and 'C'", result);
    }

    @Test
    public void testCase5_caseSensitiveStopComparison() {
        // Test case-sensitive stop comparison
        boolean result = ors.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        assertFalse("Should return false when stop names differ in case sensitivity", result);
    }
}