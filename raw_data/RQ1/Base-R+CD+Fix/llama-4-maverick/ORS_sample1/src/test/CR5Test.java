import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private Driver driver;
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        driver = new Driver();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() throws ParseException {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = createTrip("2023-12-25", "09:00", "11:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = createTrip("2023-12-25", "13:00", "15:00");
        
        // Expected Output: true
        assertTrue("Trip with time gap should be allowed", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() throws ParseException {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = createTrip("2023-12-25", "14:00", "16:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = createTrip("2023-12-25", "14:30", "17:30");
        
        // Expected Output: false
        assertFalse("Overlapping trips should be rejected", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() throws ParseException {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = createTrip("2023-12-25", "09:00", "11:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = createTrip("2023-12-25", "11:00", "13:00");
        
        // Expected Output: true (adjacent boundaries not considered conflict)
        assertTrue("Back-to-back trips should be allowed", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() throws ParseException {
        // Setup: Driver D8 has existing trip at 10:00-16:00 on same date
        Trip existingTrip = createTrip("2023-12-25", "10:00", "16:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = createTrip("2023-12-25", "12:00", "14:00");
        
        // Expected Output: false
        assertFalse("Completely enclosed trip should be rejected", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() throws ParseException {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        Trip trip1 = createTrip("2023-12-21", "08:00", "10:00");
        Trip trip2 = createTrip("2023-12-21", "11:00", "13:00");
        Trip trip3 = createTrip("2023-12-23", "14:00", "16:00");
        
        driver.getTrips().add(trip1);
        driver.getTrips().add(trip2);
        driver.getTrips().add(trip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = createTrip("2023-12-21", "09:30", "10:30");
        
        // Expected Output: false
        assertFalse("Trip overlapping with existing trips should be rejected", driver.canPostTrip(newTrip));
    }
    
    /**
     * Helper method to create a trip with specified date and times
     */
    private Trip createTrip(String dateStr, String departureTime, String arrivalTime) throws ParseException {
        Trip trip = new Trip();
        trip.setDepartureDate(sdf.parse(dateStr + " 00:00")); // Date part only
        trip.setDepartureTime(departureTime);
        trip.setArrivalTime(arrivalTime);
        return trip;
    }
}