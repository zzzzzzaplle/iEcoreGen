import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    private OnlineRideshareSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00:00-11:00:00
        Trip existingTrip = new Trip("T1", "D5", "Station A", "Station B", 5, 
                                   LocalDateTime.parse("2023-12-25 09:00:00", formatter),
                                   LocalDateTime.parse("2023-12-25 11:00:00", formatter), 100.0);
        system.getTrips().add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00:00-15:00:00
        Trip newTrip = new Trip("T2", "D5", "Station C", "Station D", 5,
                               LocalDateTime.parse("2023-12-25 13:00:00", formatter),
                               LocalDateTime.parse("2023-12-25 15:00:00", formatter), 120.0);
        
        // Validate trip posting feasibility
        boolean result = system.validateTripPostingFeasibility("D5", newTrip);
        
        // Expected Output: true (no time conflict due to gap)
        assertTrue("Trip with time gap should be allowed", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00:00-16:00:00
        Trip existingTrip = new Trip("T1", "D6", "Station A", "Station B", 5,
                                   LocalDateTime.parse("2023-12-25 14:00:00", formatter),
                                   LocalDateTime.parse("2023-12-25 16:00:00", formatter), 100.0);
        system.getTrips().add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30:00-17:30:00
        Trip newTrip = new Trip("T2", "D6", "Station C", "Station D", 5,
                               LocalDateTime.parse("2023-12-25 14:30:00", formatter),
                               LocalDateTime.parse("2023-12-25 17:30:00", formatter), 120.0);
        
        // Validate trip posting feasibility
        boolean result = system.validateTripPostingFeasibility("D6", newTrip);
        
        // Expected Output: false (time conflict due to overlap)
        assertFalse("Trip with time conflict should be rejected", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00:00-11:00:00
        Trip existingTrip = new Trip("T1", "D7", "Station A", "Station B", 5,
                                   LocalDateTime.parse("2023-12-25 09:00:00", formatter),
                                   LocalDateTime.parse("2023-12-25 11:00:00", formatter), 100.0);
        system.getTrips().add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00:00-13:00:00
        Trip newTrip = new Trip("T2", "D7", "Station C", "Station D", 5,
                               LocalDateTime.parse("2023-12-25 11:00:00", formatter),
                               LocalDateTime.parse("2023-12-25 13:00:00", formatter), 120.0);
        
        // Validate trip posting feasibility
        boolean result = system.validateTripPostingFeasibility("D7", newTrip);
        
        // Expected Output: true (adjacent boundaries allowed)
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 2023-12-25 10:00:00-16:00:00
        Trip existingTrip = new Trip("T1", "D8", "Station A", "Station B", 5,
                                   LocalDateTime.parse("2023-12-25 10:00:00", formatter),
                                   LocalDateTime.parse("2023-12-25 16:00:00", formatter), 100.0);
        system.getTrips().add(existingTrip);
        
        // New trip proposed by D8: 2023-12-25 12:00:00-14:00:00
        Trip newTrip = new Trip("T2", "D8", "Station C", "Station D", 5,
                               LocalDateTime.parse("2023-12-25 12:00:00", formatter),
                               LocalDateTime.parse("2023-12-25 14:00:00", formatter), 120.0);
        
        // Validate trip posting feasibility
        boolean result = system.validateTripPostingFeasibility("D8", newTrip);
        
        // Expected Output: false (complete enclosure conflict)
        assertFalse("Trip completely enclosed within existing trip should be rejected", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00:00-10:00:00, 2023-12-21 11:00:00-13:00:00, 2023-12-23 14:00:00-16:00:00
        Trip trip1 = new Trip("T1", "D9", "Station A", "Station B", 5,
                             LocalDateTime.parse("2023-12-21 08:00:00", formatter),
                             LocalDateTime.parse("2023-12-21 10:00:00", formatter), 100.0);
        Trip trip2 = new Trip("T2", "D9", "Station C", "Station D", 5,
                             LocalDateTime.parse("2023-12-21 11:00:00", formatter),
                             LocalDateTime.parse("2023-12-21 13:00:00", formatter), 120.0);
        Trip trip3 = new Trip("T3", "D9", "Station E", "Station F", 5,
                             LocalDateTime.parse("2023-12-23 14:00:00", formatter),
                             LocalDateTime.parse("2023-12-23 16:00:00", formatter), 140.0);
        
        system.getTrips().add(trip1);
        system.getTrips().add(trip2);
        system.getTrips().add(trip3);
        
        // New trip by D9: 2023-12-21 09:30:00-10:30:00 (overlaps first two)
        Trip newTrip = new Trip("T4", "D9", "Station G", "Station H", 5,
                               LocalDateTime.parse("2023-12-21 09:30:00", formatter),
                               LocalDateTime.parse("2023-12-21 10:30:00", formatter), 150.0);
        
        // Validate trip posting feasibility
        boolean result = system.validateTripPostingFeasibility("D9", newTrip);
        
        // Expected Output: false (overlaps with first trip)
        assertFalse("Trip overlapping with multiple existing trips should be rejected", result);
    }
}