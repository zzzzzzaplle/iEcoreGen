import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR3Test {
    
    private Driver driver;
    
    @Before
    public void setUp() {
        driver = new Driver();
        driver.setID("D3");
    }
    
    @Test
    public void testCase1_SharedStopInIndirectTrips() {
        // Setup Trip A1 with stops: [Stop("CityX"), Stop("CityY")]
        Trip tripA1 = new Trip();
        tripA1.setDepartureStation("StartA");
        tripA1.setArrivalStation("EndA");
        
        Stop stop1A1 = new Stop();
        stop1A1.setStopStation("CityX");
        Stop stop2A1 = new Stop();
        stop2A1.setStopStation("CityY");
        
        List<Stop> stopsA1 = new ArrayList<>();
        stopsA1.add(stop1A1);
        stopsA1.add(stop2A1);
        tripA1.setStops(stopsA1);
        
        // Setup Trip A2 with stops: [Stop("CityY"), Stop("CityZ")]
        Trip tripA2 = new Trip();
        tripA2.setDepartureStation("StartB");
        tripA2.setArrivalStation("EndB");
        
        Stop stop1A2 = new Stop();
        stop1A2.setStopStation("CityY");
        Stop stop2A2 = new Stop();
        stop2A2.setStopStation("CityZ");
        
        List<Stop> stopsA2 = new ArrayList<>();
        stopsA2.add(stop1A2);
        stopsA2.add(stop2A2);
        tripA2.setStops(stopsA2);
        
        // Check stop overlap - should return true with common stop "CityY"
        boolean result = driver.checkStopOverlap(tripA1, tripA2);
        assertTrue("Should return true when trips share common stop 'CityY'", result);
    }
    
    @Test
    public void testCase2_NoCommonStopsInIndirectTrips() {
        // Setup Trip B1 with stops: [Stop("CityM"), Stop("CityN")]
        Trip tripB1 = new Trip();
        tripB1.setDepartureStation("StartC");
        tripB1.setArrivalStation("EndC");
        
        Stop stop1B1 = new Stop();
        stop1B1.setStopStation("CityM");
        Stop stop2B1 = new Stop();
        stop2B1.setStopStation("CityN");
        
        List<Stop> stopsB1 = new ArrayList<>();
        stopsB1.add(stop1B1);
        stopsB1.add(stop2B1);
        tripB1.setStops(stopsB1);
        
        // Setup Trip B2 with stops: [Stop("CityP"), Stop("CityQ")]
        Trip tripB2 = new Trip();
        tripB2.setDepartureStation("StartD");
        tripB2.setArrivalStation("EndD");
        
        Stop stop1B2 = new Stop();
        stop1B2.setStopStation("CityP");
        Stop stop2B2 = new Stop();
        stop2B2.setStopStation("CityQ");
        
        List<Stop> stopsB2 = new ArrayList<>();
        stopsB2.add(stop1B2);
        stopsB2.add(stop2B2);
        tripB2.setStops(stopsB2);
        
        // Check stop overlap - should return false with no common stops
        boolean result = driver.checkStopOverlap(tripB1, tripB2);
        assertFalse("Should return false when trips have no common stops", result);
    }
    
    @Test
    public void testCase3_EmptyStopListsComparison() {
        // Setup Trip C1 with no stops
        Trip tripC1 = new Trip();
        tripC1.setDepartureStation("StartE");
        tripC1.setArrivalStation("EndE");
        tripC1.setStops(new ArrayList<>());
        
        // Setup Trip C2 with no stops
        Trip tripC2 = new Trip();
        tripC2.setDepartureStation("StartF");
        tripC2.setArrivalStation("EndF");
        tripC2.setStops(new ArrayList<>());
        
        // Check stop overlap - should return false with empty stop lists
        boolean result = driver.checkStopOverlap(tripC1, tripC2);
        assertFalse("Should return false when both trips have empty stop lists", result);
    }
    
    @Test
    public void testCase4_MultipleSharedStopsDetection() {
        // Setup Trip D1 with stops: [Stop("A"), Stop("B"), Stop("C")]
        Trip tripD1 = new Trip();
        tripD1.setDepartureStation("StartG");
        tripD1.setArrivalStation("EndG");
        
        Stop stop1D1 = new Stop();
        stop1D1.setStopStation("A");
        Stop stop2D1 = new Stop();
        stop2D1.setStopStation("B");
        Stop stop3D1 = new Stop();
        stop3D1.setStopStation("C");
        
        List<Stop> stopsD1 = new ArrayList<>();
        stopsD1.add(stop1D1);
        stopsD1.add(stop2D1);
        stopsD1.add(stop3D1);
        tripD1.setStops(stopsD1);
        
        // Setup Trip D2 with stops: [Stop("X"), Stop("B"), Stop("C")]
        Trip tripD2 = new Trip();
        tripD2.setDepartureStation("StartH");
        tripD2.setArrivalStation("EndH");
        
        Stop stop1D2 = new Stop();
        stop1D2.setStopStation("X");
        Stop stop2D2 = new Stop();
        stop2D2.setStopStation("B");
        Stop stop3D2 = new Stop();
        stop3D2.setStopStation("C");
        
        List<Stop> stopsD2 = new ArrayList<>();
        stopsD2.add(stop1D2);
        stopsD2.add(stop2D2);
        stopsD2.add(stop3D2);
        tripD2.setStops(stopsD2);
        
        // Check stop overlap - should return true with multiple common stops "B" and "C"
        boolean result = driver.checkStopOverlap(tripD1, tripD2);
        assertTrue("Should return true when trips share multiple common stops 'B' and 'C'", result);
    }
    
    @Test
    public void testCase5_CaseSensitiveStopComparison() {
        // Setup Trip E1 with stops: [Stop("Boston")]
        Trip tripE1 = new Trip();
        tripE1.setDepartureStation("StartI");
        tripE1.setArrivalStation("EndI");
        
        Stop stop1E1 = new Stop();
        stop1E1.setStopStation("Boston");
        
        List<Stop> stopsE1 = new ArrayList<>();
        stopsE1.add(stop1E1);
        tripE1.setStops(stopsE1);
        
        // Setup Trip E2 with stops: [Stop("boston")]
        Trip tripE2 = new Trip();
        tripE2.setDepartureStation("StartJ");
        tripE2.setArrivalStation("EndJ");
        
        Stop stop1E2 = new Stop();
        stop1E2.setStopStation("boston");
        
        List<Stop> stopsE2 = new ArrayList<>();
        stopsE2.add(stop1E2);
        tripE2.setStops(stopsE2);
        
        // Check stop overlap - should return false due to case sensitivity
        boolean result = driver.checkStopOverlap(tripE1, tripE2);
        assertFalse("Should return false when stop names differ only in case", result);
    }
}