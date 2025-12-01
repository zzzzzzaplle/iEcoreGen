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
        // SetUp
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        Car car = new Car();
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01 00:00:00"));
        
        store.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
        assertEquals(1, store.getNotices().size());
        assertEquals(customer, store.getNotices().get(0).getCustomer());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // SetUp
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        Car car = new Car();
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10 00:00:00"));
        
        store.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        
        // Execute
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify
        assertTrue(overdueCustomers.isEmpty());
        assertTrue(store.getNotices().isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // SetUp
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        Car car1 = new Car();
        Car car2 = new Car();
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(dateFormat.parse("2024-10-01 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-10-02 00:00:00"));
        
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
        assertEquals(1, store.getNotices().size());
        assertEquals(customer, store.getNotices().get(0).getCustomer());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // SetUp
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        Car car = new Car();
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(dateFormat.parse("2023-10-04 00:00:00"));
        rental.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        
        store.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify
        assertTrue(overdueCustomers.isEmpty());
        assertTrue(store.getNotices().isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // SetUp
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        Car car = new Car();
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-15 00:00:00"));
        
        store.getRentals().add(rental);
        
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify
        assertTrue(overdueCustomers.isEmpty());
        assertTrue(store.getNotices().isEmpty());
    }
}