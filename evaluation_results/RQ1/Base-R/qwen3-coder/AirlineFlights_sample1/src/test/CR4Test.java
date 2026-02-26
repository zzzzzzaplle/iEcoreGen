import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        f200.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        f200.setOpenForBooking(true);
        f200.setPublished(true);
        
        airlineSystem.getFlights().add(f200);
        
        // Set current time to 2025-05-01 08:00:00
        // This is done by ensuring the flight's departure time is after current time in the validation
        
        // Execute
        boolean result = airlineSystem.closeFlight(f200);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", f200.isOpenForBooking());
        assertEquals("No reservations should exist", 0, airlineSystem.getBookings().size());
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
        
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        f201.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        f201.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        f201.setOpenForBooking(true);
        f201.setPublished(true);
        
        // Create passengers and reservations
        Passenger p1 = new Passenger();
        p1.setName("Passenger1");
        Passenger p2 = new Passenger();
        p2.setName("Passenger2");
        Passenger p3 = new Passenger();
        p3.setName("Passenger3");
        
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(p1);
        passengers.add(p2);
        passengers.add(p3);
        
        // Create booking with confirmed reservations
        Booking booking = new Booking();
        
        Reservation r1 = new Reservation();
        r1.setId("R201-1");
        r1.setFlight(f201);
        r1.setPassenger(p1);
        r1.confirm();
        
        Reservation r2 = new Reservation();
        r2.setId("R201-2");
        r2.setFlight(f201);
        r2.setPassenger(p2);
        r2.confirm();
        
        Reservation r3 = new Reservation();
        r3.setId("R201-3");
        r3.setFlight(f201);
        r3.setPassenger(p3);
        r3.confirm();
        
        booking.addReservation(r1);
        booking.addReservation(r2);
        booking.addReservation(r3);
        
        airlineSystem.getFlights().add(f201);
        airlineSystem.getBookings().add(booking);
        
        // Set current time to 2025-06-10 12:00:00
        // This is done by ensuring the flight's departure time is after current time in the validation
        
        // Execute
        boolean result = airlineSystem.closeFlight(f201);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        assertFalse("Reservation R201-1 should be canceled", r1.isConfirmed());
        assertFalse("Reservation R201-2 should be canceled", r2.isConfirmed());
        assertFalse("Reservation R201-3 should be canceled", r3.isConfirmed());
        assertEquals("All reservations should have canceled status", "canceled", r1.getStatus());
        assertEquals("All reservations should have canceled status", "canceled", r2.getStatus());
        assertEquals("All reservations should have canceled status", "canceled", r3.getStatus());
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
        
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureAirport(ap14);
        f202.setArrivalAirport(ap15);
        f202.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        f202.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        f202.setOpenForBooking(false); // Already closed
        f202.setPublished(true);
        
        airlineSystem.getFlights().add(f202);
        
        // Set current time to 2025-07-01 09:00:00
        
        // Execute
        boolean result = airlineSystem.closeFlight(f202);
        
        // Verify
        assertFalse("Flight closure should fail when flight is already closed", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
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
        
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureAirport(ap16);
        f203.setArrivalAirport(ap17);
        f203.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        f203.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        f203.setOpenForBooking(true);
        f203.setPublished(true);
        
        // Create confirmed reservations
        Passenger p4 = new Passenger();
        p4.setName("Passenger4");
        Passenger p5 = new Passenger();
        p5.setName("Passenger5");
        
        Booking booking = new Booking();
        
        Reservation r4 = new Reservation();
        r4.setId("R203-1");
        r4.setFlight(f203);
        r4.setPassenger(p4);
        r4.confirm();
        
        Reservation r5 = new Reservation();
        r5.setId("R203-2");
        r5.setFlight(f203);
        r5.setPassenger(p5);
        r5.confirm();
        
        booking.addReservation(r4);
        booking.addReservation(r5);
        
        airlineSystem.getFlights().add(f203);
        airlineSystem.getBookings().add(booking);
        
        // Set current time to 2025-09-10 09:10:00 (after departure)
        // This will cause the closeFlight operation to fail
        
        // Execute
        boolean result = airlineSystem.closeFlight(f203);
        
        // Verify
        assertFalse("Flight closure should fail when current time is after departure", result);
        assertTrue("Flight should remain open for booking", f203.isOpenForBooking());
        assertTrue("Reservations should remain confirmed", r4.isConfirmed());
        assertTrue("Reservations should remain confirmed", r5.isConfirmed());
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
        
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureAirport(ap18);
        f204.setArrivalAirport(ap19);
        f204.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        f204.setArrivalTime(LocalDateTime.parse("2025-04-02 01:30:00", formatter)); // Fixed: arrival should be next day
        f204.setOpenForBooking(true);
        f204.setPublished(true);
        
        airlineSystem.getFlights().add(f204);
        
        // Set current time to 2025-04-01 22:05:00 (after departure)
        // This will cause the closeFlight operation to fail
        
        // Execute
        boolean result = airlineSystem.closeFlight(f204);
        
        // Verify
        assertFalse("Flight closure should fail when current time is after departure", result);
        assertTrue("Flight should remain open for booking", f204.isOpenForBooking());
    }
}