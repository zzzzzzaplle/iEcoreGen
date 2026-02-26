import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR5Test {
    
    private Driver driver;
    private Trip existingTrip;
    private Trip newTrip;
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        driver = new Driver();
        existingTrip = new Trip();
        newTrip = new Trip();
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        existingTrip.setDepartureDate(createDate("2023-12-25"));
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        driver.setTrips(existingTrips);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        newTrip.setDepartureDate(createDate("2023-12-25"));
        newTrip.setDepartureTime("13:00");
        newTrip.setArrivalTime("15:00");
        
        // Test: Validate trip posting feasibility
        boolean result = driver.canPostTrip(newTrip);
        
        // Verify: Should return true (no time conflict)
        assertTrue("Trip with time gap should be allowed", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        existingTrip.setDepartureDate(createDate("2023-12-25"));
        existingTrip.setDepartureTime("14:00");
        existingTrip.setArrivalTime("16:00");
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        driver.setTrips(existingTrips);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        newTrip.setDepartureDate(createDate("2023-12-25"));
        newTrip.setDepartureTime("14:30");
        newTrip.setArrivalTime("17:30");
        
        // Test: Validate trip posting feasibility
        boolean result = driver.canPostTrip(newTrip);
        
        // Verify: Should return false (time conflict exists)
        assertFalse("Trip with time conflict should be denied", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        existingTrip.setDepartureDate(createDate("2023-12-25"));
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        driver.setTrips(existingTrips);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        newTrip.setDepartureDate(createDate("2023-12-25"));
        newTrip.setDepartureTime("11:00");
        newTrip.setArrivalTime("13:00");
        
        // Test: Validate trip posting feasibility
        boolean result = driver.canPostTrip(newTrip);
        
        // Verify: Should return true (adjacent boundaries allowed)
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00 (using same date as departure date)
        existingTrip.setDepartureDate(createDate("2023-12-25"));
        existingTrip.setDepartureTime("10:00");
        existingTrip.setArrivalTime("16:00");
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        driver.setTrips(existingTrips);
        
        // New trip proposed by D8: 12:00-14:00
        newTrip.setDepartureDate(createDate("2023-12-25"));
        newTrip.setDepartureTime("12:00");
        newTrip.setArrivalTime("14:00");
        
        // Test: Validate trip posting feasibility
        boolean result = driver.canPostTrip(newTrip);
        
        // Verify: Should return false (complete overlap)
        assertFalse("Trip completely enclosed in existing trip should be denied", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        
        Trip trip1 = new Trip();
        trip1.setDepartureDate(createDate("2023-12-21"));
        trip1.setDepartureTime("08:00");
        trip1.setArrivalTime("10:00");
        
        Trip trip2 = new Trip();
        trip2.setDepartureDate(createDate("2023-12-21"));
        trip2.setDepartureTime("11:00");
        trip2.setArrivalTime("13:00");
        
        Trip trip3 = new Trip();
        trip3.setDepartureDate(createDate("2023-12-23"));
        trip3.setDepartureTime("14:00");
        trip3.setArrivalTime("16:00");
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(trip1);
        existingTrips.add(trip2);
        existingTrips.add(trip3);
        driver.setTrips(existingTrips);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        newTrip.setDepartureDate(createDate("2023-12-21"));
        newTrip.setDepartureTime("09:30");
        newTrip.setArrivalTime("10:30");
        
        // Test: Validate trip posting feasibility
        boolean result = driver.canPostTrip(newTrip);
        
        // Verify: Should return false (conflict with first trip)
        assertFalse("Trip conflicting with multiple existing trips should be denied", result);
    }
    
    private Date createDate(String dateString) {
        try {
            return sdf.parse(dateString + " 00:00");
        } catch (Exception e) {
            return new Date();
        }
    }
}