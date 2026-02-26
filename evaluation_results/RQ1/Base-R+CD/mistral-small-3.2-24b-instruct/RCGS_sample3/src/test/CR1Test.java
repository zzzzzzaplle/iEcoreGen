import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        store = new Store();
        
        // SetUp: Add cars with specified status
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Create rentals to simulate rented status
        // Only car2 (XYZ789) is rented, others are available
        Customer customer = new Customer("John", "Doe", "123 Main St", "XYZ789");
        Rental rental = new Rental(new Date(), new Date(), null, 600.0, "Standard", car2, customer);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price = [DEF456, ABC123]
        assertEquals(2, availableCars.size());
        
        // First car should be DEF456 with price 450
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Second car should be ABC123 with price 500
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new Store();
        
        // SetUp: Add cars with all rented status
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Create rentals for all cars (all are rented)
        Customer customer1 = new Customer("Alice", "Smith", "456 Oak St", "AAA111");
        Customer customer2 = new Customer("Bob", "Johnson", "789 Pine St", "BBB222");
        Customer customer3 = new Customer("Carol", "Williams", "321 Elm St", "CCC333");
        
        Rental rental1 = new Rental(new Date(), new Date(), null, 600.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(new Date(), new Date(), null, 700.0, "Standard", car2, customer2);
        Rental rental3 = new Rental(new Date(), new Date(), null, 650.0, "Standard", car3, customer3);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new Store();
        
        // SetUp: Add cars with different rental status
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Create rentals - only car2 (OPQ789) is rented
        Customer customer = new Customer("David", "Brown", "654 Maple St", "OPQ789");
        Rental rental = new Rental(new Date(), new Date(), null, 1200.0, "Premium", car2, customer);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [RST012, LMN456]
        assertEquals(2, availableCars.size());
        
        // First car should be RST012 with price 1300
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Second car should be LMN456 with price 1500
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new Store();
        // No cars added to store
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new Store();
        
        // SetUp: Add cars with specified status
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        store.setCars(cars);
        
        // Create rental for car1 (GHI789) - it is rented
        Customer customer = new Customer("Emma", "Davis", "987 Cedar St", "GHI789");
        Rental rental = new Rental(new Date(), new Date(), null, 400.0, "Standard", car1, customer);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [JKL012]
        assertEquals(1, availableCars.size());
        
        // Only available car should be JKL012 with price 350
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}