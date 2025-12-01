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
        Driver driver = new Driver();
        driver.setId("D5");
        
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        driver.setTrips(existingTrips);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility(driver, newTrip);
        
        // Verify: Should return true (no time conflict, time gap exists)
        assertTrue("Valid trip with time gap should be feasible", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Driver driver = new Driver();
        driver.setId("D6");
        
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        driver.setTrips(existingTrips);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 14:30:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 17:30:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility(driver, newTrip);
        
        // Verify: Should return false (time conflict exists)
        assertFalse("Trip with time conflict should be rejected", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Driver driver = new Driver();
        driver.setId("D7");
        
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        driver.setTrips(existingTrips);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility(driver, newTrip);
        
        // Verify: Should return true (adjacent boundaries allowed)
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 2023-12-25 10:00-16:00
        Driver driver = new Driver();
        driver.setId("D8");
        
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        existingTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        
        List<Trip> existingTrips = new ArrayList<>();
        existingTrips.add(existingTrip);
        driver.setTrips(existingTrips);
        
        // New trip proposed by D8: 2023-12-25 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility(driver, newTrip);
        
        // Verify: Should return false (complete enclosure conflict)
        assertFalse("Complete time enclosure should be rejected", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has 3 existing trips
        Driver driver = new Driver();
        driver.setId("D9");
        
        List<Trip> existingTrips = new ArrayList<>();
        
        // Trip 1: 2023-12-21 08:00-10:00
        Trip trip1 = new Trip();
        trip1.setDepartureTime(LocalDateTime.parse("2023-12-21 08:00:00", formatter));
        trip1.setArrivalTime(LocalDateTime.parse("2023-12-21 10:00:00", formatter));
        existingTrips.add(trip1);
        
        // Trip 2: 2023-12-21 11:00-13:00
        Trip trip2 = new Trip();
        trip2.setDepartureTime(LocalDateTime.parse("2023-12-21 11:00:00", formatter));
        trip2.setArrivalTime(LocalDateTime.parse("2023-12-21 13:00:00", formatter));
        existingTrips.add(trip2);
        
        // Trip 3: 2023-12-23 14:00-16:00
        Trip trip3 = new Trip();
        trip3.setDepartureTime(LocalDateTime.parse("2023-12-23 14:00:00", formatter));
        trip3.setArrivalTime(LocalDateTime.parse("2023-12-23 16:00:00", formatter));
        existingTrips.add(trip3);
        
        driver.setTrips(existingTrips);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first trip)
        Trip newTrip = new Trip();
        newTrip.setDepartureTime(LocalDateTime.parse("2023-12-21 09:30:00", formatter));
        newTrip.setArrivalTime(LocalDateTime.parse("2023-12-21 10:30:00", formatter));
        
        // Execute validation
        boolean result = system.validateTripPostingFeasibility(driver, newTrip);
        
        // Verify: Should return false (conflicts with first trip)
        assertFalse("Trip conflicting with existing trips should be rejected", result);
    }
}