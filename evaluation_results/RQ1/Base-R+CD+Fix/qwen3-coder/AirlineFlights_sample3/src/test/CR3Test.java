import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_confirmPendingReservation() throws Exception {
        // Setup Airline AL16
        Airline airline = new Airline();
        
        // Setup Airports AP160 (CityAA) and AP161 (CityAB)
        Airport ap160 = new Airport();
        City cityAA = new City();
        cityAA.setName("CityAA");
        ap160.addCity(cityAA);
        
        Airport ap161 = new Airport();
        City cityAB = new City();
        cityAB.setName("CityAB");
        ap161.addCity(cityAB);
        
        // Setup Flight F401
        Flight flight = new Flight();
        flight.setId("F401");
        flight.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        flight.setDepartureAirport(ap160);
        flight.setArrivalAirport(ap161);
        flight.setOpenForBooking(true);
        
        // Setup Customer CU16, passenger P9
        Customer customer = new Customer();
        
        // Setup Booking BK401 contains reservation R401 (status = PENDING) for P9 on F401
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        // Create reservation R401
        Reservation reservation = new Reservation();
        reservation.setId("R401");
        reservation.setStatus(ReservationStatus.PENDING);
        
        Passenger passenger = new Passenger();
        passenger.setName("P9");
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        booking.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        // Add reservation to flight
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(reservation);
        flight.setReservations(flightReservations);
        
        // Current time = 2025-11-01 09:00:00
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Test: CU16 confirm reservation R401
        boolean result = customer.confirm("R401", currentTime);
        
        // Expected Output: True
        assertTrue("Pending reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws Exception {
        // Setup Airline AL17
        Airline airline = new Airline();
        
        // Setup Airports AP170 (CityAC) and AP171 (CityAD)
        Airport ap170 = new Airport();
        City cityAC = new City();
        cityAC.setName("CityAC");
        ap170.addCity(cityAC);
        
        Airport ap171 = new Airport();
        City cityAD = new City();
        cityAD.setName("CityAD");
        ap171.addCity(cityAD);
        
        // Setup Flight F402
        Flight flight = new Flight();
        flight.setId("F402");
        flight.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-12-15 19:00:00")); // Added arrival time for completeness
        flight.setDepartureAirport(ap170);
        flight.setArrivalAirport(ap171);
        flight.setOpenForBooking(true);
        
        // Setup Customer CU17, passenger P10
        Customer customer = new Customer();
        
        // Setup Booking BK402 contains reservation R402 (status = CONFIRMED) for P10 on F402
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        // Create reservation R402
        Reservation reservation = new Reservation();
        reservation.setId("R402");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        Passenger passenger = new Passenger();
        passenger.setName("P10");
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        booking.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        // Add reservation to flight
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(reservation);
        flight.setReservations(flightReservations);
        
        // Current time = 2025-12-01 12:00:00
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Test: CU17 cancel reservation R402
        boolean result = customer.cancel("R402", currentTime);
        
        // Expected Output: True
        assertTrue("Confirmed reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, reservation.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws Exception {
        // Setup Airline AL18
        Airline airline = new Airline();
        
        // Setup Airports AP180 (CityAE) and AP181 (CityAF)
        Airport ap180 = new Airport();
        City cityAE = new City();
        cityAE.setName("CityAE");
        ap180.addCity(cityAE);
        
        Airport ap181 = new Airport();
        City cityAF = new City();
        cityAF.setName("CityAF");
        ap181.addCity(cityAF);
        
        // Setup Flight F403
        Flight flight = new Flight();
        flight.setId("F403");
        flight.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-01-05 10:00:00")); // Added arrival time for completeness
        flight.setDepartureAirport(ap180);
        flight.setArrivalAirport(ap181);
        flight.setOpenForBooking(true);
        
        // Setup Customer
        Customer customer = new Customer();
        
        // Setup Booking with reservation R403 status = PENDING (passenger P11)
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        // Create reservation R403
        Reservation reservation = new Reservation();
        reservation.setId("R403");
        reservation.setStatus(ReservationStatus.PENDING);
        
        Passenger passenger = new Passenger();
        passenger.setName("P11");
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        booking.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        // Add reservation to flight
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(reservation);
        flight.setReservations(flightReservations);
        
        // Current time = 2025-01-05 07:00:00 (flight already left)
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Test: Confirm reservation R403
        boolean result = customer.confirm("R403", currentTime);
        
        // Expected Output: False
        assertFalse("Confirmation should fail when flight has already departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, reservation.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws Exception {
        // Setup Airline AL19
        Airline airline = new Airline();
        
        // Setup Airports AP190 (CityAG) and AP191 (CityAH)
        Airport ap190 = new Airport();
        City cityAG = new City();
        cityAG.setName("CityAG");
        ap190.addCity(cityAG);
        
        Airport ap191 = new Airport();
        City cityAH = new City();
        cityAH.setName("CityAH");
        ap191.addCity(cityAH);
        
        // Setup Flight F404
        Flight flight = new Flight();
        flight.setId("F404");
        flight.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-02-01 13:00:00")); // Added arrival time for completeness
        flight.setDepartureAirport(ap190);
        flight.setArrivalAirport(ap191);
        flight.setOpenForBooking(false); // Flight is closed
        
        // Setup Customer
        Customer customer = new Customer();
        
        // Setup Booking with reservation R404 status = CONFIRMED (passenger P12)
        Booking booking = new Booking();
        booking.setCustomer(customer);
        
        // Create reservation R404
        Reservation reservation = new Reservation();
        reservation.setId("R404");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        Passenger passenger = new Passenger();
        passenger.setName("P12");
        reservation.setPassenger(passenger);
        reservation.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        booking.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(booking);
        customer.setBookings(bookings);
        
        // Add reservation to flight
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(reservation);
        flight.setReservations(flightReservations);
        
        // Current time = 2025-01-20 08:00:00
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Test: Cancel reservation R404
        boolean result = customer.cancel("R404", currentTime);
        
        // Expected Output: False
        assertFalse("Cancellation should fail when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws Exception {
        // Setup Airline AL20
        Airline airline = new Airline();
        
        // Setup Airports AP200 (CityAI) and AP201 (CityAJ)
        Airport ap200 = new Airport();
        City cityAI = new City();
        cityAI.setName("CityAI");
        ap200.addCity(cityAI);
        
        Airport ap201 = new Airport();
        City cityAJ = new City();
        cityAJ.setName("CityAJ");
        ap201.addCity(cityAJ);
        
        // Setup Flight F405
        Flight flight = new Flight();
        flight.setId("F405");
        flight.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-03-10 14:00:00")); // Added arrival time for completeness
        flight.setDepartureAirport(ap200);
        flight.setArrivalAirport(ap201);
        flight.setOpenForBooking(true);
        
        // Setup Customer CU20 with one existing reservation R405 (status = PENDING) for passenger P13
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
        
        List<Reservation> reservations20 = new ArrayList<>();
        reservations20.add(reservation405);
        booking20.setReservations(reservations20);
        
        List<Booking> bookings20 = new ArrayList<>();
        bookings20.add(booking20);
        customer20.setBookings(bookings20);
        
        // Setup Customer CU21 with one existing reservation R406 (status = PENDING) for passenger P14
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
        
        List<Reservation> reservations21 = new ArrayList<>();
        reservations21.add(reservation406);
        booking21.setReservations(reservations21);
        
        List<Booking> bookings21 = new ArrayList<>();
        bookings21.add(booking21);
        customer21.setBookings(bookings21);
        
        // Add reservations to flight
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(reservation405);
        flightReservations.add(reservation406);
        flight.setReservations(flightReservations);
        
        // Current time = 2025-02-15 09:00:00
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Test: Customer CU20 confirm reservation R406 (which belongs to CU21)
        boolean result = customer20.confirm("R406", currentTime);
        
        // Expected Output: False
        assertFalse("Customer should not be able to confirm another customer's reservation", result);
        assertEquals("Reservation R406 status should remain PENDING", ReservationStatus.PENDING, reservation406.getStatus());
    }
}