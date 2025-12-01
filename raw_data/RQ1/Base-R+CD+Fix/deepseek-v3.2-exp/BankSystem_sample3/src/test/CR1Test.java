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
        assertTrue("Savings account should be added successfully", result);
        
        // Verify account was actually added
        Account account = customer.findAccountById("SAV001");
        assertNotNull("Account should exist after successful addition", account);
        assertTrue("Account should be a SavingAccount", account instanceof SavingAccount);
        
        SavingAccount savingAccount = (SavingAccount) account;
        assertEquals("Interest rate should be set correctly", 2.0, savingAccount.getInterestRate(), 0.001);
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
        assertFalse("Adding account with duplicate ID should fail", result);
        
        // Expected: Customer still holds exactly one account "INV001"
        assertEquals("Account count should remain unchanged", initialAccountCount, customer.getAccounts().size());
        
        Account account = customer.findAccountById("INV001");
        assertNotNull("Original account should still exist", account);
        assertTrue("Account should be an InvestmentAccount", account instanceof InvestmentAccount);
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV002", 3.65);
        
        // Verify initial setup
        Account account = customer.findAccountById("SAV002");
        assertNotNull("Account should exist before removal attempt", account);
        assertEquals("Account balance should be 0", 0.0, account.getBalance(), 0.001);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeSavingAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Removal of zero-balance account should succeed", result);
        
        // Expected: Customer now has no account "SAV002"
        Account removedAccount = customer.findAccountById("SAV002");
        assertNull("Account should not exist after successful removal", removedAccount);
        assertTrue("Customer should have no accounts after removal", customer.getAccounts().isEmpty());
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        customer.addSavingAccount("SAV003", 3.65);
        
        // Deposit $250 to create a balance
        Account account = customer.findAccountById("SAV003");
        assertTrue("Deposit should succeed", account.deposit(250.0));
        assertEquals("Account balance should be $250", 250.0, account.getBalance(), 0.001);
        
        int initialAccountCount = customer.getAccounts().size();
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeSavingAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Removal of account with balance should fail", result);
        
        // Expected: Customer still holds account "SAV003" with balance $250
        assertEquals("Account count should remain unchanged", initialAccountCount, customer.getAccounts().size());
        
        Account remainingAccount = customer.findAccountById("SAV003");
        assertNotNull("Account should still exist after failed removal", remainingAccount);
        assertEquals("Account balance should remain $250", 250.0, remainingAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 - no transactions, zero balance
        customer.addInvestmentAccount("INV001");
        
        // Create INV002 - with transaction and balance
        customer.addInvestmentAccount("INV002");
        InvestmentAccount inv002 = (InvestmentAccount) customer.findAccountById("INV002");
        assertTrue("Deposit should succeed", inv002.deposit(100.0));
        assertTrue("Stock purchase should succeed", inv002.buyStock("ABS", 10, 5.0));
        
        int initialAccountCount = customer.getAccounts().size();
        assertEquals("Should have 2 accounts initially", 2, initialAccountCount);
        
        // Action: Attempt to remove account "INV001"
        boolean resultINV001 = customer.removeInvestmentAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Removal of zero-balance, no-transaction investment account should succeed", resultINV001);
        
        // Verify INV001 was removed
        assertNull("INV001 should be removed", customer.findAccountById("INV001"));
        assertEquals("Account count should decrease by 1", initialAccountCount - 1, customer.getAccounts().size());
        
        // Action: Attempt to remove account "INV002"
        boolean resultINV002 = customer.removeInvestmentAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Removal of investment account with transactions should fail", resultINV002);
        
        // Verify INV002 still exists
        assertNotNull("INV002 should still exist", customer.findAccountById("INV002"));
        InvestmentAccount remainingInv002 = (InvestmentAccount) customer.findAccountById("INV002");
        assertFalse("INV002 should still have transactions", remainingInv002.getTransactions().isEmpty());
    }
}