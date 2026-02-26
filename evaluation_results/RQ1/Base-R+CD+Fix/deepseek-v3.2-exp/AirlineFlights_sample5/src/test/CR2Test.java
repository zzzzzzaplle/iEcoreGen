import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    private Airline airline;
    private Customer customer;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP100");
        City departureCity = new City();
        departureCity.setName("CityA");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        City arrivalCity = new City();
        arrivalCity.setName("CityB");
        arrivalAirport.addCity(arrivalCity);
        
        Flight flight = new Flight();
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
        City departureCity = new City();
        departureCity.setName("CityC");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        City arrivalCity = new City();
        arrivalCity.setName("CityD");
        arrivalAirport.addCity(arrivalCity);
        
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
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
        assertFalse("Booking should fail with duplicate passengers in same request", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        City departureCity = new City();
        departureCity.setName("CityE");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        City arrivalCity = new City();
        arrivalCity.setName("CityF");
        arrivalAirport.addCity(arrivalCity);
        
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        // Create pre-existing booking with passenger "Jucy"
        Passenger passenger = new Passenger();
        passenger.setName("Jucy");
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setPassenger(passenger);
        existingReservation.setFlight(flight);
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        existingBooking.getReservations().add(existingReservation);
        customer.getBookings().add(existingBooking);
        flight.getReservations().add(existingReservation);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Jucy"); // Same passenger as existing
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail with passenger already booked", result);
        assertEquals("Customer should still have only 1 booking", 1, customer.getBookings().size());
        assertEquals("Flight should still have only 1 reservation", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        City departureCity = new City();
        departureCity.setName("CityG");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        City arrivalCity = new City();
        arrivalCity.setName("CityH");
        arrivalAirport.addCity(arrivalCity);
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight closed for booking
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail with flight closed", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTimeBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        City departureCity = new City();
        departureCity.setName("CityI");
        departureAirport.addCity(departureCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        City arrivalCity = new City();
        arrivalCity.setName("CityJ");
        arrivalAirport.addCity(arrivalCity);
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
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
        assertFalse("Booking should fail with current time after departure time", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
}