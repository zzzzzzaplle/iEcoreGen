import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR4Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // Setup: Create cars with daily prices 50, 70, 80 CNY
        Car car1 = new Car();
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setDailyPrice(70.0);
        
        Car car3 = new Car();
        car3.setDailyPrice(80.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01); // Using delta for double comparison
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // Setup: Store is already empty from @Before
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: 0.0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // Setup: Create a car with daily price 100 CNY
        Car car1 = new Car();
        car1.setDailyPrice(100.0);
        
        // Add car to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: 100.0 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // Setup: Create cars with daily prices 30, 150, 120 CNY
        Car car1 = new Car();
        car1.setDailyPrice(30.0);
        
        Car car2 = new Car();
        car2.setDailyPrice(150.0);
        
        Car car3 = new Car();
        car3.setDailyPrice(120.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: (30 + 150 + 120) / 3 = 100.0 CNY
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // Setup: Create cars with daily price 60 CNY (all same price)
        Car car1 = new Car();
        car1.setDailyPrice(60.0);
        
        Car car2 = new Car();
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setDailyPrice(60.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: (60 + 60 + 60) / 3 = 60.0 CNY
        assertEquals(60.0, result, 0.0);
    }
}