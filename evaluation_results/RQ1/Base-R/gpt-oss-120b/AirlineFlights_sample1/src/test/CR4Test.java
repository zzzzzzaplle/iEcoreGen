import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CR4Test {
    
    private AirlineReservationSystem.AirlineService service;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        service = new AirlineReservationSystem.AirlineService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_noReservationsToCancel() {
        // Setup: Create cities and airports for AL6
        AirlineReservationSystem.City cityJ = new AirlineReservationSystem.City();
        cityJ.setId("CJ");
        cityJ.setName("CityJ");
        
        AirlineReservationSystem.City cityK = new AirlineReservationSystem.City();
        cityK.setId("CK");
        cityK.setName("CityK");
        
        AirlineReservationSystem.Airport ap10 = new AirlineReservationSystem.Airport();
        ap10.setId("AP10");
        ap10.setName("AP10");
        ap10.setCity(cityJ);
        
        AirlineReservationSystem.Airport ap11 = new AirlineReservationSystem.Airport();
        ap11.setId("AP11");
        ap11.setName("AP11");
        ap11.setCity(cityK);
        
        // Create flight F200
        AirlineReservationSystem.Flight flight = new AirlineReservationSystem.Flight();
        flight.setId("F200");
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);
        flight.setDepartureTime(LocalDateTime.parse("2025-06-20 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-06-20 13:00:00", formatter));
        flight.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        // Manually add flight to service (since publishFlight would assign a different ID)
        service.getAllFlights().clear();
        service.getAllFlights().add(flight);
        
        // Mock current time = 2025-05-01 08:00:00
        // Test closeFlight
        boolean result = service.closeFlight("F200");
        
        // Verify results
        assertTrue("Flight should be closed successfully", result);
        assertEquals("Flight status should be CLOSED", 
                     AirlineReservationSystem.FlightStatus.CLOSED, 
                     flight.getStatus());
        assertEquals("No reservations should be canceled", 
                     0, 
                     service.getAllReservations().stream()
                         .filter(r -> r.getStatus() == AirlineReservationSystem.ReservationStatus.CANCELED)
                         .count());
    }
    
    @Test
    public void testCase2_threeConfirmedReservationsCanceled() {
        // Setup: Create cities and airports for AL7
        AirlineReservationSystem.City cityL = new AirlineReservationSystem.City();
        cityL.setId("CL");
        cityL.setName("CityL");
        
        AirlineReservationSystem.City cityM = new AirlineReservationSystem.City();
        cityM.setId("CM");
        cityM.setName("CityM");
        
        AirlineReservationSystem.Airport ap12 = new AirlineReservationSystem.Airport();
        ap12.setId("AP12");
        ap12.setName("AP12");
        ap12.setCity(cityL);
        
        AirlineReservationSystem.Airport ap13 = new AirlineReservationSystem.Airport();
        ap13.setId("AP13");
        ap13.setName("AP13");
        ap13.setCity(cityM);
        
        // Create flight F201
        AirlineReservationSystem.Flight flight = new AirlineReservationSystem.Flight();
        flight.setId("F201");
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);
        flight.setDepartureTime(LocalDateTime.parse("2025-07-15 14:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-07-15 18:00:00", formatter));
        flight.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        // Create customer
        AirlineReservationSystem.Customer customer = new AirlineReservationSystem.Customer();
        customer.setId("CUST7");
        customer.setName("Customer AL7");
        
        // Manually create and add confirmed reservations
        AirlineReservationSystem.Reservation res1 = new AirlineReservationSystem.Reservation();
        res1.setId("R201-1");
        res1.setPassengerName("Passenger1");
        res1.setFlight(flight);
        res1.setStatus(AirlineReservationSystem.ReservationStatus.CONFIRMED);
        
        AirlineReservationSystem.Reservation res2 = new AirlineReservationSystem.Reservation();
        res2.setId("R201-2");
        res2.setPassengerName("Passenger2");
        res2.setFlight(flight);
        res2.setStatus(AirlineReservationSystem.ReservationStatus.CONFIRMED);
        
        AirlineReservationSystem.Reservation res3 = new AirlineReservationSystem.Reservation();
        res3.setId("R201-3");
        res3.setPassengerName("Passenger3");
        res3.setFlight(flight);
        res3.setStatus(AirlineReservationSystem.ReservationStatus.CONFIRMED);
        
        // Manually add flight and reservations to service
        service.getAllFlights().clear();
        service.getAllFlights().add(flight);
        service.getAllReservations().clear();
        service.getAllReservations().add(res1);
        service.getAllReservations().add(res2);
        service.getAllReservations().add(res3);
        
        // Mock current time = 2025-06-10 12:00:00
        // Test closeFlight
        boolean result = service.closeFlight("F201");
        
        // Verify results
        assertTrue("Flight should be closed successfully", result);
        assertEquals("Flight status should be CLOSED", 
                     AirlineReservationSystem.FlightStatus.CLOSED, 
                     flight.getStatus());
        assertEquals("All three reservations should be canceled", 
                     3, 
                     service.getAllReservations().stream()
                         .filter(r -> r.getStatus() == AirlineReservationSystem.ReservationStatus.CANCELED)
                         .count());
    }
    
    @Test
    public void testCase3_flightAlreadyClosed() {
        // Setup: Create flight F202 that is already closed
        AirlineReservationSystem.Flight flight = new AirlineReservationSystem.Flight();
        flight.setId("F202");
        flight.setDepartureTime(LocalDateTime.parse("2025-08-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-08-10 13:30:00", formatter));
        flight.setStatus(AirlineReservationSystem.FlightStatus.CLOSED);
        
        // Manually add flight to service
        service.getAllFlights().clear();
        service.getAllFlights().add(flight);
        
        // Mock current time = 2025-07-01 09:00:00
        // Test closeFlight
        boolean result = service.closeFlight("F202");
        
        // Verify results
        assertFalse("Closing already closed flight should return false", result);
        assertEquals("Flight status should remain CLOSED", 
                     AirlineReservationSystem.FlightStatus.CLOSED, 
                     flight.getStatus());
    }
    
    @Test
    public void testCase4_closeOnDepartureDayAfterDepartureTime() {
        // Setup: Create flight F203 with confirmed reservations
        AirlineReservationSystem.Flight flight = new AirlineReservationSystem.Flight();
        flight.setId("F203");
        flight.setDepartureTime(LocalDateTime.parse("2025-09-10 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-09-10 15:30:00", formatter));
        flight.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        // Create confirmed reservations
        AirlineReservationSystem.Reservation res1 = new AirlineReservationSystem.Reservation();
        res1.setId("R203-1");
        res1.setPassengerName("Passenger1");
        res1.setFlight(flight);
        res1.setStatus(AirlineReservationSystem.ReservationStatus.CONFIRMED);
        
        AirlineReservationSystem.Reservation res2 = new AirlineReservationSystem.Reservation();
        res2.setId("R203-2");
        res2.setPassengerName("Passenger2");
        res2.setFlight(flight);
        res2.setStatus(AirlineReservationSystem.ReservationStatus.CONFIRMED);
        
        // Manually add flight and reservations to service
        service.getAllFlights().clear();
        service.getAllFlights().add(flight);
        service.getAllReservations().clear();
        service.getAllReservations().add(res1);
        service.getAllReservations().add(res2);
        
        // Mock current time = 2025-09-10 09:10:00 (after departure)
        // Test closeFlight
        boolean result = service.closeFlight("F203");
        
        // Verify results
        assertFalse("Closing flight after departure should return false", result);
        assertEquals("Flight status should remain OPEN", 
                     AirlineReservationSystem.FlightStatus.OPEN, 
                     flight.getStatus());
        assertEquals("No reservations should be canceled", 
                     0, 
                     service.getAllReservations().stream()
                         .filter(r -> r.getStatus() == AirlineReservationSystem.ReservationStatus.CANCELED)
                         .count());
    }
    
    @Test
    public void testCase5_attemptToCloseAfterDeparture() {
        // Setup: Create flight F204 that has already departed
        AirlineReservationSystem.Flight flight = new AirlineReservationSystem.Flight();
        flight.setId("F204");
        flight.setDepartureTime(LocalDateTime.parse("2025-04-01 22:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-04-01 01:30:00", formatter)); // Next day arrival
        flight.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        // Manually add flight to service
        service.getAllFlights().clear();
        service.getAllFlights().add(flight);
        
        // Mock current time = 2025-04-01 22:05:00 (after departure)
        // Test closeFlight
        boolean result = service.closeFlight("F204");
        
        // Verify results
        assertFalse("Closing flight after departure should return false", result);
        assertEquals("Flight status should remain OPEN", 
                     AirlineReservationSystem.FlightStatus.OPEN, 
                     flight.getStatus());
    }
}