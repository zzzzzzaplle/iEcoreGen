import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_confirmPendingReservation() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setBookings(new ArrayList<>());
        
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP160");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP161");
        
        Flight flight = new Flight();
        flight.setId("F401");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setReservations(new ArrayList<>());
        
        Passenger passenger = new Passenger();
        passenger.setName("P9");
        
        Reservation reservation = new Reservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setReservations(new ArrayList<>());
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
    public void testCase2_cancelConfirmedReservation() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setBookings(new ArrayList<>());
        
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP170");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP171");
        
        Flight flight = new Flight();
        flight.setId("F402");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setReservations(new ArrayList<>());
        
        Passenger passenger = new Passenger();
        passenger.setName("P10");
        
        Reservation reservation = new Reservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setReservations(new ArrayList<>());
        booking.getReservations().add(reservation);
        
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Confirmed reservation should be canceled successfully", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setBookings(new ArrayList<>());
        
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP180");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP181");
        
        Flight flight = new Flight();
        flight.setId("F403");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setReservations(new ArrayList<>());
        
        Passenger passenger = new Passenger();
        passenger.setName("P11");
        
        Reservation reservation = new Reservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setReservations(new ArrayList<>());
        booking.getReservations().add(reservation);
        
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail when flight has already departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setBookings(new ArrayList<>());
        
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP190");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP191");
        
        Flight flight = new Flight();
        flight.setId("F404");
        flight.setOpenForBooking(false);
        flight.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setReservations(new ArrayList<>());
        
        Passenger passenger = new Passenger();
        passenger.setName("P12");
        
        Reservation reservation = new Reservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setReservations(new ArrayList<>());
        booking.getReservations().add(reservation);
        
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail when flight is closed for booking", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setBookings(new ArrayList<>());
        
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP200");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP201");
        
        Flight flight = new Flight();
        flight.setId("F405");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setReservations(new ArrayList<>());
        
        Passenger passenger = new Passenger();
        passenger.setName("P13");
        
        Reservation reservation = new Reservation();
        reservation.setId("R405");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setCustomer(customer);
        booking.setReservations(new ArrayList<>());
        booking.getReservations().add(reservation);
        
        customer.getBookings().add(booking);
        flight.getReservations().add(reservation);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute
        boolean result = customer.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation ID", result);
    }
}