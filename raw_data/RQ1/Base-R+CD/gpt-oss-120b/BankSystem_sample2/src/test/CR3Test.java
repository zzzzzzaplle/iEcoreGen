import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR3Test {
    
    private Customer customer;
    private SavingAccount savingAccount;
    
    @Before
    public void setUp() {
        // Common setup can be done here if needed
    }
    
    @Test
    public void testCase1_standardBalanceAndRate() {
        // SetUp: Customer "Alie" (address "0812 Center St") holds a savings account "SAV007"
        // "SAV007" balance $10 000, interest rate 3.65 %.
        customer = new Customer("Alie", "0812 Center St");
        customer.addSavingAccount("SAV007", 0.0365);
        savingAccount = (SavingAccount) customer.findAccountById("SAV007");
        savingAccount.deposit(10000.00); // Set balance to $10,000
        
        // Action: Trigger daily-interest calculation.
        double interest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 1.01
        assertEquals(1.01, interest, 0.001);
        assertEquals(10001.01, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase2_zeroBalance() {
        // SetUp: Customer "Poe" (address "0814 Center St") holds a savings account "SAV008"
        // Savings account "SAV008" balance $0, interest rate 5 %.
        customer = new Customer("Poe", "0814 Center St");
        customer.addSavingAccount("SAV008", 0.05);
        savingAccount = (SavingAccount) customer.findAccountById("SAV008");
        // Balance is already $0 by default
        
        // Action: Trigger daily-interest calculation.
        double interest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, interest, 0.001);
        assertEquals(0.00, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase3_verySmallBalance() {
        // SetUp: Customer "Paul" (address "0815 Center St") holds a savings account "SAV009"
        // Savings account "SAV009" balance $0.01, interest rate 2 %.
        customer = new Customer("Paul", "0815 Center St");
        customer.addSavingAccount("SAV009", 0.02);
        savingAccount = (SavingAccount) customer.findAccountById("SAV009");
        savingAccount.deposit(0.01); // Set balance to $0.01
        
        // Action: Trigger daily-interest calculation.
        double interest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.00
        assertEquals(0.00, interest, 0.001);
        assertEquals(0.01, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase4_largeBalance() {
        // SetUp: Customer "Peter" (address "0816 Center St") holds a savings account "SAV010"
        // Savings account "SAV010" balance $1 000 000, interest rate 2 %.
        customer = new Customer("Peter", "0816 Center St");
        customer.addSavingAccount("SAV010", 0.02);
        savingAccount = (SavingAccount) customer.findAccountById("SAV010");
        savingAccount.deposit(1000000.00); // Set balance to $1,000,000
        
        // Action: Trigger daily-interest calculation.
        double interest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 55.56
        assertEquals(55.56, interest, 0.001);
        assertEquals(1000055.56, savingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_highPrecisionRate() {
        // SetUp: Customer "Beli" (address "0819 Center St") holds a savings account "SAV011"
        // Savings account "SAV011" balance $1 234.56, interest rate 2.75 %.
        customer = new Customer("Beli", "0819 Center St");
        customer.addSavingAccount("SAV011", 0.0275);
        savingAccount = (SavingAccount) customer.findAccountById("SAV011");
        savingAccount.deposit(1234.56); // Set balance to $1,234.56
        
        // Action: Trigger daily-interest calculation.
        double interest = savingAccount.calculateDailyInterest();
        
        // Expected Output: 0.09
        assertEquals(0.09, interest, 0.001);
        assertEquals(1234.65, savingAccount.getBalance(), 0.001);
    }
}