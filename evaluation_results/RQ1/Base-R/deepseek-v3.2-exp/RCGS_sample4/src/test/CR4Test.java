import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private CarRentalSystem store;
    
    @Before
    public void setUp() {
        // Reset the store before each test
        store = new CarRentalSystem();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // SetUp: Add Car 1 with daily price: 50 CNY, Car 2 with daily price: 70 CNY, Car 3 with daily price: 80 CNY
        
        // Create and add cars with specified daily prices
        Car car1 = new Car();
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(70.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(80.0);
        store.addCar(car3);
        
        // Calculate average daily price: (50 + 70 + 80) / 3 = 66.67 CNY
        double expectedAverage = (50.0 + 70.0 + 80.0) / 3;
        double actualAverage = calculateAverageDailyPrice(store);
        
        // Verify the result with delta for floating point precision
        assertEquals("Average daily price should be 66.67 CNY", expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // SetUp: Store is empty (no cars added)
        
        // Calculate average daily price for empty store: should return 0.0
        double expectedAverage = 0.0;
        double actualAverage = calculateAverageDailyPrice(store);
        
        // Verify the result
        assertEquals("Average daily price should be 0.0 CNY for empty store", expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // SetUp: Add Car 1 with daily price: 100 CNY
        
        // Create and add car with specified daily price
        Car car1 = new Car();
        car1.setDailyPrice(100.0);
        store.addCar(car1);
        
        // Calculate average daily price: 100 / 1 = 100 CNY
        double expectedAverage = 100.0;
        double actualAverage = calculateAverageDailyPrice(store);
        
        // Verify the result
        assertEquals("Average daily price should be 100.0 CNY for single car", expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // SetUp: Add Car 1 with daily price: 30 CNY, Car 2 with daily price: 150 CNY, Car 3 with daily price: 120 CNY
        
        // Create and add cars with specified daily prices
        Car car1 = new Car();
        car1.setDailyPrice(30.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(150.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(120.0);
        store.addCar(car3);
        
        // Calculate average daily price: (30 + 150 + 120) / 3 = 100 CNY
        double expectedAverage = (30.0 + 150.0 + 120.0) / 3;
        double actualAverage = calculateAverageDailyPrice(store);
        
        // Verify the result
        assertEquals("Average daily price should be 100.0 CNY for cars with varying prices", expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // SetUp: Add Car 1 with daily price: 60 CNY, Car 2 with daily price: 60 CNY, Car 3 with daily price: 60 CNY
        
        // Create and add cars with identical daily prices
        Car car1 = new Car();
        car1.setDailyPrice(60.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setDailyPrice(60.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setDailyPrice(60.0);
        store.addCar(car3);
        
        // Calculate average daily price: (60 + 60 + 60) / 3 = 60 CNY
        double expectedAverage = 60.0;
        double actualAverage = calculateAverageDailyPrice(store);
        
        // Verify the result
        assertEquals("Average daily price should be 60.0 CNY for cars with duplicate prices", expectedAverage, actualAverage, 0.0);
    }
    
    /**
     * Helper method to calculate the average daily price of cars in the store
     * Sums the daily prices of all cars and divides by the total number of cars
     * Returns 0.0 if the store has no cars
     */
    private double calculateAverageDailyPrice(CarRentalSystem store) {
        List<Car> cars = store.getCars();
        if (cars.isEmpty()) {
            return 0.0;
        }
        
        double totalPrice = 0.0;
        for (Car car : cars) {
            totalPrice += car.getDailyPrice();
        }
        
        return totalPrice / cars.size();
    }
}