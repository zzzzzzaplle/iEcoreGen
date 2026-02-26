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
        // Initialize a fresh Store instance before each test
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // Test Case 1: Average Price Calculation with Multiple Cars
        // SetUp: Add 3 cars with different daily prices
        car1 = new Car();
        car1.setPlate("ABC123");
        car1.setDailyPrice(50.0);
        
        car2 = new Car();
        car2.setPlate("DEF456");
        car2.setDailyPrice(70.0);
        
        car3 = new Car();
        car3.setPlate("GHI789");
        car3.setDailyPrice(80.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Calculate average and verify result
        double expectedAverage = (50.0 + 70.0 + 80.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        // Use delta for floating point comparison
        assertEquals("Average daily price should be 66.67 for 3 cars", 
                     expectedAverage, actualAverage, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // Test Case 2: Average Price Calculation with No Cars
        // SetUp: Store is empty (no cars added)
        
        // Calculate average and verify result
        double expectedAverage = 0.0;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 0.0 for empty store", 
                     expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // Test Case 3: Average Price Calculation with One Car
        // SetUp: Add 1 car with daily price 100 CNY
        car1 = new Car();
        car1.setPlate("JKL012");
        car1.setDailyPrice(100.0);
        
        store.addCar(car1);
        
        // Calculate average and verify result
        double expectedAverage = 100.0;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 100.0 for single car", 
                     expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithVaryingPrices() {
        // Test Case 4: Average Price Calculation with Cars Having Varying Prices
        // SetUp: Add 3 cars with varying daily prices
        car1 = new Car();
        car1.setPlate("MNO345");
        car1.setDailyPrice(30.0);
        
        car2 = new Car();
        car2.setPlate("PQR678");
        car2.setDailyPrice(150.0);
        
        car3 = new Car();
        car3.setPlate("STU901");
        car3.setDailyPrice(120.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Calculate average and verify result
        double expectedAverage = (30.0 + 150.0 + 120.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 100.0 for cars with varying prices", 
                     expectedAverage, actualAverage, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // Test Case 5: Average Price Calculation with Duplicate Price Cars
        // SetUp: Add 3 cars with same daily price
        car1 = new Car();
        car1.setPlate("VWX234");
        car1.setDailyPrice(60.0);
        
        car2 = new Car();
        car2.setPlate("YZA567");
        car2.setDailyPrice(60.0);
        
        car3 = new Car();
        car3.setPlate("BCD890");
        car3.setDailyPrice(60.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Calculate average and verify result
        double expectedAverage = (60.0 + 60.0 + 60.0) / 3;
        double actualAverage = store.determineAverageDailyPrice();
        
        assertEquals("Average daily price should be 60.0 for cars with duplicate prices", 
                     expectedAverage, actualAverage, 0.0);
    }
}