import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private RentalSystem rentalSystem;
    private DateTimeFormatter formatter;

    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        tape1.setTitle("Movie 1");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        tape2.setTitle("Movie 2");
        
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        rental1.setBaseRentalFee(4.0); // 4 days rental
        rental1.setOverdueFee(0.0);
        
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        rental2.setBaseRentalFee(14.0); // 14 days rental
        rental2.setOverdueFee(0.0);
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        rentalSystem.getCustomers().add(customer);
        
        // Execute
        double totalAmountDue = customer.getTotalAmountDue();
        
        // Verify
        assertEquals(18.0, totalAmountDue, 0.001); // 4 + 14 = 18
    }

    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T003");
        tape.setTitle("Movie 3");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        rental.setBaseRentalFee(11.0); // 11 days rental
        rental.setOverdueFee(3.50); // 7 days overdue × $0.5
        
        customer.getRentals().add(rental);
        rentalSystem.getCustomers().add(customer);
        
        // Execute
        double totalAmountDue = customer.getTotalAmountDue();
        
        // Verify
        assertEquals(14.50, totalAmountDue, 0.001); // 11 + 3.50 = 14.50
    }

    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T004");
        tape1.setTitle("Movie 4");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T005");
        tape2.setTitle("Movie 5");
        
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        rental1.setBaseRentalFee(8.0); // 8 days rental
        rental1.setOverdueFee(2.0); // 4 days overdue × $0.5
        
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-18", formatter));
        rental2.setBaseRentalFee(8.0); // 8 days rental
        rental2.setOverdueFee(1.50); // 3 days overdue × $0.5
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalSystem.getCustomers().add(customer);
        
        // Execute
        double totalAmountDue = customer.getTotalAmountDue();
        
        // Verify
        assertEquals(19.50, totalAmountDue, 0.001); // 8+8 + 2+1.5 = 19.50
    }

    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T006");
        tape1.setTitle("Movie 6");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T007");
        tape2.setTitle("Movie 7");
        
        Rental rental1 = new Rental();
        rental1.setVideoTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-07", formatter));
        rental1.setBaseRentalFee(6.0); // 6 days rental
        rental1.setOverdueFee(1.0); // 2 days overdue × $0.5
        
        Rental rental2 = new Rental();
        rental2.setVideoTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-14", formatter));
        rental2.setBaseRentalFee(4.0); // 4 days rental
        rental2.setOverdueFee(0.0); // on-time
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalSystem.getCustomers().add(customer);
        
        // Execute
        double totalAmountDue = customer.getTotalAmountDue();
        
        // Verify
        assertEquals(11.00, totalAmountDue, 0.001); // 6+4 + 1 = 11.00
    }

    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C006");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T008");
        tape.setTitle("Movie 8");
        
        Rental rental = new Rental();
        rental.setVideoTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null); // unreturned
        rental.setBaseRentalFee(9.0); // 9 days rental (current date is 2025-01-10)
        rental.setOverdueFee(2.50); // 5 days overdue × $0.5
        
        customer.getRentals().add(rental);
        rentalSystem.getCustomers().add(customer);
        
        // Execute
        double totalAmountDue = customer.getTotalAmountDue();
        
        // Verify
        assertEquals(11.50, totalAmountDue, 0.001); // 9 + 2.50 = 11.50
    }
}