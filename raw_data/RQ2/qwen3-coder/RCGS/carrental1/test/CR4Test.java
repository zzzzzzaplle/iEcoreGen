package edu.carrental.carrental1.test;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import edu.carrental.CarrentalFactory;
import edu.carrental.CarrentalPackage;
import edu.carrental.Store;
import edu.carrental.Car;

public class CR4Test {
    
    private Store store;
    private CarrentalFactory factory;
    
    @Before
    public void setUp() {
        // Initialize the factory and create a new store instance
        factory = CarrentalFactory.eINSTANCE;
        store = factory.createStore();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Add Car 1 with daily price: 50 CNY
        Car car1 = factory.createCar();
        car1.setDailyPrice(50.0);
        store.getCars().add(car1);
        
        // SetUp: Add Car 2 with daily price: 70 CNY
        Car car2 = factory.createCar();
        car2.setDailyPrice(70.0);
        store.getCars().add(car2);
        
        // SetUp: Add Car 3 with daily price: 80 CNY
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
        // SetUp: Store instance is already created with no cars
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
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
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Add Car 1 with daily price: 30 CNY
        Car car1 = factory.createCar();
        car1.setDailyPrice(30.0);
        store.getCars().add(car1);
        
        // SetUp: Add Car 2 with daily price: 150 CNY
        Car car2 = factory.createCar();
        car2.setDailyPrice(150.0);
        store.getCars().add(car2);
        
        // SetUp: Add Car 3 with daily price: 120 CNY
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
        // SetUp: Add Car 1 with daily price: 60 CNY
        Car car1 = factory.createCar();
        car1.setDailyPrice(60.0);
        store.getCars().add(car1);
        
        // SetUp: Add Car 2 with daily price: 60 CNY
        Car car2 = factory.createCar();
        car2.setDailyPrice(60.0);
        store.getCars().add(car2);
        
        // SetUp: Add Car 3 with daily price: 60 CNY
        Car car3 = factory.createCar();
        car3.setDailyPrice(60.0);
        store.getCars().add(car3);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.0);
    }
}