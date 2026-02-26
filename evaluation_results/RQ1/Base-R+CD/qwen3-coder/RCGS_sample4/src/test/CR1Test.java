import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR1Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_singleAvailableCarCheck() throws ParseException {
        // Create cars
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(500);
        
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(600);
        
        Car car3 = new Car();
        car3.setPlate("DEF456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(450);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2, car3));
        
        // Create rental for car2 (XYZ789) to mark it as rented
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setBackDate(null); // null backDate means currently rented
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Execute method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results
        assertEquals(2, availableCars.size());
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_allCarsRentedCheck() throws ParseException {
        // Create cars
        Car car1 = new Car();
        car1.setPlate("AAA111");
        car1.setModel("Nissan Altima");
        car1.setDailyPrice(600);
        
        Car car2 = new Car();
        car2.setPlate("BBB222");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(700);
        
        Car car3 = new Car();
        car3.setPlate("CCC333");
        car3.setModel("Kia Optima");
        car3.setDailyPrice(650);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2, car3));
        
        // Create rentals for all cars to mark them as rented
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setBackDate(null);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setBackDate(null);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setBackDate(null);
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3));
        
        // Execute method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should be empty list
        assertEquals(0, availableCars.size());
    }
    
    @Test
    public void testCase3_multipleCarsWithDifferentRentalStatus() throws ParseException {
        // Create cars
        Car car1 = new Car();
        car1.setPlate("LMN456");
        car1.setModel("Porsche 911");
        car1.setDailyPrice(1500);
        
        Car car2 = new Car();
        car2.setPlate("OPQ789");
        car2.setModel("Mercedes Benz");
        car2.setDailyPrice(1200);
        
        Car car3 = new Car();
        car3.setPlate("RST012");
        car3.setModel("BMW 5 Series");
        car3.setDailyPrice(1300);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2, car3));
        
        // Create rental for car2 (OPQ789) to mark it as rented
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setBackDate(null); // null backDate means currently rented
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Execute method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results
        assertEquals(2, availableCars.size());
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_noCarsInStore() {
        // Store is already initialized with empty lists in setUp()
        
        // Execute method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should be empty list
        assertEquals(0, availableCars.size());
    }
    
    @Test
    public void testCase5_singleCarRentedAndOneAvailable() throws ParseException {
        // Create cars
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350);
        
        // Add cars to store
        store.setCars(Arrays.asList(car1, car2));
        
        // Create rental for car1 (GHI789) to mark it as rented
        Rental rental = new Rental();
        rental.setCar(car1);
        rental.setBackDate(null); // null backDate means currently rented
        
        // Add rental to store
        store.setRentals(Arrays.asList(rental));
        
        // Execute method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results
        assertEquals(1, availableCars.size());
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}