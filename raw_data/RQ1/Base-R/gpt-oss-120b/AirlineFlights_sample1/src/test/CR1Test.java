import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CR1Test {
    
    private AirlineReservationSystem.AirlineService service;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        service = new AirlineReservationSystem.AirlineService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() {
        // Setup: Create airports AP01 (serves CityA) and AP02 (serves CityB)
        AirlineReservationSystem.City cityA = new AirlineReservationSystem.City();
        cityA.setId("C1");
        cityA.setName("CityA");
        
        AirlineReservationSystem.City cityB = new AirlineReservationSystem.City();
        cityB.setId("C2");
        cityB.setName("CityB");
        
        AirlineReservationSystem.Airport ap01 = new AirlineReservationSystem.Airport();
        ap01.setId("AP01");
        ap01.setName("AP01 Airport");
        ap01.setCity(cityA);
        
        AirlineReservationSystem.Airport ap02 = new AirlineReservationSystem.Airport();
        ap02.setId("AP02");
        ap02.setName("AP02 Airport");
        ap02.setCity(cityB);
        
        // Create flight F100: departureAirport = AP01, arrivalAirport = AP02
        AirlineReservationSystem.Flight flight = new AirlineReservationSystem.Flight();
        flight.setDepartureAirport(ap01);
        flight.setArrivalAirport(ap02);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        // Test: Publish flight F100
        boolean result = service.publishFlight(flight);
        
        // Expected Output: True
        assertTrue("Flight with correct schedule and route should be published successfully", result);
        assertNotNull("Flight ID should be assigned after successful publication", flight.getId());
        assertEquals("Flight status should be OPEN after publication", 
                     AirlineReservationSystem.FlightStatus.OPEN, flight.getStatus());
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() {
        // Setup: Create airports AP03 (CityC) and AP04 (CityD)
        AirlineReservationSystem.City cityC = new AirlineReservationSystem.City();
        cityC.setId("C3");
        cityC.setName("CityC");
        
        AirlineReservationSystem.City cityD = new AirlineReservationSystem.City();
        cityD.setId("C4");
        cityD.setName("CityD");
        
        AirlineReservationSystem.Airport ap03 = new AirlineReservationSystem.Airport();
        ap03.setId("AP03");
        ap03.setName("AP03 Airport");
        ap03.setCity(cityC);
        
        AirlineReservationSystem.Airport ap04 = new AirlineReservationSystem.Airport();
        ap04.setId("AP04");
        ap04.setName("AP04 Airport");
        ap04.setCity(cityD);
        
        // Create flight F101: AP03  ➜ AP04, departure after arrival
        AirlineReservationSystem.Flight flight = new AirlineReservationSystem.Flight();
        flight.setDepartureAirport(ap03);
        flight.setArrivalAirport(ap04);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        
        // Test: Publish flight F101
        boolean result = service.publishFlight(flight);
        
        // Expected Output: False
        assertFalse("Flight with departure after arrival should not be published", result);
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() {
        // Setup: Create airport AP05 (CityE)
        AirlineReservationSystem.City cityE = new AirlineReservationSystem.City();
        cityE.setId("C5");
        cityE.setName("CityE");
        
        AirlineReservationSystem.Airport ap05 = new AirlineReservationSystem.Airport();
        ap05.setId("AP05");
        ap05.setName("AP05 Airport");
        ap05.setCity(cityE);
        
        // Create flight F102: AP05 ➜ AP05 (same airport)
        AirlineReservationSystem.Flight flight = new AirlineReservationSystem.Flight();
        flight.setDepartureAirport(ap05);
        flight.setArrivalAirport(ap05); // Same airport
        flight.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        // Test: Publish flight F102
        boolean result = service.publishFlight(flight);
        
        // Expected Output: False
        assertFalse("Flight with same departure and arrival airport should not be published", result);
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() {
        // Setup: Create airports AP06 (CityF) and AP07 (CityG)
        AirlineReservationSystem.City cityF = new AirlineReservationSystem.City();
        cityF.setId("C6");
        cityF.setName("CityF");
        
        AirlineReservationSystem.City cityG = new AirlineReservationSystem.City();
        cityG.setId("C7");
        cityG.setName("CityG");
        
        AirlineReservationSystem.Airport ap06 = new AirlineReservationSystem.Airport();
        ap06.setId("AP06");
        ap06.setName("AP06 Airport");
        ap06.setCity(cityF);
        
        AirlineReservationSystem.Airport ap07 = new AirlineReservationSystem.Airport();
        ap07.setId("AP07");
        ap07.setName("AP07 Airport");
        ap07.setCity(cityG);
        
        // Create flight F103: departure in the past relative to current time
        AirlineReservationSystem.Flight flight = new AirlineReservationSystem.Flight();
        flight.setDepartureAirport(ap06);
        flight.setArrivalAirport(ap07);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        
        // Test: Publish flight F103 (current time is after departure time in setup)
        boolean result = service.publishFlight(flight);
        
        // Expected Output: False
        assertFalse("Flight with departure time in the past should not be published", result);
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() {
        // Setup: Create airports AP08 (CityH) and AP09 (CityI)
        AirlineReservationSystem.City cityH = new AirlineReservationSystem.City();
        cityH.setId("C8");
        cityH.setName("CityH");
        
        AirlineReservationSystem.City cityI = new AirlineReservationSystem.City();
        cityI.setId("C9");
        cityI.setName("CityI");
        
        AirlineReservationSystem.Airport ap08 = new AirlineReservationSystem.Airport();
        ap08.setId("AP08");
        ap08.setName("AP08 Airport");
        ap08.setCity(cityH);
        
        AirlineReservationSystem.Airport ap09 = new AirlineReservationSystem.Airport();
        ap09.setId("AP09");
        ap09.setName("AP09 Airport");
        ap09.setCity(cityI);
        
        // Create and publish flight F104 first time
        AirlineReservationSystem.Flight flight = new AirlineReservationSystem.Flight();
        flight.setDepartureAirport(ap08);
        flight.setArrivalAirport(ap09);
        flight.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
        
        boolean firstPublish = service.publishFlight(flight);
        assertTrue("First publication should succeed", firstPublish);
        String flightId = flight.getId();
        assertNotNull("Flight ID should be assigned", flightId);
        
        // Test: Attempt to publish the same flight again
        boolean secondPublish = service.publishFlight(flight);
        
        // Expected Output: False
        assertFalse("Second publish attempt should fail", secondPublish);
    }
}