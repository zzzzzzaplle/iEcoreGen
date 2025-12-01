package edu.flights.flights1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import edu.flights.*;
import org.eclipse.emf.common.util.EList;

public class CR3Test {
    
    private FlightsFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        factory = FlightsFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP160");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP161");
        
        Flight flight = factory.createFlight();
        flight.setId("F401");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        flight.setOpenForBooking(true);
        
        Customer customer = factory.createCustomer();
        
        Passenger passenger = factory.createPassenger();
        passenger.setName("P9");
        
        Reservation reservation = factory.createReservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = factory.createBooking();
        booking.setCustomer(customer);
        booking.getReservations().add(reservation);
        
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Pending reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP170");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP171");
        
        Flight flight = factory.createFlight();
        flight.setId("F402");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight.setOpenForBooking(true);
        
        Customer customer = factory.createCustomer();
        
        Passenger passenger = factory.createPassenger();
        passenger.setName("P10");
        
        Reservation reservation = factory.createReservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = factory.createBooking();
        booking.setCustomer(customer);
        booking.getReservations().add(reservation);
        
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Confirmed reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELLED", ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP180");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP181");
        
        Flight flight = factory.createFlight();
        flight.setId("F403");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight.setOpenForBooking(true);
        
        Customer customer = factory.createCustomer();
        
        Passenger passenger = factory.createPassenger();
        passenger.setName("P11");
        
        Reservation reservation = factory.createReservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = factory.createBooking();
        booking.setCustomer(customer);
        booking.getReservations().add(reservation);
        
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00"); // Flight already departed
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail when flight has already departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP190");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP191");
        
        Flight flight = factory.createFlight();
        flight.setId("F404");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight.setOpenForBooking(false); // Flight is closed
        
        Customer customer = factory.createCustomer();
        
        Passenger passenger = factory.createPassenger();
        passenger.setName("P12");
        
        Reservation reservation = factory.createReservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = factory.createBooking();
        booking.setCustomer(customer);
        booking.getReservations().add(reservation);
        
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP200");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP201");
        
        Flight flight = factory.createFlight();
        flight.setId("F405");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setOpenForBooking(true);
        
        Customer customerCU20 = factory.createCustomer();
        
        Customer customerCU21 = factory.createCustomer();
        
        Passenger passengerP13 = factory.createPassenger();
        passengerP13.setName("P13");
        
        Passenger passengerP14 = factory.createPassenger();
        passengerP14.setName("P14");
        
        // Reservation R405 for customer CU20
        Reservation reservationR405 = factory.createReservation();
        reservationR405.setId("R405");
        reservationR405.setStatus(ReservationStatus.PENDING);
        reservationR405.setPassenger(passengerP13);
        reservationR405.setFlight(flight);
        
        Booking bookingCU20 = factory.createBooking();
        bookingCU20.setCustomer(customerCU20);
        bookingCU20.getReservations().add(reservationR405);
        customerCU20.getBookings().add(bookingCU20);
        
        // Reservation R406 for customer CU21
        Reservation reservationR406 = factory.createReservation();
        reservationR406.setId("R406");
        reservationR406.setStatus(ReservationStatus.PENDING);
        reservationR406.setPassenger(passengerP14);
        reservationR406.setFlight(flight);
        
        Booking bookingCU21 = factory.createBooking();
        bookingCU21.setCustomer(customerCU21);
        bookingCU21.getReservations().add(reservationR406);
        customerCU21.getBookings().add(bookingCU21);
        
        // Add both reservations to flight
        flight.getReservations().add(reservationR405);
        flight.getReservations().add(reservationR406);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - CU20 trying to confirm CU21's reservation R406
        boolean result = customerCU20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation identifier", result);
        assertEquals("Reservation R406 status should remain PENDING", ReservationStatus.PENDING, reservationR406.getStatus());
    }
}