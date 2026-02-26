import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
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
        
        // Verify account was added
        Account account = customer.findAccountById("SAV001");
        assertNotNull("Account should be found after addition", account);
        assertTrue("Account should be a SavingAccount", account instanceof SavingAccount);
        assertEquals("Interest rate should be 2.0", 2.0, ((SavingAccount)account).getInterestRate(), 0.001);
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        customer.addInvestmentAccount("INV001");
        
        // Verify initial setup
        assertEquals("Customer should have exactly 1 account initially", 1, customer.getAccounts().size());
        assertNotNull("INV001 account should exist", customer.findAccountById("INV001"));
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addInvestmentAccount("INV001");
        
        // Expected Output: False
        assertFalse("Adding duplicate account ID should return false", result);
        
        // Customer still holds exactly one account "INV001"
        assertEquals("Customer should still have exactly 1 account", 1, customer.getAccounts().size());
        assertNotNull("INV001 account should still exist", customer.findAccountById("INV001"));
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 3.65);
        
        // Verify initial setup
        Account account = customer.findAccountById("SAV002");
        assertNotNull("SAV002 account should exist", account);
        assertEquals("Balance should be 0", 0.0, account.getBalance(), 0.001);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Removing zero-balance savings account should return true", result);
        
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
        
        // Set balance to $250
        Account account = customer.findAccountById("SAV003");
        account.setBalance(250.0);
        
        // Verify initial setup
        assertNotNull("SAV003 account should exist", account);
        assertEquals("Balance should be 250", 250.0, account.getBalance(), 0.001);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Removing account with balance should return false", result);
        
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
        
        // Add INV001 (no transactions)
        customer.addInvestmentAccount("INV001");
        
        // Add INV002 with stock transaction
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv002 = (InvestmentAccount) customer.findAccountById("INV002");
        inv002.setBalance(100.0);
        
        // Create and add stock transaction
        StockTransaction transaction = new StockTransaction();
        transaction.setStock("ABS");
        transaction.setQuantity(10);
        transaction.setPrice(5.0);
        transaction.setCommission(5.0); // 10*5*0.1 = 5.0 commission
        List<StockTransaction> transactions = new ArrayList<>();
        transactions.add(transaction);
        inv002.setTransactions(transactions);
        
        // Verify initial setup
        assertEquals("Customer should have 2 accounts initially", 2, customer.getAccounts().size());
        assertNotNull("INV001 should exist", customer.findAccountById("INV001"));
        assertNotNull("INV002 should exist", customer.findAccountById("INV002"));
        
        // Action: Attempt to remove account "INV001"
        boolean result1 = customer.removeInvestmentAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Removing INV001 should return true", result1);
        assertNull("INV001 should no longer exist", customer.findAccountById("INV001"));
        assertEquals("Customer should have 1 account after removing INV001", 1, customer.getAccounts().size());
        
        // Action: Attempt to remove account "INV002"
        boolean result2 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Removing INV002 should return false", result2);
        assertNotNull("INV002 should still exist", customer.findAccountById("INV002"));
        assertEquals("Customer should still have 1 account", 1, customer.getAccounts().size());
    }
}