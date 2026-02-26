import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Add Car 1 with daily price: 50 CNY, Car 2 with daily price: 70 CNY, Car 3 with daily price: 80 CNY
        store.addCar(new Car("PLATE1", "Model1", 50.0));
        store.addCar(new Car("PLATE2", "Model2", 70.0));
        store.addCar(new Car("PLATE3", "Model3", 80.0));
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Empty store (no cars added)
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Add Car 1 with daily price: 100 CNY
        store.addCar(new Car("PLATE1", "Model1", 100.0));
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Add Car 1 with daily price: 30 CNY, Car 2 with daily price: 150 CNY, Car 3 with daily price: 120 CNY
        store.addCar(new Car("PLATE1", "Model1", 30.0));
        store.addCar(new Car("PLATE2", "Model2", 150.0));
        store.addCar(new Car("PLATE3", "Model3", 120.0));
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Add Car 1 with daily price: 60 CNY, Car 2 with daily price: 60 CNY, Car 3 with daily price: 60 CNY
        store.addCar(new Car("PLATE1", "Model1", 60.0));
        store.addCar(new Car("PLATE2", "Model2", 60.0));
        store.addCar(new Car("PLATE3", "Model3", 60.0));
        
        // Calculate average daily price
        double result = store.getAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.0);
    }
}