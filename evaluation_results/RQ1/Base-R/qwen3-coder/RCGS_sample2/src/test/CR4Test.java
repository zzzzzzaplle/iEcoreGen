import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR4Test {
    
    private RentalStore store;
    private Car car1, car2, car3;
    
    @Before
    public void setUp() {
        // Initialize a new store before each test
        store = new RentalStore();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // SetUp: Add Car 1 with daily price: 50 CNY, Car 2 with daily price: 70 CNY, Car 3 with daily price: 80 CNY
        car1 = new Car();
        car1.setDailyPrice(50.0);
        
        car2 = new Car();
        car2.setDailyPrice(70.0);
        
        car3 = new Car();
        car3.setDailyPrice(80.0);
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Calculate average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        double result = store.getAverageCarPrice();
        assertEquals(66.67, result, 0.01); // Allowing delta for floating point precision
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // SetUp: Store is empty (no cars added)
        
        // Calculate average daily price = 0 CNY (no cars available)
        double result = store.getAverageCarPrice();
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // SetUp: Add Car 1 with daily price: 100 CNY
        car1 = new Car();
        car1.setDailyPrice(100.0);
        
        store.getCars().add(car1);
        
        // Calculate average daily price = 100 CNY (only one car available)
        double result = store.getAverageCarPrice();
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // SetUp: Add Car 1 with daily price: 30 CNY, Car 2 with daily price: 150 CNY, Car 3 with daily price: 120 CNY
        car1 = new Car();
        car1.setDailyPrice(30.0);
        
        car2 = new Car();
        car2.setDailyPrice(150.0);
        
        car3 = new Car();
        car3.setDailyPrice(120.0);
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Calculate average daily price = (30 + 150 + 120) / 3 = 100 CNY
        double result = store.getAverageCarPrice();
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // SetUp: Add Car 1 with daily price: 60 CNY, Car 2 with daily price: 60 CNY, Car 3 with daily price: 60 CNY
        car1 = new Car();
        car1.setDailyPrice(60.0);
        
        car2 = new Car();
        car2.setDailyPrice(60.0);
        
        car3 = new Car();
        car3.setDailyPrice(60.0);
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Calculate average daily price = (60 + 60 + 60) / 3 = 60 CNY
        double result = store.getAverageCarPrice();
        assertEquals(60.0, result, 0.0);
    }
}