import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customer;
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    
    @Before
    public void setUp() throws ParseException {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws ParseException {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        departureAirport.setId("AP100");
        arrivalAirport = new Airport();
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
        
        // Input
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Should have exactly one booking", 1, customer.getBookings().size());
        assertEquals("Should have exactly two reservations", 2, customer.getBookings().get(0).getReservations().size());
        assertEquals("Should have two reservations in flight", 2, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws ParseException {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        departureAirport.setId("AP102");
        arrivalAirport = new Airport();
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
        
        // Input - Duplicate passenger "Alice"
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail for duplicate passengers", result);
        assertEquals("Should have no bookings", 0, customer.getBookings().size());
        assertEquals("Should have no reservations in flight", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws ParseException {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        departureAirport.setId("AP104");
        arrivalAirport = new Airport();
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
        
        // Create pre-existing booking with passenger "Jucy"
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> existingPassenger = Arrays.asList("Jucy");
        customer.addBooking(flight, currentTime, existingPassenger);
        
        // Input - Try to book same passenger again
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail for already booked passenger", result);
        assertEquals("Should have only one booking", 1, customer.getBookings().size());
        assertEquals("Should have only one reservation in flight", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws ParseException {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        departureAirport.setId("AP106");
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(flight);
        customer = new Customer();
        
        // Input
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail for closed flight", result);
        assertEquals("Should have no bookings", 0, customer.getBookings().size());
        assertEquals("Should have no reservations in flight", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTime() throws ParseException {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        departureAirport.setId("AP104");
        arrivalAirport = new Airport();
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
        
        // Input - Current time is after departure time
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00");
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail when current time is after departure time", result);
        assertEquals("Should have no bookings", 0, customer.getBookings().size());
        assertEquals("Should have no reservations in flight", 0, flight.getReservations().size());
    }
}