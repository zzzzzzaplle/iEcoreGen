import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

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
        assertTrue("Adding savings account should return true", result);
        assertNotNull("Account should be found by ID", customer.findAccountById("SAV001"));
        assertEquals("Customer should have exactly 1 account", 1, customer.getAccounts().size());
    }
    
    @Test
    public void testCase2_AddAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        
        int initialAccountCount = customer.getAccounts().size();
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False
        assertFalse("Adding duplicate account ID should return false", result);
        
        // Customer still holds exactly one account "INV001"
        assertEquals("Account count should remain unchanged", initialAccountCount, customer.getAccounts().size());
        assertNotNull("Original account should still exist", customer.findAccountById("INV001"));
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 3.65);
        
        // Verify initial setup
        assertNotNull("Account should exist before removal", customer.findAccountById("SAV002"));
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Removing zero-balance savings account should return true", result);
        
        // Customer now has no account "SAV002"
        assertNull("Account should not exist after removal", customer.findAccountById("SAV002"));
        assertEquals("Customer should have no accounts", 0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 3.65);
        
        // Set balance to $250
        customer.findAccountById("SAV003").deposit(250.0);
        assertEquals("Account balance should be $250", 250.0, customer.findAccountById("SAV003").getBalance(), 0.001);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Removing account with balance should return false", result);
        
        // Customer still holds account "SAV003" with balance $250
        assertNotNull("Account should still exist", customer.findAccountById("SAV003"));
        assertEquals("Account balance should remain $250", 250.0, customer.findAccountById("SAV003").getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Add first investment account with no transactions
        customer.addInvestmentAccount("INV001");
        
        // Add second investment account with balance and transaction
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv2 = (InvestmentAccount) customer.findAccountById("INV002");
        inv2.deposit(100.0);
        inv2.buyStock("ABS", 10, 5.0);
        
        // Verify initial setup
        assertEquals("Customer should have 2 accounts initially", 2, customer.getAccounts().size());
        
        // Action: Attempt to remove account "INV001"
        boolean result1 = customer.removeInvestmentAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Removing investment account with no transactions should return true", result1);
        assertNull("INV001 should be removed", customer.findAccountById("INV001"));
        
        // Action: Attempt to remove account "INV002"
        boolean result2 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Removing investment account with transactions should return false", result2);
        assertNotNull("INV002 should still exist", customer.findAccountById("INV002"));
        
        // Final verification
        assertEquals("Customer should have 1 account remaining", 1, customer.getAccounts().size());
    }
}