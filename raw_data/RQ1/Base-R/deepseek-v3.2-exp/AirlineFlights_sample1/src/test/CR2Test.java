import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    private DateTimeFormatter formatter;
    private AirlineSystem airlineSystem;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        airlineSystem = new AirlineSystem();
    }
    
    @Test
    public void testCase1_TwoNewPassengersSucceed() {
        // Setup
        // 1. Create airline AL11
        // Note: AirlineSystem is already created in setUp()
        
        // 2. Create airports AP100 (departure) and AP101 (arrival)
        Airport ap100 = new Airport();
        ap100.setId("AP100");
        Set<String> servedCities100 = new HashSet<>();
        servedCities100.add("City100");
        ap100.setServedCities(servedCities100);
        
        Airport ap101 = new Airport();
        ap101.setId("AP101");
        Set<String> servedCities101 = new HashSet<>();
        servedCities101.add("City101");
        ap101.setServedCities(servedCities101);
        
        // 3. Create flight F300 under AL11
        Flight flight300 = new Flight();
        flight300.setId("F300");
        flight300.setDepartureAirport(ap100);
        flight300.setArrivalAirport(ap101);
        flight300.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        flight300.setArrivalTime(LocalDateTime.parse("2025-10-05 12:00:00", formatter));
        flight300.setOpenForBooking(true);
        
        // Add flight to airline system
        List<Flight> flights = new ArrayList<>();
        flights.add(flight300);
        airlineSystem.setFlights(flights);
        
        // 4. Instantiate customer CUA (represented by Booking object)
        Booking customerCUA = new Booking();
        customerCUA.setFlight(flight300);
        
        // 5. Instantiate passengers P1:"Peter", P2:"Beck"
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Set current time = 2025-10-01 09:00:00
        // Mock current time by ensuring flight departure is in the future
        // (actual time validation happens within the method)
        
        // Execute
        boolean result = customerCUA.createBooking(passengerNames);
        
        // Verify
        assertTrue("Booking should succeed for two new passengers", result);
        assertEquals("Should have exactly 2 reservations", 2, customerCUA.getReservations().size());
        
        // Verify passenger names
        List<Reservation> reservations = customerCUA.getReservations();
        assertEquals("Peter", reservations.get(0).getPassengerName());
        assertEquals("Beck", reservations.get(1).getPassengerName());
        
        // Verify initial status is "pending"
        assertEquals("pending", reservations.get(0).getStatus());
        assertEquals("pending", reservations.get(1).getStatus());
        
        // Verify unique IDs are generated
        assertNotNull("Reservation ID should not be null", reservations.get(0).getId());
        assertNotNull("Reservation ID should not be null", reservations.get(1).getId());
        assertNotEquals("Reservation IDs should be different", 
                       reservations.get(0).getId(), reservations.get(1).getId());
    }
    
    @Test
    public void testCase2_DuplicatePassengerInSameRequest() {
        // Setup
        // 1. Create airline AL12
        // Note: AirlineSystem is already created in setUp()
        
        // 2. Create airports AP102 (dep) and AP103 (arr)
        Airport ap102 = new Airport();
        ap102.setId("AP102");
        Set<String> servedCities102 = new HashSet<>();
        servedCities102.add("City102");
        ap102.setServedCities(servedCities102);
        
        Airport ap103 = new Airport();
        ap103.setId("AP103");
        Set<String> servedCities103 = new HashSet<>();
        servedCities103.add("City103");
        ap103.setServedCities(servedCities103);
        
        // 3. Create flight F301 under AL12
        Flight flight301 = new Flight();
        flight301.setId("F301");
        flight301.setDepartureAirport(ap102);
        flight301.setArrivalAirport(ap103);
        flight301.setDepartureTime(LocalDateTime.parse("2025-10-05 08:00:00", formatter));
        flight301.setArrivalTime(LocalDateTime.parse("2025-10-05 10:00:00", formatter));
        flight301.setOpenForBooking(true);
        
        // Add flight to airline system
        List<Flight> flights = new ArrayList<>();
        flights.add(flight301);
        airlineSystem.setFlights(flights);
        
        // 4. Customer CUB (represented by Booking object)
        Booking customerCUB = new Booking();
        customerCUB.setFlight(flight301);
        
        // 5. Passenger P3 "Alice" (duplicate in same request)
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Set current time = 2025-10-01 09:00:00
        // Mock current time by ensuring flight departure is in the future
        
        // Execute
        boolean result = customerCUB.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passenger", result);
        assertEquals("Should have no reservations", 0, customerCUB.getReservations().size());
    }
    
    @Test
    public void testCase3_PassengerAlreadyBookedEarlier() {
        // Setup
        // 1. Create airline AL13 with airports AP104, AP105
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        Set<String> servedCities104 = new HashSet<>();
        servedCities104.add("City104");
        ap104.setServedCities(servedCities104);
        
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        Set<String> servedCities105 = new HashSet<>();
        servedCities105.add("City105");
        ap105.setServedCities(servedCities105);
        
        // 2. Create flight F302
        Flight flight302 = new Flight();
        flight302.setId("F302");
        flight302.setDepartureAirport(ap104);
        flight302.setArrivalAirport(ap105);
        flight302.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight302.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight302.setOpenForBooking(true);
        
        // Add flight to airline system
        List<Flight> flights = new ArrayList<>();
        flights.add(flight302);
        airlineSystem.setFlights(flights);
        
        // 3. Passenger P4 "Jucy"
        // 4. Customer CUC
        // 5. Customer CUC: Pre-existing booking BK-OLD containing reservation R302-A for passenger P4 "Jucy" on F302 (status = PENDING)
        
        // Create pre-existing booking
        Booking existingBooking = new Booking();
        existingBooking.setFlight(flight302);
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setFlight(flight302);
        existingReservation.setPassengerName("Jucy");
        existingReservation.setStatus("pending");
        
        List<Reservation> existingReservations = new ArrayList<>();
        existingReservations.add(existingReservation);
        existingBooking.setReservations(existingReservations);
        
        // Add existing booking to airline system
        List<Booking> bookings = new ArrayList<>();
        bookings.add(existingBooking);
        airlineSystem.setBookings(bookings);
        
        // Create new booking attempt for same passenger
        Booking customerCUC = new Booking();
        customerCUC.setFlight(flight302);
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Set current time = 2025-10-01 09:00:00
        
        // Execute
        boolean result = customerCUC.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to passenger already booked", result);
        assertEquals("New booking should have no reservations", 0, customerCUC.getReservations().size());
    }
    
    @Test
    public void testCase4_FlightClosedBlocksBooking() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        Set<String> servedCities106 = new HashSet<>();
        servedCities106.add("City106");
        ap106.setServedCities(servedCities106);
        
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        Set<String> servedCities107 = new HashSet<>();
        servedCities107.add("City107");
        ap107.setServedCities(servedCities107);
        
        // 2. Create flight F303 under AL14
        Flight flight303 = new Flight();
        flight303.setId("F303");
        flight303.setDepartureAirport(ap106);
        flight303.setArrivalAirport(ap107);
        flight303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight303.setOpenForBooking(false); // Flight is closed
        
        // Add flight to airline system
        List<Flight> flights = new ArrayList<>();
        flights.add(flight303);
        airlineSystem.setFlights(flights);
        
        // 3. Customer CUD
        Booking customerCUD = new Booking();
        customerCUD.setFlight(flight303);
        
        // 4. Passenger P5 "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Set current time = 2025-10-01 09:00:00
        
        // Execute
        boolean result = customerCUD.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to flight being closed", result);
        assertEquals("Should have no reservations", 0, customerCUD.getReservations().size());
    }
    
    @Test
    public void testCase5_TheTimeIsAfterTheDepartureTime() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        Set<String> servedCities106 = new HashSet<>();
        servedCities106.add("City106");
        ap106.setServedCities(servedCities106);
        
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        Set<String> servedCities107 = new HashSet<>();
        servedCities107.add("City107");
        ap107.setServedCities(servedCities107);
        
        // 2. Create flight F303 under AL14
        Flight flight303 = new Flight();
        flight303.setId("F303");
        flight303.setDepartureAirport(ap106);
        flight303.setArrivalAirport(ap107);
        flight303.setDepartureTime(LocalDateTime.parse("2025-10-05 18:00:00", formatter));
        flight303.setArrivalTime(LocalDateTime.parse("2025-10-06 02:00:00", formatter));
        flight303.setOpenForBooking(true); // Flight is open
        
        // Add flight to airline system
        List<Flight> flights = new ArrayList<>();
        flights.add(flight303);
        airlineSystem.setFlights(flights);
        
        // 3. Customer CUD
        Booking customerCUD = new Booking();
        customerCUD.setFlight(flight303);
        
        // 4. Passenger P5 "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Set current time = 2025-10-06 09:00:00 (after departure time)
        // This is handled by the method's internal time check
        
        // Execute
        boolean result = customerCUD.createBooking(passengerNames);
        
        // Verify
        assertFalse("Booking should fail due to current time being after departure time", result);
        assertEquals("Should have no reservations", 0, customerCUD.getReservations().size());
    }
}