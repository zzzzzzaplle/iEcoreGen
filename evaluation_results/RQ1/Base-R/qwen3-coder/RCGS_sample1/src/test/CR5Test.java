import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.Map;

public class CR5Test {
    
    private RentalStore store;
    
    @Before
    public void setUp() {
        store = new RentalStore();
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create customer C001
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        store.addCustomer(customer);
        
        // Add cars with plates "ABC123", "XYZ456", "LMN789"
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setDailyPrice(60.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setDailyPrice(70.0);
        store.addCar(car3);
        
        // Add 3 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomerName("John");
        rental1.setCustomerSurname("Doe");
        rental1.setCarPlate("ABC123");
        rental1.setLeasingTermDays(5);
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomerName("John");
        rental2.setCustomerSurname("Doe");
        rental2.setCarPlate("XYZ456");
        rental2.setLeasingTermDays(3);
        store.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomerName("John");
        rental3.setCustomerSurname("Doe");
        rental3.setCarPlate("LMN789");
        rental3.setLeasingTermDays(7);
        store.addRental(rental3);
        
        // Get rentals per customer
        Map<String, Integer> result = store.getRentalsPerCustomer();
        
        // Verify number of rentals for customer C001 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get("John Doe"));
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create customer C001 and C002
        Customer customer1 = new Customer();
        customer1.setName("Alice");
        customer1.setSurname("Smith");
        store.addCustomer(customer1);
        
        Customer customer2 = new Customer();
        customer2.setName("Bob");
        customer2.setSurname("Johnson");
        store.addCustomer(customer2);
        
        // Add cars with plates "ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"
        String[] plates = {"ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setDailyPrice(50.0);
            store.addCar(car);
        }
        
        // Add rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomerName("Alice");
        rental1.setCustomerSurname("Smith");
        rental1.setCarPlate("ABC123");
        rental1.setLeasingTermDays(2);
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomerName("Alice");
        rental2.setCustomerSurname("Smith");
        rental2.setCarPlate("XYZ456");
        rental2.setLeasingTermDays(3);
        store.addRental(rental2);
        
        // Add rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomerName("Bob");
        rental3.setCustomerSurname("Johnson");
        rental3.setCarPlate("LMN789");
        rental3.setLeasingTermDays(4);
        store.addRental(rental3);
        
        Rental rental4 = new Rental();
        rental4.setCustomerName("Bob");
        rental4.setCustomerSurname("Johnson");
        rental4.setCarPlate("OPQ012");
        rental4.setLeasingTermDays(1);
        store.addRental(rental4);
        
        Rental rental5 = new Rental();
        rental5.setCustomerName("Bob");
        rental5.setCustomerSurname("Johnson");
        rental5.setCarPlate("RST345");
        rental5.setLeasingTermDays(5);
        store.addRental(rental5);
        
        Rental rental6 = new Rental();
        rental6.setCustomerName("Bob");
        rental6.setCustomerSurname("Johnson");
        rental6.setCarPlate("UVW678");
        rental6.setLeasingTermDays(2);
        store.addRental(rental6);
        
        Rental rental7 = new Rental();
        rental7.setCustomerName("Bob");
        rental7.setCustomerSurname("Johnson");
        rental7.setCarPlate("JKL901");
        rental7.setLeasingTermDays(3);
        store.addRental(rental7);
        
        // Get rentals per customer
        Map<String, Integer> result = store.getRentalsPerCustomer();
        
        // Verify number of rentals for customer C001 = 2 and C002 = 5
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get("Alice Smith"));
        assertEquals(Integer.valueOf(5), result.get("Bob Johnson"));
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer C003
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Brown");
        store.addCustomer(customer);
        
        // No rental records are added for customer C003
        
        // Get rentals per customer
        Map<String, Integer> result = store.getRentalsPerCustomer();
        
        // Verify empty map (no rentals)
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create customer C004
        Customer customer = new Customer();
        customer.setName("David");
        customer.setSurname("Wilson");
        store.addCustomer(customer);
        
        // Add cars with plates "DEF234", "GHI567", "JKL890", "MNO123"
        String[] plates = {"DEF234", "GHI567", "JKL890", "MNO123"};
        for (String plate : plates) {
            Car car = new Car();
            car.setPlate(plate);
            car.setDailyPrice(50.0);
            store.addCar(car);
        }
        
        // Add rental records for customer C004 (4 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomerName("David");
        rental1.setCustomerSurname("Wilson");
        rental1.setCarPlate("DEF234");
        rental1.setLeasingTermDays(2);
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomerName("David");
        rental2.setCustomerSurname("Wilson");
        rental2.setCarPlate("GHI567");
        rental2.setLeasingTermDays(3);
        store.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomerName("David");
        rental3.setCustomerSurname("Wilson");
        rental3.setCarPlate("JKL890");
        rental3.setLeasingTermDays(4);
        store.addRental(rental3);
        
        Rental rental4 = new Rental();
        rental4.setCustomerName("David");
        rental4.setCustomerSurname("Wilson");
        rental4.setCarPlate("MNO123");
        rental4.setLeasingTermDays(5);
        store.addRental(rental4);
        
        // Mark 2 of them as returned
        store.returnCar("DEF234", LocalDate.now());
        store.returnCar("GHI567", LocalDate.now());
        
        // Get rentals per customer
        Map<String, Integer> result = store.getRentalsPerCustomer();
        
        // Verify number of rentals for customer C004 = 4 (all stored rentals)
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get("David Wilson"));
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create customer C005
        Customer customer = new Customer();
        customer.setName("Eva");
        customer.setSurname("Davis");
        store.addCustomer(customer);
        
        // Add cars with plates "PQR456", "STU789", "VWX012"
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setDailyPrice(50.0);
        store.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setDailyPrice(60.0);
        store.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setDailyPrice(70.0);
        store.addCar(car3);
        
        // Add rental records for customer C005 (3 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomerName("Eva");
        rental1.setCustomerSurname("Davis");
        rental1.setCarPlate("PQR456");
        rental1.setLeasingTermDays(5);
        rental1.setDueDate(LocalDate.now().plusDays(5)); // Not overdue
        store.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomerName("Eva");
        rental2.setCustomerSurname("Davis");
        rental2.setCarPlate("STU789");
        rental2.setLeasingTermDays(3);
        rental2.setDueDate(LocalDate.now().minusDays(1)); // Overdue
        store.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomerName("Eva");
        rental3.setCustomerSurname("Davis");
        rental3.setCarPlate("VWX012");
        rental3.setLeasingTermDays(7);
        rental3.setDueDate(LocalDate.now().plusDays(7)); // Not overdue
        store.addRental(rental3);
        
        // Get rentals per customer
        Map<String, Integer> result = store.getRentalsPerCustomer();
        
        // Verify number of rentals for customer C005 = 3
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get("Eva Davis"));
        
        // Verify number of overdue rentals for customer C005 = 1
        assertEquals(1, store.getOverdueCustomers().size());
    }
}