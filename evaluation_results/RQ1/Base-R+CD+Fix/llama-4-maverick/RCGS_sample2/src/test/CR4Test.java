import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private Store store;
    
    @Before
    public void setUp() {
        // Initialize a fresh Store instance before each test
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Add three cars with different daily prices
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("ABC123", "Toyota Camry", 50.0));
        cars.add(new Car("DEF456", "Honda Civic", 70.0));
        cars.add(new Car("GHI789", "Ford Focus", 80.0));
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: (50 + 70 + 80) / 3 = 66.666...
        assertEquals(66.67, result, 0.01); // Using delta for double comparison
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Store is already empty from @Before
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: 0.0 when no cars are available
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Add one car
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("JKL012", "BMW X5", 100.0));
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: 100.0 (only one car available)
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Add three cars with varying prices
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("MNO345", "Kia Rio", 30.0));
        cars.add(new Car("PQR678", "Mercedes C-Class", 150.0));
        cars.add(new Car("STU901", "Audi A4", 120.0));
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: (30 + 150 + 120) / 3 = 100.0
        assertEquals(100.0, result, 0.001);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Add three cars with the same daily price
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("VWX234", "Hyundai Elantra", 60.0));
        cars.add(new Car("YZA567", "Nissan Sentra", 60.0));
        cars.add(new Car("BCD890", "Chevrolet Malibu", 60.0));
        store.setCars(cars);
        
        // Calculate average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected: (60 + 60 + 60) / 3 = 60.0
        assertEquals(60.0, result, 0.001);
    }
}