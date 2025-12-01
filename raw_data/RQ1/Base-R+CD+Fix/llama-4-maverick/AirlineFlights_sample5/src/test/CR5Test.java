import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CR5Test {
    
    private Airline airline;
    private Flight flight;
    
    @Before
    public void setUp() {
        airline = new Airline();
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup
        Flight f501 = new Flight();
        f501.setId("F501");
        f501.setOpenForBooking(true);
        
        List<Reservation> reservations = new ArrayList<>();
        
        // Create confirmed reservations
        Reservation r5011 = createConfirmedReservation("R501-1", f501);
        Reservation r5012 = createConfirmedReservation("R501-2", f501);
        Reservation r5013 = createConfirmedReservation("R501-3", f501);
        
        reservations.add(r5011);
        reservations.add(r5012);
        reservations.add(r5013);
        
        f501.setReservations(reservations);
        airline.addFlight(f501);
        
        // Execute
        List<Reservation> result = f501.getConfirmedReservations();
        
        // Verify
        assertEquals(3, result.size());
        assertTrue(result.contains(r5011));
        assertTrue(result.contains(r5012));
        assertTrue(result.contains(r5013));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup
        Flight f502 = new Flight();
        f502.setId("F502");
        f502.setOpenForBooking(true);
        
        List<Reservation> reservations = new ArrayList<>();
        
        // Create pending reservations
        Reservation r5021 = createReservationWithStatus("R502-1", f502, ReservationStatus.PENDING);
        Reservation r5022 = createReservationWithStatus("R502-2", f502, ReservationStatus.PENDING);
        
        reservations.add(r5021);
        reservations.add(r5022);
        
        f502.setReservations(reservations);
        airline.addFlight(f502);
        
        // Execute
        List<Reservation> result = f502.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup
        Flight f503 = new Flight();
        f503.setId("F503");
        f503.setOpenForBooking(false); // Flight closed
        
        List<Reservation> reservations = new ArrayList<>();
        
        // Create confirmed reservation (but flight is closed)
        Reservation r5031 = createConfirmedReservation("R503-1", f503);
        
        reservations.add(r5031);
        
        f503.setReservations(reservations);
        airline.addFlight(f503);
        
        // Execute
        List<Reservation> result = f503.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup
        Flight f504 = new Flight();
        f504.setId("F504");
        Flight f505 = new Flight();
        f505.setId("F505");
        
        airline.addFlight(f504);
        airline.addFlight(f505);
        
        // Execute - Try to get flight with unknown ID
        Flight unknownFlight = null;
        for (Flight f : airline.getFlights()) {
            if (f.getId().equals("FX999")) {
                unknownFlight = f;
                break;
            }
        }
        
        // Verify
        assertNull(unknownFlight);
        
        // If we had a flight with unknown ID, we would test getConfirmedReservations()
        // But since we can't get the flight, the result is implicitly empty
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup
        Flight f504 = new Flight();
        f504.setId("F504");
        f504.setOpenForBooking(true);
        
        List<Reservation> reservations = new ArrayList<>();
        
        // Create mixed status reservations
        Reservation r504A = createConfirmedReservation("R504-A", f504);
        Reservation r504B = createConfirmedReservation("R504-B", f504);
        Reservation r504C = createReservationWithStatus("R504-C", f504, ReservationStatus.CANCELED);
        Reservation r504D = createReservationWithStatus("R504-D", f504, ReservationStatus.PENDING);
        
        reservations.add(r504A);
        reservations.add(r504B);
        reservations.add(r504C);
        reservations.add(r504D);
        
        f504.setReservations(reservations);
        airline.addFlight(f504);
        
        // Execute
        List<Reservation> result = f504.getConfirmedReservations();
        
        // Verify
        assertEquals(2, result.size());
        assertTrue(result.contains(r504A));
        assertTrue(result.contains(r504B));
        assertFalse(result.contains(r504C));
        assertFalse(result.contains(r504D));
    }
    
    // Helper method to create a confirmed reservation
    private Reservation createConfirmedReservation(String id, Flight flight) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger_" + id);
        reservation.setPassenger(passenger);
        
        reservation.setFlight(flight);
        return reservation;
    }
    
    // Helper method to create a reservation with specific status
    private Reservation createReservationWithStatus(String id, Flight flight, ReservationStatus status) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatus(status);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger_" + id);
        reservation.setPassenger(passenger);
        
        reservation.setFlight(flight);
        return reservation;
    }
}