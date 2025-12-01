import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR1Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create a Store instance named "City Car Rentals"
        store = new CarRentalStore();
        
        // Add cars with specified plates, models, and daily prices
        Car car1 = new Car("ABC123", "Toyota Camry", 500);
        Car car2 = new Car("XYZ789", "Honda Accord", 600);
        Car car3 = new Car("DEF456", "Ford Focus", 450);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customers
        Customer customer1 = new Customer("John", "Doe", "123 Main St");
        
        // Create rentals to set status
        LocalDate rentDate = LocalDate.now().minusDays(5);
        LocalDate dueDate = LocalDate.now().plusDays(5);
        
        // Add rental for car2 (XYZ789) to make it rented
        Rental rental2 = new Rental(car2, customer1, rentDate, dueDate);
        store.addRental(rental2);
        
        // Get available cars sorted by price
        List<Car> availableCars = store.getAvailableCarsSortedByPrice();
        
        // Verify the expected output
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
        
        // Add cars with specified plates, models, and daily prices
        Car car1 = new Car("AAA111", "Nissan Altima", 600);
        Car car2 = new Car("BBB222", "Chevy Malibu", 700);
        Car car3 = new Car("CCC333", "Kia Optima", 650);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customers
        Customer customer1 = new Customer("Jane", "Smith", "456 Oak Ave");
        
        // Create rentals for all cars to make them all rented
        LocalDate rentDate = LocalDate.now().minusDays(3);
        LocalDate dueDate = LocalDate.now().plusDays(7);
        
        Rental rental1 = new Rental(car1, customer1, rentDate, dueDate);
        Rental rental2 = new Rental(car2, customer1, rentDate, dueDate);
        Rental rental3 = new Rental(car3, customer1, rentDate, dueDate);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Get available cars
        List<Car> availableCars = store.getAvailableCarsSortedByPrice();
        
        // Verify the expected output - empty list
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new CarRentalStore();
        
        // Add cars with specified plates, models, and daily prices
        Car car1 = new Car("LMN456", "Porsche 911", 1500);
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200);
        Car car3 = new Car("RST012", "BMW 5 Series", 1300);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customers
        Customer customer1 = new Customer("Mike", "Johnson", "789 Pine St");
        
        // Create rental for car2 (OPQ789) to make it rented
        LocalDate rentDate = LocalDate.now().minusDays(2);
        LocalDate dueDate = LocalDate.now().plusDays(8);
        
        Rental rental2 = new Rental(car2, customer1, rentDate, dueDate);
        store.addRental(rental2);
        
        // Get available cars sorted by price
        List<Car> availableCars = store.getAvailableCarsSortedByPrice();
        
        // Verify the expected output
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
        
        // Get available cars
        List<Car> availableCars = store.getAvailableCarsSortedByPrice();
        
        // Verify the expected output - empty list
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new CarRentalStore();
        
        // Add cars with specified plates, models, and daily prices
        Car car1 = new Car("GHI789", "Subaru Impreza", 400);
        Car car2 = new Car("JKL012", "Mazda 3", 350);
        
        store.addCar(car1);
        store.addCar(car2);
        
        // Create customers
        Customer customer1 = new Customer("Sarah", "Wilson", "321 Beach Blvd");
        
        // Create rental for car1 (GHI789) to make it rented
        LocalDate rentDate = LocalDate.now().minusDays(4);
        LocalDate dueDate = LocalDate.now().plusDays(6);
        
        Rental rental1 = new Rental(car1, customer1, rentDate, dueDate);
        store.addRental(rental1);
        
        // Get available cars sorted by price
        List<Car> availableCars = store.getAvailableCarsSortedByPrice();
        
        // Verify the expected output
        assertEquals(1, availableCars.size());
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}