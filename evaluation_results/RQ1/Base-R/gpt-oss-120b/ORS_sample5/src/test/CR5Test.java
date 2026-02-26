import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;

public class CR5Test {
    
    private RideShareSystem system;
    private Driver driverD5, driverD6, driverD7, driverD8, driverD9;
    
    @Before
    public void setUp() {
        system = new RideShareSystem();
        
        // Create drivers and add them to the system
        driverD5 = new Driver();
        driverD5.setId("D5");
        system.addUser(driverD5);
        
        driverD6 = new Driver();
        driverD6.setId("D6");
        system.addUser(driverD6);
        
        driverD7 = new Driver();
        driverD7.setId("D7");
        system.addUser(driverD7);
        
        driverD8 = new Driver();
        driverD8.setId("D8");
        system.addUser(driverD8);
        
        driverD9 = new Driver();
        driverD9.setId("D9");
        system.addUser(driverD9);
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setId("T1");
        existingTrip.setDriver(driverD5);
        existingTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 9, 0));
        existingTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 11, 0));
        system.addTrip(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD5);
        newTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 13, 0));
        newTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 15, 0));
        
        // Expected Output: true
        assertTrue("Valid trip posting with time gap should return true", 
                   system.validateTripPostingFeasibility(driverD5, newTrip));
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setId("T2");
        existingTrip.setDriver(driverD6);
        existingTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 14, 0));
        existingTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 16, 0));
        system.addTrip(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD6);
        newTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 14, 30));
        newTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 17, 30));
        
        // Expected Output: false
        assertFalse("Posting should be denied due to time conflict", 
                    system.validateTripPostingFeasibility(driverD6, newTrip));
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setId("T3");
        existingTrip.setDriver(driverD7);
        existingTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 9, 0));
        existingTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 11, 0));
        system.addTrip(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD7);
        newTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 11, 0));
        newTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 13, 0));
        
        // Expected Output: true (adjacent boundaries allowed)
        assertTrue("Back-to-back trips should be allowed", 
                   system.validateTripPostingFeasibility(driverD7, newTrip));
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00 (assuming same day as new trip)
        Trip existingTrip = new Trip();
        existingTrip.setId("T4");
        existingTrip.setDriver(driverD8);
        existingTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 10, 0));
        existingTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 16, 0));
        system.addTrip(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD8);
        newTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 12, 0));
        newTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 14, 0));
        
        // Expected Output: false (complete overlap)
        assertFalse("Complete time enclosure should be rejected", 
                    system.validateTripPostingFeasibility(driverD8, newTrip));
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        
        Trip trip1 = new Trip();
        trip1.setId("T5a");
        trip1.setDriver(driverD9);
        trip1.setDepartureDateTime(LocalDateTime.of(2023, 12, 21, 8, 0));
        trip1.setArrivalDateTime(LocalDateTime.of(2023, 12, 21, 10, 0));
        system.addTrip(trip1);
        
        Trip trip2 = new Trip();
        trip2.setId("T5b");
        trip2.setDriver(driverD9);
        trip2.setDepartureDateTime(LocalDateTime.of(2023, 12, 21, 11, 0));
        trip2.setArrivalDateTime(LocalDateTime.of(2023, 12, 21, 13, 0));
        system.addTrip(trip2);
        
        Trip trip3 = new Trip();
        trip3.setId("T5c");
        trip3.setDriver(driverD9);
        trip3.setDepartureDateTime(LocalDateTime.of(2023, 12, 23, 14, 0));
        trip3.setArrivalDateTime(LocalDateTime.of(2023, 12, 23, 16, 0));
        system.addTrip(trip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD9);
        newTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 21, 9, 30));
        newTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 21, 10, 30));
        
        // Expected Output: false (overlaps with first trip)
        assertFalse("Multiple trip conflict detection should work correctly", 
                    system.validateTripPostingFeasibility(driverD9, newTrip));
    }
}