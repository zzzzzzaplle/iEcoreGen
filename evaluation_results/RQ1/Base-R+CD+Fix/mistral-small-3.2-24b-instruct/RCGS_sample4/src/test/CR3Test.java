import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public void testCase1_SingleOverdueRental() throws Exception {
        // Create customer C001
        Customer customer = new Customer("John", "Doe", "123 Main St", null);
        
        // Create car
        Car car = new Car("ABC123", "Toyota", 50.0);
        
        // Create rental with due date in the past and no back date
        Date dueDate = dateFormat.parse("2023-10-01 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        Rental rental = new Rental(null, dueDate, null, 200.0, "Standard", car, customer);
        
        // Set up store
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertEquals("Should have one overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be John Doe", customer, overdueCustomers.get(0));
        assertEquals("Should have one overdue notice", 1, store.getNotices().size());
        assertEquals("Notice should be for John Doe", customer, store.getNotices().get(0).getCustomer());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = new Customer("Jane", "Smith", "456 Oak St", null);
        
        // Create car
        Car car = new Car("DEF456", "Honda", 60.0);
        
        // Create rental with future due date and no back date
        Date dueDate = dateFormat.parse("2025-10-10 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        Rental rental = new Rental(null, dueDate, null, 300.0, "Standard", car, customer);
        
        // Set up store
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results - no overdue customers
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
        assertTrue("Should have no overdue notices", store.getNotices().isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = new Customer("Alice", "Johnson", "789 Pine St", null);
        
        // Create cars
        Car car1 = new Car("GHI789", "Ford", 70.0);
        Car car2 = new Car("JKL012", "Chevrolet", 80.0);
        
        // Create first rental (overdue)
        Date overdueDueDate = dateFormat.parse("2023-10-03 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        Rental overdueRental = new Rental(null, overdueDueDate, null, 280.0, "Standard", car1, customer);
        
        // Create second rental (not overdue - already returned)
        Date returnedDueDate = dateFormat.parse("2024-10-02 00:00:00");
        Date backDate = dateFormat.parse("2024-10-01 00:00:00");
        Rental returnedRental = new Rental(null, returnedDueDate, backDate, 320.0, "Standard", car2, customer);
        
        // Set up store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(overdueRental);
        rentals.add(returnedRental);
        store.setRentals(rentals);
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results - should have one overdue customer (Alice Johnson)
        assertEquals("Should have one overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be Alice Johnson", customer, overdueCustomers.get(0));
        assertEquals("Should have one overdue notice", 1, store.getNotices().size());
        assertEquals("Notice should be for Alice Johnson", customer, store.getNotices().get(0).getCustomer());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer("Bob", "Brown", "321 Elm St", null);
        
        // Create car
        Car car = new Car("MNO345", "BMW", 90.0);
        
        // Create rental with back date (already returned)
        Date dueDate = dateFormat.parse("2023-10-03 00:00:00");
        Date backDate = dateFormat.parse("2023-10-04 00:00:00");
        Rental rental = new Rental(null, dueDate, backDate, 360.0, "Standard", car, customer);
        
        // Set up store
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Execute method under test with current date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results - no overdue customers since rental was returned
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
        assertTrue("Should have no overdue notices", store.getNotices().isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer("Charlie", "Green", "654 Maple St", null);
        
        // Create car
        Car car = new Car("PQR678", "Audi", 100.0);
        
        // Create rental with future due date
        Date dueDate = dateFormat.parse("2025-10-15 00:00:00");
        Rental rental = new Rental(null, dueDate, null, 400.0, "Standard", car, customer);
        
        // Set up store
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Execute method under test with current date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results - no overdue customers since due date is in future
        assertTrue("Should have no overdue customers", overdueCustomers.isEmpty());
        assertTrue("Should have no overdue notices", store.getNotices().isEmpty());
    }
}