import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    
    private Driver driver;
    private Trip newTrip;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        driver = new Driver();
        newTrip = new Trip();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() throws Exception {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        Date departureDate = dateFormat.parse("2023-12-25 00:00:00");
        existingTrip.setDepartureDate(departureDate);
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        
        List<Trip> trips = new ArrayList<>();
        trips.add(existingTrip);
        driver.setTrips(trips);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        newTrip.setDepartureDate(departureDate);
        newTrip.setDepartureTime("13:00");
        newTrip.setArrivalTime("15:00");
        
        // Execute and verify
        boolean result = driver.canPostTrip(newTrip);
        assertTrue("Valid trip with time gap should be allowed", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() throws Exception {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = new Trip();
        Date departureDate = dateFormat.parse("2023-12-25 00:00:00");
        existingTrip.setDepartureDate(departureDate);
        existingTrip.setDepartureTime("14:00");
        existingTrip.setArrivalTime("16:00");
        
        List<Trip> trips = new ArrayList<>();
        trips.add(existingTrip);
        driver.setTrips(trips);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        newTrip.setDepartureDate(departureDate);
        newTrip.setDepartureTime("14:30");
        newTrip.setArrivalTime("17:30");
        
        // Execute and verify
        boolean result = driver.canPostTrip(newTrip);
        assertFalse("Trip with time conflict should be denied", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() throws Exception {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        Date departureDate = dateFormat.parse("2023-12-25 00:00:00");
        existingTrip.setDepartureDate(departureDate);
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        
        List<Trip> trips = new ArrayList<>();
        trips.add(existingTrip);
        driver.setTrips(trips);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        newTrip.setDepartureDate(departureDate);
        newTrip.setDepartureTime("11:00");
        newTrip.setArrivalTime("13:00");
        
        // Execute and verify
        boolean result = driver.canPostTrip(newTrip);
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() throws Exception {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        Trip existingTrip = new Trip();
        Date departureDate = new Date(); // Using current date since no specific date provided
        existingTrip.setDepartureDate(departureDate);
        existingTrip.setDepartureTime("10:00");
        existingTrip.setArrivalTime("16:00");
        
        List<Trip> trips = new ArrayList<>();
        trips.add(existingTrip);
        driver.setTrips(trips);
        
        // New trip proposed by D8: 12:00-14:00
        newTrip.setDepartureDate(departureDate);
        newTrip.setDepartureTime("12:00");
        newTrip.setArrivalTime("14:00");
        
        // Execute and verify
        boolean result = driver.canPostTrip(newTrip);
        assertFalse("Complete time enclosure should be rejected", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() throws Exception {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        List<Trip> trips = new ArrayList<>();
        
        Trip trip1 = new Trip();
        Date date1 = dateFormat.parse("2023-12-21 00:00:00");
        trip1.setDepartureDate(date1);
        trip1.setDepartureTime("08:00");
        trip1.setArrivalTime("10:00");
        trips.add(trip1);
        
        Trip trip2 = new Trip();
        trip2.setDepartureDate(date1);
        trip2.setDepartureTime("11:00");
        trip2.setArrivalTime("13:00");
        trips.add(trip2);
        
        Trip trip3 = new Trip();
        Date date3 = dateFormat.parse("2023-12-23 00:00:00");
        trip3.setDepartureDate(date3);
        trip3.setDepartureTime("14:00");
        trip3.setArrivalTime("16:00");
        trips.add(trip3);
        
        driver.setTrips(trips);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        newTrip.setDepartureDate(date1);
        newTrip.setDepartureTime("09:30");
        newTrip.setArrivalTime("10:30");
        
        // Execute and verify
        boolean result = driver.canPostTrip(newTrip);
        assertFalse("Trip overlapping with existing trips should be rejected", result);
    }
}