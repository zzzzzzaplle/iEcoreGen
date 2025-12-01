import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Create a Store instance and add cars with prices 50, 70, 80
        store.getCars().add(new Car("ABC123", "Toyota", 50.0));
        store.getCars().add(new Car("DEF456", "Honda", 70.0));
        store.getCars().add(new Car("GHI789", "Ford", 80.0));
        
        // Input: Calculate the average daily price of cars in the store
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Create a Store instance (no cars added)
        
        // Input: Calculate the average daily price of cars in an empty store
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Create a Store instance and add one car with price 100
        store.getCars().add(new Car("JKL012", "BMW", 100.0));
        
        // Input: Calculate the average daily price with only one car in the store
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Create a Store instance and add cars with prices 30, 150, 120
        store.getCars().add(new Car("MNO345", "Nissan", 30.0));
        store.getCars().add(new Car("PQR678", "Mercedes", 150.0));
        store.getCars().add(new Car("STU901", "Audi", 120.0));
        
        // Input: Calculate the average daily price of cars with different prices
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Create a Store instance and add three cars with same price 60
        store.getCars().add(new Car("VWX234", "Toyota", 60.0));
        store.getCars().add(new Car("YZA567", "Honda", 60.0));
        store.getCars().add(new Car("BCD890", "Ford", 60.0));
        
        // Input: Calculate the average daily price of cars when some cars have the same price
        double result = store.calculateAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.0);
    }
}