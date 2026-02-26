import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private FlightSystem flightSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        flightSystem = new FlightSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup: Airline AL6; airports AP10 (CityJ) and AP11 (CityK)
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        List<String> citiesAP10 = new ArrayList<>();
        citiesAP10.add("CityJ");
        ap10.setCities(citiesAP10);
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        List<String> citiesAP11 = new ArrayList<>();
        citiesAP11.add("CityK");
        ap11.setCities(citiesAP11);
        
        // Setup: Flight F200
        Flight flightF200 = new Flight();
        flightF200.setId("F200");
        flightF200.setDepartureAirport(ap10);
        flightF200.setArrivalAirport(ap11);
        flightF200.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        flightF200.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        flightF200.setOpenForBooking(true);
        flightF200.setPublished(true);
        
        flightSystem.getFlights().add(flightF200);
        
        // Current time = 2025-05-01 08:00:00 (before departure)
        // Mock current time by ensuring flight is valid (departure in future)
        
        // Execute closeFlight
        boolean result = flightSystem.closeFlight("F200");
        
        // Verify results
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed", flightF200.isOpenForBooking());
        assertEquals("No reservations should exist", 0, flightF200.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup: Airline AL7; airports AP12 (CityL) and AP13 (CityM)
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        List<String> citiesAP12 = new ArrayList<>();
        citiesAP12.add("CityL");
        ap12.setCities(citiesAP12);
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        List<String> citiesAP13 = new ArrayList<>();
        citiesAP13.add("CityM");
        ap13.setCities(citiesAP13);
        
        // Setup: Flight F201
        Flight flightF201 = new Flight();
        flightF201.setId("F201");
        flightF201.setDepartureAirport(ap12);
        flightF201.setArrivalAirport(ap13);
        flightF201.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        flightF201.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        flightF201.setOpenForBooking(true);
        flightF201.setPublished(true);
        
        // Setup: Three confirmed reservations
        Reservation res1 = new Reservation();
        res1.setId("R201-1");
        res1.setFlight(flightF201);
        res1.setPassengerName("Passenger1");
        res1.setStatus(Reservation.CONFIRMED);
        
        Reservation res2 = new Reservation();
        res2.setId("R201-2");
        res2.setFlight(flightF201);
        res2.setPassengerName("Passenger2");
        res2.setStatus(Reservation.CONFIRMED);
        
        Reservation res3 = new Reservation();
        res3.setId("R201-3");
        res3.setFlight(flightF201);
        res3.setPassengerName("Passenger3");
        res3.setStatus(Reservation.CONFIRMED);
        
        flightF201.getReservations().add(res1);
        flightF201.getReservations().add(res2);
        flightF201.getReservations().add(res3);
        
        flightSystem.getFlights().add(flightF201);
        
        // Current time = 2025-06-10 12:00:00 (before departure)
        
        // Execute closeFlight
        boolean result = flightSystem.closeFlight("F201");
        
        // Verify results
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed", flightF201.isOpenForBooking());
        
        // Verify all three reservations are canceled
        assertEquals("Should have 3 reservations", 3, flightF201.getReservations().size());
        for (Reservation res : flightF201.getReservations()) {
            assertEquals("Reservation should be canceled", Reservation.CANCELED, res.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup: Airline AL8; flight F202 openForBooking = False
        Airport departureAirport = new Airport();
        departureAirport.setId("AP14");
        List<String> cities1 = new ArrayList<>();
        cities1.add("CityN");
        departureAirport.setCities(cities1);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP15");
        List<String> cities2 = new ArrayList<>();
        cities2.add("CityO");
        arrivalAirport.setCities(cities2);
        
        Flight flightF202 = new Flight();
        flightF202.setId("F202");
        flightF202.setDepartureAirport(departureAirport);
        flightF202.setArrivalAirport(arrivalAirport);
        flightF202.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        flightF202.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        flightF202.setOpenForBooking(false); // Already closed
        flightF202.setPublished(true);
        
        flightSystem.getFlights().add(flightF202);
        
        // Current time = 2025-07-01 09:00:00 (before departure)
        
        // Execute closeFlight
        boolean result = flightSystem.closeFlight("F202");
        
        // Verify results
        assertFalse("Close flight should return false for already closed flight", result);
        assertFalse("Flight should remain closed", flightF202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup: Airline AL9; flight F203 openForBooking = True
        Airport departureAirport = new Airport();
        departureAirport.setId("AP16");
        List<String> cities1 = new ArrayList<>();
        cities1.add("CityP");
        departureAirport.setCities(cities1);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP17");
        List<String> cities2 = new ArrayList<>();
        cities2.add("CityQ");
        arrivalAirport.setCities(cities2);
        
        Flight flightF203 = new Flight();
        flightF203.setId("F203");
        flightF203.setDepartureAirport(departureAirport);
        flightF203.setArrivalAirport(arrivalAirport);
        flightF203.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        flightF203.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        flightF203.setOpenForBooking(true);
        flightF203.setPublished(true);
        
        // Setup: Two confirmed reservations
        Reservation res1 = new Reservation();
        res1.setId("R203-1");
        res1.setFlight(flightF203);
        res1.setPassengerName("Passenger1");
        res1.setStatus(Reservation.CONFIRMED);
        
        Reservation res2 = new Reservation();
        res2.setId("R203-2");
        res2.setFlight(flightF203);
        res2.setPassengerName("Passenger2");
        res2.setStatus(Reservation.CONFIRMED);
        
        flightF203.getReservations().add(res1);
        flightF203.getReservations().add(res2);
        
        flightSystem.getFlights().add(flightF203);
        
        // Current time = 2025-09-10 09:10:00 (after departure time)
        // Since we can't mock LocalDateTime.now(), we rely on the flight's isValid() logic
        // The flight departure time is in the past relative to current system time during test execution
        // This should cause closeFlight to return false
        
        // Execute closeFlight
        boolean result = flightSystem.closeFlight("F203");
        
        // Verify results - should fail because current time is after departure
        assertFalse("Close flight should return false when current time is after departure", result);
        assertTrue("Flight should remain open", flightF203.isOpenForBooking());
        
        // Verify reservations remain confirmed (not canceled)
        for (Reservation res : flightF203.getReservations()) {
            assertEquals("Reservations should remain confirmed", Reservation.CONFIRMED, res.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup: Airline AL10; flight F204 openForBooking = True
        Airport departureAirport = new Airport();
        departureAirport.setId("AP18");
        List<String> cities1 = new ArrayList<>();
        cities1.add("CityR");
        departureAirport.setCities(cities1);
        
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("AP19");
        List<String> cities2 = new ArrayList<>();
        cities2.add("CityS");
        arrivalAirport.setCities(cities2);
        
        Flight flightF204 = new Flight();
        flightF204.setId("F204");
        flightF204.setDepartureAirport(departureAirport);
        flightF204.setArrivalAirport(arrivalAirport);
        flightF204.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        flightF204.setArrivalTime(LocalDateTime.parse("2025-04-02 01:30:00", formatter)); // Fixed date (next day)
        flightF204.setOpenForBooking(true);
        flightF204.setPublished(true);
        
        flightSystem.getFlights().add(flightF204);
        
        // Current time = 2025-04-01 22:05:00 (after departure)
        // Since departure time is in the past relative to current system time during test execution
        // This should cause closeFlight to return false
        
        // Execute closeFlight
        boolean result = flightSystem.closeFlight("F204");
        
        // Verify results
        assertFalse("Close flight should return false when current time is after departure", result);
        assertTrue("Flight should remain open", flightF204.isOpenForBooking());
    }
}