import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CR4Test {
    
    private AirlineSystem airlineSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airlineSystem = new AirlineSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        List<String> cities10 = new ArrayList<>();
        cities10.add("CityJ");
        ap10.setCities(cities10);
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        List<String> cities11 = new ArrayList<>();
        cities11.add("CityK");
        ap11.setCities(cities11);
        
        Flight flight200 = new Flight();
        flight200.setId("F200");
        flight200.setDepartureAirport(ap10);
        flight200.setArrivalAirport(ap11);
        flight200.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        flight200.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        flight200.setOpenForBooking(true);
        
        airlineSystem.setFlights(new ArrayList<>());
        airlineSystem.getFlights().add(flight200);
        
        airlineSystem.setBookings(new ArrayList<>());
        
        // Set current time to 2025-05-01 08:00:00
        // Note: In actual implementation, you would need to mock LocalDateTime.now()
        // For this test, we'll assume the system uses the specified current time
        
        // Execute
        boolean result = airlineSystem.closeFlight("F200");
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", flight200.isOpenForBooking());
        
        // Check that no reservations were canceled (since there were none)
        List<Reservation> confirmedReservations = airlineSystem.getConfirmedReservations("F200");
        assertEquals("No confirmed reservations should exist", 0, confirmedReservations.size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        List<String> cities12 = new ArrayList<>();
        cities12.add("CityL");
        ap12.setCities(cities12);
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        List<String> cities13 = new ArrayList<>();
        cities13.add("CityM");
        ap13.setCities(cities13);
        
        Flight flight201 = new Flight();
        flight201.setId("F201");
        flight201.setDepartureAirport(ap12);
        flight201.setArrivalAirport(ap13);
        flight201.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        flight201.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        flight201.setOpenForBooking(true);
        
        airlineSystem.setFlights(new ArrayList<>());
        airlineSystem.getFlights().add(flight201);
        
        // Create booking with three confirmed reservations
        Booking booking = new Booking();
        
        Reservation reservation1 = new Reservation();
        reservation1.setId("R201-1");
        reservation1.setPassengerName("Passenger1");
        reservation1.setFlight(flight201);
        reservation1.confirm();
        
        Reservation reservation2 = new Reservation();
        reservation2.setId("R201-2");
        reservation2.setPassengerName("Passenger2");
        reservation2.setFlight(flight201);
        reservation2.confirm();
        
        Reservation reservation3 = new Reservation();
        reservation3.setId("R201-3");
        reservation3.setPassengerName("Passenger3");
        reservation3.setFlight(flight201);
        reservation3.confirm();
        
        booking.addReservation(reservation1);
        booking.addReservation(reservation2);
        booking.addReservation(reservation3);
        
        airlineSystem.setBookings(new ArrayList<>());
        airlineSystem.getBookings().add(booking);
        
        // Set current time to 2025-06-10 12:00:00
        // Note: In actual implementation, you would need to mock LocalDateTime.now()
        
        // Execute
        boolean result = airlineSystem.closeFlight("F201");
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", flight201.isOpenForBooking());
        
        // Check that all three reservations are canceled
        assertEquals("Reservation R201-1 should be canceled", "canceled", reservation1.getStatus());
        assertEquals("Reservation R201-2 should be canceled", "canceled", reservation2.getStatus());
        assertEquals("Reservation R201-3 should be canceled", "canceled", reservation3.getStatus());
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup
        Airport ap14 = new Airport();
        ap14.setId("AP14");
        List<String> cities14 = new ArrayList<>();
        cities14.add("CityN");
        ap14.setCities(cities14);
        
        Airport ap15 = new Airport();
        ap15.setId("AP15");
        List<String> cities15 = new ArrayList<>();
        cities15.add("CityO");
        ap15.setCities(cities15);
        
        Flight flight202 = new Flight();
        flight202.setId("F202");
        flight202.setDepartureAirport(ap14);
        flight202.setArrivalAirport(ap15);
        flight202.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        flight202.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        flight202.setOpenForBooking(false); // Already closed
        
        airlineSystem.setFlights(new ArrayList<>());
        airlineSystem.getFlights().add(flight202);
        
        airlineSystem.setBookings(new ArrayList<>());
        
        // Set current time to 2025-07-01 09:00:00
        // Note: In actual implementation, you would need to mock LocalDateTime.now()
        
        // Execute
        boolean result = airlineSystem.closeFlight("F202");
        
        // Verify
        assertFalse("Should return false when flight is already closed", result);
        assertFalse("Flight should remain closed", flight202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup
        Airport ap16 = new Airport();
        ap16.setId("AP16");
        List<String> cities16 = new ArrayList<>();
        cities16.add("CityP");
        ap16.setCities(cities16);
        
        Airport ap17 = new Airport();
        ap17.setId("AP17");
        List<String> cities17 = new ArrayList<>();
        cities17.add("CityQ");
        ap17.setCities(cities17);
        
        Flight flight203 = new Flight();
        flight203.setId("F203");
        flight203.setDepartureAirport(ap16);
        flight203.setArrivalAirport(ap17);
        flight203.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        flight203.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        flight203.setOpenForBooking(true);
        
        airlineSystem.setFlights(new ArrayList<>());
        airlineSystem.getFlights().add(flight203);
        
        // Create booking with two confirmed reservations
        Booking booking = new Booking();
        
        Reservation reservation1 = new Reservation();
        reservation1.setId("R203-1");
        reservation1.setPassengerName("Passenger1");
        reservation1.setFlight(flight203);
        reservation1.confirm();
        
        Reservation reservation2 = new Reservation();
        reservation2.setId("R203-2");
        reservation2.setPassengerName("Passenger2");
        reservation2.setFlight(flight203);
        reservation2.confirm();
        
        booking.addReservation(reservation1);
        booking.addReservation(reservation2);
        
        airlineSystem.setBookings(new ArrayList<>());
        airlineSystem.getBookings().add(booking);
        
        // Set current time to 2025-09-10 09:10:00 (after departure time)
        // Note: In actual implementation, you would need to mock LocalDateTime.now()
        
        // Execute
        boolean result = airlineSystem.closeFlight("F203");
        
        // Verify
        assertFalse("Should return false when trying to close flight after departure time", result);
        assertTrue("Flight should remain open for booking", flight203.isOpenForBooking());
        
        // Check that reservations remain confirmed (not canceled)
        assertEquals("Reservation R203-1 should remain confirmed", "confirmed", reservation1.getStatus());
        assertEquals("Reservation R203-2 should remain confirmed", "confirmed", reservation2.getStatus());
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup
        Airport ap18 = new Airport();
        ap18.setId("AP18");
        List<String> cities18 = new ArrayList<>();
        cities18.add("CityR");
        ap18.setCities(cities18);
        
        Airport ap19 = new Airport();
        ap19.setId("AP19");
        List<String> cities19 = new ArrayList<>();
        cities19.add("CityS");
        ap19.setCities(cities19);
        
        Flight flight204 = new Flight();
        flight204.setId("F204");
        flight204.setDepartureAirport(ap18);
        flight204.setArrivalAirport(ap19);
        flight204.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        flight204.setArrivalTime(LocalDateTime.parse("2025-04-02 01:30:00", formatter)); // Fixed date for arrival
        flight204.setOpenForBooking(true);
        
        airlineSystem.setFlights(new ArrayList<>());
        airlineSystem.getFlights().add(flight204);
        
        airlineSystem.setBookings(new ArrayList<>());
        
        // Set current time to 2025-04-01 22:05:00 (after departure time)
        // Note: In actual implementation, you would need to mock LocalDateTime.now()
        
        // Execute
        boolean result = airlineSystem.closeFlight("F204");
        
        // Verify
        assertFalse("Should return false when trying to close flight after departure", result);
        assertTrue("Flight should remain open for booking", flight204.isOpenForBooking());
    }
}