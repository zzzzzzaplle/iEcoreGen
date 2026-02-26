import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private VideoRentalSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_ReturnedOneDayLate() {
        // Setup
        Customer c1 = new Customer();
        c1.setAccountNumber("ACC001");
        c1.setIdCardNumber("ID001");
        c1.setName("Customer 1");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("V001");
        tape.setTitle("Video 1");
        tape.setAvailable(true);
        
        system.getVideoInventory().add(tape);
        system.getCustomers().add(c1);
        
        // Create rental transaction
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId("TXN001");
        rental.setCustomer(c1);
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setRentalDays(1);
        rental.setDueDate("2025-01-02");
        rental.setReturnDate("2025-01-09");
        
        c1.getRentalTransactions().add(rental);
        system.getAllRentalTransactions().add(rental);
        
        // Test
        double result = rental.calculateOverdueFee("2025-01-10");
        assertEquals("Returned 1 day late should have overdue fee of 0.50", 0.50, result, 0.001);
    }
    
    @Test
    public void testCase2_UnreturnedAndThreeDaysOverdue() {
        // Setup
        Customer c2 = new Customer();
        c2.setAccountNumber("ACC002");
        c2.setIdCardNumber("ID002");
        c2.setName("Customer 2");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("V002");
        tape.setTitle("Video 2");
        tape.setAvailable(true);
        
        system.getVideoInventory().add(tape);
        system.getCustomers().add(c2);
        
        // Create rental transaction (not returned)
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId("TXN002");
        rental.setCustomer(c2);
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setRentalDays(8); // Due date would be 2025-01-09
        rental.setDueDate("2025-01-09");
        rental.setReturnDate(null); // Not returned
        
        c2.getRentalTransactions().add(rental);
        system.getAllRentalTransactions().add(rental);
        
        // Test with current date 2025-01-12 (3 days overdue)
        double result = rental.calculateOverdueFee("2025-01-12");
        assertEquals("Unreturned and 3 days overdue should have overdue fee of 1.50", 1.50, result, 0.001);
    }
    
    @Test
    public void testCase3_ReturnedOnDueDate() {
        // Setup
        Customer c3 = new Customer();
        c3.setAccountNumber("ACC003");
        c3.setIdCardNumber("ID003");
        c3.setName("Customer 3");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("V003");
        tape.setTitle("Video 3");
        tape.setAvailable(true);
        
        system.getVideoInventory().add(tape);
        system.getCustomers().add(c3);
        
        // Create rental transaction
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId("TXN003");
        rental.setCustomer(c3);
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setRentalDays(5); // Due date would be 2025-01-06
        rental.setDueDate("2025-01-06");
        rental.setReturnDate("2025-01-06"); // Returned on due date
        
        c3.getRentalTransactions().add(rental);
        system.getAllRentalTransactions().add(rental);
        
        // Test
        double result = rental.calculateOverdueFee("2025-01-10");
        assertEquals("Returned on due date should have overdue fee of 0.00", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase4_UnreturnedButNotOverdue() {
        // Setup
        Customer c4 = new Customer();
        c4.setAccountNumber("ACC004");
        c4.setIdCardNumber("ID004");
        c4.setName("Customer 4");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("V004");
        tape.setTitle("Video 4");
        tape.setAvailable(true);
        
        system.getVideoInventory().add(tape);
        system.getCustomers().add(c4);
        
        // Create rental transaction (not returned)
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId("TXN004");
        rental.setCustomer(c4);
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-05");
        rental.setRentalDays(5); // Due date would be 2025-01-10
        rental.setDueDate("2025-01-10");
        rental.setReturnDate(null); // Not returned
        
        c4.getRentalTransactions().add(rental);
        system.getAllRentalTransactions().add(rental);
        
        // Test with current date 2025-01-06 (not overdue)
        double result = rental.calculateOverdueFee("2025-01-06");
        assertEquals("Unreturned but not overdue should have overdue fee of 0.00", 0.00, result, 0.001);
    }
    
    @Test
    public void testCase5_ReturnedFiveDaysLate() {
        // Setup
        Customer c5 = new Customer();
        c5.setAccountNumber("ACC005");
        c5.setIdCardNumber("ID005");
        c5.setName("Customer 5");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("V005");
        tape.setTitle("Video 5");
        tape.setAvailable(true);
        
        system.getVideoInventory().add(tape);
        system.getCustomers().add(c5);
        
        // Create rental transaction
        RentalTransaction rental = new RentalTransaction();
        rental.setTransactionId("TXN005");
        rental.setCustomer(c5);
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setRentalDays(7); // Due date would be 2025-01-08
        rental.setDueDate("2025-01-08");
        rental.setReturnDate("2025-01-10"); // Returned 2 days late
        
        c5.getRentalTransactions().add(rental);
        system.getAllRentalTransactions().add(rental);
        
        // Test with current date 2025-01-10
        double result = rental.calculateOverdueFee("2025-01-10");
        assertEquals("Returned 2 days late should have overdue fee of 1.00", 1.00, result, 0.001);
    }
}