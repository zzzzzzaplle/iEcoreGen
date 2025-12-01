// IMPORTANT: Do not include package declaration
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR4Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_averagePriceCalculationWithMultipleCars() {
        // SetUp: Create a Store instance and add 3 cars with different prices
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Sedan");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("SUV");
        car2.setDailyPrice(70.0);
        
        Car car3 = new Car();
        car3.setPlate("CAR003");
        car3.setModel("Hatchback");
        car3.setDailyPrice(80.0);
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Input: Calculate the average daily price of cars in the store
        double averagePrice = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (50 + 70 + 80) / 3 = 66.67 CNY
        assertEquals("Average price should be 66.67", 66.67, averagePrice, 0.01);
    }
    
    @Test
    public void testCase2_averagePriceCalculationWithNoCars() {
        // SetUp: Create a Store instance with no cars
        // No cars added to the store
        
        // Input: Calculate the average daily price of cars in an empty store
        double averagePrice = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 0 CNY (no cars available)
        assertEquals("Average price should be 0.0 for empty store", 0.0, averagePrice, 0.0);
    }
    
    @Test
    public void testCase3_averagePriceCalculationWithOneCar() {
        // SetUp: Create a Store instance and add 1 car
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Luxury");
        car1.setDailyPrice(100.0);
        
        store.getCars().add(car1);
        
        // Input: Calculate the average daily price with only one car in the store
        double averagePrice = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = 100 CNY (only one car available)
        assertEquals("Average price should be 100.0 for single car", 100.0, averagePrice, 0.0);
    }
    
    @Test
    public void testCase4_averagePriceCalculationWithCarsHavingVaryingPrices() {
        // SetUp: Create a Store instance and add 3 cars with varying prices
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Economy");
        car1.setDailyPrice(30.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Luxury");
        car2.setDailyPrice(150.0);
        
        Car car3 = new Car();
        car3.setPlate("CAR003");
        car3.setModel("Premium");
        car3.setDailyPrice(120.0);
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Input: Calculate the average daily price of cars with different prices
        double averagePrice = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (30 + 150 + 120) / 3 = 100 CNY
        assertEquals("Average price should be 100.0", 100.0, averagePrice, 0.0);
    }
    
    @Test
    public void testCase5_averagePriceCalculationWithDuplicatePriceCars() {
        // SetUp: Create a Store instance and add 3 cars with the same price
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Model A");
        car1.setDailyPrice(60.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Model B");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("CAR003");
        car3.setModel("Model C");
        car3.setDailyPrice(60.0);
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Input: Calculate the average daily price of cars in the store when some cars have the same price
        double averagePrice = store.determineAverageDailyPrice();
        
        // Expected Output: Average daily price = (60 + 60 + 60) / 3 = 60 CNY
        assertEquals("Average price should be 60.0", 60.0, averagePrice, 0.0);
    }
}