import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR2Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Flight flight;
    private Customer customer;
    private List<String> passengerNames;
    
    @Before
    public void setUp() throws Exception {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws Exception {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        flight = new Flight();
        
        // Set flight details
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger list
        passengerNames = Arrays.asList("Peter", "Beck");
        
        // Current time
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute test
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify result
        assertTrue("Booking should succeed for two new passengers", result);
        
        // Verify reservations were created
        assertEquals("Should have 2 reservations", 2, customer.getBookings().size());
        
        // Verify passenger names in reservations
        List<String> actualPassengerNames = new ArrayList<>();
        for (Booking booking : customer.getBookings()) {
            for (Reservation reservation : booking.getReservations()) {
                actualPassengerNames.add(reservation.getPassenger().getName());
            }
        }
        assertTrue("Should contain Peter", actualPassengerNames.contains("Peter"));
        assertTrue("Should contain Beck", actualPassengerNames.contains("Beck"));
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws Exception {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        flight = new Flight();
        
        // Set flight details
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create customer
        customer = new Customer();
        
        // Create passenger list with duplicate names
        passengerNames = Arrays.asList("Alice", "Alice");
        
        // Current time
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute test
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify result
        assertFalse("Booking should fail for duplicate passengers", result);
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        flight = new Flight();
        
        // Set flight details
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create customer
        customer = new Customer();
        
        // Create pre-existing booking with reservation for Jucy
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        
        // Create reservation for Jucy
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        Passenger jucyPassenger = new Passenger();
        jucyPassenger.setName("Jucy");
        existingReservation.setPassenger(jucyPassenger);
        existingReservation.setFlight(flight);
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        // Add reservation to booking and flight
        existingBooking.getReservations().add(existingReservation);
        flight.getReservations().add(existingReservation);
        
        // Add booking to customer
        customer.getBookings().add(existingBooking);
        
        // Current time
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute test - try to book Jucy again
        boolean result = customer.addBooking(flight, currentTime, Arrays.asList("Jucy"));
        
        // Verify result
        assertFalse("Booking should fail for already booked passenger", result);
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        flight = new Flight();
        
        // Set flight details
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight is closed for booking
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create customer
        customer = new Customer();
        
        // Current time
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute test
        boolean result = customer.addBooking(flight, currentTime, Arrays.asList("Ruby"));
        
        // Verify result
        assertFalse("Booking should fail for closed flight", result);
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTimeBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        flight = new Flight();
        
        // Set flight details
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create customer
        customer = new Customer();
        
        // Current time is AFTER departure time
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00");
        
        // Execute test
        boolean result = customer.addBooking(flight, currentTime, Arrays.asList("Ruby"));
        
        // Verify result
        assertFalse("Booking should fail when current time is after departure time", result);
    }
}