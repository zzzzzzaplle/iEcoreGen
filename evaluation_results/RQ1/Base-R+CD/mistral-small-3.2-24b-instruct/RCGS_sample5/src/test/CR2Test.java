import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class CR2Test {
    private Store store;
    private SimpleDateFormat dateFormat;

    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void testCase1_SingleRentalCalculation() throws Exception {
        // Create rentals
        Rental rental1 = createRental("ABC123", "Toyota Camry", 100.0, 
                                    dateFormat.parse("2025-11-10 00:00:00"),
                                    dateFormat.parse("2025-11-13 00:00:00"),
                                    dateFormat.parse("2025-11-12 00:00:00"),
                                    300.0); // 100 * 3 days
        
        Rental rental2 = createRental("XYZ789", "Honda Civic", 150.0,
                                    dateFormat.parse("2025-11-10 00:00:00"),
                                    dateFormat.parse("2025-11-13 00:00:00"),
                                    dateFormat.parse("2025-11-11 00:00:00"),
                                    300.0); // 150 * 2 days
        
        Rental rental3 = createRental("LMN456", "Ford Focus", 200.0,
                                    dateFormat.parse("2025-11-12 00:00:00"),
                                    dateFormat.parse("2025-11-13 00:00:00"),
                                    dateFormat.parse("2025-11-12 00:00:00"),
                                    200.0); // 200 * 1 day

        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);

        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Verify expected output: 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, result, 0.001);
    }

    @Test
    public void testCase2_NoRentalsCalculation() {
        // Verify there are no rental records
        assertTrue(store.getRentals().isEmpty());
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Verify expected output: 0 CNY
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws Exception {
        // Create rentals with same daily price
        Rental rental1 = createRental("CAR001", "Chevrolet Malibu", 120.0,
                                    dateFormat.parse("2025-11-12 00:00:00"),
                                    dateFormat.parse("2025-11-13 00:00:00"),
                                    dateFormat.parse("2025-11-13 00:00:00"),
                                    240.0); // 120 * 2 days
        
        Rental rental2 = createRental("CAR002", "Hyundai Elantra", 120.0,
                                    dateFormat.parse("2025-11-12 00:00:00"),
                                    dateFormat.parse("2025-12-01 00:00:00"),
                                    dateFormat.parse("2025-11-15 00:00:00"),
                                    480.0); // 120 * 4 days

        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);

        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Verify expected output: 240 + 480 = 720 CNY
        assertEquals(720.0, result, 0.001);
    }

    @Test
    public void testCase4_MixedPricesCalculation() throws Exception {
        // Create rentals with varied daily prices
        Rental rental1 = createRental("SED123", "Mazda 3", 90.0,
                                    dateFormat.parse("2025-08-09 00:00:00"),
                                    dateFormat.parse("2025-09-01 00:00:00"),
                                    dateFormat.parse("2025-08-13 00:00:00"),
                                    450.0); // 90 * 5 days
        
        Rental rental2 = createRental("SUV456", "Kia Sportage", 150.0,
                                    dateFormat.parse("2025-08-11 00:00:00"),
                                    dateFormat.parse("2026-01-01 00:00:00"),
                                    dateFormat.parse("2025-08-13 00:00:00"),
                                    450.0); // 150 * 3 days
        
        Rental rental3 = createRental("TRK789", "Ford F-150", 250.0,
                                    dateFormat.parse("2025-08-09 00:00:00"),
                                    dateFormat.parse("2025-09-01 00:00:00"),
                                    dateFormat.parse("2025-08-09 00:00:00"),
                                    250.0); // 250 * 1 day

        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        rentals.add(rental3);
        store.setRentals(rentals);

        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Verify expected output: 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, result, 0.001);
    }

    @Test
    public void testCase5_OneDayRentalCalculation() throws Exception {
        // Create one-day rentals
        Rental rental1 = createRental("MINI001", "Mini Cooper", 180.0,
                                    dateFormat.parse("2025-08-12 00:00:00"),
                                    dateFormat.parse("2025-09-01 00:00:00"),
                                    dateFormat.parse("2025-08-12 00:00:00"),
                                    180.0); // 180 * 1 day
        
        Rental rental2 = createRental("MOTO002", "Harley Davidson", 220.0,
                                    dateFormat.parse("2025-08-09 00:00:00"),
                                    dateFormat.parse("2025-09-01 00:00:00"),
                                    dateFormat.parse("2025-08-09 00:00:00"),
                                    220.0); // 220 * 1 day

        // Add rentals to store
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        store.setRentals(rentals);

        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Verify expected output: 180 + 220 = 400 CNY
        assertEquals(400.0, result, 0.001);
    }

    private Rental createRental(String plate, String model, double dailyPrice,
                               Date rentalDate, Date dueDate, Date backDate,
                               double totalPrice) {
        Car car = new Car();
        car.setPlate(plate);
        car.setModel(model);
        car.setDailyPrice(dailyPrice);

        Customer customer = new Customer();
        customer.setRentedCarPlate(plate);

        Rental rental = new Rental();
        rental.setCar(car);
        rental.setCustomer(customer);
        rental.setRentalDate(rentalDate);
        rental.setDueDate(dueDate);
        rental.setBackDate(backDate);
        rental.setTotalPrice(totalPrice);

        return rental;
    }
}