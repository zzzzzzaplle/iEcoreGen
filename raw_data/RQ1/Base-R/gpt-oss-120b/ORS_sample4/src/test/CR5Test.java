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
        // Initialize drivers with their existing trips as per test specifications
        driverD5 = new Driver("D5", "d5@example.com", "555-0001");
        driverD6 = new Driver("D6", "d6@example.com", "555-0002");
        driverD7 = new Driver("D7", "d7@example.com", "555-0003");
        driverD8 = new Driver("D8", "d8@example.com", "555-0004");
        driverD9 = new Driver("D9", "d9@example.com", "555-0005");
        
        // Setup existing trips for each driver
        setupDriverD5ExistingTrips();
        setupDriverD6ExistingTrips();
        setupDriverD7ExistingTrips();
        setupDriverD8ExistingTrips();
        setupDriverD9ExistingTrips();
    }
    
    private void setupDriverD5ExistingTrips() {
        // Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new DirectTrip("T5-1", driverD5, "Station A", "Station B", 
            4, 
            LocalDateTime.of(2023, 12, 25, 9, 0),
            LocalDateTime.of(2023, 12, 25, 11, 0),
            25.0);
        driverD5.addTrip(existingTrip);
    }
    
    private void setupDriverD6ExistingTrips() {
        // Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = new DirectTrip("T6-1", driverD6, "Station C", "Station D", 
            4, 
            LocalDateTime.of(2023, 12, 25, 14, 0),
            LocalDateTime.of(2023, 12, 25, 16, 0),
            30.0);
        driverD6.addTrip(existingTrip);
    }
    
    private void setupDriverD7ExistingTrips() {
        // Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new DirectTrip("T7-1", driverD7, "Station E", "Station F", 
            4, 
            LocalDateTime.of(2023, 12, 25, 9, 0),
            LocalDateTime.of(2023, 12, 25, 11, 0),
            20.0);
        driverD7.addTrip(existingTrip);
    }
    
    private void setupDriverD8ExistingTrips() {
        // Driver D8 has existing trip at 10:00-16:00 (assuming same day as new trip)
        Trip existingTrip = new DirectTrip("T8-1", driverD8, "Station G", "Station H", 
            4, 
            LocalDateTime.of(2023, 12, 25, 10, 0), // Using 2023-12-25 as reference date
            LocalDateTime.of(2023, 12, 25, 16, 0),
            35.0);
        driverD8.addTrip(existingTrip);
    }
    
    private void setupDriverD9ExistingTrips() {
        // Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        Trip trip1 = new DirectTrip("T9-1", driverD9, "Station I", "Station J", 
            4, 
            LocalDateTime.of(2023, 12, 21, 8, 0),
            LocalDateTime.of(2023, 12, 21, 10, 0),
            40.0);
        
        Trip trip2 = new DirectTrip("T9-2", driverD9, "Station K", "Station L", 
            4, 
            LocalDateTime.of(2023, 12, 21, 11, 0),
            LocalDateTime.of(2023, 12, 21, 13, 0),
            45.0);
        
        Trip trip3 = new DirectTrip("T9-3", driverD9, "Station M", "Station N", 
            4, 
            LocalDateTime.of(2023, 12, 23, 14, 0),
            LocalDateTime.of(2023, 12, 23, 16, 0),
            50.0);
            
        driverD9.addTrip(trip1);
        driverD9.addTrip(trip2);
        driverD9.addTrip(trip3);
    }
    
    @Test
    public void testCase1_ValidTripPostingWithTimeGap() {
        // Test Case 1: "Valid trip posting with time gap"
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        // Expected Output: true
        
        Trip newTrip = new DirectTrip("T5-2", driverD5, "Station X", "Station Y", 
            4, 
            LocalDateTime.of(2023, 12, 25, 13, 0),
            LocalDateTime.of(2023, 12, 25, 15, 0),
            28.0);
        
        boolean result = ORSService.validateTripPostingFeasibility(driverD5, newTrip);
        assertTrue("Trip with time gap should be allowed", result);
    }
    
    @Test
    public void testCase2_PostingDeniedDueToTimeConflict() {
        // Test Case 2: "Posting denied due to time conflict"
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        // Expected Output: false
        
        Trip newTrip = new DirectTrip("T6-2", driverD6, "Station P", "Station Q", 
            4, 
            LocalDateTime.of(2023, 12, 25, 14, 30),
            LocalDateTime.of(2023, 12, 25, 17, 30),
            32.0);
        
        boolean result = ORSService.validateTripPostingFeasibility(driverD6, newTrip);
        assertFalse("Overlapping trips should be rejected", result);
    }
    
    @Test
    public void testCase3_BackToBackTripsAllowed() {
        // Test Case 3: "Back-to-back trips allowed"
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        // Expected Output: true
        // Tests exact boundary handling
        
        Trip newTrip = new DirectTrip("T7-2", driverD7, "Station R", "Station S", 
            4, 
            LocalDateTime.of(2023, 12, 25, 11, 0), // Ends when existing trip starts
            LocalDateTime.of(2023, 12, 25, 13, 0),
            22.0);
        
        boolean result = ORSService.validateTripPostingFeasibility(driverD7, newTrip);
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_CompleteTimeEnclosureRejection() {
        // Test Case 4: "Complete time enclosure rejection"
        // Setup: Driver D8 has existing trip at 10:00-16:00
        // New trip proposed by D8: 12:00-14:00
        // Expected Output: false
        // Tests complete overlap
        
        Trip newTrip = new DirectTrip("T8-2", driverD8, "Station T", "Station U", 
            4, 
            LocalDateTime.of(2023, 12, 25, 12, 0), // Completely within existing trip
            LocalDateTime.of(2023, 12, 25, 14, 0),
            38.0);
        
        boolean result = ORSService.validateTripPostingFeasibility(driverD8, newTrip);
        assertFalse("Completely overlapping trips should be rejected", result);
    }
    
    @Test
    public void testCase5_MultipleExistingTripComparison() {
        // Test Case 5: "Multiple existing trip comparison"
        // Setup: Driver D9 has Existing trips at 2023-12-21 08:00-10:00, 
        // 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        // Expected Output: false
        // Tests multiple conflict detection
        
        Trip newTrip = new DirectTrip("T9-4", driverD9, "Station V", "Station W", 
            4, 
            LocalDateTime.of(2023, 12, 21, 9, 30), // Overlaps with first trip (08:00-10:00)
            LocalDateTime.of(2023, 12, 21, 10, 30),
            42.0);
        
        boolean result = ORSService.validateTripPostingFeasibility(driverD9, newTrip);
        assertFalse("Trip overlapping with existing trips should be rejected", result);
    }
}