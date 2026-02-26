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
        
        // Verify account was actually added
        Account account = customer.findAccountById("SAV001");
        assertNotNull("Account should be found after addition", account);
        assertTrue("Account should be a SavingAccount", account instanceof SavingAccount);
        assertEquals("Interest rate should be 2.0", 2.0, ((SavingAccount) account).getInterestRate(), 0.001);
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        
        // Verify initial setup
        assertEquals("Customer should have exactly one account initially", 1, customer.getAccounts().size());
        assertNotNull("INV001 account should exist", customer.findAccountById("INV001"));
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False
        assertFalse("Adding duplicate account ID should return false", result);
        
        // Customer still holds exactly one account "INV001"
        assertEquals("Customer should still have exactly one account", 1, customer.getAccounts().size());
        assertNotNull("INV001 account should still exist", customer.findAccountById("INV001"));
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 3.65);
        
        // Verify initial setup - account exists with zero balance
        Account account = customer.findAccountById("SAV002");
        assertNotNull("SAV002 account should exist", account);
        assertEquals("Account balance should be 0", 0.0, account.getBalance(), 0.001);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Removing zero-balance account should return true", result);
        
        // Customer now has no account "SAV002"
        assertNull("SAV002 account should no longer exist", customer.findAccountById("SAV002"));
        assertEquals("Customer should have no accounts", 0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 3.65);
        
        // Deposit $250 to create balance
        Account account = customer.findAccountById("SAV003");
        account.deposit(250.0);
        assertEquals("Account balance should be $250", 250.0, account.getBalance(), 0.001);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Removing account with balance should return false", result);
        
        // Customer still holds account "SAV003" with balance $250
        Account remainingAccount = customer.findAccountById("SAV003");
        assertNotNull("SAV003 account should still exist", remainingAccount);
        assertEquals("Account balance should remain $250", 250.0, remainingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_removeInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 account (no transactions, zero balance)
        customer.addInvestmentAccount("INV001");
        
        // Create INV002 account with balance and transaction
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv002 = (InvestmentAccount) customer.findAccountById("INV002");
        inv002.deposit(100.0); // Set balance to $100
        inv002.buyStock("ABS", 10, 5.0); // Add stock transaction
        
        // Verify setup
        assertEquals("Customer should have 2 accounts initially", 2, customer.getAccounts().size());
        InvestmentAccount inv001 = (InvestmentAccount) customer.findAccountById("INV001");
        assertEquals("INV001 should have zero balance", 0.0, inv001.getBalance(), 0.001);
        assertEquals("INV001 should have no transactions", 0, inv001.getTransactions().size());
        assertEquals("INV002 should have balance $100", 100.0, inv002.getBalance(), 0.001);
        assertEquals("INV002 should have 1 transaction", 1, inv002.getTransactions().size());
        
        // Action: Attempt to remove account "INV001"
        boolean resultINV001 = customer.removeInvestmentAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Removing zero-balance, no-transaction investment account should return true", resultINV001);
        
        // Action: Attempt to remove account "INV002"
        boolean resultINV002 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Removing investment account with transactions should return false", resultINV002);
        
        // Verify final state
        assertNull("INV001 account should be removed", customer.findAccountById("INV001"));
        assertNotNull("INV002 account should still exist", customer.findAccountById("INV002"));
        assertEquals("Customer should have 1 account remaining", 1, customer.getAccounts().size());
    }
}