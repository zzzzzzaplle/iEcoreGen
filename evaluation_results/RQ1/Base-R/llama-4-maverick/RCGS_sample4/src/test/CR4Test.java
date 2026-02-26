import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        // SetUp: Add Car 1 with daily price: 50 CNY, Car 2 with daily price: 70 CNY, Car 3 with daily price: 80 CNY
        store.addCar(new Car("CAR001", "Model1", 50.0));
        store.addCar(new Car("CAR002", "Model2", 70.0));
        store.addCar(new Car("CAR003", "Model3", 80.0));
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01); // Using delta for double comparison
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // SetUp: Create a Store instance (already done in @Before)
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // SetUp: Add Car 1 with daily price: 100 CNY
        store.addCar(new Car("CAR001", "Model1", 100.0));
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // SetUp: Add Car 1 with daily price: 30 CNY, Car 2 with daily price: 150 CNY, Car 3 with daily price: 120 CNY
        store.addCar(new Car("CAR001", "Model1", 30.0));
        store.addCar(new Car("CAR002", "Model2", 150.0));
        store.addCar(new Car("CAR003", "Model3", 120.0));
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // SetUp: Add three cars with daily price: 60 CNY each
        store.addCar(new Car("CAR001", "Model1", 60.0));
        store.addCar(new Car("CAR002", "Model2", 60.0));
        store.addCar(new Car("CAR003", "Model3", 60.0));
        
        // Calculate average daily price
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.0);
    }
}