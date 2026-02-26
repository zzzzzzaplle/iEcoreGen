import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class CR1Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleAvailableCarCheck() {
        // SetUp: Create a Store instance named "City Car Rentals"
        store = new CarRentalStore();
        
        // Add cars to store
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
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Set rental status: car2 is rented, others available
        Rental rental1 = new Rental();
        rental1.setCar(car2);
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        rental1.setCustomer(customer1);
        rental1.setStartDate(LocalDate.now().minusDays(5));
        rental1.setDueDate(LocalDate.now().plusDays(5));
        rental1.setBackDate(null); // Not returned yet
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        store.setRentals(rentals);
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list sorted by price = [{"plate": "DEF456", "model": "Ford Focus", "daily price": 450}, {"plate": "ABC123", "model": "Toyota Camry", "daily price": 500}]
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
        // SetUp: Create a Store instance named "Downtown Rentals"
        store = new CarRentalStore();
        
        // Add cars to store
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
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Set all cars as rented
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Smith");
        rental1.setCustomer(customer1);
        rental1.setStartDate(LocalDate.now().minusDays(3));
        rental1.setDueDate(LocalDate.now().plusDays(7));
        rental1.setBackDate(null);
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setSurname("Johnson");
        rental2.setCustomer(customer2);
        rental2.setStartDate(LocalDate.now().minusDays(1));
        rental2.setDueDate(LocalDate.now().plusDays(9));
        rental2.setBackDate(null);
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        Customer customer3 = new Customer();
        customer3.setName("Carol");
        customer3.setSurname("Williams");
        rental3.setCustomer(customer3);
        rental3.setStartDate(LocalDate.now().minusDays(2));
        rental3.setDueDate(LocalDate.now().plusDays(8));
        rental3.setBackDate(null);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleCarsWithDifferentRentalStatus() {
        // SetUp: Create a Store instance named "Luxury Car Rentals"
        store = new CarRentalStore();
        
        // Add cars to store
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
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        // Set rental status: car2 is rented, others available
        Rental rental1 = new Rental();
        rental1.setCar(car2);
        Customer customer1 = new Customer();
        customer1.setName("David");
        customer1.setSurname("Brown");
        rental1.setCustomer(customer1);
        rental1.setStartDate(LocalDate.now().minusDays(4));
        rental1.setDueDate(LocalDate.now().plusDays(6));
        rental1.setBackDate(null);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        store.setRentals(rentals);
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [ {"plate": "RST012", "model": "BMW 5 Series", "daily price": 1300},{"plate": "LMN456", "model": "Porsche 911", "daily price": 1500}]
        assertEquals(2, availableCars.size());
        
        // Check first car (lower price)
        assertEquals("RST012", availableCars.get(0).getPlate());
        assertEquals("BMW 5 Series", availableCars.get(0).getModel());
        assertEquals(1300.0, availableCars.get(0).getDailyPrice(), 0.001);
        
        // Check second car (higher price)
        assertEquals("LMN456", availableCars.get(1).getPlate());
        assertEquals("Porsche 911", availableCars.get(1).getModel());
        assertEquals(1500.0, availableCars.get(1).getDailyPrice(), 0.001);
    }
    
    @Test
    public void testCase4_NoCarsInStore() {
        // SetUp: Create a Store instance named "Empty Rentals"
        store = new CarRentalStore();
        
        // No cars added to store
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = []
        assertTrue(availableCars.isEmpty());
    }
    
    @Test
    public void testCase5_SingleCarRentedAndOneAvailable() {
        // SetUp: Create a Store instance named "Coastal Rentals"
        store = new CarRentalStore();
        
        // Add cars to store
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Subaru Impreza");
        car1.setDailyPrice(400.0);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Mazda 3");
        car2.setDailyPrice(350.0);
        
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        store.setCars(cars);
        
        // Set rental status: car1 is rented, car2 available
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        Customer customer1 = new Customer();
        customer1.setName("Emma");
        customer1.setSurname("Wilson");
        rental1.setCustomer(customer1);
        rental1.setStartDate(LocalDate.now().minusDays(2));
        rental1.setDueDate(LocalDate.now().plusDays(8));
        rental1.setBackDate(null);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        store.setRentals(rentals);
        
        // Execute: Check for available cars
        List<Car> availableCars = store.identifyAvailableCars();
        
        // Verify: Available cars list = [{"plate": "JKL012", "model": "Mazda 3", "daily price": 350}]
        assertEquals(1, availableCars.size());
        
        Car availableCar = availableCars.get(0);
        assertEquals("JKL012", availableCar.getPlate());
        assertEquals("Mazda 3", availableCar.getModel());
        assertEquals(350.0, availableCar.getDailyPrice(), 0.001);
    }
}