import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private Driver driver;
    
    @Before
    public void setUp() {
        // Create a driver for all test cases
        driver = new Driver();
        driver.setID("D3");
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Create Trip A1 with stops [CityX, CityY]
        Trip tripA1 = new Trip();
        List<Stop> stopsA1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("CityX");
        Stop stop2 = new Stop();
        stop2.setStopStation("CityY");
        stopsA1.add(stop1);
        stopsA1.add(stop2);
        tripA1.setStops(stopsA1);
        
        // Setup: Create Trip A2 with stops [CityY, CityZ]
        Trip tripA2 = new Trip();
        List<Stop> stopsA2 = new ArrayList<>();
        Stop stop3 = new Stop();
        stop3.setStopStation("CityY");
        Stop stop4 = new Stop();
        stop4.setStopStation("CityZ");
        stopsA2.add(stop3);
        stopsA2.add(stop4);
        tripA2.setStops(stopsA2);
        
        // Execute: Check stop overlap between Trip A1 and A2
        boolean result = driver.checkStopOverlap(tripA1, tripA2);
        
        // Verify: Expected true, common stop: "CityY"
        assertTrue("Trips should share common stop CityY", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Create Trip B1 with stops [CityM, CityN]
        Trip tripB1 = new Trip();
        List<Stop> stopsB1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("CityM");
        Stop stop2 = new Stop();
        stop2.setStopStation("CityN");
        stopsB1.add(stop1);
        stopsB1.add(stop2);
        tripB1.setStops(stopsB1);
        
        // Setup: Create Trip B2 with stops [CityP, CityQ]
        Trip tripB2 = new Trip();
        List<Stop> stopsB2 = new ArrayList<>();
        Stop stop3 = new Stop();
        stop3.setStopStation("CityP");
        Stop stop4 = new Stop();
        stop4.setStopStation("CityQ");
        stopsB2.add(stop3);
        stopsB2.add(stop4);
        tripB2.setStops(stopsB2);
        
        // Execute: Check stop overlap between Trip B1 and B2
        boolean result = driver.checkStopOverlap(tripB1, tripB2);
        
        // Verify: Expected false, no common stop
        assertFalse("Trips should not share any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Create Trip C1 with empty stops
        Trip tripC1 = new Trip();
        tripC1.setStops(new ArrayList<Stop>());
        
        // Setup: Create Trip C2 with empty stops
        Trip tripC2 = new Trip();
        tripC2.setStops(new ArrayList<Stop>());
        
        // Execute: Check stop overlap between Trip C1 and C2
        boolean result = driver.checkStopOverlap(tripC1, tripC2);
        
        // Verify: Expected false, tests empty stop handling
        assertFalse("Empty stop lists should return false", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Create Trip D1 with stops [A, B, C]
        Trip tripD1 = new Trip();
        List<Stop> stopsD1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("A");
        Stop stop2 = new Stop();
        stop2.setStopStation("B");
        Stop stop3 = new Stop();
        stop3.setStopStation("C");
        stopsD1.add(stop1);
        stopsD1.add(stop2);
        stopsD1.add(stop3);
        tripD1.setStops(stopsD1);
        
        // Setup: Create Trip D2 with stops [X, B, C]
        Trip tripD2 = new Trip();
        List<Stop> stopsD2 = new ArrayList<>();
        Stop stop4 = new Stop();
        stop4.setStopStation("X");
        Stop stop5 = new Stop();
        stop5.setStopStation("B");
        Stop stop6 = new Stop();
        stop6.setStopStation("C");
        stopsD2.add(stop4);
        stopsD2.add(stop5);
        stopsD2.add(stop6);
        tripD2.setStops(stopsD2);
        
        // Execute: Check stop overlap between Trip D1 and D2
        boolean result = driver.checkStopOverlap(tripD1, tripD2);
        
        // Verify: Expected true, common stops: B and C
        assertTrue("Trips should share common stops B and C", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Create Trip E1 with stop [Boston]
        Trip tripE1 = new Trip();
        List<Stop> stopsE1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("Boston");
        stopsE1.add(stop1);
        tripE1.setStops(stopsE1);
        
        // Setup: Create Trip E2 with stop [boston]
        Trip tripE2 = new Trip();
        List<Stop> stopsE2 = new ArrayList<>();
        Stop stop2 = new Stop();
        stop2.setStopStation("boston");
        stopsE2.add(stop2);
        tripE2.setStops(stopsE2);
        
        // Execute: Check stop overlap between Trip E1 and E2
        boolean result = driver.checkStopOverlap(tripE1, tripE2);
        
        // Verify: Expected false, no common stop due to case sensitivity
        assertFalse("Case-sensitive comparison should return false", result);
    }
}