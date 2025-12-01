import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CR3Test {
    
    private Flight flight;
    private Reservation reservation;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() {
        // Setup
        Airport ap160 = new Airport();
        ap160.setId("AP160");
        ap160.addCity("CityAA");
        
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        ap161.addCity("CityAB");
        
        flight = new Flight();
        flight.setId("F401");
        flight.setDepartureAirport(ap160);
        flight.setArrivalAirport(ap161);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 15:00:00", formatter));
        flight.setStatus("open");
        
        reservation = new Reservation();
        reservation.setId("R401");
        reservation.setPassengerName("P9");
        reservation.setStatus("pending");
        flight.addReservation(reservation);
        
        // Set current time to 2025-11-01 09:00:00 (before departure)
        // In actual implementation, you might need to mock LocalDateTime.now()
        // For this test, we'll rely on the fact that departure time is in the future
        
        // Test
        boolean result = flight.updateReservation("R401", true);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be confirmed", "confirmed", reservation.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() {
        // Setup
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        ap170.addCity("CityAC");
        
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        ap171.addCity("CityAD");
        
        flight = new Flight();
        flight.setId("F402");
        flight.setDepartureAirport(ap170);
        flight.setArrivalAirport(ap171);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-15 15:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-15 19:00:00", formatter));
        flight.setStatus("open");
        
        reservation = new Reservation();
        reservation.setId("R402");
        reservation.setPassengerName("P10");
        reservation.setStatus("confirmed");
        flight.addReservation(reservation);
        
        // Set current time to 2025-12-01 12:00:00 (before departure)
        // In actual implementation, you might need to mock LocalDateTime.now()
        // For this test, we'll rely on the fact that departure time is in the future
        
        // Test
        boolean result = flight.updateReservation("R402", false);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be canceled", "canceled", reservation.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() {
        // Setup
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        ap180.addCity("CityAE");
        
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        ap181.addCity("CityAF");
        
        flight = new Flight();
        flight.setId("F403");
        flight.setDepartureAirport(ap180);
        flight.setArrivalAirport(ap181);
        
        // Set departure time to past time (flight already departed)
        LocalDateTime pastDepartureTime = LocalDateTime.parse("2025-01-05 06:00:00", formatter);
        flight.setDepartureTime(pastDepartureTime);
        flight.setArrivalTime(LocalDateTime.parse("2025-01-05 10:00:00", formatter));
        flight.setStatus("open");
        
        reservation = new Reservation();
        reservation.setId("R403");
        reservation.setPassengerName("P11");
        reservation.setStatus("pending");
        flight.addReservation(reservation);
        
        // Set current time to 2025-01-05 07:00:00 (after departure)
        // In actual implementation, you would need to mock LocalDateTime.now() to return this time
        // For this test, we'll test the logic directly by calling the method
        
        // Test
        boolean result = flight.updateReservation("R403", true);
        
        // Verify
        assertFalse("Reservation confirmation should fail when flight has departed", result);
        assertEquals("Reservation status should remain pending", "pending", reservation.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() {
        // Setup
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        ap190.addCity("CityAG");
        
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        ap191.addCity("CityAH");
        
        flight = new Flight();
        flight.setId("F404");
        flight.setDepartureAirport(ap190);
        flight.setArrivalAirport(ap191);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-01 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-01 13:00:00", formatter));
        flight.setStatus("closed"); // Flight is closed for booking
        
        reservation = new Reservation();
        reservation.setId("R404");
        reservation.setPassengerName("P12");
        reservation.setStatus("confirmed");
        flight.addReservation(reservation);
        
        // Set current time to 2025-01-20 08:00:00 (before departure but flight is closed)
        // In actual implementation, you might need to mock LocalDateTime.now()
        // For this test, we'll rely on the flight status being "closed"
        
        // Test
        boolean result = flight.updateReservation("R404", false);
        
        // Verify
        assertFalse("Reservation cancellation should fail when flight is closed", result);
        assertEquals("Reservation status should remain confirmed", "confirmed", reservation.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() {
        // Setup
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        ap200.addCity("CityAI");
        
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        ap201.addCity("CityAJ");
        
        flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirport(ap200);
        flight.setArrivalAirport(ap201);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-10 14:00:00", formatter));
        flight.setStatus("open");
        
        // Add reservation R405 for passenger P13
        Reservation reservation405 = new Reservation();
        reservation405.setId("R405");
        reservation405.setPassengerName("P13");
        reservation405.setStatus("pending");
        flight.addReservation(reservation405);
        
        // Add reservation R406 for passenger P14 (different customer)
        Reservation reservation406 = new Reservation();
        reservation406.setId("R406");
        reservation406.setPassengerName("P14");
        reservation406.setStatus("pending");
        flight.addReservation(reservation406);
        
        // Set current time to 2025-02-15 09:00:00 (before departure)
        // In actual implementation, you might need to mock LocalDateTime.now()
        // For this test, we'll rely on the fact that departure time is in the future
        
        // Test - Try to update with non-existent reservation ID
        boolean result = flight.updateReservation("R999", true);
        
        // Verify
        assertFalse("Should return false for unknown reservation ID", result);
    }
}