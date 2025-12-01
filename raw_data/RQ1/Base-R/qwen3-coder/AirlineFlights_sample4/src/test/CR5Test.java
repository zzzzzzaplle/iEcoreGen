import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR5Test {
    
    private FlightSystem flightSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        flightSystem = new FlightSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup
        Flight flightF501 = new Flight();
        flightF501.setId("F501");
        flightF501.setOpenForBooking(true);
        
        // Create three confirmed reservations
        Reservation r5011 = new Reservation();
        r5011.setId("R501-1");
        r5011.setStatus(Reservation.CONFIRMED);
        r5011.setFlight(flightF501);
        
        Reservation r5012 = new Reservation();
        r5012.setId("R501-2");
        r5012.setStatus(Reservation.CONFIRMED);
        r5012.setFlight(flightF501);
        
        Reservation r5013 = new Reservation();
        r5013.setId("R501-3");
        r5013.setStatus(Reservation.CONFIRMED);
        r5013.setFlight(flightF501);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r5011);
        reservations.add(r5012);
        reservations.add(r5013);
        
        flightF501.setReservations(reservations);
        flightSystem.getFlights().add(flightF501);
        
        // Execute
        List<Reservation> result = flightSystem.getConfirmedReservations("F501");
        
        // Verify
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1", result.contains(r5011));
        assertTrue("Should contain R501-2", result.contains(r5012));
        assertTrue("Should contain R501-3", result.contains(r5013));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup
        Flight flightF502 = new Flight();
        flightF502.setId("F502");
        flightF502.setOpenForBooking(true);
        
        // Create two pending reservations
        Reservation r5021 = new Reservation();
        r5021.setId("R502-1");
        r5021.setStatus(Reservation.PENDING);
        r5021.setFlight(flightF502);
        
        Reservation r5022 = new Reservation();
        r5022.setId("R502-2");
        r5022.setStatus(Reservation.PENDING);
        r5022.setFlight(flightF502);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r5021);
        reservations.add(r5022);
        
        flightF502.setReservations(reservations);
        flightSystem.getFlights().add(flightF502);
        
        // Execute
        List<Reservation> result = flightSystem.getConfirmedReservations("F502");
        
        // Verify
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup
        Flight flightF503 = new Flight();
        flightF503.setId("F503");
        flightF503.setOpenForBooking(false); // Flight is closed
        
        // Create one confirmed reservation
        Reservation r5031 = new Reservation();
        r5031.setId("R503-1");
        r5031.setStatus(Reservation.CONFIRMED);
        r5031.setFlight(flightF503);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r5031);
        
        flightF503.setReservations(reservations);
        flightSystem.getFlights().add(flightF503);
        
        // Execute
        List<Reservation> result = flightSystem.getConfirmedReservations("F503");
        
        // Verify
        assertTrue("Should return empty list when flight is closed", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup
        Flight flightF504 = new Flight();
        flightF504.setId("F504");
        
        Flight flightF505 = new Flight();
        flightF505.setId("F505");
        
        flightSystem.getFlights().add(flightF504);
        flightSystem.getFlights().add(flightF505);
        
        // Execute - query for non-existent flight
        List<Reservation> result = flightSystem.getConfirmedReservations("FX999");
        
        // Verify
        assertTrue("Should return empty list for unknown flight ID", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup
        Flight flightF504 = new Flight();
        flightF504.setId("F504");
        flightF504.setOpenForBooking(true);
        
        // Create reservations with mixed statuses
        Reservation r504A = new Reservation();
        r504A.setId("R504-A");
        r504A.setStatus(Reservation.CONFIRMED);
        r504A.setFlight(flightF504);
        
        Reservation r504B = new Reservation();
        r504B.setId("R504-B");
        r504B.setStatus(Reservation.CONFIRMED);
        r504B.setFlight(flightF504);
        
        Reservation r504C = new Reservation();
        r504C.setId("R504-C");
        r504C.setStatus(Reservation.CANCELED);
        r504C.setFlight(flightF504);
        
        Reservation r504D = new Reservation();
        r504D.setId("R504-D");
        r504D.setStatus(Reservation.PENDING);
        r504D.setFlight(flightF504);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r504A);
        reservations.add(r504B);
        reservations.add(r504C);
        reservations.add(r504D);
        
        flightF504.setReservations(reservations);
        flightSystem.getFlights().add(flightF504);
        
        // Execute
        List<Reservation> result = flightSystem.getConfirmedReservations("F504");
        
        // Verify
        assertEquals("Should return only 2 confirmed reservations", 2, result.size());
        assertTrue("Should contain R504-A", result.contains(r504A));
        assertTrue("Should contain R504-B", result.contains(r504B));
        assertFalse("Should not contain canceled reservation R504-C", result.contains(r504C));
        assertFalse("Should not contain pending reservation R504-D", result.contains(r504D));
    }
}