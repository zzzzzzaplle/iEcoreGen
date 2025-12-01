package edu.rideshare.rideshare5.test;

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
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = RideshareFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ValidTripPostingWithTimeGap() throws Exception {
        // Setup: Create driver D5 with existing trip
        Driver driver = factory.createDriver();
        driver.setID("D5");
        driver.setEmail("d5@example.com");
        driver.setPhoneNumber("1234567890");
        
        // Create existing trip for D5: 2023-12-25 09:00-11:00
        Trip existingTrip = factory.createTrip();
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        existingTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        driver.getTrips().add(existingTrip);
        
        // Create new trip proposal: 2023-12-25 13:00-15:00
        Trip newTrip = factory.createTrip();
        newTrip.setDepartureTime("13:00");
        newTrip.setArrivalTime("15:00");
        newTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        
        // Test: Validate trip posting
        boolean result = driver.canPostTrip(newTrip);
        
        // Verify: Should return true (no time conflict)
        assertTrue("Valid trip with time gap should be allowed", result);
    }
    
    @Test
    public void testCase2_PostingDeniedDueToTimeConflict() throws Exception {
        // Setup: Create driver D6 with existing trip
        Driver driver = factory.createDriver();
        driver.setID("D6");
        driver.setEmail("d6@example.com");
        driver.setPhoneNumber("1234567891");
        
        // Create existing trip for D6: 2023-12-25 14:00-16:00
        Trip existingTrip = factory.createTrip();
        existingTrip.setDepartureTime("14:00");
        existingTrip.setArrivalTime("16:00");
        existingTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        driver.getTrips().add(existingTrip);
        
        // Create new trip proposal: 2023-12-25 14:30-17:30 (overlaps with existing)
        Trip newTrip = factory.createTrip();
        newTrip.setDepartureTime("14:30");
        newTrip.setArrivalTime("17:30");
        newTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        
        // Test: Validate trip posting
        boolean result = driver.canPostTrip(newTrip);
        
        // Verify: Should return false (time conflict detected)
        assertFalse("Trip with time conflict should be rejected", result);
    }
    
    @Test
    public void testCase3_BackToBackTripsAllowed() throws Exception {
        // Setup: Create driver D7 with existing trip
        Driver driver = factory.createDriver();
        driver.setID("D7");
        driver.setEmail("d7@example.com");
        driver.setPhoneNumber("1234567892");
        
        // Create existing trip for D7: 2023-12-25 09:00-11:00
        Trip existingTrip = factory.createTrip();
        existingTrip.setDepartureTime("09:00");
        existingTrip.setArrivalTime("11:00");
        existingTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        driver.getTrips().add(existingTrip);
        
        // Create new trip proposal: 2023-12-25 11:00-13:00 (back-to-back)
        Trip newTrip = factory.createTrip();
        newTrip.setDepartureTime("11:00");
        newTrip.setArrivalTime("13:00");
        newTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        
        // Test: Validate trip posting
        boolean result = driver.canPostTrip(newTrip);
        
        // Verify: Should return true (adjacent boundaries allowed)
        assertTrue("Back-to-back trips should be allowed", result);
    }
    
    @Test
    public void testCase4_CompleteTimeEnclosureRejection() throws Exception {
        // Setup: Create driver D8 with existing trip
        Driver driver = factory.createDriver();
        driver.setID("D8");
        driver.setEmail("d8@example.com");
        driver.setPhoneNumber("1234567893");
        
        // Create existing trip for D8: 10:00-16:00
        Trip existingTrip = factory.createTrip();
        existingTrip.setDepartureTime("10:00");
        existingTrip.setArrivalTime("16:00");
        existingTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        driver.getTrips().add(existingTrip);
        
        // Create new trip proposal: 12:00-14:00 (completely within existing trip)
        Trip newTrip = factory.createTrip();
        newTrip.setDepartureTime("12:00");
        newTrip.setArrivalTime("14:00");
        newTrip.setDepartureDate(dateFormat.parse("2023-12-25 00:00:00"));
        
        // Test: Validate trip posting
        boolean result = driver.canPostTrip(newTrip);
        
        // Verify: Should return false (complete overlap detected)
        assertFalse("Trip completely within existing trip should be rejected", result);
    }
    
    @Test
    public void testCase5_MultipleExistingTripComparison() throws Exception {
        // Setup: Create driver D9 with multiple existing trips
        Driver driver = factory.createDriver();
        driver.setID("D9");
        driver.setEmail("d9@example.com");
        driver.setPhoneNumber("1234567894");
        
        // Create first existing trip: 2023-12-21 08:00-10:00
        Trip trip1 = factory.createTrip();
        trip1.setDepartureTime("08:00");
        trip1.setArrivalTime("10:00");
        trip1.setDepartureDate(dateFormat.parse("2023-12-21 00:00:00"));
        driver.getTrips().add(trip1);
        
        // Create second existing trip: 2023-12-21 11:00-13:00
        Trip trip2 = factory.createTrip();
        trip2.setDepartureTime("11:00");
        trip2.setArrivalTime("13:00");
        trip2.setDepartureDate(dateFormat.parse("2023-12-21 00:00:00"));
        driver.getTrips().add(trip2);
        
        // Create third existing trip: 2023-12-23 14:00-16:00
        Trip trip3 = factory.createTrip();
        trip3.setDepartureTime("14:00");
        trip3.setArrivalTime("16:00");
        trip3.setDepartureDate(dateFormat.parse("2023-12-23 00:00:00"));
        driver.getTrips().add(trip3);
        
        // Create new trip proposal: 2023-12-21 09:30-10:30 (overlaps first two trips)
        Trip newTrip = factory.createTrip();
        newTrip.setDepartureTime("09:30");
        newTrip.setArrivalTime("10:30");
        newTrip.setDepartureDate(dateFormat.parse("2023-12-21 00:00:00"));
        
        // Test: Validate trip posting
        boolean result = driver.canPostTrip(newTrip);
        
        // Verify: Should return false (conflict with first trip detected)
        assertFalse("Trip conflicting with multiple existing trips should be rejected", result);
    }
}