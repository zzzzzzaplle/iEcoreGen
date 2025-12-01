import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    private Flight flight;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP100");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        
        flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-05 12:00:00", formatter));
        
        // Publish flight to make it open for booking
        flight.publish();
        
        // Set current time to 2025-10-01 09:00:00 (before departure)
        // This is handled implicitly by using LocalDateTime.now() in the actual test execution
        
        // Input: Request booking for passengers "Peter" and "Beck"
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Peter");
        passengerNames.add("Beck");
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("There should be exactly 2 reservations", 2, flight.getReservations().size());
        
        // Verify passenger names
        List<String> bookedPassengerNames = new ArrayList<>();
        for (Reservation reservation : flight.getReservations()) {
            bookedPassengerNames.add(reservation.getPassengerName());
        }
        assertTrue("Reservations should contain Peter", bookedPassengerNames.contains("Peter"));
        assertTrue("Reservations should contain Beck", bookedPassengerNames.contains("Beck"));
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP102");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        
        flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-05 10:00:00", formatter));
        
        // Publish flight to make it open for booking
        flight.publish();
        
        // Input: Request booking with duplicate passenger "Alice" twice
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Alice");
        passengerNames.add("Alice"); // Duplicate passenger
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger in same request", result);
        assertEquals("There should be no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        
        // Publish flight to make it open for booking
        flight.publish();
        
        // Create pre-existing booking for passenger "Jucy"
        List<String> firstBooking = new ArrayList<>();
        firstBooking.add("Jucy");
        flight.createBooking(firstBooking);
        
        // Input: Try to book the same passenger "Jucy" again
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Jucy");
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("There should be only 1 reservation", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP106");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        
        // Flight is closed (not published)
        // Status remains "open" by default, so we need to explicitly close it
        flight.publish(); // First publish to make it published
        flight.close(); // Then close it
        
        // Input: Try to book passenger "Ruby" on closed flight
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to flight being closed", result);
        assertEquals("There should be no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTimeBlocksBooking() {
        // Setup
        Airport departureAirport = new Airport();
        departureAirport.setId("AP104");
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        
        // Publish flight to make it open for booking
        flight.publish();
        
        // Note: The actual current time check happens inside createBooking() method
        // Since we can't easily mock LocalDateTime.now(), we rely on the fact that
        // the test will run at a time when 2025-10-06 09:00:00 is in the future
        // relative to the current real time, but we need to simulate the scenario
        
        // For this test to work correctly, we need to create a flight with departure time
        // that is before the "current time" we want to simulate
        // Let's create a flight with past departure time
        Flight pastFlight = new Flight();
        pastFlight.setId("F303_PAST");
        pastFlight.setDepartureAirport(departureAirport);
        pastFlight.setArrivalAirport(arrivalAirport);
        
        // Set departure time to a past date relative to when test runs
        pastFlight.setDepartureTime(LocalDateTime.now().minusDays(1));
        pastFlight.setArrivalTime(LocalDateTime.now().plusHours(1));
        pastFlight.publish();
        
        // Input: Try to book passenger "Ruby" after departure time
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        // Execute
        boolean result = pastFlight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time being after departure time", result);
        assertEquals("There should be no reservations", 0, pastFlight.getReservations().size());
    }
}