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
        // Setup: Airline AL21; flight F501 openForBooking = True
        Flight flight = createFlight("F501", true);
        airline.addFlight(flight);
        
        // Setup: Reservations R501-1, R501-2, R501-3 status = CONFIRMED
        addReservationToFlight(flight, "R501-1", ReservationStatus.CONFIRMED);
        addReservationToFlight(flight, "R501-2", ReservationStatus.CONFIRMED);
        addReservationToFlight(flight, "R501-3", ReservationStatus.CONFIRMED);
        
        // Execute: Retrieve confirmed list for flight F501
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, result.size());
        assertTrue(containsReservationWithId(result, "R501-1"));
        assertTrue(containsReservationWithId(result, "R501-2"));
        assertTrue(containsReservationWithId(result, "R501-3"));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() throws Exception {
        // Setup: Airline AL22; flight F502 openForBooking = True
        Flight flight = createFlight("F502", true);
        airline.addFlight(flight);
        
        // Setup: Two reservations status = PENDING
        addReservationToFlight(flight, "R502-1", ReservationStatus.PENDING);
        addReservationToFlight(flight, "R502-2", ReservationStatus.PENDING);
        
        // Execute: Retrieve confirmed list for flight F502
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() throws Exception {
        // Setup: Airline AL23; flight F503 openForBooking = False
        Flight flight = createFlight("F503", false);
        airline.addFlight(flight);
        
        // Setup: One reservation status = CONFIRMED
        addReservationToFlight(flight, "R503-1", ReservationStatus.CONFIRMED);
        
        // Execute: Retrieve confirmed list for flight F503
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() throws Exception {
        // Setup: Airline AL24 holds flights F504, F505 only
        Flight flight504 = createFlight("F504", true);
        Flight flight505 = createFlight("F505", true);
        airline.addFlight(flight504);
        airline.addFlight(flight505);
        
        // Execute: Retrieve confirmed list for flight FX999 (unknown flight)
        // Since we need to test against a specific flight object, we'll create a non-existent flight
        Flight unknownFlight = createFlight("FX999", true);
        
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        
        // Verify: Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() throws Exception {
        // Setup: Airline AL25; flight F504 openForBooking = True
        Flight flight = createFlight("F504", true);
        airline.addFlight(flight);
        
        // Setup: Reservations: R504-A, R504-B status = CONFIRMED;
        // R504-C status = CANCELED; R504-D status = PENDING
        addReservationToFlight(flight, "R504-A", ReservationStatus.CONFIRMED);
        addReservationToFlight(flight, "R504-B", ReservationStatus.CONFIRMED);
        addReservationToFlight(flight, "R504-C", ReservationStatus.CANCELED);
        addReservationToFlight(flight, "R504-D", ReservationStatus.PENDING);
        
        // Execute: Retrieve confirmed list for flight F504
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify: Expected Output: R504-A, R504-B
        assertEquals(2, result.size());
        assertTrue(containsReservationWithId(result, "R504-A"));
        assertTrue(containsReservationWithId(result, "R504-B"));
        assertFalse(containsReservationWithId(result, "R504-C"));
        assertFalse(containsReservationWithId(result, "R504-D"));
    }
    
    // Helper method to create a flight with basic configuration
    private Flight createFlight(String flightId, boolean openForBooking) throws Exception {
        Flight flight = new Flight();
        flight.setId(flightId);
        flight.setOpenForBooking(openForBooking);
        
        // Set departure and arrival times (required for flight validation)
        Date departureTime = dateFormat.parse("2024-06-01 10:00:00");
        Date arrivalTime = dateFormat.parse("2024-06-01 12:00:00");
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);
        
        // Set airports with cities (required for flight validation)
        Airport departureAirport = new Airport();
        City departureCity = new City();
        departureCity.setName("New York");
        departureAirport.addCity(departureCity);
        flight.setDepartureAirport(departureAirport);
        
        Airport arrivalAirport = new Airport();
        City arrivalCity = new City();
        arrivalCity.setName("Los Angeles");
        arrivalAirport.addCity(arrivalCity);
        flight.setArrivalAirport(arrivalAirport);
        
        return flight;
    }
    
    // Helper method to add a reservation to a flight
    private void addReservationToFlight(Flight flight, String reservationId, ReservationStatus status) {
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setStatus(status);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger " + reservationId);
        reservation.setPassenger(passenger);
        
        reservation.setFlight(flight);
        flight.getReservations().add(reservation);
    }
    
    // Helper method to check if a list contains a reservation with specific ID
    private boolean containsReservationWithId(List<Reservation> reservations, String reservationId) {
        for (Reservation res : reservations) {
            if (res.getId().equals(reservationId)) {
                return true;
            }
        }
        return false;
    }
}