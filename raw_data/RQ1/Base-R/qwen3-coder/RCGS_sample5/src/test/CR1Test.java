import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    private RentalStore store;
    
    @Before
    public void setUp() {
        // Reset store before each test
        store = new RentalStore();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create a Store instance named "City Car Rentals"
        RentalStore cityCarRentals = new RentalStore();
        
        // Add cars with specified status
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(500.0);
        cityCarRentals.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(600.0);
        cityCarRentals.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("DEF456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(450.0);
        cityCarRentals.addCar(car3);
        
        // Create rental records to set status
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        cityCarRentals.addCustomer(customer);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car2); // Mark Honda Accord as rented
        rental.setRentDate(LocalDate.now().minusDays(2));
        rental.setDueDate(LocalDate.now().plusDays(5));
        // No back date = currently rented
        cityCarRentals.addRental(rental);
        
        // Test: Check for available cars
        List<Car> availableCars = cityCarRentals.getAvailableCars();
        
        // Verify expected output
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
        // SetUp: Create a Store instance named "Downtown Rentals"
        RentalStore downtownRentals = new RentalStore();
        
        // Add cars
        Car car1 = new Car();
        car1.setPlate("AAA111");
        car1.setModel("Nissan Altima");
        car1.setDailyPrice(600.0);
        downtownRentals.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("BBB222");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(700.0);
        downtownRentals.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("CCC333");
        car3.setModel("Kia Optima");
        car3.setDailyPrice(650.0);
        downtownRentals.addCar(car3);
        
        // Create customer and mark all cars as rented
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        customer.setAddress("456 Oak Ave");
        downtownRentals.addCustomer(customer);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.now().minusDays(1));
        rental1.setDueDate(LocalDate.now().plusDays(6));
        downtownRentals.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.now().minusDays(3));
        rental2.setDueDate(LocalDate.now().plusDays(4));
        downtownRentals.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.now().minusDays(2));
        rental3.setDueDate(LocalDate.now().plusDays(5));
        downtownRentals.addRental(rental3);
        
        // Test: Check for available cars
        List<Car> availableCars = downtownRentals.getAvailableCars();
        
        // Verify expected output: empty list
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        RentalStore luxuryCarRentals = new RentalStore();
        
        // Add cars
        Car car1 = new Car();
        car1.setPlate("LMN456");
        car1.setModel("Porsche 911");
        car1.setDailyPrice(1500.0);
        luxuryCarRentals.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("OPQ789");
        car2.setModel("Mercedes Benz");
        car2.setDailyPrice(1200.0);
        luxuryCarRentals.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("RST012");
        car3.setModel("BMW 5 Series");
        car3.setDailyPrice(1300.0);
        luxuryCarRentals.addCar(car3);
        
        // Create customer and mark Mercedes as rented
        Customer customer = new Customer();
        customer.setName("Robert");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine Rd");
        luxuryCarRentals.addCustomer(customer);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car2); // Mark Mercedes as rented
        rental.setRentDate(LocalDate.now().minusDays(2));
        rental.setDueDate(LocalDate.now().plusDays(7));
        luxuryCarRentals.addRental(rental);
        
        // Test: Check for available cars
        List<Car> availableCars = luxuryCarRentals.getAvailableCars();
        
        // Verify expected output
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
        // SetUp: Create a Store instance named "Empty Rentals"
        RentalStore emptyRentals = new RentalStore();
        // No cars added
        
        // Test: Check for available cars
        List<Car> availableCars = emptyRentals.getAvailableCars();
        
        // Verify expected output: empty list
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        RentalStore coastalRentals = new RentalStore();
        
        // Add cars
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        coastalRentals.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        coastalRentals.addCar(car2);
        
        // Create customer and mark Subaru as rented
        Customer customer = new Customer();
        customer.setName("Sarah");
        customer.setSurname("Wilson");
        customer.setAddress("321 Beach Blvd");
        coastalRentals.addCustomer(customer);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car1); // Mark Subaru as rented
        rental.setRentDate(LocalDate.now().minusDays(1));
        rental.setDueDate(LocalDate.now().plusDays(4));
        coastalRentals.addRental(rental);
        
        // Test: Check for available cars
        List<Car> availableCars = coastalRentals.getAvailableCars();
        
        // Verify expected output
        assertEquals(1, availableCars.size());
        
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}