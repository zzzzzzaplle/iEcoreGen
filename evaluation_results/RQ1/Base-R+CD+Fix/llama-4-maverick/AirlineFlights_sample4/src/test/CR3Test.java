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
        Airline airline = new Airline();
        
        Airport ap160 = new Airport();
        ap160.setId("AP160");
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        
        Flight flight = new Flight();
        flight.setId("F401");
        flight.setDepartureAirport(ap160);
        flight.setArrivalAirport(ap161);
        flight.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Passenger passenger = new Passenger();
        passenger.setName("P9");
        
        Reservation reservation = new Reservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(reservation);
        flight.setReservations(flightReservations);
        
        Customer customer = new Customer();
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        List<Reservation> bookingReservations = new ArrayList<>();
        bookingReservations.add(reservation);
        booking.setReservations(bookingReservations);
        
        List<Booking> customerBookings = new ArrayList<>();
        customerBookings.add(booking);
        customer.setBookings(customerBookings);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        
        Flight flight = new Flight();
        flight.setId("F402");
        flight.setDepartureAirport(ap170);
        flight.setArrivalAirport(ap171);
        flight.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Passenger passenger = new Passenger();
        passenger.setName("P10");
        
        Reservation reservation = new Reservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(reservation);
        flight.setReservations(flightReservations);
        
        Customer customer = new Customer();
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        List<Reservation> bookingReservations = new ArrayList<>();
        bookingReservations.add(reservation);
        booking.setReservations(bookingReservations);
        
        List<Booking> customerBookings = new ArrayList<>();
        customerBookings.add(booking);
        customer.setBookings(customerBookings);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        
        Flight flight = new Flight();
        flight.setId("F403");
        flight.setDepartureAirport(ap180);
        flight.setArrivalAirport(ap181);
        flight.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Passenger passenger = new Passenger();
        passenger.setName("P11");
        
        Reservation reservation = new Reservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(reservation);
        flight.setReservations(flightReservations);
        
        Customer customer = new Customer();
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        List<Reservation> bookingReservations = new ArrayList<>();
        bookingReservations.add(reservation);
        booking.setReservations(bookingReservations);
        
        List<Booking> customerBookings = new ArrayList<>();
        customerBookings.add(booking);
        customer.setBookings(customerBookings);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail because flight has departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        
        Flight flight = new Flight();
        flight.setId("F404");
        flight.setDepartureAirport(ap190);
        flight.setArrivalAirport(ap191);
        flight.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight.setOpenForBooking(false);
        
        airline.addFlight(flight);
        
        Passenger passenger = new Passenger();
        passenger.setName("P12");
        
        Reservation reservation = new Reservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(reservation);
        flight.setReservations(flightReservations);
        
        Customer customer = new Customer();
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        List<Reservation> bookingReservations = new ArrayList<>();
        bookingReservations.add(reservation);
        booking.setReservations(bookingReservations);
        
        List<Booking> customerBookings = new ArrayList<>();
        customerBookings.add(booking);
        customer.setBookings(customerBookings);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail because flight is closed for booking", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws ParseException {
        // Setup
        Airline airline = new Airline();
        
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        
        Flight flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirport(ap200);
        flight.setArrivalAirport(ap201);
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Passenger passengerCU20 = new Passenger();
        passengerCU20.setName("P13");
        
        Reservation reservationCU20 = new Reservation();
        reservationCU20.setId("R405");
        reservationCU20.setStatus(ReservationStatus.PENDING);
        reservationCU20.setPassenger(passengerCU20);
        reservationCU20.setFlight(flight);
        
        Passenger passengerCU21 = new Passenger();
        passengerCU21.setName("P14");
        
        Reservation reservationCU21 = new Reservation();
        reservationCU21.setId("R406");
        reservationCU21.setStatus(ReservationStatus.PENDING);
        reservationCU21.setPassenger(passengerCU21);
        reservationCU21.setFlight(flight);
        
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(reservationCU20);
        flightReservations.add(reservationCU21);
        flight.setReservations(flightReservations);
        
        Customer customerCU20 = new Customer();
        Booking bookingCU20 = new Booking();
        bookingCU20.setCustomer(customerCU20);
        
        List<Reservation> bookingReservationsCU20 = new ArrayList<>();
        bookingReservationsCU20.add(reservationCU20);
        bookingCU20.setReservations(bookingReservationsCU20);
        
        List<Booking> customerBookingsCU20 = new ArrayList<>();
        customerBookingsCU20.add(bookingCU20);
        customerCU20.setBookings(customerBookingsCU20);
        
        Customer customerCU21 = new Customer();
        Booking bookingCU21 = new Booking();
        bookingCU21.setCustomer(customerCU21);
        
        List<Reservation> bookingReservationsCU21 = new ArrayList<>();
        bookingReservationsCU21.add(reservationCU21);
        bookingCU21.setReservations(bookingReservationsCU21);
        
        List<Booking> customerBookingsCU21 = new ArrayList<>();
        customerBookingsCU21.add(bookingCU21);
        customerCU21.setBookings(customerBookingsCU21);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - Customer CU20 trying to confirm reservation R406 which belongs to CU21
        boolean result = customerCU20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail because reservation doesn't belong to customer", result);
        assertEquals("Reservation R406 status should remain PENDING", ReservationStatus.PENDING, reservationCU21.getStatus());
    }
}