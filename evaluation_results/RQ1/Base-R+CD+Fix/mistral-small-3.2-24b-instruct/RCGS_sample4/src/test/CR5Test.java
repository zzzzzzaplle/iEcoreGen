import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class CR5Test {
    
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C001
        Customer customer1 = new Customer("John", "Doe", "123 Main St", "C001");
        
        // Add 3 rental records for customer C001 with different car details
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-05 10:00:00");
        
        Rental rental1 = new Rental(rentalDate, dueDate, null, 200.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate, dueDate, null, 180.0, "Standard", car2, customer1);
        Rental rental3 = new Rental(rentalDate, dueDate, null, 160.0, "Standard", car3, customer1);
        
        List<Rental> rentals = Arrays.asList(rental1, rental2, rental3);
        store.setRentals(rentals);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals("Map should contain exactly one customer", 1, result.size());
        assertTrue("Map should contain customer C001", result.containsKey(customer1));
        assertEquals("Number of rentals for customer C001 should be 3", 3, (int) result.get(customer1));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // SetUp: Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer("John", "Doe", "123 Main St", "C001");
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak St", "C002");
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        Car car4 = new Car("OPQ012", "Nissan Altima", 55.0);
        Car car5 = new Car("RST345", "Chevrolet Malibu", 48.0);
        Car car6 = new Car("UVW678", "Hyundai Elantra", 42.0);
        Car car7 = new Car("JKL901", "Kia Optima", 46.0);
        
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-05 10:00:00");
        
        // Customer C001 rentals
        Rental rental1 = new Rental(rentalDate, dueDate, null, 200.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate, dueDate, null, 180.0, "Standard", car2, customer1);
        
        // Customer C002 rentals
        Rental rental3 = new Rental(rentalDate, dueDate, null, 160.0, "Standard", car3, customer2);
        Rental rental4 = new Rental(rentalDate, dueDate, null, 220.0, "Standard", car4, customer2);
        Rental rental5 = new Rental(rentalDate, dueDate, null, 192.0, "Standard", car5, customer2);
        Rental rental6 = new Rental(rentalDate, dueDate, null, 168.0, "Standard", car6, customer2);
        Rental rental7 = new Rental(rentalDate, dueDate, null, 184.0, "Standard", car7, customer2);
        
        List<Rental> rentals = Arrays.asList(rental1, rental2, rental3, rental4, rental5, rental6, rental7);
        store.setRentals(rentals);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals("Map should contain exactly two customers", 2, result.size());
        assertTrue("Map should contain customer C001", result.containsKey(customer1));
        assertTrue("Map should contain customer C002", result.containsKey(customer2));
        assertEquals("Number of rentals for customer C001 should be 2", 2, (int) result.get(customer1));
        assertEquals("Number of rentals for customer C002 should be 5", 5, (int) result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C003
        Customer customer3 = new Customer("Bob", "Johnson", "789 Pine St", "C003");
        
        // No rental records are added for customer C003
        store.setRentals(new ArrayList<Rental>());
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should return an empty map, not null
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be an empty map", result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C004
        Customer customer4 = new Customer("Alice", "Brown", "321 Elm St", "C004");
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        Car car1 = new Car("DEF234", "Toyota Corolla", 35.0);
        Car car2 = new Car("GHI567", "Honda Accord", 60.0);
        Car car3 = new Car("JKL890", "Ford Fusion", 55.0);
        Car car4 = new Car("MNO123", "Nissan Sentra", 38.0);
        
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-05 10:00:00");
        Date backDate = dateFormat.parse("2024-01-04 10:00:00");
        
        // 2 returned rentals
        Rental rental1 = new Rental(rentalDate, dueDate, backDate, 140.0, "Standard", car1, customer4);
        Rental rental2 = new Rental(rentalDate, dueDate, backDate, 240.0, "Standard", car2, customer4);
        
        // 2 active rentals
        Rental rental3 = new Rental(rentalDate, dueDate, null, 220.0, "Standard", car3, customer4);
        Rental rental4 = new Rental(rentalDate, dueDate, null, 152.0, "Standard", car4, customer4);
        
        List<Rental> rentals = Arrays.asList(rental1, rental2, rental3, rental4);
        store.setRentals(rentals);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 4 rentals regardless of return status
        assertEquals("Map should contain exactly one customer", 1, result.size());
        assertTrue("Map should contain customer C004", result.containsKey(customer4));
        assertEquals("Number of rentals for customer C004 should be 4 (all stored rentals)", 4, (int) result.get(customer4));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C005
        Customer customer5 = new Customer("Charlie", "Wilson", "654 Maple St", "C005");
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        Car car1 = new Car("PQR456", "BMW 3 Series", 80.0);
        Car car2 = new Car("STU789", "Mercedes C-Class", 85.0);
        Car car3 = new Car("VWX012", "Audi A4", 78.0);
        
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-05 10:00:00");
        Date overdueDate = dateFormat.parse("2024-01-10 10:00:00"); // Past due date
        
        // Regular rental
        Rental rental1 = new Rental(rentalDate, dueDate, null, 320.0, "Standard", car1, customer5);
        
        // Overdue rental
        Rental rental2 = new Rental(rentalDate, dueDate, null, 340.0, "Standard", car2, customer5);
        
        // Another regular rental
        Rental rental3 = new Rental(rentalDate, dueDate, null, 312.0, "Standard", car3, customer5);
        
        List<Rental> rentals = Arrays.asList(rental1, rental2, rental3);
        store.setRentals(rentals);
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 3 rentals regardless of overdue status
        assertEquals("Map should contain exactly one customer", 1, result.size());
        assertTrue("Map should contain customer C005", result.containsKey(customer5));
        assertEquals("Number of rentals for customer C005 should be 3", 3, (int) result.get(customer5));
    }
}