import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CR4Test {

    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_noReservationsToCancel() throws ParseException {
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

        // Create flight F200
        Flight flight = new Flight();
        flight.setId("F200");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-06-20 09:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-06-20 13:00:00"));
        flight.setDepartureAirport(ap10);
        flight.setArrivalAirport(ap11);

        airline.addFlight(flight);

        Date now = dateFormat.parse("2025-05-01 08:00:00");

        // Execute
        boolean result = airline.closeFlight("F200", now);

        // Verify
        assertTrue(result);
        assertFalse(flight.isOpenForBooking());
        assertEquals(0, flight.getReservations().size());
    }

    @Test
    public void testCase2_threeConfirmedReservationsCanceled() throws ParseException {
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

        // Create flight F201
        Flight flight = new Flight();
        flight.setId("F201");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-07-15 14:00:00"));
        flight.setArrivalTime(dateFormat.parse("2025-07-15 18:00:00"));
        flight.setDepartureAirport(ap12);
        flight.setArrivalAirport(ap13);

        airline.addFlight(flight);

        // Create customer and reservations
        Customer customer = new Customer();
        
        // Create three confirmed reservations
        Reservation r1 = new Reservation();
        r1.setId("R201-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        Passenger p1 = new Passenger();
        p1.setName("Passenger1");
        r1.setPassenger(p1);
        r1.setFlight(flight);
        
        Reservation r2 = new Reservation();
        r2.setId("R201-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        Passenger p2 = new Passenger();
        p2.setName("Passenger2");
        r2.setPassenger(p2);
        r2.setFlight(flight);
        
        Reservation r3 = new Reservation();
        r3.setId("R201-3");
        r3.setStatus(ReservationStatus.CONFIRMED);
        Passenger p3 = new Passenger();
        p3.setName("Passenger3");
        r3.setPassenger(p3);
        r3.setFlight(flight);
        
        flight.getReservations().add(r1);
        flight.getReservations().add(r2);
        flight.getReservations().add(r3);

        Date now = dateFormat.parse("2025-06-10 12:00:00");

        // Execute
        boolean result = airline.closeFlight("F201", now);

        // Verify
        assertTrue(result);
        assertFalse(flight.isOpenForBooking());
        assertEquals(ReservationStatus.CANCELLED, r1.getStatus());
        assertEquals(ReservationStatus.CANCELLED, r2.getStatus());
        assertEquals(ReservationStatus.CANCELLED, r3.getStatus());
    }

    @Test
    public void testCase3_flightAlreadyClosed() throws ParseException {
        // Setup
        Airline airline = new Airline();

        // Create flight F202
        Flight flight = new Flight();
        flight.setId("F202");
        flight.setOpenForBooking(false); // Already closed
        flight.setDepartureTime(dateFormat.parse("2025-08-10 11:00:00"));

        airline.addFlight(flight);

        Date now = dateFormat.parse("2025-07-01 09:00:00");

        // Execute
        boolean result = airline.closeFlight("F202", now);

        // Verify
        assertFalse(result);
    }

    @Test
    public void testCase4_closeOnDepartureDay() throws ParseException {
        // Setup
        Airline airline = new Airline();

        // Create airports
        Airport ap14 = new Airport();
        ap14.setId("AP14");
        City cityN = new City();
        cityN.setName("CityN");
        ap14.addCity(cityN);

        Airport ap15 = new Airport();
        ap15.setId("AP15");
        City cityO = new City();
        cityO.setName("CityO");
        ap15.addCity(cityO);

        // Create flight F203
        Flight flight = new Flight();
        flight.setId("F203");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-09-10 09:00:00"));
        flight.setDepartureAirport(ap14);
        flight.setArrivalAirport(ap15);

        // Add confirmed reservations
        Reservation r1 = new Reservation();
        r1.setId("R203-1");
        r1.setStatus(ReservationStatus.CONFIRMED);
        Passenger p1 = new Passenger();
        p1.setName("Passenger1");
        r1.setPassenger(p1);
        r1.setFlight(flight);
        
        Reservation r2 = new Reservation();
        r2.setId("R203-2");
        r2.setStatus(ReservationStatus.CONFIRMED);
        Passenger p2 = new Passenger();
        p2.setName("Passenger2");
        r2.setPassenger(p2);
        r2.setFlight(flight);
        
        flight.getReservations().add(r1);
        flight.getReservations().add(r2);

        airline.addFlight(flight);

        Date now = dateFormat.parse("2025-09-10 05:00:00");

        // Execute
        boolean result = airline.closeFlight("F203", now);

        // Verify
        assertFalse(result);
        // Flight should still be open
        assertTrue(flight.isOpenForBooking());
        // Reservations should still be confirmed
        assertEquals(ReservationStatus.CONFIRMED, r1.getStatus());
        assertEquals(ReservationStatus.CONFIRMED, r2.getStatus());
    }

    @Test
    public void testCase5_attemptToCloseAfterDeparture() throws ParseException {
        // Setup
        Airline airline = new Airline();

        // Create airports
        Airport ap16 = new Airport();
        ap16.setId("AP16");
        City cityP = new City();
        cityP.setName("CityP");
        ap16.addCity(cityP);

        Airport ap17 = new Airport();
        ap17.setId("AP17");
        City cityQ = new City();
        cityQ.setName("CityQ");
        ap17.addCity(cityQ);

        // Create flight F204
        Flight flight = new Flight();
        flight.setId("F204");
        flight.setOpenForBooking(true);
        flight.setDepartureTime(dateFormat.parse("2025-04-01 10:00:00"));
        flight.setDepartureAirport(ap16);
        flight.setArrivalAirport(ap17);

        airline.addFlight(flight);

        Date now = dateFormat.parse("2025-04-01 12:00:00"); // After departure

        // Execute
        boolean result = airline.closeFlight("F204", now);

        // Verify
        assertFalse(result);
    }
}