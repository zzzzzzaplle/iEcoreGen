import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Create store and add three cars with different prices
        store.addCar(new Car("ABC123", "Model1", 50.0));
        store.addCar(new Car("DEF456", "Model2", 70.0));
        store.addCar(new Car("GHI789", "Model3", 80.0));
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Store is already empty from @Before setup
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: 0 CNY (no cars available)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Create store and add one car
        store.addCar(new Car("ABC123", "Model1", 100.0));
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: 100 CNY (only one car available)
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Create store and add three cars with varying prices
        store.addCar(new Car("ABC123", "Model1", 30.0));
        store.addCar(new Car("DEF456", "Model2", 150.0));
        store.addCar(new Car("GHI789", "Model3", 120.0));
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Create store and add three cars with same price
        store.addCar(new Car("ABC123", "Model1", 60.0));
        store.addCar(new Car("DEF456", "Model2", 60.0));
        store.addCar(new Car("GHI789", "Model3", 60.0));
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.001);
    }
}