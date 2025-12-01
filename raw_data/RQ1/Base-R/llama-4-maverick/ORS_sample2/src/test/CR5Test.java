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
    public void testCase1_ValidTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        List<Trip> existingTrips = new ArrayList<>();
        Trip existingTrip = new Trip();
        existingTrip.setDriverId("D5");
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        existingTrips.add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDriverId("D5");
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility("D5", newTrip, existingTrips);
        
        // Verify expected output: true
        assertTrue("Valid trip posting with time gap should return true", result);
    }
    
    @Test
    public void testCase2_PostingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        List<Trip> existingTrips = new ArrayList<>();
        Trip existingTrip = new Trip();
        existingTrip.setDriverId("D6");
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        existingTrips.add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDriverId("D6");
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:30:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 17:30:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility("D6", newTrip, existingTrips);
        
        // Verify expected output: false
        assertFalse("Posting should be denied due to time conflict", result);
    }
    
    @Test
    public void testCase3_BackToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        List<Trip> existingTrips = new ArrayList<>();
        Trip existingTrip = new Trip();
        existingTrip.setDriverId("D7");
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        existingTrips.add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDriverId("D7");
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility("D7", newTrip, existingTrips);
        
        // Verify expected output: true
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_CompleteTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        List<Trip> existingTrips = new ArrayList<>();
        Trip existingTrip = new Trip();
        existingTrip.setDriverId("D8");
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        existingTrips.add(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDriverId("D8");
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility("D8", newTrip, existingTrips);
        
        // Verify expected output: false
        assertFalse("Complete time enclosure should be rejected", result);
    }
    
    @Test
    public void testCase5_MultipleExistingTripComparison() {
        // Setup: Driver D9 has Existing trips at 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        List<Trip> existingTrips = new ArrayList<>();
        
        Trip existingTrip1 = new Trip();
        existingTrip1.setDriverId("D9");
        existingTrip1.setDepartureTime(LocalDateTime.parse("2023-12-21 08:00:00", formatter));
        existingTrip1.setArrivalTime(LocalDateTime.parse("2023-12-21 10:00:00", formatter));
        existingTrips.add(existingTrip1);
        
        Trip existingTrip2 = new Trip();
        existingTrip2.setDriverId("D9");
        existingTrip2.setDepartureTime(LocalDateTime.parse("2023-12-21 11:00:00", formatter));
        existingTrip2.setArrivalTime(LocalDateTime.parse("2023-12-21 13:00:00", formatter));
        existingTrips.add(existingTrip2);
        
        Trip existingTrip3 = new Trip();
        existingTrip3.setDriverId("D9");
        existingTrip3.setDepartureTime(LocalDateTime.parse("2023-12-23 14:00:00", formatter));
        existingTrip3.setArrivalTime(LocalDateTime.parse("2023-12-23 16:00:00", formatter));
        existingTrips.add(existingTrip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = new Trip();
        newTrip.setDriverId("D9");
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-21 09:30:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-21 10:30:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility("D9", newTrip, existingTrips);
        
        // Verify expected output: false
        assertFalse("Multiple time conflicts should be detected and rejected", result);
    }
}