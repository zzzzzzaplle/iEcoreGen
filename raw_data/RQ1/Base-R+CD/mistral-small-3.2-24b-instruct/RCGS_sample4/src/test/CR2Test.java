import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CR2Test {
    private Store store;
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        store = new Store();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SingleRentalCalculation() throws ParseException {
        // Set up rentals as specified
        List<Rental> rentals = new ArrayList<>();
        
        // First rental: Toyota Camry, 100 CNY/day, 3 days
        Rental rental1 = createRental("ABC123", "Toyota Camry", 100.0, 
                                    dateFormat.parse("2025-11-10"), 
                                    dateFormat.parse("2025-11-13"), 
                                    dateFormat.parse("2025-11-12"));
        rentals.add(rental1);
        
        // Second rental: Honda Civic, 150 CNY/day, 2 days
        Rental rental2 = createRental("XYZ789", "Honda Civic", 150.0, 
                                    dateFormat.parse("2025-11-10"), 
                                    dateFormat.parse("2025-11-13"), 
                                    dateFormat.parse("2025-11-11"));
        rentals.add(rental2);
        
        // Third rental: Ford Focus, 200 CNY/day, 1 day
        Rental rental3 = createRental("LMN456", "Ford Focus", 200.0, 
                                    dateFormat.parse("2025-11-12"), 
                                    dateFormat.parse("2025-11-13"), 
                                    dateFormat.parse("2025-11-12"));
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected: (100 * 3) + (150 * 2) + (200 * 1) = 300 + 300 + 200 = 800 CNY
        assertEquals(800.0, result, 0.001);
    }
    
    @Test
    public void testCase2_NoRentalsCalculation() {
        // Set empty rentals list
        store.setRentals(new ArrayList<Rental>());
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected: 0 CNY
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleRentalsWithSameDailyPrice() throws ParseException {
        // Set up rentals as specified
        List<Rental> rentals = new ArrayList<>();
        
        // First rental: Chevrolet Malibu, 120 CNY/day, 2 days
        Rental rental1 = createRental("CAR001", "Chevrolet Malibu", 120.0, 
                                    dateFormat.parse("2025-11-12"), 
                                    dateFormat.parse("2025-11-13"), 
                                    dateFormat.parse("2025-11-13"));
        rentals.add(rental1);
        
        // Second rental: Hyundai Elantra, 120 CNY/day, 4 days
        Rental rental2 = createRental("CAR002", "Hyundai Elantra", 120.0, 
                                    dateFormat.parse("2025-11-12"), 
                                    dateFormat.parse("2025-12-01"), 
                                    dateFormat.parse("2025-11-15"));
        rentals.add(rental2);
        
        store.setRentals(rentals);
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected: (120 * 2) + (120 * 4) = 240 + 480 = 720 CNY
        assertEquals(720.0, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedPricesCalculation() throws ParseException {
        // Set up rentals as specified
        List<Rental> rentals = new ArrayList<>();
        
        // First rental: Mazda 3, 90 CNY/day, 5 days
        Rental rental1 = createRental("SED123", "Mazda 3", 90.0, 
                                    dateFormat.parse("2025-08-09"), 
                                    dateFormat.parse("2025-09-01"), 
                                    dateFormat.parse("2025-08-13"));
        rentals.add(rental1);
        
        // Second rental: Kia Sportage, 150 CNY/day, 3 days
        Rental rental2 = createRental("SUV456", "Kia Sportage", 150.0, 
                                    dateFormat.parse("2025-08-11"), 
                                    dateFormat.parse("2026-01-01"), 
                                    dateFormat.parse("2025-08-13"));
        rentals.add(rental2);
        
        // Third rental: Ford F-150, 250 CNY/day, 1 day
        Rental rental3 = createRental("TRK789", "Ford F-150", 250.0, 
                                    dateFormat.parse("2025-08-09"), 
                                    dateFormat.parse("2025-09-01"), 
                                    dateFormat.parse("2025-08-09"));
        rentals.add(rental3);
        
        store.setRentals(rentals);
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected: (90 * 5) + (150 * 3) + (250 * 1) = 450 + 450 + 250 = 1150 CNY
        assertEquals(1150.0, result, 0.001);
    }
    
    @Test
    public void testCase5_OneDayRentalCalculation() throws ParseException {
        // Set up rentals as specified
        List<Rental> rentals = new ArrayList<>();
        
        // First rental: Mini Cooper, 180 CNY/day, 1 day
        Rental rental1 = createRental("MINI001", "Mini Cooper", 180.0, 
                                    dateFormat.parse("2025-08-12"), 
                                    dateFormat.parse("2025-09-01"), 
                                    dateFormat.parse("2025-08-12"));
        rentals.add(rental1);
        
        // Second rental: Harley Davidson, 220 CNY/day, 1 day
        Rental rental2 = createRental("MOTO002", "Harley Davidson", 220.0, 
                                    dateFormat.parse("2025-08-09"), 
                                    dateFormat.parse("2025-09-01"), 
                                    dateFormat.parse("2025-08-09"));
        rentals.add(rental2);
        
        store.setRentals(rentals);
        
        // Calculate total revenue
        double result = store.calculateTotalRevenue();
        
        // Expected: (180 * 1) + (220 * 1) = 180 + 220 = 400 CNY
        assertEquals(400.0, result, 0.001);
    }
    
    // Helper method to create Rental objects with the specified parameters
    private Rental createRental(String plate, String model, double dailyPrice, 
                               Date rentalDate, Date dueDate, Date backDate) {
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
        
        // Calculate total price: daily price * number of days
        long diffInMillies = backDate.getTime() - rentalDate.getTime();
        long diffInDays = diffInMillies / (1000 * 60 * 60 * 24);
        rental.setTotalPrice(dailyPrice * diffInDays);
        
        return rental;
    }
}