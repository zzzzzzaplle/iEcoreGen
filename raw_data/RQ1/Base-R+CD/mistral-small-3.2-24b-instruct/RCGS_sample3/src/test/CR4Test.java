import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Create Store instance and add cars with daily prices 50, 70, 80
        store.setCars(new ArrayList<>());
        store.getCars().add(new Car("ABC123", "Model1", 50.0));
        store.getCars().add(new Car("DEF456", "Model2", 70.0));
        store.getCars().add(new Car("GHI789", "Model3", 80.0));
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Create Store instance with no cars
        store.setCars(new ArrayList<>());
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: 0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Create Store instance and add one car with daily price 100
        store.setCars(new ArrayList<>());
        store.getCars().add(new Car("XYZ789", "ModelX", 100.0));
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: 100 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithVaryingPrices() {
        // SetUp: Create Store instance and add cars with daily prices 30, 150, 120
        store.setCars(new ArrayList<>());
        store.getCars().add(new Car("CAR001", "ModelA", 30.0));
        store.getCars().add(new Car("CAR002", "ModelB", 150.0));
        store.getCars().add(new Car("CAR003", "ModelC", 120.0));
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Create Store instance and add three cars with same daily price 60
        store.setCars(new ArrayList<>());
        store.getCars().add(new Car("CAR111", "Model1", 60.0));
        store.getCars().add(new Car("CAR222", "Model2", 60.0));
        store.getCars().add(new Car("CAR333", "Model3", 60.0));
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.0);
    }
}