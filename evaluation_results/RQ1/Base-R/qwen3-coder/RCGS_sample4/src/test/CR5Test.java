import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.time.LocalDate;

public class CR5Test {
    private RentalStore store;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    
    @Before
    public void setUp() {
        store = new RentalStore();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create customer with ID C001
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        // Add customer to store
        store.getCustomers().add(customer1);
        
        // Create rental records for customer C001 with different car details
        Rental rental1 = new Rental();
        rental1.setCustomerName("John");
        rental1.setCustomerSurname("Doe");
        rental1.setCarPlate("ABC123");
        
        Rental rental2 = new Rental();
        rental2.setCustomerName("John");
        rental2.setCustomerSurname("Doe");
        rental2.setCarPlate("XYZ456");
        
        Rental rental3 = new Rental();
        rental3.setCustomerName("John");
        rental3.setCustomerSurname("Doe");
        rental3.setCarPlate("LMN789");
        
        // Add rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        
        // Verify the result
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer1));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create customer C001
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        // Create customer C002
        customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        // Add customers to store
        store.getCustomers().add(customer1);
        store.getCustomers().add(customer2);
        
        // Add rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomerName("John");
        rental1.setCustomerSurname("Doe");
        rental1.setCarPlate("ABC123");
        
        Rental rental2 = new Rental();
        rental2.setCustomerName("John");
        rental2.setCustomerSurname("Doe");
        rental2.setCarPlate("XYZ456");
        
        // Add rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomerName("Jane");
        rental3.setCustomerSurname("Smith");
        rental3.setCarPlate("LMN789");
        
        Rental rental4 = new Rental();
        rental4.setCustomerName("Jane");
        rental4.setCustomerSurname("Smith");
        rental4.setCarPlate("OPQ012");
        
        Rental rental5 = new Rental();
        rental5.setCustomerName("Jane");
        rental5.setCustomerSurname("Smith");
        rental5.setCarPlate("RST345");
        
        Rental rental6 = new Rental();
        rental6.setCustomerName("Jane");
        rental6.setCustomerSurname("Smith");
        rental6.setCarPlate("UVW678");
        
        Rental rental7 = new Rental();
        rental7.setCustomerName("Jane");
        rental7.setCustomerSurname("Smith");
        rental7.setCarPlate("JKL901");
        
        // Add all rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        store.getRentals().add(rental5);
        store.getRentals().add(rental6);
        store.getRentals().add(rental7);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        
        // Verify the result
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer with ID C003
        customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        // Add customer to store
        store.getCustomers().add(customer3);
        
        // No rental records are added
        
        // Execute the method under test
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        
        // Verify the result - should be an empty map, not null
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create customer with ID C004
        customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        
        // Add customer to store
        store.getCustomers().add(customer4);
        
        // Add rental records for customer C004 (4 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomerName("Alice");
        rental1.setCustomerSurname("Brown");
        rental1.setCarPlate("DEF234");
        
        Rental rental2 = new Rental();
        rental2.setCustomerName("Alice");
        rental2.setCustomerSurname("Brown");
        rental2.setCarPlate("GHI567");
        
        Rental rental3 = new Rental();
        rental3.setCustomerName("Alice");
        rental3.setCustomerSurname("Brown");
        rental3.setCarPlate("JKL890");
        
        Rental rental4 = new Rental();
        rental4.setCustomerName("Alice");
        rental4.setCustomerSurname("Brown");
        rental4.setCarPlate("MNO123");
        
        // Mark 2 of them as returned
        rental1.setBackDate(LocalDate.now());
        rental2.setBackDate(LocalDate.now());
        
        // Add all rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        store.getRentals().add(rental4);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        
        // Verify the result - should count all 4 rentals regardless of return status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer4));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create customer with ID C005
        customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Wilson");
        
        // Add customer to store
        store.getCustomers().add(customer5);
        
        // Add rental records for customer C005 (3 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomerName("Charlie");
        rental1.setCustomerSurname("Wilson");
        rental1.setCarPlate("PQR456");
        
        Rental rental2 = new Rental();
        rental2.setCustomerName("Charlie");
        rental2.setCustomerSurname("Wilson");
        rental2.setCarPlate("STU789");
        
        Rental rental3 = new Rental();
        rental3.setCustomerName("Charlie");
        rental3.setCustomerSurname("Wilson");
        rental2.setDueDate(LocalDate.now().minusDays(1)); // Make one overdue
        rental3.setCustomerName("Charlie");
        rental3.setCustomerSurname("Wilson");
        rental3.setCarPlate("VWX012");
        
        // Add all rentals to store
        store.getRentals().add(rental1);
        store.getRentals().add(rental2);
        store.getRentals().add(rental3);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        
        // Verify the result - should count all 3 rentals regardless of overdue status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer5));
    }
}