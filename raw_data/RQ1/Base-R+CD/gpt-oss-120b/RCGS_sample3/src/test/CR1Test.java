import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
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
        
        // Add cars with specified status
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
        
        // Create rentals to set status
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        
        // Car XYZ789 is rented (backDate is null)
        Rental rental1 = new Rental();
        rental1.setCar(car2);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 10:00:00"));
        rental1.setBackDate(null); // Still rented
        rental1.setTotalPrice(2400.0);
        store.addRental(rental1);
        
        // Car ABC123 and DEF456 are available (no rental with backDate null)
        // For ABC123, create a rental that has been returned
        Rental rental2 = new Rental();
        rental2.setCar(car1);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2024-01-01 09:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-04 09:00:00"));
        rental2.setBackDate(dateFormat.parse("2024-01-04 09:00:00")); // Returned
        rental2.setTotalPrice(1500.0);
        store.addRental(rental2);
        
        // DEF456 has no rental records, so it's available
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price = [DEF456, ABC123]
        assertEquals(2, availableCars.size());
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
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
        
        // Create customers
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Johnson");
        customer1.setAddress("789 Pine St");
        
        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setSurname("Williams");
        customer2.setAddress("321 Elm St");
        
        Customer customer3 = new Customer();
        customer3.setName("Charlie");
        customer3.setSurname("Brown");
        customer3.setAddress("654 Maple Ave");
        
        // All cars are rented (backDate is null for all)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 08:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-07 08:00:00"));
        rental1.setBackDate(null);
        rental1.setTotalPrice(3600.0);
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2024-01-02 09:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-09 09:00:00"));
        rental2.setBackDate(null);
        rental2.setTotalPrice(4900.0);
        store.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(dateFormat.parse("2024-01-03 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2024-01-08 10:00:00"));
        rental3.setBackDate(null);
        rental3.setTotalPrice(3250.0);
        store.addRental(rental3);
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = []
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
        
        // Create customers
        Customer customer1 = new Customer();
        customer1.setName("David");
        customer1.setSurname("Miller");
        customer1.setAddress("111 Luxury Ln");
        
        Customer customer2 = new Customer();
        customer2.setName("Eva");
        customer2.setSurname("Davis");
        customer2.setAddress("222 Premium Rd");
        
        // Set rental status
        // LMN456 is available (no rental with backDate null)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2024-01-01 11:00:00"));
        rental1.setDueDate(dateFormat.parse("2024-01-05 11:00:00"));
        rental1.setBackDate(dateFormat.parse("2024-01-05 11:00:00")); // Returned
        rental1.setTotalPrice(6000.0);
        store.addRental(rental1);
        
        // OPQ789 is rented (backDate is null)
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2024-01-02 12:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-01-06 12:00:00"));
        rental2.setBackDate(null); // Still rented
        rental2.setTotalPrice(4800.0);
        store.addRental(rental2);
        
        // RST012 is available (no rental records)
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [RST012, LMN456] sorted by price
        assertEquals(2, availableCars.size());
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new Store();
        // No cars added
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = []
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
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        store.addCar(car2);
        
        // Create customer
        Customer customer = new Customer();
        customer.setName("Frank");
        customer.setSurname("Wilson");
        customer.setAddress("777 Beach Blvd");
        
        // Set rental status
        // GHI789 is rented (backDate is null)
        Rental rental = new Rental();
        rental.setCar(car1);
        rental.setCustomer(customer);
        rental.setRentalDate(dateFormat.parse("2024-01-01 13:00:00"));
        rental.setDueDate(dateFormat.parse("2024-01-04 13:00:00"));
        rental.setBackDate(null); // Still rented
        rental.setTotalPrice(1200.0);
        store.addRental(rental);
        
        // JKL012 is available (no rental records)
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [JKL012]
        assertEquals(1, availableCars.size());
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}