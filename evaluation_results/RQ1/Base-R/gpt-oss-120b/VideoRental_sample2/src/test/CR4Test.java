import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CR4Test {
    private RentalStore rentalStore;
    
    @Before
    public void setUp() {
        rentalStore = new RentalStore();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        VideoTape tape1 = new VideoTape();
        tape1.setTapeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setTapeId("T002");
        
        rentalStore.getInventory().add(tape1);
        rentalStore.getInventory().add(tape2);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-03"));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-01"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-12"));
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute
        double totalPrice = rentalStore.calculateTotalPrice(customer);
        
        // Verify
        assertEquals(13.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        VideoTape tape = new VideoTape();
        tape.setTapeId("T003");
        rentalStore.getInventory().add(tape);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01"));
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(LocalDate.parse("2025-01-12"));
        
        customer.getRentals().add(rental);
        
        // Execute
        double totalPrice = rentalStore.calculateTotalPrice(customer);
        
        // Verify
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        VideoTape tape1 = new VideoTape();
        tape1.setTapeId("T004");
        VideoTape tape2 = new VideoTape();
        tape2.setTapeId("T005");
        
        rentalStore.getInventory().add(tape1);
        rentalStore.getInventory().add(tape2);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-09"));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-10"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-18"));
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute
        double totalPrice = rentalStore.calculateTotalPrice(customer);
        
        // Verify
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        
        VideoTape tape1 = new VideoTape();
        tape1.setTapeId("T006");
        VideoTape tape2 = new VideoTape();
        tape2.setTapeId("T007");
        
        rentalStore.getInventory().add(tape1);
        rentalStore.getInventory().add(tape2);
        
        Rental rental1 = new Rental();
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-07"));
        
        Rental rental2 = new Rental();
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-10"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-14"));
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        
        // Execute
        double totalPrice = rentalStore.calculateTotalPrice(customer);
        
        // Verify
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C006");
        
        VideoTape tape = new VideoTape();
        tape.setTapeId("T008");
        rentalStore.getInventory().add(tape);
        
        Rental rental = new Rental();
        rental.setCustomer(customer);
        rental.setTape(tape);
        rental.setRentalDate(LocalDate.parse("2025-01-01"));
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(null); // Not returned yet
        
        customer.getRentals().add(rental);
        
        // Execute
        double totalPrice = rentalStore.calculateTotalPrice(customer);
        
        // Verify
        assertEquals(11.50, totalPrice, 0.001);
    }
}