import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        Flight flightF501 = new Flight();
        flightF501.setId("F501");
        flightF501.setOpenForBooking(true);
        
        // Create confirmed reservations
        List<Reservation> reservations = new ArrayList<>();
        
        Reservation r5011 = new Reservation();
        r5011.setId("R501-1");
        r5011.setStatus(ReservationStatus.CONFIRMED);
        reservations.add(r5011);
        
        Reservation r5012 = new Reservation();
        r5012.setId("R501-2");
        r5012.setStatus(ReservationStatus.CONFIRMED);
        reservations.add(r5012);
        
        Reservation r5013 = new Reservation();
        r5013.setId("R501-3");
        r5013.setStatus(ReservationStatus.CONFIRMED);
        reservations.add(r5013);
        
        flightF501.setReservations(reservations);
        airline.addFlight(flightF501);
        
        // Execute
        List<Reservation> result = flightF501.getConfirmedReservations();
        
        // Verify
        assertEquals(3, result.size());
        assertTrue(result.contains(r5011));
        assertTrue(result.contains(r5012));
        assertTrue(result.contains(r5013));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup
        Flight flightF502 = new Flight();
        flightF502.setId("F502");
        flightF502.setOpenForBooking(true);
        
        // Create pending reservations only
        List<Reservation> reservations = new ArrayList<>();
        
        Reservation r5021 = new Reservation();
        r5021.setId("R502-1");
        r5021.setStatus(ReservationStatus.PENDING);
        reservations.add(r5021);
        
        Reservation r5022 = new Reservation();
        r5022.setId("R502-2");
        r5022.setStatus(ReservationStatus.PENDING);
        reservations.add(r5022);
        
        flightF502.setReservations(reservations);
        airline.addFlight(flightF502);
        
        // Execute
        List<Reservation> result = flightF502.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup
        Flight flightF503 = new Flight();
        flightF503.setId("F503");
        flightF503.setOpenForBooking(false); // Flight is closed
        
        // Create one confirmed reservation
        List<Reservation> reservations = new ArrayList<>();
        
        Reservation r5031 = new Reservation();
        r5031.setId("R503-1");
        r5031.setStatus(ReservationStatus.CONFIRMED);
        reservations.add(r5031);
        
        flightF503.setReservations(reservations);
        airline.addFlight(flightF503);
        
        // Execute
        List<Reservation> result = flightF503.getConfirmedReservations();
        
        // Verify
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup - Airline AL24 holds flights F504, F505 only
        Flight flightF504 = new Flight();
        flightF504.setId("F504");
        flightF504.setOpenForBooking(true);
        
        Flight flightF505 = new Flight();
        flightF505.setId("F505");
        flightF505.setOpenForBooking(true);
        
        airline.addFlight(flightF504);
        airline.addFlight(flightF505);
        
        // Execute - Try to get confirmed reservations for non-existent flight FX999
        // Since we're testing the Flight.getConfirmedReservations() method directly,
        // we need to create a flight with ID FX999 that doesn't exist in the airline
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
        Flight flightF504 = new Flight();
        flightF504.setId("F504");
        flightF504.setOpenForBooking(true);
        
        // Create mixed status reservations
        List<Reservation> reservations = new ArrayList<>();
        
        Reservation r504A = new Reservation();
        r504A.setId("R504-A");
        r504A.setStatus(ReservationStatus.CONFIRMED);
        reservations.add(r504A);
        
        Reservation r504B = new Reservation();
        r504B.setId("R504-B");
        r504B.setStatus(ReservationStatus.CONFIRMED);
        reservations.add(r504B);
        
        Reservation r504C = new Reservation();
        r504C.setId("R504-C");
        r504C.setStatus(ReservationStatus.CANCELLED);
        reservations.add(r504C);
        
        Reservation r504D = new Reservation();
        r504D.setId("R504-D");
        r504D.setStatus(ReservationStatus.PENDING);
        reservations.add(r504D);
        
        flightF504.setReservations(reservations);
        airline.addFlight(flightF504);
        
        // Execute
        List<Reservation> result = flightF504.getConfirmedReservations();
        
        // Verify
        assertEquals(2, result.size());
        assertTrue(result.contains(r504A));
        assertTrue(result.contains(r504B));
        assertFalse(result.contains(r504C));
        assertFalse(result.contains(r504D));
    }
}