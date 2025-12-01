import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        List<String> passengers = Arrays.asList("P9");
        Date now = dateFormat.parse("2025-11-01 09:00:00");
        cu16.addBooking(f401, now, passengers);
        
        // Get the reservation ID (R401)
        String reservationID = cu16.getBookings().get(0).getReservations().get(0).getId();
        
        // Confirm reservation R401
        boolean result = cu16.confirm(reservationID, now);
        
        // Expected Output: True
        assertTrue(result);
        assertEquals(ReservationStatus.CONFIRMED, cu16.getBookings().get(0).getReservations().get(0).getStatus());
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
        
        // Setup Booking BK402 with reservation R402 (status = PENDING) for P10 on F402
        List<String> passengers = Arrays.asList("P10");
        Date now = dateFormat.parse("2025-12-01 12:00:00");
        cu17.addBooking(f402, now, passengers);
        
        // Get the reservation ID (R402) and confirm it first
        String reservationID = cu17.getBookings().get(0).getReservations().get(0).getId();
        cu17.confirm(reservationID, now);
        
        // Verify it's confirmed
        assertEquals(ReservationStatus.CONFIRMED, cu17.getBookings().get(0).getReservations().get(0).getStatus());
        
        // Cancel reservation R402
        boolean result = cu17.cancel(reservationID, now);
        
        // Expected Output: True
        assertTrue(result);
        assertEquals(ReservationStatus.CANCELLED, cu17.getBookings().get(0).getReservations().get(0).getStatus());
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
        
        // Setup Customer with passenger P11
        Customer customer = new Customer();
        
        // Setup reservation R403 (status = PENDING) for P11 on F403
        List<String> passengers = Arrays.asList("P11");
        Date bookingTime = dateFormat.parse("2025-01-01 00:00:00");
        customer.addBooking(f403, bookingTime, passengers);
        
        // Get the reservation ID (R403)
        String reservationID = customer.getBookings().get(0).getReservations().get(0).getId();
        
        // Current time = 2025-01-05 07:00:00 (flight already left)
        Date now = dateFormat.parse("2025-01-05 07:00:00");
        
        // Attempt to confirm reservation R403
        boolean result = customer.confirm(reservationID, now);
        
        // Expected Output: False
        assertFalse(result);
        assertEquals(ReservationStatus.PENDING, customer.getBookings().get(0).getReservations().get(0).getStatus());
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
        
        // Setup Flight F404 (openForBooking = False)
        Flight f404 = new Flight();
        f404.setId("F404");
        f404.setDepartureAirport(ap190);
        f404.setArrivalAirport(ap191);
        f404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        f404.setOpenForBooking(false);
        airline.addFlight(f404);
        
        // Setup Customer with passenger P12
        Customer customer = new Customer();
        
        // Setup reservation R404 (status = CONFIRMED) for P12 on F404
        List<String> passengers = Arrays.asList("P12");
        Date bookingTime = dateFormat.parse("2025-01-01 00:00:00");
        
        // First, temporarily open the flight for booking to create the reservation
        f404.setOpenForBooking(true);
        customer.addBooking(f404, bookingTime, passengers);
        
        // Get the reservation ID (R404) and confirm it
        String reservationID = customer.getBookings().get(0).getReservations().get(0).getId();
        customer.confirm(reservationID, bookingTime);
        
        // Verify it's confirmed
        assertEquals(ReservationStatus.CONFIRMED, customer.getBookings().get(0).getReservations().get(0).getStatus());
        
        // Close the flight again
        f404.setOpenForBooking(false);
        
        // Current time = 2025-01-20 08:00:00
        Date now = dateFormat.parse("2025-01-20 08:00:00");
        
        // Attempt to cancel reservation R404
        boolean result = customer.cancel(reservationID, now);
        
        // Expected Output: False
        assertFalse(result);
        assertEquals(ReservationStatus.CONFIRMED, customer.getBookings().get(0).getReservations().get(0).getStatus());
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
        List<String> passengers20 = Arrays.asList("P13");
        Date now = dateFormat.parse("2025-02-15 09:00:00");
        cu20.addBooking(f405, now, passengers20);
        String reservationID_R405 = cu20.getBookings().get(0).getReservations().get(0).getId();
        
        // Setup Customer CU21 with one existing reservation R406 (status = PENDING) for passenger P14
        Customer cu21 = new Customer();
        List<String> passengers21 = Arrays.asList("P14");
        cu21.addBooking(f405, now, passengers21);
        String reservationID_R406 = cu21.getBookings().get(0).getReservations().get(0).getId();
        
        // Customer CU20 attempts to confirm reservation R406 (which belongs to CU21)
        boolean result = cu20.confirm(reservationID_R406, now);
        
        // Expected Output: False
        assertFalse(result);
        assertEquals(ReservationStatus.PENDING, cu21.getBookings().get(0).getReservations().get(0).getStatus());
    }
}