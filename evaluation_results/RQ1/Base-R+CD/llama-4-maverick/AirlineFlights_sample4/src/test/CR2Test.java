import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CR2Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customer;
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create airline AL11
        airline = new Airline();
        
        // Create airports AP100 (departure) and AP101 (arrival)
        departureAirport = new Airport();
        departureAirport.setId("AP100");
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        
        // Create flight F300 under AL11
        flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Instantiate customer CUA
        customer = new Customer();
        
        // Execute test
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify results
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, customer.getBookings().size());
        assertEquals("Booking should have 2 reservations", 2, customer.getBookings().get(0).getReservations().size());
        
        // Verify reservation details
        List<Reservation> reservations = customer.getBookings().get(0).getReservations();
        assertEquals("Peter reservation should be created", "Peter", reservations.get(0).getPassenger().getName());
        assertEquals("Beck reservation should be created", "Beck", reservations.get(1).getPassenger().getName());
        assertEquals("Reservations should be PENDING", ReservationStatus.PENDING, reservations.get(0).getStatus());
        assertEquals("Reservations should be PENDING", ReservationStatus.PENDING, reservations.get(1).getStatus());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create airline AL12
        airline = new Airline();
        
        // Create airports AP102 (dep) and AP103 (arr)
        departureAirport = new Airport();
        departureAirport.setId("AP102");
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        
        // Create flight F301 under AL12
        flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Customer CUB
        customer = new Customer();
        
        // Execute test - duplicate passenger "Alice"
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify results
        assertFalse("Booking should fail due to duplicate passenger", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create airline AL13 with airports AP104, AP105
        airline = new Airline();
        
        departureAirport = new Airport();
        departureAirport.setId("AP104");
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        // Create flight F302
        flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Customer CUC
        customer = new Customer();
        
        // Pre-existing booking BK-OLD containing reservation R302-A for passenger P4 "Jucy" on F302 (status = PENDING)
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        Passenger existingPassenger = new Passenger();
        existingPassenger.setName("Jucy");
        existingReservation.setPassenger(existingPassenger);
        existingReservation.setFlight(flight);
        
        existingBooking.getReservations().add(existingReservation);
        flight.getReservations().add(existingReservation);
        customer.getBookings().add(existingBooking);
        
        // Execute test - try to book same passenger again
        List<String> passengerNames = Arrays.asList("Jucy");
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify results
        assertFalse("Booking should fail due to already booked passenger", result);
        assertEquals("Customer should still have only 1 booking", 1, customer.getBookings().size());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create airline AL14 with airports AP106, AP107
        airline = new Airline();
        
        departureAirport = new Airport();
        departureAirport.setId("AP106");
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        // Create flight F303 under AL14 (closed for booking)
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(flight);
        
        // Customer CUD
        customer = new Customer();
        
        // Execute test
        List<String> passengerNames = Arrays.asList("Ruby");
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify results
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase5_timeIsAfterDepartureTime() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        
        // Create airline AL14 with airports AP106, AP107
        airline = new Airline();
        
        departureAirport = new Airport();
        departureAirport.setId("AP106");
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        // Create flight F303 under AL14 (open for booking)
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Customer CUD
        customer = new Customer();
        
        // Execute test
        List<String> passengerNames = Arrays.asList("Ruby");
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify results
        assertFalse("Booking should fail because current time is after departure time", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
}