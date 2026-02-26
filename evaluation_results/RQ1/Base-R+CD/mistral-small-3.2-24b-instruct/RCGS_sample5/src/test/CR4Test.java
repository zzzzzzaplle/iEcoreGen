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
        // SetUp: Add Car 1 with daily price: 50 CNY
        car1.setDailyPrice(50.0);
        // SetUp: Add Car 2 with daily price: 70 CNY
        car2.setDailyPrice(70.0);
        // SetUp: Add Car 3 with daily price: 80 CNY
        car3.setDailyPrice(80.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Input: Calculate the average daily price of cars in the store
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01);
    }

    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Create a Store instance (already done in @Before)
        // Input: Calculate the average daily price of cars in an empty store
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }

    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Add Car 1 with daily price: 100 CNY
        car1.setDailyPrice(100.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        store.setCars(cars);
        
        // Input: Calculate the average daily price with only one car in the store
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }

    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Add Car 1 with daily price: 30 CNY
        car1.setDailyPrice(30.0);
        // SetUp: Add Car 2 with daily price: 150 CNY
        car2.setDailyPrice(150.0);
        // SetUp: Add Car 3 with daily price: 120 CNY
        car3.setDailyPrice(120.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Input: Calculate the average daily price of cars with different prices
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.0);
    }

    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Add Car 1 with daily price: 60 CNY
        car1.setDailyPrice(60.0);
        // SetUp: Add Car 2 with daily price: 60 CNY
        car2.setDailyPrice(60.0);
        // SetUp: Add Car 3 with daily price: 60 CNY
        car3.setDailyPrice(60.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Input: Calculate the average daily price of cars in the store when some cars have the same price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.0);
    }
}