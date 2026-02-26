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
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(500.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(600.0);
        
        Car car3 = new Car();
        car3.setPlate("DEF456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(450.0);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2, car3));
        
        // Create rentals to simulate rented status
        Customer customer = new Customer();
        Rental rental = new Rental();
        rental.setCar(car2); // Only car2 is rented
        rental.setBackDate(null); // Still rented (back date is null)
        
        store.setRentals(Arrays.asList(rental));
        
        // Execute: Identify available cars
        List<Car> result = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price
        assertEquals(2, result.size());
        assertEquals("DEF456", result.get(0).getPlate());
        assertEquals("Ford Focus", result.get(0).getModel());
        assertEquals(450.0, result.get(0).getDailyPrice(), 0.001);
        
        assertEquals("ABC123", result.get(1).getPlate());
        assertEquals("Toyota Camry", result.get(1).getModel());
        assertEquals(500.0, result.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new Store();
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("AAA111");
        car1.setModel("Nissan Altima");
        car1.setDailyPrice(600.0);
        
        Car car2 = new Car();
        car2.setPlate("BBB222");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(700.0);
        
        Car car3 = new Car();
        car3.setPlate("CCC333");
        car3.setModel("Kia Optima");
        car3.setDailyPrice(650.0);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2, car3));
        
        // Create rentals for all cars (all are rented)
        Customer customer = new Customer();
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setBackDate(null);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setBackDate(null);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setBackDate(null);
        
        store.setRentals(Arrays.asList(rental1, rental2, rental3));
        
        // Execute: Identify available cars
        List<Car> result = store.identifyAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new Store();
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("LMN456");
        car1.setModel("Porsche 911");
        car1.setDailyPrice(1500.0);
        
        Car car2 = new Car();
        car2.setPlate("OPQ789");
        car2.setModel("Mercedes Benz");
        car2.setDailyPrice(1200.0);
        
        Car car3 = new Car();
        car3.setPlate("RST012");
        car3.setModel("BMW 5 Series");
        car3.setDailyPrice(1300.0);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2, car3));
        
        // Create rental for only car2 (rented)
        Customer customer = new Customer();
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setBackDate(null);
        
        store.setRentals(Arrays.asList(rental));
        
        // Execute: Identify available cars
        List<Car> result = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price
        assertEquals(2, result.size());
        assertEquals("RST012", result.get(0).getPlate());
        assertEquals("BMW 5 Series", result.get(0).getModel());
        assertEquals(1300.0, result.get(0).getDailyPrice(), 0.001);
        
        assertEquals("LMN456", result.get(1).getPlate());
        assertEquals("Porsche 911", result.get(1).getModel());
        assertEquals(1500.0, result.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new Store();
        
        // Execute: Identify available cars
        List<Car> result = store.identifyAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new Store();
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2));
        
        // Create rental for car1 (rented)
        Customer customer = new Customer();
        Rental rental = new Rental();
        rental.setCar(car1);
        rental.setBackDate(null);
        
        store.setRentals(Arrays.asList(rental));
        
        // Execute: Identify available cars
        List<Car> result = store.identifyAvailableCars();
        
        // Verify: Available cars list with only car2
        assertEquals(1, result.size());
        assertEquals("JKL012", result.get(0).getPlate());
        assertEquals("Mazda 3", result.get(0).getModel());
        assertEquals(350.0, result.get(0).getDailyPrice(), 0.001);
    }
}