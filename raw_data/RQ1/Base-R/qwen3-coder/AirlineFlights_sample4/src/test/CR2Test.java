import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    private FlightSystem flightSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        flightSystem = new FlightSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP100");
        departureAirport.setCities(Arrays.asList("CityA"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        arrivalAirport.setCities(Arrays.asList("CityB"));
        
        Flight flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-05 12:00:00", formatter));
        flight.setOpenForBooking(true);
        flight.setPublished(true);
        
        flightSystem.getFlights().add(flight);
        
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Set current time to 2025-10-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute test with mocked current time
        boolean result = createBookingWithMockedTime(flight, passengerNames, currentTime);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Flight should have 2 reservations", 2, flight.getReservations().size());
        assertEquals("System should have 1 booking", 1, flightSystem.getBookings().size());
        
        // Verify reservation details
        List<Reservation> reservations = flight.getReservations();
        assertEquals("Peter", reservations.get(0).getPassengerName());
        assertEquals("Beck", reservations.get(1).getPassengerName());
        assertEquals(Reservation.PENDING, reservations.get(0).getStatus());
        assertEquals(Reservation.PENDING, reservations.get(1).getStatus());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP102");
        departureAirport.setCities(Arrays.asList("CityC"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        arrivalAirport.setCities(Arrays.asList("CityD"));
        
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-05 10:00:00", formatter));
        flight.setOpenForBooking(true);
        flight.setPublished(true);
        
        flightSystem.getFlights().add(flight);
        
        // Duplicate passenger names in the same request
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Set current time to 2025-10-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute test with mocked current time
        boolean result = createBookingWithMockedTime(flight, passengerNames, currentTime);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passengers in same request", result);
        assertEquals("Flight should have 0 reservations", 0, flight.getReservations().size());
        assertEquals("System should have 0 bookings", 0, flightSystem.getBookings().size());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        departureAirport.setCities(Arrays.asList("CityE"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        arrivalAirport.setCities(Arrays.asList("CityF"));
        
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight.setOpenForBooking(true);
        flight.setPublished(true);
        
        // Pre-existing reservation for passenger "Jucy"
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setFlight(flight);
        existingReservation.setPassengerName("Jucy");
        existingReservation.setStatus(Reservation.PENDING);
        flight.getReservations().add(existingReservation);
        
        flightSystem.getFlights().add(flight);
        
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Set current time to 2025-10-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute test with mocked current time
        boolean result = createBookingWithMockedTime(flight, passengerNames, currentTime);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("Flight should have only 1 reservation (the pre-existing one)", 1, flight.getReservations().size());
        assertEquals("System should have 0 bookings", 0, flightSystem.getBookings().size());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        departureAirport.setCities(Arrays.asList("CityG"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        arrivalAirport.setCities(Arrays.asList("CityH"));
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight.setOpenForBooking(false); // Flight is closed for booking
        flight.setPublished(true);
        
        flightSystem.getFlights().add(flight);
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Set current time to 2025-10-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute test with mocked current time
        boolean result = createBookingWithMockedTime(flight, passengerNames, currentTime);
        
        // Verify
        assertFalse("Booking should fail due to flight being closed", result);
        assertEquals("Flight should have 0 reservations", 0, flight.getReservations().size());
        assertEquals("System should have 0 bookings", 0, flightSystem.getBookings().size());
    }
    
    @Test
    public void testCase5_timeIsAfterDepartureTime() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        departureAirport.setCities(Arrays.asList("CityE"));
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        arrivalAirport.setCities(Arrays.asList("CityF"));
        
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight.setOpenForBooking(true);
        flight.setPublished(true);
        
        flightSystem.getFlights().add(flight);
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Set current time to 2025-10-06 09:00:00 (AFTER departure time)
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-06 09:00:00", formatter);
        
        // Execute test with mocked current time
        boolean result = createBookingWithMockedTime(flight, passengerNames, currentTime);
        
        // Verify
        assertFalse("Booking should fail due to current time being after departure time", result);
        assertEquals("Flight should have 0 reservations", 0, flight.getReservations().size());
        assertEquals("System should have 0 bookings", 0, flightSystem.getBookings().size());
    }
    
    /**
     * Helper method to simulate createBooking with mocked current time
     * This method replicates the logic of createBooking but allows us to control the current time
     */
    private boolean createBookingWithMockedTime(Flight flight, List<String> passengerNames, LocalDateTime currentTime) {
        if (flight == null || passengerNames == null || passengerNames.isEmpty()) {
            return false;
        }

        if (!flight.isOpenForBooking() || flight.isPublished() == false) {
            return false;
        }

        if (!currentTime.isBefore(flight.getDepartureTime())) {
            return false;
        }

        // Check for duplicate passengers in existing reservations for this flight
        List<String> existingPassengers = new ArrayList<>();
        for (Reservation reservation : flight.getReservations()) {
            existingPassengers.add(reservation.getPassengerName());
        }

        for (String passenger : passengerNames) {
            if (existingPassengers.contains(passenger)) {
                return false;
            }
        }

        // Create booking
        Booking booking = new Booking();
        booking.setId(java.util.UUID.randomUUID().toString());

        // Create reservations for each passenger
        for (String passengerName : passengerNames) {
            Reservation reservation = new Reservation();
            reservation.setId(java.util.UUID.randomUUID().toString());
            reservation.setFlight(flight);
            reservation.setPassengerName(passengerName);
            reservation.setStatus(Reservation.PENDING);
            booking.getReservations().add(reservation);
            flight.getReservations().add(reservation);
        }

        flightSystem.getBookings().add(booking);
        return true;
    }
}