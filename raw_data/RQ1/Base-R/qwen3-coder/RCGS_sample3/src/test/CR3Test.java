import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private CarRentalStore store;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // SetUp: Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("CAR001");
        car.setModel("Toyota");
        car.setDailyPrice(50.0);
        
        // SetUp: Create rental with backDate: null and dueDate: "2023-10-01"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setLeaseDate(LocalDate.parse("2023-09-20", formatter));
        rental.setDueDate(LocalDate.parse("2023-10-01", formatter));
        rental.setBackDate(null);
        
        // Add customer, car and rental to store
        store.getCustomers().add(customer);
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // SetUp: Set current date to "2023-10-05" (overdue rental)
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify customer C001 is in overdue list
        assertTrue("Customer C001 should be in overdue list", overdueCustomers.contains(customer));
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("CAR002");
        car.setModel("Honda");
        car.setDailyPrice(60.0);
        
        // SetUp: Create rental with backDate: null and dueDate: "2025-10-10"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setLeaseDate(LocalDate.parse("2023-10-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-10-10", formatter));
        rental.setBackDate(null);
        
        // Add customer, car and rental to store
        store.getCustomers().add(customer);
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // SetUp: Set current date to "2023-10-12"
        LocalDate currentDate = LocalDate.parse("2023-10-12", formatter);
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify no overdue customers found
        assertTrue("No overdue customers should be found", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // SetUp: Create cars for rentals
        Car car1 = new Car();
        car1.setPlate("CAR003A");
        car1.setModel("Ford");
        car1.setDailyPrice(55.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR003B");
        car2.setModel("BMW");
        car2.setDailyPrice(80.0);
        
        // SetUp: Create rental R003 with backDate: null and dueDate: "2023-10-03"
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setLeaseDate(LocalDate.parse("2023-09-28", formatter));
        rental1.setDueDate(LocalDate.parse("2023-10-03", formatter));
        rental1.setBackDate(null);
        
        // SetUp: Create rental R004 with backDate: "2024-10-01" and dueDate: "2024-10-02"
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setLeaseDate(LocalDate.parse("2024-09-28", formatter));
        rental2.setDueDate(LocalDate.parse("2024-10-02", formatter));
        rental2.setBackDate(LocalDate.parse("2024-10-01", formatter));
        
        // Add customer, cars and rentals to store
        store.getCustomers().add(customer);
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // SetUp: Set current date to "2023-10-05"
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify customer C003 is in overdue list (due to rental R003)
        assertTrue("Customer C003 should be in overdue list due to rental R003", 
                   overdueCustomers.contains(customer));
        assertEquals("Should have exactly 1 overdue customer", 1, overdueCustomers.size());
        
        // Verify rental R003 is overdue
        assertTrue("Rental R003 should be overdue", rental1.isOverdue(currentDate));
        
        // Verify rental R004 is not overdue
        assertFalse("Rental R004 should not be overdue", rental2.isOverdue(currentDate));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("CAR004");
        car.setModel("Mercedes");
        car.setDailyPrice(75.0);
        
        // SetUp: Create rental with backDate: "2023-10-04" and dueDate: "2023-10-03"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setLeaseDate(LocalDate.parse("2023-09-25", formatter));
        rental.setDueDate(LocalDate.parse("2023-10-03", formatter));
        rental.setBackDate(LocalDate.parse("2023-10-04", formatter));
        
        // Add customer, car and rental to store
        store.getCustomers().add(customer);
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // SetUp: Set current date to "2023-10-05"
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify no overdue customers found since car was returned (has back date)
        assertTrue("No overdue customers should be found for customer C004", 
                   overdueCustomers.isEmpty());
        
        // Verify rental is not overdue since it has a back date
        assertFalse("Rental should not be overdue since it has back date", 
                    rental.isOverdue(currentDate));
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // SetUp: Create car for rental
        Car car = new Car();
        car.setPlate("CAR005");
        car.setModel("Audi");
        car.setDailyPrice(90.0);
        
        // SetUp: Create rental with dueDate: "2025-10-15"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setLeaseDate(LocalDate.parse("2023-10-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-10-15", formatter));
        rental.setBackDate(null);
        
        // Add customer, car and rental to store
        store.getCustomers().add(customer);
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // SetUp: Set current date to "2023-10-05"
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);
        
        // Get overdue customers
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        
        // Verify no overdue customers found since due date is in future
        assertTrue("No overdue customers should be found for customer C005", 
                   overdueCustomers.isEmpty());
        
        // Verify rental is not overdue since due date is in future
        assertFalse("Rental should not be overdue since due date is in future", 
                    rental.isOverdue(currentDate));
    }
}