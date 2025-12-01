import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.List;

public class CR1Test {
    
    private Customer customer;
    
    @Before
    public void setUp() {
        customer = new Customer();
    }
    
    @Test
    public void testCase1_AddNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        boolean result = customer.addSavingAccount("SAV001", 2.0);
        
        // Expected Output: True
        assertTrue("Should successfully add savings account", result);
        
        // Verify account was added
        Account addedAccount = customer.findAccountById("SAV001");
        assertNotNull("Account should exist after addition", addedAccount);
        assertTrue("Added account should be a SavingAccount", addedAccount instanceof SavingAccount);
        assertEquals("Account ID should match", "SAV001", addedAccount.getId());
    }
    
    @Test
    public void testCase2_AddAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        
        // Verify initial setup
        assertEquals("Customer should have exactly 1 account initially", 1, customer.getAccounts().size());
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False
        assertFalse("Should not allow duplicate account ID", result);
        
        // Customer still holds exactly one account "INV001"
        assertEquals("Customer should still have exactly 1 account", 1, customer.getAccounts().size());
        Account existingAccount = customer.findAccountById("INV001");
        assertNotNull("Original account should still exist", existingAccount);
        assertEquals("Account ID should be INV001", "INV001", existingAccount.getId());
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 3.65);
        
        // Verify initial setup
        Account savingsAccount = customer.findAccountById("SAV002");
        assertNotNull("Account should exist before removal", savingsAccount);
        assertEquals("Account balance should be $0", 0.0, savingsAccount.getBalance(), 0.001);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Should successfully remove zero-balance savings account", result);
        
        // Customer now has no account "SAV002"
        Account removedAccount = customer.findAccountById("SAV002");
        assertNull("Account should not exist after removal", removedAccount);
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 3.65);
        
        // Set balance to $250
        Account savingsAccount = customer.findAccountById("SAV003");
        savingsAccount.setBalance(250.0);
        
        // Verify initial setup
        assertEquals("Account balance should be $250", 250.0, savingsAccount.getBalance(), 0.001);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Should not remove account with positive balance", result);
        
        // Customer still holds account "SAV003" with balance $250
        Account remainingAccount = customer.findAccountById("SAV003");
        assertNotNull("Account should still exist after failed removal", remainingAccount);
        assertEquals("Account balance should remain $250", 250.0, remainingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction (10 shares of stock "ABS" at price $5)
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Add first investment account with no transactions
        customer.addInvestmentAccount("INV001");
        
        // Add second investment account with balance and transaction
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv2 = (InvestmentAccount) customer.findAccountById("INV002");
        inv2.setBalance(100.0);
        inv2.buyStock("ABS", 10, 5.0);
        
        // Verify initial setup
        assertEquals("Customer should have 2 accounts initially", 2, customer.getAccounts().size());
        assertNotNull("INV001 should exist", customer.findAccountById("INV001"));
        assertNotNull("INV002 should exist", customer.findAccountById("INV002"));
        
        // Action: Attempt to remove account "INV001"
        boolean result1 = customer.removeInvestmentAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Should remove investment account with no balance and no transactions", result1);
        
        // Action: Attempt to remove account "INV002"
        boolean result2 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Should not remove investment account with balance and transactions", result2);
        
        // Verify final state
        assertNull("INV001 should be removed", customer.findAccountById("INV001"));
        assertNotNull("INV002 should still exist", customer.findAccountById("INV002"));
        assertEquals("Customer should have 1 account remaining", 1, customer.getAccounts().size());
    }
}