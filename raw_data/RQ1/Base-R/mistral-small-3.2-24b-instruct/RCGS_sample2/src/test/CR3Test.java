import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR3Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // Set up customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // Set up car
        Car car = new Car();
        car.setPlate("CAR001");
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        
        // Set up rental R001 with overdue condition
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setStartDate(LocalDate.of(2023, 9, 20));
        rental.setDueDate(LocalDate.of(2023, 10, 1)); // Overdue date
        rental.setBackDate(null); // Not returned
        
        // Add to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (past due date)
        // Note: In real implementation, we'd need to mock LocalDate.now()
        // For this test, we rely on the fact that dueDate.isBefore(LocalDate.now()) will be true
        
        // Execute method under test
        List<Customer> overdueCustomers = store.listOverdueCustomers();
        
        // Verify results
        assertEquals("Should find one overdue customer", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be John Doe", "John Doe", overdueCustomers.get(0).toString());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() {
        // Set up customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        
        // Set up car
        Car car = new Car();
        car.setPlate("CAR002");
        car.setModel("Honda Civic");
        car.setDailyPrice(45.0);
        
        // Set up rental R002 with future due date
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setStartDate(LocalDate.of(2023, 10, 1));
        rental.setDueDate(LocalDate.of(2025, 10, 10)); // Future date
        rental.setBackDate(null); // Not returned but not overdue
        
        // Add to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Execute method under test
        List<Customer> overdueCustomers = store.listOverdueCustomers();
        
        // Verify results
        assertTrue("Should return empty list when no overdue rentals", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // Set up customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        
        // Set up cars
        Car car1 = new Car();
        car1.setPlate("CAR003A");
        car1.setModel("Ford Focus");
        car1.setDailyPrice(40.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR003B");
        car2.setModel("Nissan Altima");
        car2.setDailyPrice(55.0);
        
        // Set up rental R003 (overdue)
        Rental rental1 = new Rental();
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        rental1.setStartDate(LocalDate.of(2023, 9, 25));
        rental1.setDueDate(LocalDate.of(2023, 10, 3)); // Past due
        rental1.setBackDate(null); // Not returned
        
        // Set up rental R004 (returned, not overdue)
        Rental rental2 = new Rental();
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        rental2.setStartDate(LocalDate.of(2024, 9, 28));
        rental2.setDueDate(LocalDate.of(2024, 10, 2));
        rental2.setBackDate(LocalDate.of(2024, 10, 1)); // Returned early
        
        // Add to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Execute method under test
        List<Customer> overdueCustomers = store.listOverdueCustomers();
        
        // Verify results - customer should be listed once for the overdue rental
        assertEquals("Should find one customer with overdue rental", 1, overdueCustomers.size());
        assertEquals("Overdue customer should be Alice Johnson", "Alice Johnson", overdueCustomers.get(0).toString());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() {
        // Set up customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        
        // Set up car
        Car car = new Car();
        car.setPlate("CAR004");
        car.setModel("Chevrolet Malibu");
        car.setDailyPrice(48.0);
        
        // Set up rental R005 with back date (returned)
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setStartDate(LocalDate.of(2023, 9, 28));
        rental.setDueDate(LocalDate.of(2023, 10, 3));
        rental.setBackDate(LocalDate.of(2023, 10, 4)); // Returned (even if late, has back date so not overdue)
        
        // Add to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Execute method under test
        List<Customer> overdueCustomers = store.listOverdueCustomers();
        
        // Verify results - rentals with back date should not be considered overdue
        assertTrue("Should return empty list when rental has back date", overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() {
        // Set up customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        
        // Set up car
        Car car = new Car();
        car.setPlate("CAR005");
        car.setModel("BMW 3 Series");
        car.setDailyPrice(80.0);
        
        // Set up rental R006 with future due date
        Rental rental = new Rental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setStartDate(LocalDate.of(2023, 10, 1));
        rental.setDueDate(LocalDate.of(2025, 10, 15)); // Far future date
        rental.setBackDate(null); // Not returned but not overdue
        
        // Add to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Execute method under test
        List<Customer> overdueCustomers = store.listOverdueCustomers();
        
        // Verify results - future due dates should not be overdue
        assertTrue("Should return empty list for future due dates", overdueCustomers.isEmpty());
    }
}