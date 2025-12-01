import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
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
        // Initialize all trip objects
        tripA1 = new Trip();
        tripA2 = new Trip();
        tripB1 = new Trip();
        tripB2 = new Trip();
        tripC1 = new Trip();
        tripC2 = new Trip();
        tripD1 = new Trip();
        tripD2 = new Trip();
        tripE1 = new Trip();
        tripE2 = new Trip();
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        tripA1.setDriverId("D3");
        List<Stop> stopsA1 = new ArrayList<>();
        stopsA1.add(createStop("CityX"));
        stopsA1.add(createStop("CityY"));
        tripA1.setStops(stopsA1);
        
        // Setup: Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        tripA2.setDriverId("D3");
        List<Stop> stopsA2 = new ArrayList<>();
        stopsA2.add(createStop("CityY"));
        stopsA2.add(createStop("CityZ"));
        tripA2.setStops(stopsA2);
        
        // Check stop overlap between Trip A1 and A2
        boolean result = tripA1.checkStopOverlap(tripA2);
        
        // Expected Output: true, common stop: "CityY"
        assertTrue("Trips should have common stop CityY", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        tripB1.setDriverId("D4");
        List<Stop> stopsB1 = new ArrayList<>();
        stopsB1.add(createStop("CityM"));
        stopsB1.add(createStop("CityN"));
        tripB1.setStops(stopsB1);
        
        // Setup: Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        tripB2.setDriverId("D4");
        List<Stop> stopsB2 = new ArrayList<>();
        stopsB2.add(createStop("CityP"));
        stopsB2.add(createStop("CityQ"));
        tripB2.setStops(stopsB2);
        
        // Check stop overlap between Trip B1 and B2
        boolean result = tripB1.checkStopOverlap(tripB2);
        
        // Expected Output: false, no common stop
        assertFalse("Trips should not have any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 has no stops
        tripC1.setDriverId("D5");
        tripC1.setStops(new ArrayList<>());
        
        // Setup: Trip C2 has no stops
        tripC2.setDriverId("D5");
        tripC2.setStops(new ArrayList<>());
        
        // Check stop overlap between Trip C1 and C2
        boolean result = tripC1.checkStopOverlap(tripC2);
        
        // Expected Output: false. Tests empty stop handling
        assertFalse("Trips with empty stop lists should return false", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        tripD1.setDriverId("D6");
        List<Stop> stopsD1 = new ArrayList<>();
        stopsD1.add(createStop("A"));
        stopsD1.add(createStop("B"));
        stopsD1.add(createStop("C"));
        tripD1.setStops(stopsD1);
        
        // Setup: Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        tripD2.setDriverId("D6");
        List<Stop> stopsD2 = new ArrayList<>();
        stopsD2.add(createStop("X"));
        stopsD2.add(createStop("B"));
        stopsD2.add(createStop("C"));
        tripD2.setStops(stopsD2);
        
        // Check stop overlap between Trip D1 and D2
        boolean result = tripD1.checkStopOverlap(tripD2);
        
        // Expected Output: true, common stop: Stop("B"), Stop("C")
        assertTrue("Trips should have common stops B and C", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 stops: [Stop("Boston")]
        tripE1.setDriverId("D7");
        List<Stop> stopsE1 = new ArrayList<>();
        stopsE1.add(createStop("Boston"));
        tripE1.setStops(stopsE1);
        
        // Setup: Trip E2 stops: [Stop("boston")]
        tripE2.setDriverId("D7");
        List<Stop> stopsE2 = new ArrayList<>();
        stopsE2.add(createStop("boston"));
        tripE2.setStops(stopsE2);
        
        // Check stop overlap between Trip E1 and E2
        boolean result = tripE1.checkStopOverlap(tripE2);
        
        // Expected Output: false, no common stop
        assertFalse("Case-sensitive comparison should return false for 'Boston' vs 'boston'", result);
    }
    
    // Helper method to create Stop objects
    private Stop createStop(String stationName) {
        Stop stop = new Stop();
        stop.setStopStation(stationName);
        return stop;
    }
}