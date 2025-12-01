import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR4Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
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
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        flight.setOpenForBooking(true);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-05-01 08:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F200", currentTime);
        
        // Verify
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed after successful close operation", flight.isOpenForBooking());
        assertEquals("No reservations should be canceled", 0, flight.getReservations().size());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create airports
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
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        flight.setOpenForBooking(true);
        
        // Create customer and booking with 3 confirmed reservations
        Customer customer = new Customer();
        
        // Create reservations manually to simulate confirmed status
        Reservation res1 = new Reservation();
        res1.setId("R201-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        Passenger passenger1 = new Passenger();
        passenger1.setName("Passenger1");
        res1.setPassenger(passenger1);
        res1.setFlight(flight);
        
        Reservation res2 = new Reservation();
        res2.setId("R201-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        Passenger passenger2 = new Passenger();
        passenger2.setName("Passenger2");
        res2.setPassenger(passenger2);
        res2.setFlight(flight);
        
        Reservation res3 = new Reservation();
        res3.setId("R201-3");
        res3.setStatus(ReservationStatus.CONFIRMED);
        Passenger passenger3 = new Passenger();
        passenger3.setName("Passenger3");
        res3.setPassenger(passenger3);
        res3.setFlight(flight);
        
        flight.getReservations().add(res1);
        flight.getReservations().add(res2);
        flight.getReservations().add(res3);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-06-10 12:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F201", currentTime);
        
        // Verify
        assertTrue("Close flight should return true", result);
        assertFalse("Flight should be closed after successful close operation", flight.isOpenForBooking());
        
        // Check that all three reservations are canceled
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("All confirmed reservations should be canceled", 
                        ReservationStatus.CANCELED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create flight that is already closed
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-08-10 13:30:00"));
        flight.setOpenForBooking(false);
        
        // Create minimal airports to satisfy flight requirements
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-07-01 09:00:00");
        
        // Execute
        boolean result = airline.closeFlight("F202", currentTime);
        
        // Verify
        assertFalse("Close flight should return false for already closed flight", result);
        assertFalse("Flight should remain closed", flight.isOpenForBooking());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-09-10 15:30:00"));
        flight.setOpenForBooking(true);
        
        // Create minimal airports to satisfy flight requirements
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        
        // Add two confirmed reservations
        Reservation res1 = new Reservation();
        res1.setId("R203-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        Passenger passenger1 = new Passenger();
        passenger1.setName("Passenger1");
        res1.setPassenger(passenger1);
        res1.setFlight(flight);
        
        Reservation res2 = new Reservation();
        res2.setId("R203-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        Passenger passenger2 = new Passenger();
        passenger2.setName("Passenger2");
        res2.setPassenger(passenger2);
        res2.setFlight(flight);
        
        flight.getReservations().add(res1);
        flight.getReservations().add(res2);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-09-10 09:10:00"); // After departure time
        
        // Execute
        boolean result = airline.closeFlight("F203", currentTime);
        
        // Verify
        assertFalse("Close flight should return false when current time is after departure", result);
        assertTrue("Flight should remain open since close operation failed", flight.isOpenForBooking());
        
        // Reservations should remain confirmed since close operation failed
        for (Reservation reservation : flight.getReservations()) {
            assertEquals("Reservations should remain CONFIRMED when close operation fails", 
                        ReservationStatus.CONFIRMED, reservation.getStatus());
        }
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws Exception {
        // Setup
        Airline airline = new Airline();
        
        // Create flight
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setDepartureTime(dateFormat.parse("2025-04-01 22:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-04-01 01:30:00")); // Same day arrival
        flight.setOpenForBooking(true);
        
        // Create minimal airports to satisfy flight requirements
        Airport departureAirport = new Airport();
        departureAirport.setId("DEP");
        Airport arrivalAirport = new Airport();
        arrivalAirport.setId("ARR");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        
        airline.addFlight(flight);
        
        Date currentTime = dateFormat.parse("2025-04-01 22:05:00"); // After departure
        
        // Execute
        boolean result = airline.closeFlight("F204", currentTime);
        
        // Verify
        assertFalse("Close flight should return false when flight has already departed", result);
        assertTrue("Flight should remain open since close operation failed", flight.isOpenForBooking());
    }
}