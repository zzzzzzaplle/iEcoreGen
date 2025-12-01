import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Store store;
    private Car car;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // SetUp: Create a Store instance and add 3 cars with different prices
        car = new Car();
        car.setDailyPrice(50.0);
        store.getCars().add(car);
        
        car = new Car();
        car.setDailyPrice(70.0);
        store.getCars().add(car);
        
        car = new Car();
        car.setDailyPrice(80.0);
        store.getCars().add(car);
        
        // Calculate average and verify expected result
        double result = store.determineAverageDailyPrice();
        double expected = (50.0 + 70.0 + 80.0) / 3; // 66.666...
        assertEquals("Average daily price should be 66.67 CNY", 66.67, result, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // SetUp: Create a Store instance with no cars
        // No cars added to the store
        
        // Calculate average and verify expected result
        double result = store.determineAverageDailyPrice();
        assertEquals("Average daily price should be 0.0 CNY when no cars are available", 0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // SetUp: Create a Store instance and add one car
        car = new Car();
        car.setDailyPrice(100.0);
        store.getCars().add(car);
        
        // Calculate average and verify expected result
        double result = store.determineAverageDailyPrice();
        assertEquals("Average daily price should be 100.0 CNY when only one car is available", 100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // SetUp: Create a Store instance and add 3 cars with varying prices
        car = new Car();
        car.setDailyPrice(30.0);
        store.getCars().add(car);
        
        car = new Car();
        car.setDailyPrice(150.0);
        store.getCars().add(car);
        
        car = new Car();
        car.setDailyPrice(120.0);
        store.getCars().add(car);
        
        // Calculate average and verify expected result
        double result = store.determineAverageDailyPrice();
        double expected = (30.0 + 150.0 + 120.0) / 3; // 100.0
        assertEquals("Average daily price should be 100.0 CNY", 100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // SetUp: Create a Store instance and add 3 cars with same price
        car = new Car();
        car.setDailyPrice(60.0);
        store.getCars().add(car);
        
        car = new Car();
        car.setDailyPrice(60.0);
        store.getCars().add(car);
        
        car = new Car();
        car.setDailyPrice(60.0);
        store.getCars().add(car);
        
        // Calculate average and verify expected result
        double result = store.determineAverageDailyPrice();
        double expected = (60.0 + 60.0 + 60.0) / 3; // 60.0
        assertEquals("Average daily price should be 60.0 CNY", 60.0, result, 0.0);
    }
}