import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    private VideoRentalSystem rentalSystem;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalSystem = new VideoRentalSystem();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T002");
        
        Rental rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        rental1.setBaseRentalFee(0.0);
        
        Rental rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        rental2.setBaseRentalFee(0.0);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Execute
        double totalPrice = rentalSystem.calculateTotalPrice(customer, currentDate);
        
        // Verify
        assertEquals(13.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T003");
        
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        rental.setBaseRentalFee(0.0);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Execute
        double totalPrice = rentalSystem.calculateTotalPrice(customer, currentDate);
        
        // Verify
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T004");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T005");
        
        Rental rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        rental1.setBaseRentalFee(0.0);
        
        Rental rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(LocalDate.parse("2025-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-18", formatter));
        rental2.setBaseRentalFee(0.0);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Execute
        double totalPrice = rentalSystem.calculateTotalPrice(customer, currentDate);
        
        // Verify
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarcodeId("T006");
        VideoTape tape2 = new VideoTape();
        tape2.setBarcodeId("T007");
        
        Rental rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-07", formatter));
        rental1.setBaseRentalFee(0.0);
        
        Rental rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(LocalDate.parse("2025-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-14", formatter));
        rental2.setBaseRentalFee(0.0);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        LocalDate currentDate = LocalDate.parse("2025-01-20", formatter);
        
        // Execute
        double totalPrice = rentalSystem.calculateTotalPrice(customer, currentDate);
        
        // Verify
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C006");
        
        VideoTape tape = new VideoTape();
        tape.setBarcodeId("T008");
        
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental.setReturnDate(null); // Unreturned tape
        rental.setBaseRentalFee(0.0);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        LocalDate currentDate = LocalDate.parse("2025-01-10", formatter);
        
        // Execute
        double totalPrice = rentalSystem.calculateTotalPrice(customer, currentDate);
        
        // Verify
        assertEquals(11.50, totalPrice, 0.001);
    }
}