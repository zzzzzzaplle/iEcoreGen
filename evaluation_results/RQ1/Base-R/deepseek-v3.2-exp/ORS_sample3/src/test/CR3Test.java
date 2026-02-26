import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private OnlineRideshareSystem system;
    private User driverD3;
    private User driverD4;
    private Station cityX, cityY, cityZ, cityM, cityN, cityP, cityQ;
    private Station stationA, stationB, stationC, stationX;
    private Station boston1, boston2;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        
        // Create drivers
        driverD3 = new User();
        driverD3.setUserId("D3");
        
        driverD4 = new User();
        driverD4.setUserId("D4");
        
        // Create stations for Test Case 1
        cityX = new Station();
        cityX.setStationId("X001");
        cityX.setStationName("CityX");
        
        cityY = new Station();
        cityY.setStationId("Y001");
        cityY.setStationName("CityY");
        
        cityZ = new Station();
        cityZ.setStationId("Z001");
        cityZ.setStationName("CityZ");
        
        // Create stations for Test Case 2
        cityM = new Station();
        cityM.setStationId("M001");
        cityM.setStationName("CityM");
        
        cityN = new Station();
        cityN.setStationId("N001");
        cityN.setStationName("CityN");
        
        cityP = new Station();
        cityP.setStationId("P001");
        cityP.setStationName("CityP");
        
        cityQ = new Station();
        cityQ.setStationId("Q001");
        cityQ.setStationName("CityQ");
        
        // Create stations for Test Case 4
        stationA = new Station();
        stationA.setStationId("A001");
        stationA.setStationName("A");
        
        stationB = new Station();
        stationB.setStationId("B001");
        stationB.setStationName("B");
        
        stationC = new Station();
        stationC.setStationId("C001");
        stationC.setStationName("C");
        
        stationX = new Station();
        stationX.setStationId("X002");
        stationX.setStationName("X");
        
        // Create stations for Test Case 5
        boston1 = new Station();
        boston1.setStationId("BOS1");
        boston1.setStationName("Boston");
        
        boston2 = new Station();
        boston2.setStationId("BOS2");
        boston2.setStationName("boston");
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        Trip tripA1 = new Trip();
        tripA1.setTripId("A1");
        tripA1.setDriver(driverD3);
        
        Stop stop1A1 = new Stop();
        stop1A1.setStopId("S1A1");
        stop1A1.setStopStation(cityX);
        
        Stop stop2A1 = new Stop();
        stop2A1.setStopId("S2A1");
        stop2A1.setStopStation(cityY);
        
        List<Stop> stopsA1 = new ArrayList<>();
        stopsA1.add(stop1A1);
        stopsA1.add(stop2A1);
        tripA1.setStops(stopsA1);
        
        // Setup: Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        Trip tripA2 = new Trip();
        tripA2.setTripId("A2");
        tripA2.setDriver(driverD3);
        
        Stop stop1A2 = new Stop();
        stop1A2.setStopId("S1A2");
        stop1A2.setStopStation(cityY);
        
        Stop stop2A2 = new Stop();
        stop2A2.setStopId("S2A2");
        stop2A2.setStopStation(cityZ);
        
        List<Stop> stopsA2 = new ArrayList<>();
        stopsA2.add(stop1A2);
        stopsA2.add(stop2A2);
        tripA2.setStops(stopsA2);
        
        // Test: Check stop overlap between Trip A1 and A2
        boolean result = system.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        
        // Expected Output: true, common stop: "CityY"
        assertTrue("Trips should have shared stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        Trip tripB1 = new Trip();
        tripB1.setTripId("B1");
        tripB1.setDriver(driverD4);
        
        Stop stop1B1 = new Stop();
        stop1B1.setStopId("S1B1");
        stop1B1.setStopStation(cityM);
        
        Stop stop2B1 = new Stop();
        stop2B1.setStopId("S2B1");
        stop2B1.setStopStation(cityN);
        
        List<Stop> stopsB1 = new ArrayList<>();
        stopsB1.add(stop1B1);
        stopsB1.add(stop2B1);
        tripB1.setStops(stopsB1);
        
        // Setup: Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        Trip tripB2 = new Trip();
        tripB2.setTripId("B2");
        tripB2.setDriver(driverD4);
        
        Stop stop1B2 = new Stop();
        stop1B2.setStopId("S1B2");
        stop1B2.setStopStation(cityP);
        
        Stop stop2B2 = new Stop();
        stop2B2.setStopId("S2B2");
        stop2B2.setStopStation(cityQ);
        
        List<Stop> stopsB2 = new ArrayList<>();
        stopsB2.add(stop1B2);
        stopsB2.add(stop2B2);
        tripB2.setStops(stopsB2);
        
        // Test: Check stop overlap between Trip B1 and B2
        boolean result = system.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        
        // Expected Output: false, no common stop
        assertFalse("Trips should not have any common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 has no stops
        Trip tripC1 = new Trip();
        tripC1.setTripId("C1");
        tripC1.setDriver(driverD3);
        tripC1.setStops(new ArrayList<>());
        
        // Setup: Trip C2 has no stops
        Trip tripC2 = new Trip();
        tripC2.setTripId("C2");
        tripC2.setDriver(driverD3);
        tripC2.setStops(new ArrayList<>());
        
        // Test: Check stop overlap between Trip C1 and C2
        boolean result = system.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        
        // Expected Output: false (tests empty stop handling)
        assertFalse("Empty stop lists should not have overlap", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        Trip tripD1 = new Trip();
        tripD1.setTripId("D1");
        tripD1.setDriver(driverD3);
        
        Stop stop1D1 = new Stop();
        stop1D1.setStopId("S1D1");
        stop1D1.setStopStation(stationA);
        
        Stop stop2D1 = new Stop();
        stop2D1.setStopId("S2D1");
        stop2D1.setStopStation(stationB);
        
        Stop stop3D1 = new Stop();
        stop3D1.setStopId("S3D1");
        stop3D1.setStopStation(stationC);
        
        List<Stop> stopsD1 = new ArrayList<>();
        stopsD1.add(stop1D1);
        stopsD1.add(stop2D1);
        stopsD1.add(stop3D1);
        tripD1.setStops(stopsD1);
        
        // Setup: Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        Trip tripD2 = new Trip();
        tripD2.setTripId("D2");
        tripD2.setDriver(driverD3);
        
        Stop stop1D2 = new Stop();
        stop1D2.setStopId("S1D2");
        stop1D2.setStopStation(stationX);
        
        Stop stop2D2 = new Stop();
        stop2D2.setStopId("S2D2");
        stop2D2.setStopStation(stationB);
        
        Stop stop3D2 = new Stop();
        stop3D2.setStopId("S3D2");
        stop3D2.setStopStation(stationC);
        
        List<Stop> stopsD2 = new ArrayList<>();
        stopsD2.add(stop1D2);
        stopsD2.add(stop2D2);
        stopsD2.add(stop3D2);
        tripD2.setStops(stopsD2);
        
        // Test: Check stop overlap between Trip D1 and D2
        boolean result = system.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        
        // Expected Output: true, common stop: Stop("B"), Stop("C")
        assertTrue("Trips should have shared stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 stops: [Stop("Boston")]
        Trip tripE1 = new Trip();
        tripE1.setTripId("E1");
        tripE1.setDriver(driverD3);
        
        Stop stop1E1 = new Stop();
        stop1E1.setStopId("S1E1");
        stop1E1.setStopStation(boston1);
        
        List<Stop> stopsE1 = new ArrayList<>();
        stopsE1.add(stop1E1);
        tripE1.setStops(stopsE1);
        
        // Setup: Trip E2 stops: [Stop("boston")]
        Trip tripE2 = new Trip();
        tripE2.setTripId("E2");
        tripE2.setDriver(driverD3);
        
        Stop stop1E2 = new Stop();
        stop1E2.setStopId("S1E2");
        stop1E2.setStopStation(boston2);
        
        List<Stop> stopsE2 = new ArrayList<>();
        stopsE2.add(stop1E2);
        tripE2.setStops(stopsE2);
        
        // Test: Check stop overlap between Trip E1 and E2
        boolean result = system.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        
        // Expected Output: false, no common stop (case-sensitive comparison)
        assertFalse("Case-sensitive station names should not match", result);
    }
}