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
        // Create a customer with customer ID: C001
        Customer customer1 = new Customer("John", "Doe", "Address1", "ABC123", LocalDate.now().plusDays(7));
        customer1.setName("C001"); // Using name field as customer ID as per specification
        
        // Add 3 rental records for customer C001 with different car details
        // Add cars with plates "ABC123", "XYZ456", "LMN789" rented by customer C001
        Car car1 = new Car("ABC123", "Model1", 50.0);
        Car car2 = new Car("XYZ456", "Model2", 60.0);
        Car car3 = new Car("LMN789", "Model3", 70.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Create rental records by adding customer with different car plates
        store.addCustomer(customer1);
        store.addCustomer(new Customer("C001", "Doe", "Address1", "XYZ456", LocalDate.now().plusDays(7)));
        store.addCustomer(new Customer("C001", "Doe", "Address1", "LMN789", LocalDate.now().plusDays(7)));
        
        // Expected Output: Number of rentals for customer C001 = 3
        Map<String, Long> result = store.countCarsRentedPerCustomer();
        assertEquals(3, result.get("C001 Doe").longValue());
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // SetUp: Create a store instance
        // Create customer C001 and C002
        Customer customer1 = new Customer("C001", "Smith", "Address1", "ABC123", LocalDate.now().plusDays(7));
        Customer customer2 = new Customer("C002", "Johnson", "Address2", "LMN789", LocalDate.now().plusDays(7));
        
        // Add rental records for customer C001 (2 rentals) and customer C002 (5 rentals)
        // Customer C001 rented cars with plates "ABC123", "XYZ456"
        // Customer C002 rented cars with plates "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        
        // Add cars to store
        Car car1 = new Car("ABC123", "Model1", 50.0);
        Car car2 = new Car("XYZ456", "Model2", 60.0);
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
        
        // Add rental records for C001
        store.addCustomer(customer1);
        store.addCustomer(new Customer("C001", "Smith", "Address1", "XYZ456", LocalDate.now().plusDays(7)));
        
        // Add rental records for C002
        store.addCustomer(customer2);
        store.addCustomer(new Customer("C002", "Johnson", "Address2", "OPQ012", LocalDate.now().plusDays(7)));
        store.addCustomer(new Customer("C002", "Johnson", "Address2", "RST345", LocalDate.now().plusDays(7)));
        store.addCustomer(new Customer("C002", "Johnson", "Address2", "UVW678", LocalDate.now().plusDays(7)));
        store.addCustomer(new Customer("C002", "Johnson", "Address2", "JKL901", LocalDate.now().plusDays(7)));
        
        // Expected Output: Number of rentals for customer C001 = 2, customer C002 = 5
        Map<String, Long> result = store.countCarsRentedPerCustomer();
        assertEquals(2, result.get("C001 Smith").longValue());
        assertEquals(5, result.get("C002 Johnson").longValue());
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C003
        Customer customer = new Customer("C003", "Brown", "Address3", null, null);
        
        // No rental records are added for customer C003
        // Customer is added but with no rental car plate
        store.addCustomer(customer);
        
        // Expected Output: Null (empty map since no rentals recorded)
        Map<String, Long> result = store.countCarsRentedPerCustomer();
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C004
        Customer customer1 = new Customer("C004", "Wilson", "Address4", "DEF234", LocalDate.now().plusDays(7));
        Customer customer2 = new Customer("C004", "Wilson", "Address4", "GHI567", LocalDate.now().plusDays(7));
        Customer customer3 = new Customer("C004", "Wilson", "Address4", "JKL890", LocalDate.now().plusDays(7));
        Customer customer4 = new Customer("C004", "Wilson", "Address4", "MNO123", LocalDate.now().plusDays(7));
        
        // Add rental records for customer C004 (4 rentals) and mark 2 of them as returned
        // Rental records: Customer C004 rented cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        
        // Add cars to store
        Car car1 = new Car("DEF234", "Model1", 50.0);
        Car car2 = new Car("GHI567", "Model2", 60.0);
        Car car3 = new Car("JKL890", "Model3", 70.0);
        Car car4 = new Car("MNO123", "Model4", 80.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        store.addCar(car4);
        
        // Add all rental records
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        store.addCustomer(customer3);
        store.addCustomer(customer4);
        
        // Mark 2 rentals as returned by setting back date
        customer2.setBackDate(LocalDate.now());
        customer3.setBackDate(LocalDate.now());
        
        // Expected Output: Number of rentals for customer C004 = 4 (stored)
        Map<String, Long> result = store.countCarsRentedPerCustomer();
        assertEquals(4, result.get("C004 Wilson").longValue());
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // SetUp: Create a store instance
        // Create a customer with customer ID: C005
        Customer customer1 = new Customer("C005", "Davis", "Address5", "PQR456", LocalDate.now().minusDays(1)); // Overdue
        Customer customer2 = new Customer("C005", "Davis", "Address5", "STU789", LocalDate.now().plusDays(7));
        Customer customer3 = new Customer("C005", "Davis", "Address5", "VWX012", LocalDate.now().plusDays(7));
        
        // Add rental records for customer C005 (3 rentals), with one marked as overdue
        // Rental records: Customer C005 rented cars with plates "PQR456", "STU789", "VWX012" with "PQR456" being overdue
        
        // Add cars to store
        Car car1 = new Car("PQR456", "Model1", 50.0);
        Car car2 = new Car("STU789", "Model2", 60.0);
        Car car3 = new Car("VWX012", "Model3", 70.0);
        
        store.addCar(car1);
        store.addCar(car2);
        store.addCar(car3);
        
        // Add all rental records
        store.addCustomer(customer1);
        store.addCustomer(customer2);
        store.addCustomer(customer3);
        
        // Expected Output: Number of rentals for customer C005 = 3
        Map<String, Long> result = store.countCarsRentedPerCustomer();
        assertEquals(3, result.get("C005 Davis").longValue());
        
        // Verify overdue count separately using getOverdueCustomers()
        assertEquals(1, store.getOverdueCustomers().size());
    }
}