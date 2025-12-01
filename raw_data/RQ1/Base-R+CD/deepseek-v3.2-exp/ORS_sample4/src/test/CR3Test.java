import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private Driver driver;
    
    @Before
    public void setUp() {
        driver = new Driver();
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        Trip tripA1 = new Trip();
        tripA1.setDepartureStation("StartA");
        tripA1.setArrivalStation("EndA");
        List<Stop> stopsA1 = new ArrayList<>();
        stopsA1.add(createStop("CityX"));
        stopsA1.add(createStop("CityY"));
        tripA1.setStops(stopsA1);
        
        // Setup: Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        Trip tripA2 = new Trip();
        tripA2.setDepartureStation("StartB");
        tripA2.setArrivalStation("EndB");
        List<Stop> stopsA2 = new ArrayList<>();
        stopsA2.add(createStop("CityY"));
        stopsA2.add(createStop("CityZ"));
        tripA2.setStops(stopsA2);
        
        // Execute: Check stop overlap between Trip A1 and A2
        boolean result = driver.checkStopOverlap(tripA1, tripA2);
        
        // Verify: Expected Output: true, common stop: "CityY"
        assertTrue("Should return true when trips share common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        Trip tripB1 = new Trip();
        tripB1.setDepartureStation("StartC");
        tripB1.setArrivalStation("EndC");
        List<Stop> stopsB1 = new ArrayList<>();
        stopsB1.add(createStop("CityM"));
        stopsB1.add(createStop("CityN"));
        tripB1.setStops(stopsB1);
        
        // Setup: Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        Trip tripB2 = new Trip();
        tripB2.setDepartureStation("StartD");
        tripB2.setArrivalStation("EndD");
        List<Stop> stopsB2 = new ArrayList<>();
        stopsB2.add(createStop("CityP"));
        stopsB2.add(createStop("CityQ"));
        tripB2.setStops(stopsB2);
        
        // Execute: Check stop overlap between Trip B1 and B2
        boolean result = driver.checkStopOverlap(tripB1, tripB2);
        
        // Verify: Expected Output: false, no common stop
        assertFalse("Should return false when trips have no common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 has no stops
        Trip tripC1 = new Trip();
        tripC1.setDepartureStation("StartE");
        tripC1.setArrivalStation("EndE");
        tripC1.setStops(new ArrayList<>());
        
        // Setup: Trip C2 has no stops
        Trip tripC2 = new Trip();
        tripC2.setDepartureStation("StartF");
        tripC2.setArrivalStation("EndF");
        tripC2.setStops(new ArrayList<>());
        
        // Execute: Check stop overlap between Trip C1 and C2
        boolean result = driver.checkStopOverlap(tripC1, tripC2);
        
        // Verify: Expected Output: false. Tests empty stop handling
        assertFalse("Should return false when both trips have empty stop lists", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        Trip tripD1 = new Trip();
        tripD1.setDepartureStation("StartG");
        tripD1.setArrivalStation("EndG");
        List<Stop> stopsD1 = new ArrayList<>();
        stopsD1.add(createStop("A"));
        stopsD1.add(createStop("B"));
        stopsD1.add(createStop("C"));
        tripD1.setStops(stopsD1);
        
        // Setup: Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        Trip tripD2 = new Trip();
        tripD2.setDepartureStation("StartH");
        tripD2.setArrivalStation("EndH");
        List<Stop> stopsD2 = new ArrayList<>();
        stopsD2.add(createStop("X"));
        stopsD2.add(createStop("B"));
        stopsD2.add(createStop("C"));
        tripD2.setStops(stopsD2);
        
        // Execute: Check stop overlap between Trip D1 and D2
        boolean result = driver.checkStopOverlap(tripD1, tripD2);
        
        // Verify: Expected Output: true, common stop: Stop("B"), Stop("C")
        assertTrue("Should return true when trips share multiple common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 stops: [Stop("Boston")]
        Trip tripE1 = new Trip();
        tripE1.setDepartureStation("StartI");
        tripE1.setArrivalStation("EndI");
        List<Stop> stopsE1 = new ArrayList<>();
        stopsE1.add(createStop("Boston"));
        tripE1.setStops(stopsE1);
        
        // Setup: Trip E2 stops: [Stop("boston")]
        Trip tripE2 = new Trip();
        tripE2.setDepartureStation("StartJ");
        tripE2.setArrivalStation("EndJ");
        List<Stop> stopsE2 = new ArrayList<>();
        stopsE2.add(createStop("boston"));
        tripE2.setStops(stopsE2);
        
        // Execute: Check stop overlap between Trip E1 and E2
        boolean result = driver.checkStopOverlap(tripE1, tripE2);
        
        // Verify: Expected Output: false, no common stop
        assertFalse("Should return false when stop names differ only in case", result);
    }
    
    // Helper method to create Stop objects
    private Stop createStop(String stationName) {
        Stop stop = new Stop();
        stop.setStopStation(stationName);
        return stop;
    }
}