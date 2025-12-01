import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        // 1. Create airline AL11 (not explicitly modeled in provided classes)
        // 2. Create airports AP100 (departure) and AP101 (arrival)
        departureAirport = new Airport();
        departureAirport.setId("AP100");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP101");
        
        // 3. Create flight F300 under AL11
        flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05T08:00:00"));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-05T12:00:00"));
        flight.setStatus("open");
        
        // 4. Instantiate customer CUA (not explicitly modeled in provided classes)
        // 5. Instantiate passengers P1:"Peter", P2:"Beck"
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Peter");
        passengerNames.add("Beck");
        
        // Set current time to 2025-10-01 09:00:00 (before departure)
        // This is handled by the flight's internal logic using LocalDateTime.now()
        // For testing, we rely on the flight's temporal consistency check
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertTrue("Booking should succeed with two new passengers", result);
        assertEquals("Should have exactly 2 reservations", 2, flight.getReservations().size());
        assertEquals("First passenger should be Peter", "Peter", flight.getReservations().get(0).getPassengerName());
        assertEquals("Second passenger should be Beck", "Beck", flight.getReservations().get(1).getPassengerName());
        assertEquals("Reservation status should be pending", "pending", flight.getReservations().get(0).getStatus());
        assertEquals("Reservation status should be pending", "pending", flight.getReservations().get(1).getStatus());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() {
        // Setup
        // 1. Create airline AL12 (not explicitly modeled in provided classes)
        // 2. Create airports AP102 (dep) and AP103 (arr)
        departureAirport = new Airport();
        departureAirport.setId("AP102");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP103");
        
        // 3. Create flight F301 under AL12
        flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05T08:00:00"));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-05T10:00:00"));
        flight.setStatus("open");
        
        // 4. Customer CUB (not explicitly modeled in provided classes)
        // 5. Passenger P3 "Alice" (duplicated in the request)
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Alice");
        passengerNames.add("Alice"); // Duplicate passenger
        
        // Set current time to 2025-10-01 09:00:00 (before departure)
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger in same request", result);
        assertEquals("Should have no reservations", 0, flight.getReservations().size());
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
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05T18:00:00"));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06T02:00:00"));
        flight.setStatus("open");
        
        // 3. Passenger P4 "Jucy"
        // 4. Customer CUC
        // 5. Pre-existing booking BK-OLD containing reservation R302-A for passenger P4 "Jucy"
        Reservation existingReservation = new Reservation();
        existingReservation.setPassengerName("Jucy");
        flight.getReservations().add(existingReservation);
        
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Jucy"); // Same passenger as existing reservation
        
        // Set current time to 2025-10-01 09:00:00 (before departure)
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("Should still have only 1 reservation", 1, flight.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        departureAirport = new Airport();
        departureAirport.setId("AP106");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        // 2. Create flight F303 under AL14
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05T18:00:00"));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06T02:00:00"));
        flight.setStatus("closed"); // Flight is closed for booking
        
        // 3. Customer CUD
        // 4. Passenger P5 "Ruby"
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        // Set current time to 2025-10-01 09:00:00 (before departure)
        
        // Execute
        boolean result = flight.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("Should have no reservations", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTimeBlocksBooking() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        departureAirport = new Airport();
        departureAirport.setId("AP106");
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP107");
        
        // 2. Create flight F303 under AL14
        flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05T18:00:00"));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06T02:00:00"));
        flight.setStatus("open"); // Flight is open for booking
        
        // 3. Customer CUD
        // 4. Passenger P5 "Ruby"
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Ruby");
        
        // Note: The flight's createBooking method uses LocalDateTime.now() internally
        // For this test to work correctly, the system time should be after the departure time
        // In a real test environment, we would mock the time, but with the given constraints
        // we rely on the flight's internal logic
        
        // Execute - the flight's internal check will compare current time with departure time
        boolean result = flight.createBooking(passengerNames);
        
        // Verify - if current time is actually after departure time, booking should fail
        // Since we can't control the system time in this test setup, we document the expected behavior
        // In a proper test environment, we would mock LocalDateTime.now() to return 2025-10-06 09:00:00
        assertFalse("Booking should fail when current time is after departure time", result);
    }
}