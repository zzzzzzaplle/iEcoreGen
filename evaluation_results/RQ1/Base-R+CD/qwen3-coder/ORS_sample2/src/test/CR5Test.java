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
        driver.addTrip(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDepartureDate("2023-12-25");
        newTrip.setDepartureTime("13:00");
        newTrip.setArrivalTime("15:00");
        
        // Validate trip posting feasibility
        boolean result = driver.canPostTrip(newTrip);
        
        // Expected Output: true
        assertTrue("Trip posting should be allowed when there's a time gap", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureDate("2023-12-25");
        existingTrip.setDepartureTime("14:00");
        existingTrip.setArrivalTime("16:00");
        driver.addTrip(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDepartureDate("2023-12-25");
        newTrip.setDepartureTime("14:30");
        newTrip.setArrivalTime("17:30");
        
        // Validate trip posting feasibility
        boolean result = driver.canPostTrip(newTrip);
        
        // Expected Output: false
        assertFalse("Trip posting should be denied due to time conflict", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureDate("2023-12-25");
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        driver.addTrip(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDepartureDate("2023-12-25");
        newTrip.setDepartureTime("11:00");
        newTrip.setArrivalTime("13:00");
        
        // Validate trip posting feasibility
        boolean result = driver.canPostTrip(newTrip);
        
        // Expected Output: true (adjacent boundaries not considered conflict)
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureDate("2023-12-25");
        existingTrip.setDepartureTime("10:00");
        existingTrip.setArrivalTime("16:00");
        driver.addTrip(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDepartureDate("2023-12-25");
        newTrip.setDepartureTime("12:00");
        newTrip.setArrivalTime("14:00");
        
        // Validate trip posting feasibility
        boolean result = driver.canPostTrip(newTrip);
        
        // Expected Output: false (complete overlap)
        assertFalse("Trip posting should be denied due to complete time enclosure", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        
        Trip trip1 = new Trip();
        trip1.setDepartureDate("2023-12-21");
        trip1.setDepartureTime("08:00");
        trip1.setArrivalTime("10:00");
        driver.addTrip(trip1);
        
        Trip trip2 = new Trip();
        trip2.setDepartureDate("2023-12-21");
        trip2.setDepartureTime("11:00");
        trip2.setArrivalTime("13:00");
        driver.addTrip(trip2);
        
        Trip trip3 = new Trip();
        trip3.setDepartureDate("2023-12-23");
        trip3.setDepartureTime("14:00");
        trip3.setArrivalTime("16:00");
        driver.addTrip(trip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = new Trip();
        newTrip.setDepartureDate("2023-12-21");
        newTrip.setDepartureTime("09:30");
        newTrip.setArrivalTime("10:30");
        
        // Validate trip posting feasibility
        boolean result = driver.canPostTrip(newTrip);
        
        // Expected Output: false (overlaps with first trip)
        assertFalse("Trip posting should be denied due to overlap with existing trips", result);
    }
}