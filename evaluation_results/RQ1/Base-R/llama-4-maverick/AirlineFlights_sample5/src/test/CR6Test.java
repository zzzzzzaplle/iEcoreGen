import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR6Test {
    
    private DateTimeFormatter formatter;
    private Flight flight;
    private Stopover stopover;
    
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
        
        flight = new Flight();
        flight.setId("F601");
        flight.setDepartureAirport(ap28);
        flight.setArrivalAirport(ap29);
        flight.setDepartureTime(LocalDateTime.parse("2025-04-20 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        flight.setStatus("open");
        
        stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Set current time to before departure (2025-04-19 09:00:00)
        // This is simulated by the logic in addStopoverToFlight method which checks if current time is after departure
        
        // Execute
        boolean result = flight.addStopoverToFlight(stopover);
        
        // Verify
        assertTrue("Stopover should be added successfully when times are within flight window", result);
        assertEquals("Flight should have 1 stopover after addition", 1, flight.getStopovers().size());
        assertEquals("Added stopover airport should match", "AP30", flight.getStopovers().get(0).getAirport().getId());
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
        
        flight = new Flight();
        flight.setId("F602");
        flight.setDepartureAirport(ap32);
        flight.setArrivalAirport(ap33);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        flight.setStatus("open");
        
        stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter)); // Outside window
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter)); // Outside window
        
        // Set current time to before departure (2025-05-09 11:00:00)
        
        // Execute
        boolean result = flight.addStopoverToFlight(stopover);
        
        // Verify
        assertFalse("Stopover should fail when times are outside flight window", result);
        assertEquals("Flight should have 0 stopovers after failed addition", 0, flight.getStopovers().size());
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
        
        flight = new Flight();
        flight.setId("F603");
        flight.setDepartureAirport(ap34);
        flight.setArrivalAirport(ap35);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-15 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        flight.setStatus("open");
        
        // Add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap36);
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        flight.addStopover(existingStopover);
        
        // Set current time to before departure (2025-06-14 10:00:00)
        
        // Execute
        boolean result = flight.deleteStopoverFromFlight(existingStopover);
        
        // Verify
        assertTrue("Stopover should be deleted successfully", result);
        assertEquals("Flight should have 0 stopovers after deletion", 0, flight.getStopovers().size());
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
        
        flight = new Flight();
        flight.setId("F604");
        flight.setDepartureAirport(ap37);
        flight.setArrivalAirport(ap38);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-20 12:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        flight.setStatus("closed"); // Flight is closed
        
        stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Set current time to before departure (2025-07-10 09:00:00)
        
        // Execute
        boolean result = flight.addStopoverToFlight(stopover);
        
        // Verify
        assertFalse("Stopover should fail when flight is closed", result);
        assertEquals("Flight should have 0 stopovers after failed addition", 0, flight.getStopovers().size());
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
        
        flight = new Flight();
        flight.setId("F606");
        flight.setDepartureAirport(ap42);
        flight.setArrivalAirport(ap43);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        flight.setStatus("open");
        
        // Add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap44);
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        flight.addStopover(existingStopover);
        
        // Set current time to after departure (2025-12-09 20:50:00) - flight mid-air
        // This is simulated by the logic in deleteStopoverFromFlight method which checks if current time is after departure
        
        // Execute
        boolean result = flight.deleteStopoverFromFlight(existingStopover);
        
        // Verify
        assertFalse("Stopover deletion should fail when flight has departed", result);
        assertEquals("Flight should still have 1 stopover after failed deletion", 1, flight.getStopovers().size());
    }
}