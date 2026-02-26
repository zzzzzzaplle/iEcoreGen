import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
        
        Customer customer = new Customer();
        customer.setBookings(new ArrayList<>());
        
        Passenger passenger = new Passenger();
        passenger.setName("P9");
        
        Reservation reservation = new Reservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setReservations(new ArrayList<>(Arrays.asList(reservation)));
        
        customer.getBookings().add(booking);
        flight.setReservations(new ArrayList<>(Arrays.asList(reservation)));
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Action
        boolean result = customer.confirm("R401", currentTime);
        
        // Assert
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
        flight.setArrivalTime(dateFormat.parse("2025-12-15 19:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        customer.setBookings(new ArrayList<>());
        
        Passenger passenger = new Passenger();
        passenger.setName("P10");
        
        Reservation reservation = new Reservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setReservations(new ArrayList<>(Arrays.asList(reservation)));
        
        customer.getBookings().add(booking);
        flight.setReservations(new ArrayList<>(Arrays.asList(reservation)));
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Action
        boolean result = customer.cancel("R402", currentTime);
        
        // Assert
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
        flight.setArrivalTime(dateFormat.parse("2025-01-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        customer.setBookings(new ArrayList<>());
        
        Passenger passenger = new Passenger();
        passenger.setName("P11");
        
        Reservation reservation = new Reservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setReservations(new ArrayList<>(Arrays.asList(reservation)));
        
        customer.getBookings().add(booking);
        flight.setReservations(new ArrayList<>(Arrays.asList(reservation)));
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Action
        boolean result = customer.confirm("R403", currentTime);
        
        // Assert
        assertFalse("Reservation should not be confirmed when flight has departed", result);
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
        flight.setArrivalTime(dateFormat.parse("2025-02-01 13:00:00"));
        flight.setOpenForBooking(false);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        customer.setBookings(new ArrayList<>());
        
        Passenger passenger = new Passenger();
        passenger.setName("P12");
        
        Reservation reservation = new Reservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        Booking booking = new Booking();
        booking.setReservations(new ArrayList<>(Arrays.asList(reservation)));
        
        customer.getBookings().add(booking);
        flight.setReservations(new ArrayList<>(Arrays.asList(reservation)));
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Action
        boolean result = customer.cancel("R404", currentTime);
        
        // Assert
        assertFalse("Reservation should not be canceled when flight is closed", result);
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
        flight.setArrivalTime(dateFormat.parse("2025-03-10 14:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer20 = new Customer();
        customer20.setBookings(new ArrayList<>());
        
        Customer customer21 = new Customer();
        customer21.setBookings(new ArrayList<>());
        
        Passenger passenger13 = new Passenger();
        passenger13.setName("P13");
        
        Passenger passenger14 = new Passenger();
        passenger14.setName("P14");
        
        // Customer CU20's reservation
        Reservation reservation405 = new Reservation();
        reservation405.setId("R405");
        reservation405.setStatus(ReservationStatus.PENDING);
        reservation405.setPassenger(passenger13);
        reservation405.setFlight(flight);
        
        Booking booking405 = new Booking();
        booking405.setReservations(new ArrayList<>(Arrays.asList(reservation405)));
        customer20.getBookings().add(booking405);
        
        // Customer CU21's reservation
        Reservation reservation406 = new Reservation();
        reservation406.setId("R406");
        reservation406.setStatus(ReservationStatus.PENDING);
        reservation406.setPassenger(passenger14);
        reservation406.setFlight(flight);
        
        Booking booking406 = new Booking();
        booking406.setReservations(new ArrayList<>(Arrays.asList(reservation406)));
        customer21.getBookings().add(booking406);
        
        flight.setReservations(new ArrayList<>(Arrays.asList(reservation405, reservation406)));
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Action: Customer CU20 tries to confirm reservation R406 (which belongs to CU21)
        boolean result = customer20.confirm("R406", currentTime);
        
        // Assert
        assertFalse("Customer should not be able to confirm another customer's reservation", result);
        assertEquals("Reservation R405 should remain PENDING", ReservationStatus.PENDING, reservation405.getStatus());
        assertEquals("Reservation R406 should remain PENDING", ReservationStatus.PENDING, reservation406.getStatus());
    }
}