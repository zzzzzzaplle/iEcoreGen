import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public void testCase1_ValidTripPostingWithTimeGap() throws ParseException {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 09:00");
        existingTrip.setArrivalTime("2023-12-25 11:00");
        
        List<Trip> trips = new ArrayList<>();
        trips.add(existingTrip);
        driver.setTrips(trips);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 13:00");
        newTrip.setArrivalTime("2023-12-25 15:00");
        
        // Test: Validate new trip posting
        boolean result = driver.canPostTrip(newTrip);
        
        // Expected Output: true
        assertTrue("Trip with time gap should be allowed", result);
    }
    
    @Test
    public void testCase2_PostingDeniedDueToTimeConflict() throws ParseException {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 14:00");
        existingTrip.setArrivalTime("2023-12-25 16:00");
        
        List<Trip> trips = new ArrayList<>();
        trips.add(existingTrip);
        driver.setTrips(trips);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 14:30");
        newTrip.setArrivalTime("2023-12-25 17:30");
        
        // Test: Validate new trip posting
        boolean result = driver.canPostTrip(newTrip);
        
        // Expected Output: false
        assertFalse("Trip with time conflict should be rejected", result);
    }
    
    @Test
    public void testCase3_BackToBackTripsAllowed() throws ParseException {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 09:00");
        existingTrip.setArrivalTime("2023-12-25 11:00");
        
        List<Trip> trips = new ArrayList<>();
        trips.add(existingTrip);
        driver.setTrips(trips);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 11:00");
        newTrip.setArrivalTime("2023-12-25 13:00");
        
        // Test: Validate new trip posting
        boolean result = driver.canPostTrip(newTrip);
        
        // Expected Output: true
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_CompleteTimeEnclosureRejection() throws ParseException {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 10:00");
        existingTrip.setArrivalTime("2023-12-25 16:00");
        
        List<Trip> trips = new ArrayList<>();
        trips.add(existingTrip);
        driver.setTrips(trips);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 12:00");
        newTrip.setArrivalTime("2023-12-25 14:00");
        
        // Test: Validate new trip posting
        boolean result = driver.canPostTrip(newTrip);
        
        // Expected Output: false
        assertFalse("Completely overlapping trip should be rejected", result);
    }
    
    @Test
    public void testCase5_MultipleExistingTripComparison() throws ParseException {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2023-12-21 08:00");
        trip1.setArrivalTime("2023-12-21 10:00");
        
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2023-12-21 11:00");
        trip2.setArrivalTime("2023-12-21 13:00");
        
        Trip trip3 = new Trip();
        trip3.setDepartureTime("2023-12-23 14:00");
        trip3.setArrivalTime("2023-12-23 16:00");
        
        List<Trip> trips = new ArrayList<>();
        trips.add(trip1);
        trips.add(trip2);
        trips.add(trip3);
        driver.setTrips(trips);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first trip)
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-21 09:30");
        newTrip.setArrivalTime("2023-12-21 10:30");
        
        // Test: Validate new trip posting
        boolean result = driver.canPostTrip(newTrip);
        
        // Expected Output: false
        assertFalse("Trip overlapping with existing trips should be rejected", result);
    }
}