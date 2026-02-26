import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Flight flight;
    private Customer customer;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    // Test Case 1: "Two new passengers succeed"
    @Test
    public void testCase1_twoNewPassengersSucceed() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP100");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        
        flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed with two new passengers", result);
        assertEquals("Customer should have one booking", 1, customer.getBookings().size());
        assertEquals("Booking should have two reservations", 2, customer.getBookings().get(0).getReservations().size());
        assertEquals("Flight should have two reservations", 2, flight.getReservations().size());
    }

    // Test Case 2: "Duplicate passenger in same request"
    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP102");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        
        flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail with duplicate passengers in same request", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }

    // Test Case 3: "Passenger already booked earlier"
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create pre-existing booking with passenger "Jucy"
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        
        Passenger existingPassenger = new Passenger();
        existingPassenger.setName("Jucy");
        
        Reservation existingReservation = new Reservation();
        existingReservation.setFlight(flight);
        existingReservation.setPassenger(existingPassenger);
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        existingBooking.getReservations().add(existingReservation);
        flight.getReservations().add(existingReservation);
        customer.getBookings().add(existingBooking);
        
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail with passenger already booked", result);
        assertEquals("Customer should have only one booking", 1, customer.getBookings().size());
        assertEquals("Flight should have only one reservation", 1, flight.getReservations().size());
    }

    // Test Case 4: "Flight closed blocks booking"
    @Test
    public void testCase4_flightClosedBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight is closed for booking
        
        airline.addFlight(flight);
        customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail when flight is closed", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }

    // Test Case 5: "The time is after the departure time"
    @Test
    public void testCase5_timeAfterDepartureTimeBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // Current time after departure time
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail when current time is after departure time", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
}