import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class CR1Test {
    
    private CarStore store;
    
    @Before
    public void setUp() {
        // Reset store before each test
        store = new CarStore();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create a Store instance named "City Car Rentals"
        store = new CarStore(); // Store name is not stored in class, so we just create instance
        
        // Add cars with specified status (available = not in rentals, rented = in rentals without back date)
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental for rented car (XYZ789)
        Customer customer = new Customer("John", "Doe", "123 Main St");
        Rental rental = new Rental(customer, car2, LocalDate.now(), LocalDate.now().plusDays(7));
        store.addRental(rental);
        // No back date set = currently rented
        
        // Execute: Check for available cars
        java.util.List<Car> result = store.getAvailableCars();
        
        // Verify: Available cars list sorted by price = [DEF456, ABC123]
        assertEquals(2, result.size());
        
        // First car should be DEF456 (price 450)
        assertEquals("DEF456", result.get(0).getPlate());
        assertEquals("Ford Focus", result.get(0).getModel());
        assertEquals(450.0, result.get(0).getDailyPrice(), 0.001);
        
        // Second car should be ABC123 (price 500)
        assertEquals("ABC123", result.get(1).getPlate());
        assertEquals("Toyota Camry", result.get(1).getModel());
        assertEquals(500.0, result.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new CarStore();
        
        // Add all cars with status: rented
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rentals for all cars (no back date = currently rented)
        Customer customer = new Customer("Jane", "Smith", "456 Oak Ave");
        Rental rental1 = new Rental(customer, car1, LocalDate.now(), LocalDate.now().plusDays(5));
        Rental rental2 = new Rental(customer, car2, LocalDate.now(), LocalDate.now().plusDays(3));
        Rental rental3 = new Rental(customer, car3, LocalDate.now(), LocalDate.now().plusDays(7));
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Execute: Check for available cars
        java.util.List<Car> result = store.getAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new CarStore();
        
        // Add cars with different statuses
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental for rented car (OPQ789)
        Customer customer = new Customer("Mike", "Johnson", "789 Pine Rd");
        Rental rental = new Rental(customer, car2, LocalDate.now(), LocalDate.now().plusDays(10));
        store.addRental(rental);
        // No back date set = currently rented
        
        // Execute: Check for available cars
        java.util.List<Car> result = store.getAvailableCars();
        
        // Verify: Available cars list = [RST012, LMN456] (sorted by price)
        assertEquals(2, result.size());
        
        // First car should be RST012 (price 1300)
        assertEquals("RST012", result.get(0).getPlate());
        assertEquals("BMW 5 Series", result.get(0).getModel());
        assertEquals(1300.0, result.get(0).getDailyPrice(), 0.001);
        
        // Second car should be LMN456 (price 1500)
        assertEquals("LMN456", result.get(1).getPlate());
        assertEquals("Porsche 911", result.get(1).getModel());
        assertEquals(1500.0, result.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals" (no cars added)
        store = new CarStore();
        
        // Execute: Check for available cars
        java.util.List<Car> result = store.getAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new CarStore();
        
        // Add cars with specified status
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        
        store.addCar(car1);
        store.addCar(car2);
        
        // Create rental for rented car (GHI789)
        Customer customer = new Customer("Sarah", "Wilson", "321 Beach Blvd");
        Rental rental = new Rental(customer, car1, LocalDate.now(), LocalDate.now().plusDays(5));
        store.addRental(rental);
        // No back date set = currently rented
        
        // Execute: Check for available cars
        java.util.List<Car> result = store.getAvailableCars();
        
        // Verify: Available cars list = [JKL012]
        assertEquals(1, result.size());
        
        // Only available car should be JKL012
        assertEquals("JKL012", result.get(0).getPlate());
        assertEquals("Mazda 3", result.get(0).getModel());
        assertEquals(350.0, result.get(0).getDailyPrice(), 0.001);
    }
}