import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR1Test {
    
    private CarGallery carGallery;
    
    @Before
    public void setUp() {
        carGallery = new CarGallery();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create a Store instance named "City Car Rentals"
        // Note: Store name is not used in the implementation, so we'll focus on the functional setup
        
        // Add cars with specified statuses
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        
        carGallery.addCar(car1);
        carGallery.addCar(car2);
        carGallery.addCar(car3);
        
        // Create rentals to set status (rented cars have rentals without back date)
        Customer customer = new Customer("John", "Doe", "123 Main St");
        
        // Mark car2 as rented (no back date)
        Rental rental2 = new Rental(car2, customer, LocalDate.now(), LocalDate.now().plusDays(5));
        carGallery.addRental(rental2);
        
        // Mark car1 and car3 as available (either no rental or rental with back date)
        Rental rental1 = new Rental(car1, customer, LocalDate.now().minusDays(10), LocalDate.now().minusDays(5));
        rental1.setBackDate(LocalDate.now().minusDays(5)); // Car returned
        carGallery.addRental(rental1);
        
        // Car3 has no rental, so it's available
        
        // Execute: Check for available cars
        List<Car> availableCars = carGallery.getAvailableCars();
        
        // Verify: Available cars list sorted by price
        assertEquals(2, availableCars.size());
        
        // First car should be DEF456 (price 450)
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Second car should be ABC123 (price 500)
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create a Store instance named "Downtown Rentals"
        
        // Add cars
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        
        carGallery.addCar(car1);
        carGallery.addCar(car2);
        carGallery.addCar(car3);
        
        // Mark all cars as rented (no back date)
        Customer customer = new Customer("Jane", "Smith", "456 Oak St");
        
        Rental rental1 = new Rental(car1, customer, LocalDate.now(), LocalDate.now().plusDays(7));
        carGallery.addRental(rental1);
        
        Rental rental2 = new Rental(car2, customer, LocalDate.now(), LocalDate.now().plusDays(3));
        carGallery.addRental(rental2);
        
        Rental rental3 = new Rental(car3, customer, LocalDate.now(), LocalDate.now().plusDays(5));
        carGallery.addRental(rental3);
        
        // Execute: Check for available cars
        List<Car> availableCars = carGallery.getAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        
        // Add cars
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        
        carGallery.addCar(car1);
        carGallery.addCar(car2);
        carGallery.addCar(car3);
        
        // Set rental status
        Customer customer = new Customer("Mike", "Johnson", "789 Pine St");
        
        // Mark car1 as available (no rental)
        // Mark car2 as rented (no back date)
        Rental rental2 = new Rental(car2, customer, LocalDate.now(), LocalDate.now().plusDays(10));
        carGallery.addRental(rental2);
        
        // Mark car3 as available (no rental)
        
        // Execute: Check for available cars
        List<Car> availableCars = carGallery.getAvailableCars();
        
        // Verify: Available cars list sorted by price
        assertEquals(2, availableCars.size());
        
        // First car should be RST012 (price 1300)
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Second car should be LMN456 (price 1500)
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals"
        // No cars added to the gallery
        
        // Execute: Check for available cars
        List<Car> availableCars = carGallery.getAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        
        // Add cars
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        
        carGallery.addCar(car1);
        carGallery.addCar(car2);
        
        // Set rental status
        Customer customer = new Customer("Sarah", "Williams", "321 Beach Rd");
        
        // Mark car1 as rented (no back date)
        Rental rental1 = new Rental(car1, customer, LocalDate.now(), LocalDate.now().plusDays(7));
        carGallery.addRental(rental1);
        
        // Mark car2 as available (no rental)
        
        // Execute: Check for available cars
        List<Car> availableCars = carGallery.getAvailableCars();
        
        // Verify: Available cars list
        assertEquals(1, availableCars.size());
        
        // Only car should be JKL012
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}