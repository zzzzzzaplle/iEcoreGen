import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CR5Test {
    
    private CarRentalSystem system;
    
    @Before
    public void setUp() {
        system = new CarRentalSystem();
    }
    
    /**
     * Helper method to count rentals per customer
     * This method implements the computational requirement
     */
    private Map<Customer, Integer> countRentalsPerCustomer() {
        Map<Customer, Integer> rentalCounts = new HashMap<>();
        
        // Get all rentals from the system
        List<Rental> allRentals = system.getRentals();
        
        // Count rentals for each customer
        for (Rental rental : allRentals) {
            Customer customer = rental.getCustomer();
            rentalCounts.put(customer, rentalCounts.getOrDefault(customer, 0) + 1);
        }
        
        return rentalCounts;
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // SetUp: Create a store instance, customer with ID C001, and 3 rental records
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        
        // Create 3 cars
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        
        // Add cars to system
        system.addCar(car1);
        system.addCar(car2);
        system.addCar(car3);
        
        // Add customer to system
        system.addCustomer(customer);
        
        // Create 3 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(LocalDate.now().minusDays(10));
        rental1.setDueDate(LocalDate.now().plusDays(5));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(LocalDate.now().minusDays(7));
        rental2.setDueDate(LocalDate.now().plusDays(3));
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(LocalDate.now().minusDays(3));
        rental3.setDueDate(LocalDate.now().plusDays(7));
        
        // Add rentals to system
        system.addRental(rental1);
        system.addRental(rental2);
        system.addRental(rental3);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = countRentalsPerCustomer();
        
        // Verify: Number of rentals for customer C001 = 3
        assertEquals("Map should contain exactly one customer", 1, result.size());
        assertEquals("Customer should have 3 rentals", Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // SetUp: Create customers C001 and C002 with their respective rentals
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Smith");
        
        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setSurname("Johnson");
        
        // Create cars for customer1 (2 rentals)
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        
        // Create cars for customer2 (5 rentals)
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("Nissan Altima");
        car4.setDailyPrice(55.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Chevrolet Malibu");
        car5.setDailyPrice(48.0);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Hyundai Elantra");
        car6.setDailyPrice(42.0);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Kia Optima");
        car7.setDailyPrice(46.0);
        
        // Add all cars to system
        system.addCar(car1);
        system.addCar(car2);
        system.addCar(car3);
        system.addCar(car4);
        system.addCar(car5);
        system.addCar(car6);
        system.addCar(car7);
        
        // Add customers to system
        system.addCustomer(customer1);
        system.addCustomer(customer2);
        
        // Create rentals for customer1 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentalDate(LocalDate.now().minusDays(10));
        rental1.setDueDate(LocalDate.now().plusDays(5));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentalDate(LocalDate.now().minusDays(7));
        rental2.setDueDate(LocalDate.now().plusDays(3));
        
        // Create rentals for customer2 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentalDate(LocalDate.now().minusDays(15));
        rental3.setDueDate(LocalDate.now().plusDays(10));
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentalDate(LocalDate.now().minusDays(12));
        rental4.setDueDate(LocalDate.now().plusDays(8));
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentalDate(LocalDate.now().minusDays(8));
        rental5.setDueDate(LocalDate.now().plusDays(12));
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentalDate(LocalDate.now().minusDays(5));
        rental6.setDueDate(LocalDate.now().plusDays(5));
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentalDate(LocalDate.now().minusDays(3));
        rental7.setDueDate(LocalDate.now().plusDays(7));
        
        // Add all rentals to system
        system.addRental(rental1);
        system.addRental(rental2);
        system.addRental(rental3);
        system.addRental(rental4);
        system.addRental(rental5);
        system.addRental(rental6);
        system.addRental(rental7);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = countRentalsPerCustomer();
        
        // Verify: Customer1 should have 2 rentals, Customer2 should have 5 rentals
        assertEquals("Map should contain exactly 2 customers", 2, result.size());
        assertEquals("Customer1 should have 2 rentals", Integer.valueOf(2), result.get(customer1));
        assertEquals("Customer2 should have 5 rentals", Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create a customer with ID C003 but no rental records
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Brown");
        
        // Add customer to system
        system.addCustomer(customer);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = countRentalsPerCustomer();
        
        // Verify: Expected Output: Null (empty map)
        assertNotNull("Result should not be null", result);
        assertTrue("Map should be empty when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // SetUp: Customer C004 with 4 rentals, 2 of them returned
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Wilson");
        
        // Create 4 cars
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Nissan Altima");
        car4.setDailyPrice(55.0);
        
        // Add cars to system
        system.addCar(car1);
        system.addCar(car2);
        system.addCar(car3);
        system.addCar(car4);
        
        // Add customer to system
        system.addCustomer(customer);
        
        // Create 4 rental records
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(LocalDate.now().minusDays(20));
        rental1.setDueDate(LocalDate.now().minusDays(10));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(LocalDate.now().minusDays(15));
        rental2.setDueDate(LocalDate.now().minusDays(5));
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(LocalDate.now().minusDays(8));
        rental3.setDueDate(LocalDate.now().plusDays(2));
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        rental4.setRentalDate(LocalDate.now().minusDays(3));
        rental4.setDueDate(LocalDate.now().plusDays(7));
        
        // Add rentals to system
        system.addRental(rental1);
        system.addRental(rental2);
        system.addRental(rental3);
        system.addRental(rental4);
        
        // Mark 2 rentals as returned
        rental1.setReturnDate(LocalDate.now().minusDays(12));
        car1.markAsReturned();
        
        rental2.setReturnDate(LocalDate.now().minusDays(7));
        car2.markAsReturned();
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = countRentalsPerCustomer();
        
        // Verify: Number of rentals for customer C004 = 4 (stored)
        assertEquals("Customer should have 4 total rentals", Integer.valueOf(4), result.get(customer));
        
        // Additional verification: Currently active rentals = 2
        List<Rental> activeRentals = customer.getActiveRentals();
        assertEquals("Customer should have 2 active rentals", 2, activeRentals.size());
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // SetUp: Customer C005 with 3 rentals, 1 overdue
        Customer customer = new Customer();
        customer.setName("Eva");
        customer.setSurname("Davis");
        
        // Create 3 cars
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(45.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(40.0);
        
        // Add cars to system
        system.addCar(car1);
        system.addCar(car2);
        system.addCar(car3);
        
        // Add customer to system
        system.addCustomer(customer);
        
        // Create 3 rental records
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentalDate(LocalDate.now().minusDays(10));
        rental1.setDueDate(LocalDate.now().plusDays(5)); // Not overdue
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentalDate(LocalDate.now().minusDays(15));
        rental2.setDueDate(LocalDate.now().minusDays(2)); // Overdue
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setRentalDate(LocalDate.now().minusDays(3));
        rental3.setDueDate(LocalDate.now().plusDays(7)); // Not overdue
        
        // Add rentals to system
        system.addRental(rental1);
        system.addRental(rental2);
        system.addRental(rental3);
        
        // Execute: Count rentals per customer
        Map<Customer, Integer> result = countRentalsPerCustomer();
        
        // Verify: Number of rentals for customer C005 = 3
        assertEquals("Customer should have 3 rentals", Integer.valueOf(3), result.get(customer));
        
        // Additional verification: Number of overdue rentals for customer C005 = 1
        int overdueCount = 0;
        for (Rental rental : customer.getRentalHistory()) {
            if (rental.isOverdue()) {
                overdueCount++;
            }
        }
        assertEquals("Customer should have 1 overdue rental", 1, overdueCount);
    }
}