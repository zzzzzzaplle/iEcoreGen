import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CR4Test {
    
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        Set<String> cities10 = new HashSet<>();
        cities10.add("CityJ");
        ap10.setServedCities(cities10);
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        Set<String> cities11 = new HashSet<>();
        cities11.add("CityK");
        ap11.setServedCities(cities11);
        
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        f200.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        f200.setOpenForBooking(true);
        
        // Set current time to 2025-05-01 08:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-05-01 08:00:00", formatter);
        
        // Execute
        boolean result = f200.closeFlight();
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", f200.isOpenForBooking());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        Set<String> cities12 = new HashSet<>();
        cities12.add("CityL");
        ap12.setServedCities(cities12);
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        Set<String> cities13 = new HashSet<>();
        cities13.add("CityM");
        ap13.setServedCities(cities13);
        
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        f201.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        f201.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        f201.setOpenForBooking(true);
        
        // Create booking with three confirmed reservations
        Booking booking = new Booking();
        booking.setFlight(f201);
        
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("Passenger1");
        passengerNames.add("Passenger2");
        passengerNames.add("Passenger3");
        
        booking.createBooking(passengerNames);
        
        // Confirm all reservations
        for (Reservation reservation : booking.getReservations()) {
            reservation.confirmOrCancel(true);
        }
        
        // Verify reservations are confirmed before closing flight
        assertEquals("All 3 reservations should be confirmed", 3, booking.getConfirmedReservations().size());
        
        // Set current time to 2025-06-10 12:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-06-10 12:00:00", formatter);
        
        // Execute
        boolean result = f201.closeFlight();
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", f201.isOpenForBooking());
        
        // Verify all reservations are now canceled
        assertEquals("All reservations should be canceled after flight closure", 0, booking.getConfirmedReservations().size());
        for (Reservation reservation : booking.getReservations()) {
            assertEquals("Reservation status should be canceled", "canceled", reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup
        Airport ap14 = new Airport();
        ap14.setId("AP14");
        Set<String> cities14 = new HashSet<>();
        cities14.add("CityN");
        ap14.setServedCities(cities14);
        
        Airport ap15 = new Airport();
        ap15.setId("AP15");
        Set<String> cities15 = new HashSet<>();
        cities15.add("CityO");
        ap15.setServedCities(cities15);
        
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureAirport(ap14);
        f202.setArrivalAirport(ap15);
        f202.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        f202.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        f202.setOpenForBooking(false); // Flight already closed
        
        // Set current time to 2025-07-01 09:00:00
        LocalDateTime currentTime = LocalDateTime.parse("2025-07-01 09:00:00", formatter);
        
        // Execute
        boolean result = f202.closeFlight();
        
        // Verify
        assertFalse("Should return false when flight is already closed", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup
        Airport ap16 = new Airport();
        ap16.setId("AP16");
        Set<String> cities16 = new HashSet<>();
        cities16.add("CityP");
        ap16.setServedCities(cities16);
        
        Airport ap17 = new Airport();
        ap17.setId("AP17");
        Set<String> cities17 = new HashSet<>();
        cities17.add("CityQ");
        ap17.setServedCities(cities17);
        
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureAirport(ap16);
        f203.setArrivalAirport(ap17);
        f203.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        f203.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        f203.setOpenForBooking(true);
        
        // Create booking with two confirmed reservations
        Booking booking = new Booking();
        booking.setFlight(f203);
        
        List<String> passengerNames = new ArrayList<>();
        passengerNames.add("PassengerA");
        passengerNames.add("PassengerB");
        
        booking.createBooking(passengerNames);
        
        // Confirm all reservations
        for (Reservation reservation : booking.getReservations()) {
            reservation.confirmOrCancel(true);
        }
        
        // Set current time to 2025-09-10 09:10:00 (after departure time)
        LocalDateTime currentTime = LocalDateTime.parse("2025-09-10 09:10:00", formatter);
        
        // Execute
        boolean result = f203.closeFlight();
        
        // Verify
        assertFalse("Should return false when trying to close after departure time", result);
        assertTrue("Flight should remain open since closure failed", f203.isOpenForBooking());
        
        // Verify reservations remain confirmed
        assertEquals("Reservations should remain confirmed", 2, booking.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup
        Airport ap18 = new Airport();
        ap18.setId("AP18");
        Set<String> cities18 = new HashSet<>();
        cities18.add("CityR");
        ap18.setServedCities(cities18);
        
        Airport ap19 = new Airport();
        ap19.setId("AP19");
        Set<String> cities19 = new HashSet<>();
        cities19.add("CityS");
        ap19.setServedCities(cities19);
        
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureAirport(ap18);
        f204.setArrivalAirport(ap19);
        f204.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        f204.setArrivalTime(LocalDateTime.parse("2025-04-02 01:30:00", formatter));
        f204.setOpenForBooking(true);
        
        // Set current time to 2025-04-01 22:05:00 (after departure)
        LocalDateTime currentTime = LocalDateTime.parse("2025-04-01 22:05:00", formatter);
        
        // Execute
        boolean result = f204.closeFlight();
        
        // Verify
        assertFalse("Should return false when trying to close after departure", result);
        assertTrue("Flight should remain open since closure failed", f204.isOpenForBooking());
    }
}