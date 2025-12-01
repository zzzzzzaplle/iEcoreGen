import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Store store;
    
    @Before
    public void setUp() {
        // Initialize a new Store instance before each test
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // Setup: Add Car 1 with daily price: 50 CNY, Car 2 with daily price: 70 CNY, Car 3 with daily price: 80 CNY
        Car car1 = new Car();
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(70.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(80.0);
        store.addCar(car3);
        
        // Calculate average daily price and verify expected result
        double expectedAverage = (50.0 + 70.0 + 80.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        // Use delta of 0.01 for floating point comparison
        assertEquals("Average daily price should be 66.67 CNY", expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // Setup: Store instance is empty (no cars added)
        
        // Calculate average daily price and verify expected result
        double expectedAverage = 0.0;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 0.0 when no cars available", expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // Setup: Add Car 1 with daily price: 100 CNY
        Car car1 = new Car();
        car1.setDailyPrice(100.0);
        store.addCar(car1);
        
        // Calculate average daily price and verify expected result
        double expectedAverage = 100.0;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 100.0 CNY with one car", expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // Setup: Add Car 1 with daily price: 30 CNY, Car 2 with daily price: 150 CNY, Car 3 with daily price: 120 CNY
        Car car1 = new Car();
        car1.setDailyPrice(30.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(150.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(120.0);
        store.addCar(car3);
        
        // Calculate average daily price and verify expected result
        double expectedAverage = (30.0 + 150.0 + 120.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 100.0 CNY with varying prices", expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // Setup: Add three cars with the same daily price: 60 CNY each
        Car car1 = new Car();
        car1.setDailyPrice(60.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(60.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(60.0);
        store.addCar(car3);
        
        // Calculate average daily price and verify expected result
        double expectedAverage = (60.0 + 60.0 + 60.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 60.0 CNY with duplicate price cars", expectedAverage, actualAverage, 0.0);
    }
}