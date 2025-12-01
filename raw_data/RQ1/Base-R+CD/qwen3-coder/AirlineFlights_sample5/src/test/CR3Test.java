import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR3Test {
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_confirmPendingReservation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap160 = new Airport();
        ap160.setId("AP160");
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        
        // Create flight F401
        Flight flight = new Flight();
        flight.setId("F401");
        flight.setDepartureAirport(ap160);
        flight.setArrivalAirport(ap161);
        flight.setDepartureTime(sdf.parse("2025-12-10 11:00:00"));
        flight.setArrivalTime(sdf.parse("2025-12-10 15:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create customer CU16
        Customer customer = new Customer();
        
        // Create booking BK401 with reservation R401
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        Reservation reservation = new Reservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        
        Passenger passenger = new Passenger();
        passenger.setName("P9");
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        // Current time
        Date now = sdf.parse("2025-11-01 09:00:00");
        
        // Execute: Confirm reservation R401
        boolean result = customer.confirm("R401", now);
        
        // Verify
        assertTrue(result);
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        
        // Create flight F402
        Flight flight = new Flight();
        flight.setId("F402");
        flight.setDepartureAirport(ap170);
        flight.setArrivalAirport(ap171);
        flight.setDepartureTime(sdf.parse("2025-12-15 15:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create customer CU17
        Customer customer = new Customer();
        
        // Create booking BK402 with reservation R402
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        Reservation reservation = new Reservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        Passenger passenger = new Passenger();
        passenger.setName("P10");
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        // Current time
        Date now = sdf.parse("2025-12-01 12:00:00");
        
        // Execute: Cancel reservation R402
        boolean result = customer.cancel("R402", now);
        
        // Verify
        assertTrue(result);
        assertEquals(ReservationStatus.CANCELLED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        
        // Create flight F403
        Flight flight = new Flight();
        flight.setId("F403");
        flight.setDepartureAirport(ap180);
        flight.setArrivalAirport(ap181);
        flight.setDepartureTime(sdf.parse("2025-01-05 06:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create reservation R403
        Reservation reservation = new Reservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        
        Passenger passenger = new Passenger();
        passenger.setName("P11");
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        flight.getReservations().add(reservation);
        
        // Current time (flight already left)
        Date now = sdf.parse("2025-01-05 07:00:00");
        
        // Create a customer with the reservation
        Customer customer = new Customer();
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        // Execute: Confirm reservation R403
        boolean result = customer.confirm("R403", now);
        
        // Verify
        assertFalse(result);
        assertEquals(ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        
        // Create flight F404
        Flight flight = new Flight();
        flight.setId("F404");
        flight.setDepartureAirport(ap190);
        flight.setArrivalAirport(ap191);
        flight.setDepartureTime(sdf.parse("2025-02-01 09:00:00"));
        flight.setOpenForBooking(false); // Flight is closed
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create reservation R404
        Reservation reservation = new Reservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        Passenger passenger = new Passenger();
        passenger.setName("P12");
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        flight.getReservations().add(reservation);
        
        // Current time
        Date now = sdf.parse("2025-01-20 08:00:00");
        
        // Create a customer with the reservation
        Customer customer = new Customer();
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.getReservations().add(reservation);
        customer.getBookings().add(booking);
        
        // Execute: Cancel reservation R404
        boolean result = customer.cancel("R404", now);
        
        // Verify
        assertFalse(result);
        assertEquals(ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        
        // Create flight F405
        Flight flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirport(ap200);
        flight.setArrivalAirport(ap201);
        flight.setDepartureTime(sdf.parse("2025-03-10 10:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create customer CU20 with reservation R405
        Customer customer20 = new Customer();
        Booking booking20 = new Booking();
        booking20.setCustomer(customer20);
        
        Reservation reservation405 = new Reservation();
        reservation405.setId("R405");
        reservation405.setStatus(ReservationStatus.PENDING);
        
        Passenger passenger13 = new Passenger();
        passenger13.setName("P13");
        reservation405.setPassenger(passenger13);
        reservation405.setFlight(flight);
        
        booking20.getReservations().add(reservation405);
        customer20.getBookings().add(booking20);
        flight.getReservations().add(reservation405);
        
        // Create customer CU21 with reservation R406
        Customer customer21 = new Customer();
        Booking booking21 = new Booking();
        booking21.setCustomer(customer21);
        
        Reservation reservation406 = new Reservation();
        reservation406.setId("R406");
        reservation406.setStatus(ReservationStatus.PENDING);
        
        Passenger passenger14 = new Passenger();
        passenger14.setName("P14");
        reservation406.setPassenger(passenger14);
        reservation406.setFlight(flight);
        
        booking21.getReservations().add(reservation406);
        customer21.getBookings().add(booking21);
        flight.getReservations().add(reservation406);
        
        // Current time
        Date now = sdf.parse("2025-02-15 09:00:00");
        
        // Execute: CU20 tries to confirm reservation R406 (which belongs to CU21)
        boolean result = customer20.confirm("R406", now);
        
        // Verify
        assertFalse(result);
        assertEquals(ReservationStatus.PENDING, reservation406.getStatus());
    }
}