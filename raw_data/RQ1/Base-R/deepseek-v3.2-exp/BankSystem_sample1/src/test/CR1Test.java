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
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV001");
        savingsAccount.setInterestRate(2.0);
        
        boolean result = customer.addAccount(savingsAccount);
        
        // Expected Output: True
        assertTrue("Adding new savings account should return true", result);
        
        // Verify account was added
        List<Account> accounts = customer.getAccounts();
        assertEquals("Customer should have exactly 1 account", 1, accounts.size());
        assertEquals("Account ID should be SAV001", "SAV001", accounts.get(0).getAccountId());
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        
        InvestmentAccount existingAccount = new InvestmentAccount();
        existingAccount.setAccountId("INV001");
        customer.addAccount(existingAccount);
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        InvestmentAccount duplicateAccount = new InvestmentAccount();
        duplicateAccount.setAccountId("INV001");
        
        boolean result = customer.addAccount(duplicateAccount);
        
        // Expected Output: False
        assertFalse("Adding duplicate account ID should return false", result);
        
        // Expected: Customer still holds exactly one account "INV001"
        List<Account> accounts = customer.getAccounts();
        assertEquals("Customer should still have exactly 1 account", 1, accounts.size());
        assertEquals("Account ID should still be INV001", "INV001", accounts.get(0).getAccountId());
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV002");
        savingsAccount.setBalance(0.0);
        savingsAccount.setInterestRate(3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount("SAV002");
        
        // Expected Output: True
        assertTrue("Removing zero-balance account should return true", result);
        
        // Expected: Customer now has no account "SAV002"
        List<Account> accounts = customer.getAccounts();
        assertTrue("Customer should have no accounts after removal", accounts.isEmpty());
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV003");
        savingsAccount.setBalance(250.0);
        savingsAccount.setInterestRate(3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount("SAV003");
        
        // Expected Output: False
        assertFalse("Removing account with balance should return false", result);
        
        // Expected: Customer still holds account "SAV003" with balance $250
        List<Account> accounts = customer.getAccounts();
        assertEquals("Customer should still have 1 account", 1, accounts.size());
        assertEquals("Account ID should be SAV003", "SAV003", accounts.get(0).getAccountId());
        assertEquals("Balance should remain $250", 250.0, accounts.get(0).getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_removeInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Account INV001 - no stock transactions, can be removed
        InvestmentAccount account1 = new InvestmentAccount();
        account1.setAccountId("INV001");
        account1.setBalance(0.0);
        customer.addAccount(account1);
        
        // Account INV002 - has stock transaction, cannot be removed
        InvestmentAccount account2 = new InvestmentAccount();
        account2.setAccountId("INV002");
        account2.setBalance(100.0);
        
        // Add stock transaction to INV002
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker("ABS");
        transaction.setQuantity(10);
        transaction.setPurchasePrice(5.0);
        account2.getStockTransactions().add(transaction);
        
        customer.addAccount(account2);
        
        // Action: Attempt to remove account "INV001"
        boolean result1 = customer.removeAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Removing INV001 (no balance, no transactions) should return true", result1);
        
        // Action: Attempt to remove account "INV002"
        boolean result2 = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Removing INV002 (has stock transactions) should return false", result2);
        
        // Verify final state
        List<Account> accounts = customer.getAccounts();
        assertEquals("Customer should have 1 account remaining", 1, accounts.size());
        assertEquals("Remaining account should be INV002", "INV002", accounts.get(0).getAccountId());
    }
}