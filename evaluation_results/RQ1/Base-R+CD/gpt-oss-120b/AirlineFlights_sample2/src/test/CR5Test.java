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
    public void testCase1_FlightWithThreeConfirmations() throws Exception {
        // Setup
        Flight flight = new Flight();
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
        
        List<Reservation> reservations = Arrays.asList(r1, r2, r3);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(3, result.size());
        assertTrue(result.contains(r1));
        assertTrue(result.contains(r2));
        assertTrue(result.contains(r3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup
        Flight flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        
        // Create pending reservations
        Reservation r1 = new Reservation();
        r1.setId("R502-1");
        r1.setStatus(ReservationStatus.PENDING);
        
        Reservation r2 = new Reservation();
        r2.setId("R502-2");
        r2.setStatus(ReservationStatus.PENDING);
        
        List<Reservation> reservations = Arrays.asList(r1, r2);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup
        Flight flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false);
        
        // Create confirmed reservation
        Reservation r1 = new Reservation();
        r1.setId("R503-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        
        List<Reservation> reservations = Arrays.asList(r1);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup
        Flight flight1 = new Flight();
        flight1.setId("F504");
        flight1.setOpenForBooking(true);
        
        Flight flight2 = new Flight();
        flight2.setId("F505");
        flight2.setOpenForBooking(true);
        
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        
        // Execute - Create a flight with unknown ID and try to get confirmed reservations
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setOpenForBooking(true);
        
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup
        Flight flight = new Flight();
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
        rC.setStatus(ReservationStatus.CANCELLED);
        
        Reservation rD = new Reservation();
        rD.setId("R504-D");
        rD.setStatus(ReservationStatus.PENDING);
        
        List<Reservation> reservations = Arrays.asList(rA, rB, rC, rD);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(2, result.size());
        assertTrue(result.contains(rA));
        assertTrue(result.contains(rB));
        assertFalse(result.contains(rC));
        assertFalse(result.contains(rD));
    }
}