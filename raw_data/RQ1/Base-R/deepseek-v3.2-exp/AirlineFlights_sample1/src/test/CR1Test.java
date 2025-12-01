import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class CR1Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Create airline AL1
        // Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport airportAP01 = new Airport();
        airportAP01.setId("AP01");
        Set<String> servedCitiesAP01 = new HashSet<>();
        servedCitiesAP01.add("CityA");
        airportAP01.setServedCities(servedCitiesAP01);
        
        Airport airportAP02 = new Airport();
        airportAP02.setId("AP02");
        Set<String> servedCitiesAP02 = new HashSet<>();
        servedCitiesAP02.add("CityB");
        airportAP02.setServedCities(servedCitiesAP02);
        
        // Create flight F100: departureAirport = AP01, arrivalAirport = AP02, 
        // departureTime = 2025-01-10 10:00:00, arrivalTime = 2025-01-10 14:00:00
        Flight flightF100 = new Flight();
        flightF100.setId("F100");
        flightF100.setDepartureAirport(airportAP01);
        flightF100.setArrivalAirport(airportAP02);
        flightF100.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flightF100.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        // Current system time = 2024-12-01 09:00:00
        // (Note: In actual implementation, you might need to mock current time)
        
        // Publish flight F100 for airline AL1
        boolean result = flightF100.publishFlight();
        
        // Expected Output: True
        assertTrue("Flight should be successfully published with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Create airline AL2
        // Create airports AP03 (CityC) and AP04 (CityD)
        Airport airportAP03 = new Airport();
        airportAP03.setId("AP03");
        Set<String> servedCitiesAP03 = new HashSet<>();
        servedCitiesAP03.add("CityC");
        airportAP03.setServedCities(servedCitiesAP03);
        
        Airport airportAP04 = new Airport();
        airportAP04.setId("AP04");
        Set<String> servedCitiesAP04 = new HashSet<>();
        servedCitiesAP04.add("CityD");
        airportAP04.setServedCities(servedCitiesAP04);
        
        // Create flight F101: AP03 ➜ AP04, departure 2025-02-05 20:00:00,
        // arrival 2025-02-05 18:00:00 (departure after arrival)
        Flight flightF101 = new Flight();
        flightF101.setId("F101");
        flightF101.setDepartureAirport(airportAP03);
        flightF101.setArrivalAirport(airportAP04);
        flightF101.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flightF101.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        
        // Current time = 2024-12-15 10:00:00
        // (Note: In actual implementation, you might need to mock current time)
        
        // Publish flight F101 for airline AL2
        boolean result = flightF101.publishFlight();
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure is after arrival", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Create airline AL3
        // Create airport AP05 (CityE)
        Airport airportAP05 = new Airport();
        airportAP05.setId("AP05");
        Set<String> servedCitiesAP05 = new HashSet<>();
        servedCitiesAP05.add("CityE");
        airportAP05.setServedCities(servedCitiesAP05);
        
        // Create flight F102: AP05 ➜ AP05 (same airport), 
        // departure 2025-03-01 08:00:00, arrival 2025-03-01 12:00:00
        Flight flightF102 = new Flight();
        flightF102.setId("F102");
        flightF102.setDepartureAirport(airportAP05);
        flightF102.setArrivalAirport(airportAP05); // Same airport
        flightF102.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flightF102.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        // Current time = 2025-02-01 09:00:00
        // (Note: In actual implementation, you might need to mock current time)
        
        // Publish flight F102 for airline AL3
        boolean result = flightF102.publishFlight();
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Create airline AL4
        // Create airports AP06 (CityF) and AP07 (CityG)
        Airport airportAP06 = new Airport();
        airportAP06.setId("AP06");
        Set<String> servedCitiesAP06 = new HashSet<>();
        servedCitiesAP06.add("CityF");
        airportAP06.setServedCities(servedCitiesAP06);
        
        Airport airportAP07 = new Airport();
        airportAP07.setId("AP07");
        Set<String> servedCitiesAP07 = new HashSet<>();
        servedCitiesAP07.add("CityG");
        airportAP07.setServedCities(servedCitiesAP07);
        
        // Create flight F103: departure 2025-03-30 10:00:00, arrival 2025-03-30 12:00:00
        Flight flightF103 = new Flight();
        flightF103.setId("F103");
        flightF103.setDepartureAirport(airportAP06);
        flightF103.setArrivalAirport(airportAP07);
        flightF103.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flightF103.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        
        // Current time = 2025-04-01 09:00:00 (after departure time)
        // (Note: In actual implementation, you might need to mock current time)
        
        // Publish flight F103 for airline AL4
        boolean result = flightF103.publishFlight();
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Create airline AL5
        // Create airports AP08 (CityH) and AP09 (CityI)
        Airport airportAP08 = new Airport();
        airportAP08.setId("AP08");
        Set<String> servedCitiesAP08 = new HashSet<>();
        servedCitiesAP08.add("CityH");
        airportAP08.setServedCities(servedCitiesAP08);
        
        Airport airportAP09 = new Airport();
        airportAP09.setId("AP09");
        Set<String> servedCitiesAP09 = new HashSet<>();
        servedCitiesAP09.add("CityI");
        airportAP09.setServedCities(servedCitiesAP09);
        
        // Create flight F104 already published successfully with
        // departure 2025-05-05 07:00:00, arrival 2025-05-05 10:00:00
        Flight flightF104 = new Flight();
        flightF104.setId("F104");
        flightF104.setDepartureAirport(airportAP08);
        flightF104.setArrivalAirport(airportAP09);
        flightF104.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flightF104.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        
        // First publish attempt - should succeed
        boolean firstResult = flightF104.publishFlight();
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Current time = 2025-04-01 10:00:00
        // (Note: In actual implementation, you might need to mock current time)
        
        // Second publish attempt
        boolean secondResult = flightF104.publishFlight();
        
        // Expected Output: False
        assertFalse("Second publish attempt should fail", secondResult);
    }
}