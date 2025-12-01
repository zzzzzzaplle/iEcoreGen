import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    private RentalSystem rentalSystem;
    private Customer customer1;
    private Customer customer2;
    private Customer customer3;
    private Customer customer4;
    private Customer customer5;
    private VideoTape tape1;
    private VideoTape tape2;
    private VideoTape tape3;
    private VideoTape tape4;
    private VideoTape tape5;
    private Customer otherCustomer1;
    private Customer otherCustomer2;

    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        
        // Create test customers
        customer1 = new Customer();
        customer1.setAccountId("C001");
        
        customer2 = new Customer();
        customer2.setAccountId("C002");
        
        customer3 = new Customer();
        customer3.setAccountId("C003");
        
        customer4 = new Customer();
        customer4.setAccountId("C004");
        
        customer5 = new Customer();
        customer5.setAccountId("C005");
        
        otherCustomer1 = new Customer();
        otherCustomer1.setAccountId("C010");
        
        otherCustomer2 = new Customer();
        otherCustomer2.setAccountId("C011");
        
        // Create test tapes
        tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        tape1.setAvailable(true);
        
        tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        tape2.setAvailable(true);
        
        tape3 = new VideoTape();
        tape3.setBarcodeId("T003");
        tape3.setAvailable(true);
        
        tape4 = new VideoTape();
        tape4.setBarcodeId("T004");
        tape4.setAvailable(true);
        
        tape5 = new VideoTape();
        tape5.setBarcodeId("T005");
        tape5.setAvailable(true);
        
        // Add customers and tapes to the system
        rentalSystem.getCustomers().add(customer1);
        rentalSystem.getCustomers().add(customer2);
        rentalSystem.getCustomers().add(customer3);
        rentalSystem.getCustomers().add(customer4);
        rentalSystem.getCustomers().add(customer5);
        rentalSystem.getCustomers().add(otherCustomer1);
        rentalSystem.getCustomers().add(otherCustomer2);
        
        rentalSystem.getTapes().add(tape1);
        rentalSystem.getTapes().add(tape2);
        rentalSystem.getTapes().add(tape3);
        rentalSystem.getTapes().add(tape4);
        rentalSystem.getTapes().add(tape5);
    }

    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        for (int i = 1; i <= 5; i++) {
            Rental rental = new Rental();
            rental.setRentalId("R" + i);
            rental.setCustomer(customer1);
            rental.setTape(tape1); // Using tape1 as placeholder
            rental.setRentalDate(LocalDate.of(2025, 1, 1));
            rental.setDueDate(LocalDate.of(2025, 1, 1).plusDays(7));
            rental.setRentalDays(7);
            rental.setBaseRentalFee(7.0);
            customer1.addRental(rental);
            rentalSystem.getRentals().add(rental);
        }
        
        // Test: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental("C001", "T001", 7);
        
        // Verify: Expected output is True
        assertTrue("Rental should be successful", result);
    }

    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup: Create Customer C002 with 20 active rentals
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setRentalId("R" + i);
            rental.setCustomer(customer2);
            rental.setTape(tape2); // Using tape2 as placeholder
            rental.setRentalDate(LocalDate.of(2025, 1, 1));
            rental.setDueDate(LocalDate.of(2025, 1, 1).plusDays(7));
            rental.setRentalDays(7);
            rental.setBaseRentalFee(7.0);
            customer2.addRental(rental);
            rentalSystem.getRentals().add(rental);
        }
        
        // Test: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental("C002", "T002", 7);
        
        // Verify: Expected output is False
        assertFalse("Rental should fail due to maximum rental limit", result);
    }

    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental, due_date="2025-01-02", return_date=null (3 days overdue at current_date="2025-01-05")
        Rental overdueRental = new Rental();
        overdueRental.setRentalId("R1");
        overdueRental.setCustomer(customer3);
        overdueRental.setTape(tape3); // Using tape3 as placeholder
        overdueRental.setRentalDate(LocalDate.of(2025, 1, 1));
        overdueRental.setDueDate(LocalDate.of(2025, 1, 2)); // Due 2 days after rental
        overdueRental.setReturnDate(null); // Not returned
        overdueRental.setRentalDays(2);
        overdueRental.setBaseRentalFee(2.0);
        customer3.addRental(overdueRental);
        rentalSystem.getRentals().add(overdueRental);
        
        // Test: C003 rents tape "T003" with current_date="2025-01-05"
        // Note: We need to simulate the current date being 2025-01-05 for the overdue calculation
        // Since we can't mock LocalDate.now(), we'll rely on the actual calculation
        boolean result = rentalSystem.addVideoTapeRental("C003", "T003", 7);
        
        // Verify: Expected output is False
        assertFalse("Rental should fail due to overdue fees", result);
    }

    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Tape T004 with active rental (rented by another customer C010)
        Rental activeRental = new Rental();
        activeRental.setRentalId("R1");
        activeRental.setCustomer(otherCustomer1);
        activeRental.setTape(tape4);
        activeRental.setRentalDate(LocalDate.of(2025, 1, 1));
        activeRental.setDueDate(LocalDate.of(2025, 1, 5));
        activeRental.setReturnDate(null); // Not returned
        activeRental.setRentalDays(4);
        activeRental.setBaseRentalFee(4.0);
        otherCustomer1.addRental(activeRental);
        rentalSystem.getRentals().add(activeRental);
        
        // Test: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental("C004", "T004", 7);
        
        // Verify: Expected output is False
        assertFalse("Rental should fail due to tape being unavailable", result);
    }

    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setRentalId("R" + i);
            rental.setCustomer(customer5);
            rental.setTape(tape5); // Using tape5 as placeholder
            rental.setRentalDate(LocalDate.of(2025, 1, 1));
            rental.setDueDate(LocalDate.of(2025, 1, 5));
            rental.setReturnDate(null);
            rental.setRentalDays(4);
            rental.setBaseRentalFee(4.0);
            customer5.addRental(rental);
            rentalSystem.getRentals().add(rental);
        }
        
        // Add an overdue rental for customer5
        Rental overdueRental = new Rental();
        overdueRental.setRentalId("R21");
        overdueRental.setCustomer(customer5);
        overdueRental.setTape(tape5); // Using tape5 as placeholder
        overdueRental.setRentalDate(LocalDate.of(2024, 12, 31));
        overdueRental.setDueDate(LocalDate.of(2024, 12, 31).plusDays(7)); // Due 7 days after rental
        overdueRental.setReturnDate(null); // Not returned
        overdueRental.setRentalDays(7);
        overdueRental.setBaseRentalFee(7.0);
        customer5.addRental(overdueRental);
        rentalSystem.getRentals().add(overdueRental);
        
        // Setup: Create Tape T005 with active rental (rented by another customer C011)
        Rental activeRental = new Rental();
        activeRental.setRentalId("R22");
        activeRental.setCustomer(otherCustomer2);
        activeRental.setTape(tape5);
        activeRental.setRentalDate(LocalDate.of(2025, 1, 1));
        activeRental.setDueDate(LocalDate.of(2025, 1, 5));
        activeRental.setReturnDate(null); // Not returned
        activeRental.setRentalDays(4);
        activeRental.setBaseRentalFee(4.0);
        otherCustomer2.addRental(activeRental);
        rentalSystem.getRentals().add(activeRental);
        
        // Test: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = rentalSystem.addVideoTapeRental("C005", "T005", 7);
        
        // Verify: Expected output is False
        assertFalse("Rental should fail due to all conditions failing", result);
    }
}