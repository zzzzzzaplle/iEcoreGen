import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        ap160.setName("CityAA Airport");
        List<String> servedCities160 = new ArrayList<>();
        servedCities160.add("CityAA");
        ap160.setServedCities(servedCities160);
        
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        ap161.setName("CityAB Airport");
        List<String> servedCities161 = new ArrayList<>();
        servedCities161.add("CityAB");
        ap161.setServedCities(servedCities161);
        
        Flight f401 = new Flight();
        f401.setId("F401");
        f401.setDepartureAirport(ap160);
        f401.setArrivalAirport(ap161);
        f401.setDepartureTime(LocalDateTime.parse("2025-12-10 11:00:00", formatter));
        f401.setArrivalTime(LocalDateTime.parse("2025-12-10 15:00:00", formatter));
        f401.setOpenForBooking(true);
        f401.setPublished(true);
        
        Reservation r401 = new Reservation();
        r401.setId("R401");
        r401.setFlight(f401);
        r401.setPassengerName("P9");
        r401.setStatus("pending");
        
        airlineSystem.getFlights().add(f401);
        airlineSystem.getReservations().add(r401);
        
        // Execute - Confirm reservation R401
        boolean result = airlineSystem.confirmOrCancelReservation("R401", true);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be confirmed", "confirmed", r401.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() {
        // Setup
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        ap170.setName("CityAC Airport");
        List<String> servedCities170 = new ArrayList<>();
        servedCities170.add("CityAC");
        ap170.setServedCities(servedCities170);
        
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        ap171.setName("CityAD Airport");
        List<String> servedCities171 = new ArrayList<>();
        servedCities171.add("CityAD");
        ap171.setServedCities(servedCities171);
        
        Flight f402 = new Flight();
        f402.setId("F402");
        f402.setDepartureAirport(ap170);
        f402.setArrivalAirport(ap171);
        f402.setDepartureTime(LocalDateTime.parse("2025-12-15 15:00:00", formatter));
        f402.setOpenForBooking(true);
        f402.setPublished(true);
        
        Reservation r402 = new Reservation();
        r402.setId("R402");
        r402.setFlight(f402);
        r402.setPassengerName("P10");
        r402.setStatus("confirmed");
        
        airlineSystem.getFlights().add(f402);
        airlineSystem.getReservations().add(r402);
        
        // Execute - Cancel reservation R402
        boolean result = airlineSystem.confirmOrCancelReservation("R402", false);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be canceled", "canceled", r402.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() {
        // Setup
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        ap180.setName("CityAE Airport");
        List<String> servedCities180 = new ArrayList<>();
        servedCities180.add("CityAE");
        ap180.setServedCities(servedCities180);
        
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        ap181.setName("CityAF Airport");
        List<String> servedCities181 = new ArrayList<>();
        servedCities181.add("CityAF");
        ap181.setServedCities(servedCities181);
        
        Flight f403 = new Flight();
        f403.setId("F403");
        f403.setDepartureAirport(ap180);
        f403.setArrivalAirport(ap181);
        f403.setDepartureTime(LocalDateTime.parse("2025-01-05 06:00:00", formatter));
        f403.setOpenForBooking(true);
        f403.setPublished(true);
        
        Reservation r403 = new Reservation();
        r403.setId("R403");
        r403.setFlight(f403);
        r403.setPassengerName("P11");
        r403.setStatus("pending");
        
        airlineSystem.getFlights().add(f403);
        airlineSystem.getReservations().add(r403);
        
        // Execute - Try to confirm reservation R403 after flight departure
        boolean result = airlineSystem.confirmOrCancelReservation("R403", true);
        
        // Verify
        assertFalse("Confirmation should fail because flight has departed", result);
        assertEquals("Reservation status should remain pending", "pending", r403.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() {
        // Setup
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        ap190.setName("CityAG Airport");
        List<String> servedCities190 = new ArrayList<>();
        servedCities190.add("CityAG");
        ap190.setServedCities(servedCities190);
        
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        ap191.setName("CityAH Airport");
        List<String> servedCities191 = new ArrayList<>();
        servedCities191.add("CityAH");
        ap191.setServedCities(servedCities191);
        
        Flight f404 = new Flight();
        f404.setId("F404");
        f404.setDepartureAirport(ap190);
        f404.setArrivalAirport(ap191);
        f404.setDepartureTime(LocalDateTime.parse("2025-02-01 09:00:00", formatter));
        f404.setOpenForBooking(false); // Flight is closed
        f404.setPublished(true);
        
        Reservation r404 = new Reservation();
        r404.setId("R404");
        r404.setFlight(f404);
        r404.setPassengerName("P12");
        r404.setStatus("confirmed");
        
        airlineSystem.getFlights().add(f404);
        airlineSystem.getReservations().add(r404);
        
        // Execute - Try to cancel reservation R404 on closed flight
        boolean result = airlineSystem.confirmOrCancelReservation("R404", false);
        
        // Verify
        assertFalse("Cancellation should fail because flight is closed", result);
        assertEquals("Reservation status should remain confirmed", "confirmed", r404.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() {
        // Setup
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        ap200.setName("CityAI Airport");
        List<String> servedCities200 = new ArrayList<>();
        servedCities200.add("CityAI");
        ap200.setServedCities(servedCities200);
        
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        ap201.setName("CityAJ Airport");
        List<String> servedCities201 = new ArrayList<>();
        servedCities201.add("CityAJ");
        ap201.setServedCities(servedCities201);
        
        Flight f405 = new Flight();
        f405.setId("F405");
        f405.setDepartureAirport(ap200);
        f405.setArrivalAirport(ap201);
        f405.setDepartureTime(LocalDateTime.parse("2025-03-10 10:00:00", formatter));
        f405.setOpenForBooking(true);
        f405.setPublished(true);
        
        Reservation r405 = new Reservation();
        r405.setId("R405");
        r405.setFlight(f405);
        r405.setPassengerName("P13");
        r405.setStatus("pending");
        
        airlineSystem.getFlights().add(f405);
        airlineSystem.getReservations().add(r405);
        
        // Execute - Try to confirm non-existent reservation R406
        boolean result = airlineSystem.confirmOrCancelReservation("R406", true);
        
        // Verify
        assertFalse("Operation should fail for unknown reservation ID", result);
    }
}