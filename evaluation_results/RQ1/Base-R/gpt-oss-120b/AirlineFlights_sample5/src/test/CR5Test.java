import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class CR5Test {
    
    private AirlineSystem system;
    private Flight flightF501;
    private Flight flightF502;
    private Flight flightF503;
    private Flight flightF504;
    private Flight flightF505;
    
    @Before
    public void setUp() {
        system = new AirlineSystem();
        
        // Create airports
        Airport airport1 = new Airport("AP1", "Airport 1");
        Airport airport2 = new Airport("AP2", "Airport 2");
        system.addAirport(airport1);
        system.addAirport(airport2);
        
        // Create customers
        Customer customer1 = new Customer("CUST1", "Customer 1");
        Customer customer2 = new Customer("CUST2", "Customer 2");
        Customer customer3 = new Customer("CUST3", "Customer 3");
        Customer customer4 = new Customer("CUST4", "Customer 4");
        Customer customer5 = new Customer("CUST5", "Customer 5");
        system.addCustomer(customer1);
        system.addCustomer(customer2);
        system.addCustomer(customer3);
        system.addCustomer(customer4);
        system.addCustomer(customer5);
        
        // Create flights with future departure times
        LocalDateTime futureDeparture = LocalDateTime.now().plusDays(1);
        LocalDateTime futureArrival = futureDeparture.plusHours(2);
        
        flightF501 = new Flight(airport1, airport2, futureDeparture, futureArrival);
        flightF502 = new Flight(airport1, airport2, futureDeparture.plusDays(1), futureArrival.plusDays(1));
        flightF503 = new Flight(airport1, airport2, futureDeparture.plusDays(2), futureArrival.plusDays(2));
        flightF504 = new Flight(airport1, airport2, futureDeparture.plusDays(3), futureArrival.plusDays(3));
        flightF505 = new Flight(airport1, airport2, futureDeparture.plusDays(4), futureArrival.plusDays(4));
        
        flightF501.setId("F501");
        flightF502.setId("F502");
        flightF503.setId("F503");
        flightF504.setId("F504");
        flightF505.setId("F505");
        
        system.addFlight(flightF501);
        system.addFlight(flightF502);
        system.addFlight(flightF503);
        system.addFlight(flightF504);
        system.addFlight(flightF505);
    }
    
    @Test
    public void testCase1_FlightWithThreeConfirmations() {
        // Setup: Airline AL21; flight F501 openForBooking = True
        assertTrue("Flight F501 should be published successfully", system.publishFlight("F501"));
        
        // Setup: Reservations R501-1, R501-2, R501-3 status = CONFIRMED
        assertTrue("Booking for F501 should be created successfully", 
                   system.createBooking("F501", "CUST1", Arrays.asList("Passenger1")));
        assertTrue("Booking for F501 should be created successfully", 
                   system.createBooking("F501", "CUST2", Arrays.asList("Passenger2")));
        assertTrue("Booking for F501 should be created successfully", 
                   system.createBooking("F501", "CUST3", Arrays.asList("Passenger3")));
        
        // Confirm all reservations
        for (Reservation reservation : system.getReservations().values()) {
            if (reservation.getFlight().getId().equals("F501")) {
                assertTrue("Reservation should be confirmed", system.confirmReservation(reservation.getId()));
            }
        }
        
        // Test: Retrieve confirmed list for flight F501
        List<Reservation> result = system.getConfirmedReservations("F501");
        
        // Expected Output: R501-1, R501-2, R501-3
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        
        // Verify all reservations are confirmed and belong to flight F501
        for (Reservation reservation : result) {
            assertEquals("Reservation should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
            assertEquals("Reservation should belong to flight F501", "F501", reservation.getFlight().getId());
        }
    }
    
    @Test
    public void testCase2_NoConfirmedReservations() {
        // Setup: Airline AL22; flight F502 openForBooking = True
        assertTrue("Flight F502 should be published successfully", system.publishFlight("F502"));
        
        // Setup: Two reservations status = PENDING
        assertTrue("Booking for F502 should be created successfully", 
                   system.createBooking("F502", "CUST1", Arrays.asList("Passenger1")));
        assertTrue("Booking for F502 should be created successfully", 
                   system.createBooking("F502", "CUST2", Arrays.asList("Passenger2")));
        
        // Verify reservations are PENDING (default status)
        for (Reservation reservation : system.getReservations().values()) {
            if (reservation.getFlight().getId().equals("F502")) {
                assertEquals("Reservation should be PENDING", ReservationStatus.PENDING, reservation.getStatus());
            }
        }
        
        // Test: Retrieve confirmed list for flight F502
        List<Reservation> result = system.getConfirmedReservations("F502");
        
        // Expected Output: []
        assertTrue("Should return empty list when no confirmed reservations", result.isEmpty());
    }
    
    @Test
    public void testCase3_FlightClosedReturnsZero() {
        // Setup: Airline AL23; flight F503 openForBooking = False
        // First publish the flight
        assertTrue("Flight F503 should be published successfully", system.publishFlight("F503"));
        
        // Setup: One reservation status = CONFIRMED
        assertTrue("Booking for F503 should be created successfully", 
                   system.createBooking("F503", "CUST1", Arrays.asList("Passenger1")));
        
        // Confirm the reservation
        for (Reservation reservation : system.getReservations().values()) {
            if (reservation.getFlight().getId().equals("F503")) {
                assertTrue("Reservation should be confirmed", system.confirmReservation(reservation.getId()));
            }
        }
        
        // Close the flight (sets openForBooking = False)
        assertTrue("Flight F503 should be closed successfully", system.closeFlight("F503"));
        
        // Verify flight status is CLOSED
        assertEquals("Flight status should be CLOSED", FlightStatus.CLOSED, flightF503.getStatus());
        
        // Test: Retrieve confirmed list for flight F503
        List<Reservation> result = system.getConfirmedReservations("F503");
        
        // Expected Output: []
        assertTrue("Should return empty list when flight is closed", result.isEmpty());
    }
    
    @Test
    public void testCase4_UnknownFlightId() {
        // Setup: Airline AL24 holds flights F504, F505 only (already set up in @Before)
        
        // Test: Retrieve confirmed list for flight FX999 (unknown flight)
        List<Reservation> result = system.getConfirmedReservations("FX999");
        
        // Expected Output: []
        assertTrue("Should return empty list for unknown flight ID", result.isEmpty());
    }
    
    @Test
    public void testCase5_MixedReservationStatuses() {
        // Setup: Airline AL25; flight F504 openForBooking = True
        assertTrue("Flight F504 should be published successfully", system.publishFlight("F504"));
        
        // Setup: Reservations: R504-A, R504-B status = CONFIRMED; R504-C status = CANCELED; R504-D status = PENDING
        assertTrue("Booking for F504 should be created successfully", 
                   system.createBooking("F504", "CUST1", Arrays.asList("PassengerA")));
        assertTrue("Booking for F504 should be created successfully", 
                   system.createBooking("F504", "CUST2", Arrays.asList("PassengerB")));
        assertTrue("Booking for F504 should be created successfully", 
                   system.createBooking("F504", "CUST3", Arrays.asList("PassengerC")));
        assertTrue("Booking for F504 should be created successfully", 
                   system.createBooking("F504", "CUST4", Arrays.asList("PassengerD")));
        
        // Set specific statuses for each reservation
        int count = 0;
        for (Reservation reservation : system.getReservations().values()) {
            if (reservation.getFlight().getId().equals("F504")) {
                switch (count) {
                    case 0: // R504-A - CONFIRMED
                        assertTrue("Reservation should be confirmed", system.confirmReservation(reservation.getId()));
                        break;
                    case 1: // R504-B - CONFIRMED
                        assertTrue("Reservation should be confirmed", system.confirmReservation(reservation.getId()));
                        break;
                    case 2: // R504-C - CANCELED
                        assertTrue("Reservation should be canceled", system.cancelReservation(reservation.getId()));
                        break;
                    case 3: // R504-D - PENDING (default status, no action needed)
                        break;
                }
                count++;
            }
        }
        
        // Test: Retrieve confirmed list for flight F504
        List<Reservation> result = system.getConfirmedReservations("F504");
        
        // Expected Output: R504-A, R504-B
        assertEquals("Should return 2 confirmed reservations", 2, result.size());
        
        // Verify only CONFIRMED reservations are returned
        for (Reservation reservation : result) {
            assertEquals("Reservation should be CONFIRMED", ReservationStatus.CONFIRMED, reservation.getStatus());
            assertEquals("Reservation should belong to flight F504", "F504", reservation.getFlight().getId());
        }
    }
}