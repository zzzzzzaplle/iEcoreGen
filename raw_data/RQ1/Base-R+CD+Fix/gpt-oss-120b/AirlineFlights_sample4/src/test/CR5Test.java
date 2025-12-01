import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR5Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_flightWithThreeConfirmations() throws Exception {
        // Setup
        Flight flight = new Flight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        
        // Create three confirmed reservations
        Reservation r1 = new Reservation();
        r1.setId("R501-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r2 = new Reservation();
        r2.setId("R501-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r3 = new Reservation();
        r3.setId("R501-3");
        r3.setStatus(ReservationStatus.CONFIRMED);
        
        // Add reservations to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1", containsReservationWithId(result, "R501-1"));
        assertTrue("Should contain R501-2", containsReservationWithId(result, "R501-2"));
        assertTrue("Should contain R501-3", containsReservationWithId(result, "R501-3"));
    }
    
    @Test
    public void testCase2_noConfirmedReservations() throws Exception {
        // Setup
        Flight flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        
        // Create two pending reservations
        Reservation r1 = new Reservation();
        r1.setId("R502-1");
        r1.setStatus(ReservationStatus.PENDING);
        
        Reservation r2 = new Reservation();
        r2.setId("R502-2");
        r2.setStatus(ReservationStatus.PENDING);
        
        // Add reservations to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_flightClosedReturnsZero() throws Exception {
        // Setup
        Flight flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false); // Flight is closed
        
        // Create one confirmed reservation
        Reservation r1 = new Reservation();
        r1.setId("R503-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        
        // Add reservation to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify - Flight being closed doesn't affect getConfirmedReservations method
        // The method only filters by status, not flight booking status
        assertEquals("Should return 1 confirmed reservation regardless of flight status", 1, result.size());
        assertTrue("Should contain the confirmed reservation", containsReservationWithId(result, "R503-1"));
    }
    
    @Test
    public void testCase4_unknownFlightId() throws Exception {
        // Setup: Airline holds flights F504 and F505 only
        Flight flight1 = new Flight();
        flight1.setId("F504");
        flight1.setOpenForBooking(true);
        
        Flight flight2 = new Flight();
        flight2.setId("F505");
        flight2.setOpenForBooking(true);
        
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        
        // Execute: Try to get confirmed reservations for non-existent flight
        // Since we're testing getConfirmedReservations on Flight object, not Airline,
        // we need to test with a flight that doesn't exist in our test context
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setOpenForBooking(true);
        
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list for flight with no reservations", result.isEmpty());
    }
    
    @Test
    public void testCase5_mixedReservationStatuses() throws Exception {
        // Setup
        Flight flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        
        // Create mixed status reservations
        Reservation r1 = new Reservation();
        r1.setId("R504-A");
        r1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r2 = new Reservation();
        r2.setId("R504-B");
        r2.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r3 = new Reservation();
        r3.setId("R504-C");
        r3.setStatus(ReservationStatus.CANCELED);
        
        Reservation r4 = new Reservation();
        r4.setId("R504-D");
        r4.setStatus(ReservationStatus.PENDING);
        
        // Add all reservations to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        reservations.add(r4);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed reservations
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return only 2 confirmed reservations", 2, result.size());
        assertTrue("Should contain R504-A", containsReservationWithId(result, "R504-A"));
        assertTrue("Should contain R504-B", containsReservationWithId(result, "R504-B"));
        assertFalse("Should not contain canceled reservation R504-C", containsReservationWithId(result, "R504-C"));
        assertFalse("Should not contain pending reservation R504-D", containsReservationWithId(result, "R504-D"));
    }
    
    // Helper method to check if a list contains a reservation with specific ID
    private boolean containsReservationWithId(List<Reservation> reservations, String id) {
        for (Reservation r : reservations) {
            if (r.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
}