import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR5Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create a store instance
        // Create a customer with customer ID: C001
        Customer customer = new Customer("John", "Doe", "123 Main St");
        customer.setName("C001");
        
        // Add cars with plates "ABC123", "XYZ456", "LMN789"
        Car car1 = new Car("ABC123", "Toyota", 50.0);
        Car car2 = new Car("XYZ456", "Honda", 60.0);
        Car car3 = new Car("LMN789", "Ford", 55.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCustomer(customer);
        
        // Add 3 rental records for customer C001 with different car details
        LocalDate rentDate = LocalDate.now();
        LocalDate dueDate = rentDate.plusDays(7);
        
        store.rentCar(customer, car1, rentDate, dueDate);
        store.rentCar(customer, car2, rentDate, dueDate);
        store.rentCar(customer, car3, rentDate, dueDate);
        
        // Expected Output: Number of rentals for customer C001 = 3
        Map<Customer, Integer> result = store.getRentalCountPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer("Alice", "Smith", "456 Oak St");
        customer1.setName("C001");
        Customer customer2 = new Customer("Bob", "Johnson", "789 Pine St");
        customer2.setName("C002");
        
        // Add cars with plates "ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        Car car1 = new Car("ABC123", "Toyota", 50.0);
        Car car2 = new Car("XYZ456", "Honda", 60.0);
        Car car3 = new Car("LMN789", "Ford", 55.0);
        Car car4 = new Car("OPQ012", "Chevrolet", 65.0);
        Car car5 = new Car("RST345", "Nissan", 70.0);
        Car car6 = new Car("UVW678", "BMW", 100.0);
        Car car7 = new Car("JKL901", "Mercedes", 120.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        store.addCar(car5);
        store.addCar(car6);
        store.addCar(car7);
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        LocalDate rentDate = LocalDate.now();
        LocalDate dueDate = rentDate.plusDays(7);
        
        // Customer C001 rented cars with plates "ABC123", "XYZ456"
        store.rentCar(customer1, car1, rentDate, dueDate);
        store.rentCar(customer1, car2, rentDate, dueDate);
        
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        store.rentCar(customer2, car3, rentDate, dueDate);
        store.rentCar(customer2, car4, rentDate, dueDate);
        store.rentCar(customer2, car5, rentDate, dueDate);
        store.rentCar(customer2, car6, rentDate, dueDate);
        store.rentCar(customer2, car7, rentDate, dueDate);
        
        // Expected Output: 
        // - Number of rentals for customer C001 = 2
        // - Number of rentals for customer C002 = 5
        Map<Customer, Integer> result = store.getRentalCountPerCustomer();
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create a store instance
        // Create a customer with customer ID: C003
        Customer customer = new Customer("Charlie", "Brown", "321 Elm St");
        customer.setName("C003");
        
        store.addCustomer(customer);
        
        // No rental records are added for customer C003
        // Expected Output: Empty map (not null)
        Map<Customer, Integer> result = store.getRentalCountPerCustomer();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create a store instance
        // Create a customer with customer ID: C004
        Customer customer = new Customer("David", "Wilson", "654 Maple St");
        customer.setName("C004");
        
        // Add cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Car car1 = new Car("DEF234", "Toyota", 50.0);
        Car car2 = new Car("GHI567", "Honda", 60.0);
        Car car3 = new Car("JKL890", "Ford", 55.0);
        Car car4 = new Car("MNO123", "Chevrolet", 65.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        store.addCustomer(customer);
        
        // Add rental records for customer C004 (4 rentals)
        LocalDate rentDate = LocalDate.now();
        LocalDate dueDate = rentDate.plusDays(7);
        
        store.rentCar(customer, car1, rentDate, dueDate);
        store.rentCar(customer, car2, rentDate, dueDate);
        store.rentCar(customer, car3, rentDate, dueDate);
        store.rentCar(customer, car4, rentDate, dueDate);
        
        // Mark 2 of them as returned
        store.returnCar(car1, LocalDate.now().plusDays(3));
        store.returnCar(car2, LocalDate.now().plusDays(4));
        
        // Expected Output: Number of rentals for customer C004 = 4 (stored)
        Map<Customer, Integer> result = store.getRentalCountPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create a store instance
        // Create a customer with customer ID: C005
        Customer customer = new Customer("Eva", "Davis", "987 Cedar St");
        customer.setName("C005");
        
        // Add cars with plates "PQR456", "STU789", "VWX012"
        Car car1 = new Car("PQR456", "Toyota", 50.0);
        Car car2 = new Car("STU789", "Honda", 60.0);
        Car car3 = new Car("VWX012", "Ford", 55.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCustomer(customer);
        
        // Add rental records for customer C005 (3 rentals)
        LocalDate rentDate = LocalDate.now().minusDays(10); // rented 10 days ago
        LocalDate dueDatePast = LocalDate.now().minusDays(3); // due 3 days ago (overdue)
        LocalDate dueDateFuture = LocalDate.now().plusDays(7); // due in 7 days
        
        // Create overdue rental (STU789)
        store.rentCar(customer, car1, rentDate, dueDateFuture);
        store.rentCar(customer, car2, rentDate, dueDatePast); // This one will be overdue
        store.rentCar(customer, car3, rentDate, dueDateFuture);
        
        // Expected Output: Number of rentals for customer C005 = 3
        Map<Customer, Integer> result = store.getRentalCountPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
}