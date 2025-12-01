import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CR5Test {
    
    private CarStore store;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        store = new CarStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C001
        Customer customer1 = new Customer("John", "Doe", "123 Main St");
        
        // Add 3 rental records for customer C001 with different car details
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 40.0);
        Car car3 = new Car("LMN789", "Ford Focus", 35.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        LocalDate rentDate = LocalDate.parse("2024-01-01", formatter);
        LocalDate dueDate = LocalDate.parse("2024-01-05", formatter);
        
        Rental rental1 = new Rental(customer1, car1, rentDate, dueDate);
        Rental rental2 = new Rental(customer1, car2, rentDate, dueDate);
        Rental rental3 = new Rental(customer1, car3, rentDate, dueDate);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Expected Output: Number of rentals for customer C001 = 3
        Map<Customer, Long> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer1));
        assertEquals(Long.valueOf(3), result.get(customer1));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // SetUp: Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer("John", "Doe", "123 Main St");
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak Ave");
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 40.0);
        Car car3 = new Car("LMN789", "Ford Focus", 35.0);
        Car car4 = new Car("OPQ012", "Chevrolet Malibu", 45.0);
        Car car5 = new Car("RST345", "Nissan Altima", 42.0);
        Car car6 = new Car("UVW678", "Hyundai Elantra", 38.0);
        Car car7 = new Car("JKL901", "Kia Optima", 36.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        store.addCar(car5);
        store.addCar(car6);
        store.addCar(car7);
        
        LocalDate rentDate = LocalDate.parse("2024-01-01", formatter);
        LocalDate dueDate = LocalDate.parse("2024-01-05", formatter);
        
        // Customer C001 rented cars with plates "ABC123", "XYZ456"
        Rental rental1 = new Rental(customer1, car1, rentDate, dueDate);
        Rental rental2 = new Rental(customer1, car2, rentDate, dueDate);
        
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        Rental rental3 = new Rental(customer2, car3, rentDate, dueDate);
        Rental rental4 = new Rental(customer2, car4, rentDate, dueDate);
        Rental rental5 = new Rental(customer2, car5, rentDate, dueDate);
        Rental rental6 = new Rental(customer2, car6, rentDate, dueDate);
        Rental rental7 = new Rental(customer2, car7, rentDate, dueDate);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        store.addRental(rental5);
        store.addRental(rental6);
        store.addRental(rental7);
        
        // Expected Output: Number of rentals for customer C001 = 2, customer C002 = 5
        Map<Customer, Long> result = store.countCarsRentedPerCustomer();
        assertEquals(2, result.size());
        assertTrue(result.containsKey(customer1));
        assertTrue(result.containsKey(customer2));
        assertEquals(Long.valueOf(2), result.get(customer1));
        assertEquals(Long.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C003
        Customer customer3 = new Customer("Bob", "Johnson", "789 Pine St");
        
        // No rental records are added for customer C003
        // Expected Output: Empty map (not null)
        Map<Customer, Long> result = store.countCarsRentedPerCustomer();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C004
        Customer customer4 = new Customer("Alice", "Brown", "321 Elm St");
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        Car car1 = new Car("DEF234", "Toyota Corolla", 30.0);
        Car car2 = new Car("GHI567", "Honda Accord", 55.0);
        Car car3 = new Car("JKL890", "Ford Fusion", 48.0);
        Car car4 = new Car("MNO123", "Chevrolet Impala", 52.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        
        LocalDate rentDate = LocalDate.parse("2024-01-01", formatter);
        LocalDate dueDate = LocalDate.parse("2024-01-05", formatter);
        
        Rental rental1 = new Rental(customer4, car1, rentDate, dueDate);
        Rental rental2 = new Rental(customer4, car2, rentDate, dueDate);
        Rental rental3 = new Rental(customer4, car3, rentDate, dueDate);
        Rental rental4 = new Rental(customer4, car4, rentDate, dueDate);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        
        // Mark 2 rentals as returned
        rental1.setBackDate(LocalDate.parse("2024-01-04", formatter));
        rental2.setBackDate(LocalDate.parse("2024-01-06", formatter));
        
        // Expected Output: Number of rentals for customer C004 = 4 (stored)
        Map<Customer, Long> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer4));
        assertEquals(Long.valueOf(4), result.get(customer4));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C005
        Customer customer5 = new Customer("Charlie", "Wilson", "654 Maple Dr");
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        Car car1 = new Car("PQR456", "Toyota Avalon", 60.0);
        Car car2 = new Car("STU789", "Honda CR-V", 65.0);
        Car car3 = new Car("VWX012", "Ford Explorer", 70.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        LocalDate rentDate = LocalDate.parse("2024-01-01", formatter);
        LocalDate dueDate = LocalDate.parse("2024-01-05", formatter);
        LocalDate overdueDueDate = LocalDate.parse("2024-01-03", formatter); // Past date
        
        Rental rental1 = new Rental(customer5, car1, rentDate, dueDate);
        Rental rental2 = new Rental(customer5, car2, rentDate, overdueDueDate); // Overdue
        Rental rental3 = new Rental(customer5, car3, rentDate, dueDate);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Expected Output: Number of rentals for customer C005 = 3
        Map<Customer, Long> result = store.countCarsRentedPerCustomer();
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customer5));
        assertEquals(Long.valueOf(3), result.get(customer5));
        
        // Verify overdue functionality separately
        List<Customer> overdueCustomers = store.listOverdueCustomers();
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer5));
    }
}