import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP100");
        departureAirport.addCity(new City("OriginCity"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        arrivalAirport.addCity(new City("DestinationCity"));
        
        Flight flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, customer.getBookings().size());
        assertEquals("Booking should have 2 reservations", 2, customer.getBookings().get(0).getReservations().size());
        assertEquals("Flight should have 2 reservations", 2, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP102");
        departureAirport.addCity(new City("OriginCity"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        arrivalAirport.addCity(new City("DestinationCity"));
        
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Alice", "Alice"); // Duplicate passenger
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        departureAirport.addCity(new City("OriginCity"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        arrivalAirport.addCity(new City("DestinationCity"));
        
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create pre-existing booking with passenger "Jucy"
        Booking existingBooking = new Booking(customer);
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
        
        List<String> passengerNames = Arrays.asList("Jucy"); // Same passenger
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to existing passenger reservation", result);
        assertEquals("Customer should still have only 1 booking", 1, customer.getBookings().size());
        assertEquals("Flight should still have only 1 reservation", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        departureAirport.addCity(new City("OriginCity"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        arrivalAirport.addCity(new City("DestinationCity"));
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight is closed
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to closed flight", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTimeBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        departureAirport.addCity(new City("OriginCity"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        arrivalAirport.addCity(new City("DestinationCity"));
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true); // Flight is open
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time after departure", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
}