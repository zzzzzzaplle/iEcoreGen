import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR3Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_confirmPendingReservation() throws ParseException {
        // Setup Airline AL16
        Airline airline = new Airline();
        
        // Setup Airports AP160 (CityAA) and AP161 (CityAB)
        Airport ap160 = new Airport();
        ap160.setId("AP160");
        City cityAA = new City();
        cityAA.setName("CityAA");
        ap160.addCity(cityAA);
        
        Airport ap161 = new Airport();
        ap161.setId("AP161");
        City cityAB = new City();
        cityAB.setName("CityAB");
        ap161.addCity(cityAB);
        
        // Setup Flight F401
        Flight f401 = new Flight();
        f401.setId("F401");
        f401.setDepartureAirport(ap160);
        f401.setArrivalAirport(ap161);
        f401.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        f401.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        f401.setOpenForBooking(true);
        airline.addFlight(f401);
        
        // Setup Customer CU16 and passenger P9
        Customer cu16 = new Customer();
        
        // Setup Booking BK401 with reservation R401 (status = PENDING) for P9 on F401
        Booking bk401 = new Booking();
        bk401.setCustomer(cu16);
        cu16.getBookings().add(bk401);
        
        Passenger p9 = new Passenger();
        p9.setName("P9");
        
        Reservation r401 = new Reservation();
        r401.setId("R401");
        r401.setStatus(ReservationStatus.PENDING);
        r401.setPassenger(p9);
        r401.setFlight(f401);
        bk401.getReservations().add(r401);
        f401.getReservations().add(r401);
        
        // Current time = 2025-11-01 09:00:00
        Date now = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute: CU16 confirm reservation R401
        boolean result = cu16.confirm("R401", now);
        
        // Expected Output: True
        assertTrue(result);
        assertEquals(ReservationStatus.CONFIRMED, r401.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() throws ParseException {
        // Setup Airline AL17
        Airline airline = new Airline();
        
        // Setup Airports AP170 (CityAC) and AP171 (CityAD)
        Airport ap170 = new Airport();
        ap170.setId("AP170");
        City cityAC = new City();
        cityAC.setName("CityAC");
        ap170.addCity(cityAC);
        
        Airport ap171 = new Airport();
        ap171.setId("AP171");
        City cityAD = new City();
        cityAD.setName("CityAD");
        ap171.addCity(cityAD);
        
        // Setup Flight F402
        Flight f402 = new Flight();
        f402.setId("F402");
        f402.setDepartureAirport(ap170);
        f402.setArrivalAirport(ap171);
        f402.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        f402.setOpenForBooking(true);
        airline.addFlight(f402);
        
        // Setup Customer CU17 and passenger P10
        Customer cu17 = new Customer();
        
        // Setup Booking BK402 with reservation R402 (status = CONFIRMED) for P10 on F402
        Booking bk402 = new Booking();
        bk402.setCustomer(cu17);
        cu17.getBookings().add(bk402);
        
        Passenger p10 = new Passenger();
        p10.setName("P10");
        
        Reservation r402 = new Reservation();
        r402.setId("R402");
        r402.setStatus(ReservationStatus.CONFIRMED);
        r402.setPassenger(p10);
        r402.setFlight(f402);
        bk402.getReservations().add(r402);
        f402.getReservations().add(r402);
        
        // Current time = 2025-12-01 12:00:00
        Date now = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute: CU17 cancel reservation R402
        boolean result = cu17.cancel("R402", now);
        
        // Expected Output: True
        assertTrue(result);
        assertEquals(ReservationStatus.CANCELLED, r402.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() throws ParseException {
        // Setup Airline AL18
        Airline airline = new Airline();
        
        // Setup Airports AP180 (CityAE) and AP181 (CityAF)
        Airport ap180 = new Airport();
        ap180.setId("AP180");
        City cityAE = new City();
        cityAE.setName("CityAE");
        ap180.addCity(cityAE);
        
        Airport ap181 = new Airport();
        ap181.setId("AP181");
        City cityAF = new City();
        cityAF.setName("CityAF");
        ap181.addCity(cityAF);
        
        // Setup Flight F403
        Flight f403 = new Flight();
        f403.setId("F403");
        f403.setDepartureAirport(ap180);
        f403.setArrivalAirport(ap181);
        f403.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        f403.setOpenForBooking(true);
        airline.addFlight(f403);
        
        // Setup reservation R403 status = PENDING (passenger P11)
        Passenger p11 = new Passenger();
        p11.setName("P11");
        
        Reservation r403 = new Reservation();
        r403.setId("R403");
        r403.setStatus(ReservationStatus.PENDING);
        r403.setPassenger(p11);
        r403.setFlight(f403);
        f403.getReservations().add(r403);
        
        // Setup Customer CU18
        Customer cu18 = new Customer();
        Booking bk403 = new Booking();
        bk403.setCustomer(cu18);
        cu18.getBookings().add(bk403);
        bk403.getReservations().add(r403);
        
        // Current time = 2025-01-05 07:00:00 (flight already left)
        Date now = dateFormat.parse("2025-01-05 07:00:00");
        
        // Execute: Confirm reservation R403
        boolean result = cu18.confirm("R403", now);
        
        // Expected Output: False
        assertFalse(result);
        assertEquals(ReservationStatus.PENDING, r403.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() throws ParseException {
        // Setup Airline AL19
        Airline airline = new Airline();
        
        // Setup Airports AP190 (CityAG) and AP191 (CityAH)
        Airport ap190 = new Airport();
        ap190.setId("AP190");
        City cityAG = new City();
        cityAG.setName("CityAG");
        ap190.addCity(cityAG);
        
        Airport ap191 = new Airport();
        ap191.setId("AP191");
        City cityAH = new City();
        cityAH.setName("CityAH");
        ap191.addCity(cityAH);
        
        // Setup Flight F404 with openForBooking = False
        Flight f404 = new Flight();
        f404.setId("F404");
        f404.setDepartureAirport(ap190);
        f404.setArrivalAirport(ap191);
        f404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        f404.setOpenForBooking(false);
        airline.addFlight(f404);
        
        // Setup reservation R404 status = CONFIRMED (passenger P12)
        Passenger p12 = new Passenger();
        p12.setName("P12");
        
        Reservation r404 = new Reservation();
        r404.setId("R404");
        r404.setStatus(ReservationStatus.CONFIRMED);
        r404.setPassenger(p12);
        r404.setFlight(f404);
        f404.getReservations().add(r404);
        
        // Setup Customer CU19
        Customer cu19 = new Customer();
        Booking bk404 = new Booking();
        bk404.setCustomer(cu19);
        cu19.getBookings().add(bk404);
        bk404.getReservations().add(r404);
        
        // Current time = 2025-01-20 08:00:00
        Date now = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute: Cancel reservation R404
        boolean result = cu19.cancel("R404", now);
        
        // Expected Output: False
        assertFalse(result);
        assertEquals(ReservationStatus.CONFIRMED, r404.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() throws ParseException {
        // Setup Airline AL20
        Airline airline = new Airline();
        
        // Setup Airports AP200 (CityAI) and AP201 (CityAJ)
        Airport ap200 = new Airport();
        ap200.setId("AP200");
        City cityAI = new City();
        cityAI.setName("CityAI");
        ap200.addCity(cityAI);
        
        Airport ap201 = new Airport();
        ap201.setId("AP201");
        City cityAJ = new City();
        cityAJ.setName("CityAJ");
        ap201.addCity(cityAJ);
        
        // Setup Flight F405
        Flight f405 = new Flight();
        f405.setId("F405");
        f405.setDepartureAirport(ap200);
        f405.setArrivalAirport(ap201);
        f405.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        f405.setOpenForBooking(true);
        airline.addFlight(f405);
        
        // Setup Customer CU20 with one existing reservation R405 (status = PENDING) for passenger P13
        Customer cu20 = new Customer();
        Passenger p13 = new Passenger();
        p13.setName("P13");
        
        Reservation r405 = new Reservation();
        r405.setId("R405");
        r405.setStatus(ReservationStatus.PENDING);
        r405.setPassenger(p13);
        r405.setFlight(f405);
        
        Booking bk405 = new Booking();
        bk405.setCustomer(cu20);
        bk405.getReservations().add(r405);
        cu20.getBookings().add(bk405);
        f405.getReservations().add(r405);
        
        // Setup Customer CU21 with one existing reservation R406 (status = PENDING) for passenger P14
        Customer cu21 = new Customer();
        Passenger p14 = new Passenger();
        p14.setName("P14");
        
        Reservation r406 = new Reservation();
        r406.setId("R406");
        r406.setStatus(ReservationStatus.PENDING);
        r406.setPassenger(p14);
        
        Booking bk406 = new Booking();
        bk406.setCustomer(cu21);
        bk406.getReservations().add(r406);
        cu21.getBookings().add(bk406);
        
        // Current time = 2025-02-15 09:00:00
        Date now = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute: Customer CU20 confirm reservation R406 (which belongs to CU21)
        boolean result = cu20.confirm("R406", now);
        
        // Expected Output: False
        assertFalse(result);
        assertEquals(ReservationStatus.PENDING, r406.getStatus());
    }
}