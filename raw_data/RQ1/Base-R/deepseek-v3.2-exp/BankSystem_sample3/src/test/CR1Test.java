import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

public class CR1Test {
    
    private Customer customer;
    private BankSystem bankSystem;
    
    @Before
    public void setUp() {
        bankSystem = new BankSystem();
    }
    
    @Test
    public void testCase1_addNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        customer = new Customer();
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV001");
        savingsAccount.setInterestRate(2.0);
        
        boolean result = customer.addAccount(savingsAccount);
        
        // Expected Output: True
        assertTrue("Adding new savings account should return true", result);
        assertEquals("Customer should have exactly 1 account", 1, customer.getAccounts().size());
        assertEquals("Account ID should be SAV001", "SAV001", customer.getAccounts().get(0).getAccountId());
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer = new Customer();
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        
        InvestmentAccount existingAccount = new InvestmentAccount();
        existingAccount.setAccountId("INV001");
        customer.addAccount(existingAccount);
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        InvestmentAccount duplicateAccount = new InvestmentAccount();
        duplicateAccount.setAccountId("INV001");
        
        boolean result = customer.addAccount(duplicateAccount);
        
        // Expected Output: False and Customer still holds exactly one account "INV001"
        assertFalse("Adding duplicate account ID should return false", result);
        assertEquals("Customer should still have exactly 1 account", 1, customer.getAccounts().size());
        assertEquals("Account ID should remain INV001", "INV001", customer.getAccounts().get(0).getAccountId());
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
        // SetUp: Customer "Doli" (address "123 Main St") holds savings account "SAV002", balance $0, interest rate 3.65%
        customer = new Customer();
        customer.setName("Doli");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV002");
        savingsAccount.setBalance(0.0);
        savingsAccount.setInterestRate(3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Remove account "SAV002"
        boolean result = customer.removeAccount("SAV002");
        
        // Expected Output: True and Customer now has no account "SAV002"
        assertTrue("Removing zero-balance account should return true", result);
        assertEquals("Customer should have 0 accounts after removal", 0, customer.getAccounts().size());
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
        // SetUp: Customer "Liam" (address "123 Main St") holds savings account "SAV003", balance $250, interest rate 3.65%
        customer = new Customer();
        customer.setName("Liam");
        customer.setAddress("123 Main St");
        
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setAccountId("SAV003");
        savingsAccount.setBalance(250.0);
        savingsAccount.setInterestRate(3.65);
        customer.addAccount(savingsAccount);
        
        // Action: Request deletion of account "SAV003"
        boolean result = customer.removeAccount("SAV003");
        
        // Expected Output: False and Customer still holds account "SAV003" with balance $250
        assertFalse("Removing account with balance should return false", result);
        assertEquals("Customer should still have 1 account", 1, customer.getAccounts().size());
        assertEquals("Account ID should remain SAV003", "SAV003", customer.getAccounts().get(0).getAccountId());
        assertEquals("Balance should remain $250", 250.0, customer.getAccounts().get(0).getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_removeInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts: 
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer = new Customer();
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 account (no stock transactions, zero balance - eligible for removal)
        InvestmentAccount inv001 = new InvestmentAccount();
        inv001.setAccountId("INV001");
        inv001.setBalance(0.0);
        
        // Create INV002 account (with stock transaction and balance - not eligible for removal)
        InvestmentAccount inv002 = new InvestmentAccount();
        inv002.setAccountId("INV002");
        inv002.setBalance(100.0);
        
        // Add stock transaction to INV002
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker("ABS");
        transaction.setQuantity(10);
        transaction.setPurchasePrice(5.0);
        transaction.setCommission(5.0); // 10 shares * $5 * 10% = $5 commission
        inv002.getStockTransactions().add(transaction);
        
        customer.addAccount(inv001);
        customer.addAccount(inv002);
        
        // Action: Attempt to remove account "INV001" and "INV002"
        boolean resultInv001 = customer.removeAccount("INV001");
        boolean resultInv002 = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV001" : True, remove "INV002" : False
        assertTrue("Removing INV001 (no balance, no transactions) should return true", resultInv001);
        assertFalse("Removing INV002 (has balance and transactions) should return false", resultInv002);
        
        // Verify customer now has only INV002 account
        assertEquals("Customer should have 1 account remaining", 1, customer.getAccounts().size());
        assertEquals("Remaining account should be INV002", "INV002", customer.getAccounts().get(0).getAccountId());
    }
}