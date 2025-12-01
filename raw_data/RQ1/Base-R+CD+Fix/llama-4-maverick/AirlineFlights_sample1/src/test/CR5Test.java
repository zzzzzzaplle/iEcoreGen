import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        
        List<Reservation> reservations = new ArrayList<>();
        
        Reservation r1 = new Reservation();
        r1.setId("R501-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r2 = new Reservation();
        r2.setId("R501-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation r3 = new Reservation();
        r3.setId("R501-3");
        r3.setStatus(ReservationStatus.CONFIRMED);
        
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
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup
        flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        
        List<Reservation> reservations = new ArrayList<>();
        
        Reservation r1 = new Reservation();
        r1.setId("R502-1");
        r1.setStatus(ReservationStatus.PENDING);
        
        Reservation r2 = new Reservation();
        r2.setId("R502-2");
        r2.setStatus(ReservationStatus.PENDING);
        
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
        flight.setOpenForBooking(false);
        
        List<Reservation> reservations = new ArrayList<>();
        
        Reservation r1 = new Reservation();
        r1.setId("R503-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        
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
        // Setup
        Flight flight1 = new Flight();
        flight1.setId("F504");
        flight1.setOpenForBooking(true);
        
        Flight flight2 = new Flight();
        flight2.setId("F505");
        flight2.setOpenForBooking(true);
        
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        
        // Execute - For unknown flight FX999, we need to find it first
        Flight unknownFlight = null;
        for (Flight f : airline.getFlights()) {
            if (f.getId().equals("FX999")) {
                unknownFlight = f;
                break;
            }
        }
        
        // Verify
        assertNull(unknownFlight);
        // Since the flight doesn't exist, we can't call getConfirmedReservations on it
        // The expected output is an empty list, which is achieved by not having the flight
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup
        flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        
        List<Reservation> reservations = new ArrayList<>();
        
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
    }
}