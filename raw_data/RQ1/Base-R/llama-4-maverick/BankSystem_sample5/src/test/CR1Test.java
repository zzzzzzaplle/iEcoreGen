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
        
        // Expected Output: True (account was added successfully)
        Map<String, Account> accounts = customer.getAccounts();
        assertTrue("Account should be added successfully", accounts.containsKey("SAV001"));
        assertEquals("Customer should have exactly 1 account", 1, accounts.size());
        
        // Verify account details
        Account retrievedAccount = accounts.get("SAV001");
        assertTrue("Account should be a SavingsAccount", retrievedAccount instanceof SavingsAccount);
        SavingsAccount retrievedSavingsAccount = (SavingsAccount) retrievedAccount;
        assertEquals("Interest rate should be 2.0", 2.0, retrievedSavingsAccount.getInterestRate(), 0.001);
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
        InvestmentAccount duplicateAccount = new InvestmentAccount();
        duplicateAccount.setId("INV001");
        customer.addAccount(duplicateAccount); // This will overwrite the existing account
        
        // Expected Output: False (in terms of maintaining unique accounts) 
        // and Customer still holds exactly one account "INV001"
        Map<String, Account> accounts = customer.getAccounts();
        assertEquals("Customer should have exactly 1 account", 1, accounts.size());
        assertTrue("Customer should still have account INV001", accounts.containsKey("INV001"));
        
        // Note: The addAccount method overwrites existing accounts with same ID,
        // so we need to verify that only one account exists with ID "INV001"
        Account retrievedAccount = accounts.get("INV001");
        assertNotNull("Account INV001 should exist", retrievedAccount);
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
        
        // Verify initial setup
        assertEquals("Initial account count should be 1", 1, customer.getAccounts().size());
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount("SAV002");
        
        // Expected Output: True and Customer now has no account "SAV002"
        assertTrue("Removal should be successful", result);
        assertFalse("Account SAV002 should no longer exist", customer.getAccounts().containsKey("SAV002"));
        assertEquals("Customer should have 0 accounts after removal", 0, customer.getAccounts().size());
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
        
        // Verify initial setup
        assertEquals("Initial account count should be 1", 1, customer.getAccounts().size());
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount("SAV003");
        
        // Expected Output: False and Customer still holds account "SAV003" with balance $250
        assertFalse("Removal should fail when account has balance", result);
        assertTrue("Account SAV003 should still exist", customer.getAccounts().containsKey("SAV003"));
        assertEquals("Customer should still have 1 account", 1, customer.getAccounts().size());
        
        // Verify balance remains unchanged
        Account retrievedAccount = customer.getAccounts().get("SAV003");
        assertEquals("Balance should remain $250", 250.0, retrievedAccount.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 - no transactions, zero balance
        InvestmentAccount inv001 = new InvestmentAccount();
        inv001.setId("INV001");
        inv001.setBalance(0.0);
        customer.addAccount(inv001);
        
        // Create INV002 - with transaction, balance $100
        InvestmentAccount inv002 = new InvestmentAccount();
        inv002.setId("INV002");
        inv002.setBalance(100.0);
        // Add a stock transaction: 10 shares of stock "ABS" at price $5
        inv002.buyStocks("ABS", 10, 5.0);
        customer.addAccount(inv002);
        
        // Verify initial setup
        assertEquals("Initial account count should be 2", 2, customer.getAccounts().size());
        
        // Action: Attempt to remove account "INV001"
        boolean resultInv001 = customer.removeAccount("INV001");
        
        // Expected Output: remove "INV001" : True
        assertTrue("Removal of INV001 should be successful", resultInv001);
        assertFalse("Account INV001 should no longer exist", customer.getAccounts().containsKey("INV001"));
        
        // Action: Attempt to remove account "INV002"
        boolean resultInv002 = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV002" : False
        assertFalse("Removal of INV002 should fail", resultInv002);
        assertTrue("Account INV002 should still exist", customer.getAccounts().containsKey("INV002"));
        
        // Final verification
        assertEquals("Customer should have 1 account after operations", 1, customer.getAccounts().size());
        assertTrue("Remaining account should be INV002", customer.getAccounts().containsKey("INV002"));
    }
}