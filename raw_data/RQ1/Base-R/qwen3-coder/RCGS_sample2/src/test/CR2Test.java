import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private RentalStore store;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        // Initialize store and date formatter before each test
        store = new RentalStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() {
        // Test Case 1: Calculate total revenue from rentals with multiple rental records
        // SetUp: Create rental records with different daily prices and rental durations
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        
        // Create customers
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        customer3.setAddress("789 Pine Rd");
        
        // Create and configure rentals
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.parse("2025-11-10", formatter));
        rental1.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-12", formatter));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer2);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.parse("2025-11-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-11", formatter));
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer3);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.parse("2025-11-12", formatter));
        rental3.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental3.setBackDate(LocalDate.parse("2025-11-12", formatter));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: Calculate total revenue from rentals with no rental records
        // SetUp: Store with no rentals
        
        // Verify there are no rental objects
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() {
        // Test Case 3: Calculate total revenue from rentals where multiple cars have the same daily price
        
        // Create cars with same daily price
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        
        // Create customers
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Brown");
        customer1.setAddress("111 First St");
        
        Customer customer2 = new Customer();
        customer2.setName("Charlie");
        customer2.setSurname("Davis");
        customer2.setAddress("222 Second Ave");
        
        // Create and configure rentals
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.parse("2025-11-12", formatter));
        rental1.setDueDate(LocalDate.parse("2025-11-13", formatter));
        rental1.setBackDate(LocalDate.parse("2025-11-13", formatter));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer2);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.parse("2025-11-12", formatter));
        rental2.setDueDate(LocalDate.parse("2025-12-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-11-15", formatter));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() {
        // Test Case 4: Calculate total revenue from rentals with varied daily prices
        
        // Create cars with varied daily prices
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        
        Car car3 = new Car();
        car3.setPlate("TRK789");
        car3.setModel("Ford F-150");
        car3.setDailyPrice(250.0);
        
        // Create customers
        Customer customer1 = new Customer();
        customer1.setName("David");
        customer1.setSurname("Wilson");
        customer1.setAddress("333 Third St");
        
        Customer customer2 = new Customer();
        customer2.setName("Eva");
        customer2.setSurname("Miller");
        customer2.setAddress("444 Fourth Ave");
        
        Customer customer3 = new Customer();
        customer3.setName("Frank");
        customer3.setSurname("Taylor");
        customer3.setAddress("555 Fifth Rd");
        
        // Create and configure rentals
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.parse("2025-08-09", formatter));
        rental1.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-13", formatter));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer2);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.parse("2025-08-11", formatter));
        rental2.setDueDate(LocalDate.parse("2026-01-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-13", formatter));
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer3);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.parse("2025-08-09", formatter));
        rental3.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental3.setBackDate(LocalDate.parse("2025-08-09", formatter));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() {
        // Test Case 5: Calculate total revenue from rentals with all rentals for only one day
        
        // Create cars
        Car car1 = new Car();
        car1.setPlate("MINI001");
        car1.setModel("Mini Cooper");
        car1.setDailyPrice(180.0);
        
        Car car2 = new Car();
        car2.setPlate("MOTO002");
        car2.setModel("Harley Davidson");
        car2.setDailyPrice(220.0);
        
        // Create customers
        Customer customer1 = new Customer();
        customer1.setName("Grace");
        customer1.setSurname("Anderson");
        customer1.setAddress("666 Sixth St");
        
        Customer customer2 = new Customer();
        customer2.setName("Henry");
        customer2.setSurname("Thomas");
        customer2.setAddress("777 Seventh Ave");
        
        // Create and configure rentals
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.parse("2025-08-12", formatter));
        rental1.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental1.setBackDate(LocalDate.parse("2025-08-12", formatter));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer2);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.parse("2025-08-09", formatter));
        rental2.setDueDate(LocalDate.parse("2025-09-01", formatter));
        rental2.setBackDate(LocalDate.parse("2025-08-09", formatter));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}