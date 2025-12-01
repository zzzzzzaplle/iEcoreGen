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
        boolean result = customer.addSavingAccount("SAV001", 0.02);
        
        // Expected Output: True
        assertTrue("Adding savings account should return true", result);
        
        // Verify account was actually added
        Account account = customer.findAccountById("SAV001");
        assertNotNull("Account should be found after addition", account);
        assertTrue("Account should be a SavingAccount", account instanceof SavingAccount);
        SavingAccount savingAccount = (SavingAccount) account;
        assertEquals("Interest rate should be 2%", 0.02, savingAccount.getInterestRate(), 0.0001);
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
        assertFalse("Adding duplicate account should return false", result);
        
        // Customer still holds exactly one account "INV001"
        assertEquals("Account count should remain the same", initialAccountCount, customer.getAccounts().size());
        assertNotNull("Original account should still exist", customer.findAccountById("INV001"));
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 0.0365);
        
        Account account = customer.findAccountById("SAV002");
        assertNotNull("Account should exist before removal", account);
        account.setBalance(0.0); // Ensure zero balance
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Removing zero-balance account should return true", result);
        
        // Customer now has no account "SAV002"
        assertNull("Account should not exist after removal", customer.findAccountById("SAV002"));
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 0.0365);
        
        Account account = customer.findAccountById("SAV003");
        assertNotNull("Account should exist before removal attempt", account);
        account.setBalance(250.0); // Set balance to $250
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Removing account with balance should return false", result);
        
        // Customer still holds account "SAV003" with balance $250
        Account remainingAccount = customer.findAccountById("SAV003");
        assertNotNull("Account should still exist after failed removal", remainingAccount);
        assertEquals("Balance should remain $250", 250.0, remainingAccount.getBalance(), 0.0001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Add first investment account with zero balance and no transactions
        customer.addInvestmentAccount("INV001");
        InvestmentAccount inv001 = (InvestmentAccount) customer.findAccountById("INV001");
        inv001.setBalance(0.0);
        
        // Add second investment account with balance and stock transaction
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv002 = (InvestmentAccount) customer.findAccountById("INV002");
        inv002.setBalance(100.0);
        inv002.buyStock("ABS", 10, 5.0); // Add stock transaction
        
        int initialAccountCount = customer.getAccounts().size();
        
        // Action: Attempt to remove account "INV001"
        boolean resultInv001 = customer.removeInvestmentAccount("INV001");
        
        // Expected Output for INV001: True
        assertTrue("Removing zero-balance investment account with no transactions should return true", resultInv001);
        
        // Action: Attempt to remove account "INV002"
        boolean resultInv002 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output for INV002: False
        assertFalse("Removing investment account with transactions should return false", resultInv002);
        
        // Verify INV001 was removed and INV002 remains
        assertNull("INV001 should not exist after removal", customer.findAccountById("INV001"));
        assertNotNull("INV002 should still exist", customer.findAccountById("INV002"));
        assertEquals("Should have one fewer account", initialAccountCount - 1, customer.getAccounts().size());
    }
}