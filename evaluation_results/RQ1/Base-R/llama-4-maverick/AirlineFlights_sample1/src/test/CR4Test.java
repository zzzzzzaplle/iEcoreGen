import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup
        Airport airportAP10 = new Airport();
        airportAP10.setId("AP10");
        airportAP10.addCity("CityJ");
        
        Airport airportAP11 = new Airport();
        airportAP11.setId("AP11");
        airportAP11.addCity("CityK");
        
        Flight flightF200 = new Flight();
        flightF200.setId("F200");
        flightF200.setDepartureAirport(airportAP10);
        flightF200.setArrivalAirport(airportAP11);
        flightF200.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        flightF200.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        flightF200.setStatus("open");
        
        // Set current time to 2025-05-01 08:00:00 (before departure)
        // Note: We cannot mock LocalDateTime.now() without external libraries, so we rely on the flight's validation logic
        
        // Execute
        boolean result = flightF200.close();
        
        // Verify
        assertTrue("Flight should close successfully", result);
        assertEquals("Flight status should be 'closed'", "closed", flightF200.getStatus());
        assertEquals("No reservations should exist", 0, flightF200.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup
        Airport airportAP12 = new Airport();
        airportAP12.setId("AP12");
        airportAP12.addCity("CityL");
        
        Airport airportAP13 = new Airport();
        airportAP13.setId("AP13");
        airportAP13.addCity("CityM");
        
        Flight flightF201 = new Flight();
        flightF201.setId("F201");
        flightF201.setDepartureAirport(airportAP12);
        flightF201.setArrivalAirport(airportAP13);
        flightF201.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        flightF201.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        flightF201.setStatus("published");
        
        // Create and add three confirmed reservations
        Reservation reservation1 = new Reservation();
        reservation1.setPassengerName("Passenger1");
        reservation1.setStatus("confirmed");
        flightF201.addReservation(reservation1);
        
        Reservation reservation2 = new Reservation();
        reservation2.setPassengerName("Passenger2");
        reservation2.setStatus("confirmed");
        flightF201.addReservation(reservation2);
        
        Reservation reservation3 = new Reservation();
        reservation3.setPassengerName("Passenger3");
        reservation3.setStatus("confirmed");
        flightF201.addReservation(reservation3);
        
        // Set current time to 2025-06-10 12:00:00 (before departure)
        // Note: We cannot mock LocalDateTime.now() without external libraries, so we rely on the flight's validation logic
        
        // Execute
        boolean result = flightF201.close();
        
        // Verify
        assertTrue("Flight should close successfully", result);
        assertEquals("Flight status should be 'closed'", "closed", flightF201.getStatus());
        assertEquals("Should have 3 reservations", 3, flightF201.getReservations().size());
        
        // Verify all reservations are canceled
        for (Reservation reservation : flightF201.getReservations()) {
            assertEquals("Reservation should be canceled", "canceled", reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup
        Airport airport = new Airport();
        airport.setId("AP");
        airport.addCity("City");
        
        Flight flightF202 = new Flight();
        flightF202.setId("F202");
        flightF202.setDepartureAirport(airport);
        flightF202.setArrivalAirport(airport);
        flightF202.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        flightF202.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        flightF202.setStatus("closed"); // Flight is already closed
        
        // Set current time to 2025-07-01 09:00:00 (before departure)
        // Note: We cannot mock LocalDateTime.now() without external libraries, so we rely on the flight's validation logic
        
        // Execute
        boolean result = flightF202.close();
        
        // Verify
        assertFalse("Flight should not close when already closed", result);
        assertEquals("Flight status should remain 'closed'", "closed", flightF202.getStatus());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup
        Airport airport = new Airport();
        airport.setId("AP");
        airport.addCity("City");
        
        Flight flightF203 = new Flight();
        flightF203.setId("F203");
        flightF203.setDepartureAirport(airport);
        flightF203.setArrivalAirport(airport);
        flightF203.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        flightF203.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        flightF203.setStatus("published");
        
        // Add two confirmed reservations
        Reservation reservation1 = new Reservation();
        reservation1.setPassengerName("Passenger1");
        reservation1.setStatus("confirmed");
        flightF203.addReservation(reservation1);
        
        Reservation reservation2 = new Reservation();
        reservation2.setPassengerName("Passenger2");
        reservation2.setStatus("confirmed");
        flightF203.addReservation(reservation2);
        
        // Set departure time to past (09:00:00) and current time to 09:10:00 (after departure)
        // Note: We cannot mock LocalDateTime.now() without external libraries, so we need to adjust the test
        // Since the flight's close() method checks if current time is after departure time, we need to ensure
        // the departure time is in the past relative to when the test runs
        
        // For this test, we'll set a departure time that is definitely in the past
        flightF203.setDepartureTime(LocalDateTime.now().minusMinutes(10));
        flightF203.setArrivalTime(LocalDateTime.now().plusHours(6));
        
        // Execute
        boolean result = flightF203.close();
        
        // Verify
        assertFalse("Flight should not close after departure time", result);
        assertEquals("Flight status should remain 'published'", "published", flightF203.getStatus());
        
        // Verify reservations remain confirmed (not canceled)
        for (Reservation reservation : flightF203.getReservations()) {
            assertEquals("Reservation should remain confirmed", "confirmed", reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup
        Airport airport = new Airport();
        airport.setId("AP");
        airport.addCity("City");
        
        Flight flightF204 = new Flight();
        flightF204.setId("F204");
        flightF204.setDepartureAirport(airport);
        flightF204.setArrivalAirport(airport);
        
        // Set departure time to past and current time after departure (22:05:00)
        // Note: We cannot mock LocalDateTime.now() without external libraries, so we need to adjust the test
        // Set departure time to definitely be in the past
        flightF204.setDepartureTime(LocalDateTime.now().minusMinutes(10));
        flightF204.setArrivalTime(LocalDateTime.now().plusHours(3));
        flightF204.setStatus("published");
        
        // Execute
        boolean result = flightF204.close();
        
        // Verify
        assertFalse("Flight should not close after departure", result);
        assertEquals("Flight status should remain 'published'", "published", flightF204.getStatus());
    }
}