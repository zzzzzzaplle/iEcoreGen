import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

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
        // Create Store instance
        Store cityCarRentals = new Store();
        
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
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        cityCarRentals.setCars(cars);
        
        // Create rental for rented car (XYZ789)
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setBackDate(null); // Still rented
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        cityCarRentals.setRentals(rentals);
        
        // Test available cars
        List<Car> availableCars = cityCarRentals.identifyAvailableCars();
        
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
    public void testCase2_AllCarsRentedCheck() throws Exception {
        // Create Store instance
        Store downtownRentals = new Store();
        
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
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        downtownRentals.setCars(cars);
        
        // Create rentals for all cars
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
        
        downtownRentals.setRentals(rentals);
        
        // Test available cars
        List<Car> availableCars = downtownRentals.identifyAvailableCars();
        
        // Verify results
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() throws Exception {
        // Create Store instance
        Store luxuryCarRentals = new Store();
        
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
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        luxuryCarRentals.setCars(cars);
        
        // Create rental for rented car (OPQ789)
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setBackDate(null); // Still rented
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        luxuryCarRentals.setRentals(rentals);
        
        // Test available cars
        List<Car> availableCars = luxuryCarRentals.identifyAvailableCars();
        
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
    public void testCase4_NoCarsInStore() throws Exception {
        // Create Store instance with no cars
        Store emptyRentals = new Store();
        
        // Test available cars
        List<Car> availableCars = emptyRentals.identifyAvailableCars();
        
        // Verify results
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() throws Exception {
        // Create Store instance
        Store coastalRentals = new Store();
        
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
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        coastalRentals.setCars(cars);
        
        // Create rental for rented car (GHI789)
        Rental rental = new Rental();
        rental.setCar(car1);
        rental.setBackDate(null); // Still rented
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        coastalRentals.setRentals(rentals);
        
        // Test available cars
        List<Car> availableCars = coastalRentals.identifyAvailableCars();
        
        // Verify results
        assertEquals(1, availableCars.size());
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}