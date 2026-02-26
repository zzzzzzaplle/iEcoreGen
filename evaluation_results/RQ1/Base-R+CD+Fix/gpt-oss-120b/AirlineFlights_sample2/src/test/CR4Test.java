import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    
    private Airline airline;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        airline = new Airline();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_NoReservationsToCancel() {
        // Setup: Airline AL6; airports AP10 (CityJ) and AP11 (CityK)
        Airport ap10 = new Airport("AP10");
        City cityJ = new City("CityJ");
        ap10.addCity(cityJ);
        
        Airport ap11 = new Airport("AP11");
        City cityK = new City("CityK");
        ap11.addCity(cityK);
        
        // Flight F200: depart 2025-06-20 09:00:00, arrive 2025-06-20 13:00:00, 0 reservations
        Flight flight = new Flight("F200");
        flight.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Current time = 2025-05-01 08:00:00
        LocalDateTime now = LocalDateTime.parse("2025-05-01 08:00:00", formatter);
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F200", now);
        
        // Verify results
        assertTrue("Close flight should return true when successful", result);
        assertFalse("Flight should be closed after successful operation", flight.isOpenForBooking());
        assertEquals("No reservations should exist", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_ThreeConfirmedReservationsCanceled() {
        // Setup: Airline AL7; airports AP12 (CityL) and AP13 (CityM)
        Airport ap12 = new Airport("AP12");
        City cityL = new City("CityL");
        ap12.addCity(cityL);
        
        Airport ap13 = new Airport("AP13");
        City cityM = new City("CityM");
        ap13.addCity(cityM);
        
        // Flight F201: depart 2025-07-15 14:00:00, arrive 2025-07-15 18:00:00
        Flight flight = new Flight("F201");
        flight.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        flight.setOpenForBooking(true);
        
        // Create customer and booking with three confirmed reservations
        Customer customer = new Customer();
        List<String> passengerNames = Arrays.asList("Passenger1", "Passenger2", "Passenger3");
        
        // Create reservations manually to set them as confirmed
        LocalDateTime bookingTime = LocalDateTime.parse("2025-06-01 10:00:00", formatter);
        for (String passengerName : passengerNames) {
            Passenger passenger = new Passenger(passengerName);
            Reservation reservation = new Reservation("R201-" + passengerName, 
                    ReservationStatus.CONFIRMED, passenger, flight);
            flight.addReservation(reservation);
        }
        
        airline.addFlight(flight);
        
        // Current time = 2025-06-10 12:00:00
        LocalDateTime now = LocalDateTime.parse("2025-06-10 12:00:00", formatter);
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F201", now);
        
        // Verify results
        assertTrue("Close flight should return true when successful", result);
        assertFalse("Flight should be closed after successful operation", flight.isOpenForBooking());
        
        // Check that all reservations are canceled
        List<Reservation> reservations = flight.getReservations();
        assertEquals("Should have 3 reservations", 3, reservations.size());
        for (Reservation reservation : reservations) {
            assertEquals("All confirmed reservations should be canceled", 
                        ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_FlightAlreadyClosed() {
        // Setup: Airline AL8; flight F202 openForBooking = False
        Airport ap14 = new Airport("AP14");
        City cityN = new City("CityN");
        ap14.addCity(cityN);
        
        Airport ap15 = new Airport("AP15");
        City cityO = new City("CityO");
        ap15.addCity(cityO);
        
        Flight flight = new Flight("F202");
        flight.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        flight.setDepartureAirport(ap14);
        flight.setArrivalAirport(ap15);
        flight.setOpenForBooking(false); // Already closed
        
        airline.addFlight(flight);
        
        // Current time = 2025-07-01 09:00:00
        LocalDateTime now = LocalDateTime.parse("2025-07-01 09:00:00", formatter);
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F202", now);
        
        // Verify results
        assertFalse("Close flight should return false when flight is already closed", result);
        assertFalse("Flight should remain closed", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_CloseOnDepartureDayAfterDepartureTime() {
        // Setup: Airline AL9; flight F203 openForBooking = True
        Airport ap16 = new Airport("AP16");
        City cityP = new City("CityP");
        ap16.addCity(cityP);
        
        Airport ap17 = new Airport("AP17");
        City cityQ = new City("CityQ");
        ap17.addCity(cityQ);
        
        Flight flight = new Flight("F203");
        flight.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        flight.setDepartureAirport(ap16);
        flight.setArrivalAirport(ap17);
        flight.setOpenForBooking(true);
        
        // Add two confirmed reservations
        Passenger passenger1 = new Passenger("Passenger1");
        Passenger passenger2 = new Passenger("Passenger2");
        Reservation reservation1 = new Reservation("R203-1", ReservationStatus.CONFIRMED, passenger1, flight);
        Reservation reservation2 = new Reservation("R203-2", ReservationStatus.CONFIRMED, passenger2, flight);
        flight.addReservation(reservation1);
        flight.addReservation(reservation2);
        
        airline.addFlight(flight);
        
        // Current time = 2025-09-10 09:10:00 (after departure)
        LocalDateTime now = LocalDateTime.parse("2025-09-10 09:10:00", formatter);
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F203", now);
        
        // Verify results
        assertFalse("Close flight should return false when current time is after departure", result);
        assertTrue("Flight should remain open when close fails", flight.isOpenForBooking());
        
        // Verify reservations remain confirmed (not canceled)
        List<Reservation> reservations = flight.getReservations();
        for (Reservation reservation : reservations) {
            assertEquals("Reservations should remain confirmed when close fails", 
                        ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_AttemptToCloseAfterDeparture() {
        // Setup: Airline AL10; flight F204 openForBooking = True
        Airport ap18 = new Airport("AP18");
        City cityR = new City("CityR");
        ap18.addCity(cityR);
        
        Airport ap19 = new Airport("AP19");
        City cityS = new City("CityS");
        ap19.addCity(cityS);
        
        Flight flight = new Flight("F204");
        flight.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-02 01:30:00", formatter)); // Fixed date for arrival (next day)
        flight.setDepartureAirport(ap18);
        flight.setArrivalAirport(ap19);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        // Current time = 2025-04-01 22:05:00 (flight already left)
        LocalDateTime now = LocalDateTime.parse("2025-04-01 22:05:00", formatter);
        
        // Execute closeFlight
        boolean result = airline.closeFlight("F204", now);
        
        // Verify results
        assertFalse("Close flight should return false when current time is after departure", result);
        assertTrue("Flight should remain open when close fails", flight.isOpenForBooking());
    }
}