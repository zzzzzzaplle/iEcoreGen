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
        // Setup
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
        
        Flight f200 = new Flight();
        f200.setId("F200");
        f200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrialAirport(ap11);
        f200.setOpenForBooking(true);
        
        airline.addFlight(f200);
        Date now = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", now);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", f200.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, f200.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
        // Setup
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
        
        Flight f201 = new Flight();
        f201.setId("F201");
        f201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrialAirport(ap13);
        f201.setOpenForBooking(true);
        
        // Create reservations
        Reservation r201_1 = new Reservation();
        r201_1.setId("R201-1");
        r201_1.setStatus(ReservationStatus.CONFIRMED);
        Passenger p1 = new Passenger();
        p1.setName("Passenger1");
        r201_1.setPassenger(p1);
        r201_1.setFlight(f201);
        
        Reservation r201_2 = new Reservation();
        r201_2.setId("R201-2");
        r201_2.setStatus(ReservationStatus.CONFIRMED);
        Passenger p2 = new Passenger();
        p2.setName("Passenger2");
        r201_2.setPassenger(p2);
        r201_2.setFlight(f201);
        
        Reservation r201_3 = new Reservation();
        r201_3.setId("R201-3");
        r201_3.setStatus(ReservationStatus.CONFIRMED);
        Passenger p3 = new Passenger();
        p3.setName("Passenger3");
        r201_3.setPassenger(p3);
        r201_3.setFlight(f201);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r201_1);
        reservations.add(r201_2);
        reservations.add(r201_3);
        f201.setReservations(reservations);
        
        airline.addFlight(f201);
        Date now = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", now);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed after operation", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        for (Reservation r : f201.getReservations()) {
            assertEquals("All reservations should be canceled", ReservationStatus.CANCELED, r.getStatus());
        }
        assertEquals("Three reservations should exist", 3, f201.getReservations().size());
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
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
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        f203.setOpenForBooking(true);
        
        // Create confirmed reservations
        Reservation r203_1 = new Reservation();
        r203_1.setId("R203-1");
        r203_1.setStatus(ReservationStatus.CONFIRMED);
        Reservation r203_2 = new Reservation();
        r203_2.setId("R203-2");
        r203_2.setStatus(ReservationStatus.CONFIRMED);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r203_1);
        reservations.add(r203_2);
        f203.setReservations(reservations);
        
        airline.addFlight(f203);
        Date now = dateFormat.parse("2025-09-10 09:10:00");
        
        // Execute
        boolean result = airline.closeFlight("F203", now);
        
        // Verify
        assertFalse("Should return false when trying to close after departure time", result);
        assertTrue("Flight should remain open since close failed", f203.isOpenForBooking());
        
        // Reservations should remain confirmed
        for (Reservation r : f203.getReservations()) {
            assertEquals("Reservations should remain confirmed", ReservationStatus.CONFIRMED, r.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-01 01:30:00")); // Note: next day arrival
        f204.setOpenForBooking(true);
        
        airline.addFlight(f204);
        Date now = dateFormat.parse("2025-04-01 22:05:00");
        
        // Execute
        boolean result = airline.closeFlight("F204", now);
        
        // Verify
        assertFalse("Should return false when trying to close after departure", result);
        assertTrue("Flight should remain open since close failed", f204.isOpenForBooking());
    }
}