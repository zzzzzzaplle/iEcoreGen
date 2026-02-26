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
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        User driverD5 = createUser("D5");
        Trip existingTrip = createTrip("T1", driverD5, 
            LocalDateTime.parse("2023-12-25 09:00:00", formatter),
            LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        system.getTrips().add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = createTrip("T2", driverD5,
            LocalDateTime.parse("2023-12-25 13:00:00", formatter),
            LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        
        // Test validation
        boolean result = system.validateTripPostingFeasibility(driverD5, newTrip);
        
        // Verify result
        assertTrue("Trip with time gap should be valid", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        User driverD6 = createUser("D6");
        Trip existingTrip = createTrip("T1", driverD6,
            LocalDateTime.parse("2023-12-25 14:00:00", formatter),
            LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        system.getTrips().add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = createTrip("T2", driverD6,
            LocalDateTime.parse("2023-12-25 14:30:00", formatter),
            LocalDateTime.parse("2023-12-25 17:30:00", formatter));
        
        // Test validation
        boolean result = system.validateTripPostingFeasibility(driverD6, newTrip);
        
        // Verify result
        assertFalse("Overlapping trips should be rejected", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        User driverD7 = createUser("D7");
        Trip existingTrip = createTrip("T1", driverD7,
            LocalDateTime.parse("2023-12-25 09:00:00", formatter),
            LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        system.getTrips().add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = createTrip("T2", driverD7,
            LocalDateTime.parse("2023-12-25 11:00:00", formatter),
            LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        
        // Test validation
        boolean result = system.validateTripPostingFeasibility(driverD7, newTrip);
        
        // Verify result - back-to-back trips should be allowed (adjacent boundaries)
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00 (assuming same day)
        User driverD8 = createUser("D8");
        Trip existingTrip = createTrip("T1", driverD8,
            LocalDateTime.parse("2023-12-25 10:00:00", formatter),
            LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        system.getTrips().add(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = createTrip("T2", driverD8,
            LocalDateTime.parse("2023-12-25 12:00:00", formatter),
            LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        
        // Test validation
        boolean result = system.validateTripPostingFeasibility(driverD8, newTrip);
        
        // Verify result - completely enclosed trip should be rejected
        assertFalse("Completely enclosed trip should be rejected", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        User driverD9 = createUser("D9");
        
        Trip trip1 = createTrip("T1", driverD9,
            LocalDateTime.parse("2023-12-21 08:00:00", formatter),
            LocalDateTime.parse("2023-12-21 10:00:00", formatter));
        
        Trip trip2 = createTrip("T2", driverD9,
            LocalDateTime.parse("2023-12-21 11:00:00", formatter),
            LocalDateTime.parse("2023-12-21 13:00:00", formatter));
        
        Trip trip3 = createTrip("T3", driverD9,
            LocalDateTime.parse("2023-12-23 14:00:00", formatter),
            LocalDateTime.parse("2023-12-23 16:00:00", formatter));
        
        system.getTrips().add(trip1);
        system.getTrips().add(trip2);
        system.getTrips().add(trip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = createTrip("T4", driverD9,
            LocalDateTime.parse("2023-12-21 09:30:00", formatter),
            LocalDateTime.parse("2023-12-21 10:30:00", formatter));
        
        // Test validation
        boolean result = system.validateTripPostingFeasibility(driverD9, newTrip);
        
        // Verify result - overlapping with existing trip should be rejected
        assertFalse("Trip overlapping with existing trips should be rejected", result);
    }
    
    // Helper method to create a user with ID
    private User createUser(String userId) {
        User user = new User();
        user.setUserId(userId);
        return user;
    }
    
    // Helper method to create a trip with basic information
    private Trip createTrip(String tripId, User driver, LocalDateTime departure, LocalDateTime arrival) {
        Trip trip = new Trip();
        trip.setTripId(tripId);
        trip.setDriver(driver);
        trip.setDepartureTime(departure);
        trip.setArrivalTime(arrival);
        trip.setAvailableSeats(4); // Default value
        trip.setPrice(50.0); // Default value
        return trip;
    }
}