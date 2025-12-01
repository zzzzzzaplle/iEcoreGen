import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;

public class CR5Test {
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create customer C001
        Customer customer1 = new Customer("John", "Doe", "123 Main St", "ABC123", "2023-01-01", "2023-01-10");
        Customer customer2 = new Customer("John", "Doe", "123 Main St", "XYZ456", "2023-02-01", "2023-02-10");
        Customer customer3 = new Customer("John", "Doe", "123 Main St", "LMN789", "2023-03-01", "2023-03-10");
        
        // Add customers to store
        store.getCustomers().add(customer1);
        store.getCustomers().add(customer2);
        store.getCustomers().add(customer3);
        
        // Execute method under test
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result
        assertEquals(3, (int) result.get("John Doe"));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create customer C001 with 2 rentals
        Customer customer1 = new Customer("Alice", "Smith", "456 Oak St", "ABC123", "2023-01-01", "2023-01-10");
        Customer customer2 = new Customer("Alice", "Smith", "456 Oak St", "XYZ456", "2023-02-01", "2023-02-10");
        
        // Create customer C002 with 5 rentals
        Customer customer3 = new Customer("Bob", "Johnson", "789 Pine St", "LMN789", "2023-01-01", "2023-01-10");
        Customer customer4 = new Customer("Bob", "Johnson", "789 Pine St", "OPQ012", "2023-02-01", "2023-02-10");
        Customer customer5 = new Customer("Bob", "Johnson", "789 Pine St", "RST345", "2023-03-01", "2023-03-10");
        Customer customer6 = new Customer("Bob", "Johnson", "789 Pine St", "UVW678", "2023-04-01", "2023-04-10");
        Customer customer7 = new Customer("Bob", "Johnson", "789 Pine St", "JKL901", "2023-05-01", "2023-05-10");
        
        // Add customers to store
        store.getCustomers().add(customer1);
        store.getCustomers().add(customer2);
        store.getCustomers().add(customer3);
        store.getCustomers().add(customer4);
        store.getCustomers().add(customer5);
        store.getCustomers().add(customer6);
        store.getCustomers().add(customer7);
        
        // Execute method under test
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify results
        assertEquals(2, (int) result.get("Alice Smith"));
        assertEquals(5, (int) result.get("Bob Johnson"));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer C003 with no rental records
        Customer customer = new Customer("Charlie", "Brown", "321 Elm St", null, null, null);
        
        // Add customer to store
        store.getCustomers().add(customer);
        
        // Execute method under test
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - should be empty map as specified in the test case
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create customer C004 with 4 rental records
        Customer customer1 = new Customer("David", "Wilson", "654 Maple St", "DEF234", "2023-01-01", "2023-01-10");
        Customer customer2 = new Customer("David", "Wilson", "654 Maple St", "GHI567", "2023-02-01", "2023-02-10");
        Customer customer3 = new Customer("David", "Wilson", "654 Maple St", "JKL890", "2023-03-01", "2023-03-10");
        Customer customer4 = new Customer("David", "Wilson", "654 Maple St", "MNO123", "2023-04-01", "2023-04-10");
        
        // Mark 2 rentals as returned by setting backDate
        customer1.setBackDate("2023-01-05");
        customer2.setBackDate("2023-02-05");
        
        // Add customers to store
        store.getCustomers().add(customer1);
        store.getCustomers().add(customer2);
        store.getCustomers().add(customer3);
        store.getCustomers().add(customer4);
        
        // Execute method under test
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - should count all 4 rentals regardless of return status
        assertEquals(4, (int) result.get("David Wilson"));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create customer C005 with 3 rental records
        Customer customer1 = new Customer("Eva", "Davis", "987 Cedar St", "PQR456", "2023-01-01", "2023-01-10");
        Customer customer2 = new Customer("Eva", "Davis", "987 Cedar St", "STU789", "2023-02-01", "2023-02-05"); // Overdue
        Customer customer3 = new Customer("Eva", "Davis", "987 Cedar St", "VWX012", "2023-03-01", "2023-03-10");
        
        // Add customers to store
        store.getCustomers().add(customer1);
        store.getCustomers().add(customer2);
        store.getCustomers().add(customer3);
        
        // Execute method under test
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify result - should count all 3 rentals regardless of overdue status
        assertEquals(3, (int) result.get("Eva Davis"));
    }
}