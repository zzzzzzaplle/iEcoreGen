import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR4Test {
    
    private VideoRentalSystem rentalSystem;
    
    @Before
    public void setUp() {
        rentalSystem = new VideoRentalSystem();
    }
    
    @Test
    public void testCase1_NoOverdueFees() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C001");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T001");
        tape1.setTitle("Movie 1");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T002");
        tape2.setTitle("Movie 2");
        
        // Add tapes to inventory
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape1);
        inventory.add(tape2);
        rentalSystem.setVideoInventory(inventory);
        
        // Create rental transactions manually
        RentalTransaction rental1 = new RentalTransaction();
        rental1.setCustomer(customer);
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-03");
        rental1.setRentalDays(2);
        
        RentalTransaction rental2 = new RentalTransaction();
        rental2.setCustomer(customer);
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-01");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-12");
        rental2.setRentalDays(11);
        
        List<RentalTransaction> customerRentals = new ArrayList<>();
        customerRentals.add(rental1);
        customerRentals.add(rental2);
        customer.setRentalTransactions(customerRentals);
        
        // Add to system records
        List<RentalTransaction> allTransactions = new ArrayList<>();
        allTransactions.add(rental1);
        allTransactions.add(rental2);
        rentalSystem.setAllRentalTransactions(allTransactions);
        
        // Test
        double totalPrice = rentalSystem.calculateTotalPriceForCustomer(customer, "2025-01-20");
        
        // Verify
        assertEquals(13.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase2_OneOverdueRental() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C002");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T003");
        tape.setTitle("Movie 3");
        
        // Add tape to inventory
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setVideoInventory(inventory);
        
        // Create rental transaction manually
        RentalTransaction rental = new RentalTransaction();
        rental.setCustomer(customer);
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate("2025-01-12");
        rental.setRentalDays(11);
        
        List<RentalTransaction> customerRentals = new ArrayList<>();
        customerRentals.add(rental);
        customer.setRentalTransactions(customerRentals);
        
        // Add to system records
        List<RentalTransaction> allTransactions = new ArrayList<>();
        allTransactions.add(rental);
        rentalSystem.setAllRentalTransactions(allTransactions);
        
        // Test
        double totalPrice = rentalSystem.calculateTotalPriceForCustomer(customer, "2025-01-20");
        
        // Verify
        assertEquals(14.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase3_MultipleOverdueRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C003");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T004");
        tape1.setTitle("Movie 4");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T005");
        tape2.setTitle("Movie 5");
        
        // Add tapes to inventory
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape1);
        inventory.add(tape2);
        rentalSystem.setVideoInventory(inventory);
        
        // Create rental transactions manually
        RentalTransaction rental1 = new RentalTransaction();
        rental1.setCustomer(customer);
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-09");
        rental1.setRentalDays(8);
        
        RentalTransaction rental2 = new RentalTransaction();
        rental2.setCustomer(customer);
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-18");
        rental2.setRentalDays(8);
        
        List<RentalTransaction> customerRentals = new ArrayList<>();
        customerRentals.add(rental1);
        customerRentals.add(rental2);
        customer.setRentalTransactions(customerRentals);
        
        // Add to system records
        List<RentalTransaction> allTransactions = new ArrayList<>();
        allTransactions.add(rental1);
        allTransactions.add(rental2);
        rentalSystem.setAllRentalTransactions(allTransactions);
        
        // Test
        double totalPrice = rentalSystem.calculateTotalPriceForCustomer(customer, "2025-01-20");
        
        // Verify
        assertEquals(19.50, totalPrice, 0.001);
    }
    
    @Test
    public void testCase4_MixedOverdueAndOnTimeRentals() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C004");
        
        VideoTape tape1 = new VideoTape();
        tape1.setBarCodeId("T006");
        tape1.setTitle("Movie 6");
        
        VideoTape tape2 = new VideoTape();
        tape2.setBarCodeId("T007");
        tape2.setTitle("Movie 7");
        
        // Add tapes to inventory
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape1);
        inventory.add(tape2);
        rentalSystem.setVideoInventory(inventory);
        
        // Create rental transactions manually
        RentalTransaction rental1 = new RentalTransaction();
        rental1.setCustomer(customer);
        rental1.setVideoTape(tape1);
        rental1.setRentalDate("2025-01-01");
        rental1.setDueDate("2025-01-05");
        rental1.setReturnDate("2025-01-07");
        rental1.setRentalDays(6);
        
        RentalTransaction rental2 = new RentalTransaction();
        rental2.setCustomer(customer);
        rental2.setVideoTape(tape2);
        rental2.setRentalDate("2025-01-10");
        rental2.setDueDate("2025-01-15");
        rental2.setReturnDate("2025-01-14");
        rental2.setRentalDays(4);
        
        List<RentalTransaction> customerRentals = new ArrayList<>();
        customerRentals.add(rental1);
        customerRentals.add(rental2);
        customer.setRentalTransactions(customerRentals);
        
        // Add to system records
        List<RentalTransaction> allTransactions = new ArrayList<>();
        allTransactions.add(rental1);
        allTransactions.add(rental2);
        rentalSystem.setAllRentalTransactions(allTransactions);
        
        // Test
        double totalPrice = rentalSystem.calculateTotalPriceForCustomer(customer, "2025-01-20");
        
        // Verify
        assertEquals(11.00, totalPrice, 0.001);
    }
    
    @Test
    public void testCase5_UnreturnedTapeWithCurrentDateOverdue() {
        // Setup
        Customer customer = new Customer();
        customer.setAccountNumber("C006");
        
        VideoTape tape = new VideoTape();
        tape.setBarCodeId("T008");
        tape.setTitle("Movie 8");
        
        // Add tape to inventory
        List<VideoTape> inventory = new ArrayList<>();
        inventory.add(tape);
        rentalSystem.setVideoInventory(inventory);
        
        // Create rental transaction manually (return date is null)
        RentalTransaction rental = new RentalTransaction();
        rental.setCustomer(customer);
        rental.setVideoTape(tape);
        rental.setRentalDate("2025-01-01");
        rental.setDueDate("2025-01-05");
        rental.setReturnDate(null); // Unreturned tape
        rental.setRentalDays(9);
        
        List<RentalTransaction> customerRentals = new ArrayList<>();
        customerRentals.add(rental);
        customer.setRentalTransactions(customerRentals);
        
        // Add to system records
        List<RentalTransaction> allTransactions = new ArrayList<>();
        allTransactions.add(rental);
        rentalSystem.setAllRentalTransactions(allTransactions);
        
        // Test
        double totalPrice = rentalSystem.calculateTotalPriceForCustomer(customer, "2025-01-10");
        
        // Verify
        assertEquals(11.50, totalPrice, 0.001);
    }
}