import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        Trip existingTrip = new Trip();
        existingTrip.setDepartureDate("2023-12-25");
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDepartureDate("2023-12-25");
        newTrip.setDepartureTime("13:00");
        newTrip.setArrivalTime("15:00");
        
        // Expected Output: true
        assertTrue("Valid trip with time gap should be allowed", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureDate("2023-12-25");
        existingTrip.setDepartureTime("14:00");
        existingTrip.setArrivalTime("16:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDepartureDate("2023-12-25");
        newTrip.setDepartureTime("14:30");
        newTrip.setArrivalTime("17:30");
        
        // Expected Output: false
        assertFalse("Trip with time conflict should be rejected", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureDate("2023-12-25");
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDepartureDate("2023-12-25");
        newTrip.setDepartureTime("11:00");
        newTrip.setArrivalTime("13:00");
        
        // Expected Output: true (adjacent boundaries allowed)
        assertTrue("Back-to-back trips should be allowed", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureDate("2023-12-25"); // Assuming same date as test case
        existingTrip.setDepartureTime("10:00");
        existingTrip.setArrivalTime("16:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDepartureDate("2023-12-25");
        newTrip.setDepartureTime("12:00");
        newTrip.setArrivalTime("14:00");
        
        // Expected Output: false (complete overlap)
        assertFalse("Trip completely enclosed within existing trip should be rejected", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has Existing trips at:
        // 2023-12-21 08:00-10:00
        Trip trip1 = new Trip();
        trip1.setDepartureDate("2023-12-21");
        trip1.setDepartureTime("08:00");
        trip1.setArrivalTime("10:00");
        
        // 2023-12-21 11:00-13:00
        Trip trip2 = new Trip();
        trip2.setDepartureDate("2023-12-21");
        trip2.setDepartureTime("11:00");
        trip2.setArrivalTime("13:00");
        
        // 2023-12-23 14:00-16:00
        Trip trip3 = new Trip();
        trip3.setDepartureDate("2023-12-23");
        trip3.setDepartureTime("14:00");
        trip3.setArrivalTime("16:00");
        
        driver.getTrips().add(trip1);
        driver.getTrips().add(trip2);
        driver.getTrips().add(trip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = new Trip();
        newTrip.setDepartureDate("2023-12-21");
        newTrip.setDepartureTime("09:30");
        newTrip.setArrivalTime("10:30");
        
        // Expected Output: false (overlaps with first trip)
        assertFalse("Trip overlapping with existing trips should be rejected", driver.canPostTrip(newTrip));
    }
}