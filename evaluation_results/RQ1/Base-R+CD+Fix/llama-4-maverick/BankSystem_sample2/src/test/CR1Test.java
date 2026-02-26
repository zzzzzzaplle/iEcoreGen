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
    public void testCase1_addNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        boolean result = customer.addSavingAccount("SAV001", 2.0);
        
        // Expected Output: True
        assertTrue("Adding savings account should return true", result);
        assertNotNull("Account should exist after adding", customer.findAccountById("SAV001"));
        assertEquals("Customer should have exactly 1 account", 1, customer.getAccounts().size());
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False
        assertFalse("Adding duplicate account should return false", result);
        // Customer still holds exactly one account "INV001"
        assertEquals("Customer should still have exactly 1 account", 1, customer.getAccounts().size());
        assertNotNull("Account INV001 should still exist", customer.findAccountById("INV001"));
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 3.65);
        // Ensure balance is 0 (default value)
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Removing zero-balance savings account should return true", result);
        // Customer now has no account "SAV002"
        assertNull("Account SAV002 should not exist after removal", customer.findAccountById("SAV002"));
        assertEquals("Customer should have no accounts", 0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 3.65);
        // Set balance to $250
        customer.findAccountById("SAV003").setBalance(250.0);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Removing account with balance should return false", result);
        // Customer still holds account "SAV003" with balance $250
        assertNotNull("Account SAV003 should still exist", customer.findAccountById("SAV003"));
        assertEquals("Account balance should remain $250", 250.0, customer.findAccountById("SAV003").getBalance(), 0.001);
        assertEquals("Customer should still have 1 account", 1, customer.getAccounts().size());
    }
    
    @Test
    public void testCase5_removeInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 - no transactions, zero balance
        customer.addInvestmentAccount("INV001");
        
        // Create INV002 - with transaction and balance
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv002 = (InvestmentAccount) customer.findAccountById("INV002");
        inv002.setBalance(100.0);
        // Add stock transaction
        inv002.buyStock("ABS", 10, 5.0);
        
        // Action: Attempt to remove account "INV001", "INV002"
        boolean resultINV001 = customer.removeInvestmentAccount("INV001");
        boolean resultINV002 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV001" : True, remove "INV002" : False
        assertTrue("Removing INV001 with no balance/transactions should return true", resultINV001);
        assertFalse("Removing INV002 with transactions should return false", resultINV002);
        
        // Verify INV001 is removed
        assertNull("INV001 should be removed", customer.findAccountById("INV001"));
        // Verify INV002 still exists
        assertNotNull("INV002 should still exist", customer.findAccountById("INV002"));
        assertEquals("Customer should have 1 remaining account", 1, customer.getAccounts().size());
    }
}