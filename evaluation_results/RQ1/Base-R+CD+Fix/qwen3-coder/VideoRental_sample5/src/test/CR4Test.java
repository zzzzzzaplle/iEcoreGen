import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
        
        // Create Rental 1: returned early, no overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T001");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-03"));
        
        // Create Rental 2: returned early, no overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T002");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(dateFormat.parse("2025-01-12"));
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01"), currentDate);
        
        // Verify expected output
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C002");
        
        Date currentDate = dateFormat.parse("2025-01-20");
        
        // Create Rental 1: returned late with overdue fee
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T003");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-12"));
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01"), currentDate);
        
        // Verify expected output
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C003");
        
        Date currentDate = dateFormat.parse("2025-01-20");
        
        // Create Rental 1: 4 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T004");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-09"));
        
        // Create Rental 2: 3 days overdue
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T005");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(dateFormat.parse("2025-01-18"));
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01"), currentDate);
        
        // Verify expected output
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C004");
        
        Date currentDate = dateFormat.parse("2025-01-20");
        
        // Create Rental 1: 2 days overdue
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T006");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(dateFormat.parse("2025-01-07"));
        
        // Create Rental 2: on-time return
        VideoRental rental2 = new VideoRental();
        Tape tape2 = new Tape();
        tape2.setId("T007");
        rental2.setTape(tape2);
        rental2.setDueDate(dateFormat.parse("2025-01-15"));
        rental2.setReturnDate(dateFormat.parse("2025-01-14"));
        
        // Add rentals to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01"), currentDate);
        
        // Verify expected output
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() throws Exception {
        // Setup
        Customer customer = new Customer();
        customer.setId("C006");
        
        Date currentDate = dateFormat.parse("2025-01-10");
        
        // Create Rental 1: unreturned with overdue fee
        VideoRental rental1 = new VideoRental();
        Tape tape1 = new Tape();
        tape1.setId("T008");
        rental1.setTape(tape1);
        rental1.setDueDate(dateFormat.parse("2025-01-05"));
        rental1.setReturnDate(null); // Not returned
        
        // Add rental to customer
        List<VideoRental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Create rental transaction
        RentalTransaction transaction = new RentalTransaction();
        transaction.setCustomer(customer);
        transaction.setRentals(rentals);
        transaction.setRentalDate(dateFormat.parse("2025-01-01"));
        
        // Calculate total price
        double totalPrice = transaction.calculateTotalPrice(dateFormat.parse("2025-01-01"), currentDate);
        
        // Verify expected output
        assertEquals(11.50, totalPrice, 0.001);
    }
}