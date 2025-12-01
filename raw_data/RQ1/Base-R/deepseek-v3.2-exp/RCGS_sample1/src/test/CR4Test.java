import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private CarRentalSystem system;
    
    @Before
    public void setUp() {
        // Initialize a new CarRentalSystem before each test
        system = new CarRentalSystem();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // Input: Calculate the average daily price of cars in the store.
        // SetUp: 
        // 1. Create a Store instance.
        // 2. Add Car 1 with daily price: 50 CNY.
        // 3. Add Car 2 with daily price: 70 CNY.
        // 4. Add Car 3 with daily price: 80 CNY.
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        
        // Create and add cars with specified daily prices
        Car car1 = new Car();
        car1.setDailyPrice(50.0);
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(70.0);
        system.addCar(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(80.0);
        system.addCar(car3);
        
        // Calculate average daily price
        double result = system.calculateAverageDailyPrice();
        
        // Verify the result with delta for floating point comparison
        assertEquals(66.67, result, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // Input: Calculate the average daily price of cars in an empty store.
        // SetUp: 
        // 1. Create a Store instance.
        // Expected Output: Average daily price = 0 CNY (no cars available)
        
        // Calculate average daily price on empty system
        double result = system.calculateAverageDailyPrice();
        
        // Verify the result is 0.0
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // Input: Calculate the average daily price with only one car in the store.
        // SetUp: 
        // 1. Create a Store instance.
        // 2. Add Car 1 with daily price: 100 CNY.
        // Expected Output: Average daily price = 100 CNY (only one car available)
        
        // Create and add one car with specified daily price
        Car car1 = new Car();
        car1.setDailyPrice(100.0);
        system.addCar(car1);
        
        // Calculate average daily price
        double result = system.calculateAverageDailyPrice();
        
        // Verify the result
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // Input: Calculate the average daily price of cars with different prices.
        // SetUp: 
        // 1. Create a Store instance.
        // 2. Add Car 1 with daily price: 30 CNY.
        // 3. Add Car 2 with daily price: 150 CNY.
        // 4. Add Car 3 with daily price: 120 CNY.
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        
        // Create and add cars with specified daily prices
        Car car1 = new Car();
        car1.setDailyPrice(30.0);
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(150.0);
        system.addCar(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(120.0);
        system.addCar(car3);
        
        // Calculate average daily price
        double result = system.calculateAverageDailyPrice();
        
        // Verify the result
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // Input: Calculate the average daily price of cars in the store when some cars have the same price.
        // SetUp: 
        // 1. Create a Store instance.
        // 2. Add Car 1 with daily price: 60 CNY.
        // 3. Add Car 2 with daily price: 60 CNY.
        // 4. Add Car 3 with daily price: 60 CNY.
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        
        // Create and add cars with identical daily prices
        Car car1 = new Car();
        car1.setDailyPrice(60.0);
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(60.0);
        system.addCar(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(60.0);
        system.addCar(car3);
        
        // Calculate average daily price
        double result = system.calculateAverageDailyPrice();
        
        // Verify the result
        assertEquals(60.0, result, 0.0);
    }
}