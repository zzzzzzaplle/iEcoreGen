import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {

    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_twoNewPassengersSucceed() throws ParseException {
        // Setup
        Airline airline = new Airline();

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

        Customer customer = new Customer();

        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");

        List<String> passengerNames = Arrays.asList("Peter", "Beck");

        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);

        // Verify
        assertTrue(result);
        assertEquals(1, customer.getBookings().size());
        assertEquals(2, customer.getBookings().get(0).getReservations().size());
        
        List<Reservation> reservations = customer.getBookings().get(0).getReservations();
        Set<String> passengerNamesInReservations = new HashSet<>();
        for (Reservation reservation : reservations) {
            assertEquals(ReservationStatus.PENDING, reservation.getStatus());
            assertNotNull(reservation.getId());
            passengerNamesInReservations.add(reservation.getPassenger().getName());
        }
        
        assertTrue(passengerNamesInReservations.contains("Peter"));
        assertTrue(passengerNamesInReservations.contains("Beck"));
    }

    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws ParseException {
        // Setup
        Airline airline = new Airline();

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

        Customer customer = new Customer();

        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");

        List<String> passengerNames = Arrays.asList("Alice", "Alice");

        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);

        // Verify
        assertFalse(result);
        assertTrue(customer.getBookings().isEmpty());
    }

    @Test
    public void testCase3_passengerAlreadyBookedEarlier() throws ParseException {
        // Setup
        Airline airline = new Airline();

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

        Customer customer = new Customer();

        // Create pre-existing booking
        Booking oldBooking = new Booking();
        oldBooking.setCustomer(customer);

        Passenger passenger = new Passenger();
        passenger.setName("Jucy");

        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setStatus(ReservationStatus.PENDING);
        existingReservation.setPassenger(passenger);
        existingReservation.setFlight(flight);

        oldBooking.getReservations().add(existingReservation);
        flight.getReservations().add(existingReservation);
        customer.getBookings().add(oldBooking);

        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");

        List<String> passengerNames = Arrays.asList("Jucy");

        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);

        // Verify
        assertFalse(result);
        assertEquals(1, customer.getBookings().size()); // Only the original booking should exist
        assertEquals(1, flight.getReservations().size()); // Only the original reservation should exist
    }

    @Test
    public void testCase4_flightClosedBlocksBooking() throws ParseException {
        // Setup
        Airline airline = new Airline();

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

        Customer customer = new Customer();

        Date currentTime = dateFormat.parse("2025-10-01 09:00:00");

        List<String> passengerNames = Arrays.asList("Ruby");

        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);

        // Verify
        assertFalse(result);
        assertTrue(customer.getBookings().isEmpty());
    }

    @Test
    public void testCase5_timeIsAfterDepartureTime() throws ParseException {
        // Setup
        Airline airline = new Airline();

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

        Customer customer = new Customer();

        Date currentTime = dateFormat.parse("2025-10-06 09:00:00"); // After departure time

        List<String> passengerNames = Arrays.asList("Ruby");

        // Execute
        boolean result = customer.addBooking(flight, currentTime, passengerNames);

        // Verify
        assertFalse(result);
        assertTrue(customer.getBookings().isEmpty());
    }
}