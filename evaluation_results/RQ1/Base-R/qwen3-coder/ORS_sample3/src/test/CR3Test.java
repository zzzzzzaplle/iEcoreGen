import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;

public class CR3Test {
    
    private OnlineRideshareSystem system;
    private Driver driver;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        driver = new Driver();
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Create Trip A1 with stops [CityX, CityY]
        Trip tripA1 = new Trip();
        tripA1.setStops(Arrays.asList("CityX", "CityY"));
        
        // Setup: Create Trip A2 with stops [CityY, CityZ]
        Trip tripA2 = new Trip();
        tripA2.setStops(Arrays.asList("CityY", "CityZ"));
        
        // Execute: Check stop overlap between Trip A1 and A2
        boolean result = system.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        
        // Verify: Expected true, common stop "CityY"
        assertTrue("Should return true when trips share common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Create Trip B1 with stops [CityM, CityN]
        Trip tripB1 = new Trip();
        tripB1.setStops(Arrays.asList("CityM", "CityN"));
        
        // Setup: Create Trip B2 with stops [CityP, CityQ]
        Trip tripB2 = new Trip();
        tripB2.setStops(Arrays.asList("CityP", "CityQ"));
        
        // Execute: Check stop overlap between Trip B1 and B2
        boolean result = system.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        
        // Verify: Expected false, no common stops
        assertFalse("Should return false when trips have no common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Create Trip C1 with empty stops list
        Trip tripC1 = new Trip();
        tripC1.setStops(Arrays.asList());
        
        // Setup: Create Trip C2 with empty stops list
        Trip tripC2 = new Trip();
        tripC2.setStops(Arrays.asList());
        
        // Execute: Check stop overlap between Trip C1 and C2
        boolean result = system.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        
        // Verify: Expected false, empty stop lists
        assertFalse("Should return false when both trips have empty stop lists", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Create Trip D1 with stops [A, B, C]
        Trip tripD1 = new Trip();
        tripD1.setStops(Arrays.asList("A", "B", "C"));
        
        // Setup: Create Trip D2 with stops [X, B, C]
        Trip tripD2 = new Trip();
        tripD2.setStops(Arrays.asList("X", "B", "C"));
        
        // Execute: Check stop overlap between Trip D1 and D2
        boolean result = system.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        
        // Verify: Expected true, common stops B and C
        assertTrue("Should return true when trips share multiple common stops", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Create Trip E1 with stop [Boston]
        Trip tripE1 = new Trip();
        tripE1.setStops(Arrays.asList("Boston"));
        
        // Setup: Create Trip E2 with stop [boston]
        Trip tripE2 = new Trip();
        tripE2.setStops(Arrays.asList("boston"));
        
        // Execute: Check stop overlap between Trip E1 and E2
        boolean result = system.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        
        // Verify: Expected false, case-sensitive comparison
        assertFalse("Should return false when stops differ only in case", result);
    }
}