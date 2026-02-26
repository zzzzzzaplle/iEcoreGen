import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        
        // Add cars with specified attributes
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        car1.setRented(false); // available
        
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        car2.setRented(true); // rented
        
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        car3.setRented(false); // available
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Check that we have exactly 2 available cars
        assertEquals(2, availableCars.size());
        
        // Verify: Cars are sorted by daily price in ascending order
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
        
        // Add cars with rented status
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        car1.setRented(true);
        
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        car2.setRented(true);
        
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        car3.setRented(true);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Empty list when all cars are rented
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new CarRentalStore();
        
        // Add cars with mixed rental status
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        car1.setRented(false); // available
        
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        car2.setRented(true); // rented
        
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        car3.setRented(false); // available
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Check that we have exactly 2 available cars
        assertEquals(2, availableCars.size());
        
        // Verify: Cars are sorted by daily price in ascending order
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
        // No cars added to store
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Empty list when no cars in store
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new CarRentalStore();
        
        // Add one rented and one available car
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        car1.setRented(true); // rented
        
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        car2.setRented(false); // available
        
        store.addCar(car1);
        store.addCar(car2);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Only one available car
        assertEquals(1, availableCars.size());
        
        // Verify: Correct car details
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}