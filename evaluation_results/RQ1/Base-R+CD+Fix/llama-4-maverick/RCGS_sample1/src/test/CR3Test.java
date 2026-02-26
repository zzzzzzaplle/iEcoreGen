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
    public void testCase1_SingleOverdueRentalCheck() throws Exception {
        // Create customer C001
        Customer customer = new Customer("John", "Doe", "123 Main St", "C001");
        
        // Create car for rental
        Car car = new Car("ABC123", "Toyota Camry", 50.0);
        
        // Create rental with backDate: null and dueDate: 2023-10-01
        Date dueDate = dateFormat.parse("2023-10-01 00:00:00");
        Rental rental = new Rental(null, dueDate, null, 200.0, "Standard", car, customer);
        
        // Set up store with the rental
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Set current date to 2023-10-05 (overdue)
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C001 is overdue
        assertEquals(1, overdueCustomers.size());
        assertEquals("C001", overdueCustomers.get(0).getRentedCarPlate());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Create customer C002
        Customer customer = new Customer("Jane", "Smith", "456 Oak St", "C002");
        
        // Create car for rental
        Car car = new Car("DEF456", "Honda Civic", 40.0);
        
        // Create rental with backDate: null and dueDate: 2025-10-10 (future date)
        Date dueDate = dateFormat.parse("2025-10-10 00:00:00");
        Rental rental = new Rental(null, dueDate, null, 300.0, "Premium", car, customer);
        
        // Set up store with the rental
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Set current date to 2023-10-12
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Create customer C003
        Customer customer = new Customer("Alice", "Johnson", "789 Pine St", "C003");
        
        // Create cars for rentals
        Car car1 = new Car("GHI789", "Ford Focus", 35.0);
        Car car2 = new Car("JKL012", "Chevrolet Malibu", 45.0);
        
        // Create first rental (overdue): backDate: null, dueDate: 2023-10-03
        Date dueDate1 = dateFormat.parse("2023-10-03 00:00:00");
        Rental rental1 = new Rental(null, dueDate1, null, 175.0, "Basic", car1, customer);
        
        // Create second rental (not overdue): backDate: 2024-10-01, dueDate: 2024-10-02
        Date dueDate2 = dateFormat.parse("2024-10-02 00:00:00");
        Date backDate2 = dateFormat.parse("2024-10-01 00:00:00");
        Rental rental2 = new Rental(null, dueDate2, backDate2, 225.0, "Standard", car2, customer);
        
        // Set up store with both rentals
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C003 is overdue for rental1 only
        assertEquals(1, overdueCustomers.size());
        assertEquals("C003", overdueCustomers.get(0).getRentedCarPlate());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Create customer C004
        Customer customer = new Customer("Bob", "Brown", "101 Maple St", "C004");
        
        // Create car for rental
        Car car = new Car("MNO345", "Nissan Altima", 38.0);
        
        // Create rental with backDate: 2023-10-04, dueDate: 2023-10-03
        Date dueDate = dateFormat.parse("2023-10-03 00:00:00");
        Date backDate = dateFormat.parse("2023-10-04 00:00:00");
        Rental rental = new Rental(null, dueDate, backDate, 190.0, "Standard", car, customer);
        
        // Set up store with the rental
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found since car was returned
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Create customer C005
        Customer customer = new Customer("Charlie", "Green", "202 Elm St", "C005");
        
        // Create car for rental
        Car car = new Car("PQR678", "Hyundai Sonata", 42.0);
        
        // Create rental with dueDate: 2025-10-15 (future date)
        Date dueDate = dateFormat.parse("2025-10-15 00:00:00");
        Rental rental = new Rental(null, dueDate, null, 210.0, "Premium", car, customer);
        
        // Set up store with the rental
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found since due date is in future
        assertTrue(overdueCustomers.isEmpty());
    }
}