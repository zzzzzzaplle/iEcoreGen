import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Airport airportAP01;
    private Airport airportAP02;
    private Airport airportAP03;
    private Airport airportAP04;
    private Airport airportAP05;
    private Airport airportAP06;
    private Airport airportAP07;
    private Airport airportAP08;
    private Airport airportAP09;
    private Flight flightF100;
    private Flight flightF101;
    private Flight flightF102;
    private Flight flightF103;
    private Flight flightF104;
    private DateTimeFormatter formatter;
    private LocalDateTime currentTimeTestCase1;
    private LocalDateTime currentTimeTestCase2;
    private LocalDateTime currentTimeTestCase3;
    private LocalDateTime currentTimeTestCase4;
    private LocalDateTime currentTimeTestCase5;

    @Before
    public void setUp() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        // Setup airports
        airportAP01 = new Airport();
        airportAP01.setId("AP01");
        airportAP01.addCity("CityA");
        
        airportAP02 = new Airport();
        airportAP02.setId("AP02");
        airportAP02.addCity("CityB");
        
        airportAP03 = new Airport();
        airportAP03.setId("AP03");
        airportAP03.addCity("CityC");
        
        airportAP04 = new Airport();
        airportAP04.setId("AP04");
        airportAP04.addCity("CityD");
        
        airportAP05 = new Airport();
        airportAP05.setId("AP05");
        airportAP05.addCity("CityE");
        
        airportAP06 = new Airport();
        airportAP06.setId("AP06");
        airportAP06.addCity("CityF");
        
        airportAP07 = new Airport();
        airportAP07.setId("AP07");
        airportAP07.addCity("CityG");
        
        airportAP08 = new Airport();
        airportAP08.setId("AP08");
        airportAP08.addCity("CityH");
        
        airportAP09 = new Airport();
        airportAP09.setId("AP09");
        airportAP09.addCity("CityI");
        
        // Setup test case times
        currentTimeTestCase1 = LocalDateTime.parse("2024-12-01 09:00:00", formatter);
        currentTimeTestCase2 = LocalDateTime.parse("2024-12-15 10:00:00", formatter);
        currentTimeTestCase3 = LocalDateTime.parse("2025-02-01 09:00:00", formatter);
        currentTimeTestCase4 = LocalDateTime.parse("2025-04-01 09:00:00", formatter);
        currentTimeTestCase5 = LocalDateTime.parse("2025-04-01 10:00:00", formatter);
        
        // Setup flights
        flightF100 = new Flight();
        flightF100.setId("F100");
        flightF100.setDepartureAirport(airportAP01);
        flightF100.setArrivalAirport(airportAP02);
        flightF100.setDepartureTime(LocalDateTime.parse("2025-01-10 10:00:00", formatter));
        flightF100.setArrivalTime(LocalDateTime.parse("2025-01-10 14:00:00", formatter));
        
        flightF101 = new Flight();
        flightF101.setId("F101");
        flightF101.setDepartureAirport(airportAP03);
        flightF101.setArrivalAirport(airportAP04);
        flightF101.setDepartureTime(LocalDateTime.parse("2025-02-05 20:00:00", formatter));
        flightF101.setArrivalTime(LocalDateTime.parse("2025-02-05 18:00:00", formatter));
        
        flightF102 = new Flight();
        flightF102.setId("F102");
        flightF102.setDepartureAirport(airportAP05);
        flightF102.setArrivalAirport(airportAP05);
        flightF102.setDepartureTime(LocalDateTime.parse("2025-03-01 08:00:00", formatter));
        flightF102.setArrivalTime(LocalDateTime.parse("2025-03-01 12:00:00", formatter));
        
        flightF103 = new Flight();
        flightF103.setId("F103");
        flightF103.setDepartureAirport(airportAP06);
        flightF103.setArrivalAirport(airportAP07);
        flightF103.setDepartureTime(LocalDateTime.parse("2025-03-30 10:00:00", formatter));
        flightF103.setArrivalTime(LocalDateTime.parse("2025-03-30 12:00:00", formatter));
        
        flightF104 = new Flight();
        flightF104.setId("F104");
        flightF104.setDepartureAirport(airportAP08);
        flightF104.setArrivalAirport(airportAP09);
        flightF104.setDepartureTime(LocalDateTime.parse("2025-05-05 07:00:00", formatter));
        flightF104.setArrivalTime(LocalDateTime.parse("2025-05-05 10:00:00", formatter));
    }

    @Test
    public void testCase1_correctScheduleAndRoute() {
        // Setup: Set current time for this test case
        LocalDateTime originalNow = LocalDateTime.now();
        try {
            // Mock current time for test case 1
            java.lang.reflect.Field field = LocalDateTime.class.getDeclaredField("NOW");
            field.setAccessible(true);
            field.set(null, currentTimeTestCase1);
            
            // Test: Publish flight F100
            boolean result = flightF100.publish();
            
            // Verify: Should return true for correct schedule and route
            assertTrue("Flight should publish successfully with valid schedule and route", result);
        } catch (Exception e) {
            fail("Exception occurred during test: " + e.getMessage());
        } finally {
            // Reset current time
            try {
                java.lang.reflect.Field field = LocalDateTime.class.getDeclaredField("NOW");
                field.setAccessible(true);
                field.set(null, originalNow);
            } catch (Exception e) {
                // Ignore reset failure
            }
        }
    }

    @Test
    public void testCase2_departureAfterArrival() {
        // Setup: Set current time for this test case
        LocalDateTime originalNow = LocalDateTime.now();
        try {
            // Mock current time for test case 2
            java.lang.reflect.Field field = LocalDateTime.class.getDeclaredField("NOW");
            field.setAccessible(true);
            field.set(null, currentTimeTestCase2);
            
            // Test: Publish flight F101 with departure after arrival
            boolean result = flightF101.publish();
            
            // Verify: Should return false for invalid temporal consistency
            assertFalse("Flight should not publish when departure is after arrival", result);
        } catch (Exception e) {
            fail("Exception occurred during test: " + e.getMessage());
        } finally {
            // Reset current time
            try {
                java.lang.reflect.Field field = LocalDateTime.class.getDeclaredField("NOW");
                field.setAccessible(true);
                field.set(null, originalNow);
            } catch (Exception e) {
                // Ignore reset failure
            }
        }
    }

    @Test
    public void testCase3_sameDepartureAndArrivalAirport() {
        // Setup: Set current time for this test case
        LocalDateTime originalNow = LocalDateTime.now();
        try {
            // Mock current time for test case 3
            java.lang.reflect.Field field = LocalDateTime.class.getDeclaredField("NOW");
            field.setAccessible(true);
            field.set(null, currentTimeTestCase3);
            
            // Test: Publish flight F102 with same departure and arrival airport
            boolean result = flightF102.publish();
            
            // Verify: Should return false for invalid route integrity
            assertFalse("Flight should not publish when departure and arrival airports are the same", result);
        } catch (Exception e) {
            fail("Exception occurred during test: " + e.getMessage());
        } finally {
            // Reset current time
            try {
                java.lang.reflect.Field field = LocalDateTime.class.getDeclaredField("NOW");
                field.setAccessible(true);
                field.set(null, originalNow);
            } catch (Exception e) {
                // Ignore reset failure
            }
        }
    }

    @Test
    public void testCase4_departureTimeInThePast() {
        // Setup: Set current time for this test case
        LocalDateTime originalNow = LocalDateTime.now();
        try {
            // Mock current time for test case 4
            java.lang.reflect.Field field = LocalDateTime.class.getDeclaredField("NOW");
            field.setAccessible(true);
            field.set(null, currentTimeTestCase4);
            
            // Test: Publish flight F103 with departure time in the past
            boolean result = flightF103.publish();
            
            // Verify: Should return false for departure time in the past
            assertFalse("Flight should not publish when departure time is in the past", result);
        } catch (Exception e) {
            fail("Exception occurred during test: " + e.getMessage());
        } finally {
            // Reset current time
            try {
                java.lang.reflect.Field field = LocalDateTime.class.getDeclaredField("NOW");
                field.setAccessible(true);
                field.set(null, originalNow);
            } catch (Exception e) {
                // Ignore reset failure
            }
        }
    }

    @Test
    public void testCase5_secondPublishAttempt() {
        // Setup: Set current time for this test case
        LocalDateTime originalNow = LocalDateTime.now();
        try {
            // Mock current time for test case 5
            java.lang.reflect.Field field = LocalDateTime.class.getDeclaredField("NOW");
            field.setAccessible(true);
            field.set(null, currentTimeTestCase5);
            
            // First publish attempt - should succeed
            boolean firstAttempt = flightF104.publish();
            assertTrue("First publish attempt should succeed", firstAttempt);
            
            // Second publish attempt - should fail
            boolean secondAttempt = flightF104.publish();
            
            // Verify: Should return false for second publish attempt
            assertFalse("Flight should not publish when already published", secondAttempt);
        } catch (Exception e) {
            fail("Exception occurred during test: " + e.getMessage());
        } finally {
            // Reset current time
            try {
                java.lang.reflect.Field field = LocalDateTime.class.getDeclaredField("NOW");
                field.setAccessible(true);
                field.set(null, originalNow);
            } catch (Exception e) {
                // Ignore reset failure
            }
        }
    }
}