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
        assertTrue("Should successfully add savings account", result);
        assertNotNull("Account should exist after creation", customer.findAccountById("SAV001"));
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
        assertFalse("Should not allow duplicate account ID", result);
        
        // Expected: Customer still holds exactly one account "INV001"
        assertEquals("Account count should remain unchanged", initialAccountCount, customer.getAccounts().size());
        assertNotNull("Original account should still exist", customer.findAccountById("INV001"));
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 3.65);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Should successfully remove zero-balance savings account", result);
        
        // Expected: Customer now has no account "SAV002"
        assertNull("Account should no longer exist", customer.findAccountById("SAV002"));
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
        
        int initialAccountCount = customer.getAccounts().size();
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Should not remove account with positive balance", result);
        
        // Expected: Customer still holds account "SAV003" with balance $250
        assertEquals("Account count should remain unchanged", initialAccountCount, customer.getAccounts().size());
        Account remainingAccount = customer.findAccountById("SAV003");
        assertNotNull("Account should still exist", remainingAccount);
        assertEquals("Balance should remain unchanged", 250.0, remainingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts:
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 (no transactions, zero balance)
        customer.addInvestmentAccount("INV001");
        
        // Create INV002 with balance $100 and stock transaction
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv002 = (InvestmentAccount) customer.findAccountById("INV002");
        inv002.setBalance(100.0);
        
        // Add stock transaction to INV002
        StockTransaction transaction = new StockTransaction();
        transaction.setStock("ABS");
        transaction.setQuantity(10);
        transaction.setPrice(5.0);
        transaction.setCommission(5.0);
        inv002.getTransactions().add(transaction);
        
        int initialAccountCount = customer.getAccounts().size();
        
        // Action: Attempt to remove account "INV001"
        boolean resultINV001 = customer.removeInvestmentAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Should successfully remove investment account with no transactions and zero balance", resultINV001);
        assertNull("INV001 should no longer exist", customer.findAccountById("INV001"));
        
        // Action: Attempt to remove account "INV002"
        boolean resultINV002 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Should not remove investment account with transactions and balance", resultINV002);
        assertNotNull("INV002 should still exist", customer.findAccountById("INV002"));
    }
}