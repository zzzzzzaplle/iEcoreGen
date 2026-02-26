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
        Flight flight = createFlight("F501", true);
        airline.addFlight(flight);
        
        // Create confirmed reservations
        Reservation r1 = createReservation("R501-1", ReservationStatus.CONFIRMED, flight);
        Reservation r2 = createReservation("R501-2", ReservationStatus.CONFIRMED, flight);
        Reservation r3 = createReservation("R501-3", ReservationStatus.CONFIRMED, flight);
        
        flight.getReservations().add(r1);
        flight.getReservations().add(r2);
        flight.getReservations().add(r3);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1", containsReservation(result, "R501-1"));
        assertTrue("Should contain R501-2", containsReservation(result, "R501-2"));
        assertTrue("Should contain R501-3", containsReservation(result, "R501-3"));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup
        Flight flight = createFlight("F502", true);
        airline.addFlight(flight);
        
        // Create pending reservations
        Reservation r1 = createReservation("R502-1", ReservationStatus.PENDING, flight);
        Reservation r2 = createReservation("R502-2", ReservationStatus.PENDING, flight);
        
        flight.getReservations().add(r1);
        flight.getReservations().add(r2);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup
        Flight flight = createFlight("F503", false); // Flight closed for booking
        airline.addFlight(flight);
        
        // Create a confirmed reservation
        Reservation r1 = createReservation("R503-1", ReservationStatus.CONFIRMED, flight);
        flight.getReservations().add(r1);
        
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
        
        // Execute - Try to get confirmed reservations for non-existent flight
        // Since we're testing the flight method directly, we need to create a flight with unknown ID
        Flight unknownFlight = createFlight("FX999", true);
        
        // Execute
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
        Reservation rA = createReservation("R504-A", ReservationStatus.CONFIRMED, flight);
        Reservation rB = createReservation("R504-B", ReservationStatus.CONFIRMED, flight);
        Reservation rC = createReservation("R504-C", ReservationStatus.CANCELED, flight);
        Reservation rD = createReservation("R504-D", ReservationStatus.PENDING, flight);
        
        flight.getReservations().add(rA);
        flight.getReservations().add(rB);
        flight.getReservations().add(rC);
        flight.getReservations().add(rD);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return only 2 confirmed reservations", 2, result.size());
        assertTrue("Should contain R504-A", containsReservation(result, "R504-A"));
        assertTrue("Should contain R504-B", containsReservation(result, "R504-B"));
        assertFalse("Should not contain canceled reservation R504-C", containsReservation(result, "R504-C"));
        assertFalse("Should not contain pending reservation R504-D", containsReservation(result, "R504-D"));
    }
    
    // Helper method to create a flight with basic setup
    private Flight createFlight(String id, boolean openForBooking) throws Exception {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setOpenForBooking(openForBooking);
        
        // Set departure and arrival times (future dates)
        Date departureTime = dateFormat.parse("2024-12-01 10:00:00");
        Date arrivalTime = dateFormat.parse("2024-12-01 12:00:00");
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Set airports with cities
        Airport departureAirport = new Airport();
        City departureCity = new City();
        departureCity.setName("New York");
        departureAirport.addCity(departureCity);
        flight.setDepartureAirport(departureAirport);
        
        Airport arrivalAirport = new Airport();
        City arrivalCity = new City();
        arrivalCity.setName("Los Angeles");
        arrivalAirport.addCity(arrivalCity);
        flight.setArrialAirport(arrivalAirport);
        
        return flight;
    }
    
    // Helper method to create a reservation
    private Reservation createReservation(String id, ReservationStatus status, Flight flight) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatus(status);
        reservation.setFlight(flight);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger " + id);
        reservation.setPassenger(passenger);
        
        return reservation;
    }
    
    // Helper method to check if a reservation exists in the list by ID
    private boolean containsReservation(List<Reservation> reservations, String reservationId) {
        for (Reservation res : reservations) {
            if (res.getId().equals(reservationId)) {
                return true;
            }
        }
        return false;
    }
}