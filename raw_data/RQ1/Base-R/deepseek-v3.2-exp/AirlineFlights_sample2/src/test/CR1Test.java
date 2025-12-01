import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class CR1Test {
    
    private Airline airline;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Setup
        airline = new Airline();
        airline.setName("AL1");
        
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        Set<String> servedCities1 = new HashSet<>();
        servedCities1.add("CityA");
        ap01.setServedCities(servedCities1);
        
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        Set<String> servedCities2 = new HashSet<>();
        servedCities2.add("CityB");
        ap02.setServedCities(servedCities2);
        
        Flight f100 = new Flight();
        f100.setId("F100");
        f100.setDepartureAirport(ap01);
        f100.setArrivalAirport(ap02);
        f100.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        f100.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        airline.addFlight(f100);
        
        // Set current time to 2024-12-01 09:00:00
        // Note: In real implementation, we would mock the current time
        // For this test, we rely on the flight's publishFlight method logic
        
        // Test
        boolean result = airline.publishFlight(f100);
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Setup
        airline = new Airline();
        airline.setName("AL2");
        
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        Set<String> servedCities3 = new HashSet<>();
        servedCities3.add("CityC");
        ap03.setServedCities(servedCities3);
        
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        Set<String> servedCities4 = new HashSet<>();
        servedCities4.add("CityD");
        ap04.setServedCities(servedCities4);
        
        Flight f101 = new Flight();
        f101.setId("F101");
        f101.setDepartureAirport(ap03);
        f101.setArrivalAirport(ap04);
        f101.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        f101.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter)); // Arrival before departure
        
        airline.addFlight(f101);
        
        // Set current time to 2024-12-15 10:00:00
        // Note: In real implementation, we would mock the current time
        
        // Test
        boolean result = airline.publishFlight(f101);
        
        // Verify
        assertFalse("Flight should not be published when departure is after arrival", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Setup
        airline = new Airline();
        airline.setName("AL3");
        
        Airport ap05 = new Airport();
        ap05.setId("AP05");
        Set<String> servedCities5 = new HashSet<>();
        servedCities5.add("CityE");
        ap05.setServedCities(servedCities5);
        
        Flight f102 = new Flight();
        f102.setId("F102");
        f102.setDepartureAirport(ap05);
        f102.setArrivalAirport(ap05); // Same airport
        f102.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        f102.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        airline.addFlight(f102);
        
        // Set current time to 2025-02-01 09:00:00
        // Note: In real implementation, we would mock the current time
        
        // Test
        boolean result = airline.publishFlight(f102);
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Setup
        airline = new Airline();
        airline.setName("AL4");
        
        Airport ap06 = new Airport();
        ap06.setId("AP06");
        Set<String> servedCities6 = new HashSet<>();
        servedCities6.add("CityF");
        ap06.setServedCities(servedCities6);
        
        Airport ap07 = new Airport();
        ap07.setId("AP07");
        Set<String> servedCities7 = new HashSet<>();
        servedCities7.add("CityG");
        ap07.setServedCities(servedCities7);
        
        Flight f103 = new Flight();
        f103.setId("F103");
        f103.setDepartureAirport(ap06);
        f103.setArrivalAirport(ap07);
        f103.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        f103.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        
        airline.addFlight(f103);
        
        // Note: Current time is set to 2025-04-01 09:00:00 in the test specification
        // Since we cannot mock LocalDateTime.now() in this simple implementation,
        // we rely on the fact that the flight's departure time (2025-03-30) is before current time (2025-04-01)
        // The publishFlight method will correctly detect this as an invalid condition
        
        // Test
        boolean result = airline.publishFlight(f103);
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Setup
        airline = new Airline();
        airline.setName("AL5");
        
        Airport ap08 = new Airport();
        ap08.setId("AP08");
        Set<String> servedCities8 = new HashSet<>();
        servedCities8.add("CityH");
        ap08.setServedCities(servedCities8);
        
        Airport ap09 = new Airport();
        ap09.setId("AP09");
        Set<String> servedCities9 = new HashSet<>();
        servedCities9.add("CityI");
        ap09.setServedCities(servedCities9);
        
        Flight f104 = new Flight();
        f104.setId("F104");
        f104.setDepartureAirport(ap08);
        f104.setArrivalAirport(ap09);
        f104.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        f104.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        
        airline.addFlight(f104);
        
        // First publish attempt (should succeed)
        boolean firstAttempt = airline.publishFlight(f104);
        assertTrue("First publish attempt should succeed", firstAttempt);
        
        // Second publish attempt
        boolean secondAttempt = airline.publishFlight(f104);
        
        // Verify
        assertFalse("Second publish attempt should fail", secondAttempt);
    }
}