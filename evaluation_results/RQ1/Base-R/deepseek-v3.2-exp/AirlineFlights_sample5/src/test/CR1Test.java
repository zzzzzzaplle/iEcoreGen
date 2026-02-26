import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        ap01.setName("Airport 01");
        List<String> servedCitiesAP01 = new ArrayList<>();
        servedCitiesAP01.add("CityA");
        ap01.setServedCities(servedCitiesAP01);
        
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        ap02.setName("Airport 02");
        List<String> servedCitiesAP02 = new ArrayList<>();
        servedCitiesAP02.add("CityB");
        ap02.setServedCities(servedCitiesAP02);
        
        Flight flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(ap01);
        flight.setArrivalAirport(ap02);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        flight.setOpenForBooking(true);
        flight.setPublished(false);
        
        airlineSystem.getFlights().add(flight);
        
        // Test
        boolean result = flight.publishFlight();
        
        // Assert
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Setup
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        ap03.setName("Airport 03");
        List<String> servedCitiesAP03 = new ArrayList<>();
        servedCitiesAP03.add("CityC");
        ap03.setServedCities(servedCitiesAP03);
        
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        ap04.setName("Airport 04");
        List<String> servedCitiesAP04 = new ArrayList<>();
        servedCitiesAP04.add("CityD");
        ap04.setServedCities(servedCitiesAP04);
        
        Flight flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(ap03);
        flight.setArrivalAirport(ap04);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        flight.setOpenForBooking(true);
        flight.setPublished(false);
        
        airlineSystem.getFlights().add(flight);
        
        // Test
        boolean result = flight.publishFlight();
        
        // Assert
        assertFalse("Flight should not be published when departure time is after arrival time", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Setup
        Airport ap05 = new Airport();
        ap05.setId("AP05");
        ap05.setName("Airport 05");
        List<String> servedCitiesAP05 = new ArrayList<>();
        servedCitiesAP05.add("CityE");
        ap05.setServedCities(servedCitiesAP05);
        
        Flight flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(ap05);
        flight.setArrivalAirport(ap05);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        flight.setOpenForBooking(true);
        flight.setPublished(false);
        
        airlineSystem.getFlights().add(flight);
        
        // Test
        boolean result = flight.publishFlight();
        
        // Assert
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Setup
        Airport ap06 = new Airport();
        ap06.setId("AP06");
        ap06.setName("Airport 06");
        List<String> servedCitiesAP06 = new ArrayList<>();
        servedCitiesAP06.add("CityF");
        ap06.setServedCities(servedCitiesAP06);
        
        Airport ap07 = new Airport();
        ap07.setId("AP07");
        ap07.setName("Airport 07");
        List<String> servedCitiesAP07 = new ArrayList<>();
        servedCitiesAP07.add("CityG");
        ap07.setServedCities(servedCitiesAP07);
        
        Flight flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(ap06);
        flight.setArrivalAirport(ap07);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        flight.setOpenForBooking(true);
        flight.setPublished(false);
        
        airlineSystem.getFlights().add(flight);
        
        // Test
        boolean result = flight.publishFlight();
        
        // Assert
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Setup
        Airport ap08 = new Airport();
        ap08.setId("AP08");
        ap08.setName("Airport 08");
        List<String> servedCitiesAP08 = new ArrayList<>();
        servedCitiesAP08.add("CityH");
        ap08.setServedCities(servedCitiesAP08);
        
        Airport ap09 = new Airport();
        ap09.setId("AP09");
        ap09.setName("Airport 09");
        List<String> servedCitiesAP09 = new ArrayList<>();
        servedCitiesAP09.add("CityI");
        ap09.setServedCities(servedCitiesAP09);
        
        Flight flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(ap08);
        flight.setArrivalAirport(ap09);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        flight.setOpenForBooking(true);
        flight.setPublished(false);
        
        airlineSystem.getFlights().add(flight);
        
        // First publish attempt - should succeed
        boolean firstResult = flight.publishFlight();
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt - should fail
        boolean secondResult = flight.publishFlight();
        
        // Assert
        assertFalse("Flight should not be published a second time", secondResult);
    }
}