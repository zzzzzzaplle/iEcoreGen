import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Store store;
    private Car car1;
    private Car car2;
    private Car car3;
    
    @Before
    public void setUp() {
        store = new Store();
        car1 = new Car();
        car2 = new Car();
        car3 = new Car();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Create a Store instance and add three cars with different daily prices
        List<Car> cars = new ArrayList<>();
        
        // Add Car 1 with daily price: 50 CNY
        car1.setDailyPrice(50.0);
        cars.add(car1);
        
        // Add Car 2 with daily price: 70 CNY
        car2.setDailyPrice(70.0);
        cars.add(car2);
        
        // Add Car 3 with daily price: 80 CNY
        car3.setDailyPrice(80.0);
        cars.add(car3);
        
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01); // Using delta for floating point comparison
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Create a Store instance with no cars
        store.setCars(new ArrayList<>());
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Create a Store instance and add one car
        List<Car> cars = new ArrayList<>();
        
        // Add Car 1 with daily price: 100 CNY
        car1.setDailyPrice(100.0);
        cars.add(car1);
        
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Create a Store instance and add three cars with varying daily prices
        List<Car> cars = new ArrayList<>();
        
        // Add Car 1 with daily price: 30 CNY
        car1.setDailyPrice(30.0);
        cars.add(car1);
        
        // Add Car 2 with daily price: 150 CNY
        car2.setDailyPrice(150.0);
        cars.add(car2);
        
        // Add Car 3 with daily price: 120 CNY
        car3.setDailyPrice(120.0);
        cars.add(car3);
        
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Create a Store instance and add three cars with the same daily price
        List<Car> cars = new ArrayList<>();
        
        // Add Car 1 with daily price: 60 CNY
        car1.setDailyPrice(60.0);
        cars.add(car1);
        
        // Add Car 2 with daily price: 60 CNY
        car2.setDailyPrice(60.0);
        cars.add(car2);
        
        // Add Car 3 with daily price: 60 CNY
        car3.setDailyPrice(60.0);
        cars.add(car3);
        
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.001);
    }
}