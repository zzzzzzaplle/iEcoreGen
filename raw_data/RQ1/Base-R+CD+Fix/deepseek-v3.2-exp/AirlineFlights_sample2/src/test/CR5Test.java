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
        
        flight.setReservations(Arrays.asList(r1, r2, r3));
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
    public void testCase2_noConfirmedReservations() throws Exception {
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
        
        flight.setReservations(Arrays.asList(r1, r2));
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_flightClosedReturnsZero() throws Exception {
        // Setup
        Flight flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false);
        
        // Create confirmed reservation
        Reservation r1 = new Reservation();
        r1.setId("R503-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        
        flight.setReservations(Arrays.asList(r1));
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_unknownFlightId() throws Exception {
        // Setup
        Flight flight504 = new Flight();
        flight504.setId("F504");
        flight504.setOpenForBooking(true);
        
        Flight flight505 = new Flight();
        flight505.setId("F505");
        flight505.setOpenForBooking(true);
        
        airline.addFlight(flight504);
        airline.addFlight(flight505);
        
        // Execute - Test against unknown flight ID
        Flight unknownFlight = null;
        for (Flight f : airline.getFlights()) {
            if ("FX999".equals(f.getId())) {
                unknownFlight = f;
                break;
            }
        }
        
        // Verify - Flight FX999 doesn't exist, so cannot retrieve reservations
        assertNull(unknownFlight);
    }
    
    @Test
    public void testCase5_mixedReservationStatuses() throws Exception {
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
        rC.setStatus(ReservationStatus.CANCELED);
        
        Reservation rD = new Reservation();
        rD.setId("R504-D");
        rD.setStatus(ReservationStatus.PENDING);
        
        flight.setReservations(Arrays.asList(rA, rB, rC, rD));
        airline.addFlight(flight);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals(2, result.size());
        assertEquals("R504-A", result.get(0).getId());
        assertEquals("R504-B", result.get(1).getId());
    }
}