import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private RentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new RentalSystem();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountId("C001");
        
        Rental rental1 = new Rental();
        rental1.setTapeId("T001");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 3));
        rental1.setRentalDays(2); // Jan 1-3 = 2 days
        rental1.setBaseRentalFee(2.0);
        
        Rental rental2 = new Rental();
        rental2.setTapeId("T002");
        rental2.setRentalDate(LocalDate.of(2025, 1, 1));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 12));
        rental2.setRentalDays(11); // Jan 1-12 = 11 days
        rental2.setBaseRentalFee(11.0);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute
        double totalPrice = rentalSystem.calculateTotalPrice(customer);
        
        // Verify
        assertEquals(13.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountId("C002");
        
        Rental rental1 = new Rental();
        rental1.setTapeId("T003");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 12));
        rental1.setRentalDays(11); // Jan 1-12 = 11 days
        rental1.setBaseRentalFee(11.0);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Execute
        double totalPrice = rentalSystem.calculateTotalPrice(customer);
        
        // Verify
        assertEquals(14.5, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountId("C003");
        
        Rental rental1 = new Rental();
        rental1.setTapeId("T004");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 9));
        rental1.setRentalDays(8); // Jan 1-9 = 8 days
        rental1.setBaseRentalFee(8.0);
        
        Rental rental2 = new Rental();
        rental2.setTapeId("T005");
        rental2.setRentalDate(LocalDate.of(2025, 1, 10));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 18));
        rental2.setRentalDays(8); // Jan 10-18 = 8 days
        rental2.setBaseRentalFee(8.0);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute
        double totalPrice = rentalSystem.calculateTotalPrice(customer);
        
        // Verify
        assertEquals(19.5, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountId("C004");
        
        Rental rental1 = new Rental();
        rental1.setTapeId("T006");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(LocalDate.of(2025, 1, 7));
        rental1.setRentalDays(6); // Jan 1-7 = 6 days
        rental1.setBaseRentalFee(6.0);
        
        Rental rental2 = new Rental();
        rental2.setTapeId("T007");
        rental2.setRentalDate(LocalDate.of(2025, 1, 10));
        rental2.setDueDate(LocalDate.of(2025, 1, 15));
        rental2.setReturnDate(LocalDate.of(2025, 1, 14));
        rental2.setRentalDays(4); // Jan 10-14 = 4 days
        rental2.setBaseRentalFee(4.0);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        rentals.add(rental2);
        customer.setRentals(rentals);
        
        // Execute
        double totalPrice = rentalSystem.calculateTotalPrice(customer);
        
        // Verify
        assertEquals(11.0, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup - Mock current date to be 2025-01-10
        Customer customer = new Customer();
        customer.setAccountId("C006");
        
        Rental rental1 = new Rental();
        rental1.setTapeId("T008");
        rental1.setRentalDate(LocalDate.of(2025, 1, 1));
        rental1.setDueDate(LocalDate.of(2025, 1, 5));
        rental1.setReturnDate(null); // Not returned
        rental1.setRentalDays(9); // Jan 1-10 = 9 days (current date is Jan 10)
        rental1.setBaseRentalFee(9.0);
        
        List<Rental> rentals = new ArrayList<>();
        rentals.add(rental1);
        customer.setRentals(rentals);
        
        // Execute
        double totalPrice = rentalSystem.calculateTotalPrice(customer);
        
        // Verify
        assertEquals(11.5, totalPrice, 0.001);
    }
}