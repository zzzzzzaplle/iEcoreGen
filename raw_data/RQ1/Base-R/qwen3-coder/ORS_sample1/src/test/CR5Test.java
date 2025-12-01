import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    private OnlineRideshareSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Driver driverD5 = new Driver();
        driverD5.setUserID("D5");
        
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        existingTrip.setDriver(driverD5);
        driverD5.addTrip(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        
        // Execute and verify
        boolean result = system.validateTripPostingFeasibility(driverD5, newTrip);
        assertTrue("Valid trip posting with time gap should return true", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Driver driverD6 = new Driver();
        driverD6.setUserID("D6");
        
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        existingTrip.setDriver(driverD6);
        driverD6.addTrip(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:30:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 17:30:00", formatter));
        
        // Execute and verify
        boolean result = system.validateTripPostingFeasibility(driverD6, newTrip);
        assertFalse("Posting should be denied due to time conflict", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Driver driverD7 = new Driver();
        driverD7.setUserID("D7");
        
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        existingTrip.setDriver(driverD7);
        driverD7.addTrip(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        
        // Execute and verify
        boolean result = system.validateTripPostingFeasibility(driverD7, newTrip);
        assertTrue("Back-to-back trips should be allowed (adjacent boundaries)", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        Driver driverD8 = new Driver();
        driverD8.setUserID("D8");
        
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        existingTrip.setDriver(driverD8);
        driverD8.addTrip(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        
        // Execute and verify
        boolean result = system.validateTripPostingFeasibility(driverD8, newTrip);
        assertFalse("Complete time enclosure should be rejected", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        Driver driverD9 = new Driver();
        driverD9.setUserID("D9");
        
        // First existing trip
        Trip existingTrip1 = new Trip();
        existingTrip1.setDepartureTime(LocalDateTime.parse("2023-12-21 08:00:00", formatter));
        existingTrip1.setArrivalTime(LocalDateTime.parse("2023-12-21 10:00:00", formatter));
        existingTrip1.setDriver(driverD9);
        driverD9.addTrip(existingTrip1);
        
        // Second existing trip
        Trip existingTrip2 = new Trip();
        existingTrip2.setDepartureTime(LocalDateTime.parse("2023-12-21 11:00:00", formatter));
        existingTrip2.setArrivalTime(LocalDateTime.parse("2023-12-21 13:00:00", formatter));
        existingTrip2.setDriver(driverD9);
        driverD9.addTrip(existingTrip2);
        
        // Third existing trip
        Trip existingTrip3 = new Trip();
        existingTrip3.setDepartureTime(LocalDateTime.parse("2023-12-23 14:00:00", formatter));
        existingTrip3.setArrivalTime(LocalDateTime.parse("2023-12-23 16:00:00", formatter));
        existingTrip3.setDriver(driverD9);
        driverD9.addTrip(existingTrip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-21 09:30:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-21 10:30:00", formatter));
        
        // Execute and verify
        boolean result = system.validateTripPostingFeasibility(driverD9, newTrip);
        assertFalse("Multiple conflict detection should return false", result);
    }
}