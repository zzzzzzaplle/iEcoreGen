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
    public void testCase1_NoReservationsToCancel() throws Exception {
        // Setup
        Airport ap10 = new Airport();
        City cityJ = new City();
        cityJ.setName("CityJ");
        ap10.addCity(cityJ);
        
        Airport ap11 = new Airport();
        City cityK = new City();
        cityK.setName("CityK");
        ap11.addCity(cityK);
        
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setOpenForBooking(true);
        
        airline.addFlight(f200);
        
        Date now = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", now);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, f200.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Airport ap12 = new Airport();
        City cityL = new City();
        cityL.setName("CityL");
        ap12.addCity(cityL);
        
        Airport ap13 = new Airport();
        City cityM = new City();
        cityM.setName("CityM");
        ap13.addCity(cityM);
        
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        f201.setOpenForBooking(true);
        
        // Create reservations
        Reservation r2011 = new Reservation();
        r2011.setId("R201-1");
        r2011.setStatus(ReservationStatus.CONFIRMED);
        r2011.setFlight(f201);
        
        Reservation r2012 = new Reservation();
        r2012.setId("R201-2");
        r2012.setStatus(ReservationStatus.CONFIRMED);
        r2012.setFlight(f201);
        
        Reservation r2013 = new Reservation();
        r2013.setId("R201-3");
        r2013.setStatus(ReservationStatus.CONFIRMED);
        r2013.setFlight(f201);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r2011);
        reservations.add(r2012);
        reservations.add(r2013);
        f201.setReservations(reservations);
        
        airline.addFlight(f201);
        
        Date now = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", now);
        
        // Verify
        assertTrue("Flight should be closed successfully", result);
        assertFalse("Flight should be closed", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        for (Reservation reservation : f201.getReservations()) {
            assertEquals("Reservation should be canceled", ReservationStatus.CANCELED, reservation.getStatus());
        }
        assertEquals("No confirmed reservations should remain", 0, f201.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() throws Exception {
        // Setup
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        f202.setOpenForBooking(false);
        
        airline.addFlight(f202);
        
        Date now = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", now);
        
        // Verify
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        f203.setOpenForBooking(true);
        
        // Create confirmed reservations
        Reservation r2031 = new Reservation();
        r2031.setId("R203-1");
        r2031.setStatus(ReservationStatus.CONFIRMED);
        r2031.setFlight(f203);
        
        Reservation r2032 = new Reservation();
        r2032.setId("R203-2");
        r2032.setStatus(ReservationStatus.CONFIRMED);
        r2032.setFlight(f203);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r2031);
        reservations.add(r2032);
        f203.setReservations(reservations);
        
        airline.addFlight(f203);
        
        Date now = dateFormat.parse("2025-09-10 09:10:00");
        
        // Execute
        boolean result = airline.closeFlight("F203", now);
        
        // Verify
        assertFalse("Should return false when trying to close after departure time", result);
        assertTrue("Flight should remain open", f203.isOpenForBooking());
        
        // Verify reservations are not canceled
        for (Reservation reservation : f203.getReservations()) {
            assertEquals("Reservation status should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() throws Exception {
        // Setup
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-02 01:30:00")); // Fixed: arrival should be next day
        f204.setOpenForBooking(true);
        
        airline.addFlight(f204);
        
        Date now = dateFormat.parse("2025-04-01 22:05:00");
        
        // Execute
        boolean result = airline.closeFlight("F204", now);
        
        // Verify
        assertFalse("Should return false when trying to close after departure", result);
        assertTrue("Flight should remain open", f204.isOpenForBooking());
    }
}