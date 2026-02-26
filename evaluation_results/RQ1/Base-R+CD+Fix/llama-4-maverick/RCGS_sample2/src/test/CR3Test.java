import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        // Create customer C001
        Customer customer = new Customer("John", "Doe", "123 Main St", "C001");
        
        // Create car for rental
        Car car = new Car("ABC123", "Toyota Camry", 50.0);
        
        // Create rental with backDate: null and dueDate: "2023-10-01"
        Date dueDate = dateFormat.parse("2023-10-01");
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dueDate);
        rental.setBackDate(null);
        
        // Add car and rental to store
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Check for overdue rentals with current date "2023-10-05"
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals("2023-10-05");
        
        // Verify customer C001 is in overdue list
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws ParseException {
        // Create customer C002
        Customer customer = new Customer("Jane", "Smith", "456 Oak St", "C002");
        
        // Create car for rental
        Car car = new Car("DEF456", "Honda Civic", 40.0);
        
        // Create rental with backDate: null and dueDate: "2025-10-10"
        Date dueDate = dateFormat.parse("2025-10-10");
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dueDate);
        rental.setBackDate(null);
        
        // Add car and rental to store
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Check for overdue rentals with current date "2023-10-12"
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals("2023-10-12");
        
        // Verify no overdue customers found
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws ParseException {
        // Create customer C003
        Customer customer = new Customer("Alice", "Johnson", "789 Pine St", "C003");
        
        // Create cars for rentals
        Car car1 = new Car("GHI789", "Ford Focus", 35.0);
        Car car2 = new Car("JKL012", "Chevrolet Malibu", 45.0);
        
        // Create first rental with backDate: null and dueDate: "2023-10-03" (overdue)
        Date dueDate1 = dateFormat.parse("2023-10-03");
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setDueDate(dueDate1);
        rental1.setBackDate(null);
        
        // Create second rental with backDate set (not overdue)
        Date dueDate2 = dateFormat.parse("2024-10-02");
        Date backDate = dateFormat.parse("2024-10-01");
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setDueDate(dueDate2);
        rental2.setBackDate(backDate);
        
        // Add cars and rentals to store
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        cars.add(car2);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);
        
        // Check for overdue rentals with current date "2023-10-05"
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals("2023-10-05");
        
        // Verify customer C003 is in overdue list due to rental1
        assertEquals(1, overdueCustomers.size());
        assertEquals(customer, overdueCustomers.get(0));
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws ParseException {
        // Create customer C004
        Customer customer = new Customer("Bob", "Brown", "101 Elm St", "C004");
        
        // Create car for rental
        Car car = new Car("MNO345", "Nissan Altima", 38.0);
        
        // Create rental with backDate set (returned on time)
        Date dueDate = dateFormat.parse("2023-10-03");
        Date backDate = dateFormat.parse("2023-10-04");
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dueDate);
        rental.setBackDate(backDate);
        
        // Add car and rental to store
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Check for overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals("2023-10-05");
        
        // Verify no overdue customers found since car was returned
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws ParseException {
        // Create customer C005
        Customer customer = new Customer("Charlie", "Green", "202 Maple St", "C005");
        
        // Create car for rental
        Car car = new Car("PQR678", "Hyundai Sonata", 42.0);
        
        // Create rental with future due date
        Date dueDate = dateFormat.parse("2025-10-15");
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dueDate);
        rental.setBackDate(null);
        
        // Add car and rental to store
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        store.setCars(cars);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        store.setRentals(rentals);
        
        // Check for overdue rentals with current date "2023-10-05"
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals("2023-10-05");
        
        // Verify no overdue customers found since due date is in future
        assertTrue(overdueCustomers.isEmpty());
    }
}