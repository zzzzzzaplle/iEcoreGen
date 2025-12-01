import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customer;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() throws ParseException {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("AP100");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create passenger list
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Peter");
        passengerNames.add("Beck");
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Should have 1 booking", 1, customer.getBookings().size());
        assertEquals("Should have 2 reservations", 2, customer.getBookings().get(0).getReservations().size());
        assertEquals("Flight should have 2 reservations", 2, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() throws ParseException {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("AP102");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create passenger list with duplicate
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Alice");
        passengerNames.add("Alice"); // Duplicate
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger", result);
        assertEquals("Should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() throws ParseException {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create pre-existing booking with reservation for Jucy
        Booking existingBooking = new Booking();
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        Passenger jucyPassenger = new Passenger();
        jucyPassenger.setName("Jucy");
        existingReservation.setPassenger(jucyPassenger);
        existingReservation.setFlight(flight);
        existingReservation.setStatus(ReservationStatus.PENDING);
        
        List<Reservation> existingReservations = new ArrayList<>();
        existingReservations.add(existingReservation);
        existingBooking.setReservations(existingReservations);
        
        // Add existing booking to customer
        List<Booking> existingBookings = new ArrayList<>();
        existingBookings.add(existingBooking);
        customer.setBookings(existingBookings);
        
        // Add existing reservation to flight
        flight.getReservations().add(existingReservation);
        
        // Create new passenger list with Jucy
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Jucy");
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to already booked passenger", result);
        assertEquals("Should still have only 1 booking", 1, customer.getBookings().size());
        assertEquals("Flight should still have only 1 reservation", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() throws ParseException {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        // Create flight that is closed for booking
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight is closed
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create passenger list
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to closed flight", result);
        assertEquals("Should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_TheTimeIsAfterTheDepartureTime() throws ParseException {
        // Setup
        airline = new Airline();
        customer = new Customer();
        
        // Create airports
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        // Add flight to airline
        airline.addFlight(flight);
        
        // Create passenger list
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        // Current time is after departure time
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time after departure", result);
        assertEquals("Should have no bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have no reservations", 0, flight.getReservations().size());
    }
}