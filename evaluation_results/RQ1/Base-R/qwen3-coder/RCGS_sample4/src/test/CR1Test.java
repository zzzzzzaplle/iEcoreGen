import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    private RentalStore store;
    
    @Before
    public void setUp() {
        // Reset store before each test
        store = new RentalStore();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // Test Case 1: Single Available Car Check
        // SetUp: Create a Store instance named "City Car Rentals"
        store = new RentalStore(); // Already done in @Before, but explicit for clarity
        
        // Add cars with specified properties
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(500.0);
        car1.setRented(false); // available
        
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(600.0);
        car2.setRented(true); // rented
        
        Car car3 = new Car();
        car3.setPlate("DEF456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(450.0);
        car3.setRented(false); // available
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Execute method under test
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify expected output: Available cars list sorted by price = 
        // [{"plate": "DEF456", "model": "Ford Focus", "daily price": 450}, 
        //  {"plate": "ABC123", "model": "Toyota Camry", "daily price": 500}]
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Check first car (lowest price)
        assertEquals("First car should be DEF456", "DEF456", availableCars.get(0).getPlate());
        assertEquals("First car model should be Ford Focus", "Ford Focus", availableCars.get(0).getModel());
        assertEquals("First car price should be 450", 450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car
        assertEquals("Second car should be ABC123", "ABC123", availableCars.get(1).getPlate());
        assertEquals("Second car model should be Toyota Camry", "Toyota Camry", availableCars.get(1).getModel());
        assertEquals("Second car price should be 500", 500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // Test Case 2: All Cars Rented Check
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new RentalStore();
        
        // Add cars with specified properties (all rented)
        Car car1 = new Car();
        car1.setPlate("AAA111");
        car1.setModel("Nissan Altima");
        car1.setDailyPrice(600.0);
        car1.setRented(true); // rented
        
        Car car2 = new Car();
        car2.setPlate("BBB222");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(700.0);
        car2.setRented(true); // rented
        
        Car car3 = new Car();
        car3.setPlate("CCC333");
        car3.setModel("Kia Optima");
        car3.setDailyPrice(650.0);
        car3.setRented(true); // rented
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Execute method under test
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify expected output: Available cars list = []
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // Test Case 3: Multiple Cars with Different Rental Status
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new RentalStore();
        
        // Add cars with specified properties
        Car car1 = new Car();
        car1.setPlate("LMN456");
        car1.setModel("Porsche 911");
        car1.setDailyPrice(1500.0);
        car1.setRented(false); // available
        
        Car car2 = new Car();
        car2.setPlate("OPQ789");
        car2.setModel("Mercedes Benz");
        car2.setDailyPrice(1200.0);
        car2.setRented(true); // rented
        
        Car car3 = new Car();
        car3.setPlate("RST012");
        car3.setModel("BMW 5 Series");
        car3.setDailyPrice(1300.0);
        car3.setRented(false); // available
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Execute method under test
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify expected output: Available cars list = 
        // [ {"plate": "RST012", "model": "BMW 5 Series", "daily price": 1300},
        //   {"plate": "LMN456", "model": "Porsche 911", "daily price": 1500}]
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Check first car (lowest price)
        assertEquals("First car should be RST012", "RST012", availableCars.get(0).getPlate());
        assertEquals("First car model should be BMW 5 Series", "BMW 5 Series", availableCars.get(0).getModel());
        assertEquals("First car price should be 1300", 1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car
        assertEquals("Second car should be LMN456", "LMN456", availableCars.get(1).getPlate());
        assertEquals("Second car model should be Porsche 911", "Porsche 911", availableCars.get(1).getModel());
        assertEquals("Second car price should be 1500", 1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // Test Case 4: No Cars in Store
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new RentalStore();
        // No cars added to store
        
        // Execute method under test
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify expected output: Available cars list = []
        assertTrue("Available cars list should be empty when no cars in store", availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // Test Case 5: Single Car Rented and One Available
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new RentalStore();
        
        // Add cars with specified properties
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        car1.setRented(true); // rented
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        car2.setRented(false); // available
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        
        // Execute method under test
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify expected output: Available cars list = [{"plate": "JKL012", "model": "Mazda 3", "daily price": 350}]
        assertEquals("Should have 1 available car", 1, availableCars.size());
        
        // Check the only available car
        assertEquals("Car plate should be JKL012", "JKL012", availableCars.get(0).getPlate());
        assertEquals("Car model should be Mazda 3", "Mazda 3", availableCars.get(0).getModel());
        assertEquals("Car price should be 350", 350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}