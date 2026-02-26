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
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Add 3 rental records for customer C001 with different car details
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-07 10:00:00");
        
        Rental rental1 = new Rental(rentalDate, dueDate, null, 300.0, "Standard", car1, customer);
        Rental rental2 = new Rental(rentalDate, dueDate, null, 270.0, "Standard", car2, customer);
        Rental rental3 = new Rental(rentalDate, dueDate, null, 240.0, "Standard", car3, customer);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Number of rentals for customer C001 = 3
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
        
        // Add cars with plates "ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
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
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-07 10:00:00");
        
        // Customer C001 rentals
        Rental rental1 = new Rental(rentalDate, dueDate, null, 300.0, "Standard", car1, customer1);
        Rental rental2 = new Rental(rentalDate, dueDate, null, 270.0, "Standard", car2, customer1);
        
        // Customer C002 rentals
        Rental rental3 = new Rental(rentalDate, dueDate, null, 240.0, "Standard", car3, customer2);
        Rental rental4 = new Rental(rentalDate, dueDate, null, 330.0, "Standard", car4, customer2);
        Rental rental5 = new Rental(rentalDate, dueDate, null, 288.0, "Standard", car5, customer2);
        Rental rental6 = new Rental(rentalDate, dueDate, null, 252.0, "Standard", car6, customer2);
        Rental rental7 = new Rental(rentalDate, dueDate, null, 276.0, "Standard", car7, customer2);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        store.addRental(rental5);
        store.addRental(rental6);
        store.addRental(rental7);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C001 = 2
        // - Number of rentals for customer C002 = 5
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C003
        Customer customer = new Customer("Bob", "Johnson", "789 Pine St", null);
        
        // No rental records are added for customer C003
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: Null
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C004
        Customer customer = new Customer("Alice", "Brown", "321 Elm St", null);
        
        // Add cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Car car1 = new Car("DEF234", "Toyota Corolla", 35.0);
        Car car2 = new Car("GHI567", "Honda Accord", 60.0);
        Car car3 = new Car("JKL890", "Ford Mustang", 80.0);
        Car car4 = new Car("MNO123", "Chevrolet Impala", 55.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate = dateFormat.parse("2024-01-07 10:00:00");
        Date returnDate = dateFormat.parse("2024-01-05 15:00:00");
        
        Rental rental1 = new Rental(rentalDate, dueDate, returnDate, 210.0, "Standard", car1, customer);
        Rental rental2 = new Rental(rentalDate, dueDate, returnDate, 360.0, "Standard", car2, customer);
        Rental rental3 = new Rental(rentalDate, dueDate, null, 480.0, "Standard", car3, customer);
        Rental rental4 = new Rental(rentalDate, dueDate, null, 330.0, "Standard", car4, customer);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C004 = 4 (stored) 
        // - Currently active rentals = 2 (after marking 2 as returned)
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
        
        // Verify active rentals count separately
        int activeRentals = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getBackDate() == null) {
                activeRentals++;
            }
        }
        assertEquals(2, activeRentals);
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create a store instance
        Store store = new Store();
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer("Charlie", "Wilson", "654 Maple Rd", null);
        
        // Add cars with plates "PQR456", "STU789", "VWX012"
        Car car1 = new Car("PQR456", "BMW 3 Series", 90.0);
        Car car2 = new Car("STU789", "Mercedes C-Class", 95.0);
        Car car3 = new Car("VWX012", "Audi A4", 85.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        Date rentalDate = dateFormat.parse("2024-01-01 10:00:00");
        Date dueDate1 = dateFormat.parse("2024-01-07 10:00:00");
        Date dueDate2 = dateFormat.parse("2024-01-05 10:00:00"); // Overdue date
        
        Rental rental1 = new Rental(rentalDate, dueDate1, null, 540.0, "Standard", car1, customer);
        Rental rental2 = new Rental(rentalDate, dueDate2, null, 570.0, "Standard", car2, customer);
        Rental rental3 = new Rental(rentalDate, dueDate1, null, 510.0, "Standard", car3, customer);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C005 = 3
        // - Number of overdue rentals for customer C005 = 1
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
        
        // Verify overdue rentals count separately
        Date currentDate = dateFormat.parse("2024-01-06 12:00:00");
        int overdueRentals = 0;
        for (Rental rental : store.getRentals()) {
            if (rental.getBackDate() == null && rental.getDueDate() != null && 
                rental.getDueDate().before(currentDate)) {
                overdueRentals++;
            }
        }
        assertEquals(1, overdueRentals);
    }
}