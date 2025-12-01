import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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
        // Create customer C001
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        // Create cars with plates "ABC123", "XYZ456", "LMN789"
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
        
        // Add cars to store gallery
        store.setCarGallery(Arrays.asList(car1, car2, car3));
        
        // Create 3 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.now().minusDays(5));
        rental1.setDueDate(LocalDate.now().plusDays(5));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.now().minusDays(3));
        rental2.setDueDate(LocalDate.now().plusDays(7));
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer1);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.now().minusDays(1));
        rental3.setDueDate(LocalDate.now().plusDays(9));
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3));
        
        // Get rental counts per customer
        Map<Customer, Integer> rentalCounts = store.getCarRentalCountsPerCustomer();
        
        // Verify customer C001 has 3 rentals
        assertEquals(3, rentalCounts.get(customer1).intValue());
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create customers C001 and C002
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        
        customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        
        // Create cars for both customers
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
        
        // Add cars to store gallery
        store.setCarGallery(Arrays.asList(car1, car2, car3, car4, car5, car6, car7));
        
        // Create rental records: C001 (2 rentals), C002 (5 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.now().minusDays(5));
        rental1.setDueDate(LocalDate.now().plusDays(5));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.now().minusDays(3));
        rental2.setDueDate(LocalDate.now().plusDays(7));
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.now().minusDays(10));
        rental3.setDueDate(LocalDate.now().plusDays(5));
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentDate(LocalDate.now().minusDays(8));
        rental4.setDueDate(LocalDate.now().plusDays(7));
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentDate(LocalDate.now().minusDays(6));
        rental5.setDueDate(LocalDate.now().plusDays(9));
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentDate(LocalDate.now().minusDays(4));
        rental6.setDueDate(LocalDate.now().plusDays(11));
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentDate(LocalDate.now().minusDays(2));
        rental7.setDueDate(LocalDate.now().plusDays(13));
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3, rental4, rental5, rental6, rental7));
        
        // Get rental counts per customer
        Map<Customer, Integer> rentalCounts = store.getCarRentalCountsPerCustomer();
        
        // Verify customer C001 has 2 rentals and C002 has 5 rentals
        assertEquals(2, rentalCounts.get(customer1).intValue());
        assertEquals(5, rentalCounts.get(customer2).intValue());
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer C003
        customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        
        // Add customer to store (but no rentals)
        store.setCustomers(Arrays.asList(customer3));
        
        // Get rental counts per customer
        Map<Customer, Integer> rentalCounts = store.getCarRentalCountsPerCustomer();
        
        // Verify the map is empty (no rentals exist)
        assertTrue(rentalCounts.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create customer C004
        customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        
        // Create cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota Corolla");
        car1.setDailyPrice(35.0);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda Accord");
        car2.setDailyPrice(55.0);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford Fusion");
        car3.setDailyPrice(45.0);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Nissan Sentra");
        car4.setDailyPrice(38.0);
        
        // Add cars to store gallery
        store.setCarGallery(Arrays.asList(car1, car2, car3, car4));
        
        // Create 4 rental records for customer C004
        Rental rental1 = new Rental();
        rental1.setCustomer(customer4);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.now().minusDays(10));
        rental1.setDueDate(LocalDate.now().minusDays(5));
        rental1.setBackDate(LocalDate.now().minusDays(5)); // Marked as returned
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer4);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.now().minusDays(8));
        rental2.setDueDate(LocalDate.now().minusDays(3));
        rental2.setBackDate(LocalDate.now().minusDays(3)); // Marked as returned
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer4);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.now().minusDays(5));
        rental3.setDueDate(LocalDate.now().plusDays(5));
        // Not returned (backDate = null)
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer4);
        rental4.setCar(car4);
        rental4.setRentDate(LocalDate.now().minusDays(2));
        rental4.setDueDate(LocalDate.now().plusDays(8));
        // Not returned (backDate = null)
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3, rental4));
        
        // Get rental counts per customer
        Map<Customer, Integer> rentalCounts = store.getCarRentalCountsPerCustomer();
        
        // Verify customer C004 has 4 rentals total (including returned ones)
        assertEquals(4, rentalCounts.get(customer4).intValue());
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create customer C005
        customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Wilson");
        
        // Create cars with plates "PQR456", "STU789", "VWX012"
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota Avalon");
        car1.setDailyPrice(60.0);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda CR-V");
        car2.setDailyPrice(65.0);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford Escape");
        car3.setDailyPrice(58.0);
        
        // Add cars to store gallery
        store.setCarGallery(Arrays.asList(car1, car2, car3));
        
        // Create 3 rental records for customer C005 with one overdue
        Rental rental1 = new Rental();
        rental1.setCustomer(customer5);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.now().minusDays(10));
        rental1.setDueDate(LocalDate.now().plusDays(5)); // Not overdue
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer5);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.now().minusDays(15));
        rental2.setDueDate(LocalDate.now().minusDays(2)); // Overdue
        // backDate = null (not returned)
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer5);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.now().minusDays(7));
        rental3.setDueDate(LocalDate.now().plusDays(3)); // Not overdue
        
        // Add rentals to store
        store.setRentals(Arrays.asList(rental1, rental2, rental3));
        
        // Get rental counts per customer
        Map<Customer, Integer> rentalCounts = store.getCarRentalCountsPerCustomer();
        
        // Verify customer C005 has 3 rentals total
        assertEquals(3, rentalCounts.get(customer5).intValue());
        
        // Additional verification for overdue rentals
        List<Customer> overdueCustomers = store.getOverdueCustomers();
        assertTrue(overdueCustomers.contains(customer5));
    }
}