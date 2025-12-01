package edu.flights.flights2.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.flights.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    
    private FlightsFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        factory = FlightsFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP10");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP11");
        
        Flight flight = factory.createFlight();
        flight.setId("F200");
        flight.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        airline.getFlights().add(flight);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should no longer be open for booking", flight.isOpenForBooking());
        assertTrue("No reservations should exist", flight.getReservations().isEmpty());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP12");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP13");
        
        Flight flight = factory.createFlight();
        flight.setId("F201");
        flight.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        // Create customer and booking with three confirmed reservations
        Customer customer = factory.createCustomer();
        Booking booking = factory.createBooking();
        booking.setCustomer(customer);
        
        // Create three confirmed reservations
        for (int i = 1; i <= 3; i++) {
            Reservation reservation = factory.createReservation();
            reservation.setId("R201-" + i);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservation.setFlight(flight);
            
            Passenger passenger = factory.createPassenger();
            passenger.setName("Passenger" + i);
            reservation.setPassenger(passenger);
            
            booking.getReservations().add(reservation);
            flight.getReservations().add(reservation);
        }
        
        customer.getBookings().add(booking);
        airline.getFlights().add(flight);
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should no longer be open for booking", flight.isOpenForBooking());
        
        // Check that all reservations are cancelled
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("Reservation should be cancelled", ReservationStatus.CANCELED, reservation.getStatus());
        }
        assertEquals("Should have 3 reservations", 3, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP14");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP15");
        
        Flight flight = factory.createFlight();
        flight.setId("F202");
        flight.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(false); // Already closed
        
        airline.getFlights().add(flight);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP16");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP17");
        
        Flight flight = factory.createFlight();
        flight.setId("F203");
        flight.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        // Add two confirmed reservations
        for (int i = 1; i <= 2; i++) {
            Reservation reservation = factory.createReservation();
            reservation.setId("R203-" + i);
            reservation.setStatus(ReservationStatus.CONFIRMED);
            reservation.setFlight(flight);
            
            Passenger passenger = factory.createPassenger();
            passenger.setName("Passenger" + i);
            reservation.setPassenger(passenger);
            
            flight.getReservations().add(reservation);
        }
        
        airline.getFlights().add(flight);
        
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should return false when trying to close after departure time", result);
        assertTrue("Flight should remain open for booking", flight.isOpenForBooking());
        
        // Verify reservations remain confirmed (not cancelled)
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("Reservations should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP18");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP19");
        
        Flight flight = factory.createFlight();
        flight.setId("F204");
        flight.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-01 01:30:00")); // Note: next day arrival
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        airline.getFlights().add(flight);
        
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should return false when trying to close after departure", result);
        assertTrue("Flight should remain open for booking", flight.isOpenForBooking());
    }
}