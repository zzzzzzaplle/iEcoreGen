import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private OnlineRideshareSystem system;
    private User driverD3;
    private User driverD4;
    private Trip tripA1;
    private Trip tripA2;
    private Trip tripB1;
    private Trip tripB2;
    private Trip tripC1;
    private Trip tripC2;
    private Trip tripD1;
    private Trip tripD2;
    private Trip tripE1;
    private Trip tripE2;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        
        // Create drivers
        driverD3 = new User("D3", "d3@example.com", "1234567890");
        driverD4 = new User("D4", "d4@example.com", "0987654321");
        
        // Create stops for test cases
        Stop cityX = new Stop("S1", "CityX");
        Stop cityY = new Stop("S2", "CityY");
        Stop cityZ = new Stop("S3", "CityZ");
        Stop cityM = new Stop("S4", "CityM");
        Stop cityN = new Stop("S5", "CityN");
        Stop cityP = new Stop("S6", "CityP");
        Stop cityQ = new Stop("S7", "CityQ");
        Stop stopA = new Stop("S8", "A");
        Stop stopB = new Stop("S9", "B");
        Stop stopC = new Stop("S10", "C");
        Stop stopX = new Stop("S11", "X");
        Stop stopBoston = new Stop("S12", "Boston");
        Stop stopboston = new Stop("S13", "boston");
        
        // Create trips for test case 1
        tripA1 = new Trip("A1", driverD3, "StartA1", "EndA1", 4, 
                         LocalDateTime.now(), LocalDateTime.now().plusHours(2), 50.0);
        tripA1.addStop(cityX);
        tripA1.addStop(cityY);
        
        tripA2 = new Trip("A2", driverD3, "StartA2", "EndA2", 4,
                         LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(5), 60.0);
        tripA2.addStop(cityY);
        tripA2.addStop(cityZ);
        
        // Create trips for test case 2
        tripB1 = new Trip("B1", driverD4, "StartB1", "EndB1", 4,
                         LocalDateTime.now(), LocalDateTime.now().plusHours(2), 40.0);
        tripB1.addStop(cityM);
        tripB1.addStop(cityN);
        
        tripB2 = new Trip("B2", driverD4, "StartB2", "EndB2", 4,
                         LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(5), 45.0);
        tripB2.addStop(cityP);
        tripB2.addStop(cityQ);
        
        // Create trips for test case 3
        tripC1 = new Trip("C1", driverD3, "StartC1", "EndC1", 4,
                         LocalDateTime.now(), LocalDateTime.now().plusHours(2), 30.0);
        tripC2 = new Trip("C2", driverD3, "StartC2", "EndC2", 4,
                         LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(5), 35.0);
        
        // Create trips for test case 4
        tripD1 = new Trip("D1", driverD3, "StartD1", "EndD1", 4,
                         LocalDateTime.now(), LocalDateTime.now().plusHours(2), 55.0);
        tripD1.addStop(stopA);
        tripD1.addStop(stopB);
        tripD1.addStop(stopC);
        
        tripD2 = new Trip("D2", driverD3, "StartD2", "EndD2", 4,
                         LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(5), 65.0);
        tripD2.addStop(stopX);
        tripD2.addStop(stopB);
        tripD2.addStop(stopC);
        
        // Create trips for test case 5
        tripE1 = new Trip("E1", driverD3, "StartE1", "EndE1", 4,
                         LocalDateTime.now(), LocalDateTime.now().plusHours(2), 70.0);
        tripE1.addStop(stopBoston);
        
        tripE2 = new Trip("E2", driverD3, "StartE2", "EndE2", 4,
                         LocalDateTime.now().plusHours(3), LocalDateTime.now().plusHours(5), 75.0);
        tripE2.addStop(stopboston);
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Test Case 1: "Shared stop in indirect trips"
        // Setup: Driver D3 posts Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        //        Driver D3 posts Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        // Expected Output: true, common stop: "CityY"
        
        boolean result = system.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        assertTrue("Should return true when trips share common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Test Case 2: "No common stops in indirect trips"
        // Setup: Driver D4 posts Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        //        Driver D4 posts Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        // Expected Output: false, no common stop
        
        boolean result = system.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        assertFalse("Should return false when trips have no common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Test Case 3: "Empty stop lists comparison"
        // Setup: Trip C1 has no stops, Trip C2 has no stops
        // Expected Output: false. Tests empty stop handling
        
        boolean result = system.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        assertFalse("Should return false when both trips have empty stop lists", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Test Case 4: "Multiple shared stops detection"
        // Setup: Trip D1 stops: [Stop("A"), Stop("B"), Stop("C")]
        //        Trip D2 stops: [Stop("X"), Stop("B"), Stop("C")]
        // Expected Output: true, common stop: Stop("B"), Stop("C")
        
        boolean result = system.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        assertTrue("Should return true when trips share multiple common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Test Case 5: "Case-sensitive stop comparison"
        // Setup: Trip E1 stops: [Stop("Boston")]
        //        Trip E2 stops: [Stop("boston")]
        // Expected Output: false, no common stop
        
        boolean result = system.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        assertFalse("Should return false when station names differ only in case", result);
    }
}