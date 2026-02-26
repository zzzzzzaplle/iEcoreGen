import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    
    private RentalStore store;
    
    @Before
    public void setUp() {
        store = new RentalStore();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Create a Store instance and add three cars with different prices
        Car car1 = new Car();
        car1.setDailyPrice(50.0);
        store.getCars().add(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(70.0);
        store.getCars().add(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(80.0);
        store.getCars().add(car3);
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01); // Using delta for floating point comparison
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Create a Store instance (no cars added)
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Create a Store instance and add one car
        Car car1 = new Car();
        car1.setDailyPrice(100.0);
        store.getCars().add(car1);
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Create a Store instance and add three cars with varying prices
        Car car1 = new Car();
        car1.setDailyPrice(30.0);
        store.getCars().add(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(150.0);
        store.getCars().add(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(120.0);
        store.getCars().add(car3);
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Create a Store instance and add three cars with same price
        Car car1 = new Car();
        car1.setDailyPrice(60.0);
        store.getCars().add(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(60.0);
        store.getCars().add(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(60.0);
        store.getCars().add(car3);
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.0);
    }
}