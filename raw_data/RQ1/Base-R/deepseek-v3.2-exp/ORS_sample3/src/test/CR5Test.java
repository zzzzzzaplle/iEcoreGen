import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    private OnlineRideshareSystem system;
    private User driverD5;
    private User driverD6;
    private User driverD7;
    private User driverD8;
    private User driverD9;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Initialize drivers
        driverD5 = new User();
        driverD5.setUserId("D5");
        
        driverD6 = new User();
        driverD6.setUserId("D6");
        
        driverD7 = new User();
        driverD7.setUserId("D7");
        
        driverD8 = new User();
        driverD8.setUserId("D8");
        
        driverD9 = new User();
        driverD9.setUserId("D9");
    }
    
    @Test
    public void testCase1_ValidTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTripD5 = new Trip();
        existingTripD5.setDriver(driverD5);
        existingTripD5.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTripD5.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        system.getTrips().add(existingTripD5);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD5);
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility(driverD5, newTrip);
        
        // Verify expected output: true
        assertTrue("Trip posting should be valid with time gap", result);
    }
    
    @Test
    public void testCase2_PostingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTripD6 = new Trip();
        existingTripD6.setDriver(driverD6);
        existingTripD6.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        existingTripD6.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        system.getTrips().add(existingTripD6);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD6);
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:30:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 17:30:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility(driverD6, newTrip);
        
        // Verify expected output: false
        assertFalse("Trip posting should be denied due to time conflict", result);
    }
    
    @Test
    public void testCase3_BackToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTripD7 = new Trip();
        existingTripD7.setDriver(driverD7);
        existingTripD7.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTripD7.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        system.getTrips().add(existingTripD7);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD7);
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility(driverD7, newTrip);
        
        // Verify expected output: true (back-to-back trips should be allowed)
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_CompleteTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00 (assuming same day as new trip)
        Trip existingTripD8 = new Trip();
        existingTripD8.setDriver(driverD8);
        existingTripD8.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        existingTripD8.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        system.getTrips().add(existingTripD8);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD8);
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility(driverD8, newTrip);
        
        // Verify expected output: false (complete enclosure should be rejected)
        assertFalse("Complete time enclosure should be rejected", result);
    }
    
    @Test
    public void testCase5_MultipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        Trip existingTrip1 = new Trip();
        existingTrip1.setDriver(driverD9);
        existingTrip1.setDepartureTime(LocalDateTime.parse("2023-12-21 08:00:00", formatter));
        existingTrip1.setArrivalTime(LocalDateTime.parse("2023-12-21 10:00:00", formatter));
        system.getTrips().add(existingTrip1);
        
        Trip existingTrip2 = new Trip();
        existingTrip2.setDriver(driverD9);
        existingTrip2.setDepartureTime(LocalDateTime.parse("2023-12-21 11:00:00", formatter));
        existingTrip2.setArrivalTime(LocalDateTime.parse("2023-12-21 13:00:00", formatter));
        system.getTrips().add(existingTrip2);
        
        Trip existingTrip3 = new Trip();
        existingTrip3.setDriver(driverD9);
        existingTrip3.setDepartureTime(LocalDateTime.parse("2023-12-23 14:00:00", formatter));
        existingTrip3.setArrivalTime(LocalDateTime.parse("2023-12-23 16:00:00", formatter));
        system.getTrips().add(existingTrip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD9);
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-21 09:30:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-21 10:30:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility(driverD9, newTrip);
        
        // Verify expected output: false (should detect conflict with first trip)
        assertFalse("Multiple trip conflict should be detected", result);
    }
}