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
    public void testCase1_averagePriceCalculationWithMultipleCars() {
        // Create cars with specified daily prices
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setDailyPrice(70.0);
        
        Car car3 = new Car();
        car3.setPlate("CAR003");
        car3.setDailyPrice(80.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Calculate average daily price
        double averagePrice = store.determineAverageDailyPrice();
        
        // Expected: (50 + 70 + 80) / 3 = 66.67
        assertEquals(66.67, averagePrice, 0.01);
    }
    
    @Test
    public void testCase2_averagePriceCalculationWithNoCars() {
        // Store is already initialized with empty cars list in setUp()
        
        // Calculate average daily price
        double averagePrice = store.determineAverageDailyPrice();
        
        // Expected: 0.0 (no cars available)
        assertEquals(0.0, averagePrice, 0.01);
    }
    
    @Test
    public void testCase3_averagePriceCalculationWithOneCar() {
        // Create one car with specified daily price
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setDailyPrice(100.0);
        
        // Add car to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        store.setCars(cars);
        
        // Calculate average daily price
        double averagePrice = store.determineAverageDailyPrice();
        
        // Expected: 100.0 (only one car available)
        assertEquals(100.0, averagePrice, 0.01);
    }
    
    @Test
    public void testCase4_averagePriceCalculationWithCarsHavingVaryingPrices() {
        // Create cars with specified daily prices
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setDailyPrice(30.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setDailyPrice(150.0);
        
        Car car3 = new Car();
        car3.setPlate("CAR003");
        car3.setDailyPrice(120.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Calculate average daily price
        double averagePrice = store.determineAverageDailyPrice();
        
        // Expected: (30 + 150 + 120) / 3 = 100.0
        assertEquals(100.0, averagePrice, 0.01);
    }
    
    @Test
    public void testCase5_averagePriceCalculationWithDuplicatePriceCars() {
        // Create cars with same daily price
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setDailyPrice(60.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("CAR003");
        car3.setDailyPrice(60.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Calculate average daily price
        double averagePrice = store.determineAverageDailyPrice();
        
        // Expected: (60 + 60 + 60) / 3 = 60.0
        assertEquals(60.0, averagePrice, 0.01);
    }
}