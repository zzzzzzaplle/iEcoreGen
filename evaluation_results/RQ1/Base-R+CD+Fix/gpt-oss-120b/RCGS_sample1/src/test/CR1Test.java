import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        // Reset store before each test
        store = new Store();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create a Store instance named "City Car Rentals"
        store = new Store(); // "City Car Rentals" is just a name, not stored in the class
        
        // Add cars with specified status (available/rented)
        Car car1 = new Car("ABC123", "Toyota Camry", 500);
        Car car2 = new Car("XYZ789", "Honda Accord", 600);
        Car car3 = new Car("DEF456", "Ford Focus", 450);
        
        // Add all cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental records to mark cars as rented
        // Only car2 (XYZ789) should be rented, others available
        Customer customer = new Customer("John", "Doe", "123 Main St", "XYZ789");
        Date rentalDate = new Date();
        Date dueDate = new Date(rentalDate.getTime() + 86400000); // 1 day later
        
        Rental rental = new Rental(rentalDate, dueDate, null, 600, "Standard", car2, customer);
        store.addRental(rental);
        
        // Test: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price = [DEF456, ABC123]
        assertEquals(2, availableCars.size());
        
        // Check first car (lowest price)
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car (higher price)
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new Store(); // "Downtown Rentals" is just a name
        
        // Add cars with all status: rented
        Car car1 = new Car("AAA111", "Nissan Altima", 600);
        Car car2 = new Car("BBB222", "Chevy Malibu", 700);
        Car car3 = new Car("CCC333", "Kia Optima", 650);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rentals for all cars (mark them as rented)
        Customer customer1 = new Customer("Alice", "Smith", "456 Oak St", "AAA111");
        Customer customer2 = new Customer("Bob", "Johnson", "789 Pine St", "BBB222");
        Customer customer3 = new Customer("Carol", "Williams", "101 Maple St", "CCC333");
        
        Date rentalDate = new Date();
        Date dueDate = new Date(rentalDate.getTime() + 86400000);
        
        Rental rental1 = new Rental(rentalDate, dueDate, null, 600, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate, dueDate, null, 700, "Standard", car2, customer2);
        Rental rental3 = new Rental(rentalDate, dueDate, null, 650, "Standard", car3, customer3);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Test: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new Store(); // "Luxury Car Rentals" is just a name
        
        // Add cars with specified status
        Car car1 = new Car("LMN456", "Porsche 911", 1500);
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200);
        Car car3 = new Car("RST012", "BMW 5 Series", 1300);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Mark only car2 (OPQ789) as rented
        Customer customer = new Customer("David", "Brown", "202 Elm St", "OPQ789");
        Date rentalDate = new Date();
        Date dueDate = new Date(rentalDate.getTime() + 86400000);
        
        Rental rental = new Rental(rentalDate, dueDate, null, 1200, "Premium", car2, customer);
        store.addRental(rental);
        
        // Test: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [RST012, LMN456] (sorted by price)
        assertEquals(2, availableCars.size());
        
        // Check first car (BMW 5 Series should come first due to lower price)
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car (Porsche 911)
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new Store(); // "Empty Rentals" is just a name
        // No cars added to store
        
        // Test: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new Store(); // "Coastal Rentals" is just a name
        
        // Add cars with specified status
        Car car1 = new Car("GHI789", "Subaru Impreza", 400);
        Car car2 = new Car("JKL012", "Mazda 3", 350);
        
        store.addCar(car1);
        store.addCar(car2);
        
        // Mark car1 (GHI789) as rented
        Customer customer = new Customer("Eve", "Davis", "303 Cedar St", "GHI789");
        Date rentalDate = new Date();
        Date dueDate = new Date(rentalDate.getTime() + 86400000);
        
        Rental rental = new Rental(rentalDate, dueDate, null, 400, "Economy", car1, customer);
        store.addRental(rental);
        
        // Test: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [JKL012]
        assertEquals(1, availableCars.size());
        
        // Check the available car
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350, availableCars.get(0).getDailyPrice(), 0.001);
    }
}