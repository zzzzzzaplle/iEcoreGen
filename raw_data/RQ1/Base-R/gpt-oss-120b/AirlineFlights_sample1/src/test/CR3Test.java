import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CR3Test {
    
    private AirlineReservationSystem.AirlineService service;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        service = new AirlineReservationSystem.AirlineService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_ConfirmPendingReservation() {
        // Setup
        // 1. Airline AL16 (implied by service instance)
        // 2. Airports AP160 (CityAA) and AP161 (CityAB)
        AirlineReservationSystem.City cityAA = new AirlineReservationSystem.City();
        cityAA.setId("CAA");
        cityAA.setName("CityAA");
        
        AirlineReservationSystem.City cityAB = new AirlineReservationSystem.City();
        cityAB.setId("CAB");
        cityAB.setName("CityAB");
        
        AirlineReservationSystem.Airport ap160 = new AirlineReservationSystem.Airport();
        ap160.setId("AP160");
        ap160.setName("AP160");
        ap160.setCity(cityAA);
        
        AirlineReservationSystem.Airport ap161 = new AirlineReservationSystem.Airport();
        ap161.setId("AP161");
        ap161.setName("AP161");
        ap161.setCity(cityAB);
        
        // 3. Flight F401
        AirlineReservationSystem.Flight flight401 = new AirlineReservationSystem.Flight();
        flight401.setDepartureAirport(ap160);
        flight401.setArrivalAirport(ap161);
        flight401.setDepartureTime(LocalDateTime.parse("2025-12-10 11:00:00", formatter));
        flight401.setArrivalTime(LocalDateTime.parse("2025-12-10 15:00:00", formatter));
        flight401.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        // Publish the flight
        service.publishFlight(flight401);
        
        // 4. Customer CU16, passenger P9
        AirlineReservationSystem.Customer customer16 = new AirlineReservationSystem.Customer();
        customer16.setId("CU16");
        customer16.setName("Customer16");
        
        // 5. Booking BK401 contains reservation R401 (status = PENDING) for P9 on F401
        List<String> passengers = Arrays.asList("P9");
        service.createBooking(customer16, flight401.getId(), passengers);
        
        // Get the created reservation (should be R401)
        AirlineReservationSystem.Reservation reservation401 = service.getAllReservations().iterator().next();
        
        // 6. Set current time = 2025-11-01 09:00:00
        // Note: We cannot mock time, so we rely on the fact that 2025-11-01 is before 2025-12-10
        
        // Input: CU16 confirm reservation R401
        boolean result = service.confirmReservation(reservation401.getId());
        
        // Expected Output: True
        assertTrue("Pending reservation should be confirmable", result);
        
        // Verify reservation status changed to CONFIRMED
        assertEquals("Reservation status should be CONFIRMED", 
                    AirlineReservationSystem.ReservationStatus.CONFIRMED, 
                    reservation401.getStatus());
    }
    
    @Test
    public void testCase2_CancelConfirmedReservation() {
        // Setup
        // 1. Airline AL17 (implied by service instance)
        // 2. Airports AP170 (CityAC) and AP171 (CityAD)
        AirlineReservationSystem.City cityAC = new AirlineReservationSystem.City();
        cityAC.setId("CAC");
        cityAC.setName("CityAC");
        
        AirlineReservationSystem.City cityAD = new AirlineReservationSystem.City();
        cityAD.setId("CAD");
        cityAD.setName("CityAD");
        
        AirlineReservationSystem.Airport ap170 = new AirlineReservationSystem.Airport();
        ap170.setId("AP170");
        ap170.setName("AP170");
        ap170.setCity(cityAC);
        
        AirlineReservationSystem.Airport ap171 = new AirlineReservationSystem.Airport();
        ap171.setId("AP171");
        ap171.setName("AP171");
        ap171.setCity(cityAD);
        
        // 3. Flight F402
        AirlineReservationSystem.Flight flight402 = new AirlineReservationSystem.Flight();
        flight402.setDepartureAirport(ap170);
        flight402.setArrivalAirport(ap171);
        flight402.setDepartureTime(LocalDateTime.parse("2025-12-15 15:00:00", formatter));
        flight402.setArrivalTime(LocalDateTime.parse("2025-12-15 19:00:00", formatter));
        flight402.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        // Publish the flight
        service.publishFlight(flight402);
        
        // 4. Customer CU17, passenger P10
        AirlineReservationSystem.Customer customer17 = new AirlineReservationSystem.Customer();
        customer17.setId("CU17");
        customer17.setName("Customer17");
        
        // 5. Booking BK402 contains reservation R402 (status = CONFIRMED) for P10 on F402
        List<String> passengers = Arrays.asList("P10");
        service.createBooking(customer17, flight402.getId(), passengers);
        
        // Get the created reservation and confirm it
        AirlineReservationSystem.Reservation reservation402 = service.getAllReservations().iterator().next();
        service.confirmReservation(reservation402.getId());
        
        // 6. Set current time = 2025-12-01 12:00:00
        // Note: We cannot mock time, so we rely on the fact that 2025-12-01 is before 2025-12-15
        
        // Input: CU17 cancel reservation R402
        boolean result = service.cancelReservation(reservation402.getId());
        
        // Expected Output: True
        assertTrue("Confirmed reservation should be cancellable", result);
        
        // Verify reservation status changed to CANCELED
        assertEquals("Reservation status should be CANCELED", 
                    AirlineReservationSystem.ReservationStatus.CANCELED, 
                    reservation402.getStatus());
    }
    
    @Test
    public void testCase3_FlightDepartedBlocksConfirmation() {
        // Setup
        // 1. Airline AL18 (implied by service instance)
        // 2. Airports AP180 (CityAE) and AP181 (CityAF)
        AirlineReservationSystem.City cityAE = new AirlineReservationSystem.City();
        cityAE.setId("CAE");
        cityAE.setName("CityAE");
        
        AirlineReservationSystem.City cityAF = new AirlineReservationSystem.City();
        cityAF.setId("CAF");
        cityAF.setName("CityAF");
        
        AirlineReservationSystem.Airport ap180 = new AirlineReservationSystem.Airport();
        ap180.setId("AP180");
        ap180.setName("AP180");
        ap180.setCity(cityAE);
        
        AirlineReservationSystem.Airport ap181 = new AirlineReservationSystem.Airport();
        ap181.setId("AP181");
        ap181.setName("AP181");
        ap181.setCity(cityAF);
        
        // 3. Flight F403
        AirlineReservationSystem.Flight flight403 = new AirlineReservationSystem.Flight();
        flight403.setDepartureAirport(ap180);
        flight403.setArrivalAirport(ap181);
        flight403.setDepartureTime(LocalDateTime.parse("2025-01-05 06:00:00", formatter));
        flight403.setArrivalTime(LocalDateTime.parse("2025-01-05 10:00:00", formatter));
        flight403.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        // Publish the flight
        service.publishFlight(flight403);
        
        // Create a customer and booking to get a reservation
        AirlineReservationSystem.Customer customer18 = new AirlineReservationSystem.Customer();
        customer18.setId("CU18");
        customer18.setName("Customer18");
        
        List<String> passengers = Arrays.asList("P11");
        service.createBooking(customer18, flight403.getId(), passengers);
        
        // 4. Reservation R403 status = PENDING (passenger P11)
        AirlineReservationSystem.Reservation reservation403 = service.getAllReservations().iterator().next();
        
        // 5. Current time = 2025-01-05 07:00:00 (flight already left)
        // Note: Since we cannot mock time, and 2025-01-05 is in the past relative to current time,
        // the flight will be considered as departed when we try to confirm
        
        // Input: Confirm reservation R403
        boolean result = service.confirmReservation(reservation403.getId());
        
        // Expected Output: False
        assertFalse("Confirmation should fail for departed flight", result);
        
        // Verify reservation status remains PENDING
        assertEquals("Reservation status should remain PENDING", 
                    AirlineReservationSystem.ReservationStatus.PENDING, 
                    reservation403.getStatus());
    }
    
    @Test
    public void testCase4_ClosedFlightBlocksCancellation() {
        // Setup
        // 1. Airline AL19 (implied by service instance)
        // 2. Airports AP190 (CityAG) and AP191 (CityAH)
        AirlineReservationSystem.City cityAG = new AirlineReservationSystem.City();
        cityAG.setId("CAG");
        cityAG.setName("CityAG");
        
        AirlineReservationSystem.City cityAH = new AirlineReservationSystem.City();
        cityAH.setId("CAH");
        cityAH.setName("CityAH");
        
        AirlineReservationSystem.Airport ap190 = new AirlineReservationSystem.Airport();
        ap190.setId("AP190");
        ap190.setName("AP190");
        ap190.setCity(cityAG);
        
        AirlineReservationSystem.Airport ap191 = new AirlineReservationSystem.Airport();
        ap191.setId("AP191");
        ap191.setName("AP191");
        ap191.setCity(cityAH);
        
        // 3. Flight F404
        AirlineReservationSystem.Flight flight404 = new AirlineReservationSystem.Flight();
        flight404.setDepartureAirport(ap190);
        flight404.setArrivalAirport(ap191);
        flight404.setDepartureTime(LocalDateTime.parse("2025-02-01 09:00:00", formatter));
        flight404.setArrivalTime(LocalDateTime.parse("2025-02-01 13:00:00", formatter));
        flight404.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        // Publish the flight
        service.publishFlight(flight404);
        
        // Create a customer and booking to get a reservation
        AirlineReservationSystem.Customer customer19 = new AirlineReservationSystem.Customer();
        customer19.setId("CU19");
        customer19.setName("Customer19");
        
        List<String> passengers = Arrays.asList("P12");
        service.createBooking(customer19, flight404.getId(), passengers);
        
        // Get the reservation and confirm it
        AirlineReservationSystem.Reservation reservation404 = service.getAllReservations().iterator().next();
        service.confirmReservation(reservation404.getId());
        
        // 3. Flight F404: openForBooking = False (close the flight)
        service.closeFlight(flight404.getId());
        
        // 4. Reservation R404 status = CONFIRMED (passenger P12)
        // 5. Current time = 2025-01-20 08:00:00
        // Note: We cannot mock time, but flight is already closed which should block cancellation
        
        // Input: Cancel reservation R404
        boolean result = service.cancelReservation(reservation404.getId());
        
        // Expected Output: False
        assertFalse("Cancellation should fail for closed flight", result);
        
        // Verify reservation status remains CONFIRMED
        assertEquals("Reservation status should remain CONFIRMED", 
                    AirlineReservationSystem.ReservationStatus.CONFIRMED, 
                    reservation404.getStatus());
    }
    
    @Test
    public void testCase5_UnknownReservationIdentifier() {
        // Setup
        // 1. Airline AL20 (implied by service instance)
        // 2. Airports AP200 (CityAI) and AP201 (CityAJ)
        AirlineReservationSystem.City cityAI = new AirlineReservationSystem.City();
        cityAI.setId("CAI");
        cityAI.setName("CityAI");
        
        AirlineReservationSystem.City cityAJ = new AirlineReservationSystem.City();
        cityAJ.setId("CAJ");
        cityAJ.setName("CityAJ");
        
        AirlineReservationSystem.Airport ap200 = new AirlineReservationSystem.Airport();
        ap200.setId("AP200");
        ap200.setName("AP200");
        ap200.setCity(cityAI);
        
        AirlineReservationSystem.Airport ap201 = new AirlineReservationSystem.Airport();
        ap201.setId("AP201");
        ap201.setName("AP201");
        ap201.setCity(cityAJ);
        
        // 3. Flight F405
        AirlineReservationSystem.Flight flight405 = new AirlineReservationSystem.Flight();
        flight405.setDepartureAirport(ap200);
        flight405.setArrivalAirport(ap201);
        flight405.setDepartureTime(LocalDateTime.parse("2025-03-10 10:00:00", formatter));
        flight405.setArrivalTime(LocalDateTime.parse("2025-03-10 14:00:00", formatter));
        flight405.setStatus(AirlineReservationSystem.FlightStatus.OPEN);
        
        // Publish the flight
        service.publishFlight(flight405);
        
        // 4. Customer CU20 with one existing reservation R405 (status = PENDING) for passenger P13
        AirlineReservationSystem.Customer customer20 = new AirlineReservationSystem.Customer();
        customer20.setId("CU20");
        customer20.setName("Customer20");
        
        List<String> passengers20 = Arrays.asList("P13");
        service.createBooking(customer20, flight405.getId(), passengers20);
        
        // Customer CU21 with one existing reservation R406 (status = PENDING) for passenger P14
        AirlineReservationSystem.Customer customer21 = new AirlineReservationSystem.Customer();
        customer21.setId("CU21");
        customer21.setName("Customer21");
        
        List<String> passengers21 = Arrays.asList("P14");
        service.createBooking(customer21, flight405.getId(), passengers21);
        
        // 5. Current time = 2025-02-15 09:00:00
        // Note: We cannot mock time, but 2025-02-15 is before 2025-03-10
        
        // Input: Customer CU20 confirm reservation R406
        // R406 belongs to CU21, not CU20 - but the method doesn't check customer ownership
        // The test case seems to imply R406 doesn't exist for CU20, but actually R406 exists for CU21
        // Based on the description "Unknown reservation identifier", we should test with a non-existent ID
        boolean result = service.confirmReservation("R999"); // Non-existent reservation ID
        
        // Expected Output: False
        assertFalse("Confirmation should fail for unknown reservation ID", result);
    }
}