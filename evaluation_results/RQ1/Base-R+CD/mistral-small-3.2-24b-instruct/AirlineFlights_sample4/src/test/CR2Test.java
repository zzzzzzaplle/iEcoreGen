import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP100");
        City departureCity = new City();
        departureCity.setName("OriginCity");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        City arrivalCity = new City();
        arrivalCity.setName("DestinationCity");
        arrivalAirport.addCity(arrivalCity);
        
        Flight flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
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
        assertEquals("Customer should have one booking", 1, customer.getBookings().size());
        Booking booking = customer.getBookings().get(0);
        assertEquals("Booking should have two reservations", 2, booking.getReservations().size());
        assertEquals("Flight should have two reservations", 2, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP102");
        City departureCity = new City();
        departureCity.setName("OriginCity");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        City arrivalCity = new City();
        arrivalCity.setName("DestinationCity");
        arrivalAirport.addCity(arrivalCity);
        
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
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
    public void testCase3_passengerAlreadyBookedEarlier() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        City departureCity = new City();
        departureCity.setName("OriginCity");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        City arrivalCity = new City();
        arrivalCity.setName("DestinationCity");
        arrivalAirport.addCity(arrivalCity);
        
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Create pre-existing booking with reservation for passenger "Jucy"
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId(UUID.randomUUID().toString());
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        Passenger passenger = new Passenger();
        passenger.setName("Jucy");
        existingReservation.setPassenger(passenger);
        existingReservation.setFlight(flight);
        
        existingBooking.getReservations().add(existingReservation);
        flight.getReservations().add(existingReservation);
        customer.getBookings().add(existingBooking);
        
        List<String> passengerNames = Arrays.asList("Jucy"); // Same passenger as existing reservation
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("Customer should still have only one booking", 1, customer.getBookings().size());
        assertEquals("Flight should still have only one reservation", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        City departureCity = new City();
        departureCity.setName("OriginCity");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        City arrivalCity = new City();
        arrivalCity.setName("DestinationCity");
        arrivalAirport.addCity(arrivalCity);
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight is closed for booking
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to flight being closed", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_timeAfterDepartureTimeBlocksBooking() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        City departureCity = new City();
        departureCity.setName("OriginCity");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        City arrivalCity = new City();
        arrivalCity.setName("DestinationCity");
        arrivalAirport.addCity(arrivalCity);
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true); // Flight is open for booking
        
        airline.addFlight(flight);
        
        Customer customer = new Customer();
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // Current time after departure time
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time being after departure time", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
}