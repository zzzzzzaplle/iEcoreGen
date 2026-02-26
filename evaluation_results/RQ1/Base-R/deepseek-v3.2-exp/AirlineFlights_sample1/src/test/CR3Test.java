import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CR3Test {
    
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() {
        // Setup
        Airport ap160 = new Airport();
        ap160.setId("AP160");
        Set<String> cities160 = new HashSet<>();
        cities160.add("CityAA");
        ap160.setServedCities(cities160);
        
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        Set<String> cities161 = new HashSet<>();
        cities161.add("CityAB");
        ap161.setServedCities(cities161);
        
        Flight f401 = new Flight();
        f401.setId("F401");
        f401.setDepartureAirport(ap160);
        f401.setArrivalAirport(ap161);
        f401.setDepartureTime(LocalDateTime.parse("2025-12-10 11:00:00", formatter));
        f401.setArrivalTime(LocalDateTime.parse("2025-12-10 15:00:00", formatter));
        f401.setOpenForBooking(true);
        
        Reservation r401 = new Reservation();
        r401.setId("R401");
        r401.setFlight(f401);
        r401.setPassengerName("P9");
        r401.setStatus("pending");
        
        Booking bk401 = new Booking();
        bk401.setId("BK401");
        bk401.setFlight(f401);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r401);
        bk401.setReservations(reservations);
        
        airlineSystem.getFlights().add(f401);
        airlineSystem.getBookings().add(bk401);
        
        // Execute - confirm reservation R401
        boolean result = r401.confirmOrCancel(true);
        
        // Verify
        assertTrue("Reservation should be successfully confirmed", result);
        assertEquals("Reservation status should be confirmed", "confirmed", r401.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() {
        // Setup
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        Set<String> cities170 = new HashSet<>();
        cities170.add("CityAC");
        ap170.setServedCities(cities170);
        
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        Set<String> cities171 = new HashSet<>();
        cities171.add("CityAD");
        ap171.setServedCities(cities171);
        
        Flight f402 = new Flight();
        f402.setId("F402");
        f402.setDepartureAirport(ap170);
        f402.setArrivalAirport(ap171);
        f402.setDepartureTime(LocalDateTime.parse("2025-12-15 15:00:00", formatter));
        f402.setOpenForBooking(true);
        
        Reservation r402 = new Reservation();
        r402.setId("R402");
        r402.setFlight(f402);
        r402.setPassengerName("P10");
        r402.setStatus("confirmed");
        
        Booking bk402 = new Booking();
        bk402.setId("BK402");
        bk402.setFlight(f402);
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r402);
        bk402.setReservations(reservations);
        
        airlineSystem.getFlights().add(f402);
        airlineSystem.getBookings().add(bk402);
        
        // Execute - cancel reservation R402
        boolean result = r402.confirmOrCancel(false);
        
        // Verify
        assertTrue("Reservation should be successfully canceled", result);
        assertEquals("Reservation status should be canceled", "canceled", r402.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() {
        // Setup
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        Set<String> cities180 = new HashSet<>();
        cities180.add("CityAE");
        ap180.setServedCities(cities180);
        
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        Set<String> cities181 = new HashSet<>();
        cities181.add("CityAF");
        ap181.setServedCities(cities181);
        
        Flight f403 = new Flight();
        f403.setId("F403");
        f403.setDepartureAirport(ap180);
        f403.setArrivalAirport(ap181);
        f403.setDepartureTime(LocalDateTime.parse("2025-01-05 06:00:00", formatter));
        f403.setOpenForBooking(true);
        
        Reservation r403 = new Reservation();
        r403.setId("R403");
        r403.setFlight(f403);
        r403.setPassengerName("P11");
        r403.setStatus("pending");
        
        // Execute - confirm reservation R403 (flight already departed)
        boolean result = r403.confirmOrCancel(true);
        
        // Verify
        assertFalse("Confirmation should fail because flight has departed", result);
        assertEquals("Reservation status should remain pending", "pending", r403.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() {
        // Setup
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        Set<String> cities190 = new HashSet<>();
        cities190.add("CityAG");
        ap190.setServedCities(cities190);
        
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        Set<String> cities191 = new HashSet<>();
        cities191.add("CityAH");
        ap191.setServedCities(cities191);
        
        Flight f404 = new Flight();
        f404.setId("F404");
        f404.setDepartureAirport(ap190);
        f404.setArrivalAirport(ap191);
        f404.setDepartureTime(LocalDateTime.parse("2025-02-01 09:00:00", formatter));
        f404.setOpenForBooking(false); // Flight is closed
        
        Reservation r404 = new Reservation();
        r404.setId("R404");
        r404.setFlight(f404);
        r404.setPassengerName("P12");
        r404.setStatus("confirmed");
        
        // Execute - cancel reservation R404 (flight is closed)
        boolean result = r404.confirmOrCancel(false);
        
        // Verify
        assertFalse("Cancellation should fail because flight is closed", result);
        assertEquals("Reservation status should remain confirmed", "confirmed", r404.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() {
        // Setup
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        Set<String> cities200 = new HashSet<>();
        cities200.add("CityAI");
        ap200.setServedCities(cities200);
        
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        Set<String> cities201 = new HashSet<>();
        cities201.add("CityAJ");
        ap201.setServedCities(cities201);
        
        Flight f405 = new Flight();
        f405.setId("F405");
        f405.setDepartureAirport(ap200);
        f405.setArrivalAirport(ap201);
        f405.setDepartureTime(LocalDateTime.parse("2025-03-10 10:00:00", formatter));
        f405.setOpenForBooking(true);
        
        // Customer CU20 reservation
        Reservation r405 = new Reservation();
        r405.setId("R405");
        r405.setFlight(f405);
        r405.setPassengerName("P13");
        r405.setStatus("pending");
        
        Booking bk405 = new Booking();
        bk405.setId("BK405");
        bk405.setFlight(f405);
        List<Reservation> reservations405 = new ArrayList<>();
        reservations405.add(r405);
        bk405.setReservations(reservations405);
        
        // Customer CU21 reservation (different booking)
        Reservation r406 = new Reservation();
        r406.setId("R406");
        r406.setFlight(f405);
        r406.setPassengerName("P14");
        r406.setStatus("pending");
        
        Booking bk406 = new Booking();
        bk406.setId("BK406");
        bk406.setFlight(f405);
        List<Reservation> reservations406 = new ArrayList<>();
        reservations406.add(r406);
        bk406.setReservations(reservations406);
        
        airlineSystem.getFlights().add(f405);
        airlineSystem.getBookings().add(bk405);
        airlineSystem.getBookings().add(bk406);
        
        // Create a different reservation object that represents the "unknown" one
        Reservation unknownReservation = new Reservation();
        unknownReservation.setId("R406");
        unknownReservation.setFlight(f405);
        unknownReservation.setPassengerName("P14");
        unknownReservation.setStatus("pending");
        
        // Execute - try to confirm unknown reservation (R406 doesn't exist in CU20's booking)
        // Since the test specification mentions "Unknown reservation identifier", we need to simulate
        // a scenario where we're trying to operate on a reservation that doesn't belong to the customer
        // or doesn't exist in the context
        boolean result = unknownReservation.confirmOrCancel(true);
        
        // Verify
        assertFalse("Operation should fail for unknown reservation identifier", result);
    }
}