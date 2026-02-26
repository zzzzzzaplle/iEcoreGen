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
    public void testCase1_twoNewPassengersSucceed() {
        // Setup
        // 1. Create airline AL11 (represented by AirlineSystem instance)
        // 2. Create airports AP100 (departure) and AP101 (arrival)
        Airport ap100 = new Airport();
        ap100.setId("AP100");
        ap100.setCities(Arrays.asList("CityA"));
        
        Airport ap101 = new Airport();
        ap101.setId("AP101");
        ap101.setCities(Arrays.asList("CityB"));
        
        // 3. Create flight F300
        Flight flight = new Flight();
        flight.setId("F300");
        flight.setDepartureAirport(ap100);
        flight.setArrivalAirport(ap101);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-05 12:00:00", formatter));
        flight.setOpenForBooking(true);
        
        airlineSystem.getFlights().add(flight);
        
        // 4. Instantiate customer CUA (represented by passenger names)
        // 5. Instantiate passengers P1:"Peter", P2:"Beck"
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Set current time to 2025-10-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute test
        boolean result = airlineSystem.createBooking(flight, passengerNames);
        
        // Verify result
        assertTrue("Booking should succeed for two new passengers", result);
        
        // Verify reservations were created
        assertEquals("Should have one booking", 1, airlineSystem.getBookings().size());
        Booking booking = airlineSystem.getBookings().get(0);
        assertEquals("Should have two reservations", 2, booking.getReservations().size());
        
        // Verify reservation details
        Reservation res1 = booking.getReservations().get(0);
        Reservation res2 = booking.getReservations().get(1);
        
        assertEquals("First passenger should be Peter", "Peter", res1.getPassengerName());
        assertEquals("Second passenger should be Beck", "Beck", res2.getPassengerName());
        assertEquals("Both reservations should be pending", "pending", res1.getStatus());
        assertEquals("Both reservations should be pending", "pending", res2.getStatus());
        assertEquals("Both reservations should be for flight F300", "F300", res1.getFlight().getId());
        assertEquals("Both reservations should be for flight F300", "F300", res2.getFlight().getId());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() {
        // Setup
        // 1. Create airline AL12 (represented by AirlineSystem instance)
        // 2. Create airports AP102 (dep) and AP103 (arr)
        Airport ap102 = new Airport();
        ap102.setId("AP102");
        ap102.setCities(Arrays.asList("CityC"));
        
        Airport ap103 = new Airport();
        ap103.setId("AP103");
        ap103.setCities(Arrays.asList("CityD"));
        
        // 3. Create flight F301
        Flight flight = new Flight();
        flight.setId("F301");
        flight.setDepartureAirport(ap102);
        flight.setArrivalAirport(ap103);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-05 10:00:00", formatter));
        flight.setOpenForBooking(true);
        
        airlineSystem.getFlights().add(flight);
        
        // 4. Customer CUB (represented by passenger names)
        // 5. Passenger P3 "Alice" (duplicate in list)
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Set current time to 2025-10-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute test
        boolean result = airlineSystem.createBooking(flight, passengerNames);
        
        // Verify result
        assertFalse("Booking should fail due to duplicate passengers", result);
        assertEquals("No bookings should be created", 0, airlineSystem.getBookings().size());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() {
        // Setup
        // 1. Create airline AL13 with airports AP104, AP105
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        ap104.setCities(Arrays.asList("CityE"));
        
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        ap105.setCities(Arrays.asList("CityF"));
        
        // 2. Create flight F302
        Flight flight = new Flight();
        flight.setId("F302");
        flight.setDepartureAirport(ap104);
        flight.setArrivalAirport(ap105);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight.setOpenForBooking(true);
        
        airlineSystem.getFlights().add(flight);
        
        // 3. Passenger P4 "Jucy"
        // 4. Customer CUC
        // 5. Pre-existing booking BK-OLD containing reservation R302-A for passenger P4 "Jucy" on F302
        Booking existingBooking = new Booking();
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setPassengerName("Jucy");
        existingReservation.setFlight(flight);
        existingReservation.setStatus("pending");
        existingBooking.addReservation(existingReservation);
        
        airlineSystem.getBookings().add(existingBooking);
        
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Set current time to 2025-10-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute test
        boolean result = airlineSystem.createBooking(flight, passengerNames);
        
        // Verify result
        assertFalse("Booking should fail because passenger Jucy already has a reservation", result);
        assertEquals("Only the original booking should exist", 1, airlineSystem.getBookings().size());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        ap106.setCities(Arrays.asList("CityG"));
        
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        ap107.setCities(Arrays.asList("CityH"));
        
        // 2. Create flight F303
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(ap106);
        flight.setArrivalAirport(ap107);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight.setOpenForBooking(false); // Flight is closed for booking
        
        airlineSystem.getFlights().add(flight);
        
        // 3. Customer CUD
        // 4. Passenger P5 "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Set current time to 2025-10-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-01 09:00:00", formatter);
        
        // Execute test
        boolean result = airlineSystem.createBooking(flight, passengerNames);
        
        // Verify result
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("No bookings should be created", 0, airlineSystem.getBookings().size());
    }
    
    @Test
    public void testCase5_timeIsAfterDepartureTime() {
        // Setup
        // 1. Create airline AL14 with airports AP104, AP105
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        ap104.setCities(Arrays.asList("CityI"));
        
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        ap105.setCities(Arrays.asList("CityJ"));
        
        // 2. Create flight F303
        Flight flight = new Flight();
        flight.setId("F303");
        flight.setDepartureAirport(ap104);
        flight.setArrivalAirport(ap105);
        flight.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight.setOpenForBooking(true);
        
        airlineSystem.getFlights().add(flight);
        
        // 3. Customer CUD
        // 4. Passenger P5 "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Set current time to 2025-10-06 09:00:00 (after departure time)
        LocalDateTime currentTime = LocalDateTime.parse("2025-10-06 09:00:00", formatter);
        
        // Execute test
        boolean result = airlineSystem.createBooking(flight, passengerNames);
        
        // Verify result
        assertFalse("Booking should fail because current time is after departure time", result);
        assertEquals("No bookings should be created", 0, airlineSystem.getBookings().size());
    }
}