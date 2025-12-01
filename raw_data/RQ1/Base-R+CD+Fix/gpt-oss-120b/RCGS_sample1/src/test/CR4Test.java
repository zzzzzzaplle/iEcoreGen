import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        // Create a fresh Store instance before each test
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // SetUp: 
        // 1. Create a Store instance.
        // 2. Add Car 1 with daily price: 50 CNY.
        // 3. Add Car 2 with daily price: 70 CNY.
        // 4. Add Car 3 with daily price: 80 CNY.
        
        Car car1 = new Car("ABC123", "Toyota", 50.0);
        Car car2 = new Car("DEF456", "Honda", 70.0);
        Car car3 = new Car("GHI789", "BMW", 80.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        double expectedAverage = (50.0 + 70.0 + 80.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals(66.67, actualAverage, 0.01); // Delta of 0.01 for floating point precision
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // SetUp: 
        // 1. Create a Store instance.
        // Expected Output: Average daily price = 0 CNY (no cars available)
        
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals(0.0, actualAverage, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // SetUp: 
        // 1. Create a Store instance.
        // 2. Add Car 1 with daily price: 100 CNY.
        
        Car car1 = new Car("JKL012", "Mercedes", 100.0);
        store.addCar(car1);
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals(100.0, actualAverage, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // SetUp: 
        // 1. Create a Store instance.
        // 2. Add Car 1 with daily price: 30 CNY.
        // 3. Add Car 2 with daily price: 150 CNY.
        // 4. Add Car 3 with daily price: 120 CNY.
        
        Car car1 = new Car("MNO345", "Ford", 30.0);
        Car car2 = new Car("PQR678", "Audi", 150.0);
        Car car3 = new Car("STU901", "Lexus", 120.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals(100.0, actualAverage, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // SetUp: 
        // 1. Create a Store instance.
        // 2. Add Car 1 with daily price: 60 CNY.
        // 3. Add Car 2 with daily price: 60 CNY.
        // 4. Add Car 3 with daily price: 60 CNY.
        
        Car car1 = new Car("VWX234", "Nissan", 60.0);
        Car car2 = new Car("YZA567", "Hyundai", 60.0);
        Car car3 = new Car("BCD890", "Kia", 60.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals(60.0, actualAverage, 0.0);
    }
}