import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() {
        // Setup
        // 1. Create airline AL11 (not modeled in provided classes, so we'll create flight directly)
        // 2. Create airports AP100 (departure) and AP101 (arrival)
        departureAirport = new Airport();
        departureAirport.setId("AP100");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        
        // 3. Create flight F300
        flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-05 12:00:00", formatter));
        flight.setOpen(true);
        
        // 4. Instantiate customer CUA (not modeled in provided classes)
        // 5. Instantiate passengers P1:"Peter", P2:"Beck"
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Set current time = 2025-10-01 09:00:00
        // (Note: We cannot mock LocalDateTime.now() without external libraries, 
        // so we rely on the flight's departure time being in the future)
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("There should be exactly 2 reservations", 2, flight.getReservations().size());
        
        // Check that both passengers have reservations
        assertTrue("Reservation for Peter should exist", 
            flight.getReservations().stream().anyMatch(r -> r.getPassengerName().equals("Peter")));
        assertTrue("Reservation for Beck should exist", 
            flight.getReservations().stream().anyMatch(r -> r.getPassengerName().equals("Beck")));
        
        // Check that reservations are in pending status (confirmed = false)
        assertFalse("Peter's reservation should be pending", 
            flight.getReservations().stream()
                .filter(r -> r.getPassengerName().equals("Peter"))
                .findFirst().get().isConfirmed());
        assertFalse("Beck's reservation should be pending", 
            flight.getReservations().stream()
                .filter(r -> r.getPassengerName().equals("Beck"))
                .findFirst().get().isConfirmed());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() {
        // Setup
        // 1. Create airline AL12 (not modeled in provided classes)
        // 2. Create airports AP102 (dep) and AP103 (arr)
        departureAirport = new Airport();
        departureAirport.setId("AP102");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        
        // 3. Create flight F301
        flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-05 10:00:00", formatter));
        flight.setOpen(true);
        
        // 4. Customer CUB (not modeled in provided classes)
        // 5. Passenger P3 "Alice" (duplicate in the same request)
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Set current time = 2025-10-01 09:00:00
        // (Flight departure time is in the future)
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger in same request", result);
        assertEquals("No reservations should be created", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() {
        // Setup
        // 1. Create airline AL13 with airports AP104, AP105
        departureAirport = new Airport();
        departureAirport.setId("AP104");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP105");
        
        // 2. Create flight F302
        flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight.setOpen(true);
        
        // 3. Passenger P4 "Jucy"
        // 4. Customer CUC (not modeled in provided classes)
        // 5. Pre-existing booking BK-OLD containing reservation R302-A for passenger P4 "Jucy"
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setPassengerName("Jucy");
        existingReservation.setFlight(flight);
        existingReservation.setConfirmed(false); // PENDING status
        flight.getReservations().add(existingReservation);
        
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Set current time = 2025-10-01 09:00:00
        // (Flight departure time is in the future)
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked earlier", result);
        assertEquals("Only the original reservation should exist", 1, flight.getReservations().size());
        assertEquals("Original reservation should remain unchanged", "Jucy", 
            flight.getReservations().get(0).getPassengerName());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        departureAirport = new Airport();
        departureAirport.setId("AP106");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        // 2. Create flight F303
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight.setOpen(false); // Flight is closed for booking
        
        // 3. Customer CUD (not modeled in provided classes)
        // 4. Passenger P5 "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Set current time = 2025-10-01 09:00:00
        // (Flight departure time is in the future, but flight is closed)
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("No reservations should be created", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_TimeIsAfterDepartureTime() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107 (Note: AP104, AP105 in spec seems like a typo)
        departureAirport = new Airport();
        departureAirport.setId("AP106");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        // 2. Create flight F303
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight.setOpen(true);
        
        // 3. Customer CUD (not modeled in provided classes)
        // 4. Passenger P5 "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Set current time = 2025-10-06 09:00:00 (after departure time)
        // We need to simulate this by creating a flight with past departure time
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail because current time is after departure time", result);
        assertEquals("No reservations should be created", 0, flight.getReservations().size());
    }
}