import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.math.BigDecimal;
import java.util.Map;

public class CR1Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        // Reset customer before each test
        customer = null;
    }
    
    @Test
    public void testCase1_AddNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        customer = new Customer("John Doe", "123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        SavingsAccount savingsAccount = new SavingsAccount("SAV001", new BigDecimal("0.02"));
        boolean result = customer.addAccount(savingsAccount);
        
        // Expected Output: True
        assertTrue("Adding new savings account should return true", result);
        
        // Verify account was added
        Map<String, Account> accounts = customer.getAccounts();
        assertEquals("Customer should have exactly 1 account", 1, accounts.size());
        assertTrue("Customer should contain SAV001 account", accounts.containsKey("SAV001"));
    }
    
    @Test
    public void testCase2_AddAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer = new Customer("Mia", "123 Main St");
        InvestmentAccount existingAccount = new InvestmentAccount("INV001");
        customer.addAccount(existingAccount);
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        InvestmentAccount duplicateAccount = new InvestmentAccount("INV001");
        boolean result = customer.addAccount(duplicateAccount);
        
        // Expected Output: False
        assertFalse("Adding account with duplicate ID should return false", result);
        
        // Expected Output: Customer still holds exactly one account "INV001"
        Map<String, Account> accounts = customer.getAccounts();
        assertEquals("Customer should still have exactly 1 account", 1, accounts.size());
        assertTrue("Customer should still contain INV001 account", accounts.containsKey("INV001"));
        assertSame("The account should be the original one", existingAccount, accounts.get("INV001"));
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer = new Customer("Doli", "123 Main St");
        SavingsAccount savingsAccount = new SavingsAccount("SAV002", new BigDecimal("0.0365"));
        savingsAccount.setBalance(BigDecimal.ZERO); // Explicitly set balance to 0
        customer.addAccount(savingsAccount);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Removing zero-balance account should return true", result);
        
        // Expected Output: Customer now has no account "SAV002"
        Map<String, Account> accounts = customer.getAccounts();
        assertEquals("Customer should have no accounts after removal", 0, accounts.size());
        assertFalse("Customer should not contain SAV002 account", accounts.containsKey("SAV002"));
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer = new Customer("Liam", "123 Main St");
        SavingsAccount savingsAccount = new SavingsAccount("SAV003", new BigDecimal("0.0365"));
        savingsAccount.setBalance(new BigDecimal("250")); // Set balance to $250
        customer.addAccount(savingsAccount);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Removing account with balance should return false", result);
        
        // Expected Output: Customer still holds account "SAV003" with balance $250
        Map<String, Account> accounts = customer.getAccounts();
        assertEquals("Customer should still have 1 account", 1, accounts.size());
        assertTrue("Customer should still contain SAV003 account", accounts.containsKey("SAV003"));
        
        Account remainingAccount = accounts.get("SAV003");
        assertEquals("Account balance should remain $250", 
                     new BigDecimal("250"), remainingAccount.getBalance());
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer = new Customer("Aliza", "123 Main St");
        
        // Create INV001 - no stock transactions, zero balance
        InvestmentAccount inv001 = new InvestmentAccount("INV001");
        inv001.setBalance(BigDecimal.ZERO);
        customer.addAccount(inv001);
        
        // Create INV002 - with stock transaction, balance $100
        InvestmentAccount inv002 = new InvestmentAccount("INV002");
        inv002.setBalance(new BigDecimal("100"));
        
        // Add stock transaction to INV002 (10 shares of stock "ABS" at price $5)
        StockTransaction transaction = new StockTransaction("ABS", 10, new BigDecimal("5"));
        inv002.getTransactions().add(transaction);
        
        customer.addAccount(inv002);
        
        // Action: Attempt to remove account "INV001"
        boolean resultInv001 = customer.removeAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Removing INV001 with no transactions and zero balance should return true", resultInv001);
        
        // Action: Attempt to remove account "INV002"
        boolean resultInv002 = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Removing INV002 with stock transactions should return false", resultInv002);
        
        // Verify final state
        Map<String, Account> accounts = customer.getAccounts();
        assertEquals("Customer should have 1 account remaining", 1, accounts.size());
        assertFalse("INV001 should be removed", accounts.containsKey("INV001"));
        assertTrue("INV002 should still exist", accounts.containsKey("INV002"));
    }
}