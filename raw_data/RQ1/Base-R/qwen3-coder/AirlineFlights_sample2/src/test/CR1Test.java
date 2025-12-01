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
    public void testCase1_correctScheduleAndRoute() {
        // Setup
        AirlineSystem al1 = new AirlineSystem();
        
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        List<String> citiesA = new ArrayList<>();
        citiesA.add("CityA");
        ap01.setCities(citiesA);
        
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        List<String> citiesB = new ArrayList<>();
        citiesB.add("CityB");
        ap02.setCities(citiesB);
        
        Flight f100 = new Flight();
        f100.setId("F100");
        f100.setDepartureAirport(ap01);
        f100.setArrivalAirport(ap02);
        f100.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        f100.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        f100.setOpenForBooking(true);
        
        // Execute
        boolean result = al1.publishFlight(f100);
        
        // Verify
        assertTrue("Flight should be published successfully with correct schedule and route", result);
    }
    
    @Test
    public void testCase2_departureAfterArrival() {
        // Setup
        AirlineSystem al2 = new AirlineSystem();
        
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        List<String> citiesC = new ArrayList<>();
        citiesC.add("CityC");
        ap03.setCities(citiesC);
        
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        List<String> citiesD = new ArrayList<>();
        citiesD.add("CityD");
        ap04.setCities(citiesD);
        
        Flight f101 = new Flight();
        f101.setId("F101");
        f101.setDepartureAirport(ap03);
        f101.setArrivalAirport(ap04);
        f101.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        f101.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        f101.setOpenForBooking(true);
        
        // Execute
        boolean result = al2.publishFlight(f101);
        
        // Verify
        assertFalse("Flight should not be published when departure time is after arrival time", result);
    }
    
    @Test
    public void testCase3_sameDepartureAndArrivalAirport() {
        // Setup
        AirlineSystem al3 = new AirlineSystem();
        
        Airport ap05 = new Airport();
        ap05.setId("AP05");
        List<String> citiesE = new ArrayList<>();
        citiesE.add("CityE");
        ap05.setCities(citiesE);
        
        Flight f102 = new Flight();
        f102.setId("F102");
        f102.setDepartureAirport(ap05);
        f102.setArrivalAirport(ap05);
        f102.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        f102.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        f102.setOpenForBooking(true);
        
        // Execute
        boolean result = al3.publishFlight(f102);
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
    }
    
    @Test
    public void testCase4_departureTimeInThePast() {
        // Setup
        AirlineSystem al4 = new AirlineSystem();
        
        Airport ap06 = new Airport();
        ap06.setId("AP06");
        List<String> citiesF = new ArrayList<>();
        citiesF.add("CityF");
        ap06.setCities(citiesF);
        
        Airport ap07 = new Airport();
        ap07.setId("AP07");
        List<String> citiesG = new ArrayList<>();
        citiesG.add("CityG");
        ap07.setCities(citiesG);
        
        Flight f103 = new Flight();
        f103.setId("F103");
        f103.setDepartureAirport(ap06);
        f103.setArrivalAirport(ap07);
        f103.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        f103.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        f103.setOpenForBooking(true);
        
        // Execute
        boolean result = al4.publishFlight(f103);
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past relative to current system time", result);
    }
    
    @Test
    public void testCase5_secondPublishAttempt() {
        // Setup
        AirlineSystem al5 = new AirlineSystem();
        
        Airport ap08 = new Airport();
        ap08.setId("AP08");
        List<String> citiesH = new ArrayList<>();
        citiesH.add("CityH");
        ap08.setCities(citiesH);
        
        Airport ap09 = new Airport();
        ap09.setId("AP09");
        List<String> citiesI = new ArrayList<>();
        citiesI.add("CityI");
        ap09.setCities(citiesI);
        
        Flight f104 = new Flight();
        f104.setId("F104");
        f104.setDepartureAirport(ap08);
        f104.setArrivalAirport(ap09);
        f104.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        f104.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        f104.setOpenForBooking(true);
        
        // First publish attempt (should succeed)
        boolean firstResult = al5.publishFlight(f104);
        assertTrue("First publish attempt should succeed", firstResult);
        
        // Second publish attempt
        boolean secondResult = al5.publishFlight(f104);
        
        // Verify
        assertFalse("Second publish attempt should fail as flight is already published", secondResult);
    }
}