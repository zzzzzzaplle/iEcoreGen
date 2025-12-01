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
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = createTrip("2023-12-25", "09:00", "11:00");
        driver.setID("D5");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = createTrip("2023-12-25", "13:00", "15:00");
        
        // Expected Output: true
        assertTrue("Valid trip with time gap should be allowed", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = createTrip("2023-12-25", "14:00", "16:00");
        driver.setID("D6");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = createTrip("2023-12-25", "14:30", "17:30");
        
        // Expected Output: false
        assertFalse("Trip with time conflict should be rejected", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = createTrip("2023-12-25", "09:00", "11:00");
        driver.setID("D7");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = createTrip("2023-12-25", "11:00", "13:00");
        
        // Expected Output: true
        assertTrue("Back-to-back trips should be allowed", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        Trip existingTrip = createTrip("2023-12-25", "10:00", "16:00");
        driver.setID("D8");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = createTrip("2023-12-25", "12:00", "14:00");
        
        // Expected Output: false
        assertFalse("Complete time enclosure should be rejected", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        driver.setID("D9");
        driver.getTrips().add(createTrip("2023-12-21", "08:00", "10:00"));
        driver.getTrips().add(createTrip("2023-12-21", "11:00", "13:00"));
        driver.getTrips().add(createTrip("2023-12-23", "14:00", "16:00"));
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = createTrip("2023-12-21", "09:30", "10:30");
        
        // Expected Output: false
        assertFalse("Trip overlapping with multiple existing trips should be rejected", driver.canPostTrip(newTrip));
    }
    
    // Helper method to create trips with consistent setup
    private Trip createTrip(String date, String departureTime, String arrivalTime) {
        Trip trip = new Trip();
        trip.setDepartureDate(date);
        trip.setDepartureTime(departureTime);
        trip.setArrivalTime(arrivalTime);
        trip.setNumberOfSeats(5); // Default seat count
        trip.setPrice(50.0); // Default price
        trip.setDepartureStation("Station A");
        trip.setArrivalStation("Station B");
        return trip;
    }
}