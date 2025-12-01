import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private RentalService rentalService;
    private DateTimeFormatter formatter;
    
    @Before
    public void setUp() {
        rentalService = new RentalService();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }
    
    @Test
    public void testCase1_noOverdueFees() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C001");
        customer.setName("Test Customer");
        rentalService.getCustomers().add(customer);
        
        VideoTape tape1 = new VideoTape();
        tape1.setTapeId("T001");
        tape1.setTitle("Movie 1");
        rentalService.getInventory().add(tape1);
        
        VideoTape tape2 = new VideoTape();
        tape2.setTapeId("T002");
        tape2.setTitle("Movie 2");
        rentalService.getInventory().add(tape2);
        
        // Create Rental 1: returned early, no overdue fee
        RentalItem rental1 = new RentalItem();
        rental1.setRentalId("R001");
        rental1.setCustomer(customer);
        rental1.setTape(tape1);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-03", formatter));
        
        // Create Rental 2: returned early, no overdue fee
        RentalItem rental2 = new RentalItem();
        rental2.setRentalId("R002");
        rental2.setCustomer(customer);
        rental2.setTape(tape2);
        rental2.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalService.getRentals().add(rental1);
        rentalService.getRentals().add(rental2);
        
        // Test with current date 2025-01-20
        double result = rentalService.calculateTotalPrice("C001");
        
        // Expected: Rental 1: 2 days base fee ($2) + $0 overdue = $2
        //           Rental 2: 11 days base fee ($11) + $0 overdue = $11
        //           Total = $13
        assertEquals(13.0, result, 0.001);
    }
    
    @Test
    public void testCase2_oneOverdueRental() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C002");
        customer.setName("Test Customer 2");
        rentalService.getCustomers().add(customer);
        
        VideoTape tape3 = new VideoTape();
        tape3.setTapeId("T003");
        tape3.setTitle("Movie 3");
        rentalService.getInventory().add(tape3);
        
        // Create Rental 1: returned late with overdue fee
        RentalItem rental1 = new RentalItem();
        rental1.setRentalId("R003");
        rental1.setCustomer(customer);
        rental1.setTape(tape3);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-12", formatter));
        
        customer.getRentals().add(rental1);
        rentalService.getRentals().add(rental1);
        
        // Test with current date 2025-01-20
        double result = rentalService.calculateTotalPrice("C002");
        
        // Expected: 11 days base fee ($11) + 7 days overdue × $0.5 = $3.50
        //           Total = $14.50
        assertEquals(14.50, result, 0.001);
    }
    
    @Test
    public void testCase3_multipleOverdueRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C003");
        customer.setName("Test Customer 3");
        rentalService.getCustomers().add(customer);
        
        VideoTape tape4 = new VideoTape();
        tape4.setTapeId("T004");
        tape4.setTitle("Movie 4");
        rentalService.getInventory().add(tape4);
        
        VideoTape tape5 = new VideoTape();
        tape5.setTapeId("T005");
        tape5.setTitle("Movie 5");
        rentalService.getInventory().add(tape5);
        
        // Create Rental 1: returned late with overdue fee
        RentalItem rental1 = new RentalItem();
        rental1.setRentalId("R004");
        rental1.setCustomer(customer);
        rental1.setTape(tape4);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-09", formatter));
        
        // Create Rental 2: returned late with overdue fee
        RentalItem rental2 = new RentalItem();
        rental2.setRentalId("R005");
        rental2.setCustomer(customer);
        rental2.setTape(tape5);
        rental2.setRentalDate(LocalDate.parse("2025-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-18", formatter));
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalService.getRentals().add(rental1);
        rentalService.getRentals().add(rental2);
        
        // Test with current date 2025-01-20
        double result = rentalService.calculateTotalPrice("C003");
        
        // Expected: Rental 1: 8 days base fee ($8) + 4 days overdue × $0.5 = $2.00
        //           Rental 2: 8 days base fee ($8) + 3 days overdue × $0.5 = $1.50
        //           Total = $8 + $8 + $2.00 + $1.50 = $19.50
        assertEquals(19.50, result, 0.001);
    }
    
    @Test
    public void testCase4_mixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C004");
        customer.setName("Test Customer 4");
        rentalService.getCustomers().add(customer);
        
        VideoTape tape6 = new VideoTape();
        tape6.setTapeId("T006");
        tape6.setTitle("Movie 6");
        rentalService.getInventory().add(tape6);
        
        VideoTape tape7 = new VideoTape();
        tape7.setTapeId("T007");
        tape7.setTitle("Movie 7");
        rentalService.getInventory().add(tape7);
        
        // Create Rental 1: returned late with overdue fee
        RentalItem rental1 = new RentalItem();
        rental1.setRentalId("R006");
        rental1.setCustomer(customer);
        rental1.setTape(tape6);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(LocalDate.parse("2025-01-07", formatter));
        
        // Create Rental 2: returned on time, no overdue fee
        RentalItem rental2 = new RentalItem();
        rental2.setRentalId("R007");
        rental2.setCustomer(customer);
        rental2.setTape(tape7);
        rental2.setRentalDate(LocalDate.parse("2025-01-10", formatter));
        rental2.setDueDate(LocalDate.parse("2025-01-15", formatter));
        rental2.setReturnDate(LocalDate.parse("2025-01-14", formatter));
        
        customer.getRentals().add(rental1);
        customer.getRentals().add(rental2);
        rentalService.getRentals().add(rental1);
        rentalService.getRentals().add(rental2);
        
        // Test with current date 2025-01-20
        double result = rentalService.calculateTotalPrice("C004");
        
        // Expected: Rental 1: 6 days base fee ($6) + 2 days overdue × $0.5 = $1.00
        //           Rental 2: 4 days base fee ($4) + $0 overdue = $4
        //           Total = $6 + $4 + $1.00 = $11.00
        assertEquals(11.00, result, 0.001);
    }
    
    @Test
    public void testCase5_unreturnedTapeWithCurrentDateOverdue() {
        // Setup
        Customer customer = new Customer();
        customer.setId("C006");
        customer.setName("Test Customer 6");
        rentalService.getCustomers().add(customer);
        
        VideoTape tape8 = new VideoTape();
        tape8.setTapeId("T008");
        tape8.setTitle("Movie 8");
        rentalService.getInventory().add(tape8);
        
        // Create Rental 1: unreturned and overdue
        RentalItem rental1 = new RentalItem();
        rental1.setRentalId("R008");
        rental1.setCustomer(customer);
        rental1.setTape(tape8);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", formatter));
        rental1.setDueDate(LocalDate.parse("2025-01-05", formatter));
        rental1.setReturnDate(null); // Not returned
        
        customer.getRentals().add(rental1);
        rentalService.getRentals().add(rental1);
        
        // Test with current date 2025-01-10
        double result = rentalService.calculateTotalPrice("C006");
        
        // Expected: 9 days base fee ($9) + 5 days overdue × $0.5 = $2.50
        //           Total = $11.50
        assertEquals(11.50, result, 0.001);
    }
}