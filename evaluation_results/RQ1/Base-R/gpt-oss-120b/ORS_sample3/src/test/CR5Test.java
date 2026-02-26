import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Driver driver;
    
    @Before
    public void setUp() {
        driver = new Driver();
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new DirectTrip("StationA", "StationB", 10, 
            LocalDateTime.of(2023, 12, 25, 9, 0),
            LocalDateTime.of(2023, 12, 25, 11, 0), 50.0, driver);
        driver.addTrip(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new DirectTrip("StationC", "StationD", 8,
            LocalDateTime.of(2023, 12, 25, 13, 0),
            LocalDateTime.of(2023, 12, 25, 15, 0), 60.0, driver);
        
        // Verify the trip can be posted (no time conflict)
        boolean result = driver.canPostTrip(newTrip);
        assertTrue("Trip should be postable with time gap", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = new DirectTrip("StationE", "StationF", 12,
            LocalDateTime.of(2023, 12, 25, 14, 0),
            LocalDateTime.of(2023, 12, 25, 16, 0), 70.0, driver);
        driver.addTrip(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new DirectTrip("StationG", "StationH", 6,
            LocalDateTime.of(2023, 12, 25, 14, 30),
            LocalDateTime.of(2023, 12, 25, 17, 30), 80.0, driver);
        
        // Verify the trip cannot be posted due to time conflict
        boolean result = driver.canPostTrip(newTrip);
        assertFalse("Trip should not be postable due to time conflict", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new DirectTrip("StationI", "StationJ", 15,
            LocalDateTime.of(2023, 12, 25, 9, 0),
            LocalDateTime.of(2023, 12, 25, 11, 0), 90.0, driver);
        driver.addTrip(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new DirectTrip("StationK", "StationL", 10,
            LocalDateTime.of(2023, 12, 25, 11, 0),
            LocalDateTime.of(2023, 12, 25, 13, 0), 100.0, driver);
        
        // Verify back-to-back trips are allowed (adjacent boundaries)
        boolean result = driver.canPostTrip(newTrip);
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        Trip existingTrip = new DirectTrip("StationM", "StationN", 20,
            LocalDateTime.of(2023, 12, 25, 10, 0),
            LocalDateTime.of(2023, 12, 25, 16, 0), 110.0, driver);
        driver.addTrip(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new DirectTrip("StationO", "StationP", 5,
            LocalDateTime.of(2023, 12, 25, 12, 0),
            LocalDateTime.of(2023, 12, 25, 14, 0), 120.0, driver);
        
        // Verify complete enclosure causes rejection
        boolean result = driver.canPostTrip(newTrip);
        assertFalse("Complete time enclosure should cause rejection", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has 3 existing trips
        Trip existingTrip1 = new DirectTrip("StationQ", "StationR", 18,
            LocalDateTime.of(2023, 12, 21, 8, 0),
            LocalDateTime.of(2023, 12, 21, 10, 0), 130.0, driver);
        Trip existingTrip2 = new DirectTrip("StationS", "StationT", 16,
            LocalDateTime.of(2023, 12, 21, 11, 0),
            LocalDateTime.of(2023, 12, 21, 13, 0), 140.0, driver);
        Trip existingTrip3 = new DirectTrip("StationU", "StationV", 14,
            LocalDateTime.of(2023, 12, 23, 14, 0),
            LocalDateTime.of(2023, 12, 23, 16, 0), 150.0, driver);
        
        driver.addTrip(existingTrip1);
        driver.addTrip(existingTrip2);
        driver.addTrip(existingTrip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30
        Trip newTrip = new DirectTrip("StationW", "StationX", 12,
            LocalDateTime.of(2023, 12, 21, 9, 30),
            LocalDateTime.of(2023, 12, 21, 10, 30), 160.0, driver);
        
        // Verify conflict with first two trips causes rejection
        boolean result = driver.canPostTrip(newTrip);
        assertFalse("Trip should conflict with multiple existing trips", result);
    }
}