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
        // Initialize a fresh store before each test
        store = new Store();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create "City Car Rentals" store with 3 cars
        store.setCars(new ArrayList<>());
        store.setRentals(new ArrayList<>());
        
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
        
        // Set rental status: car2 is rented (others are available)
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setBackDate(null); // Not returned yet
        store.getRentals().add(rental);
        
        // Execute method under test
        List<Car> result = store.identifyAvailableCars();
        
        // Verify result size
        assertEquals(2, result.size());
        
        // Verify cars are sorted by daily price in ascending order
        assertEquals("DEF456", result.get(0).getPlate());
        assertEquals("Ford Focus", result.get(0).getModel());
        assertEquals(450.0, result.get(0).getDailyPrice(), 0.001);
        
        assertEquals("ABC123", result.get(1).getPlate());
        assertEquals("Toyota Camry", result.get(1).getModel());
        assertEquals(500.0, result.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create "Downtown Rentals" store with 3 rented cars
        store.setCars(new ArrayList<>());
        store.setRentals(new ArrayList<>());
        
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
        
        // Set all cars as rented
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setBackDate(null);
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setBackDate(null);
        store.getRentals().add(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setBackDate(null);
        store.getRentals().add(rental3);
        
        // Execute method under test
        List<Car> result = store.identifyAvailableCars();
        
        // Verify empty list when all cars are rented
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create "Luxury Car Rentals" store with 3 cars
        store.setCars(new ArrayList<>());
        store.setRentals(new ArrayList<>());
        
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
        
        // Set rental status: car2 is rented (others are available)
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Execute method under test
        List<Car> result = store.identifyAvailableCars();
        
        // Verify result size
        assertEquals(2, result.size());
        
        // Verify cars are sorted by daily price in ascending order
        assertEquals("RST012", result.get(0).getPlate());
        assertEquals("BMW 5 Series", result.get(0).getModel());
        assertEquals(1300.0, result.get(0).getDailyPrice(), 0.001);
        
        assertEquals("LMN456", result.get(1).getPlate());
        assertEquals("Porsche 911", result.get(1).getModel());
        assertEquals(1500.0, result.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create "Empty Rentals" store with no cars
        store.setCars(new ArrayList<>());
        store.setRentals(new ArrayList<>());
        
        // Execute method under test
        List<Car> result = store.identifyAvailableCars();
        
        // Verify empty list when no cars in store
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create "Coastal Rentals" store with 2 cars
        store.setCars(new ArrayList<>());
        store.setRentals(new ArrayList<>());
        
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
        
        // Set rental status: car1 is rented, car2 is available
        Rental rental = new Rental();
        rental.setCar(car1);
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Execute method under test
        List<Car> result = store.identifyAvailableCars();
        
        // Verify result size
        assertEquals(1, result.size());
        
        // Verify the available car details
        assertEquals("JKL012", result.get(0).getPlate());
        assertEquals("Mazda 3", result.get(0).getModel());
        assertEquals(350.0, result.get(0).getDailyPrice(), 0.001);
    }
}