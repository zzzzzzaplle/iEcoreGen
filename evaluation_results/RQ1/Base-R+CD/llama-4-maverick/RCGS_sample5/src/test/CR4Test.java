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
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        double expectedAverage = (50.0 + 70.0 + 80.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals(expectedAverage, actualAverage, 0.01);
    }

    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Store instance is already created with empty car list
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        double expectedAverage = 0.0;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals(expectedAverage, actualAverage, 0.0);
    }

    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Add Car 1 with daily price: 100 CNY
        car1.setDailyPrice(100.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        store.setCars(cars);
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        double expectedAverage = 100.0;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals(expectedAverage, actualAverage, 0.0);
    }

    @Test
    public void testCase4_AveragePriceCalculationWithVaryingPrices() {
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
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        double expectedAverage = (30.0 + 150.0 + 120.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals(expectedAverage, actualAverage, 0.0);
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
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        double expectedAverage = (60.0 + 60.0 + 60.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals(expectedAverage, actualAverage, 0.0);
    }
}