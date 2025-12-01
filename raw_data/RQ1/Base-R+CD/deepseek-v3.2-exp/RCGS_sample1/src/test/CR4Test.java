import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // SetUp: Create a Store instance and add three cars with different prices
        Car car1 = new Car();
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(70.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(80.0);
        store.addCar(car3);
        
        // Calculate average daily price and verify result
        double result = store.determineAverageDailyPrice();
        double expected = (50.0 + 70.0 + 80.0) / 3;
        assertEquals(66.67, result, 0.01); // Using delta for floating point comparison
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // SetUp: Create a Store instance (no cars added)
        
        // Calculate average daily price and verify result is 0.0
        double result = store.determineAverageDailyPrice();
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // SetUp: Create a Store instance and add one car
        Car car1 = new Car();
        car1.setDailyPrice(100.0);
        store.addCar(car1);
        
        // Calculate average daily price and verify result equals the car's price
        double result = store.determineAverageDailyPrice();
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // SetUp: Create a Store instance and add three cars with different prices
        Car car1 = new Car();
        car1.setDailyPrice(30.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(150.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(120.0);
        store.addCar(car3);
        
        // Calculate average daily price and verify result
        double result = store.determineAverageDailyPrice();
        double expected = (30.0 + 150.0 + 120.0) / 3;
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // SetUp: Create a Store instance and add three cars with same price
        Car car1 = new Car();
        car1.setDailyPrice(60.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(60.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(60.0);
        store.addCar(car3);
        
        // Calculate average daily price and verify result equals the common price
        double result = store.determineAverageDailyPrice();
        assertEquals(60.0, result, 0.0);
    }
}