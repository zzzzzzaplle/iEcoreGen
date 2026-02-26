import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private Airline airline;
    private Flight flight;
    private Customer customer;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() {
        // Setup
        airline = new Airline();
        departureAirport = new Airport("AP100");
        arrivalAirport = new Airport("AP101");
        
        flight = new Flight("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-05 12:00:00", formatter));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, customer.getBookings().size());
        assertEquals("Booking should contain 2 reservations", 2, customer.getBookings().get(0).getReservations().size());
        assertEquals("Flight should have 2 reservations", 2, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() {
        // Setup
        airline = new Airline();
        departureAirport = new Airport("AP102");
        arrivalAirport = new Airport("AP103");
        
        flight = new Flight("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-05 10:00:00", formatter));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        List<String> passengerNames = Arrays.asList("Alice", "Alice"); // Duplicate passenger
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger names", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() {
        // Setup
        airline = new Airline();
        departureAirport = new Airport("AP104");
        arrivalAirport = new Airport("AP105");
        
        flight = new Flight("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        // Create pre-existing booking with passenger "Jucy"
        LocalDateTime setupTime = LocalDateTime.parse("2025-10-01 08:00:00", formatter);
        List<String> initialPassengerNames = Arrays.asList("Jucy");
        boolean initialBookingResult = customer.addBooking(flight, setupTime, initialPassengerNames);
        assertTrue("Initial booking should succeed", initialBookingResult);
        
        // Try to book the same passenger again
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        List<String> newPassengerNames = Arrays.asList("Jucy");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, newPassengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("Customer should still have only 1 booking", 1, customer.getBookings().size());
        assertEquals("Flight should still have only 1 reservation", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() {
        // Setup
        airline = new Airline();
        departureAirport = new Airport("AP106");
        arrivalAirport = new Airport("AP107");
        
        flight = new Flight("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight.setOpenForBooking(false); // Flight is closed for booking
        
        airline.addFlight(flight);
        customer = new Customer();
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to flight being closed", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_TheTimeIsAfterTheDepartureTime() {
        // Setup - Note: Test specification has inconsistent airport IDs, using AP104 and AP105 as in case 5 setup
        airline = new Airline();
        departureAirport = new Airport("AP104");
        arrivalAirport = new Airport("AP105");
        
        flight = new Flight("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        // Current time is after departure time
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-06 09:00:00", formatter);
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time being after departure time", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
}