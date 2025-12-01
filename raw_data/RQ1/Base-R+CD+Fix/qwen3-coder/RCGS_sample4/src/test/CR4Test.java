import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        // Initialize a fresh store before each test
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // SetUp: Create Store instance and add 3 cars with different prices
        Car car1 = new Car();
        car1.setDailyPrice(50.0);
        store.getCars().add(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(70.0);
        store.getCars().add(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(80.0);
        store.getCars().add(car3);
        
        // Expected Output: (50 + 70 + 80) / 3 = 66.67 CNY
        double expectedAverage = 66.67;
        double actualAverage = store.determineAverageDailyPrice();
        
        // Use delta for floating point comparison
        assertEquals("Average daily price should be 66.67 for cars priced at 50, 70, and 80", 
                     expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // SetUp: Store instance is already created in setUp() with no cars
        
        // Expected Output: 0 CNY (no cars available)
        double expectedAverage = 0.0;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 0.0 when store has no cars", 
                     expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // SetUp: Create Store instance and add one car
        Car car1 = new Car();
        car1.setDailyPrice(100.0);
        store.getCars().add(car1);
        
        // Expected Output: 100 CNY (only one car available)
        double expectedAverage = 100.0;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 100.0 when store has one car priced at 100", 
                     expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // SetUp: Create Store instance and add 3 cars with different prices
        Car car1 = new Car();
        car1.setDailyPrice(30.0);
        store.getCars().add(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(150.0);
        store.getCars().add(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(120.0);
        store.getCars().add(car3);
        
        // Expected Output: (30 + 150 + 120) / 3 = 100 CNY
        double expectedAverage = 100.0;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 100.0 for cars priced at 30, 150, and 120", 
                     expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // SetUp: Create Store instance and add 3 cars with same price
        Car car1 = new Car();
        car1.setDailyPrice(60.0);
        store.getCars().add(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(60.0);
        store.getCars().add(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(60.0);
        store.getCars().add(car3);
        
        // Expected Output: (60 + 60 + 60) / 3 = 60 CNY
        double expectedAverage = 60.0;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 60.0 when all cars are priced at 60", 
                     expectedAverage, actualAverage, 0.0);
    }
}