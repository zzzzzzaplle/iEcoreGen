import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR4Test {
    
    private VideoRentalSystem system;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup customer
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        system.getCustomers().add(customer);
        
        // Setup Rental 1: returned early
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        Rental rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-03");
        rental1.setRentalDays(4); // Jan 1-5 = 4 days
        customer.getRentals().add(rental1);
        
        // Setup Rental 2: returned early
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        Rental rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setRentalDate("2025-01-01");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-12");
        rental2.setRentalDays(14); // Jan 1-15 = 14 days
        customer.getRentals().add(rental2);
        
        // Calculate total price
        BigDecimal result = system.calculateTotalPriceForCustomer(customer, "2025-01-20");
        
        // Verify result: (4 + 0) + (14 + 0) = 18
        assertEquals(new BigDecimal("18.00"), result);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup customer
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        system.getCustomers().add(customer);
        
        // Setup Rental: 7 days overdue
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate("2025-01-12");
        rental.setRentalDays(4); // Jan 1-5 = 4 days
        customer.getRentals().add(rental);
        
        // Calculate total price
        BigDecimal result = system.calculateTotalPriceForCustomer(customer, "2025-01-20");
        
        // Verify result: 4 (base) + 3.50 (overdue) = 7.50
        assertEquals(new BigDecimal("7.50"), result);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup customer
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        system.getCustomers().add(customer);
        
        // Setup Rental 1: 4 days overdue
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T004");
        Rental rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-09");
        rental1.setRentalDays(4); // Jan 1-5 = 4 days
        customer.getRentals().add(rental1);
        
        // Setup Rental 2: 3 days overdue
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T005");
        Rental rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-18");
        rental2.setRentalDays(5); // Jan 10-15 = 5 days
        customer.getRentals().add(rental2);
        
        // Calculate total price
        BigDecimal result = system.calculateTotalPriceForCustomer(customer, "2025-01-20");
        
        // Verify result: (4 + 2.00) + (5 + 1.50) = 12.50
        assertEquals(new BigDecimal("12.50"), result);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup customer
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        system.getCustomers().add(customer);
        
        // Setup Rental 1: 2 days overdue
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T006");
        Rental rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-07");
        rental1.setRentalDays(4); // Jan 1-5 = 4 days
        customer.getRentals().add(rental1);
        
        // Setup Rental 2: on-time
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T007");
        Rental rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-14");
        rental2.setRentalDays(5); // Jan 10-15 = 5 days
        customer.getRentals().add(rental2);
        
        // Calculate total price
        BigDecimal result = system.calculateTotalPriceForCustomer(customer, "2025-01-20");
        
        // Verify result: (4 + 1.00) + (5 + 0) = 10.00
        assertEquals(new BigDecimal("10.00"), result);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup customer
        Customer customer = new Customer();
        customer.setAccountNumber("C006");
        system.getCustomers().add(customer);
        
        // Setup Rental: unreturned, 5 days overdue as of current date
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T008");
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // Not returned
        rental.setRentalDays(4); // Jan 1-5 = 4 days
        customer.getRentals().add(rental);
        
        // Calculate total price
        BigDecimal result = system.calculateTotalPriceForCustomer(customer, "2025-01-10");
        
        // Verify result: 4 (base) + 2.50 (overdue) = 6.50
        assertEquals(new BigDecimal("6.50"), result);
    }
}