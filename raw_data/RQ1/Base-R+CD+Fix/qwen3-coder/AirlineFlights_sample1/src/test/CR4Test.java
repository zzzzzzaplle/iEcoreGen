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
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
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
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed", f200.isOpenForBooking());
        assertEquals("No reservations should exist", 0, f200.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
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
        
        // Create customer and reservations
        Customer customer = new Customer();
        Passenger passenger1 = new Passenger();
        passenger1.setName("Passenger1");
        Passenger passenger2 = new Passenger();
        passenger2.setName("Passenger2");
        Passenger passenger3 = new Passenger();
        passenger3.setName("Passenger3");
        
        // Create reservations manually since we need to set status to CONFIRMED
        Reservation r201_1 = new Reservation();
        r201_1.setId("R201-1");
        r201_1.setStatus(ReservationStatus.CONFIRMED);
        r201_1.setPassenger(passenger1);
        r201_1.setFlight(f201);
        
        Reservation r201_2 = new Reservation();
        r201_2.setId("R201-2");
        r201_2.setStatus(ReservationStatus.CONFIRMED);
        r201_2.setPassenger(passenger2);
        r201_2.setFlight(f201);
        
        Reservation r201_3 = new Reservation();
        r201_3.setId("R201-3");
        r201_3.setStatus(ReservationStatus.CONFIRMED);
        r201_3.setPassenger(passenger3);
        r201_3.setFlight(f201);
        
        f201.getReservations().add(r201_1);
        f201.getReservations().add(r201_2);
        f201.getReservations().add(r201_3);
        
        airline.addFlight(f201);
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Flight should be successfully closed", result);
        assertFalse("Flight should be closed", f201.isOpenForBooking());
        
        // Check that all three reservations are canceled
        assertEquals("Should have 3 reservations", 3, f201.getReservations().size());
        for (Reservation res : f201.getReservations()) {
            assertEquals("Reservation should be canceled", ReservationStatus.CANCELED, res.getStatus());
        }
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
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
        f202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        f202.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        f202.setDepartureAirport(ap14);
        f202.setArrivalAirport(ap15);
        f202.setOpenForBooking(false); // Flight already closed
        
        airline.addFlight(f202);
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Should return false for already closed flight", result);
        assertFalse("Flight should remain closed", f202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00");
        
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
        f203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        f203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        f203.setDepartureAirport(ap16);
        f203.setArrivalAirport(ap17);
        f203.setOpenForBooking(true);
        
        // Create confirmed reservations
        Passenger passenger1 = new Passenger();
        passenger1.setName("Passenger1");
        Passenger passenger2 = new Passenger();
        passenger2.setName("Passenger2");
        
        Reservation r203_1 = new Reservation();
        r203_1.setId("R203-1");
        r203_1.setStatus(ReservationStatus.CONFIRMED);
        r203_1.setPassenger(passenger1);
        r203_1.setFlight(f203);
        
        Reservation r203_2 = new Reservation();
        r203_2.setId("R203-2");
        r203_2.setStatus(ReservationStatus.CONFIRMED);
        r203_2.setPassenger(passenger2);
        r203_2.setFlight(f203);
        
        f203.getReservations().add(r203_1);
        f203.getReservations().add(r203_2);
        
        airline.addFlight(f203);
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Should return false when closing after departure time", result);
        assertTrue("Flight should remain open for booking", f203.isOpenForBooking());
        
        // Reservations should remain confirmed
        for (Reservation res : f203.getReservations()) {
            assertEquals("Reservations should remain confirmed", ReservationStatus.CONFIRMED, res.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00");
        
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
        f204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        f204.setArrivalTime(dateFormat.parse("2025-04-02 01:30:00")); // Fixed date for arrival
        f204.setDepartureAirport(ap18);
        f204.setArrivalAirport(ap19);
        f204.setOpenForBooking(true);
        
        airline.addFlight(f204);
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Should return false when closing after departure", result);
        assertTrue("Flight should remain open for booking", f204.isOpenForBooking());
    }
}