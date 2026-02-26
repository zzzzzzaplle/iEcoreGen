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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleOverdueRentalCheck() throws Exception {
        // Set up customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        
        // Set up car
        Car car = new Car();
        car.setPlate("CAR001");
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        
        // Set up rental R001 with overdue status
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-01"));
        rental.setBackDate(null); // Not returned yet
        rental.setTotalPrice(200.0);
        
        // Add to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (after due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify results
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
        assertEquals("John", overdueCustomers.get(0).getName());
        assertEquals("Doe", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase2_NoOverdueRentals() throws Exception {
        // Set up customer C002
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        customer.setAddress("456 Oak Ave");
        
        // Set up car
        Car car = new Car();
        car.setPlate("CAR002");
        car.setModel("Honda Civic");
        car.setDailyPrice(40.0);
        
        // Set up rental R002 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-10"));
        rental.setBackDate(null); // Not returned yet, but due date is in future
        rental.setTotalPrice(300.0);
        
        // Add to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-12 (before due date)
        Date currentDate = dateFormat.parse("2023-10-12");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue rentals found
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() throws Exception {
        // Set up customer C003
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        
        // Set up cars
        Car car1 = new Car();
        car1.setPlate("CAR003");
        car1.setModel("Ford Focus");
        car1.setDailyPrice(35.0);
        
        Car car2 = new Car();
        car2.setPlate("CAR004");
        car2.setModel("Chevy Malibu");
        car2.setDailyPrice(45.0);
        
        // Set up rental R003 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setDueDate(dateFormat.parse("2023-10-03"));
        rental1.setBackDate(null); // Not returned and overdue
        rental1.setTotalPrice(150.0);
        
        // Set up rental R004 (not overdue - already returned)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setDueDate(dateFormat.parse("2024-10-02"));
        rental2.setBackDate(dateFormat.parse("2024-10-01")); // Returned before due date
        rental2.setTotalPrice(180.0);
        
        // Add to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        
        // Set current date to 2023-10-05
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify only rental R003 is overdue
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
        assertEquals("Alice", overdueCustomers.get(0).getName());
        assertEquals("Johnson", overdueCustomers.get(0).getSurname());
    }
    
    @Test
    public void testCase4_RentalWithBackDate() throws Exception {
        // Set up customer C004
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        
        // Set up car
        Car car = new Car();
        car.setPlate("CAR005");
        car.setModel("Nissan Altima");
        car.setDailyPrice(42.0);
        
        // Set up rental R005 (returned on time)
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2023-10-03"));
        rental.setBackDate(dateFormat.parse("2023-10-04")); // Returned after due date (overdue)
        rental.setTotalPrice(168.0);
        
        // Add to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Execute method under test - no current date needed since rental has back date
        Date currentDate = dateFormat.parse("2023-10-05");
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue rentals found (rental has back date set)
        assertTrue(overdueCustomers.isEmpty());
    }
    
    @Test
    public void testCase5_FutureDueDateRentals() throws Exception {
        // Set up customer C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        customer.setAddress("654 Maple Dr");
        
        // Set up car
        Car car = new Car();
        car.setPlate("CAR006");
        car.setModel("Hyundai Sonata");
        car.setDailyPrice(38.0);
        
        // Set up rental R006 with future due date
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setDueDate(dateFormat.parse("2025-10-15"));
        rental.setBackDate(null); // Not returned yet, but due date is in future
        rental.setTotalPrice(190.0);
        
        // Add to store
        store.getCars().add(car);
        store.getRentals().add(rental);
        
        // Set current date to 2023-10-05 (before due date)
        Date currentDate = dateFormat.parse("2023-10-05");
        
        // Execute method under test
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify no overdue rentals found
        assertTrue(overdueCustomers.isEmpty());
    }
}