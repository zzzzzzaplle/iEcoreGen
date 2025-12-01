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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
        store.addCar(car);
        
        // SetUp: Create rental R001 with backDate: null, dueDate: "2023-10-01"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2023-10-01 00:00:00"));
        store.addRental(rental);
        
        // SetUp: Set current date to "2023-10-05" (overdue rental)
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer C001 is overdue for rental R001
        assertEquals(1, overdueCustomers.size());
        assertEquals("John", overdueCustomers.get(0).getName());
        assertEquals("Doe", overdueCustomers.get(0).getSurname());
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
        store.addCar(car);
        
        // SetUp: Create rental R002 with backDate: null, dueDate: "2025-10-10"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-10 00:00:00"));
        store.addRental(rental);
        
        // SetUp: Set current date to "2023-10-12"
        Date currentDate = dateFormat.parse("2023-10-12 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue rentals found for customer ID C002
        assertTrue(overdueCustomers.isEmpty());
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
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("CAR004");
        car2.setModel("Nissan Altima");
        car2.setDailyPrice(45.0);
        store.addCar(car2);
        
        // SetUp: Create rental R003 with backDate: null, dueDate: "2023-10-03"
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(null);
        rental1.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        store.addRental(rental1);
        
        // SetUp: Create rental R004 with backDate: "2024-10-01", dueDate: "2024-10-02"
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(dateFormat.parse("2024-10-01 00:00:00"));
        rental2.setDueDate(dateFormat.parse("2024-10-02 00:00:00"));
        store.addRental(rental2);
        
        // SetUp: Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: Customer ID C003 is overdue for rental R003; Rental R004 is not overdue
        assertEquals(1, overdueCustomers.size());
        assertEquals("Alice", overdueCustomers.get(0).getName());
        assertEquals("Johnson", overdueCustomers.get(0).getSurname());
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
        car.setDailyPrice(42.0);
        store.addCar(car);
        
        // SetUp: Create rental R005 with backDate: "2023-10-04", dueDate: "2023-10-03"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(dateFormat.parse("2023-10-04 00:00:00"));
        rental.setDueDate(dateFormat.parse("2023-10-03 00:00:00"));
        store.addRental(rental);
        
        // Set current date (any date since rental has back date)
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue rentals found for customer ID C004, as rental R005 was returned
        assertTrue(overdueCustomers.isEmpty());
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
        car.setDailyPrice(38.0);
        store.addCar(car);
        
        // SetUp: Create rental R006 with dueDate: "2025-10-15"
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setBackDate(null);
        rental.setDueDate(dateFormat.parse("2025-10-15 00:00:00"));
        store.addRental(rental);
        
        // SetUp: Set current date to "2023-10-05"
        Date currentDate = dateFormat.parse("2023-10-05 00:00:00");
        
        // Execute: Find customers with overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        
        // Verify: No overdue rentals found for customer ID C005, as the due date is in the future
        assertTrue(overdueCustomers.isEmpty());
    }
    

}