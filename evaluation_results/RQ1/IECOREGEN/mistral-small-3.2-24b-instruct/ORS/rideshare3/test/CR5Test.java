package edu.rideshare.rideshare3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.rideshare.RideshareFactory;
import edu.rideshare.Driver;
import edu.rideshare.Trip;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR5Test {
    
    private RideshareFactory factory;
    private Driver driver;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory for object creation
        factory = RideshareFactory.eINSTANCE;
        driver = factory.createDriver();
    }
    
    @Test
    public void testCase1_ValidTripPostingWithTimeGap() throws Exception {
        // Setup: Driver D5 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = factory.createTrip();
        existingTrip.setDepartureDate(createDate("2023-12-25 09:00:00"));
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D5: 2023-12-25 13:00-15:00
        Trip newTrip = factory.createTrip();
        newTrip.setDepartureDate(createDate("2023-12-25 13:00:00"));
        newTrip.setDepartureTime("13:00");
        newTrip.setArrivalTime("15:00");
        
        // Validate trip posting - should return true (no time conflict)
        boolean result = driver.canPostTrip(newTrip);
        assertTrue("Trip with time gap should be allowed", result);
    }
    
    @Test
    public void testCase2_PostingDeniedDueToTimeConflict() throws Exception {
        // Setup: Driver D6 has existing trip at 2023-12-25 14:00-16:00
        Trip existingTrip = factory.createTrip();
        existingTrip.setDepartureDate(createDate("2023-12-25 14:00:00"));
        existingTrip.setDepartureTime("14:00");
        existingTrip.setArrivalTime("16:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D6: 2023-12-25 14:30-17:30
        Trip newTrip = factory.createTrip();
        newTrip.setDepartureDate(createDate("2023-12-25 14:30:00"));
        newTrip.setDepartureTime("14:30");
        newTrip.setArrivalTime("17:30");
        
        // Validate trip posting - should return false (time conflict)
        boolean result = driver.canPostTrip(newTrip);
        assertFalse("Trip with time conflict should be rejected", result);
    }
    
    @Test
    public void testCase3_BackToBackTripsAllowed() throws Exception {
        // Setup: Driver D7 has existing trip at 2023-12-25 09:00-11:00
        Trip existingTrip = factory.createTrip();
        existingTrip.setDepartureDate(createDate("2023-12-25 09:00:00"));
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D7: 2023-12-25 11:00-13:00 (back-to-back)
        Trip newTrip = factory.createTrip();
        newTrip.setDepartureDate(createDate("2023-12-25 11:00:00"));
        newTrip.setDepartureTime("11:00");
        newTrip.setArrivalTime("13:00");
        
        // Validate trip posting - should return true (adjacent boundaries allowed)
        boolean result = driver.canPostTrip(newTrip);
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_CompleteTimeEnclosureRejection() throws Exception {
        // Setup: Driver D8 has existing trip at 10:00-16:00
        Trip existingTrip = factory.createTrip();
        existingTrip.setDepartureDate(createDate("2023-12-25 10:00:00"));
        existingTrip.setDepartureTime("10:00");
        existingTrip.setArrivalTime("16:00");
        driver.getTrips().add(existingTrip);
        
        // New trip proposed by D8: 12:00-14:00 (completely within existing trip)
        Trip newTrip = factory.createTrip();
        newTrip.setDepartureDate(createDate("2023-12-25 12:00:00"));
        newTrip.setDepartureTime("12:00");
        newTrip.setArrivalTime("14:00");
        
        // Validate trip posting - should return false (complete overlap)
        boolean result = driver.canPostTrip(newTrip);
        assertFalse("Trip completely within existing trip should be rejected", result);
    }
    
    @Test
    public void testCase5_MultipleExistingTripComparison() throws Exception {
        // Setup: Driver D9 has 3 existing trips
        Trip trip1 = factory.createTrip();
        trip1.setDepartureDate(createDate("2023-12-21 08:00:00"));
        trip1.setDepartureTime("08:00");
        trip1.setArrivalTime("10:00");
        driver.getTrips().add(trip1);
        
        Trip trip2 = factory.createTrip();
        trip2.setDepartureDate(createDate("2023-12-21 11:00:00"));
        trip2.setDepartureTime("11:00");
        trip2.setArrivalTime("13:00");
        driver.getTrips().add(trip2);
        
        Trip trip3 = factory.createTrip();
        trip3.setDepartureDate(createDate("2023-12-23 14:00:00"));
        trip3.setDepartureTime("14:00");
        trip3.setArrivalTime("16:00");
        driver.getTrips().add(trip3);
        
        // New trip by D9: 2023-12-21 09:30-10:30 (overlaps first two trips)
        Trip newTrip = factory.createTrip();
        newTrip.setDepartureDate(createDate("2023-12-21 09:30:00"));
        newTrip.setDepartureTime("09:30");
        newTrip.setArrivalTime("10:30");
        
        // Validate trip posting - should return false (conflict with first trip)
        boolean result = driver.canPostTrip(newTrip);
        assertFalse("Trip conflicting with any existing trip should be rejected", result);
    }
    
    /**
     * Helper method to create Date objects from string format
     */
    private Date createDate(String dateString) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(dateString);
    }
}