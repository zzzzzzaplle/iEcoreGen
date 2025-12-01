import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CR5Test {
    
    private CarRentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        // Initialize a fresh rental system before each test
        rentalSystem = new CarRentalSystem();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create a store instance
        CarRentalSystem system = rentalSystem;
        
        // Create a customer with customer ID: C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        customer.setAddress("123 Main St");
        system.addCustomer(customer);
        
        // Add 3 rental records for customer C001 with different car details
        // Add cars with plates "ABC123", "XYZ456", "LMN789" rented by customer C001
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        system.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(35.0);
        system.addCar(car3);
        
        // Create rentals for the customer
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setStartDate("2024-01-01");
        rental1.setDueDate("2024-01-05");
        rental1.setTotalCost(200.0);
        system.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setStartDate("2024-02-01");
        rental2.setDueDate("2024-02-07");
        rental2.setTotalCost(240.0);
        system.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setStartDate("2024-03-01");
        rental3.setDueDate("2024-03-10");
        rental3.setTotalCost(315.0);
        system.addRental(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> rentalCounts = system.countCarsRentedPerCustomer();
        
        // Expected Output: Number of rentals for customer C001 = 3
        assertEquals(3, rentalCounts.get(customer).intValue());
        assertEquals(1, rentalCounts.size()); // Only one customer
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create a store instance
        CarRentalSystem system = rentalSystem;
        
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Smith");
        customer1.setAddress("456 Oak St");
        system.addCustomer(customer1);
        
        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setSurname("Johnson");
        customer2.setAddress("789 Pine St");
        system.addCustomer(customer2);
        
        // Add cars for rental
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        system.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(35.0);
        system.addCar(car3);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("Chevrolet Malibu");
        car4.setDailyPrice(45.0);
        system.addCar(car4);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Nissan Altima");
        car5.setDailyPrice(42.0);
        system.addCar(car5);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Hyundai Elantra");
        car6.setDailyPrice(38.0);
        system.addCar(car6);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Kia Optima");
        car7.setDailyPrice(39.0);
        system.addCar(car7);
        
        // Add rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setStartDate("2024-01-01");
        rental1.setDueDate("2024-01-05");
        rental1.setTotalCost(200.0);
        system.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setStartDate("2024-02-01");
        rental2.setDueDate("2024-02-07");
        rental2.setTotalCost(240.0);
        system.addRental(rental2);
        
        // Add rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setStartDate("2024-01-10");
        rental3.setDueDate("2024-01-15");
        rental3.setTotalCost(175.0);
        system.addRental(rental3);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setStartDate("2024-02-10");
        rental4.setDueDate("2024-02-20");
        rental4.setTotalCost(450.0);
        system.addRental(rental4);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setStartDate("2024-03-01");
        rental5.setDueDate("2024-03-08");
        rental5.setTotalCost(294.0);
        system.addRental(rental5);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setStartDate("2024-04-01");
        rental6.setDueDate("2024-04-10");
        rental6.setTotalCost(342.0);
        system.addRental(rental6);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setStartDate("2024-05-01");
        rental7.setDueDate("2024-05-07");
        rental7.setTotalCost(234.0);
        system.addRental(rental7);
        
        // Count rentals per customer
        Map<Customer, Integer> rentalCounts = system.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C001 = 2
        // - Number of rentals for customer C002 = 5
        assertEquals(2, rentalCounts.get(customer1).intValue());
        assertEquals(5, rentalCounts.get(customer2).intValue());
        assertEquals(2, rentalCounts.size()); // Two customers
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create a store instance
        CarRentalSystem system = rentalSystem;
        
        // Create a customer with customer ID: C003
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Brown");
        customer.setAddress("321 Elm St");
        system.addCustomer(customer);
        
        // No rental records are added for customer C003
        
        // Count rentals per customer
        Map<Customer, Integer> rentalCounts = system.countCarsRentedPerCustomer();
        
        // Expected Output: Empty map (not null)
        assertNotNull(rentalCounts);
        assertTrue(rentalCounts.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create a store instance
        CarRentalSystem system = rentalSystem;
        
        // Create a customer with customer ID: C004
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Wilson");
        customer.setAddress("654 Maple St");
        system.addCustomer(customer);
        
        // Add cars for rental
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota Corolla");
        car1.setDailyPrice(35.0);
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(45.0);
        system.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford Fusion");
        car3.setDailyPrice(40.0);
        system.addCar(car3);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet Impala");
        car4.setDailyPrice(42.0);
        system.addCar(car4);
        
        // Add rental records for customer C004 (4 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setStartDate("2024-01-01");
        rental1.setDueDate("2024-01-05");
        rental1.setTotalCost(140.0);
        system.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setStartDate("2024-02-01");
        rental2.setDueDate("2024-02-07");
        rental2.setTotalCost(270.0);
        system.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setStartDate("2024-03-01");
        rental3.setDueDate("2024-03-10");
        rental3.setTotalCost(360.0);
        system.addRental(rental3);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer);
        rental4.setCar(car4);
        rental4.setStartDate("2024-04-01");
        rental4.setDueDate("2024-04-08");
        rental4.setTotalCost(294.0);
        system.addRental(rental4);
        
        // Mark 2 of them as returned
        system.returnCar(rental1, "2024-01-05");
        system.returnCar(rental2, "2024-02-07");
        
        // Count rentals per customer
        Map<Customer, Integer> rentalCounts = system.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C004 = 4 (stored) 
        assertEquals(4, rentalCounts.get(customer).intValue());
        
        // Verify currently active rentals = 2 (after marking 2 as returned)
        List<Rental> activeRentals = system.getActiveRentals();
        assertEquals(2, activeRentals.size());
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() throws Exception {
        // Create a store instance
        CarRentalSystem system = rentalSystem;
        
        // Create a customer with customer ID: C005
        Customer customer = new Customer();
        customer.setName("Emma");
        customer.setSurname("Davis");
        customer.setAddress("987 Cedar St");
        system.addCustomer(customer);
        
        // Add cars for rental
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota Avalon");
        car1.setDailyPrice(55.0);
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda Insight");
        car2.setDailyPrice(48.0);
        system.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford Taurus");
        car3.setDailyPrice(43.0);
        system.addCar(car3);
        
        // Add rental records for customer C005 (3 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setStartDate("2024-01-01");
        rental1.setDueDate("2024-01-10");
        rental1.setTotalCost(495.0);
        system.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setStartDate("2024-02-01");
        rental2.setDueDate("2024-02-15"); // This will be overdue when we check with current date "2024-02-20"
        rental2.setTotalCost(672.0);
        system.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer);
        rental3.setCar(car3);
        rental3.setStartDate("2024-03-01");
        rental3.setDueDate("2024-03-12");
        rental3.setTotalCost(473.0);
        system.addRental(rental3);
        
        // Count rentals per customer
        Map<Customer, Integer> rentalCounts = system.countCarsRentedPerCustomer();
        
        // Expected Output: 
        // - Number of rentals for customer C005 = 3
        assertEquals(3, rentalCounts.get(customer).intValue());
        
        // Check for overdue rentals with current date "2024-02-20"
        // - Number of overdue rentals for customer C005 = 1
        List<Customer> overdueCustomers = system.getOverdueCustomers("2024-02-20");
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }
}