package edu.flights.flights4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.flights.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR6Test {
    
    private FlightsFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = FlightsFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_AddFirstStopoverInsideJourneyWindow() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport ap28 = factory.createAirport();
        ap28.setId("AP28");
        Airport ap29 = factory.createAirport();
        ap29.setId("AP29");
        Airport ap30 = factory.createAirport();
        ap30.setId("AP30");
        
        City cityN = factory.createCity();
        City cityO = factory.createCity();
        City cityP = factory.createCity();
        
        ap28.getServesForCities().add(cityN);
        ap29.getServesForCities().add(cityO);
        ap30.getServesForCities().add(cityP);
        
        Flight flight = factory.createFlight();
        flight.setId("F601");
        flight.setDepartureAirport(ap28);
        flight.setArrialAirport(ap29);
        flight.setDepartureTime(dateFormat.parse("2025-04-20 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-20 15:00:00"));
        flight.setOpenForBooking(true);
        
        airline.getFlights().add(flight);
        
        Date currentTime = dateFormat.parse("2025-04-19 09:00:00");
        
        // Create stopover
        Stopover stopover = factory.createStopover();
        stopover.setArrivalTime(dateFormat.parse("2025-04-20 12:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-04-20 12:40:00"));
        
        // Test
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be added successfully", result);
        assertTrue("Flight should contain the stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase2_StopoverTimeOutsideWindow() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport ap32 = factory.createAirport();
        ap32.setId("AP32");
        Airport ap33 = factory.createAirport();
        ap33.setId("AP33");
        Airport ap31 = factory.createAirport();
        ap31.setId("AP31");
        
        City cityQ = factory.createCity();
        City cityR = factory.createCity();
        City cityS = factory.createCity();
        
        ap32.getServesForCities().add(cityQ);
        ap33.getServesForCities().add(cityR);
        ap31.getServesForCities().add(cityS);
        
        Flight flight = factory.createFlight();
        flight.setId("F602");
        flight.setDepartureAirport(ap32);
        flight.setArrialAirport(ap33);
        flight.setDepartureTime(dateFormat.parse("2025-05-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-05-10 14:00:00"));
        flight.setOpenForBooking(true);
        
        airline.getFlights().add(flight);
        
        Date currentTime = dateFormat.parse("2025-05-09 11:00:00");
        
        // Create stopover with times outside flight window
        Stopover stopover = factory.createStopover();
        stopover.setArrivalTime(dateFormat.parse("2025-05-10 16:00:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-05-10 17:00:00"));
        
        // Test
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added when times are outside flight window", result);
        assertFalse("Flight should not contain the stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase3_DeleteExistingStopover() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport ap34 = factory.createAirport();
        ap34.setId("AP34");
        Airport ap35 = factory.createAirport();
        ap35.setId("AP35");
        Airport ap36 = factory.createAirport();
        ap36.setId("AP36");
        
        City cityT = factory.createCity();
        City cityU = factory.createCity();
        City cityV = factory.createCity();
        
        ap34.getServesForCities().add(cityT);
        ap35.getServesForCities().add(cityU);
        ap36.getServesForCities().add(cityV);
        
        Flight flight = factory.createFlight();
        flight.setId("F603");
        flight.setDepartureAirport(ap34);
        flight.setArrialAirport(ap35);
        flight.setDepartureTime(dateFormat.parse("2025-06-15 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-15 18:00:00"));
        flight.setOpenForBooking(true);
        
        // Add existing stopover
        Stopover existingStopover = factory.createStopover();
        existingStopover.setArrivalTime(dateFormat.parse("2025-06-15 13:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-06-15 13:45:00"));
        flight.getStopovers().add(existingStopover);
        
        airline.getFlights().add(flight);
        
        Date currentTime = dateFormat.parse("2025-06-14 10:00:00");
        
        // Test
        boolean result = flight.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertTrue("Stopover should be removed successfully", result);
        assertFalse("Flight should not contain the stopover after removal", 
                   flight.getStopovers().contains(existingStopover));
    }
    
    @Test
    public void testCase4_FlightClosedPreventsModification() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport ap37 = factory.createAirport();
        ap37.setId("AP37");
        Airport ap38 = factory.createAirport();
        ap38.setId("AP38");
        Airport ap39 = factory.createAirport();
        ap39.setId("AP39");
        
        City cityW = factory.createCity();
        City cityX = factory.createCity();
        City cityY = factory.createCity();
        
        ap37.getServesForCities().add(cityW);
        ap38.getServesForCities().add(cityX);
        ap39.getServesForCities().add(cityY);
        
        Flight flight = factory.createFlight();
        flight.setId("F604");
        flight.setDepartureAirport(ap37);
        flight.setArrialAirport(ap38);
        flight.setDepartureTime(dateFormat.parse("2025-07-20 12:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-20 17:00:00"));
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.getFlights().add(flight);
        
        Date currentTime = dateFormat.parse("2025-07-10 09:00:00");
        
        // Create stopover
        Stopover stopover = factory.createStopover();
        stopover.setArrivalTime(dateFormat.parse("2025-07-20 13:30:00"));
        stopover.setDepartureTime(dateFormat.parse("2025-07-20 14:00:00"));
        
        // Test
        boolean result = flight.addStopover(stopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be added to closed flight", result);
        assertFalse("Flight should not contain the stopover", flight.getStopovers().contains(stopover));
    }
    
    @Test
    public void testCase5_AttemptRemovalAfterDeparture() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport ap42 = factory.createAirport();
        ap42.setId("AP42");
        Airport ap43 = factory.createAirport();
        ap43.setId("AP43");
        Airport ap44 = factory.createAirport();
        ap44.setId("AP44");
        
        City cityBB = factory.createCity();
        City cityCC = factory.createCity();
        City cityDD = factory.createCity();
        
        ap42.getServesForCities().add(cityBB);
        ap43.getServesForCities().add(cityCC);
        ap44.getServesForCities().add(cityDD);
        
        Flight flight = factory.createFlight();
        flight.setId("F606");
        flight.setDepartureAirport(ap42);
        flight.setArrialAirport(ap43);
        flight.setDepartureTime(dateFormat.parse("2025-12-09 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 00:00:00"));
        flight.setOpenForBooking(true);
        
        // Add existing stopover
        Stopover existingStopover = factory.createStopover();
        existingStopover.setArrivalTime(dateFormat.parse("2025-12-09 20:00:00"));
        existingStopover.setDepartureTime(dateFormat.parse("2025-12-09 20:45:00"));
        flight.getStopovers().add(existingStopover);
        
        airline.getFlights().add(flight);
        
        // Current time is after stopover departure (flight is mid-air)
        Date currentTime = dateFormat.parse("2025-12-09 20:50:00");
        
        // Test
        boolean result = flight.removeStopover(existingStopover, currentTime);
        
        // Verify
        assertFalse("Stopover should not be removed after flight departure", result);
        assertTrue("Flight should still contain the stopover", 
                  flight.getStopovers().contains(existingStopover));
    }
}