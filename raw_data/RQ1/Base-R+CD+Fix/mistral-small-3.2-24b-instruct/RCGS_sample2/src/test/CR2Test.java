import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() throws ParseException {
        // Test Case 1: Single Rental Calculation
        // SetUp: Create a store instance and add multiple rental records
        
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
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        Customer customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        // Create rentals
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-11-10 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental1.setTotalPrice(300.0); // 100 * 3 days
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-11-10 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-11-11 00:00:00"));
        rental2.setTotalPrice(300.0); // 150 * 2 days
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental3.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental3.setBackDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental3.setTotalPrice(200.0); // 200 * 1 day
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Test Case 2: No Rentals Calculation
        // SetUp: Create a store instance with no rental records
        
        // Verify there are no Rental objects added
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = 0 CNY
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws ParseException {
        // Test Case 3: Multiple Rentals with Same Daily Price
        // SetUp: Create a store instance and add rentals with same daily price
        
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
        
        Customer customer2 = new Customer();
        customer2.setName("Charlie");
        customer2.setSurname("Wilson");
        
        // Create rentals
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental1.setTotalPrice(240.0); // 120 * 2 days
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-12-01 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-11-15 00:00:00"));
        rental2.setTotalPrice(480.0); // 120 * 4 days
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws ParseException {
        // Test Case 4: Mixed Prices Calculation
        // SetUp: Create a store instance and add rentals with varied daily prices
        
        // Create cars with different daily prices
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
        customer1.setSurname("Lee");
        
        Customer customer2 = new Customer();
        customer2.setName("Emma");
        customer2.setSurname("Davis");
        
        Customer customer3 = new Customer();
        customer3.setName("Frank");
        customer3.setSurname("Miller");
        
        // Create rentals
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-08-13 00:00:00"));
        rental1.setTotalPrice(450.0); // 90 * 5 days
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-08-11 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2026-01-01 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-08-13 00:00:00"));
        rental2.setTotalPrice(450.0); // 150 * 3 days
        
        Rental rental3 = new Rental();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental3.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental3.setBackDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental3.setTotalPrice(250.0); // 250 * 1 day
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws ParseException {
        // Test Case 5: One-Day Rental Calculation
        // SetUp: Create a store instance and add rentals for only one day
        
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
        customer1.setSurname("Taylor");
        
        Customer customer2 = new Customer();
        customer2.setName("Henry");
        customer2.setSurname("Clark");
        
        // Create rentals
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-08-12 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-08-12 00:00:00"));
        rental1.setTotalPrice(180.0); // 180 * 1 day
        
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental2.setTotalPrice(220.0); // 220 * 1 day
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Expected Output: Total revenue = (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, totalRevenue, 0.001);
    }
}