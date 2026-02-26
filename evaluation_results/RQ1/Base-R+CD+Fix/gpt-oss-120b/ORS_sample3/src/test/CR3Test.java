import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Arrays;

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
        Stop stopA1_1 = new Stop();
        stopA1_1.setStopStation("CityX");
        Stop stopA1_2 = new Stop();
        stopA1_2.setStopStation("CityY");
        tripA1.setStops(Arrays.asList(stopA1_1, stopA1_2));
        
        // Setup: Create Trip A2 with stops [CityY, CityZ]
        Trip tripA2 = new Trip();
        Stop stopA2_1 = new Stop();
        stopA2_1.setStopStation("CityY");
        Stop stopA2_2 = new Stop();
        stopA2_2.setStopStation("CityZ");
        tripA2.setStops(Arrays.asList(stopA2_1, stopA2_2));
        
        // Execute: Check stop overlap between Trip A1 and A2
        boolean result = driver.checkStopOverlap(tripA1, tripA2);
        
        // Verify: Should return true due to common stop "CityY"
        assertTrue("Expected true for shared stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Create Trip B1 with stops [CityM, CityN]
        Trip tripB1 = new Trip();
        Stop stopB1_1 = new Stop();
        stopB1_1.setStopStation("CityM");
        Stop stopB1_2 = new Stop();
        stopB1_2.setStopStation("CityN");
        tripB1.setStops(Arrays.asList(stopB1_1, stopB1_2));
        
        // Setup: Create Trip B2 with stops [CityP, CityQ]
        Trip tripB2 = new Trip();
        Stop stopB2_1 = new Stop();
        stopB2_1.setStopStation("CityP");
        Stop stopB2_2 = new Stop();
        stopB2_2.setStopStation("CityQ");
        tripB2.setStops(Arrays.asList(stopB2_1, stopB2_2));
        
        // Execute: Check stop overlap between Trip B1 and B2
        boolean result = driver.checkStopOverlap(tripB1, tripB2);
        
        // Verify: Should return false as no common stops exist
        assertFalse("Expected false for no common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Create Trip C1 with empty stop list
        Trip tripC1 = new Trip();
        tripC1.setStops(Arrays.asList());
        
        // Setup: Create Trip C2 with empty stop list
        Trip tripC2 = new Trip();
        tripC2.setStops(Arrays.asList());
        
        // Execute: Check stop overlap between Trip C1 and C2
        boolean result = driver.checkStopOverlap(tripC1, tripC2);
        
        // Verify: Should return false for empty stop lists
        assertFalse("Expected false for empty stop lists", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Create Trip D1 with stops [A, B, C]
        Trip tripD1 = new Trip();
        Stop stopD1_1 = new Stop();
        stopD1_1.setStopStation("A");
        Stop stopD1_2 = new Stop();
        stopD1_2.setStopStation("B");
        Stop stopD1_3 = new Stop();
        stopD1_3.setStopStation("C");
        tripD1.setStops(Arrays.asList(stopD1_1, stopD1_2, stopD1_3));
        
        // Setup: Create Trip D2 with stops [X, B, C]
        Trip tripD2 = new Trip();
        Stop stopD2_1 = new Stop();
        stopD2_1.setStopStation("X");
        Stop stopD2_2 = new Stop();
        stopD2_2.setStopStation("B");
        Stop stopD2_3 = new Stop();
        stopD2_3.setStopStation("C");
        tripD2.setStops(Arrays.asList(stopD2_1, stopD2_2, stopD2_3));
        
        // Execute: Check stop overlap between Trip D1 and D2
        boolean result = driver.checkStopOverlap(tripD1, tripD2);
        
        // Verify: Should return true due to common stops "B" and "C"
        assertTrue("Expected true for multiple shared stops 'B' and 'C'", result);
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
        
        // Execute: Check stop overlap between Trip E1 and E2
        boolean result = driver.checkStopOverlap(tripE1, tripE2);
        
        // Verify: Should return false due to case sensitivity
        assertFalse("Expected false for case-sensitive comparison", result);
    }
}