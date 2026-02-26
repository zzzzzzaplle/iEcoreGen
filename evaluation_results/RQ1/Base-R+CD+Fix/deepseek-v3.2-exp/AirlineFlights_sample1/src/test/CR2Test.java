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
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP100");
        City depCity = new City();
        depCity.setName("DepartureCity");
        departureAirport.addCity(depCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        City arrCity = new City();
        arrCity.setName("ArrivalCity");
        arrivalAirport.addCity(arrCity);
        
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
        
        Booking booking = customer.getBookings().get(0);
        assertEquals("Booking should have 2 reservations", 2, booking.getReservations().size());
        
        Set<String> passengerNamesFromReservations = new HashSet<>();
        for (Reservation reservation : booking.getReservations()) {
            passengerNamesFromReservations.add(reservation.getPassenger().getName());
            assertEquals("Reservation should be PENDING", ReservationStatus.PENDING, reservation.getStatus());
        }
        
        assertTrue("Should contain Peter", passengerNamesFromReservations.contains("Peter"));
        assertTrue("Should contain Beck", passengerNamesFromReservations.contains("Beck"));
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP102");
        City depCity = new City();
        depCity.setName("DepartureCity2");
        departureAirport.addCity(depCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        City arrCity = new City();
        arrCity.setName("ArrivalCity2");
        arrivalAirport.addCity(arrCity);
        
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
        List<String> passengerNames = Arrays.asList("Alice", "Alice"); // Duplicate
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passengers", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        City depCity = new City();
        depCity.setName("DepartureCity3");
        departureAirport.addCity(depCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        City arrCity = new City();
        arrCity.setName("ArrivalCity3");
        arrivalAirport.addCity(arrCity);
        
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
        Passenger existingPassenger = new Passenger();
        existingPassenger.setName("Jucy");
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setStatus(ReservationStatus.PENDING);
        existingReservation.setPassenger(existingPassenger);
        existingReservation.setFlight(flight);
        
        existingBooking.getReservations().add(existingReservation);
        flight.getReservations().add(existingReservation);
        customer.getBookings().add(existingBooking);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = Arrays.asList("Jucy"); // Same passenger
        
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
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        City depCity = new City();
        depCity.setName("DepartureCity4");
        departureAirport.addCity(depCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        City arrCity = new City();
        arrCity.setName("ArrivalCity4");
        arrivalAirport.addCity(arrCity);
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
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
    public void testCase5_timeIsAfterDepartureTime() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        City depCity = new City();
        depCity.setName("DepartureCity5");
        departureAirport.addCity(depCity);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        City arrCity = new City();
        arrCity.setName("ArrivalCity5");
        arrivalAirport.addCity(arrCity);
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time after departure", result);
        assertEquals("Customer should have no bookings", 0, customer.getBookings().size());
    }
}