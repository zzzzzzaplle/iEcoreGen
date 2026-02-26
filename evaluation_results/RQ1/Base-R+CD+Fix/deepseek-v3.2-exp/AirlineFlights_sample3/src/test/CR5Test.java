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
    public void testCase1_FlightWithThreeConfirmations() throws Exception {
        // Setup: Airline AL21; flight F501 openForBooking = True
        Flight flight = new Flight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        // Setup: Reservations R501-1, R501-2, R501-3 status = CONFIRMED
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
        
        flight.getReservations().add(res1);
        flight.getReservations().add(res2);
        flight.getReservations().add(res3);
        
        // Input: Retrieve confirmed list for flight F501
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, result.size());
        assertTrue(result.contains(res1));
        assertTrue(result.contains(res2));
        assertTrue(result.contains(res3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup: Airline AL22; flight F502 openForBooking = True
        Flight flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        // Setup: Two reservations status = PENDING
        Reservation res1 = new Reservation();
        res1.setId("R502-1");
        res1.setStatus(ReservationStatus.PENDING);
        res1.setFlight(flight);
        
        Reservation res2 = new Reservation();
        res2.setId("R502-2");
        res2.setStatus(ReservationStatus.PENDING);
        res2.setFlight(flight);
        
        flight.getReservations().add(res1);
        flight.getReservations().add(res2);
        
        // Input: Retrieve confirmed list for flight F502
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup: Airline AL23; flight F503 openForBooking = False
        Flight flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false);
        airline.addFlight(flight);
        
        // Setup: One reservation status = CONFIRMED
        Reservation res = new Reservation();
        res.setId("R503-1");
        res.setStatus(ReservationStatus.CONFIRMED);
        res.setFlight(flight);
        
        flight.getReservations().add(res);
        
        // Input: Retrieve confirmed list for flight F503
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Expected Output: []
        // Note: Even though flight is closed, the method getConfirmedReservations()
        // filters by status only, not by flight booking status
        // Based on the Flight.getConfirmedReservations() implementation, it should return the confirmed reservation
        // However, test specification says Expected Output: []
        // This suggests a potential discrepancy between specification and implementation
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup: Airline AL24 holds flights F504, F505 only
        Flight flight1 = new Flight();
        flight1.setId("F504");
        flight1.setOpenForBooking(true);
        airline.addFlight(flight1);
        
        Flight flight2 = new Flight();
        flight2.setId("F505");
        flight2.setOpenForBooking(true);
        airline.addFlight(flight2);
        
        // Create a flight object for the unknown flight ID
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setOpenForBooking(true);
        
        // Input: Retrieve confirmed list for flight FX999
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup: Airline AL25; flight F504 openForBooking = True
        Flight flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        airline.addFlight(flight);
        
        // Setup: Reservations: R504-A, R504-B status = CONFIRMED;
        // R504-C status = CANCELED; R504-D status = PENDING
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
        resC.setStatus(ReservationStatus.CANCELED);
        resC.setFlight(flight);
        
        Reservation resD = new Reservation();
        resD.setId("R504-D");
        resD.setStatus(ReservationStatus.PENDING);
        resD.setFlight(flight);
        
        flight.getReservations().add(resA);
        flight.getReservations().add(resB);
        flight.getReservations().add(resC);
        flight.getReservations().add(resD);
        
        // Input: Retrieve confirmed list for flight F504
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Expected Output: R504-A, R504-B
        assertEquals(2, result.size());
        assertTrue(result.contains(resA));
        assertTrue(result.contains(resB));
        assertFalse(result.contains(resC));
        assertFalse(result.contains(resD));
    }
}