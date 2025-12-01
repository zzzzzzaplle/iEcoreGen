import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

public class CR1Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() throws Exception {
        // Test Case 1: Single Available Car Check
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
        List<Rental> rentals = new ArrayList<>();
        
        // Car XYZ789 is rented (backDate is null)
        Customer customer1 = new Customer("John", "Doe", "123 Main St", "XYZ789");
        Rental rental1 = new Rental(
            dateFormat.parse("2024-01-01 10:00:00"),
            dateFormat.parse("2024-01-05 10:00:00"),
            null, // backDate is null meaning car is still rented
            2400.0,
            "Standard lease",
            car2,
            customer1
        );
        rentals.add(rental1);
        
        // Car ABC123 and DEF456 are available (backDate is not null or no rental exists)
        // For ABC123, create a rental that has been returned
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak St", "ABC123");
        Rental rental2 = new Rental(
            dateFormat.parse("2024-01-02 09:00:00"),
            dateFormat.parse("2024-01-04 09:00:00"),
            dateFormat.parse("2024-01-04 08:00:00"), // backDate is set meaning car is returned
            1000.0,
            "Standard lease",
            car1,
            customer2
        );
        rentals.add(rental2);
        
        store.setRentals(rentals);
        
        // Execute method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output
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
        // Test Case 2: All Cars Rented Check
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
        
        // Create rentals where all cars are rented (backDate is null)
        List<Rental> rentals = new ArrayList<>();
        
        Customer customer1 = new Customer("Alice", "Johnson", "789 Pine St", "AAA111");
        Rental rental1 = new Rental(
            dateFormat.parse("2024-01-01 08:00:00"),
            dateFormat.parse("2024-01-07 08:00:00"),
            null, // backDate is null - car is rented
            3600.0,
            "Extended lease",
            car1,
            customer1
        );
        
        Customer customer2 = new Customer("Bob", "Wilson", "321 Elm St", "BBB222");
        Rental rental2 = new Rental(
            dateFormat.parse("2024-01-02 14:00:00"),
            dateFormat.parse("2024-01-06 14:00:00"),
            null, // backDate is null - car is rented
            2800.0,
            "Standard lease",
            car2,
            customer2
        );
        
        Customer customer3 = new Customer("Carol", "Brown", "654 Maple St", "CCC333");
        Rental rental3 = new Rental(
            dateFormat.parse("2024-01-03 11:00:00"),
            dateFormat.parse("2024-01-05 11:00:00"),
            null, // backDate is null - car is rented
            1300.0,
            "Short-term lease",
            car3,
            customer3
        );
        
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Execute method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() throws Exception {
        // Test Case 3: Multiple Cars with Different Rental Status
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
        List<Rental> rentals = new ArrayList<>();
        
        // Car OPQ789 is rented (backDate is null)
        Customer customer1 = new Customer("David", "Lee", "987 Cedar St", "OPQ789");
        Rental rental1 = new Rental(
            dateFormat.parse("2024-01-01 12:00:00"),
            dateFormat.parse("2024-01-08 12:00:00"),
            null, // backDate is null - car is rented
            8400.0,
            "Premium lease",
            car2,
            customer1
        );
        rentals.add(rental1);
        
        // Cars LMN456 and RST012 are available (backDate is set)
        Customer customer2 = new Customer("Emma", "Garcia", "753 Birch St", "LMN456");
        Rental rental2 = new Rental(
            dateFormat.parse("2024-01-02 10:00:00"),
            dateFormat.parse("2024-01-04 10:00:00"),
            dateFormat.parse("2024-01-04 09:00:00"), // backDate is set - car is returned
            3000.0,
            "Premium lease",
            car1,
            customer2
        );
        rentals.add(rental2);
        
        store.setRentals(rentals);
        
        // Execute method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output - sorted by daily price in ascending order
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
        // Test Case 4: No Cars in Store
        // Create a Store instance named "Empty Rentals"
        store = new Store();
        
        // Store has no cars by default
        
        // Execute method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() throws Exception {
        // Test Case 5: Single Car Rented and One Available
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
        List<Rental> rentals = new ArrayList<>();
        
        // Car GHI789 is rented (backDate is null)
        Customer customer1 = new Customer("Frank", "Miller", "159 Spruce St", "GHI789");
        Rental rental1 = new Rental(
            dateFormat.parse("2024-01-01 09:00:00"),
            dateFormat.parse("2024-01-05 09:00:00"),
            null, // backDate is null - car is rented
            1600.0,
            "Standard lease",
            car1,
            customer1
        );
        rentals.add(rental1);
        
        // Car JKL012 is available (no rental record exists for it)
        
        store.setRentals(rentals);
        
        // Execute method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output
        assertEquals(1, availableCars.size());
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}