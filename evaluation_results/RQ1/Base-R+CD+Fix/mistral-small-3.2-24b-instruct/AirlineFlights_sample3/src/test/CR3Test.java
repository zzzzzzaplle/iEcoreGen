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
    private Airline airline;
    private Customer customer;
    private Flight flight;
    private Reservation reservation;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private City departureCity;
    private City arrivalCity;
    private Passenger passenger;
    private Booking booking;
    
    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() throws ParseException {
        // Setup
        airline = new Airline();
        departureCity = new City("CityAA");
        arrivalCity = new City("CityAB");
        
        departureAirport = new Airport();
        departureAirport.addCity(departureCity);
        
        arrivalAirport = new Airport();
        arrivalAirport.addCity(arrivalCity);
        
        flight = new Flight();
        flight.setId("F401");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        flight.setOpenForBooking(true);
        
        passenger = new Passenger();
        passenger.setName("P9");
        
        reservation = new Reservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking = new Booking();
        booking.setReservations(new ArrayList<Reservation>());
        booking.getReservations().add(reservation);
        
        customer = new Customer();
        customer.setBookings(new ArrayList<Booking>());
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Pending reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() throws ParseException {
        // Setup
        airline = new Airline();
        departureCity = new City("CityAC");
        arrivalCity = new City("CityAD");
        
        departureAirport = new Airport();
        departureAirport.addCity(departureCity);
        
        arrivalAirport = new Airport();
        arrivalAirport.addCity(arrivalCity);
        
        flight = new Flight();
        flight.setId("F402");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-15 19:00:00")); // Added arrival time for completeness
        flight.setOpenForBooking(true);
        
        passenger = new Passenger();
        passenger.setName("P10");
        
        reservation = new Reservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking = new Booking();
        booking.setReservations(new ArrayList<Reservation>());
        booking.getReservations().add(reservation);
        
        customer = new Customer();
        customer.setBookings(new ArrayList<Booking>());
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Confirmed reservation should be canceled successfully", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() throws ParseException {
        // Setup
        airline = new Airline();
        departureCity = new City("CityAE");
        arrivalCity = new City("CityAF");
        
        departureAirport = new Airport();
        departureAirport.addCity(departureCity);
        
        arrivalAirport = new Airport();
        arrivalAirport.addCity(arrivalCity);
        
        flight = new Flight();
        flight.setId("F403");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-05 10:00:00")); // Added arrival time for completeness
        flight.setOpenForBooking(true);
        
        passenger = new Passenger();
        passenger.setName("P11");
        
        reservation = new Reservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking = new Booking();
        booking.setReservations(new ArrayList<Reservation>());
        booking.getReservations().add(reservation);
        
        customer = new Customer();
        customer.setBookings(new ArrayList<Booking>());
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Flight has departed, confirmation should fail", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() throws ParseException {
        // Setup
        airline = new Airline();
        departureCity = new City("CityAG");
        arrivalCity = new City("CityAH");
        
        departureAirport = new Airport();
        departureAirport.addCity(departureCity);
        
        arrivalAirport = new Airport();
        arrivalAirport.addCity(arrivalCity);
        
        flight = new Flight();
        flight.setId("F404");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-01 13:00:00")); // Added arrival time for completeness
        flight.setOpenForBooking(false);
        
        passenger = new Passenger();
        passenger.setName("P12");
        
        reservation = new Reservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        booking = new Booking();
        booking.setReservations(new ArrayList<Reservation>());
        booking.getReservations().add(reservation);
        
        customer = new Customer();
        customer.setBookings(new ArrayList<Booking>());
        customer.getBookings().add(booking);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Flight is closed, cancellation should fail", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() throws ParseException {
        // Setup
        airline = new Airline();
        departureCity = new City("CityAI");
        arrivalCity = new City("CityAJ");
        
        departureAirport = new Airport();
        departureAirport.addCity(departureCity);
        
        arrivalAirport = new Airport();
        arrivalAirport.addCity(arrivalCity);
        
        flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-10 14:00:00")); // Added arrival time for completeness
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
        booking405.setReservations(new ArrayList<Reservation>());
        booking405.getReservations().add(reservation405);
        
        Customer customer20 = new Customer();
        customer20.setBookings(new ArrayList<Booking>());
        customer20.getBookings().add(booking405);
        
        // Customer CU21 with reservation R406 (this is the unknown reservation for customer20)
        Passenger passenger14 = new Passenger();
        passenger14.setName("P14");
        
        Reservation reservation406 = new Reservation();
        reservation406.setId("R406");
        reservation406.setStatus(ReservationStatus.PENDING);
        reservation406.setPassenger(passenger14);
        reservation406.setFlight(flight);
        
        Booking booking406 = new Booking();
        booking406.setReservations(new ArrayList<Reservation>());
        booking406.getReservations().add(reservation406);
        
        Customer customer21 = new Customer();
        customer21.setBookings(new ArrayList<Booking>());
        customer21.getBookings().add(booking406);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - Customer CU20 tries to confirm reservation R406 (which belongs to CU21)
        boolean result = customer20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Unknown reservation ID should return false", result);
        assertEquals("Reservation R405 status should remain unchanged", ReservationStatus.PENDING, reservation405.getStatus());
        assertEquals("Reservation R406 status should remain unchanged", ReservationStatus.PENDING, reservation406.getStatus());
    }
}