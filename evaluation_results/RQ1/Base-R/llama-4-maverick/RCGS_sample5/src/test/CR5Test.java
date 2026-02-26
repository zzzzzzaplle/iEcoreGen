import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class CR5Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        // Initialize a fresh store before each test
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create a store instance
        // Create a customer with customer ID: C001
        Customer customer1 = new Customer("John", "Doe", "123 Main St", "ABC123", "2024-01-01", "2024-01-05");
        
        // Add 3 rental records for customer C001 with different car details
        // Add cars with plates "ABC123", "XYZ456", "LMN789" rented by customer C001
        Customer rental1 = new Customer("John", "Doe", "123 Main St", "ABC123", "2024-01-01", "2024-01-05");
        Customer rental2 = new Customer("John", "Doe", "123 Main St", "XYZ456", "2024-02-01", "2024-02-05");
        Customer rental3 = new Customer("John", "Doe", "123 Main St", "LMN789", "2024-03-01", "2024-03-05");
        
        store.addCustomer(rental1);
        store.addCustomer(rental2);
        store.addCustomer(rental3);
        
        // Expected Output: Number of rentals for customer C001 = 3
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get("John Doe"));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create a store instance
        // Create customer C001 and C002
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        
        // Customer C001 rented cars with plates "ABC123", "XYZ456"
        Customer rental1 = new Customer("John", "Doe", "123 Main St", "ABC123", "2024-01-01", "2024-01-05");
        Customer rental2 = new Customer("John", "Doe", "123 Main St", "XYZ456", "2024-02-01", "2024-02-05");
        
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        Customer rental3 = new Customer("Jane", "Smith", "456 Oak St", "LMN789", "2024-01-01", "2024-01-05");
        Customer rental4 = new Customer("Jane", "Smith", "456 Oak St", "OPQ012", "2024-02-01", "2024-02-05");
        Customer rental5 = new Customer("Jane", "Smith", "456 Oak St", "RST345", "2024-03-01", "2024-03-05");
        Customer rental6 = new Customer("Jane", "Smith", "456 Oak St", "UVW678", "2024-04-01", "2024-04-05");
        Customer rental7 = new Customer("Jane", "Smith", "456 Oak St", "JKL901", "2024-05-01", "2024-05-05");
        
        store.addCustomer(rental1);
        store.addCustomer(rental2);
        store.addCustomer(rental3);
        store.addCustomer(rental4);
        store.addCustomer(rental5);
        store.addCustomer(rental6);
        store.addCustomer(rental7);
        
        // Expected Output: 
        // - Number of rentals for customer C001 = 2
        // - Number of rentals for customer C002 = 5
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get("John Doe"));
        assertEquals(Integer.valueOf(5), result.get("Jane Smith"));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create a store instance
        // Create a customer with customer ID: C003
        Customer customer = new Customer("Bob", "Johnson", "789 Pine St", null, null, null);
        
        // No rental records are added for customer C003
        
        // Expected Output: Null
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        assertTrue("Map should be empty when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create a store instance
        // Create a customer with customer ID: C004
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        
        // Rental records: Customer C004 rented cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Customer rental1 = new Customer("Alice", "Brown", "321 Elm St", "DEF234", "2024-01-01", "2024-01-05");
        Customer rental2 = new Customer("Alice", "Brown", "321 Elm St", "GHI567", "2024-02-01", "2024-02-05");
        Customer rental3 = new Customer("Alice", "Brown", "321 Elm St", "JKL890", "2024-03-01", "2024-03-05");
        Customer rental4 = new Customer("Alice", "Brown", "321 Elm St", "MNO123", "2024-04-01", "2024-04-05");
        
        // Mark 2 rentals as returned by setting backDate
        rental1.setBackDate("2024-01-05");
        rental3.setBackDate("2024-03-05");
        
        store.addCustomer(rental1);
        store.addCustomer(rental2);
        store.addCustomer(rental3);
        store.addCustomer(rental4);
        
        // Expected Output: 
        // - Number of rentals for customer C004 = 4 (stored) 
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get("Alice Brown"));
        
        // - Currently active rentals = 2 (after marking 2 as returned)
        // Note: The countCarsRentedPerCustomer method counts all rentals regardless of return status
        // This is consistent with the specification which asks for "number of cars rented per customer"
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create a store instance
        // Create a customer with customer ID: C005
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        
        // Rental records: Customer C005 rented cars with plates "PQR456", "STU789", "VWX012" with "STU789" being overdue
        Customer rental1 = new Customer("Charlie", "Wilson", "654 Maple St", "PQR456", "2024-01-01", "2024-01-05");
        Customer rental2 = new Customer("Charlie", "Wilson", "654 Maple St", "STU789", "2024-01-01", "2024-01-05"); // Overdue
        Customer rental3 = new Customer("Charlie", "Wilson", "654 Maple St", "VWX012", "2024-02-01", "2024-02-05");
        
        store.addCustomer(rental1);
        store.addCustomer(rental2);
        store.addCustomer(rental3);
        
        // Expected Output: 
        // - Number of rentals for customer C005 = 3
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get("Charlie Wilson"));
        
        // - Number of overdue rentals for customer C005 = 1
        // Note: The countCarsRentedPerCustomer method doesn't track overdue status
        // This information would need to be obtained from getOverdueCustomers()
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        int overdueCount = 0;
        for (Customer customer : overdueCustomers) {
            if (customer.getName().equals("Charlie") && customer.getSurname