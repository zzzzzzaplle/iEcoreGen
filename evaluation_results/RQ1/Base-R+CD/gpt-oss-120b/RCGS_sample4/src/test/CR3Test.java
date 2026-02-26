import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CR3Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() throws Exception {
        // Create customer C001: John Doe
        Customer customer1 = new Customer("John", "Doe", "123 Main St", null);
        
        // Create car for rental
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        
        // Create rental R001 with backDate: null, dueDate: 2023-10-01
        Date dueDate1 = dateFormat.parse("2023-10-01 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setDueDate(dueDate1);
        rental1.setBackDate(null);
        
        // Add car and rental to store
        store.addCar(car1);
        store.addRental(rental1);
        
        // Test findCustomersWithOverdueRentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C001 is in overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C001 should be in overdue list", overdueCustomers.contains(customer1));
        
        // Test generateOverdueNotices
        store.generateOverdueNotices(currentDate);
        List<OverdueNotice> notices = store.getNotices();
        
        // Verify notice was generated for customer C001
        assertEquals("Should generate 1 overdue notice", 1, notices.size());
        assertEquals("Notice should be for customer C001", customer1, notices.get(0).getCustomer());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002: Jane Smith
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak St", null);
        
        // Create car for rental
        Car car2 = new Car("DEF456", "Honda Civic", 45.0);
        
        // Create rental R002 with backDate: null, dueDate: 2025-10-10 (future date)
        Date dueDate2 = dateFormat.parse("2025-10-10 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        Rental rental2 = new Rental();
        rental2.setCustomer(customer2);
        rental2.setCar(car2);
        rental2.setDueDate(dueDate2);
        rental2.setBackDate(null);
        
        // Add car and rental to store
        store.addCar(car2);
        store.addRental(rental2);
        
        // Test findCustomersWithOverdueRentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertTrue("Should return empty list when no overdue rentals", overdueCustomers.isEmpty());
        
        // Test generateOverdueNotices
        store.generateOverdueNotices(currentDate);
        List<OverdueNotice> notices = store.getNotices();
        
        // Verify no notices generated
        assertTrue("Should generate no overdue notices", notices.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003: Alice Johnson
        Customer customer3 = new Customer("Alice", "Johnson", "789 Pine St", null);
        
        // Create cars for rentals
        Car car3a = new Car("GHI789", "Ford Focus", 40.0);
        Car car3b = new Car("JKL012", "Chevrolet Malibu", 55.0);
        
        // Create rental R003 (overdue): backDate: null, dueDate: 2023-10-03
        Date dueDate3a = dateFormat.parse("2023-10-03 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        Rental rental3a = new Rental();
        rental3a.setCustomer(customer3);
        rental3a.setCar(car3a);
        rental3a.setDueDate(dueDate3a);
        rental3a.setBackDate(null);
        
        // Create rental R004 (not overdue): backDate: 2024-10-01, dueDate: 2024-10-02
        Date dueDate3b = dateFormat.parse("2024-10-02 00:00:00");
        Date backDate3b = dateFormat.parse("2024-10-01 00:00:00");
        Rental rental3b = new Rental();
        rental3b.setCustomer(customer3);
        rental3b.setCar(car3b);
        rental3b.setDueDate(dueDate3b);
        rental3b.setBackDate(backDate3b);
        
        // Add cars and rentals to store
        store.addCar(car3a);
        store.addCar(car3b);
        store.addRental(rental3a);
        store.addRental(rental3b);
        
        // Test findCustomersWithOverdueRentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C003 is in overdue list (only for rental R003)
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertTrue("Customer C003 should be in overdue list", overdueCustomers.contains(customer3));
        
        // Test generateOverdueNotices
        store.generateOverdueNotices(currentDate);
        List<OverdueNotice> notices = store.getNotices();
        
        // Verify notice was generated for customer C003
        assertEquals("Should generate 1 overdue notice", 1, notices.size());
        assertEquals("Notice should be for customer C003", customer3, notices.get(0).getCustomer());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004: Bob Brown
        Customer customer4 = new Customer("Bob", "Brown", "321 Elm St", null);
        
        // Create car for rental
        Car car4 = new Car("MNO345", "Nissan Altima", 48.0);
        
        // Create rental R005 with backDate: 2023-10-04, dueDate: 2023-10-03
        Date dueDate4 = dateFormat.parse("2023-10-03 00:00:00");
        Date backDate4 = dateFormat.parse("2023-10-04 00:00:00");
        Rental rental4 = new Rental();
        rental4.setCustomer(customer4);
        rental4.setCar(car4);
        rental4.setDueDate(dueDate4);
        rental4.setBackDate(backDate4);
        
        // Add car and rental to store
        store.addCar(car4);
        store.addRental(rental4);
        
        // Test findCustomersWithOverdueRentals with current date after back date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (car was returned)
        assertTrue("Should return empty list when rental has back date", overdueCustomers.isEmpty());
        
        // Test generateOverdueNotices
        store.generateOverdueNotices(currentDate);
        List<OverdueNotice> notices = store.getNotices();
        
        // Verify no notices generated
        assertTrue("Should generate no overdue notices", notices.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005: Charlie Green
        Customer customer5 = new Customer("Charlie", "Green", "654 Maple St", null);
        
        // Create car for rental
        Car car5 = new Car("PQR678", "Hyundai Sonata", 42.0);
        
        // Create rental R006 with dueDate: 2025-10-15 (future date)
        Date dueDate5 = dateFormat.parse("2025-10-15 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        Rental rental5 = new Rental();
        rental5.setCustomer(customer5);
        rental5.setCar(car5);
        rental5.setDueDate(dueDate5);
        rental5.setBackDate(null);
        
        // Add car and rental to store
        store.addCar(car5);
        store.addRental(rental5);
        
        // Test findCustomersWithOverdueRentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (due date is in future)
        assertTrue("Should return empty list when due date is in future", overdueCustomers.isEmpty());
        
        // Test generateOverdueNotices
        store.generateOverdueNotices(currentDate);
        List<OverdueNotice> notices = store.getNotices();
        
        // Verify no notices generated
        assertTrue("Should generate no overdue notices", notices.isEmpty());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNullCurrentDate() {
        // Test that null current date throws IllegalArgumentException
        store.findCustomersWithOverdueRentals(null);
    }
}