import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    private Airline airline;
    private Flight flightWithConfirmations;
    private Flight flightWithNoConfirmations;
    private Flight closedFlight;
    private Flight mixedStatusFlight;
    private Reservation confirmedReservation1;
    private Reservation confirmedReservation2;
    private Reservation confirmedReservation3;
    private Reservation pendingReservation;
    private Reservation canceledReservation;
    
    @Before
    public void setUp() throws Exception {
        // Setup for all test cases
        airline = new Airline();
        
        // Create flights
        flightWithConfirmations = new Flight();
        flightWithConfirmations.setId("F501");
        flightWithConfirmations.setOpenForBooking(true);
        
        flightWithNoConfirmations = new Flight();
        flightWithNoConfirmations.setId("F502");
        flightWithNoConfirmations.setOpenForBooking(true);
        
        closedFlight = new Flight();
        closedFlight.setId("F503");
        closedFlight.setOpenForBooking(false);
        
        mixedStatusFlight = new Flight();
        mixedStatusFlight.setId("F504");
        mixedStatusFlight.setOpenForBooking(true);
        
        // Create reservations
        confirmedReservation1 = new Reservation();
        confirmedReservation1.setId("R501-1");
        confirmedReservation1.setStatus(ReservationStatus.CONFIRMED);
        
        confirmedReservation2 = new Reservation();
        confirmedReservation2.setId("R501-2");
        confirmedReservation2.setStatus(ReservationStatus.CONFIRMED);
        
        confirmedReservation3 = new Reservation();
        confirmedReservation3.setId("R501-3");
        confirmedReservation3.setStatus(ReservationStatus.CONFIRMED);
        
        pendingReservation = new Reservation();
        pendingReservation.setId("R502-1");
        pendingReservation.setStatus(ReservationStatus.PENDING);
        
        Reservation pendingReservation2 = new Reservation();
        pendingReservation2.setId("R502-2");
        pendingReservation2.setStatus(ReservationStatus.PENDING);
        
        Reservation confirmedForClosed = new Reservation();
        confirmedForClosed.setId("R503-1");
        confirmedForClosed.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation mixedConfirmed1 = new Reservation();
        mixedConfirmed1.setId("R504-A");
        mixedConfirmed1.setStatus(ReservationStatus.CONFIRMED);
        
        Reservation mixedConfirmed2 = new Reservation();
        mixedConfirmed2.setId("R504-B");
        mixedConfirmed2.setStatus(ReservationStatus.CONFIRMED);
        
        canceledReservation = new Reservation();
        canceledReservation.setId("R504-C");
        canceledReservation.setStatus(ReservationStatus.CANCELED);
        
        Reservation pendingReservation3 = new Reservation();
        pendingReservation3.setId("R504-D");
        pendingReservation3.setStatus(ReservationStatus.PENDING);
        
        // Add reservations to flights
        flightWithConfirmations.setReservations(new ArrayList<>());
        flightWithConfirmations.getReservations().add(confirmedReservation1);
        flightWithConfirmations.getReservations().add(confirmedReservation2);
        flightWithConfirmations.getReservations().add(confirmedReservation3);
        
        flightWithNoConfirmations.setReservations(new ArrayList<>());
        flightWithNoConfirmations.getReservations().add(pendingReservation);
        flightWithNoConfirmations.getReservations().add(pendingReservation2);
        
        closedFlight.setReservations(new ArrayList<>());
        closedFlight.getReservations().add(confirmedForClosed);
        
        mixedStatusFlight.setReservations(new ArrayList<>());
        mixedStatusFlight.getReservations().add(mixedConfirmed1);
        mixedStatusFlight.getReservations().add(mixedConfirmed2);
        mixedStatusFlight.getReservations().add(canceledReservation);
        mixedStatusFlight.getReservations().add(pendingReservation3);
        
        // Add flights to airline
        airline.addFlight(flightWithConfirmations);
        airline.addFlight(flightWithNoConfirmations);
        airline.addFlight(closedFlight);
        airline.addFlight(mixedStatusFlight);
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Test retrieving confirmed reservations for flight with three confirmations
        List<Reservation> result = flightWithConfirmations.getConfirmedReservations();
        
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1", result.contains(confirmedReservation1));
        assertTrue("Should contain R501-2", result.contains(confirmedReservation2));
        assertTrue("Should contain R501-3", result.contains(confirmedReservation3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Test retrieving confirmed reservations for flight with no confirmations
        List<Reservation> result = flightWithNoConfirmations.getConfirmedReservations();
        
        assertEquals("Should return empty list for no confirmations", 0, result.size());
        assertTrue("Result should be empty", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Test retrieving confirmed reservations for closed flight
        List<Reservation> result = closedFlight.getConfirmedReservations();
        
        assertEquals("Should return empty list for closed flight", 0, result.size());
        assertTrue("Result should be empty for closed flight", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Test retrieving confirmed reservations for unknown flight ID
        // Since we're testing the Flight.getConfirmedReservations() method directly,
        // we need to simulate an unknown flight by creating a new flight
        Flight unknownFlight = new Flight();
        unknownFlight.setId("FX999");
        unknownFlight.setOpenForBooking(true);
        unknownFlight.setReservations(new ArrayList<>());
        
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        assertEquals("Should return empty list for unknown flight", 0, result.size());
        assertTrue("Result should be empty for unknown flight", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Test retrieving confirmed reservations for flight with mixed statuses
        List<Reservation> result = mixedStatusFlight.getConfirmedReservations();
        
        assertEquals("Should return 2 confirmed reservations", 2, result.size());
        
        // Check that only confirmed reservations are returned
        for (Reservation res : result) {
            assertEquals("All returned reservations should be CONFIRMED", 
                        ReservationStatus.CONFIRMED, res.getStatus());
        }
        
        // Verify specific confirmed reservations are included
        boolean hasR504A = false;
        boolean hasR504B = false;
        for (Reservation res : result) {
            if ("R504-A".equals(res.getId())) hasR504A = true;
            if ("R504-B".equals(res.getId())) hasR504B = true;
        }
        
        assertTrue("Should contain R504-A", hasR504A);
        assertTrue("Should contain R504-B", hasR504B);
    }
}