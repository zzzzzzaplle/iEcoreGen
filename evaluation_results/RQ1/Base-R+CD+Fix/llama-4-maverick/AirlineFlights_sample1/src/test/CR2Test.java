import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

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
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        
        Flight flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        customer = new Customer();
        
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Peter");
        passengerNames.add("Beck");
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed with two new passengers", result);
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP102");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        customer = new Customer();
        
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Alice");
        passengerNames.add("Alice"); // Duplicate passenger
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passengers", result);
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        customer = new Customer();
        
        // Create pre-existing booking with reservation for passenger "Jucy"
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        Passenger existingPassenger = new Passenger();
        existingPassenger.setName("Jucy");
        existingReservation.setPassenger(existingPassenger);
        existingReservation.setFlight(flight);
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(existingReservation);
        existingBooking.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(existingBooking);
        customer.setBookings(bookings);
        
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Jucy"); // Same passenger as existing reservation
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight is closed for booking
        
        airline.addFlight(flight);
        
        customer = new Customer();
        
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to flight being closed", result);
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTime() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        customer = new Customer();
        
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time being after departure time", result);
    }
}