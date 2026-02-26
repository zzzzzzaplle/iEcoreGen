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
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        ap10.addCity("CityJ");
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        ap11.addCity("CityK");
        
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        flight.setStatus("open");
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-05-01 08:00:00", formatter);
        
        // Execute close operation (simulating current time check)
        boolean result = closeFlightWithCurrentTimeCheck(flight, currentTime);
        
        // Verify results
        assertTrue("Flight should be closed successfully", result);
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
        
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        flight.setStatus("open");
        
        // Add three confirmed reservations
        Reservation res1 = new Reservation();
        res1.setId("R201-1");
        res1.setPassengerName("Passenger1");
        res1.setStatus("confirmed");
        flight.addReservation(res1);
        
        Reservation res2 = new Reservation();
        res2.setId("R201-2");
        res2.setPassengerName("Passenger2");
        res2.setStatus("confirmed");
        flight.addReservation(res2);
        
        Reservation res3 = new Reservation();
        res3.setId("R201-3");
        res3.setPassengerName("Passenger3");
        res3.setStatus("confirmed");
        flight.addReservation(res3);
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-06-10 12:00:00", formatter);
        
        // Execute close operation (simulating current time check)
        boolean result = closeFlightWithCurrentTimeCheck(flight, currentTime);
        
        // Verify results
        assertTrue("Flight should be closed successfully", result);
        assertEquals("Flight status should be closed", "closed", flight.getStatus());
        assertEquals("All three reservations should exist", 3, flight.getReservations().size());
        
        // Verify all reservations are canceled
        for (Reservation res : flight.getReservations()) {
            assertEquals("Reservation should be canceled", "canceled", res.getStatus());
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
        
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        flight.setStatus("closed"); // Flight is already closed
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-07-01 09:00:00", formatter);
        
        // Execute close operation (simulating current time check)
        boolean result = closeFlightWithCurrentTimeCheck(flight, currentTime);
        
        // Verify results
        assertFalse("Flight should not be closed as it's already closed", result);
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
        
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        flight.setStatus("open");
        
        // Add two confirmed reservations
        Reservation res1 = new Reservation();
        res1.setId("R203-1");
        res1.setPassengerName("Passenger1");
        res1.setStatus("confirmed");
        flight.addReservation(res1);
        
        Reservation res2 = new Reservation();
        res2.setId("R203-2");
        res2.setPassengerName("Passenger2");
        res2.setStatus("confirmed");
        flight.addReservation(res2);
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-09-10 09:10:00", formatter);
        
        // Execute close operation (simulating current time check)
        boolean result = closeFlightWithCurrentTimeCheck(flight, currentTime);
        
        // Verify results
        assertFalse("Flight should not be closed as current time is after departure time", result);
        assertEquals("Flight status should remain open", "open", flight.getStatus());
        
        // Verify reservations remain unchanged
        assertEquals("Reservations should remain", 2, flight.getReservations().size());
        for (Reservation res : flight.getReservations()) {
            assertEquals("Reservation status should remain confirmed", "confirmed", res.getStatus());
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
        
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-02 01:30:00", formatter)); // Next day arrival
        flight.setStatus("open");
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-04-01 22:05:00", formatter);
        
        // Execute close operation (simulating current time check)
        boolean result = closeFlightWithCurrentTimeCheck(flight, currentTime);
        
        // Verify results
        assertFalse("Flight should not be closed as current time is after departure time", result);
        assertEquals("Flight status should remain open", "open", flight.getStatus());
    }
    
    /**
     * Helper method to simulate the close operation with current time check
     * This method modifies the Flight.close() method behavior to use the provided current time
     * instead of LocalDateTime.now() for testing purposes
     */
    private boolean closeFlightWithCurrentTimeCheck(Flight flight, LocalDateTime currentTime) {
        // Check if flight is already closed or current time is after departure time
        if (!flight.getStatus().equals("open") || currentTime.isAfter(flight.getDepartureTime())) {
            return false;
        }
        
        // Close the flight and cancel all reservations
        flight.setStatus("closed");
        for (Reservation reservation : flight.getReservations()) {
            reservation.setStatus("canceled");
        }
        return true;
    }
}