import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    private SavingAccount savingAccount;
    
    @Before
    public void setUp() {
        customer = new Customer();
        savingAccount = new SavingAccount();
        customer.getAccounts().add(savingAccount);
    }
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // Setup: Customer "Alie" with address "0812 Center St" holds a savings account "SAV007"
        customer.setName("Alie");
        customer.setAddress("0812 Center St");
        savingAccount.setId("SAV007");
        
        // Savings account "SAV007" balance $10 000, interest rate 3.65%
        savingAccount.setBalance(10000.0);
        savingAccount.setInterestRate(0.0365);
        
        // Action: Trigger daily-interest calculation
        double interest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, interest, 0.001);
        
        // New balance should be $10 001.01
        assertEquals(10001.01, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // Setup: Customer "Poe" with address "0814 Center St" holds a savings account "SAV008"
        customer.setName("Poe");
        customer.setAddress("0814 Center St");
        savingAccount.setId("SAV008");
        
        // Savings account "SAV008" balance $0, interest rate 5%
        savingAccount.setBalance(0.0);
        savingAccount.setInterestRate(0.05);
        
        // Action: Trigger daily-interest calculation
        double interest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, interest, 0.001);
        
        // Balance should remain $0
        assertEquals(0.00, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // Setup: Customer "Paul" with address "0815 Center St" holds a savings account "SAV009"
        customer.setName("Paul");
        customer.setAddress("0815 Center St");
        savingAccount.setId("SAV009");
        
        // Savings account "SAV009" balance $0.01, interest rate 2%
        savingAccount.setBalance(0.01);
        savingAccount.setInterestRate(0.02);
        
        // Action: Trigger daily-interest calculation
        double interest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00 (rounds down)
        assertEquals(0.00, interest, 0.001);
        
        // Balance should remain $0.01
        assertEquals(0.01, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // Setup: Customer "Peter" with address "0816 Center St" holds a savings account "SAV010"
        customer.setName("Peter");
        customer.setAddress("0816 Center St");
        savingAccount.setId("SAV010");
        
        // Savings account "SAV010" balance $1 000 000, interest rate 2%
        savingAccount.setBalance(1000000.0);
        savingAccount.setInterestRate(0.02);
        
        // Action: Trigger daily-interest calculation
        double interest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, interest, 0.001);
        
        // New balance should be $1 000 055.56
        assertEquals(1000055.56, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // Setup: Customer "Beli" with address "0819 Center St" holds a savings account "SAV011"
        customer.setName("Beli");
        customer.setAddress("0819 Center St");
        savingAccount.setId("SAV011");
        
        // Savings account "SAV011" balance $1 234.56, interest rate 2.75%
        savingAccount.setBalance(1234.56);
        savingAccount.setInterestRate(0.0275);
        
        // Action: Trigger daily-interest calculation
        double interest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, interest, 0.001);
        
        // New balance should be $1 234.65
        assertEquals(1234.65, savingAccount.getBalance(), 0.001);
    }
}