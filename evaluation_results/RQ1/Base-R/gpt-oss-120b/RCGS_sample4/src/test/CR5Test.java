import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR5Test {
    
    private CarRentalStore store;
    private Customer customerC001;
    private Customer customerC002;
    private Customer customerC003;
    private Customer customerC004;
    private Customer customerC005;
    
    @Before
    public void setUp() {
        // Initialize a new store instance before each test
        store = new CarRentalStore();
        
        // Create customer instances that can be reused across tests
        customerC001 = new Customer("John", "Doe", "123 Main St");
        customerC002 = new Customer("Jane", "Smith", "456 Oak Ave");
        customerC003 = new Customer("Bob", "Johnson", "789 Pine Rd");
        customerC004 = new Customer("Alice", "Brown", "321 Elm St");
        customerC005 = new Customer("Charlie", "Wilson", "654 Maple Dr");
        
        // Add customers to the store
        store.addCustomer(customerC001);
        store.addCustomer(customerC002);
        store.addCustomer(customerC003);
        store.addCustomer(customerC004);
        store.addCustomer(customerC005);
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Test Case 1: Count Rentals for a Single Customer
        // Input: Customer with ID C001 rented 3 cars
        
        // Create cars for rental
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        
        // Add cars to the store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental records for customer C001
        LocalDate rentDate = LocalDate.now();
        LocalDate dueDate = rentDate.plusDays(7);
        
        Rental rental1 = new Rental(customerC001, car1, rentDate, dueDate);
        Rental rental2 = new Rental(customerC001, car2, rentDate, dueDate);
        Rental rental3 = new Rental(customerC001, car3, rentDate, dueDate);
        
        // Add rentals to the store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify the count for customer C001
        assertEquals("Customer C001 should have 3 rentals", 3, 
                    rentalCounts.get(customerC001).intValue());
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Test Case 2: Count Rentals for Multiple Customers
        // Input: Customers C001 and C002 rented cars
        
        // Create cars for rental
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        Car car4 = new Car("OPQ012", "Chevrolet Malibu", 55.0);
        Car car5 = new Car("RST345", "Nissan Altima", 48.0);
        Car car6 = new Car("UVW678", "Hyundai Elantra", 42.0);
        Car car7 = new Car("JKL901", "Kia Optima", 46.0);
        
        // Add cars to the store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        store.addCar(car5);
        store.addCar(car6);
        store.addCar(car7);
        
        // Create rental records
        LocalDate rentDate = LocalDate.now();
        LocalDate dueDate = rentDate.plusDays(7);
        
        // Customer C001: 2 rentals
        Rental rental1 = new Rental(customerC001, car1, rentDate, dueDate);
        Rental rental2 = new Rental(customerC001, car2, rentDate, dueDate);
        
        // Customer C002: 5 rentals
        Rental rental3 = new Rental(customerC002, car3, rentDate, dueDate);
        Rental rental4 = new Rental(customerC002, car4, rentDate, dueDate);
        Rental rental5 = new Rental(customerC002, car5, rentDate, dueDate);
        Rental rental6 = new Rental(customerC002, car6, rentDate, dueDate);
        Rental rental7 = new Rental(customerC002, car7, rentDate, dueDate);
        
        // Add rentals to the store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        store.addRental(rental5);
        store.addRental(rental6);
        store.addRental(rental7);
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify counts for both customers
        assertEquals("Customer C001 should have 2 rentals", 2, 
                    rentalCounts.get(customerC001).intValue());
        assertEquals("Customer C002 should have 5 rentals", 5, 
                    rentalCounts.get(customerC002).intValue());
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Test Case 3: Count Rentals with No Records
        // Input: No cars rented by any customer
        
        // Create a car but don't create any rental records
        Car car = new Car("DEF234", "Toyota Corolla", 35.0);
        store.addCar(car);
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify that customer C003 is not in the map (null)
        assertNull("Customer C003 should not be in the rental counts map", 
                  rentalCounts.get(customerC003));
        
        // Verify that the map is empty since there are no rentals
        assertTrue("Rental counts map should be empty when no rentals exist", 
                  rentalCounts.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Test Case 4: Count Rentals Including Returned Cars
        // Input: Customer rented and returned 4 cars
        
        // Create cars for rental
        Car car1 = new Car("DEF234", "Toyota Camry", 50.0);
        Car car2 = new Car("GHI567", "Honda Civic", 45.0);
        Car car3 = new Car("JKL890", "Ford Focus", 40.0);
        Car car4 = new Car("MNO123", "Chevrolet Malibu", 55.0);
        
        // Add cars to the store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        
        // Create rental records for customer C004
        LocalDate rentDate = LocalDate.now().minusDays(10);
        LocalDate dueDate = rentDate.plusDays(7);
        
        Rental rental1 = new Rental(customerC004, car1, rentDate, dueDate);
        Rental rental2 = new Rental(customerC004, car2, rentDate, dueDate);
        Rental rental3 = new Rental(customerC004, car3, rentDate, dueDate);
        Rental rental4 = new Rental(customerC004, car4, rentDate, dueDate);
        
        // Mark 2 rentals as returned
        rental1.setBackDate(rentDate.plusDays(5));
        rental2.setBackDate(rentDate.plusDays(6));
        
        // Add rentals to the store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify the count for customer C004 (should be 4 regardless of return status)
        assertEquals("Customer C004 should have 4 total rentals regardless of return status", 
                    4, rentalCounts.get(customerC004).intValue());
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Test Case 5: Count Rentals for a Customer with Overdue Cars
        // Input: Customer rented 3 cars, with 1 overdue
        
        // Create cars for rental
        Car car1 = new Car("PQR456", "Toyota Camry", 50.0);
        Car car2 = new Car("STU789", "Honda Civic", 45.0);
        Car car3 = new Car("VWX012", "Ford Focus", 40.0);
        
        // Add cars to the store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental records for customer C005
        LocalDate rentDate = LocalDate.now().minusDays(10);
        LocalDate dueDate1 = rentDate.plusDays(5); // This one will be overdue
        LocalDate dueDate2 = rentDate.plusDays(15); // This one is not overdue yet
        
        Rental rental1 = new Rental(customerC005, car1, rentDate, dueDate1);
        Rental rental2 = new Rental(customerC005, car2, rentDate, dueDate2);
        Rental rental3 = new Rental(customerC005, car3, rentDate, dueDate2);
        
        // Mark rental1 as overdue by setting backDate to null and dueDate in the past
        // (it's already set up this way by the constructor)
        
        // Add rentals to the store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify the count for customer C005 (should be 3 regardless of overdue status)
        assertEquals("Customer C005 should have 3 total rentals regardless of overdue status", 
                    3, rentalCounts.get(customerC005).intValue());
        
        // Additional verification: check overdue customers list
        // Note: This is not part of the CR5 requirement but aligns with the test specification
        assertEquals("Customer C005 should have 1 overdue rental", 
                    1, store.getOverdueCustomers().size());
        assertTrue("Customer C005 should be in overdue customers list", 
                  store.getOverdueCustomers().contains(customerC005));
    }
}