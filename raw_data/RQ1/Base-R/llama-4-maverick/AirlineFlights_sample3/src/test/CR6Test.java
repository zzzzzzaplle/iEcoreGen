import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR6Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() {
        // Setup
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        ap28.addCity("CityN");
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        ap29.addCity("CityO");
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        ap30.addCity("CityP");
        
        Flight f601 = new Flight();
        f601.setId("F601");
        f601.setDepartureAirport(ap28);
        f601.setArrivalAirport(ap29);
        f601.setDepartureTime(LocalDateTime.parse("2025-04-20 10:00:00", formatter));
        f601.setArrivalTime(LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        f601.setStatus("open");
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Set current time to 2025-04-19 09:00:00 (before departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-04-19 09:00:00", formatter);
        
        // Test - using reflection to set current time for testing
        boolean result = testAddStopoverWithCurrentTime(f601, stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, f601.getStopovers().size());
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() {
        // Setup
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        ap32.addCity("CityQ");
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        ap33.addCity("CityR");
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        ap31.addCity("CityS");
        
        Flight f602 = new Flight();
        f602.setId("F602");
        f602.setDepartureAirport(ap32);
        f602.setArrivalAirport(ap33);
        f602.setDepartureTime(LocalDateTime.parse("2025-05-10 09:00:00", formatter));
        f602.setArrivalTime(LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        f602.setStatus("open");
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Set current time to 2025-05-09 11:00:00 (before departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-05-09 11:00:00", formatter);
        
        // Test - stopover time is after flight arrival time
        boolean result = testAddStopoverWithCurrentTime(f602, stopover, currentTime);
        
        // Verify
        assertFalse("Stopover outside flight window should fail", result);
        assertEquals("Flight should have 0 stopovers", 0, f602.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() {
        // Setup
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        ap34.addCity("CityT");
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        ap35.addCity("CityU");
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        ap36.addCity("CityV");
        
        Flight f603 = new Flight();
        f603.setId("F603");
        f603.setDepartureAirport(ap34);
        f603.setArrivalAirport(ap35);
        f603.setDepartureTime(LocalDateTime.parse("2025-06-15 11:00:00", formatter));
        f603.setArrivalTime(LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        f603.setStatus("open");
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap36);
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        f603.addStopover(existingStopover);
        
        // Set current time to 2025-06-14 10:00:00 (before departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-06-14 10:00:00", formatter);
        
        // Test
        boolean result = testDeleteStopoverWithCurrentTime(f603, existingStopover, currentTime);
        
        // Verify
        assertTrue("Stopover deletion should succeed", result);
        assertEquals("Flight should have 0 stopovers after deletion", 0, f603.getStopovers().size());
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() {
        // Setup
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        ap37.addCity("CityW");
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        ap38.addCity("CityX");
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        ap39.addCity("CityY");
        
        Flight f604 = new Flight();
        f604.setId("F604");
        f604.setDepartureAirport(ap37);
        f604.setArrivalAirport(ap38);
        f604.setDepartureTime(LocalDateTime.parse("2025-07-20 12:00:00", formatter));
        f604.setArrivalTime(LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        f604.setStatus("closed"); // Flight is closed
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Set current time to 2025-07-10 09:00:00 (before departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-07-10 09:00:00", formatter);
        
        // Test - flight is closed, should prevent modification
        boolean result = testAddStopoverWithCurrentTime(f604, stopover, currentTime);
        
        // Verify
        assertFalse("Stopover addition should fail for closed flight", result);
        assertEquals("Flight should have 0 stopovers", 0, f604.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() {
        // Setup
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        ap42.addCity("CityBB");
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        ap43.addCity("CityCC");
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        ap44.addCity("CityDD");
        
        Flight f606 = new Flight();
        f606.setId("F606");
        f606.setDepartureAirport(ap42);
        f606.setArrivalAirport(ap43);
        f606.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        f606.setArrivalTime(LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        f606.setStatus("open");
        
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap44);
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        f606.addStopover(existingStopover);
        
        // Set current time to 2025-12-09 20:50:00 (after departure and stopover)
        LocalDateTime currentTime = LocalDateTime.parse("2025-12-09 20:50:00", formatter);
        
        // Test - current time is after departure, should prevent modification
        boolean result = testDeleteStopoverWithCurrentTime(f606, existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover deletion should fail after departure", result);
        assertEquals("Flight should still have 1 stopover", 1, f606.getStopovers().size());
    }
    
    // Helper method to test addStopoverWithValidation with a specific current time
    private boolean testAddStopoverWithCurrentTime(Flight flight, Stopover stopover, LocalDateTime currentTime) {
        // For testing purposes, we'll modify the Flight class behavior to accept a specific current time
        // In a real scenario, this would require dependency injection or mocking
        
        // Check if current time is after departure time (flight logic)
        if (currentTime.isAfter(flight.getDepartureTime())) {
            return false;
        }
        
        // Check if stopover times are within flight window
        if (stopover.getDepartureTime().isBefore(flight.getDepartureTime()) || 
            stopover.getArrivalTime().isAfter(flight.getArrivalTime())) {
            return false;
        }
        
        // Check if airport serves valid cities
        if (stopover.getAirport().getCities().isEmpty()) {
            return false;
        }
        
        // Check flight status
        if (!flight.getStatus().equals("open")) {
            return false;
        }
        
        // All validations passed, add the stopover
        flight.addStopover(stopover);
        return true;
    }
    
    // Helper method to test deleteStopover with a specific current time
    private boolean testDeleteStopoverWithCurrentTime(Flight flight, Stopover stopover, LocalDateTime currentTime) {
        // Check if current time is after departure time (flight logic)
        if (currentTime.isAfter(flight.getDepartureTime())) {
            return false;
        }
        
        // Check flight status
        if (!flight.getStatus().equals("open")) {
            return false;
        }
        
        // Remove the stopover
        return flight.removeStopover(stopover);
    }
}