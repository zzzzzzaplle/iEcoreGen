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
        // Setup: Create Trip A1 with stops [CityX, CityY]
        Trip tripA1 = new Trip();
        Stop stop1A1 = new Stop();
        stop1A1.setStopStation("CityX");
        Stop stop2A1 = new Stop();
        stop2A1.setStopStation("CityY");
        tripA1.getStops().add(stop1A1);
        tripA1.getStops().add(stop2A1);
        
        // Setup: Create Trip A2 with stops [CityY, CityZ]
        Trip tripA2 = new Trip();
        Stop stop1A2 = new Stop();
        stop1A2.setStopStation("CityY");
        Stop stop2A2 = new Stop();
        stop2A2.setStopStation("CityZ");
        tripA2.getStops().add(stop1A2);
        tripA2.getStops().add(stop2A2);
        
        // Execute: Check stop overlap between Trip A1 and A2
        boolean result = driver.checkStopOverlap(tripA1, tripA2);
        
        // Verify: Expected true, common stop: "CityY"
        assertTrue("Trips should have common stop CityY", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Create Trip B1 with stops [CityM, CityN]
        Trip tripB1 = new Trip();
        Stop stop1B1 = new Stop();
        stop1B1.setStopStation("CityM");
        Stop stop2B1 = new Stop();
        stop2B1.setStopStation("CityN");
        tripB1.getStops().add(stop1B1);
        tripB1.getStops().add(stop2B1);
        
        // Setup: Create Trip B2 with stops [CityP, CityQ]
        Trip tripB2 = new Trip();
        Stop stop1B2 = new Stop();
        stop1B2.setStopStation("CityP");
        Stop stop2B2 = new Stop();
        stop2B2.setStopStation("CityQ");
        tripB2.getStops().add(stop1B2);
        tripB2.getStops().add(stop2B2);
        
        // Execute: Check stop overlap between Trip B1 and B2
        boolean result = driver.checkStopOverlap(tripB1, tripB2);
        
        // Verify: Expected false, no common stop
        assertFalse("Trips should not have any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Create Trip C1 with no stops
        Trip tripC1 = new Trip();
        
        // Setup: Create Trip C2 with no stops
        Trip tripC2 = new Trip();
        
        // Execute: Check stop overlap between Trip C1 and C2
        boolean result = driver.checkStopOverlap(tripC1, tripC2);
        
        // Verify: Expected false, tests empty stop handling
        assertFalse("Empty stop lists should return false", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Create Trip D1 with stops [A, B, C]
        Trip tripD1 = new Trip();
        Stop stop1D1 = new Stop();
        stop1D1.setStopStation("A");
        Stop stop2D1 = new Stop();
        stop2D1.setStopStation("B");
        Stop stop3D1 = new Stop();
        stop3D1.setStopStation("C");
        tripD1.getStops().add(stop1D1);
        tripD1.getStops().add(stop2D1);
        tripD1.getStops().add(stop3D1);
        
        // Setup: Create Trip D2 with stops [X, B, C]
        Trip tripD2 = new Trip();
        Stop stop1D2 = new Stop();
        stop1D2.setStopStation("X");
        Stop stop2D2 = new Stop();
        stop2D2.setStopStation("B");
        Stop stop3D2 = new Stop();
        stop3D2.setStopStation("C");
        tripD2.getStops().add(stop1D2);
        tripD2.getStops().add(stop2D2);
        tripD2.getStops().add(stop3D2);
        
        // Execute: Check stop overlap between Trip D1 and D2
        boolean result = driver.checkStopOverlap(tripD1, tripD2);
        
        // Verify: Expected true, common stops: B and C
        assertTrue("Trips should have common stops B and C", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Create Trip E1 with stop [Boston]
        Trip tripE1 = new Trip();
        Stop stopE1 = new Stop();
        stopE1.setStopStation("Boston");
        tripE1.getStops().add(stopE1);
        
        // Setup: Create Trip E2 with stop [boston]
        Trip tripE2 = new Trip();
        Stop stopE2 = new Stop();
        stopE2.setStopStation("boston");
        tripE2.getStops().add(stopE2);
        
        // Execute: Check stop overlap between Trip E1 and E2
        boolean result = driver.checkStopOverlap(tripE1, tripE2);
        
        // Verify: Expected false, no common stop due to case sensitivity
        assertFalse("Case-sensitive comparison should return false", result);
    }
}