import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        // Initialize store and date format before each test
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() throws Exception {
        // Test Case 1: Calculate total revenue from rentals with multiple rental records
        
        // Create and setup first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-11-10"));
        rental1.setDueDate(dateFormat.parse("2025-11-13"));
        rental1.setBackDate(dateFormat.parse("2025-11-12"));
        rental1.setTotalPrice(100.0 * 3); // 3 days rental
        
        // Create and setup second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-11-10"));
        rental2.setDueDate(dateFormat.parse("2025-11-13"));
        rental2.setBackDate(dateFormat.parse("2025-11-11"));
        rental2.setTotalPrice(150.0 * 2); // 2 days rental
        
        // Create and setup third rental
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(dateFormat.parse("2025-11-12"));
        rental3.setDueDate(dateFormat.parse("2025-11-13"));
        rental3.setBackDate(dateFormat.parse("2025-11-12"));
        rental3.setTotalPrice(200.0 * 1); // 1 day rental
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: Calculate total revenue from rentals with no rental records
        
        // Verify there are no Rental objects added (store is initialized empty)
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws Exception {
        // Test Case 3: Calculate total revenue from rentals where multiple cars have the same daily price
        
        // Create and setup first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Brown");
        
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-11-12"));
        rental1.setDueDate(dateFormat.parse("2025-11-13"));
        rental1.setBackDate(dateFormat.parse("2025-11-13"));
        rental1.setTotalPrice(120.0 * 2); // 2 days rental
        
        // Create and setup second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Charlie");
        customer2.setSurname("Wilson");
        
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-11-12"));
        rental2.setDueDate(dateFormat.parse("2025-12-01"));
        rental2.setBackDate(dateFormat.parse("2025-11-15"));
        rental2.setTotalPrice(120.0 * 4); // 4 days rental
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws Exception {
        // Test Case 4: Calculate total revenue from rentals with varied daily prices
        
        // Create and setup first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        
        Customer customer1 = new Customer();
        customer1.setName("David");
        customer1.setSurname("Lee");
        
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-08-09"));
        rental1.setDueDate(dateFormat.parse("2025-09-01"));
        rental1.setBackDate(dateFormat.parse("2025-08-13"));
        rental1.setTotalPrice(90.0 * 5); // 5 days rental
        
        // Create and setup second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Emma");
        customer2.setSurname("Davis");
        
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-08-11"));
        rental2.setDueDate(dateFormat.parse("2026-01-01"));
        rental2.setBackDate(dateFormat.parse("2025-08-13"));
        rental2.setTotalPrice(150.0 * 3); // 3 days rental
        
        // Create and setup third rental
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        
        Customer customer3 = new Customer();
        customer3.setName("Frank");
        customer3.setSurname("Miller");
        
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(dateFormat.parse("2025-08-09"));
        rental3.setDueDate(dateFormat.parse("2025-09-01"));
        rental3.setBackDate(dateFormat.parse("2025-08-09"));
        rental3.setTotalPrice(250.0 * 1); // 1 day rental
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws Exception {
        // Test Case 5: Calculate total revenue from rentals with all rentals for only one day
        
        // Create and setup first rental
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        
        Customer customer1 = new Customer();
        customer1.setName("Grace");
        customer1.setSurname("Taylor");
        
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-08-12"));
        rental1.setDueDate(dateFormat.parse("2025-09-01"));
        rental1.setBackDate(dateFormat.parse("2025-08-12"));
        rental1.setTotalPrice(180.0 * 1); // 1 day rental
        
        // Create and setup second rental
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        
        Customer customer2 = new Customer();
        customer2.setName("Henry");
        customer2.setSurname("Clark");
        
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-08-09"));
        rental2.setDueDate(dateFormat.parse("2025-09-01"));
        rental2.setBackDate(dateFormat.parse("2025-08-09"));
        rental2.setTotalPrice(220.0 * 1); // 1 day rental
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}