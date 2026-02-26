package edu.flights.flights4.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.flights.*;
import org.eclipse.emf.common.util.EList;
import java.util.Date;

public class CR5Test {
    
    private Airline airline;
    private FlightsFactory factory;
    
    @Before
    public void setUp() {
        factory = FlightsFactory.eINSTANCE;
        airline = factory.createAirline();
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup
        Flight flight = factory.createFlight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        
        // Create confirmed reservations
        Reservation res1 = factory.createReservation();
        res1.setId("R501-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation res2 = factory.createReservation();
        res2.setId("R501-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation res3 = factory.createReservation();
        res3.setId("R501-3");
        res3.setStatus(ReservationStatus.CONFIRMED);
        
        // Add reservations to flight
        flight.getReservations().add(res1);
        flight.getReservations().add(res2);
        flight.getReservations().add(res3);
        
        // Add flight to airline
        airline.getFlights().add(flight);
        
        // Test
        EList<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(3, confirmedReservations.size());
        assertTrue(containsReservation(confirmedReservations, "R501-1"));
        assertTrue(containsReservation(confirmedReservations, "R501-2"));
        assertTrue(containsReservation(confirmedReservations, "R501-3"));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup
        Flight flight = factory.createFlight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        
        // Create pending reservations
        Reservation res1 = factory.createReservation();
        res1.setId("R502-1");
        res1.setStatus(ReservationStatus.PENDING);
        
        Reservation res2 = factory.createReservation();
        res2.setId("R502-2");
        res2.setStatus(ReservationStatus.PENDING);
        
        // Add reservations to flight
        flight.getReservations().add(res1);
        flight.getReservations().add(res2);
        
        // Add flight to airline
        airline.getFlights().add(flight);
        
        // Test
        EList<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(confirmedReservations.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup
        Flight flight = factory.createFlight();
        flight.setId("F503");
        flight.setOpenForBooking(false); // Flight is closed
        
        // Create one confirmed reservation
        Reservation res1 = factory.createReservation();
        res1.setId("R503-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        
        // Add reservation to flight
        flight.getReservations().add(res1);
        
        // Add flight to airline
        airline.getFlights().add(flight);
        
        // Test
        EList<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Verify - should return empty list even though there's a confirmed reservation
        // because flight is not open for booking
        assertTrue(confirmedReservations.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup - airline has flights F504 and F505 only
        Flight flight504 = factory.createFlight();
        flight504.setId("F504");
        flight504.setOpenForBooking(true);
        
        Flight flight505 = factory.createFlight();
        flight505.setId("F505");
        flight505.setOpenForBooking(true);
        
        airline.getFlights().add(flight504);
        airline.getFlights().add(flight505);
        
        // Test - try to get confirmed reservations for non-existent flight FX999
        // Since we're testing the flight's method directly, we need to create a flight object
        // that's not in the airline to simulate "unknown flight"
        Flight unknownFlight = factory.createFlight();
        unknownFlight.setId("FX999");
        unknownFlight.setOpenForBooking(true);
        
        EList<Reservation> confirmedReservations = unknownFlight.getConfirmedReservations();
        
        // Verify - should return empty list for unknown flight
        assertTrue(confirmedReservations.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup
        Flight flight = factory.createFlight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        
        // Create mixed status reservations
        Reservation resA = factory.createReservation();
        resA.setId("R504-A");
        resA.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation resB = factory.createReservation();
        resB.setId("R504-B");
        resB.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation resC = factory.createReservation();
        resC.setId("R504-C");
        resC.setStatus(ReservationStatus.CANCELED);
        
        Reservation resD = factory.createReservation();
        resD.setId("R504-D");
        resD.setStatus(ReservationStatus.PENDING);
        
        // Add all reservations to flight
        flight.getReservations().add(resA);
        flight.getReservations().add(resB);
        flight.getReservations().add(resC);
        flight.getReservations().add(resD);
        
        // Add flight to airline
        airline.getFlights().add(flight);
        
        // Test
        EList<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Verify - should return only confirmed reservations
        assertEquals(2, confirmedReservations.size());
        assertTrue(containsReservation(confirmedReservations, "R504-A"));
        assertTrue(containsReservation(confirmedReservations, "R504-B"));
        assertFalse(containsReservation(confirmedReservations, "R504-C")); // CANCELLED
        assertFalse(containsReservation(confirmedReservations, "R504-D")); // PENDING
    }
    
    // Helper method to check if a reservation with given ID exists in the list
    private boolean containsReservation(EList<Reservation> reservations, String reservationId) {
        for (Reservation res : reservations) {
            if (reservationId.equals(res.getId())) {
                return true;
            }
        }
        return false;
    }
}