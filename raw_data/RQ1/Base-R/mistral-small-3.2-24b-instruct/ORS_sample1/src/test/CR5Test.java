import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private Trip trip;
    private List<Trip> existingTrips;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        trip = new Trip();
        existingTrips = new ArrayList<>();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_validTripPostingWithTimeGap() {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDriverId("D5");
        existingTrip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTrip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        existingTrips.add(existingTrip);
        
        // Mock the static method to return existing trips
        Trip testTrip = new Trip() {
            @Override
            protected static List<Trip> getTripsByDriver(String driverId) {
                return existingTrips;
            }
        };
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = new Trip();
        newTrip.setDriverId("D5");
        newTrip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        newTrip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 15:00:00", formatter));
        
        // Execute validation
        boolean result = Trip.validateTripPostingFeasibility("D5", newTrip);
        
        // Verify result
        assertTrue("Trip with time gap should be valid", result);
    }
    
    @Test
    public void testCase2_postingDeniedDueToTimeConflict() {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = new Trip();
        existingTrip.setDriverId("D6");
        existingTrip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        existingTrip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        existingTrips.add(existingTrip);
        
        // Mock the static method to return existing trips
        Trip testTrip = new Trip() {
            @Override
            protected static List<Trip> getTripsByDriver(String driverId) {
                return existingTrips;
            }
        };
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = new Trip();
        newTrip.setDriverId("D6");
        newTrip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 14:30:00", formatter));
        newTrip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 17:30:00", formatter));
        
        // Execute validation
        boolean result = Trip.validateTripPostingFeasibility("D6", newTrip);
        
        // Verify result
        assertFalse("Trip with time conflict should be invalid", result);
    }
    
    @Test
    public void testCase3_backToBackTripsAllowed() {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = new Trip();
        existingTrip.setDriverId("D7");
        existingTrip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 09:00:00", formatter));
        existingTrip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        existingTrips.add(existingTrip);
        
        // Mock the static method to return existing trips
        Trip testTrip = new Trip() {
            @Override
            protected static List<Trip> getTripsByDriver(String driverId) {
                return existingTrips;
            }
        };
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00
        Trip newTrip = new Trip();
        newTrip.setDriverId("D7");
        newTrip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 11:00:00", formatter));
        newTrip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 13:00:00", formatter));
        
        // Execute validation
        boolean result = Trip.validateTripPostingFeasibility("D7", newTrip);
        
        // Verify result - back-to-back trips should be allowed
        assertTrue("Back-to-back trips should be valid", result);
    }
    
    @Test
    public void testCase4_completeTimeEnclosureRejection() {
        // Setup: Driver D8 has existing trip at 10:00-16:00 (assuming same day)
        Trip existingTrip = new Trip();
        existingTrip.setDriverId("D8");
        existingTrip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 10:00:00", formatter));
        existingTrip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 16:00:00", formatter));
        existingTrips.add(existingTrip);
        
        // Mock the static method to return existing trips
        Trip testTrip = new Trip() {
            @Override
            protected static List<Trip> getTripsByDriver(String driverId) {
                return existingTrips;
            }
        };
        
        // New trip proposed by D8: 12:00-14:00
        Trip newTrip = new Trip();
        newTrip.setDriverId("D8");
        newTrip.setDepartureDateTime(LocalDateTime.parse("2023-12-25 12:00:00", formatter));
        newTrip.setArrivalDateTime(LocalDateTime.parse("2023-12-25 14:00:00", formatter));
        
        // Execute validation
        boolean result = Trip.validateTripPostingFeasibility("D8", newTrip);
        
        // Verify result - completely enclosed trip should be rejected
        assertFalse("Completely enclosed trip should be invalid", result);
    }
    
    @Test
    public void testCase5_multipleExistingTripComparison() {
        // Setup: Driver D9 has existing trips at:
        // 2023-12-21 08:00-10:00, 2023-12-21 11:00-13:00, 2023-12-23 14:00-16:00
        Trip trip1 = new Trip();
        trip1.setDriverId("D9");
        trip1.setDepartureDateTime(LocalDateTime.parse("2023-12-21 08:00:00", formatter));
        trip1.setArrivalDateTime(LocalDateTime.parse("2023-12-21 10:00:00", formatter));
        existingTrips.add(trip1);
        
        Trip trip2 = new Trip();
        trip2.setDriverId("D9");
        trip2.setDepartureDateTime(LocalDateTime.parse("2023-12-21 11:00:00", formatter));
        trip2.setArrivalDateTime(LocalDateTime.parse("2023-12-21 13:00:00", formatter));
        existingTrips.add(trip2);
        
        Trip trip3 = new Trip();
        trip3.setDriverId("D9");
        trip3.setDepartureDateTime(LocalDateTime.parse("2023-12-23 14:00:00", formatter));
        trip3.setArrivalDateTime(LocalDateTime.parse("2023-12-23 16:00:00", formatter));
        existingTrips.add(trip3);
        
        // Mock the static method to return existing trips
        Trip testTrip = new Trip() {
            @Override
            protected static List<Trip> getTripsByDriver(String driverId) {
                return existingTrips;
            }
        };
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two)
        Trip newTrip = new Trip();
        newTrip.setDriverId("D9");
        newTrip.setDepartureDateTime(LocalDateTime.parse("2023-12-21 09:30:00", formatter));
        newTrip.setArrivalDateTime(LocalDateTime.parse("2023-12-21 10:30:00", formatter));
        
        // Execute validation
        boolean result = Trip.validateTripPostingFeasibility("D9", newTrip);
        
        // Verify result - trip overlapping with existing trips should be rejected
        assertFalse("Trip overlapping with multiple existing trips should be invalid", result);
    }
}