import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Trip newTrip;
    private User driver;
    private List<Trip> existingTrips;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        newTrip = new Trip();
        driver = new User();
        existingTrips = new ArrayList<>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        User driverD5 = new User();
        driverD5.setId("D5");
        
        Trip existingTrip = new Trip();
        existingTrip.setDriver(driverD5);
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        existingTrips.add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        newTrip.setDriver(driverD5);
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        
        // Execute and verify
        boolean result = newTrip.validateTripPostingFeasibility(driverD5, existingTrips);
        assertTrue("Trip with time gap should be valid", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        User driverD6 = new User();
        driverD6.setId("D6");
        
        Trip existingTrip = new Trip();
        existingTrip.setDriver(driverD6);
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        existingTrips.add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        newTrip.setDriver(driverD6);
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:30:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 17:30:00", formatter));
        
        // Execute and verify
        boolean result = newTrip.validateTripPostingFeasibility(driverD6, existingTrips);
        assertFalse("Trip with time conflict should be invalid", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        User driverD7 = new User();
        driverD7.setId("D7");
        
        Trip existingTrip = new Trip();
        existingTrip.setDriver(driverD7);
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        existingTrips.add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        newTrip.setDriver(driverD7);
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        
        // Execute and verify
        boolean result = newTrip.validateTripPostingFeasibility(driverD7, existingTrips);
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        User driverD8 = new User();
        driverD8.setId("D8");
        
        Trip existingTrip = new Trip();
        existingTrip.setDriver(driverD8);
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        existingTrips.add(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        newTrip.setDriver(driverD8);
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        
        // Execute and verify
        boolean result = newTrip.validateTripPostingFeasibility(driverD8, existingTrips);
        assertFalse("Trip completely enclosed within existing trip should be rejected", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        User driverD9 = new User();
        driverD9.setId("D9");
        
        Trip trip1 = new Trip();
        trip1.setDriver(driverD9);
        trip1.setDepartureTime(LocalDateTime.parse("2023-12-21 08:00:00", formatter));
        trip1.setArrivalTime(LocalDateTime.parse("2023-12-21 10:00:00", formatter));
        existingTrips.add(trip1);
        
        Trip trip2 = new Trip();
        trip2.setDriver(driverD9);
        trip2.setDepartureTime(LocalDateTime.parse("2023-12-21 11:00:00", formatter));
        trip2.setArrivalTime(LocalDateTime.parse("2023-12-21 13:00:00", formatter));
        existingTrips.add(trip2);
        
        Trip trip3 = new Trip();
        trip3.setDriver(driverD9);
        trip3.setDepartureTime(LocalDateTime.parse("2023-12-23 14:00:00", formatter));
        trip3.setArrivalTime(LocalDateTime.parse("2023-12-23 16:00:00", formatter));
        existingTrips.add(trip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        newTrip.setDriver(driverD9);
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-21 09:30:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-21 10:30:00", formatter));
        
        // Execute and verify
        boolean result = newTrip.validateTripPostingFeasibility(driverD9, existingTrips);
        assertFalse("Trip overlapping with multiple existing trips should be rejected", result);
    }
}