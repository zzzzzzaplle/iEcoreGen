import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR3Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() throws ParseException {
        // SetUp: Create customer C001
        Customer customer = new Customer();
        customer.setName("John Doe");
        
        // SetUp: Create rental R001 for customer C001
        Car car = new Car();
        car.setPlate("CAR001");
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        rental.setBackDate(null);
        
        store.getRentals().add(rental);
        
        // SetUp: Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer ID C001 is overdue for rental R001
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws ParseException {
        // SetUp: Create customer C002
        Customer customer = new Customer();
        customer.setName("Jane Smith");
        
        // SetUp: Create rental R002 for customer C002
        Car car = new Car();
        car.setPlate("CAR002");
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        rental.setBackDate(null);
        
        store.getRentals().add(rental);
        
        // SetUp: Set current date to "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue rentals found for customer ID C002
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws ParseException {
        // SetUp: Create customer C003
        Customer customer = new Customer();
        customer.setName("Alice Johnson");
        
        // SetUp: Create overdue rental R003 for customer C003
        Car car1 = new Car();
        car1.setPlate("CAR003");
        
        Rental rentalOverdue = new Rental();
        rentalOverdue.setCustomer(customer);
        rentalOverdue.setCar(car1);
        rentalOverdue.setDueDate(dateFormat.parse("2023-10-03"));
        rentalOverdue.setBackDate(null);
        
        // SetUp: Create returned rental R004 for customer C003
        Car car2 = new Car();
        car2.setPlate("CAR004");
        
        Rental rentalReturned = new Rental();
        rentalReturned.setCustomer(customer);
        rentalReturned.setCar(car2);
        rentalReturned.setDueDate(dateFormat.parse("2024-10-02"));
        rentalReturned.setBackDate(dateFormat.parse("2024-10-01"));
        
        store.getRentals().add(rentalOverdue);
        store.getRentals().add(rentalReturned);
        
        // SetUp: Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer ID C003 is overdue for rental R003
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws ParseException {
        // SetUp: Create customer C004
        Customer customer = new Customer();
        customer.setName("Bob Brown");
        
        // SetUp: Create rental R005 for customer C004 (returned on time)
        Car car = new Car();
        car.setPlate("CAR005");
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Returned after due date
        
        store.getRentals().add(rental);
        
        // Execute: Find customers with overdue rentals (current date doesn't matter for this case)
        Date currentDate = dateFormat.parse("2023-10-05");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue rentals found for customer ID C004
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws ParseException {
        // SetUp: Create customer C005
        Customer customer = new Customer();
        customer.setName("Charlie Green");
        
        // SetUp: Create rental R006 for customer C005 with future due date
        Car car = new Car();
        car.setPlate("CAR006");
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        rental.setBackDate(null);
        
        store.getRentals().add(rental);
        
        // SetUp: Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue rentals found for customer ID C005
        assertTrue(overdueCustomers.isEmpty());
    }
}