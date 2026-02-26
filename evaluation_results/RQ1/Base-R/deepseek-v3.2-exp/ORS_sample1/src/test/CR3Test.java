import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private Trip trip1;
    private Trip trip2;
    private User driver1;
    private User driver2;
    
    @Before
    public void setUp() {
        // Initialize common test objects
        driver1 = new User();
        driver1.setId("D3");
        
        driver2 = new User();
        driver2.setId("D4");
        
        trip1 = new Trip();
        trip2 = new Trip();
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        trip1.setDriver(driver1);
        List<Stop> stops1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("CityX");
        Stop stop2 = new Stop();
        stop2.setStopStation("CityY");
        stops1.add(stop1);
        stops1.add(stop2);
        trip1.setStops(stops1);
        
        // Setup: Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        trip2.setDriver(driver1);
        List<Stop> stops2 = new ArrayList<>();
        Stop stop3 = new Stop();
        stop3.setStopStation("CityY");
        Stop stop4 = new Stop();
        stop4.setStopStation("CityZ");
        stops2.add(stop3);
        stops2.add(stop4);
        trip2.setStops(stops2);
        
        // Execute: Check stop overlap between Trip A1 and A2
        boolean result = trip1.checkStopOverlapForIndirectTrips(trip2);
        
        // Verify: Expected Output: true, common stop: "CityY"
        assertTrue("Should return true when trips share common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        trip1.setDriver(driver2);
        List<Stop> stops1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("CityM");
        Stop stop2 = new Stop();
        stop2.setStopStation("CityN");
        stops1.add(stop1);
        stops1.add(stop2);
        trip1.setStops(stops1);
        
        // Setup: Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        trip2.setDriver(driver2);
        List<Stop> stops2 = new ArrayList<>();
        Stop stop3 = new Stop();
        stop3.setStopStation("CityP");
        Stop stop4 = new Stop();
        stop4.setStopStation("CityQ");
        stops2.add(stop3);
        stops2.add(stop4);
        trip2.setStops(stops2);
        
        // Execute: Check stop overlap between Trip B1 and B2
        boolean result = trip1.checkStopOverlapForIndirectTrips(trip2);
        
        // Verify: Expected Output: false, no common stop
        assertFalse("Should return false when trips have no common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 has no stops
        trip1.setDriver(driver1);
        trip1.setStops(new ArrayList<>());
        
        // Setup: Trip C2 has no stops
        trip2.setDriver(driver1);
        trip2.setStops(new ArrayList<>());
        
        // Execute: Check stop overlap between Trip C1 and C2
        boolean result = trip1.checkStopOverlapForIndirectTrips(trip2);
        
        // Verify: Expected Output: false. Tests empty stop handling
        assertFalse("Should return false when both trips have empty stop lists", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        trip1.setDriver(driver1);
        List<Stop> stops1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("A");
        Stop stop2 = new Stop();
        stop2.setStopStation("B");
        Stop stop3 = new Stop();
        stop3.setStopStation("C");
        stops1.add(stop1);
        stops1.add(stop2);
        stops1.add(stop3);
        trip1.setStops(stops1);
        
        // Setup: Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        trip2.setDriver(driver1);
        List<Stop> stops2 = new ArrayList<>();
        Stop stop4 = new Stop();
        stop4.setStopStation("X");
        Stop stop5 = new Stop();
        stop5.setStopStation("B");
        Stop stop6 = new Stop();
        stop6.setStopStation("C");
        stops2.add(stop4);
        stops2.add(stop5);
        stops2.add(stop6);
        trip2.setStops(stops2);
        
        // Execute: Check stop overlap between Trip D1 and D2
        boolean result = trip1.checkStopOverlapForIndirectTrips(trip2);
        
        // Verify: Expected Output: true, common stop: Stop("B"), Stop("C")
        assertTrue("Should return true when trips share multiple common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 stops: [Stop("Boston")]
        trip1.setDriver(driver1);
        List<Stop> stops1 = new ArrayList<>();
        Stop stop1 = new Stop();
        stop1.setStopStation("Boston");
        stops1.add(stop1);
        trip1.setStops(stops1);
        
        // Setup: Trip E2 stops: [Stop("boston")]
        trip2.setDriver(driver1);
        List<Stop> stops2 = new ArrayList<>();
        Stop stop2 = new Stop();
        stop2.setStopStation("boston");
        stops2.add(stop2);
        trip2.setStops(stops2);
        
        // Execute: Check stop overlap between Trip E1 and E2
        boolean result = trip1.checkStopOverlapForIndirectTrips(trip2);
        
        // Verify: Expected Output: false, no common stop (case-sensitive comparison)
        assertFalse("Should return false when stop names differ only in case", result);
    }
}