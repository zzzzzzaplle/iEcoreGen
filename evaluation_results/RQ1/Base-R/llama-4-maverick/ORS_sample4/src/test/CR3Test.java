import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
        // Test Case 1: "Shared stop in indirect trips"
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        //        Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        
        Trip tripA1 = new Trip("A1", "D3", "StartA", "EndA", 4, 
                              LocalDateTime.parse("2024-09-20 10:00:00", formatter),
                              LocalDateTime.parse("2024-09-20 12:00:00", formatter), 50.0);
        tripA1.setStops(Arrays.asList(new Stop("CityX"), new Stop("CityY")));
        
        Trip tripA2 = new Trip("A2", "D3", "StartB", "EndB", 4,
                              LocalDateTime.parse("2024-09-21 10:00:00", formatter),
                              LocalDateTime.parse("2024-09-21 12:00:00", formatter), 60.0);
        tripA2.setStops(Arrays.asList(new Stop("CityY"), new Stop("CityZ")));
        
        // Execute the method under test
        boolean result = system.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        
        // Verify the result - should be true with common stop "CityY"
        assertTrue("Trips should share common stop CityY", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Test Case 2: "No common stops in indirect trips"
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        //        Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        
        Trip tripB1 = new Trip("B1", "D4", "StartC", "EndC", 4,
                              LocalDateTime.parse("2024-09-22 10:00:00", formatter),
                              LocalDateTime.parse("2024-09-22 12:00:00", formatter), 70.0);
        tripB1.setStops(Arrays.asList(new Stop("CityM"), new Stop("CityN")));
        
        Trip tripB2 = new Trip("B2", "D4", "StartD", "EndD", 4,
                              LocalDateTime.parse("2024-09-23 10:00:00", formatter),
                              LocalDateTime.parse("2024-09-23 12:00:00", formatter), 80.0);
        tripB2.setStops(Arrays.asList(new Stop("CityP"), new Stop("CityQ")));
        
        // Execute the method under test
        boolean result = system.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        
        // Verify the result - should be false with no common stops
        assertFalse("Trips should not share any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Test Case 3: "Empty stop lists comparison"
        // Setup: Trip C1 has no stops, Trip C2 has no stops
        
        Trip tripC1 = new Trip("C1", "D5", "StartE", "EndE", 4,
                              LocalDateTime.parse("2024-09-24 10:00:00", formatter),
                              LocalDateTime.parse("2024-09-24 12:00:00", formatter), 90.0);
        // No stops set - uses empty list from constructor
        
        Trip tripC2 = new Trip("C2", "D5", "StartF", "EndF", 4,
                              LocalDateTime.parse("2024-09-25 10:00:00", formatter),
                              LocalDateTime.parse("2024-09-25 12:00:00", formatter), 100.0);
        // No stops set - uses empty list from constructor
        
        // Execute the method under test
        boolean result = system.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        
        // Verify the result - should be false with empty stop lists
        assertFalse("Empty stop lists should return false", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Test Case 4: "Multiple shared stops detection"
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        //        Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        
        Trip tripD1 = new Trip("D1", "D6", "StartG", "EndG", 4,
                              LocalDateTime.parse("2024-09-26 10:00:00", formatter),
                              LocalDateTime.parse("2024-09-26 12:00:00", formatter), 110.0);
        tripD1.setStops(Arrays.asList(new Stop("A"), new Stop("B"), new Stop("C")));
        
        Trip tripD2 = new Trip("D2", "D6", "StartH", "EndH", 4,
                              LocalDateTime.parse("2024-09-27 10:00:00", formatter),
                              LocalDateTime.parse("2024-09-27 12:00:00", formatter), 120.0);
        tripD2.setStops(Arrays.asList(new Stop("X"), new Stop("B"), new Stop("C")));
        
        // Execute the method under test
        boolean result = system.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        
        // Verify the result - should be true with multiple common stops (B and C)
        assertTrue("Trips should share multiple common stops B and C", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Test Case 5: "Case-sensitive stop comparison"
        // Setup: Trip E1 stops: [Stop("Boston")]
        //        Trip E2 stops: [Stop("boston")]
        
        Trip tripE1 = new Trip("E1", "D7", "StartI", "EndI", 4,
                              LocalDateTime.parse("2024-09-28 10:00:00", formatter),
                              LocalDateTime.parse("2024-09-28 12:00:00", formatter), 130.0);
        tripE1.setStops(Arrays.asList(new Stop("Boston")));
        
        Trip tripE2 = new Trip("E2", "D7", "StartJ", "EndJ", 4,
                              LocalDateTime.parse("2024-09-29 10:00:00", formatter),
                              LocalDateTime.parse("2024-09-29 12:00:00", formatter), 140.0);
        tripE2.setStops(Arrays.asList(new Stop("boston")));
        
        // Execute the method under test
        boolean result = system.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        
        // Verify the result - should be false due to case sensitivity
        assertFalse("Case-sensitive comparison should return false for 'Boston' vs 'boston'", result);
    }
}