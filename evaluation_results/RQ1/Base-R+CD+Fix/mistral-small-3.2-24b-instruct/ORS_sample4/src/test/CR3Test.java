import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

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
        tripA1.setStops(Arrays.asList(stop1A1, stop2A1));
        
        // Setup: Create Trip A2 with stops [CityY, CityZ]
        Trip tripA2 = new Trip();
        Stop stop1A2 = new Stop();
        stop1A2.setStopStation("CityY");
        Stop stop2A2 = new Stop();
        stop2A2.setStopStation("CityZ");
        tripA2.setStops(Arrays.asList(stop1A2, stop2A2));
        
        // Execute: Check stop overlap
        boolean result = driver.checkStopOverlap(tripA1, tripA2);
        
        // Verify: Expect true due to common stop "CityY"
        assertTrue("Trips should share common stop CityY", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Create Trip B1 with stops [CityM, CityN]
        Trip tripB1 = new Trip();
        Stop stop1B1 = new Stop();
        stop1B1.setStopStation("CityM");
        Stop stop2B1 = new Stop();
        stop2B1.setStopStation("CityN");
        tripB1.setStops(Arrays.asList(stop1B1, stop2B1));
        
        // Setup: Create Trip B2 with stops [CityP, CityQ]
        Trip tripB2 = new Trip();
        Stop stop1B2 = new Stop();
        stop1B2.setStopStation("CityP");
        Stop stop2B2 = new Stop();
        stop2B2.setStopStation("CityQ");
        tripB2.setStops(Arrays.asList(stop1B2, stop2B2));
        
        // Execute: Check stop overlap
        boolean result = driver.checkStopOverlap(tripB1, tripB2);
        
        // Verify: Expect false as no common stops
        assertFalse("Trips should not share any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Create Trip C1 with no stops
        Trip tripC1 = new Trip();
        tripC1.setStops(Arrays.asList());
        
        // Setup: Create Trip C2 with no stops
        Trip tripC2 = new Trip();
        tripC2.setStops(Arrays.asList());
        
        // Execute: Check stop overlap
        boolean result = driver.checkStopOverlap(tripC1, tripC2);
        
        // Verify: Expect false for empty stop lists
        assertFalse("Empty stop lists should not overlap", result);
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
        tripD1.setStops(Arrays.asList(stop1D1, stop2D1, stop3D1));
        
        // Setup: Create Trip D2 with stops [X, B, C]
        Trip tripD2 = new Trip();
        Stop stop1D2 = new Stop();
        stop1D2.setStopStation("X");
        Stop stop2D2 = new Stop();
        stop2D2.setStopStation("B");
        Stop stop3D2 = new Stop();
        stop3D2.setStopStation("C");
        tripD2.setStops(Arrays.asList(stop1D2, stop2D2, stop3D2));
        
        // Execute: Check stop overlap
        boolean result = driver.checkStopOverlap(tripD1, tripD2);
        
        // Verify: Expect true due to multiple common stops B and C
        assertTrue("Trips should share multiple common stops B and C", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Create Trip E1 with stop [Boston]
        Trip tripE1 = new Trip();
        Stop stopE1 = new Stop();
        stopE1.setStopStation("Boston");
        tripE1.setStops(Arrays.asList(stopE1));
        
        // Setup: Create Trip E2 with stop [boston]
        Trip tripE2 = new Trip();
        Stop stopE2 = new Stop();
        stopE2.setStopStation("boston");
        tripE2.setStops(Arrays.asList(stopE2));
        
        // Execute: Check stop overlap
        boolean result = driver.checkStopOverlap(tripE1, tripE2);
        
        // Verify: Expect false due to case sensitivity
        assertFalse("Case-sensitive stops should not be considered common", result);
    }
}