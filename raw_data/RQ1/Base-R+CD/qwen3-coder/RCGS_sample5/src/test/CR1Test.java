import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR1Test {
    
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_singleAvailableCarCheck() throws ParseException {
        // Create a Store instance named "City Car Rentals"
        Store store = new Store();
        
        // Add cars to the store
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
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Add a rental for the Honda Accord (car2) to mark it as rented
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        // backDate is null, meaning it's still rented
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Call the method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result
        assertEquals(2, availableCars.size());
        
        // Check that the cars are sorted by daily price (ascending)
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.01);
        
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.01);
    }
    
    @Test
    public void testCase2_allCarsRentedCheck() throws ParseException {
        // Create a Store instance named "Downtown Rentals"
        Store store = new Store();
        
        // Add cars to the store
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
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Add rentals for all cars to mark them as rented
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setRentalDate(dateFormat.parse("2023-01-02 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-01-11 10:00:00"));
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setRentalDate(dateFormat.parse("2023-01-03 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-12 10:00:00"));
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Call the method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result - no available cars
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_multipleCarsWithDifferentRentalStatus() throws ParseException {
        // Create a Store instance named "Luxury Car Rentals"
        Store store = new Store();
        
        // Add cars to the store
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
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Add a rental for the Mercedes Benz (car2) to mark it as rented
        Rental rental = new Rental();
        rental.setCar(car2);
        rental.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        // backDate is null, meaning it's still rented
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Call the method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result
        assertEquals(2, availableCars.size());
        
        // Check that the cars are sorted by daily price (ascending)
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.01);
        
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.01);
    }
    
    @Test
    public void testCase4_noCarsInStore() {
        // Create a Store instance named "Empty Rentals"
        Store store = new Store();
        
        // Call the method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result - no available cars
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_singleCarRentedAndOneAvailable() throws ParseException {
        // Create a Store instance named "Coastal Rentals"
        Store store = new Store();
        
        // Add cars to the store
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        store.setCars(cars);
        
        // Add a rental for the Subaru Impreza (car1) to mark it as rented
        Rental rental = new Rental();
        rental.setCar(car1);
        rental.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        // backDate is null, meaning it's still rented
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Call the method under test
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify the result
        assertEquals(1, availableCars.size());
        
        // Check the available car
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.01);
    }
}