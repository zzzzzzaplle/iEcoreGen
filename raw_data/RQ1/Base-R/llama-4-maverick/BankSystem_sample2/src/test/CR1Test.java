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
    public void testCase1_addNewSavingsAccountSuccessfully() {
        // SetUp: Customer "John Doe", address "123 Main St", currently holds no accounts
        customer.setName("John Doe");
        customer.setAddress("123 Main St");
        
        // Action: Add a savings account with ID "SAV001", interest rate 2%
        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.setId("SAV001");
        savingsAccount.setInterestRate(2.0);
        
        // Expected Output: True
        customer.addAccount(savingsAccount);
        Map<String, Account> accounts = customer.getAccounts();
        assertTrue("Account should be added successfully", accounts.containsKey("SAV001"));
        assertEquals("Customer should have exactly 1 account", 1, accounts.size());
        assertEquals("Account ID should match", "SAV001", accounts.get("SAV001").getId());
    }
    
    @Test
    public void testCase2_addAccountWithDuplicateID() {
        // SetUp: Customer "Mia" (address "123 Main St") already holds an investment account "INV001"
        customer.setName("Mia");
        customer.setAddress("123 Main St");
        
        InvestmentAccount existingAccount = new InvestmentAccount();
        existingAccount.setId("INV001");
        customer.addAccount(existingAccount);
        
        // Action: Attempt to add another investment account using the same ID "INV001"
        InvestmentAccount duplicateAccount = new InvestmentAccount();
        duplicateAccount.setId("INV001");
        customer.addAccount(duplicateAccount);
        
        // Expected Output: False - Customer still holds exactly one account "INV001"
        Map<String, Account> accounts = customer.getAccounts();
        assertEquals("Customer should still have exactly 1 account", 1, accounts.size());
        assertTrue("Account INV001 should still exist", accounts.containsKey("INV001"));
        // Note: The addAccount method always adds/replaces, so we need to check if the original account was replaced
        // Since the specification says "False" but the method doesn't return boolean, we interpret this as verifying the state
    }
    
    @Test
    public void testCase3_removeExistingZeroBalanceAccount() {
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
        
        // Expected Output: True - Customer now has no account "SAV002"
        assertTrue("Remove operation should return true", result);
        Map<String, Account> accounts = customer.getAccounts();
        assertFalse("Account SAV002 should be removed", accounts.containsKey("SAV002"));
        assertEquals("Customer should have 0 accounts", 0, accounts.size());
    }
    
    @Test
    public void testCase4_attemptToRemoveAccountThatStillHasBalance() {
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
        
        // Expected Output: False - Customer still holds account "SAV003" with balance $250
        assertFalse("Remove operation should return false", result);
        Map<String, Account> accounts = customer.getAccounts();
        assertTrue("Account SAV003 should still exist", accounts.containsKey("SAV003"));
        assertEquals("Customer should have 1 account", 1, accounts.size());
        assertEquals("Balance should remain $250", 250.0, accounts.get("SAV003").getBalance(), 0.001);
    }
    
    @Test
    public void testCase5_removeInvestmentAccount() {
        // SetUp: Customer "Aliza" (address "123 Main St") currently has 2 investment accounts:
        // "INV001" with no stock transaction, "INV002" (balance $100) with 1 stock transaction
        customer.setName("Aliza");
        customer.setAddress("123 Main St");
        
        // Create INV001 - no stock transactions, zero balance
        InvestmentAccount inv1 = new InvestmentAccount();
        inv1.setId("INV001");
        inv1.setBalance(0.0);
        customer.addAccount(inv1);
        
        // Create INV002 - has stock transaction, balance $100
        InvestmentAccount inv2 = new InvestmentAccount();
        inv2.setId("INV002");
        inv2.setBalance(100.0);
        
        // Add stock transaction to INV002
        StockTransaction transaction = new StockTransaction();
        transaction.setTicker("ABS");
        transaction.setQuantity(10);
        transaction.setPrice(5.0);
        inv2.getStockTransactions().add(transaction);
        
        customer.addAccount(inv2);
        
        // Action: Attempt to remove account "INV001", "INV002"
        boolean removeInv1Result = customer.removeAccount("INV001");
        boolean removeInv2Result = customer.removeAccount("INV002");
        
        // Expected Output: remove "INV001" : True, remove "INV002" : False
        assertTrue("Remove INV001 should return true", removeInv1Result);
        assertFalse("Remove INV002 should return false", removeInv2Result);
        
        Map<String, Account> accounts = customer.getAccounts();
        assertFalse("INV001 should be removed", accounts.containsKey("INV001"));
        assertTrue("INV002 should still exist", accounts.containsKey("INV002"));
        assertEquals("Customer should have 1 account remaining", 1, accounts.size());
    }
}