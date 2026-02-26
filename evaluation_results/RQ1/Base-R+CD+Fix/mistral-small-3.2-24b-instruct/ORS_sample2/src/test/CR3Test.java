import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    private Driver driver;
    private Trip tripA1, tripA2, tripB1, tripB2, tripC1, tripC2, tripD1, tripD2, tripE1, tripE2;
    
    @Before
    public void setUp() {
        driver = new Driver();
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        tripA1 = new Trip();
        List<Stop> stopsA1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("CityX");
        Stop stop2 = new Stop();
        stop2.setStopStation("CityY");
        stopsA1.add(stop1);
        stopsA1.add(stop2);
        tripA1.setStops(stopsA1);
        
        // Setup: Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        tripA2 = new Trip();
        List<Stop> stopsA2 = new ArrayList<>();
        Stop stop3 = new Stop();
        stop3.setStopStation("CityY");
        Stop stop4 = new Stop();
        stop4.setStopStation("CityZ");
        stopsA2.add(stop3);
        stopsA2.add(stop4);
        tripA2.setStops(stopsA2);
        
        // Expected Output: true, common stop: "CityY"
        assertTrue("Trips should share common stop CityY", driver.checkStopOverlap(tripA1, tripA2));
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        tripB1 = new Trip();
        List<Stop> stopsB1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("CityM");
        Stop stop2 = new Stop();
        stop2.setStopStation("CityN");
        stopsB1.add(stop1);
        stopsB1.add(stop2);
        tripB1.setStops(stopsB1);
        
        // Setup: Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        tripB2 = new Trip();
        List<Stop> stopsB2 = new ArrayList<>();
        Stop stop3 = new Stop();
        stop3.setStopStation("CityP");
        Stop stop4 = new Stop();
        stop4.setStopStation("CityQ");
        stopsB2.add(stop3);
        stopsB2.add(stop4);
        tripB2.setStops(stopsB2);
        
        // Expected Output: false, no common stop
        assertFalse("Trips should not share any common stops", driver.checkStopOverlap(tripB1, tripB2));
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 has no stops
        tripC1 = new Trip();
        tripC1.setStops(new ArrayList<Stop>());
        
        // Setup: Trip C2 has no stops
        tripC2 = new Trip();
        tripC2.setStops(new ArrayList<Stop>());
        
        // Expected Output: false. Tests empty stop handling
        assertFalse("Empty stop lists should not overlap", driver.checkStopOverlap(tripC1, tripC2));
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        tripD1 = new Trip();
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
        
        // Setup: Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        tripD2 = new Trip();
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
        
        // Expected Output: true, common stop: Stop("B"), Stop("C")
        assertTrue("Trips should share multiple common stops B and C", driver.checkStopOverlap(tripD1, tripD2));
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 stops: [Stop("Boston")]
        tripE1 = new Trip();
        List<Stop> stopsE1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("Boston");
        stopsE1.add(stop1);
        tripE1.setStops(stopsE1);
        
        // Setup: Trip E2 stops: [Stop("boston")]
        tripE2 = new Trip();
        List<Stop> stopsE2 = new ArrayList<>();
        Stop stop2 = new Stop();
        stop2.setStopStation("boston");
        stopsE2.add(stop2);
        tripE2.setStops(stopsE2);
        
        // Expected Output: false, no common stop
        assertFalse("Case-sensitive stops should not be considered equal", driver.checkStopOverlap(tripE1, tripE2));
    }
}