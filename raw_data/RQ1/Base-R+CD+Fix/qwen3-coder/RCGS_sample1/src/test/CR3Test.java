import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CR3Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() throws Exception {
        // SetUp: Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("CAR001");
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        store.getCars().add(car);
        
        // SetUp: Create rental R001 with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        rental.setBackDate(null); // Not returned yet
        store.getRentals().add(rental);
        
        // Set current date to overdue date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer C001 should be in overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be John Doe", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("CAR002");
        car.setModel("Honda Civic");
        car.setDailyPrice(40.0);
        store.getCars().add(car);
        
        // SetUp: Create rental R002 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Set current date to before due date
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue customers should be found
        assertTrue("Should return empty list for no overdue rentals", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // SetUp: Create cars for rentals
        Car car1 = new Car();
        car1.setPlate("CAR003");
        car1.setModel("Ford Focus");
        car1.setDailyPrice(35.0);
        store.getCars().add(car1);
        
        Car car2 = new Car();
        car2.setPlate("CAR004");
        car2.setModel("Nissan Altima");
        car2.setDailyPrice(45.0);
        store.getCars().add(car2);
        
        // SetUp: Create rental R003 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        rental1.setBackDate(null); // Not returned - overdue
        store.getRentals().add(rental1);
        
        // SetUp: Create rental R004 (returned on time)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Returned before due date
        store.getRentals().add(rental2);
        
        // Set current date to after R003 due date but before R004 due date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer C003 should be in overdue list due to R003
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be Alice Johnson", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("CAR005");
        car.setModel("Chevrolet Malibu");
        car.setDailyPrice(55.0);
        store.getCars().add(car);
        
        // SetUp: Create rental R005 that was returned on time
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Returned after due date but has back date
        store.getRentals().add(rental);
        
        // Execute: Find customers with overdue rentals (current date doesn't matter since back date exists)
        Date currentDate = dateFormat.parse("2023-10-05");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue customers should be found since rental has back date
        assertTrue("Should return empty list when rental has back date", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("CAR006");
        car.setModel("Hyundai Elantra");
        car.setDailyPrice(42.0);
        store.getCars().add(car);
        
        // SetUp: Create rental R006 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        rental.setBackDate(null);
        store.getRentals().add(rental);
        
        // Set current date to before due date
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue customers should be found
        assertTrue("Should return empty list for future due date rentals", overdueCustomers.isEmpty());
    }
}