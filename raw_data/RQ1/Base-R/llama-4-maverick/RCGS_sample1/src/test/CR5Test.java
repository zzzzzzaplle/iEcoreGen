import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CR5Test {
    
    private CarRentalStore store;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_countRentalsForSingleCustomer() {
        // Create a store instance
        // Create a customer with customer ID: C001
        Customer customer1 = new Customer("John", "Doe", "123 Main St");
        
        // Add 3 rental records for customer C001 with different car details
        Car car1 = new Car("ABC123", "Toyota", 50.0);
        Car car2 = new Car("XYZ456", "Honda", 60.0);
        Car car3 = new Car("LMN789", "Ford", 70.0);
        
        // Set cars as rented
        car1.setRented(true);
        car2.setRented(true);
        car3.setRented(true);
        
        // Create rentals
        Rental rental1 = new Rental(car1, LocalDate.now().plusDays(3));
        Rental rental2 = new Rental(car2, LocalDate.now().plusDays(5));
        Rental rental3 = new Rental(car3, LocalDate.now().plusDays(7));
        
        // Add rentals to customer
        customer1.addRental(rental1);
        customer1.addRental(rental2);
        customer1.addRental(rental3);
        
        // Add customer and cars to store
        store.getCustomers().add(customer1);
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Count rentals per customer
        Map<Customer, Long> result = store.countRentalsPerCustomer();
        
        // Verify the number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertEquals(Long.valueOf(3), result.get(customer1));
    }
    
    @Test
    public void testCase2_countRentalsForMultipleCustomers() {
        // Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer("John", "Doe", "123 Main St");
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak St");
        
        // Add rental records for customer C001 (2 rentals)
        Car car1 = new Car("ABC123", "Toyota", 50.0);
        Car car2 = new Car("XYZ456", "Honda", 60.0);
        car1.setRented(true);
        car2.setRented(true);
        
        Rental rental1 = new Rental(car1, LocalDate.now().plusDays(3));
        Rental rental2 = new Rental(car2, LocalDate.now().plusDays(5));
        customer1.addRental(rental1);
        customer1.addRental(rental2);
        
        // Add rental records for customer C002 (5 rentals)
        Car car3 = new Car("LMN789", "Ford", 70.0);
        Car car4 = new Car("OPQ012", "Chevy", 80.0);
        Car car5 = new Car("RST345", "BMW", 90.0);
        Car car6 = new Car("UVW678", "Audi", 100.0);
        Car car7 = new Car("JKL901", "Mercedes", 110.0);
        car3.setRented(true);
        car4.setRented(true);
        car5.setRented(true);
        car6.setRented(true);
        car7.setRented(true);
        
        Rental rental3 = new Rental(car3, LocalDate.now().plusDays(2));
        Rental rental4 = new Rental(car4, LocalDate.now().plusDays(4));
        Rental rental5 = new Rental(car5, LocalDate.now().plusDays(6));
        Rental rental6 = new Rental(car6, LocalDate.now().plusDays(8));
        Rental rental7 = new Rental(car7, LocalDate.now().plusDays(10));
        customer2.addRental(rental3);
        customer2.addRental(rental4);
        customer2.addRental(rental5);
        customer2.addRental(rental6);
        customer2.addRental(rental7);
        
        // Add customers and cars to store
        store.getCustomers().add(customer1);
        store.getCustomers().add(customer2);
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getCars().add(car4);
        store.getCars().add(car5);
        store.getCars().add(car6);
        store.getCars().add(car7);
        
        // Count rentals per customer
        Map<Customer, Long> result = store.countRentalsPerCustomer();
        
        // Verify the number of rentals for customer C001 = 2 and C002 = 5
        assertEquals(2, result.size());
        assertEquals(Long.valueOf(2), result.get(customer1));
        assertEquals(Long.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_countRentalsWithNoRecords() {
        // Create a store instance
        // Create a customer with customer ID: C003
        Customer customer3 = new Customer("Bob", "Johnson", "789 Pine St");
        
        // No rental records are added for customer C003
        store.getCustomers().add(customer3);
        
        // Count rentals per customer
        Map<Customer, Long> result = store.countRentalsPerCustomer();
        
        // Verify the output is an empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_countRentalsIncludingReturnedCars() {
        // Create a store instance
        // Create a customer with customer ID: C004
        Customer customer4 = new Customer("Alice", "Brown", "321 Elm St");
        
        // Add rental records for customer C004 (4 rentals)
        Car car1 = new Car("DEF234", "Toyota", 50.0);
        Car car2 = new Car("GHI567", "Honda", 60.0);
        Car car3 = new Car("JKL890", "Ford", 70.0);
        Car car4 = new Car("MNO123", "Chevy", 80.0);
        car1.setRented(true);
        car2.setRented(true);
        car3.setRented(true);
        car4.setRented(true);
        
        Rental rental1 = new Rental(car1, LocalDate.now().plusDays(3));
        Rental rental2 = new Rental(car2, LocalDate.now().plusDays(5));
        Rental rental3 = new Rental(car3, LocalDate.now().plusDays(7));
        Rental rental4 = new Rental(car4, LocalDate.now().plusDays(9));
        
        // Mark 2 of them as returned
        rental1.setBackDate(LocalDate.now().plusDays(1));
        rental2.setBackDate(LocalDate.now().plusDays(2));
        
        // Add rentals to customer
        customer4.addRental(rental1);
        customer4.addRental(rental2);
        customer4.addRental(rental3);
        customer4.addRental(rental4);
        
        // Add customer and cars to store
        store.getCustomers().add(customer4);
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        store.getCars().add(car4);
        
        // Count rentals per customer
        Map<Customer, Long> result = store.countRentalsPerCustomer();
        
        // Verify the number of rentals for customer C004 = 2 (only active rentals)
        assertEquals(1, result.size());
        assertEquals(Long.valueOf(2), result.get(customer4));
    }
    
    @Test
    public void testCase5_countRentalsForCustomerWithOverdueCars() {
        // Create a store instance
        // Create a customer with customer ID: C005
        Customer customer5 = new Customer("Charlie", "Wilson", "654 Maple St");
        
        // Add rental records for customer C005 (3 rentals)
        Car car1 = new Car("PQR456", "Toyota", 50.0);
        Car car2 = new Car("STU789", "Honda", 60.0);
        Car car3 = new Car("VWX012", "Ford", 70.0);
        car1.setRented(true);
        car2.setRented(true);
        car3.setRented(true);
        
        // One marked as overdue (due date in the past)
        Rental rental1 = new Rental(car1, LocalDate.now().plusDays(3));
        Rental rental2 = new Rental(car2, LocalDate.now().minusDays(1)); // Overdue
        Rental rental3 = new Rental(car3, LocalDate.now().plusDays(5));
        
        // Add rentals to customer
        customer5.addRental(rental1);
        customer5.addRental(rental2);
        customer5.addRental(rental3);
        
        // Add customer and cars to store
        store.getCustomers().add(customer5);
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Count rentals per customer
        Map<Customer, Long> result = store.countRentalsPerCustomer();
        
        // Verify the number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertEquals(Long.valueOf(3), result.get(customer5));
        
        // Verify number of overdue rentals for customer C005 = 1
        List<Customer> overdueCustomers = store.getCustomersWithOverdueRentals();
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer5));
    }
}