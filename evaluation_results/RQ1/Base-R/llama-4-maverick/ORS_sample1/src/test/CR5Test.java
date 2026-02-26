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
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDriverId("D5");
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00", formatter));
        
        List<Trip> trips = new ArrayList<>();
        trips.add(existingTrip);
        system.setTrips(trips);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDriverId("D5");
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00", formatter));
        
        // Expected Output: true
        boolean result = system.validateTripPostingFeasibility("D5", newTrip);
        assertTrue("Trip posting should be valid with time gap", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDriverId("D6");
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00", formatter));
        
        List<Trip> trips = new ArrayList<>();
        trips.add(existingTrip);
        system.setTrips(trips);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDriverId("D6");
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:30", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 17:30", formatter));
        
        // Expected Output: false
        boolean result = system.validateTripPostingFeasibility("D6", newTrip);
        assertFalse("Trip posting should be denied due to time conflict", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDriverId("D7");
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00", formatter));
        
        List<Trip> trips = new ArrayList<>();
        trips.add(existingTrip);
        system.setTrips(trips);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDriverId("D7");
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 11:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 13:00", formatter));
        
        // Expected Output: true
        boolean result = system.validateTripPostingFeasibility("D7", newTrip);
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00 (assuming same day as new trip)
        Trip existingTrip = new Trip();
        existingTrip.setDriverId("D8");
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00", formatter));
        
        List<Trip> trips = new ArrayList<>();
        trips.add(existingTrip);
        system.setTrips(trips);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDriverId("D8");
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00", formatter));
        
        // Expected Output: false
        boolean result = system.validateTripPostingFeasibility("D8", newTrip);
        assertFalse("Trip posting should be rejected due to complete time enclosure", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        Trip trip1 = new Trip();
        trip1.setDriverId("D9");
        trip1.setDepartureTime(LocalDateTime.parse("2023-12-21 08:00", formatter));
        trip1.setArrivalTime(LocalDateTime.parse("2023-12-21 10:00", formatter));
        
        Trip trip2 = new Trip();
        trip2.setDriverId("D9");
        trip2.setDepartureTime(LocalDateTime.parse("2023-12-21 11:00", formatter));
        trip2.setArrivalTime(LocalDateTime.parse("2023-12-21 13:00", formatter));
        
        Trip trip3 = new Trip();
        trip3.setDriverId("D9");
        trip3.setDepartureTime(LocalDateTime.parse("2023-12-23 14:00", formatter));
        trip3.setArrivalTime(LocalDateTime.parse("2023-12-23 16:00", formatter));
        
        List<Trip> trips = new ArrayList<>();
        trips.add(trip1);
        trips.add(trip2);
        trips.add(trip3);
        system.setTrips(trips);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = new Trip();
        newTrip.setDriverId("D9");
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-21 09:30", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-21 10:30", formatter));
        
        // Expected Output: false
        boolean result = system.validateTripPostingFeasibility("D9", newTrip);
        assertFalse("Trip posting should be rejected due to conflict with multiple existing trips", result);
    }
}