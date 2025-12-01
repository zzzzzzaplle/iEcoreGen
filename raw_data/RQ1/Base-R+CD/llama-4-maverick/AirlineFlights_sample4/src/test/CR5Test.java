import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
        // Setup
        Flight flight = createFlight("F501", true, "2024-12-01 10:00:00", "2024-12-01 12:00:00");
        
        // Create confirmed reservations
        createConfirmedReservation(flight, "R501-1");
        createConfirmedReservation(flight, "R501-2");
        createConfirmedReservation(flight, "R501-3");
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1", containsReservationWithId(result, "R501-1"));
        assertTrue("Should contain R501-2", containsReservationWithId(result, "R501-2"));
        assertTrue("Should contain R501-3", containsReservationWithId(result, "R501-3"));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup
        Flight flight = createFlight("F502", true, "2024-12-02 10:00:00", "2024-12-02 12:00:00");
        
        // Create pending reservations
        createPendingReservation(flight, "R502-1");
        createPendingReservation(flight, "R502-2");
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list when no confirmed reservations exist", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup
        Flight flight = createFlight("F503", false, "2024-12-03 10:00:00", "2024-12-03 12:00:00");
        
        // Create confirmed reservation
        createConfirmedReservation(flight, "R503-1");
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return empty list when flight is closed", 0, result.size());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup
        Flight flight504 = createFlight("F504", true, "2024-12-04 10:00:00", "2024-12-04 12:00:00");
        Flight flight505 = createFlight("F505", true, "2024-12-05 10:00:00", "2024-12-05 12:00:00");
        
        airline.addFlight(flight504);
        airline.addFlight(flight505);
        
        // Execute - Search for non-existent flight in airline's flights
        Flight unknownFlight = findFlightById("FX999");
        
        // Verify
        assertNull("Unknown flight should not be found", unknownFlight);
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup
        Flight flight = createFlight("F504", true, "2024-12-06 10:00:00", "2024-12-06 12:00:00");
        
        // Create reservations with mixed statuses
        createConfirmedReservation(flight, "R504-A");
        createConfirmedReservation(flight, "R504-B");
        createCanceledReservation(flight, "R504-C");
        createPendingReservation(flight, "R504-D");
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return only 2 confirmed reservations", 2, result.size());
        assertTrue("Should contain R504-A", containsReservationWithId(result, "R504-A"));
        assertTrue("Should contain R504-B", containsReservationWithId(result, "R504-B"));
        assertFalse("Should not contain canceled reservation R504-C", containsReservationWithId(result, "R504-C"));
        assertFalse("Should not contain pending reservation R504-D", containsReservationWithId(result, "R504-D"));
    }
    
    // Helper methods
    private Flight createFlight(String id, boolean openForBooking, String departureTimeStr, String arrivalTimeStr) throws Exception {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setOpenForBooking(openForBooking);
        flight.setDepartureTime(dateFormat.parse(departureTimeStr));
        flight.setArrivalTime(dateFormat.parse(arrivalTimeStr));
        flight.setReservations(new ArrayList<>());
        return flight;
    }
    
    private void createConfirmedReservation(Flight flight, String reservationId) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setFlight(flight);
        flight.getReservations().add(reservation);
    }
    
    private void createPendingReservation(Flight flight, String reservationId) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setFlight(flight);
        flight.getReservations().add(reservation);
    }
    
    private void createCanceledReservation(Flight flight, String reservationId) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus(ReservationStatus.CANCELED);
        reservation.setFlight(flight);
        flight.getReservations().add(reservation);
    }
    
    private boolean containsReservationWithId(List<Reservation> reservations, String id) {
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    private Flight findFlightById(String flightId) {
        for (Flight flight : airline.getFlights()) {
            if (flight.getId().equals(flightId)) {
                return flight;
            }
        }
        return null;
    }
}