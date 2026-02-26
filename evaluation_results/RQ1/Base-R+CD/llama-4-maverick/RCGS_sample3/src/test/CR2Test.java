import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() throws Exception {
        // Create and setup Rental 1
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(100.0);
        Customer customer1 = new Customer();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-11-10 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental1.setTotalPrice(100.0 * 3); // 3 days
        
        // Create and setup Rental 2
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("XYZ789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(150.0);
        Customer customer2 = new Customer();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-11-10 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-11-11 00:00:00"));
        rental2.setTotalPrice(150.0 * 2); // 2 days
        
        // Create and setup Rental 3
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("LMN456");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(200.0);
        Customer customer3 = new Customer();
        rental3.setCar(car3);
        rental3.setCustomer(customer3);
        rental3.setRentalDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental3.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental3.setBackDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental3.setTotalPrice(200.0 * 1); // 1 day
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected result
        assertEquals(800.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Verify no rentals exist in store
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected result
        assertEquals(0.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws Exception {
        // Create and setup Rental 1
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("CAR001");
        car1.setModel("Chevrolet Malibu");
        car1.setDailyPrice(120.0);
        Customer customer1 = new Customer();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-11-13 00:00:00"));
        rental1.setTotalPrice(120.0 * 2); // 2 days
        
        // Create and setup Rental 2
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("CAR002");
        car2.setModel("Hyundai Elantra");
        car2.setDailyPrice(120.0);
        Customer customer2 = new Customer();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-11-12 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2025-12-01 00:00:00"));
        rental2.setBackDate(dateFormat.parse("2025-11-15 00:00:00"));
        rental2.setTotalPrice(120.0 * 4); // 4 days
        
        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Calculate total revenue
        double totalRevenue = store.calculateTotalRevenue();
        
        // Verify expected result
        assertEquals(720.0, totalRevenue, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws Exception {
        // Create and setup Rental 1
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("SED123");
        car1.setModel("Mazda 3");
        car1.setDailyPrice(90.0);
        Customer customer1 = new Customer();
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        rental1.setRentalDate(dateFormat.parse("2025-08-09 00:00:00"));
        rental1.setDueDate(dateFormat.parse("2025-09-01 00:00:00"));
        rental1.setBackDate(dateFormat.parse("2025-08-13 00:00:00"));
        rental1.setTotalPrice(90.0 * 5); // 5 days
        
        // Create and setup Rental 2
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("SUV456");
        car2.setModel("Kia Sportage");
        car2.setDailyPrice(150.0);
        Customer customer2 = new Customer();
        rental2.setCar(car2);
        rental2.setCustomer(customer2);
        rental2.setRentalDate(dateFormat.parse("2025-08-11 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2026-01-01 00:00:00"));
        rental2.set