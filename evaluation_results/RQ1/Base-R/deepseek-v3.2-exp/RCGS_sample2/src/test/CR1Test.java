import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private CarRentalSystem system;
    
    @Before
    public void setUp() {
        // Reset the system before each test
        system = new CarRentalSystem();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // Test Case 1: Single Available Car Check
        // SetUp: Create a Store instance named "City Car Rentals"
        system = new CarRentalSystem();
        
        // Add cars with specified properties
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(500);
        car1.setRented(false); // available
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(600);
        car2.setRented(true); // rented
        system.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("DEF456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(450);
        car3.setRented(false); // available
        system.addCar(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = system.getAvailableCars();
        
        // Verify: Check that we have 2 available cars
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Verify: Check that cars are sorted by daily price in ascending order
        assertEquals("First car should be Ford Focus with price 450", "DEF456", availableCars.get(0).getPlate());
        assertEquals("First car model should be Ford Focus", "Ford Focus", availableCars.get(0).getModel());
        assertEquals(450, availableCars.get(0).getDailyPrice(), 0.001);
        
        assertEquals("Second car should be Toyota Camry with price 500", "ABC123", availableCars.get(1).getPlate());
        assertEquals("Second car model should be Toyota Camry", "Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // Test Case 2: All Cars Rented Check
        // SetUp: Create a Store instance named "Downtown Rentals"
        system = new CarRentalSystem();
        
        // Add cars with specified properties (all rented)
        Car car1 = new Car();
        car1.setPlate("AAA111");
        car1.setModel("Nissan Altima");
        car1.setDailyPrice(600);
        car1.setRented(true); // rented
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("BBB222");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(700);
        car2.setRented(true); // rented
        system.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("CCC333");
        car3.setModel("Kia Optima");
        car3.setDailyPrice(650);
        car3.setRented(true); // rented
        system.addCar(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = system.getAvailableCars();
        
        // Verify: Check that no cars are available
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // Test Case 3: Multiple Cars with Different Rental Status
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        system = new CarRentalSystem();
        
        // Add cars with specified properties
        Car car1 = new Car();
        car1.setPlate("LMN456");
        car1.setModel("Porsche 911");
        car1.setDailyPrice(1500);
        car1.setRented(false); // available
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("OPQ789");
        car2.setModel("Mercedes Benz");
        car2.setDailyPrice(1200);
        car2.setRented(true); // rented
        system.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("RST012");
        car3.setModel("BMW 5 Series");
        car3.setDailyPrice(1300);
        car3.setRented(false); // available
        system.addCar(car3);
        
        // Execute: Get available cars
        List<Car> availableCars = system.getAvailableCars();
        
        // Verify: Check that we have 2 available cars
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Verify: Check that cars are sorted by daily price in ascending order
        assertEquals("First car should be BMW 5 Series with price 1300", "RST012", availableCars.get(0).getPlate());
        assertEquals("First car model should be BMW 5 Series", "BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300, availableCars.get(0).getDailyPrice(), 0.001);
        
        assertEquals("Second car should be Porsche 911 with price 1500", "LMN456", availableCars.get(1).getPlate());
        assertEquals("Second car model should be Porsche 911", "Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // Test Case 4: No Cars in Store
        // SetUp: Create a Store instance named "Empty Rentals"
        system = new CarRentalSystem();
        // No cars added to the system
        
        // Execute: Get available cars
        List<Car> availableCars = system.getAvailableCars();
        
        // Verify: Check that available cars list is empty
        assertTrue("Available cars list should be empty when no cars in store", availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // Test Case 5: Single Car Rented and One Available
        // SetUp: Create a Store instance named "Coastal Rentals"
        system = new CarRentalSystem();
        
        // Add cars with specified properties
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400);
        car1.setRented(true); // rented
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350);
        car2.setRented(false); // available
        system.addCar(car2);
        
        // Execute: Get available cars
        List<Car> availableCars = system.getAvailableCars();
        
        // Verify: Check that we have 1 available car
        assertEquals("Should have 1 available car", 1, availableCars.size());
        
        // Verify: Check the available car details
        assertEquals("Available car should be Mazda 3 with plate JKL012", "JKL012", availableCars.get(0).getPlate());
        assertEquals("Available car model should be Mazda 3", "Mazda 3", availableCars.get(0).getModel());
        assertEquals(350, availableCars.get(0).getDailyPrice(), 0.001);
    }
}