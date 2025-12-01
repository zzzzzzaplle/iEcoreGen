import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private VideoRentalSystem videoRentalSystem;
    
    @Before
    public void setUp() {
        videoRentalSystem = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup: Create Customer C001 with 5 active rentals
        Customer customer = new Customer();
        customer.setId("C001");
        customer.setAccountNumber("ACC001");
        customer.setIdCardNumber("ID001");
        
        // Create 5 active rentals for customer
        for (int i = 1; i <= 5; i++) {
            RentalTransaction transaction = new RentalTransaction();
            transaction.setCustomer(customer);
            transaction.setRentalDate("2025-01-0" + i);
            
            VideoTape tape = new VideoTape();
            tape.setBarCodeId("T00" + (i + 5)); // T006 to T010
            
            Rental rental = new Rental();
            rental.setVideoTape(tape);
            rental.setRentalDate("2025-01-0" + i);
            rental.setDueDate("2025-01-" + String.format("%02d", i + 7));
            rental.setReturnDate(null);
            
            transaction.getRentals().add(rental);
            customer.getRentalTransactions().add(transaction);
            videoRentalSystem.getRentalTransactions().add(transaction);
        }
        
        videoRentalSystem.getCustomers().add(customer);
        
        // Create available Tape T001
        VideoTape tapeT001 = new VideoTape();
        tapeT001.setBarCodeId("T001");
        videoRentalSystem.getVideoTapes().add(tapeT001);
        
        // Test: C001 rents tape "T001" with current_date="2025-01-01"
        boolean result = videoRentalSystem.addVideoTapeRental("C001", "T001", "2025-01-01", "2025-01-08");
        
        // Verify: Expected Output: True
        assertTrue("Rental should be successful when customer has <20 rentals and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20Rentals() {
        // Setup: Create Customer C002 with 20 active rentals
        Customer customer = new Customer();
        customer.setId("C002");
        customer.setAccountNumber("ACC002");
        customer.setIdCardNumber("ID002");
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            RentalTransaction transaction = new RentalTransaction();
            transaction.setCustomer(customer);
            transaction.setRentalDate("2025-01-01");
            
            VideoTape tape = new VideoTape();
            tape.setBarCodeId("T1" + String.format("%02d", i)); // T101 to T120
            
            Rental rental = new Rental();
            rental.setVideoTape(tape);
            rental.setRentalDate("2025-01-01");
            rental.setDueDate("2025-01-08");
            rental.setReturnDate(null);
            
            transaction.getRentals().add(rental);
            customer.getRentalTransactions().add(transaction);
            videoRentalSystem.getRentalTransactions().add(transaction);
        }
        
        videoRentalSystem.getCustomers().add(customer);
        
        // Create available Tape T002
        VideoTape tapeT002 = new VideoTape();
        tapeT002.setBarCodeId("T002");
        videoRentalSystem.getVideoTapes().add(tapeT002);
        
        // Test: C002 rents tape "T002" with current_date="2025-01-01"
        boolean result = videoRentalSystem.addVideoTapeRental("C002", "T002", "2025-01-01", "2025-01-08");
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has 20 rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup: Create Customer C003 with 1 active rental that is overdue
        Customer customer = new Customer();
        customer.setId("C003");
        customer.setAccountNumber("ACC003");
        customer.setIdCardNumber("ID003");
        
        // Create overdue rental for customer (due_date="2025-01-05", return_date=null, 3 days overdue)
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentalDate("2025-01-01");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T020");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05"); // Due date is in the past for current_date="2025-01-08"
        rental.setReturnDate(null);
        
        transaction.getRentals().add(rental);
        customer.getRentalTransactions().add(transaction);
        videoRentalSystem.getRentalTransactions().add(transaction);
        
        videoRentalSystem.getCustomers().add(customer);
        
        // Create available Tape T003
        VideoTape tapeT003 = new VideoTape();
        tapeT003.setBarCodeId("T003");
        videoRentalSystem.getVideoTapes().add(tapeT003);
        
        // Test: C003 rents tape "T003" with current_date="2025-01-08" (3 days after due date)
        boolean result = videoRentalSystem.addVideoTapeRental("C003", "T003", "2025-01-08", "2025-01-15");
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup: Create Customer C004 with 0 rentals
        Customer customer = new Customer();
        customer.setId("C004");
        customer.setAccountNumber("ACC004");
        customer.setIdCardNumber("ID004");
        videoRentalSystem.getCustomers().add(customer);
        
        // Create Tape T004 with active rental by another customer C010
        VideoTape tapeT004 = new VideoTape();
        tapeT004.setBarCodeId("T004");
        videoRentalSystem.getVideoTapes().add(tapeT004);
        
        // Create another customer C010 who has rented T004
        Customer customerC010 = new Customer();
        customerC010.setId("C010");
        customerC010.setAccountNumber("ACC010");
        customerC010.setIdCardNumber("ID010");
        videoRentalSystem.getCustomers().add(customerC010);
        
        // Create rental transaction for C010 with T004
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customerC010);
        transaction.setRentalDate("2025-01-01");
        
        Rental rental = new Rental();
        rental.setVideoTape(tapeT004);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null);
        
        transaction.getRentals().add(rental);
        customerC010.getRentalTransactions().add(transaction);
        videoRentalSystem.getRentalTransactions().add(transaction);
        
        // Test: C004 rents tape "T004" with current_date="2025-01-01"
        boolean result = videoRentalSystem.addVideoTapeRental("C004", "T004", "2025-01-01", "2025-01-08");
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup: Create Customer C005 with 20 active rentals and one overdue rental
        Customer customer = new Customer();
        customer.setId("C005");
        customer.setAccountNumber("ACC005");
        customer.setIdCardNumber("ID005");
        
        // Create 20 active rentals for customer
        for (int i = 1; i <= 20; i++) {
            RentalTransaction transaction = new RentalTransaction();
            transaction.setCustomer(customer);
            transaction.setRentalDate("2025-01-01");
            
            VideoTape tape = new VideoTape();
            tape.setBarCodeId("T2" + String.format("%02d", i)); // T201 to T220
            
            Rental rental = new Rental();
            rental.setVideoTape(tape);
            rental.setRentalDate("2025-01-01");
            rental.setDueDate("2025-01-08");
            rental.setReturnDate(null);
            
            transaction.getRentals().add(rental);
            customer.getRentalTransactions().add(transaction);
            videoRentalSystem.getRentalTransactions().add(transaction);
        }
        
        // Add one overdue rental (due_date="2024-12-31", return_date=null)
        RentalTransaction overdueTransaction = new RentalTransaction();
        overdueTransaction.setCustomer(customer);
        overdueTransaction.setRentalDate("2024-12-24");
        
        VideoTape overdueTape = new VideoTape();
        overdueTape.setBarCodeId("T221");
        
        Rental overdueRental = new Rental();
        overdueRental.setVideoTape(overdueTape);
        overdueRental.setRentalDate("2024-12-24");
        overdueRental.setDueDate("2024-12-31"); // Overdue for current_date="2025-01-01"
        overdueRental.setReturnDate(null);
        
        overdueTransaction.getRentals().add(overdueRental);
        customer.getRentalTransactions().add(overdueTransaction);
        videoRentalSystem.getRentalTransactions().add(overdueTransaction);
        
        videoRentalSystem.getCustomers().add(customer);
        
        // Create Tape T005 with active rental by another customer C011
        VideoTape tapeT005 = new VideoTape();
        tapeT005.setBarCodeId("T005");
        videoRentalSystem.getVideoTapes().add(tapeT005);
        
        // Create another customer C011 who has rented T005
        Customer customerC011 = new Customer();
        customerC011.setId("C011");
        customerC011.setAccountNumber("ACC011");
        customerC011.setIdCardNumber("ID011");
        videoRentalSystem.getCustomers().add(customerC011);
        
        // Create rental transaction for C011 with T005
        RentalTransaction transactionC011 = new RentalTransaction();
        transactionC011.setCustomer(customerC011);
        transactionC011.setRentalDate("2025-01-01");
        
        Rental rentalC011 = new Rental();
        rentalC011.setVideoTape(tapeT005);
        rentalC011.setRentalDate("2025-01-01");
        rentalC011.setDueDate("2025-01-05");
        rentalC011.setReturnDate(null);
        
        transactionC011.getRentals().add(rentalC011);
        customerC011.getRentalTransactions().add(transactionC011);
        videoRentalSystem.getRentalTransactions().add(transactionC011);
        
        // Test: C005 rents tape "T005" with current_date="2025-01-01"
        boolean result = videoRentalSystem.addVideoTapeRental("C005", "T005", "2025-01-01", "2025-01-08");
        
        // Verify: Expected Output: False
        assertFalse("Rental should fail when all conditions (customer limit, overdue fees, tape availability) are violated", result);
    }
    
    // Helper method to access private fields for testing setup
    private static class VideoRentalSystemHelper extends VideoRentalSystem {
        public List<Customer> getCustomers() {
            return super.customers;
        }
        
        public List<VideoTape> getVideoTapes() {
            return super.videoTapes;
        }
        
        public List<RentalTransaction> getRentalTransactions() {
            return super.rentalTransactions;
        }
    }
    
    static {
        // Initialize the video rental system with the helper class
        try {
            Class<?> clazz = CR3Test.class;
            java.lang.reflect.Field field = clazz.getDeclaredField("videoRentalSystem");
            field.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}