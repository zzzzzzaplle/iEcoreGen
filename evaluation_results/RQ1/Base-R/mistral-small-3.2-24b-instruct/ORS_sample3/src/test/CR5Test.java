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
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        User driverD5 = new User("D5", "d5@example.com", "555-0005");
        Trip existingTrip = new Trip("T1", driverD5, "Station A", "Station B", 4,
                LocalDateTime.parse("2023-12-25 09:00", formatter),
                LocalDateTime.parse("2023-12-25 11:00", formatter), 50.0);
        system.addTrip(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip("T2", driverD5, "Station C", "Station D", 3,
                LocalDateTime.parse("2023-12-25 13:00", formatter),
                LocalDateTime.parse("2023-12-25 15:00", formatter), 60.0);
        
        // Execute and verify
        boolean result = system.validateTripPostingFeasibility(driverD5, newTrip);
        assertTrue("Valid trip posting with time gap should return true", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        User driverD6 = new User("D6", "d6@example.com", "555-0006");
        Trip existingTrip = new Trip("T1", driverD6, "Station A", "Station B", 4,
                LocalDateTime.parse("2023-12-25 14:00", formatter),
                LocalDateTime.parse("2023-12-25 16:00", formatter), 50.0);
        system.addTrip(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip("T2", driverD6, "Station C", "Station D", 3,
                LocalDateTime.parse("2023-12-25 14:30", formatter),
                LocalDateTime.parse("2023-12-25 17:30", formatter), 60.0);
        
        // Execute and verify
        boolean result = system.validateTripPostingFeasibility(driverD6, newTrip);
        assertFalse("Posting with time conflict should return false", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        User driverD7 = new User("D7", "d7@example.com", "555-0007");
        Trip existingTrip = new Trip("T1", driverD7, "Station A", "Station B", 4,
                LocalDateTime.parse("2023-12-25 09:00", formatter),
                LocalDateTime.parse("2023-12-25 11:00", formatter), 50.0);
        system.addTrip(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip("T2", driverD7, "Station C", "Station D", 3,
                LocalDateTime.parse("2023-12-25 11:00", formatter),
                LocalDateTime.parse("2023-12-25 13:00", formatter), 60.0);
        
        // Execute and verify
        boolean result = system.validateTripPostingFeasibility(driverD7, newTrip);
        assertTrue("Back-to-back trips should be allowed (adjacent boundaries)", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        // Note: Using 2023-12-25 as date for consistency
        User driverD8 = new User("D8", "d8@example.com", "555-0008");
        Trip existingTrip = new Trip("T1", driverD8, "Station A", "Station B", 4,
                LocalDateTime.parse("2023-12-25 10:00", formatter),
                LocalDateTime.parse("2023-12-25 16:00", formatter), 50.0);
        system.addTrip(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip("T2", driverD8, "Station C", "Station D", 3,
                LocalDateTime.parse("2023-12-25 12:00", formatter),
                LocalDateTime.parse("2023-12-25 14:00", formatter), 60.0);
        
        // Execute and verify
        boolean result = system.validateTripPostingFeasibility(driverD8, newTrip);
        assertFalse("Complete time enclosure should be rejected", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        User driverD9 = new User("D9", "d9@example.com", "555-0009");
        
        Trip trip1 = new Trip("T1", driverD9, "Station A", "Station B", 4,
                LocalDateTime.parse("2023-12-21 08:00", formatter),
                LocalDateTime.parse("2023-12-21 10:00", formatter), 50.0);
        
        Trip trip2 = new Trip("T2", driverD9, "Station C", "Station D", 3,
                LocalDateTime.parse("2023-12-21 11:00", formatter),
                LocalDateTime.parse("2023-12-21 13:00", formatter), 60.0);
        
        Trip trip3 = new Trip("T3", driverD9, "Station E", "Station F", 2,
                LocalDateTime.parse("2023-12-23 14:00", formatter),
                LocalDateTime.parse("2023-12-23 16:00", formatter), 70.0);
        
        system.addTrip(trip1);
        system.addTrip(trip2);
        system.addTrip(trip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = new Trip("T4", driverD9, "Station G", "Station H", 3,
                LocalDateTime.parse("2023-12-21 09:30", formatter),
                LocalDateTime.parse("2023-12-21 10:30", formatter), 80.0);
        
        // Execute and verify
        boolean result = system.validateTripPostingFeasibility(driverD9, newTrip);
        assertFalse("Trip overlapping with existing trips should be rejected", result);
    }
}