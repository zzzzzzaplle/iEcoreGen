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
        // Setup
        Trip tripA1 = new Trip();
        Stop stop1A1 = new Stop();
        stop1A1.setStopStation("CityX");
        Stop stop2A1 = new Stop();
        stop2A1.setStopStation("CityY");
        tripA1.setStops(Arrays.asList(stop1A1, stop2A1));
        
        Trip tripA2 = new Trip();
        Stop stop1A2 = new Stop();
        stop1A2.setStopStation("CityY");
        Stop stop2A2 = new Stop();
        stop2A2.setStopStation("CityZ");
        tripA2.setStops(Arrays.asList(stop1A2, stop2A2));
        
        // Execute
        boolean result = driver.checkStopOverlap(tripA1, tripA2);
        
        // Verify
        assertTrue("Should return true for shared stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup
        Trip tripB1 = new Trip();
        Stop stop1B1 = new Stop();
        stop1B1.setStopStation("CityM");
        Stop stop2B1 = new Stop();
        stop2B1.setStopStation("CityN");
        tripB1.setStops(Arrays.asList(stop1B1, stop2B1));
        
        Trip tripB2 = new Trip();
        Stop stop1B2 = new Stop();
        stop1B2.setStopStation("CityP");
        Stop stop2B2 = new Stop();
        stop2B2.setStopStation("CityQ");
        tripB2.setStops(Arrays.asList(stop1B2, stop2B2));
        
        // Execute
        boolean result = driver.checkStopOverlap(tripB1, tripB2);
        
        // Verify
        assertFalse("Should return false for no common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup
        Trip tripC1 = new Trip();
        tripC1.setStops(Arrays.asList());
        
        Trip tripC2 = new Trip();
        tripC2.setStops(Arrays.asList());
        
        // Execute
        boolean result = driver.checkStopOverlap(tripC1, tripC2);
        
        // Verify
        assertFalse("Should return false for empty stop lists", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup
        Trip tripD1 = new Trip();
        Stop stop1D1 = new Stop();
        stop1D1.setStopStation("A");
        Stop stop2D1 = new Stop();
        stop2D1.setStopStation("B");
        Stop stop3D1 = new Stop();
        stop3D1.setStopStation("C");
        tripD1.setStops(Arrays.asList(stop1D1, stop2D1, stop3D1));
        
        Trip tripD2 = new Trip();
        Stop stop1D2 = new Stop();
        stop1D2.setStopStation("X");
        Stop stop2D2 = new Stop();
        stop2D2.setStopStation("B");
        Stop stop3D2 = new Stop();
        stop3D2.setStopStation("C");
        tripD2.setStops(Arrays.asList(stop1D2, stop2D2, stop3D2));
        
        // Execute
        boolean result = driver.checkStopOverlap(tripD1, tripD2);
        
        // Verify
        assertTrue("Should return true for multiple shared stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup
        Trip tripE1 = new Trip();
        Stop stop1E1 = new Stop();
        stop1E1.setStopStation("Boston");
        tripE1.setStops(Arrays.asList(stop1E1));
        
        Trip tripE2 = new Trip();
        Stop stop1E2 = new Stop();
        stop1E2.setStopStation("boston");
        tripE2.setStops(Arrays.asList(stop1E2));
        
        // Execute
        boolean result = driver.checkStopOverlap(tripE1, tripE2);
        
        // Verify
        assertFalse("Should return false for case-sensitive stop comparison", result);
    }
}