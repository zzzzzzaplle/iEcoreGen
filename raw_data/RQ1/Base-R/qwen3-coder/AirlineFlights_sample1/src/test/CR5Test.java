import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        // Setup
        Flight flightF501 = createFlight("F501", true);
        airlineSystem.getFlights().add(flightF501);
        
        // Create and add reservations
        Reservation r5011 = createReservation(flightF501, "CONFIRMED");
        Reservation r5012 = createReservation(flightF501, "CONFIRMED");
        Reservation r5013 = createReservation(flightF501, "CONFIRMED");
        
        Booking booking = new Booking();
        booking.addReservation(r5011);
        booking.addReservation(r5012);
        booking.addReservation(r5013);
        airlineSystem.getBookings().add(booking);
        
        // Execute
        List<Reservation> result = airlineSystem.getConfirmedReservations("F501");
        
        // Verify
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        assertTrue("Should contain R501-1", result.contains(r5011));
        assertTrue("Should contain R501-2", result.contains(r5012));
        assertTrue("Should contain R501-3", result.contains(r5013));
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup
        Flight flightF502 = createFlight("F502", true);
        airlineSystem.getFlights().add(flightF502);
        
        // Create and add pending reservations
        Reservation r5021 = createReservation(flightF502, "PENDING");
        Reservation r5022 = createReservation(flightF502, "PENDING");
        
        Booking booking = new Booking();
        booking.addReservation(r5021);
        booking.addReservation(r5022);
        airlineSystem.getBookings().add(booking);
        
        // Execute
        List<Reservation> result = airlineSystem.getConfirmedReservations("F502");
        
        // Verify
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup
        Flight flightF503 = createFlight("F503", false); // closed for booking
        airlineSystem.getFlights().add(flightF503);
        
        // Create and add confirmed reservation
        Reservation r5031 = createReservation(flightF503, "CONFIRMED");
        
        Booking booking = new Booking();
        booking.addReservation(r5031);
        airlineSystem.getBookings().add(booking);
        
        // Execute
        List<Reservation> result = airlineSystem.getConfirmedReservations("F503");
        
        // Verify
        assertTrue("Should return empty list when flight is closed", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup
        Flight flightF504 = createFlight("F504", true);
        Flight flightF505 = createFlight("F505", true);
        airlineSystem.getFlights().add(flightF504);
        airlineSystem.getFlights().add(flightF505);
        
        // Execute - query for non-existent flight
        List<Reservation> result = airlineSystem.getConfirmedReservations("FX999");
        
        // Verify
        assertTrue("Should return empty list for unknown flight ID", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup
        Flight flightF504 = createFlight("F504", true);
        airlineSystem.getFlights().add(flightF504);
        
        // Create reservations with mixed statuses
        Reservation r504A = createReservation(flightF504, "CONFIRMED");
        Reservation r504B = createReservation(flightF504, "CONFIRMED");
        Reservation r504C = createReservation(flightF504, "CANCELED");
        Reservation r504D = createReservation(flightF504, "PENDING");
        
        Booking booking = new Booking();
        booking.addReservation(r504A);
        booking.addReservation(r504B);
        booking.addReservation(r504C);
        booking.addReservation(r504D);
        airlineSystem.getBookings().add(booking);
        
        // Execute
        List<Reservation> result = airlineSystem.getConfirmedReservations("F504");
        
        // Verify
        assertEquals("Should return only 2 confirmed reservations", 2, result.size());
        assertTrue("Should contain R504-A", result.contains(r504A));
        assertTrue("Should contain R504-B", result.contains(r504B));
        assertFalse("Should not contain canceled reservation", result.contains(r504C));
        assertFalse("Should not contain pending reservation", result.contains(r504D));
    }
    
    // Helper method to create a flight with basic configuration
    private Flight createFlight(String flightId, boolean openForBooking) {
        Flight flight = new Flight();
        flight.setId(flightId);
        flight.setOpenForBooking(openForBooking);
        
        // Set departure and arrival airports with different IDs
        Airport departure = new Airport();
        departure.setId("DEP" + flightId);
        departure.setCities(List.of("CityA"));
        
        Airport arrival = new Airport();
        arrival.setId("ARR" + flightId);
        arrival.setCities(List.of("CityB"));
        
        flight.setDepartureAirport(departure);
        flight.setArrivalAirport(arrival);
        
        // Set future departure and arrival times
        flight.setDepartureTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(3));
        
        return flight;
    }
    
    // Helper method to create a reservation with specified status
    private Reservation createReservation(Flight flight, String status) {
        Reservation reservation = new Reservation();
        reservation.setFlight(flight);
        
        Passenger passenger = new Passenger();
        passenger.setName("Passenger_" + System.currentTimeMillis() + "_" + Math.random());
        reservation.setPassenger(passenger);
        
        // Set status based on input
        switch (status) {
            case "CONFIRMED":
                reservation.confirm();
                break;
            case "CANCELED":
                reservation.cancel();
                break;
            case "PENDING":
                // Default is pending, no action needed
                break;
        }
        
        return reservation;
    }
}