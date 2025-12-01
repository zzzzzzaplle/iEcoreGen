import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class CR5Test {
    private CarRentalSystem system;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;

    @Before
    public void setUp() {
        system = new CarRentalSystem();
        
        // Create test customers
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        
        customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        customer3.setAddress("789 Pine Rd");
        
        customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        customer4.setAddress("321 Elm St");
        
        customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Wilson");
        customer5.setAddress("654 Maple Ave");
    }

    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // SetUp: Create a store instance and add customer with 3 rentals
        system.addCustomer(customer1);
        
        // Create and add cars
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
        car3.setDailyPrice(45.0);
        system.addCar(car3);
        
        // Create and add 3 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.now().minusDays(10));
        rental1.setDueDate(LocalDate.now().plusDays(5));
        rental1.setTotalCost(500.0);
        system.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.now().minusDays(5));
        rental2.setDueDate(LocalDate.now().plusDays(10));
        rental2.setTotalCost(400.0);
        system.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer1);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.now().minusDays(2));
        rental3.setDueDate(LocalDate.now().plusDays(8));
        rental3.setTotalCost(450.0);
        system.addRental(rental3);
        
        // Execute the method under test
        Map<Customer, Integer> result = system.countCarsRentedPerCustomer();
        
        // Verify the expected output
        assertEquals("Map should contain exactly one customer", 1, result.size());
        assertEquals("Customer C001 should have 3 rentals", Integer.valueOf(3), result.get(customer1));
    }

    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // SetUp: Create store instance and add customers C001 and C002
        system.addCustomer(customer1);
        system.addCustomer(customer2);
        
        // Create and add cars for customer C001
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
        
        // Create and add cars for customer C002
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(45.0);
        system.addCar(car3);
        
        Car car4 = new Car();
        car4.setPlate("OPQ012");
        car4.setModel("Chevrolet Malibu");
        car4.setDailyPrice(55.0);
        system.addCar(car4);
        
        Car car5 = new Car();
        car5.setPlate("RST345");
        car5.setModel("Nissan Altima");
        car5.setDailyPrice(48.0);
        system.addCar(car5);
        
        Car car6 = new Car();
        car6.setPlate("UVW678");
        car6.setModel("Hyundai Elantra");
        car6.setDailyPrice(42.0);
        system.addCar(car6);
        
        Car car7 = new Car();
        car7.setPlate("JKL901");
        car7.setModel("Kia Optima");
        car7.setDailyPrice(47.0);
        system.addCar(car7);
        
        // Add 2 rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.now().minusDays(10));
        rental1.setDueDate(LocalDate.now().plusDays(5));
        rental1.setTotalCost(500.0);
        system.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.now().minusDays(5));
        rental2.setDueDate(LocalDate.now().plusDays(10));
        rental2.setTotalCost(400.0);
        system.addRental(rental2);
        
        // Add 5 rental records for customer C002
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.now().minusDays(15));
        rental3.setDueDate(LocalDate.now().minusDays(5));
        rental3.setTotalCost(675.0);
        system.addRental(rental3);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(car4);
        rental4.setRentDate(LocalDate.now().minusDays(12));
        rental4.setDueDate(LocalDate.now().minusDays(2));
        rental4.setTotalCost(660.0);
        system.addRental(rental4);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(car5);
        rental5.setRentDate(LocalDate.now().minusDays(8));
        rental5.setDueDate(LocalDate.now().plusDays(2));
        rental5.setTotalCost(480.0);
        system.addRental(rental5);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(car6);
        rental6.setRentDate(LocalDate.now().minusDays(6));
        rental6.setDueDate(LocalDate.now().plusDays(4));
        rental6.setTotalCost(420.0);
        system.addRental(rental6);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(car7);
        rental7.setRentDate(LocalDate.now().minusDays(3));
        rental7.setDueDate(LocalDate.now().plusDays(7));
        rental7.setTotalCost(470.0);
        system.addRental(rental7);
        
        // Execute the method under test
        Map<Customer, Integer> result = system.countCarsRentedPerCustomer();
        
        // Verify the expected output
        assertEquals("Map should contain exactly 2 customers", 2, result.size());
        assertEquals("Customer C001 should have 2 rentals", Integer.valueOf(2), result.get(customer1));
        assertEquals("Customer C002 should have 5 rentals", Integer.valueOf(5), result.get(customer2));
    }

    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // SetUp: Create store instance and add customer C003 with no rental records
        system.addCustomer(customer3);
        
        // Execute the method under test
        Map<Customer, Integer> result = system.countCarsRentedPerCustomer();
        
        // Verify the expected output - empty map
        assertNotNull("Result should not be null", result);
        assertTrue("Result should be empty when no rentals exist", result.isEmpty());
    }

    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // SetUp: Create store instance and add customer C004 with 4 rentals
        system.addCustomer(customer4);
        
        // Create and add cars
        Car car1 = new Car();
        car1.setPlate("DEF234");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("GHI567");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        system.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("JKL890");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(45.0);
        system.addCar(car3);
        
        Car car4 = new Car();
        car4.setPlate("MNO123");
        car4.setModel("Chevrolet Malibu");
        car4.setDailyPrice(55.0);
        system.addCar(car4);
        
        // Create and add 4 rental records for customer C004
        Rental rental1 = new Rental();
        rental1.setCustomer(customer4);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.now().minusDays(20));
        rental1.setDueDate(LocalDate.now().minusDays(10));
        rental1.setTotalCost(500.0);
        system.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer4);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.now().minusDays(15));
        rental2.setDueDate(LocalDate.now().minusDays(5));
        rental2.setTotalCost(400.0);
        system.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer4);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.now().minusDays(5));
        rental3.setDueDate(LocalDate.now().plusDays(5));
        rental3.setTotalCost(450.0);
        system.addRental(rental3);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer4);
        rental4.setCar(car4);
        rental4.setRentDate(LocalDate.now().minusDays(3));
        rental4.setDueDate(LocalDate.now().plusDays(7));
        rental4.setTotalCost(550.0);
        system.addRental(rental4);
        
        // Mark 2 rentals as returned
        system.returnCar(rental1, LocalDate.now().minusDays(10));
        system.returnCar(rental2, LocalDate.now().minusDays(5));
        
        // Execute the method under test
        Map<Customer, Integer> result = system.countCarsRentedPerCustomer();
        
        // Verify the expected output - should count all 4 rentals regardless of return status
        assertEquals("Map should contain exactly one customer", 1, result.size());
        assertEquals("Customer C004 should have 4 rentals total", Integer.valueOf(4), result.get(customer4));
        
        // Verify current rental status
        assertEquals("Should have 2 currently active rentals", 2, system.getRentedCars().size());
    }

    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // SetUp: Create store instance and add customer C005 with 3 rentals, one overdue
        system.addCustomer(customer5);
        
        // Create and add cars
        Car car1 = new Car();
        car1.setPlate("PQR456");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        system.addCar(car1);
        
        Car car2 = new Car();
        car2.setPlate("STU789");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        system.addCar(car2);
        
        Car car3 = new Car();
        car3.setPlate("VWX012");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(45.0);
        system.addCar(car3);
        
        // Create and add 3 rental records for customer C005
        Rental rental1 = new Rental();
        rental1.setCustomer(customer5);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.now().minusDays(10));
        rental1.setDueDate(LocalDate.now().plusDays(5));
        rental1.setTotalCost(500.0);
        system.addRental(rental1);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer5);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.now().minusDays(15));
        rental2.setDueDate(LocalDate.now().minusDays(5)); // Overdue
        rental2.setTotalCost(600.0);
        system.addRental(rental2);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer5);
        rental3.setCar(car3);
        rental3.setRentDate(LocalDate.now().minusDays(3));
        rental3.setDueDate(LocalDate.now().plusDays(7));
        rental3.setTotalCost(450.0);
        system.addRental(rental3);
        
        // Execute the method under test
        Map<Customer, Integer> result = system.countCarsRentedPerCustomer();
        
        // Verify the expected output
        assertEquals("Map should contain exactly one customer", 1, result.size());
        assertEquals("Customer C005 should have 3 rentals total", Integer.valueOf(3), result.get(customer5));
        
        // Verify overdue status
        assertEquals("Should have 1 overdue rental", 1, system.getOverdueCustomers().size());
        assertTrue("Customer C005 should be in overdue customers list", system.getOverdueCustomers().contains(customer5));
    }
}