import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;
import java.util.Date;
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
    public void testCase1_countRentalsForSingleCustomer() throws Exception {
        // Create customer C001
        Customer customer = new Customer();
        
        // Add 3 rental records for customer C001 with different car details
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("ABC123");
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        store.getRentals().add(rental2);
        
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("LMN789");
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        store.getRentals().add(rental3);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the expected output
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
    
    @Test
    public void testCase2_countRentalsForMultipleCustomers() throws Exception {
        // Create customers C001 and C002
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        
        // Add 2 rental records for customer C001
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("ABC123");
        rental1.setCar(car1);
        rental1.setCustomer(customer1);
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        rental2.setCar(car2);
        rental2.setCustomer(customer1);
        store.getRentals().add(rental2);
        
        // Add 5 rental records for customer C002
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("LMN789");
        rental3.setCar(car3);
        rental3.setCustomer(customer2);
        store.getRentals().add(rental3);
        
        Rental rental4 = new Rental();
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        rental4.setCar(car4);
        rental4.setCustomer(customer2);
        store.getRentals().add(rental4);
        
        Rental rental5 = new Rental();
        Car car5 = new Car();
        car5.setPlate("RST345");
        rental5.setCar(car5);
        rental5.setCustomer(customer2);
        store.getRentals().add(rental5);
        
        Rental rental6 = new Rental();
        Car car6 = new Car();
        car6.setPlate("UVW678");
        rental6.setCar(car6);
        rental6.setCustomer(customer2);
        store.getRentals().add(rental6);
        
        Rental rental7 = new Rental();
        Car car7 = new Car();
        car7.setPlate("JKL901");
        rental7.setCar(car7);
        rental7.setCustomer(customer2);
        store.getRentals().add(rental7);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the expected output
        assertEquals(2, result.size());
        assertEquals(Integer.valueOf(2), result.get(customer1));
        assertEquals(Integer.valueOf(5), result.get(customer2));
    }
    
    @Test
    public void testCase3_countRentalsWithNoRecords() throws Exception {
        // Create customer C003
        Customer customer = new Customer();
        
        // No rental records are added for customer C003
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the expected output - empty map
        assertNotNull(result);
        assertEquals(0, result.size());
    }
    
    @Test
    public void testCase4_countRentalsIncludingReturnedCars() throws Exception {
        // Create customer C004
        Customer customer = new Customer();
        
        // Add 4 rental records for customer C004
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("DEF234");
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("GHI567");
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        Date backDate = dateFormat.parse("2023-12-20 10:00:00");
        rental2.setBackDate(backDate);
        store.getRentals().add(rental2);
        
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("JKL890");
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        store.getRentals().add(rental3);
        
        Rental rental4 = new Rental();
        Car car4 = new Car();
        car4.setPlate("MNO123");
        rental4.setCar(car4);
        rental4.setCustomer(customer);
        Date backDate2 = dateFormat.parse("2023-12-25 15:00:00");
        rental4.setBackDate(backDate2);
        store.getRentals().add(rental4);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the expected output - all 4 rentals should be counted regardless of return status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(4), result.get(customer));
    }
    
    @Test
    public void testCase5_countRentalsForCustomerWithOverdueCars() throws Exception {
        // Create customer C005
        Customer customer = new Customer();
        
        // Add 3 rental records for customer C005
        Rental rental1 = new Rental();
        Car car1 = new Car();
        car1.setPlate("PQR456");
        rental1.setCar(car1);
        rental1.setCustomer(customer);
        store.getRentals().add(rental1);
        
        Rental rental2 = new Rental();
        Car car2 = new Car();
        car2.setPlate("STU789");
        rental2.setCar(car2);
        rental2.setCustomer(customer);
        Date dueDate = dateFormat.parse("2023-12-15 10:00:00");
        rental2.setDueDate(dueDate);
        store.getRentals().add(rental2);
        
        Rental rental3 = new Rental();
        Car car3 = new Car();
        car3.setPlate("VWX012");
        rental3.setCar(car3);
        rental3.setCustomer(customer);
        store.getRentals().add(rental3);
        
        // Call the method under test
        Map<Customer, Integer> result = store.countCarsRentedPerCustomer();
        
        // Verify the expected output - all 3 rentals should be counted regardless of overdue status
        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.get(customer));
    }
}