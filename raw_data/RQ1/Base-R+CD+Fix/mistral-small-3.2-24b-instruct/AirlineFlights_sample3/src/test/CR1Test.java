import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP01");
        departureAirport.addCity(new City("CityA"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP02");
        arrivalAirport.addCity(new City("CityB"));
        
        Flight flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
        assertTrue("Flight should be open for booking after successful publication", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP03");
        departureAirport.addCity(new City("CityC"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP04");
        arrivalAirport.addCity(new City("CityD"));
        
        Flight flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00"));
        
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is after arrival time", result);
        assertFalse("Flight should remain closed for booking after failed publication", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport airport = new Airport();
        airport.setId("AP05");
        airport.addCity(new City("CityE"));
        
        Flight flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(airport);
        flight.setArrialAirport(airport);
        flight.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
        assertFalse("Flight should remain closed for booking after failed publication", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP06");
        departureAirport.addCity(new City("CityF"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP07");
        arrivalAirport.addCity(new City("CityG"));
        
        Flight flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        // Execute
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past", result);
        assertFalse("Flight should remain closed for booking after failed publication", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP08");
        departureAirport.addCity(new City("CityH"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP09");
        arrivalAirport.addCity(new City("CityI"));
        
        Flight flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt (should succeed)
        boolean firstResult = airline.publishFlight(flight, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        assertTrue("Flight should be open for booking after first publication", flight.isOpenForBooking());
        
        // Execute second publish attempt
        boolean secondResult = airline.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Second publish attempt should fail", secondResult);
        assertTrue("Flight should remain open for booking after second publish attempt", flight.isOpenForBooking());
    }
}