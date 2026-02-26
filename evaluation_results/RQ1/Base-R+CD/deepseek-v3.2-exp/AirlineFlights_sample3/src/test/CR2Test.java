import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customer;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports AP100 (departure) and AP101 (arrival)
        Airport ap100 = new Airport();
        ap100.setId("AP100");
        Airport ap101 = new Airport();
        ap101.setId("AP101");
        
        // Create flight F300
        Flight flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(ap100);
        flight.setArrivalAirport(ap101);
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
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, customer.getBookings().size());
        assertEquals("Booking should have 2 reservations", 2, customer.getBookings().get(0).getReservations().size());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports AP102 (dep) and AP103 (arr)
        Airport ap102 = new Airport();
        ap102.setId("AP102");
        Airport ap103 = new Airport();
        ap103.setId("AP103");
        
        // Create flight F301
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(ap102);
        flight.setArrivalAirport(ap103);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Alice", "Alice"); // Duplicate passenger
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger in request", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports AP104 and AP105
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        
        // Create flight F302
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(ap104);
        flight.setArrivalAirport(ap105);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        // Create pre-existing booking for passenger "Jucy"
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        
        Passenger existingPassenger = new Passenger();
        existingPassenger.setName("Jucy");
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setPassenger(existingPassenger);
        existingReservation.setFlight(flight);
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        existingBooking.addReservation(existingReservation);
        flight.addReservation(existingReservation);
        customer.getBookings().add(existingBooking);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Jucy"); // Same passenger as existing booking
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("Customer should still have only 1 booking", 1, customer.getBookings().size());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports AP106 and AP107
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        
        // Create flight F303 (closed for booking)
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(ap106);
        flight.setArrivalAirport(ap107);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to flight being closed", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase5_timeAfterDepartureTimeBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        // Create airports AP104 and AP105
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        
        // Create flight F303 (open for booking)
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(ap104);
        flight.setArrivalAirport(ap105);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time being after departure time", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
}