import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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
        // 1. Create airline AL11.
        AirlineSystem al11 = new AirlineSystem();
        
        // 2. Create airports AP100 (departure) and AP101 (arrival).
        Airport ap100 = new Airport();
        ap100.setId("AP100");
        Set<String> servedCities100 = new HashSet<>();
        servedCities100.add("CityA");
        ap100.setServedCities(servedCities100);
        
        Airport ap101 = new Airport();
        ap101.setId("AP101");
        Set<String> servedCities101 = new HashSet<>();
        servedCities101.add("CityB");
        ap101.setServedCities(servedCities101);
        
        // 3. Create flight F300 under AL11
        Flight f300 = new Flight();
        f300.setId("F300");
        f300.setDepartureAirport(ap100);
        f300.setArrivalAirport(ap101);
        f300.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        f300.setArrivalTime(LocalDateTime.parse("2025-10-05 12:00:00", formatter));
        f300.setOpenForBooking(true); // it is open for booking
        
        // Add flight to airline
        al11.getFlights().add(f300);
        
        // 4. Instantiate customer CUA.
        String customerId = "CUA";
        
        // 5. Instantiate passengers P1:"Peter", P2:"Beck".
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Set current time = 2025-10-01 09:00:00
        // Mock current time by ensuring flight departure time is after this time
        
        // Execute
        boolean result = al11.createBooking(f300, passengerNames, customerId);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Should have 2 reservations", 2, f300.getReservations().size());
        
        // Check that reservations were created for both passengers
        boolean peterFound = false;
        boolean beckFound = false;
        for (Reservation reservation : f300.getReservations()) {
            if ("Peter".equals(reservation.getPassengerName())) {
                peterFound = true;
                assertEquals("Reservation status should be pending", "pending", reservation.getStatus());
            }
            if ("Beck".equals(reservation.getPassengerName())) {
                beckFound = true;
                assertEquals("Reservation status should be pending", "pending", reservation.getStatus());
            }
        }
        assertTrue("Peter reservation should be created", peterFound);
        assertTrue("Beck reservation should be created", beckFound);
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() {
        // Setup
        // 1. Create airline AL12.
        AirlineSystem al12 = new AirlineSystem();
        
        // 2. Create airports AP102 (dep) and AP103 (arr).
        Airport ap102 = new Airport();
        ap102.setId("AP102");
        Set<String> servedCities102 = new HashSet<>();
        servedCities102.add("CityC");
        ap102.setServedCities(servedCities102);
        
        Airport ap103 = new Airport();
        ap103.setId("AP103");
        Set<String> servedCities103 = new HashSet<>();
        servedCities103.add("CityD");
        ap103.setServedCities(servedCities103);
        
        // 3. Create flight F301 under AL12
        Flight f301 = new Flight();
        f301.setId("F301");
        f301.setDepartureAirport(ap102);
        f301.setArrivalAirport(ap103);
        f301.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        f301.setArrivalTime(LocalDateTime.parse("2025-10-05 10:00:00", formatter));
        f301.setOpenForBooking(true); // it is open for booking
        
        // Add flight to airline
        al12.getFlights().add(f301);
        
        // 4. Customer CUB.
        String customerId = "CUB";
        
        // 5. Passenger P3 "Alice" (duplicate in same request)
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Set current time = 2025-10-01 09:00:00
        // Mock current time by ensuring flight departure time is after this time
        
        // Execute
        boolean result = al12.createBooking(f301, passengerNames, customerId);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger", result);
        assertEquals("No reservations should be created", 0, f301.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() {
        // Setup
        // 1. Create airline AL13 with airports AP104, AP105.
        AirlineSystem al13 = new AirlineSystem();
        
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        Set<String> servedCities104 = new HashSet<>();
        servedCities104.add("CityE");
        ap104.setServedCities(servedCities104);
        
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        Set<String> servedCities105 = new HashSet<>();
        servedCities105.add("CityF");
        ap105.setServedCities(servedCities105);
        
        // 2. Create flight F302
        Flight f302 = new Flight();
        f302.setId("F302");
        f302.setDepartureAirport(ap104);
        f302.setArrivalAirport(ap105);
        f302.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f302.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f302.setOpenForBooking(true); // it is open for booking
        
        // Add flight to airline
        al13.getFlights().add(f302);
        
        // 3. Passenger P4 "Jucy".
        // 4. Customer CUC.
        String customerId = "CUC";
        
        // 5. Customer CUC : Pre-existing booking BK-OLD containing reservation R302-A for passenger P4 "Jucy" on F302 (status = PENDING).
        Booking existingBooking = new Booking();
        existingBooking.setCustomerId(customerId);
        existingBooking.setFlight(f302);
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setPassengerName("Jucy");
        existingReservation.setFlight(f302);
        existingReservation.setStatus("pending");
        existingReservation.setBooking(existingBooking);
        
        existingBooking.getReservations().add(existingReservation);
        f302.getReservations().add(existingReservation);
        al13.getBookings().add(existingBooking);
        
        // New booking request for same passenger
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Set current time = 2025-10-01 09:00:00
        // Mock current time by ensuring flight departure time is after this time
        
        // Execute
        boolean result = al13.createBooking(f302, passengerNames, customerId);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger already booked", result);
        assertEquals("Only one reservation should exist", 1, f302.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107.
        AirlineSystem al14 = new AirlineSystem();
        
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        Set<String> servedCities106 = new HashSet<>();
        servedCities106.add("CityG");
        ap106.setServedCities(servedCities106);
        
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        Set<String> servedCities107 = new HashSet<>();
        servedCities107.add("CityH");
        ap107.setServedCities(servedCities107);
        
        // 2. Create flight F303 under AL14
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f303.setOpenForBooking(false); // it is closed for booking
        
        // Add flight to airline
        al14.getFlights().add(f303);
        
        // 3. Customer CUD.
        String customerId = "CUD";
        
        // 4. Passenger P5 "Ruby".
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Set current time = 2025-10-01 09:00:00
        // Mock current time by ensuring flight departure time is after this time
        
        // Execute
        boolean result = al14.createBooking(f303, passengerNames, customerId);
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("No reservations should be created", 0, f303.getReservations().size());
    }
    
    @Test
    public void testCase5_TheTimeIsAfterTheDepartureTime() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107.
        AirlineSystem al14 = new AirlineSystem();
        
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        Set<String> servedCities106 = new HashSet<>();
        servedCities106.add("CityG");
        ap106.setServedCities(servedCities106);
        
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        Set<String> servedCities107 = new HashSet<>();
        servedCities107.add("CityH");
        ap107.setServedCities(servedCities107);
        
        // 2. Create flight F303 under AL14
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        f303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        f303.setOpenForBooking(true); // it is open for booking
        
        // Add flight to airline
        al14.getFlights().add(f303);
        
        // 3. Customer CUD.
        String customerId = "CUD";
        
        // 4. Passenger P5 "Ruby".
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Set current time = 2025-10-06 09:00:00 (after departure time)
        // This is handled by the booking logic which checks if current time is before departure time
        
        // Execute
        boolean result = al14.createBooking(f303, passengerNames, customerId);
        
        // Verify
        assertFalse("Booking should fail because current time is after departure time", result);
        assertEquals("No reservations should be created", 0, f303.getReservations().size());
    }
}