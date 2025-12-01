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
        assertTrue("Adding new savings account should return true", result);
        
        // Verify account was added
        Account account = customer.findAccountById("SAV001");
        assertNotNull("Account should exist after successful addition", account);
        assertTrue("Added account should be a SavingAccount", account instanceof SavingAccount);
        assertEquals("Account ID should match", "SAV001", account.getId());
    }
    
    @Test
    public void testCase2_AddAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        
        // Verify initial account exists
        Account initialAccount = customer.findAccountById("INV001");
        assertNotNull("Initial account should exist", initialAccount);
        assertEquals("Initial setup should have exactly 1 account", 1, customer.getAccounts().size());
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False
        assertFalse("Adding duplicate account ID should return false", result);
        
        // Customer still holds exactly one account "INV001"
        assertEquals("Customer should still have exactly 1 account", 1, customer.getAccounts().size());
        Account remainingAccount = customer.findAccountById("INV001");
        assertNotNull("Account INV001 should still exist", remainingAccount);
        assertTrue("Remaining account should be InvestmentAccount", remainingAccount instanceof InvestmentAccount);
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 3.65);
        
        // Verify initial account exists with zero balance
        Account account = customer.findAccountById("SAV002");
        assertNotNull("Account should exist before removal", account);
        assertEquals("Account balance should be 0", 0.0, account.getBalance(), 0.001);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Removing zero-balance account should return true", result);
        
        // Customer now has no account "SAV002"
        Account removedAccount = customer.findAccountById("SAV002");
        assertNull("Account should not exist after removal", removedAccount);
        assertEquals("Customer should have no accounts", 0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 3.65);
        
        // Set balance to $250
        Account account = customer.findAccountById("SAV003");
        account.setBalance(250.0);
        assertEquals("Account balance should be set to 250", 250.0, account.getBalance(), 0.001);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Removing account with balance should return false", result);
        
        // Customer still holds account "SAV003" with balance $250
        Account remainingAccount = customer.findAccountById("SAV003");
        assertNotNull("Account should still exist", remainingAccount);
        assertEquals("Account balance should remain 250", 250.0, remainingAccount.getBalance(), 0.001);
        assertEquals("Customer should still have 1 account", 1, customer.getAccounts().size());
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Add INV001 (no transactions)
        customer.addInvestmentAccount("INV001");
        
        // Add INV002 with balance and stock transaction
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv002 = (InvestmentAccount) customer.findAccountById("INV002");
        inv002.setBalance(100.0);
        inv002.buyStock("ABS", 10, 5.0); // This creates a stock transaction
        
        // Verify setup
        assertEquals("Customer should have 2 accounts initially", 2, customer.getAccounts().size());
        
        // Action: Attempt to remove account "INV001"
        boolean resultInv001 = customer.removeInvestmentAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Removing INV001 with no transactions should return true", resultInv001);
        
        // Action: Attempt to remove account "INV002"
        boolean resultInv002 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Removing INV002 with stock transactions should return false", resultInv002);
        
        // Verify final state
        assertNull("INV001 should be removed", customer.findAccountById("INV001"));
        assertNotNull("INV002 should still exist", customer.findAccountById("INV002"));
        assertEquals("Customer should have 1 account remaining", 1, customer.getAccounts().size());
    }
}