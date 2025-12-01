import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR5Test {
    private Driver driver;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        driver = new Driver();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidTripPostingWithTimeGap() throws ParseException {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        newTrip.setDepartureTime("13:00");
        newTrip.setArrivalTime("15:00");
        
        // Expected Output: true (no time conflict)
        assertTrue(driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase2_PostingDeniedDueToTimeConflict() throws ParseException {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        existingTrip.setDepartureTime("14:00");
        existingTrip.setArrivalTime("16:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        newTrip.setDepartureTime("14:30");
        newTrip.setArrivalTime("17:30");
        
        // Expected Output: false (time conflict exists)
        assertFalse(driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase3_BackToBackTripsAllowed() throws ParseException {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        newTrip.setDepartureTime("11:00");
        newTrip.setArrivalTime("13:00");
        
        // Expected Output: true (adjacent boundaries not considered conflict)
        assertTrue(driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase4_CompleteTimeEnclosureRejection() throws ParseException {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        existingTrip.setDepartureTime("10:00");
        existingTrip.setArrivalTime("16:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        newTrip.setDepartureTime("12:00");
        newTrip.setArrivalTime("14:00");
        
        // Expected Output: false (complete overlap)
        assertFalse(driver.canPostTrip(newTrip));
    }
    
    @Test
    public void testCase5_MultipleExistingTripComparison() throws ParseException {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00
        Trip existingTrip1 = new Trip();
        existingTrip1.setDepartureDate(dateFormat.parse("2023-12-21 00:00:00"));
        existingTrip1.setDepartureTime("08:00");
        existingTrip1.setArrivalTime("10:00");
        driver.getTrips().add(existingTrip1);
        
        // 2023-12-21 11:00-13:00
        Trip existingTrip2 = new Trip();
        existingTrip2.setDepartureDate(dateFormat.parse("2023-12-21 00:00:00"));
        existingTrip2.setDepartureTime("11:00");
        existingTrip2.setArrivalTime("13:00");
        driver.getTrips().add(existingTrip2);
        
        // 2023-12-23 14:00-16:00
        Trip existingTrip3 = new Trip();
        existingTrip3.setDepartureDate(dateFormat.parse("2023-12-23 00:00:00"));
        existingTrip3.setDepartureTime("14:00");
        existingTrip3.setArrivalTime("16:00");
        driver.getTrips().add(existingTrip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first trip)
        Trip newTrip = new Trip();
        newTrip.setDepartureDate(dateFormat.parse("2023-12-21 00:00:00"));
        newTrip.setDepartureTime("09:30");
        newTrip.setArrivalTime("10:30");
        
        // Expected Output: false (overlaps with first trip)
        assertFalse(driver.canPostTrip(newTrip));
    }
}