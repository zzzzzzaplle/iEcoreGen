import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class CR1Test {
    
    private Store store;
    
    @Before
    public void setUp() {
        // Reset store before each test
        store = null;
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // Create a Store instance named "City Car Rentals"
        store = new Store();
        
        // Add cars to the store
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(500.0);
        store.getCars().add(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(600.0);
        store.getCars().add(car2);
        
        Car car3 = new Car();
        car3.setPlate("DEF456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(450.0);
        store.getCars().add(car3);
        
        // Create rental for the rented car (XYZ789)
        Rental rental = new Rental();
        rental.setCar(car2);
        // Set backDate to null to indicate it's currently rented
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Call the method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result
        assertEquals(2, availableCars.size());
        
        // Check first car (lowest price)
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // Create a Store instance named "Downtown Rentals"
        store = new Store();
        
        // Add cars to the store
        Car car1 = new Car();
        car1.setPlate("AAA111");
        car1.setModel("Nissan Altima");
        car1.setDailyPrice(600.0);
        store.getCars().add(car1);
        
        Car car2 = new Car();
        car2.setPlate("BBB222");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(700.0);
        store.getCars().add(car2);
        
        Car car3 = new Car();
        car3.setPlate("CCC333");
        car3.setModel("Kia Optima");
        car3.setDailyPrice(650.0);
        store.getCars().add(car3);
        
        // Create rentals for all cars (all rented)
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
        
        // Call the method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result - should be empty list
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // Create a Store instance named "Luxury Car Rentals"
        store = new Store();
        
        // Add cars to the store
        Car car1 = new Car();
        car1.setPlate("LMN456");
        car1.setModel("Porsche 911");
        car1.setDailyPrice(1500.0);
        store.getCars().add(car1);
        
        Car car2 = new Car();
        car2.setPlate("OPQ789");
        car2.setModel("Mercedes Benz");
        car2.setDailyPrice(1200.0);
        store.getCars().add(car2);
        
        Car car3 = new Car();
        car3.setPlate("RST012");
        car3.setModel("BMW 5 Series");
        car3.setDailyPrice(1300.0);
        store.getCars().add(car3);
        
        // Create rental for the rented car (OPQ789)
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Call the method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result
        assertEquals(2, availableCars.size());
        
        // Check first car (lower price first)
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // Create a Store instance named "Empty Rentals"
        store = new Store();
        
        // No cars added to the store
        
        // Call the method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result - should be empty list
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // Create a Store instance named "Coastal Rentals"
        store = new Store();
        
        // Add cars to the store
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        store.getCars().add(car1);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        store.getCars().add(car2);
        
        // Create rental for the rented car (GHI789)
        Rental rental = new Rental();
        rental.setCar(car1);
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Call the method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result
        assertEquals(1, availableCars.size());
        
        // Check the available car
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}