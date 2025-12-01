import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Add Car 1 with daily price: 50 CNY
        Car car1 = new Car();
        car1.setPlate("PLATE001");
        car1.setDailyPrice(50.0);
        
        // SetUp: Add Car 2 with daily price: 70 CNY
        Car car2 = new Car();
        car2.setPlate("PLATE002");
        car2.setDailyPrice(70.0);
        
        // SetUp: Add Car 3 with daily price: 80 CNY
        Car car3 = new Car();
        car3.setPlate("PLATE003");
        car3.setDailyPrice(80.0);
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Input: Calculate the average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(200.0/3, result, 0.01); // 66.666... rounded to 2 decimal places
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Store instance is already created with empty car list (by @Before)
        
        // Input: Calculate the average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Add Car 1 with daily price: 100 CNY
        Car car1 = new Car();
        car1.setPlate("PLATE001");
        car1.setDailyPrice(100.0);
        
        store.getCars().add(car1);
        
        // Input: Calculate the average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Add Car 1 with daily price: 30 CNY
        Car car1 = new Car();
        car1.setPlate("PLATE001");
        car1.setDailyPrice(30.0);
        
        // SetUp: Add Car 2 with daily price: 150 CNY
        Car car2 = new Car();
        car2.setPlate("PLATE002");
        car2.setDailyPrice(150.0);
        
        // SetUp: Add Car 3 with daily price: 120 CNY
        Car car3 = new Car();
        car3.setPlate("PLATE003");
        car3.setDailyPrice(120.0);
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Input: Calculate the average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Add Car 1 with daily price: 60 CNY
        Car car1 = new Car();
        car1.setPlate("PLATE001");
        car1.setDailyPrice(60.0);
        
        // SetUp: Add Car 2 with daily price: 60 CNY
        Car car2 = new Car();
        car2.setPlate("PLATE002");
        car2.setDailyPrice(60.0);
        
        // SetUp: Add Car 3 with daily price: 60 CNY
        Car car3 = new Car();
        car3.setPlate("PLATE003");
        car3.setDailyPrice(60.0);
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Input: Calculate the average daily price
        double result = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.0);
    }
}