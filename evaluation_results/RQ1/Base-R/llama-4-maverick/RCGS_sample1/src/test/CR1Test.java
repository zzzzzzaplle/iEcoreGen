import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        // Reset store before each test
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create "City Car Rentals" store with mixed rental status cars
        store = new CarRentalStore(); // "City Car Rentals" instance
        
        // Add cars as specified in test case
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        car1.setRented(false); // available
        
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        car2.setRented(true); // rented
        
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        car3.setRented(false); // available
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Check list size and order
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
        // SetUp: Create "Downtown Rentals" store with all cars rented
        store = new CarRentalStore(); // "Downtown Rentals" instance
        
        // Add cars as specified in test case
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        car1.setRented(true); // rented
        
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        car2.setRented(true); // rented
        
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        car3.setRented(true); // rented
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Should return empty list
        assertNotNull("Available cars list should not be null", availableCars);
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create "Luxury Car Rentals" store with luxury cars
        store = new CarRentalStore(); // "Luxury Car Rentals" instance
        
        // Add cars as specified in test case
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        car1.setRented(false); // available
        
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        car2.setRented(true); // rented
        
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        car3.setRented(false); // available
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Check list size and order (sorted by price ascending)
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Check first car (BMW - lower price)
        assertEquals("First car should be RST012", "RST012", availableCars.get(0).getPlate());
        assertEquals("First car model should be BMW 5 Series", "BMW 5 Series", availableCars.get(0).getModel());
        assertEquals("First car price should be 1300", 1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car (Porsche - higher price)
        assertEquals("Second car should be LMN456", "LMN456", availableCars.get(1).getPlate());
        assertEquals("Second car model should be Porsche 911", "Porsche 911", availableCars.get(1).getModel());
        assertEquals("Second car price should be 1500", 1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create "Empty Rentals" store with no cars
        store = new CarRentalStore(); // "Empty Rentals" instance
        // No cars added to store
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Should return empty list
        assertNotNull("Available cars list should not be null", availableCars);
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create "Coastal Rentals" store with one rented and one available car
        store = new CarRentalStore(); // "Coastal Rentals" instance
        
        // Add cars as specified in test case
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        car1.setRented(true); // rented
        
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        car2.setRented(false); // available
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Should have exactly one available car
        assertEquals("Should have 1 available car", 1, availableCars.size());
        
        // Check the available car details
        assertEquals("Car plate should be JKL012", "JKL012", availableCars.get(0).getPlate());
        assertEquals("Car model should be Mazda 3", "Mazda 3", availableCars.get(0).getModel());
        assertEquals("Car price should be 350", 350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}