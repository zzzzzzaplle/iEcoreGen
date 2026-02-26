import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class CR3Test {
    
    private OnlineRideshareSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new OnlineRideshareSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup: Create driver D3 and trips A1, A2 with shared stop "CityY"
        Driver driverD3 = new Driver();
        driverD3.setUserID("D3");
        
        Trip tripA1 = new Trip();
        tripA1.setDriver(driverD3);
        tripA1.addStop("CityX");
        tripA1.addStop("CityY");
        
        Trip tripA2 = new Trip();
        tripA2.setDriver(driverD3);
        tripA2.addStop("CityY");
        tripA2.addStop("CityZ");
        
        // Execute: Check stop overlap
        boolean result = system.checkStopOverlapForIndirectTrips(tripA1, tripA2);
        
        // Verify: Should return true due to common stop "CityY"
        assertTrue("Trips should have common stop CityY", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup: Create driver D4 and trips B1, B2 with no common stops
        Driver driverD4 = new Driver();
        driverD4.setUserID("D4");
        
        Trip tripB1 = new Trip();
        tripB1.setDriver(driverD4);
        tripB1.addStop("CityM");
        tripB1.addStop("CityN");
        
        Trip tripB2 = new Trip();
        tripB2.setDriver(driverD4);
        tripB2.addStop("CityP");
        tripB2.addStop("CityQ");
        
        // Execute: Check stop overlap
        boolean result = system.checkStopOverlapForIndirectTrips(tripB1, tripB2);
        
        // Verify: Should return false as no common stops exist
        assertFalse("Trips should have no common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup: Create trips C1 and C2 with empty stop lists
        Driver driver = new Driver();
        driver.setUserID("D5");
        
        Trip tripC1 = new Trip();
        tripC1.setDriver(driver);
        // No stops added (empty list)
        
        Trip tripC2 = new Trip();
        tripC2.setDriver(driver);
        // No stops added (empty list)
        
        // Execute: Check stop overlap
        boolean result = system.checkStopOverlapForIndirectTrips(tripC1, tripC2);
        
        // Verify: Should return false when both trips have empty stops
        assertFalse("Trips with empty stops should return false", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup: Create trips D1 and D2 with multiple shared stops
        Driver driver = new Driver();
        driver.setUserID("D6");
        
        Trip tripD1 = new Trip();
        tripD1.setDriver(driver);
        tripD1.addStop("A");
        tripD1.addStop("B");
        tripD1.addStop("C");
        
        Trip tripD2 = new Trip();
        tripD2.setDriver(driver);
        tripD2.addStop("X");
        tripD2.addStop("B");
        tripD2.addStop("C");
        
        // Execute: Check stop overlap
        boolean result = system.checkStopOverlapForIndirectTrips(tripD1, tripD2);
        
        // Verify: Should return true due to common stops "B" and "C"
        assertTrue("Trips should have common stops B and C", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup: Create trips E1 and E2 with case-sensitive stop names
        Driver driver = new Driver();
        driver.setUserID("D7");
        
        Trip tripE1 = new Trip();
        tripE1.setDriver(driver);
        tripE1.addStop("Boston");
        
        Trip tripE2 = new Trip();
        tripE2.setDriver(driver);
        tripE2.addStop("boston");
        
        // Execute: Check stop overlap
        boolean result = system.checkStopOverlapForIndirectTrips(tripE1, tripE2);
        
        // Verify: Should return false due to case sensitivity
        assertFalse("Case-sensitive stops should not match", result);
    }
}