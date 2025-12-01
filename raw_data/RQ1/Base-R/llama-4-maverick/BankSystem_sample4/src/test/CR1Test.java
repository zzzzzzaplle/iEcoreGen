import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;

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
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId("SAV001");
        savingsAccount.setInterestRate(2.0);
        customer.addAccount(savingsAccount);
        
        // Expected Output: True (account added successfully)
        Map<String, Account> accounts = customer.getAccounts();
        assertTrue("Account should be added successfully", accounts.containsKey("SAV001"));
        assertEquals("Customer should have exactly one account", 1, accounts.size());
        assertEquals("Account ID should match", "SAV001", accounts.get("SAV001").getId());
    }
    
    @Test
    public void testCase2_AddAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        
        InvestmentAccount existingAccount = new InvestmentAccount();
        existingAccount.setId("INV001");
        customer.addAccount(existingAccount);
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        InvestmentAccount newAccount = new InvestmentAccount();
        newAccount.setId("INV001");
        customer.addAccount(newAccount);
        
        // Expected Output: False (duplicate ID not allowed), Customer still holds exactly one account "INV001"
        Map<String, Account> accounts = customer.getAccounts();
        assertEquals("Customer should still have exactly one account", 1, accounts.size());
        assertTrue("Original account should still exist", accounts.containsKey("INV001"));
        
        // Verify it's the original account (not replaced)
        Account retrievedAccount = accounts.get("INV001");
        assertNotNull("Account should not be null", retrievedAccount);
        assertEquals("Account ID should be INV001", "INV001", retrievedAccount.getId());
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId("SAV002");
        savingsAccount.setBalance(0.0);
        savingsAccount.setInterestRate(3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount("SAV002");
        
        // Expected Output: True, Customer now has no account "SAV002"
        assertTrue("Removal should be successful for zero-balance account", result);
        Map<String, Account> accounts = customer.getAccounts();
        assertFalse("Account should no longer exist", accounts.containsKey("SAV002"));
        assertEquals("Customer should have no accounts", 0, accounts.size());
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId("SAV003");
        savingsAccount.setBalance(250.0);
        savingsAccount.setInterestRate(3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount("SAV003");
        
        // Expected Output: False, Customer still holds account "SAV003" with balance $250
        assertFalse("Removal should fail for account with balance", result);
        Map<String, Account> accounts = customer.getAccounts();
        assertTrue("Account should still exist", accounts.containsKey("SAV003"));
        assertEquals("Customer should still have one account", 1, accounts.size());
        
        Account retrievedAccount = accounts.get("SAV003");
        assertEquals("Balance should remain unchanged", 250.0, retrievedAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 - no stock transactions, zero balance
        InvestmentAccount inv001 = new InvestmentAccount();
        inv001.setId("INV001");
        inv001.setBalance(0.0);
        customer.addAccount(inv001);
        
        // Create INV002 - with stock transaction, balance $100
        InvestmentAccount inv002 = new InvestmentAccount();
        inv002.setId("INV002");
        inv002.setBalance(100.0);
        
        // Add stock transaction to INV002
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker("ABS");
        transaction.setQuantity(10);
        transaction.setPrice(5.0);
        inv002.getStockTransactions().add(transaction);
        
        customer.addAccount(inv002);
        
        // Action: Attempt to remove account "INV001" and "INV002"
        boolean resultInv001 = customer.removeAccount("INV001");
        boolean resultInv002 = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV001" : True, remove "INV002" : False
        assertTrue("INV001 should be removable (zero balance, no transactions)", resultInv001);
        assertFalse("INV002 should not be removable (has stock transactions)", resultInv002);
        
        Map<String, Account> accounts = customer.getAccounts();
        assertFalse("INV001 should be removed", accounts.containsKey("INV001"));
        assertTrue("INV002 should still exist", accounts.containsKey("INV002"));
        assertEquals("Customer should have one remaining account", 1, accounts.size());
    }
}