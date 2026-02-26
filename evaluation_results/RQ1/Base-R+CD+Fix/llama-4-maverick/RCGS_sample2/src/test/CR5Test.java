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
        // Create customer C001
        Customer customer = new Customer("John", "Doe", "123 Main St", null);
        
        // Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        
        // Create rental dates
        Date rentalDate1 = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate1 = dateFormat.parse("2024-01-05 10:00:00");
        Date rentalDate2 = dateFormat.parse("2024-01-10 10:00:00");
        Date dueDate2 = dateFormat.parse("2024-01-15 10:00:00");
        Date rentalDate3 = dateFormat.parse("2024-01-20 10:00:00");
        Date dueDate3 = dateFormat.parse("2024-01-25 10:00:00");
        
        // Create rentals for customer C001
        Rental rental1 = new Rental(rentalDate1, dueDate1, null, 200.0, "Standard", car1, customer);
        Rental rental2 = new Rental(rentalDate2, dueDate2, null, 225.0, "Standard", car2, customer);
        Rental rental3 = new Rental(rentalDate3, dueDate3, null, 200.0, "Standard", car3, customer);
        
        // Add cars and rentals to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3));
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3));
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the count for customer C001 is 3
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer));
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create customers C001 and C002
        Customer customer1 = new Customer("John", "Doe", "123 Main St", null);
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak Ave", null);
        
        // Create cars for customer C001
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        
        // Create cars for customer C002
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        Car car4 = new Car("OPQ012", "Chevrolet Malibu", 55.0);
        Car car5 = new Car("RST345", "Nissan Altima", 48.0);
        Car car6 = new Car("UVW678", "Hyundai Elantra", 42.0);
        Car car7 = new Car("JKL901", "Kia Optima", 46.0);
        
        // Create rental dates
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-05 10:00:00");
        
        // Create rentals for customer C001 (2 rentals)
        Rental rental1 = new Rental(rentalDate, dueDate, null, 200.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate, dueDate, null, 180.0, "Standard", car2, customer1);
        
        // Create rentals for customer C002 (5 rentals)
        Rental rental3 = new Rental(rentalDate, dueDate, null, 160.0, "Standard", car3, customer2);
        Rental rental4 = new Rental(rentalDate, dueDate, null, 220.0, "Standard", car4, customer2);
        Rental rental5 = new Rental(rentalDate, dueDate, null, 192.0, "Standard", car5, customer2);
        Rental rental6 = new Rental(rentalDate, dueDate, null, 168.0, "Standard", car6, customer2);
        Rental rental7 = new Rental(rentalDate, dueDate, null, 184.0, "Standard", car7, customer2);
        
        // Add cars and rentals to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4, car5, car6, car7));
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3, rental4, rental5, rental6, rental7));
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify counts for both customers
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer C003
        Customer customer = new Customer("Bob", "Johnson", "789 Pine St", null);
        
        // Add customer to store (but no rentals)
        // Note: The store doesn't maintain a list of customers, only rentals reference customers
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the result is an empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // Create customer C004
        Customer customer = new Customer("Alice", "Brown", "321 Elm St", null);
        
        // Create cars
        Car car1 = new Car("DEF234", "Toyota Corolla", 35.0);
        Car car2 = new Car("GHI567", "Honda Accord", 52.0);
        Car car3 = new Car("JKL890", "Ford Fusion", 47.0);
        Car car4 = new Car("MNO123", "Chevrolet Impala", 58.0);
        
        // Create rental dates
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-05 10:00:00");
        Date backDate = dateFormat.parse("2024-01-04 10:00:00");
        
        // Create 4 rentals for customer C004, mark 2 as returned
        Rental rental1 = new Rental(rentalDate, dueDate, backDate, 140.0, "Standard", car1, customer);
        Rental rental2 = new Rental(rentalDate, dueDate, backDate, 208.0, "Standard", car2, customer);
        Rental rental3 = new Rental(rentalDate, dueDate, null, 188.0, "Standard", car3, customer);
        Rental rental4 = new Rental(rentalDate, dueDate, null, 232.0, "Standard", car4, customer);
        
        // Add cars and rentals to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4));
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3, rental4));
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the count for customer C004 is 4 (all rentals count, regardless of return status)
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create customer C005
        Customer customer = new Customer("Charlie", "Wilson", "654 Maple Dr", null);
        
        // Create cars
        Car car1 = new Car("PQR456", "Toyota Camry", 50.0);
        Car car2 = new Car("STU789", "Honda Civic", 45.0);
        Car car3 = new Car("VWX012", "Ford Focus", 40.0);
        
        // Create rental dates - one overdue
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDateNormal = dateFormat.parse("2024-01-10 10:00:00");
        Date dueDateOverdue = dateFormat.parse("2024-01-05 10:00:00"); // Overdue date
        Date currentDate = dateFormat.parse("2024-01-08 10:00:00"); // Current date after overdue date
        
        // Create 3 rentals for customer C005, with one overdue
        Rental rental1 = new Rental(rentalDate, dueDateNormal, null, 450.0, "Standard", car1, customer);
        Rental rental2 = new Rental(rentalDate, dueDateOverdue, null, 180.0, "Standard", car2, customer); // Overdue
        Rental rental3 = new Rental(rentalDate, dueDateNormal, null, 360.0, "Standard", car3, customer);
        
        // Add cars and rentals to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3));
        store.getRentals().addAll(Arrays.asList(rental1, rental2, rental3));
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the count for customer C005 is 3 (all rentals count, regardless of overdue status)
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Additional verification: Check for overdue rentals
        List<Customer> overdueCustomers = store.findCustomersWithOverdueRentals("2024-01-08 10:00:00");
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }
}