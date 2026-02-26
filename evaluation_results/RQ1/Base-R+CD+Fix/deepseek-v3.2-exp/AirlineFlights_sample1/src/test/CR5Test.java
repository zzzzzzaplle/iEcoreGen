import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR5Test {
    private Airline airline;
    private Flight flight1;
    private Flight flight2;
    private Flight flight3;
    private Flight flight4;
    private Flight flight5;
    private Date futureDate;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() throws Exception {
        airline = new Airline();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        futureDate = dateFormat.parse("2024-12-31 23:59:59"); // Future date for valid operations
        
        // Setup flight F501 with three confirmed reservations
        flight1 = new Flight();
        flight1.setId("F501");
        flight1.setOpenForBooking(true);
        flight1.setDepartureTime(futureDate);
        flight1.setArrivalTime(futureDate);
        
        Reservation res1 = new Reservation();
        res1.setId("R501-1");
        res1.setStatus(ReservationStatus.CONFIRMED);
        Reservation res2 = new Reservation();
        res2.setId("R501-2");
        res2.setStatus(ReservationStatus.CONFIRMED);
        Reservation res3 = new Reservation();
        res3.setId("R501-3");
        res3.setStatus(ReservationStatus.CONFIRMED);
        
        flight1.setReservations(Arrays.asList(res1, res2, res3));
        airline.addFlight(flight1);
        
        // Setup flight F502 with two pending reservations
        flight2 = new Flight();
        flight2.setId("F502");
        flight2.setOpenForBooking(true);
        flight2.setDepartureTime(futureDate);
        flight2.setArrivalTime(futureDate);
        
        Reservation res4 = new Reservation();
        res4.setId("R502-1");
        res4.setStatus(ReservationStatus.PENDING);
        Reservation res5 = new Reservation();
        res5.setId("R502-2");
        res5.setStatus(ReservationStatus.PENDING);
        
        flight2.setReservations(Arrays.asList(res4, res5));
        airline.addFlight(flight2);
        
        // Setup flight F503 (closed) with one confirmed reservation
        flight3 = new Flight();
        flight3.setId("F503");
        flight3.setOpenForBooking(false);
        flight3.setDepartureTime(futureDate);
        flight3.setArrivalTime(futureDate);
        
        Reservation res6 = new Reservation();
        res6.setId("R503-1");
        res6.setStatus(ReservationStatus.CONFIRMED);
        
        flight3.setReservations(Arrays.asList(res6));
        airline.addFlight(flight3);
        
        // Setup flight F504 with mixed reservation statuses
        flight4 = new Flight();
        flight4.setId("F504");
        flight4.setOpenForBooking(true);
        flight4.setDepartureTime(futureDate);
        flight4.setArrivalTime(futureDate);
        
        Reservation res7 = new Reservation();
        res7.setId("R504-A");
        res7.setStatus(ReservationStatus.CONFIRMED);
        Reservation res8 = new Reservation();
        res8.setId("R504-B");
        res8.setStatus(ReservationStatus.CONFIRMED);
        Reservation res9 = new Reservation();
        res9.setId("R504-C");
        res9.setStatus(ReservationStatus.CANCELED);
        Reservation res10 = new Reservation();
        res10.setId("R504-D");
        res10.setStatus(ReservationStatus.PENDING);
        
        flight4.setReservations(Arrays.asList(res7, res8, res9, res10));
        airline.addFlight(flight4);
        
        // Setup flight F505 for unknown flight test
        flight5 = new Flight();
        flight5.setId("F505");
        flight5.setOpenForBooking(true);
        flight5.setDepartureTime(futureDate);
        flight5.setArrivalTime(futureDate);
        airline.addFlight(flight5);
    }

    @Test
    public void testCase1_flightWithThreeConfirmations() {
        // Retrieve confirmed reservations for flight F501
        Flight foundFlight = null;
        for (Flight f : airline.getFlights()) {
            if (f.getId().equals("F501")) {
                foundFlight = f;
                break;
            }
        }
        
        assertNotNull("Flight F501 should exist", foundFlight);
        List<Reservation> result = foundFlight.getConfirmedReservations();
        
        // Verify output contains exactly R501-1, R501-2, R501-3
        assertEquals("Should return 3 confirmed reservations", 3, result.size());
        Set<String> expectedIds = new HashSet<>(Arrays.asList("R501-1", "R501-2", "R501-3"));
        Set<String> actualIds = new HashSet<>();
        for (Reservation res : result) {
            actualIds.add(res.getId());
        }
        assertEquals("Reservation IDs should match expected set", expectedIds, actualIds);
    }

    @Test
    public void testCase2_noConfirmedReservations() {
        // Retrieve confirmed reservations for flight F502
        Flight foundFlight = null;
        for (Flight f : airline.getFlights()) {
            if (f.getId().equals("F502")) {
                foundFlight = f;
                break;
            }
        }
        
        assertNotNull("Flight F502 should exist", foundFlight);
        List<Reservation> result = foundFlight.getConfirmedReservations();
        
        // Verify output is empty list
        assertTrue("Should return empty list", result.isEmpty());
    }

    @Test
    public void testCase3_flightClosedReturnsZero() {
        // Retrieve confirmed reservations for flight F503
        Flight foundFlight = null;
        for (Flight f : airline.getFlights()) {
            if (f.getId().equals("F503")) {
                foundFlight = f;
                break;
            }
        }
        
        assertNotNull("Flight F503 should exist", foundFlight);
        List<Reservation> result = foundFlight.getConfirmedReservations();
        
        // Verify output is empty list (flight is closed)
        assertTrue("Should return empty list for closed flight", result.isEmpty());
    }

    @Test
    public void testCase4_unknownFlightId() {
        // Try to find non-existent flight FX999
        Flight foundFlight = null;
        for (Flight f : airline.getFlights()) {
            if (f.getId().equals("FX999")) {
                foundFlight = f;
                break;
            }
        }
        
        // Flight should not be found
        assertNull("Flight FX999 should not exist", foundFlight);
        
        // Since flight doesn't exist, getConfirmedReservations cannot be called
        // This test verifies that the flight retrieval returns null as expected
    }

    @Test
    public void testCase5_mixedReservationStatuses() {
        // Retrieve confirmed reservations for flight F504
        Flight foundFlight = null;
        for (Flight f : airline.getFlights()) {
            if (f.getId().equals("F504")) {
                foundFlight = f;
                break;
            }
        }
        
        assertNotNull("Flight F504 should exist", foundFlight);
        List<Reservation> result = foundFlight.getConfirmedReservations();
        
        // Verify output contains only R504-A and R504-B
        assertEquals("Should return 2 confirmed reservations", 2, result.size());
        Set<String> expectedIds = new HashSet<>(Arrays.asList("R504-A", "R504-B"));
        Set<String> actualIds = new HashSet<>();
        for (Reservation res : result) {
            actualIds.add(res.getId());
        }
        assertEquals("Reservation IDs should match expected set", expectedIds, actualIds);
    }
}