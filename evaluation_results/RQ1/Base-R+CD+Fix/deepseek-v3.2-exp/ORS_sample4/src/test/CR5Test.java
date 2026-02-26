import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Driver driverD5;
    private Driver driverD6;
    private Driver driverD7;
    private Driver driverD8;
    private Driver driverD9;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        
        // Initialize drivers
        driverD5 = new Driver();
        driverD6 = new Driver();
        driverD7 = new Driver();
        driverD8 = new Driver();
        driverD9 = new Driver();
        
        // Set up existing trips for each driver
        setupDriverD5Trips();
        setupDriverD6Trips();
        setupDriverD7Trips();
        setupDriverD8Trips();
        setupDriverD9Trips();
    }
    
    private void setupDriverD5Trips() {
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 09:00");
        existingTrip.setArrivalTime("2023-12-25 11:00");
        driverD5.getTrips().add(existingTrip);
    }
    
    private void setupDriverD6Trips() {
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 14:00");
        existingTrip.setArrivalTime("2023-12-25 16:00");
        driverD6.getTrips().add(existingTrip);
    }
    
    private void setupDriverD7Trips() {
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 09:00");
        existingTrip.setArrivalTime("2023-12-25 11:00");
        driverD7.getTrips().add(existingTrip);
    }
    
    private void setupDriverD8Trips() {
        Trip existingTrip = new Trip();
        existingTrip.setDepartureTime("2023-12-25 10:00");
        existingTrip.setArrivalTime("2023-12-25 16:00");
        driverD8.getTrips().add(existingTrip);
    }
    
    private void setupDriverD9Trips() {
        Trip trip1 = new Trip();
        trip1.setDepartureTime("2023-12-21 08:00");
        trip1.setArrivalTime("2023-12-21 10:00");
        driverD9.getTrips().add(trip1);
        
        Trip trip2 = new Trip();
        trip2.setDepartureTime("2023-12-21 11:00");
        trip2.setArrivalTime("2023-12-21 13:00");
        driverD9.getTrips().add(trip2);
        
        Trip trip3 = new Trip();
        trip3.setDepartureTime("2023-12-23 14:00");
        trip3.setArrivalTime("2023-12-23 16:00");
        driverD9.getTrips().add(trip3);
    }
    
    @Test
    public void testCase1_ValidTripPostingWithTimeGap() {
        // Test Case 1: Valid trip posting with time gap
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 13:00");
        newTrip.setArrivalTime("2023-12-25 15:00");
        
        boolean result = driverD5.canPostTrip(newTrip);
        
        assertTrue("New trip should be allowed with time gap", result);
    }
    
    @Test
    public void testCase2_PostingDeniedDueToTimeConflict() {
        // Test Case 2: Posting denied due to time conflict
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 14:30");
        newTrip.setArrivalTime("2023-12-25 17:30");
        
        boolean result = driverD6.canPostTrip(newTrip);
        
        assertFalse("New trip should be denied due to time conflict", result);
    }
    
    @Test
    public void testCase3_BackToBackTripsAllowed() {
        // Test Case 3: Back-to-back trips allowed
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 11:00");
        newTrip.setArrivalTime("2023-12-25 13:00");
        
        boolean result = driverD7.canPostTrip(newTrip);
        
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_CompleteTimeEnclosureRejection() {
        // Test Case 4: Complete time enclosure rejection
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-25 12:00");
        newTrip.setArrivalTime("2023-12-25 14:00");
        
        boolean result = driverD8.canPostTrip(newTrip);
        
        assertFalse("New trip completely within existing trip should be rejected", result);
    }
    
    @Test
    public void testCase5_MultipleExistingTripComparison() {
        // Test Case 5: Multiple existing trip comparison
        Trip newTrip = new Trip();
        newTrip.setDepartureTime("2023-12-21 09:30");
        newTrip.setArrivalTime("2023-12-21 10:30");
        
        boolean result = driverD9.canPostTrip(newTrip);
        
        assertFalse("New trip overlapping with existing trips should be rejected", result);
    }
}