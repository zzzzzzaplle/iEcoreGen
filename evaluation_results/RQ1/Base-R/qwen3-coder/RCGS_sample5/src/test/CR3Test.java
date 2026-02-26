import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CR3Test {
    private RentalStore rentalStore;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        rentalStore = new RentalStore();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    public void testCase1_SingleOverdueRentalCheck() {
        // Create customer C001: John Doe
        Customer customer = new Customer();
        customer.setName("John");
        customer.setSurname("Doe");
        rentalStore.addCustomer(customer);

        // Create car for rental
        Car car = new Car();
        car.setPlate("ABC123");
        car.setModel("Toyota Camry");
        car.setDailyPrice(50.0);
        rentalStore.addCar(car);

        // Create rental R001 with backDate: null, dueDate: 2023-10-01
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.parse("2023-09-20", formatter));
        rental.setDueDate(LocalDate.parse("2023-10-01", formatter));
        rental.setBackDate(null);
        rentalStore.addRental(rental);

        // Set current date to 2023-10-05 (overdue)
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);

        // Check for overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers(currentDate);

        // Verify customer C001 is in overdue list
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }

    @Test
    public void testCase2_NoOverdueRentals() {
        // Create customer C002: Jane Smith
        Customer customer = new Customer();
        customer.setName("Jane");
        customer.setSurname("Smith");
        rentalStore.addCustomer(customer);

        // Create car for rental
        Car car = new Car();
        car.setPlate("DEF456");
        car.setModel("Honda Civic");
        car.setDailyPrice(40.0);
        rentalStore.addCar(car);

        // Create rental R002 with backDate: null, dueDate: 2025-10-10
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.parse("2023-10-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-10-10", formatter));
        rental.setBackDate(null);
        rentalStore.addRental(rental);

        // Set current date to 2023-10-12
        LocalDate currentDate = LocalDate.parse("2023-10-12", formatter);

        // Check for overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers(currentDate);

        // Verify no overdue customers found
        assertTrue(overdueCustomers.isEmpty());
    }

    @Test
    public void testCase3_MultipleRentalsWithMixedStatus() {
        // Create customer C003: Alice Johnson
        Customer customer = new Customer();
        customer.setName("Alice");
        customer.setSurname("Johnson");
        rentalStore.addCustomer(customer);

        // Create cars for rentals
        Car car1 = new Car();
        car1.setPlate("GHI789");
        car1.setModel("Ford Focus");
        car1.setDailyPrice(35.0);
        rentalStore.addCar(car1);

        Car car2 = new Car();
        car2.setPlate("JKL012");
        car2.setModel("Nissan Altima");
        car2.setDailyPrice(45.0);
        rentalStore.addCar(car2);

        // Create rental R003 with backDate: null, dueDate: 2023-10-03 (overdue)
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setCar(car1);
        rental1.setRentDate(LocalDate.parse("2023-09-28", formatter));
        rental1.setDueDate(LocalDate.parse("2023-10-03", formatter));
        rental1.setBackDate(null);
        rentalStore.addRental(rental1);

        // Create rental R004 with backDate: 2024-10-01, dueDate: 2024-10-02 (not overdue)
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setCar(car2);
        rental2.setRentDate(LocalDate.parse("2024-09-25", formatter));
        rental2.setDueDate(LocalDate.parse("2024-10-02", formatter));
        rental2.setBackDate(LocalDate.parse("2024-10-01", formatter));
        rentalStore.addRental(rental2);

        // Set current date to 2023-10-05
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);

        // Check for overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers(currentDate);

        // Verify customer C003 is in overdue list due to rental R003
        assertEquals(1, overdueCustomers.size());
        assertTrue(overdueCustomers.contains(customer));
    }

    @Test
    public void testCase4_RentalWithBackDate() {
        // Create customer C004: Bob Brown
        Customer customer = new Customer();
        customer.setName("Bob");
        customer.setSurname("Brown");
        rentalStore.addCustomer(customer);

        // Create car for rental
        Car car = new Car();
        car.setPlate("MNO345");
        car.setModel("Chevrolet Malibu");
        car.setDailyPrice(42.0);
        rentalStore.addCar(car);

        // Create rental R005 with backDate: 2023-10-04, dueDate: 2023-10-03
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.parse("2023-09-29", formatter));
        rental.setDueDate(LocalDate.parse("2023-10-03", formatter));
        rental.setBackDate(LocalDate.parse("2023-10-04", formatter));
        rentalStore.addRental(rental);

        // Check for overdue customers (no current date needed as backDate is set)
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers(LocalDate.now());

        // Verify no overdue customers found since rental has back date
        assertTrue(overdueCustomers.isEmpty());
    }

    @Test
    public void testCase5_FutureDueDateRentals() {
        // Create customer C005: Charlie Green
        Customer customer = new Customer();
        customer.setName("Charlie");
        customer.setSurname("Green");
        rentalStore.addCustomer(customer);

        // Create car for rental
        Car car = new Car();
        car.setPlate("PQR678");
        car.setModel("Hyundai Elantra");
        car.setDailyPrice(38.0);
        rentalStore.addCar(car);

        // Create rental R006 with dueDate: 2025-10-15
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setCar(car);
        rental.setRentDate(LocalDate.parse("2023-10-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-10-15", formatter));
        rental.setBackDate(null);
        rentalStore.addRental(rental);

        // Set current date to 2023-10-05
        LocalDate currentDate = LocalDate.parse("2023-10-05", formatter);

        // Check for overdue customers
        List<Customer> overdueCustomers = rentalStore.getOverdueCustomers(currentDate);

        // Verify no overdue customers found as due date is in the future
        assertTrue(overdueCustomers.isEmpty());
    }
}