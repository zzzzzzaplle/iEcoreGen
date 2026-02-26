import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // Setup: Create a Store instance named "City Car Rentals"
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
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rental for the rented car (XYZ789)
        Rental rental = new Rental();
        rental.setCar(car2);
        Customer customer = new Customer();
        customer.setName("Test");
        customer.setSurname("Customer");
        rental.setCustomer(customer);
        rental.setRentalDate(new Date());
        rental.setDueDate(new Date(System.currentTimeMillis() + 86400000)); // 1 day later
        // backDate remains null (not returned yet)
        
        store.getRentals().add(rental);
        
        // Test: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price = [{"plate": "DEF456", "model": "Ford Focus", "daily price": 450}, {"plate": "ABC123", "model": "Toyota Camry", "daily price": 500}]
        assertEquals(2, availableCars.size());
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.01);
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.01);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // Setup: Create a Store instance named "Downtown Rentals"
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
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rentals for all cars (all rented)
        Customer customer1 = new Customer();
        customer1.setName("Customer1");
        customer1.setSurname("Test");
        
        Customer customer2 = new Customer();
        customer2.setName("Customer2");
        customer2.setSurname("Test");
        
        Customer customer3 = new Customer();
        customer3.setName("Customer3");
        customer3.setSurname("Test");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(new Date());
        rental1.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(new Date());
        rental2.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(new Date());
        rental3.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Test: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // Setup: Create a Store instance named "Luxury Car Rentals"
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
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Create rental for the rented car (OPQ789)
        Rental rental = new Rental();
        rental.setCar(car2);
        Customer customer = new Customer();
        customer.setName("Luxury");
        customer.setSurname("Customer");
        rental.setCustomer(customer);
        rental.setRentalDate(new Date());
        rental.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        
        store.getRentals().add(rental);
        
        // Test: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [ {"plate": "RST012", "model": "BMW 5 Series", "daily price": 1300},{"plate": "LMN456", "model": "Porsche 911", "daily price": 1500}]
        assertEquals(2, availableCars.size());
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.01);
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.01);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // Setup: Create a Store instance named "Empty Rentals"
        store = new Store();
        // No cars added to store
        
        // Test: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // Setup: Create a Store instance named "Coastal Rentals"
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
        store.getCars().add(car1);
        store.getCars().add(car2);
        
        // Create rental for the rented car (GHI789)
        Rental rental = new Rental();
        rental.setCar(car1);
        Customer customer = new Customer();
        customer.setName("Coastal");
        customer.setSurname("Customer");
        rental.setCustomer(customer);
        rental.setRentalDate(new Date());
        rental.setDueDate(new Date(System.currentTimeMillis() + 86400000));
        
        store.getRentals().add(rental);
        
        // Test: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [{"plate": "JKL012", "model": "Mazda 3", "daily price": 350}]
        assertEquals(1, availableCars.size());
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.01);
    }
}