import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CR3Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_confirmPendingReservation() {
        // Setup
        Airline AL16 = new Airline();
        Airport AP160 = new Airport("AP160");
        City cityAA = new City("CityAA");
        AP160.addCity(cityAA);
        
        Airport AP161 = new Airport("AP161");
        City cityAB = new City("CityAB");
        AP161.addCity(cityAB);
        
        Flight F401 = new Flight("F401");
        F401.setDepartureTime(LocalDateTime.parse("2025-12-10 11:00:00", formatter));
        F401.setArrivalTime(LocalDateTime.parse("2025-12-10 15:00:00", formatter));
        F401.setDepartureAirport(AP160);
        F401.setArrivalAirport(AP161);
        F401.setOpenForBooking(true);
        
        Customer CU16 = new Customer();
        Passenger P9 = new Passenger("P9");
        
        // Create booking and reservation
        Booking BK401 = new Booking(CU16);
        Reservation R401 = new Reservation("R401", ReservationStatus.PENDING, P9, F401);
        BK401.setReservations(Arrays.asList(R401));
        CU16.setBookings(Arrays.asList(BK401));
        F401.setReservations(Arrays.asList(R401));
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-11-01 09:00:00", formatter);
        
        // Execute
        boolean result = CU16.confirm("R401", currentTime);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertEquals("Reservation status should be CONFIRMED", ReservationStatus.CONFIRMED, R401.getStatus());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() {
        // Setup
        Airline AL17 = new Airline();
        Airport AP170 = new Airport("AP170");
        City cityAC = new City("CityAC");
        AP170.addCity(cityAC);
        
        Airport AP171 = new Airport("AP171");
        City cityAD = new City("CityAD");
        AP171.addCity(cityAD);
        
        Flight F402 = new Flight("F402");
        F402.setDepartureTime(LocalDateTime.parse("2025-12-15 15:00:00", formatter));
        F402.setArrivalTime(LocalDateTime.parse("2025-12-15 19:00:00", formatter));
        F402.setDepartureAirport(AP170);
        F402.setArrivalAirport(AP171);
        F402.setOpenForBooking(true);
        
        Customer CU17 = new Customer();
        Passenger P10 = new Passenger("P10");
        
        // Create booking and reservation
        Booking BK402 = new Booking(CU17);
        Reservation R402 = new Reservation("R402", ReservationStatus.CONFIRMED, P10, F402);
        BK402.setReservations(Arrays.asList(R402));
        CU17.setBookings(Arrays.asList(BK402));
        F402.setReservations(Arrays.asList(R402));
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-12-01 12:00:00", formatter);
        
        // Execute
        boolean result = CU17.cancel("R402", currentTime);
        
        // Verify
        assertTrue("Reservation should be cancelled successfully", result);
        assertEquals("Reservation status should be CANCELED", ReservationStatus.CANCELED, R402.getStatus());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() {
        // Setup
        Airline AL18 = new Airline();
        Airport AP180 = new Airport("AP180");
        City cityAE = new City("CityAE");
        AP180.addCity(cityAE);
        
        Airport AP181 = new Airport("AP181");
        City cityAF = new City("CityAF");
        AP181.addCity(cityAF);
        
        Flight F403 = new Flight("F403");
        F403.setDepartureTime(LocalDateTime.parse("2025-01-05 06:00:00", formatter));
        F403.setArrivalTime(LocalDateTime.parse("2025-01-05 10:00:00", formatter));
        F403.setDepartureAirport(AP180);
        F403.setArrivalAirport(AP181);
        F403.setOpenForBooking(true);
        
        Customer CU18 = new Customer();
        Passenger P11 = new Passenger("P11");
        
        // Create booking and reservation
        Booking BK403 = new Booking(CU18);
        Reservation R403 = new Reservation("R403", ReservationStatus.PENDING, P11, F403);
        BK403.setReservations(Arrays.asList(R403));
        CU18.setBookings(Arrays.asList(BK403));
        F403.setReservations(Arrays.asList(R403));
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-01-05 07:00:00", formatter);
        
        // Execute
        boolean result = CU18.confirm("R403", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail because flight has departed", result);
        assertEquals("Reservation status should remain PENDING", ReservationStatus.PENDING, R403.getStatus());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() {
        // Setup
        Airline AL19 = new Airline();
        Airport AP190 = new Airport("AP190");
        City cityAG = new City("CityAG");
        AP190.addCity(cityAG);
        
        Airport AP191 = new Airport("AP191");
        City cityAH = new City("CityAH");
        AP191.addCity(cityAH);
        
        Flight F404 = new Flight("F404");
        F404.setDepartureTime(LocalDateTime.parse("2025-02-01 09:00:00", formatter));
        F404.setArrivalTime(LocalDateTime.parse("2025-02-01 13:00:00", formatter));
        F404.setDepartureAirport(AP190);
        F404.setArrivalAirport(AP191);
        F404.setOpenForBooking(false); // Flight is closed for booking
        
        Customer CU19 = new Customer();
        Passenger P12 = new Passenger("P12");
        
        // Create booking and reservation
        Booking BK404 = new Booking(CU19);
        Reservation R404 = new Reservation("R404", ReservationStatus.CONFIRMED, P12, F404);
        BK404.setReservations(Arrays.asList(R404));
        CU19.setBookings(Arrays.asList(BK404));
        F404.setReservations(Arrays.asList(R404));
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-01-20 08:00:00", formatter);
        
        // Execute
        boolean result = CU19.cancel("R404", currentTime);
        
        // Verify
        assertFalse("Cancellation should fail because flight is closed", result);
        assertEquals("Reservation status should remain CONFIRMED", ReservationStatus.CONFIRMED, R404.getStatus());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() {
        // Setup
        Airline AL20 = new Airline();
        Airport AP200 = new Airport("AP200");
        City cityAI = new City("CityAI");
        AP200.addCity(cityAI);
        
        Airport AP201 = new Airport("AP201");
        City cityAJ = new City("CityAJ");
        AP201.addCity(cityAJ);
        
        Flight F405 = new Flight("F405");
        F405.setDepartureTime(LocalDateTime.parse("2025-03-10 10:00:00", formatter));
        F405.setArrivalTime(LocalDateTime.parse("2025-03-10 14:00:00", formatter));
        F405.setDepartureAirport(AP200);
        F405.setArrivalAirport(AP201);
        F405.setOpenForBooking(true);
        
        Customer CU20 = new Customer();
        Passenger P13 = new Passenger("P13");
        
        Customer CU21 = new Customer();
        Passenger P14 = new Passenger("P14");
        
        // Create bookings and reservations
        Booking BK405_CU20 = new Booking(CU20);
        Reservation R405 = new Reservation("R405", ReservationStatus.PENDING, P13, F405);
        BK405_CU20.setReservations(Arrays.asList(R405));
        CU20.setBookings(Arrays.asList(BK405_CU20));
        
        Booking BK406_CU21 = new Booking(CU21);
        Reservation R406 = new Reservation("R406", ReservationStatus.PENDING, P14, F405);
        BK406_CU21.setReservations(Arrays.asList(R406));
        CU21.setBookings(Arrays.asList(BK406_CU21));
        
        F405.setReservations(Arrays.asList(R405, R406));
        
        LocalDateTime currentTime = LocalDateTime.parse("2025-02-15 09:00:00", formatter);
        
        // Execute - CU20 tries to confirm R406 which belongs to CU21
        boolean result = CU20.confirm("R406", currentTime);
        
        // Verify
        assertFalse("Confirmation should fail because reservation ID doesn't belong to customer", result);
        assertEquals("R406 status should remain PENDING", ReservationStatus.PENDING, R406.getStatus());
    }
}