import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CR4Test {
    private SimpleDateFormat dateFormat;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_NoOverdueFees() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setId("C001");
        
        // Create Rental 1: returned early, no overdue fee
        Tape tape1 = new Tape();
        tape1.setId("T001");
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date returnDate1 = dateFormat.parse("2025-01-03");
        VideoRental rental1 = new VideoRental(tape1, rentalDate1);
        rental1.setReturnDate(returnDate1);
        
        // Create Rental 2: returned early, no overdue fee
        Tape tape2 = new Tape();
        tape2.setId("T002");
        Date rentalDate2 = dateFormat.parse("2025-01-01");
        Date returnDate2 = dateFormat.parse("2025-01-12");
        VideoRental rental2 = new VideoRental(tape2, rentalDate2);
        rental2.setReturnDate(returnDate2);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        Date currentDate = dateFormat.parse("2025-01-20");
        double totalPrice = transaction.calculateTotalPrice(rentalDate1, currentDate);
        
        // Expected: Rental 1: 2 days + 0 overdue = 2, Rental 2: 11 days + 0 overdue = 11, Total = 13
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setId("C002");
        
        // Create Rental 1: 7 days overdue
        Tape tape1 = new Tape();
        tape1.setId("T003");
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date returnDate1 = dateFormat.parse("2025-01-12");
        VideoRental rental1 = new VideoRental(tape1, rentalDate1);
        rental1.setReturnDate(returnDate1);
        
        // Add rental to customer
        customer.getRentals().add(rental1);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        
        Date currentDate = dateFormat.parse("2025-01-20");
        double totalPrice = transaction.calculateTotalPrice(rentalDate1, currentDate);
        
        // Expected: 11 days base fee + 3.50 overdue = 14.50
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setId("C003");
        
        // Create Rental 1: 4 days overdue
        Tape tape1 = new Tape();
        tape1.setId("T004");
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date returnDate1 = dateFormat.parse("2025-01-09");
        VideoRental rental1 = new VideoRental(tape1, rentalDate1);
        rental1.setReturnDate(returnDate1);
        
        // Create Rental 2: 3 days overdue
        Tape tape2 = new Tape();
        tape2.setId("T005");
        Date rentalDate2 = dateFormat.parse("2025-01-10");
        Date returnDate2 = dateFormat.parse("2025-01-18");
        VideoRental rental2 = new VideoRental(tape2, rentalDate2);
        rental2.setReturnDate(returnDate2);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        Date currentDate = dateFormat.parse("2025-01-20");
        double totalPrice = transaction.calculateTotalPrice(rentalDate1, currentDate);
        
        // Expected: (8 + 8) base fees + (2.00 + 1.50) overdue = 19.50
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setId("C004");
        
        // Create Rental 1: 2 days overdue
        Tape tape1 = new Tape();
        tape1.setId("T006");
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        Date returnDate1 = dateFormat.parse("2025-01-07");
        VideoRental rental1 = new VideoRental(tape1, rentalDate1);
        rental1.setReturnDate(returnDate1);
        
        // Create Rental 2: on-time, no overdue
        Tape tape2 = new Tape();
        tape2.setId("T007");
        Date rentalDate2 = dateFormat.parse("2025-01-10");
        Date returnDate2 = dateFormat.parse("2025-01-14");
        VideoRental rental2 = new VideoRental(tape2, rentalDate2);
        rental2.setReturnDate(returnDate2);
        
        // Add rentals to customer
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        transaction.getRentals().add(rental2);
        
        Date currentDate = dateFormat.parse("2025-01-20");
        double totalPrice = transaction.calculateTotalPrice(rentalDate1, currentDate);
        
        // Expected: (6 + 4) base fees + 1.00 overdue = 11.00
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() throws ParseException {
        // Setup
        Customer customer = new Customer();
        customer.setId("C006");
        
        // Create Rental 1: unreturned, 5 days overdue
        Tape tape1 = new Tape();
        tape1.setId("T008");
        Date rentalDate1 = dateFormat.parse("2025-01-01");
        VideoRental rental1 = new VideoRental(tape1, rentalDate1);
        // returnDate remains null (unreturned)
        
        // Add rental to customer
        customer.getRentals().add(rental1);
        
        // Create transaction and calculate total price
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.getRentals().add(rental1);
        
        Date currentDate = dateFormat.parse("2025-01-10");
        double totalPrice = transaction.calculateTotalPrice(rentalDate1, currentDate);
        
        // Expected: 9 days base fee + 2.50 overdue = 11.50
        assertEquals(11.50, totalPrice, 0.001);
    }
}