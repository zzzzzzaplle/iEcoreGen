import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private VideoRentalSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup
        Customer customer = createCustomer("C001", "ID001");
        VideoTape tape = createTape("T001", "Movie Title 1", true);
        
        // Create 5 active rentals for customer
        for (int i = 1; i <= 5; i++) {
            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setTape(createTape("T00" + (i + 10), "Movie " + i, true));
            rental.setRentalDate(LocalDate.parse("2025-01-0" + i, formatter));
            rental.setDueDate(LocalDate.parse("2025-01-0" + i, formatter).plusDays(7));
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
            system.getRentals().add(rental);
        }
        
        system.getCustomers().add(customer);
        system.getInventory().add(tape);
        
        // Test
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = system.addVideoRental(customer, tape, currentDate, currentDate.plusDays(7), currentDate);
        
        // Verify
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue fees, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup
        Customer customer = createCustomer("C002", "ID002");
        VideoTape tape = createTape("T002", "Movie Title 2", true);
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setTape(createTape("T0" + (20 + i), "Movie " + i, true));
            rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
            rental.setDueDate(LocalDate.parse("2025-01-01", formatter).plusDays(7));
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
            system.getRentals().add(rental);
        }
        
        system.getCustomers().add(customer);
        system.getInventory().add(tape);
        
        // Test
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = system.addVideoRental(customer, tape, currentDate, currentDate.plusDays(7), currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has 20 active rentals", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup
        Customer customer = createCustomer("C003", "ID003");
        VideoTape tape = createTape("T003", "Movie Title 3", true);
        
        // Create one overdue rental for customer
        Rental overdueRental = new Rental();
        overdueRental.setCustomer(customer);
        overdueRental.setTape(createTape("T030", "Overdue Movie", true));
        overdueRental.setRentalDate(LocalDate.parse("2024-12-29", formatter));
        overdueRental.setDueDate(LocalDate.parse("2025-01-05", formatter)); // Already overdue on current date
        overdueRental.setReturnDate(null);
        customer.getRentals().add(overdueRental);
        system.getRentals().add(overdueRental);
        
        system.getCustomers().add(customer);
        system.getInventory().add(tape);
        
        // Test
        LocalDate currentDate = LocalDate.parse("2025-01-08", formatter); // 3 days overdue
        boolean result = system.addVideoRental(customer, tape, currentDate, currentDate.plusDays(7), currentDate);
        
        // Verify
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup
        Customer customer = createCustomer("C004", "ID004");
        Customer otherCustomer = createCustomer("C010", "ID010");
        VideoTape tape = createTape("T004", "Movie Title 4", true);
        
        // Create active rental for tape by another customer
        Rental existingRental = new Rental();
        existingRental.setCustomer(otherCustomer);
        existingRental.setTape(tape);
        existingRental.setRentalDate(LocalDate.parse("2024-12-29", formatter));
        existingRental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        existingRental.setReturnDate(null);
        otherCustomer.getRentals().add(existingRental);
        system.getRentals().add(existingRental);
        
        system.getCustomers().add(customer);
        system.getCustomers().add(otherCustomer);
        system.getInventory().add(tape);
        
        // Test
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = system.addVideoRental(customer, tape, currentDate, currentDate.plusDays(7), currentDate);
        
        // Verify
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup
        Customer customer = createCustomer("C005", "ID005");
        Customer otherCustomer = createCustomer("C011", "ID011");
        VideoTape tape = createTape("T005", "Movie Title 5", true);
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setCustomer(customer);
            rental.setTape(createTape("T0" + (50 + i), "Movie " + i, true));
            rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
            rental.setDueDate(LocalDate.parse("2025-01-01", formatter).plusDays(7));
            rental.setReturnDate(null);
            customer.getRentals().add(rental);
            system.getRentals().add(rental);
        }
        
        // Add one overdue rental to customer
        Rental overdueRental = new Rental();
        overdueRental.setCustomer(customer);
        overdueRental.setTape(createTape("T080", "Overdue Movie", true));
        overdueRental.setRentalDate(LocalDate.parse("2024-12-25", formatter));
        overdueRental.setDueDate(LocalDate.parse("2024-12-31", formatter)); // Overdue on current date
        overdueRental.setReturnDate(null);
        customer.getRentals().add(overdueRental);
        system.getRentals().add(overdueRental);
        
        // Create active rental for tape by another customer
        Rental existingRental = new Rental();
        existingRental.setCustomer(otherCustomer);
        existingRental.setTape(tape);
        existingRental.setRentalDate(LocalDate.parse("2024-12-29", formatter));
        existingRental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        existingRental.setReturnDate(null);
        otherCustomer.getRentals().add(existingRental);
        system.getRentals().add(existingRental);
        
        system.getCustomers().add(customer);
        system.getCustomers().add(otherCustomer);
        system.getInventory().add(tape);
        
        // Test
        LocalDate currentDate = LocalDate.parse("2025-01-01", formatter);
        boolean result = system.addVideoRental(customer, tape, currentDate, currentDate.plusDays(7), currentDate);
        
        // Verify
        assertFalse("Rental should fail when all conditions (20 rentals, overdue fees, tape unavailable) are violated", result);
    }
    
    private Customer createCustomer(String accountNumber, String idCard) {
        Customer customer = new Customer();
        customer.setAccountNumber(accountNumber);
        customer.setIdCard(idCard);
        return customer;
    }
    
    private VideoTape createTape(String barcodeId, String title, boolean available) {
        VideoTape tape = new VideoTape();
        tape.setBarcodeId(barcodeId);
        tape.setTitle(title);
        tape.setAvailable(available);
        return tape;
    }
}