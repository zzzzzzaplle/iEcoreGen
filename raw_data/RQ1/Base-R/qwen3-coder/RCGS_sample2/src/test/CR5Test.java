import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.*;

public class CR5Test {
    
    private RentalStore store;
    
    @Before
    public void setUp() {
        store = new RentalStore();
    }
    
    @Test
    public void testCase1_countRentalsForSingleCustomer() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C001
        Customer customer = new Customer();
        customer.setName("C001");
        
        // Add 3 rental records for customer C001 with different car details
        Car car1 = new Car();
        car1.setPlate("ABC123");
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        Car car3 = new Car();
        car3.setPlate("LMN789");
        
        // Create rental records
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Expected Output: Number of rentals for customer C001 = 3
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_countRentalsForMultipleCustomers() {
        // SetUp: Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("C001");
        Customer customer2 = new Customer();
        customer2.setName("C002");
        
        // Add rental records for customer C001 (2 rentals)
        Car car1 = new Car();
        car1.setPlate("ABC123");
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        
        // Add rental records for customer C002 (5 rentals)
        Car car3 = new Car();
        car3.setPlate("LMN789");
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        Car car5 = new Car();
        car5.setPlate("RST345");
        Car car6 = new Car();
        car6.setPlate("UVW678");
        Car car7 = new Car();
        car7.setPlate("JKL901");
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        
        // Add all rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        store.getRentals().add(rental5);
        store.getRentals().add(rental6);
        store.getRentals().add(rental7);
        
        // Expected Output: Number of rentals for customer C001 = 2, customer C002 = 5
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_countRentalsWithNoRecords() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("C003");
        
        // No rental records are added for customer C003
        
        // Expected Output: Empty map (not null)
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_countRentalsIncludingReturnedCars() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C004
        Customer customer = new Customer();
        customer.setName("C004");
        
        // Add rental records for customer C004 (4 rentals)
        Car car1 = new Car();
        car1.setPlate("DEF234");
        Car car2 = new Car();
        car2.setPlate("GHI567");
        Car car3 = new Car();
        car3.setPlate("JKL890");
        Car car4 = new Car();
        car4.setPlate("MNO123");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        
        // Mark 2 of them as returned
        rental1.setBackDate(LocalDate.now());
        rental2.setBackDate(LocalDate.now());
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        
        // Expected Output: Number of rentals for customer C004 = 4 (all stored rentals)
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
    }
    
    @Test
    public void testCase5_countRentalsForCustomerWithOverdueCars() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("C005");
        
        // Add rental records for customer C005 (3 rentals)
        Car car1 = new Car();
        car1.setPlate("PQR456");
        Car car2 = new Car();
        car2.setPlate("STU789");
        Car car3 = new Car();
        car3.setPlate("VWX012");
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        
        // Mark one as overdue (back date null and due date in the past)
        rental2.setDueDate(LocalDate.now().minusDays(1));
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Expected Output: Number of rentals for customer C005 = 3
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Additional verification for overdue count
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }
}