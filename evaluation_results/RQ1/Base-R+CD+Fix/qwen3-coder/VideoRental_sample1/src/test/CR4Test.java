import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_NoOverdueFees() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C001");
        
        Date currentDate = dateFormat.parse("2025-01-20");
        
        // Rental 1: returned early, no overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-03"));
        
        // Rental 2: returned early, no overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(dateFormat.parse("2025-01-12"));
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(customer.getRentals());
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01"), currentDate);
        
        // Verify: Rental 1: 2 days + 0 overdue = 2, Rental 2: 11 days + 0 overdue = 11, Total = 13
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C002");
        
        Date currentDate = dateFormat.parse("2025-01-20");
        
        // Rental: 7 days overdue
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("T003");
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05"));
        rental.setReturnDate(dateFormat.parse("2025-01-12"));
        
        customer.getRentals().add(rental);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(customer.getRentals());
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01"), currentDate);
        
        // Verify: 11 days base fee + 3.50 overdue = 14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C003");
        
        Date currentDate = dateFormat.parse("2025-01-20");
        
        // Rental 1: 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-09"));
        
        // Rental 2: 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(dateFormat.parse("2025-01-18"));
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(customer.getRentals());
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01"), currentDate);
        
        // Verify: (8 + 8) base fees + (2.00 + 1.50) overdue = 19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C004");
        
        Date currentDate = dateFormat.parse("2025-01-20");
        
        // Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-07"));
        
        // Rental 2: on-time
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(dateFormat.parse("2025-01-14"));
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(customer.getRentals());
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01"), currentDate);
        
        // Verify: (6 + 4) base fees + 1.00 overdue = 11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C006");
        
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Rental: unreturned, 5 days overdue
        VideoRental rental = new VideoRental();
        Tape tape = new Tape();
        tape.setId("T008");
        rental.setTape(tape);
        rental.setDueDate(dateFormat.parse("2025-01-05"));
        rental.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(customer.getRentals());
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01"), currentDate);
        
        // Verify: 9 days base fee + 2.50 overdue = 11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}