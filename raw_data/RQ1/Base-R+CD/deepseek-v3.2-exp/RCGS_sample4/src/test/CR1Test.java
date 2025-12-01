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
        
        // Mark car2 as rented by creating a rental for it
        Rental rental = new Rental();
        rental.setCar(car2);
        Customer customer = new Customer();
        rental.setCustomer(customer);
        rental.setRentalDate(new Date());
        rental.setDueDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day later
        // backDate remains null to indicate active rental
        store.addRental(rental);
        
        // Test identifyAvailableCars method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result
        assertEquals(2, availableCars.size());
        
        // Check sorting by daily price (ascending)
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.001);
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
        
        // Mark all cars as rented by creating rentals for them
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Customer customer3 = new Customer();
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(new Date());
        rental1.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(new Date());
        rental2.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        store.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(new Date());
        rental3.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        store.addRental(rental3);
        
        // Test identifyAvailableCars method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result - should be empty list
        assertTrue(availableCars.isEmpty());
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
        Customer customer = new Customer();
        rental.setCustomer(customer);
        rental.setRentalDate(new Date());
        rental.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        store.addRental(rental);
        
        // Test identifyAvailableCars method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result
        assertEquals(2, availableCars.size());
        
        // Check sorting by daily price (ascending)
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // Create a Store instance named "Empty Rentals"
        store = new Store();
        
        // Test identifyAvailableCars method on empty store
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result - should be empty list
        assertTrue(availableCars.isEmpty());
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
        Customer customer = new Customer();
        rental.setCustomer(customer);
        rental.setRentalDate(new Date());
        rental.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        store.addRental(rental);
        
        // Test identifyAvailableCars method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result
        assertEquals(1, availableCars.size());
        
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}