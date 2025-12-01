import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() throws Exception {
        // Setup
        Airport ap01 = new Airport();
        ap01.setId("AP01");
        City cityA = new City("CityA");
        ap01.addCity(cityA);
        
        Airport ap02 = new Airport();
        ap02.setId("AP02");
        City cityB = new City("CityB");
        ap02.addCity(cityB);
        
        Flight flight = new Flight();
        flight.setId("F100");
        flight.setDepartureAirport(ap01);
        flight.setArrialAirport(ap02);
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
        Airline airlineAL2 = new Airline();
        
        Airport ap03 = new Airport();
        ap03.setId("AP03");
        City cityC = new City("CityC");
        ap03.addCity(cityC);
        
        Airport ap04 = new Airport();
        ap04.setId("AP04");
        City cityD = new City("CityD");
        ap04.addCity(cityD);
        
        Flight flight = new Flight();
        flight.setId("F101");
        flight.setDepartureAirport(ap03);
        flight.setArrialAirport(ap04);
        flight.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00"));
        
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Execute
        boolean result = airlineAL2.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is after arrival time", result);
        assertFalse("Flight should remain closed for booking after failed publication", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws Exception {
        // Setup
        Airline airlineAL3 = new Airline();
        
        Airport ap05 = new Airport();
        ap05.setId("AP05");
        City cityE = new City("CityE");
        ap05.addCity(cityE);
        
        Flight flight = new Flight();
        flight.setId("F102");
        flight.setDepartureAirport(ap05);
        flight.setArrialAirport(ap05); // Same airport
        flight.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Execute
        boolean result = airlineAL3.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
        assertFalse("Flight should remain closed for booking after failed publication", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws Exception {
        // Setup
        Airline airlineAL4 = new Airline();
        
        Airport ap06 = new Airport();
        ap06.setId("AP06");
        City cityF = new City("CityF");
        ap06.addCity(cityF);
        
        Airport ap07 = new Airport();
        ap07.setId("AP07");
        City cityG = new City("CityG");
        ap07.addCity(cityG);
        
        Flight flight = new Flight();
        flight.setId("F103");
        flight.setDepartureAirport(ap06);
        flight.setArrialAirport(ap07);
        flight.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00"); // Current time after departure
        
        // Execute
        boolean result = airlineAL4.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published when departure time is in the past", result);
        assertFalse("Flight should remain closed for booking after failed publication", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws Exception {
        // Setup
        Airline airlineAL5 = new Airline();
        
        Airport ap08 = new Airport();
        ap08.setId("AP08");
        City cityH = new City("CityH");
        ap08.addCity(cityH);
        
        Airport ap09 = new Airport();
        ap09.setId("AP09");
        City cityI = new City("CityI");
        ap09.addCity(cityI);
        
        Flight flight = new Flight();
        flight.setId("F104");
        flight.setDepartureAirport(ap08);
        flight.setArrialAirport(ap09);
        flight.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt (should succeed)
        boolean firstAttempt = airlineAL5.publishFlight(flight, currentTime);
        assertTrue("First publish attempt should succeed", firstAttempt);
        
        // Second publish attempt
        boolean secondAttempt = airlineAL5.publishFlight(flight, currentTime);
        
        // Verify
        assertFalse("Flight should not be published again after successful first publication", secondAttempt);
        assertTrue("Flight should remain open for booking after second publication attempt", flight.isOpenForBooking());
    }
}