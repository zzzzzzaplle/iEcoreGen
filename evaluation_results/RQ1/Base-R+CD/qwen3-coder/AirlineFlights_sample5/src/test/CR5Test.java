import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR5Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() throws Exception {
        // Setup: Airline AL21; flight F501 openForBooking = True
        Airline airline = new Airline();
        Flight flight = new Flight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        
        // Setup reservations R501-1, R501-2, R501-3 status = CONFIRMED
        Reservation res1 = new Reservation();
        res1.setId("R501-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        res1.setFlight(flight);
        
        Reservation res2 = new Reservation();
        res2.setId("R501-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        res2.setFlight(flight);
        
        Reservation res3 = new Reservation();
        res3.setId("R501-3");
        res3.setStatus(ReservationStatus.CONFIRMED);
        res3.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        reservations.add(res2);
        reservations.add(res3);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed list for flight F501
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, confirmedReservations.size());
        assertTrue(confirmedReservations.contains(res1));
        assertTrue(confirmedReservations.contains(res2));
        assertTrue(confirmedReservations.contains(res3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup: Airline AL22; flight F502 openForBooking = True
        Airline airline = new Airline();
        Flight flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        
        // Setup: Two reservations status = PENDING
        Reservation res1 = new Reservation();
        res1.setId("R502-1");
        res1.setStatus(ReservationStatus.PENDING);
        res1.setFlight(flight);
        
        Reservation res2 = new Reservation();
        res2.setId("R502-2");
        res2.setStatus(ReservationStatus.PENDING);
        res2.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        reservations.add(res2);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed list for flight F502
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Expected Output: []
        assertEquals(0, confirmedReservations.size());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup: Airline AL23; flight F503 openForBooking = False
        Airline airline = new Airline();
        Flight flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false); // Flight closed
        
        // Setup: One reservation status = CONFIRMED
        Reservation res1 = new Reservation();
        res1.setId("R503-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        res1.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(res1);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed list for flight F503
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Expected Output: []
        assertEquals(0, confirmedReservations.size());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup: Airline AL24 holds flights F504, F505 only
        Airline airline = new Airline();
        Flight flight1 = new Flight();
        flight1.setId("F504");
        flight1.setOpenForBooking(true);
        
        Flight flight2 = new Flight();
        flight2.setId("F505");
        flight2.setOpenForBooking(true);
        
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        
        // Create a flight but don't add it to the airline (FX999)
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setOpenForBooking(true);
        
        // Execute: Retrieve confirmed list for flight FX999
        List<Reservation> confirmedReservations = unknownFlight.getConfirmedReservations();
        
        // Expected Output: []
        assertEquals(0, confirmedReservations.size());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup: Airline AL25; flight F504 openForBooking = True
        Airline airline = new Airline();
        Flight flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        
        // Setup reservations: R504-A, R504-B status = CONFIRMED;
        // R504-C status = CANCELLED; R504-D status = PENDING
        Reservation resA = new Reservation();
        resA.setId("R504-A");
        resA.setStatus(ReservationStatus.CONFIRMED);
        resA.setFlight(flight);
        
        Reservation resB = new Reservation();
        resB.setId("R504-B");
        resB.setStatus(ReservationStatus.CONFIRMED);
        resB.setFlight(flight);
        
        Reservation resC = new Reservation();
        resC.setId("R504-C");
        resC.setStatus(ReservationStatus.CANCELLED);
        resC.setFlight(flight);
        
        Reservation resD = new Reservation();
        resD.setId("R504-D");
        resD.setStatus(ReservationStatus.PENDING);
        resD.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(resA);
        reservations.add(resB);
        reservations.add(resC);
        reservations.add(resD);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed list for flight F504
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Expected Output: R504-A, R504-B
        assertEquals(2, confirmedReservations.size());
        assertTrue(confirmedReservations.contains(resA));
        assertTrue(confirmedReservations.contains(resB));
        assertFalse(confirmedReservations.contains(resC));
        assertFalse(confirmedReservations.contains(resD));
    }
}