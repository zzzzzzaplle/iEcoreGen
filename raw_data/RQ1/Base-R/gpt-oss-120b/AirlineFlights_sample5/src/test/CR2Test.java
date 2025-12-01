import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CR2Test {
    private AirlineSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() {
        // Setup
        Airport ap100 = new Airport("AP100", "Departure Airport");
        Airport ap101 = new Airport("AP101", "Arrival Airport");
        system.addAirport(ap100);
        system.addAirport(ap101);
        
        LocalDateTime depTime = LocalDateTime.parse("2025-10-05 08:00:00", formatter);
        LocalDateTime arrTime = LocalDateTime.parse("2025-10-05 12:00:00", formatter);
        Flight f300 = new Flight(ap100, ap101, depTime, arrTime);
        f300.setStatus(FlightStatus.PUBLISHED);
        system.addFlight(f300);
        
        Customer cua = new Customer("CUA", "Customer A");
        system.addCustomer(cua);
        
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute - current time is before departure (2025-10-01 09:00:00)
        boolean result = system.createBooking("FL-1", "CUA", passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Should have exactly 2 reservations", 2, system.getReservations().size());
        
        // Check that reservations were created for both passengers
        boolean hasPeter = false;
        boolean hasBeck = false;
        for (Reservation res : system.getReservations().values()) {
            if ("Peter".equals(res.getPassengerName())) hasPeter = true;
            if ("Beck".equals(res.getPassengerName())) hasBeck = true;
            assertEquals("Reservation should be in PENDING status", ReservationStatus.PENDING, res.getStatus());
        }
        assertTrue("Should have reservation for Peter", hasPeter);
        assertTrue("Should have reservation for Beck", hasBeck);
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() {
        // Setup
        Airport ap102 = new Airport("AP102", "Departure Airport");
        Airport ap103 = new Airport("AP103", "Arrival Airport");
        system.addAirport(ap102);
        system.addAirport(ap103);
        
        LocalDateTime depTime = LocalDateTime.parse("2025-10-05 08:00:00", formatter);
        LocalDateTime arrTime = LocalDateTime.parse("2025-10-05 10:00:00", formatter);
        Flight f301 = new Flight(ap102, ap103, depTime, arrTime);
        f301.setStatus(FlightStatus.PUBLISHED);
        system.addFlight(f301);
        
        Customer cub = new Customer("CUB", "Customer B");
        system.addCustomer(cub);
        
        List<String> passengerNames = Arrays.asList("Alice", "Alice"); // Duplicate passenger names
        
        // Execute - current time is before departure (2025-10-01 09:00:00)
        boolean result = system.createBooking("FL-1", "CUB", passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passengers", result);
        assertEquals("Should have no reservations created", 0, system.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() {
        // Setup
        Airport ap104 = new Airport("AP104", "Departure Airport");
        Airport ap105 = new Airport("AP105", "Arrival Airport");
        system.addAirport(ap104);
        system.addAirport(ap105);
        
        LocalDateTime depTime = LocalDateTime.parse("2025-10-05 18:00:00", formatter);
        LocalDateTime arrTime = LocalDateTime.parse("2025-10-06 02:00:00", formatter);
        Flight f302 = new Flight(ap104, ap105, depTime, arrTime);
        f302.setStatus(FlightStatus.PUBLISHED);
        system.addFlight(f302);
        
        Customer cuc = new Customer("CUC", "Customer C");
        system.addCustomer(cuc);
        
        // Create pre-existing booking with reservation for "Jucy"
        Booking existingBooking = new Booking("CUC", f302);
        Reservation existingReservation = new Reservation(f302, "Jucy");
        existingBooking.addReservation(existingReservation);
        system.getBookings().put(existingBooking.getId(), existingBooking);
        system.getReservations().put(existingReservation.getId(), existingReservation);
        
        List<String> passengerNames = Arrays.asList("Jucy"); // Same passenger name
        
        // Execute - current time is before departure (2025-10-01 09:00:00)
        boolean result = system.createBooking("FL-1", "CUC", passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("Should still have only 1 reservation", 1, system.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() {
        // Setup
        Airport ap106 = new Airport("AP106", "Departure Airport");
        Airport ap107 = new Airport("AP107", "Arrival Airport");
        system.addAirport(ap106);
        system.addAirport(ap107);
        
        LocalDateTime depTime = LocalDateTime.parse("2025-10-05 18:00:00", formatter);
        LocalDateTime arrTime = LocalDateTime.parse("2025-10-06 02:00:00", formatter);
        Flight f303 = new Flight(ap106, ap107, depTime, arrTime);
        f303.setStatus(FlightStatus.CLOSED); // Flight is closed for booking
        system.addFlight(f303);
        
        Customer cud = new Customer("CUD", "Customer D");
        system.addCustomer(cud);
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute - current time is before departure (2025-10-01 09:00:00)
        boolean result = system.createBooking("FL-1", "CUD", passengerNames);
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("Should have no reservations created", 0, system.getReservations().size());
    }
    
    @Test
    public void testCase5_TimeAfterDepartureTimeBlocksBooking() {
        // Setup
        Airport ap106 = new Airport("AP106", "Departure Airport");
        Airport ap107 = new Airport("AP107", "Arrival Airport");
        system.addAirport(ap106);
        system.addAirport(ap107);
        
        LocalDateTime depTime = LocalDateTime.parse("2025-10-05 18:00:00", formatter);
        LocalDateTime arrTime = LocalDateTime.parse("2025-10-06 02:00:00", formatter);
        Flight f303 = new Flight(ap106, ap107, depTime, arrTime);
        f303.setStatus(FlightStatus.PUBLISHED);
        system.addFlight(f303);
        
        Customer cud = new Customer("CUD", "Customer D");
        system.addCustomer(cud);
        
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Note: The test specification says current time is 2025-10-06 09:00:00
        // which is AFTER departure time (2025-10-05 18:00:00)
        // This should cause the booking to fail due to time check
        
        // Execute - current time is after departure (2025-10-06 09:00:00)
        // We simulate this by setting the flight's departure time to be in the past
        // relative to "now", but since we can't change system time, we rely on the
        // flight's departure time being before "now" in the test scenario
        LocalDateTime pastDepartureTime = LocalDateTime.parse("2025-10-05 18:00:00", formatter);
        f303.setDepartureTime(pastDepartureTime);
        
        boolean result = system.createBooking("FL-1", "CUD", passengerNames);
        
        // Verify
        assertFalse("Booking should fail because current time is after departure", result);
        assertEquals("Should have no reservations created", 0, system.getReservations().size());
    }
}