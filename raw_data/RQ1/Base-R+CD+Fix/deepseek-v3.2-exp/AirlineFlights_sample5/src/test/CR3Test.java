import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR3Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
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
        
        // Create flight
        Flight f401 = new Flight();
        f401.setId("F401");
        f401.setDepartureAirport(ap160);
        f401.setArrivalAirport(ap161);
        f401.setDepartureTime(dateFormat.parse("2025-12-10 11:00:00"));
        f401.setArrivalTime(dateFormat.parse("2025-12-10 15:00:00"));
        f401.setOpenForBooking(true);
        
        // Create passenger
        Passenger p9 = new Passenger();
        p9.setName("P9");
        
        // Create reservation
        Reservation r401 = new Reservation();
        r401.setId("R401");
        r401.setPassenger(p9);
        r401.setFlight(f401);
        r401.setStatus(ReservationStatus.PENDING);
        
        // Add reservation to flight
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r401);
        f401.setReservations(flightReservations);
        
        // Create customer and booking
        Customer cu16 = new Customer();
        Booking bk401 = new Booking();
        bk401.setCustomer(cu16);
        List<Reservation> bookingReservations = new ArrayList<>();
        bookingReservations.add(r401);
        bk401.setReservations(bookingReservations);
        
        List<Booking> customerBookings = new ArrayList<>();
        customerBookings.add(bk401);
        cu16.setBookings(customerBookings);
        
        Date currentTime = dateFormat.parse("2025-11-01 09:00:00");
        
        // Execute
        boolean result = cu16.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Should successfully confirm pending reservation", result);
        assertEquals("Reservation status should be CONFIRMED", 
                     ReservationStatus.CONFIRMED, r401.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
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
        
        // Create flight
        Flight f402 = new Flight();
        f402.setId("F402");
        f402.setDepartureAirport(ap170);
        f402.setArrivalAirport(ap171);
        f402.setDepartureTime(dateFormat.parse("2025-12-15 15:00:00"));
        f402.setOpenForBooking(true);
        
        // Create passenger
        Passenger p10 = new Passenger();
        p10.setName("P10");
        
        // Create reservation
        Reservation r402 = new Reservation();
        r402.setId("R402");
        r402.setPassenger(p10);
        r402.setFlight(f402);
        r402.setStatus(ReservationStatus.CONFIRMED);
        
        // Add reservation to flight
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r402);
        f402.setReservations(flightReservations);
        
        // Create customer and booking
        Customer cu17 = new Customer();
        Booking bk402 = new Booking();
        bk402.setCustomer(cu17);
        List<Reservation> bookingReservations = new ArrayList<>();
        bookingReservations.add(r402);
        bk402.setReservations(bookingReservations);
        
        List<Booking> customerBookings = new ArrayList<>();
        customerBookings.add(bk402);
        cu17.setBookings(customerBookings);
        
        Date currentTime = dateFormat.parse("2025-12-01 12:00:00");
        
        // Execute
        boolean result = cu17.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Should successfully cancel confirmed reservation", result);
        assertEquals("Reservation status should be CANCELED", 
                     ReservationStatus.CANCELED, r402.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
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
        
        // Create flight
        Flight f403 = new Flight();
        f403.setId("F403");
        f403.setDepartureAirport(ap180);
        f403.setArrivalAirport(ap181);
        f403.setDepartureTime(dateFormat.parse("2025-01-05 06:00:00"));
        f403.setOpenForBooking(true);
        
        // Create passenger
        Passenger p11 = new Passenger();
        p11.setName("P11");
        
        // Create reservation
        Reservation r403 = new Reservation();
        r403.setId("R403");
        r403.setPassenger(p11);
        r403.setFlight(f403);
        r403.setStatus(ReservationStatus.PENDING);
        
        // Add reservation to flight
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r403);
        f403.setReservations(flightReservations);
        
        // Create customer and booking
        Customer cu18 = new Customer(); // Assuming AL18 airline implies CU18 customer
        Booking bk403 = new Booking();
        bk403.setCustomer(cu18);
        List<Reservation> bookingReservations = new ArrayList<>();
        bookingReservations.add(r403);
        bk403.setReservations(bookingReservations);
        
        List<Booking> customerBookings = new ArrayList<>();
        customerBookings.add(bk403);
        cu18.setBookings(customerBookings);
        
        Date currentTime = dateFormat.parse("2025-01-05 07:00:00"); // Flight already departed
        
        // Execute
        boolean result = cu18.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Should fail to confirm reservation when flight has departed", result);
        assertEquals("Reservation status should remain PENDING", 
                     ReservationStatus.PENDING, r403.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
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
        
        // Create flight
        Flight f404 = new Flight();
        f404.setId("F404");
        f404.setDepartureAirport(ap190);
        f404.setArrivalAirport(ap191);
        f404.setDepartureTime(dateFormat.parse("2025-02-01 09:00:00"));
        f404.setOpenForBooking(false); // Flight is closed for booking
        
        // Create passenger
        Passenger p12 = new Passenger();
        p12.setName("P12");
        
        // Create reservation
        Reservation r404 = new Reservation();
        r404.setId("R404");
        r404.setPassenger(p12);
        r404.setFlight(f404);
        r404.setStatus(ReservationStatus.CONFIRMED);
        
        // Add reservation to flight
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r404);
        f404.setReservations(flightReservations);
        
        // Create customer and booking
        Customer cu19 = new Customer(); // Assuming AL19 airline implies CU19 customer
        Booking bk404 = new Booking();
        bk404.setCustomer(cu19);
        List<Reservation> bookingReservations = new ArrayList<>();
        bookingReservations.add(r404);
        bk404.setReservations(bookingReservations);
        
        List<Booking> customerBookings = new ArrayList<>();
        customerBookings.add(bk404);
        cu19.setBookings(customerBookings);
        
        Date currentTime = dateFormat.parse("2025-01-20 08:00:00");
        
        // Execute
        boolean result = cu19.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Should fail to cancel reservation when flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", 
                     ReservationStatus.CONFIRMED, r404.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
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
        
        // Create flight
        Flight f405 = new Flight();
        f405.setId("F405");
        f405.setDepartureAirport(ap200);
        f405.setArrivalAirport(ap201);
        f405.setDepartureTime(dateFormat.parse("2025-03-10 10:00:00"));
        f405.setOpenForBooking(true);
        
        // Create passengers
        Passenger p13 = new Passenger();
        p13.setName("P13");
        Passenger p14 = new Passenger();
        p14.setName("P14");
        
        // Create reservations
        Reservation r405 = new Reservation();
        r405.setId("R405");
        r405.setPassenger(p13);
        r405.setFlight(f405);
        r405.setStatus(ReservationStatus.PENDING);
        
        Reservation r406 = new Reservation();
        r406.setId("R406");
        r406.setPassenger(p14);
        r406.setFlight(f405);
        r406.setStatus(ReservationStatus.PENDING);
        
        // Add reservations to flight
        List<Reservation> flightReservations = new ArrayList<>();
        flightReservations.add(r405);
        flightReservations.add(r406);
        f405.setReservations(flightReservations);
        
        // Create customer CU20 with booking BK405 containing R405
        Customer cu20 = new Customer();
        Booking bk405 = new Booking();
        bk405.setCustomer(cu20);
        List<Reservation> booking405Reservations = new ArrayList<>();
        booking405Reservations.add(r405);
        bk405.setReservations(booking405Reservations);
        
        List<Booking> cu20Bookings = new ArrayList<>();
        cu20Bookings.add(bk405);
        cu20.setBookings(cu20Bookings);
        
        // Create customer CU21 with booking BK406 containing R406
        Customer cu21 = new Customer();
        Booking bk406 = new Booking();
        bk406.setCustomer(cu21);
        List<Reservation> booking406Reservations = new ArrayList<>();
        booking406Reservations.add(r406);
        bk406.setReservations(booking406Reservations);
        
        List<Booking> cu21Bookings = new ArrayList<>();
        cu21Bookings.add(bk406);
        cu21.setBookings(cu21Bookings);
        
        Date currentTime = dateFormat.parse("2025-02-15 09:00:00");
        
        // Execute - CU20 trying to confirm R406 which belongs to CU21
        boolean result = cu20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Should fail to confirm reservation that doesn't belong to customer", result);
        assertEquals("Reservation R406 status should remain PENDING", 
                     ReservationStatus.PENDING, r406.getStatus());
    }
}