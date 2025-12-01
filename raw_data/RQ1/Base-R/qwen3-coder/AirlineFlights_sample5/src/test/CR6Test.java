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
    public void testCase1_AddFirstStopoverInsideJourneyWindow() {
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
        
        Flight flight = new Flight();
        flight.setId("F601");
        flight.setDepartureAirport(ap28);
        flight.setArrivalAirport(ap29);
        flight.setDepartureTime(LocalDateTime.parse("2025-04-20 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Test
        boolean result = flight.addStopover(stopover);
        
        // Assert
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have one stopover", 1, flight.getStopovers().size());
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() {
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
        
        Flight flight = new Flight();
        flight.setId("F602");
        flight.setDepartureAirport(ap32);
        flight.setArrivalAirport(ap33);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Test
        boolean result = flight.addStopover(stopover);
        
        // Assert
        assertFalse("Stopover outside flight window should not be added", result);
        assertEquals("Flight should have no stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() {
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
        
        Flight flight = new Flight();
        flight.setId("F603");
        flight.setDepartureAirport(ap34);
        flight.setArrivalAirport(ap35);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-15 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap36);
        stopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        
        // Add the stopover first
        flight.addStopover(stopover);
        assertEquals("Flight should have one stopover before removal", 1, flight.getStopovers().size());
        
        // Test removal
        boolean result = flight.removeStopover(stopover);
        
        // Assert
        assertTrue("Stopover should be removed successfully", result);
        assertEquals("Flight should have no stopovers after removal", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() {
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
        
        Flight flight = new Flight();
        flight.setId("F604");
        flight.setDepartureAirport(ap37);
        flight.setArrivalAirport(ap38);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-20 12:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        flight.setStatus(FlightStatus.CLOSED);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Test
        boolean result = flight.addStopover(stopover);
        
        // Assert
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have no stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() {
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
        
        Flight flight = new Flight();
        flight.setId("F606");
        flight.setDepartureAirport(ap42);
        flight.setArrivalAirport(ap43);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap44);
        stopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        
        // Add the stopover first
        flight.addStopover(stopover);
        assertEquals("Flight should have one stopover before attempting removal", 1, flight.getStopovers().size());
        
        // Close the flight (simulating departure)
        flight.setStatus(FlightStatus.CLOSED);
        
        // Test removal
        boolean result = flight.removeStopover(stopover);
        
        // Assert
        assertFalse("Stopover should not be removed from closed flight", result);
        assertEquals("Flight should still have one stopover", 1, flight.getStopovers().size());
    }
}