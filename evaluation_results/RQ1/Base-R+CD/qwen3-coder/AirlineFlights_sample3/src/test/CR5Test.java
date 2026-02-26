import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR5Test {
    
    private Airline airline;
    private Flight flightF501;
    private Flight flightF502;
    private Flight flightF503;
    private Flight flightF504;
    private Flight flightF505;
    
    @Before
    public void setUp() throws ParseException {
        airline = new Airline();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = sdf.parse("2023-06-15 10:00:00");
        Date departure = sdf.parse("2023-06-20 14:00:00");
        Date arrival = sdf.parse("2023-06-20 18:00:00");
        
        // Setup for Test Case 1: Flight with three confirmations
        flightF501 = new Flight();
        flightF501.setId("F501");
        flightF501.setOpenForBooking(true);
        flightF501.setDepartureTime(departure);
        flightF501.setArrivalTime(arrival);
        
        // Create three confirmed reservations
        Reservation r1 = new Reservation();
        r1.setId("R501-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        r1.setFlight(flightF501);
        flightF501.getReservations().add(r1);
        
        Reservation r2 = new Reservation();
        r2.setId("R501-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        r2.setFlight(flightF501);
        flightF501.getReservations().add(r2);
        
        Reservation r3 = new Reservation();
        r3.setId("R501-3");
        r3.setStatus(ReservationStatus.CONFIRMED);
        r3.setFlight(flightF501);
        flightF501.getReservations().add(r3);
        
        // Setup for Test Case 2: No confirmed reservations
        flightF502 = new Flight();
        flightF502.setId("F502");
        flightF502.setOpenForBooking(true);
        flightF502.setDepartureTime(departure);
        flightF502.setArrivalTime(arrival);
        
        // Create two pending reservations
        Reservation r4 = new Reservation();
        r4.setId("R502-1");
        r4.setStatus(ReservationStatus.PENDING);
        r4.setFlight(flightF502);
        flightF502.getReservations().add(r4);
        
        Reservation r5 = new Reservation();
        r5.setId("R502-2");
        r5.setStatus(ReservationStatus.PENDING);
        r5.setFlight(flightF502);
        flightF502.getReservations().add(r5);
        
        // Setup for Test Case 3: Flight closed returns zero
        flightF503 = new Flight();
        flightF503.setId("F503");
        flightF503.setOpenForBooking(false); // Closed for booking
        flightF503.setDepartureTime(departure);
        flightF503.setArrivalTime(arrival);
        
        // Create one confirmed reservation
        Reservation r6 = new Reservation();
        r6.setId("R503-1");
        r6.setStatus(ReservationStatus.CONFIRMED);
        r6.setFlight(flightF503);
        flightF503.getReservations().add(r6);
        
        // Setup for Test Case 5: Mixed reservation statuses
        flightF504 = new Flight();
        flightF504.setId("F504");
        flightF504.setOpenForBooking(true);
        flightF504.setDepartureTime(departure);
        flightF504.setArrivalTime(arrival);
        
        // Create reservations with mixed statuses
        Reservation r7 = new Reservation();
        r7.setId("R504-A");
        r7.setStatus(ReservationStatus.CONFIRMED);
        r7.setFlight(flightF504);
        flightF504.getReservations().add(r7);
        
        Reservation r8 = new Reservation();
        r8.setId("R504-B");
        r8.setStatus(ReservationStatus.CONFIRMED);
        r8.setFlight(flightF504);
        flightF504.getReservations().add(r8);
        
        Reservation r9 = new Reservation();
        r9.setId("R504-C");
        r9.setStatus(ReservationStatus.CANCELLED);
        r9.setFlight(flightF504);
        flightF504.getReservations().add(r9);
        
        Reservation r10 = new Reservation();
        r10.setId("R504-D");
        r10.setStatus(ReservationStatus.PENDING);
        r10.setFlight(flightF504);
        flightF504.getReservations().add(r10);
        
        // Setup for Test Case 4: Unknown flight id
        flightF505 = new Flight();
        flightF505.setId("F505");
        flightF505.setOpenForBooking(true);
        flightF505.setDepartureTime(departure);
        flightF505.setArrivalTime(arrival);
        
        // Add flights to airline
        airline.addFlight(flightF501);
        airline.addFlight(flightF502);
        airline.addFlight(flightF503);
        airline.addFlight(flightF504);
        airline.addFlight(flightF505);
    }
    
    @Test
    public void testCase1_flightWithThreeConfirmations() {
        // Retrieve confirmed reservations for flight F501
        List<Reservation> confirmedReservations = flightF501.getConfirmedReservations();
        
        // Verify that we get exactly 3 confirmed reservations
        assertEquals(3, confirmedReservations.size());
        
        // Verify that all returned reservations are confirmed
        for (Reservation r : confirmedReservations) {
            assertEquals(ReservationStatus.CONFIRMED, r.getStatus());
        }
        
        // Verify specific reservation IDs are present
        Set<String> reservationIds = new HashSet<>();
        for (Reservation r : confirmedReservations) {
            reservationIds.add(r.getId());
        }
        
        assertTrue(reservationIds.contains("R501-1"));
        assertTrue(reservationIds.contains("R501-2"));
        assertTrue(reservationIds.contains("R501-3"));
    }
    
    @Test
    public void testCase2_noConfirmedReservations() {
        // Retrieve confirmed reservations for flight F502
        List<Reservation> confirmedReservations = flightF502.getConfirmedReservations();
        
        // Verify that we get an empty list
        assertTrue(confirmedReservations.isEmpty());
    }
    
    @Test
    public void testCase3_flightClosedReturnsZero() {
        // Retrieve confirmed reservations for flight F503 (closed for booking)
        List<Reservation> confirmedReservations = flightF503.getConfirmedReservations();
        
        // Verify that we get an empty list even though there is a confirmed reservation
        assertTrue(confirmedReservations.isEmpty());
    }
    
    @Test
    public void testCase4_unknownFlightId() {
        // Create a flight that is not in the airline's flight list
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setOpenForBooking(true);
        
        // Retrieve confirmed reservations for this unknown flight
        List<Reservation> confirmedReservations = unknownFlight.getConfirmedReservations();
        
        // Verify that we get an empty list
        assertTrue(confirmedReservations.isEmpty());
    }
    
    @Test
    public void testCase5_mixedReservationStatuses() {
        // Retrieve confirmed reservations for flight F504
        List<Reservation> confirmedReservations = flightF504.getConfirmedReservations();
        
        // Verify that we get exactly 2 confirmed reservations
        assertEquals(2, confirmedReservations.size());
        
        // Verify that all returned reservations are confirmed
        for (Reservation r : confirmedReservations) {
            assertEquals(ReservationStatus.CONFIRMED, r.getStatus());
        }
        
        // Verify specific reservation IDs are present
        Set<String> reservationIds = new HashSet<>();
        for (Reservation r : confirmedReservations) {
            reservationIds.add(r.getId());
        }
        
        assertTrue(reservationIds.contains("R504-A"));
        assertTrue(reservationIds.contains("R504-B"));
        
        // Verify that cancelled and pending reservations are not included
        assertFalse(reservationIds.contains("R504-C"));
        assertFalse(reservationIds.contains("R504-D"));
    }
}