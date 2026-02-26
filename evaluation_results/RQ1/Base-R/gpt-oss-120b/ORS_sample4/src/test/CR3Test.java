import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class CR3Test {
    
    private Driver driverD3;
    private Driver driverD4;
    
    @Before
    public void setUp() {
        // Initialize drivers for test cases
        driverD3 = new Driver("D3", "d3@example.com", "1234567890");
        driverD4 = new Driver("D4", "d4@example.com", "0987654321");
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        List<Stop> stopsA1 = new ArrayList<>();
        stopsA1.add(new Stop("CityX"));
        stopsA1.add(new Stop("CityY"));
        IndirectTrip tripA1 = new IndirectTrip("A1", driverD3, "StartA", "EndA", 
            5, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50.0, stopsA1);
        
        // Setup: Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        List<Stop> stopsA2 = new ArrayList<>();
        stopsA2.add(new Stop("CityY"));
        stopsA2.add(new Stop("CityZ"));
        IndirectTrip tripA2 = new IndirectTrip("A2", driverD3, "StartB", "EndB", 
            5, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50.0, stopsA2);
        
        // Expected Output: true, common stop: "CityY"
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        assertTrue("Trips should share common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        List<Stop> stopsB1 = new ArrayList<>();
        stopsB1.add(new Stop("CityM"));
        stopsB1.add(new Stop("CityN"));
        IndirectTrip tripB1 = new IndirectTrip("B1", driverD4, "StartC", "EndC", 
            5, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50.0, stopsB1);
        
        // Setup: Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        List<Stop> stopsB2 = new ArrayList<>();
        stopsB2.add(new Stop("CityP"));
        stopsB2.add(new Stop("CityQ"));
        IndirectTrip tripB2 = new IndirectTrip("B2", driverD4, "StartD", "EndD", 
            5, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50.0, stopsB2);
        
        // Expected Output: false, no common stop
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        assertFalse("Trips should not share any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 has no stops
        IndirectTrip tripC1 = new IndirectTrip("C1", driverD3, "StartE", "EndE", 
            5, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50.0, new ArrayList<>());
        
        // Setup: Trip C2 has no stops
        IndirectTrip tripC2 = new IndirectTrip("C2", driverD3, "StartF", "EndF", 
            5, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50.0, new ArrayList<>());
        
        // Expected Output: false. Tests empty stop handling
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        assertFalse("Trips with empty stop lists should not overlap", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        List<Stop> stopsD1 = new ArrayList<>();
        stopsD1.add(new Stop("A"));
        stopsD1.add(new Stop("B"));
        stopsD1.add(new Stop("C"));
        IndirectTrip tripD1 = new IndirectTrip("D1", driverD3, "StartG", "EndG", 
            5, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50.0, stopsD1);
        
        // Setup: Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        List<Stop> stopsD2 = new ArrayList<>();
        stopsD2.add(new Stop("X"));
        stopsD2.add(new Stop("B"));
        stopsD2.add(new Stop("C"));
        IndirectTrip tripD2 = new IndirectTrip("D2", driverD3, "StartH", "EndH", 
            5, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50.0, stopsD2);
        
        // Expected Output: true, common stop: Stop("B"), Stop("C")
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        assertTrue("Trips should share multiple common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 stops: [Stop("Boston")]
        List<Stop> stopsE1 = new ArrayList<>();
        stopsE1.add(new Stop("Boston"));
        IndirectTrip tripE1 = new IndirectTrip("E1", driverD3, "StartI", "EndI", 
            5, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50.0, stopsE1);
        
        // Setup: Trip E2 stops: [Stop("boston")]
        List<Stop> stopsE2 = new ArrayList<>();
        stopsE2.add(new Stop("boston"));
        IndirectTrip tripE2 = new IndirectTrip("E2", driverD3, "StartJ", "EndJ", 
            5, LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50.0, stopsE2);
        
        // Expected Output: false, no common stop
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        assertFalse("Case-sensitive comparison should return false for 'Boston' vs 'boston'", result);
    }
}