import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

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
        
        // Add all cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Set rental status: car2 (XYZ789) is rented, others are available
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
            Date dueDate = dateFormat.parse("2024-01-10 10:00:00");
            
            Rental rental = new Rental();
            rental.setCar(car2);
            rental.setRentalDate(rentalDate);
            rental.setDueDate(dueDate);
            // backDate is null meaning car is still rented
            rental.setBackDate(null);
            
            Customer customer = new Customer();
            rental.setCustomer(customer);
            
            store.getRentals().add(rental);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price
        assertEquals(2, availableCars.size());
        
        // First car should be DEF456 with price 450
        assertEquals("DEF456", availableCars.get(0).getPlate());
        assertEquals("Ford Focus", availableCars.get(0).getModel());
        assertEquals(450.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Second car should be ABC123 with price 500
        assertEquals("ABC123", availableCars.get(1).getPlate());
        assertEquals("Toyota Camry", availableCars.get(1).getModel());
        assertEquals(500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase2_AllCarsRentedCheck() {
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new Store();
        
        // Add cars with specified status (all rented)
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
        
        // Add all cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Set all cars as rented
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
            Date dueDate = dateFormat.parse("2024-01-10 10:00:00");
            
            // Rent car1
            Rental rental1 = new Rental();
            rental1.setCar(car1);
            rental1.setRentalDate(rentalDate);
            rental1.setDueDate(dueDate);
            rental1.setBackDate(null);
            rental1.setCustomer(new Customer());
            store.getRentals().add(rental1);
            
            // Rent car2
            Rental rental2 = new Rental();
            rental2.setCar(car2);
            rental2.setRentalDate(rentalDate);
            rental2.setDueDate(dueDate);
            rental2.setBackDate(null);
            rental2.setCustomer(new Customer());
            store.getRentals().add(rental2);
            
            // Rent car3
            Rental rental3 = new Rental();
            rental3.setCar(car3);
            rental3.setRentalDate(rentalDate);
            rental3.setDueDate(dueDate);
            rental3.setBackDate(null);
            rental3.setCustomer(new Customer());
            store.getRentals().add(rental3);
            
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new Store();
        
        // Add cars with specified status
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
        
        // Add all cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Set rental status: car2 (OPQ789) is rented, others are available
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
            Date dueDate = dateFormat.parse("2024-01-10 10:00:00");
            
            Rental rental = new Rental();
            rental.setCar(car2);
            rental.setRentalDate(rentalDate);
            rental.setDueDate(dueDate);
            rental.setBackDate(null);
            rental.setCustomer(new Customer());
            
            store.getRentals().add(rental);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price
        assertEquals(2, availableCars.size());
        
        // First car should be RST012 with price 1300
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Second car should be LMN456 with price 1500
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new Store();
        
        // Execute: Check for available cars (no cars added to store)
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list should be empty
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new Store();
        
        // Add cars with specified status
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        
        // Add all cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        
        // Set rental status: car1 (GHI789) is rented, car2 is available
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
            Date dueDate = dateFormat.parse("2024-01-10 10:00:00");
            
            Rental rental = new Rental();
            rental.setCar(car1);
            rental.setRentalDate(rentalDate);
            rental.setDueDate(dueDate);
            rental.setBackDate(null);
            rental.setCustomer(new Customer());
            
            store.getRentals().add(rental);
        } catch (Exception e) {
            fail("Date parsing failed");
        }
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list should contain only JKL012
        assertEquals(1, availableCars.size());
        
        assertEquals("JKL012", availableCars.get(0).getPlate());
        assertEquals("Mazda 3", availableCars.get(0).getModel());
        assertEquals(350.0, availableCars.get(0).getDailyPrice(), 0.001);
    }
}