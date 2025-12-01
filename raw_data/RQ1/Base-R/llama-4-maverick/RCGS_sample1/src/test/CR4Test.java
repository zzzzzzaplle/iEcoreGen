import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        // Initialize a new store before each test
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        
        // Setup: Add cars with different prices
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("CAR001", "Model A", 50.0));
        cars.add(new Car("CAR002", "Model B", 70.0));
        cars.add(new Car("CAR003", "Model C", 80.0));
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Assert the result with delta for floating point comparison
        assertEquals(66.67, result, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // Expected Output: Average daily price = 0 CNY (no cars available)
        
        // Setup: Store is empty (no cars added)
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Assert the result is 0.0
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // Expected Output: Average daily price = 100 CNY (only one car available)
        
        // Setup: Add one car
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("CAR001", "Model A", 100.0));
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Assert the result
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        
        // Setup: Add cars with varying prices
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("CAR001", "Model A", 30.0));
        cars.add(new Car("CAR002", "Model B", 150.0));
        cars.add(new Car("CAR003", "Model C", 120.0));
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Assert the result
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        
        // Setup: Add cars with same prices
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("CAR001", "Model A", 60.0));
        cars.add(new Car("CAR002", "Model B", 60.0));
        cars.add(new Car("CAR003", "Model C", 60.0));
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Assert the result
        assertEquals(60.0, result, 0.0);
    }
}