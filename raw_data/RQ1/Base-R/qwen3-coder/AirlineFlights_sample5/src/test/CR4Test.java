import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Customer customer;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        departureAirport = new Airport();
        arrivalAirport = new Airport();
        customer = new Customer();
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup
        departureAirport.setId("AP10");
        departureAirport.addCity("CityJ");
        
        arrivalAirport.setId("AP11");
        arrivalAirport.addCity("CityK");
        
        flight = new Flight();
        flight.setId("F200");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        // Current time = 2025-05-01 08:00:00 (before departure)
        // No reservations added
        
        // Execute
        boolean result = flight.closeFlight();
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertEquals("Flight status should be CLOSED", FlightStatus.CLOSED, flight.getStatus());
        assertEquals("No reservations should be canceled", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup
        departureAirport.setId("AP12");
        departureAirport.addCity("CityL");
        
        arrivalAirport.setId("AP13");
        arrivalAirport.addCity("CityM");
        
        flight = new Flight();
        flight.setId("F201");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        // Create three passengers and reservations
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Passenger passenger = new Passenger();
            passenger.setName("Passenger" + i);
            passengers.add(passenger);
        }
        
        // Customer makes booking and confirms reservations
        boolean bookingResult = customer.createBooking(flight, passengers);
        assertTrue("Booking should be created successfully", bookingResult);
        
        // Confirm all reservations
        for (Reservation reservation : flight.getReservations()) {
            boolean updateResult = customer.updateReservationStatus(reservation.getId(), true);
            assertTrue("Reservation should be confirmed", updateResult);
        }
        
        // Verify all reservations are confirmed before closing
        assertEquals("All three reservations should be confirmed", 3, flight.getConfirmedReservations().size());
        
        // Current time = 2025-06-10 12:00:00 (before departure)
        
        // Execute
        boolean result = flight.closeFlight();
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertEquals("Flight status should be CLOSED", FlightStatus.CLOSED, flight.getStatus());
        
        // Check that all confirmed reservations are now canceled
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("All reservations should be canceled", ReservationStatus.CANCELED, reservation.getStatus());
        }
        
        // Verify no confirmed reservations remain
        assertEquals("No confirmed reservations should remain", 0, flight.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup
        departureAirport.setId("AP14");
        departureAirport.addCity("CityN");
        
        arrivalAirport.setId("AP15");
        arrivalAirport.addCity("CityO");
        
        flight = new Flight();
        flight.setId("F202");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        flight.setStatus(FlightStatus.CLOSED); // Flight already closed
        
        // Current time = 2025-07-01 09:00:00 (before departure, but flight is closed)
        
        // Execute
        boolean result = flight.closeFlight();
        
        // Verify
        assertFalse("Should return false for already closed flight", result);
        assertEquals("Flight status should remain CLOSED", FlightStatus.CLOSED, flight.getStatus());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup
        departureAirport.setId("AP16");
        departureAirport.addCity("CityP");
        
        arrivalAirport.setId("AP17");
        arrivalAirport.addCity("CityQ");
        
        flight = new Flight();
        flight.setId("F203");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        // Create two confirmed reservations
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Passenger passenger = new Passenger();
            passenger.setName("Passenger" + i);
            passengers.add(passenger);
        }
        
        // Customer makes booking and confirms reservations
        customer.createBooking(flight, passengers);
        for (Reservation reservation : flight.getReservations()) {
            customer.updateReservationStatus(reservation.getId(), true);
        }
        
        // Current time = 2025-09-10 09:10:00 (after departure time)
        // Note: The closeFlight method doesn't check current time vs departure time,
        // but the test specification expects false
        
        // Execute
        boolean result = flight.closeFlight();
        
        // Verify
        assertFalse("Should return false when closing after departure time", result);
        
        // Reservations should remain confirmed since flight wasn't closed
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("Reservations should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup
        departureAirport.setId("AP18");
        departureAirport.addCity("CityR");
        
        arrivalAirport.setId("AP19");
        arrivalAirport.addCity("CityS");
        
        flight = new Flight();
        flight.setId("F204");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-02 01:30:00", formatter));
        flight.setStatus(FlightStatus.OPEN);
        
        // Current time = 2025-04-01 22:05:00 (after departure)
        // Note: The closeFlight method doesn't check current time vs departure time,
        // but the test specification expects false
        
        // Execute
        boolean result = flight.closeFlight();
        
        // Verify
        assertFalse("Should return false when closing after departure", result);
    }
}