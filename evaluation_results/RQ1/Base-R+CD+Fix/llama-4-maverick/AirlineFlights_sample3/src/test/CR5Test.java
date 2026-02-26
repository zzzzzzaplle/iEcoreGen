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
    public void testCase1_flightWithThreeConfirmations() throws Exception {
        // Setup: Airline AL21; flight F501 openForBooking = True
        Flight flight = new Flight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        // Setup: Reservations R501-1, R501-2, R501-3 status = CONFIRMED
        Reservation r1 = createReservation("R501-1", ReservationStatus.CONFIRMED, flight);
        Reservation r2 = createReservation("R501-2", ReservationStatus.CONFIRMED, flight);
        Reservation r3 = createReservation("R501-3", ReservationStatus.CONFIRMED, flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        flight.setReservations(reservations);
        
        // Execute: Retrieve confirmed list for flight F501
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, result.size());
        assertTrue(result.contains(r1));
        assertTrue(result.contains(r2));
        assertTrue(result.contains(r3));
    }
    
    @Test
    public void testCase2_noConfirmedReservations() throws Exception {
        // Setup: Airline AL22; flight F502 openForBooking = True
        Flight flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        // Setup: Two reservations status = PENDING
        Reservation r1 = createReservation("R502-1", ReservationStatus.PENDING, flight);
        Reservation r2 = createReservation("R502-2", ReservationStatus.PENDING, flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        flight.setReservations(reservations);
        
        // Execute: Retrieve confirmed list for flight F502
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_flightClosedReturnsZero() throws Exception {
        // Setup: Airline AL23; flight F503 openForBooking = False
        Flight flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false);
        airline.addFlight(flight);
        
        // Setup: One reservation status = CONFIRMED
        Reservation r1 = createReservation("R503-1", ReservationStatus.CONFIRMED, flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        flight.setReservations(reservations);
        
        // Execute: Retrieve confirmed list for flight F503
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        // Note: The getConfirmedReservations() method only checks status, not openForBooking
        // So this test might not match the expected behavior described in the requirement
        // The requirement says "provided the flight is currently open for booking"
        // but the Flight.getConfirmedReservations() method doesn't check this condition
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_unknownFlightId() throws Exception {
        // Setup: Airline AL24 holds flights F504, F505 only
        Flight flight1 = new Flight();
        flight1.setId("F504");
        flight1.setOpenForBooking(true);
        airline.addFlight(flight1);
        
        Flight flight2 = new Flight();
        flight2.setId("F505");
        flight2.setOpenForBooking(true);
        airline.addFlight(flight2);
        
        // Execute: Retrieve confirmed list for flight FX999 (unknown flight)
        // Since we need to get a flight by ID first, we'll search through airline's flights
        Flight targetFlight = null;
        for (Flight f : airline.getFlights()) {
            if (f.getId().equals("FX999")) {
                targetFlight = f;
                break;
            }
        }
        
        // Verify: Expected Output: []
        assertNull(targetFlight); // Flight not found
    }
    
    @Test
    public void testCase5_mixedReservationStatuses() throws Exception {
        // Setup: Airline AL25; flight F504 openForBooking = True
        Flight flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        // Setup: Reservations with mixed statuses
        Reservation rA = createReservation("R504-A", ReservationStatus.CONFIRMED, flight);
        Reservation rB = createReservation("R504-B", ReservationStatus.CONFIRMED, flight);
        Reservation rC = createReservation("R504-C", ReservationStatus.CANCELED, flight);
        Reservation rD = createReservation("R504-D", ReservationStatus.PENDING, flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(rA);
        reservations.add(rB);
        reservations.add(rC);
        reservations.add(rD);
        flight.setReservations(reservations);
        
        // Execute: Retrieve confirmed list for flight F504
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: R504-A, R504-B
        assertEquals(2, result.size());
        assertTrue(result.contains(rA));
        assertTrue(result.contains(rB));
        assertFalse(result.contains(rC));
        assertFalse(result.contains(rD));
    }
    
    // Helper method to create a reservation with specified status
    private Reservation createReservation(String id, ReservationStatus status, Flight flight) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatus(status);
        reservation.setFlight(flight);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger_" + id);
        reservation.setPassenger(passenger);
        
        return reservation;
    }
}