import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

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
        
        // Add cars with specified attributes and status
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
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rentals to simulate rental status
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        // Create rental for car2 (rented)
        Rental rental1 = new Rental();
        rental1.setCar(car2);
        rental1.setCustomer(customer1);
        rental1.setBackDate(null); // Still rented
        
        // Add rental to store
        store.addRental(rental1);
        
        // Execute: Call identifyAvailableCars method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Check expected output
        assertEquals(2, availableCars.size());
        
        // Check first car (lowest daily price)
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() throws Exception {
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new Store();
        
        // Add cars with rented status
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
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customers
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Smith");
        customer1.setAddress("456 Oak St");
        
        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setSurname("Johnson");
        customer2.setAddress("789 Pine St");
        
        Customer customer3 = new Customer();
        customer3.setName("Charlie");
        customer3.setSurname("Brown");
        customer3.setAddress("321 Maple St");
        
        // Create rentals for all cars (all rented)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setBackDate(null); // Still rented
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setBackDate(null); // Still rented
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setBackDate(null); // Still rented
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Execute: Call identifyAvailableCars method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Check that available cars list is empty
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() throws Exception {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new Store();
        
        // Add luxury cars
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
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customer for rented car
        Customer customer1 = new Customer();
        customer1.setName("David");
        customer1.setSurname("Wilson");
        customer1.setAddress("654 Elm St");
        
        // Create rental for car2 (rented)
        Rental rental1 = new Rental();
        rental1.setCar(car2);
        rental1.setCustomer(customer1);
        rental1.setBackDate(null); // Still rented
        
        // Add rental to store
        store.addRental(rental1);
        
        // Execute: Call identifyAvailableCars method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Check expected output - sorted by daily price
        assertEquals(2, availableCars.size());
        
        // Check first available car (BMW 5 Series with daily price 1300)
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second available car (Porsche 911 with daily price 1500)
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() throws Exception {
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new Store();
        // No cars added to store
        
        // Execute: Call identifyAvailableCars method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Check that available cars list is empty
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
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        
        // Create customer
        Customer customer1 = new Customer();
        customer1.setName("Eve");
        customer1.setSurname("Davis");
        customer1.setAddress("987 Cedar St");
        
        // Create rental for car1 (rented)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setBackDate(null); // Still rented
        
        // Add rental to store
        store.addRental(rental1);
        
        // Execute: Call identifyAvailableCars method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Check expected output - only car2 should be available
        assertEquals(1, availableCars.size());
        
        // Check the available car
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}