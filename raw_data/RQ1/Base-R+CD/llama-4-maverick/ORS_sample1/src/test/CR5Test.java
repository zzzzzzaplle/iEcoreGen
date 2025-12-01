import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private Driver driver;
    private Trip existingTrip;
    private Trip newTrip;
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        driver = new Driver();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() throws Exception {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        existingTrip = new Trip();
        existingTrip.setDepartureDate(sdf.parse("2023-12-25 00:00"));
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        driver.setTrips(existingTrips);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        newTrip = new Trip();
        newTrip.setDepartureDate(sdf.parse("2023-12-25 00:00"));
        newTrip.setDepartureTime("13:00");
        newTrip.setArrivalTime("15:00");
        
        // Expected Output: true
        assertTrue("Valid trip with time gap should be allowed", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() throws Exception {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        existingTrip = new Trip();
        existingTrip.setDepartureDate(sdf.parse("2023-12-25 00:00"));
        existingTrip.setDepartureTime("14:00");
        existingTrip.setArrivalTime("16:00");
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        driver.setTrips(existingTrips);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        newTrip = new Trip();
        newTrip.setDepartureDate(sdf.parse("2023-12-25 00:00"));
        newTrip.setDepartureTime("14:30");
        newTrip.setArrivalTime("17:30");
        
        // Expected Output: false
        assertFalse("Trip with time conflict should be denied", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() throws Exception {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        existingTrip = new Trip();
        existingTrip.setDepartureDate(sdf.parse("2023-12-25 00:00"));
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        driver.setTrips(existingTrips);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        newTrip = new Trip();
        newTrip.setDepartureDate(sdf.parse("2023-12-25 00:00"));
        newTrip.setDepartureTime("11:00");
        newTrip.setArrivalTime("13:00");
        
        // Expected Output: true
        assertTrue("Back-to-back trips should be allowed", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() throws Exception {
        // Setup: Driver D8 has existing trip at 10:00-16:00 (assuming same date as new trip)
        existingTrip = new Trip();
        existingTrip.setDepartureDate(sdf.parse("2023-12-25 00:00"));
        existingTrip.setDepartureTime("10:00");
        existingTrip.setArrivalTime("16:00");
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        driver.setTrips(existingTrips);
        
        // New trip proposed by D8: 12:00-14:00
        newTrip = new Trip();
        newTrip.setDepartureDate(sdf.parse("2023-12-25 00:00"));
        newTrip.setDepartureTime("12:00");
        newTrip.setArrivalTime("14:00");
        
        // Expected Output: false
        assertFalse("Completely enclosed trip should be denied", driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() throws Exception {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        List<Trip> existingTrips = new ArrayList<>();
        
        Trip trip1 = new Trip();
        trip1.setDepartureDate(sdf.parse("2023-12-21 00:00"));
        trip1.setDepartureTime("08:00");
        trip1.setArrivalTime("10:00");
        existingTrips.add(trip1);
        
        Trip trip2 = new Trip();
        trip2.setDepartureDate(sdf.parse("2023-12-21 00:00"));
        trip2.setDepartureTime("11:00");
        trip2.setArrivalTime("13:00");
        existingTrips.add(trip2);
        
        Trip trip3 = new Trip();
        trip3.setDepartureDate(sdf.parse("2023-12-23 00:00"));
        trip3.setDepartureTime("14:00");
        trip3.setArrivalTime("16:00");
        existingTrips.add(trip3);
        
        driver.setTrips(existingTrips);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        newTrip = new Trip();
        newTrip.setDepartureDate(sdf.parse("2023-12-21 00:00"));
        newTrip.setDepartureTime("09:30");
        newTrip.setArrivalTime("10:30");
        
        // Expected Output: false
        assertFalse("Trip overlapping with multiple existing trips should be denied", driver.canPostTrip(newTrip));
    }
}