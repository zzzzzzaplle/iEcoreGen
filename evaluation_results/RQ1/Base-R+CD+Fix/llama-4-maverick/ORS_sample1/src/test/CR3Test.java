import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Driver driver;
    
    @Before
    public void setUp() {
        driver = new Driver();
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        Trip tripA1 = new Trip();
        List<Stop> stopsA1 = new ArrayList<>();
        Stop stop1A1 = new Stop();
        stop1A1.setStopStation("CityX");
        stopsA1.add(stop1A1);
        Stop stop2A1 = new Stop();
        stop2A1.setStopStation("CityY");
        stopsA1.add(stop2A1);
        tripA1.setStops(stopsA1);
        
        // Setup Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        Trip tripA2 = new Trip();
        List<Stop> stopsA2 = new ArrayList<>();
        Stop stop1A2 = new Stop();
        stop1A2.setStopStation("CityY");
        stopsA2.add(stop1A2);
        Stop stop2A2 = new Stop();
        stop2A2.setStopStation("CityZ");
        stopsA2.add(stop2A2);
        tripA2.setStops(stopsA2);
        
        // Check stop overlap
        boolean result = driver.checkStopOverlap(tripA1, tripA2);
        
        // Verify result
        assertTrue("Expected true for shared stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        Trip tripB1 = new Trip();
        List<Stop> stopsB1 = new ArrayList<>();
        Stop stop1B1 = new Stop();
        stop1B1.setStopStation("CityM");
        stopsB1.add(stop1B1);
        Stop stop2B1 = new Stop();
        stop2B1.setStopStation("CityN");
        stopsB1.add(stop2B1);
        tripB1.setStops(stopsB1);
        
        // Setup Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        Trip tripB2 = new Trip();
        List<Stop> stopsB2 = new ArrayList<>();
        Stop stop1B2 = new Stop();
        stop1B2.setStopStation("CityP");
        stopsB2.add(stop1B2);
        Stop stop2B2 = new Stop();
        stop2B2.setStopStation("CityQ");
        stopsB2.add(stop2B2);
        tripB2.setStops(stopsB2);
        
        // Check stop overlap
        boolean result = driver.checkStopOverlap(tripB1, tripB2);
        
        // Verify result
        assertFalse("Expected false for no common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup Trip C1 with no stops
        Trip tripC1 = new Trip();
        tripC1.setStops(new ArrayList<Stop>());
        
        // Setup Trip C2 with no stops
        Trip tripC2 = new Trip();
        tripC2.setStops(new ArrayList<Stop>());
        
        // Check stop overlap
        boolean result = driver.checkStopOverlap(tripC1, tripC2);
        
        // Verify result
        assertFalse("Expected false for empty stop lists", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup Trip D1 with stops: [Stop("A"), Stop("B"), Stop("C")]
        Trip tripD1 = new Trip();
        List<Stop> stopsD1 = new ArrayList<>();
        Stop stop1D1 = new Stop();
        stop1D1.setStopStation("A");
        stopsD1.add(stop1D1);
        Stop stop2D1 = new Stop();
        stop2D1.setStopStation("B");
        stopsD1.add(stop2D1);
        Stop stop3D1 = new Stop();
        stop3D1.setStopStation("C");
        stopsD1.add(stop3D1);
        tripD1.setStops(stopsD1);
        
        // Setup Trip D2 with stops: [Stop("X"), Stop("B"), Stop("C")]
        Trip tripD2 = new Trip();
        List<Stop> stopsD2 = new ArrayList<>();
        Stop stop1D2 = new Stop();
        stop1D2.setStopStation("X");
        stopsD2.add(stop1D2);
        Stop stop2D2 = new Stop();
        stop2D2.setStopStation("B");
        stopsD2.add(stop2D2);
        Stop stop3D2 = new Stop();
        stop3D2.setStopStation("C");
        stopsD2.add(stop3D2);
        tripD2.setStops(stopsD2);
        
        // Check stop overlap
        boolean result = driver.checkStopOverlap(tripD1, tripD2);
        
        // Verify result
        assertTrue("Expected true for multiple shared stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup Trip E1 with stops: [Stop("Boston")]
        Trip tripE1 = new Trip();
        List<Stop> stopsE1 = new ArrayList<>();
        Stop stop1E1 = new Stop();
        stop1E1.setStopStation("Boston");
        stopsE1.add(stop1E1);
        tripE1.setStops(stopsE1);
        
        // Setup Trip E2 with stops: [Stop("boston")]
        Trip tripE2 = new Trip();
        List<Stop> stopsE2 = new ArrayList<>();
        Stop stop1E2 = new Stop();
        stop1E2.setStopStation("boston");
        stopsE2.add(stop1E2);
        tripE2.setStops(stopsE2);
        
        // Check stop overlap
        boolean result = driver.checkStopOverlap(tripE1, tripE2);
        
        // Verify result
        assertFalse("Expected false for case-sensitive comparison", result);
    }
}