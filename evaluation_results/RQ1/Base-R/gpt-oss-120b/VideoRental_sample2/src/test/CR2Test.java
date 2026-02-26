import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR2Test {
    
    private RentalStore rentalStore;
    private VideoTape tapeT001;
    private VideoTape tapeT002;
    private VideoTape tapeT003;
    private VideoTape tapeT004;
    private VideoTape tapeT005;
    private Customer customerC001;
    private Customer customerC002;
    private Customer customerC003;
    private Customer customerC004;
    private Customer customerC005;
    
    @Before
    public void setUp() {
        rentalStore = new RentalStore();
        
        // Create all tapes
        tapeT001 = new VideoTape();
        tapeT001.setTapeId("T001");
        
        tapeT002 = new VideoTape();
        tapeT002.setTapeId("T002");
        
        tapeT003 = new VideoTape();
        tapeT003.setTapeId("T003");
        
        tapeT004 = new VideoTape();
        tapeT004.setTapeId("T004");
        
        tapeT005 = new VideoTape();
        tapeT005.setTapeId("T005");
        
        // Add tapes to inventory
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tapeT001);
        inventory.add(tapeT002);
        inventory.add(tapeT003);
        inventory.add(tapeT004);
        inventory.add(tapeT005);
        rentalStore.setInventory(inventory);
        
        // Create customers
        customerC001 = new Customer();
        customerC001.setAccountNumber("C001");
        
        customerC002 = new Customer();
        customerC002.setAccountNumber("C002");
        
        customerC003 = new Customer();
        customerC003.setAccountNumber("C003");
        
        customerC004 = new Customer();
        customerC004.setAccountNumber("C004");
        
        customerC005 = new Customer();
        customerC005.setAccountNumber("C005");
        
        // Add customers to store
        List<Customer> customers = new ArrayList<>();
        customers.add(customerC001);
        customers.add(customerC002);
        customers.add(customerC003);
        customers.add(customerC004);
        customers.add(customerC005);
        rentalStore.setCustomers(customers);
    }
    
    @Test
    public void testCase1_TapeIsAvailable() {
        // Setup: Create Tape with id="T001", No active rentals for T001
        List<Rental> rentals = new ArrayList<>();
        rentalStore.setRentals(rentals);
        
        // Input: tape_id="T001", current_date="2025-01-01"
        boolean result = rentalStore.isTapeAvailable("T001", "2025-01-01");
        
        // Expected Output: True
        assertTrue("Tape T001 should be available when no active rentals exist", result);
    }
    
    @Test
    public void testCase2_TapeIsRentedOut() {
        // Setup: Create Tape with id="T002". Create Customer C001.
        // C001 rents T002 with rental date="2024-12-28", due_date="2025-01-05", return_date=null (unreturned)
        List<Rental> rentals = new ArrayList<>();
        
        Rental rental = new Rental();
        rental.setCustomer(customerC001);
        rental.setTape(tapeT002);
        rental.setRentalDate(LocalDate.parse("2024-12-28", DateTimeFormatter.ISO_LOCAL_DATE));
        rental.setDueDate(LocalDate.parse("2025-01-05", DateTimeFormatter.ISO_LOCAL_DATE));
        rental.setReturnDate(null);
        
        rentals.add(rental);
        customerC001.setRentals(new ArrayList<>());
        customerC001.getRentals().add(rental);
        rentalStore.setRentals(rentals);
        
        // Input: tape_id="T002", current_date="2025-01-01"
        boolean result = rentalStore.isTapeAvailable("T002", "2025-01-01");
        
        // Expected Output: False
        assertFalse("Tape T002 should be unavailable when actively rented out", result);
    }
    
    @Test
    public void testCase3_TapeWasRentedButReturned() {
        // Setup: Create Tape with id="T003". Create Customer C002.
        // C002 rents T003 with rental date="2024-12-25", due_date="2024-12-30", return_date="2024-12-31"
        List<Rental> rentals = new ArrayList<>();
        
        Rental rental = new Rental();
        rental.setCustomer(customerC002);
        rental.setTape(tapeT003);
        rental.setRentalDate(LocalDate.parse("2024-12-25", DateTimeFormatter.ISO_LOCAL_DATE));
        rental.setDueDate(LocalDate.parse("2024-12-30", DateTimeFormatter.ISO_LOCAL_DATE));
        rental.setReturnDate(LocalDate.parse("2024-12-31", DateTimeFormatter.ISO_LOCAL_DATE));
        
        rentals.add(rental);
        customerC002.setRentals(new ArrayList<>());
        customerC002.getRentals().add(rental);
        rentalStore.setRentals(rentals);
        
        // Input: tape_id="T003", current_date="2025-01-01"
        boolean result = rentalStore.isTapeAvailable("T003", "2025-01-01");
        
        // Expected Output: True
        assertTrue("Tape T003 should be available when previously rented but returned", result);
    }
    
    @Test
    public void testCase4_TapeExistsButHasOverdueRental() {
        // Setup: Create Tape with id="T004". Create Customer C003.
        // C003 rents T004 with rental date="2024-12-28", due_date="2025-01-01", return_date=null (unreturned)
        List<Rental> rentals = new ArrayList<>();
        
        Rental rental = new Rental();
        rental.setCustomer(customerC003);
        rental.setTape(tapeT004);
        rental.setRentalDate(LocalDate.parse("2024-12-28", DateTimeFormatter.ISO_LOCAL_DATE));
        rental.setDueDate(LocalDate.parse("2025-01-01", DateTimeFormatter.ISO_LOCAL_DATE));
        rental.setReturnDate(null);
        
        rentals.add(rental);
        customerC003.setRentals(new ArrayList<>());
        customerC003.getRentals().add(rental);
        rentalStore.setRentals(rentals);
        
        // Input: tape_id="T004", current_date="2025-01-10"
        boolean result = rentalStore.isTapeAvailable("T004", "2025-01-10");
        
        // Expected Output: False
        assertFalse("Tape T004 should be unavailable when actively rented out and overdue", result);
    }
    
    @Test
    public void testCase5_TapeWasRentedButReturnedByMultipleCustomers() {
        // Setup: Create Tape with id="T005". Create Customer C004, C005.
        // C004 rents T005 with rental date="2025-01-01", due_date="2025-01-05", return_date="2025-01-01" → first rental
        // C005 rents T005 with rental date="2025-01-06", due_date="2025-01-15", return_date=null → second rental
        
        List<Rental> rentals = new ArrayList<>();
        
        // First rental: C004 rents T005 and returns immediately
        Rental rental1 = new Rental();
        rental1.setCustomer(customerC004);
        rental1.setTape(tapeT005);
        rental1.setRentalDate(LocalDate.parse("2025-01-01", DateTimeFormatter.ISO_LOCAL_DATE));
        rental1.setDueDate(LocalDate.parse("2025-01-05", DateTimeFormatter.ISO_LOCAL_DATE));
        rental1.setReturnDate(LocalDate.parse("2025-01-01", DateTimeFormatter.ISO_LOCAL_DATE));
        
        // Second rental: C005 rents T005 and hasn't returned
        Rental rental2 = new Rental();
        rental2.setCustomer(customerC005);
        rental2.setTape(tapeT005);
        rental2.setRentalDate(LocalDate.parse("2025-01-06", DateTimeFormatter.ISO_LOCAL_DATE));
        rental2.setDueDate(LocalDate.parse("2025-01-15", DateTimeFormatter.ISO_LOCAL_DATE));
        rental2.setReturnDate(null);
        
        rentals.add(rental1);
        rentals.add(rental2);
        
        customerC004.setRentals(new ArrayList<>());
        customerC004.getRentals().add(rental1);
        
        customerC005.setRentals(new ArrayList<>());
        customerC005.getRentals().add(rental2);
        
        rentalStore.setRentals(rentals);
        
        // Input: tape_id="T005", current_date="2025-01-10"
        boolean result = rentalStore.isTapeAvailable("T005", "2025-01-10");
        
        // Expected Output: False (since second rental is still active)
        assertFalse("Tape T005 should be unavailable when actively rented out by second customer", result);
    }
}