import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CR3Test {
    
    private Flight flight;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_confirmPendingReservation() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP160");
        List<String> citiesA = new ArrayList<>();
        citiesA.add("CityAA");
        departureAirport.setCities(citiesA);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP161");
        List<String> citiesB = new ArrayList<>();
        citiesB.add("CityAB");
        arrivalAirport.setCities(citiesB);
        
        flight = new Flight();
        flight.setId("F401");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-10 11:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-10 15:00:00", formatter));
        flight.setOpen(true);
        
        // Create reservation R401 with status PENDING
        Reservation reservation = new Reservation();
        reservation.setId("R401");
        reservation.setPassengerName("P9");
        reservation.setFlight(flight);
        reservation.setConfirmed(false);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        flight.setReservations(reservations);
        
        // Set current time to 2025-11-01 09:00:00
        // Mock current time by ensuring flight departure is in the future
        // The flight's departure time is 2025-12-10 which is after current time
        
        // Execute: Confirm reservation R401
        boolean result = flight.updateReservation("R401", true);
        
        // Verify
        assertTrue("Reservation should be confirmed successfully", result);
        assertTrue("Reservation status should be confirmed", reservation.isConfirmed());
    }
    
    @Test
    public void testCase2_cancelConfirmedReservation() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP170");
        List<String> citiesA = new ArrayList<>();
        citiesA.add("CityAC");
        departureAirport.setCities(citiesA);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP171");
        List<String> citiesB = new ArrayList<>();
        citiesB.add("CityAD");
        arrivalAirport.setCities(citiesB);
        
        flight = new Flight();
        flight.setId("F402");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-12-15 15:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-12-15 18:00:00", formatter));
        flight.setOpen(true);
        
        // Create reservation R402 with status CONFIRMED
        Reservation reservation = new Reservation();
        reservation.setId("R402");
        reservation.setPassengerName("P10");
        reservation.setFlight(flight);
        reservation.setConfirmed(true);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        flight.setReservations(reservations);
        
        // Set current time to 2025-12-01 12:00:00
        // The flight's departure time is in the future
        
        // Execute: Cancel reservation R402
        boolean result = flight.updateReservation("R402", false);
        
        // Verify
        assertTrue("Reservation should be cancelled successfully", result);
        assertFalse("Reservation status should be cancelled", reservation.isConfirmed());
    }
    
    @Test
    public void testCase3_flightDepartedBlocksConfirmation() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP180");
        List<String> citiesA = new ArrayList<>();
        citiesA.add("CityAE");
        departureAirport.setCities(citiesA);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP181");
        List<String> citiesB = new ArrayList<>();
        citiesB.add("CityAF");
        arrivalAirport.setCities(citiesB);
        
        flight = new Flight();
        flight.setId("F403");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-01-05 06:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-01-05 08:00:00", formatter));
        flight.setOpen(true);
        
        // Create reservation R403 with status PENDING
        Reservation reservation = new Reservation();
        reservation.setId("R403");
        reservation.setPassengerName("P11");
        reservation.setFlight(flight);
        reservation.setConfirmed(false);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        flight.setReservations(reservations);
        
        // Set current time to 2025-01-05 07:00:00 (flight already departed)
        // Override the flight's departure time check by setting it in the past
        flight.setDepartureTime(LocalDateTime.parse("2025-01-05 06:00:00", formatter));
        
        // Execute: Confirm reservation R403
        boolean result = flight.updateReservation("R403", true);
        
        // Verify
        assertFalse("Reservation confirmation should fail due to departed flight", result);
        assertFalse("Reservation status should remain pending", reservation.isConfirmed());
    }
    
    @Test
    public void testCase4_closedFlightBlocksCancellation() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP190");
        List<String> citiesA = new ArrayList<>();
        citiesA.add("CityAG");
        departureAirport.setCities(citiesA);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP191");
        List<String> citiesB = new ArrayList<>();
        citiesB.add("CityAH");
        arrivalAirport.setCities(citiesB);
        
        flight = new Flight();
        flight.setId("F404");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-02-01 09:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-02-01 12:00:00", formatter));
        flight.setOpen(false); // Flight is closed for booking
        
        // Create reservation R404 with status CONFIRMED
        Reservation reservation = new Reservation();
        reservation.setId("R404");
        reservation.setPassengerName("P12");
        reservation.setFlight(flight);
        reservation.setConfirmed(true);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        flight.setReservations(reservations);
        
        // Set current time to 2025-01-20 08:00:00
        // The flight's departure time is in the future but flight is closed
        
        // Execute: Cancel reservation R404
        boolean result = flight.updateReservation("R404", false);
        
        // Verify
        assertFalse("Reservation cancellation should fail due to closed flight", result);
        assertTrue("Reservation status should remain confirmed", reservation.isConfirmed());
    }
    
    @Test
    public void testCase5_unknownReservationIdentifier() {
        // Setup
        departureAirport = new Airport();
        departureAirport.setId("AP200");
        List<String> citiesA = new ArrayList<>();
        citiesA.add("CityAI");
        departureAirport.setCities(citiesA);
        
        arrivalAirport = new Airport();
        arrivalAirport.setId("AP201");
        List<String> citiesB = new ArrayList<>();
        citiesB.add("CityAJ");
        arrivalAirport.setCities(citiesB);
        
        flight = new Flight();
        flight.setId("F405");
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(LocalDateTime.parse("2025-03-10 10:00:00", formatter));
        flight.setArrivalTime(LocalDateTime.parse("2025-03-10 14:00:00", formatter));
        flight.setOpen(true);
        
        // Create reservation R405 for customer CU20 (P13)
        Reservation reservation1 = new Reservation();
        reservation1.setId("R405");
        reservation1.setPassengerName("P13");
        reservation1.setFlight(flight);
        reservation1.setConfirmed(false);
        
        // Create reservation R406 for customer CU21 (P14)
        Reservation reservation2 = new Reservation();
        reservation2.setId("R406");
        reservation2.setPassengerName("P14");
        reservation2.setFlight(flight);
        reservation2.setConfirmed(false);
        
        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation1);
        reservations.add(reservation2);
        flight.setReservations(reservations);
        
        // Set current time to 2025-02-15 09:00:00
        // The flight's departure time is in the future
        
        // Execute: Customer CU20 tries to confirm reservation R406 (which belongs to CU21)
        boolean result = flight.updateReservation("R999", true); // Unknown reservation ID
        
        // Verify
        assertFalse("Reservation confirmation should fail for unknown reservation ID", result);
    }
}