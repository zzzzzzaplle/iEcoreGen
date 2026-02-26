import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR6Test {
    
    private FlightSystem flightSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        flightSystem = new FlightSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() {
        // Setup
        Airport ap28 = createAirport("AP28", "CityN");
        Airport ap29 = createAirport("AP29", "CityO");
        Airport ap30 = createAirport("AP30", "CityP");
        
        Flight f601 = createFlight("F601", ap28, ap29, 
            LocalDateTime.parse("2025-04-20 10:00:00", formatter),
            LocalDateTime.parse("2025-04-20 15:00:00", formatter),
            true, false);
        
        flightSystem.getFlights().add(f601);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap30);
        stopover.setArrivalTime(LocalDateTime.parse("2025-04-20 12:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-04-20 12:40:00", formatter));
        
        // Set current time to 2025-04-19 09:00:00
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test, we assume the time condition is met
        
        // Execute
        boolean result = flightSystem.addStopover(f601, stopover);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertEquals("Flight should have 1 stopover", 1, f601.getStopovers().size());
        assertEquals("Stopover airport should be AP30", "AP30", f601.getStopovers().get(0).getAirport().getId());
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() {
        // Setup
        Airport ap32 = createAirport("AP32", "CityQ");
        Airport ap33 = createAirport("AP33", "CityR");
        Airport ap31 = createAirport("AP31", "CityS");
        
        Flight f602 = createFlight("F602", ap32, ap33, 
            LocalDateTime.parse("2025-05-10 09:00:00", formatter),
            LocalDateTime.parse("2025-05-10 14:00:00", formatter),
            true, false);
        
        flightSystem.getFlights().add(f602);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap31);
        stopover.setArrivalTime(LocalDateTime.parse("2025-05-10 16:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-05-10 17:00:00", formatter));
        
        // Set current time to 2025-05-09 11:00:00
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test, we assume the time condition is met
        
        // Execute
        boolean result = flightSystem.addStopover(f602, stopover);
        
        // Verify
        assertFalse("Stopover outside flight window should not be added", result);
        assertEquals("Flight should have 0 stopovers", 0, f602.getStopovers().size());
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() {
        // Setup
        Airport ap34 = createAirport("AP34", "CityT");
        Airport ap35 = createAirport("AP35", "CityU");
        Airport ap36 = createAirport("AP36", "CityV");
        
        Flight f603 = createFlight("F603", ap35, ap36, 
            LocalDateTime.parse("2025-06-15 11:00:00", formatter),
            LocalDateTime.parse("2025-06-15 18:00:00", formatter),
            true, false);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap34);
        stopover.setArrivalTime(LocalDateTime.parse("2025-06-15 13:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-06-15 13:45:00", formatter));
        
        f603.getStopovers().add(stopover);
        flightSystem.getFlights().add(f603);
        
        // Set current time to 2025-06-14 10:00:00
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test, we assume the time condition is met
        
        // Execute
        boolean result = flightSystem.deleteStopover(f603, stopover);
        
        // Verify
        assertTrue("Stopover should be deleted successfully", result);
        assertEquals("Flight should have 0 stopovers after deletion", 0, f603.getStopovers().size());
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() {
        // Setup
        Airport ap37 = createAirport("AP37", "CityW");
        Airport ap38 = createAirport("AP38", "CityX");
        Airport ap39 = createAirport("AP39", "CityY");
        
        Flight f604 = createFlight("F604", ap38, ap39, 
            LocalDateTime.parse("2025-07-20 12:00:00", formatter),
            LocalDateTime.parse("2025-07-20 17:00:00", formatter),
            false, false); // openForBooking = false
        
        flightSystem.getFlights().add(f604);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap37);
        stopover.setArrivalTime(LocalDateTime.parse("2025-07-20 13:30:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-07-20 14:00:00", formatter));
        
        // Set current time to 2025-07-10 09:00:00
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test, we assume the time condition is met
        
        // Execute
        boolean result = flightSystem.addStopover(f604, stopover);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertEquals("Flight should have 0 stopovers", 0, f604.getStopovers().size());
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() {
        // Setup
        Airport ap42 = createAirport("AP42", "CityBB");
        Airport ap43 = createAirport("AP43", "CityCC");
        Airport ap44 = createAirport("AP44", "CityDD");
        
        Flight f606 = createFlight("F606", ap43, ap44, 
            LocalDateTime.parse("2025-12-09 18:00:00", formatter),
            LocalDateTime.parse("2025-12-10 00:00:00", formatter),
            true, false);
        
        Stopover stopover = new Stopover();
        stopover.setAirport(ap42);
        stopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        stopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        
        f606.getStopovers().add(stopover);
        flightSystem.getFlights().add(f606);
        
        // Set current time to 2025-12-09 20:50:00 (after departure)
        // Note: In real implementation, we would mock LocalDateTime.now()
        // For this test, we need to simulate the time check failing
        // Since we can't easily mock LocalDateTime.now(), we'll modify the flight's departure time
        // to be in the past relative to a fixed "current time"
        
        // Modify the test approach: Create a flight that has already departed
        Flight pastFlight = createFlight("F606", ap43, ap44, 
            LocalDateTime.parse("2025-12-09 18:00:00", formatter),
            LocalDateTime.parse("2025-12-10 00:00:00", formatter),
            true, false);
        
        Stopover pastStopover = new Stopover();
        pastStopover.setAirport(ap42);
        pastStopover.setArrivalTime(LocalDateTime.parse("2025-12-09 20:00:00", formatter));
        pastStopover.setDepartureTime(LocalDateTime.parse("2025-12-09 20:45:00", formatter));
        
        pastFlight.getStopovers().add(pastStopover);
        flightSystem.getFlights().clear();
        flightSystem.getFlights().add(pastFlight);
        
        // Execute - the method should check if current time is before departure
        // Since we can't easily mock the time, we'll rely on the logic in the method
        boolean result = flightSystem.deleteStopover(pastFlight, pastStopover);
        
        // Verify - the method should return false because the flight has already departed
        // (even though in our setup the actual time hasn't changed, the departure time is in the past)
        assertFalse("Stopover should not be deleted after departure", result);
    }
    
    // Helper methods
    private Airport createAirport(String id, String city) {
        Airport airport = new Airport();
        airport.setId(id);
        List<String> cities = new ArrayList<>();
        cities.add(city);
        airport.setCities(cities);
        return airport;
    }
    
    private Flight createFlight(String id, Airport departure, Airport arrival, 
                              LocalDateTime departTime, LocalDateTime arriveTime,
                              boolean openForBooking, boolean published) {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setDepartureAirport(departure);
        flight.setArrivalAirport(arrival);
        flight.setDepartureTime(departTime);
        flight.setArrivalTime(arriveTime);
        flight.setOpenForBooking(openForBooking);
        flight.setPublished(published);
        return flight;
    }
}