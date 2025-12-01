import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CR3Test {

    private Driver driverD3;
    private Driver driverD4;
    private IndirectTrip tripA1;
    private IndirectTrip tripA2;
    private IndirectTrip tripB1;
    private IndirectTrip tripB2;
    private IndirectTrip tripC1;
    private IndirectTrip tripC2;
    private IndirectTrip tripD1;
    private IndirectTrip tripD2;
    private IndirectTrip tripE1;
    private IndirectTrip tripE2;

    @Before
    public void setUp() {
        // Create drivers
        driverD3 = new Driver("D3", "d3@example.com", "1234567890");
        driverD4 = new Driver("D4", "d4@example.com", "0987654321");
        
        // Common trip parameters
        LocalDateTime departure = LocalDateTime.of(2024, 1, 1, 10, 0);
        LocalDateTime arrival = LocalDateTime.of(2024, 1, 1, 12, 0);
        int totalSeats = 4;
        double price = 50.0;
        
        // Setup for Test Case 1
        List<Stop> stopsA1 = Arrays.asList(new Stop("CityX"), new Stop("CityY"));
        List<Stop> stopsA2 = Arrays.asList(new Stop("CityY"), new Stop("CityZ"));
        tripA1 = new IndirectTrip("StartA", "EndA", totalSeats, departure, arrival, price, driverD3, stopsA1);
        tripA2 = new IndirectTrip("StartB", "EndB", totalSeats, departure, arrival, price, driverD3, stopsA2);
        
        // Setup for Test Case 2
        List<Stop> stopsB1 = Arrays.asList(new Stop("CityM"), new Stop("CityN"));
        List<Stop> stopsB2 = Arrays.asList(new Stop("CityP"), new Stop("CityQ"));
        tripB1 = new IndirectTrip("StartC", "EndC", totalSeats, departure, arrival, price, driverD4, stopsB1);
        tripB2 = new IndirectTrip("StartD", "EndD", totalSeats, departure, arrival, price, driverD4, stopsB2);
        
        // Setup for Test Case 3
        tripC1 = new IndirectTrip("StartE", "EndE", totalSeats, departure, arrival, price, driverD3, null);
        tripC2 = new IndirectTrip("StartF", "EndF", totalSeats, departure, arrival, price, driverD3, null);
        
        // Setup for Test Case 4
        List<Stop> stopsD1 = Arrays.asList(new Stop("A"), new Stop("B"), new Stop("C"));
        List<Stop> stopsD2 = Arrays.asList(new Stop("X"), new Stop("B"), new Stop("C"));
        tripD1 = new IndirectTrip("StartG", "EndG", totalSeats, departure, arrival, price, driverD3, stopsD1);
        tripD2 = new IndirectTrip("StartH", "EndH", totalSeats, departure, arrival, price, driverD3, stopsD2);
        
        // Setup for Test Case 5
        List<Stop> stopsE1 = Arrays.asList(new Stop("Boston"));
        List<Stop> stopsE2 = Arrays.asList(new Stop("boston"));
        tripE1 = new IndirectTrip("StartI", "EndI", totalSeats, departure, arrival, price, driverD3, stopsE1);
        tripE2 = new IndirectTrip("StartJ", "EndJ", totalSeats, departure, arrival, price, driverD3, stopsE2);
    }

    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Test Case 1: "Shared stop in indirect trips"
        // Input: Check stop overlap between Trip A1 and A2
        boolean result = IndirectTrip.haveCommonStops(tripA1, tripA2);
        
        // Expected Output: true, common stop: "CityY"
        assertTrue("Trips should share common stop 'CityY'", result);
    }

    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Test Case 2: "No common stops in indirect trips"
        // Input: Check stop overlap between Trip B1 and B2
        boolean result = IndirectTrip.haveCommonStops(tripB1, tripB2);
        
        // Expected Output: false, no common stop
        assertFalse("Trips should not share any common stops", result);
    }

    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Test Case 3: "Empty stop lists comparison"
        // Input: Check stop overlap between Trip C1 and C2
        boolean result = IndirectTrip.haveCommonStops(tripC1, tripC2);
        
        // Expected Output: false. Tests empty stop handling
        assertFalse("Trips with empty stop lists should not share common stops", result);
    }

    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Test Case 4: "Multiple shared stops detection"
        // Input: Check stop overlap between Trip D1 and D2
        boolean result = IndirectTrip.haveCommonStops(tripD1, tripD2);
        
        // Expected Output: true, common stop: Stop("B"), Stop("C")
        assertTrue("Trips should share multiple common stops 'B' and 'C'", result);
    }

    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Test Case 5: "Case-sensitive stop comparison"
        // Input: Check stop overlap between Trip E1 and E2
        boolean result = IndirectTrip.haveCommonStops(tripE1, tripE2);
        
        // Expected Output: false, no common stop
        assertFalse("Case-sensitive comparison should return false for 'Boston' vs 'boston'", result);
    }
}