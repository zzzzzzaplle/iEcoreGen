package edu.flights.flights1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import edu.flights.*;
import org.eclipse.emf.common.util.EList;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CR2Test {
    
    private FlightsFactory factory;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() throws Exception {
        factory = FlightsFactory.eINSTANCE;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP100");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP101");
        
        Flight flight = factory.createFlight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setOpenForBooking(true);
        
        airline.getFlights().add(flight);
        
        Customer customer = factory.createCustomer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, "Peter,Beck");
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have one booking", 1, customer.getBookings().size());
        
        Booking booking = customer.getBookings().get(0);
        assertEquals("Booking should have two reservations", 2, booking.getReservations().size());
        
        EList<Reservation> reservations = booking.getReservations();
        assertEquals("First passenger should be Peter", "Peter", reservations.get(0).getPassenger().getName());
        assertEquals("Second passenger should be Beck", "Beck", reservations.get(1).getPassenger().getName());
        assertEquals("Reservation status should be PENDING", ReservationStatus.PENDING, reservations.get(0).getStatus());
        assertEquals("Reservation status should be PENDING", ReservationStatus.PENDING, reservations.get(1).getStatus());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP102");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP103");
        
        Flight flight = factory.createFlight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.getFlights().add(flight);
        
        Customer customer = factory.createCustomer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, "Alice,Alice");
        
        // Verify
        assertFalse("Booking should fail due to duplicate passengers", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP104");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP105");
        
        Flight flight = factory.createFlight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.getFlights().add(flight);
        
        Customer customer = factory.createCustomer();
        
        // Create pre-existing booking with passenger "Jucy"
        Booking existingBooking = factory.createBooking();
        existingBooking.setCustomer(customer);
        
        Passenger existingPassenger = factory.createPassenger();
        existingPassenger.setName("Jucy");
        
        Reservation existingReservation = factory.createReservation();
        existingReservation.setId("R302-A");
        existingReservation.setStatus(ReservationStatus.PENDING);
        existingReservation.setFlight(flight);
        existingReservation.setPassenger(existingPassenger);
        
        existingBooking.getReservations().add(existingReservation);
        flight.getReservations().add(existingReservation);
        customer.getBookings().add(existingBooking);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, "Jucy");
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger in existing booking", result);
        assertEquals("Customer should still have only one booking", 1, customer.getBookings().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP106");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP107");
        
        Flight flight = factory.createFlight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.getFlights().add(flight);
        
        Customer customer = factory.createCustomer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, "Ruby");
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTime() throws Exception {
        // Setup
        Airline airline = factory.createAirline();
        
        Airport departureAirport = factory.createAirport();
        departureAirport.setId("AP106");
        
        Airport arrivalAirport = factory.createAirport();
        arrivalAirport.setId("AP107");
        
        Flight flight = factory.createFlight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.getFlights().add(flight);
        
        Customer customer = factory.createCustomer();
        
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, "Ruby");
        
        // Verify
        assertFalse("Booking should fail because current time is after departure time", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
}