import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR1Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        // Reset store before each test
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create store and add cars with different rental statuses
        // 1. Create a Store instance named "City Car Rentals"
        store = new CarRentalStore(); // Name is for reference only, not stored in class
        
        // 2. Add a car with plate: "ABC123", model: "Toyota Camry", daily price: 500, and status: available
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        car1.setRented(false); // Available
        
        // 3. Add a car with plate: "XYZ789", model: "Honda Accord", daily price: 600, and status: rented
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        car2.setRented(true); // Rented
        
        // 4. Add a car with plate: "DEF456", model: "Ford Focus", daily price: 450, and status: available
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        car3.setRented(false); // Available
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Check that only available cars are returned, sorted by price ascending
        assertEquals("Should return 2 available cars", 2, availableCars.size());
        
        // Check first car (lowest price)
        assertEquals("First car should be DEF456", "DEF456", availableCars.get(0).getPlate());
        assertEquals("First car model should be Ford Focus", "Ford Focus", availableCars.get(0).getModel());
        assertEquals("First car price should be 450", 450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car (next lowest price)
        assertEquals("Second car should be ABC123", "ABC123", availableCars.get(1).getPlate());
        assertEquals("Second car model should be Toyota Camry", "Toyota Camry", availableCars.get(1).getModel());
        assertEquals("Second car price should be 500", 500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create store with all cars rented
        // 1. Create a Store instance named "Downtown Rentals"
        store = new CarRentalStore(); // Name is for reference only, not stored in class
        
        // 2. Add a car with plate: "AAA111", model: "Nissan Altima", daily price: 600, and status: rented
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        car1.setRented(true); // Rented
        
        // 3. Add a car with plate: "BBB222", model: "Chevy Malibu", daily price: 700, and status: rented
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        car2.setRented(true); // Rented
        
        // 4. Add a car with plate: "CCC333", model: "Kia Optima", daily price: 650, and status: rented
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        car3.setRented(true); // Rented
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Check that empty list is returned when all cars are rented
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create store with mixed rental status cars
        // 1. Create a Store instance named "Luxury Car Rentals"
        store = new CarRentalStore(); // Name is for reference only, not stored in class
        
        // 2. Add a car with plate: "LMN456", model: "Porsche 911", daily price: 1500, and status: available
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        car1.setRented(false); // Available
        
        // 3. Add a car with plate: "OPQ789", model: "Mercedes Benz", daily price: 1200, and status: rented
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        car2.setRented(true); // Rented
        
        // 4. Add a car with plate: "RST012", model: "BMW 5 Series", daily price: 1300, and status: available
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        car3.setRented(false); // Available
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Check that only available cars are returned, sorted by price ascending
        assertEquals("Should return 2 available cars", 2, availableCars.size());
        
        // Check first car (lower price)
        assertEquals("First car should be RST012", "RST012", availableCars.get(0).getPlate());
        assertEquals("First car model should be BMW 5 Series", "BMW 5 Series", availableCars.get(0).getModel());
        assertEquals("First car price should be 1300", 1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car (higher price)
        assertEquals("Second car should be LMN456", "LMN456", availableCars.get(1).getPlate());
        assertEquals("Second car model should be Porsche 911", "Porsche 911", availableCars.get(1).getModel());
        assertEquals("Second car price should be 1500", 1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create empty store
        // 1. Create a Store instance named "Empty Rentals"
        store = new CarRentalStore(); // Name is for reference only, not stored in class
        // No cars added
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Check that empty list is returned when no cars in store
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create store with one rented and one available car
        // 1. Create a Store instance named "Coastal Rentals"
        store = new CarRentalStore(); // Name is for reference only, not stored in class
        
        // 2. Add a car with plate: "GHI789", model: "Subaru Impreza", daily price: 400, and status: rented
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        car1.setRented(true); // Rented
        
        // 3. Add a car with plate: "JKL012", model: "Mazda 3", daily price: 350, and status: available
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        car2.setRented(false); // Available
        
        store.addCar(car1);
        store.addCar(car2);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Check that only the available car is returned
        assertEquals("Should return 1 available car", 1, availableCars.size());
        
        // Check the single available car
        assertEquals("Car should be JKL012", "JKL012", availableCars.get(0).getPlate());
        assertEquals("Car model should be Mazda 3", "Mazda 3", availableCars.get(0).getModel());
        assertEquals("Car price should be 350", 350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}