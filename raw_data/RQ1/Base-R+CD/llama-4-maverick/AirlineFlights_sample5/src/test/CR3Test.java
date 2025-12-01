import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR3Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() throws Exception {
        // Setup
        Airline AL16 = new Airline();
        
        Airport AP160 = new Airport();
        AP160.setId("AP160");
        Airport AP161 = new Airport();
        AP161.setId("AP161");
        
        Flight F401 = new Flight();
        F401.setId("F401");
        F401.setDepartureAirport(AP160);
        F401.setArrivalAirport(AP161);
        F401.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        F401.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        F401.setOpenForBooking(true);
        
        Customer CU16 = new Customer();
        Passenger P9 = new Passenger();
        P9.setName("P9");
        
        Booking BK401 = new Booking();
        BK401.setCustomer(CU16);
        
        Reservation R401 = new Reservation();
        R401.setId("R401");
        R401.setStatus(ReservationStatus.PENDING);
        R401.setPassenger(P9);
        R401.setFlight(F401);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(R401);
        BK401.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(BK401);
        CU16.setBookings(bookings);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = CU16.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Confirm pending reservation should return true", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, R401.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() throws Exception {
        // Setup
        Airline AL17 = new Airline();
        
        Airport AP170 = new Airport();
        AP170.setId("AP170");
        Airport AP171 = new Airport();
        AP171.setId("AP171");
        
        Flight F402 = new Flight();
        F402.setId("F402");
        F402.setDepartureAirport(AP170);
        F402.setArrivalAirport(AP171);
        F402.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        F402.setOpenForBooking(true);
        
        Customer CU17 = new Customer();
        Passenger P10 = new Passenger();
        P10.setName("P10");
        
        Booking BK402 = new Booking();
        BK402.setCustomer(CU17);
        
        Reservation R402 = new Reservation();
        R402.setId("R402");
        R402.setStatus(ReservationStatus.CONFIRMED);
        R402.setPassenger(P10);
        R402.setFlight(F402);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(R402);
        BK402.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(BK402);
        CU17.setBookings(bookings);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = CU17.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Cancel confirmed reservation should return true", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, R402.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() throws Exception {
        // Setup
        Airline AL18 = new Airline();
        
        Airport AP180 = new Airport();
        AP180.setId("AP180");
        Airport AP181 = new Airport();
        AP181.setId("AP181");
        
        Flight F403 = new Flight();
        F403.setId("F403");
        F403.setDepartureAirport(AP180);
        F403.setArrivalAirport(AP181);
        F403.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        F403.setOpenForBooking(true);
        
        Customer CU18 = new Customer();
        Passenger P11 = new Passenger();
        P11.setName("P11");
        
        Booking BK403 = new Booking();
        BK403.setCustomer(CU18);
        
        Reservation R403 = new Reservation();
        R403.setId("R403");
        R403.setStatus(ReservationStatus.PENDING);
        R403.setPassenger(P11);
        R403.setFlight(F403);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(R403);
        BK403.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(BK403);
        CU18.setBookings(bookings);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute
        boolean result = CU18.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirm reservation after flight departure should return false", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, R403.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() throws Exception {
        // Setup
        Airline AL19 = new Airline();
        
        Airport AP190 = new Airport();
        AP190.setId("AP190");
        Airport AP191 = new Airport();
        AP191.setId("AP191");
        
        Flight F404 = new Flight();
        F404.setId("F404");
        F404.setDepartureAirport(AP190);
        F404.setArrivalAirport(AP191);
        F404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        F404.setOpenForBooking(false);
        
        Customer CU19 = new Customer();
        Passenger P12 = new Passenger();
        P12.setName("P12");
        
        Booking BK404 = new Booking();
        BK404.setCustomer(CU19);
        
        Reservation R404 = new Reservation();
        R404.setId("R404");
        R404.setStatus(ReservationStatus.CONFIRMED);
        R404.setPassenger(P12);
        R404.setFlight(F404);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(R404);
        BK404.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(BK404);
        CU19.setBookings(bookings);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = CU19.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancel reservation on closed flight should return false", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, R404.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() throws Exception {
        // Setup
        Airline AL20 = new Airline();
        
        Airport AP200 = new Airport();
        AP200.setId("AP200");
        Airport AP201 = new Airport();
        AP201.setId("AP201");
        
        Flight F405 = new Flight();
        F405.setId("F405");
        F405.setDepartureAirport(AP200);
        F405.setArrivalAirport(AP201);
        F405.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        F405.setOpenForBooking(true);
        
        Customer CU20 = new Customer();
        Passenger P13 = new Passenger();
        P13.setName("P13");
        
        Booking BK405 = new Booking();
        BK405.setCustomer(CU20);
        
        Reservation R405 = new Reservation();
        R405.setId("R405");
        R405.setStatus(ReservationStatus.PENDING);
        R405.setPassenger(P13);
        R405.setFlight(F405);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(R405);
        BK405.setReservations(reservations);
        
        List<Booking> bookings = new ArrayList<>();
        bookings.add(BK405);
        CU20.setBookings(bookings);
        
        Customer CU21 = new Customer();
        Passenger P14 = new Passenger();
        P14.setName("P14");
        
        Booking BK406 = new Booking();
        BK406.setCustomer(CU21);
        
        Reservation R406 = new Reservation();
        R406.setId("R406");
        R406.setStatus(ReservationStatus.PENDING);
        R406.setPassenger(P14);
        R406.setFlight(F405);
        
        List<Reservation> reservations2 = new ArrayList<>();
        reservations2.add(R406);
        BK406.setReservations(reservations2);
        
        List<Booking> bookings2 = new ArrayList<>();
        bookings2.add(BK406);
        CU21.setBookings(bookings2);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute
        boolean result = CU20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirm unknown reservation ID should return false", result);
    }
}