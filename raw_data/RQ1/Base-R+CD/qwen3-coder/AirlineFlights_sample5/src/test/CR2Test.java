import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR2Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_twoNewPassengersSucceed() throws ParseException {
        // Setup
        // 1. Create airline AL11
        Airline al11 = new Airline();
        
        // 2. Create airports AP100 (departure) and AP101 (arrival)
        Airport ap100 = new Airport();
        ap100.setId("AP100");
        Airport ap101 = new Airport();
        ap101.setId("AP101");
        
        // 3. Create flight F300 under AL11
        Flight f300 = new Flight();
        f300.setId("F300");
        f300.setDepartureAirport(ap100);
        f300.setArrivalAirport(ap101);
        f300.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        f300.setArrivalTime(dateFormat.parse("2025-10-05 12:00:00"));
        f300.setOpenForBooking(true);
        al11.addFlight(f300);
        
        // 4. Instantiate customer CUA
        Customer cua = new Customer();
        
        // 5. Current time = 2025-10-01 09:00:00
        Date now = dateFormat.parse("2025-10-01 09:00:00");
        
        // Input: Request booking on flight F300 for passenger list P1:"Peter", P2:"Beck"
        List<String> passengerNames = Arrays.asList("Peter", "Beck");
        
        // Execute
        boolean result = cua.addBooking(f300, now, passengerNames);
        
        // Expected Output: True. There are two reservations for passengers P1 and P2.
        assertTrue(result);
        assertEquals(1, cua.getBookings().size());
        assertEquals(2, cua.getBookings().get(0).getReservations().size());
        
        List<Reservation> reservations = cua.getBookings().get(0).getReservations();
        boolean foundPeter = false;
        boolean foundBeck = false;
        
        for (Reservation reservation : reservations) {
            assertEquals(ReservationStatus.PENDING, reservation.getStatus());
            if ("Peter".equals(reservation.getPassenger().getName())) {
                foundPeter = true;
            } else if ("Beck".equals(reservation.getPassenger().getName())) {
                foundBeck = true;
            }
        }
        
        assertTrue(foundPeter);
        assertTrue(foundBeck);
    }
    
    @Test
    public void testCase2_duplicatePassengerInSameRequest() throws ParseException {
        // Setup
        // 1. Create airline AL12
        Airline al12 = new Airline();
        
        // 2. Create airports AP102 (dep) and AP103 (arr)
        Airport ap102 = new Airport();
        ap102.setId("AP102");
        Airport ap103 = new Airport();
        ap103.setId("AP103");
        
        // 3. Create flight F301 under AL12
        Flight f301 = new Flight();
        f301.setId("F301");
        f301.setDepartureAirport(ap102);
        f301.setArrivalAirport(ap103);
        f301.setDepartureTime(dateFormat.parse("2025-10-05 08:00:00"));
        f301.setArrivalTime(dateFormat.parse("2025-10-05 10:00:00"));
        f301.setOpenForBooking(true);
        al12.addFlight(f301);
        
        // 4. Customer CUB
        Customer cub = new Customer();
        
        // 5. Current time = 2025-10-01 09:00:00
        Date now = dateFormat.parse("2025-10-01 09:00:00");
        
        // Input: Request booking on flight F301 for passenger P3: "Alice" P3: "Alice"
        List<String> passengerNames = Arrays.asList("Alice", "Alice");
        
        // Execute
        boolean result = cub.addBooking(f301, now, passengerNames);
        
        // Expected Output: False. (Duplicate passenger)
        assertFalse(result);
        assertEquals(0, cub.getBookings().size());
    }
    
    @Test
    public void testCase3_passengerAlreadyBookedEarlier() throws ParseException {
        // Setup
        // 1. Create airline AL13 with airports AP104, AP105
        Airline al13 = new Airline();
        Airport ap104 = new Airport();
        ap104.setId("AP104");
        Airport ap105 = new Airport();
        ap105.setId("AP105");
        
        // 2. Create flight F302
        Flight f302 = new Flight();
        f302.setId("F302");
        f302.setDepartureAirport(ap104);
        f302.setArrivalAirport(ap105);
        f302.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        f302.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        f302.setOpenForBooking(true);
        al13.addFlight(f302);
        
        // 3. Passenger P4 "Jucy"
        // 4. Customer CUC
        Customer cuc = new Customer();
        
        // 5. Pre-existing booking BK-OLD containing reservation R302-A for passenger P4 "Jucy" on F302 (status = PENDING)
        Booking oldBooking = new Booking();
        oldBooking.setCustomer(cuc);
        
        Passenger p4 = new Passenger();
        p4.setName("Jucy");
        
        Reservation r302A = new Reservation();
        r302A.setId("R302-A");
        r302A.setStatus(ReservationStatus.PENDING);
        r302A.setPassenger(p4);
        r302A.setFlight(f302);
        
        oldBooking.getReservations().add(r302A);
        f302.getReservations().add(r302A);
        cuc.getBookings().add(oldBooking);
        
        // Current time = 2025-10-01 09:00:00
        Date now = dateFormat.parse("2025-10-01 09:00:00");
        
        // Input: Request booking on flight F302 for passenger "Jucy"
        List<String> passengerNames = Arrays.asList("Jucy");
        
        // Execute
        boolean result = cuc.addBooking(f302, now, passengerNames);
        
        // Expected Output: False
        assertFalse(result);
    }
    
    @Test
    public void testCase4_flightClosedBlocksBooking() throws ParseException {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        Airline al14 = new Airline();
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        
        // 2. Create flight F303 under AL14
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        f303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        f303.setOpenForBooking(false); // Flight is closed for booking
        al14.addFlight(f303);
        
        // 3. Customer CUD
        Customer cud = new Customer();
        
        // 4. Passenger P5 "Ruby"
        // Current time = 2025-10-01 09:00:00
        Date now = dateFormat.parse("2025-10-01 09:00:00");
        
        // Input: Request booking on flight F303 for passenger list "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = cud.addBooking(f303, now, passengerNames);
        
        // Expected Output: False
        assertFalse(result);
        assertEquals(0, cud.getBookings().size());
    }
    
    @Test
    public void testCase5_theTimeIsAfterTheDepartureTime() throws ParseException {
        // Setup
        // 1. Create airline AL14 with airports AP106, AP107
        Airline al14 = new Airline();
        Airport ap106 = new Airport();
        ap106.setId("AP106");
        Airport ap107 = new Airport();
        ap107.setId("AP107");
        
        // 2. Create flight F303 under AL14
        Flight f303 = new Flight();
        f303.setId("F303");
        f303.setDepartureAirport(ap106);
        f303.setArrivalAirport(ap107);
        f303.setDepartureTime(dateFormat.parse("2025-10-05 18:00:00"));
        f303.setArrivalTime(dateFormat.parse("2025-10-06 02:00:00"));
        f303.setOpenForBooking(true);
        al14.addFlight(f303);
        
        // 3. Customer CUD
        Customer cud = new Customer();
        
        // 4. Passenger P5 "Ruby"
        // Current time = 2025-10-06 09:00:00 (after departure time)
        Date now = dateFormat.parse("2025-10-06 09:00:00");
        
        // Input: Request booking on flight F303 for passenger list "Ruby"
        List<String> passengerNames = Arrays.asList("Ruby");
        
        // Execute
        boolean result = cud.addBooking(f303, now, passengerNames);
        
        // Expected Output: False
        assertFalse(result);
        assertEquals(0, cud.getBookings().size());
    }
}