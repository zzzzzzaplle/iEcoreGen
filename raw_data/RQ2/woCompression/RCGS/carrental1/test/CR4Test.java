package edu.carrental.carrental1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.carrental.CarrentalFactory;
import edu.carrental.CarrentalPackage;
import edu.carrental.Store;
import edu.carrental.Car;

public class CR4Test {
    
    private CarrentalFactory factory;
    private Store store;
    
    @Before
    public void setUp() {
        // Initialize the Ecore factory and create a new store instance
        factory = CarrentalFactory.eINSTANCE;
        store = factory.createStore();
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
        
        // Create cars using Ecore factory
        Car car1 = factory.createCar();
        car1.setDailyPrice(50.0);
        
        Car car2 = factory.createCar();
        car2.setDailyPrice(70.0);
        
        Car car3 = factory.createCar();
        car3.setDailyPrice(80.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Verify result with delta for floating point comparison
        assertEquals(66.67, result, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // Input: Calculate the average daily price of cars in an empty store.
        // SetUp: 
        // 1. Create a Store instance.
        // Expected Output: Average daily price = 0 CNY (no cars available)
        
        // Store is already empty from setUp()
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Verify result
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
        
        // Create car using Ecore factory
        Car car1 = factory.createCar();
        car1.setDailyPrice(100.0);
        
        // Add car to store
        store.getCars().add(car1);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Verify result
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
        
        // Create cars using Ecore factory
        Car car1 = factory.createCar();
        car1.setDailyPrice(30.0);
        
        Car car2 = factory.createCar();
        car2.setDailyPrice(150.0);
        
        Car car3 = factory.createCar();
        car3.setDailyPrice(120.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Verify result
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
        
        // Create cars using Ecore factory
        Car car1 = factory.createCar();
        car1.setDailyPrice(60.0);
        
        Car car2 = factory.createCar();
        car2.setDailyPrice(60.0);
        
        Car car3 = factory.createCar();
        car3.setDailyPrice(60.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Verify result
        assertEquals(60.0, result, 0.0);
    }
}