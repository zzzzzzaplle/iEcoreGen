import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        // Initialize a new Store instance before each test
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // SetUp: Add Car 1 with daily price: 50 CNY, Car 2 with daily price: 70 CNY, Car 3 with daily price: 80 CNY
        Car car1 = new Car("PLATE001", "Model1", 50.0);
        Car car2 = new Car("PLATE002", "Model2", 70.0);
        Car car3 = new Car("PLATE003", "Model3", 80.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        double expectedAverage = (50.0 + 70.0 + 80.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        // Use delta of 0.01 for double comparison
        assertEquals("Average daily price should be 66.67 for three cars", expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // SetUp: Store instance is already created with empty car list (via @Before)
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        double expectedAverage = 0.0;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 0.0 when no cars are available", expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // SetUp: Add Car 1 with daily price: 100 CNY
        Car car1 = new Car("PLATE001", "Model1", 100.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        store.setCars(cars);
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        double expectedAverage = 100.0;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 100.0 when only one car is available", expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // SetUp: Add Car 1 with daily price: 30 CNY, Car 2 with daily price: 150 CNY, Car 3 with daily price: 120 CNY
        Car car1 = new Car("PLATE001", "Model1", 30.0);
        Car car2 = new Car("PLATE002", "Model2", 150.0);
        Car car3 = new Car("PLATE003", "Model3", 120.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        double expectedAverage = (30.0 + 150.0 + 120.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 100.0 for cars with varying prices", expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // SetUp: Add Car 1 with daily price: 60 CNY, Car 2 with daily price: 60 CNY, Car 3 with daily price: 60 CNY
        Car car1 = new Car("PLATE001", "Model1", 60.0);
        Car car2 = new Car("PLATE002", "Model2", 60.0);
        Car car3 = new Car("PLATE003", "Model3", 60.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        double expectedAverage = (60.0 + 60.0 + 60.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 60.0 for cars with duplicate prices", expectedAverage, actualAverage, 0.0);
    }
}