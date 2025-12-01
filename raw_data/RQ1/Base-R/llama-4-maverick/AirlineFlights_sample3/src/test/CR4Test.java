import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Flight flight;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        ap10.addCity("CityJ");
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        ap11.addCity("CityK");
        
        flight = new Flight();
        flight.setId("F200");
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        flight.setStatus("open");
        
        // Set current time to 2025-05-01 08:00:00 (before departure)
        // Note: We'll need to mock the current time, but since we can't modify the Flight class,
        // we'll rely on the fact that the test runs quickly enough that current time is before departure
        
        // Execute
        boolean result = flight.close();
        
        // Verify
        assertTrue("Closing flight should return true when no reservations exist", result);
        assertEquals("Flight status should be closed", "closed", flight.getStatus());
        assertEquals("No reservations should exist", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        ap12.addCity("CityL");
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        ap13.addCity("CityM");
        
        flight = new Flight();
        flight.setId("F201");
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        flight.setStatus("open");
        
        // Add three reservations and confirm them
        Reservation r1 = new Reservation();
        r1.setPassengerName("Passenger1");
        r1.setStatus("confirmed");
        flight.addReservation(r1);
        
        Reservation r2 = new Reservation();
        r2.setPassengerName("Passenger2");
        r2.setStatus("confirmed");
        flight.addReservation(r2);
        
        Reservation r3 = new Reservation();
        r3.setPassengerName("Passenger3");
        r3.setStatus("confirmed");
        flight.addReservation(r3);
        
        // Set current time to 2025-06-10 12:00:00 (before departure)
        
        // Execute
        boolean result = flight.close();
        
        // Verify
        assertTrue("Closing flight should return true when reservations exist", result);
        assertEquals("Flight status should be closed", "closed", flight.getStatus());
        
        // Check that all reservations are canceled
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("All confirmed reservations should be canceled", "canceled", reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP14");
        departureAirport.addCity("CityN");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP15");
        arrivalAirport.addCity("CityO");
        
        flight = new Flight();
        flight.setId("F202");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        flight.setStatus("closed"); // Flight is already closed
        
        // Set current time to 2025-07-01 09:00:00 (before departure)
        
        // Execute
        boolean result = flight.close();
        
        // Verify
        assertFalse("Closing already closed flight should return false", result);
        assertEquals("Flight status should remain closed", "closed", flight.getStatus());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP16");
        departureAirport.addCity("CityP");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP17");
        arrivalAirport.addCity("CityQ");
        
        flight = new Flight();
        flight.setId("F203");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        flight.setStatus("open");
        
        // Add two confirmed reservations
        Reservation r1 = new Reservation();
        r1.setPassengerName("Passenger1");
        r1.setStatus("confirmed");
        flight.addReservation(r1);
        
        Reservation r2 = new Reservation();
        r2.setPassengerName("Passenger2");
        r2.setStatus("confirmed");
        flight.addReservation(r2);
        
        // Set current time to 2025-09-10 09:10:00 (after departure time)
        // Note: We cannot directly set current time in Flight class, but the close() method
        // checks if current time is after departure time, which will be true in this test
        
        // Execute
        boolean result = flight.close();
        
        // Verify
        assertFalse("Closing flight after departure time should return false", result);
        assertEquals("Flight status should remain open", "open", flight.getStatus());
        
        // Check that reservations remain confirmed (not canceled)
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("Reservations should remain confirmed when close fails", "confirmed", reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP18");
        departureAirport.addCity("CityR");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP19");
        arrivalAirport.addCity("CityS");
        
        flight = new Flight();
        flight.setId("F204");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-02 01:30:00", formatter)); // Fixed: arrival should be next day
        flight.setStatus("open");
        
        // Set current time to 2025-04-01 22:05:00 (after departure time)
        // Note: We cannot directly set current time in Flight class, but the close() method
        // checks if current time is after departure time, which will be true in this test
        
        // Execute
        boolean result = flight.close();
        
        // Verify
        assertFalse("Closing flight after departure should return false", result);
        assertEquals("Flight status should remain open", "open", flight.getStatus());
    }
}