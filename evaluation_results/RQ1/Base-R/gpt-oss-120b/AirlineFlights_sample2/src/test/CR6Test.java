import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class CR6Test {
    private AirlineService airlineService;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    @Before
    public void setUp() {
        airlineService = new AirlineService();
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() {
        // Setup
        Airport ap28 = new Airport("AP28", "Airport AP28");
        ap28.addCity(new City("CityN"));
        
        Airport ap29 = new Airport("AP29", "Airport AP29");
        ap29.addCity(new City("CityO"));
        
        Airport ap30 = new Airport("AP30", "Airport AP30");
        ap30.addCity(new City("CityP"));
        
        Flight flight = new Flight("F601", ap28, ap29,
            LocalDateTime.parse("2025-04-20 10:00:00", FORMATTER),
            LocalDateTime.parse("2025-04-20 15:00:00", FORMATTER));
        flight.setStatus(FlightStatus.OPEN);
        
        airlineService.publishFlight(flight);
        
        // Test current time simulation (before flight departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-04-19 09:00:00", FORMATTER);
        
        Stopover stopover = new Stopover(ap30,
            LocalDateTime.parse("2025-04-20 12:00:00", FORMATTER),
            LocalDateTime.parse("2025-04-20 12:40:00", FORMATTER));
        
        // Execute and Verify
        boolean result = airlineService.addStopover("F601", stopover);
        assertTrue("Stopover should be successfully added", result);
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() {
        // Setup
        Airport ap32 = new Airport("AP32", "Airport AP32");
        ap32.addCity(new City("CityQ"));
        
        Airport ap33 = new Airport("AP33", "Airport AP33");
        ap33.addCity(new City("CityR"));
        
        Airport ap31 = new Airport("AP31", "Airport AP31");
        ap31.addCity(new City("CityS"));
        
        Flight flight = new Flight("F602", ap32, ap33,
            LocalDateTime.parse("2025-05-10 09:00:00", FORMATTER),
            LocalDateTime.parse("2025-05-10 14:00:00", FORMATTER));
        flight.setStatus(FlightStatus.OPEN);
        
        airlineService.publishFlight(flight);
        
        // Test current time simulation (before flight departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-05-09 11:00:00", FORMATTER);
        
        Stopover stopover = new Stopover(ap31,
            LocalDateTime.parse("2025-05-10 16:00:00", FORMATTER),
            LocalDateTime.parse("2025-05-10 17:00:00", FORMATTER));
        
        // Execute and Verify
        boolean result = airlineService.addStopover("F602", stopover);
        assertFalse("Stopover should fail due to time outside flight window", result);
    }
    
    @Test
    public void testCase3_deleteExistingStopover() {
        // Setup
        Airport ap34 = new Airport("AP34", "Airport AP34");
        ap34.addCity(new City("CityT"));
        
        Airport ap35 = new Airport("AP35", "Airport AP35");
        ap35.addCity(new City("CityU"));
        
        Airport ap36 = new Airport("AP36", "Airport AP36");
        ap36.addCity(new City("CityV"));
        
        Flight flight = new Flight("F603", ap34, ap35,
            LocalDateTime.parse("2025-06-15 11:00:00", FORMATTER),
            LocalDateTime.parse("2025-06-15 18:00:00", FORMATTER));
        flight.setStatus(FlightStatus.OPEN);
        
        // Add existing stopover
        Stopover existingStopover = new Stopover(ap34,
            LocalDateTime.parse("2025-06-15 13:00:00", FORMATTER),
            LocalDateTime.parse("2025-06-15 13:45:00", FORMATTER));
        flight.addStopover(existingStopover);
        
        airlineService.publishFlight(flight);
        
        // Test current time simulation (before flight departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-06-14 10:00:00", FORMATTER);
        
        // Execute and Verify
        boolean result = airlineService.deleteStopover("F603", "AP34");
        assertTrue("Stopover should be successfully deleted", result);
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() {
        // Setup
        Airport ap37 = new Airport("AP37", "Airport AP37");
        ap37.addCity(new City("CityW"));
        
        Airport ap38 = new Airport("AP38", "Airport AP38");
        ap38.addCity(new City("CityX"));
        
        Airport ap39 = new Airport("AP39", "Airport AP39");
        ap39.addCity(new City("CityY"));
        
        Flight flight = new Flight("F604", ap37, ap38,
            LocalDateTime.parse("2025-07-20 12:00:00", FORMATTER),
            LocalDateTime.parse("2025-07-20 17:00:00", FORMATTER));
        flight.setStatus(FlightStatus.CLOSED); // Flight is closed
        
        airlineService.publishFlight(flight);
        
        // Test current time simulation (before flight departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-07-10 09:00:00", FORMATTER);
        
        Stopover stopover = new Stopover(ap37,
            LocalDateTime.parse("2025-07-20 13:30:00", FORMATTER),
            LocalDateTime.parse("2025-07-20 14:00:00", FORMATTER));
        
        // Execute and Verify
        boolean result = airlineService.addStopover("F604", stopover);
        assertFalse("Stopover should fail due to closed flight", result);
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() {
        // Setup
        Airport ap42 = new Airport("AP42", "Airport AP42");
        ap42.addCity(new City("CityBB"));
        
        Airport ap43 = new Airport("AP43", "Airport AP43");
        ap43.addCity(new City("CityCC"));
        
        Airport ap44 = new Airport("AP44", "Airport AP44");
        ap44.addCity(new City("CityDD"));
        
        Flight flight = new Flight("F606", ap42, ap43,
            LocalDateTime.parse("2025-12-09 18:00:00", FORMATTER),
            LocalDateTime.parse("2025-12-10 00:00:00", FORMATTER));
        flight.setStatus(FlightStatus.OPEN);
        
        // Add existing stopover
        Stopover existingStopover = new Stopover(ap42,
            LocalDateTime.parse("2025-12-09 20:00:00", FORMATTER),
            LocalDateTime.parse("2025-12-09 20:45:00", FORMATTER));
        flight.addStopover(existingStopover);
        
        airlineService.publishFlight(flight);
        
        // Test current time simulation (after departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-12-09 20:50:00", FORMATTER);
        
        // Execute and Verify
        boolean result = airlineService.deleteStopover("F606", "AP42");
        assertFalse("Stopover deletion should fail after flight departure", result);
    }
}