import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        // Setup
        Airport ap10 = new Airport();
        ap10.setId("AP10");
        ap10.addCity(new City("CityJ"));
        
        Airport ap11 = new Airport();
        ap11.setId("AP11");
        ap11.addCity(new City("CityK"));
        
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setOpenForBooking(true);
        
        airline.addFlight(f200);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Expected closeFlight to return true", result);
        assertFalse("Flight should be closed", f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, f200.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Airport ap12 = new Airport();
        ap12.setId("AP12");
        ap12.addCity(new City("CityL"));
        
        Airport ap13 = new Airport();
        ap13.setId("AP13");
        ap13.addCity(new City("CityM"));
        
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
        f201.setOpenForBooking(true);
        
        // Create and add confirmed reservations
        Reservation r201_1 = new Reservation();
        r201_1.setId("R201-1");
        r201_1.setStatus(ReservationStatus.CONFIRMED);
        r201_1.setFlight(f201);
        
        Reservation r201_2 = new Reservation();
        r201_2.setId("R201-2");
        r201_2.setStatus(ReservationStatus.CONFIRMED);
        r201_2.setFlight(f201);
        
        Reservation r201_3 = new Reservation();
        r201_3.setId("R201-3");
        r201_3.setStatus(ReservationStatus.CONFIRMED);
        r201_3.setFlight(f201);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r201_1);
        reservations.add(r201_2);
        reservations.add(r201_3);
        f201.setReservations(reservations);
        
        airline.addFlight(f201);
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Expected closeFlight to return true", result);
        assertFalse("Flight should be closed", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        for (Reservation reservation : f201.getReservations()) {
            assertEquals("Reservation should be canceled", ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup
        Airport ap14 = new Airport();
        ap14.setId("AP14");
        ap14.addCity(new City("CityN"));
        
        Airport ap15 = new Airport();
        ap15.setId("AP15");
        ap15.addCity(new City("CityO"));
        
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        f202.setDepartureAirport(ap14);
        f202.setArrivalAirport(ap15);
        f202.setOpenForBooking(false); // Flight already closed
        
        airline.addFlight(f202);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Expected closeFlight to return false for already closed flight", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Airport ap16 = new Airport();
        ap16.setId("AP16");
        ap16.addCity(new City("CityP"));
        
        Airport ap17 = new Airport();
        ap17.setId("AP17");
        ap17.addCity(new City("CityQ"));
        
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        f203.setDepartureAirport(ap16);
        f203.setArrivalAirport(ap17);
        f203.setOpenForBooking(true);
        
        // Create and add confirmed reservations
        Reservation r203_1 = new Reservation();
        r203_1.setId("R203-1");
        r203_1.setStatus(ReservationStatus.CONFIRMED);
        r203_1.setFlight(f203);
        
        Reservation r203_2 = new Reservation();
        r203_2.setId("R203-2");
        r203_2.setStatus(ReservationStatus.CONFIRMED);
        r203_2.setFlight(f203);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r203_1);
        reservations.add(r203_2);
        f203.setReservations(reservations);
        
        airline.addFlight(f203);
        
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Expected closeFlight to return false when current time is after departure", result);
        assertTrue("Flight should remain open", f203.isOpenForBooking());
        
        // Check that reservations remain confirmed (not canceled)
        for (Reservation reservation : f203.getReservations()) {
            assertEquals("Reservation should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        Airport ap18 = new Airport();
        ap18.setId("AP18");
        ap18.addCity(new City("CityR"));
        
        Airport ap19 = new Airport();
        ap19.setId("AP19");
        ap19.addCity(new City("CityS"));
        
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-01 01:30:00"));
        f204.setDepartureAirport(ap18);
        f204.setArrivalAirport(ap19);
        f204.setOpenForBooking(true);
        
        airline.addFlight(f204);
        
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Expected closeFlight to return false when current time is after departure", result);
        assertTrue("Flight should remain open", f204.isOpenForBooking());
    }
}