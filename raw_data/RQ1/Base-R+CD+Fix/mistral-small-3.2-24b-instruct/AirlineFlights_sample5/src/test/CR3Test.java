import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR3Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customer;
    private Flight flight;
    private Booking booking;
    private Reservation reservation;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private City departureCity;
    private City arrivalCity;
    private Passenger passenger;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() throws Exception {
        // Setup
        airline = new Airline();
        departureCity = new City();
        departureCity.setName("CityAA");
        arrivalCity = new City();
        arrivalCity.setName("CityAB");
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
        airline.addFlight(flight);
        customer = new Customer();
        passenger = new Passenger();
        passenger.setName("P9");
        booking = new Booking();
        booking.setCustomer(customer);
        reservation = new Reservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        customer.getBookings().add(booking);
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() throws Exception {
        // Setup
        airline = new Airline();
        departureCity = new City();
        departureCity.setName("CityAC");
        arrivalCity = new City();
        arrivalCity.setName("CityAD");
        departureAirport = new Airport();
        departureAirport.addCity(departureCity);
        arrivalAirport = new Airport();
        arrivalAirport.addCity(arrivalCity);
        flight = new Flight();
        flight.setId("F402");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        customer = new Customer();
        passenger = new Passenger();
        passenger.setName("P10");
        booking = new Booking();
        booking.setCustomer(customer);
        reservation = new Reservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        customer.getBookings().add(booking);
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() throws Exception {
        // Setup
        airline = new Airline();
        departureCity = new City();
        departureCity.setName("CityAE");
        arrivalCity = new City();
        arrivalCity.setName("CityAF");
        departureAirport = new Airport();
        departureAirport.addCity(departureCity);
        arrivalAirport = new Airport();
        arrivalAirport.addCity(arrivalCity);
        flight = new Flight();
        flight.setId("F403");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        customer = new Customer();
        passenger = new Passenger();
        passenger.setName("P11");
        booking = new Booking();
        booking.setCustomer(customer);
        reservation = new Reservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        customer.getBookings().add(booking);
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Reservation should not be confirmed when flight has departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() throws Exception {
        // Setup
        airline = new Airline();
        departureCity = new City();
        departureCity.setName("CityAG");
        arrivalCity = new City();
        arrivalCity.setName("CityAH");
        departureAirport = new Airport();
        departureAirport.addCity(departureCity);
        arrivalAirport = new Airport();
        arrivalAirport.addCity(arrivalCity);
        flight = new Flight();
        flight.setId("F404");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight.setOpenForBooking(false);
        airline.addFlight(flight);
        customer = new Customer();
        passenger = new Passenger();
        passenger.setName("P12");
        booking = new Booking();
        booking.setCustomer(customer);
        reservation = new Reservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        booking.getReservations().add(reservation);
        flight.getReservations().add(reservation);
        customer.getBookings().add(booking);
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Reservation should not be canceled when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() throws Exception {
        // Setup
        airline = new Airline();
        departureCity = new City();
        departureCity.setName("CityAI");
        arrivalCity = new City();
        arrivalCity.setName("CityAJ");
        departureAirport = new Airport();
        departureAirport.addCity(departureCity);
        arrivalAirport = new Airport();
        arrivalAirport.addCity(arrivalCity);
        flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        // Customer CU20 with reservation R405
        Customer customer20 = new Customer();
        Passenger passenger13 = new Passenger();
        passenger13.setName("P13");
        Booking booking405 = new Booking();
        booking405.setCustomer(customer20);
        Reservation reservation405 = new Reservation();
        reservation405.setId("R405");
        reservation405.setStatus(ReservationStatus.PENDING);
        reservation405.setPassenger(passenger13);
        reservation405.setFlight(flight);
        booking405.getReservations().add(reservation405);
        flight.getReservations().add(reservation405);
        customer20.getBookings().add(booking405);
        
        // Customer CU21 with reservation R406
        Customer customer21 = new Customer();
        Passenger passenger14 = new Passenger();
        passenger14.setName("P14");
        Booking booking406 = new Booking();
        booking406.setCustomer(customer21);
        Reservation reservation406 = new Reservation();
        reservation406.setId("R406");
        reservation406.setStatus(ReservationStatus.PENDING);
        reservation406.setPassenger(passenger14);
        reservation406.setFlight(flight);
        booking406.getReservations().add(reservation406);
        flight.getReservations().add(reservation406);
        customer21.getBookings().add(booking406);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - Customer CU20 tries to confirm reservation R406 (which belongs to CU21)
        boolean result = customer20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Customer should not be able to confirm another customer's reservation", result);
        assertEquals("Reservation R406 status should remain PENDING", ReservationStatus.PENDING, reservation406.getStatus());
    }
}