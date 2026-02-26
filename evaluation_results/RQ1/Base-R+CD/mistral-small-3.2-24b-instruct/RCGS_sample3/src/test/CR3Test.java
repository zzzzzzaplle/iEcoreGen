import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
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
    public void testCase1_SingleOverdueRentalCheck() throws ParseException {
        // Create customer C001
        Customer customer = new Customer("John", "Doe", "123 Main St", "ABC123");
        
        // Create car
        Car car = new Car("ABC123", "Toyota", 50.0);
        
        // Create rental R001 with null backDate and overdue dueDate
        Date dueDate = dateFormat.parse("2023-10-01 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        Rental rental = new Rental(dateFormat.parse("2023-09-20 00:00:00"), 
                                  dueDate, 
                                  null, 
                                  500.0, 
                                  "Standard", 
                                  car, 
                                  customer);
        
        // Add to store
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Test findCustomersWithOverdueRentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C001 is in overdue list
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be C001", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws ParseException {
        // Create customer C002
        Customer customer = new Customer("Jane", "Smith", "456 Oak St", "DEF456");
        
        // Create car
        Car car = new Car("DEF456", "Honda", 60.0);
        
        // Create rental R002 with future dueDate
        Date dueDate = dateFormat.parse("2025-10-10 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        Rental rental = new Rental(dateFormat.parse("2023-10-01 00:00:00"), 
                                  dueDate, 
                                  null, 
                                  600.0, 
                                  "Premium", 
                                  car, 
                                  customer);
        
        // Add to store
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Test findCustomersWithOverdueRentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found
        assertTrue("Should return empty list for no overdue rentals", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws ParseException {
        // Create customer C003
        Customer customer = new Customer("Alice", "Johnson", "789 Pine St", "GHI789");
        
        // Create cars
        Car car1 = new Car("GHI789", "Ford", 70.0);
        Car car2 = new Car("JKL012", "Chevy", 80.0);
        
        // Create rental R003 (overdue) and R004 (returned)
        Date dueDate1 = dateFormat.parse("2023-10-03 00:00:00");
        Date dueDate2 = dateFormat.parse("2024-10-02 00:00:00");
        Date backDate2 = dateFormat.parse("2024-10-01 00:00:00");
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        Rental rental1 = new Rental(dateFormat.parse("2023-09-25 00:00:00"), 
                                   dueDate1, 
                                   null, 
                                   700.0, 
                                   "Standard", 
                                   car1, 
                                   customer);
        
        Rental rental2 = new Rental(dateFormat.parse("2024-09-28 00:00:00"), 
                                   dueDate2, 
                                   backDate2, 
                                   800.0, 
                                   "Premium", 
                                   car2, 
                                   customer);
        
        // Add to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Test findCustomersWithOverdueRentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify customer C003 is in overdue list (only R003 is overdue)
        assertEquals("Should find 1 overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be C003", customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws ParseException {
        // Create customer C004
        Customer customer = new Customer("Bob", "Brown", "101 Elm St", "MNO345");
        
        // Create car
        Car car = new Car("MNO345", "BMW", 90.0);
        
        // Create rental R005 with backDate (returned on time)
        Date dueDate = dateFormat.parse("2023-10-03 00:00:00");
        Date backDate = dateFormat.parse("2023-10-04 00:00:00");
        Rental rental = new Rental(dateFormat.parse("2023-10-01 00:00:00"), 
                                  dueDate, 
                                  backDate, 
                                  900.0, 
                                  "Luxury", 
                                  car, 
                                  customer);
        
        // Add to store
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Test findCustomersWithOverdueRentals with current date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (backDate is not null)
        assertTrue("Should return empty list when backDate is not null", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws ParseException {
        // Create customer C005
        Customer customer = new Customer("Charlie", "Green", "202 Maple St", "PQR678");
        
        // Create car
        Car car = new Car("PQR678", "Audi", 100.0);
        
        // Create rental R006 with future dueDate
        Date dueDate = dateFormat.parse("2025-10-15 00:00:00");
        Rental rental = new Rental(dateFormat.parse("2023-10-01 00:00:00"), 
                                  dueDate, 
                                  null, 
                                  1000.0, 
                                  "Executive", 
                                  car, 
                                  customer);
        
        // Add to store
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Test findCustomersWithOverdueRentals with current date before due date
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue customers found (current date before due date)
        assertTrue("Should return empty list when current date is before due date", overdueCustomers.isEmpty());
    }
}