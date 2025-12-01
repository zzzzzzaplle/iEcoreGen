import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() throws Exception {
        // Setup: Airline AL21; flight F501 openForBooking = True
        Flight flight = createFlight("F501", true);
        airline.addFlight(flight);
        
        // Setup: Reservations R501-1, R501-2, R501-3 status = CONFIRMED
        addConfirmedReservation(flight, "R501-1");
        addConfirmedReservation(flight, "R501-2");
        addConfirmedReservation(flight, "R501-3");
        
        // Execute: Retrieve confirmed list for flight F501
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, result.size());
        assertContainsReservationId(result, "R501-1");
        assertContainsReservationId(result, "R501-2");
        assertContainsReservationId(result, "R501-3");
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup: Airline AL22; flight F502 openForBooking = True
        Flight flight = createFlight("F502", true);
        airline.addFlight(flight);
        
        // Setup: Two reservations status = PENDING
        addPendingReservation(flight, "R502-1");
        addPendingReservation(flight, "R502-2");
        
        // Execute: Retrieve confirmed list for flight F502
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup: Airline AL23; flight F503 openForBooking = False
        Flight flight = createFlight("F503", false);
        airline.addFlight(flight);
        
        // Setup: One reservation status = CONFIRMED
        addConfirmedReservation(flight, "R503-1");
        
        // Execute: Retrieve confirmed list for flight F503
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup: Airline AL24 holds flights F504, F505 only
        Flight flight504 = createFlight("F504", true);
        Flight flight505 = createFlight("F505", true);
        airline.addFlight(flight504);
        airline.addFlight(flight505);
        
        // Execute: Retrieve confirmed list for flight FX999 (unknown flight)
        // Note: Since we're testing getConfirmedReservations() on Flight object,
        // we need to create a separate flight for FX999 or handle it differently
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setOpenForBooking(true);
        
        // Execute: Retrieve confirmed list for flight FX999
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup: Airline AL25; flight F504 openForBooking = True
        Flight flight = createFlight("F504", true);
        airline.addFlight(flight);
        
        // Setup: Reservations: R504-A, R504-B status = CONFIRMED;
        addConfirmedReservation(flight, "R504-A");
        addConfirmedReservation(flight, "R504-B");
        
        // Setup: R504-C status = CANCELED; R504-D status = PENDING
        addCanceledReservation(flight, "R504-C");
        addPendingReservation(flight, "R504-D");
        
        // Execute: Retrieve confirmed list for flight F504
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: R504-A, R504-B
        assertEquals(2, result.size());
        assertContainsReservationId(result, "R504-A");
        assertContainsReservationId(result, "R504-B");
        assertDoesNotContainReservationId(result, "R504-C");
        assertDoesNotContainReservationId(result, "R504-D");
    }
    
    // Helper methods
    private Flight createFlight(String id, boolean openForBooking) throws Exception {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setOpenForBooking(openForBooking);
        
        // Set future departure and arrival times for valid flight
        Date futureDate = dateFormat.parse("2024-12-31 12:00:00");
        flight.setDepartureTime(futureDate);
        flight.setArrivalTime(dateFormat.parse("2024-12-31 14:00:00"));
        
        return flight;
    }
    
    private void addConfirmedReservation(Flight flight, String reservationId) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setFlight(flight);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger_" + reservationId);
        reservation.setPassenger(passenger);
        
        flight.getReservations().add(reservation);
    }
    
    private void addPendingReservation(Flight flight, String reservationId) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setFlight(flight);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger_" + reservationId);
        reservation.setPassenger(passenger);
        
        flight.getReservations().add(reservation);
    }
    
    private void addCanceledReservation(Flight flight, String reservationId) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus(ReservationStatus.CANCELED);
        reservation.setFlight(flight);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger_" + reservationId);
        reservation.setPassenger(passenger);
        
        flight.getReservations().add(reservation);
    }
    
    private void assertContainsReservationId(List<Reservation> reservations, String expectedId) {
        boolean found = false;
        for (Reservation reservation : reservations) {
            if (expectedId.equals(reservation.getId())) {
                found = true;
                break;
            }
        }
        assertTrue("Expected reservation with ID " + expectedId + " not found", found);
    }
    
    private void assertDoesNotContainReservationId(List<Reservation> reservations, String unexpectedId) {
        for (Reservation reservation : reservations) {
            if (unexpectedId.equals(reservation.getId())) {
                fail("Unexpected reservation with ID " + unexpectedId + " found in results");
            }
        }
    }
}