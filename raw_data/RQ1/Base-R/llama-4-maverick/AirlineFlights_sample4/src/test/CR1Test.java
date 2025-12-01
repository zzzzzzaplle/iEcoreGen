import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Setup
        // Create airports AP01 (serves CityA) and AP02 (serves CityB)
        departureAirport = new Airport();
        departureAirport.setId("AP01");
        List<String> citiesA = new ArrayList<>();
        citiesA.add("CityA");
        departureAirport.setCities(citiesA);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP02");
        List<String> citiesB = new ArrayList<>();
        citiesB.add("CityB");
        arrivalAirport.setCities(citiesB);
        
        // Create flight F100
        flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        // Test
        boolean result = flight.publish();
        
        // Assert
        assertTrue("Flight should publish successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Setup
        // Create airports AP03 (CityC) and AP04 (CityD)
        departureAirport = new Airport();
        departureAirport.setId("AP03");
        List<String> citiesC = new ArrayList<>();
        citiesC.add("CityC");
        departureAirport.setCities(citiesC);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP04");
        List<String> citiesD = new ArrayList<>();
        citiesD.add("CityD");
        arrivalAirport.setCities(citiesD);
        
        // Create flight F101
        flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        
        // Test
        boolean result = flight.publish();
        
        // Assert
        assertFalse("Flight should not publish when departure time is after arrival time", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Setup
        // Create airport AP05 (CityE)
        Airport airport = new Airport();
        airport.setId("AP05");
        List<String> citiesE = new ArrayList<>();
        citiesE.add("CityE");
        airport.setCities(citiesE);
        
        // Create flight F102
        flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(airport);
        flight.setArrivalAirport(airport);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        // Test
        boolean result = flight.publish();
        
        // Assert
        assertFalse("Flight should not publish when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Setup
        // Create airports AP06 (CityF) and AP07 (CityG)
        departureAirport = new Airport();
        departureAirport.setId("AP06");
        List<String> citiesF = new ArrayList<>();
        citiesF.add("CityF");
        departureAirport.setCities(citiesF);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP07");
        List<String> citiesG = new ArrayList<>();
        citiesG.add("CityG");
        arrivalAirport.setCities(citiesG);
        
        // Create flight F103
        flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        
        // Note: Current system time is set to 2025-04-01 09:00:00 in specification
        // Since we cannot mock LocalDateTime.now() in this implementation,
        // we rely on the flight's internal temporal consistency check
        // which compares departureTime with current system time
        
        // Test
        boolean result = flight.publish();
        
        // Assert
        // This test may fail depending on when it's run due to real current time
        // In a real implementation, we would mock LocalDateTime.now()
        // For this test specification, we expect false when current time is after departure
        if (LocalDateTime.now().isAfter(LocalDateTime.parse("2025-03-30 10:00:00", formatter))) {
            assertFalse("Flight should not publish when departure time is in the past", result);
        }
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Setup
        // Create airports AP08 (CityH) and AP09 (CityI)
        departureAirport = new Airport();
        departureAirport.setId("AP08");
        List<String> citiesH = new ArrayList<>();
        citiesH.add("CityH");
        departureAirport.setCities(citiesH);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP09");
        List<String> citiesI = new ArrayList<>();
        citiesI.add("CityI");
        arrivalAirport.setCities(citiesI);
        
        // Create flight F104
        flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        
        // First publish attempt (should succeed)
        boolean firstAttempt = flight.publish();
        assertTrue("First publish attempt should succeed", firstAttempt);
        
        // Second publish attempt
        boolean secondAttempt = flight.publish();
        
        // Assert
        assertFalse("Second publish attempt should fail", secondAttempt);
    }
}