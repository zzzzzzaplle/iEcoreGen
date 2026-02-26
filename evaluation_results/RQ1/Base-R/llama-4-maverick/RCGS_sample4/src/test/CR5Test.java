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
        // SetUp: Create a store instance
        // Create customer C001
        Customer customer1 = new Customer("John", "Doe", "Address1", "ABC123", LocalDate.now().plusDays(7));
        customer1.setName("C001"); // Using name as customer ID as per test specification
        
        // Add 3 cars
        Car car1 = new Car("ABC123", "Model1", 50.0);
        Car car2 = new Car("XYZ456", "Model2", 60.0);
        Car car3 = new Car("LMN789", "Model3", 70.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Add 3 rental records for customer C001
        Rental rental1 = new Rental(customer1, car1);
        Rental rental2 = new Rental(customer1, car2);
        Rental rental3 = new Rental(customer1, car3);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Execute: Count rentals per customer
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C001 = 3
        assertEquals("Customer C001 should have 3 rentals", Integer.valueOf(3), result.get("C001 Doe"));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // SetUp: Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer("C001", "Smith", "Address1", "ABC123", LocalDate.now().plusDays(7));
        Customer customer2 = new Customer("C002", "Johnson", "Address2", "LMN789", LocalDate.now().plusDays(7));
        
        // Add cars for customer C001 (2 rentals)
        Car car1 = new Car("ABC123", "Model1", 50.0);
        Car car2 = new Car("XYZ456", "Model2", 60.0);
        
        // Add cars for customer C002 (5 rentals)
        Car car3 = new Car("LMN789", "Model3", 70.0);
        Car car4 = new Car("OPQ012", "Model4", 80.0);
        Car car5 = new Car("RST345", "Model5", 90.0);
        Car car6 = new Car("UVW678", "Model6", 100.0);
        Car car7 = new Car("JKL901", "Model7", 110.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        store.addCar(car5);
        store.addCar(car6);
        store.addCar(car7);
        
        // Add rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental(customer1, car1);
        Rental rental2 = new Rental(customer1, car2);
        
        // Add rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental(customer2, car3);
        Rental rental4 = new Rental(customer2, car4);
        Rental rental5 = new Rental(customer2, car5);
        Rental rental6 = new Rental(customer2, car6);
        Rental rental7 = new Rental(customer2, car7);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        store.addRental(rental5);
        store.addRental(rental6);
        store.addRental(rental7);
        
        // Execute: Count rentals per customer
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C001 = 2 and C002 = 5
        assertEquals("Customer C001 should have 2 rentals", Integer.valueOf(2), result.get("C001 Smith"));
        assertEquals("Customer C002 should have 5 rentals", Integer.valueOf(5), result.get("C002 Johnson"));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create a store instance
        // Create customer C003
        Customer customer3 = new Customer("C003", "Brown", "Address3", null, LocalDate.now().plusDays(7));
        store.addCustomer(customer3);
        
        // No rental records are added
        
        // Execute: Count rentals per customer
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Empty map since no rentals
        assertTrue("Result should be empty when no rentals exist", result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // SetUp: Create a store instance
        // Create customer C004
        Customer customer4 = new Customer("C004", "Wilson", "Address4", "DEF234", LocalDate.now().plusDays(7));
        
        // Add 4 cars
        Car car1 = new Car("DEF234", "Model1", 50.0);
        Car car2 = new Car("GHI567", "Model2", 60.0);
        Car car3 = new Car("JKL890", "Model3", 70.0);
        Car car4 = new Car("MNO123", "Model4", 80.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        
        // Add 4 rental records for customer C004
        Rental rental1 = new Rental(customer4, car1);
        Rental rental2 = new Rental(customer4, car2);
        Rental rental3 = new Rental(customer4, car3);
        Rental rental4 = new Rental(customer4, car4);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        store.addRental(rental4);
        
        // Mark 2 rentals as returned by setting back date
        customer4.setBackDate(LocalDate.now().plusDays(5)); // Returned early
        Customer customer4Copy = new Customer("C004", "Wilson", "Address4", "GHI567", LocalDate.now().plusDays(7));
        customer4Copy.setBackDate(LocalDate.now().plusDays(6)); // Another returned
        
        // Execute: Count rentals per customer
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C004 = 4 (all stored rentals count)
        assertEquals("Customer C004 should have 4 total rentals regardless of return status", 
                    Integer.valueOf(4), result.get("C004 Wilson"));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // SetUp: Create a store instance
        // Create customer C005
        Customer customer5 = new Customer("C005", "Davis", "Address5", "PQR456", LocalDate.now().minusDays(1)); // Overdue
        
        // Add 3 cars
        Car car1 = new Car("PQR456", "Model1", 50.0);
        Car car2 = new Car("STU789", "Model2", 60.0);
        Car car3 = new Car("VWX012", "Model3", 70.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create overdue customer
        Customer overdueCustomer = new Customer("C005", "Davis", "Address5", "STU789", LocalDate.now().minusDays(5)); // Overdue
        
        // Add 3 rental records for customer C005
        Rental rental1 = new Rental(customer5, car1);
        Rental rental2 = new Rental(overdueCustomer, car2);
        Rental rental3 = new Rental(customer5, car3);
        
        store.addRental(rental1);
        store.addRental(rental2);
        store.addRental(rental3);
        
        // Execute: Count rentals per customer
        Map<String, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify: Number of rentals for customer C005 = 3
        assertEquals("Customer C005 should have 3 total rentals regardless of overdue status", 
                    Integer.valueOf(3), result.get("C005 Davis"));
    }
}