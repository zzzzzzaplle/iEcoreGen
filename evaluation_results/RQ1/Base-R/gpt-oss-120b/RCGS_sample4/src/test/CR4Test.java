import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        // Initialize a fresh store before each test
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // SetUp: Add 3 cars with different daily prices
        Car car1 = new Car("ABC123", "Toyota", 50.0);
        Car car2 = new Car("DEF456", "Honda", 70.0);
        Car car3 = new Car("GHI789", "Ford", 80.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01); // Using delta for floating point comparison
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // SetUp: Store is empty (no cars added)
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: 0.0 CNY (no cars available)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // SetUp: Add one car with daily price 100 CNY
        Car car1 = new Car("JKL012", "BMW", 100.0);
        store.addCar(car1);
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: 100.0 CNY (only one car available)
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // SetUp: Add 3 cars with different daily prices
        Car car1 = new Car("MNO345", "Nissan", 30.0);
        Car car2 = new Car("PQR678", "Mercedes", 150.0);
        Car car3 = new Car("STU901", "Audi", 120.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: (30 + 150 + 120) / 3 = 100.0 CNY
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // SetUp: Add 3 cars with the same daily price
        Car car1 = new Car("VWX234", "Volkswagen", 60.0);
        Car car2 = new Car("YZA567", "Volkswagen", 60.0);
        Car car3 = new Car("BCD890", "Volkswagen", 60.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: (60 + 60 + 60) / 3 = 60.0 CNY
        assertEquals(60.0, result, 0.001);
    }
}