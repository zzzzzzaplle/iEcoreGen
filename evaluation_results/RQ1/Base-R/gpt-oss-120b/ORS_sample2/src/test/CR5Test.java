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
        Trip existingTrip = new Trip();
        existingTrip.setDriver(driverD5);
        existingTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 9, 0));
        existingTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 11, 0));
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD5);
        newTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 13, 0));
        newTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 15, 0));
        
        // Execute validation
        boolean result = ORSService.validateTripPostingFeasibility(driverD5, newTrip, existingTrips);
        
        // Verify expected output: true
        assertTrue("Trip with time gap should be valid", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDriver(driverD6);
        existingTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 14, 0));
        existingTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 16, 0));
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD6);
        newTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 14, 30));
        newTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 17, 30));
        
        // Execute validation
        boolean result = ORSService.validateTripPostingFeasibility(driverD6, newTrip, existingTrips);
        
        // Verify expected output: false
        assertFalse("Overlapping trips should be rejected", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDriver(driverD7);
        existingTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 9, 0));
        existingTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 11, 0));
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD7);
        newTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 11, 0));
        newTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 13, 0));
        
        // Execute validation
        boolean result = ORSService.validateTripPostingFeasibility(driverD7, newTrip, existingTrips);
        
        // Verify expected output: true (adjacent boundaries allowed)
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00 (2023-12-25 assumed)
        Trip existingTrip = new Trip();
        existingTrip.setDriver(driverD8);
        existingTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 10, 0));
        existingTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 16, 0));
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD8);
        newTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 25, 12, 0));
        newTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 25, 14, 0));
        
        // Execute validation
        boolean result = ORSService.validateTripPostingFeasibility(driverD8, newTrip, existingTrips);
        
        // Verify expected output: false (complete overlap)
        assertFalse("Trip completely within existing trip should be rejected", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        Trip trip1 = new Trip();
        trip1.setDriver(driverD9);
        trip1.setDepartureDateTime(LocalDateTime.of(2023, 12, 21, 8, 0));
        trip1.setArrivalDateTime(LocalDateTime.of(2023, 12, 21, 10, 0));
        
        Trip trip2 = new Trip();
        trip2.setDriver(driverD9);
        trip2.setDepartureDateTime(LocalDateTime.of(2023, 12, 21, 11, 0));
        trip2.setArrivalDateTime(LocalDateTime.of(2023, 12, 21, 13, 0));
        
        Trip trip3 = new Trip();
        trip3.setDriver(driverD9);
        trip3.setDepartureDateTime(LocalDateTime.of(2023, 12, 23, 14, 0));
        trip3.setArrivalDateTime(LocalDateTime.of(2023, 12, 23, 16, 0));
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(trip1);
        existingTrips.add(trip2);
        existingTrips.add(trip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = new Trip();
        newTrip.setDriver(driverD9);
        newTrip.setDepartureDateTime(LocalDateTime.of(2023, 12, 21, 9, 30));
        newTrip.setArrivalDateTime(LocalDateTime.of(2023, 12, 21, 10, 30));
        
        // Execute validation
        boolean result = ORSService.validateTripPostingFeasibility(driverD9, newTrip, existingTrips);
        
        // Verify expected output: false (overlaps with first trip)
        assertFalse("Trip overlapping with existing trips should be rejected", result);
    }
}