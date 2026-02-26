import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR6Test {
    
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_addFirstStopoverInsideJourneyWindow() {
        // Setup
        Airport ap28 = createAirport("AP28", "CityN");
        Airport ap29 = createAirport("AP29", "CityO");
        Airport ap30 = createAirport("AP30", "CityP");
        
        Flight flight = createFlight("F601", ap28, ap29, 
                                   LocalDateTime.parse("2025-04-20 10:00:00", formatter),
                                   LocalDateTime.parse("2025-04-20 15:00:00", formatter),
                                   true, 0);
        
        airlineSystem.getFlights().add(flight);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Execute
        boolean result = airlineSystem.addStopoverToFlight("F601", stopover);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, flight.getStopovers().size());
        assertEquals("Stopover airport should be AP30", "AP30", flight.getStopovers().get(0).getAirport().getId());
    }
    
    @Test
    public void testCase2_stopoverTimeOutsideWindow() {
        // Setup
        Airport ap32 = createAirport("AP32", "CityQ");
        Airport ap33 = createAirport("AP33", "CityR");
        Airport ap31 = createAirport("AP31", "CityS");
        
        Flight flight = createFlight("F602", ap32, ap33, 
                                   LocalDateTime.parse("2025-05-10 09:00:00", formatter),
                                   LocalDateTime.parse("2025-05-10 14:00:00", formatter),
                                   true, 0);
        
        airlineSystem.getFlights().add(flight);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Execute
        boolean result = airlineSystem.addStopoverToFlight("F602", stopover);
        
        // Verify
        assertFalse("Stopover outside journey window should not be added", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase3_deleteExistingStopover() {
        // Setup
        Airport ap34 = createAirport("AP34", "CityT");
        Airport ap35 = createAirport("AP35", "CityU");
        Airport ap36 = createAirport("AP36", "CityV");
        
        Flight flight = createFlight("F603", ap34, ap36, 
                                   LocalDateTime.parse("2025-06-15 11:00:00", formatter),
                                   LocalDateTime.parse("2025-06-15 18:00:00", formatter),
                                   true, 0);
        
        // Add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap35);
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        flight.getStopovers().add(existingStopover);
        flight.setStopovers(flight.getStopovers()); // Update the list
        
        airlineSystem.getFlights().add(flight);
        
        // Execute
        boolean result = airlineSystem.deleteStopoverFromFlight("F603", existingStopover);
        
        // Verify
        assertTrue("Existing stopover should be deleted successfully", result);
        assertEquals("Flight should have 0 stopovers after deletion", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase4_flightClosedPreventsModification() {
        // Setup
        Airport ap37 = createAirport("AP37", "CityW");
        Airport ap38 = createAirport("AP38", "CityX");
        Airport ap39 = createAirport("AP39", "CityY");
        
        Flight flight = createFlight("F604", ap37, ap39, 
                                   LocalDateTime.parse("2025-07-20 12:00:00", formatter),
                                   LocalDateTime.parse("2025-07-20 17:00:00", formatter),
                                   false, 0); // closed for booking
        
        airlineSystem.getFlights().add(flight);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap38);
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Execute
        boolean result = airlineSystem.addStopoverToFlight("F604", stopover);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have 0 stopovers", 0, flight.getStopovers().size());
    }
    
    @Test
    public void testCase5_attemptRemovalAfterDeparture() {
        // Setup
        Airport ap42 = createAirport("AP42", "CityBB");
        Airport ap43 = createAirport("AP43", "CityCC");
        Airport ap44 = createAirport("AP44", "CityDD");
        
        Flight flight = createFlight("F606", ap42, ap44, 
                                   LocalDateTime.parse("2025-12-09 18:00:00", formatter),
                                   LocalDateTime.parse("2025-12-10 00:00:00", formatter),
                                   true, 0);
        
        // Add existing stopover
        Stopover existingStopover = new Stopover();
        existingStopover.setAirport(ap43);
        existingStopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        existingStopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        flight.getStopovers().add(existingStopover);
        flight.setStopovers(flight.getStopovers()); // Update the list
        
        airlineSystem.getFlights().add(flight);
        
        // Execute - simulate current time after departure
        boolean result = airlineSystem.deleteStopoverFromFlight("F606", existingStopover);
        
        // Verify
        assertFalse("Stopover should not be deleted after flight departure", result);
        assertEquals("Flight should still have 1 stopover", 1, flight.getStopovers().size());
    }
    
    // Helper methods for test setup
    private Airport createAirport(String id, String city) {
        Airport airport = new Airport();
        airport.setId(id);
        airport.setName("Airport " + id);
        List<String> servedCities = new ArrayList<>();
        servedCities.add(city);
        airport.setServedCities(servedCities);
        return airport;
    }
    
    private Flight createFlight(String id, Airport departure, Airport arrival, 
                               LocalDateTime departureTime, LocalDateTime arrivalTime, 
                               boolean openForBooking, int stopoverCount) {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setDepartureAirport(departure);
        flight.setArrivalAirport(arrival);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        flight.setOpenForBooking(openForBooking);
        flight.setPublished(true); // Flights need to be published for booking
        
        // Initialize stopovers list
        for (int i = 0; i < stopoverCount; i++) {
            flight.getStopovers().add(new Stopover());
        }
        flight.setStopovers(flight.getStopovers());
        
        return flight;
    }
}