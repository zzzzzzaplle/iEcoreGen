package edu.flights.flights3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.flights.FlightsFactory;
import edu.flights.Airline;
import edu.flights.Airport;
import edu.flights.Flight;
import edu.flights.City;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CR1Test {
    
    private FlightsFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = FlightsFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CorrectScheduleAndRoute() throws Exception {
        // Create airline AL1
        Airline airline = factory.createAirline();
        
        // Create airports AP01 (serves CityA) and AP02 (serves CityB)
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP01");
        City cityA = factory.createCity();
        departureAirport.getServesForCities().add(cityA);
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP02");
        City cityB = factory.createCity();
        arrivalAirport.getServesForCities().add(cityB);
        
        // Create flight F100
        Flight flight = factory.createFlight();
        flight.setId("F100");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-01-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-10 14:00:00"));
        flight.setOpenForBooking(false);
        
        // Current system time = 2024-12-01 09:00:00
        Date currentTime = dateFormat.parse("2024-12-01 09:00:00");
        
        // Publish flight
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Expected Output: True
        assertTrue("Flight should be published successfully with correct schedule and route", result);
        assertTrue("Flight should be open for booking after successful publication", flight.isOpenForBooking());
        assertTrue("Flight should be added to airline's flight list", airline.getFlights().contains(flight));
    }
    
    @Test
    public void testCase2_DepartureAfterArrival() throws Exception {
        // Create airline AL2
        Airline airline = factory.createAirline();
        
        // Create airports AP03 (CityC) and AP04 (CityD)
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP03");
        City cityC = factory.createCity();
        departureAirport.getServesForCities().add(cityC);
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP04");
        City cityD = factory.createCity();
        arrivalAirport.getServesForCities().add(cityD);
        
        // Create flight F101 with departure after arrival
        Flight flight = factory.createFlight();
        flight.setId("F101");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-02-05 20:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-05 18:00:00")); // Arrival before departure
        flight.setOpenForBooking(false);
        
        // Current time = 2024-12-15 10:00:00
        Date currentTime = dateFormat.parse("2024-12-15 10:00:00");
        
        // Publish flight
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure time is after arrival time", result);
        assertFalse("Flight should remain closed for booking after failed publication", flight.isOpenForBooking());
        assertFalse("Flight should not be added to airline's flight list", airline.getFlights().contains(flight));
    }
    
    @Test
    public void testCase3_SameDepartureAndArrivalAirport() throws Exception {
        // Create airline AL3
        Airline airline = factory.createAirline();
        
        // Create airport AP05 (CityE)
        Airport airport = factory.createAirport();
        airport.setId("AP05");
        City cityE = factory.createCity();
        airport.getServesForCities().add(cityE);
        
        // Create flight F102 with same departure and arrival airport
        Flight flight = factory.createFlight();
        flight.setId("F102");
        flight.setDepartureAirport(airport);
        flight.setArrialAirport(airport); // Same airport
        flight.setDepartureTime(dateFormat.parse("2025-03-01 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-01 12:00:00"));
        flight.setOpenForBooking(false);
        
        // Current time = 2025-02-01 09:00:00
        Date currentTime = dateFormat.parse("2025-02-01 09:00:00");
        
        // Publish flight
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure and arrival airports are the same", result);
        assertFalse("Flight should remain closed for booking after failed publication", flight.isOpenForBooking());
        assertFalse("Flight should not be added to airline's flight list", airline.getFlights().contains(flight));
    }
    
    @Test
    public void testCase4_DepartureTimeInThePast() throws Exception {
        // Create airline AL4
        Airline airline = factory.createAirline();
        
        // Create airports AP06 (CityF) and AP07 (CityG)
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP06");
        City cityF = factory.createCity();
        departureAirport.getServesForCities().add(cityF);
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP07");
        City cityG = factory.createCity();
        arrivalAirport.getServesForCities().add(cityG);
        
        // Create flight F103
        Flight flight = factory.createFlight();
        flight.setId("F103");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-03-30 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-30 12:00:00"));
        flight.setOpenForBooking(false);
        
        // Current time = 2025-04-01 09:00:00 (after departure time)
        Date currentTime = dateFormat.parse("2025-04-01 09:00:00");
        
        // Publish flight
        boolean result = airline.publishFlight(flight, currentTime);
        
        // Expected Output: False
        assertFalse("Flight should not be published when departure time is in the past", result);
        assertFalse("Flight should remain closed for booking after failed publication", flight.isOpenForBooking());
        assertFalse("Flight should not be added to airline's flight list", airline.getFlights().contains(flight));
    }
    
    @Test
    public void testCase5_SecondPublishAttempt() throws Exception {
        // Create airline AL5
        Airline airline = factory.createAirline();
        
        // Create airports AP08 (CityH) and AP09 (CityI)
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP08");
        City cityH = factory.createCity();
        departureAirport.getServesForCities().add(cityH);
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP09");
        City cityI = factory.createCity();
        arrivalAirport.getServesForCities().add(cityI);
        
        // Create flight F104
        Flight flight = factory.createFlight();
        flight.setId("F104");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-05-05 07:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-05 10:00:00"));
        flight.setOpenForBooking(false);
        
        // Current time = 2025-04-01 10:00:00
        Date currentTime = dateFormat.parse("2025-04-01 10:00:00");
        
        // First publish attempt - should succeed
        boolean firstResult = airline.publishFlight(flight, currentTime);
        assertTrue("First publish attempt should succeed", firstResult);
        assertTrue("Flight should be open for booking after successful publication", flight.isOpenForBooking());
        assertTrue("Flight should be added to airline's flight list", airline.getFlights().contains(flight));
        
        // Second publish attempt - should fail
        boolean secondResult = airline.publishFlight(flight, currentTime);
        
        // Expected Output: False for second attempt
        assertFalse("Second publish attempt should fail when flight is already published", secondResult);
        // Flight should remain open for booking (no change from first successful publication)
        assertTrue("Flight should remain open for booking after second publish attempt", flight.isOpenForBooking());
    }
}