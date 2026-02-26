import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.*;

public class CR5Test {
    
    private RentalStore store;
    
    @Before
    public void setUp() {
        store = new RentalStore();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create a store instance
        // Create a customer with customer ID: C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        store.addCustomer(customer);
        
        // Add 3 rental records for customer C001 with different car details
        // Add cars with plates "ABC123", "XYZ456", "LMN789" rented by customer C001
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(35.0);
        store.addCar(car3);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.now());
        rental1.setDueDate(LocalDate.now().plusDays(7));
        rental1.setBackDate(LocalDate.now().plusDays(5));
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.now());
        rental2.setDueDate(LocalDate.now().plusDays(7));
        rental2.setBackDate(LocalDate.now().plusDays(6));
        store.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.now());
        rental3.setDueDate(LocalDate.now().plusDays(7));
        rental3.setBackDate(LocalDate.now().plusDays(4));
        store.addRental(rental3);
        
        // Expected Output: Number of rentals for customer C001 = 3
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        store.addCustomer(customer1);
        
        Customer customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        store.addCustomer(customer2);
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        // Customer C001 rented cars with plates "ABC123", "XYZ456"
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        store.addCar(car2);
        
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(35.0);
        store.addCar(car3);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("Chevrolet Malibu");
        car4.setDailyPrice(45.0);
        store.addCar(car4);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Nissan Altima");
        car5.setDailyPrice(42.0);
        store.addCar(car5);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Hyundai Elantra");
        car6.setDailyPrice(38.0);
        store.addCar(car6);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Kia Optima");
        car7.setDailyPrice(36.0);
        store.addCar(car7);
        
        // Add rentals for customer1
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.now());
        rental1.setDueDate(LocalDate.now().plusDays(7));
        rental1.setBackDate(LocalDate.now().plusDays(5));
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.now());
        rental2.setDueDate(LocalDate.now().plusDays(7));
        rental2.setBackDate(LocalDate.now().plusDays(6));
        store.addRental(rental2);
        
        // Add rentals for customer2
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.now());
        rental3.setDueDate(LocalDate.now().plusDays(7));
        rental3.setBackDate(LocalDate.now().plusDays(4));
        store.addRental(rental3);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentDate(LocalDate.now());
        rental4.setDueDate(LocalDate.now().plusDays(7));
        rental4.setBackDate(LocalDate.now().plusDays(3));
        store.addRental(rental4);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentDate(LocalDate.now());
        rental5.setDueDate(LocalDate.now().plusDays(7));
        rental5.setBackDate(LocalDate.now().plusDays(2));
        store.addRental(rental5);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentDate(LocalDate.now());
        rental6.setDueDate(LocalDate.now().plusDays(7));
        rental6.setBackDate(LocalDate.now().plusDays(1));
        store.addRental(rental6);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentDate(LocalDate.now());
        rental7.setDueDate(LocalDate.now().plusDays(7));
        rental7.setBackDate(LocalDate.now().plusDays(6));
        store.addRental(rental7);
        
        // Expected Output: 
        // - Number of rentals for customer C001 = 2
        // - Number of rentals for customer C002 = 5
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create a store instance
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Johnson");
        customer.setAddress("789 Pine St");
        store.addCustomer(customer);
        
        // No rental records are added for customer C003
        // Expected Output: Null
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create a store instance
        // Create a customer with customer ID: C004
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        store.addCustomer(customer);
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        // Rental records: Customer C004 rented cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(35.0);
        store.addCar(car3);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet Malibu");
        car4.setDailyPrice(45.0);
        store.addCar(car4);
        
        LocalDate today = LocalDate.now();
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentDate(today.minusDays(10));
        rental1.setDueDate(today.minusDays(3));
        rental1.setBackDate(today.minusDays(5)); // Returned
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentDate(today.minusDays(8));
        rental2.setDueDate(today.minusDays(1));
        rental2.setBackDate(today.minusDays(2)); // Returned
        store.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentDate(today.minusDays(5));
        rental3.setDueDate(today.plusDays(2));
        // Not returned (backDate is null)
        store.addRental(rental3);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        rental4.setRentDate(today.minusDays(3));
        rental4.setDueDate(today.plusDays(4));
        // Not returned (backDate is null)
        store.addRental(rental4);
        
        // Expected Output: 
        // - Number of rentals for customer C004 = 4 (stored) 
        // - Currently active rentals = 2 (after marking 2 as returned)
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
        
        // Verify that only 2 rentals are currently active (backDate is null)
        long activeRentals = store.getRentals().stream()
            .filter(r -> r.getCustomer().equals(customer))
            .filter(r -> r.getBackDate() == null)
            .count();
        assertEquals(2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create a store instance
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Wilson");
        customer.setAddress("654 Maple Dr");
        store.addCustomer(customer);
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        // Rental records: Customer C005 rented cars with plates "PQR456", "STU789", "VWX012" with "STU789" being overdue
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(35.0);
        store.addCar(car3);
        
        LocalDate today = LocalDate.now();
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentDate(today.minusDays(10));
        rental1.setDueDate(today.minusDays(3));
        rental1.setBackDate(today.minusDays(5)); // Returned
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentDate(today.minusDays(8));
        rental2.setDueDate(today.minusDays(2)); // Overdue (due date in past, not returned)
        // backDate is null - overdue
        store.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentDate(today.minusDays(5));
        rental3.setDueDate(today.plusDays(2));
        // Not returned but not overdue yet
        store.addRental(rental3);
        
        // Expected Output: 
        // - Number of rentals for customer C005 = 3
        // - Number of overdue rentals for customer C005 = 1
        Map<Customer, Integer> result = store.getRentalsPerCustomer();
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Verify that only 1 rental is overdue
        long overdueRentals = store.getRentals().stream()
            .filter(r -> r.getCustomer().equals(customer))
            .filter(r -> r.isOverdue(today))
            .count();
        assertEquals(1, overdueRentals);
    }
}