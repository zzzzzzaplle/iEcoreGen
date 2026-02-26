import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create a Store instance named "City Car Rentals"
        // Add cars with specified properties
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(500.0);
        car1.setRented(false); // available
        
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(600.0);
        car2.setRented(true); // rented
        
        Car car3 = new Car();
        car3.setPlate("DEF456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(450.0);
        car3.setRented(false); // available
        
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        
        // Execute: Check for available cars
        List<Car> availableCars = rentalSystem.getAvailableCars();
        
        // Verify: Available cars list sorted by price
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Check first car (lowest price)
        assertEquals("First car should be DEF456", "DEF456", availableCars.get(0).getPlate());
        assertEquals("First car model should be Ford Focus", "Ford Focus", availableCars.get(0).getModel());
        assertEquals("First car price should be 450", 450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car (next lowest price)
        assertEquals("Second car should be ABC123", "ABC123", availableCars.get(1).getPlate());
        assertEquals("Second car model should be Toyota Camry", "Toyota Camry", availableCars.get(1).getModel());
        assertEquals("Second car price should be 500", 500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create a Store instance named "Downtown Rentals"
        // Add cars with specified properties (all rented)
        Car car1 = new Car();
        car1.setPlate("AAA111");
        car1.setModel("Nissan Altima");
        car1.setDailyPrice(600.0);
        car1.setRented(true); // rented
        
        Car car2 = new Car();
        car2.setPlate("BBB222");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(700.0);
        car2.setRented(true); // rented
        
        Car car3 = new Car();
        car3.setPlate("CCC333");
        car3.setModel("Kia Optima");
        car3.setDailyPrice(650.0);
        car3.setRented(true); // rented
        
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        
        // Execute: Check for available cars
        List<Car> availableCars = rentalSystem.getAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        // Add cars with specified properties
        Car car1 = new Car();
        car1.setPlate("LMN456");
        car1.setModel("Porsche 911");
        car1.setDailyPrice(1500.0);
        car1.setRented(false); // available
        
        Car car2 = new Car();
        car2.setPlate("OPQ789");
        car2.setModel("Mercedes Benz");
        car2.setDailyPrice(1200.0);
        car2.setRented(true); // rented
        
        Car car3 = new Car();
        car3.setPlate("RST012");
        car3.setModel("BMW 5 Series");
        car3.setDailyPrice(1300.0);
        car3.setRented(false); // available
        
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        rentalSystem.addCar(car3);
        
        // Execute: Check for available cars
        List<Car> availableCars = rentalSystem.getAvailableCars();
        
        // Verify: Available cars list sorted by price
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Check first car (lower price)
        assertEquals("First car should be RST012", "RST012", availableCars.get(0).getPlate());
        assertEquals("First car model should be BMW 5 Series", "BMW 5 Series", availableCars.get(0).getModel());
        assertEquals("First car price should be 1300", 1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car (higher price)
        assertEquals("Second car should be LMN456", "LMN456", availableCars.get(1).getPlate());
        assertEquals("Second car model should be Porsche 911", "Porsche 911", availableCars.get(1).getModel());
        assertEquals("Second car price should be 1500", 1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals" (no cars added)
        
        // Execute: Check for available cars
        List<Car> availableCars = rentalSystem.getAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        // Add cars with specified properties
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        car1.setRented(true); // rented
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        car2.setRented(false); // available
        
        rentalSystem.addCar(car1);
        rentalSystem.addCar(car2);
        
        // Execute: Check for available cars
        List<Car> availableCars = rentalSystem.getAvailableCars();
        
        // Verify: Available cars list should contain only the available car
        assertEquals("Should have 1 available car", 1, availableCars.size());
        
        Car availableCar = availableCars.get(0);
        assertEquals("Available car should be JKL012", "JKL012", availableCar.getPlate());
        assertEquals("Available car model should be Mazda 3", "Mazda 3", availableCar.getModel());
        assertEquals("Available car price should be 350", 350.0, availableCar.getDailyPrice(), 0.001);
    }
}