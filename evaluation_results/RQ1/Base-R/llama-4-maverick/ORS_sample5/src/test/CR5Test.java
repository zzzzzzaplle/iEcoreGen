import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private ORS ors;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        ors = new ORS();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        User driverD5 = new User();
        driverD5.setId("D5");
        ors.getUsers().add(driverD5);
        
        Trip existingTrip = new Trip();
        existingTrip.setId("T1");
        existingTrip.setDriverId("D5");
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        ors.getTrips().add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        
        // Expected Output: true
        assertTrue("Valid trip posting with time gap should return true", 
                   ors.validateTripPostingFeasibility("D5", newTrip));
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        User driverD6 = new User();
        driverD6.setId("D6");
        ors.getUsers().add(driverD6);
        
        Trip existingTrip = new Trip();
        existingTrip.setId("T2");
        existingTrip.setDriverId("D6");
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        ors.getTrips().add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:30:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 17:30:00", formatter));
        
        // Expected Output: false
        assertFalse("Posting should be denied due to time conflict", 
                    ors.validateTripPostingFeasibility("D6", newTrip));
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        User driverD7 = new User();
        driverD7.setId("D7");
        ors.getUsers().add(driverD7);
        
        Trip existingTrip = new Trip();
        existingTrip.setId("T3");
        existingTrip.setDriverId("D7");
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        ors.getTrips().add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        
        // Expected Output: true (adjacent boundaries not considered conflict)
        assertTrue("Back-to-back trips should be allowed", 
                   ors.validateTripPostingFeasibility("D7", newTrip));
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        User driverD8 = new User();
        driverD8.setId("D8");
        ors.getUsers().add(driverD8);
        
        Trip existingTrip = new Trip();
        existingTrip.setId("T4");
        existingTrip.setDriverId("D8");
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        ors.getTrips().add(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        
        // Expected Output: false (complete overlap)
        assertFalse("Complete time enclosure should be rejected", 
                    ors.validateTripPostingFeasibility("D8", newTrip));
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        User driverD9 = new User();
        driverD9.setId("D9");
        ors.getUsers().add(driverD9);
        
        Trip trip1 = new Trip();
        trip1.setId("T5-1");
        trip1.setDriverId("D9");
        trip1.setDepartureTime(LocalDateTime.parse("2023-12-21 08:00:00", formatter));
        trip1.setArrivalTime(LocalDateTime.parse("2023-12-21 10:00:00", formatter));
        ors.getTrips().add(trip1);
        
        Trip trip2 = new Trip();
        trip2.setId("T5-2");
        trip2.setDriverId("D9");
        trip2.setDepartureTime(LocalDateTime.parse("2023-12-21 11:00:00", formatter));
        trip2.setArrivalTime(LocalDateTime.parse("2023-12-21 13:00:00", formatter));
        ors.getTrips().add(trip2);
        
        Trip trip3 = new Trip();
        trip3.setId("T5-3");
        trip3.setDriverId("D9");
        trip3.setDepartureTime(LocalDateTime.parse("2023-12-23 14:00:00", formatter));
        trip3.setArrivalTime(LocalDateTime.parse("2023-12-23 16:00:00", formatter));
        ors.getTrips().add(trip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-21 09:30:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-21 10:30:00", formatter));
        
        // Expected Output: false (overlaps with first trip)
        assertFalse("Multiple trip conflict detection should work correctly", 
                    ors.validateTripPostingFeasibility("D9", newTrip));
    }
}