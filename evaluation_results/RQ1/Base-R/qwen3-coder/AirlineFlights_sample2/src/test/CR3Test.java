import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR3Test {
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() {
        // Setup airports
        Airport airportAP160 = new Airport();
        airportAP160.setId("AP160");
        airportAP160.setCities(Arrays.asList("CityAA"));
        
        Airport airportAP161 = new Airport();
        airportAP161.setId("AP161");
        airportAP161.setCities(Arrays.asList("CityAB"));
        
        // Setup flight
        Flight flightF401 = new Flight();
        flightF401.setId("F401");
        flightF401.setDepartureAirport(airportAP160);
        flightF401.setArrivalAirport(airportAP161);
        flightF401.setDepartureTime(LocalDateTime.parse("2025-12-10 11:00:00", formatter));
        flightF401.setArrivalTime(LocalDateTime.parse("2025-12-10 15:00:00", formatter));
        flightF401.setOpenForBooking(true);
        
        // Add flight to system
        airlineSystem.getFlights().add(flightF401);
        
        // Setup booking with pending reservation
        Booking bookingBK401 = new Booking();
        Reservation reservationR401 = new Reservation();
        reservationR401.setId("R401");
        reservationR401.setPassengerName("P9");
        reservationR401.setFlight(flightF401);
        reservationR401.setStatus("pending");
        
        bookingBK401.addReservation(reservationR401);
        airlineSystem.getBookings().add(bookingBK401);
        
        // Set current time (mocked by ensuring flight departure time is in future)
        // The method checks LocalDateTime.now() internally, but our setup ensures
        // flight departure time is in the future relative to when test runs
        
        // Test: confirm reservation R401
        boolean result = airlineSystem.confirmOrCancelReservation("R401", true);
        
        // Verify reservation is confirmed
        assertTrue("Reservation should be successfully confirmed", result);
        assertEquals("Reservation status should be confirmed", "confirmed", reservationR401.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() {
        // Setup airports
        Airport airportAP170 = new Airport();
        airportAP170.setId("AP170");
        airportAP170.setCities(Arrays.asList("CityAC"));
        
        Airport airportAP171 = new Airport();
        airportAP171.setId("AP171");
        airportAP171.setCities(Arrays.asList("CityAD"));
        
        // Setup flight
        Flight flightF402 = new Flight();
        flightF402.setId("F402");
        flightF402.setDepartureAirport(airportAP170);
        flightF402.setArrivalAirport(airportAP171);
        flightF402.setDepartureTime(LocalDateTime.parse("2025-12-15 15:00:00", formatter));
        flightF402.setArrivalTime(LocalDateTime.parse("2025-12-15 19:00:00", formatter));
        flightF402.setOpenForBooking(true);
        
        // Add flight to system
        airlineSystem.getFlights().add(flightF402);
        
        // Setup booking with confirmed reservation
        Booking bookingBK402 = new Booking();
        Reservation reservationR402 = new Reservation();
        reservationR402.setId("R402");
        reservationR402.setPassengerName("P10");
        reservationR402.setFlight(flightF402);
        reservationR402.setStatus("confirmed");
        
        bookingBK402.addReservation(reservationR402);
        airlineSystem.getBookings().add(bookingBK402);
        
        // Test: cancel reservation R402
        boolean result = airlineSystem.confirmOrCancelReservation("R402", false);
        
        // Verify reservation is cancelled
        assertTrue("Reservation should be successfully cancelled", result);
        assertEquals("Reservation status should be canceled", "canceled", reservationR402.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() {
        // Setup airports
        Airport airportAP180 = new Airport();
        airportAP180.setId("AP180");
        airportAP180.setCities(Arrays.asList("CityAE"));
        
        Airport airportAP181 = new Airport();
        airportAP181.setId("AP181");
        airportAP181.setCities(Arrays.asList("CityAF"));
        
        // Setup flight with past departure time
        Flight flightF403 = new Flight();
        flightF403.setId("F403");
        flightF403.setDepartureAirport(airportAP180);
        flightF403.setArrivalAirport(airportAP181);
        flightF403.setDepartureTime(LocalDateTime.parse("2025-01-05 06:00:00", formatter));
        flightF403.setArrivalTime(LocalDateTime.parse("2025-01-05 10:00:00", formatter));
        flightF403.setOpenForBooking(true);
        
        // Add flight to system
        airlineSystem.getFlights().add(flightF403);
        
        // Setup booking with pending reservation
        Booking bookingBK403 = new Booking();
        Reservation reservationR403 = new Reservation();
        reservationR403.setId("R403");
        reservationR403.setPassengerName("P11");
        reservationR403.setFlight(flightF403);
        reservationR403.setStatus("pending");
        
        bookingBK403.addReservation(reservationR403);
        airlineSystem.getBookings().add(bookingBK403);
        
        // Test: confirm reservation R403 (should fail because flight departed)
        boolean result = airlineSystem.confirmOrCancelReservation("R403", true);
        
        // Verify operation fails
        assertFalse("Confirmation should fail when flight has departed", result);
        assertEquals("Reservation status should remain pending", "pending", reservationR403.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() {
        // Setup airports
        Airport airportAP190 = new Airport();
        airportAP190.setId("AP190");
        airportAP190.setCities(Arrays.asList("CityAG"));
        
        Airport airportAP191 = new Airport();
        airportAP191.setId("AP191");
        airportAP191.setCities(Arrays.asList("CityAH"));
        
        // Setup flight that is closed for booking
        Flight flightF404 = new Flight();
        flightF404.setId("F404");
        flightF404.setDepartureAirport(airportAP190);
        flightF404.setArrivalAirport(airportAP191);
        flightF404.setDepartureTime(LocalDateTime.parse("2025-02-01 09:00:00", formatter));
        flightF404.setArrivalTime(LocalDateTime.parse("2025-02-01 13:00:00", formatter));
        flightF404.setOpenForBooking(false); // Flight is closed
        
        // Add flight to system
        airlineSystem.getFlights().add(flightF404);
        
        // Setup booking with confirmed reservation
        Booking bookingBK404 = new Booking();
        Reservation reservationR404 = new Reservation();
        reservationR404.setId("R404");
        reservationR404.setPassengerName("P12");
        reservationR404.setFlight(flightF404);
        reservationR404.setStatus("confirmed");
        
        bookingBK404.addReservation(reservationR404);
        airlineSystem.getBookings().add(bookingBK404);
        
        // Test: cancel reservation R404 (should fail because flight is closed)
        boolean result = airlineSystem.confirmOrCancelReservation("R404", false);
        
        // Verify operation fails
        assertFalse("Cancellation should fail when flight is closed", result);
        assertEquals("Reservation status should remain confirmed", "confirmed", reservationR404.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() {
        // Setup airports
        Airport airportAP200 = new Airport();
        airportAP200.setId("AP200");
        airportAP200.setCities(Arrays.asList("CityAI"));
        
        Airport airportAP201 = new Airport();
        airportAP201.setId("AP201");
        airportAP201.setCities(Arrays.asList("CityAJ"));
        
        // Setup flight
        Flight flightF405 = new Flight();
        flightF405.setId("F405");
        flightF405.setDepartureAirport(airportAP200);
        flightF405.setArrivalAirport(airportAP201);
        flightF405.setDepartureTime(LocalDateTime.parse("2025-03-10 10:00:00", formatter));
        flightF405.setArrivalTime(LocalDateTime.parse("2025-03-10 14:00:00", formatter));
        flightF405.setOpenForBooking(true);
        
        // Add flight to system
        airlineSystem.getFlights().add(flightF405);
        
        // Setup bookings with different reservations
        Booking bookingBK405 = new Booking();
        Reservation reservationR405 = new Reservation();
        reservationR405.setId("R405");
        reservationR405.setPassengerName("P13");
        reservationR405.setFlight(flightF405);
        reservationR405.setStatus("pending");
        
        bookingBK405.addReservation(reservationR405);
        airlineSystem.getBookings().add(bookingBK405);
        
        // Test: confirm unknown reservation R406
        boolean result = airlineSystem.confirmOrCancelReservation("R406", true);
        
        // Verify operation fails for unknown reservation
        assertFalse("Operation should fail for unknown reservation ID", result);
    }
}