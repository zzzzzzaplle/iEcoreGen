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
        Store store = new Store();
        
        // Create a customer with customer ID: C001
        Customer customer = new Customer("John", "Doe", "123 Main St", "C001");
        
        // Add cars to the store
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        store.getCars().addAll(Arrays.asList(car1, car2, car3));
        
        // Add 3 rental records for customer C001 with different car details
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-05 10:00:00");
        
        Rental rental1 = new Rental(rentalDate, dueDate, null, 200.0, "Standard", car1, customer);
        Rental rental2 = new Rental(rentalDate, dueDate, null, 180.0, "Standard", car2, customer);
        Rental rental3 = new Rental(rentalDate, dueDate, null, 160.0, "Standard", car3, customer);
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(3, (int) result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // SetUp: Create a store instance
        Store store = new Store();
        
        // Create customer C001 and C002
        Customer customer1 = new Customer("John", "Doe", "123 Main St", "C001");
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak St", "C002");
        
        // Add cars to the store
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        Car car4 = new Car("OPQ012", "Chevrolet Malibu", 55.0);
        Car car5 = new Car("RST345", "Nissan Altima", 48.0);
        Car car6 = new Car("UVW678", "Hyundai Elantra", 42.0);
        Car car7 = new Car("JKL901", "Kia Optima", 46.0);
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4, car5, car6, car7));
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-05 10:00:00");
        
        Rental rental1 = new Rental(rentalDate, dueDate, null, 200.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate, dueDate, null, 180.0, "Standard", car2, customer1);
        
        Rental rental3 = new Rental(rentalDate, dueDate, null, 160.0, "Standard", car3, customer2);
        Rental rental4 = new Rental(rentalDate, dueDate, null, 220.0, "Standard", car4, customer2);
        Rental rental5 = new Rental(rentalDate, dueDate, null, 192.0, "Standard", car5, customer2);
        Rental rental6 = new Rental(rentalDate, dueDate, null, 168.0, "Standard", car6, customer2);
        Rental rental7 = new Rental(rentalDate, dueDate, null, 184.0, "Standard", car7, customer2);
        
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3, rental4, rental5, rental6, rental7));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Number of rentals for customer C001 = 2, customer C002 = 5
        assertEquals(2, result.size());
        assertTrue(result.containsKey(customer1));
        assertTrue(result.containsKey(customer2));
        assertEquals(2, (int) result.get(customer1));
        assertEquals(5, (int) result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer = new Customer("Bob", "Johnson", "789 Pine St", "C003");
        
        // No rental records are added for customer C003
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // SetUp: Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C004
        Customer customer = new Customer("Alice", "Brown", "321 Elm St", "C004");
        
        // Add cars to the store
        Car car1 = new Car("DEF234", "Toyota Corolla", 35.0);
        Car car2 = new Car("GHI567", "Honda Accord", 60.0);
        Car car3 = new Car("JKL890", "Ford Mustang", 80.0);
        Car car4 = new Car("MNO123", "Chevrolet Impala", 55.0);
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4));
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-05 10:00:00");
        Date backDate = dateFormat.parse("2024-01-04 10:00:00");
        
        Rental rental1 = new Rental(rentalDate, dueDate, backDate, 140.0, "Standard", car1, customer);
        Rental rental2 = new Rental(rentalDate, dueDate, backDate, 240.0, "Standard", car2, customer);
        Rental rental3 = new Rental(rentalDate, dueDate, null, 320.0, "Standard", car3, customer);
        Rental rental4 = new Rental(rentalDate, dueDate, null, 220.0, "Standard", car4, customer);
        
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3, rental4));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Number of rentals for customer C004 = 4 (stored)
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(4, (int) result.get(customer));
        
        // Verify that returned cars are still counted (method counts all rentals regardless of return status)
        // Currently active rentals = 2 (after marking 2 as returned) - this is verified by checking rental records
        int activeRentals = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getBackDate() == null && rental.getCustomer().equals(customer)) {
                activeRentals++;
            }
        }
        assertEquals(2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // SetUp: Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer("Charlie", "Wilson", "654 Maple St", "C005");
        
        // Add cars to the store
        Car car1 = new Car("PQR456", "BMW 3 Series", 90.0);
        Car car2 = new Car("STU789", "Mercedes C-Class", 95.0);
        Car car3 = new Car("VWX012", "Audi A4", 85.0);
        store.getCars().addAll(Arrays.asList(car1, car2, car3));
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-05 10:00:00");
        Date overdueDate = dateFormat.parse("2024-01-10 10:00:00"); // Past due date
        
        Rental rental1 = new Rental(rentalDate, dueDate, null, 360.0, "Standard", car1, customer);
        Rental rental2 = new Rental(rentalDate, dueDate, null, 380.0, "Standard", car2, customer); // This will be overdue
        Rental rental3 = new Rental(rentalDate, dueDate, null, 340.0, "Standard", car3, customer);
        
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(3, (int) result.get(customer));
        
        // Verify overdue rentals for customer C005 = 1
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(overdueDate);
        int overdueCount = 0;
        for (Customer cust : overdueCustomers) {
            if (cust.equals(customer)) {
                overdueCount++;
            }
        }
        assertEquals(1, overdueCount);
    }
}