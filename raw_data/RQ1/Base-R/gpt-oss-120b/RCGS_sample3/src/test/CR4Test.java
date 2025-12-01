import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        // Initialize a fresh Store instance before each test
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // SetUp: Add Car 1 with daily price: 50 CNY, Car 2 with 70 CNY, Car 3 with 80 CNY
        Car car1 = new Car("PLATE001", "Model1", 50.0);
        Car car2 = new Car("PLATE002", "Model2", 70.0);
        Car car3 = new Car("PLATE003", "Model3", 80.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: (50 + 70 + 80) / 3 = 66.67 CNY
        double expected = (50.0 + 70.0 + 80.0) / 3;
        assertEquals("Average daily price should be 66.67 CNY", expected, result, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // SetUp: Empty store (no cars added)
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: 0.0 CNY (no cars available)
        assertEquals("Average daily price should be 0.0 when no cars are available", 0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // SetUp: Add Car 1 with daily price: 100 CNY
        Car car1 = new Car("PLATE001", "Model1", 100.0);
        store.addCar(car1);
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: 100.0 CNY (only one car available)
        assertEquals("Average daily price should be 100.0 CNY when only one car is available", 100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // SetUp: Add Car 1 with daily price: 30 CNY, Car 2 with 150 CNY, Car 3 with 120 CNY
        Car car1 = new Car("PLATE001", "Model1", 30.0);
        Car car2 = new Car("PLATE002", "Model2", 150.0);
        Car car3 = new Car("PLATE003", "Model3", 120.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: (30 + 150 + 120) / 3 = 100.0 CNY
        double expected = (30.0 + 150.0 + 120.0) / 3;
        assertEquals("Average daily price should be 100.0 CNY", expected, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // SetUp: Add three cars, all with daily price: 60 CNY
        Car car1 = new Car("PLATE001", "Model1", 60.0);
        Car car2 = new Car("PLATE002", "Model2", 60.0);
        Car car3 = new Car("PLATE003", "Model3", 60.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: (60 + 60 + 60) / 3 = 60.0 CNY
        assertEquals("Average daily price should be 60.0 CNY when all cars have the same price", 60.0, result, 0.0);
    }
}