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
    public void testCase1_twoNewPassengersSucceed() {
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
        flight.setStatus("open");
        
        // Create passenger list
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Peter");
        passengerNames.add("Beck");
        
        // Set current time before departure
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Should have 2 reservations", 2, flight.getReservations().size());
        assertTrue("Should contain Peter", flight.getReservations().stream().anyMatch(r -> r.getPassengerName().equals("Peter")));
        assertTrue("Should contain Beck", flight.getReservations().stream().anyMatch(r -> r.getPassengerName().equals("Beck")));
        assertEquals("All reservations should be pending", "pending", flight.getReservations().get(0).getStatus());
        assertEquals("All reservations should be pending", "pending", flight.getReservations().get(1).getStatus());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() {
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
        flight.setStatus("open");
        
        // Create passenger list with duplicate
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Alice");
        passengerNames.add("Alice"); // Duplicate passenger
        
        // Set current time before departure
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger", result);
        assertEquals("Should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() {
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
        flight.setStatus("open");
        
        // Add pre-existing reservation for Jucy
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setPassengerName("Jucy");
        existingReservation.setStatus("pending");
        flight.addReservation(existingReservation);
        
        // Create passenger list with already booked passenger
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Jucy"); // Already booked passenger
        
        // Set current time before departure
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to already booked passenger", result);
        assertEquals("Should still have only 1 reservation", 1, flight.getReservations().size());
        assertEquals("Reservation should still be for Jucy", "Jucy", flight.getReservations().get(0).getPassengerName());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() {
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
        flight.setStatus("closed"); // Flight is closed
        
        // Create passenger list
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        // Set current time before departure
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("Should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_timeAfterDepartureTimeBlocksBooking() {
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
        flight.setStatus("open");
        
        // Create passenger list
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        // Set current time AFTER departure (violates requirement)
        // Note: We cannot directly set current time in Flight class, so we rely on the logic
        // that checks if current time is after departure time
        
        // Execute - this will use the actual current time which may or may not be after departure
        // For proper testing, we'd need to mock the time, but since we can't modify the Flight class,
        // we'll create a flight with departure in the past
        flight.setDepartureTime(LocalDateTime.parse("2020-10-05 18:00:00", formatter)); // Past date
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail because current time is after departure", result);
    }
}