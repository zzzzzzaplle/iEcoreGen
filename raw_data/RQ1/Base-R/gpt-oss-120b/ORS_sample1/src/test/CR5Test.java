import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Driver driverD5;
    private Driver driverD6;
    private Driver driverD7;
    private Driver driverD8;
    private Driver driverD9;
    
    @Before
    public void setUp() {
        // Initialize drivers for all test cases
        driverD5 = new Driver();
        driverD5.setId("D5");
        
        driverD6 = new Driver();
        driverD6.setId("D6");
        
        driverD7 = new Driver();
        driverD7.setId("D7");
        
        driverD8 = new Driver();
        driverD8.setId("D8");
        
        driverD9 = new Driver();
        driverD9.setId("D9");
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTripD5 = new Trip();
        existingTripD5.setDepartureDateTime(ORSService.parseDateTime("2023-12-25 09:00"));
        existingTripD5.setArrivalDateTime(ORSService.parseDateTime("2023-12-25 11:00"));
        driverD5.addTrip(existingTripD5);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTripD5 = new Trip();
        newTripD5.setDepartureDateTime(ORSService.parseDateTime("2023-12-25 13:00"));
        newTripD5.setArrivalDateTime(ORSService.parseDateTime("2023-12-25 15:00"));
        
        // Execute and verify
        boolean result = ORSService.validateTripPostingFeasibility(driverD5, newTripD5);
        assertTrue("Valid trip posting with time gap should return true", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTripD6 = new Trip();
        existingTripD6.setDepartureDateTime(ORSService.parseDateTime("2023-12-25 14:00"));
        existingTripD6.setArrivalDateTime(ORSService.parseDateTime("2023-12-25 16:00"));
        driverD6.addTrip(existingTripD6);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTripD6 = new Trip();
        newTripD6.setDepartureDateTime(ORSService.parseDateTime("2023-12-25 14:30"));
        newTripD6.setArrivalDateTime(ORSService.parseDateTime("2023-12-25 17:30"));
        
        // Execute and verify
        boolean result = ORSService.validateTripPostingFeasibility(driverD6, newTripD6);
        assertFalse("Posting with time conflict should return false", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTripD7 = new Trip();
        existingTripD7.setDepartureDateTime(ORSService.parseDateTime("2023-12-25 09:00"));
        existingTripD7.setArrivalDateTime(ORSService.parseDateTime("2023-12-25 11:00"));
        driverD7.addTrip(existingTripD7);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTripD7 = new Trip();
        newTripD7.setDepartureDateTime(ORSService.parseDateTime("2023-12-25 11:00"));
        newTripD7.setArrivalDateTime(ORSService.parseDateTime("2023-12-25 13:00"));
        
        // Execute and verify
        boolean result = ORSService.validateTripPostingFeasibility(driverD7, newTripD7);
        assertTrue("Back-to-back trips should be allowed (adjacent boundaries)", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00 (assuming same day 2023-12-25)
        Trip existingTripD8 = new Trip();
        existingTripD8.setDepartureDateTime(ORSService.parseDateTime("2023-12-25 10:00"));
        existingTripD8.setArrivalDateTime(ORSService.parseDateTime("2023-12-25 16:00"));
        driverD8.addTrip(existingTripD8);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTripD8 = new Trip();
        newTripD8.setDepartureDateTime(ORSService.parseDateTime("2023-12-25 12:00"));
        newTripD8.setArrivalDateTime(ORSService.parseDateTime("2023-12-25 14:00"));
        
        // Execute and verify
        boolean result = ORSService.validateTripPostingFeasibility(driverD8, newTripD8);
        assertFalse("Complete time enclosure should be rejected", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        Trip existingTrip1 = new Trip();
        existingTrip1.setDepartureDateTime(ORSService.parseDateTime("2023-12-21 08:00"));
        existingTrip1.setArrivalDateTime(ORSService.parseDateTime("2023-12-21 10:00"));
        driverD9.addTrip(existingTrip1);
        
        Trip existingTrip2 = new Trip();
        existingTrip2.setDepartureDateTime(ORSService.parseDateTime("2023-12-21 11:00"));
        existingTrip2.setArrivalDateTime(ORSService.parseDateTime("2023-12-21 13:00"));
        driverD9.addTrip(existingTrip2);
        
        Trip existingTrip3 = new Trip();
        existingTrip3.setDepartureDateTime(ORSService.parseDateTime("2023-12-23 14:00"));
        existingTrip3.setArrivalDateTime(ORSService.parseDateTime("2023-12-23 16:00"));
        driverD9.addTrip(existingTrip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTripD9 = new Trip();
        newTripD9.setDepartureDateTime(ORSService.parseDateTime("2023-12-21 09:30"));
        newTripD9.setArrivalDateTime(ORSService.parseDateTime("2023-12-21 10:30"));
        
        // Execute and verify
        boolean result = ORSService.validateTripPostingFeasibility(driverD9, newTripD9);
        assertFalse("Multiple conflict detection should return false", result);
    }
}