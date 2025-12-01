import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CR5Test {
    
    private AirlineService airlineService;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineService = new AirlineService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Airline AL21; flight F501 openForBooking = True
        // Reservations R501-1, R501-2, R501-3 status = CONFIRMED
        
        // Create airports
        Airport departureAirport = new Airport("DEP501", "Departure Airport 501");
        Airport arrivalAirport = new Airport("ARR501", "Arrival Airport 501");
        
        // Create flight with future dates
        LocalDateTime departureTime = LocalDateTime.now().plusDays(1);
        LocalDateTime arrivalTime = departureTime.plusHours(2);
        Flight flight = new Flight("F501", departureAirport, arrivalAirport, departureTime, arrivalTime);
        flight.setStatus(FlightStatus.OPEN);
        
        // Publish flight
        boolean published = airlineService.publishFlight(flight);
        assertTrue("Flight should be published successfully", published);
        
        // Create and add confirmed reservations
        Reservation res1 = new Reservation("R501-1", "Passenger1", flight);
        res1.setStatus(ReservationStatus.CONFIRMED);
        flight.addReservation(res1);
        airlineService.getReservations().put("R501-1", res1);
        
        Reservation res2 = new Reservation("R501-2", "Passenger2", flight);
        res2.setStatus(ReservationStatus.CONFIRMED);
        flight.addReservation(res2);
        airlineService.getReservations().put("R501-2", res2);
        
        Reservation res3 = new Reservation("R501-3", "Passenger3", flight);
        res3.setStatus(ReservationStatus.CONFIRMED);
        flight.addReservation(res3);
        airlineService.getReservations().put("R501-3", res3);
        
        // Test: Retrieve confirmed list for flight F501
        List<Reservation> result = airlineService.getConfirmedReservations("F501");
        
        // Verify: Expected Output: R501-1, R501-2, R501-3
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1", result.contains(res1));
        assertTrue("Should contain R501-2", result.contains(res2));
        assertTrue("Should contain R501-3", result.contains(res3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Airline AL22; flight F502 openForBooking = True
        // Two reservations status = PENDING
        
        // Create airports
        Airport departureAirport = new Airport("DEP502", "Departure Airport 502");
        Airport arrivalAirport = new Airport("ARR502", "Arrival Airport 502");
        
        // Create flight with future dates
        LocalDateTime departureTime = LocalDateTime.now().plusDays(1);
        LocalDateTime arrivalTime = departureTime.plusHours(2);
        Flight flight = new Flight("F502", departureAirport, arrivalAirport, departureTime, arrivalTime);
        flight.setStatus(FlightStatus.OPEN);
        
        // Publish flight
        boolean published = airlineService.publishFlight(flight);
        assertTrue("Flight should be published successfully", published);
        
        // Create and add pending reservations
        Reservation res1 = new Reservation("R502-1", "Passenger1", flight);
        res1.setStatus(ReservationStatus.PENDING);
        flight.addReservation(res1);
        airlineService.getReservations().put("R502-1", res1);
        
        Reservation res2 = new Reservation("R502-2", "Passenger2", flight);
        res2.setStatus(ReservationStatus.PENDING);
        flight.addReservation(res2);
        airlineService.getReservations().put("R502-2", res2);
        
        // Test: Retrieve confirmed list for flight F502
        List<Reservation> result = airlineService.getConfirmedReservations("F502");
        
        // Verify: Expected Output: []
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Airline AL23; flight F503 openForBooking = False
        // One reservation status = CONFIRMED
        
        // Create airports
        Airport departureAirport = new Airport("DEP503", "Departure Airport 503");
        Airport arrivalAirport = new Airport("ARR503", "Arrival Airport 503");
        
        // Create flight with future dates
        LocalDateTime departureTime = LocalDateTime.now().plusDays(1);
        LocalDateTime arrivalTime = departureTime.plusHours(2);
        Flight flight = new Flight("F503", departureAirport, arrivalAirport, departureTime, arrivalTime);
        flight.setStatus(FlightStatus.CLOSED); // Flight is closed
        
        // Publish flight
        boolean published = airlineService.publishFlight(flight);
        assertTrue("Flight should be published successfully", published);
        
        // Create and add confirmed reservation
        Reservation res = new Reservation("R503-1", "Passenger1", flight);
        res.setStatus(ReservationStatus.CONFIRMED);
        flight.addReservation(res);
        airlineService.getReservations().put("R503-1", res);
        
        // Test: Retrieve confirmed list for flight F503
        List<Reservation> result = airlineService.getConfirmedReservations("F503");
        
        // Verify: Expected Output: []
        assertTrue("Should return empty list when flight is closed", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup: Airline AL24 holds flights F504, F505 only
        
        // Create and publish flight F504
        Airport dep504 = new Airport("DEP504", "Departure Airport 504");
        Airport arr504 = new Airport("ARR504", "Arrival Airport 504");
        LocalDateTime depTime504 = LocalDateTime.now().plusDays(1);
        LocalDateTime arrTime504 = depTime504.plusHours(2);
        Flight flight504 = new Flight("F504", dep504, arr504, depTime504, arrTime504);
        flight504.setStatus(FlightStatus.OPEN);
        airlineService.publishFlight(flight504);
        
        // Create and publish flight F505
        Airport dep505 = new Airport("DEP505", "Departure Airport 505");
        Airport arr505 = new Airport("ARR505", "Arrival Airport 505");
        LocalDateTime depTime505 = LocalDateTime.now().plusDays(1);
        LocalDateTime arrTime505 = depTime505.plusHours(2);
        Flight flight505 = new Flight("F505", dep505, arr505, depTime505, arrTime505);
        flight505.setStatus(FlightStatus.OPEN);
        airlineService.publishFlight(flight505);
        
        // Test: Retrieve confirmed list for unknown flight FX999
        List<Reservation> result = airlineService.getConfirmedReservations("FX999");
        
        // Verify: Expected Output: []
        assertTrue("Should return empty list for unknown flight ID", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Airline AL25; flight F504 openForBooking = True
        // Reservations: R504-A, R504-B status = CONFIRMED; R504-C status = CANCELED; R504-D status = PENDING
        
        // Create airports
        Airport departureAirport = new Airport("DEP504", "Departure Airport 504");
        Airport arrivalAirport = new Airport("ARR504", "Arrival Airport 504");
        
        // Create flight with future dates
        LocalDateTime departureTime = LocalDateTime.now().plusDays(1);
        LocalDateTime arrivalTime = departureTime.plusHours(2);
        Flight flight = new Flight("F504", departureAirport, arrivalAirport, departureTime, arrivalTime);
        flight.setStatus(FlightStatus.OPEN);
        
        // Publish flight
        boolean published = airlineService.publishFlight(flight);
        assertTrue("Flight should be published successfully", published);
        
        // Create and add mixed status reservations
        Reservation resA = new Reservation("R504-A", "PassengerA", flight);
        resA.setStatus(ReservationStatus.CONFIRMED);
        flight.addReservation(resA);
        airlineService.getReservations().put("R504-A", resA);
        
        Reservation resB = new Reservation("R504-B", "PassengerB", flight);
        resB.setStatus(ReservationStatus.CONFIRMED);
        flight.addReservation(resB);
        airlineService.getReservations().put("R504-B", resB);
        
        Reservation resC = new Reservation("R504-C", "PassengerC", flight);
        resC.setStatus(ReservationStatus.CANCELLED);
        flight.addReservation(resC);
        airlineService.getReservations().put("R504-C", resC);
        
        Reservation resD = new Reservation("R504-D", "PassengerD", flight);
        resD.setStatus(ReservationStatus.PENDING);
        flight.addReservation(resD);
        airlineService.getReservations().put("R504-D", resD);
        
        // Test: Retrieve confirmed list for flight F504
        List<Reservation> result = airlineService.getConfirmedReservations("F504");
        
        // Verify: Expected Output: R504-A, R504-B
        assertEquals("Should return only 2 confirmed reservations", 2, result.size());
        assertTrue("Should contain R504-A", result.contains(resA));
        assertTrue("Should contain R504-B", result.contains(resB));
        assertFalse("Should not contain cancelled reservation R504-C", result.contains(resC));
        assertFalse("Should not contain pending reservation R504-D", result.contains(resD));
    }
}