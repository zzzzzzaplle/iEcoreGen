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
        customer.setName("C001"); // Using name field as ID per specification
        
        // Add 3 rental records for customer C001 with different car details
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCustomer(customer);
        
        Rental rental1 = new Rental(car1, customer, LocalDate.now(), LocalDate.now().plusDays(3));
        Rental rental2 = new Rental(car2, customer, LocalDate.now(), LocalDate.now().plusDays(5));
        Rental rental3 = new Rental(car3, customer, LocalDate.now(), LocalDate.now().plusDays(7));
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Get rental count map
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify number of rentals for customer C001 = 3
        assertEquals(1, rentalCounts.size());
        assertEquals(Integer.valueOf(3), rentalCounts.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer("John", "Doe", "123 Main St");
        customer1.setName("C001");
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak St");
        customer2.setName("C002");
        
        // Add cars for both customers
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        Car car4 = new Car("OPQ012", "Chevrolet Malibu", 55.0);
        Car car5 = new Car("RST345", "Nissan Altima", 48.0);
        Car car6 = new Car("UVW678", "Hyundai Elantra", 42.0);
        Car car7 = new Car("JKL901", "Kia Optima", 46.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        store.addCar(car5);
        store.addCar(car6);
        store.addCar(car7);
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        
        // Add rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental(car1, customer1, LocalDate.now(), LocalDate.now().plusDays(3));
        Rental rental2 = new Rental(car2, customer1, LocalDate.now(), LocalDate.now().plusDays(5));
        
        // Add rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental(car3, customer2, LocalDate.now(), LocalDate.now().plusDays(2));
        Rental rental4 = new Rental(car4, customer2, LocalDate.now(), LocalDate.now().plusDays(4));
        Rental rental5 = new Rental(car5, customer2, LocalDate.now(), LocalDate.now().plusDays(6));
        Rental rental6 = new Rental(car6, customer2, LocalDate.now(), LocalDate.now().plusDays(8));
        Rental rental7 = new Rental(car7, customer2, LocalDate.now(), LocalDate.now().plusDays(10));
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        store.addRental(rental5);
        store.addRental(rental6);
        store.addRental(rental7);
        
        // Get rental count map
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify number of rentals for customer C001 = 2 and customer C002 = 5
        assertEquals(2, rentalCounts.size());
        assertEquals(Integer.valueOf(2), rentalCounts.get(customer1));
        assertEquals(Integer.valueOf(5), rentalCounts.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create a store instance
        // Create a customer with customer ID: C003
        Customer customer = new Customer("Bob", "Johnson", "789 Pine St");
        customer.setName("C003");
        
        store.addCustomer(customer);
        
        // No rental records are added for customer C003
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Expected Output: Empty map (not null)
        assertNotNull(rentalCounts);
        assertTrue(rentalCounts.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create a store instance
        // Create a customer with customer ID: C004
        Customer customer = new Customer("Alice", "Brown", "321 Elm St");
        customer.setName("C004");
        
        // Add cars
        Car car1 = new Car("DEF234", "Toyota Camry", 50.0);
        Car car2 = new Car("GHI567", "Honda Civic", 45.0);
        Car car3 = new Car("JKL890", "Ford Focus", 40.0);
        Car car4 = new Car("MNO123", "Chevrolet Malibu", 55.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        store.addCustomer(customer);
        
        // Add rental records for customer C004 (4 rentals)
        Rental rental1 = new Rental(car1, customer, LocalDate.now().minusDays(10), LocalDate.now().minusDays(5));
        Rental rental2 = new Rental(car2, customer, LocalDate.now().minusDays(8), LocalDate.now().minusDays(3));
        Rental rental3 = new Rental(car3, customer, LocalDate.now().minusDays(6), LocalDate.now().plusDays(1));
        Rental rental4 = new Rental(car4, customer, LocalDate.now().minusDays(4), LocalDate.now().plusDays(3));
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        
        // Mark 2 of them as returned
        rental1.setBackDate(LocalDate.now().minusDays(5));
        rental2.setBackDate(LocalDate.now().minusDays(3));
        
        // Get rental count map
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify number of rentals for customer C004 = 4 (stored)
        assertEquals(1, rentalCounts.size());
        assertEquals(Integer.valueOf(4), rentalCounts.get(customer));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create a store instance
        // Create a customer with customer ID: C005
        Customer customer = new Customer("Charlie", "Wilson", "654 Maple St");
        customer.setName("C005");
        
        // Add cars
        Car car1 = new Car("PQR456", "Toyota Camry", 50.0);
        Car car2 = new Car("STU789", "Honda Civic", 45.0);
        Car car3 = new Car("VWX012", "Ford Focus", 40.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCustomer(customer);
        
        // Add rental records for customer C005 (3 rentals)
        Rental rental1 = new Rental(car1, customer, LocalDate.now().minusDays(10), LocalDate.now().minusDays(3));
        Rental rental2 = new Rental(car2, customer, LocalDate.now().minusDays(8), LocalDate.now().minusDays(5)); // Overdue
        Rental rental3 = new Rental(car3, customer, LocalDate.now().minusDays(3), LocalDate.now().plusDays(2));
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Get rental count map
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify number of rentals for customer C005 = 3
        assertEquals(1, rentalCounts.size());
        assertEquals(Integer.valueOf(3), rentalCounts.get(customer));
    }
}