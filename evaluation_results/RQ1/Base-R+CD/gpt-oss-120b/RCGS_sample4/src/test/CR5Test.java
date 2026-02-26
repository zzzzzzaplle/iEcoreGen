import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.util.Map;
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
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C001
        Customer customer = new Customer("John", "Doe", "123 Main St", null);
        
        // Add cars with plates "ABC123", "XYZ456", "LMN789"
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        
        // Add 3 rental records for customer C001 with different car details
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setTotalPrice(150.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setTotalPrice(135.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setTotalPrice(120.0);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create customer C001 and C002
        Customer customer1 = new Customer("John", "Doe", "123 Main St", null);
        Customer customer2 = new Customer("Jane", "Smith", "456 Oak Ave", null);
        
        // Customer C001 rented cars with plates "ABC123", "XYZ456"
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        Car car4 = new Car("OPQ012", "Chevrolet Malibu", 55.0);
        Car car5 = new Car("RST345", "Nissan Altima", 48.0);
        Car car6 = new Car("UVW678", "Hyundai Elantra", 42.0);
        Car car7 = new Car("JKL901", "Kia Optima", 46.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        
        // Add all rentals to store
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        store.addRental(rental5);
        store.addRental(rental6);
        store.addRental(rental7);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C001 = 2, customer C002 = 5
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer = new Customer("Bob", "Johnson", "789 Pine Rd", null);
        
        // No rental records are added for customer C003
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Empty map (no rentals)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C004
        Customer customer = new Customer("Alice", "Brown", "321 Elm St", null);
        
        // Add rental records for customer C004 (4 rentals)
        Car car1 = new Car("DEF234", "Toyota Corolla", 35.0);
        Car car2 = new Car("GHI567", "Honda Accord", 52.0);
        Car car3 = new Car("JKL890", "Ford Fusion", 47.0);
        Car car4 = new Car("MNO123", "Chevrolet Impala", 58.0);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setBackDate(dateFormat.parse("2024-01-15 10:00:00")); // returned
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setBackDate(dateFormat.parse("2024-01-20 14:30:00")); // returned
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        // not returned
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        // not returned
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C004 = 4 (all stored rentals)
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer("Charlie", "Wilson", "654 Maple Dr", null);
        
        // Add rental records for customer C005 (3 rentals)
        Car car1 = new Car("PQR456", "BMW 3 Series", 75.0);
        Car car2 = new Car("STU789", "Mercedes C-Class", 80.0);
        Car car3 = new Car("VWX012", "Audi A4", 70.0);
        
        Date currentDate = dateFormat.parse("2024-02-01 12:00:00");
        Date overdueDueDate = dateFormat.parse("2024-01-25 10:00:00"); // overdue
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setDueDate(dateFormat.parse("2024-02-05 10:00:00")); // not overdue
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setDueDate(overdueDueDate); // overdue
        rental2.setBackDate(null); // not returned
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setDueDate(dateFormat.parse("2024-02-10 10:00:00")); // not overdue
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Additional verification for overdue rentals
        int overdueCount = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getCustomer().equals(customer) && 
                rental.getBackDate() == null && 
                rental.getDueDate() != null && 
                rental.getDueDate().before(currentDate)) {
                overdueCount++;
            }
        }
        assertEquals(1, overdueCount);
    }
}