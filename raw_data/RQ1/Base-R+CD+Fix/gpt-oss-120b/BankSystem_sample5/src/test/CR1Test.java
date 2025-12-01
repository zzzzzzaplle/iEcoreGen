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
        boolean result = customer.addSavingAccount("SAV001", 0.02);
        
        // Expected Output: True
        assertTrue("Should successfully add savings account", result);
        
        // Verify account was added
        Account account = customer.findAccountById("SAV001");
        assertNotNull("Account should exist after addition", account);
        assertTrue("Account should be a SavingAccount", account instanceof SavingAccount);
        assertEquals("Account ID should match", "SAV001", account.getId());
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        
        // Verify initial setup
        assertEquals("Should have exactly one account initially", 1, customer.getAccounts().size());
        assertNotNull("INV001 account should exist", customer.findAccountById("INV001"));
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False
        assertFalse("Should fail to add duplicate account ID", result);
        
        // Customer still holds exactly one account "INV001"
        assertEquals("Should still have exactly one account", 1, customer.getAccounts().size());
        assertNotNull("INV001 account should still exist", customer.findAccountById("INV001"));
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 0.0365);
        
        // Verify initial setup
        Account account = customer.findAccountById("SAV002");
        assertNotNull("SAV002 account should exist initially", account);
        assertEquals("Balance should be 0", 0.0, account.getBalance(), 0.001);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Should successfully remove zero-balance account", result);
        
        // Customer now has no account "SAV002"
        assertNull("SAV002 account should no longer exist", customer.findAccountById("SAV002"));
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 0.0365);
        
        // Set balance to $250
        Account account = customer.findAccountById("SAV003");
        account.setBalance(250.0);
        
        // Verify initial setup
        assertEquals("Balance should be 250", 250.0, account.getBalance(), 0.001);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Should fail to remove account with non-zero balance", result);
        
        // Customer still holds account "SAV003" with balance $250
        Account remainingAccount = customer.findAccountById("SAV003");
        assertNotNull("SAV003 account should still exist", remainingAccount);
        assertEquals("Balance should remain 250", 250.0, remainingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_removeInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts:
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 account with no transactions and zero balance
        customer.addInvestmentAccount("INV001");
        InvestmentAccount inv001 = (InvestmentAccount) customer.findAccountById("INV001");
        inv001.setBalance(0.0);
        
        // Create INV002 account with balance $100 and one stock transaction
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv002 = (InvestmentAccount) customer.findAccountById("INV002");
        inv002.setBalance(100.0);
        
        // Add a stock transaction to INV002
        StockTransaction transaction = new StockTransaction();
        transaction.setStock("ABS");
        transaction.setQuantity(10);
        transaction.setPrice(5.0);
        transaction.setCommission(5.0);
        inv002.getTransactions().add(transaction);
        
        // Verify initial setup
        assertEquals("Should have exactly 2 accounts initially", 2, customer.getAccounts().size());
        
        // Action: Attempt to remove account "INV001"
        boolean resultINV001 = customer.removeInvestmentAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Should successfully remove INV001 with zero balance and no transactions", resultINV001);
        
        // Action: Attempt to remove account "INV002"
        boolean resultINV002 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Should fail to remove INV002 with transactions", resultINV002);
        
        // Verify final state
        assertNull("INV001 account should no longer exist", customer.findAccountById("INV001"));
        assertNotNull("INV002 account should still exist", customer.findAccountById("INV002"));
        assertEquals("Should have exactly 1 account remaining", 1, customer.getAccounts().size());
    }
}