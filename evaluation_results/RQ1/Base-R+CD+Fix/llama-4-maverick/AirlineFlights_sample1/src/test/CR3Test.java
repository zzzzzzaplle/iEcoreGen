import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customer;
    private Flight flight;
    private Booking booking;
    private Reservation reservation;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Passenger passenger;
    private Date currentTime;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    
    @Test
    public void testCase1_confirmPendingReservation() throws Exception {
        // Setup
        airline = new Airline();
        
        departureAirport = new Airport();
        departureAirport.setId("AP160");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP161");
        
        flight = new Flight();
        flight.setId("F401");
        flight.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        passenger = new Passenger();
        passenger.setName("P9");
        
        reservation = new Reservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking = new Booking();
        booking.setReservations(Arrays.asList(reservation));
        
        customer = new Customer();
        customer.setBookings(Arrays.asList(booking));
        
        currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws Exception {
        // Setup
        airline = new Airline();
        
        departureAirport = new Airport();
        departureAirport.setId("AP170");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP171");
        
        flight = new Flight();
        flight.setId("F402");
        flight.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        passenger = new Passenger();
        passenger.setName("P10");
        
        reservation = new Reservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking = new Booking();
        booking.setReservations(Arrays.asList(reservation));
        
        customer = new Customer();
        customer.setBookings(Arrays.asList(booking));
        
        currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws Exception {
        // Setup
        airline = new Airline();
        
        departureAirport = new Airport();
        departureAirport.setId("AP180");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP181");
        
        flight = new Flight();
        flight.setId("F403");
        flight.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        passenger = new Passenger();
        passenger.setName("P11");
        
        reservation = new Reservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking = new Booking();
        booking.setReservations(Arrays.asList(reservation));
        
        customer = new Customer();
        customer.setBookings(Arrays.asList(booking));
        
        currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail because flight has departed", result);
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws Exception {
        // Setup
        airline = new Airline();
        
        departureAirport = new Airport();
        departureAirport.setId("AP190");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP191");
        
        flight = new Flight();
        flight.setId("F404");
        flight.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(false);
        
        passenger = new Passenger();
        passenger.setName("P12");
        
        reservation = new Reservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking = new Booking();
        booking.setReservations(Arrays.asList(reservation));
        
        customer = new Customer();
        customer.setBookings(Arrays.asList(booking));
        
        currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail because flight is closed", result);
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws Exception {
        // Setup
        airline = new Airline();
        
        departureAirport = new Airport();
        departureAirport.setId("AP200");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP201");
        
        flight = new Flight();
        flight.setId("F405");
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setOpenForBooking(true);
        
        // Customer CU20 with reservation R405
        Passenger passenger13 = new Passenger();
        passenger13.setName("P13");
        
        Reservation reservation405 = new Reservation();
        reservation405.setId("R405");
        reservation405.setStatus(ReservationStatus.PENDING);
        reservation405.setPassenger(passenger13);
        reservation405.setFlight(flight);
        
        Booking booking405 = new Booking();
        booking405.setReservations(Arrays.asList(reservation405));
        
        Customer customer20 = new Customer();
        customer20.setBookings(Arrays.asList(booking405));
        
        // Customer CU21 with reservation R406
        Passenger passenger14 = new Passenger();
        passenger14.setName("P14");
        
        Reservation reservation406 = new Reservation();
        reservation406.setId("R406");
        reservation406.setStatus(ReservationStatus.PENDING);
        reservation406.setPassenger(passenger14);
        reservation406.setFlight(flight);
        
        Booking booking406 = new Booking();
        booking406.setReservations(Arrays.asList(reservation406));
        
        Customer customer21 = new Customer();
        customer21.setBookings(Arrays.asList(booking406));
        
        currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute
        boolean result = customer20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail because reservation R406 belongs to another customer", result);
    }
}