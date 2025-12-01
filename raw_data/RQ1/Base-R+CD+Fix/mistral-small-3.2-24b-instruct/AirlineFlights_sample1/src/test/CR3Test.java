import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CR3Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_confirmPendingReservation() throws Exception {
        // Setup
        Airline AL16 = new Airline();
        
        Airport AP160 = new Airport();
        AP160.setId("AP160");
        City CityAA = new City("CityAA");
        AP160.addCity(CityAA);
        
        Airport AP161 = new Airport();
        AP161.setId("AP161");
        City CityAB = new City("CityAB");
        AP161.addCity(CityAB);
        
        Flight F401 = new Flight();
        F401.setId("F401");
        F401.setDepartureAirport(AP160);
        F401.setArrialAirport(AP161);
        F401.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        F401.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        F401.setOpenForBooking(true);
        
        AL16.addFlight(F401);
        
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
        
        BK401.setReservations(new ArrayList<>());
        BK401.getReservations().add(R401);
        
        CU16.setBookings(new ArrayList<>());
        CU16.getBookings().add(BK401);
        
        F401.setReservations(new ArrayList<>());
        F401.getReservations().add(R401);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Test
        boolean result = CU16.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, R401.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws Exception {
        // Setup
        Airline AL17 = new Airline();
        
        Airport AP170 = new Airport();
        AP170.setId("AP170");
        City CityAC = new City("CityAC");
        AP170.addCity(CityAC);
        
        Airport AP171 = new Airport();
        AP171.setId("AP171");
        City CityAD = new City("CityAD");
        AP171.addCity(CityAD);
        
        Flight F402 = new Flight();
        F402.setId("F402");
        F402.setDepartureAirport(AP170);
        F402.setArrialAirport(AP171);
        F402.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        F402.setArrivalTime(dateFormat.parse("2025-12-15 19:00:00"));
        F402.setOpenForBooking(true);
        
        AL17.addFlight(F402);
        
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
        
        BK402.setReservations(new ArrayList<>());
        BK402.getReservations().add(R402);
        
        CU17.setBookings(new ArrayList<>());
        CU17.getBookings().add(BK402);
        
        F402.setReservations(new ArrayList<>());
        F402.getReservations().add(R402);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Test
        boolean result = CU17.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Reservation should be canceled successfully", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, R402.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws Exception {
        // Setup
        Airline AL18 = new Airline();
        
        Airport AP180 = new Airport();
        AP180.setId("AP180");
        City CityAE = new City("CityAE");
        AP180.addCity(CityAE);
        
        Airport AP181 = new Airport();
        AP181.setId("AP181");
        City CityAF = new City("CityAF");
        AP181.addCity(CityAF);
        
        Flight F403 = new Flight();
        F403.setId("F403");
        F403.setDepartureAirport(AP180);
        F403.setArrialAirport(AP181);
        F403.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        F403.setArrivalTime(dateFormat.parse("2025-01-05 10:00:00"));
        F403.setOpenForBooking(true);
        
        AL18.addFlight(F403);
        
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
        
        BK403.setReservations(new ArrayList<>());
        BK403.getReservations().add(R403);
        
        CU18.setBookings(new ArrayList<>());
        CU18.getBookings().add(BK403);
        
        F403.setReservations(new ArrayList<>());
        F403.getReservations().add(R403);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00");
        
        // Test
        boolean result = CU18.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Reservation should not be confirmed when flight has departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, R403.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws Exception {
        // Setup
        Airline AL19 = new Airline();
        
        Airport AP190 = new Airport();
        AP190.setId("AP190");
        City CityAG = new City("CityAG");
        AP190.addCity(CityAG);
        
        Airport AP191 = new Airport();
        AP191.setId("AP191");
        City CityAH = new City("CityAH");
        AP191.addCity(CityAH);
        
        Flight F404 = new Flight();
        F404.setId("F404");
        F404.setDepartureAirport(AP190);
        F404.setArrialAirport(AP191);
        F404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        F404.setArrivalTime(dateFormat.parse("2025-02-01 13:00:00"));
        F404.setOpenForBooking(false);
        
        AL19.addFlight(F404);
        
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
        
        BK404.setReservations(new ArrayList<>());
        BK404.getReservations().add(R404);
        
        CU19.setBookings(new ArrayList<>());
        CU19.getBookings().add(BK404);
        
        F404.setReservations(new ArrayList<>());
        F404.getReservations().add(R404);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Test
        boolean result = CU19.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Reservation should not be canceled when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, R404.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws Exception {
        // Setup
        Airline AL20 = new Airline();
        
        Airport AP200 = new Airport();
        AP200.setId("AP200");
        City CityAI = new City("CityAI");
        AP200.addCity(CityAI);
        
        Airport AP201 = new Airport();
        AP201.setId("AP201");
        City CityAJ = new City("CityAJ");
        AP201.addCity(CityAJ);
        
        Flight F405 = new Flight();
        F405.setId("F405");
        F405.setDepartureAirport(AP200);
        F405.setArrialAirport(AP201);
        F405.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        F405.setArrivalTime(dateFormat.parse("2025-03-10 14:00:00"));
        F405.setOpenForBooking(true);
        
        AL20.addFlight(F405);
        
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
        
        BK405.setReservations(new ArrayList<>());
        BK405.getReservations().add(R405);
        
        CU20.setBookings(new ArrayList<>());
        CU20.getBookings().add(BK405);
        
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
        
        BK406.setReservations(new ArrayList<>());
        BK406.getReservations().add(R406);
        
        CU21.setBookings(new ArrayList<>());
        CU21.getBookings().add(BK406);
        
        F405.setReservations(new ArrayList<>());
        F405.getReservations().add(R405);
        F405.getReservations().add(R406);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Test - Customer CU20 trying to confirm reservation R406 which belongs to CU21
        boolean result = CU20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Customer should not be able to confirm another customer's reservation", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, R406.getStatus());
    }
}