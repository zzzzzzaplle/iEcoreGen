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
        
        // Add cars with specified plates, models, daily prices, and rental status
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create a customer for rented car
        Customer customer = new Customer("John", "Doe", "123 Main St", "XYZ789");
        
        // Create rental for car2 (XYZ789) - status: rented (backDate is null)
        Rental rental = new Rental(
            dateFormat.parse("2024-01-01 10:00:00"),
            dateFormat.parse("2024-01-10 10:00:00"),
            null, // backDate null means car is still rented
            6000.0,
            "Standard",
            car2,
            customer
        );
        store.addRental(rental);
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price = [{"plate": "DEF456", "model": "Ford Focus", "daily price": 450}, {"plate": "ABC123", "model": "Toyota Camry", "daily price": 500}]
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
        
        // Add cars with specified plates, models, daily prices, and status: all rented
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customers and rentals for all cars (all rented - backDate is null)
        Customer customer1 = new Customer("Alice", "Smith", "456 Oak St", "AAA111");
        Customer customer2 = new Customer("Bob", "Johnson", "789 Pine St", "BBB222");
        Customer customer3 = new Customer("Carol", "Williams", "321 Elm St", "CCC333");
        
        Rental rental1 = new Rental(
            dateFormat.parse("2024-01-01 09:00:00"),
            dateFormat.parse("2024-01-08 09:00:00"),
            null, // backDate null means car is still rented
            4200.0,
            "Standard",
            car1,
            customer1
        );
        
        Rental rental2 = new Rental(
            dateFormat.parse("2024-01-02 10:00:00"),
            dateFormat.parse("2024-01-09 10:00:00"),
            null, // backDate null means car is still rented
            4900.0,
            "Premium",
            car2,
            customer2
        );
        
        Rental rental3 = new Rental(
            dateFormat.parse("2024-01-03 11:00:00"),
            dateFormat.parse("2024-01-10 11:00:00"),
            null, // backDate null means car is still rented
            4550.0,
            "Standard",
            car3,
            customer3
        );
        
        store.addRental(rental1);
        store.addRental(rental2);
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
        
        // Add cars with specified plates, models, daily prices, and rental status
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create customer and rental for car2 (OPQ789) - status: rented
        Customer customer = new Customer("David", "Brown", "654 Maple St", "OPQ789");
        
        Rental rental = new Rental(
            dateFormat.parse("2024-01-01 12:00:00"),
            dateFormat.parse("2024-01-07 12:00:00"),
            null, // backDate null means car is still rented
            8400.0,
            "Luxury",
            car2,
            customer
        );
        store.addRental(rental);
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [ {"plate": "RST012", "model": "BMW 5 Series", "daily price": 1300},{"plate": "LMN456", "model": "Porsche 911", "daily price": 1500}]
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
        // No cars added to the store
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() throws Exception {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new Store();
        
        // Add cars with specified plates, models, daily prices, and rental status
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        
        store.addCar(car1);
        store.addCar(car2);
        
        // Create customer and rental for car1 (GHI789) - status: rented
        Customer customer = new Customer("Emma", "Davis", "987 Cedar St", "GHI789");
        
        Rental rental = new Rental(
            dateFormat.parse("2024-01-01 08:00:00"),
            dateFormat.parse("2024-01-05 08:00:00"),
            null, // backDate null means car is still rented
            1600.0,
            "Standard",
            car1,
            customer
        );
        store.addRental(rental);
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [{"plate": "JKL012", "model": "Mazda 3", "daily price": 350}]
        assertEquals(1, availableCars.size());
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}