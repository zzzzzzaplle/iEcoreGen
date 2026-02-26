import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR1Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create a Store instance named "City Car Rentals"
        store = new Store();
        
        // Add cars with specified status
        Car car1 = new Car("ABC123", "Toyota Camry", 500.0);
        Car car2 = new Car("XYZ789", "Honda Accord", 600.0);
        Car car3 = new Car("DEF456", "Ford Focus", 450.0);
        
        // Add cars to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Create rentals to set status (rented cars have backDate = null)
        List<Rental> rentals = new ArrayList<>();
        
        // Car XYZ789 is rented (backDate = null)
        Rental rental1 = new Rental();
        rental1.setCar(car2);
        rental1.setBackDate(null); // Currently rented
        rentals.add(rental1);
        
        // Car ABC123 and DEF456 are available (backDate not null or no rental)
        Rental rental2 = new Rental();
        rental2.setCar(car1);
        rental2.setBackDate(new Date()); // Already returned
        rentals.add(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setBackDate(new Date()); // Already returned
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Execute: Call identifyAvailableCars
        List<Car> result = store.identifyAvailableCars();
        
        // Verify: Check available cars list sorted by price
        assertEquals(2, result.size());
        assertEquals("DEF456", result.get(0).getPlate());
        assertEquals("Ford Focus", result.get(0).getModel());
        assertEquals(450.0, result.get(0).getDailyPrice(), 0.001);
        assertEquals("ABC123", result.get(1).getPlate());
        assertEquals("Toyota Camry", result.get(1).getModel());
        assertEquals(500.0, result.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new Store();
        
        // Add cars
        Car car1 = new Car("AAA111", "Nissan Altima", 600.0);
        Car car2 = new Car("BBB222", "Chevy Malibu", 700.0);
        Car car3 = new Car("CCC333", "Kia Optima", 650.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // All cars are rented (backDate = null)
        List<Rental> rentals = new ArrayList<>();
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setBackDate(null);
        rentals.add(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setBackDate(null);
        rentals.add(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setBackDate(null);
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Execute: Call identifyAvailableCars
        List<Car> result = store.identifyAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new Store();
        
        // Add cars
        Car car1 = new Car("LMN456", "Porsche 911", 1500.0);
        Car car2 = new Car("OPQ789", "Mercedes Benz", 1200.0);
        Car car3 = new Car("RST012", "BMW 5 Series", 1300.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Set rental status
        List<Rental> rentals = new ArrayList<>();
        
        // Car OPQ789 is rented (backDate = null)
        Rental rental1 = new Rental();
        rental1.setCar(car2);
        rental1.setBackDate(null);
        rentals.add(rental1);
        
        // Car LMN456 and RST012 are available (backDate not null)
        Rental rental2 = new Rental();
        rental2.setCar(car1);
        rental2.setBackDate(new Date());
        rentals.add(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setBackDate(new Date());
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Execute: Call identifyAvailableCars
        List<Car> result = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price
        assertEquals(2, result.size());
        assertEquals("RST012", result.get(0).getPlate());
        assertEquals("BMW 5 Series", result.get(0).getModel());
        assertEquals(1300.0, result.get(0).getDailyPrice(), 0.001);
        assertEquals("LMN456", result.get(1).getPlate());
        assertEquals("Porsche 911", result.get(1).getModel());
        assertEquals(1500.0, result.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new Store();
        // No cars added to store
        
        // Execute: Call identifyAvailableCars
        List<Car> result = store.identifyAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new Store();
        
        // Add cars
        Car car1 = new Car("GHI789", "Subaru Impreza", 400.0);
        Car car2 = new Car("JKL012", "Mazda 3", 350.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        store.setCars(cars);
        
        // Set rental status
        List<Rental> rentals = new ArrayList<>();
        
        // Car GHI789 is rented (backDate = null)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setBackDate(null);
        rentals.add(rental1);
        
        // Car JKL012 is available (backDate not null)
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setBackDate(new Date());
        rentals.add(rental2);
        
        store.setRentals(rentals);
        
        // Execute: Call identifyAvailableCars
        List<Car> result = store.identifyAvailableCars();
        
        // Verify: Available cars list
        assertEquals(1, result.size());
        assertEquals("JKL012", result.get(0).getPlate());
        assertEquals("Mazda 3", result.get(0).getModel());
        assertEquals(350.0, result.get(0).getDailyPrice(), 0.001);
    }
}