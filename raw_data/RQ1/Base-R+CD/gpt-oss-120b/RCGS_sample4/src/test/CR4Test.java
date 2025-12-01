import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Create a Store instance and add three cars with different prices
        Car car1 = new Car("ABC123", "Toyota", 50.0);
        Car car2 = new Car("DEF456", "Honda", 70.0);
        Car car3 = new Car("GHI789", "BMW", 80.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01); // Using delta for floating point comparison
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Create a Store instance (no cars added)
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Create a Store instance and add one car
        Car car1 = new Car("JKL012", "Mercedes", 100.0);
        store.addCar(car1);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Create a Store instance and add three cars with varying prices
        Car car1 = new Car("MNO345", "Kia", 30.0);
        Car car2 = new Car("PQR678", "Audi", 150.0);
        Car car3 = new Car("STU901", "Lexus", 120.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Create a Store instance and add three cars with same price
        Car car1 = new Car("VWX234", "Ford", 60.0);
        Car car2 = new Car("YZA567", "Chevrolet", 60.0);
        Car car3 = new Car("BCD890", "Nissan", 60.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.0);
    }
}