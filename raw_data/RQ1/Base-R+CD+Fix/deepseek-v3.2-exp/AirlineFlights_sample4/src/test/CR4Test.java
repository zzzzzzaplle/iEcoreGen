import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR4Test {
    private Airline airline;
    private SimpleDateFormat sdf;
    
    @Before
    public void setUp() {
        airline = new Airline();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() throws Exception {
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
        f200.setDepartureTime(sdf.parse("2025-06-20 09:00:00"));
        f200.setArrivalTime(sdf.parse("2025-06-20 13:00:00"));
        f200.setDepartureAirport(ap10);
        f200.setArrivalAirport(ap11);
        f200.setOpenForBooking(true);
        
        airline.addFlight(f200);
        
        Date currentTime = sdf.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should no longer be open for booking", f200.isOpenForBooking());
        assertEquals("No reservations should exist", 0, f200.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
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
        f201.setDepartureTime(sdf.parse("2025-07-15 14:00:00"));
        f201.setArrivalTime(sdf.parse("2025-07-15 18:00:00"));
        f201.setDepartureAirport(ap12);
        f201.setArrivalAirport(ap13);
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
        
        Date currentTime = sdf.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should no longer be open for booking", f201.isOpenForBooking());
        
        // Check that all reservations are canceled
        for (Reservation reservation : f201.getReservations()) {
            assertEquals("Reservation should be canceled", ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup
        Airport ap14 = new Airport();
        City cityN = new City();
        cityN.setName("CityN");
        ap14.addCity(cityN);
        
        Airport ap15 = new Airport();
        City cityO = new City();
        cityO.setName("CityO");
        ap15.addCity(cityO);
        
        Flight f202 = new Flight();
        f202.setId("F202");
        f202.setDepartureTime(sdf.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(sdf.parse("2025-08-10 13:30:00"));
        f202.setDepartureAirport(ap14);
        f202.setArrivalAirport(ap15);
        f202.setOpenForBooking(false); // Flight already closed
        
        airline.addFlight(f202);
        
        Date currentTime = sdf.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Flight should not be closed as it's already closed", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Airport ap16 = new Airport();
        City cityP = new City();
        cityP.setName("CityP");
        ap16.addCity(cityP);
        
        Airport ap17 = new Airport();
        City cityQ = new City();
        cityQ.setName("CityQ");
        ap17.addCity(cityQ);
        
        Flight f203 = new Flight();
        f203.setId("F203");
        f203.setDepartureTime(sdf.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(sdf.parse("2025-09-10 15:30:00"));
        f203.setDepartureAirport(ap16);
        f203.setArrivalAirport(ap17);
        f203.setOpenForBooking(true);
        
        // Create confirmed reservations
        Reservation r203_1 = new Reservation();
        r203_1.setId("R203-1");
        r203_1.setStatus(ReservationStatus.CONFIRMED);
        Passenger p1 = new Passenger();
        p1.setName("Passenger1");
        r203_1.setPassenger(p1);
        r203_1.setFlight(f203);
        
        Reservation r203_2 = new Reservation();
        r203_2.setId("R203-2");
        r203_2.setStatus(ReservationStatus.CONFIRMED);
        Passenger p2 = new Passenger();
        p2.setName("Passenger2");
        r203_2.setPassenger(p2);
        r203_2.setFlight(f203);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(r203_1);
        reservations.add(r203_2);
        f203.setReservations(reservations);
        
        airline.addFlight(f203);
        
        Date currentTime = sdf.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Flight should not be closed after departure time", result);
        assertTrue("Flight should remain open for booking", f203.isOpenForBooking());
        
        // Check that reservations remain confirmed (not canceled)
        for (Reservation reservation : f203.getReservations()) {
            assertEquals("Reservation should remain confirmed", ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        Airport ap18 = new Airport();
        City cityR = new City();
        cityR.setName("CityR");
        ap18.addCity(cityR);
        
        Airport ap19 = new Airport();
        City cityS = new City();
        cityS.setName("CityS");
        ap19.addCity(cityS);
        
        Flight f204 = new Flight();
        f204.setId("F204");
        f204.setDepartureTime(sdf.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(sdf.parse("2025-04-01 01:30:00"));
        f204.setDepartureAirport(ap18);
        f204.setArrivalAirport(ap19);
        f204.setOpenForBooking(true);
        
        airline.addFlight(f204);
        
        Date currentTime = sdf.parse("2025-04-01 22:05:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Flight should not be closed after departure", result);
        assertTrue("Flight should remain open for booking", f204.isOpenForBooking());
    }
}