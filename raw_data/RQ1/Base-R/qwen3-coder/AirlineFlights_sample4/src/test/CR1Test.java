import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class CR1Test {
    
    private FlightSystem flightSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        flightSystem = new FlightSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_correctScheduleAndRoute() {
        // Setup
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        ap01.setCities(Arrays.asList("CityA"));
        
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        ap02.setCities(Arrays.asList("CityB"));
        
        Flight flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(ap01);
        flight.setArrivalAirport(ap02);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        flight.setOpenForBooking(true);
        
        // Add flight to system
        flightSystem.getFlights().add(flight);
        
        // Execute and verify
        boolean result = flightSystem.publishFlight(flight);
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_departureAfterArrival() {
        // Setup
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        ap03.setCities(Arrays.asList("CityC"));
        
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        ap04.setCities(Arrays.asList("CityD"));
        
        Flight flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(ap03);
        flight.setArrivalAirport(ap04);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        flight.setOpenForBooking(true);
        
        // Add flight to system
        flightSystem.getFlights().add(flight);
        
        // Execute and verify
        boolean result = flightSystem.publishFlight(flight);
        assertFalse("Flight should not be published when departure is after arrival", result);
    }
    
    @Test
    public void testCase3_sameDepartureAndArrivalAirport() {