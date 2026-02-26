import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR5Test {
    private Airline airline;
    private SimpleDateFormat dateFormat;
    private Date futureDate;
    private Date pastDate;
    
    @Before
    public void setUp() throws Exception {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        futureDate = dateFormat.parse("2025-01-01 12:00:00");
        pastDate = dateFormat.parse("2020-01-01 12:00:00");
    }
    
    @Test
    public void testCase1_flightWithThreeConfirmations() throws Exception {
        // Setup
        Flight flight = createFlight("F501", true);
        Reservation r1 = createReservation("R501-1", ReservationStatus.CONFIRMED, flight);
        Reservation r2 = createReservation("R501-2", ReservationStatus.CONFIRMED, flight);
        Reservation r3 = createReservation("R501-3", ReservationStatus.CONFIRMED, flight);
        
        // Add reservations to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        reservations.add(r3);
        flight.setReservations(reservations);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1", result.contains(r1));
        assertTrue("Should contain R501-2", result.contains(r2));
        assertTrue("Should contain R501-3", result.contains(r3));
    }
    
    @Test
    public void testCase2_noConfirmedReservations() throws Exception {
        // Setup
        Flight flight = createFlight("F502", true);
        Reservation r1 = createReservation("R502-1", ReservationStatus.PENDING, flight);
        Reservation r2 = createReservation("R502-2", ReservationStatus.PENDING, flight);
        
        // Add reservations to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        reservations.add(r2);
        flight.setReservations(reservations);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list for no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_flightClosedReturnsZero() throws Exception {
        // Setup
        Flight flight = createFlight("F503", false);
        Reservation r1 = createReservation("R503-1", ReservationStatus.CONFIRMED, flight);
        
        // Add reservation to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r1);
        flight.setReservations(reservations);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertTrue("Should return empty list when flight is closed", result.isEmpty());
    }
    
    @Test
    public void testCase4_unknownFlightId() throws Exception {
        // Setup
        Flight flight1 = createFlight("F504", true);
        Flight flight2 = createFlight("F505", true);
        airline.addFlight(flight1);
        airline.addFlight(flight2);
        
        // Execute - This test is for a flight that doesn't exist in the airline
        // Since the method getConfirmedReservations() is on Flight class, we need to simulate
        // what happens when we call it on a flight that doesn't exist in the system
        Flight unknownFlight = createFlight("FX999", true);
        
        // Verify
        List<Reservation> result = unknownFlight.getConfirmedReservations();
        assertTrue("Should return empty list for unknown flight", result.isEmpty());
    }
    
    @Test
    public void testCase5_mixedReservationStatuses() throws Exception {
        // Setup
        Flight flight = createFlight("F504", true);
        Reservation rA = createReservation("R504-A", ReservationStatus.CONFIRMED, flight);
        Reservation rB = createReservation("R504-B", ReservationStatus.CONFIRMED, flight);
        Reservation rC = createReservation("R504-C", ReservationStatus.CANCELED, flight);
        Reservation rD = createReservation("R504-D", ReservationStatus.PENDING, flight);
        
        // Add reservations to flight
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(rA);
        reservations.add(rB);
        reservations.add(rC);
        reservations.add(rD);
        flight.setReservations(reservations);
        
        // Execute
        List<Reservation> result = flight.getConfirmedReservations();
        
        // Verify
        assertEquals("Should return 2 confirmed reservations", 2, result.size());
        assertTrue("Should contain R504-A", result.contains(rA));
        assertTrue("Should contain R504-B", result.contains(rB));
        assertFalse("Should not contain canceled reservation R504-C", result.contains(rC));
        assertFalse("Should not contain pending reservation R504-D", result.contains(rD));
    }
    
    private Flight createFlight(String id, boolean openForBooking) throws Exception {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setOpenForBooking(openForBooking);
        flight.setDepartureTime(futureDate);
        flight.setArrivalTime(dateFormat.parse("2025-01-01 15:00:00"));
        
        // Create airports
        Airport departureAirport = new Airport();
        Airport arrivalAirport = new Airport();
        
        // Create cities
        City departureCity = new City();
        departureCity.setName("CityA");
        City arrivalCity = new City();
        arrivalCity.setName("CityB");
        
        // Setup airports
        departureAirport.addCity(departureCity);
        arrivalAirport.addCity(arrivalCity);
        
        flight.setDepartureAirport(departureAirport);
        flight.setArrialAirport(arrivalAirport);
        
        return flight;
    }
    
    private Reservation createReservation(String id, ReservationStatus status, Flight flight) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setStatus(status);
        reservation.setFlight(flight);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger_" + id);
        reservation.setPassenger(passenger);
        
        return reservation;
    }
}