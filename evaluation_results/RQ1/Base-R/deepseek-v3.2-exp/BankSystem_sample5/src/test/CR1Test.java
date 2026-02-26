import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class CR1Test {
    
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_AddNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        bankSystem.addCustomer("John Doe", "123 Main St");
        Customer customer = bankSystem.findCustomer("John Doe", "123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        boolean result = customer.addAccount("SAV001", "SAVINGS");
        SavingsAccount account = (SavingsAccount) customer.getAccountById("SAV001");
        account.setInterestRate(2.0);
        
        // Expected Output: True
        assertTrue("Adding savings account should return true", result);
        assertNotNull("Account should be added to customer", account);
        assertEquals("Customer should have exactly 1 account", 1, customer.getAccounts().size());
    }
    
    @Test
    public void testCase2_AddAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        bankSystem.addCustomer("Mia", "123 Main St");
        Customer customer = bankSystem.findCustomer("Mia", "123 Main St");
        customer.addAccount("INV001", "INVESTMENT");
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        boolean result = customer.addAccount("INV001", "INVESTMENT");
        
        // Expected Output: False, Customer still holds exactly one account "INV001"
        assertFalse("Adding duplicate account ID should return false", result);
        assertEquals("Customer should still have exactly one account", 1, customer.getAccounts().size());
        assertNotNull("Original account should still exist", customer.getAccountById("INV001"));
    }
    
    @Test
    public void testCase3_RemoveExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        bankSystem.addCustomer("Doli", "123 Main St");
        Customer customer = bankSystem.findCustomer("Doli", "123 Main St");
        customer.addAccount("SAV002", "SAVINGS");
        SavingsAccount account = (SavingsAccount) customer.getAccountById("SAV002");
        account.setInterestRate(3.65);
        account.setBalance(0.0); // Ensure zero balance
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount("SAV002");
        
        // Expected Output: True, Customer now has no account "SAV002"
        assertTrue("Removing zero-balance account should return true", result);
        assertNull("Account should be removed from customer", customer.getAccountById("SAV002"));
        assertEquals("Customer should have no accounts", 0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_AttemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        bankSystem.addCustomer("Liam", "123 Main St");
        Customer customer = bankSystem.findCustomer("Liam", "123 Main St");
        customer.addAccount("SAV003", "SAVINGS");
        SavingsAccount account = (SavingsAccount) customer.getAccountById("SAV003");
        account.setInterestRate(3.65);
        account.setBalance(250.0); // Set balance to $250
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount("SAV003");
        
        // Expected Output: False, Customer still holds account "SAV003" with balance $250
        assertFalse("Removing account with balance should return false", result);
        assertNotNull("Account should still exist", customer.getAccountById("SAV003"));
        assertEquals("Customer should still have one account", 1, customer.getAccounts().size());
        assertEquals("Account balance should remain $250", 250.0, account.getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_RemoveInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        bankSystem.addCustomer("Aliza", "123 Main St");
        Customer customer = bankSystem.findCustomer("Aliza", "123 Main St");
        
        // Create INV001 - no stock transactions
        customer.addAccount("INV001", "INVESTMENT");
        
        // Create INV002 - with stock transaction
        customer.addAccount("INV002", "INVESTMENT");
        InvestmentAccount inv002 = (InvestmentAccount) customer.getAccountById("INV002");
        inv002.setBalance(100.0);
        inv002.buyStock("ABS", 10, 5.0); // Add stock transaction
        
        // Action: Attempt to remove account "INV001" and "INV002"
        boolean resultINV001 = customer.removeAccount("INV001");
        boolean resultINV002 = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV001" : True, remove "INV002" : False
        assertTrue("Removing INV001 (no stock transactions) should return true", resultINV001);
        assertFalse("Removing INV002 (with stock transactions) should return false", resultINV002);
        
        // Verify INV001 was removed
        assertNull("INV001 should be removed", customer.getAccountById("INV001"));
        
        // Verify INV002 still exists
        assertNotNull("INV002 should still exist", customer.getAccountById("INV002"));
        assertEquals("Customer should have exactly one account remaining", 1, customer.getAccounts().size());
    }
}