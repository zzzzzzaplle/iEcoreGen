import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR5Test {
    
    private Store store;
    private Customer customerC001;
    private Customer customerC002;
    private Customer customerC003;
    private Customer customerC004;
    private Customer customerC005;
    
    @Before
    public void setUp() {
        store = new Store();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create customer C001
        customerC001 = new Customer("John", "Doe", "123 Main St");
        
        // Create cars with plates "ABC123", "XYZ456", "LMN789"
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        
        // Add cars to store
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCustomer(customerC001);
        
        // Create rental records for customer C001 with different cars
        Rental rental1 = new Rental(car1, customerC001, LocalDate.now(), 
                                   LocalDate.now().plusDays(3), null, 150.0);
        Rental rental2 = new Rental(car2, customerC001, LocalDate.now(), 
                                   LocalDate.now().plusDays(5), null, 225.0);
        Rental rental3 = new Rental(car3, customerC001, LocalDate.now(), 
                                   LocalDate.now().plusDays(7), null, 280.0);
        
        // Add rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify that customer C001 has 3 rentals
        assertEquals(3, rentalCounts.get(customerC001).intValue());
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create customers C001 and C002
        customerC001 = new Customer("John", "Doe", "123 Main St");
        customerC002 = new Customer("Jane", "Smith", "456 Oak Ave");
        
        // Create cars for both customers
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        Car car4 = new Car("OPQ012", "Chevrolet Malibu", 55.0);
        Car car5 = new Car("RST345", "Nissan Altima", 48.0);
        Car car6 = new Car("UVW678", "Hyundai Elantra", 42.0);
        Car car7 = new Car("JKL901", "Kia Optima", 46.0);
        
        // Add cars and customers to store
        store.addCar(car1); store.addCar(car2); store.addCar(car3);
        store.addCar(car4); store.addCar(car5); store.addCar(car6); store.addCar(car7);
        store.addCustomer(customerC001);
        store.addCustomer(customerC002);
        
        // Create rentals for customer C001 (2 rentals)
        Rental rental1 = new Rental(car1, customerC001, LocalDate.now(), 
                                   LocalDate.now().plusDays(3), null, 150.0);
        Rental rental2 = new Rental(car2, customerC001, LocalDate.now(), 
                                   LocalDate.now().plusDays(5), null, 225.0);
        
        // Create rentals for customer C002 (5 rentals)
        Rental rental3 = new Rental(car3, customerC002, LocalDate.now(), 
                                   LocalDate.now().plusDays(2), null, 80.0);
        Rental rental4 = new Rental(car4, customerC002, LocalDate.now(), 
                                   LocalDate.now().plusDays(4), null, 220.0);
        Rental rental5 = new Rental(car5, customerC002, LocalDate.now(), 
                                   LocalDate.now().plusDays(6), null, 288.0);
        Rental rental6 = new Rental(car6, customerC002, LocalDate.now(), 
                                   LocalDate.now().plusDays(8), null, 336.0);
        Rental rental7 = new Rental(car7, customerC002, LocalDate.now(), 
                                   LocalDate.now().plusDays(10), null, 460.0);
        
        // Add all rentals to store
        store.addRental(rental1); store.addRental(rental2);
        store.addRental(rental3); store.addRental(rental4); store.addRental(rental5);
        store.addRental(rental6); store.addRental(rental7);
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify rental counts
        assertEquals(2, rentalCounts.get(customerC001).intValue());
        assertEquals(5, rentalCounts.get(customerC002).intValue());
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer C003
        customerC003 = new Customer("Bob", "Johnson", "789 Pine St");
        store.addCustomer(customerC003);
        
        // No rental records are added for customer C003
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify that the map is empty (no rentals exist)
        assertTrue(rentalCounts.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create customer C004
        customerC004 = new Customer("Alice", "Brown", "321 Elm St");
        
        // Create cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Car car1 = new Car("DEF234", "Toyota Corolla", 35.0);
        Car car2 = new Car("GHI567", "Honda Accord", 52.0);
        Car car3 = new Car("JKL890", "Ford Fusion", 47.0);
        Car car4 = new Car("MNO123", "Chevrolet Impala", 58.0);
        
        // Add cars and customer to store
        store.addCar(car1); store.addCar(car2); store.addCar(car3); store.addCar(car4);
        store.addCustomer(customerC004);
        
        // Create 4 rental records for customer C004
        Rental rental1 = new Rental(car1, customerC004, LocalDate.now().minusDays(10), 
                                   LocalDate.now().minusDays(5), LocalDate.now().minusDays(5), 175.0);
        Rental rental2 = new Rental(car2, customerC004, LocalDate.now().minusDays(8), 
                                   LocalDate.now().minusDays(3), LocalDate.now().minusDays(3), 260.0);
        Rental rental3 = new Rental(car3, customerC004, LocalDate.now().minusDays(5), 
                                   LocalDate.now().plusDays(2), null, 0.0);
        Rental rental4 = new Rental(car4, customerC004, LocalDate.now().minusDays(3), 
                                   LocalDate.now().plusDays(4), null, 0.0);
        
        // Add rentals to store
        store.addRental(rental1); store.addRental(rental2);
        store.addRental(rental3); store.addRental(rental4);
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify that customer C004 has 4 rentals (including returned ones)
        assertEquals(4, rentalCounts.get(customerC004).intValue());
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create customer C005
        customerC005 = new Customer("Charlie", "Wilson", "654 Maple Dr");
        
        // Create cars with plates "PQR456", "STU789", "VWX012"
        Car car1 = new Car("PQR456", "BMW 3 Series", 85.0);
        Car car2 = new Car("STU789", "Mercedes C-Class", 90.0);
        Car car3 = new Car("VWX012", "Audi A4", 80.0);
        
        // Add cars and customer to store
        store.addCar(car1); store.addCar(car2); store.addCar(car3);
        store.addCustomer(customerC005);
        
        // Create 3 rental records for customer C005, with one marked as overdue
        Rental rental1 = new Rental(car1, customerC005, LocalDate.now().minusDays(5), 
                                   LocalDate.now().plusDays(2), null, 0.0);
        Rental rental2 = new Rental(car2, customerC005, LocalDate.now().minusDays(10), 
                                   LocalDate.now().minusDays(3), null, 0.0); // Overdue
        Rental rental3 = new Rental(car3, customerC005, LocalDate.now().minusDays(3), 
                                   LocalDate.now().plusDays(4), null, 0.0);
        
        // Add rentals to store
        store.addRental(rental1); store.addRental(rental2); store.addRental(rental3);
        
        // Get rental count per customer
        Map<Customer, Integer> rentalCounts = store.getRentalCountPerCustomer();
        
        // Verify that customer C005 has 3 rentals (overdue rental is still counted)
        assertEquals(3, rentalCounts.get(customerC005).intValue());
    }
}