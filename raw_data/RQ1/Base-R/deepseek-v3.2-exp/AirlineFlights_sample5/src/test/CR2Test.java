import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() {
        // Setup
        // 1. Create airline AL11 (implicitly created through AirlineSystem)
        // 2. Create airports AP100 (departure) and AP101 (arrival)
        Airport ap100 = new Airport();
        ap100.setId("AP100");
        ap100.setName("Departure Airport");
        
        Airport ap101 = new Airport();
        ap101.setId("AP101");
        ap101.setName("Arrival Airport");
        
        // 3. Create flight F300 under AL11
        Flight f300 = new Flight();
        f300.setId("F300");
        f300.setDepartureAirport(ap100);
        f300.setArrivalAirport(ap101);
        f300.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        f300.setArrivalTime(LocalDateTime.parse("2025-10-05 12:00:00", formatter));
        f300.setOpenForBooking(true);
        f300.setPublished(true); // Flight must be published for booking
        
        airlineSystem.getFlights().add(f300);
        
        // 4. Instantiate customer CUA (implicit)
        // 5. Instantiate passengers P1:"Peter", P2:"Beck"
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Input: Request booking on flight F300 for passenger list P1:"Peter", P2:"Beck" 
        // by customer CUA, current time = 2025-10-01 09:00:00
        // Note: We need to mock the current time to be 2025-10-01 09:00:00
        // Since we cannot modify the LocalDateTime.now() in the original code, 
        // we'll assume the test environment is set up with this time
        
        // Execute
        boolean result = airlineSystem.createBooking(f300, passengerNames);
        
        // Verify
        assertTrue("Booking should be successful for two new passengers", result);
        assertEquals("Should have 2 reservations created", 2, airlineSystem.getReservations().size());
        assertEquals("First reservation should be for Peter", "Peter", airlineSystem.getReservations().get(0).getPassengerName());
        assertEquals("Second reservation should be for Beck", "Beck", airlineSystem.getReservations().get(1).getPassengerName());
        assertEquals("Reservation status should be pending", "pending", airlineSystem.getReservations().get(0).getStatus());
        assertEquals("Booking should be created", 1, airlineSystem.getBookings().size());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() {
        // Setup
        // 1. Create airline AL12 (implicitly created through AirlineSystem)
        // 2. Create airports AP102 (dep) and AP103 (arr)
        Airport ap102 = new Airport();
        ap102.setId("AP102");
        ap102.setName("Departure Airport");
        
        Airport ap103 = new Airport();
        ap103.setId("AP103");
        ap103.setName("Arrival Airport");
        
        // 3. Create flight F301 under AL12
        Flight f301 = new Flight();
        f301.setId("F301");
        f301.setDepartureAirport(ap102);
        f301.setArrivalAirport(ap103);
        f301.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        f301.setArrivalTime(LocalDateTime.parse("2025-10-05 10:00:00", formatter));
        f301.setOpenForBooking(true);
        f301.setPublished(true);
        
        airlineSystem.getFlights().add(f301);
        
        // 4. Customer CUB (implicit)
        // 5. Passenger P3 "Alice" (duplicate in the same request)
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Input: Request booking on flight F301 for passenger P3: "Alice" P3: "Alice" 
        // by customer CUB, current time = 2025-10-01 09:00:00
        
        // Execute
        boolean result = airlineSystem.createBooking(f301, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passengers", result);
        assertEquals("No reservations should be created", 0, airlineSystem.getReservations().size());
        assertEquals("No bookings should be created", 0, airlineSystem.getBookings().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() {
        // Setup
        // 1. Create airline AL13 with airports AP104, AP105
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        ap104.setName("Departure Airport");
        
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        ap105.setName("Arrival Airport");
        
        // 2. Create flight F302
        Flight f302 = new Flight();
        f302.setId("F302");
        f302.setDepartureAirport(ap104);
        f302.setArrivalAirport(ap105);
        f302.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f302.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f302.setOpenForBooking(true);
        f302.setPublished(true);
        
        airlineSystem.getFlights().add(f302);
        
        // 3. Passenger P4 "Jucy"
        // 4. Customer CUC
        // 5. Customer CUC: Pre-existing booking BK-OLD containing reservation R302-A 
        // for passenger P4 "Jucy" on F302 (status = PENDING)
        
        // Create pre-existing reservation
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setFlight(f302);
        existingReservation.setPassengerName("Jucy");
        existingReservation.setStatus("pending");
        
        Booking existingBooking = new Booking();
        existingBooking.setId("BK-OLD");
        existingBooking.setFlight(f302);
        existingBooking.getReservations().add(existingReservation);
        
        airlineSystem.getBookings().add(existingBooking);
        airlineSystem.getReservations().add(existingReservation);
        
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Input: Request booking on flight F302 for passenger "Jucy" by customer CUC, 
        // current time = 2025-10-01 09:00:00
        
        // Execute
        boolean result = airlineSystem.createBooking(f302, passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger on same flight", result);
        assertEquals("Should still have only 1 reservation", 1, airlineSystem.getReservations().size());
        assertEquals("Should still have only 1 booking", 1, airlineSystem.getBookings().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        ap106.setName("Departure Airport");
        
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        ap107.setName("Arrival Airport");
        
        // 2. Create flight F303 under AL14
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f303.setOpenForBooking(false); // Flight is closed for booking
        f303.setPublished(true);
        
        airlineSystem.getFlights().add(f303);
        
        // 3. Customer CUD
        // 4. Passenger P5 "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Input: Request booking on flight F303 for passenger list "Ruby" by customer CUD, 
        // current time = 2025-10-01 09:00:00
        
        // Execute
        boolean result = airlineSystem.createBooking(f303, passengerNames);
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("No reservations should be created", 0, airlineSystem.getReservations().size());
        assertEquals("No bookings should be created", 0, airlineSystem.getBookings().size());
    }
    
    @Test
    public void testCase5_TheTimeIsAfterTheDepartureTime() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        ap106.setName("Departure Airport");
        
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        ap107.setName("Arrival Airport");
        
        // 2. Create flight F303 under AL14
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f303.setOpenForBooking(true);
        f303.setPublished(true);
        
        airlineSystem.getFlights().add(f303);
        
        // 3. Customer CUD
        // 4. Passenger P5 "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Input: Request booking on flight F303 for passenger list "Ruby" by customer CUD, 
        // current time = 2025-10-06 09:00:00 (which is after departure time 2025-10-05 18:00:00)
        // Note: We need to mock the current time to be 2025-10-06 09:00:00
        // Since we cannot modify the LocalDateTime.now() in the original code,
        // we'll assume the test environment is set up with this time
        
        // Execute
        boolean result = airlineSystem.createBooking(f303, passengerNames);
        
        // Verify
        assertFalse("Booking should fail because current time is after departure time", result);
        assertEquals("No reservations should be created", 0, airlineSystem.getReservations().size());
        assertEquals("No bookings should be created", 0, airlineSystem.getBookings().size());
    }
}