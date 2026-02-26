import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    private CarRentalSystem carRentalSystem;
    
    @Before
    public void setUp() {
        carRentalSystem = new CarRentalSystem();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create a Store instance named "City Car Rentals"
        carRentalSystem = new CarRentalSystem();
        
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
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        carRentalSystem.setCars(cars);
        
        // Execute: Get available cars
        List<Car> availableCars = carRentalSystem.getAvailableCars();
        
        // Verify: Check that available cars are sorted by price in ascending order
        assertEquals(2, availableCars.size());
        
        // First car should be the cheapest available one
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Second car should be the next cheapest available one
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create a Store instance named "Downtown Rentals"
        carRentalSystem = new CarRentalSystem();
        
        // Add cars with rented status
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
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        carRentalSystem.setCars(cars);
        
        // Execute: Get available cars
        List<Car> availableCars = carRentalSystem.getAvailableCars();
        
        // Verify: Empty list should be returned since all cars are rented
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        carRentalSystem = new CarRentalSystem();
        
        // Add cars with mixed rental status
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
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        carRentalSystem.setCars(cars);
        
        // Execute: Get available cars
        List<Car> availableCars = carRentalSystem.getAvailableCars();
        
        // Verify: Available cars should be sorted by price in ascending order
        assertEquals(2, availableCars.size());
        
        // First car should be the cheapest available one (BMW 5 Series)
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Second car should be the more expensive available one (Porsche 911)
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals"
        carRentalSystem = new CarRentalSystem();
        // No cars added to the system
        
        // Execute: Get available cars
        List<Car> availableCars = carRentalSystem.getAvailableCars();
        
        // Verify: Empty list should be returned since there are no cars
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        carRentalSystem = new CarRentalSystem();
        
        // Add one rented and one available car
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
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        carRentalSystem.setCars(cars);
        
        // Execute: Get available cars
        List<Car> availableCars = carRentalSystem.getAvailableCars();
        
        // Verify: Only the available car should be returned
        assertEquals(1, availableCars.size());
        
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}