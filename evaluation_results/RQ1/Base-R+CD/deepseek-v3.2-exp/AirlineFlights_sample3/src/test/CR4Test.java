import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR4Test {
    
    private Airline airline;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() throws Exception {
        // Setup: Airline AL6; airports AP10 (CityJ) and AP11 (CityK)
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        City cityJ = new City();
        cityJ.setName("CityJ");
        ap10.addCity(cityJ);
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        City cityK = new City();
        cityK.setName("CityK");
        ap11.addCity(cityK);
        
        // Flight F200: depart 2025-06-20 09:00:00, arrive 2025-06-20 13:00:00, 0 reservations
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        
        airline.addFlight(flight);
        
        // Current time = 2025-05-01 08:00:00
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute: Close flight F200
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify: True and no canceled reservation
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed", flight.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, flight.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
        // Setup: Airline AL7; airports AP12 (CityL) and AP13 (CityM)
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        City cityL = new City();
        cityL.setName("CityL");
        ap12.addCity(cityL);
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        City cityM = new City();
        cityM.setName("CityM");
        ap13.addCity(cityM);
        
        // Flight F201: depart 2025-07-15 14:00:00, arrive 2025-07-15 18:00:00
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        
        // Create three confirmed reservations R201-1, R201-2, R201-3
        Reservation res1 = new Reservation();
        res1.setId("R201-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        Passenger pass1 = new Passenger();
        pass1.setName("Passenger1");
        res1.setPassenger(pass1);
        res1.setFlight(flight);
        
        Reservation res2 = new Reservation();
        res2.setId("R201-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        Passenger pass2 = new Passenger();
        pass2.setName("Passenger2");
        res2.setPassenger(pass2);
        res2.setFlight(flight);
        
        Reservation res3 = new Reservation();
        res3.setId("R201-3");
        res3.setStatus(ReservationStatus.CONFIRMED);
        Passenger pass3 = new Passenger();
        pass3.setName("Passenger3");
        res3.setPassenger(pass3);
        res3.setFlight(flight);
        
        flight.getReservations().add(res1);
        flight.getReservations().add(res2);
        flight.getReservations().add(res3);
        
        airline.addFlight(flight);
        
        // Current time = 2025-06-10 12:00:00
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute: Close flight F201
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify: True and the three reservations are canceled
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed", flight.isOpenForBooking());
        
        // Check that all confirmed reservations are now canceled
        List<Reservation> confirmedReservations = flight.getConfirmedReservations();
        assertEquals("All confirmed reservations should be canceled", 0, confirmedReservations.size());
        
        // Verify individual reservation status
        assertEquals("Reservation 1 should be canceled", ReservationStatus.CANCELLED, res1.getStatus());
        assertEquals("Reservation 2 should be canceled", ReservationStatus.CANCELLED, res2.getStatus());
        assertEquals("Reservation 3 should be canceled", ReservationStatus.CANCELLED, res3.getStatus());
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup: Airline AL8; flight F202 openForBooking = False
        Airport ap14 = new Airport();
        ap14.setId("AP14");
        City cityN = new City();
        cityN.setName("CityN");
        ap14.addCity(cityN);
        
        Airport ap15 = new Airport();
        ap15.setId("AP15");
        City cityO = new City();
        cityO.setName("CityO");
        ap15.addCity(cityO);
        
        // Flight F202: depart 2025-08-10 11:00:00, arrive 2025-08-10 13:30:00
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setOpenForBooking(false); // Already closed
        flight.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        flight.setDepartureAirport(ap14);
        flight.setArrivalAirport(ap15);
        
        airline.addFlight(flight);
        
        // Current time = 2025-07-01 09:00:00
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute: Close flight F202
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify: False (flight already closed)
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup: Airline AL9; flight F203 openForBooking = True
        Airport ap16 = new Airport();
        ap16.setId("AP16");
        City cityP = new City();
        cityP.setName("CityP");
        ap16.addCity(cityP);
        
        Airport ap17 = new Airport();
        ap17.setId("AP17");
        City cityQ = new City();
        cityQ.setName("CityQ");
        ap17.addCity(cityQ);
        
        // Flight F203: depart 2025-09-10 09:00:00, arrive 2025-09-10 15:30:00
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        flight.setDepartureAirport(ap16);
        flight.setArrivalAirport(ap17);
        
        // Two confirmed reservations R203-1, R203-2
        Reservation res1 = new Reservation();
        res1.setId("R203-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        Passenger pass1 = new Passenger();
        pass1.setName("Passenger1");
        res1.setPassenger(pass1);
        res1.setFlight(flight);
        
        Reservation res2 = new Reservation();
        res2.setId("R203-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        Passenger pass2 = new Passenger();
        pass2.setName("Passenger2");
        res2.setPassenger(pass2);
        res2.setFlight(flight);
        
        flight.getReservations().add(res1);
        flight.getReservations().add(res2);
        
        airline.addFlight(flight);
        
        // Current time = 2025-09-10 09:10:00 (after departure time)
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00");
        
        // Execute: Close flight F203
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify: False (flight has already departed)
        assertFalse("Should return false when trying to close after departure", result);
        assertTrue("Flight should remain open since close failed", flight.isOpenForBooking());
        
        // Verify reservations are still confirmed
        assertEquals("Reservation 1 should remain confirmed", ReservationStatus.CONFIRMED, res1.getStatus());
        assertEquals("Reservation 2 should remain confirmed", ReservationStatus.CONFIRMED, res2.getStatus());
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup: Airline AL10; flight F204 openForBooking = True
        Airport ap18 = new Airport();
        ap18.setId("AP18");
        City cityR = new City();
        cityR.setName("CityR");
        ap18.addCity(cityR);
        
        Airport ap19 = new Airport();
        ap19.setId("AP19");
        City cityS = new City();
        cityS.setName("CityS");
        ap19.addCity(cityS);
        
        // Flight F204: depart 2025-04-01 22:00:00, arrive 2025-04-1 01:30:00
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-01 01:30:00")); // Note: This appears to be same day arrival
        
        airline.addFlight(flight);
        
        // Current time = 2025-04-01 22:05:00 (flight already left)
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00");
        
        // Execute: Close flight F204
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify: False (flight has already departed)
        assertFalse("Should return false when trying to close after departure", result);
        assertTrue("Flight should remain open since close failed", flight.isOpenForBooking());
    }
}