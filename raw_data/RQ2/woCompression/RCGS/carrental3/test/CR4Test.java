package edu.carrental.carrental3.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.carrental.CarrentalFactory;
import edu.carrental.Store;
import edu.carrental.Car;

public class CR4Test {
    
    private CarrentalFactory factory;
    private Store store;
    
    @Before
    public void setUp() {
        // Initialize the factory and store before each test
        factory = CarrentalFactory.eINSTANCE;
        store = factory.createStore();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // SetUp: Add Car 1 with daily price: 50 CNY, Car 2 with daily price: 70 CNY, Car 3 with daily price: 80 CNY
        Car car1 = factory.createCar();
        car1.setDailyPrice(50.0);
        store.getCars().add(car1);
        
        Car car2 = factory.createCar();
        car2.setDailyPrice(70.0);
        store.getCars().add(car2);
        
        Car car3 = factory.createCar();
        car3.setDailyPrice(80.0);
        store.getCars().add(car3);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01); // Using delta for floating point comparison
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // SetUp: Store instance with no cars (already set up in @Before)
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // SetUp: Add Car 1 with daily price: 100 CNY
        Car car1 = factory.createCar();
        car1.setDailyPrice(100.0);
        store.getCars().add(car1);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // SetUp: Add Car 1 with daily price: 30 CNY, Car 2 with daily price: 150 CNY, Car 3 with daily price: 120 CNY
        Car car1 = factory.createCar();
        car1.setDailyPrice(30.0);
        store.getCars().add(car1);
        
        Car car2 = factory.createCar();
        car2.setDailyPrice(150.0);
        store.getCars().add(car2);
        
        Car car3 = factory.createCar();
        car3.setDailyPrice(120.0);
        store.getCars().add(car3);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // SetUp: Add Car 1 with daily price: 60 CNY, Car 2 with daily price: 60 CNY, Car 3 with daily price: 60 CNY
        Car car1 = factory.createCar();
        car1.setDailyPrice(60.0);
        store.getCars().add(car1);
        
        Car car2 = factory.createCar();
        car2.setDailyPrice(60.0);
        store.getCars().add(car2);
        
        Car car3 = factory.createCar();
        car3.setDailyPrice(60.0);
        store.getCars().add(car3);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.0);
    }
}