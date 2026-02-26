import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CR1Test {
    
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP01");
        Set<String> servedCities1 = new HashSet<>();
        servedCities1.add("CityA");
        departureAirport.setServedCities(servedCities1);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP02");
        Set<String> servedCities2 = new HashSet<>();
        servedCities2.add("CityB");
        arrivalAirport.setServedCities(servedCities2);
        
        Flight flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        // Execute and Verify
        boolean result = airlineSystem.publishFlight(flight);
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP03");
        Set<String> servedCities1 = new HashSet<>();
        servedCities1.add("CityC");
        departureAirport.setServedCities(servedCities1);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP04");
        Set<String> servedCities2 = new HashSet<>();
        servedCities2.add("CityD");
        arrivalAirport.setServedCities(servedCities2);
        
        Flight flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        
        // Execute and Verify
        boolean result = airlineSystem.publishFlight(flight);
        assertFalse("Flight should not be published when departure time is after arrival time", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Setup
        Airport airport = new Airport();
        airport.setId("AP05");
        Set<String> servedCities = new HashSet<>();
        servedCities.add("CityE");
        airport.setServedCities(servedCities);
        
        Flight flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(airport);
        flight.setArrivalAirport(airport); // Same airport
        flight.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        // Execute and Verify
        boolean result = airlineSystem.publishFlight(flight);
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP06");
        Set<String> servedCities1 = new HashSet<>();
        servedCities1.add("CityF");
        departureAirport.setServedCities(servedCities1);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP07");
        Set<String> servedCities2 = new HashSet<>();
        servedCities2.add("CityG");
        arrivalAirport.setServedCities(servedCities2);
        
        Flight flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        
        // Note: This test relies on the current system time being after 2025-03-30
        // In a real implementation, we would mock the time, but for this test we rely on the actual logic
        
        // Execute and Verify
        boolean result = airlineSystem.publishFlight(flight);
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP08");
        Set<String> servedCities1 = new HashSet<>();
        servedCities1.add("CityH");
        departureAirport.setServedCities(servedCities1);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP09");
        Set<String> servedCities2 = new HashSet<>();
        servedCities2.add("CityI");
        arrivalAirport.setServedCities(servedCities2);
        
        Flight flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        
        // First publish attempt (should succeed)
        boolean firstResult = airlineSystem.publishFlight(flight);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt (should fail)
        boolean secondResult = airlineSystem.publishFlight(flight);
        assertFalse("Second publish attempt should fail", secondResult);
    }
}