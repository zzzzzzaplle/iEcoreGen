import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private VideoRentalSystem system;
    private Customer customer;
    
    @Before
    public void setUp() {
        system = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        customer = new Customer();
        customer.setAccountNumber("C001");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        
        Rental rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-03"));
        
        Rental rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(LocalDate.parse("2025-01-01"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-12"));
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute
        double result = system.calculateTotalPrice(customer, "2025-01-20");
        
        // Verify
        assertEquals(13.00, result, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        customer = new Customer();
        customer.setAccountNumber("C002");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T003");
        
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDate.parse("2025-01-01"));
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        rental.setReturnDate(LocalDate.parse("2025-01-12"));
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Execute
        double result = system.calculateTotalPrice(customer, "2025-01-20");
        
        // Verify
        assertEquals(14.50, result, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        customer = new Customer();
        customer.setAccountNumber("C003");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T004");
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T005");
        
        Rental rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-09"));
        
        Rental rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(LocalDate.parse("2025-01-10"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-18"));
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute
        double result = system.calculateTotalPrice(customer, "2025-01-20");
        
        // Verify
        assertEquals(19.50, result, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        customer = new Customer();
        customer.setAccountNumber("C004");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T006");
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T007");
        
        Rental rental1 = new Rental();
        rental1.setTape(tape1);
        rental1.setCustomer(customer);
        rental1.setRentalDate(LocalDate.parse("2025-01-01"));
        rental1.setDueDate(LocalDate.parse("2025-01-05"));
        rental1.setReturnDate(LocalDate.parse("2025-01-07"));
        
        Rental rental2 = new Rental();
        rental2.setTape(tape2);
        rental2.setCustomer(customer);
        rental2.setRentalDate(LocalDate.parse("2025-01-10"));
        rental2.setDueDate(LocalDate.parse("2025-01-15"));
        rental2.setReturnDate(LocalDate.parse("2025-01-14"));
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute
        double result = system.calculateTotalPrice(customer, "2025-01-20");
        
        // Verify
        assertEquals(11.00, result, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        customer = new Customer();
        customer.setAccountNumber("C006");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T008");
        
        Rental rental = new Rental();
        rental.setTape(tape);
        rental.setCustomer(customer);
        rental.setRentalDate(LocalDate.parse("2025-01-01"));
        rental.setDueDate(LocalDate.parse("2025-01-05"));
        // return_date remains null (unreturned)
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental);
        customer.setRentals(rentals);
        
        // Execute
        double result = system.calculateTotalPrice(customer, "2025-01-10");
        
        // Verify
        assertEquals(11.50, result, 0.001);
    }
}