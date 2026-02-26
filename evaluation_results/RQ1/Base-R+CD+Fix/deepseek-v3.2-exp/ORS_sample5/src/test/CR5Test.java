import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR5Test {
    
    private Driver driver;
    
    @Before
    public void setUp() {
        driver = new Driver();
    }
    
    @Test
    public void testCase1_ValidTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 09:00");
        existingTrip.setArrivalTime("2023-12-25 11:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 13:00");
        newTrip.setArrivalTime("2023-12-25 15:00");
        
        // Expected Output: true
        assertTrue("Valid trip posting with time gap should return true", 
                  driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase2_PostingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 14:00");
        existingTrip.setArrivalTime("2023-12-25 16:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 14:30");
        newTrip.setArrivalTime("2023-12-25 17:30");
        
        // Expected Output: false
        assertFalse("Posting should be denied due to time conflict", 
                   driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase3_BackToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 09:00");
        existingTrip.setArrivalTime("2023-12-25 11:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 11:00");
        newTrip.setArrivalTime("2023-12-25 13:00");
        
        // Expected Output: true (adjacent boundaries not considered conflict)
        assertTrue("Back-to-back trips should be allowed", 
                  driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase4_CompleteTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 10:00"); // Assuming same date as test case
        existingTrip.setArrivalTime("2023-12-25 16:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 12:00");
        newTrip.setArrivalTime("2023-12-25 14:00");
        
        // Expected Output: false (complete overlap)
        assertFalse("Complete time enclosure should be rejected", 
                   driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase5_MultipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2023-12-21 08:00");
        trip1.setArrivalTime("2023-12-21 10:00");
        driver.getTrips().add(trip1);
        
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2023-12-21 11:00");
        trip2.setArrivalTime("2023-12-21 13:00");
        driver.getTrips().add(trip2);
        
        Trip trip3 = new Trip();
        trip3.setDepartureTime("2023-12-23 14:00");
        trip3.setArrivalTime("2023-12-23 16:00");
        driver.getTrips().add(trip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-21 09:30");
        newTrip.setArrivalTime("2023-12-21 10:30");
        
        // Expected Output: false (overlaps with first trip)
        assertFalse("Multiple trip conflict detection should work correctly", 
                   driver.canPostTrip(newTrip));
    }
}