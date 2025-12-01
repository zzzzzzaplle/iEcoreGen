import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private OnlineRideshareSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        Trip tripA1 = new Trip();
        tripA1.setDriverId("D3");
        Stop stop1A1 = new Stop();
        stop1A1.setStopStation("CityX");
        Stop stop2A1 = new Stop();
        stop2A1.setStopStation("CityY");
        tripA1.addStop(stop1A1);
        tripA1.addStop(stop2A1);
        
        // Setup Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        Trip tripA2 = new Trip();
        tripA2.setDriverId("D3");
        Stop stop1A2 = new Stop();
        stop1A2.setStopStation("CityY");
        Stop stop2A2 = new Stop();
        stop2A2.setStopStation("CityZ");
        tripA2.addStop(stop1A2);
        tripA2.addStop(stop2A2);
        
        // Check stop overlap - should be true with common stop "CityY"
        boolean result = system.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        assertTrue("Trips should share common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        Trip tripB1 = new Trip();
        tripB1.setDriverId("D4");
        Stop stop1B1 = new Stop();
        stop1B1.setStopStation("CityM");
        Stop stop2B1 = new Stop();
        stop2B1.setStopStation("CityN");
        tripB1.addStop(stop1B1);
        tripB1.addStop(stop2B1);
        
        // Setup Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        Trip tripB2 = new Trip();
        tripB2.setDriverId("D4");
        Stop stop1B2 = new Stop();
        stop1B2.setStopStation("CityP");
        Stop stop2B2 = new Stop();
        stop2B2.setStopStation("CityQ");
        tripB2.addStop(stop1B2);
        tripB2.addStop(stop2B2);
        
        // Check stop overlap - should be false with no common stops
        boolean result = system.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        assertFalse("Trips should not share any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup Trip C1 with no stops
        Trip tripC1 = new Trip();
        tripC1.setDriverId("D5");
        
        // Setup Trip C2 with no stops
        Trip tripC2 = new Trip();
        tripC2.setDriverId("D5");
        
        // Check stop overlap - should be false with empty stop lists
        boolean result = system.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        assertFalse("Trips with empty stop lists should not overlap", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup Trip D1 with stops: [Stop("A"), Stop("B"), Stop("C")]
        Trip tripD1 = new Trip();
        tripD1.setDriverId("D6");
        Stop stop1D1 = new Stop();
        stop1D1.setStopStation("A");
        Stop stop2D1 = new Stop();
        stop2D1.setStopStation("B");
        Stop stop3D1 = new Stop();
        stop3D1.setStopStation("C");
        tripD1.addStop(stop1D1);
        tripD1.addStop(stop2D1);
        tripD1.addStop(stop3D1);
        
        // Setup Trip D2 with stops: [Stop("X"), Stop("B"), Stop("C")]
        Trip tripD2 = new Trip();
        tripD2.setDriverId("D6");
        Stop stop1D2 = new Stop();
        stop1D2.setStopStation("X");
        Stop stop2D2 = new Stop();
        stop2D2.setStopStation("B");
        Stop stop3D2 = new Stop();
        stop3D2.setStopStation("C");
        tripD2.addStop(stop1D2);
        tripD2.addStop(stop2D2);
        tripD2.addStop(stop3D2);
        
        // Check stop overlap - should be true with common stops "B" and "C"
        boolean result = system.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        assertTrue("Trips should share common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup Trip E1 with stop: [Stop("Boston")]
        Trip tripE1 = new Trip();
        tripE1.setDriverId("D7");
        Stop stopE1 = new Stop();
        stopE1.setStopStation("Boston");
        tripE1.addStop(stopE1);
        
        // Setup Trip E2 with stop: [Stop("boston")]
        Trip tripE2 = new Trip();
        tripE2.setDriverId("D7");
        Stop stopE2 = new Stop();
        stopE2.setStopStation("boston");
        tripE2.addStop(stopE2);
        
        // Check stop overlap - should be false due to case sensitivity
        boolean result = system.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        assertFalse("Case-sensitive comparison should return false for 'Boston' vs 'boston'", result);
    }
}