import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR5Test {
    
    private Store store;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Set up customer
        customer1 = new Customer("John", "Doe", "123 Main St");
        
        // Set up cars
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental records for customer C001
        LocalDate rentDate = LocalDate.of(2024, 1, 1);
        LocalDate dueDate = LocalDate.of(2024, 1, 5);
        
        store.rentCar(customer1, car1, rentDate, dueDate);
        store.rentCar(customer1, car2, rentDate, dueDate);
        store.rentCar(customer1, car3, rentDate, dueDate);
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify results
        assertEquals(1, rentalCounts.size());
        assertTrue(rentalCounts.containsKey(customer1));
        assertEquals(3, (int) rentalCounts.get(customer1));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Set up customers
        customer1 = new Customer("John", "Doe", "123 Main St");
        customer2 = new Customer("Jane", "Smith", "456 Oak Ave");
        
        // Set up cars
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        Car car4 = new Car("OPQ012", "Chevrolet Malibu", 55.0);
        Car car5 = new Car("RST345", "Nissan Altima", 48.0);
        Car car6 = new Car("UVW678", "Hyundai Elantra", 42.0);
        Car car7 = new Car("JKL901", "Kia Optima", 46.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        store.addCar(car5);
        store.addCar(car6);
        store.addCar(car7);
        
        // Create rental records
        LocalDate rentDate = LocalDate.of(2024, 1, 1);
        LocalDate dueDate = LocalDate.of(2024, 1, 5);
        
        // Customer C001: 2 rentals
        store.rentCar(customer1, car1, rentDate, dueDate);
        store.rentCar(customer1, car2, rentDate, dueDate);
        
        // Customer C002: 5 rentals
        store.rentCar(customer2, car3, rentDate, dueDate);
        store.rentCar(customer2, car4, rentDate, dueDate);
        store.rentCar(customer2, car5, rentDate, dueDate);
        store.rentCar(customer2, car6, rentDate, dueDate);
        store.rentCar(customer2, car7, rentDate, dueDate);
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify results
        assertEquals(2, rentalCounts.size());
        assertTrue(rentalCounts.containsKey(customer1));
        assertTrue(rentalCounts.containsKey(customer2));
        assertEquals(2, (int) rentalCounts.get(customer1));
        assertEquals(5, (int) rentalCounts.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Set up customer
        customer3 = new Customer("Bob", "Johnson", "789 Pine Rd");
        
        // Get rental count per customer - no rentals added
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify results - should be empty map, not null
        assertNotNull(rentalCounts);
        assertEquals(0, rentalCounts.size());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Set up customer
        customer4 = new Customer("Alice", "Brown", "321 Elm St");
        
        // Set up cars
        Car car1 = new Car("DEF234", "Toyota Corolla", 35.0);
        Car car2 = new Car("GHI567", "Honda Accord", 52.0);
        Car car3 = new Car("JKL890", "Ford Fusion", 47.0);
        Car car4 = new Car("MNO123", "Chevrolet Impala", 58.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        
        // Create rental records
        LocalDate rentDate = LocalDate.of(2024, 1, 1);
        LocalDate dueDate = LocalDate.of(2024, 1, 5);
        
        // Add 4 rental records for customer C004
        store.rentCar(customer4, car1, rentDate, dueDate);
        store.rentCar(customer4, car2, rentDate, dueDate);
        store.rentCar(customer4, car3, rentDate, dueDate);
        store.rentCar(customer4, car4, rentDate, dueDate);
        
        // Mark 2 rentals as returned
        Rental rental1 = store.getRentals().get(0);
        Rental rental2 = store.getRentals().get(1);
        
        store.returnCar(rental1, LocalDate.of(2024, 1, 3));
        store.returnCar(rental2, LocalDate.of(2024, 1, 4));
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify results - should count all 4 rentals regardless of return status
        assertEquals(1, rentalCounts.size());
        assertTrue(rentalCounts.containsKey(customer4));
        assertEquals(4, (int) rentalCounts.get(customer4));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Set up customer
        customer5 = new Customer("Charlie", "Wilson", "654 Maple Dr");
        
        // Set up cars
        Car car1 = new Car("PQR456", "BMW 3 Series", 75.0);
        Car car2 = new Car("STU789", "Mercedes C-Class", 80.0);
        Car car3 = new Car("VWX012", "Audi A4", 78.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental records with different dates
        LocalDate pastDate = LocalDate.of(2024, 1, 1);
        LocalDate overdueDueDate = LocalDate.of(2024, 1, 5); // This will be overdue
        LocalDate futureDueDate = LocalDate.of(2024, 12, 31); // This will not be overdue
        
        // Customer C005: 3 rentals
        store.rentCar(customer5, car1, pastDate, overdueDueDate);
        store.rentCar(customer5, car2, pastDate, overdueDueDate);
        store.rentCar(customer5, car3, LocalDate.now(), futureDueDate);
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify results - should count all 3 rentals regardless of overdue status
        assertEquals(1, rentalCounts.size());
        assertTrue(rentalCounts.containsKey(customer5));
        assertEquals(3, (int) rentalCounts.get(customer5));
        
        // Additional verification for overdue customers
        // Note: This test depends on current date being after 2024-01-05
        // If today's date is before 2024-01-05, this verification might fail
        // For robustness, we only verify the rental count which is the main requirement
    }
}