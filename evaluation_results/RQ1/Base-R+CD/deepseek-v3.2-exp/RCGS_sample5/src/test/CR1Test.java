import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() throws Exception {
        // SetUp: Create a Store instance named "City Car Rentals"
        store = new Store();
        
        // Add cars with specified status
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
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental for car2 (XYZ789) to mark it as rented
        Rental rental = new Rental();
        rental.setCar(car2);
        Customer customer = new Customer();
        rental.setCustomer(customer);
        rental.setBackDate(null); // Still rented
        store.addRental(rental);
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price
        assertEquals(2, availableCars.size());
        
        // Check first car (lowest price)
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car (higher price)
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() throws Exception {
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new Store();
        
        // Add cars
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
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rentals for all cars to mark them as rented
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(new Customer());
        rental1.setBackDate(null);
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(new Customer());
        rental2.setBackDate(null);
        store.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(new Customer());
        rental3.setBackDate(null);
        store.addRental(rental3);
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() throws Exception {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new Store();
        
        // Add cars
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
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental only for car2 (OPQ789) to mark it as rented
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setCustomer(new Customer());
        rental.setBackDate(null);
        store.addRental(rental);
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price
        assertEquals(2, availableCars.size());
        
        // Check first car (lower price)
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car (higher price)
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() throws Exception {
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new Store();
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() throws Exception {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new Store();
        
        // Add cars
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        
        store.addCar(car1);
        store.addCar(car2);
        
        // Create rental for car1 (GHI789) to mark it as rented
        Rental rental = new Rental();
        rental.setCar(car1);
        rental.setCustomer(new Customer());
        rental.setBackDate(null);
        store.addRental(rental);
        
        // Execute: Identify available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Only one available car
        assertEquals(1, availableCars.size());
        
        // Check the available car
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}