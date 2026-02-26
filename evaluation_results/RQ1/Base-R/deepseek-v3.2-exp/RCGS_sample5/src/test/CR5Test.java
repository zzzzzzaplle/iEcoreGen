import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
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
    }
    
    @Test
    public void testCase1_CountRentalsForSingleCustomer() {
        // Create customer with ID C001
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        // Create cars with plates "ABC123", "XYZ456", "LMN789"
        Car car1 = new Car();
        car1.setPlate("ABC123");
        car1.setModel("Toyota Camry");
        car1.setDailyPrice(50.0);
        
        Car car2 = new Car();
        car2.setPlate("XYZ456");
        car2.setModel("Honda Civic");
        car2.setDailyPrice(40.0);
        
        Car car3 = new Car();
        car3.setPlate("LMN789");
        car3.setModel("Ford Focus");
        car3.setDailyPrice(45.0);
        
        // Add cars to system
        system.getCars().add(car1);
        system.getCars().add(car2);
        system.getCars().add(car3);
        
        // Create rental records for customer C001
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(car1);
        rental1.setTotalPrice(150.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(car2);
        rental2.setTotalPrice(120.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer1);
        rental3.setCar(car3);
        rental3.setTotalPrice(135.0);
        
        // Add rentals to system
        system.getRentals().add(rental1);
        system.getRentals().add(rental2);
        system.getRentals().add(rental3);
        
        // Get rental count per customer
        Map<Customer, Integer> result = system.getRentalCountPerCustomer();
        
        // Verify number of rentals for customer C001 = 3
        assertEquals(3, result.get(customer1).intValue());
        assertEquals(1, result.size()); // Only one customer in the map
    }
    
    @Test
    public void testCase2_CountRentalsForMultipleCustomers() {
        // Create customer C001 and C002
        customer1 = new Customer();
        customer1.setName("John");
        customer1.setSurname("Doe");
        customer1.setAddress("123 Main St");
        
        customer2 = new Customer();
        customer2.setName("Jane");
        customer2.setSurname("Smith");
        customer2.setAddress("456 Oak Ave");
        
        // Create cars for both customers
        Car[] cars = new Car[7];
        String[] plates = {"ABC123", "XYZ456", "LMN789", "OPQ012", "RST345", "UVW678", "JKL901"};
        
        for (int i = 0; i < 7; i++) {
            cars[i] = new Car();
            cars[i].setPlate(plates[i]);
            cars[i].setModel("Model " + (i+1));
            cars[i].setDailyPrice(30.0 + i*5);
            system.getCars().add(cars[i]);
        }
        
        // Add rental records for customer C001 (2 rentals)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer1);
        rental1.setCar(cars[0]); // ABC123
        rental1.setTotalPrice(100.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer1);
        rental2.setCar(cars[1]); // XYZ456
        rental2.setTotalPrice(120.0);
        
        // Add rental records for customer C002 (5 rentals)
        Rental rental3 = new Rental();
        rental3.setCustomer(customer2);
        rental3.setCar(cars[2]); // LMN789
        rental3.setTotalPrice(150.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer2);
        rental4.setCar(cars[3]); // OPQ012
        rental4.setTotalPrice(180.0);
        
        Rental rental5 = new Rental();
        rental5.setCustomer(customer2);
        rental5.setCar(cars[4]); // RST345
        rental5.setTotalPrice(200.0);
        
        Rental rental6 = new Rental();
        rental6.setCustomer(customer2);
        rental6.setCar(cars[5]); // UVW678
        rental6.setTotalPrice(220.0);
        
        Rental rental7 = new Rental();
        rental7.setCustomer(customer2);
        rental7.setCar(cars[6]); // JKL901
        rental7.setTotalPrice(240.0);
        
        // Add all rentals to system
        system.getRentals().add(rental1);
        system.getRentals().add(rental2);
        system.getRentals().add(rental3);
        system.getRentals().add(rental4);
        system.getRentals().add(rental5);
        system.getRentals().add(rental6);
        system.getRentals().add(rental7);
        
        // Get rental count per customer
        Map<Customer, Integer> result = system.getRentalCountPerCustomer();
        
        // Verify number of rentals for customer C001 = 2 and C002 = 5
        assertEquals(2, result.get(customer1).intValue());
        assertEquals(5, result.get(customer2).intValue());
        assertEquals(2, result.size()); // Two customers in the map
    }
    
    @Test
    public void testCase3_CountRentalsWithNoRecords() {
        // Create customer with ID C003
        customer3 = new Customer();
        customer3.setName("Bob");
        customer3.setSurname("Johnson");
        customer3.setAddress("789 Pine St");
        
        // No rental records are added for customer C003
        
        // Get rental count per customer
        Map<Customer, Integer> result = system.getRentalCountPerCustomer();
        
        // Expected Output: Empty map (not null)
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    public void testCase4_CountRentalsIncludingReturnedCars() {
        // Create customer with ID C004
        customer4 = new Customer();
        customer4.setName("Alice");
        customer4.setSurname("Brown");
        customer4.setAddress("321 Elm St");
        
        // Create 4 cars
        Car[] cars = new Car[4];
        String[] plates = {"DEF234", "GHI567", "JKL890", "MNO123"};
        
        for (int i = 0; i < 4; i++) {
            cars[i] = new Car();
            cars[i].setPlate(plates[i]);
            cars[i].setModel("Model " + (i+1));
            cars[i].setDailyPrice(35.0 + i*5);
            system.getCars().add(cars[i]);
        }
        
        // Create 4 rental records for customer C004
        LocalDate today = LocalDate.now();
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer4);
        rental1.setCar(cars[0]); // DEF234
        rental1.setStartDate(today.minusDays(10));
        rental1.setDueDate(today.minusDays(3));
        rental1.setBackDate(today.minusDays(2)); // Returned
        rental1.setTotalPrice(280.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer4);
        rental2.setCar(cars[1]); // GHI567
        rental2.setStartDate(today.minusDays(5));
        rental2.setDueDate(today.plusDays(2));
        rental2.setBackDate(today.minusDays(1)); // Returned
        rental2.setTotalPrice(200.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer4);
        rental3.setCar(cars[2]); // JKL890
        rental3.setStartDate(today.minusDays(2));
        rental3.setDueDate(today.plusDays(5));
        rental3.setBackDate(null); // Not returned
        rental3.setTotalPrice(180.0);
        
        Rental rental4 = new Rental();
        rental4.setCustomer(customer4);
        rental4.setCar(cars[3]); // MNO123
        rental4.setStartDate(today.minusDays(1));
        rental4.setDueDate(today.plusDays(6));
        rental4.setBackDate(null); // Not returned
        rental4.setTotalPrice(210.0);
        
        // Add all rentals to system
        system.getRentals().add(rental1);
        system.getRentals().add(rental2);
        system.getRentals().add(rental3);
        system.getRentals().add(rental4);
        
        // Get rental count per customer
        Map<Customer, Integer> result = system.getRentalCountPerCustomer();
        
        // Verify number of rentals for customer C004 = 4 (all stored records)
        assertEquals(4, result.get(customer4).intValue());
        assertEquals(1, result.size()); // Only one customer in the map
    }
    
    @Test
    public void testCase5_CountRentalsForCustomerWithOverdueCars() {
        // Create customer with ID C005
        customer5 = new Customer();
        customer5.setName("Charlie");
        customer5.setSurname("Wilson");
        customer5.setAddress("654 Maple Dr");
        
        // Create 3 cars
        Car[] cars = new Car[3];
        String[] plates = {"PQR456", "STU789", "VWX012"};
        
        for (int i = 0; i < 3; i++) {
            cars[i] = new Car();
            cars[i].setPlate(plates[i]);
            cars[i].setModel("Model " + (i+1));
            cars[i].setDailyPrice(40.0 + i*5);
            system.getCars().add(cars[i]);
        }
        
        // Create 3 rental records for customer C005
        LocalDate today = LocalDate.now();
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer5);
        rental1.setCar(cars[0]); // PQR456
        rental1.setStartDate(today.minusDays(5));
        rental1.setDueDate(today.plusDays(2));
        rental1.setBackDate(null); // Not returned, but not overdue
        rental1.setTotalPrice(200.0);
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer5);
        rental2.setCar(cars[1]); // STU789
        rental2.setStartDate(today.minusDays(10));
        rental2.setDueDate(today.minusDays(2)); // Overdue
        rental2.setBackDate(null); // Not returned
        rental2.setTotalPrice(320.0);
        
        Rental rental3 = new Rental();
        rental3.setCustomer(customer5);
        rental3.setCar(cars[2]); // VWX012
        rental3.setStartDate(today.minusDays(3));
        rental3.setDueDate(today.plusDays(4));
        rental3.setBackDate(null); // Not returned, but not overdue
        rental3.setTotalPrice(210.0);
        
        // Add all rentals to system
        system.getRentals().add(rental1);
        system.getRentals().add(rental2);
        system.getRentals().add(rental3);
        
        // Get rental count per customer
        Map<Customer, Integer> result = system.getRentalCountPerCustomer();
        
        // Verify number of rentals for customer C005 = 3 (all stored records)
        assertEquals(3, result.get(customer5).intValue());
        assertEquals(1, result.size()); // Only one customer in the map
    }
}