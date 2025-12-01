import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;

public class CR1Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create a Store instance named "City Car Rentals"
        store = new Store();
        
        // SetUp: Add cars with different statuses
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
        
        // Create rental for the rented car (XYZ789)
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setBackDate(null); // Active rental (car not returned)
        store.addRental(rental);
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Expected available cars list sorted by price
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // First car should be the cheapest available (DEF456)
        assertEquals("First car should be Ford Focus", "DEF456", availableCars.get(0).getPlate());
        assertEquals("First car model should be Ford Focus", "Ford Focus", availableCars.get(0).getModel());
        assertEquals("First car price should be 450", 450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Second car should be the next cheapest available (ABC123)
        assertEquals("Second car should be Toyota Camry", "ABC123", availableCars.get(1).getPlate());
        assertEquals("Second car model should be Toyota Camry", "Toyota Camry", availableCars.get(1).getModel());
        assertEquals("Second car price should be 500", 500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new Store();
        
        // SetUp: Add cars with rented status
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
        
        // Create rentals for all cars (all rented)
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
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Expected empty list since all cars are rented
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new Store();
        
        // SetUp: Add luxury cars with different statuses
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
        
        // Create rental only for the rented car (OPQ789)
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setBackDate(null);
        store.addRental(rental);
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Expected available cars list sorted by price
        assertEquals("Should have 2 available cars", 2, availableCars.size());
        
        // First car should be BMW 5 Series (cheapest available)
        assertEquals("First car should be BMW 5 Series", "RST012", availableCars.get(0).getPlate());
        assertEquals("First car model should be BMW 5 Series", "BMW 5 Series", availableCars.get(0).getModel());
        assertEquals("First car price should be 1300", 1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Second car should be Porsche 911 (more expensive available)
        assertEquals("Second car should be Porsche 911", "LMN456", availableCars.get(1).getPlate());
        assertEquals("Second car model should be Porsche 911", "Porsche 911", availableCars.get(1).getModel());
        assertEquals("Second car price should be 1500", 1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new Store();
        // No cars added to the store
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Expected empty list since no cars in store
        assertTrue("Available cars list should be empty", availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new Store();
        
        // SetUp: Add cars with different statuses
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
        
        // Create rental for the rented car (GHI789)
        Rental rental = new Rental();
        rental.setCar(car1);
        rental.setBackDate(null);
        store.addRental(rental);
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Expected single available car
        assertEquals("Should have 1 available car", 1, availableCars.size());
        
        // Only available car should be Mazda 3
        assertEquals("Available car should be Mazda 3", "JKL012", availableCars.get(0).getPlate());
        assertEquals("Available car model should be Mazda 3", "Mazda 3", availableCars.get(0).getModel());
        assertEquals("Available car price should be 350", 350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}