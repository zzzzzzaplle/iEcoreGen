import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private AirlineSystem airlineSystem;
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private Passenger passenger1;
    private Passenger passenger2;
    private List<Passenger> passengers;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() {
        // Setup
        // 1. Create airline AL11
        AirlineSystem al11 = new AirlineSystem();
        
        // 2. Create airports AP100 (departure) and AP101 (arrival)
        Airport ap100 = new Airport();
        ap100.setId("AP100");
        List<String> cities100 = new ArrayList<>();
        cities100.add("CityA");
        ap100.setCities(cities100);
        
        Airport ap101 = new Airport();
        ap101.setId("AP101");
        List<String> cities101 = new ArrayList<>();
        cities101.add("CityB");
        ap101.setCities(cities101);
        
        // 3. Create flight F300 under AL11
        Flight f300 = new Flight();
        f300.setId("F300");
        f300.setDepartureAirport(ap100);
        f300.setArrivalAirport(ap101);
        f300.setDepartureTime(LocalDateTime.of(2025, 10, 5, 8, 0, 0));
        f300.setArrivalTime(LocalDateTime.of(2025, 10, 5, 12, 0, 0));
        f300.setOpenForBooking(true);
        
        al11.getFlights().add(f300);
        
        // 4. Instantiate customer CUA (represented by AirlineSystem instance)
        AirlineSystem cua = al11;
        
        // 5. Instantiate passengers P1:"Peter", P2:"Beck"
        Passenger p1 = new Passenger();
        p1.setName("Peter");
        
        Passenger p2 = new Passenger();
        p2.setName("Beck");
        
        List<Passenger> passengerList = new ArrayList<>();
        passengerList.add(p1);
        passengerList.add(p2);
        
        // Test
        boolean result = cua.createBooking(f300, passengerList);
        
        // Verify
        assertTrue("Booking should succeed with two new passengers", result);
        assertEquals("Should have exactly one booking", 1, cua.getBookings().size());
        assertEquals("Booking should have exactly two reservations", 2, cua.getBookings().get(0).getReservations().size());
        
        // Verify reservation details
        Reservation res1 = cua.getBookings().get(0).getReservations().get(0);
        Reservation res2 = cua.getBookings().get(0).getReservations().get(1);
        
        assertEquals("Reservation 1 should be for Peter", "Peter", res1.getPassenger().getName());
        assertEquals("Reservation 2 should be for Beck", "Beck", res2.getPassenger().getName());
        assertEquals("Reservation 1 should be pending", "pending", res1.getStatus());
        assertEquals("Reservation 2 should be pending", "pending", res2.getStatus());
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() {
        // Setup
        // 1. Create airline AL12
        AirlineSystem al12 = new AirlineSystem();
        
        // 2. Create airports AP102 (dep) and AP103 (arr)
        Airport ap102 = new Airport();
        ap102.setId("AP102");
        List<String> cities102 = new ArrayList<>();
        cities102.add("CityC");
        ap102.setCities(cities102);
        
        Airport ap103 = new Airport();
        ap103.setId("AP103");
        List<String> cities103 = new ArrayList<>();
        cities103.add("CityD");
        ap103.setCities(cities103);
        
        // 3. Create flight F301 under AL12
        Flight f301 = new Flight();
        f301.setId("F301");
        f301.setDepartureAirport(ap102);
        f301.setArrivalAirport(ap103);
        f301.setDepartureTime(LocalDateTime.of(2025, 10, 5, 8, 0, 0));
        f301.setArrivalTime(LocalDateTime.of(2025, 10, 5, 10, 0, 0));
        f301.setOpenForBooking(true);
        
        al12.getFlights().add(f301);
        
        // 4. Customer CUB (represented by AirlineSystem instance)
        AirlineSystem cub = al12;
        
        // 5. Passenger P3 "Alice" (duplicate entries)
        Passenger p3a = new Passenger();
        p3a.setName("Alice");
        
        Passenger p3b = new Passenger();
        p3b.setName("Alice");
        
        List<Passenger> passengerList = new ArrayList<>();
        passengerList.add(p3a);
        passengerList.add(p3b);
        
        // Test
        boolean result = cub.createBooking(f301, passengerList);
        
        // Verify
        assertFalse("Booking should fail due to duplicate passengers", result);
        assertEquals("Should have no bookings", 0, cub.getBookings().size());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() {
        // Setup
        // 1. Create airline AL13 with airports AP104, AP105
        AirlineSystem al13 = new AirlineSystem();
        
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        List<String> cities104 = new ArrayList<>();
        cities104.add("CityE");
        ap104.setCities(cities104);
        
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        List<String> cities105 = new ArrayList<>();
        cities105.add("CityF");
        ap105.setCities(cities105);
        
        // 2. Create flight F302
        Flight f302 = new Flight();
        f302.setId("F302");
        f302.setDepartureAirport(ap104);
        f302.setArrivalAirport(ap105);
        f302.setDepartureTime(LocalDateTime.of(2025, 10, 5, 18, 0, 0));
        f302.setArrivalTime(LocalDateTime.of(2025, 10, 6, 2, 0, 0));
        f302.setOpenForBooking(true);
        
        al13.getFlights().add(f302);
        
        // 3. Passenger P4 "Jucy"
        Passenger p4 = new Passenger();
        p4.setName("Jucy");
        
        // 4. Customer CUC
        AirlineSystem cuc = al13;
        
        // 5. Pre-existing booking BK-OLD containing reservation R302-A for passenger P4 "Jucy" on F302
        Booking existingBooking = new Booking();
        existingBooking.setId("BK-OLD");
        
        Reservation existingReservation = new Reservation();
        existingReservation.setId("R302-A");
        existingReservation.setFlight(f302);
        existingReservation.setPassenger(p4);
        existingReservation.setStatus("pending");
        
        existingBooking.addReservation(existingReservation);
        cuc.getBookings().add(existingBooking);
        
        List<Passenger> passengerList = new ArrayList<>();
        passengerList.add(p4);
        
        // Test
        boolean result = cuc.createBooking(f302, passengerList);
        
        // Verify
        assertFalse("Booking should fail due to existing reservation for same passenger", result);
        assertEquals("Should still have only one booking", 1, cuc.getBookings().size());
        assertEquals("Existing booking should still have one reservation", 1, cuc.getBookings().get(0).getReservations().size());
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        AirlineSystem al14 = new AirlineSystem();
        
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        List<String> cities106 = new ArrayList<>();
        cities106.add("CityG");
        ap106.setCities(cities106);
        
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        List<String> cities107 = new ArrayList<>();
        cities107.add("CityH");
        ap107.setCities(cities107);
        
        // 2. Create flight F303 under AL14
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(LocalDateTime.of(2025, 10, 5, 18, 0, 0));
        f303.setArrivalTime(LocalDateTime.of(2025, 10, 6, 2, 0, 0));
        f303.setOpenForBooking(false); // Closed for booking
        
        al14.getFlights().add(f303);
        
        // 3. Customer CUD
        AirlineSystem cud = al14;
        
        // 4. Passenger P5 "Ruby"
        Passenger p5 = new Passenger();
        p5.setName("Ruby");
        
        List<Passenger> passengerList = new ArrayList<>();
        passengerList.add(p5);
        
        // Test
        boolean result = cud.createBooking(f303, passengerList);
        
        // Verify
        assertFalse("Booking should fail because flight is closed", result);
        assertEquals("Should have no bookings", 0, cud.getBookings().size());
    }
    
    @Test
    public void testCase5_timeIsAfterDepartureTime() {
        // Setup
        // 1. Create airline AL14 with airports AP104, AP105
        AirlineSystem al14 = new AirlineSystem();
        
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        List<String> cities104 = new ArrayList<>();
        cities104.add("CityE");
        ap104.setCities(cities104);
        
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        List<String> cities105 = new ArrayList<>();
        cities105.add("CityF");
        ap105.setCities(cities105);
        
        // 2. Create flight F303 under AL14
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap104);
        f303.setArrivalAirport(ap105);
        f303.setDepartureTime(LocalDateTime.of(2025, 10, 5, 18, 0, 0));
        f303.setArrivalTime(LocalDateTime.of(2025, 10, 6, 2, 0, 0));
        f303.setOpenForBooking(true);
        
        al14.getFlights().add(f303);
        
        // 3. Customer CUD
        AirlineSystem cud = al14;
        
        // 4. Passenger P5 "Ruby"
        Passenger p5 = new Passenger();
        p5.setName("Ruby");
        
        List<Passenger> passengerList = new ArrayList<>();
        passengerList.add(p5);
        
        // Note: Current time in test case is 2025-10-06 09:00:00 which is after departure time
        // Since we cannot mock LocalDateTime.now(), we'll test the logic by setting up a flight with past departure time
        
        // Create a flight that has already departed
        Flight pastFlight = new Flight();
        pastFlight.setId("F303_PAST");
        pastFlight.setDepartureAirport(ap104);
        pastFlight.setArrivalAirport(ap105);
        pastFlight.setDepartureTime(LocalDateTime.of(2024, 10, 5, 18, 0, 0)); // Past date
        pastFlight.setArrivalTime(LocalDateTime.of(2024, 10, 6, 2, 0, 0)); // Past date
        pastFlight.setOpenForBooking(true);
        
        al14.getFlights().add(pastFlight);
        
        // Test with past flight
        boolean result = cud.createBooking(pastFlight, passengerList);
        
        // Verify
        assertFalse("Booking should fail because current time is after departure time", result);
        assertEquals("Should have no bookings", 0, cud.getBookings().size());
    }
}