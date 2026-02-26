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
        // SetUp: Create a Store instance named "City Car Rentals"
        store = new CarRentalStore();
        
        // Add cars with specified status
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Set rental status: car2 is rented, others are available
        Customer customer = new Customer("John", "Doe", "123 Main St");
        store.addCustomer(customer);
        
        // Rent car2 to make it unavailable
        LocalDate rentDate = LocalDate.now().minusDays(2);
        LocalDate dueDate = LocalDate.now().plusDays(5);
        store.rentCar(customer, car2, rentDate, dueDate);
        
        // Test: Check for available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify expected output: Available cars list sorted by price
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
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new CarRentalStore();
        
        // Add cars
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Set all cars as rented
        Customer customer = new Customer("Jane", "Smith", "456 Oak St");
        store.addCustomer(customer);
        
        LocalDate rentDate = LocalDate.now().minusDays(1);
        LocalDate dueDate = LocalDate.now().plusDays(3);
        
        store.rentCar(customer, car1, rentDate, dueDate);
        store.rentCar(customer, car2, rentDate, dueDate);
        store.rentCar(customer, car3, rentDate, dueDate);
        
        // Test: Check for available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify expected output: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new CarRentalStore();
        
        // Add cars
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Set rental status: car2 is rented, others are available
        Customer customer = new Customer("Mike", "Johnson", "789 Pine St");
        store.addCustomer(customer);
        
        LocalDate rentDate = LocalDate.now().minusDays(2);
        LocalDate dueDate = LocalDate.now().plusDays(4);
        store.rentCar(customer, car2, rentDate, dueDate);
        
        // Test: Check for available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify expected output: Available cars list sorted by price
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
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new CarRentalStore();
        // No cars added to the store
        
        // Test: Check for available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify expected output: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new CarRentalStore();
        
        // Add cars
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        
        store.addCar(car1);
        store.addCar(car2);
        
        // Set rental status: car1 is rented, car2 is available
        Customer customer = new Customer("Sarah", "Wilson", "321 Beach Rd");
        store.addCustomer(customer);
        
        LocalDate rentDate = LocalDate.now().minusDays(1);
        LocalDate dueDate = LocalDate.now().plusDays(2);
        store.rentCar(customer, car1, rentDate, dueDate);
        
        // Test: Check for available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify expected output: Available cars list with one car
        assertEquals(1, availableCars.size());
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}