import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    private Airline airline;
    private Flight flight;
    private Customer customer;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport ap100 = new Airport();
        ap100.setId("AP100");
        Airport ap101 = new Airport();
        ap101.setId("AP101");
        
        flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(ap100);
        flight.setArrivalAirport(ap101);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Peter");
        passengerNames.add("Beck");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertTrue("Booking should succeed with two new passengers", result);
        assertEquals("Customer should have 1 booking", 1, customer.getBookings().size());
        Booking booking = customer.getBookings().get(0);
        assertEquals("Booking should have 2 reservations", 2, booking.getReservations().size());
        assertEquals("Flight should have 2 reservations", 2, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport ap102 = new Airport();
        ap102.setId("AP102");
        Airport ap103 = new Airport();
        ap103.setId("AP103");
        
        flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(ap102);
        flight.setArrivalAirport(ap103);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Alice");
        passengerNames.add("Alice"); // Duplicate passenger
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger", result);
        assertEquals("Customer should have 0 bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have 0 reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        
        flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(ap104);
        flight.setArrivalAirport(ap105);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        // Create pre-existing booking
        Passenger passenger = new Passenger();
        passenger.setName("Jucy");
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setStatus(ReservationStatus.PENDING);
        existingReservation.setPassenger(passenger);
        existingReservation.setFlight(flight);
        
        Booking existingBooking = new Booking();
        existingBooking.setCustomer(customer);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(existingReservation);
        existingBooking.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(existingBooking);
        customer.setBookings(bookings);
        
        flight.getReservations().add(existingReservation);
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Jucy"); // Same passenger as existing booking
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to existing passenger booking", result);
        assertEquals("Customer should still have 1 booking", 1, customer.getBookings().size());
        assertEquals("Flight should still have 1 reservation", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(ap106);
        flight.setArrivalAirport(ap107);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(false); // Flight closed for booking
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to closed flight", result);
        assertEquals("Customer should have 0 bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have 0 reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_timeAfterDepartureTimeBlocksBooking() throws Exception {
        // Setup
        airline = new Airline();
        
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(ap104);
        flight.setArrivalAirport(ap105);
        flight.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        customer = new Customer();
        
        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time after departure time", result);
        assertEquals("Customer should have 0 bookings", 0, customer.getBookings().size());
        assertEquals("Flight should have 0 reservations", 0, flight.getReservations().size());
    }
}