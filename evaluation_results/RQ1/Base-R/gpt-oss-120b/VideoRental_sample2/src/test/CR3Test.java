import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CR3Test {
    
    private RentalStore rentalStore;
    private Customer customer;
    private VideoTape tape;
    
    @Before
    public void setUp() {
        rentalStore = new RentalStore();
    }
    
    @Test
    public void testCase1_SuccessfulRental() {
        // Setup
        // Create Customer C001 with 5 active rentals
        Customer c001 = new Customer();
        c001.setAccountNumber("C001");
        c001.setName("Customer One");
        c001.setIdCardBarcode("ID001");
        
        List<Rental> c001Rentals = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Rental rental = new Rental();
            rental.setCustomer(c001);
            
            VideoTape tape = new VideoTape();
            tape.setTapeId("T00" + (i + 100));
            tape.setTitle("Movie " + i);
            
            rental.setTape(tape);
            rental.setRentalDate(LocalDate.parse("2025-01-0" + i, DateTimeFormatter.ISO_LOCAL_DATE));
            rental.setDueDate(LocalDate.parse("2025-01-0" + i, DateTimeFormatter.ISO_LOCAL_DATE).plusDays(7));
            rental.setReturnDate(null);
            
            c001Rentals.add(rental);
        }
        c001.setRentals(c001Rentals);
        rentalStore.getCustomers().add(c001);
        
        // Create available Tape T001
        VideoTape t001 = new VideoTape();
        t001.setTapeId("T001");
        t001.setTitle("Available Movie");
        rentalStore.getInventory().add(t001);
        
        // Test
        boolean result = rentalStore.addVideoTapeRental(c001, "T001", "2025-01-01", 7);
        
        // Verify
        assertTrue("Rental should be successful when customer has <20 rentals, no overdue fees, and tape is available", result);
    }
    
    @Test
    public void testCase2_CustomerHas20RentalsMaxLimit() {
        // Setup
        // Create Customer C002 with 20 active rentals
        Customer c002 = new Customer();
        c002.setAccountNumber("C002");
        c002.setName("Customer Two");
        c002.setIdCardBarcode("ID002");
        
        List<Rental> c002Rentals = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Rental rental = new Rental();
            rental.setCustomer(c002);
            
            VideoTape tape = new VideoTape();
            tape.setTapeId("T00" + (i + 200));
            tape.setTitle("Movie " + i);
            
            rental.setTape(tape);
            rental.setRentalDate(LocalDate.parse("2025-01-01", DateTimeFormatter.ISO_LOCAL_DATE));
            rental.setDueDate(LocalDate.parse("2025-01-01", DateTimeFormatter.ISO_LOCAL_DATE).plusDays(7));
            rental.setReturnDate(null);
            
            c002Rentals.add(rental);
        }
        c002.setRentals(c002Rentals);
        rentalStore.getCustomers().add(c002);
        
        // Create available Tape T002
        VideoTape t002 = new VideoTape();
        t002.setTapeId("T002");
        t002.setTitle("Available Movie");
        rentalStore.getInventory().add(t002);
        
        // Test
        boolean result = rentalStore.addVideoTapeRental(c002, "T002", "2025-01-01", 7);
        
        // Verify
        assertFalse("Rental should fail when customer has 20 active rentals (max limit)", result);
    }
    
    @Test
    public void testCase3_CustomerHasOverdueFees() {
        // Setup
        // Create Customer C003 with 1 active rental that is overdue
        Customer c003 = new Customer();
        c003.setAccountNumber("C003");
        c003.setName("Customer Three");
        c003.setIdCardBarcode("ID003");
        
        List<Rental> c003Rentals = new ArrayList<>();
        Rental overdueRental = new Rental();
        overdueRental.setCustomer(c003);
        
        VideoTape overdueTape = new VideoTape();
        overdueTape.setTapeId("T999");
        overdueTape.setTitle("Overdue Movie");
        
        overdueRental.setTape(overdueTape);
        overdueRental.setRentalDate(LocalDate.parse("2024-12-20", DateTimeFormatter.ISO_LOCAL_DATE));
        overdueRental.setDueDate(LocalDate.parse("2025-01-02", DateTimeFormatter.ISO_LOCAL_DATE));
        overdueRental.setReturnDate(null);
        
        c003Rentals.add(overdueRental);
        c003.setRentals(c003Rentals);
        rentalStore.getCustomers().add(c003);
        rentalStore.getRentals().add(overdueRental);
        rentalStore.getInventory().add(overdueTape);
        
        // Create available Tape T003
        VideoTape t003 = new VideoTape();
        t003.setTapeId("T003");
        t003.setTitle("Available Movie");
        rentalStore.getInventory().add(t003);
        
        // Test
        boolean result = rentalStore.addVideoTapeRental(c003, "T003", "2025-01-05", 7);
        
        // Verify
        assertFalse("Rental should fail when customer has overdue fees", result);
    }
    
    @Test
    public void testCase4_TapeIsUnavailable() {
        // Setup
        // Create Customer C004 with 0 rentals
        Customer c004 = new Customer();
        c004.setAccountNumber("C004");
        c004.setName("Customer Four");
        c004.setIdCardBarcode("ID004");
        c004.setRentals(new ArrayList<>());
        rentalStore.getCustomers().add(c004);
        
        // Create Tape T004 with active rental by another customer
        VideoTape t004 = new VideoTape();
        t004.setTapeId("T004");
        t004.setTitle("Rented Movie");
        rentalStore.getInventory().add(t004);
        
        Customer c010 = new Customer();
        c010.setAccountNumber("C010");
        c010.setName("Customer Ten");
        c010.setIdCardBarcode("ID010");
        
        Rental activeRental = new Rental();
        activeRental.setCustomer(c010);
        activeRental.setTape(t004);
        activeRental.setRentalDate(LocalDate.parse("2024-12-28", DateTimeFormatter.ISO_LOCAL_DATE));
        activeRental.setDueDate(LocalDate.parse("2025-01-05", DateTimeFormatter.ISO_LOCAL_DATE));
        activeRental.setReturnDate(null);
        
        c010.setRentals(new ArrayList<>());
        c010.getRentals().add(activeRental);
        
        rentalStore.getCustomers().add(c010);
        rentalStore.getRentals().add(activeRental);
        
        // Test
        boolean result = rentalStore.addVideoTapeRental(c004, "T004", "2025-01-01", 7);
        
        // Verify
        assertFalse("Rental should fail when tape is unavailable", result);
    }
    
    @Test
    public void testCase5_AllConditionsFail() {
        // Setup
        // Create Customer C005 with 20 active rentals and one overdue rental
        Customer c005 = new Customer();
        c005.setAccountNumber("C005");
        c005.setName("Customer Five");
        c005.setIdCardBarcode("ID005");
        
        List<Rental> c005Rentals = new ArrayList<>();
        
        // Add 19 regular rentals
        for (int i = 1; i <= 19; i++) {
            Rental rental = new Rental();
            rental.setCustomer(c005);
            
            VideoTape tape = new VideoTape();
            tape.setTapeId("T00" + (i + 500));
            tape.setTitle("Movie " + i);
            
            rental.setTape(tape);
            rental.setRentalDate(LocalDate.parse("2025-01-01", DateTimeFormatter.ISO_LOCAL_DATE));
            rental.setDueDate(LocalDate.parse("2025-01-08", DateTimeFormatter.ISO_LOCAL_DATE));
            rental.setReturnDate(null);
            
            c005Rentals.add(rental);
            rentalStore.getRentals().add(rental);
            rentalStore.getInventory().add(tape);
        }
        
        // Add 1 overdue rental
        Rental overdueRental = new Rental();
        overdueRental.setCustomer(c005);
        
        VideoTape overdueTape = new VideoTape();
        overdueTape.setTapeId("T999");
        overdueTape.setTitle("Overdue Movie");
        
        overdueRental.setTape(overdueTape);
        overdueRental.setRentalDate(LocalDate.parse("2024-12-25", DateTimeFormatter.ISO_LOCAL_DATE));
        overdueRental.setDueDate(LocalDate.parse("2024-12-31", DateTimeFormatter.ISO_LOCAL_DATE));
        overdueRental.setReturnDate(null);
        
        c005Rentals.add(overdueRental);
        c005.setRentals(c005Rentals);
        rentalStore.getCustomers().add(c005);
        rentalStore.getRentals().add(overdueRental);
        rentalStore.getInventory().add(overdueTape);
        
        // Create Tape T005 with active rental by another customer
        VideoTape t005 = new VideoTape();
        t005.setTapeId("T005");
        t005.setTitle("Rented Movie");
        rentalStore.getInventory().add(t005);
        
        Customer c011 = new Customer();
        c011.setAccountNumber("C011");
        c011.setName("Customer Eleven");
        c011.setIdCardBarcode("ID011");
        
        Rental activeRental = new Rental();
        activeRental.setCustomer(c011);
        activeRental.setTape(t005);
        activeRental.setRentalDate(LocalDate.parse("2024-12-28", DateTimeFormatter.ISO_LOCAL_DATE));
        activeRental.setDueDate(LocalDate.parse("2025-01-05", DateTimeFormatter.ISO_LOCAL_DATE));
        activeRental.setReturnDate(null);
        
        c011.setRentals(new ArrayList<>());
        c011.getRentals().add(activeRental);
        
        rentalStore.getCustomers().add(c011);
        rentalStore.getRentals().add(activeRental);
        
        // Test
        boolean result = rentalStore.addVideoTapeRental(c005, "T005", "2025-01-01", 7);
        
        // Verify
        assertFalse("Rental should fail when all conditions (max rentals, overdue fees, tape availability) fail", result);
    }
}