import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CR5Test {
    
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Airline AL21; flight F501 openForBooking = True
        Flight flightF501 = new Flight();
        flightF501.setId("F501");
        flightF501.setOpenForBooking(true);
        
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP501");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR501");
        
        flightF501.setDepartureAirport(departureAirport);
        flightF501.setArrivalAirport(arrivalAirport);
        flightF501.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF501.setArrivalTime(LocalDateTime.now().plusDays(2));
        
        airlineSystem.getFlights().add(flightF501);
        
        // Setup: Reservations R501-1, R501-2, R501-3 status = CONFIRMED
        Booking booking1 = new Booking();
        Reservation reservation1 = createReservation("R501-1", "Passenger1", flightF501, "confirmed");
        booking1.addReservation(reservation1);
        
        Booking booking2 = new Booking();
        Reservation reservation2 = createReservation("R501-2", "Passenger2", flightF501, "confirmed");
        booking2.addReservation(reservation2);
        
        Booking booking3 = new Booking();
        Reservation reservation3 = createReservation("R501-3", "Passenger3", flightF501, "confirmed");
        booking3.addReservation(reservation3);
        
        airlineSystem.getBookings().add(booking1);
        airlineSystem.getBookings().add(booking2);
        airlineSystem.getBookings().add(booking3);
        
        // Input: Retrieve confirmed list for flight F501
        List<Reservation> result = airlineSystem.getConfirmedReservations("F501");
        
        // Expected Output: R501-1, R501-2, R501-3
        assertEquals(3, result.size());
        assertTrue(result.contains(reservation1));
        assertTrue(result.contains(reservation2));
        assertTrue(result.contains(reservation3));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Airline AL22; flight F502 openForBooking = True
        Flight flightF502 = new Flight();
        flightF502.setId("F502");
        flightF502.setOpenForBooking(true);
        
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP502");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR502");
        
        flightF502.setDepartureAirport(departureAirport);
        flightF502.setArrivalAirport(arrivalAirport);
        flightF502.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF502.setArrivalTime(LocalDateTime.now().plusDays(2));
        
        airlineSystem.getFlights().add(flightF502);
        
        // Setup: Two reservations status = PENDING
        Booking booking1 = new Booking();
        Reservation reservation1 = createReservation("R502-1", "Passenger1", flightF502, "pending");
        booking1.addReservation(reservation1);
        
        Booking booking2 = new Booking();
        Reservation reservation2 = createReservation("R502-2", "Passenger2", flightF502, "pending");
        booking2.addReservation(reservation2);
        
        airlineSystem.getBookings().add(booking1);
        airlineSystem.getBookings().add(booking2);
        
        // Input: Retrieve confirmed list for flight F502
        List<Reservation> result = airlineSystem.getConfirmedReservations("F502");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Airline AL23; flight F503 openForBooking = False
        Flight flightF503 = new Flight();
        flightF503.setId("F503");
        flightF503.setOpenForBooking(false);
        
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP503");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR503");
        
        flightF503.setDepartureAirport(departureAirport);
        flightF503.setArrivalAirport(arrivalAirport);
        flightF503.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF503.setArrivalTime(LocalDateTime.now().plusDays(2));
        
        airlineSystem.getFlights().add(flightF503);
        
        // Setup: One reservation status = CONFIRMED
        Booking booking = new Booking();
        Reservation reservation = createReservation("R503-1", "Passenger1", flightF503, "confirmed");
        booking.addReservation(reservation);
        
        airlineSystem.getBookings().add(booking);
        
        // Input: Retrieve confirmed list for flight F503
        List<Reservation> result = airlineSystem.getConfirmedReservations("F503");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup: Airline AL24 holds flights F504, F505 only
        Flight flightF504 = new Flight();
        flightF504.setId("F504");
        flightF504.setOpenForBooking(true);
        
        Airport departureAirport504 = new Airport();
        departureAirport504.setId("DEP504");
        Airport arrivalAirport504 = new Airport();
        arrivalAirport504.setId("ARR504");
        
        flightF504.setDepartureAirport(departureAirport504);
        flightF504.setArrivalAirport(arrivalAirport504);
        flightF504.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF504.setArrivalTime(LocalDateTime.now().plusDays(2));
        
        Flight flightF505 = new Flight();
        flightF505.setId("F505");
        flightF505.setOpenForBooking(true);
        
        Airport departureAirport505 = new Airport();
        departureAirport505.setId("DEP505");
        Airport arrivalAirport505 = new Airport();
        arrivalAirport505.setId("ARR505");
        
        flightF505.setDepartureAirport(departureAirport505);
        flightF505.setArrivalAirport(arrivalAirport505);
        flightF505.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF505.setArrivalTime(LocalDateTime.now().plusDays(2));
        
        airlineSystem.getFlights().add(flightF504);
        airlineSystem.getFlights().add(flightF505);
        
        // Input: Retrieve confirmed list for flight FX999
        List<Reservation> result = airlineSystem.getConfirmedReservations("FX999");
        
        // Expected Output: []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Airline AL25; flight F504 openForBooking = True
        Flight flightF504 = new Flight();
        flightF504.setId("F504");
        flightF504.setOpenForBooking(true);
        
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP504");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR504");
        
        flightF504.setDepartureAirport(departureAirport);
        flightF504.setArrivalAirport(arrivalAirport);
        flightF504.setDepartureTime(LocalDateTime.now().plusDays(1));
        flightF504.setArrivalTime(LocalDateTime.now().plusDays(2));
        
        airlineSystem.getFlights().add(flightF504);
        
        // Setup: Reservations: R504-A, R504-B status = CONFIRMED; R504-C status = CANCELED; R504-D status = PENDING
        Booking booking1 = new Booking();
        Reservation reservationA = createReservation("R504-A", "PassengerA", flightF504, "confirmed");
        booking1.addReservation(reservationA);
        
        Booking booking2 = new Booking();
        Reservation reservationB = createReservation("R504-B", "PassengerB", flightF504, "confirmed");
        booking2.addReservation(reservationB);
        
        Booking booking3 = new Booking();
        Reservation reservationC = createReservation("R504-C", "PassengerC", flightF504, "canceled");
        booking3.addReservation(reservationC);
        
        Booking booking4 = new Booking();
        Reservation reservationD = createReservation("R504-D", "PassengerD", flightF504, "pending");
        booking4.addReservation(reservationD);
        
        airlineSystem.getBookings().add(booking1);
        airlineSystem.getBookings().add(booking2);
        airlineSystem.getBookings().add(booking3);
        airlineSystem.getBookings().add(booking4);
        
        // Input: Retrieve confirmed list for flight F504
        List<Reservation> result = airlineSystem.getConfirmedReservations("F504");
        
        // Expected Output: R504-A, R504-B
        assertEquals(2, result.size());
        assertTrue(result.contains(reservationA));
        assertTrue(result.contains(reservationB));
        assertFalse(result.contains(reservationC));
        assertFalse(result.contains(reservationD));
    }
    
    private Reservation createReservation(String id, String passengerName, Flight flight, String status) {
        Reservation reservation = new Reservation();
        reservation.setId(id);
        reservation.setPassengerName(passengerName);
        reservation.setFlight(flight);
        reservation.setStatus(status);
        return reservation;
    }
}