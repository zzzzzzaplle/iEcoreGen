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
        // 1. Create a customer with customer ID: C001 and name: "John Doe"
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setRentedCarPlate("C001");
        
        // Create a car for the rental
        Car car = new Car();
        car.setPlate("CAR001");
        car.setModel("Toyota");
        car.setDailyPrice(50.0);
        
        // 2. Create a rental with rental ID: R001 for customer ID: C001, with backDate: null, and dueDate: "2023-10-01"
        Rental rental = new Rental();
        rental.setRentalDate(dateFormat.parse("2023-09-20 10:00:00"));
        rental.setDueDate(dateFormat.parse("2023-10-01 10:00:00"));
        rental.setBackDate(null); // Not returned yet
        rental.setTotalPrice(500.0);
        rental.setCar(car);
        rental.setCustomer(customer);
        
        // Add to store
        store.getRentals().add(rental);
        
        // 3. Set the current date to "2023-10-05" (overdue rental)
        Date currentDate = dateFormat.parse("2023-10-05 10:00:00");
        
        // 4. Create an overdue notice to customer C001
        OverdueNotice notice = new OverdueNotice();
        notice.setCustomer(customer);
        store.getNotices().add(notice);
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify expected output: Customer ID C001 is overdue for rental R001
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be John Doe", customer, overdueCustomers.get(0));
        
        // Verify the overdue notice was created
        assertEquals("Should have 1 overdue notice", 1, store.getNotices().size());
        assertEquals("Notice should be for customer C001", customer, store.getNotices().get(0).getCustomer());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // SetUp
        // 1. Create a customer with customer ID: C002 and name: "Jane Smith"
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        customer.setRentedCarPlate("C002");
        
        // Create a car for the rental
        Car car = new Car();
        car.setPlate("CAR002");
        car.setModel("Honda");
        car.setDailyPrice(60.0);
        
        // 2. Create a rental with rental ID: R002 for customer ID: C002, with backDate: null, and dueDate: "2025-10-10"
        Rental rental = new Rental();
        rental.setRentalDate(dateFormat.parse("2023-10-01 10:00:00"));
        rental.setDueDate(dateFormat.parse("2025-10-10 10:00:00")); // Future due date
        rental.setBackDate(null);
        rental.setTotalPrice(700.0);
        rental.setCar(car);
        rental.setCustomer(customer);
        
        // Add to store
        store.getRentals().add(rental);
        
        // 3. Set the current date to "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12 10:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify expected output: No overdue rentals found for customer ID C002
        assertTrue("Should return empty list for no overdue rentals", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // SetUp
        // 1. Create a customer with customer ID: C003 and name: "Alice Johnson"
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setRentedCarPlate("C003");
        
        // Create cars for the rentals
        Car car1 = new Car();
        car1.setPlate("CAR003");
        car1.setModel("Ford");
        car1.setDailyPrice(55.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR004");
        car2.setModel("BMW");
        car2.setDailyPrice(80.0);
        
        // 2. Create a rental with rental ID: R003 for customer ID: C003, with backDate: null, and dueDate: "2023-10-03"
        Rental rental1 = new Rental();
        rental1.setRentalDate(dateFormat.parse("2023-09-25 10:00:00"));
        rental1.setDueDate(dateFormat.parse("2023-10-03 10:00:00")); // Past due date
        rental1.setBackDate(null); // Not returned - overdue
        rental1.setTotalPrice(450.0);
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        
        // 3. Create another rental with rental ID: R004 for customer ID: C003, with backDate: "2024-10-01", and dueDate: "2024-10-02"
        Rental rental2 = new Rental();
        rental2.setRentalDate(dateFormat.parse("2024-09-28 10:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-10-02 10:00:00")); // Future due date
        rental2.setBackDate(dateFormat.parse("2024-10-01 10:00:00")); // Already returned
        rental2.setTotalPrice(320.0);
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // 4. Set the current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05 10:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify expected output: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Customer should be Alice Johnson", customer, overdueCustomers.get(0));
        
        // Verify that only rental1 is considered overdue (backDate is null and current date is past due date)
        assertNull("Rental R003 should have null backDate", rental1.getBackDate());
        assertTrue("Rental R003 should be overdue", rental1.getDueDate().before(currentDate));
        
        // Verify rental2 is not overdue (has backDate set)
        assertNotNull("Rental R004 should have backDate set", rental2.getBackDate());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // SetUp
        // 1. Create a customer with customer ID: C004 and name: "Bob Brown"
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        customer.setRentedCarPlate("C004");
        
        // Create a car for the rental
        Car car = new Car();
        car.setPlate("CAR005");
        car.setModel("Mercedes");
        car.setDailyPrice(75.0);
        
        // 2. Create a rental with rental ID: R005 for customer ID: C004, with backDate: "2023-10-04", and dueDate: "2023-10-03"
        Rental rental = new Rental();
        rental.setRentalDate(dateFormat.parse("2023-09-28 10:00:00"));
        rental.setDueDate(dateFormat.parse("2023-10-03 10:00:00"));
        rental.setBackDate(dateFormat.parse("2023-10-04 10:00:00")); // Returned after due date but backDate is set
        rental.setTotalPrice(525.0);
        rental.setCar(car);
        rental.setCustomer(customer);
        
        // Add to store
        store.getRentals().add(rental);
        
        // Set current date (any date since backDate is set)
        Date currentDate = dateFormat.parse("2023-10-05 10:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify expected output: No overdue rentals found for customer ID C004, as rental R005 was returned
        assertTrue("Should return empty list when backDate is set", overdueCustomers.isEmpty());
        
        // Verify the rental has backDate set (not null)
        assertNotNull("Rental should have backDate set", rental.getBackDate());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // SetUp
        // 1. Create a customer with customer ID: C005 and name: "Charlie Green"
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        customer.setRentedCarPlate("C005");
        
        // Create a car for the rental
        Car car = new Car();
        car.setPlate("CAR006");
        car.setModel("Audi");
        car.setDailyPrice(90.0);
        
        // 2. Create a rental with rental ID: R006 for customer ID: C005, with dueDate: "2025-10-15"
        Rental rental = new Rental();
        rental.setRentalDate(dateFormat.parse("2023-10-01 10:00:00"));
        rental.setDueDate(dateFormat.parse("2025-10-15 10:00:00")); // Far future due date
        rental.setBackDate(null);
        rental.setTotalPrice(1800.0);
        rental.setCar(car);
        rental.setCustomer(customer);
        
        // Add to store
        store.getRentals().add(rental);
        
        // 3. Set the current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05 10:00:00");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify expected output: No overdue rentals found for customer ID C005, as the due date is in the future
        assertTrue("Should return empty list for future due dates", overdueCustomers.isEmpty());
        
        // Verify the due date is in the future compared to current date
        assertTrue("Due date should be in the future", rental.getDueDate().after(currentDate));
    }
}