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
        // SetUp: Create store instance and add cars with different rental statuses
        store = new CarRentalStore();
        
        // Add cars with specified attributes
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        
        // Set car2 as rented
        car2.setRented(true);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Check list size and order
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Verify first car (lowest price)
        Car firstCar = availableCars.get(0);
        assertEquals("DEF456", firstCar.getPlate());
        assertEquals("Ford Focus", firstCar.getModel());
        assertEquals(450.0, firstCar.getDailyPrice(), 0.001);
        
        // Verify second car
        Car secondCar = availableCars.get(1);
        assertEquals("ABC123", secondCar.getPlate());
        assertEquals("Toyota Camry", secondCar.getModel());
        assertEquals(500.0, secondCar.getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create store instance and add only rented cars
        store = new CarRentalStore();
        
        // Add rented cars
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        
        // Set all cars as rented
        car1.setRented(true);
        car2.setRented(true);
        car3.setRented(true);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Should return empty list
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create store instance and add mixed rental status cars
        store = new CarRentalStore();
        
        // Add cars with specified attributes
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        
        // Set car2 as rented
        car2.setRented(true);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Check list size and order
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Verify first car (lowest price)
        Car firstCar = availableCars.get(0);
        assertEquals("RST012", firstCar.getPlate());
        assertEquals("BMW 5 Series", firstCar.getModel());
        assertEquals(1300.0, firstCar.getDailyPrice(), 0.001);
        
        // Verify second car
        Car secondCar = availableCars.get(1);
        assertEquals("LMN456", secondCar.getPlate());
        assertEquals("Porsche 911", secondCar.getModel());
        assertEquals(1500.0, secondCar.getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create empty store instance
        store = new CarRentalStore();
        
        // Execute: Get available cars from empty store
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Should return empty list
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create store instance with one rented and one available car
        store = new CarRentalStore();
        
        // Add cars with specified attributes
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        
        // Set car1 as rented
        car1.setRented(true);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Check list size and content
        assertEquals("Should have 1 available car", 1, availableCars.size());
        
        Car availableCar = availableCars.get(0);
        assertEquals("JKL012", availableCar.getPlate());
        assertEquals("Mazda 3", availableCar.getModel());
        assertEquals(350.0, availableCar.getDailyPrice(), 0.001);
    }
}