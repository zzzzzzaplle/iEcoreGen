import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CR1Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // Test Case 1: Single Available Car Check
        // SetUp: Create "City Car Rentals" store with mixed rental status cars
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
        
        // Create rentals - car2 is rented (backDate is null), others are available (backDate not null)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setBackDate(new Date()); // Available
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setBackDate(null); // Rented
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setBackDate(new Date()); // Available
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output: Available cars sorted by price
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
        // Test Case 2: All Cars Rented Check
        // SetUp: Create "Downtown Rentals" store with all cars rented
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
        
        // Create rentals - all cars are rented (backDate is null)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setBackDate(null);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setBackDate(null);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setBackDate(null);
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output: Empty list
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // Test Case 3: Multiple Cars with Different Rental Status
        // SetUp: Create "Luxury Car Rentals" store with mixed rental status luxury cars
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
        
        // Create rentals - car2 is rented (backDate null), others available (backDate not null)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setBackDate(new Date()); // Available
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setBackDate(null); // Rented
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setBackDate(new Date()); // Available
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output: Available cars sorted by price
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
        // SetUp: Create "Empty Rentals" store with no cars
        store.setCars(new ArrayList<>());
        store.setRentals(new ArrayList<>());
        
        // Execute method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output: Empty list
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // Test Case 5: Single Car Rented and One Available
        // SetUp: Create "Coastal Rentals" store with one rented and one available car
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
        
        // Create rentals - car1 is rented (backDate null), car2 is available (backDate not null)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setBackDate(null); // Rented
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setBackDate(new Date()); // Available
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Execute method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify expected output: Single available car
        assertEquals(1, availableCars.size());
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}