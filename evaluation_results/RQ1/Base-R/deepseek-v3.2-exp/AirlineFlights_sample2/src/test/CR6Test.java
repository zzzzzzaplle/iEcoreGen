import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class CR6Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() {
        // Setup
        Airline airline = new Airline();
        airline.setName("AL26");
        
        Airport ap28 = new Airport();
        ap28.setId("AP28");
        ap28.addServedCity("CityN");
        
        Airport ap29 = new Airport();
        ap29.setId("AP29");
        ap29.addServedCity("CityO");
        
        Airport ap30 = new Airport();
        ap30.setId("AP30");
        ap30.addServedCity("CityP");
        
        Flight flight = new Flight();
        flight.setId("F601");
        flight.setDepartureAirport(ap28);
        flight.setArrivalAirport(ap29);
        flight.setDepartureTime(LocalDateTime.parse("2025-04-20 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-20 15:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        airline.addFlight(flight);
        
        // Set current time to 2025-04-19 09:00:00
        // In a real test, we would mock LocalDateTime.now(), but here we rely on the flight's internal logic
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Execute
        boolean result = flight.addStopoverToFlight(stopover);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() {
        // Setup
        Airline airline = new Airline();
        airline.setName("AL27");
        
        Airport ap32 = new Airport();
        ap32.setId("AP32");
        ap32.addServedCity("CityQ");
        
        Airport ap33 = new Airport();
        ap33.setId("AP33");
        ap33.addServedCity("CityR");
        
        Airport ap31 = new Airport();
        ap31.setId("AP31");
        ap31.addServedCity("CityS");
        
        Flight flight = new Flight();
        flight.setId("F602");
        flight.setDepartureAirport(ap32);
        flight.setArrivalAirport(ap33);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-10 14:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        airline.addFlight(flight);
        
        // Set current time to 2025-05-09 11:00:00
        // In a real test, we would mock LocalDateTime.now(), but here we rely on the flight's internal logic
        
        // Create stopover with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Execute
        boolean result = flight.addStopoverToFlight(stopover);
        
        // Verify
        assertFalse("Stopover should not be added due to time outside window", result);
    }
    
    @Test
    public void testCase3_deleteExistingStopover() {
        // Setup
        Airline airline = new Airline();
        airline.setName("AL28");
        
        Airport ap34 = new Airport();
        ap34.setId("AP34");
        ap34.addServedCity("CityT");
        
        Airport ap35 = new Airport();
        ap35.setId("AP35");
        ap35.addServedCity("CityU");
        
        Airport ap36 = new Airport();
        ap36.setId("AP36");
        ap36.addServedCity("CityV");
        
        Flight flight = new Flight();
        flight.setId("F603");
        flight.setDepartureAirport(ap34);
        flight.setArrivalAirport(ap35);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-15 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-15 18:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        // Add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap36);
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        flight.addStopover(existingStopover);
        
        airline.addFlight(flight);
        
        // Set current time to 2025-06-14 10:00:00
        // In a real test, we would mock LocalDateTime.now(), but here we rely on the flight's internal logic
        
        // Execute
        boolean result = flight.deleteStopoverFromFlight(existingStopover);
        
        // Verify
        assertTrue("Stopover should be deleted successfully", result);
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() {
        // Setup
        Airline airline = new Airline();
        airline.setName("AL29");
        
        Airport ap37 = new Airport();
        ap37.setId("AP37");
        ap37.addServedCity("CityW");
        
        Airport ap38 = new Airport();
        ap38.setId("AP38");
        ap38.addServedCity("CityX");
        
        Airport ap39 = new Airport();
        ap39.setId("AP39");
        ap39.addServedCity("CityY");
        
        Flight flight = new Flight();
        flight.setId("F604");
        flight.setDepartureAirport(ap37);
        flight.setArrivalAirport(ap38);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-20 12:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-20 17:00:00", formatter));
        flight.setStatus(FlightStatus.CLOSED); // Flight is closed
        
        airline.addFlight(flight);
        
        // Set current time to 2025-07-10 09:00:00
        // In a real test, we would mock LocalDateTime.now(), but here we rely on the flight's internal logic
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(ap39);
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Execute
        boolean result = flight.addStopoverToFlight(stopover);
        
        // Verify
        assertFalse("Stopover should not be added because flight is closed", result);
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() {
        // Setup
        Airline airline = new Airline();
        airline.setName("AL31");
        
        Airport ap42 = new Airport();
        ap42.setId("AP42");
        ap42.addServedCity("CityBB");
        
        Airport ap43 = new Airport();
        ap43.setId("AP43");
        ap43.addServedCity("CityCC");
        
        Airport ap44 = new Airport();
        ap44.setId("AP44");
        ap44.addServedCity("CityDD");
        
        Flight flight = new Flight();
        flight.setId("F606");
        flight.setDepartureAirport(ap42);
        flight.setArrivalAirport(ap43);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-09 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 00:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        // Add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap44);
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        flight.addStopover(existingStopover);
        
        airline.addFlight(flight);
        
        // Set current time to 2025-12-09 20:50:00 (after departure and after stopover)
        // In a real test, we would mock LocalDateTime.now(), but here we rely on the flight's internal logic
        
        // Execute
        boolean result = flight.deleteStopoverFromFlight(existingStopover);
        
        // Verify
        assertFalse("Stopover should not be deleted because flight has departed", result);
    }
}