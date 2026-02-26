import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR5Test {
    
    private CarGallery carGallery;
    
    @Before
    public void setUp() {
        // Initialize CarGallery before each test
        carGallery = new CarGallery();
    }
    
    @Test
    public void testCase1_countRentalsForSingleCustomer() {
        // Test Case 1: Count Rentals for a Single Customer
        // Input: Customer with ID C001 rented 3 cars.
        
        // SetUp: Create customer C001
        Customer customerC001 = new Customer("John", "Doe", "123 Main St");
        
        // SetUp: Create cars with plates "ABC123", "XYZ456", "LMN789"
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        
        // Add cars to gallery
        carGallery.addCar(car1);
        carGallery.addCar(car2);
        carGallery.addCar(car3);
        
        // SetUp: Add 3 rental records for customer C001
        LocalDate startDate = LocalDate.now().minusDays(10);
        LocalDate dueDate = LocalDate.now().plusDays(5);
        
        Rental rental1 = new Rental(car1, customerC001, startDate, dueDate);
        Rental rental2 = new Rental(car2, customerC001, startDate, dueDate);
        Rental rental3 = new Rental(car3, customerC001, startDate, dueDate);
        
        carGallery.addRental(rental1);
        carGallery.addRental(rental2);
        carGallery.addRental(rental3);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = carGallery.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customerC001));
        assertEquals(Integer.valueOf(3), result.get(customerC001));
    }
    
    @Test
    public void testCase2_countRentalsForMultipleCustomers() {
        // Test Case 2: Count Rentals for Multiple Customers
        // Input: Customers C001 and C002 rented cars.
        
        // SetUp: Create customers C001 and C002
        Customer customerC001 = new Customer("John", "Doe", "123 Main St");
        Customer customerC002 = new Customer("Jane", "Smith", "456 Oak St");
        
        // SetUp: Create cars
        Car car1 = new Car("ABC123", "Toyota Camry", 50.0);
        Car car2 = new Car("XYZ456", "Honda Civic", 45.0);
        Car car3 = new Car("LMN789", "Ford Focus", 40.0);
        Car car4 = new Car("OPQ012", "Chevrolet Malibu", 55.0);
        Car car5 = new Car("RST345", "Nissan Altima", 48.0);
        Car car6 = new Car("UVW678", "Hyundai Elantra", 42.0);
        Car car7 = new Car("JKL901", "Kia Optima", 46.0);
        
        // Add all cars to gallery
        carGallery.addCar(car1);
        carGallery.addCar(car2);
        carGallery.addCar(car3);
        carGallery.addCar(car4);
        carGallery.addCar(car5);
        carGallery.addCar(car6);
        carGallery.addCar(car7);
        
        // SetUp: Add rental records
        LocalDate startDate = LocalDate.now().minusDays(10);
        LocalDate dueDate = LocalDate.now().plusDays(5);
        
        // Customer C001: 2 rentals
        Rental rental1 = new Rental(car1, customerC001, startDate, dueDate);
        Rental rental2 = new Rental(car2, customerC001, startDate, dueDate);
        
        // Customer C002: 5 rentals
        Rental rental3 = new Rental(car3, customerC002, startDate, dueDate);
        Rental rental4 = new Rental(car4, customerC002, startDate, dueDate);
        Rental rental5 = new Rental(car5, customerC002, startDate, dueDate);
        Rental rental6 = new Rental(car6, customerC002, startDate, dueDate);
        Rental rental7 = new Rental(car7, customerC002, startDate, dueDate);
        
        carGallery.addRental(rental1);
        carGallery.addRental(rental2);
        carGallery.addRental(rental3);
        carGallery.addRental(rental4);
        carGallery.addRental(rental5);
        carGallery.addRental(rental6);
        carGallery.addRental(rental7);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = carGallery.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C001 = 2, for customer C002 = 5
        assertEquals(2, result.size());
        assertTrue(result.containsKey(customerC001));
        assertTrue(result.containsKey(customerC002));
        assertEquals(Integer.valueOf(2), result.get(customerC001));
        assertEquals(Integer.valueOf(5), result.get(customerC002));
    }
    
    @Test
    public void testCase3_countRentalsWithNoRecords() {
        // Test Case 3: Count Rentals with No Records
        // Input: No cars rented by any customer.
        
        // SetUp: Create a customer with customer ID: C003
        Customer customerC003 = new Customer("Bob", "Johnson", "789 Pine St");
        
        // SetUp: No rental records are added for customer C003
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = carGallery.countCarsRentedPerCustomer();
        
        // Verify: Expected Output: Empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_countRentalsIncludingReturnedCars() {
        // Test Case 4: Count Rentals Including Returned Cars
        // Input: Customer rented and returned 4 cars.
        
        // SetUp: Create a customer with customer ID: C004
        Customer customerC004 = new Customer("Alice", "Brown", "321 Elm St");
        
        // SetUp: Create cars
        Car car1 = new Car("DEF234", "Toyota Corolla", 35.0);
        Car car2 = new Car("GHI567", "Honda Accord", 60.0);
        Car car3 = new Car("JKL890", "Ford Fusion", 55.0);
        Car car4 = new Car("MNO123", "Chevrolet Impala", 65.0);
        
        // Add cars to gallery
        carGallery.addCar(car1);
        carGallery.addCar(car2);
        carGallery.addCar(car3);
        carGallery.addCar(car4);
        
        // SetUp: Add rental records for customer C004 (4 rentals)
        LocalDate startDate = LocalDate.now().minusDays(20);
        LocalDate dueDate = LocalDate.now().minusDays(5);
        
        Rental rental1 = new Rental(car1, customerC004, startDate, dueDate);
        Rental rental2 = new Rental(car2, customerC004, startDate, dueDate);
        Rental rental3 = new Rental(car3, customerC004, startDate, dueDate);
        Rental rental4 = new Rental(car4, customerC004, startDate, dueDate);
        
        // Mark 2 of them as returned
        rental1.setBackDate(LocalDate.now().minusDays(10));
        rental3.setBackDate(LocalDate.now().minusDays(8));
        
        carGallery.addRental(rental1);
        carGallery.addRental(rental2);
        carGallery.addRental(rental3);
        carGallery.addRental(rental4);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = carGallery.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C004 = 4 (stored)
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customerC004));
        assertEquals(Integer.valueOf(4), result.get(customerC004));
    }
    
    @Test
    public void testCase5_countRentalsForCustomerWithOverdueCars() {
        // Test Case 5: Count Rentals for a Customer with Overdue Cars
        // Input: Customer rented 3 cars, with 1 overdue.
        
        // SetUp: Create a customer with customer ID: C005
        Customer customerC005 = new Customer("Charlie", "Wilson", "654 Maple St");
        
        // SetUp: Create cars
        Car car1 = new Car("PQR456", "BMW 3 Series", 80.0);
        Car car2 = new Car("STU789", "Mercedes C-Class", 85.0);
        Car car3 = new Car("VWX012", "Audi A4", 78.0);
        
        // Add cars to gallery
        carGallery.addCar(car1);
        carGallery.addCar(car2);
        carGallery.addCar(car3);
        
        // SetUp: Add rental records for customer C005 (3 rentals)
        LocalDate startDate = LocalDate.now().minusDays(15);
        LocalDate dueDateNormal = LocalDate.now().plusDays(5);
        LocalDate dueDateOverdue = LocalDate.now().minusDays(3); // Overdue due date
        
        Rental rental1 = new Rental(car1, customerC005, startDate, dueDateNormal);
        Rental rental2 = new Rental(car2, customerC005, startDate, dueDateOverdue); // Overdue
        Rental rental3 = new Rental(car3, customerC005, startDate, dueDateNormal);
        
        carGallery.addRental(rental1);
        carGallery.addRental(rental2);
        carGallery.addRental(rental3);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = carGallery.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertTrue(result.containsKey(customerC005));
        assertEquals(Integer.valueOf(3), result.get(customerC005));
        
        // Additional verification for overdue count (using listOverdueCustomers method)
        assertEquals(1, carGallery.listOverdueCustomers().size());
        assertTrue(carGallery.listOverdueCustomers().contains(customerC005));
    }
}