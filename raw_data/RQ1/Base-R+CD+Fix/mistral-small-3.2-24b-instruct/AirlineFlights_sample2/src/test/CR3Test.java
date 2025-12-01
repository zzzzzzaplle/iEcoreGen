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
    private City city1;
    private City city2;
    private Passenger passenger;
    private Date currentTime;

    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_ConfirmPendingReservation() throws Exception {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        city1 = new City();
        city1.setName("CityAA");
        city2 = new City();
        city2.setName("CityAB");
        departureAirport.addCity(city1);
        arrivalAirport.addCity(city2);
        
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
        
        currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Confirmation should succeed", result);
        assertEquals("Reservation status should be CONFIRMED", 
                     ReservationStatus.CONFIRMED, reservation.getStatus());
    }

    @Test
    public void testCase2_CancelConfirmedReservation() throws Exception {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        city1 = new City();
        city1.setName("CityAC");
        city2 = new City();
        city2.setName("CityAD");
        departureAirport.addCity(city1);
        arrivalAirport.addCity(city2);
        
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
        
        currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Cancellation should succeed", result);
        assertEquals("Reservation status should be CANCELED", 
                     ReservationStatus.CANCELED, reservation.getStatus());
    }

    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() throws Exception {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        city1 = new City();
        city1.setName("CityAE");
        city2 = new City();
        city2.setName("CityAF");
        departureAirport.addCity(city1);
        arrivalAirport.addCity(city2);
        
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
        
        currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail due to departed flight", result);
        assertEquals("Reservation status should remain PENDING", 
                     ReservationStatus.PENDING, reservation.getStatus());
    }

    @Test
    public void testCase4_ClosedFlightBlocksCancellation() throws Exception {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        city1 = new City();
        city1.setName("CityAG");
        city2 = new City();
        city2.setName("CityAH");
        departureAirport.addCity(city1);
        arrivalAirport.addCity(city2);
        
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
        
        currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail due to closed flight", result);
        assertEquals("Reservation status should remain CONFIRMED", 
                     ReservationStatus.CONFIRMED, reservation.getStatus());
    }

    @Test
    public void testCase5_UnknownReservationIdentifier() throws Exception {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        city1 = new City();
        city1.setName("CityAI");
        city2 = new City();
        city2.setName("CityAJ");
        departureAirport.addCity(city1);
        arrivalAirport.addCity(city2);
        
        flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        Customer customerCU20 = new Customer();
        Customer customerCU21 = new Customer();
        
        Passenger passengerP13 = new Passenger();
        passengerP13.setName("P13");
        Passenger passengerP14 = new Passenger();
        passengerP14.setName("P14");
        
        Booking bookingCU20 = new Booking();
        bookingCU20.setCustomer(customerCU20);
        Booking bookingCU21 = new Booking();
        bookingCU21.setCustomer(customerCU21);
        
        Reservation reservationR405 = new Reservation();
        reservationR405.setId("R405");
        reservationR405.setStatus(ReservationStatus.PENDING);
        reservationR405.setPassenger(passengerP13);
        reservationR405.setFlight(flight);
        bookingCU20.getReservations().add(reservationR405);
        flight.getReservations().add(reservationR405);
        customerCU20.getBookings().add(bookingCU20);
        
        Reservation reservationR406 = new Reservation();
        reservationR406.setId("R406");
        reservationR406.setStatus(ReservationStatus.PENDING);
        reservationR406.setPassenger(passengerP14);
        reservationR406.setFlight(flight);
        bookingCU21.getReservations().add(reservationR406);
        flight.getReservations().add(reservationR406);
        customerCU21.getBookings().add(bookingCU21);
        
        currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - Customer CU20 trying to confirm reservation R406 (which belongs to CU21)
        boolean result = customerCU20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail due to unknown reservation ID for this customer", result);
        assertEquals("Reservation R405 status should remain PENDING", 
                     ReservationStatus.PENDING, reservationR405.getStatus());
        assertEquals("Reservation R406 status should remain PENDING", 
                     ReservationStatus.PENDING, reservationR406.getStatus());
    }
}