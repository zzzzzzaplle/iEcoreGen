import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Create a Store instance and add three cars with different prices
        Car car1 = new Car("ABC123", "Toyota", 50.0);
        Car car2 = new Car("DEF456", "Honda", 70.0);
        Car car3 = new Car("GHI789", "Ford", 80.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Input: Calculate the average daily price of cars in the store
        double result = store.getAverageDailyCarPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        double expected = (50.0 + 70.0 + 80.0) / 3.0;
        assertEquals(66.67, result, 0.01); // Using delta of 0.01 for floating point comparison
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Create a Store instance (no cars added)
        // Input: Calculate the average daily price of cars in an empty store
        double result = store.getAverageDailyCarPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Create a Store instance and add one car
        Car car1 = new Car("ABC123", "Toyota", 100.0);
        store.addCar(car1);
        
        // Input: Calculate the average daily price with only one car in the store
        double result = store.getAverageDailyCarPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Create a Store instance and add three cars with varying prices
        Car car1 = new Car("ABC123", "Toyota", 30.0);
        Car car2 = new Car("DEF456", "Honda", 150.0);
        Car car3 = new Car("GHI789", "Ford", 120.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Input: Calculate the average daily price of cars with different prices
        double result = store.getAverageDailyCarPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        double expected = (30.0 + 150.0 + 120.0) / 3.0;
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Create a Store instance and add three cars with the same price
        Car car1 = new Car("ABC123", "Toyota", 60.0);
        Car car2 = new Car("DEF456", "Honda", 60.0);
        Car car3 = new Car("GHI789", "Ford", 60.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Input: Calculate the average daily price when some cars have the same price
        double result = store.getAverageDailyCarPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.001);
    }
}