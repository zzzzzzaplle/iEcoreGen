import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR5Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_flightWithThreeConfirmations() throws ParseException {
        // Setup: Airline AL21; flight F501 openForBooking = True.
        Airline airline = new Airline();
        Flight flight = new Flight();
        flight.setId("F501");
        flight.setOpenForBooking(true);
        
        Date departureTime = dateFormat.parse("2023-12-01 10:00:00");
        Date arrivalTime = dateFormat.parse("2023-12-01 14:00:00");
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Setup: Reservations R501-1, R501-2, R501-3 status = CONFIRMED.
        Reservation reservation1 = new Reservation();
        reservation1.setId("R501-1");
        reservation1.setStatus(ReservationStatus.CONFIRMED);
        reservation1.setFlight(flight);
        
        Reservation reservation2 = new Reservation();
        reservation2.setId("R501-2");
        reservation2.setStatus(ReservationStatus.CONFIRMED);
        reservation2.setFlight(flight);
        
        Reservation reservation3 = new Reservation();
        reservation3.setId("R501-3");
        reservation3.setStatus(ReservationStatus.CONFIRMED);
        reservation3.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        reservations.add(reservation3);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed list for flight F501.
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, confirmedReservations.size());
        assertTrue(confirmedReservations.contains(reservation1));
        assertTrue(confirmedReservations.contains(reservation2));
        assertTrue(confirmedReservations.contains(reservation3));
    }
    
    @Test
    public void testCase2_noConfirmedReservations() throws ParseException {
        // Setup: Airline AL22; flight F502 openForBooking = True.
        Airline airline = new Airline();
        Flight flight = new Flight();
        flight.setId("F502");
        flight.setOpenForBooking(true);
        
        Date departureTime = dateFormat.parse("2023-12-01 10:00:00");
        Date arrivalTime = dateFormat.parse("2023-12-01 14:00:00");
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Setup: Two reservations status = PENDING.
        Reservation reservation1 = new Reservation();
        reservation1.setId("R502-1");
        reservation1.setStatus(ReservationStatus.PENDING);
        reservation1.setFlight(flight);
        
        Reservation reservation2 = new Reservation();
        reservation2.setId("R502-2");
        reservation2.setStatus(ReservationStatus.PENDING);
        reservation2.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed list for flight F502.
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Expected Output: []
        assertEquals(0, confirmedReservations.size());
    }
    
    @Test
    public void testCase3_flightClosedReturnsZero() throws ParseException {
        // Setup: Airline AL23; flight F503 openForBooking = False.
        Airline airline = new Airline();
        Flight flight = new Flight();
        flight.setId("F503");
        flight.setOpenForBooking(false); // Flight closed
        
        Date departureTime = dateFormat.parse("2023-12-01 10:00:00");
        Date arrivalTime = dateFormat.parse("2023-12-01 14:00:00");
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Setup: One reservation status = CONFIRMED.
        Reservation reservation = new Reservation();
        reservation.setId("R503-1");
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed list for flight F503.
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Expected Output: []
        assertEquals(0, confirmedReservations.size());
    }
    
    @Test
    public void testCase4_unknownFlightId() throws ParseException {
        // Setup: Airline AL24 holds flights F504, F505 only.
        Airline airline = new Airline();
        
        Flight flight1 = new Flight();
        flight1.setId("F504");
        flight1.setOpenForBooking(true);
        Date departureTime1 = dateFormat.parse("2023-12-01 10:00:00");
        Date arrivalTime1 = dateFormat.parse("2023-12-01 14:00:00");
        flight1.setDepartureTime(departureTime1);
        flight1.setArrivalTime(arrivalTime1);
        
        Flight flight2 = new Flight();
        flight2.setId("F505");
        flight2.setOpenForBooking(true);
        Date departureTime2 = dateFormat.parse("2023-12-02 10:00:00");
        Date arrivalTime2 = dateFormat.parse("2023-12-02 14:00:00");
        flight2.setDepartureTime(departureTime2);
        flight2.setArrivalTime(arrivalTime2);
        
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        
        // Execute: Retrieve confirmed list for flight FX999.
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        List<Reservation> confirmedReservations = unknownFlight.getConfirmedReservations();
        
        // Expected Output: []
        assertEquals(0, confirmedReservations.size());
    }
    
    @Test
    public void testCase5_mixedReservationStatuses() throws ParseException {
        // Setup: Airline AL25; flight F504 openForBooking = True.
        Airline airline = new Airline();
        Flight flight = new Flight();
        flight.setId("F504");
        flight.setOpenForBooking(true);
        
        Date departureTime = dateFormat.parse("2023-12-01 10:00:00");
        Date arrivalTime = dateFormat.parse("2023-12-01 14:00:00");
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Setup: Reservations: R504-A, R504-B status = CONFIRMED;
        //        R504-C status = CANCELED; R504-D status = PENDING.
        Reservation reservationA = new Reservation();
        reservationA.setId("R504-A");
        reservationA.setStatus(ReservationStatus.CONFIRMED);
        reservationA.setFlight(flight);
        
        Reservation reservationB = new Reservation();
        reservationB.setId("R504-B");
        reservationB.setStatus(ReservationStatus.CONFIRMED);
        reservationB.setFlight(flight);
        
        Reservation reservationC = new Reservation();
        reservationC.setId("R504-C");
        reservationC.setStatus(ReservationStatus.CANCELLED);
        reservationC.setFlight(flight);
        
        Reservation reservationD = new Reservation();
        reservationD.setId("R504-D");
        reservationD.setStatus(ReservationStatus.PENDING);
        reservationD.setFlight(flight);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservationA);
        reservations.add(reservationB);
        reservations.add(reservationC);
        reservations.add(reservationD);
        flight.setReservations(reservations);
        
        airline.addFlight(flight);
        
        // Execute: Retrieve confirmed list for flight F504.
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        
        // Expected Output: R504-A, R504-B
        assertEquals(2, confirmedReservations.size());
        assertTrue(confirmedReservations.contains(reservationA));
        assertTrue(confirmedReservations.contains(reservationB));
        assertFalse(confirmedReservations.contains(reservationC));
        assertFalse(confirmedReservations.contains(reservationD));
    }
}