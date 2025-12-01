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
        flight = new Flight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        
        // Create confirmed reservations
        Reservation r1 = new Reservation();
        r1.setId("R501-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r2 = new Reservation();
        r2.setId("R501-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r3 = new Reservation();
        r3.setId("R501-3");
        r3.setStatus(ReservationStatus.CONFIRMED);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(3, result.size());
        assertEquals("R501-1", result.get(0).getId());
        assertEquals("R501-2", result.get(1).getId());
        assertEquals("R501-3", result.get(2).getId());
        assertEquals(ReservationStatus.CONFIRMED, result.get(0).getStatus());
        assertEquals(ReservationStatus.CONFIRMED, result.get(1).getStatus());
        assertEquals(ReservationStatus.CONFIRMED, result.get(2).getStatus());
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup
        flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        
        // Create pending reservations
        Reservation r1 = new Reservation();
        r1.setId("R502-1");
        r1.setStatus(ReservationStatus.PENDING);
        
        Reservation r2 = new Reservation();
        r2.setId("R502-2");
        r2.setStatus(ReservationStatus.PENDING);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup
        flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false); // Flight closed
        
        // Create confirmed reservation
        Reservation r1 = new Reservation();
        r1.setId("R503-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup - Create airline with different flights
        Flight f504 = new Flight();
        f504.setId("F504");
        f504.setOpenForBooking(true);
        
        Flight f505 = new Flight();
        f505.setId("F505");
        f505.setOpenForBooking(true);
        
        airline.addFlight(f504);
        airline.addFlight(f505);
        
        // Execute - Try to get confirmed reservations for non-existent flight
        Flight unknownFlight = null;
        for (Flight f : airline.getFlights()) {
            if (f.getId().equals("FX999")) {
                unknownFlight = f;
                break;
            }
        }
        
        // Verify
        assertNull(unknownFlight);
        // The method getConfirmedReservations() is called on Flight objects,
        // so if flight doesn't exist, we can't call the method
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup
        flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        
        // Create mixed status reservations
        Reservation rA = new Reservation();
        rA.setId("R504-A");
        rA.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation rB = new Reservation();
        rB.setId("R504-B");
        rB.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation rC = new Reservation();
        rC.setId("R504-C");
        rC.setStatus(ReservationStatus.CANCELED);
        
        Reservation rD = new Reservation();
        rD.setId("R504-D");
        rD.setStatus(ReservationStatus.PENDING);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(rA);
        reservations.add(rB);
        reservations.add(rC);
        reservations.add(rD);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(2, result.size());
        assertEquals("R504-A", result.get(0).getId());
        assertEquals("R504-B", result.get(1).getId());
        assertEquals(ReservationStatus.CONFIRMED, result.get(0).getStatus());
        assertEquals(ReservationStatus.CONFIRMED, result.get(1).getStatus());
    }
}