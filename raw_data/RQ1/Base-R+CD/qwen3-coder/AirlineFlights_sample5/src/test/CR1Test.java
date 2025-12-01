import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR1Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_correctScheduleAndRoute() throws ParseException {
        // Create airline AL1
        Airline airline = new Airline();
        
        // Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        ap01.addCity(new City());
        
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        ap02.addCity(new City());
        
        // Create flight F100: departureAirport = AP01, arrivalAirport = AP02,
        // departureTime = 2025-01-10 10:00:00, arrivalTime = 2025-01-10 14:00:00
        Flight flight = new Flight();
        flight.setDepartureAirport(ap01);
        flight.setArrivalAirport(ap02);
        flight.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        flight.setOpenForBooking(true);
        
        // Current system time = 2024-12-01 09:00:00
        Date now = dateFormat.parse("2024-12-01 09:00:00");
        
        // Publish flight F100 for airline AL1
        boolean result = airline.publishFlight(flight, now);
        
        // Expected Output: True
        assertTrue(result);
    }
    
    @Test
    public void testCase2_departureAfterArrival() throws ParseException {
        // Create airline AL2
        Airline airline = new Airline();
        
        // Airports AP03 (CityC) and AP04 (CityD)
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        ap03.addCity(new City());
        
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        ap04.addCity(new City());
        
        // Flight F101: AP03 ➜ AP04, departure 2025-02-05 20:00:00,
        // arrival 2025-02-05 18:00:00
        Flight flight = new Flight();
        flight.setDepartureAirport(ap03);
        flight.setArrivalAirport(ap04);
        flight.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00"));
        flight.setOpenForBooking(true);
        
        // Current time = 2024-12-15 10:00:00
        Date now = dateFormat.parse("2024-12-15 10:00:00");
        
        // Publish flight F101 for airline AL2
        boolean result = airline.publishFlight(flight, now);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase3_sameDepartureAndArrivalAirport() throws ParseException {
        // Create airline AL3
        Airline airline = new Airline();
        
        // Airport AP05 (CityE)
        Airport ap05 = new Airport();
        ap05.setId("AP05");
        ap05.addCity(new City());
        
        // Flight F102: AP05 ➜ AP05, departure 2025-03-01 08:00:00,
        // arrival 2025-03-01 12:00:00
        Flight flight = new Flight();
        flight.setDepartureAirport(ap05);
        flight.setArrivalAirport(ap05);
        flight.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        flight.setOpenForBooking(true);
        
        // Current time = 2025-02-01 09:00:00
        Date now = dateFormat.parse("2025-02-01 09:00:00");
        
        // Publish flight F102 for airline AL3
        boolean result = airline.publishFlight(flight, now);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_departureTimeInThePast() throws ParseException {
        // Create airline AL4
        Airline airline = new Airline();
        
        // Airports AP06 (CityF) and AP07 (CityG)
        Airport ap06 = new Airport();
        ap06.setId("AP06");
        ap06.addCity(new City());
        
        Airport ap07 = new Airport();
        ap07.setId("AP07");
        ap07.addCity(new City());
        
        // Flight F103: departure 2025-03-30 10:00:00, arrival 2025-03-30 12:00:00
        Flight flight = new Flight();
        flight.setDepartureAirport(ap06);
        flight.setArrivalAirport(ap07);
        flight.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        flight.setOpenForBooking(true);
        
        // Current time = 2025-04-01 09:00:00
        Date now = dateFormat.parse("2025-04-01 09:00:00");
        
        // Publish flight F103 for airline AL4
        boolean result = airline.publishFlight(flight, now);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase5_secondPublishAttempt() throws ParseException {
        // Create airline AL5
        Airline airline = new Airline();
        
        // Airports AP08 (CityH) and AP09 (CityI)
        Airport ap08 = new Airport();
        ap08.setId("AP08");
        ap08.addCity(new City());
        
        Airport ap09 = new Airport();
        ap09.setId("AP09");
        ap09.addCity(new City());
        
        // Flight F104 already published successfully with
        // departure 2025-05-05 07:00:00, arrival 2025-05-05 10:00:00
        Flight flight = new Flight();
        flight.setDepartureAirport(ap08);
        flight.setArrivalAirport(ap09);
        flight.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        // First publish (to simulate already published)
        Date firstPublishTime = dateFormat.parse("2025-04-01 09:00:00");
        airline.publishFlight(flight, firstPublishTime);
        
        // Current time = 2025-04-01 10:00:00
        Date now = dateFormat.parse("2025-04-01 10:00:00");
        
        // Try to publish flight F104 again for airline AL5
        boolean result = airline.publishFlight(flight, now);
        
        // Expected Output: False
        assertFalse(result);
    }
}