import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR1Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        // Reset store before each test
        store = null;
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create "City Car Rentals" store with mixed rental status cars
        store = new Store();
        store.setName("City Car Rentals");
        
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customer for rental
        Customer customer = new Customer("John", "Doe", "123 Main St");
        store.addCustomer(customer);
        
        // Create rental for car2 (XYZ789) - mark as rented
        Rental rental = new Rental(car2, customer, LocalDate.now().minusDays(2), 
                                 LocalDate.now().plusDays(5), null, 0.0);
        store.addRental(rental);
        
        // Test: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Should return 2 available cars sorted by price
        assertEquals(2, availableCars.size());
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create "Downtown Rentals" store with all cars rented
        store = new Store();
        store.setName("Downtown Rentals");
        
        // Create cars
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customer for rentals
        Customer customer = new Customer("Jane", "Smith", "456 Oak Ave");
        store.addCustomer(customer);
        
        // Create rentals for all cars - mark all as rented
        Rental rental1 = new Rental(car1, customer, LocalDate.now().minusDays(1), 
                                  LocalDate.now().plusDays(3), null, 0.0);
        Rental rental2 = new Rental(car2, customer, LocalDate.now().minusDays(2), 
                                  LocalDate.now().plusDays(4), null, 0.0);
        Rental rental3 = new Rental(car3, customer, LocalDate.now().minusDays(3), 
                                  LocalDate.now().plusDays(5), null, 0.0);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Test: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Should return empty list
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create "Luxury Car Rentals" store with luxury cars
        store = new Store();
        store.setName("Luxury Car Rentals");
        
        // Create luxury cars
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customer for rental
        Customer customer = new Customer("Robert", "Johnson", "789 Park Blvd");
        store.addCustomer(customer);
        
        // Create rental for car2 (OPQ789) - mark as rented
        Rental rental = new Rental(car2, customer, LocalDate.now().minusDays(1), 
                                 LocalDate.now().plusDays(7), null, 0.0);
        store.addRental(rental);
        
        // Test: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Should return 2 available cars sorted by price
        assertEquals(2, availableCars.size());
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create "Empty Rentals" store with no cars
        store = new Store();
        store.setName("Empty Rentals");
        
        // Test: Get available cars from empty store
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Should return empty list
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create "Coastal Rentals" store with one rented and one available car
        store = new Store();
        store.setName("Coastal Rentals");
        
        // Create cars
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        
        // Create customer for rental
        Customer customer = new Customer("Sarah", "Wilson", "321 Beach Rd");
        store.addCustomer(customer);
        
        // Create rental for car1 (GHI789) - mark as rented
        Rental rental = new Rental(car1, customer, LocalDate.now().minusDays(1), 
                                 LocalDate.now().plusDays(2), null, 0.0);
        store.addRental(rental);
        
        // Test: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Should return only the available car
        assertEquals(1, availableCars.size());
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}