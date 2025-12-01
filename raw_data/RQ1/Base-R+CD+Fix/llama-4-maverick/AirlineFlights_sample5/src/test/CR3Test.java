import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR3Test {
    private SimpleDateFormat dateFormat;
    private Airline airline;
    private Customer customer;
    private Date currentTime;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_confirmPendingReservation() throws ParseException {
        // Setup
        airline = new Airline();
        currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        Airport ap160 = new Airport();
        ap160.setId("AP160");
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        
        Flight f401 = new Flight();
        f401.setId("F401");
        f401.setDepartureAirport(ap160);
        f401.setArrivalAirport(ap161);
        f401.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        f401.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        f401.setOpenForBooking(true);
        
        Passenger p9 = new Passenger();
        p9.setName("P9");
        
        Reservation r401 = new Reservation();
        r401.setId("R401");
        r401.setStatus(ReservationStatus.PENDING);
        r401.setPassenger(p9);
        r401.setFlight(f401);
        
        Booking bk401 = new Booking();
        bk401.setReservations(Collections.singletonList(r401));
        
        customer = new Customer();
        customer.setBookings(Collections.singletonList(bk401));
        
        f401.setReservations(Collections.singletonList(r401));
        airline.addFlight(f401);
        
        // Execute
        boolean result = customer.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, r401.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws ParseException {
        // Setup
        airline = new Airline();
        currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        
        Flight f402 = new Flight();
        f402.setId("F402");
        f402.setDepartureAirport(ap170);
        f402.setArrivalAirport(ap171);
        f402.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        f402.setOpenForBooking(true);
        
        Passenger p10 = new Passenger();
        p10.setName("P10");
        
        Reservation r402 = new Reservation();
        r402.setId("R402");
        r402.setStatus(ReservationStatus.CONFIRMED);
        r402.setPassenger(p10);
        r402.setFlight(f402);
        
        Booking bk402 = new Booking();
        bk402.setReservations(Collections.singletonList(r402));
        
        customer = new Customer();
        customer.setBookings(Collections.singletonList(bk402));
        
        f402.setReservations(Collections.singletonList(r402));
        airline.addFlight(f402);
        
        // Execute
        boolean result = customer.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, r402.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws ParseException {
        // Setup
        airline = new Airline();
        currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        
        Flight f403 = new Flight();
        f403.setId("F403");
        f403.setDepartureAirport(ap180);
        f403.setArrivalAirport(ap181);
        f403.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        f403.setOpenForBooking(true);
        
        Passenger p11 = new Passenger();
        p11.setName("P11");
        
        Reservation r403 = new Reservation();
        r403.setId("R403");
        r403.setStatus(ReservationStatus.PENDING);
        r403.setPassenger(p11);
        r403.setFlight(f403);
        
        Booking bk403 = new Booking();
        bk403.setReservations(Collections.singletonList(r403));
        
        customer = new Customer();
        customer.setBookings(Collections.singletonList(bk403));
        
        f403.setReservations(Collections.singletonList(r403));
        airline.addFlight(f403);
        
        // Execute
        boolean result = customer.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail when flight has departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, r403.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws ParseException {
        // Setup
        airline = new Airline();
        currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        
        Flight f404 = new Flight();
        f404.setId("F404");
        f404.setDepartureAirport(ap190);
        f404.setArrivalAirport(ap191);
        f404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        f404.setOpenForBooking(false);
        
        Passenger p12 = new Passenger();
        p12.setName("P12");
        
        Reservation r404 = new Reservation();
        r404.setId("R404");
        r404.setStatus(ReservationStatus.CONFIRMED);
        r404.setPassenger(p12);
        r404.setFlight(f404);
        
        Booking bk404 = new Booking();
        bk404.setReservations(Collections.singletonList(r404));
        
        customer = new Customer();
        customer.setBookings(Collections.singletonList(bk404));
        
        f404.setReservations(Collections.singletonList(r404));
        airline.addFlight(f404);
        
        // Execute
        boolean result = customer.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, r404.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws ParseException {
        // Setup
        airline = new Airline();
        currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        
        Flight f405 = new Flight();
        f405.setId("F405");
        f405.setDepartureAirport(ap200);
        f405.setArrivalAirport(ap201);
        f405.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        f405.setOpenForBooking(true);
        
        Passenger p13 = new Passenger();
        p13.setName("P13");
        
        Reservation r405 = new Reservation();
        r405.setId("R405");
        r405.setStatus(ReservationStatus.PENDING);
        r405.setPassenger(p13);
        r405.setFlight(f405);
        
        Passenger p14 = new Passenger();
        p14.setName("P14");
        
        Reservation r406 = new Reservation();
        r406.setId("R406");
        r406.setStatus(ReservationStatus.PENDING);
        r406.setPassenger(p14);
        r406.setFlight(f405);
        
        Booking bk405 = new Booking();
        bk405.setReservations(Collections.singletonList(r405));
        
        Booking bk406 = new Booking();
        bk406.setReservations(Collections.singletonList(r406));
        
        Customer cu20 = new Customer();
        cu20.setBookings(Collections.singletonList(bk405));
        
        Customer cu21 = new Customer();
        cu21.setBookings(Collections.singletonList(bk406));
        
        f405.setReservations(Arrays.asList(r405, r406));
        airline.addFlight(f405);
        
        // Execute
        boolean result = cu20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail for unknown reservation ID", result);
        assertEquals("R405 status should remain PENDING", ReservationStatus.PENDING, r405.getStatus());
        assertEquals("R406 status should remain PENDING", ReservationStatus.PENDING, r406.getStatus());
    }
}