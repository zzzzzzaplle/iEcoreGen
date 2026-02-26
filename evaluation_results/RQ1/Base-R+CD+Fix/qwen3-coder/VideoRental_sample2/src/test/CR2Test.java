import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CR2Test {
    private SimpleDateFormat dateFormat;
    private Map<String, Customer> customers;
    private Map<String, Tape> tapes;
    private List<VideoRental> allRentals;
    
    @Before
    public void setUp() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        customers = new HashMap<>();
        tapes = new HashMap<>();
        allRentals = new ArrayList<>();
    }
    
    // Helper method to create date from string
    private Date createDate(String dateStr) {
        try {
            return dateFormat.parse(dateStr + " 00:00:00");
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format: " + dateStr, e);
        }
    }
    
    // Helper method to check tape availability based on the computational requirement
    private boolean isTapeAvailable(String tapeId, Date currentDate) {
        for (VideoRental rental : allRentals) {
            if (rental.getTape().getId().equals(tapeId)) {
                // Tape is unavailable if it belongs to any active rental whose return date is null
                // or whose return date is after the given date
                if (rental.getReturnDate() == null) {
                    return false;
                }
                if (rental.getReturnDate().after(currentDate)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Test
    public void testCase1_TapeIsAvailable() throws Exception {
        // Setup: Create Tape with id="T001", No active rentals for T001
        Tape tape = new Tape();
        tape.setId("T001");
        tapes.put("T001", tape);
        
        Date currentDate = createDate("2025-01-01");
        
        // Test the availability
        boolean result = isTapeAvailable("T001", currentDate);
        
        // Verify expected output: True
        assertTrue("Tape T001 should be available when no rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() throws Exception {
        // Setup: Create Tape with id="T002", Create Customer C001
        Tape tape = new Tape();
        tape.setId("T002");
        tapes.put("T002", tape);
        
        Customer customer = new Customer();
        customer.setId("C001");
        customers.put("C001", customer);
        
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(createDate("2025-01-05"));
        rental.setReturnDate(null); // unreturned
        
        customer.getRentals().add(rental);
        allRentals.add(rental);
        
        Date currentDate = createDate("2025-01-01");
        
        // Test the availability
        boolean result = isTapeAvailable("T002", currentDate);
        
        // Verify expected output: False
        assertFalse("Tape T002 should be unavailable when rented out and not returned", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() throws Exception {
        // Setup: Create Tape with id="T003", Create Customer C002
        Tape tape = new Tape();
        tape.setId("T003");
        tapes.put("T003", tape);
        
        Customer customer = new Customer();
        customer.setId("C002");
        customers.put("C002", customer);
        
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(createDate("2024-12-30"));
        rental.setReturnDate(createDate("2024-12-31")); // returned
        
        customer.getRentals().add(rental);
        allRentals.add(rental);
        
        Date currentDate = createDate("2025-01-01");
        
        // Test the availability
        boolean result = isTapeAvailable("T003", currentDate);
        
        // Verify expected output: True
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() throws Exception {
        // Setup: Create Tape with id="T004", Create Customer C003
        Tape tape = new Tape();
        tape.setId("T004");
        tapes.put("T004", tape);
        
        Customer customer = new Customer();
        customer.setId("C003");
        customers.put("C003", customer);
        
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        VideoRental rental = new VideoRental();
        rental.setTape(tape);
        rental.setDueDate(createDate("2025-01-01"));
        rental.setReturnDate(null); // unreturned
        
        customer.getRentals().add(rental);
        allRentals.add(rental);
        
        Date currentDate = createDate("2025-01-10");
        
        // Test the availability
        boolean result = isTapeAvailable("T004", currentDate);
        
        // Verify expected output: False
        assertFalse("Tape T004 should be unavailable when has overdue rental", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() throws Exception {
        // Setup: Create Tape with id="T005", Create Customer C004, C005
        Tape tape = new Tape();
        tape.setId("T005");
        tapes.put("T005", tape);
        
        Customer customer1 = new Customer();
        customer1.setId("C004");
        customers.put("C004", customer1);
        
        Customer customer2 = new Customer();
        customer2.setId("C005");
        customers.put("C005", customer2);
        
        // First rental: C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01"
        VideoRental rental1 = new VideoRental();
        rental1.setTape(tape);
        rental1.setDueDate(createDate("2025-01-05"));
        rental1.setReturnDate(createDate("2025-01-01")); // returned
        
        customer1.getRentals().add(rental1);
        allRentals.add(rental1);
        
        Date currentDate = createDate("2025-01-10");
        
        // Test first creation: True
        boolean result1 = isTapeAvailable("T005", currentDate);
        assertTrue("Tape T005 should be available after first rental was returned", result1);
        
        // Second rental: C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null
        VideoRental rental2 = new VideoRental();
        rental2.setTape(tape);
        rental2.setDueDate(createDate("2025-01-15"));
        rental2.setReturnDate(null); // unreturned
        
        customer2.getRentals().add(rental2);
        allRentals.add(rental2);
        
        // Test second creation: False
        boolean result2 = isTapeAvailable("T005", currentDate);
        assertFalse("Tape T005 should be unavailable when currently rented out", result2);
    }
}