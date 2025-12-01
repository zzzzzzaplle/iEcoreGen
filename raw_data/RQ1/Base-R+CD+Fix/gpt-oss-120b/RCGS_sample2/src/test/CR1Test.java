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
        
        // Add cars with specified details and status
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
        
        // Add all cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rentals for rented cars
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        Rental rental1 = new Rental();
        rental1.setCar(car2); // XYZ789 is rented
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental1.setBackDate(null); // Car is still rented
        rental1.setTotalPrice(6000.0);
        
        store.addRental(rental1);
        
        // Test: Check for available cars in the store
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output
        assertEquals(2, availableCars.size());
        
        // Check first car (lowest daily price)
        Car firstCar = availableCars.get(0);
        assertEquals("DEF456", firstCar.getPlate());
        assertEquals("Ford Focus", firstCar.getModel());
        assertEquals(450.0, firstCar.getDailyPrice(), 0.001);
        
        // Check second car
        Car secondCar = availableCars.get(1);
        assertEquals("ABC123", secondCar.getPlate());
        assertEquals("Toyota Camry", secondCar.getModel());
        assertEquals(500.0, secondCar.getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() throws Exception {
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new Store();
        
        // Add cars with specified details
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
        
        // Add all cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rentals for all cars (all are rented)
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Smith");
        customer1.setAddress("456 Oak Ave");
        
        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setSurname("Johnson");
        customer2.setAddress("789 Pine St");
        
        Customer customer3 = new Customer();
        customer3.setName("Carol");
        customer3.setSurname("Williams");
        customer3.setAddress("321 Elm St");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental1.setBackDate(null);
        rental1.setTotalPrice(6000.0);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2023-01-02 11:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-01-12 11:00:00"));
        rental2.setBackDate(null);
        rental2.setTotalPrice(7000.0);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(dateFormat.parse("2023-01-03 12:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-13 12:00:00"));
        rental3.setBackDate(null);
        rental3.setTotalPrice(6500.0);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Test: Check for available cars in the store
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() throws Exception {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new Store();
        
        // Add cars with specified details
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
        
        // Add all cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental only for the rented car (OPQ789)
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Brown");
        customer.setAddress("654 Maple Dr");
        
        Rental rental = new Rental();
        rental.setCar(car2); // OPQ789 is rented
        rental.setCustomer(customer);
        rental.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental.setBackDate(null);
        rental.setTotalPrice(12000.0);
        
        store.addRental(rental);
        
        // Test: Check for available cars in the store
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output
        assertEquals(2, availableCars.size());
        
        // Check first car (lowest daily price)
        Car firstCar = availableCars.get(0);
        assertEquals("RST012", firstCar.getPlate());
        assertEquals("BMW 5 Series", firstCar.getModel());
        assertEquals(1300.0, firstCar.getDailyPrice(), 0.001);
        
        // Check second car
        Car secondCar = availableCars.get(1);
        assertEquals("LMN456", secondCar.getPlate());
        assertEquals("Porsche 911", secondCar.getModel());
        assertEquals(1500.0, secondCar.getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new Store();
        
        // Test: Check for available cars in the store
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() throws Exception {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new Store();
        
        // Add cars with specified details
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        
        // Add all cars to store
        store.addCar(car1);
        store.addCar(car2);
        
        // Create rental for the rented car (GHI789)
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Wilson");
        customer.setAddress("987 Beach Rd");
        
        Rental rental = new Rental();
        rental.setCar(car1); // GHI789 is rented
        rental.setCustomer(customer);
        rental.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        rental.setBackDate(null);
        rental.setTotalPrice(4000.0);
        
        store.addRental(rental);
        
        // Test: Check for available cars in the store
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output
        assertEquals(1, availableCars.size());
        
        // Check the available car
        Car availableCar = availableCars.get(0);
        assertEquals("JKL012", availableCar.getPlate());
        assertEquals("Mazda 3", availableCar.getModel());
        assertEquals(350.0, availableCar.getDailyPrice(), 0.001);
    }
}