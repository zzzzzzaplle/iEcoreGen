import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR3Test {
    private AirlineSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_confirmPendingReservation() {
        // Setup
        // 1. Airline AL16 - not directly used in test
        // 2. Airports AP160 (CityAA) and AP161 (CityAB)
        Airport ap160 = new Airport("AP160", "Airport AP160");
        City cityAA = new City("CityAA", "City AA");
        ap160.addCity(cityAA);
        
        Airport ap161 = new Airport("AP161", "Airport AP161");
        City cityAB = new City("CityAB", "City AB");
        ap161.addCity(cityAB);
        
        system.addAirport(ap160);
        system.addAirport(ap161);
        
        // 3. Flight F401
        LocalDateTime depTime = LocalDateTime.parse("2025-12-10 11:00:00", formatter);
        LocalDateTime arrTime = LocalDateTime.parse("2025-12-10 15:00:00", formatter);
        Flight f401 = new Flight(ap160, ap161, depTime, arrTime);
        f401.setStatus(FlightStatus.PUBLISHED); // openForBooking = True
        system.addFlight(f401);
        
        // 4. Customer CU16, passenger P9
        Customer cu16 = new Customer("CU16", "Customer CU16");
        system.addCustomer(cu16);
        
        // 5. Booking BK401 contains reservation R401 (status =PENDING) for P9 on F401
        List<String> passengerNames = Arrays.asList("P9");
        system.createBooking("FL-1", "CU16", passengerNames);
        
        // Find the reservation ID (R401) - since we don't control ID generation, we'll use the one created
        Map<String, Reservation> reservations = system.getReservations();
        String reservationId = reservations.keySet().iterator().next();
        
        // Set current time to 2025-11-01 09:00:00
        // This is handled by the system's internal time checks
        
        // Test
        boolean result = system.confirmReservation(reservationId);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", 
                    ReservationStatus.CONFIRMED, reservations.get(reservationId).getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() {
        // Setup
        // 1. Airline AL17 - not directly used in test
        // 2. Airports AP170 (CityAC) and AP171 (CityAD)
        Airport ap170 = new Airport("AP170", "Airport AP170");
        City cityAC = new City("CityAC", "City AC");
        ap170.addCity(cityAC);
        
        Airport ap171 = new Airport("AP171", "Airport AP171");
        City cityAD = new City("CityAD", "City AD");
        ap171.addCity(cityAD);
        
        system.addAirport(ap170);
        system.addAirport(ap171);
        
        // 3. Flight F402
        LocalDateTime depTime = LocalDateTime.parse("2025-12-15 15:00:00", formatter);
        LocalDateTime arrTime = depTime.plusHours(4); // Add arrival time
        Flight f402 = new Flight(ap170, ap171, depTime, arrTime);
        f402.setStatus(FlightStatus.PUBLISHED); // openForBooking = True
        system.addFlight(f402);
        
        // 4. Customer CU17, passenger P10
        Customer cu17 = new Customer("CU17", "Customer CU17");
        system.addCustomer(cu17);
        
        // 5. Booking BK402 contains reservation R402 (status =CONFIRMED) for P10 on F402
        List<String> passengerNames = Arrays.asList("P10");
        system.createBooking("FL-1", "CU17", passengerNames);
        
        // Find the reservation and confirm it first
        Map<String, Reservation> reservations = system.getReservations();
        String reservationId = reservations.keySet().iterator().next();
        system.confirmReservation(reservationId);
        
        // Set current time to 2025-12-01 12:00:00
        // This is handled by the system's internal time checks
        
        // Test
        boolean result = system.cancelReservation(reservationId);
        
        // Verify
        assertTrue("Reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELED", 
                    ReservationStatus.CANCELED, reservations.get(reservationId).getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() {
        // Setup
        // 1. Airline AL18 - not directly used in test
        // 2. Airports AP180 (CityAE) and AP181 (CityAF)
        Airport ap180 = new Airport("AP180", "Airport AP180");
        City cityAE = new City("CityAE", "City AE");
        ap180.addCity(cityAE);
        
        Airport ap181 = new Airport("AP181", "Airport AP181");
        City cityAF = new City("CityAF", "City AF");
        ap181.addCity(cityAF);
        
        system.addAirport(ap180);
        system.addAirport(ap181);
        
        // 3. Flight F403 - departure time in the past
        LocalDateTime depTime = LocalDateTime.parse("2025-01-05 06:00:00", formatter);
        LocalDateTime arrTime = depTime.plusHours(3);
        Flight f403 = new Flight(ap180, ap181, depTime, arrTime);
        f403.setStatus(FlightStatus.PUBLISHED); // openForBooking = True
        system.addFlight(f403);
        
        // 4. Reservation R403 status = PENDING (passenger P11)
        Customer customer = new Customer("CUST", "Customer");
        system.addCustomer(customer);
        List<String> passengerNames = Arrays.asList("P11");
        system.createBooking("FL-1", "CUST", passengerNames);
        
        Map<String, Reservation> reservations = system.getReservations();
        String reservationId = reservations.keySet().iterator().next();
        
        // 5. Current time = 2025-01-05 07:00:00 (flight already left)
        // The system uses LocalDateTime.now() which we can't mock, but the flight departure time
        // is in the past relative to when the test runs, so it should fail
        
        // Test
        boolean result = system.confirmReservation(reservationId);
        
        // Verify
        assertFalse("Confirmation should fail because flight has departed", result);
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() {
        // Setup
        // 1. Airline AL19 - not directly used in test
        // 2. Airports AP190 (CityAG) and AP191 (CityAH)
        Airport ap190 = new Airport("AP190", "Airport AP190");
        City cityAG = new City("CityAG", "City AG");
        ap190.addCity(cityAG);
        
        Airport ap191 = new Airport("AP191", "Airport AP191");
        City cityAH = new City("CityAH", "City AH");
        ap191.addCity(cityAH);
        
        system.addAirport(ap190);
        system.addAirport(ap191);
        
        // 3. Flight F404 - openForBooking = False (CLOSED)
        LocalDateTime depTime = LocalDateTime.parse("2025-02-01 09:00:00", formatter);
        LocalDateTime arrTime = depTime.plusHours(4);
        Flight f404 = new Flight(ap190, ap191, depTime, arrTime);
        f404.setStatus(FlightStatus.CLOSED); // openForBooking = False
        system.addFlight(f404);
        
        // 4. Reservation R404 status = CONFIRMED (passenger P12)
        Customer customer = new Customer("CUST", "Customer");
        system.addCustomer(customer);
        
        // Manually create reservation since flight is closed and createBooking would fail
        Reservation r404 = new Reservation(f404, "P12");
        r404.setStatus(ReservationStatus.CONFIRMED);
        system.getReservations().put(r404.getId(), r404);
        
        // 5. Current time = 2025-01-20 08:00:00
        // The system uses LocalDateTime.now() which we can't mock
        
        // Test
        boolean result = system.cancelReservation(r404.getId());
        
        // Verify
        assertFalse("Cancellation should fail because flight is closed", result);
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() {
        // Setup
        // 1. Airline AL20 - not directly used in test
        // 2. Airports AP200 (CityAI) and AP201 (CityAJ)
        Airport ap200 = new Airport("AP200", "Airport AP200");
        City cityAI = new City("CityAI", "City AI");
        ap200.addCity(cityAI);
        
        Airport ap201 = new Airport("AP201", "Airport AP201");
        City cityAJ = new City("CityAJ", "City AJ");
        ap201.addCity(cityAJ);
        
        system.addAirport(ap200);
        system.addAirport(ap201);
        
        // 3. Flight F405
        LocalDateTime depTime = LocalDateTime.parse("2025-03-10 10:00:00", formatter);
        LocalDateTime arrTime = depTime.plusHours(5);
        Flight f405 = new Flight(ap200, ap201, depTime, arrTime);
        f405.setStatus(FlightStatus.PUBLISHED); // openForBooking = True
        system.addFlight(f405);
        
        // 4. Customer CU20 with one existing reservation R405 (status =PENDING) for passenger P13
        Customer cu20 = new Customer("CU20", "Customer CU20");
        system.addCustomer(cu20);
        List<String> passengerNames1 = Arrays.asList("P13");
        system.createBooking("FL-1", "CU20", passengerNames1);
        
        // 5. Customer CU21 with one existing reservation R406 (status =PENDING) for passenger P14
        Customer cu21 = new Customer("CU21", "Customer CU21");
        system.addCustomer(cu21);
        List<String> passengerNames2 = Arrays.asList("P14");
        system.createBooking("FL-1", "CU21", passengerNames2);
        
        // Current time = 2025-02-15 09:00:00
        // The system uses LocalDateTime.now() which we can't mock
        
        // Test - try to confirm non-existent reservation R406 (R406 doesn't exist in this context)
        boolean result = system.confirmReservation("R406");
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation ID", result);
    }
}