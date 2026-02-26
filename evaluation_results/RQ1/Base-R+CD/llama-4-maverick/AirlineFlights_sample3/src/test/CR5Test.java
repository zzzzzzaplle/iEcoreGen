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
        Flight flight = createFlight("F501", true);
        airline.addFlight(flight);
        
        // Create confirmed reservations
        List<Reservation> reservations = new ArrayList<>();
        Reservation r1 = createReservation("R501-1", ReservationStatus.CONFIRMED);
        Reservation r2 = createReservation("R501-2", ReservationStatus.CONFIRMED);
        Reservation r3 = createReservation("R501-3", ReservationStatus.CONFIRMED);
        
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        flight.setReservations(reservations);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1", containsReservationWithStatus(result, "R501-1", ReservationStatus.CONFIRMED));
        assertTrue("Should contain R501-2", containsReservationWithStatus(result, "R501-2", ReservationStatus.CONFIRMED));
        assertTrue("Should contain R501-3", containsReservationWithStatus(result, "R501-3", ReservationStatus.CONFIRMED));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup
        Flight flight = createFlight("F502", true);
        airline.addFlight(flight);
        
        // Create pending reservations
        List<Reservation> reservations = new ArrayList<>();
        Reservation r1 = createReservation("R502-1", ReservationStatus.PENDING);
        Reservation r2 = createReservation("R502-2", ReservationStatus.PENDING);
        
        reservations.add(r1);
        reservations.add(r2);
        flight.setReservations(reservations);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup
        Flight flight = createFlight("F503", false); // Flight closed
        airline.addFlight(flight);
        
        // Create a confirmed reservation
        List<Reservation> reservations = new ArrayList<>();
        Reservation r1 = createReservation("R503-1", ReservationStatus.CONFIRMED);
        
        reservations.add(r1);
        flight.setReservations(reservations);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list when flight is closed", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup
        Flight flight1 = createFlight("F504", true);
        Flight flight2 = createFlight("F505", true);
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        
        // Execute - This test case seems to be about searching for non-existent flight
        // but getConfirmedReservations() is called on a flight object, not by flight ID
        // Since the requirement is to test getConfirmedReservations() method,
        // we'll verify that non-existent reservations return empty list
        
        Flight unknownFlight = createFlight("FX999", true);
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list for unknown flight", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup
        Flight flight = createFlight("F504", true);
        airline.addFlight(flight);
        
        // Create mixed status reservations
        List<Reservation> reservations = new ArrayList<>();
        Reservation rA = createReservation("R504-A", ReservationStatus.CONFIRMED);
        Reservation rB = createReservation("R504-B", ReservationStatus.CONFIRMED);
        Reservation rC = createReservation("R504-C", ReservationStatus.CANCELED);
        Reservation rD = createReservation("R504-D", ReservationStatus.PENDING);
        
        reservations.add(rA);
        reservations.add(rB);
        reservations.add(rC);
        reservations.add(rD);
        flight.setReservations(reservations);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return only 2 confirmed reservations", 2, result.size());
        assertTrue("Should contain R504-A", containsReservationWithStatus(result, "R504-A", ReservationStatus.CONFIRMED));
        assertTrue("Should contain R504-B", containsReservationWithStatus(result, "R504-B", ReservationStatus.CONFIRMED));
        assertFalse("Should not contain R504-C", containsReservationWithStatus(result, "R504-C", ReservationStatus.CONFIRMED));
        assertFalse("Should not contain R504-D", containsReservationWithStatus(result, "R504-D", ReservationStatus.CONFIRMED));
    }
    
    // Helper method to create a flight with basic setup
    private Flight createFlight(String id, boolean openForBooking) throws Exception {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setOpenForBooking(openForBooking);
        flight.setDepartureTime(dateFormat.parse("2024-12-31 10:00:00"));
        flight.setArrivalTime(dateFormat.parse("2024-12-31 12:00:00"));
        
        Airport depAirport = new Airport();
        depAirport.setId("DEP");
        Airport arrAirport = new Airport();
        arrAirport.setId("ARR");
        
        flight.setDepartureAirport(depAirport);
        flight.setArrivalAirport(arrAirport);
        
        return flight;
    }
    
    // Helper method to create a reservation
    private Reservation createReservation(String id, ReservationStatus status) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatus(status);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger_" + id);
        reservation.setPassenger(passenger);
        
        return reservation;
    }
    
    // Helper method to check if a reservation with specific ID and status exists in the list
    private boolean containsReservationWithStatus(List<Reservation> reservations, String id, ReservationStatus status) {
        for (Reservation r : reservations) {
            if (r.getId().equals(id) && r.getStatus() == status) {
                return true;
            }
        }
        return false;
    }
}