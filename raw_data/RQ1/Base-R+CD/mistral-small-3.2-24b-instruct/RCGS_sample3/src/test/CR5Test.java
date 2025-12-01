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
        // Create store instance
        Store store = new Store();
        
        // Create customer with ID C001
        Customer customer = new Customer("John", "Doe", "123 Main St", "C001");
        
        // Create cars with plates "ABC123", "XYZ456", "LMN789"
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        
        // Create rental records for customer C001 with different cars
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-10 10:00:00");
        
        Rental rental1 = new Rental(rentalDate, dueDate, null, 500.0, "Standard", car1, customer);
        Rental rental2 = new Rental(rentalDate, dueDate, null, 450.0, "Standard", car2, customer);
        Rental rental3 = new Rental(rentalDate, dueDate, null, 400.0, "Standard", car3, customer);
        
        // Add cars and rentals to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3));
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create store instance
        Store store = new Store();
        
        // Create customers C001 and C002
        Customer customer1 = new Customer("John", "Doe", "123 Main St", "C001");
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak St", "C002");
        
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        Car car4 = new Car("OPQ012", "Nissan Altima", 55.0);
        Car car5 = new Car("RST345", "Chevrolet Malibu", 48.0);
        Car car6 = new Car("UVW678", "Hyundai Elantra", 42.0);
        Car car7 = new Car("JKL901", "Kia Optima", 46.0);
        
        // Create rental records
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-10 10:00:00");
        
        // Customer C001: 2 rentals
        Rental rental1 = new Rental(rentalDate, dueDate, null, 500.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate, dueDate, null, 450.0, "Standard", car2, customer1);
        
        // Customer C002: 5 rentals
        Rental rental3 = new Rental(rentalDate, dueDate, null, 400.0, "Standard", car3, customer2);
        Rental rental4 = new Rental(rentalDate, dueDate, null, 550.0, "Standard", car4, customer2);
        Rental rental5 = new Rental(rentalDate, dueDate, null, 480.0, "Standard", car5, customer2);
        Rental rental6 = new Rental(rentalDate, dueDate, null, 420.0, "Standard", car6, customer2);
        Rental rental7 = new Rental(rentalDate, dueDate, null, 460.0, "Standard", car7, customer2);
        
        // Add cars and rentals to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4, car5, car6, car7));
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3, rental4, rental5, rental6, rental7));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create store instance
        Store store = new Store();
        
        // Create customer with ID C003
        Customer customer = new Customer("Bob", "Johnson", "789 Pine St", "C003");
        
        // No rental records are added for customer C003
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result is an empty map
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // Create store instance
        Store store = new Store();
        
        // Create customer with ID C004
        Customer customer = new Customer("Alice", "Brown", "321 Elm St", "C004");
        
        // Create cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Car car1 = new Car("DEF234", "Toyota Camry", 50.0);
        Car car2 = new Car("GHI567", "Honda Civic", 45.0);
        Car car3 = new Car("JKL890", "Ford Focus", 40.0);
        Car car4 = new Car("MNO123", "Nissan Altima", 55.0);
        
        // Create rental records for customer C004 (4 rentals)
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-10 10:00:00");
        Date backDate = dateFormat.parse("2024-01-05 10:00:00");
        
        Rental rental1 = new Rental(rentalDate, dueDate, backDate, 500.0, "Standard", car1, customer);
        Rental rental2 = new Rental(rentalDate, dueDate, backDate, 450.0, "Standard", car2, customer);
        Rental rental3 = new Rental(rentalDate, dueDate, null, 400.0, "Standard", car3, customer);
        Rental rental4 = new Rental(rentalDate, dueDate, null, 550.0, "Standard", car4, customer);
        
        // Add cars and rentals to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4));
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3, rental4));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 4 rentals regardless of return status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
        
        // Verify currently active rentals (backDate is null)
        long activeRentals = store.getRentals().stream()
                .filter(rental -> rental.getCustomer().equals(customer) && rental.getBackDate() == null)
                .count();
        assertEquals(2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create store instance
        Store store = new Store();
        
        // Create customer with ID C005
        Customer customer = new Customer("Charlie", "Wilson", "654 Maple St", "C005");
        
        // Create cars with plates "PQR456", "STU789", "VWX012"
        Car car1 = new Car("PQR456", "Toyota Camry", 50.0);
        Car car2 = new Car("STU789", "Honda Civic", 45.0);
        Car car3 = new Car("VWX012", "Ford Focus", 40.0);
        
        // Create rental records for customer C005 (3 rentals)
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDatePast = dateFormat.parse("2024-01-05 10:00:00"); // Past due date
        Date dueDateFuture = dateFormat.parse("2024-01-15 10:00:00"); // Future due date
        Date currentDate = dateFormat.parse("2024-01-10 10:00:00"); // Current date
        
        // One rental is overdue (due date is past current date)
        Rental rental1 = new Rental(rentalDate, dueDatePast, null, 500.0, "Standard", car1, customer);
        Rental rental2 = new Rental(rentalDate, dueDateFuture, null, 450.0, "Standard", car2, customer);
        Rental rental3 = new Rental(rentalDate, dueDateFuture, null, 400.0, "Standard", car3, customer);
        
        // Add cars and rentals to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3));
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3));
        
        // Execute the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result - should count all 3 rentals
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Verify number of overdue rentals for customer C005
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals(currentDate);
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
        
        // Count overdue rentals specifically
        long overdueRentals = store.getRentals().stream()
                .filter(rental -> rental.getCustomer().equals(customer) && 
                                 rental.getBackDate() == null && 
                                 rental.getDueDate().before(currentDate))
                .count();
        assertEquals(1, overdueRentals);
    }
}