import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.*;

public class CR5Test {
    
    private CarRentalStore store;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    
    @Before
    public void setUp() {
        store = new CarRentalStore();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // SetUp: Create a customer with ID C001
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        // Create 3 cars with different plates
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setDailyPrice(70.0);
        
        // Add cars to store
        store.getCars().add(car1);
        store.getCars().add(car2);
        store.getCars().add(car3);
        
        // Add 3 rental records for customer C001
        LocalDate leaseDate = LocalDate.now();
        LocalDate dueDate = leaseDate.plusDays(7);
        
        store.rentCar(customer1, car1, leaseDate, dueDate);
        store.rentCar(customer1, car2, leaseDate, dueDate);
        store.rentCar(customer1, car3, leaseDate, dueDate);
        
        // Test: Count rentals for customer C001
        Map<Customer, Integer> result = store.getRentedCarsPerCustomer();
        
        // Expected Output: Number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer1));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // SetUp: Create customers C001 and C002
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        // Create cars for customer C001 (2 rentals)
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setDailyPrice(60.0);
        
        // Create cars for customer C002 (5 rentals)
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setDailyPrice(70.0);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setDailyPrice(80.0);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setDailyPrice(90.0);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setDailyPrice(100.0);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setDailyPrice(110.0);
        
        // Add all cars to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4, car5, car6, car7));
        
        // Add rental records
        LocalDate leaseDate = LocalDate.now();
        LocalDate dueDate = leaseDate.plusDays(7);
        
        // Customer C001: 2 rentals
        store.rentCar(customer1, car1, leaseDate, dueDate);
        store.rentCar(customer1, car2, leaseDate, dueDate);
        
        // Customer C002: 5 rentals
        store.rentCar(customer2, car3, leaseDate, dueDate);
        store.rentCar(customer2, car4, leaseDate, dueDate);
        store.rentCar(customer2, car5, leaseDate, dueDate);
        store.rentCar(customer2, car6, leaseDate, dueDate);
        store.rentCar(customer2, car7, leaseDate, dueDate);
        
        // Test: Count rentals for both customers
        Map<Customer, Integer> result = store.getRentedCarsPerCustomer();
        
        // Expected Output: C001 = 2, C002 = 5
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create a customer with ID C003
        customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        // No rental records are added for customer C003
        
        // Test: Count rentals - should return empty map
        Map<Customer, Integer> result = store.getRentedCarsPerCustomer();
        
        // Expected Output: Empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // SetUp: Create a customer with ID C004
        customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        
        // Create 4 cars
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setDailyPrice(70.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setDailyPrice(80.0);
        
        // Add cars to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3, car4));
        
        // Add 4 rental records for customer C004
        LocalDate leaseDate = LocalDate.now();
        LocalDate dueDate = leaseDate.plusDays(7);
        LocalDate returnDate = leaseDate.plusDays(3);
        
        store.rentCar(customer4, car1, leaseDate, dueDate);
        store.rentCar(customer4, car2, leaseDate, dueDate);
        store.rentCar(customer4, car3, leaseDate, dueDate);
        store.rentCar(customer4, car4, leaseDate, dueDate);
        
        // Mark 2 of them as returned
        store.returnCar(car1, returnDate);
        store.returnCar(car2, returnDate);
        
        // Test: Count rentals for customer C004
        Map<Customer, Integer> result = store.getRentedCarsPerCustomer();
        
        // Expected Output: Number of rentals for customer C004 = 4 (stored)
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer4));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // SetUp: Create a customer with ID C005
        customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Wilson");
        
        // Create 3 cars
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setDailyPrice(60.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setDailyPrice(70.0);
        
        // Add cars to store
        store.getCars().addAll(Arrays.asList(car1, car2, car3));
        
        // Add 3 rental records for customer C005
        LocalDate leaseDate = LocalDate.now().minusDays(10); // 10 days ago
        LocalDate dueDate = leaseDate.plusDays(7); // Due 3 days ago
        LocalDate futureDueDate = LocalDate.now().plusDays(7); // Future due date
        
        store.rentCar(customer5, car1, leaseDate, dueDate);
        store.rentCar(customer5, car2, leaseDate, dueDate); // This will be overdue
        store.rentCar(customer5, car3, leaseDate, futureDueDate);
        
        // Test: Count rentals for customer C005
        Map<Customer, Integer> result = store.getRentedCarsPerCustomer();
        
        // Expected Output: Number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer5));
        
        // Additional verification for overdue rentals
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer5));
    }
}