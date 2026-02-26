import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR4Test {
    
    private Store store;
    private Car car1, car2, car3;
    
    @Before
    public void setUp() {
        // Initialize a fresh Store instance before each test
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Create cars with different daily prices
        car1 = new Car();
        car1.setDailyPrice(50.0);
        
        car2 = new Car();
        car2.setDailyPrice(70.0);
        
        car3 = new Car();
        car3.setDailyPrice(80.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01); // Using delta for floating point comparison
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Store with no cars (empty list)
        store.setCars(new ArrayList<Car>());
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: 0.0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Create one car with daily price 100 CNY
        car1 = new Car();
        car1.setDailyPrice(100.0);
        
        // Add car to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: 100.0 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithVaryingPrices() {
        // SetUp: Create cars with varying daily prices
        car1 = new Car();
        car1.setDailyPrice(30.0);
        
        car2 = new Car();
        car2.setDailyPrice(150.0);
        
        car3 = new Car();
        car3.setDailyPrice(120.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: (30 + 150 + 120) / 3 = 100.0 CNY
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Create cars with identical daily prices
        car1 = new Car();
        car1.setDailyPrice(60.0);
        
        car2 = new Car();
        car2.setDailyPrice(60.0);
        
        car3 = new Car();
        car3.setDailyPrice(60.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: (60 + 60 + 60) / 3 = 60.0 CNY
        assertEquals(60.0, result, 0.0);
    }
}