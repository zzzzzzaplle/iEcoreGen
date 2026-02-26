import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.Arrays;

public class CR3Test {
    
    private OnlineRideshareSystem system;
    private Driver driverD3;
    private Driver driverD4;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        driverD3 = new Driver();
        driverD3.setId("D3");
        driverD4 = new Driver();
        driverD4.setId("D4");
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        Trip tripA1 = new Trip();
        tripA1.setDriver(driverD3);
        Stop stopA1_1 = new Stop();
        stopA1_1.setStopStation("CityX");
        Stop stopA1_2 = new Stop();
        stopA1_2.setStopStation("CityY");
        tripA1.setStops(Arrays.asList(stopA1_1, stopA1_2));
        
        // Setup: Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        Trip tripA2 = new Trip();
        tripA2.setDriver(driverD3);
        Stop stopA2_1 = new Stop();
        stopA2_1.setStopStation("CityY");
        Stop stopA2_2 = new Stop();
        stopA2_2.setStopStation("CityZ");
        tripA2.setStops(Arrays.asList(stopA2_1, stopA2_2));
        
        // Execute: Check stop overlap between Trip A1 and A2
        boolean result = system.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        
        // Verify: Expected Output: true, common stop: "CityY"
        assertTrue("Should return true when trips share common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        Trip tripB1 = new Trip();
        tripB1.setDriver(driverD4);
        Stop stopB1_1 = new Stop();
        stopB1_1.setStopStation("CityM");
        Stop stopB1_2 = new Stop();
        stopB1_2.setStopStation("CityN");
        tripB1.setStops(Arrays.asList(stopB1_1, stopB1_2));
        
        // Setup: Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        Trip tripB2 = new Trip();
        tripB2.setDriver(driverD4);
        Stop stopB2_1 = new Stop();
        stopB2_1.setStopStation("CityP");
        Stop stopB2_2 = new Stop();
        stopB2_2.setStopStation("CityQ");
        tripB2.setStops(Arrays.asList(stopB2_1, stopB2_2));
        
        // Execute: Check stop overlap between Trip B1 and B2
        boolean result = system.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        
        // Verify: Expected Output: false, no common stop
        assertFalse("Should return false when trips have no common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Trip C1 has no stops
        Trip tripC1 = new Trip();
        tripC1.setDriver(driverD3);
        tripC1.setStops(Arrays.asList());
        
        // Setup: Trip C2 has no stops
        Trip tripC2 = new Trip();
        tripC2.setDriver(driverD3);
        tripC2.setStops(Arrays.asList());
        
        // Execute: Check stop overlap between Trip C1 and C2
        boolean result = system.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        
        // Verify: Expected Output: false. Tests empty stop handling
        assertFalse("Should return false when both trips have empty stop lists", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        Trip tripD1 = new Trip();
        tripD1.setDriver(driverD3);
        Stop stopD1_1 = new Stop();
        stopD1_1.setStopStation("A");
        Stop stopD1_2 = new Stop();
        stopD1_2.setStopStation("B");
        Stop stopD1_3 = new Stop();
        stopD1_3.setStopStation("C");
        tripD1.setStops(Arrays.asList(stopD1_1, stopD1_2, stopD1_3));
        
        // Setup: Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        Trip tripD2 = new Trip();
        tripD2.setDriver(driverD3);
        Stop stopD2_1 = new Stop();
        stopD2_1.setStopStation("X");
        Stop stopD2_2 = new Stop();
        stopD2_2.setStopStation("B");
        Stop stopD2_3 = new Stop();
        stopD2_3.setStopStation("C");
        tripD2.setStops(Arrays.asList(stopD2_1, stopD2_2, stopD2_3));
        
        // Execute: Check stop overlap between Trip D1 and D2
        boolean result = system.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        
        // Verify: Expected Output: true, common stop: Stop("B"), Stop("C")
        assertTrue("Should return true when trips share multiple common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Trip E1 stops: [Stop("Boston")]
        Trip tripE1 = new Trip();
        tripE1.setDriver(driverD3);
        Stop stopE1 = new Stop();
        stopE1.setStopStation("Boston");
        tripE1.setStops(Arrays.asList(stopE1));
        
        // Setup: Trip E2 stops: [Stop("boston")]
        Trip tripE2 = new Trip();
        tripE2.setDriver(driverD3);
        Stop stopE2 = new Stop();
        stopE2.setStopStation("boston");
        tripE2.setStops(Arrays.asList(stopE2));
        
        // Execute: Check stop overlap between Trip E1 and E2
        boolean result = system.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        
        // Verify: Expected Output: false, no common stop
        assertFalse("Should return false when stop names differ in case sensitivity", result);
    }
}