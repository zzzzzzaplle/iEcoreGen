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
        
        // Add cars
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
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Add rentals - car2 and car1 are rented, car3 is available
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        // backDate is null, so car is still rented
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer1);
        rental2.setRentalDate(dateFormat.parse("2023-01-02 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-01-12 10:00:00"));
        // backDate is null, so car is still rented
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Execute the method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should have 1 available car (car3)
        assertEquals(1, availableCars.size());
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.01);
    }
    
    @Test
    public void testCase2_allCarsRentedCheck() throws ParseException {
        // Create a Store instance named "Downtown Rentals"
        Store store = new Store();
        
        // Add cars
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
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Add rentals - all cars are rented
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer1);
        rental2.setRentalDate(dateFormat.parse("2023-01-02 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2023-01-12 10:00:00"));
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer1);
        rental3.setRentalDate(dateFormat.parse("2023-01-03 10:00:00"));
        rental3.setDueDate(dateFormat.parse("2023-01-13 10:00:00"));
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should be empty list
        assertEquals(0, availableCars.size());
    }
    
    @Test
    public void testCase3_multipleCarsWithDifferentRentalStatus() throws ParseException {
        // Create a Store instance named "Luxury Car Rentals"
        Store store = new Store();
        
        // Add cars
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
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Add rentals - car2 is rented, car1 and car3 are available
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Rental rental1 = new Rental();
        rental1.setCar(car2);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        // backDate is null, so car is still rented
        
        store.getRentals().add(rental1);
        
        // Execute the method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should have 2 available cars (car3 and car1), sorted by price
        assertEquals(2, availableCars.size());
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
        
        // Execute the method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should be empty list
        assertEquals(0, availableCars.size());
    }
    
    @Test
    public void testCase5_singleCarRentedAndOneAvailable() throws ParseException {
        // Create a Store instance named "Coastal Rentals"
        Store store = new Store();
        
        // Add cars
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350);
        
        store.getCars().add(car1);
        store.getCars().add(car2);
        
        // Add rental - car1 is rented, car2 is available
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2023-01-01 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-01-10 10:00:00"));
        // backDate is null, so car is still rented
        
        store.getRentals().add(rental1);
        
        // Execute the method
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify results - should have 1 available car (car2)
        assertEquals(1, availableCars.size());
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.01);
    }
}