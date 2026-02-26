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
        // Note: The test specification mentions customer ID but Customer class doesn't have ID field
        // Using name/surname/address combination as identifier per equals/hashCode implementation
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("ABC123");
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        store.addCar(car);
        
        // SetUp: Create rental R001 with backDate: null, dueDate: "2023-10-01"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        rental.setBackDate(null); // Still active rental
        store.addRental(rental);
        
        // SetUp: Create overdue notice (though not directly used in the method being tested)
        OverdueNotice notice = new OverdueNotice();
        notice.setCustomer(customer);
        store.addNotice(notice);
        
        // Input: Set current date to "2023-10-05" (overdue rental)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Expected Output: Customer C001 is overdue for rental R001
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertTrue("Should contain customer C001", overdueCustomers.contains(customer));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("DEF456");
        car.setModel("Honda Civic");
        car.setDailyPrice(40.0);
        store.addCar(car);
        
        // SetUp: Create rental R002 with backDate: null, dueDate: "2025-10-10"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        rental.setBackDate(null); // Active but not overdue
        store.addRental(rental);
        
        // Input: Set current date to "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Expected Output: No overdue rentals found for customer ID C002
        assertTrue("Should return empty list when no overdue rentals", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // SetUp: Create cars for rentals
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Ford Focus");
        car1.setDailyPrice(35.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Nissan Altima");
        car2.setDailyPrice(45.0);
        store.addCar(car2);
        
        // SetUp: Create rental R003 with backDate: null, dueDate: "2023-10-03" (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        rental1.setBackDate(null); // Active and overdue
        store.addRental(rental1);
        
        // SetUp: Create rental R004 with backDate: "2024-10-01", dueDate: "2024-10-02" (returned)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Returned early
        store.addRental(rental2);
        
        // Input: Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Expected Output: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertTrue("Should contain customer C003", overdueCustomers.contains(customer));
        // Note: The method returns customers, not specific rentals, so we verify the customer is in the list
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("MNO345");
        car.setModel("Chevrolet Malibu");
        car.setDailyPrice(55.0);
        store.addCar(car);
        
        // SetUp: Create rental R005 with backDate: "2023-10-04", dueDate: "2023-10-03"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Returned late but not active
        store.addRental(rental);
        
        // Input: Current date not needed since rental has back date (not active)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Expected Output: No overdue rentals found for customer ID C004, as rental R005 was returned
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
        car.setPlate("PQR678");
        car.setModel("Hyundai Elantra");
        car.setDailyPrice(38.0);
        store.addCar(car);
        
        // SetUp: Create rental R006 with dueDate: "2025-10-15"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        rental.setBackDate(null); // Active but not overdue
        store.addRental(rental);
        
        // Input: Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Expected Output: No overdue rentals found for customer ID C005, as the due date is in the future
        assertTrue("Should return empty list when due date is in future", overdueCustomers.isEmpty());
    }
    

}