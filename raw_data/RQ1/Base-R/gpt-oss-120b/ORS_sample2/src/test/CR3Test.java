import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class CR3Test {
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        Driver driver = new Driver();
        driver.setId("D3");
        
        Trip tripA1 = new Trip();
        tripA1.setId("A1");
        tripA1.setDriver(driver);
        
        Stop stop1A1 = new Stop();
        stop1A1.setStation("CityX");
        Stop stop2A1 = new Stop();
        stop2A1.setStation("CityY");
        tripA1.setStops(Arrays.asList(stop1A1, stop2A1));
        
        // Setup: Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        Trip tripA2 = new Trip();
        tripA2.setId("A2");
        tripA2.setDriver(driver);
        
        Stop stop1A2 = new Stop();
        stop1A2.setStation("CityY");
        Stop stop2A2 = new Stop();
        stop2A2.setStation("CityZ");
        tripA2.setStops(Arrays.asList(stop1A2, stop2A2));
        
        // Execute method under test
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        
        // Verify expected output: true, common stop: "CityY"
        assertTrue("Should return true when trips share common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        Driver driver = new Driver();
        driver.setId("D4");
        
        Trip tripB1 = new Trip();
        tripB1.setId("B1");
        tripB1.setDriver(driver);
        
        Stop stop1B1 = new Stop();
        stop1B1.setStation("CityM");
        Stop stop2B1 = new Stop();
        stop2B1.setStation("CityN");
        tripB1.setStops(Arrays.asList(stop1B1, stop2B1));
        
        // Setup: Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        Trip tripB2 = new Trip();
        tripB2.setId("B2");
        tripB2.setDriver(driver);
        
        Stop stop1B2 = new Stop();
        stop1B2.setStation("CityP");
        Stop stop2B2 = new Stop();
        stop2B2.setStation("CityQ");
        tripB2.setStops(Arrays.asList(stop1B2, stop2B2));
        
        // Execute method under test
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        
        // Verify expected output: false, no common stop
        assertFalse("Should return false when trips have no common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 has no stops
        Trip tripC1 = new Trip();
        tripC1.setId("C1");
        tripC1.setStops(Arrays.asList());
        
        // Setup: Trip C2 has no stops
        Trip tripC2 = new Trip();
        tripC2.setId("C2");
        tripC2.setStops(Arrays.asList());
        
        // Execute method under test
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        
        // Verify expected output: false. Tests empty stop handling
        assertFalse("Should return false when both trips have empty stop lists", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        Trip tripD1 = new Trip();
        tripD1.setId("D1");
        
        Stop stop1D1 = new Stop();
        stop1D1.setStation("A");
        Stop stop2D1 = new Stop();
        stop2D1.setStation("B");
        Stop stop3D1 = new Stop();
        stop3D1.setStation("C");
        tripD1.setStops(Arrays.asList(stop1D1, stop2D1, stop3D1));
        
        // Setup: Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        Trip tripD2 = new Trip();
        tripD2.setId("D2");
        
        Stop stop1D2 = new Stop();
        stop1D2.setStation("X");
        Stop stop2D2 = new Stop();
        stop2D2.setStation("B");
        Stop stop3D2 = new Stop();
        stop3D2.setStation("C");
        tripD2.setStops(Arrays.asList(stop1D2, stop2D2, stop3D2));
        
        // Execute method under test
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        
        // Verify expected output: true, common stop: Stop("B"), Stop("C")
        assertTrue("Should return true when trips share multiple common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 stops: [Stop("Boston")]
        Trip tripE1 = new Trip();
        tripE1.setId("E1");
        
        Stop stopE1 = new Stop();
        stopE1.setStation("Boston");
        tripE1.setStops(Arrays.asList(stopE1));
        
        // Setup: Trip E2 stops: [Stop("boston")]
        Trip tripE2 = new Trip();
        tripE2.setId("E2");
        
        Stop stopE2 = new Stop();
        stopE2.setStation("boston");
        tripE2.setStops(Arrays.asList(stopE2));
        
        // Execute method under test
        boolean result = ORSService.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        
        // Verify expected output: false, no common stop
        assertFalse("Should return false for case-sensitive comparison 'Boston' vs 'boston'", result);
    }
}