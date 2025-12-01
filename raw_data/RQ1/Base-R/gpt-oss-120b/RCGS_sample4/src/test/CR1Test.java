import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.List;

public class CR1Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create a Store instance named "City Car Rentals"
        store = new CarRentalStore();
        
        // Add cars with different rental statuses
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rentals to simulate rental status
        Customer customer = new Customer("John", "Doe", "123 Main St");
        store.addCustomer(customer);
        
        // Make car2 (XYZ789) rented by creating a rental with null backDate
        Rental rental2 = new Rental(customer, car2, LocalDate.now().minusDays(2), LocalDate.now().plusDays(5));
        store.addRental(rental2);
        
        // Make car1 and car3 available (no rentals or rentals with backDate set)
        Rental rental1 = new Rental(customer, car1, LocalDate.now().minusDays(10), LocalDate.now().minusDays(5));
        rental1.setBackDate(LocalDate.now().minusDays(5));
        store.addRental(rental1);
        
        Rental rental3 = new Rental(customer, car3, LocalDate.now().minusDays(8), LocalDate.now().minusDays(3));
        rental3.setBackDate(LocalDate.now().minusDays(3));
        store.addRental(rental3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
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
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new CarRentalStore();
        
        // Add cars
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customer
        Customer customer = new Customer("Jane", "Smith", "456 Oak St");
        store.addCustomer(customer);
        
        // Make all cars rented by creating rentals with null backDate
        Rental rental1 = new Rental(customer, car1, LocalDate.now().minusDays(3), LocalDate.now().plusDays(7));
        store.addRental(rental1);
        
        Rental rental2 = new Rental(customer, car2, LocalDate.now().minusDays(1), LocalDate.now().plusDays(6));
        store.addRental(rental2);
        
        Rental rental3 = new Rental(customer, car3, LocalDate.now().minusDays(2), LocalDate.now().plusDays(5));
        store.addRental(rental3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new CarRentalStore();
        
        // Add cars
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customer
        Customer customer = new Customer("Robert", "Johnson", "789 Pine St");
        store.addCustomer(customer);
        
        // Make car2 rented (OPQ789)
        Rental rental2 = new Rental(customer, car2, LocalDate.now().minusDays(4), LocalDate.now().plusDays(3));
        store.addRental(rental2);
        
        // Make car1 and car3 available
        Rental rental1 = new Rental(customer, car1, LocalDate.now().minusDays(10), LocalDate.now().minusDays(5));
        rental1.setBackDate(LocalDate.now().minusDays(5));
        store.addRental(rental1);
        
        Rental rental3 = new Rental(customer, car3, LocalDate.now().minusDays(12), LocalDate.now().minusDays(7));
        rental3.setBackDate(LocalDate.now().minusDays(7));
        store.addRental(rental3);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
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
        store = new CarRentalStore();
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new CarRentalStore();
        
        // Add cars
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        
        store.addCar(car1);
        store.addCar(car2);
        
        // Create customer
        Customer customer = new Customer("Sarah", "Wilson", "321 Beach Rd");
        store.addCustomer(customer);
        
        // Make car1 rented (GHI789)
        Rental rental1 = new Rental(customer, car1, LocalDate.now().minusDays(2), LocalDate.now().plusDays(5));
        store.addRental(rental1);
        
        // Make car2 available
        Rental rental2 = new Rental(customer, car2, LocalDate.now().minusDays(8), LocalDate.now().minusDays(3));
        rental2.setBackDate(LocalDate.now().minusDays(3));
        store.addRental(rental2);
        
        // Execute: Get available cars
        List<Car> availableCars = store.getAvailableCars();
        
        // Verify: Available cars list = [JKL012]
        assertEquals(1, availableCars.size());
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}