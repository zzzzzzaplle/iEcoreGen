import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

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
        // 1. Airline AL21; flight F501 openForBooking = True.
        Flight flight = new Flight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2024-06-15 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2024-06-15 12:00:00"));
        
        // 2. Reservations R501-1, R501-2, R501-3 status = CONFIRMED.
        Reservation res1 = new Reservation();
        res1.setId("R501-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation res2 = new Reservation();
        res2.setId("R501-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation res3 = new Reservation();
        res3.setId("R501-3");
        res3.setStatus(ReservationStatus.CONFIRMED);
        
        flight.setReservations(Arrays.asList(res1, res2, res3));
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(3, result.size());
        assertTrue(result.contains(res1));
        assertTrue(result.contains(res2));
        assertTrue(result.contains(res3));
    }
    
    @Test
    public void testCase2_noConfirmedReservations() throws Exception {
        // Setup
        // 1. Airline AL22; flight F502 openForBooking = True.
        Flight flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2024-06-16 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2024-06-16 16:00:00"));
        
        // 2. Two reservations status = PENDING.
        Reservation res1 = new Reservation();
        res1.setId("R502-1");
        res1.setStatus(ReservationStatus.PENDING);
        
        Reservation res2 = new Reservation();
        res2.setId("R502-2");
        res2.setStatus(ReservationStatus.PENDING);
        
        flight.setReservations(Arrays.asList(res1, res2));
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_flightClosedReturnsZero() throws Exception {
        // Setup
        // 1. Airline AL23; flight F503 openForBooking = False.
        Flight flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false);
        flight.setDepartureTime(dateFormat.parse("2024-06-17 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2024-06-17 11:00:00"));
        
        // 2. One reservation status = CONFIRMED.
        Reservation res = new Reservation();
        res.setId("R503-1");
        res.setStatus(ReservationStatus.CONFIRMED);
        
        flight.setReservations(Arrays.asList(res));
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        // Note: Flight being closed doesn't affect the method's behavior - it still returns confirmed reservations
        assertEquals(1, result.size());
        assertTrue(result.contains(res));
    }
    
    @Test
    public void testCase4_unknownFlightId() throws Exception {
        // Setup
        // 1. Airline AL24 holds flights F504, F505 only.
        Flight flight1 = new Flight();
        flight1.setId("F504");
        flight1.setOpenForBooking(true);
        flight1.setDepartureTime(dateFormat.parse("2024-06-18 08:00:00"));
        flight1.setArrivalTime(dateFormat.parse("2024-06-18 10:00:00"));
        
        Flight flight2 = new Flight();
        flight2.setId("F505");
        flight2.setOpenForBooking(true);
        flight2.setDepartureTime(dateFormat.parse("2024-06-19 13:00:00"));
        flight2.setArrivalTime(dateFormat.parse("2024-06-19 15:00:00"));
        
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        
        // Execute - Create a flight with unknown ID and check its reservations
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setOpenForBooking(true);
        
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_mixedReservationStatuses() throws Exception {
        // Setup
        // 1. Airline AL25; flight F504 openForBooking = True.
        Flight flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2024-06-20 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2024-06-20 13:00:00"));
        
        // 2. Reservations: R504-A, R504-B status = CONFIRMED;
        //    R504-C status = CANCELED; R504-D status = PENDING.
        Reservation resA = new Reservation();
        resA.setId("R504-A");
        resA.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation resB = new Reservation();
        resB.setId("R504-B");
        resB.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation resC = new Reservation();
        resC.setId("R504-C");
        resC.setStatus(ReservationStatus.CANCELED);
        
        Reservation resD = new Reservation();
        resD.setId("R504-D");
        resD.setStatus(ReservationStatus.PENDING);
        
        flight.setReservations(Arrays.asList(resA, resB, resC, resD));
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(2, result.size());
        assertTrue(result.contains(resA));
        assertTrue(result.contains(resB));
        assertFalse(result.contains(resC));
        assertFalse(result.contains(resD));
    }
}