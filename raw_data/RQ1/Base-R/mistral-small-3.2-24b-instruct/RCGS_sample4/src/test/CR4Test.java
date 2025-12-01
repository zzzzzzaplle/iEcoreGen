import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR4Test {
    private CarGallery carGallery;
    
    @Before
    public void setUp() {
        carGallery = new CarGallery();
    }
    
    @Test
    public void testCase1_AveragePriceCalculationWithMultipleCars() {
        // SetUp: Create a Store instance and add three cars with different prices
        carGallery.addCar(new Car("ABC123", "Toyota", 50.0));
        carGallery.addCar(new Car("DEF456", "Honda", 70.0));
        carGallery.addCar(new Car("GHI789", "BMW", 80.0));
        
        // Calculate average daily price
        double result = carGallery.getAverageDailyPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals(66.67, result, 0.01);
    }
    
    @Test
    public void testCase2_AveragePriceCalculationWithNoCars() {
        // SetUp: Create a Store instance (no cars added)
        // Calculate average daily price
        double result = carGallery.getAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals(0.0, result, 0.0);
    }
    
    @Test
    public void testCase3_AveragePriceCalculationWithOneCar() {
        // SetUp: Create a Store instance and add one car
        carGallery.addCar(new Car("JKL012", "Mercedes", 100.0));
        
        // Calculate average daily price
        double result = carGallery.getAverageDailyPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase4_AveragePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Create a Store instance and add three cars with different prices
        carGallery.addCar(new Car("MNO345", "Nissan", 30.0));
        carGallery.addCar(new Car("PQR678", "Audi", 150.0));
        carGallery.addCar(new Car("STU901", "Lexus", 120.0));
        
        // Calculate average daily price
        double result = carGallery.getAverageDailyPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals(100.0, result, 0.0);
    }
    
    @Test
    public void testCase5_AveragePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Create a Store instance and add three cars with same price
        carGallery.addCar(new Car("VWX234", "Ford", 60.0));
        carGallery.addCar(new Car("YZA567", "Chevrolet", 60.0));
        carGallery.addCar(new Car("BCD890", "Hyundai", 60.0));
        
        // Calculate average daily price
        double result = carGallery.getAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals(60.0, result, 0.0);
    }
}