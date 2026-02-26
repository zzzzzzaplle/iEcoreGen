import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR1Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        // Reset store before each test
        store = null;
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // Create a Store instance named "City Car Rentals"
        store = new Store();
        
        // Create and add cars with specified status
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(500.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(600.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("DEF456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(450.0);
        store.addCar(car3);
        
        // Create rentals to set car status (rented/available)
        Rental rental1 = new Rental();
        rental1.setCar(car2); // Mark car2 as rented
        rental1.setBackDate(null); // Active rental
        store.addRental(rental1);
        
        // Get available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Check sorting by daily price (ascending order)
        assertEquals("First car should be the cheapest", "DEF456", availableCars.get(0).getPlate());
        assertEquals("Second car should be more expensive", "ABC123", availableCars.get(1).getPlate());
        
        // Verify car details
        Car firstCar = availableCars.get(0);
        assertEquals("DEF456", firstCar.getPlate());
        assertEquals("Ford Focus", firstCar.getModel());
        assertEquals(450.0, firstCar.getDailyPrice(), 0.001);
        
        Car secondCar = availableCars.get(1);
        assertEquals("ABC123", secondCar.getPlate());
        assertEquals("Toyota Camry", secondCar.getModel());
        assertEquals(500.0, secondCar.getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // Create a Store instance named "Downtown Rentals"
        store = new Store();
        
        // Create and add cars
        Car car1 = new Car();
        car1.setPlate("AAA111");
        car1.setModel("Nissan Altima");
        car1.setDailyPrice(600.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("BBB222");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(700.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("CCC333");
        car3.setModel("Kia Optima");
        car3.setDailyPrice(650.0);
        store.addCar(car3);
        
        // Mark all cars as rented
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setBackDate(null);
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setBackDate(null);
        store.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setBackDate(null);
        store.addRental(rental3);
        
        // Get available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify no available cars
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // Create a Store instance named "Luxury Car Rentals"
        store = new Store();
        
        // Create and add cars
        Car car1 = new Car();
        car1.setPlate("LMN456");
        car1.setModel("Porsche 911");
        car1.setDailyPrice(1500.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("OPQ789");
        car2.setModel("Mercedes Benz");
        car2.setDailyPrice(1200.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("RST012");
        car3.setModel("BMW 5 Series");
        car3.setDailyPrice(1300.0);
        store.addCar(car3);
        
        // Mark car2 as rented
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setBackDate(null);
        store.addRental(rental);
        
        // Get available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // Check sorting by daily price (ascending order)
        assertEquals("First car should be BMW 5 Series", "RST012", availableCars.get(0).getPlate());
        assertEquals("Second car should be Porsche 911", "LMN456", availableCars.get(1).getPlate());
        
        // Verify car details
        Car firstCar = availableCars.get(0);
        assertEquals("RST012", firstCar.getPlate());
        assertEquals("BMW 5 Series", firstCar.getModel());
        assertEquals(1300.0, firstCar.getDailyPrice(), 0.001);
        
        Car secondCar = availableCars.get(1);
        assertEquals("LMN456", secondCar.getPlate());
        assertEquals("Porsche 911", secondCar.getModel());
        assertEquals(1500.0, secondCar.getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // Create a Store instance named "Empty Rentals"
        store = new Store();
        
        // No cars added to store
        
        // Get available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify empty list
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // Create a Store instance named "Coastal Rentals"
        store = new Store();
        
        // Create and add cars
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        store.addCar(car2);
        
        // Mark car1 as rented
        Rental rental = new Rental();
        rental.setCar(car1);
        rental.setBackDate(null);
        store.addRental(rental);
        
        // Get available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result
        assertEquals("Should have 1 available car", 1, availableCars.size());
        
        // Verify car details
        Car availableCar = availableCars.get(0);
        assertEquals("JKL012", availableCar.getPlate());
        assertEquals("Mazda 3", availableCar.getModel());
        assertEquals(350.0, availableCar.getDailyPrice(), 0.001);
    }
}