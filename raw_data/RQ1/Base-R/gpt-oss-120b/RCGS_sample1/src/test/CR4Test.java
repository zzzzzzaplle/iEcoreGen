import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR4Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Create a Store instance and add three cars with different prices
        store.addCar(new Car("ABC123", "Toyota", 50.0));
        store.addCar(new Car("DEF456", "Honda", 70.0));
        store.addCar(new Car("GHI789", "Ford", 80.0));
        
        // Input: Calculate the average daily price of cars in the store
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01); // Using delta for floating point comparison
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Create a Store instance (no cars added)
        
        // Input: Calculate the average daily price of cars in an empty store
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Create a Store instance and add one car
        store.addCar(new Car("JKL012", "BMW", 100.0));
        
        // Input: Calculate the average daily price with only one car in the store
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Create a Store instance and add three cars with varying prices
        store.addCar(new Car("MNO345", "Nissan", 30.0));
        store.addCar(new Car("PQR678", "Mercedes", 150.0));
        store.addCar(new Car("STU901", "Audi", 120.0));
        
        // Input: Calculate the average daily price of cars with different prices
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Create a Store instance and add three cars with same price
        store.addCar(new Car("VWX234", "Volkswagen", 60.0));
        store.addCar(new Car("YZA567", "Hyundai", 60.0));
        store.addCar(new Car("BCD890", "Kia", 60.0));
        
        // Input: Calculate the average daily price of cars when some cars have the same price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.001);
    }
}