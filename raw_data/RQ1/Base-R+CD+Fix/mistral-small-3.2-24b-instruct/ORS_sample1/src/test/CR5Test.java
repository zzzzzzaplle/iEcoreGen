import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Driver driver;
    private List<Trip> existingTrips;
    
    @Before
    public void setUp() {
        driver = new Driver();
        existingTrips = new ArrayList<>();
        driver.setTrips(existingTrips);
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup existing trip for Driver D5
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 09:00:00");
        existingTrip.setArrivalTime("2023-12-25 11:00:00");
        existingTrips.add(existingTrip);
        
        // Setup new trip
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 13:00:00");
        newTrip.setArrivalTime("2023-12-25 15:00:00");
        
        // Test validation
        boolean result = driver.canPostTrip(newTrip);
        assertTrue("New trip with time gap should be valid", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup existing trip for Driver D6
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 14:00:00");
        existingTrip.setArrivalTime("2023-12-25 16:00:00");
        existingTrips.add(existingTrip);
        
        // Setup new trip that overlaps
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 14:30:00");
        newTrip.setArrivalTime("2023-12-25 17:30:00");
        
        // Test validation
        boolean result = driver.canPostTrip(newTrip);
        assertFalse("Overlapping trip should be rejected", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup existing trip for Driver D7
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 09:00:00");
        existingTrip.setArrivalTime("2023-12-25 11:00:00");
        existingTrips.add(existingTrip);
        
        // Setup new trip ending at existing trip's start
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 11:00:00");
        newTrip.setArrivalTime("2023-12-25 13:00:00");
        
        // Test validation
        boolean result = driver.canPostTrip(newTrip);
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup existing trip for Driver D8
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 10:00:00");
        existingTrip.setArrivalTime("2023-12-25 16:00:00");
        existingTrips.add(existingTrip);
        
        // Setup new trip completely within existing trip
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 12:00:00");
        newTrip.setArrivalTime("2023-12-25 14:00:00");
        
        // Test validation
        boolean result = driver.canPostTrip(newTrip);
        assertFalse("Completely overlapping trip should be rejected", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup multiple existing trips for Driver D9
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2023-12-21 08:00:00");
        trip1.setArrivalTime("2023-12-21 10:00:00");
        existingTrips.add(trip1);
        
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2023-12-21 11:00:00");
        trip2.setArrivalTime("2023-12-21 13:00:00");
        existingTrips.add(trip2);
        
        Trip trip3 = new Trip();
        trip3.setDepartureTime("2023-12-23 14:00:00");
        trip3.setArrivalTime("2023-12-23 16:00:00");
        existingTrips.add(trip3);
        
        // Setup new trip that overlaps with first two trips
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-21 09:30:00");
        newTrip.setArrivalTime("2023-12-21 10:30:00");
        
        // Test validation
        boolean result = driver.canPostTrip(newTrip);
        assertFalse("Trip overlapping with existing trips should be rejected", result);
    }
}