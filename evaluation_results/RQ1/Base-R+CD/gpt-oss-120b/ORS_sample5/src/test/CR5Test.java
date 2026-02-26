import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR5Test {
    
    private Driver driver;
    
    @Before
    public void setUp() {
        driver = new Driver();
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = createTrip("2023-12-25 09:00", "2023-12-25 11:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = createTrip("2023-12-25 13:00", "2023-12-25 15:00");
        
        // Expected Output: true
        assertTrue("Valid trip with time gap should be allowed", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = createTrip("2023-12-25 14:00", "2023-12-25 16:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = createTrip("2023-12-25 14:30", "2023-12-25 17:30");
        
        // Expected Output: false
        assertFalse("Trip with time conflict should be rejected", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = createTrip("2023-12-25 09:00", "2023-12-25 11:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = createTrip("2023-12-25 11:00", "2023-12-25 13:00");
        
        // Expected Output: true
        assertTrue("Back-to-back trips should be allowed", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        Trip existingTrip = createTrip("2023-12-25 10:00", "2023-12-25 16:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = createTrip("2023-12-25 12:00", "2023-12-25 14:00");
        
        // Expected Output: false
        assertFalse("Complete time enclosure should be rejected", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        Trip trip1 = createTrip("2023-12-21 08:00", "2023-12-21 10:00");
        Trip trip2 = createTrip("2023-12-21 11:00", "2023-12-21 13:00");
        Trip trip3 = createTrip("2023-12-23 14:00", "2023-12-23 16:00");
        
        driver.getTrips().add(trip1);
        driver.getTrips().add(trip2);
        driver.getTrips().add(trip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = createTrip("2023-12-21 09:30", "2023-12-21 10:30");
        
        // Expected Output: false
        assertFalse("Trip overlapping with existing trips should be rejected", driver.canPostTrip(newTrip));
    }
    
    /**
     * Helper method to create a Trip with specified departure and arrival times
     */
    private Trip createTrip(String departureTime, String arrivalTime) {
        Trip trip = new Trip();
        trip.setDepartureTime(departureTime);
        trip.setArrivalTime(arrivalTime);
        
        // Set departure date from departure time string
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String datePart = departureTime.substring(0, 10);
            trip.setDepartureDate(sdf.parse(datePart));
        } catch (Exception e) {
            // Use current date as fallback
            trip.setDepartureDate(new Date());
        }
        
        return trip;
    }
}