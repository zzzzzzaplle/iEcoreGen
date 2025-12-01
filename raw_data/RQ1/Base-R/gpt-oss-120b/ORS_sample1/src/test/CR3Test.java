import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {

    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        //         Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        Driver driver = new Driver();
        
        Trip tripA1 = new Trip();
        Stop stop1 = new Stop();
        stop1.setStationName("CityX");
        Stop stop2 = new Stop();
        stop2.setStationName("CityY");
        tripA1.addStop(stop1);
        tripA1.addStop(stop2);
        
        Trip tripA2 = new Trip();
        Stop stop3 = new Stop();
        stop3.setStationName("CityY");
        Stop stop4 = new Stop();
        stop4.setStationName("CityZ");
        tripA2.addStop(stop3);
        tripA2.addStop(stop4);
        
        // Check stop overlap between Trip A1 and A2
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        
        // Expected Output: true, common stop: "CityY"
        assertTrue("Trips should share common stop 'CityY'", result);
    }

    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        //         Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        Driver driver = new Driver();
        
        Trip tripB1 = new Trip();
        Stop stop1 = new Stop();
        stop1.setStationName("CityM");
        Stop stop2 = new Stop();
        stop2.setStationName("CityN");
        tripB1.addStop(stop1);
        tripB1.addStop(stop2);
        
        Trip tripB2 = new Trip();
        Stop stop3 = new Stop();
        stop3.setStationName("CityP");
        Stop stop4 = new Stop();
        stop4.setStationName("CityQ");
        tripB2.addStop(stop3);
        tripB2.addStop(stop4);
        
        // Check stop overlap between Trip B1 and B2
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        
        // Expected Output: false, no common stop
        assertFalse("Trips should not share any common stops", result);
    }

    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 has no stops, Trip C2 has no stops
        Trip tripC1 = new Trip();
        Trip tripC2 = new Trip();
        
        // Check stop overlap between Trip C1 and C2
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        
        // Expected Output: false. Tests empty stop handling
        assertFalse("Trips with empty stop lists should not overlap", result);
    }

    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        //         Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        Trip tripD1 = new Trip();
        Stop stop1 = new Stop();
        stop1.setStationName("A");
        Stop stop2 = new Stop();
        stop2.setStationName("B");
        Stop stop3 = new Stop();
        stop3.setStationName("C");
        tripD1.addStop(stop1);
        tripD1.addStop(stop2);
        tripD1.addStop(stop3);
        
        Trip tripD2 = new Trip();
        Stop stop4 = new Stop();
        stop4.setStationName("X");
        Stop stop5 = new Stop();
        stop5.setStationName("B");
        Stop stop6 = new Stop();
        stop6.setStationName("C");
        tripD2.addStop(stop4);
        tripD2.addStop(stop5);
        tripD2.addStop(stop6);
        
        // Check stop overlap between Trip D1 and D2
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        
        // Expected Output: true, common stop: Stop("B"), Stop("C")
        assertTrue("Trips should share multiple common stops 'B' and 'C'", result);
    }

    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 stops: [Stop("Boston")]
        //         Trip E2 stops: [Stop("boston")]
        Trip tripE1 = new Trip();
        Stop stop1 = new Stop();
        stop1.setStationName("Boston");
        tripE1.addStop(stop1);
        
        Trip tripE2 = new Trip();
        Stop stop2 = new Stop();
        stop2.setStationName("boston");
        tripE2.addStop(stop2);
        
        // Check stop overlap between Trip E1 and E2
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        
        // Expected Output: false, no common stop
        assertFalse("Case-sensitive comparison should not match 'Boston' and 'boston'", result);
    }
}