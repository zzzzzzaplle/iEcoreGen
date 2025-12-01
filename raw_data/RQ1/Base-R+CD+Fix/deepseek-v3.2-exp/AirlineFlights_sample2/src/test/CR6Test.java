import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR6Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup
        Airline AL26 = new Airline();
        
        Airport AP28 = createAirport("AP28", "CityN");
        Airport AP29 = createAirport("AP29", "CityO");
        Airport AP30 = createAirport("AP30", "CityP");
        
        Flight F601 = createFlight("F601", AP28, AP29, "2025-04-20 10:00:00", "2025-04-20 15:00:00", true);
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(AP30);
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        // Execute
        boolean result = F601.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully within journey window", result);
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airline AL27 = new Airline();
        
        Airport AP32 = createAirport("AP32", "CityQ");
        Airport AP33 = createAirport("AP33", "CityR");
        Airport AP31 = createAirport("AP31", "CityS");
        
        Flight F602 = createFlight("F602", AP32, AP33, "2025-05-10 09:00:00", "2025-05-10 14:00:00", true);
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Create stopover with times outside flight window
        Stopover stopover = new Stopover();
        stopover.setAirport(AP31);
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        // Execute
        boolean result = F602.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added when times are outside flight window", result);
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws Exception {
        // Setup
        Airline AL28 = new Airline();
        
        Airport AP34 = createAirport("AP34", "CityT");
        Airport AP35 = createAirport("AP35", "CityU");
        Airport AP36 = createAirport("AP36", "CityV");
        
        Flight F603 = createFlight("F603", AP35, AP36, "2025-06-15 11:00:00", "2025-06-15 18:00:00", true);
        
        // Add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(AP34);
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        F603.addStopover(existingStopover, currentTime);
        
        // Execute
        boolean result = F603.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertTrue("Existing stopover should be successfully removed", result);
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws Exception {
        // Setup
        Airline AL29 = new Airline();
        
        Airport AP37 = createAirport("AP37", "CityW");
        Airport AP38 = createAirport("AP38", "CityX");
        Airport AP39 = createAirport("AP39", "CityY");
        
        // Create flight that is not open for booking
        Flight F604 = createFlight("F604", AP38, AP39, "2025-07-20 12:00:00", "2025-07-20 17:00:00", false);
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Create stopover
        Stopover stopover = new Stopover();
        stopover.setAirport(AP37);
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        // Execute
        boolean result = F604.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws Exception {
        // Setup
        Airline AL31 = new Airline();
        
        Airport AP42 = createAirport("AP42", "CityBB");
        Airport AP43 = createAirport("AP43", "CityCC");
        Airport AP44 = createAirport("AP44", "CityDD");
        
        Flight F606 = createFlight("F606", AP43, AP44, "2025-12-09 18:00:00", "2025-12-10 00:00:00", true);
        
        // Add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(AP42);
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        
        Date setupTime = dateFormat.parse("2025-12-08 10:00:00");
        F606.addStopover(existingStopover, setupTime);
        
        // Current time is after departure and after stopover
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Execute
        boolean result = F606.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after flight departure", result);
    }
    
    // Helper method to create an airport with a city
    private Airport createAirport(String id, String cityName) {
        Airport airport = new Airport();
        airport.setId(id);
        City city = new City();
        airport.addCity(city);
        return airport;
    }
    
    // Helper method to create a flight
    private Flight createFlight(String id, Airport departure, Airport arrival, 
                              String departureTimeStr, String arrivalTimeStr, boolean openForBooking) throws Exception {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setDepartureAirport(departure);
        flight.setArrivalAirport(arrival);
        flight.setDepartureTime(dateFormat.parse(departureTimeStr));
        flight.setArrivalTime(dateFormat.parse(arrivalTimeStr));
        flight.setOpenForBooking(openForBooking);
        return flight;
    }
}