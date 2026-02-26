import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private CarRentalStore store;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // Test Case 1: "Single Rental Calculation"
        // SetUp: Create a store instance and add multiple rental records
        
        // Create first rental
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setStartDate(LocalDate.parse("2025-11-10", formatter));
        rental1.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-12", formatter));
        
        // Create second rental
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setStartDate(LocalDate.parse("2025-11-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-11", formatter));
        
        // Create third rental
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setStartDate(LocalDate.parse("2025-11-12", formatter));
        rental3.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental3.setBackDate(LocalDate.parse("2025-11-12", formatter));
        
        // Add cars and rentals to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: "No Rentals Calculation"
        // SetUp: Create a store instance with no rental records
        
        // Verify there are no Rental objects added
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Test Case 3: "Multiple Rentals with Same Daily Price"
        // SetUp: Create a store instance with rentals having same daily price
        
        // Create first rental
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Brown");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setStartDate(LocalDate.parse("2025-11-12", formatter));
        rental1.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-13", formatter));
        
        // Create second rental
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Charlie");
        customer2.setSurname("Wilson");
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setStartDate(LocalDate.parse("2025-11-12", formatter));
        rental2.setDueDate(LocalDate.parse("2025-12-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-15", formatter));
        
        // Add cars and rentals to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Test Case 4: "Mixed Prices Calculation"
        // SetUp: Create a store instance with rentals having varied daily prices
        
        // Create first rental
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        
        Customer customer1 = new Customer();
        customer1.setName("David");
        customer1.setSurname("Miller");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setStartDate(LocalDate.parse("2025-08-09", formatter));
        rental1.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-13", formatter));
        
        // Create second rental
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Emma");
        customer2.setSurname("Davis");
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setStartDate(LocalDate.parse("2025-08-11", formatter));
        rental2.setDueDate(LocalDate.parse("2026-01-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-13", formatter));
        
        // Create third rental
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        
        Customer customer3 = new Customer();
        customer3.setName("Frank");
        customer3.setSurname("Taylor");
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setStartDate(LocalDate.parse("2025-08-09", formatter));
        rental3.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental3.setBackDate(LocalDate.parse("2025-08-09", formatter));
        
        // Add cars and rentals to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Test Case 5: "One-Day Rental Calculation"
        // SetUp: Create a store instance with all rentals for only one day
        
        // Create first rental
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        
        Customer customer1 = new Customer();
        customer1.setName("Grace");
        customer1.setSurname("Anderson");
        
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setStartDate(LocalDate.parse("2025-08-12", formatter));
        rental1.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-12", formatter));
        
        // Create second rental
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Henry");
        customer2.setSurname("Clark");
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setStartDate(LocalDate.parse("2025-08-09", formatter));
        rental2.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-09", formatter));
        
        // Add cars and rentals to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}