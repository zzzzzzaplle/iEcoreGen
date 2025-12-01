import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CR1Test {
    private Store store;
    
    @Before
    public void setUp() {
        // Reset store before each test
        store = new Store();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() throws Exception {
        // Create a Store instance named "City Car Rentals"
        store = new Store();
        
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Create rentals to set car status
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date rentalDate = dateFormat.parse("2024-01-01");
        Date dueDate = dateFormat.parse("2024-01-10");
        
        // Car XYZ789 is rented (has a rental with null back date)
        Customer customer1 = new Customer("John", "Doe", "123 Main St", "XYZ789");
        Rental rental1 = new Rental(rentalDate, dueDate, null, 6000.0, "Standard", car2, customer1);
        
        // Car ABC123 and DEF456 are available (no rental or rental with back date set)
        // For DEF456, create a rental that has been returned
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak St", "DEF456");
        Date backDate = dateFormat.parse("2024-01-05");
        Rental rental2 = new Rental(rentalDate, dueDate, backDate, 2250.0, "Standard", car3, customer2);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Test available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results
        assertEquals(2, availableCars.size());
        
        // Check first car (should be DEF456 with price 450)
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car (should be ABC123 with price 500)
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() throws Exception {
        // Create a Store instance named "Downtown Rentals"
        store = new Store();
        
        // Create cars
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Create rentals - all cars are rented (null back date)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date rentalDate = dateFormat.parse("2024-01-01");
        Date dueDate = dateFormat.parse("2024-01-10");
        
        Customer customer1 = new Customer("Mike", "Johnson", "789 Pine St", "AAA111");
        Rental rental1 = new Rental(rentalDate, dueDate, null, 6000.0, "Standard", car1, customer1);
        
        Customer customer2 = new Customer("Sarah", "Wilson", "321 Elm St", "BBB222");
        Rental rental2 = new Rental(rentalDate, dueDate, null, 7000.0, "Standard", car2, customer2);
        
        Customer customer3 = new Customer("Tom", "Brown", "654 Maple St", "CCC333");
        Rental rental3 = new Rental(rentalDate, dueDate, null, 6500.0, "Standard", car3, customer3);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Test available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify empty list
        assertTrue(availableCars.isEmpty());
        assertEquals(0, availableCars.size());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() throws Exception {
        // Create a Store instance named "Luxury Car Rentals"
        store = new Store();
        
        // Create cars
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Create rentals with different statuses
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date rentalDate = dateFormat.parse("2024-01-01");
        Date dueDate = dateFormat.parse("2024-01-10");
        
        // OPQ789 is rented (null back date)
        Customer customer1 = new Customer("Robert", "Davis", "111 Oak St", "OPQ789");
        Rental rental1 = new Rental(rentalDate, dueDate, null, 12000.0, "Luxury", car2, customer1);
        
        // LMN456 and RST012 are available (have back dates or no rental)
        Customer customer2 = new Customer("Lisa", "Miller", "222 Pine St", "LMN456");
        Date backDate = dateFormat.parse("2024-01-08");
        Rental rental2 = new Rental(rentalDate, dueDate, backDate, 10500.0, "Luxury", car1, customer2);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Test available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results
        assertEquals(2, availableCars.size());
        
        // Check first car (should be RST012 with price 1300)
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car (should be LMN456 with price 1500)
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // Create a Store instance named "Empty Rentals"
        store = new Store();
        
        // Store has no cars by default after setUp()
        
        // Test available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify empty list
        assertTrue(availableCars.isEmpty());
        assertEquals(0, availableCars.size());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() throws Exception {
        // Create a Store instance named "Coastal Rentals"
        store = new Store();
        
        // Create cars
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        store.setCars(cars);
        
        // Create rentals
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date rentalDate = dateFormat.parse("2024-01-01");
        Date dueDate = dateFormat.parse("2024-01-10");
        
        // GHI789 is rented (null back date)
        Customer customer1 = new Customer("Emily", "Clark", "333 Beach St", "GHI789");
        Rental rental1 = new Rental(rentalDate, dueDate, null, 4000.0, "Standard", car1, customer1);
        
        // JKL012 is available (no rental record)
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        store.setRentals(rentals);
        
        // Test available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results
        assertEquals(1, availableCars.size());
        
        // Check the available car
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}