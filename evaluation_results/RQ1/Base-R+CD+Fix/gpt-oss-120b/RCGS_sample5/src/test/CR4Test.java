import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Create a Store instance and add three cars with different prices
        Car car1 = new Car();
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setDailyPrice(70.0);
        
        Car car3 = new Car();
        car3.setDailyPrice(80.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Execute: Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Verify: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Create a Store instance (no cars added)
        // Store is already created in setUp() method
        
        // Execute: Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Verify: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Create a Store instance and add one car
        Car car1 = new Car();
        car1.setDailyPrice(100.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        store.setCars(cars);
        
        // Execute: Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Verify: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithVaryingPrices() {
        // SetUp: Create a Store instance and add three cars with varying prices
        Car car1 = new Car();
        car1.setDailyPrice(30.0);
        
        Car car2 = new Car();
        car2.setDailyPrice(150.0);
        
        Car car3 = new Car();
        car3.setDailyPrice(120.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Execute: Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Verify: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Create a Store instance and add three cars with same price
        Car car1 = new Car();
        car1.setDailyPrice(60.0);
        
        Car car2 = new Car();
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setDailyPrice(60.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Execute: Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Verify: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.0);
    }
}