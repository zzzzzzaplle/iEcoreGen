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
        // 1. Airline AL6; airports AP10 (CityJ) and AP11 (CityK)
        Airport ap10 = new Airport();
        City cityJ = new City();
        cityJ.setName("CityJ");
        ap10.addCity(cityJ);
        
        Airport ap11 = new Airport();
        City cityK = new City();
        cityK.setName("CityK");
        ap11.addCity(cityK);
        
        // 2. Flight F200: depart 2025-06-20 09:00:00, arrive 2025-06-20 13:00:00, 0 reservations
        Flight flightF200 = new Flight();
        flightF200.setId("F200");
        flightF200.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flightF200.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flightF200.setDepartureAirport(ap10);
        flightF200.setArrivalAirport(ap11);
        flightF200.setOpenForBooking(true);
        
        airline.addFlight(flightF200);
        
        // 3. Current time = 2025-05-01 08:00:00
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed", flightF200.isOpenForBooking());
        assertEquals("No reservations should be cancelled", 0, flightF200.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
        // Setup
        // 1. Airline AL7; airports AP12 (CityL) and AP13 (CityM)
        Airport ap12 = new Airport();
        City cityL = new City();
        cityL.setName("CityL");
        ap12.addCity(cityL);
        
        Airport ap13 = new Airport();
        City cityM = new City();
        cityM.setName("CityM");
        ap13.addCity(cityM);
        
        // 2. Flight F201: depart 2025-07-15 14:00:00, arrive 2025-07-15 18:00:00
        Flight flightF201 = new Flight();
        flightF201.setId("F201");
        flightF201.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flightF201.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flightF201.setDepartureAirport(ap12);
        flightF201.setArrivalAirport(ap13);
        flightF201.setOpenForBooking(true);
        
        // 3. Customer make a booking with three reservations R201-1, R201-2, R201-3
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        
        // Create passengers and reservations manually to simulate confirmed status
        Passenger passenger1 = new Passenger();
        passenger1.setName("Passenger1");
        Passenger passenger2 = new Passenger();
        passenger2.setName("Passenger2");
        Passenger passenger3 = new Passenger();
        passenger3.setName("Passenger3");
        
        Reservation reservation1 = new Reservation();
        reservation1.setId("R201-1");
        reservation1.setStatus(ReservationStatus.CONFIRMED);
        reservation1.setPassenger(passenger1);
        reservation1.setFlight(flightF201);
        
        Reservation reservation2 = new Reservation();
        reservation2.setId("R201-2");
        reservation2.setStatus(ReservationStatus.CONFIRMED);
        reservation2.setPassenger(passenger2);
        reservation2.setFlight(flightF201);
        
        Reservation reservation3 = new Reservation();
        reservation3.setId("R201-3");
        reservation3.setStatus(ReservationStatus.CONFIRMED);
        reservation3.setPassenger(passenger3);
        reservation3.setFlight(flightF201);
        
        flightF201.addReservation(reservation1);
        flightF201.addReservation(reservation2);
        flightF201.addReservation(reservation3);
        
        airline.addFlight(flightF201);
        
        // 4. Current time = 2025-06-10 12:00:00
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed", flightF201.isOpenForBooking());
        
        // Check that all reservations are cancelled
        List<Reservation> confirmedReservations = flightF201.getConfirmedReservations();
        assertEquals("All confirmed reservations should be cancelled", 0, confirmedReservations.size());
        
        // Verify individual reservation status
        for (Reservation reservation : flightF201.getReservations()) {
            assertEquals("Reservation should be cancelled", ReservationStatus.CANCELLED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup
        // 1. Airline AL8; flight F202 openForBooking = False
        Flight flightF202 = new Flight();
        flightF202.setId("F202");
        flightF202.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        flightF202.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        flightF202.setOpenForBooking(false); // Already closed
        
        // Create dummy airports
        Airport departureAirport = new Airport();
        City city1 = new City();
        city1.setName("CityN");
        departureAirport.addCity(city1);
        
        Airport arrivalAirport = new Airport();
        City city2 = new City();
        city2.setName("CityO");
        arrivalAirport.addCity(city2);
        
        flightF202.setDepartureAirport(departureAirport);
        flightF202.setArrivalAirport(arrivalAirport);
        
        airline.addFlight(flightF202);
        
        // 2. Current time = 2025-07-01 09:00:00
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Close flight should return false for already closed flight", result);
        assertFalse("Flight should remain closed", flightF202.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        // 1. Airline AL9; flight F203 openForBooking = True
        Flight flightF203 = new Flight();
        flightF203.setId("F203");
        flightF203.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flightF203.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        flightF203.setOpenForBooking(true);
        
        // Create dummy airports
        Airport departureAirport = new Airport();
        City city1 = new City();
        city1.setName("CityP");
        departureAirport.addCity(city1);
        
        Airport arrivalAirport = new Airport();
        City city2 = new City();
        city2.setName("CityQ");
        arrivalAirport.addCity(city2);
        
        flightF203.setDepartureAirport(departureAirport);
        flightF203.setArrivalAirport(arrivalAirport);
        
        // 2. Two confirmed reservations R203-1, R203-2
        Passenger passenger1 = new Passenger();
        passenger1.setName("Passenger1");
        Passenger passenger2 = new Passenger();
        passenger2.setName("Passenger2");
        
        Reservation reservation1 = new Reservation();
        reservation1.setId("R203-1");
        reservation1.setStatus(ReservationStatus.CONFIRMED);
        reservation1.setPassenger(passenger1);
        reservation1.setFlight(flightF203);
        
        Reservation reservation2 = new Reservation();
        reservation2.setId("R203-2");
        reservation2.setStatus(ReservationStatus.CONFIRMED);
        reservation2.setPassenger(passenger2);
        reservation2.setFlight(flightF203);
        
        flightF203.addReservation(reservation1);
        flightF203.addReservation(reservation2);
        
        airline.addFlight(flightF203);
        
        // 3. Current time = 2025-09-10 09:10:00 (after departure time)
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00");
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Close flight should return false when current time is after departure", result);
        assertTrue("Flight should remain open", flightF203.isOpenForBooking());
        
        // Verify reservations are not cancelled
        assertEquals("Reservations should not be cancelled", 2, flightF203.getConfirmedReservations().size());
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        // 1. Airline AL10; flight F204 openForBooking = True
        Flight flightF204 = new Flight();
        flightF204.setId("F204");
        flightF204.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        flightF204.setArrivalTime(dateFormat.parse("2025-04-02 01:30:00")); // Fixed date to avoid same-day arrival
        flightF204.setOpenForBooking(true);
        
        // Create dummy airports
        Airport departureAirport = new Airport();
        City city1 = new City();
        city1.setName("CityR");
        departureAirport.addCity(city1);
        
        Airport arrivalAirport = new Airport();
        City city2 = new City();
        city2.setName("CityS");
        arrivalAirport.addCity(city2);
        
        flightF204.setDepartureAirport(departureAirport);
        flightF204.setArrivalAirport(arrivalAirport);
        
        airline.addFlight(flightF204);
        
        // 2. Current time = 2025-04-01 22:05:00 (flight already left)
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00");
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Close flight should return false when flight has already departed", result);
        assertTrue("Flight should remain open", flightF204.isOpenForBooking());
    }
}